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

object FurnaceCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        @Suppress("SpellCheckingInspection") val builder = Commands.literal("sharedfurnace")

        entries.forEach { type ->
            builder.then(
                Commands.literal(type.name.lowercase()).then(
                    Commands.argument(ARGUMENT_NAME, StringArgumentType.word()).executes {
                        executeFurnace(it.source, type, StringArgumentType.getString(it, ARGUMENT_NAME))
                    })
            )
        }

        val literalCommandNode = dispatcher.register(builder)

        dispatcher.register(Commands.literal("sf").redirect(literalCommandNode))
    }

    private fun executeFurnace(source: CommandSourceStack, type: FurnaceInventoryType, name: String): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.shared-backpack-kt.failure.only-player",
                    Utils.FALLBACK_COMMAND_FAILED_PLAYER_ONLY
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
                            Utils.FALLBACK_SHARED_SMELTING_FURNACE_FAILURE_DISABLED
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
                            Utils.FALLBACK_SHARED_BLASTING_FURNACE_FAILURE_DISABLED
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
                            Utils.FALLBACK_SHARED_SMOKING_FURNACE_FAILURE_DISABLED
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
                        Smelting -> ContainerManager.getOrCreateNormalFurnaceContainer(player2, name)
                        Blasting -> ContainerManager.getOrCreateBlastFurnaceContainer(player2, name)
                        Smoking -> ContainerManager.getOrCreateSmokerFurnaceContainer(player2, name)
                    }

                    CustomFurnaceMenu(syncId, inventory, furnaceInventory, furnaceInventory.propertyDelegate)
                }, when (type) {
                    Smelting -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.shared-smelting-furnace.title",
                        Utils.FALLBACK_SHARED_SMELTING_FURNACE_TITLE,
                        name
                    )

                    Blasting -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.shared-blasting-furnace.title",
                        Utils.FALLBACK_SHARED_BLASTING_FURNACE_TITLE,
                        name
                    )

                    Smoking -> Component.translatableWithFallback(
                        "command.shared-backpack-kt.shared-smoking-furnace.title",
                        Utils.FALLBACK_SHARED_SMOKING_FURNACE_TITLE,
                        name
                    )
                }
            )
        )
        return 1
    }
}