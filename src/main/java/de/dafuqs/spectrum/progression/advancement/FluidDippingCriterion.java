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
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

public class FluidDippingCriterion extends AbstractCriterion<FluidDippingCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("fluid_dipping");
	
	public void trigger(ServerPlayerEntity player, ServerWorld world, BlockPos pos, ItemStack previousStack, ItemStack targetStack) {
		this.trigger(player, (conditions) -> conditions.matches(world, pos, previousStack, targetStack));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			FluidPredicate fluidPredicate,
			ItemPredicate previousStackPredicate,
			ItemPredicate targetStackPredicate
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				FluidPredicate.CODEC.optionalFieldOf("fluid", FluidPredicate.Builder.create().build()).forGetter(Conditions::fluidPredicate),
				ItemPredicate.CODEC.optionalFieldOf("source_stack", ItemPredicate.Builder.create().build()).forGetter(Conditions::previousStackPredicate),
				ItemPredicate.CODEC.optionalFieldOf("target_stack", ItemPredicate.Builder.create().build()).forGetter(Conditions::targetStackPredicate)
		).apply(instance, Conditions::new));
		
		public boolean matches(ServerWorld world, BlockPos pos, ItemStack previousStack, ItemStack targetStack) {
			return this.fluidPredicate.test(world, pos) && this.previousStackPredicate.test(previousStack) && this.targetStackPredicate.test(targetStack);
		}
	}
	
}
