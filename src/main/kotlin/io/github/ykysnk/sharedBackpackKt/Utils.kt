package io.github.ykysnk.sharedBackpackKt

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.createDirectories

object Utils {
    init {
        ServerLifecycleEvents.SERVER_STARTED.register { Server = it }
    }

    const val MOD_ID = "shared-backpack-kt"
    const val MOD_NAME = "Shared Backpack Kotlin"
    const val FALLBACK_COMMAND_FAILED_PLAYER_ONLY = "This command can only be used by players."
    const val FALLBACK_SHARED_BACKPACK_FAILURE_DISABLED = "Shared Backpack is disabled in the config."
    const val FALLBACK_PRIVATE_BACKPACK_FAILURE_DISABLED = "Private Backpack is disabled in the config."
    const val FALLBACK_SHARED_SMELTING_FURNACE_FAILURE_DISABLED = "Shared Furnace is disabled in the config."
    const val FALLBACK_SHARED_BLASTING_FURNACE_FAILURE_DISABLED = "Shared Blast Furnace is disabled in the config."
    const val FALLBACK_SHARED_SMOKING_FURNACE_FAILURE_DISABLED = "Shared Smoker is disabled in the config."
    const val FALLBACK_PRIVATE_SMELTING_FURNACE_FAILURE_DISABLED = "Private Furnace is disabled in the config."
    const val FALLBACK_PRIVATE_BLASTING_FURNACE_FAILURE_DISABLED = "Private Blast Furnace is disabled in the config."
    const val FALLBACK_PRIVATE_SMOKING_FURNACE_FAILURE_DISABLED = "Private Smoker is disabled in the config."
    const val FALLBACK_SHARED_UNLIMITED_SMELTING_FURNACE_FAILURE_DISABLED =
        "Shared Unlimited Furnace is disabled in the config."
    const val FALLBACK_SHARED_UNLIMITED_BLASTING_FURNACE_FAILURE_DISABLED =
        "Shared Unlimited Blast Furnace is disabled in the config."
    const val FALLBACK_SHARED_UNLIMITED_SMOKING_FURNACE_FAILURE_DISABLED =
        "Shared Unlimited Smoker is disabled in the config."
    const val FALLBACK_PRIVATE_UNLIMITED_SMELTING_FURNACE_FAILURE_DISABLED =
        "Private Unlimited Furnace is disabled in the config."
    const val FALLBACK_PRIVATE_UNLIMITED_BLASTING_FURNACE_FAILURE_DISABLED =
        "Private Unlimited Blast Furnace is disabled in the config."
    const val FALLBACK_PRIVATE_UNLIMITED_SMOKING_FURNACE_FAILURE_DISABLED =
        "Private Unlimited Smoker is disabled in the config."
    const val FALLBACK_TRASH_FAILURE_DISABLED = "Trash Can is disabled in the config."
    const val FALLBACK_TRASH_SUCCESS_EMPTIED = "Trash Can has been emptied."
    const val FALLBACK_SHARED_BACKPACK_TITLE = "Shared Backpack: %s"
    const val FALLBACK_PRIVATE_BACKPACK_TITLE = "Private Backpack: %s"
    const val FALLBACK_SHARED_SMELTING_FURNACE_TITLE = "Shared Furnace: %s"
    const val FALLBACK_SHARED_BLASTING_FURNACE_TITLE = "Shared Blast Furnace: %s"
    const val FALLBACK_SHARED_SMOKING_FURNACE_TITLE = "Shared Smoker: %s"
    const val FALLBACK_PRIVATE_SMELTING_FURNACE_TITLE = "Private Furnace: %s"
    const val FALLBACK_PRIVATE_BLASTING_FURNACE_TITLE = "Private Blast Furnace: %s"
    const val FALLBACK_PRIVATE_SMOKING_FURNACE_TITLE = "Private Smoker: %s"
    const val FALLBACK_SHARED_UNLIMITED_SMELTING_FURNACE_TITLE = "Shared Unlimited Furnace: %s"
    const val FALLBACK_SHARED_UNLIMITED_BLASTING_FURNACE_TITLE = "Shared Unlimited Blast Furnace: %s"
    const val FALLBACK_SHARED_UNLIMITED_SMOKING_FURNACE_TITLE = "Shared Unlimited Smoker: %s"
    const val FALLBACK_PRIVATE_UNLIMITED_SMELTING_FURNACE_TITLE = "Private Unlimited Furnace: %s"
    const val FALLBACK_PRIVATE_UNLIMITED_BLASTING_FURNACE_TITLE = "Private Unlimited Blast Furnace: %s"
    const val FALLBACK_PRIVATE_UNLIMITED_SMOKING_FURNACE_TITLE = "Private Unlimited Smoker: %s"
    const val FALLBACK_TRASH_TITLE = "Trash Can"
    val Logger: Logger = LoggerFactory.getLogger(MOD_NAME)
    var Server: MinecraftServer? = null
    val ConfigDir: Path by lazy {
        val dir = FabricLoader.getInstance().configDir.resolve(MOD_ID)
        dir.createDirectories()
        dir
    }
}