package io.github.yky.sharedBackpackKtCreateMine.inventory

import io.github.yky.sharedBackpackKtCreateMine.Utils.LOGGER
import io.github.yky.sharedBackpackKtCreateMine.Utils.MOD_ID
import io.github.yky.sharedBackpackKtCreateMine.Utils.SERVER
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtIo
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtSizeTracker
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import java.nio.file.Files
import java.nio.file.Path

class BackpackInventory(private val name: String) : SimpleInventory(54) {
    private val dataPath: Path = FabricLoader.getInstance().configDir.resolve(MOD_ID).resolve("backpack-${name}.dat")

    init {
        // Initialize the inventory from the saved NBT data
        if (Files.exists(dataPath)) {
            try {
                val nbt: NbtCompound =
                    NbtIo.readCompressed(dataPath, NbtSizeTracker.ofUnlimitedBytes())
                SERVER?.gameInstance?.let { this.readNbtList(nbt.getListOrEmpty("Items"), it.registryManager) }
            } catch (e: Exception) {
                LOGGER.error("Failed to load backpack data: {}", e.message)
            }
        }

        this.addListener { saveNbt() }
    }

    override fun readNbtList(list: NbtList, registries: WrapperLookup) {
        for (i in 0..<this.size()) {
            this.setStack(i, ItemStack.EMPTY)
        }

        for (i in list.indices) {
            val nbtCompound = list.getCompoundOrEmpty(i)
            val j = nbtCompound.getByte("Slot", 0.toByte()).toInt() and 255
            if (j < this.size()) {
                this.setStack(j, ItemStack.fromNbt(registries, nbtCompound).orElse(ItemStack.EMPTY))
            }
        }
    }

    override fun toNbtList(registries: WrapperLookup): NbtList {
        val nbtList = NbtList()

        for (i in 0..<this.size()) {
            val itemStack = this.getStack(i)
            if (!itemStack.isEmpty) {
                val nbtCompound = NbtCompound()
                nbtCompound.putByte("Slot", i.toByte())
                nbtList.add(itemStack.toNbt(registries, nbtCompound))
            }
        }

        return nbtList
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun saveNbt() {
        val nbt = NbtCompound()
        nbt.put("Items", SERVER?.gameInstance?.let { this.toNbtList(it.registryManager) })

        try {
            Files.createDirectories(dataPath.parent)
            Files.deleteIfExists(dataPath)
            val path = Files.createFile(dataPath)
            NbtIo.writeCompressed(nbt, path)
        } catch (e: Exception) {
            LOGGER.error("Failed to save backpack data: {}", e.message)
        }
    }

    fun backupBackpackData() {
        runCatching {
            if (dataPath.toFile().exists()) {
                val backupPath = dataPath.parent.resolve("backpack-${name}.dat_old")
                Files.deleteIfExists(backupPath)
                Files.copy(dataPath, backupPath)
                LOGGER.info("Backed up backpack data to {}", backupPath)
            }
        }.getOrElse {
            LOGGER.error("Failed to back up backpack data: {}", it.message)
        }
    }
}