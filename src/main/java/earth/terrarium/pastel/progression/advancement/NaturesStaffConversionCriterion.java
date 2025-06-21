package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class NaturesStaffConversionCriterion extends SimpleCriterionTrigger<NaturesStaffConversionCriterion.Conditions> {
	
	public static final ResourceLocation ID = PastelCommon.locate("natures_staff_conversion");
	
	public void trigger(ServerPlayer player, BlockState sourceBlockState, BlockState targetBlockState) {
		this.trigger(player, (conditions) -> conditions.matches(sourceBlockState, targetBlockState));
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			Optional<Block> sourceBlock,
			StatePropertiesPredicate sourceBlockState,
			Optional<Block> targetBlock,
			StatePropertiesPredicate targetBlockState
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf("source_block").forGetter(Conditions::sourceBlock),
				StatePropertiesPredicate.CODEC.optionalFieldOf("source_state", new StatePropertiesPredicate(List.of())).forGetter(Conditions::sourceBlockState),
				BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf("target_block").forGetter(Conditions::targetBlock),
				StatePropertiesPredicate.CODEC.optionalFieldOf("target_state", new StatePropertiesPredicate(List.of())).forGetter(Conditions::targetBlockState)
		).apply(instance, Conditions::new));
		
		public boolean matches(BlockState sourceBlockState, BlockState targetBlockState) {
			if (this.sourceBlock.isPresent() && !sourceBlockState.is(this.sourceBlock.get())) {
				return false;
			}
			if (!this.sourceBlockState.matches(sourceBlockState)) {
				return false;
			}
			if (this.targetBlock.isPresent() && !targetBlockState.is(this.targetBlock.get())) {
				return false;
			} else {
				return this.targetBlockState.matches(targetBlockState);
			}
		}
		
	}
	
}
