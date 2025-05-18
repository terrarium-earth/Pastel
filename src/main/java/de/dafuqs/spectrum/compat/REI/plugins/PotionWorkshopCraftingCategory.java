package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
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
		return Component.translatable("container.spectrum.rei.potion_workshop_crafting.title");
	}
	
}
