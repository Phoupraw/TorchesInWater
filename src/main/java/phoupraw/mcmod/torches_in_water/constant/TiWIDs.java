package phoupraw.mcmod.torches_in_water.constant;

import net.minecraft.util.Identifier;
import phoupraw.mcmod.torches_in_water.config.TorchesInWaterConfig;

import static phoupraw.mcmod.torches_in_water.TiW.ID;

public sealed interface TiWIDs permits InterfaceFinaler {
    Identifier GLOW_INK_TORCH = ID(TorchesInWaterConfig.GLOW_INK_TORCH);
    Identifier WATER_GLOW_INK_TORCH = ID("water_glow_ink_torch");
    Identifier ITEM_GROUP = ID("item_group");
    Identifier OVERRIDE = ID("override");
}
