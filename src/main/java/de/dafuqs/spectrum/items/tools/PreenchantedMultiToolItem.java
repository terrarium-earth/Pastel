package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;

import java.util.*;

public class PreenchantedMultiToolItem extends MultiToolItem implements Preenchanted, LoomPatternProvider {
	
	public PreenchantedMultiToolItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.EFFICIENCY, 1);
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.MULTITOOL;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}
