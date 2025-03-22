package de.dafuqs.spectrum.render.animation;

import net.minecraft.util.math.*;

@FunctionalInterface
public interface Interpolation {
	public static final Interpolation LINEAR = (start, end, delta) -> MathHelper.lerp(delta, start, end);
	public static final Interpolation CLAMPED = MathHelper::clampedLerp;
	// You know, it is kind of fucked up that clamped lerp and lerp don't have the same signature
	
	double apply(double start, double end, float delta);
}
