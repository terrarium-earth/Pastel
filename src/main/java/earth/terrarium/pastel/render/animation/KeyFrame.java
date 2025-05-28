package earth.terrarium.pastel.render.animation;

@FunctionalInterface
public interface KeyFrame<N extends Number> {
	N at(float tickDelta, long time);
	
	static <N extends Number> KeyFrame<N> simple(N number) {
		return ((tickDelta, time) -> number);
	}
}
