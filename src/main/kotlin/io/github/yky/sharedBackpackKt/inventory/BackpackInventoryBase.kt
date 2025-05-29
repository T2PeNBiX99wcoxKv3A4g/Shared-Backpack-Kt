package io.github.yky.sharedBackpackKt.inventory

import io.github.yky.sharedBackpackKt.Utils.Logger
import io.github.yky.sharedBackpackKt.Utils.MOD_ID
import io.github.yky.sharedBackpackKt.Utils.Server
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtIo
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtSizeTracker
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import java.nio.file.Files
import java.nio.file.Path

open class BackpackInventoryBase(private val fileName: String) : SimpleInventory(54) {
    private val dataPath: Path get() = FabricLoader.getInstance().configDir.resolve(MOD_ID).resolve("${fileName}.dat")
    private var isChanged = false

    init {
        // Initialize the inventory from the saved NBT data
        if (Files.exists(dataPath)) {
            try {
                val nbt: NbtCompound =
                    NbtIo.readCompressed(dataPath, NbtSizeTracker.ofUnlimitedBytes())
                Server?.gameInstance?.let { readNbtList(nbt.getListOrEmpty("Items"), it.registryManager) }
            } catch (e: Exception) {
                Logger.error("Failed to load backpack data: {}", e.message)
            }
        }
        add()
    }

    private fun add() {
        addListener { onChanged() }
    }

    override fun readNbtList(list: NbtList, registries: WrapperLookup) {
        for (i in 0..<size())
            setStack(i, ItemStack.EMPTY)

        for (i in list.indices) {
            val nbtCompound = list.getCompoundOrEmpty(i)
            val j = nbtCompound.getByte("Slot", 0.toByte()).toInt() and 255
            if (j < size()) {
                setStack(j, ItemStack.fromNbt(registries, nbtCompound).orElse(ItemStack.EMPTY))
            }
        }
    }

    override fun toNbtList(registries: WrapperLookup): NbtList {
        val nbtList = NbtList()

        for (i in 0..<size()) {
            val itemStack = getStack(i)
            if (!itemStack.isEmpty) {
                val nbtCompound = NbtCompound()
                nbtCompound.putByte("Slot", i.toByte())
                nbtList.add(itemStack.toNbt(registries, nbtCompound))
            }
        }

        return nbtList
    }

    override fun onClose(player: PlayerEntity?) {
        if (!isChanged) return
        saveNbt()
        isChanged = false
    }

    open fun onChanged() {
        isChanged = true
    }

    open fun saveNbt() {
        val nbt = NbtCompound()
        nbt.put("Items", Server?.gameInstance?.let { toNbtList(it.registryManager) })

        try {
            Files.createDirectories(dataPath.parent)
            Files.deleteIfExists(dataPath)
            val path = Files.createFile(dataPath)
            NbtIo.writeCompressed(nbt, path)
        } catch (e: Exception) {
            Logger.error("Failed to save backpack data: {}", e.message)
        }
    }

    open fun backupBackpackData() {
        runCatching {
            if (dataPath.toFile().exists()) {
                val backupPath = dataPath.parent.resolve("${fileName}.dat_old")
                Files.deleteIfExists(backupPath)
                Files.copy(dataPath, backupPath)
                Logger.info("Backed up backpack data to {}", backupPath)
            }
        }.getOrElse {
            Logger.error("Failed to back up backpack data: {}", it.message)
        }
    }
}