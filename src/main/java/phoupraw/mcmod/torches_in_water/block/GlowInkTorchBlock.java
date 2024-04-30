package phoupraw.mcmod.torches_in_water.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.torches_in_water.config.TiWConfig;

import static net.minecraft.block.HopperBlock.FACING;

@SuppressWarnings("deprecation")
public class GlowInkTorchBlock extends AbstractTorchBlock implements Waterloggable {
    public static final int SOURCE = 0;
    public static final int EMPTY = 16;
    public static final IntProperty LEVEL = IntProperty.of("level", SOURCE, EMPTY);
    public static int toLevel(FluidState any) {
        return any.isOf(Fluids.WATER) || any.isOf(Fluids.FLOWING_WATER) ? getLevel(any) : EMPTY;
    }
    public static FluidState toFluidState(int level) {
        return level == EMPTY ? Fluids.EMPTY.getDefaultState() : Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, level).getFluidState();
    }
    public static int getLevel(FluidState water) {
        return water.isStill() ? 0 : (8 - Math.min(water.getLevel(), 8) + (water.get(FlowableFluid.FALLING) ? 8 : 0));
    }
    public GlowInkTorchBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LEVEL, EMPTY));
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        Direction side = state.get(FACING);
        Vec3d topPos = side.getAxis().isVertical() ?
          Vec3d.ofCenter(pos, (8 - side.getDirection().offset()) / 16.0) :
          new Vec3d(0, 12 / 16.0, 5 / 16.0)
            .rotateY((float) (-side.getHorizontal() * Math.PI / 2))
            .add(Vec3d.ofBottomCenter(pos));
        boolean inWater = toFluidState(state.get(LEVEL)).getHeight(world, pos) >= topPos.y - pos.getY();
        if (inWater || random.nextBoolean()) {
            world.addParticle(ParticleTypes.GLOW, topPos.x, topPos.y, topPos.z, 0, 0, 0);
        }
        if (inWater && random.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.GLOW_SQUID_INK, topPos.x, topPos.y, topPos.z, random.nextGaussian() / 50, random.nextGaussian() / 50, random.nextGaussian() / 50);
        }
    }
    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER;
    }
    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        Fluid fluid = fluidState.getFluid();
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            world.setBlockState(pos, state.with(LEVEL, toLevel(fluidState)), NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluid, fluid.getTickRate(world));
            return true;
        }
        if (TiWConfig.HANDLER.instance().lavaDestroy) {
            world.breakBlock(pos, true);
            world.setBlockState(pos, fluidState.getBlockState(), NOTIFY_ALL);
            return true;
        }
        return false;
    }
    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(LEVEL) == SOURCE) {
            world.setBlockState(pos, state.with(LEVEL, EMPTY), NOTIFY_ALL);
            return Items.WATER_BUCKET.getDefaultStack();
        }
        return ItemStack.EMPTY;
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        Fluid fluid = state.getFluidState().getFluid();
        if (fluid != Fluids.EMPTY) {
            world.scheduleFluidTick(pos, fluid, fluid.getTickRate(world));
        }
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return toFluidState(state.get(LEVEL));
    }
    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        return blockState == null ? null : blockState.with(LEVEL, toLevel(ctx.getWorld().getFluidState(ctx.getBlockPos())));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LEVEL);
    }
}
