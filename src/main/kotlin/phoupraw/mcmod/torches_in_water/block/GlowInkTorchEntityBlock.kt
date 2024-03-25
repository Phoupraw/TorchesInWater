@file:Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")

package phoupraw.mcmod.torches_in_water.block

import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.FluidDrainable
import net.minecraft.block.entity.BlockEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess
import java.util.*

class GlowInkTorchEntityBlock(settings: Settings) : AbstractTorchBlock(settings), FluidDrainable, BlockEntityProvider {
    override fun getFluidState(state: BlockState): FluidState {
        return super.getFluidState(state)
    }

    override fun tryDrainFluid(world: WorldAccess, pos: BlockPos, state: BlockState): ItemStack {
        TODO("Not yet implemented")
    }

    override fun getBucketFillSound(): Optional<SoundEvent> {
        return Fluids.WATER.bucketFillSound
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return GlowInkTorchBlockEntity(pos, state)
    }
}