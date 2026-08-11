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
    const val COMMAND_FAILED_PLAYER_ONLY = "This command can only be used by players."
    val Logger: Logger = LoggerFactory.getLogger(MOD_NAME)
    var Server: MinecraftServer? = null
    val ConfigDir: Path by lazy {
        val dir = FabricLoader.getInstance().configDir.resolve(MOD_ID)
        dir.createDirectories()
        dir
    }
}