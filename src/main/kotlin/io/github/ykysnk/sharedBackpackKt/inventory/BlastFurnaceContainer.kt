package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.block.entity.FuelValues

class BlastFurnaceContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractFurnaceContainer("shared-furnace-blasting-${name}", RecipeType.BLASTING) {
    override fun getBurnDuration(fuelValues: FuelValues, stack: ItemStack): Int {
        return super.getBurnDuration(fuelValues, stack) / 2
    }
}