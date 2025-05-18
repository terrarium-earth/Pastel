package de.dafuqs.spectrum.api.interaction;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityColorProcessorRegistry {
	
	private static final Map<EntityType<?>, TriFunction<Entity, Optional<DyeColor>, Player, Boolean>> PROCESSOR = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <E extends Entity> void register(EntityType<E> entityType, EntityColorProcessor<E> processor) {
		TriFunction<Entity, Optional<DyeColor>, Player, Boolean> ttt = (entity, dyeColor, player) -> processor.colorEntity((E) entity, dyeColor, player);
		PROCESSOR.put(entityType, ttt);
	}
	
	public static boolean colorEntity(Entity entity, Optional<DyeColor> dyeColor, @Nullable Player player) {
		@Nullable TriFunction<Entity, Optional<DyeColor>, Player, Boolean> colorProcessor = PROCESSOR.getOrDefault(entity.getType(), null);
		if (colorProcessor != null) {
			return colorProcessor.apply(entity, dyeColor, player);
		}
		return false;
	}
	
}
