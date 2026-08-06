package io.github.yky.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.yky.sharedBackpackKt.Utils
import io.github.yky.sharedBackpackKt.inventory.FurnaceInventoryType
import io.github.yky.sharedBackpackKt.inventory.FurnaceInventoryType.*
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.FurnaceMenu

object FurnaceCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        @Suppress("SpellCheckingInspection") val builder = Commands.literal("sharedfurnace")

        FurnaceInventoryType.entries.forEach { type ->
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
            source.sendFailure(Component.literal("Only players can use this command"))
            return 0
        }

        player.openMenu(
            SimpleMenuProvider(
                { syncId, inventory, player2 ->
                    if (player2 == null) return@SimpleMenuProvider null
                    val furnaceInventory = when (type) {
                        Smelting -> Utils.getOrCreateNormalFurnaceContainer(player2, name)
                        Blasting -> Utils.getOrCreateBlastFurnaceContainer(player2, name)
                        Smoking -> Utils.getOrCreateSmokerFurnaceContainer(player2, name)
                    }

                    FurnaceMenu(syncId, inventory, furnaceInventory, furnaceInventory.dataAccess)
                }, when (type) {
                    Smelting -> Component.literal("Shared Furnace: $name")
                    Blasting -> Component.literal("Shared Blast Furnace: $name")
                    Smoking -> Component.literal("Shared Smoker: $name")
                }
            )
        )
        return 1
    }
}