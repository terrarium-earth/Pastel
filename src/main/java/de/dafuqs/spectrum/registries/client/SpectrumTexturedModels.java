package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;

import java.util.function.*;

public class SpectrumTexturedModels {
	
	public static final TexturedModel.Factory CUBE_COLUMN_MIRRORED = TexturedModel.makeFactory(TextureMap::sideAndEndForTop, Models.CUBE_COLUMN_MIRRORED);
	public static final TexturedModel.Factory CHIME = TexturedModel.makeFactory(b -> SpectrumTextureMaps.baseGemstone(SpectrumTextures.BALCITE_CHIME_BASE, TextureMap.getId(b)), SpectrumModels.CHIME);
	public static final TexturedModel.Factory CUSHION = TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottom(b, "_side", b, "_top", b, "_bottom"), SpectrumModels.CUSHION);
	
	public static TexturedModel.Factory cubeColumn(UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> endBlock, String endSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideEnd(sideBlock.apply(b), sideSuffix, endBlock.apply(b), endSuffix), Models.CUBE_COLUMN);
	}
	
	public static TexturedModel.Factory cubeBottomTopWall(UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix, UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> wallBlock, String wallSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottomWall(sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix, bottomBlock.apply(b), bottomSuffix, wallBlock.apply(b), wallSuffix), SpectrumModels.CUBE_BOTTOM_TOP_WALL);
	}
	
	public static TexturedModel.Factory cubeBottomTopParticle(UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix, UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> particleBlock, String particleSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottomParticle(sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix, bottomBlock.apply(b), bottomSuffix, particleBlock.apply(b), particleSuffix), SpectrumModels.CUBE_BOTTOM_TOP_PARTICLE);
	}
	
	public static TexturedModel.Factory farmland(UnaryOperator<Block> dirtBlock, String dirtSuffix, UnaryOperator<Block> topBlock, String topSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.dirtTop(dirtBlock.apply(b), dirtSuffix, topBlock.apply(b), topSuffix), Models.TEMPLATE_FARMLAND);
	}
	
	public static TexturedModel.Factory cross(UnaryOperator<Block> crossBlock, String crossSuffix) {
		return TexturedModel.makeFactory(b -> TextureMap.cross(TextureMap.getSubId(crossBlock.apply(b), crossSuffix)), Models.CROSS);
	}
	
}
