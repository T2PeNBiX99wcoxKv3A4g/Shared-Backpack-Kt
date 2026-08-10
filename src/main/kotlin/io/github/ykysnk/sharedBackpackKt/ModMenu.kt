package io.github.ykysnk.sharedBackpackKt

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import io.github.ykysnk.sharedBackpackKt.config.ConfigFallbackScreen
import io.github.ykysnk.sharedBackpackKt.config.ConfigMenu

object ModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory { parent ->
        runCatching { ConfigMenu.create().generateScreen(parent) }.getOrElse { ConfigFallbackScreen(parent, it) }
    }
}