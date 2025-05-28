package earth.terrarium.pastel.entity.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.entity.SpectrumEntitySubPredicateTypes;
import earth.terrarium.pastel.entity.entity.EggLayingWoolyPigEntity;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record EggLayingWoolyPigPredicate(Optional<DyeColor> color, Optional<Boolean> hatless, Optional<Boolean> sheared) implements EntitySubPredicate {

	public static final MapCodec<EggLayingWoolyPigPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			DyeColor.CODEC.optionalFieldOf("color").forGetter(EggLayingWoolyPigPredicate::color),
			Codec.BOOL.optionalFieldOf("hatless").forGetter(EggLayingWoolyPigPredicate::hatless),
			Codec.BOOL.optionalFieldOf("sheared").forGetter(EggLayingWoolyPigPredicate::sheared)
	).apply(instance, EggLayingWoolyPigPredicate::new));

	@Override
	public boolean matches(Entity entity, ServerLevel world, @Nullable Vec3 pos) {
		if (!(entity instanceof EggLayingWoolyPigEntity wooly)) {
			return false;
		} else {
			return (this.color.isEmpty() || this.color.get() == wooly.getColor())
					&& (this.hatless.isEmpty() || this.hatless.get() == wooly.isHatless())
					&& (this.sheared.isEmpty() || this.sheared.get() == wooly.isSheared());
		}
	}

	@Override
	public MapCodec<EggLayingWoolyPigPredicate> codec() {
		return SpectrumEntitySubPredicateTypes.EGG_LAYING_WOOLY_PIG;
	}

}
