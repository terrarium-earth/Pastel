package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.Preenchanted;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class BedrockSwordItem extends SwordItem implements Preenchanted {
	
	public BedrockSwordItem(Tier material, Properties settings) {
		super(material, settings);
	}
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.SHARPNESS, 6);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
}
