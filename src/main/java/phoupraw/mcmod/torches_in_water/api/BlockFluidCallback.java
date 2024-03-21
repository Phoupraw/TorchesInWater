package phoupraw.mcmod.torches_in_water.api;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public class BlockFluidCallback {
    @SuppressWarnings("unchecked")
    public static final BlockApiLookup<BlockState, Pair<FluidState, FluidState>> BLOCK_FLUID = BlockApiLookup.get(new Identifier("", "block_fluid"), BlockState.class, (Class<Pair<FluidState, FluidState>>) (Object) Pair.class);
    static {
        //BLOCK_FLUID.registerFallback((world, pos, state, blockEntity, context) -> {
        //    if (state instanceof FluidFillable fluidFillable) {
        //        if (fluidFillable.canFillWithFluid(world,pos,state,context.getRight().getFluid())) {
        //
        //        }
        //    }
        //    return null;
        //});
    }
}
