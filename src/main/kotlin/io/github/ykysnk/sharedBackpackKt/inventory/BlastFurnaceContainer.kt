package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType

class BlastFurnaceContainer(player: Player, name: String, override val onNoPlayersOpen: (Player) -> Unit) :
    AbstractFurnaceContainer(player, "shared-furnace-blasting-${name}", RecipeType.BLASTING) {
    override fun getBurnDuration(stack: ItemStack): Int {
        return super.getBurnDuration(stack) / 2
    }
}