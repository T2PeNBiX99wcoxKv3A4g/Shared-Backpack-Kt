package io.github.ykysnk.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.ykysnk.sharedBackpackKt.FallbackTranslations
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import io.github.ykysnk.sharedBackpackKt.inventory.ContainerManager
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.ChestMenu

object BackpackPlayerOnlyCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        @Suppress("SpellCheckingInspection") val literalCommandNode = dispatcher.register(
            Commands.literal("privatebackpack")
                .then(Commands.argument(ARGUMENT_NAME, StringArgumentType.word()).executes {
                    executeBackpack(it.source, StringArgumentType.getString(it, ARGUMENT_NAME))
                })
        )

        dispatcher.register(Commands.literal("pbp").redirect(literalCommandNode))
    }

    private fun executeBackpack(source: CommandSourceStack, name: String): Int {
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

        if (!ConfigManager.config.general.privateBackpackEnabled) {
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.shared-backpack-kt.private-backpack.failure.disabled",
                    FallbackTranslations.COMMAND_SHARED_BACKPACK_KT_PRIVATE_BACKPACK_FAILURE_DISABLED
                )
            )
            return 0
        }

        player.openMenu(
            SimpleMenuProvider(
                { syncId, inventory, player2 ->
                    if (player2 == null) return@SimpleMenuProvider null
                    ChestMenu.sixRows(
                        syncId, inventory, ContainerManager.getOrCreateBackpackPlayerOnlyContainer(player2, name)
                    )
                }, Component.translatableWithFallback(
                    "command.shared-backpack-kt.private-backpack.title",
                    FallbackTranslations.COMMAND_SHARED_BACKPACK_KT_PRIVATE_BACKPACK_TITLE,
                    name
                )
            )
        )
        return 1
    }
}