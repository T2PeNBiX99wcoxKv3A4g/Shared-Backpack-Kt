package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.FuelRegistry
import net.minecraft.item.ItemStack

class BlastFurnacePlayerOnlyInventory(player: PlayerEntity, name: String) :
    AbstractFurnaceInventory(player, "furnace-blast-${player.uuidAsString}-${name}", FurnaceInventoryType.Blast) {
    override fun getFuelTime(fuelRegistry: FuelRegistry, stack: ItemStack?): Int {
        return super.getFuelTime(fuelRegistry, stack) / 2
    }
}