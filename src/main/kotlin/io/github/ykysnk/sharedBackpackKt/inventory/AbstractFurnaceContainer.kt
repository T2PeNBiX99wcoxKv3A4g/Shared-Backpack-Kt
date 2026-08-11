package io.github.ykysnk.sharedBackpackKt.inventory

import com.google.common.collect.Lists
import com.mojang.serialization.Codec
import io.github.ykysnk.sharedBackpackKt.Utils
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.Mth
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.ExperienceOrb
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.RecipeCraftingHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.FuelValues
import net.minecraft.world.phys.Vec3
import java.util.function.Consumer

abstract class AbstractFurnaceContainer(fileName: String, recipeType: RecipeType<out AbstractCookingRecipe>) :
    AbstractBackpackContainer(fileName, 3), RecipeCraftingHolder {
    companion object {
        private val CODEC: Codec<Map<ResourceKey<Recipe<*>>, Int>> = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT)

        @JvmStatic
        protected fun canBurn(
            registryAccess: RegistryAccess,
            recipeHolder: RecipeHolder<out AbstractCookingRecipe>?,
            input: SingleRecipeInput,
            inventory: NonNullList<ItemStack>,
            maxCount: Int
        ): Boolean {
            if (!inventory[0].isEmpty && recipeHolder != null) {
                val itemStack = recipeHolder.value().assemble(input, registryAccess)
                if (itemStack.isEmpty) {
                    return false
                } else {
                    val itemStack2 = inventory[2]
                    return itemStack2.isEmpty || ItemStack.isSameItemSameComponents(
                        itemStack2,
                        itemStack
                    ) && (itemStack2.count < maxCount && itemStack2.count < itemStack2.maxStackSize || itemStack2.count < itemStack.maxStackSize)
                }
            } else {
                return false
            }
        }

        @JvmStatic
        protected fun burn(
            registryAccess: RegistryAccess,
            recipeHolder: RecipeHolder<out AbstractCookingRecipe>?,
            singleRecipeInput: SingleRecipeInput,
            nonNullList: NonNullList<ItemStack>,
            maxCount: Int
        ): Boolean {
            if (recipeHolder != null && canBurn(
                    registryAccess,
                    recipeHolder,
                    singleRecipeInput,
                    nonNullList,
                    maxCount
                )
            ) {
                val itemStack = nonNullList[0]
                val itemStack2 = recipeHolder.value().assemble(singleRecipeInput, registryAccess)
                val itemStack3 = nonNullList[2]
                if (itemStack3.isEmpty) {
                    nonNullList[2] = itemStack2.copy()
                } else if (ItemStack.isSameItemSameComponents(itemStack3, itemStack2)) {
                    itemStack3.grow(1)
                }

                if (itemStack.`is`(Blocks.WET_SPONGE.asItem()) && !nonNullList[1].isEmpty && nonNullList[1].`is`(Items.BUCKET)) {
                    nonNullList[1] = ItemStack(Items.WATER_BUCKET)
                }

                itemStack.shrink(1)
                return true
            } else {
                return false
            }
        }

        protected fun createExperience(world: ServerLevel, pos: Vec3, multiplier: Int, experience: Float) {
            var i = Mth.floor(multiplier * experience)
            val f = Mth.frac(multiplier * experience)
            if (f != 0.0f && Math.random() < f) i++

            ExperienceOrb.award(world, pos, i)
        }
    }

    var player: Player? = null
        internal set

    @Suppress("MemberVisibilityCanBePrivate")
    protected val level: ServerLevel?
        get() = player?.level() as? ServerLevel ?: Utils.Server?.overworld()

    protected var recipesUsed: Reference2IntOpenHashMap<ResourceKey<Recipe<*>>>? = null
    protected var openDelay: Int? = null

    var litTimeRemaining: Int = 0
    var litTotalTime: Int = 0
    var cookingTimer: Int = 0
    var cookingTotalTime: Int = 0

    open val propertyDelegate: ContainerData = object : ContainerData {
        override fun get(index: Int): Int {
            return when (index) {
                0 -> litTimeRemaining
                1 -> litTotalTime
                2 -> cookingTimer
                3 -> cookingTotalTime
                else -> 0
            }
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> litTimeRemaining = value
                1 -> litTotalTime = value
                2 -> cookingTimer = value
                3 -> cookingTotalTime = value
            }
        }

        override fun getCount() = 4
    }

    protected val quickCheck: RecipeManager.CachedCheck<SingleRecipeInput, out AbstractCookingRecipe> =
        RecipeManager.createCheck(recipeType)

    init {
        FurnaceTickHandler.register(this)
    }

    override fun onPreInit() {
        recipesUsed = Reference2IntOpenHashMap()
    }

    protected open fun isLit() = litTimeRemaining > 0

    open fun tick(server: MinecraftServer) {
        val isBurning = isLit()
        var isChanged = false
        if (isLit()) litTimeRemaining--
        if (openDelay?.let { it > 0 } == true)
            openDelay = openDelay?.minus(1)

        val itemStack = items[1]
        val itemStack2 = items[0]
        val bl3 = !itemStack2.isEmpty
        val bl4 = !itemStack.isEmpty
        if (isLit() || bl4 && bl3) {
            val singleRecipeInput = SingleRecipeInput(itemStack2)
            val recipeHolder = if (bl3) {
                quickCheck.getRecipeFor(singleRecipeInput, level ?: server.overworld()).orElse(null)
            } else null

            val i = maxStackSize
            if (!isLit() && canBurn(server.registryAccess(), recipeHolder, singleRecipeInput, items, i)) {
                litTimeRemaining = getBurnDuration(server.fuelValues(), itemStack)
                litTotalTime = litTimeRemaining
                if (isLit()) {
                    isChanged = true
                    if (bl4) {
                        val item = itemStack.item
                        itemStack.shrink(1)
                        if (itemStack.isEmpty) {
                            items[1] = item.craftingRemainder
                        }
                    }
                }
            }

            if (isLit() && canBurn(server.registryAccess(), recipeHolder, singleRecipeInput, items, i)) {
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
        } else if (!isLit() && cookingTimer > 0) {
            cookingTimer = Mth.clamp(cookingTimer - 2, 0, cookingTotalTime)
        }

        if (isBurning != isLit()) isChanged = true
        if (isChanged) setChanged()
        if (openDelay?.let { it <= 0 } == true && !isLit() && cookingTimer <= 0 && viewerCount <= 0) {
            saveNbt()
            viewerCount = 0
            FurnaceTickHandler.unregister(this)
            onNoPlayersOpen()
        }
    }

    protected open fun getTotalCookTime(serverLevel: ServerLevel): Int {
        val singleRecipeInput = SingleRecipeInput(getItem(0))
        val integer = quickCheck.getRecipeFor(singleRecipeInput, serverLevel)
            .map<Int?> { recipeHolder -> recipeHolder.value().cookingTime() }
            .orElse(200)!!
        return integer
    }

    protected open fun getBurnDuration(fuelValues: FuelValues, stack: ItemStack): Int {
        return fuelValues.burnDuration(stack)
    }

    override fun setItem(slot: Int, stack: ItemStack) {
        val itemStack = items[slot]
        val bl = !stack.isEmpty && ItemStack.isSameItemSameComponents(itemStack, stack)
        items[slot] = stack
        stack.limitSize(getMaxStackSize(stack))
        if (slot == 0 && !bl && level is ServerLevel) {
            cookingTotalTime = getTotalCookTime(level!!)
            cookingTimer = 0
            setChanged()
        }
    }

    override fun canPlaceItem(slot: Int, stack: ItemStack): Boolean {
        if (slot == 2) {
            return false
        } else if (slot != 1) {
            return true
        } else {
            val itemStack = items[1]
            return level != null && (level!!.fuelValues().isFuel(stack) || stack.`is`(Items.BUCKET) && !itemStack.`is`(
                Items.BUCKET
            ))
        }
    }

    override fun setRecipeUsed(recipeHolder: RecipeHolder<*>?) {
        if (recipeHolder != null) {
            val resourceKey = recipeHolder.id()
            recipesUsed?.addTo(resourceKey, 1)
        }
    }

    override fun getRecipeUsed(): RecipeHolder<*>? {
        return null
    }

    override fun awardUsedRecipes(player: Player, ingredients: List<ItemStack>) {
    }

    open fun awardUsedRecipesAndPopExperience(player: ServerPlayer) {
        val list = getRecipesToAwardAndPopExperience(player.serverLevel(), player.position())
        player.awardRecipes(list)

        for (recipeEntry in list) {
            player.triggerRecipeCrafted(recipeEntry, items)
        }

        recipesUsed?.clear()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun getRecipesToAwardAndPopExperience(serverLevel: ServerLevel, pos: Vec3): List<RecipeHolder<*>> {
        val list: MutableList<RecipeHolder<*>> = Lists.newArrayList()

        for (entry in recipesUsed?.reference2IntEntrySet()!!) {
            serverLevel.recipeAccess().byKey(entry.key).ifPresent(Consumer { recipeHolder ->
                list.add(recipeHolder)
                createExperience(
                    serverLevel,
                    pos,
                    entry.intValue,
                    (recipeHolder.value() as AbstractCookingRecipe).experience()
                )
            })
        }

        return list
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override fun loadAllItems(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) {
        ContainerHelper.loadAllItems(compoundTag, items, registries)
        cookingTimer = compoundTag.getShortOr("cooking_time_spent", 0.toShort()).toInt()
        cookingTotalTime = compoundTag.getShortOr("cooking_total_time", 0.toShort()).toInt()
        litTimeRemaining = compoundTag.getShortOr("lit_time_remaining", 0.toShort()).toInt()
        litTotalTime = compoundTag.getShortOr("lit_total_time", 0.toShort()).toInt()
        recipesUsed?.clear()
        recipesUsed?.putAll(
            compoundTag.read("RecipesUsed", CODEC).orElse(java.util.Map.of()) as Map<out ResourceKey<Recipe<*>>, Int>
        )
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override fun saveAllItems(compoundTag: CompoundTag, registries: RegistryAccess.Frozen) {
        compoundTag.putShort("cooking_time_spent", cookingTimer.toShort())
        compoundTag.putShort("cooking_total_time", cookingTotalTime.toShort())
        compoundTag.putShort("lit_time_remaining", litTimeRemaining.toShort())
        compoundTag.putShort("lit_total_time", litTotalTime.toShort())
        ContainerHelper.saveAllItems(compoundTag, items, registries)
        if (recipesUsed == null) return
        compoundTag.store("RecipesUsed", CODEC, recipesUsed!!)
    }

    override fun startOpen(player: Player) {
        super.startOpen(player)
        openDelay = 100
    }

    override fun stopOpen(player: Player) {
        viewerCount--
        saveNbt()
        if (!isLit() && cookingTimer <= 0 && viewerCount <= 0) {
            viewerCount = 0
            FurnaceTickHandler.unregister(this)
            onNoPlayersOpen()
        }
    }
}