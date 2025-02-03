package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.entity.*;
import net.minecraft.loot.context.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class MemoryManifestingCriterion extends AbstractCriterion<MemoryManifestingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("memory_manifesting");
	
	public void trigger(ServerPlayerEntity player, Entity manifestedEntity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, manifestedEntity);
		this.trigger(player, (conditions) -> conditions.matches(lootContext));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			LootContextPredicate manifestedEntity
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("manifested_entity", LootContextPredicate.create()).forGetter(Conditions::manifestedEntity)
		).apply(instance, Conditions::new));
		
		public boolean matches(LootContext context) {
			return this.manifestedEntity.test(context);
		}
	}
	
}
