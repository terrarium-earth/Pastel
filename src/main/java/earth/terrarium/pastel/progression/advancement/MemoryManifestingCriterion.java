package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

public class MemoryManifestingCriterion extends SimpleCriterionTrigger<MemoryManifestingCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("memory_manifesting");

    public void trigger(ServerPlayer player, Entity manifestedEntity) {
        LootContext lootContext = EntityPredicate.createContext(player, manifestedEntity);
        this.trigger(player, (conditions) -> conditions.matches(lootContext));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        ContextAwarePredicate manifestedEntity
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("player")
                            .forGetter(Conditions::player),
                        EntityPredicate.ADVANCEMENT_CODEC
                            .optionalFieldOf("manifested_entity", ContextAwarePredicate.create())
                            .forGetter(Conditions::manifestedEntity)
                    )
                    .apply(
                        instance,
                        Conditions::new
                    )
            );

        public boolean matches(LootContext context) {
            return this.manifestedEntity.matches(context);
        }
    }

}
