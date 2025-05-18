package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.Preenchanted;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class BedrockAxeItem extends AxeItem implements Preenchanted {
	
	public BedrockAxeItem(Tier material, Properties settings) {
		super(material, settings);
	}
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.EFFICIENCY, 6);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
}
