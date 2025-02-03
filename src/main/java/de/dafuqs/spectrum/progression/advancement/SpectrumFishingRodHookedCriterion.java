package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.loot.context.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

/**
 * Advanced fishing criterion that can also:
 * - match the fluid that was fished in
 * - fished entities
 */
public class SpectrumFishingRodHookedCriterion extends AbstractCriterion<SpectrumFishingRodHookedCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("fishing_rod_hooked");
	
	public void trigger(ServerPlayerEntity player, ItemStack rod, SpectrumFishingBobberEntity bobber, Entity fishedEntity, Collection<ItemStack> fishingLoots) {
		LootContext bobberContext = EntityPredicate.createAdvancementEntityLootContext(player, bobber);
		LootContext hookedEntityContext = bobber.getHookedEntity() == null ? null : EntityPredicate.createAdvancementEntityLootContext(player, bobber.getHookedEntity());
		LootContext fishedEntityContext = fishedEntity == null ? null : EntityPredicate.createAdvancementEntityLootContext(player, fishedEntity);
		this.trigger(player, (conditions) -> conditions.matches(rod, bobberContext, hookedEntityContext, fishedEntityContext, fishingLoots, (ServerWorld) bobber.getWorld(), bobber.getBlockPos()));
		
		// also trigger vanilla fishing criterion
		// since that one requires a FishingBobberEntity and SpectrumFishingBobberEntity
		// does not extend that we have to do some hacky shenanigans running trigger() directly
		LootContext hookedEntityOrBobberContext = EntityPredicate.createAdvancementEntityLootContext(player, (bobber.getHookedEntity() != null ? bobber.getHookedEntity() : bobber));
		Criteria.FISHING_ROD_HOOKED.trigger(player, (conditions) -> conditions.matches(rod, hookedEntityOrBobberContext, fishingLoots));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<ItemPredicate> rod,
			Optional<LootContextPredicate> bobber,
			Optional<LootContextPredicate> hookedEntity,
			Optional<LootContextPredicate> fishedEntity,
			Optional<ItemPredicate> caughtItem,
			Optional<FluidPredicate> fluidPredicate
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("rod").forGetter(Conditions::rod),
				LootContextPredicate.CODEC.optionalFieldOf("bobber").forGetter(Conditions::bobber),
				LootContextPredicate.CODEC.optionalFieldOf("fishing").forGetter(Conditions::hookedEntity),
				LootContextPredicate.CODEC.optionalFieldOf("fished_entity").forGetter(Conditions::fishedEntity),
				ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Conditions::caughtItem),
				FluidPredicate.CODEC.optionalFieldOf("fluid").forGetter(Conditions::fluidPredicate)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack rod, LootContext bobberContext, LootContext hookedEntityContext, LootContext fishedEntityContext, Collection<ItemStack> fishingLoots, ServerWorld world, BlockPos blockPos) {
			if (this.rod.isPresent() && !this.rod.get().test(rod)) return false;
			if (this.bobber.isPresent() && !this.bobber.get().test(bobberContext)) return false;
			if (this.fluidPredicate.isPresent() && !this.fluidPredicate.get().test(world, blockPos)) return false;
			// FIXME - This doesn't seem right...
			/*
			if (fishedEntityContext == null && !fishedEntity.equals(LootContextPredicate.EMPTY) ||
				!this.fishedEntity.test(fishedEntityContext)) return false;
			if (hookedEntityContext == null && !hookedEntity.equals(LootContextPredicate.EMPTY) ||
				!this.hookedEntity.test(hookedEntityContext)) return false;
			 */
			if (this.fishedEntity.isPresent() && !this.fishedEntity.get().test(fishedEntityContext) && fishedEntityContext == null)
				return false;
			if (this.hookedEntity.isPresent() && this.hookedEntity.get().test(hookedEntityContext) && hookedEntityContext == null)
				return false;
			
			if (this.caughtItem.isPresent()) {
				if (hookedEntityContext != null) {
					Entity entity = hookedEntityContext.get(LootContextParameters.THIS_ENTITY);
					if (entity instanceof ItemEntity itemEntity &&
							this.caughtItem.get().test(itemEntity.getStack())) return true;
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
