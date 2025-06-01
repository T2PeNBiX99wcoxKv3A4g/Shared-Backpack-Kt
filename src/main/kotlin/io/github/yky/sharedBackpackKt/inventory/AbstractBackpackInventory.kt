package io.github.yky.sharedBackpackKt.inventory

import io.github.yky.sharedBackpackKt.Utils.Logger
import io.github.yky.sharedBackpackKt.Utils.MOD_ID
import io.github.yky.sharedBackpackKt.Utils.Server
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtIo
import net.minecraft.nbt.NbtSizeTracker
import net.minecraft.recipe.RecipeFinder
import net.minecraft.recipe.RecipeInputProvider
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.util.collection.DefaultedList
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

abstract class AbstractBackpackInventory(private val fileName: String, size: Int = 54) : Inventory,
    RecipeInputProvider {
    protected var inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(size, ItemStack.EMPTY)
    @Suppress("MemberVisibilityCanBePrivate")
    protected val dataPath: Path get() = FabricLoader.getInstance().configDir.resolve(MOD_ID).resolve("${fileName}.dat")
    private var listeners: MutableList<InventoryChangedListener> = mutableListOf()

    @Suppress("MemberVisibilityCanBePrivate")
    protected var heldStacks: DefaultedList<ItemStack>
        get() = inventory
        set(value) {
            inventory = value
        }

    init {
        // Initialize the inventory from the saved NBT data
        if (Files.exists(dataPath)) {
            runCatching {
                val nbt: NbtCompound = NbtIo.readCompressed(dataPath, NbtSizeTracker.ofUnlimitedBytes())
                readNbt(nbt, Server?.registryManager)
            }.getOrElse {
                Logger.error("Failed to load backpack data: {}", it.message)
            }
        }
    }

    @Suppress("unused")
    fun addListener(listener: InventoryChangedListener) = listeners.add(listener)

    @Suppress("unused")
    fun removeListener(listener: InventoryChangedListener) = listeners.remove(listener)

    override fun clear() {
        heldStacks.clear()
        markDirty()
    }

    override fun provideRecipeInputs(finder: RecipeFinder) {
        for (itemStack in heldStacks) {
            finder.addInput(itemStack)
        }
    }

    override fun toString(): String {
        return (heldStacks.stream().filter { stack: ItemStack -> !stack.isEmpty }
            .collect(Collectors.toList()) as List<*>).toString()
    }

    override fun size() = inventory.size

    override fun isEmpty(): Boolean {
        for (itemStack in heldStacks) {
            if (!itemStack.isEmpty) return false
        }

        return true
    }

    override fun getStack(slot: Int): ItemStack {
        return heldStacks[slot]
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val itemStack = Inventories.splitStack(heldStacks, slot, amount)
        if (!itemStack.isEmpty) markDirty()
        return itemStack
    }

    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(heldStacks, slot)

    override fun setStack(slot: Int, stack: ItemStack) {
        heldStacks[slot] = stack
        stack.capCount(this.getMaxCount(stack))
        markDirty()
    }

    override fun markDirty() {
        onChanged()
        for (inventoryChangedListener in listeners)
            inventoryChangedListener.onInventoryChanged(this)
    }

    override fun canPlayerUse(player: PlayerEntity?) = true

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun readNbt(nbt: NbtCompound, registries: WrapperLookup?) {
        Inventories.readNbt(nbt, inventory, registries)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun writeNbt(nbt: NbtCompound, registries: WrapperLookup?) {
        Inventories.writeNbt(nbt, inventory, registries)
    }

    override fun onClose(player: PlayerEntity?) {
        saveNbt()
    }

    open fun onChanged() {
        saveNbt()
    }

    open fun saveNbt() {
        val nbt = NbtCompound()

        writeNbt(nbt, Server?.registryManager)

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