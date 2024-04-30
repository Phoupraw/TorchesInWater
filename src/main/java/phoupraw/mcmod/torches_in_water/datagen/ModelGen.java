package phoupraw.mcmod.torches_in_water.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks;
import phoupraw.mcmod.torches_in_water.constant.TiWItems;

final class ModelGen extends FabricModelProvider {
    public ModelGen(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator g) {
        BlockStateVariantMap.SingleProperty<Direction> variantMap = BlockStateVariantMap.create(Properties.HOPPER_FACING);
        variantMap.register(Direction.DOWN, BlockStateVariant.create()
          .put(VariantSettings.MODEL, ModelIds.getBlockModelId(TiWBlocks.GLOW_INK_TORCH)));
        for (Direction it : Direction.Type.HORIZONTAL) {
            variantMap.register(it, BlockStateVariant.create()
              .put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(TiWBlocks.GLOW_INK_TORCH, "_wall"))
              .put(VariantSettings.Y, VariantSettings.Rotation.values()[(it.getHorizontal() + 3) % 4]));
        }
        g.blockStateCollector.accept(VariantsBlockStateSupplier.create(TiWBlocks.GLOW_INK_TORCH).coordinate(variantMap));
        g.excludeFromSimpleItemModelGeneration(TiWBlocks.GLOW_INK_TORCH);
        Models.GENERATED.upload(ModelIds.getItemModelId(TiWItems.GLOW_INK_TORCH), TextureMap.layer0(TiWBlocks.GLOW_INK_TORCH), g.modelCollector);
    }
    @Override
    public void generateItemModels(ItemModelGenerator g) {
    
    }
}
