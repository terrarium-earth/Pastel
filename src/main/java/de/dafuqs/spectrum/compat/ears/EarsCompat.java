package de.dafuqs.spectrum.compat.ears;

import com.unascribed.ears.api.EarsAnchorPart;
import com.unascribed.ears.api.EarsStateType;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;
import com.unascribed.ears.api.registry.EarsStateOverriderRegistry;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class EarsCompat {
    public static void register() {
        EarsInhibitorRegistry.register(SpectrumCommon.MOD_ID, (part, peer) -> {
            Player player = (Player) peer;
            if (part.isAnchoredTo(EarsAnchorPart.TORSO) && EarsStateOverriderRegistry.isActive(EarsStateType.WEARING_CHESTPLATE, peer, true).getValue()) {
                Item equippedItem = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
                return equippedItem == SpectrumItems.BEDROCK_CHESTPLATE || equippedItem == SpectrumItems.FEROCIOUS_CHESTPLATE;
            }
            return false;
        });
    }
}
