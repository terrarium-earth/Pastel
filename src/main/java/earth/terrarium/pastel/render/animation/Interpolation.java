package earth.terrarium.pastel.render.animation;

import net.minecraft.util.Mth;

import java.util.function.Function;

@FunctionalInterface
public interface Interpolation {
    Interpolation LINEAR = (start, end, delta) -> Mth.lerp(delta, start, end);

    Interpolation CLAMPED = Mth::clampedLerp;
    // You know, it is kind of fucked up that clamped lerp and lerp don't have the same signature

    Interpolation CUBIC_IN = normalize(delta -> Math.pow(delta, 3));

    Interpolation CUBIC_OUT = normalize(delta -> 1 - Math.pow(1 - delta, 3));

    double apply(double start, double end, float delta);

    static Interpolation normalize(Function<Float, Number> rawDelta) {
        return (start, end, delta) -> Mth
            .lerp(
                rawDelta
                    .apply(delta)
                    .floatValue(),
                start,
                end
            );
    }
}
