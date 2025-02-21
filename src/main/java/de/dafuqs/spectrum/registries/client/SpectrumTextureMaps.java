package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;

public class SpectrumTextureMaps {
	
	public static TextureMap sideEnd(Block sideBlock, String sideSuffix, Block endBlock, String endSuffix) {
		return new TextureMap()
				.put(TextureKey.SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TextureKey.END, TextureMap.getSubId(endBlock, endSuffix));
	}
	
	public static TextureMap sideTopBottomWall(Block block, String sideSuffix, String topSuffix, String bottomSuffix, String wallSuffix) {
		return new TextureMap()
				.put(TextureKey.SIDE, TextureMap.getSubId(block, sideSuffix))
				.put(TextureKey.TOP, TextureMap.getSubId(block, topSuffix))
				.put(TextureKey.BOTTOM, TextureMap.getSubId(block, bottomSuffix))
				.put(TextureKey.WALL, TextureMap.getSubId(block, wallSuffix));
	}
	
}
