package io.github.yky.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import io.github.yky.sharedBackpackKt.Utils
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.FurnaceScreenHandler
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object FurnaceCommand {
    fun register(
        dispatcher: CommandDispatcher<ServerCommandSource>
    ) {
//        @Suppress("SpellCheckingInspection") val literalCommandNode = dispatcher.register(
//            CommandManager.literal("sharedbackpack")
//                .then(CommandManager.argument(ARGUMENT_NAME, StringArgumentType.word()).executes {
//                    executeBackpack(it.source, StringArgumentType.getString(it, ARGUMENT_NAME))
//                })
//        )

        val literalCommandNode = dispatcher.register(CommandManager.literal("furnace").executes {
            executeBackpack(it.source)
        })

//        dispatcher.register(CommandManager.literal("sbp").redirect(literalCommandNode))
    }

    private fun executeBackpack(source: ServerCommandSource): Int {
        val player = source.player
        // Send an error message if the command was called by a non-player
        if (player == null) {
            source.sendError(Text.literal("Only players can use this command"))
            return 0
        }

        player.openHandledScreen(
            SimpleNamedScreenHandlerFactory(
                { syncId: Int, playerInventory: PlayerInventory?, _: PlayerEntity? ->
                    FurnaceScreenHandler(syncId, playerInventory, Utils.furnaceInventory, ArrayPropertyDelegate(4))
                }, Text.literal("Test")
            )
        )
        return 1
    }
}