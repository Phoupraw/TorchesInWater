package phoupraw.mcmod.torches_in_water.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static net.minecraft.block.HopperBlock.FACING;

@SuppressWarnings("deprecation")
public class AbstractTorchBlock extends Block {
    public static final VoxelShape WALL = createCuboidShape(6, 3, 6, 10, 7, 10);
    public static final Map<Direction, VoxelShape> SHAPES = Map.of(
      Direction.DOWN, createCuboidShape(6, 0, 6, 10, 10, 10),
      Direction.WEST, createCuboidShape(0.0, 3.0, 5.5, 5.0, 13.0, 10.5),
      Direction.EAST, createCuboidShape(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
      Direction.NORTH, createCuboidShape(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
      Direction.SOUTH, createCuboidShape(5.5, 3.0, 11.0, 10.5, 13.0, 16.0)
    );
    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction side) {
        if (side == Direction.UP) return false;
        pos = pos.offset(side);
        Direction opposite = side.getOpposite();
        return side == Direction.DOWN ? sideCoversSmallSquare(world, pos, side) : !VoxelShapes.matchesAnywhere(world.getBlockState(pos).getSidesShape(world, pos).getFace(opposite), WALL, BooleanBiFunction.ONLY_SECOND);
    }
    public AbstractTorchBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.DOWN));
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction opposite = ctx.getSide().getOpposite();
        BlockState blockState = null;
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        if (canPlaceAt(world, pos, opposite)) {
            blockState = getDefaultState().with(FACING, opposite);
        } else {
            for (Direction facing : FACING.getValues()) {
                if (facing == opposite) continue;
                if (canPlaceAt(world, pos, facing)) {
                    blockState = getDefaultState().with(FACING, facing);
                    break;
                }
            }
        }
        return blockState;
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return state.canPlaceAt(world, pos) ? state : Blocks.AIR.getDefaultState();
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        
        builder.add(FACING);
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, state.get(FACING));
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(FACING));
    }
}
