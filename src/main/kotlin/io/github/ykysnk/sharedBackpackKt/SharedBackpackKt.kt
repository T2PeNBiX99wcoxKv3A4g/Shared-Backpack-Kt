package io.github.ykysnk.sharedBackpackKt

import io.github.ykysnk.sharedBackpackKt.Constants.ForceInitialize
import net.fabricmc.api.ModInitializer

object SharedBackpackKt : ModInitializer {
    override fun onInitialize() {
        ForceInitialize
    }
}
