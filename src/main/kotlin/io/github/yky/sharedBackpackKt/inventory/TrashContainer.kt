package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.nbt.ListTag
import net.minecraft.world.SimpleContainer
import net.minecraft.world.item.ItemStack

class TrashContainer : SimpleContainer(54) {
    override fun fromTag(listTag: ListTag) {
        for (i in 0..<containerSize)
            setItem(i, ItemStack.EMPTY)
    }

    override fun createTag(): ListTag {
        val listTag = ListTag()
        return listTag
    }
}