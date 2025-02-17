package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.energy.color.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.*;

import java.util.*;

public class PigmentBlock extends Block {

	public static final MapCodec<PigmentBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			createSettingsCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(PigmentBlock::getColor)
	).apply(i, PigmentBlock::new));
	
	private static final Map<InkColor, PigmentBlock> BLOCKS = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public PigmentBlock(Settings settings, InkColor color) {
		super(settings);
		this.color = color;
		BLOCKS.put(color, this);
	}

	@Override
	public MapCodec<? extends PigmentBlock> getCodec() {
		return CODEC;
	}
	
	public InkColor getColor() {
		return this.color;
	}
	
	public static PigmentBlock byColor(InkColor color) {
		return BLOCKS.get(color);
	}
	
}
