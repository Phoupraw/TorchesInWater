@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package phoupraw.mcmod.torches_in_water.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderLayers
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks
import phoupraw.mcmod.torches_in_water.constant.TiWItems

object TorchesInWaterClient:ClientModInitializer {
    override fun onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),TiWBlocks.GLOW_INK_TORCH)
        ItemTooltipCallback.EVENT.register{stack,context,lines->
            if (stack.isOf(TiWItems.GLOW_INK_TORCH)){
                lines.add(1,Text.translatable(stack.translationKey+".desc").formatted(Formatting.GRAY))
            }
        }
    }
}