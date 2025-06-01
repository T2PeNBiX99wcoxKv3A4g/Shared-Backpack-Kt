package io.github.yky.sharedBackpackKt

import io.github.yky.sharedBackpackKt.inventory.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Utils {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "shared-backpack-kt"
    val Logger: Logger = LoggerFactory.getLogger(MOD_ID)
    var Server: MinecraftServer? = null

    private val BackpackInventoryCache: MutableMap<String, BackpackInventory> = mutableMapOf()
    private val BackpackPlayerOnlyInventoryCache: MutableMap<String, BackpackPlayerOnlyInventory> = mutableMapOf()
    private val TrashInventoryCache: MutableMap<String, TrashInventory> = mutableMapOf()
    private val FurnaceInventoryCache: MutableMap<String, FurnaceInventory> = mutableMapOf()
    private val FurnacePlayerOnlyInventoryCache: MutableMap<String, FurnacePlayerOnlyInventory> = mutableMapOf()
    private val BlastFurnaceInventoryCache: MutableMap<String, BlastFurnaceInventory> = mutableMapOf()
    private val BlastFurnacePlayerOnlyInventoryCache: MutableMap<String, BlastFurnacePlayerOnlyInventory> =
        mutableMapOf()
    private val SmokerInventoryCache: MutableMap<String, SmokerInventory> = mutableMapOf()
    private val SmokerPlayerOnlyInventoryCache: MutableMap<String, SmokerPlayerOnlyInventory> = mutableMapOf()

    fun getOrCreateBackpackInventory(name: String): BackpackInventory {
        if (BackpackInventoryCache.containsKey(name)) return BackpackInventoryCache[name]!!
        backupBackpackData()
        val inventory = BackpackInventory(name)
        BackpackInventoryCache[name] = inventory
        return inventory
    }

    fun getOrCreateBackpackPlayerOnlyInventory(player: PlayerEntity, name: String): BackpackPlayerOnlyInventory {
        val id = player.uuidAsString + "-" + name
        if (BackpackPlayerOnlyInventoryCache.containsKey(id)) return BackpackPlayerOnlyInventoryCache[id]!!
        backupBackpackData()
        val inventory = BackpackPlayerOnlyInventory(player, name)
        BackpackPlayerOnlyInventoryCache[id] = inventory
        return inventory
    }

    fun getOrCreateTrashInventory(player: PlayerEntity): TrashInventory {
        if (TrashInventoryCache.containsKey(player.uuidAsString)) return TrashInventoryCache[player.uuidAsString]!!
        val inventory = TrashInventory()
        TrashInventoryCache[player.uuidAsString] = inventory
        return inventory
    }

    fun getOrCreateNormalFurnaceInventory(player: PlayerEntity, name: String): FurnaceInventory {
        if (FurnaceInventoryCache.containsKey(name)) return FurnaceInventoryCache[name]!!
        backupBackpackData()
        val inventory = FurnaceInventory(player, name)
        FurnaceInventoryCache[name] = inventory
        return inventory
    }

    fun getOrCreateNormalFurnacePlayerOnlyInventory(player: PlayerEntity, name: String): FurnacePlayerOnlyInventory {
        if (FurnacePlayerOnlyInventoryCache.containsKey(name)) return FurnacePlayerOnlyInventoryCache[name]!!
        backupBackpackData()
        val inventory = FurnacePlayerOnlyInventory(player, name)
        FurnacePlayerOnlyInventoryCache[name] = inventory
        return inventory
    }

    fun getOrCreateBlastFurnaceInventory(player: PlayerEntity, name: String): BlastFurnacePlayerOnlyInventory {
        if (BlastFurnacePlayerOnlyInventoryCache.containsKey(name)) return BlastFurnacePlayerOnlyInventoryCache[name]!!
        backupBackpackData()
        val inventory = BlastFurnacePlayerOnlyInventory(player, name)
        BlastFurnacePlayerOnlyInventoryCache[name] = inventory
        return inventory
    }

    fun getOrCreateBlastFurnacePlayerOnlyInventory(
        player: PlayerEntity, name: String
    ): BlastFurnacePlayerOnlyInventory {
        if (BlastFurnacePlayerOnlyInventoryCache.containsKey(name)) return BlastFurnacePlayerOnlyInventoryCache[name]!!
        backupBackpackData()
        val inventory = BlastFurnacePlayerOnlyInventory(player, name)
        BlastFurnacePlayerOnlyInventoryCache[name] = inventory
        return inventory
    }

    fun getOrCreateSmokerFurnaceInventory(player: PlayerEntity, name: String): SmokerInventory {
        if (SmokerInventoryCache.containsKey(name)) return SmokerInventoryCache[name]!!
        backupBackpackData()
        val inventory = SmokerInventory(player, name)
        SmokerInventoryCache[name] = inventory
        return inventory
    }

    fun getOrCreateSmokerFurnacePlayerOnlyInventory(player: PlayerEntity, name: String): SmokerPlayerOnlyInventory {
        if (SmokerPlayerOnlyInventoryCache.containsKey(name)) return SmokerPlayerOnlyInventoryCache[name]!!
        backupBackpackData()
        val inventory = SmokerPlayerOnlyInventory(player, name)
        SmokerPlayerOnlyInventoryCache[name] = inventory
        return inventory
    }

    private fun backupBackpackData() {
        for (cache in BackpackInventoryCache.values) cache.backupBackpackData()
        for (cache in BackpackPlayerOnlyInventoryCache.values) cache.backupBackpackData()
        for (cache in FurnaceInventoryCache.values) cache.backupBackpackData()
        for (cache in BlastFurnaceInventoryCache.values) cache.backupBackpackData()
        for (cache in SmokerInventoryCache.values) cache.backupBackpackData()
    }
}