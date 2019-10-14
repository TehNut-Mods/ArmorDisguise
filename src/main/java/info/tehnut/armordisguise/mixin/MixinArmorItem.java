package info.tehnut.armordisguise.mixin;

import dev.emi.trinkets.api.ITrinket;
import info.tehnut.armordisguise.ArmorDisguise;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Applies the ITrinket interface to all armors (vanilla and modded) so that we have full mod compatibility.
@Mixin(ArmorItem.class)
public class MixinArmorItem implements ITrinket {

    @Shadow
    @Final
    protected EquipmentSlot slot;

    @Override
    public boolean canWearInSlot(String group, String slot) {
        if (!group.equals(this.slot.getName()))
            return false;

        if (ArmorDisguise.GROUPS.contains(group))
            return "overlay".equals(slot);

        return false;
    }
}
