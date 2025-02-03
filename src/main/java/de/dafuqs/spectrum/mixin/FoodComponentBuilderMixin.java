package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.injectors.*;
import net.minecraft.component.type.*;
import org.spongepowered.asm.mixin.*;

@Mixin(FoodComponent.Builder.class)
public abstract class FoodComponentBuilderMixin implements FoodComponentBuilderInjector {
	
	@Shadow
	private float eatSeconds;
	
	@Override
	public FoodComponent.Builder spectrum$setEatSeconds(float eatSeconds) {
		this.eatSeconds = eatSeconds;
		return (FoodComponent.Builder) (Object) this;
	}
	
}
