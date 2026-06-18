package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

public class JeopardantKillCriterion extends SimpleCriterionTrigger<JeopardantKillCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("jeopardant_kill");

    public void trigger(ServerPlayer player, Entity killedEntity) {
        LootContext lootContext = EntityPredicate.createContext(player, killedEntity);
        this.trigger(player, (conditions) -> conditions.test(player, lootContext));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        ContextAwarePredicate killedEntity,
        MinMaxBounds.Ints health
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("player")
                            .forGetter(Conditions::player),
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("killed_entity", ContextAwarePredicate.create())
                            .forGetter(Conditions::killedEntity),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("health", MinMaxBounds.Ints.ANY)
                            .forGetter(Conditions::health)
                    )
                    .apply(
                        instance,
                        Conditions::new
                    )
            );

        public boolean test(ServerPlayer player, LootContext killedEntityContext) {
            return this.killedEntity.matches(killedEntityContext) && this.health
                .matches(
                    Math.round(player.getHealth())
                );
        }
    }

}
