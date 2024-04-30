package phoupraw.mcmod.torches_in_water.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.loader.api.FabricLoader;
import phoupraw.mcmod.torches_in_water.TorchesInWater;
import phoupraw.mcmod.torches_in_water.constant.TiWIDs;

@Getter
@Setter
public final class TiWConfig {
    public static final String GLOW_INK_TORCH = "glow_ink_torch";
    public static final String RESTART_KEY = "config." + TorchesInWater.ID + ".restart";
    public static final String ITEM_DESC = TiWIDs.GLOW_INK_TORCH.toTranslationKey("item", "desc");
    public static final ConfigClassHandler<TiWConfig> HANDLER = ConfigClassHandler
      .createBuilder(TiWConfig.class)
      .id(TiWIDs.of("cfg"))
      .serializer(handler -> GsonConfigSerializerBuilder
        .create(handler)
        .setJson5(true)
        .setPath(FabricLoader.getInstance().getConfigDir().resolve(TorchesInWater.ID + ".cfg.json5"))
        .build())
      .build();
    @SerialEntry
    public boolean lavaDestroy = true;
    @SerialEntry
    public int glowInkTorch_luminance = 14;
}
