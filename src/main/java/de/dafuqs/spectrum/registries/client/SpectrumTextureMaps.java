package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import static de.dafuqs.spectrum.registries.client.SpectrumTextureKeys.*;
import static net.minecraft.data.client.TextureKey.*;

public class SpectrumTextureMaps {
	
	public static TextureMap all(Block allBlock, String allSuffix) {
		return new TextureMap().put(ALL, TextureMap.getSubId(allBlock, allSuffix));
	}
	
	public static TextureMap layer0(Item layer0Item, String layer0Suffix) {
		return new TextureMap().put(LAYER0, TextureMap.getSubId(layer0Item, layer0Suffix));
	}
	
	public static TextureMap cross(Block crossBlock, String crossSuffix) {
		return new TextureMap().put(CROSS, TextureMap.getSubId(crossBlock, crossSuffix));
	}
	
	public static TextureMap plant(Block plantBlock, String plantSuffix) {
		return new TextureMap().put(PLANT, TextureMap.getSubId(plantBlock, plantSuffix));
	}
	
	public static TextureMap flowerParticle(Block flowerBlock, String flowerSuffix, Block particleBlock, String particleSuffix) {
		return new TextureMap()
				.put(FLOWER, TextureMap.getSubId(flowerBlock, flowerSuffix))
				.put(PARTICLE, TextureMap.getSubId(particleBlock, particleSuffix));
	}
	
	public static TextureMap sideTopBottom(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix) {
		return new TextureMap()
				.put(SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TOP, TextureMap.getSubId(topBlock, topSuffix))
				.put(BOTTOM, TextureMap.getSubId(bottomBlock, bottomSuffix));
	}
	
	public static TextureMap sideTopBottomWall(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block wallBlock, String wallSuffix) {
		return new TextureMap()
				.put(SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TOP, TextureMap.getSubId(topBlock, topSuffix))
				.put(BOTTOM, TextureMap.getSubId(bottomBlock, bottomSuffix))
				.put(WALL, TextureMap.getSubId(wallBlock, wallSuffix));
	}
	
	public static TextureMap sideTopBottomParticle(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block particleBlock, String particleSuffix) {
		return new TextureMap()
				.put(SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TOP, TextureMap.getSubId(topBlock, topSuffix))
				.put(BOTTOM, TextureMap.getSubId(bottomBlock, bottomSuffix))
				.put(PARTICLE, TextureMap.getSubId(particleBlock, particleSuffix));
	}
	
	public static TextureMap sideTopBottomFronds(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block frondsBlock, String frondsSuffix) {
		return new TextureMap()
				.put(SIDE, TextureMap.getSubId(sideBlock, sideSuffix))
				.put(TOP, TextureMap.getSubId(topBlock, topSuffix))
				.put(BOTTOM, TextureMap.getSubId(bottomBlock, bottomSuffix))
				.put(FRONDS, TextureMap.getSubId(frondsBlock, frondsSuffix));
	}
	
	public static TextureMap sideTopInside(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block insideBlock, String insideSuffix) {
		return sideTopInside(TextureMap.getSubId(sideBlock, sideSuffix), TextureMap.getSubId(topBlock, topSuffix), TextureMap.getSubId(insideBlock, insideSuffix));
	}
	
	public static TextureMap sideTopInside(Identifier side, Identifier top, Identifier inside) {
		return new TextureMap().put(SIDE, side).put(TOP, top).put(INSIDE, inside);
	}
	
	public static TextureMap sideEnd(Block sideBlock, String sideSuffix, Block endBlock, String endSuffix) {
		return sideEnd(TextureMap.getSubId(sideBlock, sideSuffix), TextureMap.getSubId(endBlock, endSuffix));
	}
	
	public static TextureMap sideEnd(Identifier side, Identifier end) {
		return new TextureMap().put(SIDE, side).put(END, end);
	}
	
	public static TextureMap sideLine(Identifier side, Identifier line) {
		return new TextureMap().put(SIDE, side).put(LINE, line);
	}
	
	public static TextureMap innerOuter(Block innerBlock, String innerSuffix, Block outerBlock, String outerSuffix) {
		return innerOuter(TextureMap.getSubId(innerBlock, innerSuffix), TextureMap.getSubId(outerBlock, outerSuffix));
	}
	
	public static TextureMap innerOuter(Identifier inner, Identifier outer) {
		return new TextureMap().put(INNER, inner).put(OUTER, outer);
	}
	
	public static TextureMap innerOuterParticle(Identifier inner, Identifier outer, Identifier particle) {
		return new TextureMap().put(INNER, inner).put(OUTER, outer).put(PARTICLE, particle);
	}
	
	public static TextureMap dirtTop(Block dirtBlock, String dirtSuffix, Block topBlock, String topSuffix) {
		return new TextureMap()
				.put(DIRT, TextureMap.getSubId(dirtBlock, dirtSuffix))
				.put(TOP, TextureMap.getSubId(topBlock, topSuffix));
	}
	
	public static TextureMap baseGemstone(Identifier base, Identifier gemstone) {
		return new TextureMap().put(BASE, base).put(GEMSTONE, gemstone);
	}
	
	public static TextureMap glassCase(Block glassBlock, String glassSuffix, Block caseBlock, String caseSuffix) {
		return new TextureMap()
				.put(GLASS, TextureMap.getSubId(glassBlock, glassSuffix))
				.put(CASE, TextureMap.getSubId(caseBlock, caseSuffix));
	}
	
}
