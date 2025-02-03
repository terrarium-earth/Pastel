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
 * Since BlockPredicate requires world and pos as input we can not use that in BrokenBlockCriterion
 * When the predicate would be checked the block would already be broken, unable to be tested
 * here we require a block state, that can be checked against.
 * Since block entities are already destroyed at this stage the only things that can be checked is
 * block, state and block tag. Should suffice for 99 % of cases
 * <br>
 * TODO - Review as of 1.21 port
 */
public record BrokenBlockPredicate(Optional<RegistryEntryList<Block>> blocks, StatePredicate state) {
	
	public static final BrokenBlockPredicate ANY = new BrokenBlockPredicate(Optional.empty(), new StatePredicate(List.of()));
	
	public static final Codec<BrokenBlockPredicate> CODEC = RecordCodecBuilder.create(i -> i.group(
			RegistryCodecs.entryList(RegistryKeys.BLOCK).optionalFieldOf("blocks").forGetter(BrokenBlockPredicate::blocks),
			StatePredicate.CODEC.optionalFieldOf("state", new StatePredicate(List.of())).forGetter(BrokenBlockPredicate::state)
	).apply(i, BrokenBlockPredicate::new));
	
	public boolean test(BlockState blockState) {
		if (this == ANY) {
			return true;
		} else {
			if (this.blocks.isPresent() && !this.blocks.get().contains(Registries.BLOCK.getEntry(blockState.getBlock()))) {
				return false;
			} else {
				return this.state.test(blockState);
			}
		}
	}
	
	public static class Builder {
		private Optional<RegistryEntryList<Block>> blocks = Optional.empty();
		private StatePredicate state;
		
		private Builder() {
			this.state = new StatePredicate(List.of());
		}
		
		public static BrokenBlockPredicate.Builder create() {
			return new BrokenBlockPredicate.Builder();
		}
		
		public BrokenBlockPredicate.Builder blocks(Block... blocks) {
			this.blocks = Optional.of(RegistryEntryList.of(Registries.BLOCK::getEntry, blocks));
			return this;
		}
		
		public BrokenBlockPredicate.Builder blocks(Iterable<Block> blocks) {
			List<RegistryEntry<Block>> blockEntries = new ArrayList<>();
			for (Block block : blocks)
				blockEntries.add(Registries.BLOCK.getEntry(block));
			this.blocks = Optional.of(RegistryEntryList.of(blockEntries));
			return this;
		}
		
		public BrokenBlockPredicate.Builder tag(TagKey<Block> tag) {
			this.blocks = Registries.BLOCK.getEntryList(tag).map(l -> l);
			return this;
		}
		
		public BrokenBlockPredicate.Builder state(StatePredicate state) {
			this.state = state;
			return this;
		}
		
		public BrokenBlockPredicate build() {
			return new BrokenBlockPredicate(this.blocks, this.state);
		}
	}
}
