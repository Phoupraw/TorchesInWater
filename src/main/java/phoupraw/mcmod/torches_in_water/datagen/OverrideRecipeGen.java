package phoupraw.mcmod.torches_in_water.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

final class OverrideRecipeGen extends FabricRecipeProvider {
    public OverrideRecipeGen(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ComplexRecipeJsonBuilder.create(RecipeSerializer.REPAIR_ITEM).offerTo(exporter, "aquatictorches:aquatic_torch");
    }
    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }
}
