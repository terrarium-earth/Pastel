package earth.terrarium.pastel.api.item;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

public interface PickBlockActivated {
    // WILL be on client, be warned!
    void onPickBlock(ItemStack stack, LocalPlayer player);
}
