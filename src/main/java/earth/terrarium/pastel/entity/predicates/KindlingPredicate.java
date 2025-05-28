package earth.terrarium.pastel.entity.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.entity.SpectrumEntitySubPredicateTypes;
import earth.terrarium.pastel.entity.entity.KindlingEntity;
import earth.terrarium.pastel.entity.variants.KindlingVariant;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record KindlingPredicate(Optional<Boolean> clipped, Optional<Boolean> angry, Optional<KindlingVariant> variant) implements EntitySubPredicate {

	public static final MapCodec<KindlingPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			Codec.BOOL.optionalFieldOf("clipped").forGetter(KindlingPredicate::clipped),
			Codec.BOOL.optionalFieldOf("angry").forGetter(KindlingPredicate::angry),
			KindlingVariant.CODEC.optionalFieldOf("variant").forGetter(KindlingPredicate::variant)
	).apply(instance, KindlingPredicate::new));

	@Override
	public boolean matches(Entity entity, ServerLevel world, @Nullable Vec3 pos) {
		if (!(entity instanceof KindlingEntity kindling)) {
			return false;
		} else {
			return (this.clipped.isEmpty() || this.clipped.get() == kindling.isClipped())
					&& (this.angry.isEmpty() || this.angry.get() == (kindling.getRemainingPersistentAngerTime() == 0)
					&& (this.variant.isEmpty() || this.variant.get() == kindling.getKindlingVariant()));
		}
	}

	@Override
	public MapCodec<KindlingPredicate> codec() {
		return SpectrumEntitySubPredicateTypes.KINDLING;
	}

}
