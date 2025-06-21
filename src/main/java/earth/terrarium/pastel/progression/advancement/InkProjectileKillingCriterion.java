package earth.terrarium.pastel.progression.advancement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InkProjectileKillingCriterion extends SimpleCriterionTrigger<InkProjectileKillingCriterion.Conditions> {
	
	public static final ResourceLocation ID = PastelCommon.locate("ink_projectile_killing");
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public void trigger(ServerPlayer player, List<Entity> piercingKilledEntities) {
		List<LootContext> list = Lists.newArrayList();
		Set<EntityType<?>> set = Sets.newHashSet();
		
		for (Entity entity : piercingKilledEntities) {
			set.add(entity.getType());
			list.add(EntityPredicate.createContext(player, entity));
		}
		
		this.trigger(player, (conditions) -> conditions.matches(list, set.size()));
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			List<ContextAwarePredicate> victims,
			Ints uniqueEntities
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ContextAwarePredicate.CODEC.listOf().optionalFieldOf("victims", List.of()).forGetter(Conditions::victims),
				Ints.CODEC.optionalFieldOf("unique_entity_types", Ints.ANY).forGetter(Conditions::uniqueEntities)
		).apply(instance, Conditions::new));
		
		public boolean matches(Collection<LootContext> victimContexts, int uniqueEntityTypeCount) {
			if (!this.victims.isEmpty()) {
				List<LootContext> list = Lists.newArrayList(victimContexts);
				
				for (ContextAwarePredicate extended : this.victims) {
					boolean bl = false;
					
					Iterator<LootContext> iterator = list.iterator();
					while (iterator.hasNext()) {
						LootContext lootContext = iterator.next();
						if (extended.matches(lootContext)) {
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
			
			return this.uniqueEntities.matches(uniqueEntityTypeCount);
		}
	}
}
