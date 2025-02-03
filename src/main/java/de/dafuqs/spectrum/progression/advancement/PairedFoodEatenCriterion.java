package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class PairedFoodEatenCriterion extends AbstractCriterion<PairedFoodEatenCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("consumed_paired_food");
	
	public void trigger(ServerPlayerEntity player, ItemStack teaStack, ItemStack sconeStack) {
		this.trigger(player, (conditions) -> conditions.matches(teaStack, sconeStack));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			ItemPredicate teaItem,
			ItemPredicate sconeItem
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<PairedFoodEatenCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(PairedFoodEatenCriterion.Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("eaten_item", ItemPredicate.Builder.create().build()).forGetter(PairedFoodEatenCriterion.Conditions::teaItem),
				ItemPredicate.CODEC.optionalFieldOf("paired_item", ItemPredicate.Builder.create().build()).forGetter(PairedFoodEatenCriterion.Conditions::sconeItem)
		).apply(instance, PairedFoodEatenCriterion.Conditions::new));
		
		public boolean matches(ItemStack teaStack, ItemStack sconeStack) {
			return teaItem.test(teaStack) && sconeItem.test(sconeStack);
		}
	}
	
}