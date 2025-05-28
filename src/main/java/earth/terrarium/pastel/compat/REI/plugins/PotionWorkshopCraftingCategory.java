package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.compat.REI.SpectrumPlugins;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PotionWorkshopCraftingCategory extends PotionWorkshopCategory<PotionWorkshopCraftingDisplay> {
	
	@Override
	public CategoryIdentifier<PotionWorkshopCraftingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.POTION_WORKSHOP_CRAFTING;
	}
	
	@Override
	public ResourceLocation getIdentifier() {
		return SpectrumCommon.locate("potion_workshop_crafting");
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.pastel.rei.potion_workshop_crafting.title");
	}
	
}
