package io.github.yky.sharedBackpackKt.inventory

import com.google.common.collect.Lists
import com.mojang.serialization.Codec
import io.github.yky.sharedBackpackKt.inventory.FurnaceInventoryType.*
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Blocks
import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.FuelRegistry
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.recipe.*
import net.minecraft.recipe.input.SingleStackRecipeInput
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.screen.PropertyDelegate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import java.util.function.Consumer

abstract class AbstractFurnaceInventory(
    private val player: PlayerEntity, fileName: String, furnaceType: FurnaceInventoryType
) : AbstractBackpackInventory(fileName, 3), RecipeUnlocker {
    companion object {
        private val CODEC: Codec<Map<RegistryKey<Recipe<*>>, Int>> = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT)

        private fun canAcceptRecipeOutput(
            dynamicRegistryManager: DynamicRegistryManager,
            recipe: RecipeEntry<out AbstractCookingRecipe?>?,
            input: SingleStackRecipeInput,
            inventory: DefaultedList<ItemStack>,
            maxCount: Int
        ): Boolean {
            if (!inventory[0].isEmpty && recipe != null) {
                val itemStack = recipe.value()!!.craft(input, dynamicRegistryManager)
                if (itemStack.isEmpty) {
                    return false
                } else {
                    val itemStack2 = inventory[2]
                    return if (itemStack2.isEmpty) {
                        true
                    } else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
                        false
                    } else {
                        if (itemStack2.count < maxCount && itemStack2.count < itemStack2.maxCount) true else itemStack2.count < itemStack.maxCount
                    }
                }
            } else {
                return false
            }
        }

        private fun craftRecipe(
            dynamicRegistryManager: DynamicRegistryManager,
            recipe: RecipeEntry<out AbstractCookingRecipe?>?,
            input: SingleStackRecipeInput,
            inventory: DefaultedList<ItemStack>,
            maxCount: Int
        ): Boolean {
            if (recipe != null && canAcceptRecipeOutput(dynamicRegistryManager, recipe, input, inventory, maxCount)) {
                val itemStack = inventory[0]
                val itemStack2 = recipe.value()!!.craft(input, dynamicRegistryManager)
                val itemStack3 = inventory[2]
                if (itemStack3.isEmpty) {
                    inventory[2] = itemStack2.copy()
                } else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
                    itemStack3.increment(1)
                }

                if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !inventory[1].isEmpty && inventory[1].isOf(Items.BUCKET)) {
                    inventory[1] = ItemStack(Items.WATER_BUCKET)
                }

                itemStack.decrement(1)
                return true
            } else {
                return false
            }
        }

        private fun dropExperience(world: ServerWorld, pos: Vec3d, multiplier: Int, experience: Float) {
            var i = MathHelper.floor(multiplier * experience)
            val f = MathHelper.fractionalPart(multiplier * experience)
            if (f != 0.0f && Math.random() < f) i++

            ExperienceOrbEntity.spawn(world, pos, i)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected val world: ServerWorld?
        get() {
            if (player.world is ServerWorld) return player.world as ServerWorld
            return null
        }

    private var recipesUsed: Reference2IntOpenHashMap<RegistryKey<Recipe<*>>>? =
        Reference2IntOpenHashMap<RegistryKey<Recipe<*>>>()

    // TODO: Maybe one day fix this shit
    private var matchGetter = when (furnaceType) {
        Normal -> ServerRecipeManager.createCachedMatchGetter(RecipeType.SMELTING)
        Blast -> ServerRecipeManager.createCachedMatchGetter(RecipeType.BLASTING)
        Smoker -> ServerRecipeManager.createCachedMatchGetter(RecipeType.SMOKING)
    }

    var litTimeRemaining: Int = 0
    var litTotalTime: Int = 0
    var cookingTimeSpent: Int = 0
    var cookingTotalTime: Int = 0

    val propertyDelegate: PropertyDelegate = object : PropertyDelegate {
        override fun get(index: Int): Int {
            return when (index) {
                0 -> litTimeRemaining
                1 -> litTotalTime
                2 -> cookingTimeSpent
                3 -> cookingTotalTime
                else -> 0
            }
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> litTimeRemaining = value
                1 -> litTotalTime = value
                2 -> cookingTimeSpent = value
                3 -> cookingTotalTime = value
            }
        }

        override fun size() = 4
    }

    init {
        ServerTickEvents.END_WORLD_TICK.register(::tick)
    }

    private fun isBurning() = litTimeRemaining > 0

    fun tick(world: ServerWorld) {
        val isBurning = isBurning()
        var isChanged = false
        if (isBurning()) litTimeRemaining--

        val itemStack = inventory[1]
        val itemStack2 = inventory[0]
        val bl3 = !itemStack2.isEmpty
        val bl4 = !itemStack.isEmpty
        if (isBurning() || bl4 && bl3) {
            val singleStackRecipeInput = SingleStackRecipeInput(itemStack2)
            val recipeEntry = if (bl3) {
                matchGetter.getFirstMatch(singleStackRecipeInput, world).orElse(null)
            } else null

            val i = maxCountPerStack
            if (!isBurning() && canAcceptRecipeOutput(
                    world.registryManager, recipeEntry, singleStackRecipeInput, inventory, i
                )
            ) {
                litTimeRemaining = getFuelTime(world.fuelRegistry, itemStack)
                litTotalTime = litTimeRemaining
                if (isBurning()) {
                    isChanged = true
                    if (bl4) {
                        val item = itemStack.item
                        itemStack.decrement(1)
                        if (itemStack.isEmpty) {
                            inventory[1] = item.recipeRemainder
                        }
                    }
                }
            }

            if (isBurning() && canAcceptRecipeOutput(
                    world.registryManager, recipeEntry, singleStackRecipeInput, inventory, i
                )
            ) {
                cookingTimeSpent++
                if (cookingTimeSpent == cookingTotalTime) {
                    cookingTimeSpent = 0
                    cookingTotalTime = getCookTime(world)
                    if (craftRecipe(world.registryManager, recipeEntry, singleStackRecipeInput, inventory, i)) {
                        lastRecipe = recipeEntry
                    }

                    isChanged = true
                }
            } else {
                cookingTimeSpent = 0
            }
        } else if (!isBurning() && cookingTimeSpent > 0) {
            cookingTimeSpent = MathHelper.clamp(cookingTimeSpent - 2, 0, cookingTotalTime)
        }

        if (isBurning != isBurning()) isChanged = true
        if (isChanged) markDirty()
    }

    private fun getCookTime(world: ServerWorld): Int {
        val singleStackRecipeInput = SingleStackRecipeInput(getStack(0))
        return matchGetter.getFirstMatch(singleStackRecipeInput, world)
            .map { (it.value() as AbstractCookingRecipe).cookingTime }.orElse(200) as Int
    }

    protected open fun getFuelTime(fuelRegistry: FuelRegistry, stack: ItemStack?): Int {
        return fuelRegistry.getFuelTicks(stack)
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        val itemStack = inventory[slot]
        val bl = !stack.isEmpty && ItemStack.areItemsAndComponentsEqual(itemStack, stack)
        inventory[slot] = stack
        stack.capCount(getMaxCount(stack))
        if (slot == 0 && !bl && world is ServerWorld) {
            cookingTotalTime = getCookTime(world!!)
            cookingTimeSpent = 0
            markDirty()
        }
    }

    override fun isValid(slot: Int, stack: ItemStack): Boolean {
        if (slot == 2) {
            return false
        } else if (slot != 1) {
            return true
        } else {
            val itemStack = inventory[1]
            if (world == null) return false
            return world!!.fuelRegistry.isFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET)
        }
    }

    override fun setLastRecipe(recipe: RecipeEntry<*>?) {
        if (recipe != null) {
            val registryKey = recipe.id()
            recipesUsed?.addTo(registryKey, 1)
        }
    }

    override fun getLastRecipe(): RecipeEntry<*>? {
        return null
    }

    override fun unlockLastRecipe(player: PlayerEntity?, ingredients: List<ItemStack?>?) {
    }

    fun dropExperienceForRecipesUsed(player: ServerPlayerEntity) {
        val list = getRecipesUsedAndDropExperience(player.serverWorld, player.pos)
        player.unlockRecipes(list)

        for (recipeEntry in list) {
            player.onRecipeCrafted(recipeEntry, inventory)
        }

        recipesUsed?.clear()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getRecipesUsedAndDropExperience(world: ServerWorld, pos: Vec3d): List<RecipeEntry<*>> {
        val list: MutableList<RecipeEntry<*>> = Lists.newArrayList()

        for (entry in recipesUsed?.reference2IntEntrySet()!!) {
            world.recipeManager[entry.key].ifPresent(
                Consumer<RecipeEntry<*>> { recipe: RecipeEntry<*> ->
                    list.add(recipe)
                    dropExperience(world, pos, entry.intValue, (recipe.value() as AbstractCookingRecipe).experience)
                })
        }

        return list
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override fun readNbt(nbt: NbtCompound, registries: WrapperLookup?) {
        Inventories.readNbt(nbt, inventory, registries)
        cookingTimeSpent = nbt.getShort("cooking_time_spent", 0.toShort()).toInt()
        cookingTotalTime = nbt.getShort("cooking_total_time", 0.toShort()).toInt()
        litTimeRemaining = nbt.getShort("lit_time_remaining", 0.toShort()).toInt()
        litTotalTime = nbt.getShort("lit_total_time", 0.toShort()).toInt()
        recipesUsed?.clear()
        recipesUsed?.putAll(
            nbt.get("RecipesUsed", CODEC).orElse(java.util.Map.of()) as Map<out RegistryKey<Recipe<*>>, Int>
        )
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override fun writeNbt(nbt: NbtCompound, registries: WrapperLookup?) {
        nbt.putShort("cooking_time_spent", cookingTimeSpent.toShort())
        nbt.putShort("cooking_total_time", cookingTotalTime.toShort())
        nbt.putShort("lit_time_remaining", litTimeRemaining.toShort())
        nbt.putShort("lit_total_time", litTotalTime.toShort())
        Inventories.writeNbt(nbt, inventory, registries)
        nbt.put("RecipesUsed", CODEC, recipesUsed)
    }
}