package phoupraw.mcmod.torches_in_water.config

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.impl.serializer.GsonConfigSerializer
import net.fabricmc.loader.api.FabricLoader
import phoupraw.mcmod.torches_in_water.ID

class TiWConfig {
    //@AutoGen(category = CATEGORY, group = GLOW_INK_TORCH)
    @JvmField
    @SerialEntry //@TickBox
    var lavaDestroy: Boolean = true
    //@AutoGen(category = CATEGORY, group = GLOW_INK_TORCH)
    @JvmField
    @SerialEntry //@IntSlider(min = 0, max = 15, step = 1)
    //@GameRestart
    var glowInkTorch_luminance: Int = 14

    companion object {
        const val CATEGORY: String = "only"
        const val GLOW_INK_TORCH: String = "glow_ink_torch"
        const val RESTART_KEY = "config.$ID.restart"
        val HANDLER: ConfigClassHandler<TiWConfig> = ConfigClassHandler
          .createBuilder(TiWConfig::class.java)
          .id(ID("cfg"))
          .serializer { handler ->
              GsonConfigSerializer.Builder(handler)
                .setJson5(true)
                .setPath(FabricLoader.getInstance().configDir.resolve("${handler.id().toTranslationKey()}.json5"))
                .build()
          }
          .build()

        init {
            HANDLER.load()
        }
    }
}
