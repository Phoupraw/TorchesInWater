@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package phoupraw.mcmod.torches_in_water.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.client.render.RenderLayer
import net.minecraft.text.Text
import net.minecraft.text.TranslatableTextContent
import net.minecraft.util.Formatting
import net.minecraft.util.Language
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks
import java.util.*

object TorchesInWaterClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TiWBlocks.GLOW_INK_TORCH)
        ItemTooltipCallback.EVENT.register { stack, context, lines ->
            val key = stack.translationKey + ".desc"
            if (Language.getInstance().hasTranslation(key)) {
                //val desc = Text.translatable(key)
                val queue: Queue<Text> = ArrayDeque(lines)
                while (queue.isNotEmpty()) {
                    val text = queue.poll()
                    queue += text.siblings
                    //if (text == desc) {
                    //    return@register
                    //}
                    (text.content as? TranslatableTextContent)?.also {
                        if (key == it.key) {
                            return@register
                        }
                    }
                }
                lines.add(1, Text.translatable(key).formatted(Formatting.GRAY))
            }
        }
    }
}