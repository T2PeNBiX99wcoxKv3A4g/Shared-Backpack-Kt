package io.github.yky.sharedBackpackKt.inventory

import net.minecraft.recipe.RecipeType

enum class FurnaceInventoryType(val value: RecipeType<*>) {
    Normal(RecipeType.SMELTING),
    Blast(RecipeType.BLASTING),
    Smoker(RecipeType.SMOKING)
}