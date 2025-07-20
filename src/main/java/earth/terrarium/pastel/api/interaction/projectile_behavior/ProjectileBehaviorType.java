package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

public record ProjectileBehaviorType<T extends ItemProjectileBehavior>(ResourceLocation id, MapCodec<T> codec) {
}
