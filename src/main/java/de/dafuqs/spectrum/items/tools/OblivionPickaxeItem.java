package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class OblivionPickaxeItem extends SpectrumPickaxeItem {
	
	public OblivionPickaxeItem(ToolMaterial material, Settings settings) {
		super(material, settings);
	}
	
	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		super.postMine(stack, world, state, pos, miner);
		
		// Break the tool if it is used without the voiding enchantment
		// Otherwise this would be a VERY cheap early game diamond tier tool
		if (!world.isClient && !EnchantmentHelper.hasAnyEnchantmentsIn(stack, SpectrumEnchantmentTags.NO_BLOCK_DROPS)) {
			stack.damage(5000, miner, EquipmentSlot.MAINHAND);
		}
		
		return true;
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(SpectrumEnchantments.VOIDING, 1);
	}
	
	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}
}
