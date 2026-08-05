package io.github.yky.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.yky.sharedBackpackKt.Utils
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.ChestMenu

object BackpackCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        @Suppress("SpellCheckingInspection") val literalCommandNode = dispatcher.register(
            Commands.literal("sharedbackpack")
                .then(Commands.argument(ARGUMENT_NAME, StringArgumentType.word()).executes {
                    executeBackpack(it.source, StringArgumentType.getString(it, ARGUMENT_NAME))
                })
        )

        dispatcher.register(Commands.literal("sbp").redirect(literalCommandNode))
    }

    private fun executeBackpack(source: CommandSourceStack, name: String): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendFailure(Component.literal("Only players can use this command"))
            return 0
        }

        player.openMenu(
            SimpleMenuProvider(
                { syncId, inventory, _ ->
                    ChestMenu.sixRows(syncId, inventory, Utils.getOrCreateBackpackContainer(name))
                }, Component.literal("Shared Backpack: $name")
            )
        )
        return 1
    }
}