package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.component.type.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class EnchantmentUpgradedCriterion extends AbstractCriterion<EnchantmentUpgradedCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("enchantment_upgraded");
	
	public void trigger(ServerPlayerEntity player, ItemEnchantmentsComponent enchantmentsComponent, int spentExperience) {
		this.trigger(player, (conditions) -> conditions.matches(enchantmentsComponent, spentExperience));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			EnchantmentPredicate enchantmentPredicate,
			NumberRange.IntRange spentExperience
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				EnchantmentPredicate.CODEC.optionalFieldOf("enchantments", new EnchantmentPredicate(Optional.empty(), NumberRange.IntRange.ANY)).forGetter(Conditions::enchantmentPredicate),
				NumberRange.IntRange.CODEC.optionalFieldOf("spentExperience", NumberRange.IntRange.ANY).forGetter(Conditions::spentExperience)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemEnchantmentsComponent itemEnchantmentsComponent, int spentExperience) {
			if (this.enchantmentPredicate.test(itemEnchantmentsComponent)) {
				return this.spentExperience.test(spentExperience);
			}
			return false;
		}
	}
	
}
