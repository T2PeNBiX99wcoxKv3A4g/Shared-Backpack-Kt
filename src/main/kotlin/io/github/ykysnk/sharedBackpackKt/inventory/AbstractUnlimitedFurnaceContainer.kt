package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType

abstract class AbstractUnlimitedFurnaceContainer(fileName: String, recipeType: RecipeType<out AbstractCookingRecipe>) :
    AbstractFurnaceContainer(fileName, recipeType) {
    override fun getMaxStackSize(): Int {
        return Int.MAX_VALUE
    }

    override val dataAccess: ContainerData = object : ContainerData {
        override fun get(index: Int): Int {
            return when (index) {
                0 -> cookingTotalTime - cookingProgress
                1 -> cookingTotalTime
                2 -> cookingProgress
                3 -> cookingTotalTime
                else -> 0
            }
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                2 -> cookingProgress = value
                3 -> cookingTotalTime = value
            }
        }

        override fun getCount() = 4
    }

    override fun tick(server: MinecraftServer) {
        var isChanged = false
        if (openDelay?.let { it > 0 } == true)
            openDelay = openDelay?.minus(1)

        val itemStack = items[0]
        val bl3 = !itemStack.isEmpty
        val recipe: Recipe<*>? =
            if (bl3) quickCheck.getRecipeFor(this, level ?: server.overworld()).orElse(null) else null
        val i = maxStackSize

        if (canBurn(server.registryAccess(), recipe, items, i)) {
            cookingProgress++
            if (cookingProgress == cookingTotalTime) {
                cookingProgress = 0
                cookingTotalTime = getTotalCookTime(level ?: server.overworld())
                if (burn(server.registryAccess(), recipe, items, i)) {
                    recipeUsed = recipe
                }

                isChanged = true
            }
        } else {
            cookingProgress = 0
        }

        if (isChanged) setChanged()
        if (openDelay?.let { it <= 0 } == true && cookingProgress <= 0 && viewerCount <= 0) {
            saveNbt()
            viewerCount = 0
            FurnaceTickHandler.unregister(this)
            onNoPlayersOpen()
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override fun loadAllItems(compoundTag: CompoundTag) {
        ContainerHelper.loadAllItems(compoundTag, items)
        cookingProgress = compoundTag.getShort("CookTime").toInt()
        cookingTotalTime = compoundTag.getShort("CookTimeTotal").toInt()
        if (recipesUsed == null) return
        val compoundTag2 = compoundTag.getCompound("RecipesUsed")
        for (string in compoundTag2.allKeys) {
            recipesUsed!!.put(ResourceLocation(string), compoundTag2.getInt(string))
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override fun saveAllItems(compoundTag: CompoundTag) {
        compoundTag.putShort("CookTime", cookingProgress.toShort())
        compoundTag.putShort("CookTimeTotal", cookingTotalTime.toShort())
        ContainerHelper.saveAllItems(compoundTag, items)
        if (recipesUsed == null) return
        val compoundTag2 = CompoundTag()
        recipesUsed!!.forEach { (resourceLocation, integer) ->
            compoundTag2.putInt(
                resourceLocation.toString(),
                integer!!
            )
        }
        compoundTag.put("RecipesUsed", compoundTag2)
    }

    override fun stopOpen(player: Player) {
        viewerCount--
        saveNbt()
        if (cookingProgress <= 0 && viewerCount <= 0) {
            viewerCount = 0
            FurnaceTickHandler.unregister(this)
            onNoPlayersOpen()
        }
    }
}