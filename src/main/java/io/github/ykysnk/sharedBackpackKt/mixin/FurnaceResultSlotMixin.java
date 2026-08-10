package io.github.ykysnk.sharedBackpackKt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.ykysnk.sharedBackpackKt.inventory.AbstractFurnaceContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FurnaceResultSlot.class)
abstract class FurnaceResultSlotMixin extends Slot {
    public FurnaceResultSlotMixin(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @WrapOperation(method = "checkTakeAchievements", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;onCraftedBy(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;I)V"))
    private void checkTakeAchievements(ItemStack instance, Level level, Player player, int i, Operation<Void> original) {
        original.call(instance, level, player, i);
        if (player instanceof ServerPlayer serverPlayer && container instanceof AbstractFurnaceContainer furnaceContainer)
            furnaceContainer.awardUsedRecipesAndPopExperience(serverPlayer);
    }
}
