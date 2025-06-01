package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity

class SmokerPlayerOnlyInventory(player: PlayerEntity, name: String) :
    AbstractFurnaceInventory(player, "furnace-smoker-${player.uuidAsString}-${name}", FurnaceInventoryType.Smoker)