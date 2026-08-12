package io.github.ykysnk.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import io.github.ykysnk.sharedBackpackKt.FallbackTranslations
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
                    FallbackTranslations.COMMAND_SHARED_BACKPACK_KT_FAILURE_ONLY_PLAYER
                )
            )
            return 0
        }

        if (!ConfigManager.config.general.trashEnabled) {
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.shared-backpack-kt.trash.failure.disabled",
                    FallbackTranslations.COMMAND_SHARED_BACKPACK_KT_TRASH_FAILURE_DISABLED
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
                            FallbackTranslations.COMMAND_SHARED_BACKPACK_KT_TRASH_TITLE
                        )
                    )
                )
                return 1
            }

            TrashType.Clear -> {
                ContainerManager.clearContentOfTrashContainer(player)
                source.sendSuccess({
                    Component.translatableWithFallback(
                        "command.shared-backpack-kt.trash.success.emptied",
                        FallbackTranslations.COMMAND_SHARED_BACKPACK_KT_TRASH_SUCCESS_EMPTIED
                    )
                }, false)
                return 1
            }
        }
    }
}