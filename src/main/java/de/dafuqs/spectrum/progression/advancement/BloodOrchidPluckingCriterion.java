package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class BloodOrchidPluckingCriterion extends AbstractCriterion<BloodOrchidPluckingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("blood_orchid_plucking");
	
	public void trigger(ServerPlayerEntity player) {
		this.trigger(player, conditions -> true);
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(Optional<LootContextPredicate> player) implements AbstractCriterion.Conditions {
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player)
		).apply(instance, BloodOrchidPluckingCriterion.Conditions::new));
	}
}
