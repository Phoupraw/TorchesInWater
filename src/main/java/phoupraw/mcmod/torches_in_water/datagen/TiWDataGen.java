package phoupraw.mcmod.torches_in_water.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import phoupraw.mcmod.torches_in_water.constant.TiWIDs;

public final class TiWDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator g) {
        var pack = g.createPack();
        //server
        pack.addProvider(BlockLootGen::new);
        pack.addProvider(RecipeGen::new);
        //client
        pack.addProvider(Chinese::new);
        pack.addProvider(English::new);
        pack.addProvider(ModelGen::new);
        
        //override
        var override = g.createBuiltinResourcePack(TiWIDs.OVERRIDE);
        //server
        override.addProvider(OverrideRecipeGen::new);
    }
}
