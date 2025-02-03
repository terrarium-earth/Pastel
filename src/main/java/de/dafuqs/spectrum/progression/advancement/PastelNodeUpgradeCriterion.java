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

public class PastelNodeUpgradeCriterion extends AbstractCriterion<PastelNodeUpgradeCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("pastel_node_upgrade");
	
	public void trigger(ServerPlayerEntity player, ItemStack stack) {
		this.trigger(player, conditions -> conditions.matches(stack));
	}
	
	@Override
	public Codec<PastelNodeUpgradeCriterion.Conditions> getConditionsCodec() {
		return PastelNodeUpgradeCriterion.Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			ItemPredicate item
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<PastelNodeUpgradeCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(PastelNodeUpgradeCriterion.Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("upgrade", ItemPredicate.Builder.create().build()).forGetter(PastelNodeUpgradeCriterion.Conditions::item)
		).apply(instance, PastelNodeUpgradeCriterion.Conditions::new));
		
		public boolean matches(ItemStack stack) {
			return this.item.test(stack);
		}
		
	}
}
