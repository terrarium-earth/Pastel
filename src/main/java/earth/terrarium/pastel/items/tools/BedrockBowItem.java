package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.Preenchanted;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class BedrockBowItem extends BowItem implements Preenchanted {
	
	public BedrockBowItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.POWER, 6);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

}