package info.tehnut.armordisguise;

import dev.emi.trinkets.TrinketsClient;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketSlots;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class Helper {

    public static boolean handleEquip(ItemStack stack, ArmorItem armor, PlayerEntity player, Inventory inventory) {
        ItemStack vanillaStack = player.getEquippedStack(armor.getSlotType());
        // Not exactly safe, but we know these slots exist
        if (vanillaStack.isEmpty()) {
            TrinketSlots.SlotGroup slotGroup = TrinketSlots.slotGroups.stream().filter(g -> g.getName().equals(armor.getSlotType().getName())).findFirst().get();
            player.setEquippedStack(armor.getSlotType(), stack.copy());
            TrinketsClient.lastEquipped = slotGroup;
            TrinketsClient.displayEquipped = 16;
            return true;
        } else {
            if (stack == vanillaStack)
                return false;

            int i = 0;
            for (TrinketSlots.SlotGroup group : TrinketSlots.slotGroups) {
                for (TrinketSlots.Slot slot : group.slots) {
                    if (group.getName().equals(armor.getSlotType().getName()) && slot.getName().equals("overlay")) {
                        ItemStack trinketStack = inventory.getInvStack(i);

                        if (trinketStack.isEmpty()) {
                            inventory.setInvStack(i, stack.copy());
                            TrinketsClient.lastEquipped = group;
                            TrinketsClient.displayEquipped = 16;
                            return true;
                        }
                    }
                    i++;
                }
            }
        }

        return false;
    }

    public static ItemStack getOverlayStack(PlayerEntity player, EquipmentSlot slot) {
        TrinketComponent component = TrinketsApi.getTrinketComponent(player);
        String checkSlot = "";
        switch (slot) {
            case HEAD:
                checkSlot = "head:overlay";
                break;
            case CHEST:
                checkSlot = "chest:overlay";
                break;
            case LEGS:
                checkSlot = "legs:overlay";
                break;
            case FEET:
                checkSlot = "feet:overlay";
                break;
        }

        ItemStack overlay = component.getStack(checkSlot);
        return !overlay.isEmpty() ? overlay : player.getEquippedStack(slot);
    }
}
