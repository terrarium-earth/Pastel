package earth.terrarium.pastel.entity.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.entity.SpectrumEntitySubPredicateTypes;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record ShulkerPredicate(Optional<DyeColor> color) implements EntitySubPredicate {

	public static final MapCodec<ShulkerPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			DyeColor.CODEC.optionalFieldOf("color").forGetter(ShulkerPredicate::color)
	).apply(instance, ShulkerPredicate::new));

	@Override
	public boolean matches(Entity entity, ServerLevel world, @Nullable Vec3 pos) {
		if (!(entity instanceof Shulker shulkerEntity)) {
			return false;
		} else {
			return (this.color.isEmpty() || this.color.get() == shulkerEntity.getColor());
		}
	}

	@Override
	public MapCodec<ShulkerPredicate> codec() {
		return SpectrumEntitySubPredicateTypes.SHULKER;
	}

}
