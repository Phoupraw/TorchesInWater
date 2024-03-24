package phoupraw.mcmod.torches_in_water.config

import net.minecraft.client.gui.DrawContext
import net.minecraft.item.ItemStack

fun interface ItemRendererInConfig : RendererInConfig {
    fun itemStack(): ItemStack
    override fun render(graphics: DrawContext, x: Int, y: Int, renderWidth: Int, tickDelta: Float): Int {
        graphics.drawItem(itemStack(), x, y)
        return 16
    }
}