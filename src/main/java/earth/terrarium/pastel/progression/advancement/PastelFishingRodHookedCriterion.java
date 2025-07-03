package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.PastelFishingBobberEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collection;
import java.util.Optional;

/**
 * Advanced fishing criterion that can also:
 * - match the fluid that was fished in
 * - fished entities
 */
public class PastelFishingRodHookedCriterion extends SimpleCriterionTrigger<PastelFishingRodHookedCriterion.Conditions> {
	
	public static final ResourceLocation ID = PastelCommon.locate("fishing_rod_hooked");
	
	public void trigger(ServerPlayer player, ItemStack rod, PastelFishingBobberEntity bobber, Entity fishedEntity, Collection<ItemStack> fishingLoots) {
		LootContext bobberContext = EntityPredicate.createContext(player, bobber);
		LootContext hookedEntityContext = bobber.getHookedEntity() == null ? null : EntityPredicate.createContext(player, bobber.getHookedEntity());
		LootContext fishedEntityContext = fishedEntity == null ? null : EntityPredicate.createContext(player, fishedEntity);
		this.trigger(player, (conditions) -> conditions.matches(rod, bobberContext, hookedEntityContext, fishedEntityContext, fishingLoots, (ServerLevel) bobber.level(), bobber.blockPosition()));
		
		// also trigger vanilla fishing criterion
		// since that one requires a FishingBobberEntity and PastelFishingBobberEntity
		// does not extend that we have to do some hacky shenanigans running trigger() directly
		LootContext hookedEntityOrBobberContext = EntityPredicate.createContext(player, (bobber.getHookedEntity() != null ? bobber.getHookedEntity() : bobber));
		CriteriaTriggers.FISHING_ROD_HOOKED.trigger(player, (conditions) -> conditions.matches(rod, hookedEntityOrBobberContext, fishingLoots));
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			Optional<ItemPredicate> rod,
			Optional<ContextAwarePredicate> bobber,
			Optional<ContextAwarePredicate> hookedEntity,
			Optional<ContextAwarePredicate> fishedEntity,
			Optional<ItemPredicate> caughtItem,
			Optional<FluidPredicate> fluidPredicate
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("rod").forGetter(Conditions::rod),
				ContextAwarePredicate.CODEC.optionalFieldOf("bobber").forGetter(Conditions::bobber),
				ContextAwarePredicate.CODEC.optionalFieldOf("fishing").forGetter(Conditions::hookedEntity),
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("fished_entity").forGetter(Conditions::fishedEntity),
				ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Conditions::caughtItem),
				FluidPredicate.CODEC.optionalFieldOf("fluid").forGetter(Conditions::fluidPredicate)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack rod, LootContext bobberContext, LootContext hookedEntityContext, LootContext fishedEntityContext, Collection<ItemStack> fishingLoots, ServerLevel world, BlockPos blockPos) {
			if (this.rod.isPresent() && !this.rod.get().test(rod)) return false;
			if (this.bobber.isPresent() && !this.bobber.get().matches(bobberContext)) return false;
			if (this.fluidPredicate.isPresent() && !this.fluidPredicate.get().matches(world, blockPos)) return false;
			// FIXME - This doesn't seem right...
			/*
			if (fishedEntityContext == null && !fishedEntity.equals(LootContextPredicate.EMPTY) ||
				!this.fishedEntity.test(fishedEntityContext)) return false;
			if (hookedEntityContext == null && !hookedEntity.equals(LootContextPredicate.EMPTY) ||
				!this.hookedEntity.test(hookedEntityContext)) return false;
			 */

			if (fishedEntityContext != null && !fishedEntity.map(e -> e.matches(fishedEntityContext)).orElse(true))
				return false;

			if (hookedEntityContext != null && !hookedEntity.map(e -> e.matches(hookedEntityContext)).orElse(true))
				return false;
			
			if (this.caughtItem.isPresent()) {
				if (hookedEntityContext != null) {
					Entity entity = hookedEntityContext.getParamOrNull(LootContextParams.THIS_ENTITY);
					if (entity instanceof ItemEntity itemEntity &&
							this.caughtItem.get().test(itemEntity.getItem())) return true;
				}
				for (ItemStack itemStack : fishingLoots) {
					if (this.caughtItem.get().test(itemStack)) return true;
				}
				
				return false;
			}
			
			return true;
		}
	}
	
}
