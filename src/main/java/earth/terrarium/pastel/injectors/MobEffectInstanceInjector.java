package earth.terrarium.pastel.injectors;

public interface MobEffectInstanceInjector {
	
	default boolean spectrum$isIncurable() {
		return false;
	}
	
	default void spectrum$setIncurable(boolean incurable) {
	}
	
	default void spectrum$setDuration(int newDuration) {
	}
	
	default void spectrum$setAmplifier(int newAmplifier) {
	}
	
}
