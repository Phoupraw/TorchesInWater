package phoupraw.mcmod.torches_in_water.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks

object TorchesInWaterClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TiWBlocks.GLOW_INK_TORCH)
        //ItemTooltipCallback.EVENT.register { stack, context, lines ->
        //    val key = stack.translationKey + ".desc"
        //    if (Language.getInstance().hasTranslation(key)) {
        //        //val desc = Text.translatable(key)
        //        val queue: Queue<Text> = ArrayDeque(lines)
        //        while (queue.isNotEmpty()) {
        //            val text = queue.poll()
        //            queue += text.siblings
        //            //if (text == desc) {
        //            //    return@register
        //            //}
        //            (text.content as? TranslatableTextContent)?.also {
        //                if (key == it.key) {
        //                    return@register
        //                }
        //            }
        //        }
        //        lines.add(1, Text.translatable(key).formatted(Formatting.GRAY))
        //    }
        //}
    }
}