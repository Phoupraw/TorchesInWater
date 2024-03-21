package phoupraw.mcmod.torches_in_water.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryWrapper
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction
import phoupraw.mcmod.torches_in_water.CONFIG
import phoupraw.mcmod.torches_in_water.ID
import phoupraw.mcmod.torches_in_water.TorchesInWater
import phoupraw.mcmod.torches_in_water.config.TorchesInWaterConfig
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks
import phoupraw.mcmod.torches_in_water.constant.TiWIDs
import phoupraw.mcmod.torches_in_water.constant.TiWItems
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import kotlin.reflect.KProperty

object TorchesInWaterDataGen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(g: FabricDataGenerator) {
        val pack = g.createPack()
        pack.addProvider(::BlockLootGen)
        pack.addProvider(::FluidTagGen)
        pack.addProvider(::ItemTagGen)
        pack.addProvider(::RecipeGen)

        pack.addProvider(::ModelGen)
        pack.addProvider(::ChineseGen)
        pack.addProvider(::EnglishGen)
    }
}

private class BlockLootGen(dataOutput: FabricDataOutput) : FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {
        addDrop(TiWBlocks.GLOW_INK_TORCH)
    }
}

private class FluidTagGen(output: FabricDataOutput, completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) : FabricTagProvider.FluidTagProvider(output, completableFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup) {
        //getOrCreateTagBuilder(InfiniteFluidBucket.INFINITE).add(Fluids.EMPTY, Fluids.WATER)
    }
}

private class ItemTagGen(output: FabricDataOutput, completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) : FabricTagProvider.ItemTagProvider(output, completableFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup) {
        //getOrCreateTagBuilder(InfiniteFluidBucket.INFINITE).add(Items.BUCKET, Items.GLASS_BOTTLE, Items.WATER_BUCKET)
    }
}
private class RecipeGen(output: FabricDataOutput) : FabricRecipeProvider(output) {
    override fun generate(exporter: Consumer<RecipeJsonProvider>) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS,TiWItems.GLOW_INK_TORCH)
          .criterion("SBMJ", RecipeProvider.conditionsFromItem(Items.GLOW_INK_SAC))
          .input('A',Items.GLOW_INK_SAC)
          .input('B',Items.STICK)
          .pattern("A")
          .pattern("B")
          .offerTo(exporter,TiWIDs.GLOW_INK_TORCH.withPrefixedPath("shaped_crafting/"))
    }
}

private val CFG = "yacl3.config.${CONFIG.id()}"
private fun FabricLanguageProvider.TranslationBuilder.addYACLGroup(/*category:String,*/group: String, groupTranslation: String) {
    add("$CFG.category.${TorchesInWaterConfig.CATEGORY}.group.$group", groupTranslation)
}

private fun FabricLanguageProvider.TranslationBuilder.add(property: KProperty<*>, name: String, desc: String) {
    val configName = "$CFG.${property.name}"
    add(configName, name)
    add("$configName.desc", desc)
}

private class ChineseGen(dataOutput: FabricDataOutput) : FabricLanguageProvider(dataOutput, "zh_cn"/*傻逼MCDEV不能禁用警告*/) {
    override fun generateTranslations(b: TranslationBuilder) {
        b.add(TorchesInWater.NAME, "水中火把")
        b.add("modmenu.descriptionTranslation.$ID", "添加了可以放置在水下的火把。")
        val glowInkTorchName = "荧光墨汁火把"
        b.add(TiWBlocks.GLOW_INK_TORCH, glowInkTorchName)
        b.add(TiWItems.GLOW_INK_TORCH.translationKey+".desc","可以放在水源或水流中。可以放在下半砖或楼梯的侧面。会被岩浆冲毁（可设置）。")
        //b.add(TiWBlocks.WATER_GLOW_INK_TORCH, "水中的荧光墨囊火把")
        b.add("$CFG.category.${TorchesInWaterConfig.CATEGORY}", "设置")
        b.addYACLGroup(TorchesInWaterConfig.GLOW_INK_TORCH, glowInkTorchName)
        b.add(TorchesInWaterConfig::lavaDestroy, "岩浆破坏", "岩浆可以破坏$glowInkTorchName。")
    }
}
private class EnglishGen(dataOutput: FabricDataOutput) : FabricLanguageProvider(dataOutput) {
    override fun generateTranslations(b: TranslationBuilder) {
        b.add(TorchesInWater.NAME, "Torches in Water")
        b.add("modmenu.descriptionTranslation.$ID", "Added torches which can be placed in water.")
        val glowInkTorchName = "Glow Ink Torch"
        b.add(TiWBlocks.GLOW_INK_TORCH, glowInkTorchName)
        b.add(TiWItems.GLOW_INK_TORCH.translationKey+".desc","Can be placed in water source or flow. Can be placed on the side of bottom slab or stairs. Will be destroyed by lava (can be configured).")
        //b.add(TiWBlocks.WATER_GLOW_INK_TORCH, "水中的荧光墨囊火把")
        b.add("$CFG.category.${TorchesInWaterConfig.CATEGORY}", "Settings")
        b.addYACLGroup(TorchesInWaterConfig.GLOW_INK_TORCH, glowInkTorchName)
        b.add(TorchesInWaterConfig::lavaDestroy, "Lava Destroy", "Lava can destroy $glowInkTorchName.")
    }
}
private class ModelGen(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(g: BlockStateModelGenerator) {
        g.addTorchBlock(TiWBlocks.GLOW_INK_TORCH)
        g.addTorchItem(TiWBlocks.GLOW_INK_TORCH)
    }


    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
    }
    private fun BlockStateModelGenerator.addTorchBlock(torch: Block) {
        blockStateCollector.accept(VariantsBlockStateSupplier.create(torch).coordinate(BlockStateVariantMap.create(Properties.HOPPER_FACING)
          .register(Direction.DOWN, BlockStateVariant.create()
            .put(VariantSettings.MODEL, ModelIds.getBlockModelId(torch)))
          .apply {
              BlockStateModelGenerator.createEastDefaultHorizontalRotationStates()
              Direction.Type.HORIZONTAL.forEach {
                  register(it, BlockStateVariant.create()
                    .put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(torch, "_wall"))
                    .put(VariantSettings.Y, VariantSettings.Rotation.entries[(it.horizontal + 3) % 4]))
              }
          }))
    }
    private fun BlockStateModelGenerator.addTorchItem(torch: Block) {
        excludeFromSimpleItemModelGeneration(torch)
        Models.GENERATED.upload(ModelIds.getItemModelId(torch.asItem()), TextureMap.layer0(torch), modelCollector)
    }
}