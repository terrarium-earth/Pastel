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

public class NaturesStaffConversionCriterion extends AbstractCriterion<NaturesStaffConversionCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("natures_staff_conversion");
	
	public void trigger(ServerPlayerEntity player, BlockState sourceBlockState, BlockState targetBlockState) {
		this.trigger(player, (conditions) -> conditions.matches(sourceBlockState, targetBlockState));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<Block> sourceBlock,
			StatePredicate sourceBlockState,
			Optional<Block> targetBlock,
			StatePredicate targetBlockState
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Registries.BLOCK.getCodec().optionalFieldOf("source_block").forGetter(Conditions::sourceBlock),
				StatePredicate.CODEC.optionalFieldOf("source_state", new StatePredicate(List.of())).forGetter(Conditions::sourceBlockState),
				Registries.BLOCK.getCodec().optionalFieldOf("target_block").forGetter(Conditions::targetBlock),
				StatePredicate.CODEC.optionalFieldOf("target_state", new StatePredicate(List.of())).forGetter(Conditions::targetBlockState)
		).apply(instance, Conditions::new));
		
		public boolean matches(BlockState sourceBlockState, BlockState targetBlockState) {
			if (this.sourceBlock.isPresent() && !sourceBlockState.isOf(this.sourceBlock.get())) {
				return false;
			}
			if (!this.sourceBlockState.test(sourceBlockState)) {
				return false;
			}
			if (this.targetBlock.isPresent() && !targetBlockState.isOf(this.targetBlock.get())) {
				return false;
			} else {
				return this.targetBlockState.test(targetBlockState);
			}
		}
		
	}
	
}
