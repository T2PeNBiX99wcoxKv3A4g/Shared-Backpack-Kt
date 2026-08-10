package io.github.ykysnk.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.ykysnk.sharedBackpackKt.Utils
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import io.github.ykysnk.sharedBackpackKt.inventory.FurnaceInventoryType
import io.github.ykysnk.sharedBackpackKt.inventory.FurnaceInventoryType.*
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.FurnaceMenu

object FurnacePlayerOnlyCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        @Suppress("SpellCheckingInspection") val builder = Commands.literal("privatefurnace")

        entries.forEach { type ->
            builder.then(
                Commands.literal(type.name.lowercase()).then(
                    Commands.argument(ARGUMENT_NAME, StringArgumentType.word()).executes {
                        executeFurnace(it.source, type, StringArgumentType.getString(it, ARGUMENT_NAME))
                    })
            )
        }

        val literalCommandNode = dispatcher.register(builder)

        dispatcher.register(Commands.literal("pf").redirect(literalCommandNode))
    }

    private fun executeFurnace(source: CommandSourceStack, type: FurnaceInventoryType, name: String): Int {
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

        when (type) {
            Smelting -> {
                if (!ConfigManager.config.general.privateSmeltingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.shared-backpack-kt.private-smelting-furnace.failure.disabled",
                            "Private Smelting Furnace is disabled in the config."
                        )
                    )
                    return 0
                }
            }

            Blasting -> {
                if (!ConfigManager.config.general.privateBlastingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.shared-backpack-kt.private-blasting-furnace.failure.disabled",
                            "Private Blasting Furnace is disabled in the config."
                        )
                    )
                    return 0
                }
            }

            Smoking -> {
                if (!ConfigManager.config.general.privateSmokingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.shared-backpack-kt.private-smoking-furnace.failure.disabled",
                            "Private Smoking Furnace is disabled in the config."
                        )
                    )
                    return 0
                }
            }
        }

        player.openMenu(
            SimpleMenuProvider(
                { syncId, inventory, player2 ->
                    if (player2 == null) return@SimpleMenuProvider null
                    val furnaceInventory = when (type) {
                        Smelting -> Utils.getOrCreateNormalFurnacePlayerOnlyContainer(player2, name)
                        Blasting -> Utils.getOrCreateBlastFurnacePlayerOnlyContainer(player2, name)
                        Smoking -> Utils.getOrCreateSmokerFurnacePlayerOnlyContainer(player2, name)
                    }

                    FurnaceMenu(syncId, inventory, furnaceInventory, furnaceInventory.dataAccess)
                }, when (type) {
                    Smelting -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.private-smelting-furnace.title",
                        "Private Furnace: %s",
                        name
                    )

                    Blasting -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.private-blasting-furnace.title",
                        "Private Blast Furnace: %s",
                        name
                    )

                    Smoking -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.private-smoking-furnace.title",
                        "Private Smoker Furnace: %s",
                        name
                    )
                }
            )
        )
        return 1
    }
}