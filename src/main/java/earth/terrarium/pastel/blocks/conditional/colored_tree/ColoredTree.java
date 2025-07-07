package earth.terrarium.pastel.blocks.conditional.colored_tree;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.registries.PastelAdvancements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface ColoredTree {

	BooleanProperty NATURAL = BooleanProperty.create("natural");

	enum TreePart {
		SAPLING,
		LOG,
		LEAVES,
		STRIPPED_LOG,
		WOOD,
		STRIPPED_WOOD
	}
	
	static ResourceLocation getTreeCloakAdvancementIdentifier(TreePart treePart, InkColor color) {
		if (color == InkColors.WHITE || color == InkColors.LIGHT_GRAY || color == InkColors.GRAY) {
			return PastelAdvancements.REVEAL_COLORED_TREES_WHITE;
		}
		if (color == InkColors.BLACK || color == InkColors.BROWN) {
			return PastelAdvancements.REVEAL_COLORED_TREES_BLACK;
		}
		
		return treePart == TreePart.SAPLING ? PastelAdvancements.REVEAL_COLORED_SAPLINGS_CMY : PastelAdvancements.REVEAL_COLORED_TREES_CMY;
	}
	
	InkColor getColor();
	
}
