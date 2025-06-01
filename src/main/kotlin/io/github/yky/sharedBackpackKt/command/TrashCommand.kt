package io.github.yky.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import io.github.yky.sharedBackpackKt.Utils
import io.github.yky.sharedBackpackKt.argument.TrashType
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
        val builder = CommandManager.literal("trash")

        TrashType.entries.forEach { type ->
            builder.then(CommandManager.literal(type.name.lowercase()).executes {
                executeTrash(it.source, type)
            })
        }

        dispatcher.register(builder)
    }

    private fun executeTrash(source: ServerCommandSource, trashType: TrashType): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendError(Text.literal("Only players can use this command"))
            return 0
        }

        when (trashType) {
            TrashType.Open -> {
                player.openHandledScreen(
                    SimpleNamedScreenHandlerFactory(
                        { syncId: Int, playerInventory: PlayerInventory?, player2: PlayerEntity? ->
                            if (player2 == null) return@SimpleNamedScreenHandlerFactory null
                            GenericContainerScreenHandler.createGeneric9x6(
                                syncId, playerInventory, Utils.getOrCreateTrashInventory(player2)
                            )
                        }, Text.literal("Trash")
                    )
                )
                return 1
            }

            TrashType.Clear -> {
                Utils.getOrCreateTrashInventory(player).clear()
                return 1
            }
        }
    }
}