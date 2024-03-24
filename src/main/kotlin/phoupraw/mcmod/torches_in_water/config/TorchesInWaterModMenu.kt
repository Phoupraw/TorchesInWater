@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package phoupraw.mcmod.torches_in_water.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import net.minecraft.block.Blocks
import net.minecraft.item.Items
import net.minecraft.text.Text
import phoupraw.mcmod.torches_in_water.CONFIG
import phoupraw.mcmod.torches_in_water.TorchesInWater

@Suppress("unused")
object TorchesInWaterModMenu : ModMenuApi {
    val SCREEN: YetAnotherConfigLib = YetAnotherConfigLib.create(TorchesInWaterConfig.HANDLER) { defaults, config, builder ->
        builder
          .category(ConfigCategory.createBuilder()
            .name(Text.translatable(TorchesInWater.NAME_KEY))
            .group(OptionGroup.createBuilder()
              .name(Items.TORCH.name)
              .option(Option.createBuilder<Boolean>()
                .name(Blocks.LAVA.name)
                .binding(CONFIG.instance().lavaDestroy, CONFIG.instance()::lavaDestroy, CONFIG.instance()::lavaDestroy.setter)
                .controller(TickBoxControllerBuilder::create)
                .build())
              .build())
            .build())
    }

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory {
            CONFIG.load()
            SCREEN.generateScreen(it)
            //CONFIG.generateGui().generateScreen(it)
        }
    }
}