package io.github.ykysnk.sharedBackpackKt.inventory

import io.github.ykysnk.sharedBackpackKt.config.ConfigManager
import net.minecraft.core.RegistryAccess
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.ContainerHelper
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.SingleRecipeInput

abstract class AbstractUnlimitedFurnaceContainer(fileName: String, recipeType: RecipeType<out AbstractCookingRecipe>) :
    AbstractFurnaceContainer(fileName, recipeType) {
    override val propertyDelegate: ContainerData = object : ContainerData {
        override fun get(index: Int): Int {
            return when (index) {
                0 -> cookingTotalTime - cookingTimer
                1 -> cookingTotalTime
                2 -> cookingTimer
                3 -> cookingTotalTime
                else -> 0
            }
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                2 -> cookingTimer = value
                3 -> cookingTotalTime = value
            }
        }

        override fun getCount() = 4
    }

    override fun tick(server: MinecraftServer) {
        var isChanged = false
        val itemStack = items[0]
        val bl3 = !itemStack.isEmpty
        val singleRecipeInput = SingleRecipeInput(itemStack)
        val recipeHolder = if (bl3) {
            quickCheck.getRecipeFor(singleRecipeInput, level ?: server.overworld()).orElse(null)
        } else null
        val i = maxStackSize
        canBurn = canBurn(server.registryAccess(), recipeHolder, singleRecipeInput, items, i)

        if (canBurn) {
            cookingTimer++
            if (cookingTimer == cookingTotalTime) {
                cookingTimer = 0
                cookingTotalTime = getTotalCookTime(level ?: server.overworld())
                if (burn(server.registryAccess(), recipeHolder, singleRecipeInput, items, i)) {
                    recipeUsed = recipeHolder
                }

                isChanged = true
            }
        } else {
            cookingTimer = 0
        }

        if (isChanged) setChanged()
    }

    override fun getTotalCookTime(serverLevel: ServerLevel): Int {
        val singleRecipeInput = SingleRecipeInput(getItem(0))
        val integer = quickCheck.getRecipeFor(singleRecipeInput, serverLevel)
            .map<Int?> { recipeHolder -> recipeHolder.value().cookingTime() }
            .orElse(200)!!
        return (integer / (ConfigManager.config.general.unlimitedFurnaceMultiplier).coerceAtLeast(1)).coerceAtLeast(1)
    }

    override fun loadAllItems(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) {
        ContainerHelper.loadAllItems(compoundTag, items, registries)
        cookingTimer = compoundTag.getShortOr("cooking_time_spent", 0.toShort()).toInt()
        cookingTotalTime = compoundTag.getShortOr("cooking_total_time", 0.toShort()).toInt()
        recipesUsed?.clear()
        recipesUsed?.putAll(
            compoundTag.read("RecipesUsed", CODEC).orElse(java.util.Map.of()) as Map<out ResourceKey<Recipe<*>>, Int>
        )
    }

    override fun saveAllItems(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) {
        compoundTag.putShort("cooking_time_spent", cookingTimer.toShort())
        compoundTag.putShort("cooking_total_time", cookingTotalTime.toShort())
        ContainerHelper.saveAllItems(compoundTag, items, registries)
        if (recipesUsed == null) return
        compoundTag.store("RecipesUsed", CODEC, recipesUsed!!)
    }

    override fun isNoPlayersOpen() = !canBurn && cookingTimer <= 0 && viewerCount <= 0
}