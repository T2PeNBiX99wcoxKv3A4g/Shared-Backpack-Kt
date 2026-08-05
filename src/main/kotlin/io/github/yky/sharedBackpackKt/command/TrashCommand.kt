package io.github.yky.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import io.github.yky.sharedBackpackKt.Utils
import io.github.yky.sharedBackpackKt.argument.TrashType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.ChestMenu

object TrashCommand {
    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        val builder = Commands.literal("trash")

        TrashType.entries.forEach { type ->
            builder.then(Commands.literal(type.name.lowercase()).executes {
                executeTrash(it.source, type)
            })
        }

        dispatcher.register(builder)
    }

    private fun executeTrash(source: CommandSourceStack, trashType: TrashType): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendFailure(Component.literal("Only players can use this command"))
            return 0
        }

        when (trashType) {
            TrashType.Open -> {
                player.openMenu(
                    SimpleMenuProvider(
                        { syncId, inventory, player2 ->
                            if (player2 == null) return@SimpleMenuProvider null
                            ChestMenu.sixRows(
                                syncId, inventory, Utils.getOrCreateTrashContainer(player2)
                            )
                        }, Component.literal("Trash")
                    )
                )
                return 1
            }

            TrashType.Clear -> {
                Utils.getOrCreateTrashContainer(player).clearContent()
                return 1
            }
        }
    }
}