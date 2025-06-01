package io.github.yky.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.yky.sharedBackpackKt.Utils
import io.github.yky.sharedBackpackKt.inventory.FurnaceInventoryType
import io.github.yky.sharedBackpackKt.inventory.FurnaceInventoryType.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.FurnaceScreenHandler
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object FurnaceCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<ServerCommandSource>
    ) {
        @Suppress("SpellCheckingInspection") val builder = CommandManager.literal("sharedfurnace")

        FurnaceInventoryType.entries.forEach { type ->
            builder.then(
                CommandManager.literal(type.name.lowercase()).then(
                    CommandManager.argument(ARGUMENT_NAME, StringArgumentType.word()).executes {
                        executeFurnace(it.source, type, StringArgumentType.getString(it, ARGUMENT_NAME))
                    })
            )
        }

        val literalCommandNode = dispatcher.register(builder)

        dispatcher.register(CommandManager.literal("sf").redirect(literalCommandNode))
    }

    private fun executeFurnace(source: ServerCommandSource, type: FurnaceInventoryType, name: String): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendError(Text.literal("Only players can use this command"))
            return 0
        }

        player.openHandledScreen(
            SimpleNamedScreenHandlerFactory(
                { syncId: Int, playerInventory: PlayerInventory?, player2: PlayerEntity? ->
                    if (player2 == null) return@SimpleNamedScreenHandlerFactory null
                    val furnaceInventory = when (type) {
                        Normal -> Utils.getOrCreateNormalFurnaceInventory(player2, name)
                        Blast -> Utils.getOrCreateBlastFurnaceInventory(player2, name)
                        Smoker -> Utils.getOrCreateSmokerFurnaceInventory(player2, name)
                    }

                    FurnaceScreenHandler(syncId, playerInventory, furnaceInventory, furnaceInventory.propertyDelegate)
                }, when (type) {
                    Normal -> Text.literal("Shared Furnace: $name")
                    Blast -> Text.literal("Shared Blast Furnace: $name")
                    Smoker -> Text.literal("Shared Smoker: $name")
                }
            )
        )
        return 1
    }
}