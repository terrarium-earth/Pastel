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
public class LiquidCrystalConvertingCategory extends FluidConvertingCategory<LiquidCrystalConvertingDisplay> {
	
	@Override
	public CategoryIdentifier<? extends LiquidCrystalConvertingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.LIQUID_CRYSTAL_CONVERTING;
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumItems.LIQUID_CRYSTAL_BUCKET);
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.spectrum.rei.liquid_crystal_converting.title");
	}
	
}
