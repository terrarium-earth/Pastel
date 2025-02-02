package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.helpers.enchantments.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.registry.entry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(RandomChanceWithEnchantedBonusLootCondition.class)
public abstract class RandomChanceWithEnchantedBonusLootConditionMixin {
	
	@Shadow
	@Final
	private EnchantmentLevelBasedValue enchantedChance;
	
	@Shadow
	@Final
	private RegistryEntry<Enchantment> enchantment;
	
	@ModifyReturnValue(at = @At("RETURN"), method = "test(Lnet/minecraft/loot/context/LootContext;)Z")
	public boolean spectrum$applyRareLootEnchantment(boolean original, LootContext context) {
		// if the result was to not drop a drop before reroll
		// gets more probable with each additional level of Clovers Favor
		if (!original) {
			// TODO: can we use localcapture here to avoid recalculating these values?
			if (context.get(LootContextParameters.ATTACKING_ENTITY) instanceof LivingEntity livingEntity) {
				int level = EnchantmentHelper.getEquipmentLevel(this.enchantment, livingEntity);
				if (level > 0) {
					float enchantedChanceValue = this.enchantedChance.getValue(level);
					original = context.getRandom().nextFloat() < CloversFavorHelper.rollChance(enchantedChanceValue, context.get(LootContextParameters.ATTACKING_ENTITY));
				}
			}
		}
		return original;
	}
	
}
