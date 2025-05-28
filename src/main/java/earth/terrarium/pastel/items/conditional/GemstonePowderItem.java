package earth.terrarium.pastel.items.conditional;

import earth.terrarium.pastel.api.item.GemstoneColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GemstonePowderItem extends CloakedItem {
	
	protected final GemstoneColor gemstoneColor;
	
	public GemstonePowderItem(Properties settings, ResourceLocation cloakAdvancementIdentifier, GemstoneColor gemstoneColor, Item cloak) {
		super(settings, cloakAdvancementIdentifier, cloak);
		this.gemstoneColor = gemstoneColor;
	}
	
}
