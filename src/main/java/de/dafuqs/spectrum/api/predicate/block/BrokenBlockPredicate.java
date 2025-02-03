package de.dafuqs.spectrum.api.predicate.block;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.block.*;
import net.minecraft.predicate.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;

import java.util.*;

/**
 * Since BlockPredicate requires world and pos as input, we cannot use that in BrokenBlockCriterion in some places.
 * When the predicate would be checked, the block would already be broken, unable to be tested here we require a block state that can be checked against.
 * Since block entities are already destroyed at this stage, the only things that can be checked are block, state and block tag.
 * Should suffice for 99 % of cases
 */
public record BrokenBlockPredicate(Optional<RegistryEntryList<Block>> blocks, Optional<StatePredicate> state) {
	
	public static final Codec<BrokenBlockPredicate> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			RegistryCodecs.entryList(RegistryKeys.BLOCK).optionalFieldOf("blocks").forGetter(BrokenBlockPredicate::blocks),
			StatePredicate.CODEC.optionalFieldOf("state").forGetter(BrokenBlockPredicate::state)
	).apply(instance, BrokenBlockPredicate::new));
	
	public boolean test(BlockState state) {
		if (this.blocks.isPresent() && !state.isIn(this.blocks.get())) {
			return false;
		} else {
			return this.state.isEmpty() || (this.state.get()).test(state);
		}
	}

	public static class Builder {
		private Optional<RegistryEntryList<Block>> blocks = Optional.empty();
		private Optional<StatePredicate> state = Optional.empty();
		
		private Builder() {
		}

		public static BrokenBlockPredicate.Builder create() {
			return new BrokenBlockPredicate.Builder();
		}
		
		public BrokenBlockPredicate.Builder blocks(Block... blocks) {
			this.blocks = Optional.of(RegistryEntryList.of(Block::getRegistryEntry, blocks));
			return this;
		}
		
		public BrokenBlockPredicate.Builder blocks(Collection<Block> blocks) {
			this.blocks = Optional.of(RegistryEntryList.of(Block::getRegistryEntry, blocks));
			return this;
		}
		
		public BrokenBlockPredicate.Builder tag(TagKey<Block> tag) {
			this.blocks = Optional.of(Registries.BLOCK.getOrCreateEntryList(tag));
			return this;
		}
		
		public BrokenBlockPredicate.Builder state(StatePredicate.Builder state) {
			this.state = state.build();
			return this;
		}

		public BrokenBlockPredicate build() {
			return new BrokenBlockPredicate(this.blocks, this.state);
		}
	}
}
