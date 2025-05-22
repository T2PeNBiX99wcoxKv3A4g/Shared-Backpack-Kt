package io.github.yky.sharedBackpackKtCreateMine

import io.github.yky.sharedBackpackKtCreateMine.inventory.BackpackInventory
import io.github.yky.sharedBackpackKtCreateMine.inventory.BackpackPlayerOnlyInventory
import io.github.yky.sharedBackpackKtCreateMine.inventory.TrashInventory
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Utils {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "shared-backpack-kt-create-mine"
    val Logger: Logger = LoggerFactory.getLogger(MOD_ID)
    var Server: MinecraftServer? = null
    val TrashInventoryTextCache: Text = Text.literal("Trash")

    private val BackpackInventoryCache: MutableMap<String, BackpackInventory> = mutableMapOf()
    private val BackpackInventoryTextCache: MutableMap<String, Text> = mutableMapOf()
    private val BackpackPlayerOnlyInventoryCache: MutableMap<String, BackpackPlayerOnlyInventory> = mutableMapOf()
    private val BackpackPlayerOnlyInventoryTextCache: MutableMap<String, Text> = mutableMapOf()
    private val TrashInventoryCache: MutableMap<String, TrashInventory> = mutableMapOf()

    fun getOrCreateBackpackInventory(name: String): BackpackInventory {
        if (BackpackInventoryCache.containsKey(name))
            return BackpackInventoryCache[name]!!
        backupBackpackData()
        val backpackInventory = BackpackInventory(name)
        BackpackInventoryCache[name] = backpackInventory
        return backpackInventory
    }

    fun getOrCreateBackpackInventoryText(name: String): Text {
        if (BackpackInventoryTextCache.containsKey(name))
            return BackpackInventoryTextCache[name]!!
        val backpackInventoryText = Text.literal("Shared Backpack: $name")
        BackpackInventoryTextCache[name] = backpackInventoryText
        return backpackInventoryText
    }

    fun getOrCreateBackpackPlayerOnlyInventory(player: ServerPlayerEntity, name: String): BackpackPlayerOnlyInventory {
        val id = player.uuidAsString + "-" + name
        if (BackpackPlayerOnlyInventoryCache.containsKey(id))
            return BackpackPlayerOnlyInventoryCache[id]!!
        backupBackpackData()
        val backpackPlayerOnlyInventory = BackpackPlayerOnlyInventory(player, name)
        BackpackPlayerOnlyInventoryCache[id] = backpackPlayerOnlyInventory
        return backpackPlayerOnlyInventory
    }

    fun getOrCreateBackpackPlayerOnlyInventoryText(name: String): Text {
        if (BackpackPlayerOnlyInventoryTextCache.containsKey(name))
            return BackpackPlayerOnlyInventoryTextCache[name]!!
        val backpackInventoryText = Text.literal("Private Backpack: $name")
        BackpackPlayerOnlyInventoryTextCache[name] = backpackInventoryText
        return backpackInventoryText
    }

    fun getOrCreateTrashInventory(player: ServerPlayerEntity): TrashInventory {
        if (TrashInventoryCache.containsKey(player.uuidAsString))
            return TrashInventoryCache[player.uuidAsString]!!
        val trashInventory = TrashInventory()
        TrashInventoryCache[player.uuidAsString] = trashInventory
        return trashInventory
    }

    private fun backupBackpackData() {
        for (backpackInventory in BackpackInventoryCache.values)
            backpackInventory.backupBackpackData()
        for (backpackPlayerOnlyInventory in BackpackPlayerOnlyInventoryCache.values)
            backpackPlayerOnlyInventory.backupBackpackData()
    }
}