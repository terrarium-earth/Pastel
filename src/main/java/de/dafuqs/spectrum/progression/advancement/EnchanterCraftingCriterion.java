package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class EnchanterCraftingCriterion extends AbstractCriterion<EnchanterCraftingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("enchanter_crafting");
	
	public void trigger(ServerPlayerEntity player, ItemStack itemStack, int experience) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, experience));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			ItemPredicate itemPredicate,
			NumberRange.IntRange spentExperience
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<EnchanterCraftingCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("item", ItemPredicate.Builder.create().build()).forGetter(Conditions::itemPredicate),
				NumberRange.IntRange.CODEC.optionalFieldOf("spent_experience", NumberRange.IntRange.ANY).forGetter(Conditions::spentExperience)
		).apply(instance, EnchanterCraftingCriterion.Conditions::new));
		
		public boolean matches(ItemStack stack, int spentExperience) {
			if (!this.itemPredicate.test(stack)) {
				return false;
			} else {
				return this.spentExperience.test(spentExperience);
			}
		}
	}
	
}
