package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.RecipeType

class FurnaceContainer(player: Player, name: String, override val onNoPlayersOpen: (Player) -> Unit) :
    AbstractFurnaceContainer(player, "shared-furnace-smelting-${name}", RecipeType.SMELTING)