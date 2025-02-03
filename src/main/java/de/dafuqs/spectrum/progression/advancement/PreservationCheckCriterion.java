package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class PreservationCheckCriterion extends AbstractCriterion<PreservationCheckCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("preservation_check");
	
	public void trigger(ServerPlayerEntity player, String checkName, boolean checkPassed) {
		this.trigger(player, (conditions) -> conditions.matches(checkName, checkPassed));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<String> checkName,
			Optional<Boolean> checkPassed
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Codec.STRING.optionalFieldOf("check_name").forGetter(Conditions::checkName),
				Codec.BOOL.optionalFieldOf("check_passed").forGetter(Conditions::checkPassed)
		).apply(instance, Conditions::new));
		
		public boolean matches(String name, boolean checkPassed) {
			return (this.checkPassed.isEmpty() || this.checkPassed.get() == checkPassed)
					&& (this.checkName.isEmpty() || this.checkName.get().equals(name));
		}
	}
	
}
