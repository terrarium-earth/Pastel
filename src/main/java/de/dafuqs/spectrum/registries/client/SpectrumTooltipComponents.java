package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.items.tooltip.BottomlessBundleTooltipComponent;
import de.dafuqs.spectrum.items.tooltip.BottomlessBundleTooltipData;
import de.dafuqs.spectrum.items.tooltip.CraftingTabletTooltipComponent;
import de.dafuqs.spectrum.items.tooltip.CraftingTabletTooltipData;
import de.dafuqs.spectrum.items.tooltip.PresentTooltipComponent;
import de.dafuqs.spectrum.items.tooltip.PresentTooltipData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.*;

@OnlyIn(Dist.CLIENT)
public class SpectrumTooltipComponents {
	
	public static void registerTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(CraftingTabletTooltipData.class, CraftingTabletTooltipComponent::new);
		event.register(BottomlessBundleTooltipData.class, BottomlessBundleTooltipComponent::new);
		event.register(PresentTooltipData.class, PresentTooltipComponent::new);
	}
	
}
