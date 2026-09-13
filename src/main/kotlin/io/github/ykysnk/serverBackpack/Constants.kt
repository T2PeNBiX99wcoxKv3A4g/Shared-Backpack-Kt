@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.ykysnk.serverBackpack

import io.github.ykysnk.serverBackpack.command.BackpackCommand
import io.github.ykysnk.serverBackpack.config.Configs
import io.github.ykysnk.serverBackpack.inventory.ContainerManager
import io.github.ykysnk.serverBackpack.inventory.TickHandler
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.createDirectories

object Constants {
    init {
        ServerLifecycleEvents.SERVER_STARTING.register { getServer = it }
        ServerLifecycleEvents.SERVER_STOPPED.register { getServer = null }
    }

    const val MOD_ID = "server-backpack"
    const val MOD_NAME = "Server Backpack"

    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_NAME)

    @JvmStatic
    val ConfigDir: Path by lazy {
        val dir = FabricLoader.getInstance().configDir.resolve(NameSpaces.MOD())
        dir.createDirectories()
        dir
    }

    val ForceInitialize: Unit by lazy { doNothing(Configs, Constants, ContainerManager, TickHandler, BackpackCommand) }

    private var getServer: MinecraftServer? = null

    val Server: MinecraftServer
        get() = getServer ?: error("Server is not initialized")

    private fun doNothing(vararg objects: Any) {}
}