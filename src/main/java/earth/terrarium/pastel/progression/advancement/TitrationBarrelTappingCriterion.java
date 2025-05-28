package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.SpectrumCommon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class TitrationBarrelTappingCriterion extends SimpleCriterionTrigger<TitrationBarrelTappingCriterion.Conditions> {
	
	public static final ResourceLocation ID = SpectrumCommon.locate("titration_barrel_tapping");
	
	public void trigger(ServerPlayer player, ItemStack itemStack, int ingameDaysAge, int ingredientCount) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, ingameDaysAge, ingredientCount));
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			Optional<List<ItemPredicate>> tappedItemsPredicate,
			Optional<MinMaxBounds.Ints> ingameDaysAgeRange,
			Optional<MinMaxBounds.Ints> ingredientCountRange
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.listOf().optionalFieldOf("items").forGetter(Conditions::tappedItemsPredicate),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("age_ingame_days").forGetter(Conditions::ingameDaysAgeRange),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("ingredient_count").forGetter(Conditions::ingredientCountRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack itemStack, int ingameDaysAge, int ingredientCount) {
			if (this.ingameDaysAgeRange.isEmpty()) return false;
			if (this.ingredientCountRange.isEmpty()) return false;
			
			if (this.ingameDaysAgeRange.get().matches(ingameDaysAge) && this.ingredientCountRange.get().matches(ingredientCount)) {
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
