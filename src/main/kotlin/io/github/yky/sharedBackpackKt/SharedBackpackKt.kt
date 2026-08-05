package io.github.yky.sharedBackpackKt

import io.github.yky.sharedBackpackKt.Utils.Logger
import io.github.yky.sharedBackpackKt.command.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.MinecraftServer

class SharedBackpackKt : ModInitializer {
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { commandDispatcher, _, _ ->
            BackpackCommand.register(commandDispatcher)
            TrashCommand.register(commandDispatcher)
            BackpackPlayerOnlyCommand.register(commandDispatcher)
            FurnaceCommand.register(commandDispatcher)
            FurnacePlayerOnlyCommand.register(commandDispatcher)
        }

        ServerLifecycleEvents.SERVER_STARTED.register { server -> onServerStarted(server) }

        Logger.info("Shared Backpack Kotlin version loaded")
    }

    private fun onServerStarted(server: MinecraftServer) {
        Utils.Server = server
    }
}
