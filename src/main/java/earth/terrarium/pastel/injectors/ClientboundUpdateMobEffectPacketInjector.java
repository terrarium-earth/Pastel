package earth.terrarium.pastel.injectors;

public interface ClientboundUpdateMobEffectPacketInjector {

    default boolean isIncurable() {
        return false;
    }

}
