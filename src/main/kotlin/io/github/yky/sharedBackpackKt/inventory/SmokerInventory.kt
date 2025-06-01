package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.FuelRegistry
import net.minecraft.item.ItemStack

class SmokerInventory(player: PlayerEntity, name: String) :
    AbstractFurnaceInventory(player, "furnace-smoker-${name}", FurnaceInventoryType.Smoker) {
    override fun getFuelTime(fuelRegistry: FuelRegistry, stack: ItemStack?): Int {
        return super.getFuelTime(fuelRegistry, stack) / 2
    }
}