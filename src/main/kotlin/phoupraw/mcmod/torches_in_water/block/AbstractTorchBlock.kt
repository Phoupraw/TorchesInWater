@file:Suppress("OVERRIDE_DEPRECATION")

package phoupraw.mcmod.torches_in_water.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.HOPPER_FACING
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import org.jetbrains.annotations.MustBeInvokedByOverriders
import phoupraw.mcmod.torches_in_water.voxelShapeOf16

open class AbstractTorchBlock(settings: Settings) : Block(settings) {
    init {
        defaultState = defaultState.with(HOPPER_FACING, Direction.DOWN)
    }
    @MustBeInvokedByOverriders
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(HOPPER_FACING)
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
        return blockState
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

    companion object {
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
            val opposite = side.opposite
            if (side.axis.isVertical) {
                return sideCoversSmallSquare(world, pos1, opposite)
            }
            return !VoxelShapes.matchesAnywhere(world.getBlockState(pos1).getSidesShape(world, pos1).getFace(opposite), WALL, BooleanBiFunction.ONLY_SECOND)
        }
    }
}