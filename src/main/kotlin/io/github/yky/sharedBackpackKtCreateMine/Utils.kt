package io.github.yky.sharedBackpackKtCreateMine

import io.github.yky.sharedBackpackKtCreateMine.inventory.BackpackInventory
import net.minecraft.server.MinecraftServer
import net.minecraft.text.Text
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Utils {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "shared-backpack-kt-create-mine"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    var SERVER: MinecraftServer? = null

    private val BackpackInventoryCache: MutableMap<String, BackpackInventory> = mutableMapOf()
    private val BackpackInventoryTextCache: MutableMap<String, Text> = mutableMapOf()

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

    private fun backupBackpackData() {
        for (backpackInventory in BackpackInventoryCache.values)
            backpackInventory.backupBackpackData()
    }
}