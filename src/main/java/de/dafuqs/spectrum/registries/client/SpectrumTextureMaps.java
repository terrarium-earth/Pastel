package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;

public class SpectrumTextureMaps {
	
	public static TextureMap sideTopAndBottomAsTop(Block block) {
		return TextureMap.of(TextureKey.SIDE, TextureMap.getId(block))
				.put(TextureKey.TOP, TextureMap.getSubId(block, "_top"))
				.put(TextureKey.BOTTOM, TextureMap.getSubId(block, "_top"));
	}
	
}
