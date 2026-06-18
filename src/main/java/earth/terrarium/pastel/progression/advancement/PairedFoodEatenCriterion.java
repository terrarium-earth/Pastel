package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class PairedFoodEatenCriterion extends SimpleCriterionTrigger<PairedFoodEatenCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("consumed_paired_food");

    public void trigger(ServerPlayer player, ItemStack eatenStack, ItemStack pairedStack) {
        this.trigger(player, (conditions) -> conditions.matches(eatenStack, pairedStack));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        Optional<ItemPredicate> eatenItem,
        Optional<ItemPredicate> pairedItem
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<PairedFoodEatenCriterion.Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("player")
                            .forGetter(PairedFoodEatenCriterion.Conditions::player),
                        ItemPredicate.CODEC
                            .optionalFieldOf("eaten_item")
                            .forGetter(PairedFoodEatenCriterion.Conditions::eatenItem),
                        ItemPredicate.CODEC
                            .optionalFieldOf("paired_item")
                            .forGetter(PairedFoodEatenCriterion.Conditions::pairedItem)
                    )
                    .apply(instance, PairedFoodEatenCriterion.Conditions::new)
            );

        public boolean matches(ItemStack eatenStack, ItemStack pairedStack) {
            if (eatenItem.isPresent() && !eatenItem.get().test(eatenStack))
                return false;

            return pairedItem.isEmpty() || pairedItem.get().test(pairedStack);
        }
    }

}
