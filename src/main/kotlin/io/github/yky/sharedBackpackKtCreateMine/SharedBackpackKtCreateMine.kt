package io.github.yky.sharedBackpackKtCreateMine

import com.mojang.brigadier.CommandDispatcher
import io.github.yky.sharedBackpackKtCreateMine.Utils.LOGGER
import io.github.yky.sharedBackpackKtCreateMine.Utils.SERVER
import io.github.yky.sharedBackpackKtCreateMine.command.BackpackCommand
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

class SharedBackpackKtCreateMine : ModInitializer {
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<ServerCommandSource>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            BackpackCommand.register(commandDispatcher)
        }

        ServerLifecycleEvents.SERVER_STARTED.register(ServerStarted { server: MinecraftServer ->
            this.onServerStarted(
                server
            )
        })

        LOGGER.info("Shared Backpack Kotlin version for Create Mine mod loaded")
    }

    private fun onServerStarted(server: MinecraftServer) {
        SERVER = server
    }
}
