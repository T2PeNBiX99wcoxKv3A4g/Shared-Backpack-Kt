package io.github.ykysnk.sharedBackpackKt.inventory

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractFurnaceMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.RecipeBookType
import net.minecraft.world.item.crafting.RecipePropertySet
import net.minecraft.world.item.crafting.RecipeType

@Suppress("unused")
class CustomFurnaceMenu : AbstractFurnaceMenu {
    constructor(containerId: Int, playerInventory: Inventory) : super(
        MenuType.FURNACE,
        RecipeType.SMELTING,
        RecipePropertySet.FURNACE_INPUT,
        RecipeBookType.FURNACE,
        containerId,
        playerInventory
    )

    constructor(
        containerId: Int,
        playerInventory: Inventory,
        furnaceContainer: Container?,
        furnaceData: ContainerData?
    ) : super(
        MenuType.FURNACE,
        RecipeType.SMELTING,
        RecipePropertySet.FURNACE_INPUT,
        RecipeBookType.FURNACE,
        containerId,
        playerInventory,
        furnaceContainer,
        furnaceData
    ) {
        furnaceContainer?.startOpen(playerInventory.player)
    }

    override fun removed(player: Player) {
        super.removed(player)
        container.stopOpen(player)
    }
}