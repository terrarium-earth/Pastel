package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.registries.SpectrumItems;
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
		return EntryStacks.of(SpectrumItems.DRAGONROT_BUCKET);
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.spectrum.rei.dragonrot_converting.title");
	}
	
}
