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

public class TitrationBarrelTappingCriterion extends AbstractCriterion<TitrationBarrelTappingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("titration_barrel_tapping");
	
	public void trigger(ServerPlayerEntity player, ItemStack itemStack, int ingameDaysAge, int ingredientCount) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, ingameDaysAge, ingredientCount));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<List<ItemPredicate>> tappedItemsPredicate,
			Optional<NumberRange.IntRange> ingameDaysAgeRange,
			Optional<NumberRange.IntRange> ingredientCountRange
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.listOf().optionalFieldOf("items").forGetter(Conditions::tappedItemsPredicate),
				NumberRange.IntRange.CODEC.optionalFieldOf("age_ingame_days").forGetter(Conditions::ingameDaysAgeRange),
				NumberRange.IntRange.CODEC.optionalFieldOf("ingredient_count").forGetter(Conditions::ingredientCountRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack itemStack, int ingameDaysAge, int ingredientCount) {
			if (this.ingameDaysAgeRange.isEmpty()) return false;
			if (this.ingredientCountRange.isEmpty()) return false;
			
			if (this.ingameDaysAgeRange.get().test(ingameDaysAge) && this.ingredientCountRange.get().test(ingredientCount)) {
				List<ItemPredicate> list = new ObjectArrayList<>(this.tappedItemsPredicate.orElse(List.of()));
				if (list.isEmpty()) {
					return true;
				} else {
					if (!itemStack.isEmpty()) {
						list.removeIf((itemPredicate) -> itemPredicate.test(itemStack));
					}
					return list.isEmpty();
				}
			}
			
			return false;
		}
	}
	
}
