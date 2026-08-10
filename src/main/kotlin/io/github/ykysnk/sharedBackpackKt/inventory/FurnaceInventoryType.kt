package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.item.crafting.RecipeType

enum class FurnaceInventoryType(val value: RecipeType<*>) {
    Smelting(RecipeType.SMELTING),
    Blasting(RecipeType.BLASTING),
    Smoking(RecipeType.SMOKING)
}