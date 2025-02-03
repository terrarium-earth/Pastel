package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class PedestalCraftingCriterion extends AbstractCriterion<PedestalCraftingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("crafted_with_pedestal");
	
	public void trigger(ServerPlayerEntity player, ItemStack itemStack, int experience, int durationTicks) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, experience, durationTicks));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return PedestalCraftingCriterion.Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<LootContextPredicate> location,
			List<ItemPredicate> itemPredicates,
			NumberRange.IntRange experienceRange,
			NumberRange.IntRange craftingDurationTicksRange
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<PedestalCraftingCriterion.Conditions> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(PedestalCraftingCriterion.Conditions::player),
						LootContextPredicate.CODEC.optionalFieldOf("location").forGetter(PedestalCraftingCriterion.Conditions::location),
						ItemPredicate.CODEC.listOf().optionalFieldOf("items", List.of()).forGetter(PedestalCraftingCriterion.Conditions::itemPredicates),
						NumberRange.IntRange.CODEC.optionalFieldOf("gained_experience", NumberRange.IntRange.ANY).forGetter(PedestalCraftingCriterion.Conditions::experienceRange),
						NumberRange.IntRange.CODEC.optionalFieldOf("crafting_duration_ticks", NumberRange.IntRange.ANY).forGetter(PedestalCraftingCriterion.Conditions::craftingDurationTicksRange)
				).apply(instance, PedestalCraftingCriterion.Conditions::new)
		);
		
		@Override
		public void validate(LootContextPredicateValidator validator) {
			AbstractCriterion.Conditions.super.validate(validator);
		}
		
		public boolean matches(ItemStack itemStack, int experience, int durationTicks) {
			if (this.experienceRange.test(experience) && this.craftingDurationTicksRange.test(durationTicks)) {
				List<ItemPredicate> list = new ObjectArrayList<>(this.itemPredicates);
				if (list.isEmpty()) {
					return true;
				} else {
					if (!itemStack.isEmpty()) {
						list.removeIf((itemPredicate) -> itemPredicate.test(itemStack));
					}
					return list.isEmpty();
				}
			} else {
				return false;
			}
		}
	}
	
}
