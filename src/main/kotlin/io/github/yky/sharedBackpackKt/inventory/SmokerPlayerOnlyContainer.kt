package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType

class SmokerPlayerOnlyContainer(player: Player, name: String) :
    AbstractFurnaceContainer(player, "player-furnace-smoking-${player.stringUUID}-${name}", RecipeType.SMOKING) {
    override fun getBurnDuration(stack: ItemStack): Int {
        return super.getBurnDuration(stack) / 2
    }
}