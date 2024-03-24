package phoupraw.mcmod.torches_in_water.mixin.modmenu;

import com.terraformersmc.modmenu.gui.widget.DescriptionListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(DescriptionListWidget.class)
@Environment(EnvType.CLIENT)
abstract class MDescriptionListWidget {
    //private List<EntryListWidget.Entry<?>> children;
    //@ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lcom/terraformersmc/modmenu/gui/widget/entries/ModListEntry;getMod()Lcom/terraformersmc/modmenu/util/mod/Mod;"))
    //private Mod renderImg(Mod original, DrawContext DrawContext, int mouseX, int mouseY, float delta) {
    //    if (TiW.ID.equals(original.getId()) && children!=null) {
    //        children.add(ImageEntry.INSTANCE);
    //    }
    //    return original;
    //}
    //@ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lcom/terraformersmc/modmenu/gui/widget/DescriptionListWidget;children()Ljava/util/List;"))
    //private List<EntryListWidget.Entry<?>> getChildren(List<EntryListWidget.Entry<?>> original) {
    //    if (children==null) {
    //        children = original;
    //    }
    //    return original;
    //}
}
