package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FusionShrineCategory extends GatedDisplayCategory<FusionShrineDisplay> {
	
	private static final EntryIngredient FUSION_SHRINE_BASALT = EntryIngredients.of(SpectrumBlocks.FUSION_SHRINE_BASALT);
	
	@Override
	public CategoryIdentifier<FusionShrineDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.FUSION_SHRINE;
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("block.spectrum.fusion_shrine");
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumBlocks.FUSION_SHRINE_BASALT);
	}
	
	@Override
	public void setupWidgets(Point startPoint, Rectangle bounds, List<Widget> widgets, @NotNull FusionShrineDisplay display) {
		List<EntryIngredient> inputs = display.getInputEntries();
		
		// shrine + fluid
		if (!inputs.get(0).equals(EntryIngredients.of(Fluids.EMPTY))) {
			widgets.add(Widgets.createSlot(new Point(startPoint.x + 10, startPoint.y - 7 + 35)).entries(FUSION_SHRINE_BASALT).disableBackground());
			widgets.add(Widgets.createSlot(new Point(startPoint.x + 30, startPoint.y - 7 + 35)).markInput().entries(inputs.get(0)));
		} else {
			widgets.add(Widgets.createSlot(new Point(startPoint.x + 20, startPoint.y - 7 + 35)).entries(FUSION_SHRINE_BASALT).disableBackground());
		}
		
		// input slots
		int ingredientSize = inputs.size() - 1;
		int startX = Math.max(-10, 10 - ingredientSize * 10);
		for (int i = 0; i < ingredientSize; i++) {
			EntryIngredient currentIngredient = inputs.get(i + 1);
			widgets.add(Widgets.createSlot(new Point(startPoint.x + startX + i * 20, startPoint.y - 7 + 9)).markInput().entries(currentIngredient));
		}
		
		// output arrow and slot
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 60, startPoint.y - 7 + 35)));
		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 95, startPoint.y - 7 + 35)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y - 7 + 35)).markOutput().disableBackground().entries(display.getOutputEntries().get(0)));
		
		if (display.getDescription().isPresent()) {
			Component description = display.getDescription().get();
			widgets.add(Widgets.createLabel(new Point(startPoint.x - 10, startPoint.y - 13 + 65), description).leftAligned().color(0x3f3f3f).noShadow());
		}
		
		// description text
		Component text = getCraftingTimeText(display.craftingTime, display.experience);
		widgets.add(Widgets.createLabel(new Point(startPoint.x - 10, startPoint.y - 13 + 75), text).leftAligned().color(0x3f3f3f).noShadow());
	}
	
	@Override
	public int getDisplayHeight() {
		return 80;
	}
	
}
