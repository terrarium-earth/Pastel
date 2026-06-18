package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.items.trinkets.TakeOffBeltItem;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class TakeOffBeltJumpCriterion extends SimpleCriterionTrigger<TakeOffBeltJumpCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("takeoff_belt_jump");

    public static TakeOffBeltJumpCriterion.Conditions create(
        ItemPredicate itemPredicate,
        MinMaxBounds.Ints chargesRange
    ) {
        return new TakeOffBeltJumpCriterion.Conditions(Optional.empty(), itemPredicate, chargesRange);
    }

    public void trigger(ServerPlayer player) {
        this
            .trigger(
                player,
                (conditions) -> PastelTrinketItem
                    .getFirstEquipped(player, PastelItems.TAKEOFF_BELT.get())
                    .map((belt) -> TakeOffBeltItem.getCurrentCharge(player) > 0)
                    .orElse(false)
            );
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        ItemPredicate itemPredicate,
        MinMaxBounds.Ints chargesRange
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("player")
                            .forGetter(Conditions::player),
                        ItemPredicate.CODEC
                            .optionalFieldOf(
                                "item",
                                ItemPredicate.Builder
                                    .item()
                                    .build()
                            )
                            .forGetter(Conditions::itemPredicate),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("charges", MinMaxBounds.Ints.ANY)
                            .forGetter(Conditions::chargesRange)
                    )
                    .apply(
                        instance,
                        Conditions::new
                    )
            );

        public boolean matches(ItemStack beltStack, int charge) {
            return itemPredicate.test(beltStack) && this.chargesRange.matches(charge);
        }
    }

}
