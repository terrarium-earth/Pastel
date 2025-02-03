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

public class DivinityTickCriterion extends AbstractCriterion<DivinityTickCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("divinity_tick");
	
	public void trigger(ServerPlayerEntity player) {
		this.trigger(player, (conditions) -> conditions.matches(player.isAlive(), player.getHealth()));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<Boolean> isAlive,
			NumberRange.DoubleRange healthRange
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Codec.BOOL.optionalFieldOf("isAlive").forGetter(Conditions::isAlive),
				NumberRange.DoubleRange.CODEC.optionalFieldOf("healthRange", NumberRange.DoubleRange.ANY).forGetter(Conditions::healthRange)
		).apply(instance, DivinityTickCriterion.Conditions::new));
		
		public boolean matches(boolean isPlayerAlive, float health) {
			return this.isAlive.isPresent() && (isPlayerAlive == this.isAlive.get()) && this.healthRange.test(health);
		}
	}
}
