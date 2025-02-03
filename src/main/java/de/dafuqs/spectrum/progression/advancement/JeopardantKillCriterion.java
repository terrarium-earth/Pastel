package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.entity.*;
import net.minecraft.loot.context.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class JeopardantKillCriterion extends AbstractCriterion<JeopardantKillCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("jeopardant_kill");
	
	public void trigger(ServerPlayerEntity player, Entity killedEntity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, killedEntity);
		this.trigger(player, (conditions) -> conditions.test(player, lootContext));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			LootContextPredicate killedEntity,
			NumberRange.IntRange health
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				LootContextPredicate.CODEC.optionalFieldOf("killed_entity", LootContextPredicate.create()).forGetter(Conditions::killedEntity),
				NumberRange.IntRange.CODEC.optionalFieldOf("health", NumberRange.IntRange.ANY).forGetter(Conditions::health)
		).apply(instance, Conditions::new));
		
		public boolean test(ServerPlayerEntity player, LootContext killedEntityContext) {
			return this.killedEntity.test(killedEntityContext) && this.health.test(Math.round(player.getHealth()));
		}
	}
	
}
