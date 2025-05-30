package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.*;
import net.minecraft.resources.*;

import java.util.*;

public class ItemProjectileBehaviorRegistry {
	public static final Codec<ProjectileBehaviorType<?>> TYPE_CODEC = ResourceLocation.CODEC.comapFlatMap(ItemProjectileBehaviorRegistry::decode, ProjectileBehaviorType::id);
	public static final Codec<ItemProjectileBehavior> CODEC = TYPE_CODEC.dispatch(ItemProjectileBehavior::type, ProjectileBehaviorType::codec);
	
	private static final Map<ResourceLocation, ProjectileBehaviorType<?>> BEHAVIOR_TYPES = new HashMap<>();
	
	public static void register(ProjectileBehaviorType<?> type) {
		if (BEHAVIOR_TYPES.containsKey(type.id())) {
			throw new IllegalArgumentException("Projectile behavior type with id " + type.id() + " is already registered.");
		}
		BEHAVIOR_TYPES.put(type.id(), type);
	}
	
	private static DataResult<? extends ProjectileBehaviorType<?>> decode(ResourceLocation id) {
		return Optional.ofNullable(BEHAVIOR_TYPES.get(id)).map(DataResult::success).orElse(DataResult.error(() -> "No trait type found."));
	}
}
