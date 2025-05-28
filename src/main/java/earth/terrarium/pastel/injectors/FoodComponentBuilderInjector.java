package earth.terrarium.pastel.injectors;

import net.minecraft.world.food.FoodProperties;
import org.apache.commons.lang3.NotImplementedException;

public interface FoodComponentBuilderInjector {
	
	default FoodProperties.Builder spectrum$setEatSeconds(float eatSeconds) {
		throw new NotImplementedException();
	}
	
}
