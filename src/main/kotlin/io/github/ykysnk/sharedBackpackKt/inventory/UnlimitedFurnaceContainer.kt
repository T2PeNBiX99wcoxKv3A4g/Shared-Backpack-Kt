package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.item.crafting.RecipeType

class UnlimitedFurnaceContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractUnlimitedFurnaceContainer("shared-unlimited-furnace-smelting-${name}", RecipeType.SMELTING)