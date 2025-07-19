package earth.terrarium.pastel.blocks.pedestal;

import earth.terrarium.pastel.api.block.PedestalVariant;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.world.level.block.Block;

public enum PedestalVariants implements PedestalVariant {
	BASIC_TOPAZ(PedestalTier.BASIC),
	BASIC_AMETHYST(PedestalTier.BASIC),
	BASIC_CITRINE(PedestalTier.BASIC),
	CMY(PedestalTier.SIMPLE),
	ONYX(PedestalTier.ADVANCED),
	MOONSTONE(PedestalTier.COMPLEX);
	
	private final PedestalTier tier;
	
	PedestalVariants(PedestalTier tier) {
		this.tier = tier;
	}
	
	@Override
	public PedestalTier getRecipeTier() {
		return this.tier;
	}
	
	@Override
	public Block getPedestalBlock() {
		switch (this) {
			case BASIC_TOPAZ -> {
                return PastelBlocks.PEDESTAL_BASIC_TOPAZ.get();
			}
			case BASIC_AMETHYST -> {
                return PastelBlocks.PEDESTAL_BASIC_AMETHYST.get();
			}
			case BASIC_CITRINE -> {
                return PastelBlocks.PEDESTAL_BASIC_CITRINE.get();
			}
			case CMY -> {
                return PastelBlocks.PEDESTAL_ALL_BASIC.get();
			}
			case ONYX -> {
                return PastelBlocks.PEDESTAL_ONYX.get();
			}
			default -> {
                return PastelBlocks.PEDESTAL_MOONSTONE.get();
			}
		}
	}
	
}