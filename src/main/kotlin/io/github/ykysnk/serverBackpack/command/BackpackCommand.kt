package io.github.ykysnk.serverBackpack.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.ykysnk.serverBackpack.FallbackTranslations
import io.github.ykysnk.serverBackpack.argument.TrashType
import io.github.ykysnk.serverBackpack.config.Configs
import io.github.ykysnk.serverBackpack.extensions.argument
import io.github.ykysnk.serverBackpack.extensions.executesLogError
import io.github.ykysnk.serverBackpack.extensions.literal
import io.github.ykysnk.serverBackpack.inventory.ContainerManager
import io.github.ykysnk.serverBackpack.inventory.CustomFurnaceMenu
import io.github.ykysnk.serverBackpack.inventory.FurnaceInventoryType
import io.github.ykysnk.serverBackpack.inventory.FurnaceInventoryType.*
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.ChestMenu

object BackpackCommand {
    private const val ARGUMENT_NAME = "name"

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        @Suppress("unused") registryAccess: CommandBuildContext,
        @Suppress("unused") environment: Commands.CommandSelection
    ) {
        val command = Commands.literal("serverbackpack").apply {
            literal("backpack") {
                literal("shared") {
                    argument(ARGUMENT_NAME, StringArgumentType.word()) {
                        executesLogError {
                            sharedBackpack(it.source, StringArgumentType.getString(it, ARGUMENT_NAME))
                        }
                    }
                }

                literal("private") {
                    argument(ARGUMENT_NAME, StringArgumentType.word()) {
                        executesLogError {
                            privateBackpack(it.source, StringArgumentType.getString(it, ARGUMENT_NAME))
                        }
                    }
                }
            }

            literal("furnace") {
                FurnaceInventoryType.entries.forEach { type ->
                    literal(type.name.lowercase()) {
                        literal("shared") {
                            argument(ARGUMENT_NAME, StringArgumentType.word()) {
                                executesLogError {
                                    sharedFurnace(it.source, type, StringArgumentType.getString(it, ARGUMENT_NAME))
                                }
                            }
                        }
                        literal("private") {
                            argument(ARGUMENT_NAME, StringArgumentType.word()) {
                                executesLogError {
                                    privateFurnace(it.source, type, StringArgumentType.getString(it, ARGUMENT_NAME))
                                }
                            }
                        }
                    }
                }
            }

            literal("unlimitedfurnace") {
                requires { it.hasPermission(2) }

                FurnaceInventoryType.entries.forEach { type ->
                    literal(type.name.lowercase()) {
                        literal("shared") {
                            argument(ARGUMENT_NAME, StringArgumentType.word()) {
                                executesLogError {
                                    sharedUnlimitedFurnace(
                                        it.source,
                                        type,
                                        StringArgumentType.getString(it, ARGUMENT_NAME)
                                    )
                                }
                            }
                        }
                        literal("private") {
                            argument(ARGUMENT_NAME, StringArgumentType.word()) {
                                executesLogError {
                                    privateUnlimitedFurnace(
                                        it.source,
                                        type,
                                        StringArgumentType.getString(it, ARGUMENT_NAME)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            literal("trash") {
                TrashType.entries.forEach { type ->
                    literal(type.name.lowercase()) {
                        executesLogError {
                            trash(it.source, type)
                        }
                    }
                }
            }
        }

        dispatcher.register(Commands.literal("sbp").redirect(dispatcher.register(command)))
    }

    private fun sharedBackpack(source: CommandSourceStack, name: String): Int {
        val player = source.playerOrException

        if (!Configs.mainConfig.backpack.sharedBackpackEnabled) {
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.server-backpack.shared-backpack.failure.disabled",
                    FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_BACKPACK_FAILURE_DISABLED
                )
            )
            return 0
        }

        player.openMenu(
            SimpleMenuProvider(
                { syncId, inventory, _ ->
                    ChestMenu.sixRows(syncId, inventory, ContainerManager.getOrCreateBackpackContainer(name))
                },
                Component.translatableWithFallback(
                    "command.server-backpack.shared-backpack.title",
                    FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_BACKPACK_TITLE,
                    name
                )
            )
        )
        return 1
    }

    private fun privateBackpack(source: CommandSourceStack, name: String): Int {
        val player = source.playerOrException

        if (!Configs.mainConfig.backpack.privateBackpackEnabled) {
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.server-backpack.private-backpack.failure.disabled",
                    FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_BACKPACK_FAILURE_DISABLED
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
                    "command.server-backpack.private-backpack.title",
                    FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_BACKPACK_TITLE,
                    name
                )
            )
        )
        return 1
    }

    private fun sharedFurnace(source: CommandSourceStack, type: FurnaceInventoryType, name: String): Int {
        val player = source.playerOrException

        when (type) {
            Smelting -> {
                if (!Configs.mainConfig.furnace.sharedSmeltingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.shared-smelting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_SMELTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Blasting -> {
                if (!Configs.mainConfig.furnace.sharedBlastingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.shared-blasting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_BLASTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Smoking -> {
                if (!Configs.mainConfig.furnace.sharedSmokingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.shared-smoking-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_SMOKING_FURNACE_FAILURE_DISABLED
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

                    CustomFurnaceMenu(syncId, inventory, furnaceInventory, furnaceInventory.dataAccess)
                }, when (type) {
                    Smelting -> Component.translatableWithFallback(
                        "command.server-backpack.shared-smelting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_SMELTING_FURNACE_TITLE,
                        name
                    )

                    Blasting -> Component.translatableWithFallback(
                        "command.server-backpack.shared-blasting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_BLASTING_FURNACE_TITLE,
                        name
                    )

                    Smoking -> Component.translatableWithFallback(
                        "command.server-backpack.shared-smoking-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_SMOKING_FURNACE_TITLE,
                        name
                    )
                }
            )
        )
        return 1
    }

    private fun privateFurnace(source: CommandSourceStack, type: FurnaceInventoryType, name: String): Int {
        val player = source.playerOrException

        when (type) {
            Smelting -> {
                if (!Configs.mainConfig.furnace.privateSmeltingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.private-smelting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_SMELTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Blasting -> {
                if (!Configs.mainConfig.furnace.privateBlastingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.private-blasting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_BLASTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Smoking -> {
                if (!Configs.mainConfig.furnace.privateSmokingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.private-smoking-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_SMOKING_FURNACE_FAILURE_DISABLED
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
                        Smelting -> ContainerManager.getOrCreateNormalFurnacePlayerOnlyContainer(player2, name)
                        Blasting -> ContainerManager.getOrCreateBlastFurnacePlayerOnlyContainer(player2, name)
                        Smoking -> ContainerManager.getOrCreateSmokerFurnacePlayerOnlyContainer(player2, name)
                    }

                    CustomFurnaceMenu(syncId, inventory, furnaceInventory, furnaceInventory.dataAccess)
                }, when (type) {
                    Smelting -> Component.translatableWithFallback(
                        "command.server-backpack.private-smelting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_SMELTING_FURNACE_TITLE,
                        name
                    )

                    Blasting -> Component.translatableWithFallback(
                        "command.server-backpack.private-blasting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_BLASTING_FURNACE_TITLE,
                        name
                    )

                    Smoking -> Component.translatableWithFallback(
                        "command.server-backpack.private-smoking-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_SMOKING_FURNACE_TITLE,
                        name
                    )
                }
            )
        )
        return 1
    }

    private fun sharedUnlimitedFurnace(source: CommandSourceStack, type: FurnaceInventoryType, name: String): Int {
        val player = source.playerOrException

        when (type) {
            Smelting -> {
                if (!Configs.mainConfig.unlimitedFurnace.sharedUnlimitedSmeltingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.shared-unlimited-smelting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_UNLIMITED_SMELTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Blasting -> {
                if (!Configs.mainConfig.unlimitedFurnace.sharedUnlimitedBlastingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.shared-unlimited-blasting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_UNLIMITED_BLASTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Smoking -> {
                if (!Configs.mainConfig.unlimitedFurnace.sharedUnlimitedSmokingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.shared-unlimited-smoking-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_UNLIMITED_SMOKING_FURNACE_FAILURE_DISABLED
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
                        "command.server-backpack.shared-unlimited-smelting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_UNLIMITED_SMELTING_FURNACE_TITLE,
                        name
                    )

                    Blasting -> Component.translatableWithFallback(
                        "command.server-backpack.shared-unlimited-blasting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_UNLIMITED_BLASTING_FURNACE_TITLE,
                        name
                    )

                    Smoking -> Component.translatableWithFallback(
                        "command.server-backpack.shared-unlimited-smoking-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_SHARED_UNLIMITED_SMOKING_FURNACE_TITLE,
                        name
                    )
                }
            )
        )
        return 1
    }

    private fun privateUnlimitedFurnace(source: CommandSourceStack, type: FurnaceInventoryType, name: String): Int {
        val player = source.playerOrException

        when (type) {
            Smelting -> {
                if (!Configs.mainConfig.unlimitedFurnace.privateUnlimitedSmeltingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.private-unlimited-smelting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_UNLIMITED_SMELTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Blasting -> {
                if (!Configs.mainConfig.unlimitedFurnace.privateUnlimitedBlastingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.private-unlimited-blasting-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_UNLIMITED_BLASTING_FURNACE_FAILURE_DISABLED
                        )
                    )
                    return 0
                }
            }

            Smoking -> {
                if (!Configs.mainConfig.unlimitedFurnace.privateUnlimitedSmokingFurnaceEnabled) {
                    source.sendFailure(
                        Component.translatableWithFallback(
                            "command.server-backpack.private-unlimited-smoking-furnace.failure.disabled",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_UNLIMITED_SMOKING_FURNACE_FAILURE_DISABLED
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
                        Smelting -> ContainerManager.getOrCreateUnlimitedNormalFurnacePlayerOnlyContainer(player2, name)
                        Blasting -> ContainerManager.getOrCreateUnlimitedBlastFurnacePlayerOnlyContainer(player2, name)
                        Smoking -> ContainerManager.getOrCreateUnlimitedSmokerFurnacePlayerOnlyContainer(player2, name)
                    }

                    CustomFurnaceMenu(syncId, inventory, furnaceInventory, furnaceInventory.dataAccess)
                }, when (type) {
                    Smelting -> Component.translatableWithFallback(
                        "command.server-backpack.private-unlimited-smelting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_UNLIMITED_SMELTING_FURNACE_TITLE,
                        name
                    )

                    Blasting -> Component.translatableWithFallback(
                        "command.server-backpack.private-unlimited-blasting-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_UNLIMITED_BLASTING_FURNACE_TITLE,
                        name
                    )

                    Smoking -> Component.translatableWithFallback(
                        "command.server-backpack.private-unlimited-smoking-furnace.title",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_PRIVATE_UNLIMITED_SMOKING_FURNACE_TITLE,
                        name
                    )
                }
            )
        )
        return 1
    }

    private fun trash(source: CommandSourceStack, trashType: TrashType): Int {
        val player = source.playerOrException

        if (!Configs.mainConfig.trash.trashEnabled) {
            source.sendFailure(
                Component.translatableWithFallback(
                    "command.server-backpack.trash.failure.disabled",
                    FallbackTranslations.COMMAND_SERVER_BACKPACK_TRASH_FAILURE_DISABLED
                )
            )
            return 0
        }

        when (trashType) {
            TrashType.Open -> {
                player.openMenu(
                    SimpleMenuProvider(
                        { syncId, inventory, player2 ->
                            if (player2 == null) return@SimpleMenuProvider null
                            ChestMenu.sixRows(
                                syncId, inventory, ContainerManager.getOrCreateTrashContainer(player2)
                            )
                        }, Component.translatableWithFallback(
                            "command.server-backpack.trash.title",
                            FallbackTranslations.COMMAND_SERVER_BACKPACK_TRASH_TITLE
                        )
                    )
                )
                return 1
            }

            TrashType.Clear -> {
                ContainerManager.clearContentOfTrashContainer(player)
                source.sendSuccess({
                    Component.translatableWithFallback(
                        "command.server-backpack.trash.success.emptied",
                        FallbackTranslations.COMMAND_SERVER_BACKPACK_TRASH_SUCCESS_EMPTIED
                    )
                }, false)
                return 1
            }
        }
    }

    init {
        CommandRegistrationCallback.EVENT.register(::register)
    }
}