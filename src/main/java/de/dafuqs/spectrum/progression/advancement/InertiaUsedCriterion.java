package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class InertiaUsedCriterion extends AbstractCriterion<InertiaUsedCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("inertia_used");
	
	public void trigger(ServerPlayerEntity player, BlockState state, long amount) {
		this.trigger(player, (conditions) -> conditions.matches(state, amount));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<Block> block,
			StatePredicate statePredicate,
			LongRange amount
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Registries.BLOCK.getCodec().optionalFieldOf("block").forGetter(Conditions::block),
				StatePredicate.CODEC.optionalFieldOf("state", new StatePredicate(List.of())).forGetter(Conditions::statePredicate),
				LongRange.CODEC.optionalFieldOf("amount", LongRange.ANY).forGetter(Conditions::amount)
		).apply(instance, Conditions::new));
		
		public boolean matches(BlockState state, long amount) {
			if (this.block.isPresent() && !state.isOf(this.block.get())) {
				return false;
			} else {
				return this.statePredicate.test(state) && this.amount.test(amount);
			}
		}
	}
}
