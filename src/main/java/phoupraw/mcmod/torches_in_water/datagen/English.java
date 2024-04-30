package phoupraw.mcmod.torches_in_water.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.torches_in_water.TorchesInWater;
import phoupraw.mcmod.torches_in_water.config.TiWConfig;
import phoupraw.mcmod.torches_in_water.constant.TiWIDs;
import phoupraw.mcmod.torches_in_water.constant.TiWItems;

import static phoupraw.mcmod.torches_in_water.TorchesInWater.ID;

final class English extends FabricLanguageProvider {
    English(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    @Override
    public void generateTranslations(TranslationBuilder b) {
        b.add(TorchesInWater.NAME_KEY, "Torches in Water");
        b.add("modmenu.summaryTranslation." + ID, "Place torches in water!");
        b.add("modmenu.descriptionTranslation." + ID, """
          - Glow Ink Torch, which can be placed in water, crafted from glow ink sac and stick.
          """);
        b.add(TiWItems.GLOW_INK_TORCH, "Glow Ink Torch");
        b.add(TiWConfig.ITEM_DESC, "Can be placed in water source or flow. Can be placed on the side of bottom slab or stairs.");
        b.add(TiWConfig.RESTART_KEY, "After modifying this option, you need to restart the game to take effect");
        b.add("config." + ID + ".lavaDestroy.desc", "Lava can flow into and destory");
        b.add("config." + ID + ".glowInkTorch_luminance.desc", "Light level");
        b.add("config." + ID + ".glowInkTorch_luminance.error", "Placed torches won't auto update light level. You need to break and re-place them");
        b.add(TiWIDs.OVERRIDE.toTranslationKey("dataPack"), "Remove recipe of Aquatic Torch.");
    }
}
