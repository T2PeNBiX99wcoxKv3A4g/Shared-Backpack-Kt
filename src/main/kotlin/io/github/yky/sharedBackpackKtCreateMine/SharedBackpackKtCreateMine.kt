package io.github.yky.sharedBackpackKtCreateMine

import com.mojang.brigadier.CommandDispatcher
import io.github.yky.sharedBackpackKtCreateMine.Utils.Logger
import io.github.yky.sharedBackpackKtCreateMine.Utils.MOD_ID
import io.github.yky.sharedBackpackKtCreateMine.argument.TrashTypeArgument
import io.github.yky.sharedBackpackKtCreateMine.command.BackpackCommand
import io.github.yky.sharedBackpackKtCreateMine.command.BackpackPlayerOnlyCommand
import io.github.yky.sharedBackpackKtCreateMine.command.TrashCommand
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier

class SharedBackpackKtCreateMine : ModInitializer {
    override fun onInitialize() {
        ArgumentTypeRegistry.registerArgumentType(
            Identifier.of(MOD_ID, TrashTypeArgument.ID),
            TrashTypeArgument::class.java,
            ConstantArgumentSerializer.of(::TrashTypeArgument)
        )

        CommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<ServerCommandSource>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            BackpackCommand.register(commandDispatcher)
            TrashCommand.register(commandDispatcher)
            BackpackPlayerOnlyCommand.register(commandDispatcher)
        }

        ServerLifecycleEvents.SERVER_STARTED.register(ServerStarted { server: MinecraftServer ->
            onServerStarted(
                server
            )
        })

        Logger.info("Shared Backpack Kotlin version for Create Mine mod loaded")
    }

    private fun onServerStarted(server: MinecraftServer) {
        Utils.Server = server
    }
}
