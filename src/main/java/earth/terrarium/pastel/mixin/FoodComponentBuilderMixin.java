package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.injectors.FoodComponentBuilderInjector;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FoodProperties.Builder.class)
public abstract class FoodComponentBuilderMixin implements FoodComponentBuilderInjector {
	
	@Shadow
	private float eatSeconds;
	
	@Override
	public FoodProperties.Builder spectrum$setEatSeconds(float eatSeconds) {
		this.eatSeconds = eatSeconds;
		return (FoodProperties.Builder) (Object) this;
	}
	
}
