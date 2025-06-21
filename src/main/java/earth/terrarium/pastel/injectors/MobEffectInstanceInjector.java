package earth.terrarium.pastel.injectors;

public interface MobEffectInstanceInjector {
	
	default boolean isIncurable() {
		return false;
	}
	
	default void setIncurable(boolean incurable) {
	}
	
	default void setDuration(int newDuration) {
	}
	
	default void setAmplifier(int newAmplifier) {
	}
	
}
