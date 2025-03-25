package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

import java.util.*;

// Waiting for https://github.com/FabricMC/fabric/pull/1804
// Who's still waiting this with me in 2025??
public class BedrockShearsItem extends ShearsItem implements Preenchanted {
	
	public BedrockShearsItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.EFFICIENCY, 6);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
}