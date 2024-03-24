@file:JvmName("TiW")

package phoupraw.mcmod.torches_in_water

import com.google.common.collect.Interner
import com.google.common.collect.Interners
import net.minecraft.block.Block
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.FluidState
import net.minecraft.util.Identifier
import net.minecraft.util.shape.VoxelShape
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import phoupraw.mcmod.torches_in_water.config.TorchesInWaterConfig
import kotlin.math.min

internal const val ID = TorchesInWater.ID
@JvmField
internal val LOGGER: Logger = LogManager.getLogger(ID)
private val IDS: Interner<Identifier> = Interners.newBuilder().build()
internal val CONFIG @JvmName("CONFIG") get() = TorchesInWaterConfig.HANDLER
internal fun ID(path: String): Identifier = IDS.intern(Identifier(ID, path))
fun voxelShapeOf16(xMin: Int, yMin: Int, zMin: Int, xMax: Int, yMax: Int, zMax: Int): VoxelShape = Block.createCuboidShape(xMin.toDouble(), yMin.toDouble(), zMin.toDouble(), xMax.toDouble(), yMax.toDouble(), zMax.toDouble())
fun Boolean.toInt() = if (this) 1 else 0
fun Int.toBoolean() = this != 0
val FluidState.blockStateLevel: Int get() = if (isStill) 0 else (8 - min(level, 8) + (if (get(FlowableFluid.FALLING)) 8 else 0))
