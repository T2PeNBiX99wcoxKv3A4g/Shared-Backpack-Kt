package io.github.ykysnk.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import io.github.ykysnk.sharedBackpackKt.Utils
import io.github.ykysnk.sharedBackpackKt.argument.TrashType
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import io.github.ykysnk.sharedBackpackKt.inventory.ContainerManager
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
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.shared-backpack-kt.failure.only-player",
                    Utils.COMMAND_FAILED_PLAYER_ONLY
                )
            )
            return 0
        }

        if (!ConfigManager.config.general.trashEnabled) {
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.shared-backpack-kt.trash.failure.disabled",
                    "Trash Can is disabled in the config."
                )
            )
            return 0
        }

        when (trashType) {
            TrashType.Open -> {
                player.openMenu(
                    SimpleMenuProvider(
                        { syncId, inventory, player2 ->
                            if (player2 == null) return@SimpleMenuProvider null
                            ChestMenu.sixRows(
                                syncId, inventory, ContainerManager.getOrCreateTrashContainer(player2)
                            )
                        }, Component.translatableWithFallback(
                            "command.shared-backpack-kt.trash.title",
                            "Trash Can"
                        )
                    )
                )
                return 1
            }

            TrashType.Clear -> {
                ContainerManager.getOrCreateTrashContainer(player).clearContent()
                source.sendSuccess({
                    Component.translatableWithFallback(
                        "command.shared-backpack-kt.trash.success",
                        "Trash Can cleared."
                    )
                }, false)
                return 1
            }
        }
    }
}