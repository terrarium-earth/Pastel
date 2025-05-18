package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.components.PairedItemComponent;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface SplittableItem {
	
	ItemStack getSplitResult(ServerPlayer player, ItemStack parent);
	
	boolean canSplit(ServerPlayer player, InteractionHand activeHand, ItemStack stack);
	
	default void sign(ServerPlayer player, ItemStack stack) {
		stack.set(SpectrumDataComponentTypes.PAIRED_ITEM, new PairedItemComponent(player.level().getGameTime() + player.getUUID().getMostSignificantBits()));
	}
	
	void playSound(ServerPlayer player);
}
