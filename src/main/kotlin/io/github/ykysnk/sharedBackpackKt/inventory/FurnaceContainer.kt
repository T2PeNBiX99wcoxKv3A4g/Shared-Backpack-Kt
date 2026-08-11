package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.item.crafting.RecipeType

class FurnaceContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractFurnaceContainer("shared-furnace-smelting-${name}", RecipeType.SMELTING)