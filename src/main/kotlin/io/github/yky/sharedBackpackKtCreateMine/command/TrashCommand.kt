package io.github.yky.sharedBackpackKtCreateMine.command

import com.mojang.brigadier.CommandDispatcher
import io.github.yky.sharedBackpackKtCreateMine.Utils
import io.github.yky.sharedBackpackKtCreateMine.argument.TrashType
import io.github.yky.sharedBackpackKtCreateMine.argument.TrashType.CLEAR
import io.github.yky.sharedBackpackKtCreateMine.argument.TrashType.OPEN
import io.github.yky.sharedBackpackKtCreateMine.argument.TrashTypeArgument
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
                CommandManager.argument(TrashTypeArgument.ID, TrashTypeArgument()).executes {
                    executeTrash(it.source, TrashTypeArgument.getTrashType(it))
                }
            ))
    }

    private fun executeTrash(source: ServerCommandSource, trashType: TrashType): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendError(Text.literal("Only players can use this command"))
            return 0
        }

        when (trashType) {
            OPEN -> {
                player.openHandledScreen(
                    SimpleNamedScreenHandlerFactory(
                        { syncId: Int, playerInventory: PlayerInventory?, _: PlayerEntity? ->
                            GenericContainerScreenHandler.createGeneric9x6(
                                syncId,
                                playerInventory,
                                Utils.getOrCreateTrashInventory(player)
                            )
                        },
                        Utils.TrashInventoryTextCache
                    )
                )
                return 1
            }

            CLEAR -> {
                Utils.getOrCreateTrashInventory(player).clear()
                return 1
            }
        }
    }
}