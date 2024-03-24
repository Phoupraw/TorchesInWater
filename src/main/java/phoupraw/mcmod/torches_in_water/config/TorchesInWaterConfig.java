package phoupraw.mcmod.torches_in_water.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.impl.serializer.GsonConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import phoupraw.mcmod.torches_in_water.TiW;

public final class TorchesInWaterConfig {
    public static final String CATEGORY = "only";
    public static final String GLOW_INK_TORCH = "glow_ink_torch";
    public static final ConfigClassHandler<TorchesInWaterConfig> HANDLER = ConfigClassHandler
      .createBuilder(TorchesInWaterConfig.class)
      .id(TiW.ID("cfg"))
      .serializer(handler -> new GsonConfigSerializer.Builder<>(handler)
        .setJson5(true)
        .setPath(FabricLoader.getInstance().getConfigDir().resolve(handler.id().toTranslationKey() + ".json5"))
        .build())
      .build();
    static {
        HANDLER.load();
    }
    //@AutoGen(category = CATEGORY, group = GLOW_INK_TORCH)
    @SerialEntry
    //@TickBox
    public boolean lavaDestroy = true;
    //@AutoGen(category = CATEGORY, group = GLOW_INK_TORCH)
    @SerialEntry
    //@IntSlider(min = 0, max = 15, step = 1)
    //@GameRestart
    public int glowInkTorch_luminance = 14;
}
