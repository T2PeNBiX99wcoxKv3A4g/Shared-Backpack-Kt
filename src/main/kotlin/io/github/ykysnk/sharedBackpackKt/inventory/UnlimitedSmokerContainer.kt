package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.item.crafting.RecipeType

class UnlimitedSmokerContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractUnlimitedFurnaceContainer("shared-unlimited-furnace-smoking-${name}", RecipeType.SMOKING)