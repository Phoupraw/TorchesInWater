package phoupraw.mcmod.torches_in_water.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightBlock;
import net.minecraft.block.MapColor;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import phoupraw.mcmod.torches_in_water.TorchesInWater;
import phoupraw.mcmod.torches_in_water.constant.TiWIDs;
import phoupraw.mcmod.torches_in_water.constant.TiWItems;

public final class TiWModMenuApi implements ModMenuApi {
    public static final Text RESTART = warning(Text.translatable(TiWConfig.RESTART_KEY));
    public static MutableText warning(Text text) {
        return Text.empty()
          .append(Text.literal("▲").formatted(Formatting.YELLOW))
          .append(text)
          .append(Text.literal("▲").formatted(Formatting.YELLOW));
    }
    
    public static MutableText error(Text text) {
        return Text.empty()
          .append(Text.literal("⚪").setStyle(Style.EMPTY.withColor(MapColor.ORANGE.color)))
          .append(text)
          .append(Text.literal("⚪").setStyle(Style.EMPTY.withColor(MapColor.ORANGE.color)));
    }
    public static MutableText fatal(Text text) {
        return Text.empty()
          .append(Text.literal("! ").formatted(Formatting.RED))
          .append(text)
          .append(Text.literal(" !").formatted(Formatting.RED));
    }
    private static YetAnotherConfigLib.Builder createScreen(TiWConfig defaults, TiWConfig config, YetAnotherConfigLib.Builder builder) {
        return builder
          .title(Text.translatable(TorchesInWater.NAME_KEY))
          .category(ConfigCategory
            .createBuilder()
            .name(Text.translatable(TorchesInWater.NAME_KEY))
            .group(OptionGroup
              .createBuilder()
              .name(TiWItems.GLOW_INK_TORCH.getName())
              .description(OptionDescription
                .createBuilder()
                .image(TiWIDs.of("textures/gallery/glow_ink_torches_placement.png"), 1920, 1017)
                .text(Text.translatable(TiWConfig.ITEM_DESC))
                .build())
              .option(Option
                .<Boolean>createBuilder()
                .name(Blocks.LAVA.getName())
                .description(OptionDescription
                  .createBuilder()
                  .customImage(FluidRendererInConfig.of(FluidVariant.of(Fluids.LAVA)))
                  .text(Text.translatable("config." + TorchesInWater.ID + ".lavaDestroy.desc"))
                  .build())
                .binding(defaults.lavaDestroy, config::isLavaDestroy, config::setLavaDestroy)
                .controller(TickBoxControllerBuilder::create)
                .build())
              .option(Option
                .<Integer>createBuilder()
                .name(Text.translatable("config." + TorchesInWater.ID + ".glowInkTorch_luminance.desc"))
                .description(OptionDescription
                  .createBuilder()
                  .customImage(ItemRendererInConfig.of(() -> LightBlock.addNbtForLevel(Items.LIGHT.getDefaultStack(), config.glowInkTorch_luminance)))
                  .text(
                    RESTART,
                    error(Text.translatable("config." + TorchesInWater.ID + ".glowInkTorch_luminance.error")))
                  .build())
                .binding(defaults.glowInkTorch_luminance, config::getGlowInkTorch_luminance, config::setGlowInkTorch_luminance)
                .controller(option -> IntegerSliderControllerBuilder
                  .create(option)
                  .range(0, 15)
                  .step(1))
                .flag(OptionFlag.GAME_RESTART)
                .build())
              .build())
            .build());
    }
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.create(TiWConfig.HANDLER, TiWModMenuApi::createScreen).generateScreen(parent);
    }
}
