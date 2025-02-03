package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.predicate.block.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class BlockBrokenCriterion extends AbstractCriterion<BlockBrokenCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("block_broken");
	
	public void trigger(ServerPlayerEntity player, BlockState minedBlock) {
		this.trigger(player, (conditions) -> conditions.matches(minedBlock));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(Optional<LootContextPredicate> player, Optional<BrokenBlockPredicate> blockPredicate) implements AbstractCriterion.Conditions {
		
		public static final Codec<BlockBrokenCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(BlockBrokenCriterion.Conditions::player),
				BrokenBlockPredicate.CODEC.optionalFieldOf("block_broken").forGetter(BlockBrokenCriterion.Conditions::blockPredicate)
		).apply(instance, Conditions::new));
		
		public boolean matches(BlockState blockState) {
			if (blockPredicate.isEmpty()) {
				return true;
			}
			return this.blockPredicate.get().test(blockState);
		}
	}
	
}
