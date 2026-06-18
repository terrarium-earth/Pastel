package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.predicate.block.BrokenBlockPredicate;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class BlockBrokenCriterion extends SimpleCriterionTrigger<BlockBrokenCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("block_broken");

    public void trigger(ServerPlayer player, BlockState minedBlock) {
        this.trigger(player, (conditions) -> conditions.matches(minedBlock));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(Optional<ContextAwarePredicate> player, Optional<BrokenBlockPredicate> blockPredicate)
        implements
        SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<BlockBrokenCriterion.Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        EntityPredicate.ADVANCEMENT_CODEC
                            .optionalFieldOf("player")
                            .forGetter(BlockBrokenCriterion.Conditions::player),
                        BrokenBlockPredicate.CODEC
                            .optionalFieldOf("block")
                            .forGetter(BlockBrokenCriterion.Conditions::blockPredicate)
                    )
                    .apply(instance, Conditions::new)
            );

        public boolean matches(BlockState blockState) {
            if (blockPredicate.isEmpty()) {
                return true;
            }
            return this.blockPredicate
                .get()
                .test(blockState);
        }
    }

}
