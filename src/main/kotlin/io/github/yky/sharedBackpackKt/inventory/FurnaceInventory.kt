package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity

class FurnaceInventory(player: PlayerEntity, name: String) :
    AbstractFurnaceInventory(player, "furnace-normal-${name}", FurnaceInventoryType.Normal)