package phoupraw.mcmod.torches_in_water.constant;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public sealed interface TiWItemTags permits InterfaceFinaler {
    //TagKey<Item> DESC = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("desc");
    private static TagKey<Item> of(Identifier id) {
        return TagKey.of(RegistryKeys.ITEM, id);
    }
}
