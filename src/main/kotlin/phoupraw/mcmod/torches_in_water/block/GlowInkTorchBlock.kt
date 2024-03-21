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
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import phoupraw.mcmod.torches_in_water.CONFIG
import phoupraw.mcmod.torches_in_water.blockStateLevel
import phoupraw.mcmod.torches_in_water.voxelShapeOf16
import java.util.*
import kotlin.math.PI

open class GlowInkTorchBlock(settings: Settings) : Block(settings), Waterloggable {
    init {
        defaultState = defaultState.with(HOPPER_FACING, Direction.DOWN).with(Water, Water(Water.EMPTY))
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(HOPPER_FACING, Water)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val opposite = ctx.side.opposite
        var blockState: BlockState? = null
        val world = ctx.world
        val pos = ctx.blockPos
        if (canPlaceAt(opposite, world, pos)) {
            blockState = defaultState.with(HOPPER_FACING, opposite)
        } else {
            for (side in HOPPER_FACING.values) {
                if (side == opposite) continue
                if (canPlaceAt(side, world, pos)) {
                    blockState = defaultState.with(HOPPER_FACING, side)
                    break
                }
            }
        }
        return blockState?.with(Water, Water(world.getFluidState(pos)))
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        return canPlaceAt(state.get(HOPPER_FACING), world, pos)
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        return SHAPES.getOrElse(state.get(HOPPER_FACING)) { VoxelShapes.fullCube() }
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        return if (state.canPlaceAt(world, pos)) state else Blocks.AIR.defaultState
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val side = state.get(HOPPER_FACING)
        val topPos: Vec3d =
            if (side.axis.isVertical) {
                Vec3d.ofCenter(pos, (8 - 1 * side.direction.offset()) / 16.0)
            } else {
                Vec3d(0.0, 12 / 16.0, 5 / 16.0).rotateY(side.horizontal * PI.toFloat() / -2).add(Vec3d.ofBottomCenter(pos))
            }
        //randomAddParticle(world, topPos, random)
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
            //world.scheduleBlockTick(pos, this, fluid.getTickRate(world))
            world.scheduleFluidTick(pos, fluid, fluid.getTickRate(world))
        }
    }

    //override fun scheduledTick(blockState: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
    //    val fluidState = blockState.fluidState
    //    if (!fluidState.isEmpty) {
    //        fluidState.onScheduledTick(world, pos)
    //        val fluidState1 = world.getFluidState(pos)
    //        world.setBlockState(pos, blockState.with(Water, Water(fluidState1)))
    //        if (!fluidState1.isEmpty) {
    //            world.scheduleBlockTick(pos, this, fluidState1.fluid.getTickRate(world))
    //        }
    //    }
    //}

    companion object {
        //val LEVEL: IntProperty = IntProperty.of("level", -2, 15)
        val SHAPES: Map<Direction, VoxelShape> = mapOf(
            Direction.DOWN to voxelShapeOf16(6, 0, 6, 10, 10, 10),
            Direction.WEST to createCuboidShape(0.0, 3.0, 5.5, 5.0, 13.0, 10.5),
            Direction.EAST to createCuboidShape(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
            Direction.NORTH to createCuboidShape(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
            Direction.SOUTH to createCuboidShape(5.5, 3.0, 11.0, 10.5, 13.0, 16.0)
        )
        val WALL = voxelShapeOf16(6, 3, 6, 10, 7, 10)
        /**
         * 如果是插在地上，那么[side]是[Direction.DOWN]，如果插在西边的墙上，那么[side]是[Direction.WEST]。
         */
        fun canPlaceAt(side: Direction, world: WorldView, pos: BlockPos): Boolean {
            val pos1 = pos.offset(side)
            //println(pos1)
            //println(world.getBlockState(pos1))
            val opposite = side.opposite
            //println(world.getBlockState(pos1).getSidesShape(world, pos1).getFace(opposite))
            if (side.axis.isVertical) {
                return sideCoversSmallSquare(world, pos1, opposite)
            }
            return !VoxelShapes.matchesAnywhere(world.getBlockState(pos1).getSidesShape(world, pos1).getFace(opposite), WALL, BooleanBiFunction.ONLY_SECOND)
        }
    }
    @JvmInline
    value class Water(val level: Int) : Comparable<Water> {
        val fluidState: FluidState
            get() = if (level == EMPTY) Fluids.EMPTY.defaultState else Blocks.WATER.defaultState.with(FluidBlock.LEVEL, level).fluidState
        val source: Boolean get() = level % FALLING == 0 && level != EMPTY
        override fun compareTo(other: Water): Int {
            return level.compareTo(other.level)
        }

        companion object : Property<Water>("level", Water::class.java) {
            //const val STILL = 16
            const val EMPTY = 16
            const val FALLING = 8
            val RANGE = 0..EMPTY
            val VALUES = RANGE.map { Water(it) }
            override fun getValues(): Collection<Water> = VALUES
            override fun parse(name: String): Optional<Water> {
                return name.toInt().let { if (it in RANGE) Optional.of(Water(it)) else Optional.empty() }
            }

            override fun name(value: Water): String {
                return value.level.toString()
            }

            operator fun invoke(fluidState: FluidState): Water {
                if (!(fluidState.isOf(Fluids.WATER) || fluidState.isOf(Fluids.FLOWING_WATER))) return Water(EMPTY)
                //val level = fluidState.get(FlowableFluid.LEVEL)
                //val falling = fluidState.get(FlowableFluid.FALLING)
                return Water(fluidState.blockStateLevel)
            }
        }
    }
}
