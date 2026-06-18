package earth.terrarium.pastel.entity.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.entity.PastelEntitySubPredicateTypes;
import earth.terrarium.pastel.entity.entity.LizardEntity;
import earth.terrarium.pastel.entity.variants.LizardFrillVariant;
import earth.terrarium.pastel.entity.variants.LizardHornVariant;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record LizardPredicate(
    Optional<InkColor> color,
    Optional<LizardFrillVariant> frills,
    Optional<LizardHornVariant> horns
) implements EntitySubPredicate {

    public static final MapCodec<LizardPredicate> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> instance
                .group(
                    InkColor.CODEC
                        .optionalFieldOf("color")
                        .forGetter(LizardPredicate::color),
                    LizardFrillVariant.CODEC
                        .optionalFieldOf("frills_variant")
                        .forGetter(LizardPredicate::frills),
                    LizardHornVariant.CODEC
                        .optionalFieldOf("horn_variant")
                        .forGetter(LizardPredicate::horns)
                )
                .apply(
                    instance,
                    LizardPredicate::new
                )
        );

    @Override
    public boolean matches(Entity entity, ServerLevel world, @Nullable Vec3 pos) {
        if (!(entity instanceof LizardEntity lizard)) {
            return false;
        } else {
            return (this.color.isEmpty() || this.color.get() == lizard.getColor()) && (this.frills
                .isEmpty() || this.frills.get() == lizard.getFrills()) && (this.horns.isEmpty() || this.horns
                    .get() == lizard.getHorns());
        }
    }

    @Override
    public MapCodec<LizardPredicate> codec() {
        return PastelEntitySubPredicateTypes.LIZARD;
    }

}
