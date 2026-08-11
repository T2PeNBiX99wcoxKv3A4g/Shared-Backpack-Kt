package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.RecipeType

class FurnacePlayerOnlyContainer(player: Player, name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractFurnaceContainer("player-furnace-smelting-${player.stringUUID}-${name}", RecipeType.SMELTING)