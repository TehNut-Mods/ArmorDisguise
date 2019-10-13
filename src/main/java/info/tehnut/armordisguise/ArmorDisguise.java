package info.tehnut.armordisguise;

import com.google.common.collect.ImmutableSet;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.util.Set;

public class ArmorDisguise implements ModInitializer {

    public static final String MODID = "armordisguise";
    public static final Set<String> GROUPS = new ImmutableSet.Builder<String>().add(
            "head", "chest", "legs", "feet"
    ).build();

    @Override
    public void onInitialize() {
        GROUPS.forEach(s -> TrinketSlots.addSubSlot(s, "overlay", new Identifier(MODID, "textures/item/empty_trinket_slot_" + s + "_overlay.png")));
    }
}
