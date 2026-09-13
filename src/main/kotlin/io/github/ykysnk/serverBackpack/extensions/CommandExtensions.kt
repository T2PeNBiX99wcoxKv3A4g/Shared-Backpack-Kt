package io.github.ykysnk.serverBackpack.extensions

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.ykysnk.serverBackpack.Constants
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

inline fun ArgumentBuilder<CommandSourceStack, *>.literal(
    name: String,
    block: LiteralArgumentBuilder<CommandSourceStack>.() -> Unit
): ArgumentBuilder<CommandSourceStack, *> = then(Commands.literal(name).apply(block))

inline fun <T> ArgumentBuilder<CommandSourceStack, *>.argument(
    name: String,
    type: ArgumentType<T>,
    block: RequiredArgumentBuilder<CommandSourceStack, T>.() -> Unit
): ArgumentBuilder<CommandSourceStack, *> = then(Commands.argument(name, type).apply(block))

inline fun <T> ArgumentBuilder<T, *>.executesLogError(crossinline command: (CommandContext<T>) -> Int): ArgumentBuilder<T, *> =
    executes { context ->
        runCatching { command(context) }.onFailure {
            Constants.LOGGER.error("Failed to execute command: {}", context.input, it)
        }.getOrThrow()
    }