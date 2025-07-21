package earth.terrarium.pastel.api.block;

import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import net.minecraft.world.level.block.Block;

public interface PedestalVariant {
	
	PedestalTier getRecipeTier();
	
	Block getPedestalBlock();
	
	default boolean isBetterThan(PedestalVariant other) {
		return this.getRecipeTier().ordinal() > other.getRecipeTier().ordinal();
	}
	
}