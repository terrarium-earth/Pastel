package earth.terrarium.pastel.injectors;

public interface EntityStatusEffectS2CPacketInjector {
	
	default boolean spectrum$isIncurable() {
		return false;
	}
	
}
