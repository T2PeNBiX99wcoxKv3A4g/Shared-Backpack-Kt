package io.github.ykysnk.sharedBackpackKt.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import io.github.ykysnk.sharedBackpackKt.config.Config
import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.network.chat.Component
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties

object ConfigCommand {
    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>
    ) {
        val root = Commands.literal("sharedbackpackconfig").requires { it.hasPermission(2) }

        for (property in ConfigManager.config.general::class.memberProperties) {
            val name = property.name

            @Suppress("UNCHECKED_CAST")
            val mutableProperty = property as? KMutableProperty1<Config.General, Any?> ?: continue

            when (property.returnType.classifier) {
                Boolean::class -> {
                    root.then(Commands.literal(name).executes { context ->
                        val value = mutableProperty.get(ConfigManager.config.general)

                        context.source.sendSuccess(
                            { Component.literal("$name = $value") },
                            false
                        )

                        1
                    }.then(argument("value", BoolArgumentType.bool()).executes { context ->
                        val value = BoolArgumentType.getBool(context, "value")

                        mutableProperty.set(
                            ConfigManager.config.general,
                            value
                        )

                        ConfigManager.save()
                        context.source.sendSuccess({ Component.literal("$name = $value") }, false)
                        1
                    }
                    )
                    )
                }

                Int::class -> {
                    root.then(
                        Commands.literal(name).executes { context ->
                            val value = mutableProperty.get(ConfigManager.config.general)

                            context.source.sendSuccess({ Component.literal("$name = $value") }, false)

                            1
                        }.then(argument("value", IntegerArgumentType.integer()).executes { context ->
                            val value = IntegerArgumentType.getInteger(
                                context,
                                "value"
                            )

                            mutableProperty.set(
                                ConfigManager.config.general,
                                value
                            )

                            ConfigManager.save()
                            context.source.sendSuccess({ Component.literal("$name = $value") }, false)
                            1
                        }
                        )
                    )
                }
            }
        }

        val node = dispatcher.register(root)
        dispatcher.register(Commands.literal("sbpc").redirect(node))
    }
}