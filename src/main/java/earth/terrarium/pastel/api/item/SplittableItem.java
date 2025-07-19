package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.components.PairedItemComponent;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface SplittableItem {
	
	ItemStack getSplitResult(ServerPlayer player, ItemStack parent);
	
	boolean canSplit(ServerPlayer player, InteractionHand activeHand, ItemStack stack);
	
	default void sign(ServerPlayer player, ItemStack stack) {
		stack.set(PastelDataComponentTypes.PAIRED_ITEM, new PairedItemComponent(player.level().getGameTime() + player.getUUID().getMostSignificantBits()));
	}
	
	void playSound(ServerPlayer player);
}
