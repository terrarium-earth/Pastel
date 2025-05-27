package de.dafuqs.spectrum.compat.REI.plugins;

import com.google.common.collect.Lists;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PedestalCraftingCategory extends GatedDisplayCategory<PedestalCraftingDisplay> {
	
	@Override
	public CategoryIdentifier<PedestalCraftingDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.PEDESTAL_CRAFTING;
	}
	
	@Override
	public ResourceLocation getIdentifier() {
		return SpectrumCommon.locate("pedestal_crafting");
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.pastel.rei.pedestal_crafting.title");
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.get());
	}
	
	@Override
	public void setupWidgets(Point startPoint, Rectangle bounds, List<Widget> widgets, @NotNull PedestalCraftingDisplay display) {
		ResourceLocation backgroundTexture = PedestalScreen.getBackgroundTextureForTier(display.getTier());
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 60, startPoint.y + 1 + 18)).animationDurationTicks(display.craftingTime));
		
		int powderSlotCount = display.getTier().getPowderSlotCount();
		int gemstoneSlotStartX = startPoint.x + 58 - powderSlotCount * 9;
		int gemstoneSlotTextureU = 88 - powderSlotCount * 9;
		
		List<EntryIngredient> input = display.getInputEntries();
		int gemstoneDustStartSlot = 9;
		
		// crafting grid slots
		List<Slot> slots = Lists.newArrayList();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				slots.add(Widgets.createSlot(new Point(startPoint.x + 1 + x * 18, startPoint.y + 2 + y * 18)).disableBackground().markInput().disableBackground().entries(input.get(y * 3 + x)));
			}
		}
		
		// powder slots
		for (int i = 0; i < powderSlotCount; i++) {
			slots.add(Widgets.createSlot(new Point(gemstoneSlotStartX + 1 + i * 18, startPoint.y + 61)).disableBackground().markInput().entries(input.get(gemstoneDustStartSlot + i)));
		}
		widgets.addAll(slots);
		
		// the gemstone slot background texture				  destinationX				 destinationY	   sourceX, sourceY, width, height
		widgets.add(Widgets.createTexturedWidget(backgroundTexture, gemstoneSlotStartX, startPoint.y + 60, gemstoneSlotTextureU, 76, powderSlotCount * 18, 18));
		// crafting input texture
		widgets.add(Widgets.createTexturedWidget(backgroundTexture, startPoint.x, startPoint.y + 1, 29, 18, 54, 54));
		// crafting output texture
		widgets.add(Widgets.createTexturedWidget(backgroundTexture, startPoint.x + 90, startPoint.y + 15, 122, 32, 26, 26));
		// miniature gemstones texture
		widgets.add(Widgets.createTexturedWidget(backgroundTexture, startPoint.x + 82, startPoint.y + 39, 200, 0, 40, 16));
		
		// description text
		Component text = getCraftingTimeText(display.craftingTime, display.experience);
		widgets.add(Widgets.createLabel(new Point(bounds.getCenterX(), startPoint.y + 82), text).centered().color(0x3f3f3f).noShadow());
		
		if (display.shapeless) {
			widgets.add(Widgets.createShapelessIcon(new Point(startPoint.x + 108, startPoint.y + 4)));
		}
		
		// output
		List<EntryIngredient> results = display.getOutputEntries();
		EntryIngredient result = EntryIngredient.of(results.get(0));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 20)).entries(result).disableBackground().markOutput());
	}
	
	@Override
	public int getDisplayHeight() {
		return 100;
	}
	
}
