package phoupraw.mcmod.torches_in_water.constant;

import net.minecraft.util.Identifier;
import phoupraw.mcmod.torches_in_water.TorchesInWater;
import phoupraw.mcmod.torches_in_water.config.TorchesInWaterConfig;

public interface TiWIDs {
    Identifier GLOW_INK_TORCH = of(TorchesInWaterConfig.GLOW_INK_TORCH);
    Identifier WATER_GLOW_INK_TORCH = of("water_glow_ink_torch");
    Identifier ITEM_GROUP = of("item_group");
    static Identifier of(String path) {
        return new Identifier(TorchesInWater.ID,path);
    }
}