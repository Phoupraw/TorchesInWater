@file:Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")

package phoupraw.mcmod.torches_in_water.block

import net.minecraft.block.*
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.particle.ParticleTypes
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.HOPPER_FACING
import net.minecraft.state.property.Property
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import phoupraw.mcmod.torches_in_water.CONFIG
import phoupraw.mcmod.torches_in_water.blockStateLevel
import java.util.*
import kotlin.math.PI

open class GlowInkTorchBlock(settings: Settings) : AbstractTorchBlock(settings), Waterloggable {
    init {
        defaultState = defaultState.with(Water, Water(Water.EMPTY))
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(Water)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)?.with(Water, Water(ctx.world.getFluidState(ctx.blockPos)))
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val side = state.get(HOPPER_FACING)
        val topPos: Vec3d =
            if (side.axis.isVertical) {
                Vec3d.ofCenter(pos, (8 - 1 * side.direction.offset()) / 16.0)
            } else {
                Vec3d(0.0, 12 / 16.0, 5 / 16.0).rotateY(side.horizontal * PI.toFloat() / -2).add(Vec3d.ofBottomCenter(pos))
            }
        val inWater = state.get(Water).fluidState.getHeight(world, pos) >= topPos.y - pos.y
        if (inWater || random.nextBoolean()) {
            world.addParticle(ParticleTypes.GLOW, topPos.x, topPos.y, topPos.z, 0.0, 0.0, 0.0)
        }
        if (inWater && random.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.GLOW_SQUID_INK, topPos.x, topPos.y, topPos.z, random.nextGaussian() / 50, random.nextGaussian() / 50, random.nextGaussian() / 50)
        }
    }

    override fun getFluidState(state: BlockState): FluidState {
        return state.get(Water).fluidState
    }

    override fun canFillWithFluid(world: BlockView, pos: BlockPos, state: BlockState, fluid: Fluid): Boolean {
        return true
    }

    override fun tryFillWithFluid(world: WorldAccess, pos: BlockPos, state: BlockState, fluidState: FluidState): Boolean {
        val fluid = fluidState.fluid
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            world.setBlockState(pos, state.with(Water, Water(fluidState)), NOTIFY_ALL)
            //world.scheduleBlockTick(pos, this, fluid.getTickRate(world))
            world.scheduleFluidTick(pos, fluid, fluid.getTickRate(world))
            return true
        }
        if (CONFIG.instance().lavaDestroy) {
            world.breakBlock(pos, true)
            world.setBlockState(pos, fluidState.blockState, NOTIFY_ALL)
            return true
        }
        return false
    }

    override fun tryDrainFluid(world: WorldAccess, pos: BlockPos, state: BlockState): ItemStack {
        if (state.get(Water).source) {
            world.setBlockState(pos, state.with(Water, Water(Water.EMPTY)), NOTIFY_ALL)
            return Items.WATER_BUCKET.defaultStack
        }
        return ItemStack.EMPTY
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, sourceBlock: Block, sourcePos: BlockPos, notify: Boolean) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify)
        val fluid = state.fluidState.fluid
        if (fluid != Fluids.EMPTY) {
            world.scheduleFluidTick(pos, fluid, fluid.getTickRate(world))
        }
    }
    @JvmInline
    value class Water(val level: Int) : Comparable<Water> {
        val fluidState: FluidState
            get() = if (level == EMPTY) Fluids.EMPTY.defaultState else Blocks.WATER.defaultState.with(FluidBlock.LEVEL, level).fluidState
        val source: Boolean get() = level == 0 || level == 8
        override fun compareTo(other: Water): Int = level.compareTo(other.level)

        companion object : Property<Water>("level", Water::class.java) {
            const val EMPTY = 16
            const val FALLING = 8
            val RANGE = 0..EMPTY
            val VALUES = RANGE.map { Water(it) }
            override fun getValues(): Collection<Water> = VALUES
            override fun parse(name: String): Optional<Water> = name.toInt().let { if (it in RANGE) Optional.of(Water(it)) else Optional.empty() }
            override fun name(value: Water): String = value.level.toString()
            operator fun invoke(fluidState: FluidState): Water = if (!(fluidState.isOf(Fluids.WATER) || fluidState.isOf(Fluids.FLOWING_WATER))) Water(EMPTY) else Water(fluidState.blockStateLevel)
        }
    }
}
