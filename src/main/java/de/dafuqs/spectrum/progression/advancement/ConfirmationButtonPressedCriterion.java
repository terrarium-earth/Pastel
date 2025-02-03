package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class ConfirmationButtonPressedCriterion extends AbstractCriterion<ConfirmationButtonPressedCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("confirmation_button_pressed");
	
	public void trigger(ServerPlayerEntity player, String confirmation) {
		this.trigger(player, (conditions) -> conditions.matches(confirmation));
	}
	
	@Override
	public Codec<ConfirmationButtonPressedCriterion.Conditions> getConditionsCodec() {
		return ConfirmationButtonPressedCriterion.Conditions.CODEC;
	}
	
	public record Conditions(Optional<LootContextPredicate> player, Optional<String> confirmation) implements AbstractCriterion.Conditions {
		
		public static final Codec<ConfirmationButtonPressedCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(ConfirmationButtonPressedCriterion.Conditions::player),
				Codec.STRING.optionalFieldOf("confirmation").forGetter(ConfirmationButtonPressedCriterion.Conditions::confirmation)
		).apply(instance, ConfirmationButtonPressedCriterion.Conditions::new));
		
		public boolean matches(String confirmation) {
			return this.confirmation.isEmpty() || this.confirmation.get().equals(confirmation);
		}
	}
	
}
