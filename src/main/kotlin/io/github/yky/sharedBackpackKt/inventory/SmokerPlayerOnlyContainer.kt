package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.block.entity.FuelValues

class SmokerPlayerOnlyContainer(player: Player, name: String) :
    AbstractFurnaceContainer(player, "player-furnace-smoking-${player.stringUUID}-${name}", RecipeType.SMOKING) {
    override fun getBurnDuration(fuelValues: FuelValues, stack: ItemStack): Int {
        return super.getBurnDuration(fuelValues, stack) / 2
    }
}