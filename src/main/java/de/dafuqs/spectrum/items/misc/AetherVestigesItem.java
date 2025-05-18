package de.dafuqs.spectrum.items.misc;

import de.dafuqs.spectrum.api.render.SlotBackgroundEffectProvider;
import de.dafuqs.spectrum.items.ItemWithTooltip;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AetherVestigesItem extends ItemWithTooltip implements SlotBackgroundEffectProvider {

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
