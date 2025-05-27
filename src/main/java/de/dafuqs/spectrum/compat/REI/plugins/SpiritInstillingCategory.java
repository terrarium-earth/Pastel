package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SpiritInstillingCategory extends GatedDisplayCategory<SpiritInstillingDisplay> {
	
	private static final EntryIngredient SPIRIT_INSTILLER = EntryIngredients.of(SpectrumBlocks.SPIRIT_INSTILLER.get());
	private static final EntryIngredient ITEM_BOWL_CALCITE = EntryIngredients.of(SpectrumBlocks.ITEM_BOWL_CALCITE.get());
	
	@Override
	public CategoryIdentifier<SpiritInstillingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.SPIRIT_INSTILLER;
	}
	
	@Override
	public Component getTitle() {
		return SpectrumBlocks.SPIRIT_INSTILLER.get().getName();
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumBlocks.SPIRIT_INSTILLER.get());
	}
	
	@Override
	public void setupWidgets(Point startPoint, Rectangle bounds, List<Widget> widgets, @NotNull SpiritInstillingDisplay display) {
		List<EntryIngredient> inputs = display.getInputEntries();
		
		// input slots
		int ingredientSize = inputs.size();
		int startX = Math.max(0, 10 - ingredientSize * 10);
		widgets.add(Widgets.createSlot(new Point(startPoint.x + startX, startPoint.y + 1)).markInput().entries(inputs.get(SpiritInstillerRecipe.FIRST_INGREDIENT)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + startX + 20, startPoint.y + 1)).markInput().entries(inputs.get(SpiritInstillerRecipe.CENTER_INGREDIENT)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + startX + 40, startPoint.y + 1)).markInput().entries(inputs.get(SpiritInstillerRecipe.SECOND_INGREDIENT)));
		
		widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y + 1 + 17)).entries(ITEM_BOWL_CALCITE).disableBackground());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 20, startPoint.y + 1 + 17)).entries(SPIRIT_INSTILLER).disableBackground());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 40, startPoint.y + 1 + 17)).entries(ITEM_BOWL_CALCITE).disableBackground());
		
		// output arrow and slot
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 60, startPoint.y + 1 + 9)).animationDurationTicks(display.craftingTime));
		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 95, startPoint.y + 1 + 9)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 1 + 9)).markOutput().disableBackground().entries(display.getOutputEntries().get(0)));
		
		// description text
		Component text = getCraftingTimeText(display.craftingTime, display.experience);
		widgets.add(Widgets.createLabel(new Point(startPoint.x - 10, startPoint.y + 1 + 39), text).leftAligned().color(0x3f3f3f).noShadow());
	}
	
	@Override
	public int getDisplayHeight() {
		return 58;
	}
	
}
