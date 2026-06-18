package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class SlimeSizingCriterion extends SimpleCriterionTrigger<SlimeSizingCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("slime_sizing");

    public void trigger(ServerPlayer player, int size) {
        this.trigger(player, (conditions) -> conditions.matches(size));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        MinMaxBounds.Ints sizeRange
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("player")
                            .forGetter(Conditions::player),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("size", MinMaxBounds.Ints.ANY)
                            .forGetter(Conditions::sizeRange)
                    )
                    .apply(
                        instance,
                        Conditions::new
                    )
            );

        public boolean matches(int size) {
            return this.sizeRange.matches(size);
        }
    }

}
