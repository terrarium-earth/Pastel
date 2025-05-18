package de.dafuqs.fractal.compat;

import de.dafuqs.fractal.interfaces.ItemGroupParent;
import de.dafuqs.fractal.interfaces.SubTabLocation;
import de.dafuqs.fractal.mixin.client.CreativeInventoryScreenAccessor;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.widget.Bounds;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;

public class FractalEMIPlugin implements EmiPlugin {
	
	@Override
	public void register(EmiRegistry registry) {
		registry.addExclusionArea(CreativeModeInventoryScreen.class, (screen, out) -> {
			if (screen != null) {
				CreativeModeTab selected = CreativeInventoryScreenAccessor.fractal$getSelectedGroup();
				if (selected instanceof ItemGroupParent parent && screen instanceof SubTabLocation stl && parent.fractal$getChildren() != null && !parent.fractal$getChildren().isEmpty()) {
					out.accept(new Bounds(stl.fractal$getX(), stl.fractal$getY(), 72, stl.fractal$getH()));
					out.accept(new Bounds(stl.fractal$getX2(), stl.fractal$getY(), 72, stl.fractal$getH2()));
				}
			}
		});
	}
	
}
