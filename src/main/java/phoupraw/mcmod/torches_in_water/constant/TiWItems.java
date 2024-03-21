package phoupraw.mcmod.torches_in_water.constant;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.torches_in_water.TiW;
import phoupraw.mcmod.torches_in_water.TorchesInWater;

public interface TiWItems {
    BlockItem GLOW_INK_TORCH = r(TiWIDs.GLOW_INK_TORCH, new BlockItem(TiWBlocks.GLOW_INK_TORCH, new FabricItemSettings()));
    ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, TiWIDs.ITEM_GROUP,FabricItemGroup.builder()
      .displayName(Text.translatable(TorchesInWater.NAME))
      .icon(GLOW_INK_TORCH::getDefaultStack)
      .entries(TiWItems::entries)
      .build());
    private static <T extends Item> T r(Identifier id, T value) {
        return Registry.register(Registries.ITEM, id, value);
    }
    private static void entries(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {entries.add(GLOW_INK_TORCH);}
}
