package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.block.entity.FuelValues

class BlastFurnaceContainer(player: Player, name: String) :
    AbstractFurnaceContainer(player, "shared-furnace-blasting-${name}", RecipeType.BLASTING) {
    override fun getBurnDuration(fuelValues: FuelValues, stack: ItemStack): Int {
        return super.getBurnDuration(fuelValues, stack) / 2
    }
}