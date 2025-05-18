package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class FreezingCategory extends BlockToBlockWithChanceCategory {
	
	@Override
	public CategoryIdentifier<? extends FreezingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.FREEZING;
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumBlocks.POLAR_BEAR_IDOL);
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.spectrum.rei.freezing.title");
	}
	
}
