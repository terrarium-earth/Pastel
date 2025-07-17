package earth.terrarium.pastel.items.misc;

import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.items.ItemWithTooltip;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AetherVestigesItem extends ItemWithTooltip implements SlotBackgroundEffect {

	public AetherVestigesItem(Properties settings, String tooltip) {
		super(settings, tooltip);
	}

	@Override
	public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		return SlotEffect.BORDER_FADE;
	}

	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		return 0xFFFFFF;
	}
}
