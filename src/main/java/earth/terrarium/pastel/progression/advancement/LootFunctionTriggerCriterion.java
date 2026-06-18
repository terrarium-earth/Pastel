package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class LootFunctionTriggerCriterion extends SimpleCriterionTrigger<LootFunctionTriggerCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("loot_function_trigger");

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (conditions) -> conditions.matches(stack));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        List<TagKey<Item>> ids
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("player")
                            .forGetter(Conditions::player),
                        TagKey
                            .codec(
                                Registries.ITEM
                            )
                            .listOf()
                            .optionalFieldOf("tags", List.of())
                            .forGetter(Conditions::ids)
                    )
                    .apply(
                        instance,
                        Conditions::new
                    )
            );

        public boolean matches(ItemStack stack) {
            return this.ids.isEmpty() || ids.stream().anyMatch(stack::is);
        }
    }

}
