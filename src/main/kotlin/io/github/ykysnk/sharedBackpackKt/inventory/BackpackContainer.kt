package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player

class BackpackContainer(name: String, override val onNoPlayersOpen: (Player) -> Unit) :
    AbstractBackpackContainer("shared-backpack-${name}")
