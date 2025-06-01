package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity

class BlastFurnacePlayerOnlyInventory(player: PlayerEntity, name: String) :
    AbstractFurnaceInventory(player, "furnace-blast-${player.uuidAsString}-${name}", FurnaceInventoryType.Blast)