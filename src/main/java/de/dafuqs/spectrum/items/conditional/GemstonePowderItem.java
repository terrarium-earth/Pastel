package de.dafuqs.spectrum.items.conditional;

import de.dafuqs.spectrum.api.item.GemstoneColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GemstonePowderItem extends CloakedItem {
	
	protected final GemstoneColor gemstoneColor;
	
	public GemstonePowderItem(Properties settings, ResourceLocation cloakAdvancementIdentifier, GemstoneColor gemstoneColor, Item cloak) {
		super(settings, cloakAdvancementIdentifier, cloak);
		this.gemstoneColor = gemstoneColor;
	}
	
}
