package de.dafuqs.spectrum.api.energy.color;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.tag.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.joml.*;

import java.util.*;

public class InkColor {
	
	public static final Codec<InkColor> CODEC = CodecHelper.SPECTRUM_DEFAULTED_IDENTIFIER.comapFlatMap(
			id -> ofId(id).map(DataResult::success).orElse(DataResult.error(() -> "Not a valid ink color: " + id)),
			InkColor::getID
	);

	public static final PacketCodec<ByteBuf, InkColor> PACKET_CODEC = Identifier.PACKET_CODEC.xmap(
			id -> ofId(id).orElseThrow(),
			InkColor::getID
	);
	
	protected static final Map<DyeColor, InkColor> DYE_TO_COLOR = new HashMap<>();
	
	protected final DyeColor dyeColor;
	protected final int colorInt;
	protected final Vector3f colorVec;
	protected final int textColor;
	protected final Vector3f textColorVec;
	
	protected final Identifier requiredAdvancement;
	
	protected InkColor(DyeColor dyeColor, int color, Identifier requiredAdvancement) {
		this(dyeColor, color, color, requiredAdvancement);
	}
	
	protected InkColor(DyeColor dyeColor, int color, int textColor, Identifier requiredAdvancement) {
		this.dyeColor = dyeColor;
		this.colorInt = color;
		this.colorVec = SpectrumColorHelper.colorIntToVec(color);
		this.textColor = textColor;
		this.textColorVec = SpectrumColorHelper.colorIntToVec(textColor);
		this.requiredAdvancement = requiredAdvancement;
		
		DYE_TO_COLOR.put(dyeColor, this);
	}
	
	public static InkColor ofDyeColor(DyeColor dyeColor) {
		return DYE_TO_COLOR.get(dyeColor);
	}
	
	public static Optional<InkColor> ofId(Identifier id) {
		return SpectrumRegistries.INK_COLOR.getOrEmpty(id);
	}
	
	public static Optional<InkColor> ofIdString(String idString) {
		return SpectrumRegistries.INK_COLOR.getOrEmpty(Identifier.of(idString));
	}
	
	public DyeColor getDyeColor() {
		return this.dyeColor;
	}
	
	@Override
	public String toString() {
		return this.getID().toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InkColor that = (InkColor) o;
		return this.dyeColor.equals(that.dyeColor);
	}
	
	// hash lookup go wheeeeee!
	@Override
	public int hashCode() {
		return dyeColor.getId();
	}
	
	public MutableText getName() {
		return Text.translatable(this.getID().toTranslationKey("ink", "name"));
	}
	
	public MutableText getColoredName() {
		return getName().setStyle(Style.EMPTY.withColor(textColor));
	}
	
	public MutableText getColoredInkName() {
		return Text.translatable("ink.suffix", getName()).setStyle(Style.EMPTY.withColor(textColor));
	}
	
	public Vector3f getColorVec() {
		return this.colorVec;
	}
	
	public int getColorInt() {
		return this.colorInt;
	}
	
	public int getTextColorInt() {
		return this.textColor;
	}
	
	public Vector3f getTextColorVec() {
		return this.textColorVec;
	}
	
	public Identifier getRequiredAdvancement() {
		return requiredAdvancement;
	}
	
	public Identifier getID() {
		return SpectrumRegistries.INK_COLOR.getId(this);
	}
	
	public boolean isIn(TagKey<InkColor> tag) {
		return SpectrumRegistries.INK_COLOR.getEntry(this).isIn(tag);
	}
	
}
