package io.github.ykysnk.sharedBackpackKt.config

import me.fzzyhmstrs.fzzy_config.api.ConfigApi

object Configs {
    val mainConfig = ConfigApi.registerAndLoadConfig(::MainConfig)
}