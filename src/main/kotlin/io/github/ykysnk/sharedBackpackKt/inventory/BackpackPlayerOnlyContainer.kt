package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player

class BackpackPlayerOnlyContainer(player: Player, name: String) :
    AbstractBackpackContainer("player-backpack-${player.stringUUID}-${name}")
