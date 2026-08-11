package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.ListTag
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class TrashContainer(val onStopOpen: (Player) -> Unit) : SimpleContainer(54) {
    override fun fromTag(listTag: ListTag, provider: HolderLookup.Provider) {
        for (i in 0..<containerSize)
            setItem(i, ItemStack.EMPTY)
    }

    override fun createTag(provider: HolderLookup.Provider): ListTag {
        val listTag = ListTag()
        return listTag
    }

    override fun stopOpen(player: Player) {
        if (!isEmpty) return
        onStopOpen(player)
    }
}