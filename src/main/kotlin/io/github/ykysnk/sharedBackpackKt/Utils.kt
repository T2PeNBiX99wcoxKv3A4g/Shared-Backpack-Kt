package io.github.ykysnk.sharedBackpackKt

import io.github.ykysnk.sharedBackpackKt.inventory.*
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import net.minecraft.world.entity.player.Player
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.createDirectories

object Utils {
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

    private val BackpackContainerCache: MutableMap<String, BackpackContainer> = mutableMapOf()
    private val BackpackPlayerOnlyContainerCache: MutableMap<String, BackpackPlayerOnlyContainer> = mutableMapOf()
    private val TrashContainerCache: MutableMap<String, TrashContainer> = mutableMapOf()
    private val FurnaceContainerCache: MutableMap<String, FurnaceContainer> = mutableMapOf()
    private val FurnacePlayerOnlyContainerCache: MutableMap<String, FurnacePlayerOnlyContainer> = mutableMapOf()
    private val BlastFurnaceContainerCache: MutableMap<String, BlastFurnaceContainer> = mutableMapOf()
    private val BlastFurnacePlayerOnlyContainerCache: MutableMap<String, BlastFurnacePlayerOnlyContainer> =
        mutableMapOf()
    private val SmokerContainerCache: MutableMap<String, SmokerContainer> = mutableMapOf()
    private val SmokerPlayerOnlyContainerCache: MutableMap<String, SmokerPlayerOnlyContainer> = mutableMapOf()

    fun getOrCreateBackpackContainer(name: String): BackpackContainer {
        if (BackpackContainerCache.containsKey(name)) return BackpackContainerCache[name]!!
        backupBackpackData()
        val container = BackpackContainer(name)
        BackpackContainerCache[name] = container
        return container
    }

    fun getOrCreateBackpackPlayerOnlyContainer(player: Player, name: String): BackpackPlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        if (BackpackPlayerOnlyContainerCache.containsKey(id)) return BackpackPlayerOnlyContainerCache[id]!!
        backupBackpackData()
        val container = BackpackPlayerOnlyContainer(player, name)
        BackpackPlayerOnlyContainerCache[id] = container
        return container
    }

    fun getOrCreateTrashContainer(player: Player): TrashContainer {
        if (TrashContainerCache.containsKey(player.stringUUID)) return TrashContainerCache[player.stringUUID]!!
        val container = TrashContainer()
        TrashContainerCache[player.stringUUID] = container
        return container
    }

    fun getOrCreateNormalFurnaceContainer(player: Player, name: String): FurnaceContainer {
        if (FurnaceContainerCache.containsKey(name)) return FurnaceContainerCache[name]!!
        backupBackpackData()
        val container = FurnaceContainer(player, name)
        FurnaceContainerCache[name] = container
        return container
    }

    fun getOrCreateNormalFurnacePlayerOnlyContainer(player: Player, name: String): FurnacePlayerOnlyContainer {
        if (FurnacePlayerOnlyContainerCache.containsKey(name)) return FurnacePlayerOnlyContainerCache[name]!!
        backupBackpackData()
        val container = FurnacePlayerOnlyContainer(player, name)
        FurnacePlayerOnlyContainerCache[name] = container
        return container
    }

    fun getOrCreateBlastFurnaceContainer(player: Player, name: String): BlastFurnacePlayerOnlyContainer {
        if (BlastFurnacePlayerOnlyContainerCache.containsKey(name)) return BlastFurnacePlayerOnlyContainerCache[name]!!
        backupBackpackData()
        val container = BlastFurnacePlayerOnlyContainer(player, name)
        BlastFurnacePlayerOnlyContainerCache[name] = container
        return container
    }

    fun getOrCreateBlastFurnacePlayerOnlyContainer(
        player: Player, name: String
    ): BlastFurnacePlayerOnlyContainer {
        if (BlastFurnacePlayerOnlyContainerCache.containsKey(name)) return BlastFurnacePlayerOnlyContainerCache[name]!!
        backupBackpackData()
        val container = BlastFurnacePlayerOnlyContainer(player, name)
        BlastFurnacePlayerOnlyContainerCache[name] = container
        return container
    }

    fun getOrCreateSmokerFurnaceContainer(player: Player, name: String): SmokerContainer {
        if (SmokerContainerCache.containsKey(name)) return SmokerContainerCache[name]!!
        backupBackpackData()
        val container = SmokerContainer(player, name)
        SmokerContainerCache[name] = container
        return container
    }

    fun getOrCreateSmokerFurnacePlayerOnlyContainer(player: Player, name: String): SmokerPlayerOnlyContainer {
        if (SmokerPlayerOnlyContainerCache.containsKey(name)) return SmokerPlayerOnlyContainerCache[name]!!
        backupBackpackData()
        val container = SmokerPlayerOnlyContainer(player, name)
        SmokerPlayerOnlyContainerCache[name] = container
        return container
    }

    private fun backupBackpackData() {
        for (cache in BackpackContainerCache.values) cache.backupBackpackData()
        for (cache in BackpackPlayerOnlyContainerCache.values) cache.backupBackpackData()
        for (cache in FurnaceContainerCache.values) cache.backupBackpackData()
        for (cache in BlastFurnaceContainerCache.values) cache.backupBackpackData()
        for (cache in SmokerContainerCache.values) cache.backupBackpackData()
    }
}