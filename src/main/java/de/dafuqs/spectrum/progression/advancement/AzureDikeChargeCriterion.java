package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class AzureDikeChargeCriterion extends AbstractCriterion<AzureDikeChargeCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("azure_dike_charge_change");
	
	public void trigger(ServerPlayerEntity player, float charges, int rechargeRate, float change) {
		this.trigger(player, (conditions) -> conditions.matches(charges, rechargeRate, change));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			NumberRange.IntRange charges,
			NumberRange.IntRange rechargeRate,
			NumberRange.IntRange change
	) implements AbstractCriterion.Conditions {
		public static final Codec<AzureDikeChargeCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(AzureDikeChargeCriterion.Conditions::player),
				NumberRange.IntRange.CODEC.optionalFieldOf("charges", NumberRange.IntRange.ANY).forGetter(AzureDikeChargeCriterion.Conditions::charges),
				NumberRange.IntRange.CODEC.optionalFieldOf("recharge_rate", NumberRange.IntRange.ANY).forGetter(AzureDikeChargeCriterion.Conditions::rechargeRate),
				NumberRange.IntRange.CODEC.optionalFieldOf("change", NumberRange.IntRange.ANY).forGetter(AzureDikeChargeCriterion.Conditions::change)
		).apply(instance, AzureDikeChargeCriterion.Conditions::new));
		
		public boolean matches(float charges, int rechargeRate, float change) {
			return this.charges.test(Math.round(charges)) && this.rechargeRate.test(rechargeRate) && this.change.test(Math.round(change));
		}
	}
}
	
