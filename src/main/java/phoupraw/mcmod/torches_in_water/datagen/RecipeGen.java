package phoupraw.mcmod.torches_in_water.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import phoupraw.mcmod.torches_in_water.constant.TiWIDs;
import phoupraw.mcmod.torches_in_water.constant.TiWItems;

import java.util.function.Consumer;

final class RecipeGen extends FabricRecipeProvider {
    public RecipeGen(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, TiWItems.GLOW_INK_TORCH, 4)
          .criterion("SBMJ", RecipeProvider.conditionsFromItem(Items.GLOW_INK_SAC))
          .input('A', Items.GLOW_INK_SAC)
          .input('B', Items.STICK)
          .pattern("A")
          .pattern("B")
          .offerTo(exporter, TiWIDs.GLOW_INK_TORCH.withPrefixedPath("shaped_crafting/"));
    }
}
