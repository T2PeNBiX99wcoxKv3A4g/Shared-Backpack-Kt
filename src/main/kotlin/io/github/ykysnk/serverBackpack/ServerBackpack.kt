package io.github.ykysnk.serverBackpack

import io.github.ykysnk.serverBackpack.Constants.ForceInitialize
import net.fabricmc.api.ModInitializer

object ServerBackpack : ModInitializer {
    override fun onInitialize() {
        ForceInitialize
    }
}
