package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity

class SmokerInventory(player: PlayerEntity, name: String) :
    AbstractFurnaceInventory(player, "furnace-smoker-${name}", FurnaceInventoryType.Smoker)