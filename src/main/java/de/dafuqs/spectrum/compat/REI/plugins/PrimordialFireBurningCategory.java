package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.compat.REI.widgets.AnimatedTexturedWidget;
import de.dafuqs.spectrum.registries.SpectrumItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PrimordialFireBurningCategory extends GatedDisplayCategory<PrimordialFireBurningDisplay> {
	
	private final static ResourceLocation FIRE_TEXTURE = SpectrumCommon.locate("textures/block/primordial_fire_0.png");
	
	@Override
	public CategoryIdentifier<PrimordialFireBurningDisplay> getCategoryIdentifier() {
		return SpectrumPlugins.PRIMORDIAL_FIRE_BURNING;
	}
	
	@Override
	public Component getTitle() {
		return Component.translatable("container.spectrum.rei.primordial_fire_burning.title");
	}
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(SpectrumItems.DOOMBLOOM_SEED);
	}
	
	@Override
	public void setupWidgets(Point startPoint, Rectangle bounds, List<Widget> widgets, @NotNull PrimordialFireBurningDisplay display) {
		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 81, startPoint.y + 9)));
		widgets.add(new AnimatedTexturedWidget(FIRE_TEXTURE, new Rectangle(startPoint.x + 18, startPoint.y + 20, 0, 0), 16, 176).animationDurationMS(1000));
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 41, startPoint.y + 8)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 78, startPoint.y + 9)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 18, startPoint.y + 1)).entries(display.getInputEntries().get(0)).markInput());
	}
	
	@Override
	public int getDisplayHeight() {
		return 46;
	}
	
}
