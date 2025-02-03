package de.dafuqs.spectrum.progression.advancement;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.entity.*;
import net.minecraft.loot.context.*;
import net.minecraft.predicate.NumberRange.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class InkProjectileKillingCriterion extends AbstractCriterion<InkProjectileKillingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("ink_projectile_killing");
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public void trigger(ServerPlayerEntity player, List<Entity> piercingKilledEntities) {
		List<LootContext> list = Lists.newArrayList();
		Set<EntityType<?>> set = Sets.newHashSet();
		
		for (Entity entity : piercingKilledEntities) {
			set.add(entity.getType());
			list.add(EntityPredicate.createAdvancementEntityLootContext(player, entity));
		}
		
		this.trigger(player, (conditions) -> conditions.matches(list, set.size()));
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			List<LootContextPredicate> victims,
			IntRange uniqueEntities
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				LootContextPredicate.CODEC.listOf().optionalFieldOf("victims", List.of()).forGetter(Conditions::victims),
				IntRange.CODEC.optionalFieldOf("unique_entity_types", IntRange.ANY).forGetter(Conditions::uniqueEntities)
		).apply(instance, Conditions::new));
		
		public boolean matches(Collection<LootContext> victimContexts, int uniqueEntityTypeCount) {
			if (!this.victims.isEmpty()) {
				List<LootContext> list = Lists.newArrayList(victimContexts);
				
				for (LootContextPredicate extended : this.victims) {
					boolean bl = false;
					
					Iterator<LootContext> iterator = list.iterator();
					while (iterator.hasNext()) {
						LootContext lootContext = iterator.next();
						if (extended.test(lootContext)) {
							iterator.remove();
							bl = true;
							break;
						}
					}
					
					if (!bl) {
						return false;
					}
				}
			}
			
			return this.uniqueEntities.test(uniqueEntityTypeCount);
		}
	}
}
