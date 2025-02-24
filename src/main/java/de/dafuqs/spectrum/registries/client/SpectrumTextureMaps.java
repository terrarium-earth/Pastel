package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import static de.dafuqs.spectrum.registries.client.SpectrumTextureKeys.*;
import static net.minecraft.data.client.TextureKey.*;
import static net.minecraft.data.client.TextureMap.*;

public class SpectrumTextureMaps {
	
	public static TextureMap all(Block allBlock, String allSuffix) {
		return all(getSubId(allBlock, allSuffix));
	}
	
	public static TextureMap all(Identifier all) {
		return new TextureMap().put(ALL, all);
	}
	
	public static TextureMap cross(Block crossBlock, String crossSuffix) {
		return new TextureMap().put(CROSS, getSubId(crossBlock, crossSuffix));
	}
	
	public static TextureMap flowerParticle(Block flowerBlock, String flowerSuffix, Block particleBlock, String particleSuffix) {
		return flowerParticle(getSubId(flowerBlock, flowerSuffix), getSubId(particleBlock, particleSuffix));
	}
	
	public static TextureMap flowerParticle(Identifier flower, Identifier particle) {
		return new TextureMap().put(FLOWER, flower).put(PARTICLE, particle);
	}
	
	public static TextureMap innerOuter(Block innerBlock, String innerSuffix, Block outerBlock, String outerSuffix) {
		return innerOuter(getSubId(innerBlock, innerSuffix), getSubId(outerBlock, outerSuffix));
	}
	
	public static TextureMap innerOuter(Identifier inner, Identifier outer) {
		return new TextureMap().put(INNER, inner).put(OUTER, outer);
	}
	
	public static TextureMap innerOuterParticle(Identifier inner, Identifier outer, Identifier particle) {
		return new TextureMap().put(INNER, inner).put(OUTER, outer).put(PARTICLE, particle);
	}
	
	public static TextureMap layer0(Item layer0Item, String layer0Suffix) {
		return new TextureMap().put(LAYER0, getSubId(layer0Item, layer0Suffix));
	}
	
	public static TextureMap layer0(Block layer0Block, String layer0Suffix) {
		return new TextureMap().put(LAYER0, getSubId(layer0Block, layer0Suffix));
	}
	
	public static TextureMap plant(Block plantBlock, String plantSuffix) {
		return new TextureMap().put(PLANT, getSubId(plantBlock, plantSuffix));
	}
	
	public static TextureMap sideEnd(Block sideBlock, String sideSuffix, Block endBlock, String endSuffix) {
		return sideEnd(getSubId(sideBlock, sideSuffix), getSubId(endBlock, endSuffix));
	}
	
	public static TextureMap sideEnd(Identifier side, Identifier end) {
		return new TextureMap().put(SIDE, side).put(END, end);
	}
	
	public static TextureMap sideLine(Identifier side, Identifier line) {
		return new TextureMap().put(SIDE, side).put(LINE, line);
	}
	
	public static TextureMap sideTop(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix) {
		return new TextureMap().put(SIDE, getSubId(sideBlock, sideSuffix)).put(TOP, getSubId(topBlock, topSuffix));
	}
	
	public static TextureMap sideTopBottom(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix) {
		return new TextureMap().put(SIDE, getSubId(sideBlock, sideSuffix)).put(TOP, getSubId(topBlock, topSuffix)).put(BOTTOM, getSubId(bottomBlock, bottomSuffix));
	}
	
	public static TextureMap sideTopBottomFronds(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block frondsBlock, String frondsSuffix) {
		return new TextureMap().put(SIDE, getSubId(sideBlock, sideSuffix)).put(TOP, getSubId(topBlock, topSuffix)).put(BOTTOM, getSubId(bottomBlock, bottomSuffix)).put(FRONDS, getSubId(frondsBlock, frondsSuffix));
	}
	
	public static TextureMap sideTopBottomParticle(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block particleBlock, String particleSuffix) {
		return new TextureMap().put(SIDE, getSubId(sideBlock, sideSuffix)).put(TOP, getSubId(topBlock, topSuffix)).put(BOTTOM, getSubId(bottomBlock, bottomSuffix)).put(PARTICLE, getSubId(particleBlock, particleSuffix));
	}
	
	public static TextureMap sideTopBottomWall(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block bottomBlock, String bottomSuffix, Block wallBlock, String wallSuffix) {
		return new TextureMap().put(SIDE, getSubId(sideBlock, sideSuffix)).put(TOP, getSubId(topBlock, topSuffix)).put(BOTTOM, getSubId(bottomBlock, bottomSuffix)).put(WALL, getSubId(wallBlock, wallSuffix));
	}
	
	public static TextureMap sideTopInside(Block sideBlock, String sideSuffix, Block topBlock, String topSuffix, Block insideBlock, String insideSuffix) {
		return sideTopInside(getSubId(sideBlock, sideSuffix), getSubId(topBlock, topSuffix), getSubId(insideBlock, insideSuffix));
	}
	
	public static TextureMap sideTopInside(Identifier side, Identifier top, Identifier inside) {
		return new TextureMap().put(SIDE, side).put(TOP, top).put(INSIDE, inside);
	}
	
}
