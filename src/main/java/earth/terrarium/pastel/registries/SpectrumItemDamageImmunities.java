package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.item.ItemDamageImmunity;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Items;

public class SpectrumItemDamageImmunities {
	
	public static void registerDefaultItemStackImmunities() {
		ItemDamageImmunity.registerImmunity(Items.NETHER_STAR, DamageTypeTags.IS_FIRE);
		ItemDamageImmunity.registerImmunity(Items.NETHER_STAR, DamageTypeTags.IS_EXPLOSION);
		
		ItemDamageImmunity.registerImmunity(SpectrumBlocks.CRACKED_END_PORTAL_FRAME.get(), DamageTypeTags.IS_EXPLOSION);
		ItemDamageImmunity.registerImmunity(SpectrumItems.DOOMBLOOM_SEED.get(), DamageTypeTags.IS_EXPLOSION);
	}
	
}
