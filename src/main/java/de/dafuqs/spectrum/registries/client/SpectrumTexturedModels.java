package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;

import java.util.function.*;

import static net.minecraft.data.client.TextureMap.*;

public class SpectrumTexturedModels {
	
	public static final TexturedModel.Factory CUBE_COLUMN_MIRRORED = TexturedModel.makeFactory(TextureMap::sideAndEndForTop, Models.CUBE_COLUMN_MIRRORED);
	public static final TexturedModel.Factory CHIME = TexturedModel.makeFactory(b -> SpectrumTextureMaps.baseGemstone(SpectrumTextures.BALCITE_CHIME_BASE, getId(b)), SpectrumModels.CHIME);
	public static final TexturedModel.Factory CUSHION = TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottom(b, "_side", b, "_top", b, "_bottom"), SpectrumModels.CUSHION);
	public static final TexturedModel.Factory BASE_TRANS_LIGHT_CORE = TexturedModel.makeFactory(b -> new TextureMap().put(SpectrumTextureKeys.CASE, getId(b)).put(SpectrumTextureKeys.BASE, getSubId(b, "_base")).put(SpectrumTextureKeys.GLASS, getSubId(b, "_glass")).put(SpectrumTextureKeys.SHELL, getSubId(b, "_shell")).put(SpectrumTextureKeys.FILAMENT, getSubId(b, "_filament")).put(SpectrumTextureKeys.ENDS, getSubId(b, "_ends")), SpectrumModels.BASE_TRANS_LIGHT_CORE);
	
	public static TexturedModel.Factory cubeAll(UnaryOperator<Block> allBlock, String allSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.all(allBlock.apply(b), allSuffix), Models.CUBE_ALL);
	}
	
	public static TexturedModel.Factory cubeColumn(UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> endBlock, String endSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideEnd(sideBlock.apply(b), sideSuffix, endBlock.apply(b), endSuffix), Models.CUBE_COLUMN);
	}
	
	public static TexturedModel.Factory cubeBottomTop(UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix, UnaryOperator<Block> bottomBlock, String bottomSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottom(sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix, bottomBlock.apply(b), bottomSuffix), Models.CUBE_BOTTOM_TOP);
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
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.cross(crossBlock.apply(b), crossSuffix), Models.CROSS);
	}
	
	public static TexturedModel.Factory doubleCross(UnaryOperator<Block> crossBlock, String crossSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.cross(crossBlock.apply(b), crossSuffix), SpectrumModels.DOUBLE_CROSS);
	}
	
	public static TexturedModel.Factory tintableCross(UnaryOperator<Block> crossBlock, String crossSuffix, boolean tinted) {
		BlockStateModelGenerator.TintType tintType = tinted ? BlockStateModelGenerator.TintType.TINTED : BlockStateModelGenerator.TintType.NOT_TINTED;
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.cross(crossBlock.apply(b), crossSuffix), tintType.getCrossModel());
	}
	
	public static TexturedModel.Factory flowerPotCross(UnaryOperator<Block> plantBlock, String plantSuffix, boolean tinted) {
		BlockStateModelGenerator.TintType tintType = tinted ? BlockStateModelGenerator.TintType.TINTED : BlockStateModelGenerator.TintType.NOT_TINTED;
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.plant(plantBlock.apply(b), plantSuffix), tintType.getFlowerPotCrossModel());
	}
	
	public static TexturedModel.Factory overgrown(UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix, UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> frondsBlock, String frondsSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottomFronds(sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix, bottomBlock.apply(b), bottomSuffix, frondsBlock.apply(b), frondsSuffix), SpectrumModels.OVERGROWN);
	}
	
	public static TexturedModel.Factory baseTransLantern(boolean diagonal, boolean tall) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.glassCase(b, "_glass_on", b, "_case_on"), SpectrumModels.baseTransLantern(diagonal, tall));
	}
	
}
