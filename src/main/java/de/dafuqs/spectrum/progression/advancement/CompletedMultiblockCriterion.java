package de.dafuqs.spectrum.progression.advancement;

import com.klikli_dev.modonomicon.api.multiblock.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class CompletedMultiblockCriterion extends AbstractCriterion<CompletedMultiblockCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("completed_multiblock");
	
	public void trigger(ServerPlayerEntity player, Multiblock iMultiblock) {
		this.trigger(player, (conditions) -> conditions.matches(iMultiblock));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(Optional<LootContextPredicate> player, Optional<Identifier> identifier) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Identifier.CODEC.optionalFieldOf("multiblock_identifier").forGetter(Conditions::identifier)
		).apply(instance, Conditions::new));
		
		public boolean matches(Multiblock multiblock) {
			return identifier.isEmpty() || multiblock.getId().equals(identifier.get());
		}
	}
	
}
