package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.items.tooltip.BottomlessBundleTooltipComponent;
import earth.terrarium.pastel.items.tooltip.ItemStorageTooltipData;
import earth.terrarium.pastel.items.tooltip.CraftingTabletTooltipComponent;
import earth.terrarium.pastel.items.tooltip.CraftingTabletTooltipData;
import earth.terrarium.pastel.items.tooltip.PresentTooltipComponent;
import earth.terrarium.pastel.items.tooltip.PresentTooltipData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.*;

@OnlyIn(Dist.CLIENT)
public class SpectrumTooltipComponents {
	
	public static void registerTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(CraftingTabletTooltipData.class, CraftingTabletTooltipComponent::new);
		event.register(ItemStorageTooltipData.class, BottomlessBundleTooltipComponent::new);
		event.register(PresentTooltipData.class, PresentTooltipComponent::new);
	}
	
}
