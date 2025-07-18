package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class FluidDippingCriterion extends SimpleCriterionTrigger<FluidDippingCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("fluid_dipping");

    public void trigger(
        ServerPlayer player, ServerLevel world, BlockPos pos, ItemStack previousStack, ItemStack targetStack) {
        this.trigger(player, (conditions) -> conditions.matches(world, pos, previousStack, targetStack));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        FluidPredicate fluidPredicate,
        ItemPredicate previousStackPredicate,
        ItemPredicate targetStackPredicate
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                                                                                                        ContextAwarePredicate.CODEC.optionalFieldOf("player")
                                                                                                                                   .forGetter(Conditions::player),
                                                                                                        FluidPredicate.CODEC.optionalFieldOf(
                                                                                                                          "fluid", FluidPredicate.Builder.fluid()
                                                                                                                                                         .build()
                                                                                                                      )
                                                                                                                            .forGetter(Conditions::fluidPredicate),
                                                                                                        ItemPredicate.CODEC.optionalFieldOf(
                                                                                                                         "source_stack", ItemPredicate.Builder.item()
                                                                                                                                                              .build()
                                                                                                                     )
                                                                                                                           .forGetter(Conditions::previousStackPredicate),
                                                                                                        ItemPredicate.CODEC.optionalFieldOf(
                                                                                                                         "target_stack", ItemPredicate.Builder.item()
                                                                                                                                                              .build()
                                                                                                                     )
                                                                                                                           .forGetter(Conditions::targetStackPredicate)
                                                                                                    )
                                                                                                    .apply(
                                                                                                        instance,
                                                                                                        Conditions::new
                                                                                                    ));

        public boolean matches(ServerLevel world, BlockPos pos, ItemStack previousStack, ItemStack targetStack) {
            return this.fluidPredicate.matches(world, pos) && this.previousStackPredicate.test(previousStack) &&
                   this.targetStackPredicate.test(targetStack);
        }
    }

}
