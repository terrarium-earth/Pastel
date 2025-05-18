package de.dafuqs.fractal.compat;

import de.dafuqs.fractal.interfaces.ItemGroupParent;
import de.dafuqs.fractal.interfaces.SubTabLocation;
import de.dafuqs.fractal.mixin.client.CreativeInventoryScreenAccessor;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;

import java.util.List;

public class FractalREIPlugin implements REIClientPlugin {
	
	@Override
	public void registerExclusionZones(ExclusionZones zones) {
		zones.register(CreativeModeInventoryScreen.class, (screen) -> {
			CreativeModeTab selected = CreativeInventoryScreenAccessor.fractal$getSelectedGroup();
			if (selected instanceof ItemGroupParent parent && screen instanceof SubTabLocation stl && parent.fractal$getChildren() != null && !parent.fractal$getChildren().isEmpty()) {
				return List.of(
						new Rectangle(stl.fractal$getX(), stl.fractal$getY(), 72, stl.fractal$getH()),
						new Rectangle(stl.fractal$getX2(), stl.fractal$getY(), 72, stl.fractal$getH2())
				);
			}
			return List.of();
		});
	}
	
}
