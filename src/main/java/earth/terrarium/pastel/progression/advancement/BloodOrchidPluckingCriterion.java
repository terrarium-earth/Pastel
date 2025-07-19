package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class BloodOrchidPluckingCriterion extends SimpleCriterionTrigger<BloodOrchidPluckingCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("blood_orchid_plucking");

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                                                                                                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                                                                                                                                         .forGetter(Conditions::player)
                                                                                                    )
                                                                                                    .apply(
                                                                                                        instance,
                                                                                                        BloodOrchidPluckingCriterion.Conditions::new
                                                                                                    ));
    }
}
