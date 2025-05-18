package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.Preenchanted;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class RazorFalchionItem extends SwordItem implements Preenchanted {
	
	public RazorFalchionItem(Tier toolMaterial, Properties settings) {
		super(toolMaterial, settings);
	}
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.LOOTING, 3);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}
}
