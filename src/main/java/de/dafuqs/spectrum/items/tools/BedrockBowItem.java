package de.dafuqs.spectrum.items.tools;

import de.dafuqs.arrowhead.api.*;
import de.dafuqs.spectrum.api.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

import java.util.*;

public class BedrockBowItem extends BowItem implements Preenchanted, ArrowheadBow {
	
	public BedrockBowItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.POWER, 6);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

	@Override
	public float getZoom(ItemStack stack) {
		return 30F;
	}

	@Override
	public float getProjectileVelocityModifier(ItemStack stack) {
		return 1.3F;
	}

	@Override
	public float getDivergenceMod(ItemStack stack) {
		return 0.8F;
	}
	
}