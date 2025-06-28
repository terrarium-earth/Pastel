package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.items.ArrowheadCrossbow;
import earth.terrarium.pastel.api.item.Preenchanted;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class BedrockCrossbowItem extends CrossbowItem implements Preenchanted, ArrowheadCrossbow {
	
	public BedrockCrossbowItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.QUICK_CHARGE, 4);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

	@Override
	public float getProjectileVelocityModifier(ItemStack stack) {
		return 1.5F;
	}

	// TODO What is this needed for?
	public float getPullTimeModifier(ItemStack stack) {
		return 3.0F;
	}

	@Override
	public float getDivergenceMod(ItemStack stack) {
		return 0.8F;
	}
	
}