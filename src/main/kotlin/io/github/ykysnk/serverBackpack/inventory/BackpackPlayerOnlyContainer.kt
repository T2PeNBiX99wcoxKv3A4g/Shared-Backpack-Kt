package io.github.ykysnk.serverBackpack.inventory

import net.minecraft.world.entity.player.Player

class BackpackPlayerOnlyContainer(player: Player, name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractBackpackContainer("player-backpack-${player.stringUUID}-${name}")
