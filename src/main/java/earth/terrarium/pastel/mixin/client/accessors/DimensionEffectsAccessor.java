package earth.terrarium.pastel.mixin.client.accessors;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionSpecialEffects.class)
public interface DimensionEffectsAccessor {
	
	@Accessor
	static Object2ObjectMap<ResourceLocation, DimensionSpecialEffects> getEFFECTS() {
		throw new AssertionError();
	}
	
}