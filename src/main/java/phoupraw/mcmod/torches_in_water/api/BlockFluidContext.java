package phoupraw.mcmod.torches_in_water.api;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import phoupraw.mcmod.torches_in_water.constant.TiWIDs;

public record BlockFluidContext(FluidState oldFluidState, FluidState newFluidState) {
    public static final BlockApiLookup<BlockState, BlockFluidContext> BLOCK_FLUID = BlockApiLookup.get(TiWIDs.of("block_fluid"), BlockState.class, BlockFluidContext.class);
}
