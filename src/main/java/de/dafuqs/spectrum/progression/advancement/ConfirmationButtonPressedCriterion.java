package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class ConfirmationButtonPressedCriterion extends SimpleCriterionTrigger<ConfirmationButtonPressedCriterion.Conditions> {
	
	public static final ResourceLocation ID = SpectrumCommon.locate("confirmation_button_pressed");
	
	public void trigger(ServerPlayer player, String confirmation) {
		this.trigger(player, (conditions) -> conditions.matches(confirmation));
	}
	
	@Override
	public Codec<ConfirmationButtonPressedCriterion.Conditions> codec() {
		return ConfirmationButtonPressedCriterion.Conditions.CODEC;
	}
	
	public record Conditions(Optional<ContextAwarePredicate> player, Optional<String> confirmation) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<ConfirmationButtonPressedCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(ConfirmationButtonPressedCriterion.Conditions::player),
				Codec.STRING.optionalFieldOf("confirmation").forGetter(ConfirmationButtonPressedCriterion.Conditions::confirmation)
		).apply(instance, ConfirmationButtonPressedCriterion.Conditions::new));
		
		public boolean matches(String confirmation) {
			return this.confirmation.isEmpty() || this.confirmation.get().equals(confirmation);
		}
	}
	
}
