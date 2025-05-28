package earth.terrarium.pastel.api.damage_type;

import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface StackTracking {
	
	Optional<ItemStack> spectrum$getTrackedStack();
	
	void spectrum$setTrackedStack(ItemStack stack);
}
