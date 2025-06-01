package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.entity.player.PlayerEntity

class BackpackPlayerOnlyInventory(player: PlayerEntity, name: String) :
    AbstractBackpackInventory("backpack-player-${player.uuidAsString}-${name}")
