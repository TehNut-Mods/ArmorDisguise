package info.tehnut.armordisguise.mixin;

import dev.emi.trinkets.api.PlayerTrinketComponent;
import info.tehnut.armordisguise.Helper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Since Trinkets hooks shift-click equipping at the top, armor items now prioritize trinket slots over vanilla slots. Our
// hack here reverts that. Shift clicking armor will now prioritize vanilla slots, but will also go into trinket slots when
// the vanilla slot contains an item.
@Mixin(PlayerTrinketComponent.class)
public abstract class MixinPlayerTrinketComponent {

    @Shadow(remap = false)
    private PlayerEntity player;

    @Shadow(remap = false)
    public abstract Inventory getInventory();

    @Inject(method = "equip", at = @At("HEAD"), cancellable = true, remap = false)
    public void armordisguise$equip(ItemStack stack, boolean shiftClick, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (!(stack.getItem() instanceof ArmorItem))
            return;

        ArmorItem item = (ArmorItem) stack.getItem();
        callbackInfoReturnable.setReturnValue(Helper.handleEquip(stack, item, player, getInventory()));
    }
}
