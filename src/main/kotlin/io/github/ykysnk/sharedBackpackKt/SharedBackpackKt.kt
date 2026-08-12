package io.github.ykysnk.sharedBackpackKt

import io.github.ykysnk.sharedBackpackKt.Utils.Logger
import io.github.ykysnk.sharedBackpackKt.command.*
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import io.github.ykysnk.sharedBackpackKt.inventory.ContainerManager
import io.github.ykysnk.sharedBackpackKt.inventory.TickHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object SharedBackpackKt : ModInitializer {
    override fun onInitialize() {
        ConfigManager.load()

        Logger.debug("Initialized: {} {} {}", Utils, ContainerManager, TickHandler)

        CommandRegistrationCallback.EVENT.register { commandDispatcher, _, _ ->
            BackpackCommand.register(commandDispatcher)
            TrashCommand.register(commandDispatcher)
            BackpackPlayerOnlyCommand.register(commandDispatcher)
            FurnaceCommand.register(commandDispatcher)
            FurnacePlayerOnlyCommand.register(commandDispatcher)
            UnlimitedFurnaceCommand.register(commandDispatcher)
            UnlimitedFurnacePlayerOnlyContainer.register(commandDispatcher)
        }


        Logger.info("Shared Backpack Kotlin version loaded")
    }
}
