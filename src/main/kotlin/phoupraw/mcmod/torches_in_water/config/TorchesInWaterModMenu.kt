@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package phoupraw.mcmod.torches_in_water.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.block.Blocks
import net.minecraft.block.LightBlock
import net.minecraft.block.MapColor
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import phoupraw.mcmod.torches_in_water.CONFIG
import phoupraw.mcmod.torches_in_water.ID
import phoupraw.mcmod.torches_in_water.TorchesInWater
import phoupraw.mcmod.torches_in_water.constant.TiWItems
import phoupraw.mcmod.torches_in_water.desc
import java.util.*
import java.util.concurrent.CompletableFuture

@Suppress("unused")
object TorchesInWaterModMenu : ModMenuApi {
    val RESTART = warning(Text.translatable(TiWConfig.RESTART_KEY))
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory { screen ->
        CONFIG.load()
        YetAnotherConfigLib.create(TiWConfig.HANDLER) { defaults, config, builder ->
            builder
              .title(Text.translatable(TorchesInWater.NAME_KEY))
              .category(ConfigCategory.createBuilder()
                .name(Text.translatable(TorchesInWater.NAME_KEY))
                .group(OptionGroup.createBuilder()
                  .name(TiWItems.GLOW_INK_TORCH.name)
                  .description(OptionDescription.createBuilder()
                    .customImage(CompletableFuture.completedFuture(Optional.of(ItemRendererInConfig { TiWItems.GLOW_INK_TORCH.defaultStack })))
                    .text(TiWItems.GLOW_INK_TORCH.desc)
                    .build())
                  .option(Option.createBuilder<Boolean>()
                    .name(Blocks.LAVA.name)
                    .description(OptionDescription.createBuilder()
                      .customImage(CompletableFuture.completedFuture(Optional.of(RendererInConfig { graphics, x, y, renderWidth, tickDelta ->
                          graphics.drawSprite(x, y, 0, 16, 16, FluidVariantRendering.getSprite(FluidVariant.of(Fluids.LAVA)))
                          16
                      })))
                      .text(Text.translatableWithFallback("config.$ID.${TiWConfig::lavaDestroy.name}", "可以被岩浆冲毁"))
                      .build())
                    .binding(defaults.lavaDestroy, config::lavaDestroy, config::lavaDestroy.setter)
                    .controller(TickBoxControllerBuilder::create)
                    .build())
                  .option(Option.createBuilder<Int>()
                    .name(Text.translatableWithFallback("config.$ID.${TiWConfig::glowInkTorch_luminance.name}", "亮度等级"))
                    .description(OptionDescription.createBuilder()
                      .customImage(CompletableFuture.completedFuture(Optional.of(ItemRendererInConfig {
                          LightBlock.addNbtForLevel(Items.LIGHT.defaultStack, config.glowInkTorch_luminance)
                      })))
                      .text(
                          RESTART,
                          error(Text.translatableWithFallback("config.$ID.${TiWConfig::glowInkTorch_luminance.name}.error", "已放置的火把不会自动更新亮度，需要破坏并重新放置")))
                      .build())
                    .binding(defaults.glowInkTorch_luminance, config::glowInkTorch_luminance, config::glowInkTorch_luminance.setter)
                    .controller {
                        IntegerSliderControllerBuilder.create(it)
                          .range(0, 15)
                          .step(1)
                    }
                    .build())
                  .build())
                .build())
        }.generateScreen(screen)
    }

    fun fatal(text: Text): MutableText = Text.empty()
      .append(Text.literal("! ").formatted(Formatting.RED))
      .append(text)
      .append(Text.literal(" !").formatted(Formatting.RED))

    fun error(text: Text): MutableText = Text.empty()
      .append(Text.literal("🔴").setStyle(Style.EMPTY.withColor(MapColor.ORANGE.color)))
      .append(text)
      .append(Text.literal("🔴").setStyle(Style.EMPTY.withColor(MapColor.ORANGE.color)))

    fun warning(text: Text): MutableText = Text.empty()
      .append(Text.literal("▲").formatted(Formatting.YELLOW))
      .append(text)
      .append(Text.literal("▲").formatted(Formatting.YELLOW))
    //@Deprecated("", ReplaceWith("Binding.generic(property.get(config.defaults()), { property.get(config.instance()) }, { property.set(config.instance(), it) })", "dev.isxander.yacl3.api.Binding"))
    //private fun <T, V> bindingOf(config: ConfigClassHandler<T>, property: KMutableProperty1<T, V>): Binding<V> = Binding.generic(
    //    property.get(config.defaults()),
    //    { property.get(config.instance()) },
    //    { property.set(config.instance(), it) })
    //@Deprecated("")
    //private fun <V> bindingOf(property: KMutableProperty1<TiWConfig, V>) = bindingOf(CONFIG, property)
}