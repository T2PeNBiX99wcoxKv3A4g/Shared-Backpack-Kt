package io.github.ykysnk.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.*
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.ykysnk.sharedBackpackKt.config.Config
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.network.chat.Component
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties

object ConfigCommand {
    const val VALUE = "value"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        val root = Commands.literal("sharedbackpackconfig").requires { it.hasPermission(2) }

        for (property in ConfigManager.config.general::class.memberProperties) {
            val name = property.name

            @Suppress("UNCHECKED_CAST", "unused")
            val fuckCast = property as? KMutableProperty1<Config.General, Any?> ?: continue

            @Suppress("UNCHECKED_CAST")
            when (property.returnType.classifier) {
                Boolean::class -> handle(
                    root,
                    name,
                    property,
                    BoolArgumentType.bool() as ArgumentType<Any?>
                ) { BoolArgumentType.getBool(it, VALUE) }

                Int::class -> handle(
                    root,
                    name,
                    property,
                    IntegerArgumentType.integer() as ArgumentType<Any?>
                ) { IntegerArgumentType.getInteger(it, VALUE) }

                Long::class -> handle(
                    root,
                    name,
                    property,
                    LongArgumentType.longArg() as ArgumentType<Any?>
                ) { LongArgumentType.getLong(it, VALUE) }

                Float::class -> handle(
                    root,
                    name,
                    property,
                    FloatArgumentType.floatArg() as ArgumentType<Any?>
                ) { FloatArgumentType.getFloat(it, VALUE) }

                Double::class -> handle(
                    root,
                    name,
                    property,
                    DoubleArgumentType.doubleArg() as ArgumentType<Any?>
                ) { DoubleArgumentType.getDouble(it, VALUE) }

                String::class -> handle(
                    root,
                    name,
                    property,
                    StringArgumentType.string() as ArgumentType<Any?>
                ) { StringArgumentType.getString(it, VALUE) }
            }
        }

        val node = dispatcher.register(root)
        dispatcher.register(Commands.literal("sbpc").redirect(node))
    }

    inline fun <reified T> handle(
        root: LiteralArgumentBuilder<CommandSourceStack>,
        name: String,
        property: KMutableProperty1<Config.General, T>,
        argumentType: ArgumentType<T>,
        crossinline getter: (CommandContext<CommandSourceStack>) -> T
    ) {
        root.then(
            Commands.literal(name)
                .executes { context ->
                    val value = property.get(ConfigManager.config.general)

                    context.source.sendSuccess(
                        { Component.literal("$name = $value") },
                        false
                    )

                    1
                }
                .then(
                    argument("value", argumentType)
                        .executes { context ->
                            val value = getter(context)

                            property.set(
                                ConfigManager.config.general,
                                value
                            )

                            ConfigManager.save()

                            context.source.sendSuccess(
                                { Component.literal("$name = $value") },
                                false
                            )

                            1
                        }
                )
        )
    }
}