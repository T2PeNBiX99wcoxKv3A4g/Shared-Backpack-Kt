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
    private val UnlimitedFurnaceContainerCache: MutableMap<String, UnlimitedFurnaceContainer> = mutableMapOf()
    private val UnlimitedFurnacePlayerOnlyContainerCache: MutableMap<String, UnlimitedFurnacePlayerOnlyContainer> =
        mutableMapOf()
    private val UnlimitedBlastFurnaceContainerCache: MutableMap<String, UnlimitedBlastFurnaceContainer> = mutableMapOf()
    private val UnlimitedBlastFurnacePlayerOnlyContainerCache: MutableMap<String, UnlimitedBlastFurnacePlayerOnlyContainer> =
        mutableMapOf()
    private val UnlimitedSmokerContainerCache: MutableMap<String, UnlimitedSmokerContainer> = mutableMapOf()
    private val UnlimitedSmokerPlayerOnlyContainerCache: MutableMap<String, UnlimitedSmokerPlayerOnlyContainer> =
        mutableMapOf()

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

    fun getOrCreateNormalFurnaceContainer(player: Player, name: String): FurnaceContainer {
        val container =
            FurnaceContainerCache.getOrPut(name) { FurnaceContainer(name) { FurnaceContainerCache.remove(name) } }
        container.player = player
        return container
    }

    fun getOrCreateNormalFurnacePlayerOnlyContainer(player: Player, name: String): FurnacePlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        val container = FurnacePlayerOnlyContainerCache.getOrPut(id) {
            FurnacePlayerOnlyContainer(
                player,
                name
            ) { FurnacePlayerOnlyContainerCache.remove(id) }
        }
        container.player = player
        return container
    }

    fun getOrCreateBlastFurnaceContainer(player: Player, name: String): BlastFurnaceContainer {
        val container = BlastFurnaceContainerCache.getOrPut(name) {
            BlastFurnaceContainer(name) { BlastFurnaceContainerCache.remove(name) }
        }
        container.player = player
        return container
    }

    fun getOrCreateBlastFurnacePlayerOnlyContainer(player: Player, name: String): BlastFurnacePlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        val container = BlastFurnacePlayerOnlyContainerCache.getOrPut(id) {
            BlastFurnacePlayerOnlyContainer(player, name) { BlastFurnacePlayerOnlyContainerCache.remove(id) }
        }
        container.player = player
        return container
    }

    fun getOrCreateSmokerFurnaceContainer(player: Player, name: String): SmokerContainer {
        val container =
            SmokerContainerCache.getOrPut(name) { SmokerContainer(name) { SmokerContainerCache.remove(name) } }
        container.player = player
        return container
    }

    fun getOrCreateSmokerFurnacePlayerOnlyContainer(player: Player, name: String): SmokerPlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        val container = SmokerPlayerOnlyContainerCache.getOrPut(id) {
            SmokerPlayerOnlyContainer(
                player,
                name
            ) { SmokerPlayerOnlyContainerCache.remove(id) }
        }
        container.player = player
        return container
    }

    fun getOrCreateUnlimitedNormalFurnaceContainer(player: Player, name: String): UnlimitedFurnaceContainer {
        val container = UnlimitedFurnaceContainerCache.getOrPut(name) {
            UnlimitedFurnaceContainer(name) { UnlimitedFurnaceContainerCache.remove(name) }
        }
        container.player = player
        return container
    }

    fun getOrCreateUnlimitedNormalFurnacePlayerOnlyContainer(
        player: Player,
        name: String
    ): UnlimitedFurnacePlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        val container = UnlimitedFurnacePlayerOnlyContainerCache.getOrPut(id) {
            UnlimitedFurnacePlayerOnlyContainer(player, name) { UnlimitedFurnacePlayerOnlyContainerCache.remove(id) }
        }
        container.player = player
        return container
    }

    fun getOrCreateUnlimitedBlastFurnaceContainer(player: Player, name: String): UnlimitedBlastFurnaceContainer {
        val container = UnlimitedBlastFurnaceContainerCache.getOrPut(name) {
            UnlimitedBlastFurnaceContainer(name) { UnlimitedBlastFurnaceContainerCache.remove(name) }
        }
        container.player = player
        return container
    }

    fun getOrCreateUnlimitedBlastFurnacePlayerOnlyContainer(
        player: Player,
        name: String
    ): UnlimitedBlastFurnacePlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        val container = UnlimitedBlastFurnacePlayerOnlyContainerCache.getOrPut(id) {
            UnlimitedBlastFurnacePlayerOnlyContainer(
                player,
                name
            ) { UnlimitedBlastFurnacePlayerOnlyContainerCache.remove(id) }
        }
        container.player = player
        return container
    }

    fun getOrCreateUnlimitedSmokerFurnaceContainer(player: Player, name: String): UnlimitedSmokerContainer {
        val container = UnlimitedSmokerContainerCache.getOrPut(name) {
            UnlimitedSmokerContainer(name) { UnlimitedSmokerContainerCache.remove(name) }
        }
        container.player = player
        return container
    }

    fun getOrCreateUnlimitedSmokerFurnacePlayerOnlyContainer(
        player: Player,
        name: String
    ): UnlimitedSmokerPlayerOnlyContainer {
        val id = "${player.stringUUID}-${name}"
        val container = UnlimitedSmokerPlayerOnlyContainerCache.getOrPut(id) {
            UnlimitedSmokerPlayerOnlyContainer(player, name) { UnlimitedSmokerPlayerOnlyContainerCache.remove(id) }
        }
        container.player = player
        return container
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