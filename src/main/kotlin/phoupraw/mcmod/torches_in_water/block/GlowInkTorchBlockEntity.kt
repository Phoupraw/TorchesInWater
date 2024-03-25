package phoupraw.mcmod.torches_in_water.block

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.util.math.BlockPos

class GlowInkTorchBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(type, pos, state) {
    var fluidState: FluidState = Fluids.EMPTY.defaultState

    constructor(pos: BlockPos, state: BlockState) : this(TODO(), pos, state)
}