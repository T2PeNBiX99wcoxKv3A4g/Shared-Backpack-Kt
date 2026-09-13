package io.github.ykysnk.serverBackpack.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.RecipeType

class UnlimitedBlastFurnacePlayerOnlyContainer(player: Player, name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractUnlimitedFurnaceContainer(
        "player-unlimited-furnace-blasting-${player.stringUUID}-${name}",
        RecipeType.BLASTING
    )