package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.items.trinkets.TakeOffBeltItem;
import de.dafuqs.spectrum.registries.SpectrumItems;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class TakeOffBeltJumpCriterion extends SimpleCriterionTrigger<TakeOffBeltJumpCriterion.Conditions> {
	
	public static final ResourceLocation ID = SpectrumCommon.locate("take_off_belt_jump");
	
	public static TakeOffBeltJumpCriterion.Conditions create(ItemPredicate itemPredicate, MinMaxBounds.Ints chargesRange) {
		return new TakeOffBeltJumpCriterion.Conditions(Optional.empty(), itemPredicate, chargesRange);
	}
	
	public void trigger(ServerPlayer player) {
		this.trigger(player, (conditions) -> {
			Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
			if (component.isPresent()) {
				List<Tuple<SlotReference, ItemStack>> equipped = component.get().getEquipped(SpectrumItems.TAKE_OFF_BELT);
				if (!equipped.isEmpty()) {
					ItemStack firstBelt = equipped.getFirst().getB();
					if (firstBelt != null) {
						int charge = TakeOffBeltItem.getCurrentCharge(player);
						if (charge > 0) {
							return conditions.matches(firstBelt, charge);
						}
					}
				}
			}
			return false;
		});
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			ItemPredicate itemPredicate,
			MinMaxBounds.Ints chargesRange
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("item", ItemPredicate.Builder.item().build()).forGetter(Conditions::itemPredicate),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("charges", MinMaxBounds.Ints.ANY).forGetter(Conditions::chargesRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack beltStack, int charge) {
			return itemPredicate.test(beltStack) && this.chargesRange.matches(charge);
		}
	}
	
}
