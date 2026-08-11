package io.github.ykysnk.sharedBackpackKt.inventory

import io.github.ykysnk.sharedBackpackKt.Utils
import io.github.ykysnk.sharedBackpackKt.Utils.Logger
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.world.entity.player.Player
import java.nio.file.Files

object ContainerManager {
    init {
        ServerLifecycleEvents.SERVER_STOPPING.register {
            clear()
            backupBackpackData()
        }
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

    fun getOrCreateBackpackContainer(name: String) = BackpackContainerCache.getOrPut(name) {
        BackpackContainer(name) {
            BackpackContainerCache.remove(name)
        }
    }

    fun getOrCreateBackpackPlayerOnlyContainer(player: Player, name: String): BackpackPlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        return BackpackPlayerOnlyContainerCache.getOrPut(id) {
            BackpackPlayerOnlyContainer(player, name) { BackpackPlayerOnlyContainerCache.remove(id) }
        }
    }

    fun getOrCreateTrashContainer(player: Player) =
        TrashContainerCache.getOrPut(player.stringUUID) { TrashContainer { TrashContainerCache.remove(player.stringUUID) } }

    fun clearContentOfTrashContainer(player: Player) {
        val container = TrashContainerCache[player.stringUUID]
        container?.clearContent()
        TrashContainerCache.remove(player.stringUUID)
    }

    fun getOrCreateNormalFurnaceContainer(player: Player, name: String) = FurnaceContainerCache.getOrPut(name) {
        FurnaceContainer(player, name) { FurnaceContainerCache.remove(name) }
    }

    fun getOrCreateNormalFurnacePlayerOnlyContainer(player: Player, name: String): FurnacePlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        return FurnacePlayerOnlyContainerCache.getOrPut(id) {
            FurnacePlayerOnlyContainer(player, name) { FurnacePlayerOnlyContainerCache.remove(id) }
        }
    }

    fun getOrCreateBlastFurnaceContainer(player: Player, name: String) = BlastFurnaceContainerCache.getOrPut(name) {
        BlastFurnaceContainer(player, name) { BlastFurnaceContainerCache.remove(name) }
    }

    fun getOrCreateBlastFurnacePlayerOnlyContainer(player: Player, name: String): BlastFurnacePlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        return BlastFurnacePlayerOnlyContainerCache.getOrPut(id) {
            BlastFurnacePlayerOnlyContainer(player, name) { BlastFurnacePlayerOnlyContainerCache.remove(id) }
        }
    }

    fun getOrCreateSmokerFurnaceContainer(player: Player, name: String) = SmokerContainerCache.getOrPut(name) {
        SmokerContainer(player, name) { SmokerContainerCache.remove(name) }
    }

    fun getOrCreateSmokerFurnacePlayerOnlyContainer(player: Player, name: String): SmokerPlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        return SmokerPlayerOnlyContainerCache.getOrPut(id) {
            SmokerPlayerOnlyContainer(player, name) { SmokerPlayerOnlyContainerCache.remove(id) }
        }
    }

    fun clear() {
        BackpackContainerCache.forEach { (_, container) -> container.saveNbt() }
        BackpackContainerCache.clear()
        BackpackPlayerOnlyContainerCache.forEach { (_, container) -> container.saveNbt() }
        BackpackPlayerOnlyContainerCache.clear()
        TrashContainerCache.clear()
        FurnaceContainerCache.forEach { (_, container) -> container.saveNbt() }
        FurnaceContainerCache.clear()
        FurnacePlayerOnlyContainerCache.forEach { (_, container) -> container.saveNbt() }
        FurnacePlayerOnlyContainerCache.clear()
        BlastFurnaceContainerCache.forEach { (_, container) -> container.saveNbt() }
        BlastFurnaceContainerCache.clear()
        BlastFurnacePlayerOnlyContainerCache.forEach { (_, container) -> container.saveNbt() }
        BlastFurnacePlayerOnlyContainerCache.clear()
        SmokerContainerCache.forEach { (_, container) -> container.saveNbt() }
        SmokerContainerCache.clear()
        SmokerPlayerOnlyContainerCache.forEach { (_, container) -> container.saveNbt() }
        SmokerPlayerOnlyContainerCache.clear()
    }

    private fun backupBackpackData() {
        Files.list(Utils.ConfigDir).use { files ->
            files
                .filter { Files.isRegularFile(it) }
                .filter { it.fileName.toString().endsWith(".dat") }
                .forEach { path ->
                    runCatching {
                        val backupPath = path.resolveSibling("${path.fileName.toString().removeSuffix(".dat")}.dat_old")
                        Files.deleteIfExists(backupPath)
                        Files.copy(path, backupPath)
                        Logger.info("Backed up backpack data to {}", backupPath)
                    }.getOrElse {
                        Logger.error(
                            "Failed to back up backpack data: {}\n{}",
                            it.localizedMessage,
                            it.stackTraceToString()
                        )
                    }
                }
        }
    }
}