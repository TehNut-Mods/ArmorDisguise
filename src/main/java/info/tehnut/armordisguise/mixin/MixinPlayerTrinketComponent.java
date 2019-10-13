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


// lol
@Mixin(PlayerTrinketComponent.class)
public abstract class MixinPlayerTrinketComponent {

    @Shadow
    private PlayerEntity player;

    @Shadow
    public abstract Inventory getInventory();

    @Inject(method = "equip", at = @At("HEAD"), cancellable = true, remap = false)
    public void tad$equip(ItemStack stack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (!(stack.getItem() instanceof ArmorItem))
            return;

        ArmorItem item = (ArmorItem) stack.getItem();
        callbackInfoReturnable.setReturnValue(Helper.handleEquip(stack, item, player, getInventory()));
    }
}
