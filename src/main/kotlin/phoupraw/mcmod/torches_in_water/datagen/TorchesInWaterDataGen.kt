package phoupraw.mcmod.torches_in_water.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.*
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryWrapper
import net.minecraft.state.property.Properties
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import phoupraw.mcmod.torches_in_water.ID
import phoupraw.mcmod.torches_in_water.TorchesInWater
import phoupraw.mcmod.torches_in_water.config.TiWConfig
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks
import phoupraw.mcmod.torches_in_water.constant.TiWIDs
import phoupraw.mcmod.torches_in_water.constant.TiWItems
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

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

        val override = g.createBuiltinResourcePack(TiWIDs.OVERRIDE)
        override.addProvider(::OverrideRecipeGen)
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
        //getOrCreateTagBuilder(TiWItemTags.DESC).add(TiWItems.GLOW_INK_TORCH)
    }
}

private class RecipeGen(output: FabricDataOutput) : FabricRecipeProvider(output) {
    override fun generate(exporter: Consumer<RecipeJsonProvider>) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, TiWItems.GLOW_INK_TORCH, 4)
          .criterion("SBMJ", RecipeProvider.conditionsFromItem(Items.GLOW_INK_SAC))
          .input('A', Items.GLOW_INK_SAC)
          .input('B', Items.STICK)
          .pattern("A")
          .pattern("B")
          .offerTo(exporter, TiWIDs.GLOW_INK_TORCH.withPrefixedPath("shaped_crafting/"))
    }
}

//private val CFG = "yacl3.config.${CONFIG.id()}"
private fun FabricLanguageProvider.TranslationBuilder.addWithDesc(item: Item, name: String, desc: String) {
    add(item, name)
    add(item.translationKey + ".desc", desc)
}

//private fun FabricLanguageProvider.TranslationBuilder.addYACLGroup(/*category:String,*/group: String, name: String, desc: String) {
//    add("$CFG.category.${TiWConfig.CATEGORY}.group.$group", name)
//    add("$CFG.category.${TiWConfig.CATEGORY}.group.$group.desc", desc)
//}
//
//private fun FabricLanguageProvider.TranslationBuilder.addYACLEntry(property: KProperty<*>, name: String, desc: String) {
//    val configName = "$CFG.${property.name}"
//    add(configName, name)
//    add("$configName.desc", desc)
//}
/**
 * @see Formatting
 */
private class ChineseGen(dataOutput: FabricDataOutput) : FabricLanguageProvider(dataOutput, "zh_cn"/*傻逼MCDEV不能禁用警告*/) {
    override fun generateTranslations(b: TranslationBuilder) {
        b.add(TorchesInWater.NAME_KEY, "水中火把")
        b.add("modmenu.descriptionTranslation.$ID", """
            把火把放在水中！
            §l概要§r
            - 添加了荧光墨汁火把，可以放在水中，由荧光墨囊和木棍合成。
            §l介绍§r
            添加了可以放置在水中的火把，包括水流。
            §l你知道吗§r
            本模组灵感来源于《Aquatic Torches》中的Aquatic Torch。
        """.trimIndent())
        val glowInkTorchName = "荧光墨汁火把"
        b.addWithDesc(TiWItems.GLOW_INK_TORCH, glowInkTorchName, "可以放在水源或水流中，以及下半砖或楼梯的侧面。")
        //
        b.add(TiWConfig.RESTART_KEY, "此选项修改后需要重启游戏才能生效")
        b.add("config.$ID.${TiWConfig::lavaDestroy.name}", "可以被岩浆冲毁")
        b.add("config.$ID.${TiWConfig::glowInkTorch_luminance.name}", "亮度等级")
        b.add("config.$ID.${TiWConfig::glowInkTorch_luminance.name}.error", "已放置的火把不会自动更新亮度，需要破坏并重新放置")
        //b.addYACLEntry(TiWConfig::lavaDestroy, "岩浆破坏", "岩浆可以冲毁$glowInkTorchName。")
        //b.addYACLEntry(TiWConfig::glowInkTorch_luminance, "亮度", """
        //    §c！此选项修改后需要重启游戏才能生效！§r
        //    方块的亮度等级。
        //    §e▲已放置的火把不会自动更新亮度，需要破坏并重新放置。▲§r
        //""".trimIndent())

        b.add(TiWIDs.OVERRIDE.toTranslationKey("dataPack"), "移除Aquatic Torch的配方。")
    }
}
/**
 * @see Formatting
 */
private class EnglishGen(dataOutput: FabricDataOutput) : FabricLanguageProvider(dataOutput) {
    override fun generateTranslations(b: TranslationBuilder) {
        b.add(TorchesInWater.NAME_KEY, "Torches in Water")
        b.add("modmenu.descriptionTranslation.$ID", """
            Added torches which can be placed in water.
            §lSynopsis§r
            Added Glow ink torch, which can be placed in water, crafted from glow ink sac and stick.
            §lProfile§r
            Added torches which can be placed in water, including flow.
            §lTrivia§r
            This mod is inspired by Aquatic Torch of §oAquatic Torches§r.
        """.trimIndent())
        val glowInkTorchName = "Glow Ink Torch"
        b.addWithDesc(TiWItems.GLOW_INK_TORCH,glowInkTorchName,"Can be placed in water source or flow. Can be placed on the side of bottom slab or stairs. Will be destroyed by lava (configurable).")
        //
        b.add(TiWConfig.RESTART_KEY, "After modifying this option, you need to restart the game to take effect")
        b.add("config.$ID.${TiWConfig::lavaDestroy.name}", "Lava can flow into and destory")
        b.add("config.$ID.${TiWConfig::glowInkTorch_luminance.name}", "Light level")
        b.add("config.$ID.${TiWConfig::glowInkTorch_luminance.name}.error", "Placed torches won't auto update light level. You need to break and re-place them")
        //b.addYACLEntry(TiWConfig::lavaDestroy, "Lava Destroy", "Lava can flow into and destory $glowInkTorchName.")
        //b.addYACLEntry(TiWConfig::glowInkTorch_luminance, "Luminous", """
        //    §c! After modifying this option, you need to restart the game to take effect!§r
        //    The light level of the block.
        //    §e▲Placed torches won't auto update light level. You need to break and re-place them.▲§r
        //""".trimIndent())

        b.add(TiWIDs.OVERRIDE.toTranslationKey("dataPack"), "Remove recipe of Aquatic Torch.")
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

private class OverrideRecipeGen(output: FabricDataOutput) : FabricRecipeProvider(output) {
    override fun generate(exporter: Consumer<RecipeJsonProvider>) {
        ComplexRecipeJsonBuilder.create(RecipeSerializer.REPAIR_ITEM).offerTo(exporter, "aquatictorches:aquatic_torch")
        //ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, Items.AIR)
        //  .criterion("SBMJ", RecipeProvider.conditionsFromItem(Items.REPEATING_COMMAND_BLOCK))
        //  .input(Ingredient.ofItems(Items.AIR))
        //  .offerTo(exporter, Identifier("aquatictorches", "aquatic_torch"))
        //ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, TiWItems.GLOW_INK_TORCH)
        //  .criterion("SBMJ", RecipeProvider.conditionsFromItem(Items.GLOW_INK_SAC))
        //  .input('A', Items.GLOW_INK_SAC)
        //  .input('B', Items.STICK)
        //  .pattern("A")
        //  .pattern("B")
        //  .offerTo(exporter, Identifier("aquatictorches","aquatic_torch"))
    }

    override fun getRecipeIdentifier(identifier: Identifier): Identifier {
        return identifier
    }
}