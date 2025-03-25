package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

import java.util.*;

public class BedrockSwordItem extends SwordItem implements Preenchanted {
	
	public BedrockSwordItem(ToolMaterial material, Settings settings) {
		super(material, settings);
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.SHARPNESS, 6);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
}
