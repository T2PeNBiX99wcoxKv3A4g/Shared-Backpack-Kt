package io.github.ykysnk.sharedBackpackKt

import io.github.ykysnk.sharedBackpackKt.Utils.Logger
import io.github.ykysnk.sharedBackpackKt.command.*
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import io.github.ykysnk.sharedBackpackKt.inventory.ContainerManager
import io.github.ykysnk.sharedBackpackKt.inventory.FurnaceTickHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object SharedBackpackKt : ModInitializer {
    override fun onInitialize() {
        ConfigManager.load()

        val utils = Utils
        val containerManager = ContainerManager
        val furnaceTickHandler = FurnaceTickHandler

        CommandRegistrationCallback.EVENT.register { commandDispatcher, _, _ ->
            BackpackCommand.register(commandDispatcher)
            TrashCommand.register(commandDispatcher)
            BackpackPlayerOnlyCommand.register(commandDispatcher)
            FurnaceCommand.register(commandDispatcher)
            FurnacePlayerOnlyCommand.register(commandDispatcher)
        }

        Logger.debug("Initialized: {} {} {}", utils, containerManager, furnaceTickHandler)
        Logger.info("Shared Backpack Kotlin version loaded")
    }
}
