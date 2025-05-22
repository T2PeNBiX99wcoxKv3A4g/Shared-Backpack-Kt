package io.github.yky.sharedBackpackKtCreateMine.inventory

import net.minecraft.server.network.ServerPlayerEntity

class BackpackPlayerOnlyInventory(player: ServerPlayerEntity, name: String) :
    BackpackInventoryBase("backpack-player-${player.uuidAsString}-${name}")
