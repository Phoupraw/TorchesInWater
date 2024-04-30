package phoupraw.mcmod.torches_in_water.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks;

final class BlockLootGen extends FabricBlockLootTableProvider {
    
    BlockLootGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    @Override
    public void generate() {
        addDrop(TiWBlocks.GLOW_INK_TORCH);
    }
}
