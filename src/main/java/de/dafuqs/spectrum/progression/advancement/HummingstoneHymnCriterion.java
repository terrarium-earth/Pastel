package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

public class HummingstoneHymnCriterion extends AbstractCriterion<HummingstoneHymnCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("hummingstone_hymn");
	
	public void trigger(ServerPlayerEntity player, ServerWorld world, BlockPos pos) {
		this.trigger(player, (conditions) -> conditions.matches(world, pos));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			LocationPredicate locationPredicate
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<HummingstoneHymnCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(HummingstoneHymnCriterion.Conditions::player),
				LocationPredicate.CODEC.optionalFieldOf("location", LocationPredicate.Builder.create().build()).forGetter(Conditions::locationPredicate)
		).apply(instance, Conditions::new));
		
		public boolean matches(ServerWorld world, BlockPos pos) {
			return this.locationPredicate.test(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5);
		}
		
	}
	
}
