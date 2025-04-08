package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

public interface SplittableItem {
	
	ItemStack getSplitResult(ServerPlayerEntity player, ItemStack parent);
	
	boolean canSplit(ServerPlayerEntity player, Hand activeHand, ItemStack stack);
	
	default void sign(ServerPlayerEntity player, ItemStack stack) {
		stack.set(SpectrumDataComponentTypes.PAIRED_ITEM, new PairedItemComponent(player.getWorld().getTime() + player.getUuid().getMostSignificantBits()));
	}
	
	void playSound(ServerPlayerEntity player);
}
