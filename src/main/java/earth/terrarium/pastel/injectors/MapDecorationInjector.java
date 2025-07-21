package earth.terrarium.pastel.injectors;

public interface MapDecorationInjector {

    default void setScale(byte scale) {
    }

    default byte getScale() {
        return 0;
    }

}
