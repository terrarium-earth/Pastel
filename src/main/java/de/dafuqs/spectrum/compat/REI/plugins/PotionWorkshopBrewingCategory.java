package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class PotionWorkshopBrewingCategory extends PotionWorkshopCategory<PotionWorkshopBrewingDisplay> {
	
	@Override
	public CategoryIdentifier<PotionWorkshopBrewingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.POTION_WORKSHOP_BREWING;
	}
	
	@Override
	public ResourceLocation getIdentifier() {
		return SpectrumCommon.locate("potion_workshop_brewing");
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.spectrum.rei.potion_workshop_brewing.title");
	}
	
}
