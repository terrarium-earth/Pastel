package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class PotionWorkshopBrewingCategory extends PotionWorkshopCategory<PotionWorkshopBrewingDisplay> {
	
	@Override
	public CategoryIdentifier<PotionWorkshopBrewingDisplay> getCategoryIdentifier() {
		return PastelPlugins.POTION_WORKSHOP_BREWING;
	}
	
	@Override
	public ResourceLocation getIdentifier() {
		return PastelCommon.locate("potion_workshop_brewing");
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.pastel.rei.potion_workshop_brewing.title");
	}
	
}
