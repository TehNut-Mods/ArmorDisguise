package info.tehnut.armordisguise.mixin.client;

import info.tehnut.armordisguise.Helper;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Modifies the stack obtained for rendering on the player. If a trinket "overlay" exists, we want to render that instead
// of the proper armor.
@Mixin(ArmorFeatureRenderer.class)
public class MixinArmorFeatureRenderer<T extends LivingEntity> {

    @Redirect(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack armordisguise$getOverlayArmor(T entity, EquipmentSlot slot) {
        if (!(entity instanceof PlayerEntity))
            return entity.getEquippedStack(slot);

        return Helper.getOverlayStack((PlayerEntity) entity, slot);
    }
}
