package info.tehnut.armordisguise.mixin;

import dev.emi.trinkets.api.ITrinket;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorItem.class)
public class MixinArmorItem implements ITrinket {

    @Shadow
    @Final
    protected EquipmentSlot slot;

    @Override
    public boolean canWearInSlot(String group, String slot) {
        if (!group.equals(this.slot.getName()))
            return false;

        switch (group) {
            case "head":
            case "chest":
            case "legs":
            case "feet":
                return slot.equals("overlay");
        }

        return false;
    }
}
