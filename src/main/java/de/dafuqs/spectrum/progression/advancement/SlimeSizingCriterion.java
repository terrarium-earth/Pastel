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

public class SlimeSizingCriterion extends AbstractCriterion<SlimeSizingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("slime_sizing");
	
	public void trigger(ServerPlayerEntity player, int size) {
		this.trigger(player, (conditions) -> conditions.matches(size));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			NumberRange.IntRange sizeRange
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				NumberRange.IntRange.CODEC.optionalFieldOf("size", NumberRange.IntRange.ANY).forGetter(Conditions::sizeRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(int size) {
			return this.sizeRange.test(size);
		}
	}
	
}
