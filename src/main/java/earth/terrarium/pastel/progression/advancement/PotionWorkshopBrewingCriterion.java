package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.InkPoweredPotionFillable;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PotionWorkshopBrewingCriterion extends SimpleCriterionTrigger<PotionWorkshopBrewingCriterion.Conditions> {
	
	public static final ResourceLocation ID = PastelCommon.locate("potion_workshop_brewing");
	
	@SuppressWarnings("deprecation")
	public void trigger(ServerPlayer player, ItemStack itemStack, int brewedCount) {
		this.trigger(player, conditions -> {
			List<MobEffectInstance> effects;
			if (itemStack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable) {
				effects = inkPoweredPotionFillable.getVanillaEffects(itemStack);
			} else {
				PotionContents potionComponent = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
				effects = potionComponent.customEffects();
			}
			
			int highestAmplifier = 0;
			int longestDuration = 0;
			for (MobEffectInstance instance : effects) {
				if (instance.getAmplifier() > highestAmplifier) {
					highestAmplifier = instance.getAmplifier();
				}
				if (instance.getDuration() > longestDuration) {
					longestDuration = instance.getDuration();
				}
			}
			
			List<MobEffect> uniqueEffects = new ArrayList<>();
			for (MobEffectInstance instance : effects) {
				if (!uniqueEffects.contains(instance.getEffect().value())) {
					uniqueEffects.add(instance.getEffect().value());
				}
			}
			
			return conditions.matches(itemStack, effects, brewedCount, highestAmplifier, longestDuration, effects.size(), uniqueEffects.size());
		});
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			ItemPredicate itemPredicate,
			MobEffectsPredicate statusEffectsPredicate,
			MinMaxBounds.Ints brewedCountRange,
			MinMaxBounds.Ints maxAmplifierRange,
			MinMaxBounds.Ints maxDurationRange,
			MinMaxBounds.Ints effectCountRange,
			MinMaxBounds.Ints uniqueEffectCountRange
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("item", ItemPredicate.Builder.item().build()).forGetter(Conditions::itemPredicate),
				MobEffectsPredicate.CODEC.optionalFieldOf("effects", new MobEffectsPredicate(Map.of())).forGetter(Conditions::statusEffectsPredicate),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("brewed_count", MinMaxBounds.Ints.ANY).forGetter(Conditions::brewedCountRange),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("highest_amplifier", MinMaxBounds.Ints.ANY).forGetter(Conditions::maxAmplifierRange),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("longest_duration", MinMaxBounds.Ints.ANY).forGetter(Conditions::maxDurationRange),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("effect_count", MinMaxBounds.Ints.ANY).forGetter(Conditions::effectCountRange),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("unique_effect_count", MinMaxBounds.Ints.ANY).forGetter(Conditions::uniqueEffectCountRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack stack, List<MobEffectInstance> effects, int brewedCount, int maxAmplifier, int maxDuration, int effectCount, int uniqueEffectCount) {
			if (this.brewedCountRange.matches(brewedCount) &&
					this.maxAmplifierRange.matches(maxAmplifier) &&
					this.maxDurationRange.matches(maxDuration) &&
					this.effectCountRange.matches(effectCount) &&
					this.uniqueEffectCountRange.matches(uniqueEffectCount) &&
					this.itemPredicate.test(stack)) {
				Map<Holder<MobEffect>, MobEffectInstance> effectMap = new HashMap<>();
				for (MobEffectInstance instance : effects) {
					if (!effectMap.containsKey(instance.getEffect())) {
						effectMap.put(instance.getEffect(), instance);
					}
				}
				
				return this.statusEffectsPredicate.matches(effectMap);
			}
			
			return false;
		}
	}
	
}
