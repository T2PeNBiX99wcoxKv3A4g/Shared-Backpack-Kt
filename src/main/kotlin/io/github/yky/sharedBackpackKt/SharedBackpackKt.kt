package io.github.yky.sharedBackpackKt

import com.mojang.brigadier.CommandDispatcher
import io.github.yky.sharedBackpackKt.Utils.Logger
import io.github.yky.sharedBackpackKt.command.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

class SharedBackpackKt : ModInitializer {
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<ServerCommandSource>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            BackpackCommand.register(commandDispatcher)
            TrashCommand.register(commandDispatcher)
            BackpackPlayerOnlyCommand.register(commandDispatcher)
            FurnaceCommand.register(commandDispatcher)
            FurnacePlayerOnlyCommand.register(commandDispatcher)
        }

        ServerLifecycleEvents.SERVER_STARTED.register(ServerStarted { server: MinecraftServer ->
            onServerStarted(
                server
            )
        })

        Logger.info("Shared Backpack Kotlin version loaded")
    }

    private fun onServerStarted(server: MinecraftServer) {
        Utils.Server = server
    }
}
