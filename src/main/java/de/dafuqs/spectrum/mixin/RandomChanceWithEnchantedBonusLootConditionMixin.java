package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.dafuqs.spectrum.helpers.enchantments.CloversFavorHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootItemRandomChanceWithEnchantedBonusCondition.class)
public abstract class RandomChanceWithEnchantedBonusLootConditionMixin {
	
	@Shadow
	@Final
	private LevelBasedValue enchantedChance;
	
	@Shadow
	@Final
	private Holder<Enchantment> enchantment;
	
	@ModifyReturnValue(at = @At("RETURN"), method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z")
	public boolean spectrum$applyRareLootEnchantment(boolean original, LootContext context) {
		// if the result was to not drop a drop before reroll
		// gets more probable with each additional level of Clovers Favor
		if (!original) {
			// TODO: can we use localcapture here to avoid recalculating these values?
			if (context.getParamOrNull(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity livingEntity) {
				int level = EnchantmentHelper.getEnchantmentLevel(this.enchantment, livingEntity);
				if (level > 0) {
					float enchantedChanceValue = this.enchantedChance.calculate(level);
					original = context.getRandom().nextFloat() < CloversFavorHelper.rollChance(enchantedChanceValue, context.getParamOrNull(LootContextParams.ATTACKING_ENTITY));
				}
			}
		}
		return original;
	}
	
}
