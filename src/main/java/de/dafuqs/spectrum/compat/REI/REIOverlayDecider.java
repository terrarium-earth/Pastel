package de.dafuqs.spectrum.compat.REI;

import de.dafuqs.spectrum.inventories.QuickNavigationGridScreen;
import me.shedaniel.rei.api.client.registry.screen.OverlayDecider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionResult;

@OnlyIn(Dist.CLIENT)
public class REIOverlayDecider implements OverlayDecider {
	
	public static final OverlayDecider INSTANCE = new REIOverlayDecider();
	
	@Override
	public <R extends Screen> boolean isHandingScreen(Class<R> screen) {
		return screen.getPackageName().startsWith("de.dafuqs.pastel");
	}
	
	@Override
	public <R extends Screen> InteractionResult shouldScreenBeOverlaid(R screen) {
		if (screen instanceof QuickNavigationGridScreen) {
			return InteractionResult.FAIL;
		}
		return InteractionResult.PASS;
	}
	
}
