package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.color.*;
import org.jetbrains.annotations.*;

import java.util.*;

// TODO - Review
public record ColorPredicate(Optional<InkColor> color) {
	
	public static final Codec<ColorPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			InkColor.CODEC.optionalFieldOf("color").forGetter(ColorPredicate::color)
	).apply(instance, ColorPredicate::new));
	
	public static final ColorPredicate ANY;
	
	static {
		ANY = new ColorPredicate(Optional.empty());
	}
	
	public boolean test(InkColor color) {
		if (this == ANY || color == null) {
			return true;
		}
		return this.color.isPresent() && this.color.get().equals(color);
	}
	
	public static class Builder {
		
		@Nullable
		private InkColor color;
		
		private Builder() {
			this.color = null;
		}
		
		public static ColorPredicate.Builder create() {
			return new ColorPredicate.Builder();
		}
		
		public ColorPredicate.Builder color(InkColor color) {
			this.color = color;
			return this;
		}
		
		public ColorPredicate build() {
			return new ColorPredicate(Optional.ofNullable(this.color));
		}
	}
}
