package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;

public class SpectrumTextureMaps {
	
	public static TextureMap sideEnd(Block sideBlock, String sideSuffix, Block endBlock, String endSuffix) {
		return new TextureMap()
				.put(TextureKey.SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TextureKey.END, TextureMap.getSubId(endBlock, endSuffix));
	}
	
	public static TextureMap sideTopBottomWall(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block wallBlock, String wallSuffix) {
		return new TextureMap()
				.put(TextureKey.SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TextureKey.TOP, TextureMap.getSubId(topBlock, topSuffix))
				.put(TextureKey.BOTTOM, TextureMap.getSubId(bottomBlock, bottomSuffix))
				.put(TextureKey.WALL, TextureMap.getSubId(wallBlock, wallSuffix));
	}
	
	public static TextureMap sideTopBottomParticle(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block particleBlock, String particleSuffix) {
		return new TextureMap()
				.put(TextureKey.SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TextureKey.TOP, TextureMap.getSubId(topBlock, topSuffix))
				.put(TextureKey.BOTTOM, TextureMap.getSubId(bottomBlock, bottomSuffix))
				.put(TextureKey.PARTICLE, TextureMap.getSubId(particleBlock, particleSuffix));
	}
	
	public static TextureMap dirtTop(Block dirtBlock, String dirtSuffix, Block topBlock, String topSuffix) {
		return new TextureMap()
				.put(TextureKey.DIRT, TextureMap.getSubId(dirtBlock, dirtSuffix))
				.put(TextureKey.TOP, TextureMap.getSubId(topBlock, topSuffix));
	}
	
}
