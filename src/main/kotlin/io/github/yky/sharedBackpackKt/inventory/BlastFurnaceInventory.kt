package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity

class BlastFurnaceInventory(player: PlayerEntity, name: String) :
    AbstractFurnaceInventory(player, "furnace-blast-${name}", FurnaceInventoryType.Blast)