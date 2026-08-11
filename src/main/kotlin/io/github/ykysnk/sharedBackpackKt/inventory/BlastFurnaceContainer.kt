package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType

class BlastFurnaceContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractFurnaceContainer("shared-furnace-blasting-${name}", RecipeType.BLASTING) {
    override fun getBurnDuration(stack: ItemStack): Int {
        return super.getBurnDuration(stack) / 2
    }
}