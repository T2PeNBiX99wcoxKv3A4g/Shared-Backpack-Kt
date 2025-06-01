package io.github.yky.sharedBackpackKt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.yky.sharedBackpackKt.inventory.AbstractFurnaceInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FurnaceOutputSlot.class)
abstract class FurnaceOutputSlotMixin extends Slot {
    FurnaceOutputSlotMixin(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @WrapOperation(method = "onCrafted(Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onCraftByPlayer(Lnet/minecraft/entity/player/PlayerEntity;I)V"))
    private void onCrafted(ItemStack instance, PlayerEntity player, int amount, Operation<Void> original) {
        original.call(instance, player, amount);
        if (player instanceof ServerPlayerEntity serverPlayerEntity && inventory instanceof AbstractFurnaceInventory furnaceInventory)
            furnaceInventory.dropExperienceForRecipesUsed(serverPlayerEntity);
    }
}
