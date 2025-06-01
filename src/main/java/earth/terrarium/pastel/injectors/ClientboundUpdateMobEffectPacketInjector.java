package earth.terrarium.pastel.injectors;

public interface ClientboundUpdateMobEffectPacketInjector {
	
	default boolean spectrum$isIncurable() {
		return false;
	}
	
}
