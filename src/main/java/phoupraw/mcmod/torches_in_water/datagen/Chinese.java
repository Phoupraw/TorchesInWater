package phoupraw.mcmod.torches_in_water.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.torches_in_water.TorchesInWater;
import phoupraw.mcmod.torches_in_water.config.TiWConfig;
import phoupraw.mcmod.torches_in_water.constant.TiWIDs;
import phoupraw.mcmod.torches_in_water.constant.TiWItems;

import static phoupraw.mcmod.torches_in_water.TorchesInWater.ID;

final class Chinese extends FabricLanguageProvider {
    Chinese(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }
    @Override
    public void generateTranslations(TranslationBuilder b) {
        b.add(TorchesInWater.NAME_KEY, "水中火把");
        b.add("modmenu.summaryTranslation." + ID, "把火把放在水中！");
        b.add("modmenu.descriptionTranslation." + ID, """
          - 荧光墨汁火把，可以放在水中，由荧光墨囊和木棍合成。
          """);
        b.add(TiWItems.GLOW_INK_TORCH, "荧光墨汁火把");
        b.add(TiWConfig.ITEM_DESC, "可以放在水源或水流中，以及下半砖或楼梯的侧面。");
        b.add(TiWConfig.RESTART_KEY, "此选项修改后需要重启游戏才能生效");
        b.add("config." + ID + ".lavaDestroy.desc", "可以被岩浆冲毁");
        b.add("config." + ID + ".glowInkTorch_luminance.desc", "亮度等级");
        b.add("config." + ID + ".glowInkTorch_luminance.error", "已放置的火把不会自动更新亮度，需要破坏并重新放置");
        b.add(TiWIDs.OVERRIDE.toTranslationKey("dataPack"), "移除Aquatic Torch的配方。");
    }
}
