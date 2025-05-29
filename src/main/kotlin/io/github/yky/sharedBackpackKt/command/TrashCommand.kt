package io.github.yky.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.yky.sharedBackpackKt.Utils
import io.github.yky.sharedBackpackKt.argument.TrashType
import io.github.yky.sharedBackpackKt.argument.TrashType.Clear
import io.github.yky.sharedBackpackKt.argument.TrashType.Open
import io.github.yky.sharedBackpackKt.argument.TrashTypeArgument
import io.github.yky.sharedBackpackKt.suggestion.TrashTypeSuggestion
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object TrashCommand {
    fun register(
        dispatcher: CommandDispatcher<ServerCommandSource>
    ) {
        dispatcher.register(
            CommandManager.literal("trash").then(
                CommandManager.argument(TrashTypeArgument.ID, StringArgumentType.word()).suggests(TrashTypeSuggestion())
                    .executes {
                        executeTrash(it.source, StringArgumentType.getString(it, TrashTypeArgument.ID))
                    })
        )
    }

    private fun executeTrash(source: ServerCommandSource, inputString: String): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendError(Text.literal("Only players can use this command"))
            return 0
        }

        val trashType = runCatching {
            var input = inputString.lowercase()
            input = input.substring(0, 1).uppercase() + input.substring(1)
            TrashType.valueOf(input)
        }.getOrElse {
            source.sendError(Text.literal("Invalid trash type"))
            return 0
        }

        when (trashType) {
            Open -> {
                player.openHandledScreen(
                    SimpleNamedScreenHandlerFactory(
                        { syncId: Int, playerInventory: PlayerInventory?, _: PlayerEntity? ->
                            GenericContainerScreenHandler.createGeneric9x6(
                                syncId, playerInventory, Utils.getOrCreateTrashInventory(player)
                            )
                        }, Utils.TrashInventoryTextCache
                    )
                )
                return 1
            }

            Clear -> {
                Utils.getOrCreateTrashInventory(player).clear()
                return 1
            }
        }
    }
}