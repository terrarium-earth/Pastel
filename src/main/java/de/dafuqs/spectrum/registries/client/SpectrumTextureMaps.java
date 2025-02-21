package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.util.*;

public class SpectrumTextureMaps {
	
	public static TextureMap all(Block allBlock, String allSuffix) {
		return new TextureMap().put(TextureKey.ALL, TextureMap.getSubId(allBlock, allSuffix));
	}
	
	public static TextureMap cross(Block crossBlock, String crossSuffix) {
		return new TextureMap().put(TextureKey.CROSS, TextureMap.getSubId(crossBlock, crossSuffix));
	}
	
	public static TextureMap plant(Block plantBlock, String plantSuffix) {
		return new TextureMap().put(TextureKey.PLANT, TextureMap.getSubId(plantBlock, plantSuffix));
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
	
	public static TextureMap sideTopInside(Identifier side, Identifier top, Identifier inside) {
		return new TextureMap().put(TextureKey.SIDE, side).put(TextureKey.TOP, top).put(TextureKey.INSIDE, inside);
	}
	
	public static TextureMap sideEnd(Block sideBlock, String sideSuffix, Block endBlock, String endSuffix) {
		return new TextureMap()
				.put(TextureKey.SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TextureKey.END, TextureMap.getSubId(endBlock, endSuffix));
	}
	
	public static TextureMap sideLine(Identifier side, Identifier line) {
		return new TextureMap().put(TextureKey.SIDE, side).put(SpectrumTextureKeys.LINE, line);
	}
	
	public static TextureMap dirtTop(Block dirtBlock, String dirtSuffix, Block topBlock, String topSuffix) {
		return new TextureMap()
				.put(TextureKey.DIRT, TextureMap.getSubId(dirtBlock, dirtSuffix))
				.put(TextureKey.TOP, TextureMap.getSubId(topBlock, topSuffix));
	}
	
	public static TextureMap baseGemstone(Identifier base, Identifier gemstone) {
		return new TextureMap().put(SpectrumTextureKeys.BASE, base).put(SpectrumTextureKeys.GEMSTONE, gemstone);
	}
	
}
