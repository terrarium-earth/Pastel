package earth.terrarium.pastel.blocks.pedestal;

import earth.terrarium.pastel.api.block.PedestalVariant;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipeTier;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.world.level.block.Block;

public enum BuiltinPedestalVariant implements PedestalVariant {
	BASIC_TOPAZ(PedestalRecipeTier.BASIC),
	BASIC_AMETHYST(PedestalRecipeTier.BASIC),
	BASIC_CITRINE(PedestalRecipeTier.BASIC),
	CMY(PedestalRecipeTier.SIMPLE),
	ONYX(PedestalRecipeTier.ADVANCED),
	MOONSTONE(PedestalRecipeTier.COMPLEX);
	
	private final PedestalRecipeTier tier;
	
	BuiltinPedestalVariant(PedestalRecipeTier tier) {
		this.tier = tier;
	}
	
	@Override
	public PedestalRecipeTier getRecipeTier() {
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