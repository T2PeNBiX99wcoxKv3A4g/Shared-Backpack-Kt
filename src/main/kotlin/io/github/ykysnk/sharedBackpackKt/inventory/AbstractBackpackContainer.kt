package io.github.ykysnk.sharedBackpackKt.inventory

import io.github.ykysnk.sharedBackpackKt.Utils
import io.github.ykysnk.sharedBackpackKt.Utils.Logger
import io.github.ykysnk.sharedBackpackKt.Utils.Server
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtAccounter
import net.minecraft.nbt.NbtIo
import net.minecraft.server.MinecraftServer
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.ContainerListener
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedItemContents
import net.minecraft.world.inventory.StackedContentsCompatible
import net.minecraft.world.item.ItemStack
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

abstract class AbstractBackpackContainer(private val fileName: String, size: Int = 54) : Container,
    StackedContentsCompatible {
    val items: NonNullList<ItemStack> = NonNullList.withSize(size, ItemStack.EMPTY)

    @Suppress("MemberVisibilityCanBePrivate")
    protected val dataPath: Path get() = Utils.ConfigDir.resolve("${fileName}.dat")
    protected var viewerCount = 0
    private val listeners: MutableList<ContainerListener> = mutableListOf()

    init {
        onPreInit()
        onInit()
        TickHandler.register(this)
    }

    open fun onPreInit() = Unit

    private fun onInit() {
        // Initialize the inventory from the saved NBT data
        if (Files.exists(dataPath)) {
            runCatching {
                val compoundTag: CompoundTag = NbtIo.readCompressed(dataPath, NbtAccounter.unlimitedHeap())
                onLoad(compoundTag, Server?.registryAccess()!!)
                loadAllItems(compoundTag, Server?.registryAccess()!!)
            }.getOrElse {
                Logger.error("Failed to load backpack data: {}\n{}", it.localizedMessage, it.stackTraceToString())
            }
        }
    }

    open fun onLoad(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) = Unit

    @Suppress("unused")
    fun addListener(listener: ContainerListener) = listeners.add(listener)

    @Suppress("unused")
    fun removeListener(listener: ContainerListener) = listeners.remove(listener)

    override fun clearContent() {
        items.clear()
        setChanged()
    }

    override fun fillStackedContents(stackedItemContents: StackedItemContents) {
        for (itemStack in items) {
            stackedItemContents.accountStack(itemStack)
        }
    }

    override fun toString(): String {
        return (items.stream().filter { stack: ItemStack -> !stack.isEmpty }
            .collect(Collectors.toList()) as List<*>).toString()
    }

    override fun getContainerSize() = items.size

    override fun isEmpty(): Boolean {
        for (itemStack in items) {
            if (!itemStack.isEmpty) return false
        }

        return true
    }

    override fun getItem(slot: Int): ItemStack {
        return items[slot]
    }

    override fun removeItem(slot: Int, amount: Int): ItemStack {
        val itemStack = ContainerHelper.removeItem(items, slot, amount)
        if (!itemStack.isEmpty) setChanged()
        return itemStack
    }

    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(items, slot)

    override fun setItem(slot: Int, stack: ItemStack) {
        items[slot] = stack
        stack.limitSize(getMaxStackSize(stack))
        setChanged()
    }

    override fun setChanged() {
        onChanged()
        for (containerListener in listeners)
            containerListener.containerChanged(this)
    }

    override fun stillValid(player: Player) = true

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun loadAllItems(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) {
        ContainerHelper.loadAllItems(compoundTag, items, registries)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun saveAllItems(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) {
        ContainerHelper.saveAllItems(compoundTag, items, registries)
    }

    override fun startOpen(player: Player) {
        viewerCount++
    }

    override fun stopOpen(player: Player) {
        viewerCount--
        saveNbt()
        setNoPlayersOpen()
    }

    open fun isNoPlayersOpen() = viewerCount <= 0

    open fun setNoPlayersOpen() {
        if (!isNoPlayersOpen()) return
        TickHandler.unregister(this)
        onNoPlayersOpen()
    }

    open fun onChanged() {
        saveNbt()
        setNoPlayersOpen()
    }

    open fun saveNbt() {
        runCatching {
            val nbt = CompoundTag()
            onSave(nbt, Server?.registryAccess()!!)
            saveAllItems(nbt, Server?.registryAccess()!!)
            Files.createDirectories(dataPath.parent)
            Files.deleteIfExists(dataPath)
            val path = Files.createFile(dataPath)
            NbtIo.writeCompressed(nbt, path)
        }.getOrElse {
            Logger.error("Failed to save backpack data: {}\n{}", it.localizedMessage, it.stackTraceToString())
        }
    }

    open fun onSave(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) = Unit

    open fun tick(server: MinecraftServer) {}

    abstract val onNoPlayersOpen: () -> Unit
}