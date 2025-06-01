package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity

class FurnacePlayerOnlyInventory(player: PlayerEntity, name: String) : AbstractFurnaceInventory(
    player, "furnace-normal-player-${player.uuidAsString}-${name}", FurnaceInventoryType.Normal
)