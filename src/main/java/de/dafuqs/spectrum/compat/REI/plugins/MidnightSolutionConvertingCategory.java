package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.registries.SpectrumItems;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class MidnightSolutionConvertingCategory extends FluidConvertingCategory<MidnightSolutionConvertingDisplay> {
	
	@Override
	public CategoryIdentifier<? extends MidnightSolutionConvertingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.MIDNIGHT_SOLUTION_CONVERTING;
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET);
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.spectrum.rei.midnight_solution_converting.title");
	}
	
}
