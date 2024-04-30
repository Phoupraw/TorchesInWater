@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package phoupraw.mcmod.torches_in_water

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemGroups
import net.minecraft.item.Items
import net.minecraft.text.Text
import phoupraw.mcmod.torches_in_water.api.BlockFluidContext
import phoupraw.mcmod.torches_in_water.block.GlowInkTorchBlock
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks
import phoupraw.mcmod.torches_in_water.constant.TiWIDs
import phoupraw.mcmod.torches_in_water.constant.TiWItems

object TorchesInWater : ModInitializer {
    const val ID = "torches_in_water"
    const val NAME_KEY = "modmenu.nameTranslation.${ID}"
    override fun onInitialize() {
        TiWItems.GLOW_INK_TORCH
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register {
            it.addAfter(Items.SOUL_TORCH, TiWItems.GLOW_INK_TORCH)
        }
        BlockFluidContext.BLOCK_FLUID.registerForBlocks({ world, pos, state, blockEntity, context ->
            state.with(GlowInkTorchBlock.Water, GlowInkTorchBlock.Water(context.newFluidState))
        }, TiWBlocks.GLOW_INK_TORCH)
        ResourceManagerHelper.registerBuiltinResourcePack(TiWIDs.OVERRIDE, FabricLoader.getInstance().getModContainer(ID).orElseThrow(), Text.translatable(TiWIDs.OVERRIDE.toTranslationKey("dataPack")), ResourcePackActivationType.DEFAULT_ENABLED)
    }
}