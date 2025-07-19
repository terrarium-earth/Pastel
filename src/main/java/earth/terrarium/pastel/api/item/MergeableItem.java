package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface MergeableItem {
	
	ItemStack getMergeResult(ServerPlayer player, ItemStack firstHalf, ItemStack secondHalf);
	
	boolean canMerge(ServerPlayer player, ItemStack parent, ItemStack other);
	
	default boolean verify(ItemStack parent, ItemStack other) {
		if (!parent.getEnchantments().equals(other.getEnchantments()))
			return false;
		
		var comp = parent.get(PastelDataComponentTypes.PAIRED_ITEM);
		var otherComp = other.get(PastelDataComponentTypes.PAIRED_ITEM);
		return comp != null && otherComp != null && comp.signature() == otherComp.signature();
	}

	void playSound(ServerPlayer player);
}
