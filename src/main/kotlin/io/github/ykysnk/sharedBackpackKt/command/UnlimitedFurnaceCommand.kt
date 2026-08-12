package io.github.ykysnk.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.ykysnk.sharedBackpackKt.Utils
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import io.github.ykysnk.sharedBackpackKt.inventory.ContainerManager
import io.github.ykysnk.sharedBackpackKt.inventory.CustomFurnaceMenu
import io.github.ykysnk.sharedBackpackKt.inventory.FurnaceInventoryType
import io.github.ykysnk.sharedBackpackKt.inventory.FurnaceInventoryType.*
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider

object UnlimitedFurnaceCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        @Suppress("SpellCheckingInspection") val builder =
            Commands.literal("sharedunlimitedfurnace").requires { it.hasPermission(2) }

        entries.forEach { type ->
            builder.then(
                Commands.literal(type.name.lowercase()).then(
                    Commands.argument(ARGUMENT_NAME, StringArgumentType.word()).executes {
                        executeFurnace(it.source, type, StringArgumentType.getString(it, ARGUMENT_NAME))
                    })
            )
        }

        val literalCommandNode = dispatcher.register(builder)

        dispatcher.register(Commands.literal("suf").redirect(literalCommandNode))
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
                if (!ConfigManager.config.general.sharedSmeltingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.shared-backpack-kt.shared-smelting-furnace.failure.disabled",
                            "Shared Smelting Furnace is disabled in the config."
                        )
                    )
                    return 0
                }
            }

            Blasting -> {
                if (!ConfigManager.config.general.sharedBlastingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.shared-backpack-kt.shared-blasting-furnace.failure.disabled",
                            "Shared Blasting Furnace is disabled in the config."
                        )
                    )
                    return 0
                }
            }

            Smoking -> {
                if (!ConfigManager.config.general.sharedSmokingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.shared-backpack-kt.shared-smoking-furnace.failure.disabled",
                            "Shared Smoking Furnace is disabled in the config."
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
                        Smelting -> ContainerManager.getOrCreateUnlimitedNormalFurnaceContainer(player2, name)
                        Blasting -> ContainerManager.getOrCreateUnlimitedBlastFurnaceContainer(player2, name)
                        Smoking -> ContainerManager.getOrCreateUnlimitedSmokerFurnaceContainer(player2, name)
                    }

                    CustomFurnaceMenu(syncId, inventory, furnaceInventory, furnaceInventory.dataAccess)
                }, when (type) {
                    Smelting -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.shared-smelting-furnace.title",
                        "Shared Furnace: %s",
                        name
                    )

                    Blasting -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.shared-blasting-furnace.title",
                        "Shared Blast Furnace: %s",
                        name
                    )

                    Smoking -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.shared-smoking-furnace.title",
                        "Shared Smoker Furnace: %s",
                        name
                    )
                }
            )
        )
        return 1
    }
}