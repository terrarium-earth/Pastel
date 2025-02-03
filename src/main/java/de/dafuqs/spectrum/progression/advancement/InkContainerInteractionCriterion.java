package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class InkContainerInteractionCriterion extends AbstractCriterion<InkContainerInteractionCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("ink_container_interaction");
	
	public void trigger(ServerPlayerEntity player, ItemStack stack, InkStorage storage, InkColor changeColor, long changeAmount) {
		this.trigger(player, (conditions) -> conditions.matches(stack, storage.getEnergy(), changeColor, changeAmount));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			ItemPredicate itemPredicate,
			Map<InkColor, LongRange> colorRanges,
			ColorPredicate changeColorPredicate,
			LongRange changeRange
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("item", ItemPredicate.Builder.create().build()).forGetter(Conditions::itemPredicate),
				CodecHelper.registryMap(SpectrumRegistries.INK_COLORS, LongRange.CODEC).forGetter(Conditions::colorRanges),
				ColorPredicate.CODEC.optionalFieldOf("change_color", ColorPredicate.ANY).forGetter(Conditions::changeColorPredicate),
				LongRange.CODEC.optionalFieldOf("change_range", LongRange.ANY).forGetter(Conditions::changeRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack stack, Map<InkColor, Long> colors, InkColor changeColor, long change) {
			return itemPredicate.test(stack)
					&& changeRange.test(change)
					&& changeColorPredicate.test(changeColor)
					&& colorRanges.entrySet().stream().allMatch(entry -> colorRanges.get(entry.getKey()).test(colors.get(entry.getKey())));
			
		}
	}
	
}