package phoupraw.mcmod.torches_in_water.config

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.Selectable
import net.minecraft.client.gui.widget.ElementListWidget
import phoupraw.mcmod.torches_in_water.ID

object ImageEntry : ElementListWidget.Entry<ImageEntry>() {
    override fun render(context: DrawContext, index: Int, y: Int, x: Int, entryWidth: Int, entryHeight: Int, mouseX: Int, mouseY: Int, hovered: Boolean, tickDelta: Float) {
        context.drawTexture(ID("textures/gallery/glow_ink_torch.png"), x, y, 0, 0, 1920, 1017)
    }

    override fun children(): List<Element> {
        return listOf()
    }

    override fun selectableChildren(): List<Selectable> {
        return listOf()
    }
}
