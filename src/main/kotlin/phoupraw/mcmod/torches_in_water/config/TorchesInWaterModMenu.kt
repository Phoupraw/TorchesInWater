package phoupraw.mcmod.torches_in_water.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import phoupraw.mcmod.torches_in_water.CONFIG

@Suppress("unused")
object TorchesInWaterModMenu : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory {
            CONFIG.load()
            CONFIG.generateGui().generateScreen(it)
        }
    }
}