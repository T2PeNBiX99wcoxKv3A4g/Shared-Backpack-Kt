package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.RecipeType

class UnlimitedFurnacePlayerOnlyContainer(player: Player, name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractUnlimitedFurnaceContainer(
        "player-unlimited-furnace-smelting-${player.stringUUID}-${name}",
        RecipeType.SMELTING
    )