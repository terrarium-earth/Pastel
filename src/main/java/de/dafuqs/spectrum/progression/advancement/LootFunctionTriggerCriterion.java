package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class LootFunctionTriggerCriterion extends AbstractCriterion<LootFunctionTriggerCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("loot_function_trigger");
	
	public void trigger(ServerPlayerEntity player, Identifier id) {
		this.trigger(player, (conditions) -> conditions.matches(id));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(Optional<LootContextPredicate> player,
							 List<Identifier> ids) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Identifier.CODEC.listOf().optionalFieldOf("tags", List.of()).forGetter(Conditions::ids)
		).apply(instance, Conditions::new));
		
		public boolean matches(Identifier id) {
			return this.ids.isEmpty() || this.ids.contains(id);
		}
	}
	
}
