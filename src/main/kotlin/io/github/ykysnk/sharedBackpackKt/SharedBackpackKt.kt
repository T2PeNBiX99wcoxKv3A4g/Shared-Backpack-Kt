package io.github.ykysnk.sharedBackpackKt

import io.github.ykysnk.sharedBackpackKt.Constants.ForceInitialize
import io.github.ykysnk.sharedBackpackKt.command.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object SharedBackpackKt : ModInitializer {
    override fun onInitialize() {
        ForceInitialize

        CommandRegistrationCallback.EVENT.register { commandDispatcher, _, _ ->
            BackpackCommand.register(commandDispatcher)
            TrashCommand.register(commandDispatcher)
            BackpackPlayerOnlyCommand.register(commandDispatcher)
            FurnaceCommand.register(commandDispatcher)
            FurnacePlayerOnlyCommand.register(commandDispatcher)
            UnlimitedFurnaceCommand.register(commandDispatcher)
            UnlimitedFurnacePlayerOnlyCommand.register(commandDispatcher)
            ConfigCommand.register(commandDispatcher)
        }
    }
}
