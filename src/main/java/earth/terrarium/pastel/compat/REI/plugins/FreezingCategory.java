package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.SpectrumPlugins;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;

@OnlyIn(Dist.CLIENT)
public class FreezingCategory extends BlockToBlockWithChanceCategory {
	
	@Override
	public CategoryIdentifier<? extends FreezingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.FREEZING;
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumBlocks.POLAR_BEAR_IDOL.get());
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.pastel.rei.freezing.title");
	}
	
}
