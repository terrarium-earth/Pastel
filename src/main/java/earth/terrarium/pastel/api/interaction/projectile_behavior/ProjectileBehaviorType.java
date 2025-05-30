package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.*;
import net.minecraft.resources.*;

public record ProjectileBehaviorType<T extends ItemProjectileBehavior>(ResourceLocation id, MapCodec<T> codec) {
}
