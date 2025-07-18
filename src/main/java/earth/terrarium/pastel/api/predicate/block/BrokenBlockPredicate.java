package earth.terrarium.pastel.api.predicate.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Since BlockPredicate requires world and pos as input we can not use that in BrokenBlockCriterion
 * When the predicate would be checked the block would already be broken, unable to be tested
 * here we require a block state, that can be checked against.
 * Since block entities are already destroyed at this stage the only things that can be checked is
 * block, state and block tag. Should suffice for 99 % of cases
 */
public record BrokenBlockPredicate(Optional<HolderSet<Block>> blocks, Optional<StatePropertiesPredicate> state) {

    public static final Codec<BrokenBlockPredicate> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                                RegistryCodecs.homogeneousList(Registries.BLOCK)
                                                                                                              .optionalFieldOf("blocks")
                                                                                                              .forGetter(BrokenBlockPredicate::blocks),
                                                                                                StatePropertiesPredicate.CODEC.optionalFieldOf("state")
                                                                                                                              .forGetter(BrokenBlockPredicate::state)
                                                                                            )
                                                                                            .apply(
                                                                                                i,
                                                                                                BrokenBlockPredicate::new
                                                                                            ));

    public boolean test(BlockState state) {
        if (this.blocks.isPresent() && !state.is(this.blocks.get())) {
            return false;
        } else {
            return this.state.isEmpty() || (this.state.get()).matches(state);
        }
    }

    public static class Builder {
        private Optional<HolderSet<Block>> blocks = Optional.empty();
        private Optional<StatePropertiesPredicate> state = Optional.empty();

        private Builder() {
        }

        public static BrokenBlockPredicate.Builder create() {
            return new BrokenBlockPredicate.Builder();
        }

        public BrokenBlockPredicate.Builder blocks(Block... blocks) {
            this.blocks = Optional.of(HolderSet.direct(BuiltInRegistries.BLOCK::wrapAsHolder, blocks));
            return this;
        }

        public BrokenBlockPredicate.Builder blocks(Iterable<Block> blocks) {
            List<Holder<Block>> blockEntries = new ArrayList<>();
            for (Block block : blocks)
                blockEntries.add(BuiltInRegistries.BLOCK.wrapAsHolder(block));
            this.blocks = Optional.of(HolderSet.direct(blockEntries));
            return this;
        }

        public BrokenBlockPredicate.Builder tag(TagKey<Block> tag) {
            this.blocks = BuiltInRegistries.BLOCK.getTag(tag)
                                                 .map(l -> l);
            return this;
        }

        public BrokenBlockPredicate.Builder registryEntryList(HolderSet<Block> list) {
            this.blocks = Optional.of(list);
            return this;
        }

        public BrokenBlockPredicate.Builder state(StatePropertiesPredicate state) {
            this.state = Optional.of(state);
            return this;
        }

        public BrokenBlockPredicate build() {
            return new BrokenBlockPredicate(this.blocks, this.state);
        }
    }
}
