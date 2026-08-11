package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType

class SmokerContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractFurnaceContainer("shared-furnace-smoking-${name}", RecipeType.SMOKING) {
    override fun getBurnDuration(stack: ItemStack): Int {
        return super.getBurnDuration(stack) / 2
    }
}