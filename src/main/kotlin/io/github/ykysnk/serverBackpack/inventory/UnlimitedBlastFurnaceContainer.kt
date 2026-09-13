package io.github.ykysnk.serverBackpack.inventory

import net.minecraft.world.item.crafting.RecipeType

class UnlimitedBlastFurnaceContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractUnlimitedFurnaceContainer("shared-unlimited-furnace-blasting-${name}", RecipeType.BLASTING)