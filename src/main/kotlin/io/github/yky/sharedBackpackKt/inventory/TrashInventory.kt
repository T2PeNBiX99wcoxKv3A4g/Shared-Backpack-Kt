package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtList
import net.minecraft.registry.RegistryWrapper.WrapperLookup

class TrashInventory : SimpleInventory(54) {
    override fun readNbtList(list: NbtList, registries: WrapperLookup) {
        for (i in 0..<size())
            setStack(i, ItemStack.EMPTY)
    }

    override fun toNbtList(registries: WrapperLookup): NbtList {
        val nbtList = NbtList()
        return nbtList
    }

//    override fun onClose(player: PlayerEntity?) {
//        clear()
//    }
}