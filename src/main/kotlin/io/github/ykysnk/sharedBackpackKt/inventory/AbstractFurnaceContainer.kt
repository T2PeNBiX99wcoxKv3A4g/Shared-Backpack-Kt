package io.github.ykysnk.sharedBackpackKt.inventory

import com.google.common.collect.Lists
import io.github.ykysnk.sharedBackpackKt.Utils
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.Mth
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.ExperienceOrb
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.world.phys.Vec3
import java.util.function.Consumer

abstract class AbstractFurnaceContainer(fileName: String, recipeType: RecipeType<out AbstractCookingRecipe>) :
    AbstractBackpackContainer(fileName, 3), RecipeHolder {
    companion object {
        private fun canBurn(
            registryAccess: RegistryAccess,
            recipe: Recipe<*>?,
            nonNullList: NonNullList<ItemStack>,
            maxCount: Int
        ): Boolean {
            if (!nonNullList[0].isEmpty && recipe != null) {
                val itemStack = recipe.getResultItem(registryAccess)
                return if (itemStack.isEmpty) {
                    false
                } else {
                    val itemStack2 = nonNullList[2]
                    itemStack2.isEmpty || ItemStack.isSameItem(
                        itemStack2,
                        itemStack
                    ) && (itemStack2.count < maxCount && itemStack2.count < itemStack2.maxStackSize || itemStack2.count < itemStack.maxStackSize)
                }
            } else {
                return false
            }
        }

        private fun burn(
            registryAccess: RegistryAccess,
            recipe: Recipe<*>?,
            nonNullList: NonNullList<ItemStack>,
            maxCount: Int
        ): Boolean {
            if (recipe != null && canBurn(registryAccess, recipe, nonNullList, maxCount)) {
                val itemStack = nonNullList[0]
                val itemStack2 = recipe.getResultItem(registryAccess)
                val itemStack3 = nonNullList[2]
                if (itemStack3.isEmpty) {
                    nonNullList[2] = itemStack2.copy()
                } else if (itemStack3.`is`(itemStack2.item)) {
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

        private fun createExperience(world: ServerLevel, pos: Vec3, multiplier: Int, experience: Float) {
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

    private var recipesUsed: Object2IntOpenHashMap<ResourceLocation>? = null
    private var openDelay: Int? = null

    var litTime: Int = 0
    var litDuration: Int = 0
    var cookingProgress: Int = 0
    var cookingTotalTime: Int = 0

    val dataAccess: ContainerData = object : ContainerData {
        override fun get(index: Int): Int {
            return when (index) {
                0 -> litTime
                1 -> litDuration
                2 -> cookingProgress
                3 -> cookingTotalTime
                else -> 0
            }
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> litTime = value
                1 -> litDuration = value
                2 -> cookingProgress = value
                3 -> cookingTotalTime = value
            }
        }

        override fun getCount() = 4
    }

    private val quickCheck: RecipeManager.CachedCheck<Container, out AbstractCookingRecipe> =
        RecipeManager.createCheck(recipeType)

    init {
        FurnaceTickHandler.register(this)
    }

    override fun onPreInit() {
        recipesUsed = Object2IntOpenHashMap()
    }

    private fun isLit() = litTime > 0

    fun tick(server: MinecraftServer) {
        val isBurning = isLit()
        var isChanged = false
        if (isLit()) litTime--
        if (openDelay?.let { it > 0 } == true)
            openDelay = openDelay?.minus(1)

        val itemStack = items[1]
        val itemStack2 = items[0]
        val bl3 = !itemStack2.isEmpty
        val bl4 = !itemStack.isEmpty
        if (isLit() || bl4 && bl3) {
            val recipe: Recipe<*>? =
                if (bl3) quickCheck.getRecipeFor(this, level ?: server.overworld()).orElse(null) else null
            val i = maxStackSize
            if (!isLit() && canBurn(server.registryAccess(), recipe, items, i)) {
                litTime = getBurnDuration(itemStack)
                litDuration = litTime
                if (isLit()) {
                    isChanged = true
                    if (bl4) {
                        val item = itemStack.item
                        itemStack.shrink(1)
                        if (itemStack.isEmpty) {
                            val item2 = item.craftingRemainingItem
                            items[1] = if (item2 == null) ItemStack.EMPTY else ItemStack(item2)
                        }
                    }
                }
            }

            if (isLit() && canBurn(server.registryAccess(), recipe, items, i)) {
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
        } else if (!isLit() && cookingProgress > 0) {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingTotalTime)
        }

        if (isBurning != isLit()) isChanged = true
        if (isChanged) setChanged()
        if (openDelay?.let { it <= 0 } == true && !isLit() && cookingProgress <= 0 && viewerCount <= 0) {
            saveNbt()
            viewerCount = 0
            FurnaceTickHandler.unregister(this)
            onNoPlayersOpen()
        }
    }

    private fun getTotalCookTime(serverLevel: ServerLevel): Int =
        quickCheck.getRecipeFor(this, serverLevel).map<Int?> { obj -> obj.getCookingTime() }.orElse(200)!!

    protected open fun getBurnDuration(stack: ItemStack): Int {
        if (stack.isEmpty) {
            return 0
        } else {
            val item: Item = stack.item
            return AbstractFurnaceBlockEntity.getFuel().getOrDefault(item, 0)
        }
    }

    override fun setItem(slot: Int, stack: ItemStack) {
        val itemStack = items[slot]
        val bl = !stack.isEmpty && ItemStack.isSameItemSameTags(itemStack, stack)
        items[slot] = stack
        if (itemStack.count > this.maxStackSize) {
            itemStack.count = this.maxStackSize
        }
        if (slot == 0 && !bl) {
            cookingTotalTime = getTotalCookTime(level!!)
            cookingProgress = 0
            setChanged()
        }
    }

    override fun canPlaceItem(slot: Int, stack: ItemStack): Boolean {
        if (slot == 2) {
            return false
        } else if (slot != 1) {
            return true
        } else {
            val stack2 = this.items[1]
            return AbstractFurnaceBlockEntity.isFuel(stack) || stack.`is`(Items.BUCKET) && !stack2.`is`(
                Items.BUCKET
            )
        }
    }

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe != null) {
            val resourceKey = recipe.id
            recipesUsed?.addTo(resourceKey, 1)
        }
    }

    override fun getRecipeUsed(): Recipe<*>? {
        return null
    }

    override fun awardUsedRecipes(player: Player, ingredients: List<ItemStack>) {
    }

    fun awardUsedRecipesAndPopExperience(player: ServerPlayer) {
        val list = getRecipesToAwardAndPopExperience(player.serverLevel(), player.position())
        player.awardRecipes(list)

        for (recipeEntry in list) {
            player.triggerRecipeCrafted(recipeEntry, items)
        }

        recipesUsed?.clear()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getRecipesToAwardAndPopExperience(serverLevel: ServerLevel, pos: Vec3): List<Recipe<*>> {
        val list: MutableList<Recipe<*>> = Lists.newArrayList()

        for (entry in recipesUsed?.object2IntEntrySet()!!) {
            serverLevel.recipeManager.byKey(entry.key).ifPresent(Consumer { recipe ->
                list.add(recipe)
                createExperience(
                    serverLevel,
                    pos,
                    entry.intValue,
                    (recipe as AbstractCookingRecipe).getExperience()
                )
            })
        }

        return list
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override fun loadAllItems(compoundTag: CompoundTag) {
        ContainerHelper.loadAllItems(compoundTag, items)
        litTime = compoundTag.getShort("BurnTime").toInt()
        litDuration = getBurnDuration(items[1])
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
        compoundTag.putShort("BurnTime", litTime.toShort())
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

    override fun startOpen(player: Player) {
        super.startOpen(player)
        openDelay = 100
    }

    override fun stopOpen(player: Player) {
        viewerCount--
        saveNbt()
        if (!isLit() && cookingProgress <= 0 && viewerCount <= 0) {
            viewerCount = 0
            FurnaceTickHandler.unregister(this)
            onNoPlayersOpen()
        }
    }
}