package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.SpectrumPlugins;
import earth.terrarium.pastel.registries.SpectrumItems;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;

@OnlyIn(Dist.CLIENT)
public class DragonrotConvertingCategory extends FluidConvertingCategory<DragonrotConvertingDisplay> {
	
	@Override
	public CategoryIdentifier<? extends FluidConvertingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.DRAGONROT_CONVERTING;
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumItems.DRAGONROT_BUCKET.get());
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.pastel.rei.dragonrot_converting.title");
	}
	
}
