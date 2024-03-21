package phoupraw.mcmod.torches_in_water.config

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen
import dev.isxander.yacl3.config.v2.api.autogen.DoubleField
import dev.isxander.yacl3.config.v2.api.autogen.TickBox
import dev.isxander.yacl3.config.v2.impl.serializer.GsonConfigSerializer
import net.fabricmc.loader.api.FabricLoader
import phoupraw.mcmod.torches_in_water.ID
import phoupraw.mcmod.linked.fabric.loader.Fabric

class TorchesInWaterConfig {
    @AutoGen(category = CATEGORY, group = GLOW_INK_TORCH)
    @SerialEntry
    @TickBox
    @JvmField
    var lavaDestroy = true

    companion object {
        const val CATEGORY = "only"
        const val GLOW_INK_TORCH = "glow_ink_torch"
        @JvmField
        val HANDLER: ConfigClassHandler<TorchesInWaterConfig> = ConfigClassHandler.createBuilder(TorchesInWaterConfig::class.java)
          .id(ID("cfg"))
          .serializer {
              GsonConfigSerializer.Builder(it)
                .setJson5(true)
                .setPath(FabricLoader.getInstance().configDir.resolve("${it.id().toTranslationKey()}.json5"))
                .build()
          }
          .build()
          .apply { load() }
    }
}