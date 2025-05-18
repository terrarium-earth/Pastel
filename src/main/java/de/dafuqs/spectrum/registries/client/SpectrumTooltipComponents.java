package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.items.tooltip.BottomlessBundleTooltipComponent;
import de.dafuqs.spectrum.items.tooltip.BottomlessBundleTooltipData;
import de.dafuqs.spectrum.items.tooltip.CraftingTabletTooltipComponent;
import de.dafuqs.spectrum.items.tooltip.CraftingTabletTooltipData;
import de.dafuqs.spectrum.items.tooltip.PresentTooltipComponent;
import de.dafuqs.spectrum.items.tooltip.PresentTooltipData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;

@OnlyIn(Dist.CLIENT)
public class SpectrumTooltipComponents {
	
	public static void registerTooltipComponents() {
		TooltipComponentCallback.EVENT.register((data -> {
			if (data instanceof CraftingTabletTooltipData craftingTabletTooltipData) {
				return new CraftingTabletTooltipComponent(craftingTabletTooltipData);
			} else if (data instanceof BottomlessBundleTooltipData bottomlessBundleTooltipData) {
				return new BottomlessBundleTooltipComponent(bottomlessBundleTooltipData);
			} else if (data instanceof PresentTooltipData presentTooltipData) {
				return new PresentTooltipComponent(presentTooltipData);
			}
			return null;
		}));
	}
	
}
