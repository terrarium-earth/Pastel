package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CinderhearthCategory extends GatedDisplayCategory<CinderhearthDisplay> {
	
	@Override
	public CategoryIdentifier<CinderhearthDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.CINDERHEARTH;
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("block.pastel.cinderhearth");
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumBlocks.CINDERHEARTH.get());
	}
	
	@Override
	public void setupWidgets(Point startPoint, Rectangle bounds, List<Widget> widgets, @NotNull CinderhearthDisplay display) {
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 6, startPoint.y + 2)).markInput().entries(display.getInputEntries().get(0))); // input slot
		widgets.add(Widgets.createBurningFire(new Point(startPoint.x - 6, startPoint.y + 1 + 20)).animationDurationMS(10000));
		widgets.add(Widgets.createArrow(new Point(startPoint.x - 6 + 18, startPoint.y + 2 + 5)).animationDurationTicks(display.craftingTime));
		
		// output arrow and slots
		List<Tuple<ItemStack, Float>> outputs = display.outputsWithChance;
		for (int i = 0; i < outputs.size(); i++) {
			Tuple<ItemStack, Float> currentOutput = outputs.get(i);
			ItemStack outputStack = currentOutput.getA();
			Float chance = currentOutput.getB();
			
			Point point = new Point(startPoint.x - 6 + 49 + i * 28, startPoint.y + 1 + 5);
			widgets.add(Widgets.createResultSlotBackground(point));
			widgets.add(Widgets.createSlot(point).disableBackground().markOutput().entries(EntryIngredients.of(outputStack)));
			if (chance < 1.0) {
				widgets.add(Widgets.createLabel(new Point(point.x - 2, point.y + 23), Component.literal((int) (chance * 100) + " %")).leftAligned().color(0x3f3f3f).noShadow());
			}
		}
		
		// description text
		Component text = getCraftingTimeText(display.craftingTime, display.experience);
		widgets.add(Widgets.createLabel(new Point(startPoint.x - 6, startPoint.y + 1 + 43), text).leftAligned().color(0x3f3f3f).noShadow());
	}
	
	@Override
	public int getDisplayHeight() {
		return 65;
	}
	
}
