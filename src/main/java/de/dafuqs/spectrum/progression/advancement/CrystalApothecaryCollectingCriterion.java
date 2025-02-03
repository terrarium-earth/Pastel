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

public class CrystalApothecaryCollectingCriterion extends AbstractCriterion<CrystalApothecaryCollectingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("collect_using_crystal_apothecary");
	
	public void trigger(ServerPlayerEntity player, ItemStack itemStack) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack));
	}
	
	@Override
	public Codec<CrystalApothecaryCollectingCriterion.Conditions> getConditionsCodec() {
		return CrystalApothecaryCollectingCriterion.Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			ItemPredicate item
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<CrystalApothecaryCollectingCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(CrystalApothecaryCollectingCriterion.Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("item", ItemPredicate.Builder.create().build()).forGetter(CrystalApothecaryCollectingCriterion.Conditions::item)
		).apply(instance, CrystalApothecaryCollectingCriterion.Conditions::new));
		
		public boolean matches(ItemStack stack) {
			return this.item.test(stack);
		}
		
	}
	
}
