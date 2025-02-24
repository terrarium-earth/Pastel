package de.dafuqs.spectrum.registries.client;

import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.util.*;

import java.util.*;
import java.util.function.*;

import static de.dafuqs.spectrum.registries.client.SpectrumTextureKeys.*;
import static net.minecraft.data.client.TextureKey.*;
import static net.minecraft.data.client.TextureMap.*;

public class SpectrumTexturedModels {
	
	public static final TexturedModel.Factory BASE_TRANS_LIGHT_CORE = TexturedModel.makeFactory(b -> new TextureMap().put(CASE, getId(b)).put(BASE, getSubId(b, "_base")).put(GLASS, getSubId(b, "_glass")).put(SHELL, getSubId(b, "_shell")).put(FILAMENT, getSubId(b, "_filament")).put(ENDS, getSubId(b, "_ends")), SpectrumModels.BASE_TRANS_LIGHT_CORE);
	public static final TexturedModel.Factory CHIME = TexturedModel.makeFactory(b -> new TextureMap().put(BASE, SpectrumTextures.BALCITE_CHIME_BASE).put(GEMSTONE, getId(b)), SpectrumModels.CHIME);
	public static final TexturedModel.Factory CUBE_COLUMN_MIRRORED = TexturedModel.makeFactory(TextureMap::sideAndEndForTop, Models.CUBE_COLUMN_MIRRORED);
	public static final TexturedModel.Factory CUSHION = TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottom(b, "_side", b, "_top", b, "_bottom"), SpectrumModels.CUSHION);
	public static final TexturedModel.Factory SHOOTING_STAR = TexturedModel.makeFactory(b -> new TextureMap().put(CORE, getId(b)).put(SIDE, getSubId(b, "_side")), SpectrumModels.SHOOTING_STAR);
	
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
	
	public static TexturedModel.Factory complexOrientable(UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix, UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> frontBlock, String frontSuffix, UnaryOperator<Block> backBlock, String backSuffix, UnaryOperator<Block> particleBlock, String particleSuffix) {
		return TexturedModel.makeFactory(b -> new TextureMap().put(SIDE, getSubId(sideBlock.apply(b), sideSuffix)).put(TOP, getSubId(topBlock.apply(b), topSuffix)).put(BOTTOM, getSubId(bottomBlock.apply(b), bottomSuffix)).put(FRONT, getSubId(frontBlock.apply(b), frontSuffix)).put(BACK, getSubId(backBlock.apply(b), backSuffix)).put(PARTICLE, getSubId(particleBlock.apply(b), particleSuffix)), SpectrumModels.COMPLEX_ORIENTABLE);
	}
	
	public static TexturedModel.Factory farmland(UnaryOperator<Block> dirtBlock, String dirtSuffix, UnaryOperator<Block> topBlock, String topSuffix) {
		return TexturedModel.makeFactory(b -> new TextureMap().put(DIRT, getSubId(dirtBlock.apply(b), dirtSuffix)).put(TOP, getSubId(topBlock.apply(b), topSuffix)), Models.TEMPLATE_FARMLAND);
	}
	
	public static TexturedModel.Factory cross(UnaryOperator<Block> crossBlock, String crossSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.cross(crossBlock.apply(b), crossSuffix), Models.CROSS);
	}
	
	public static TexturedModel.Factory leaves(UnaryOperator<Block> allBlock, String allSuffix) {
		return TexturedModel.makeFactory(b -> SpectrumTextureMaps.all(allBlock.apply(b), allSuffix), Models.LEAVES);
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
		return TexturedModel.makeFactory(b -> new TextureMap().put(GLASS, getSubId(b, "_glass_on")).put(CASE, getSubId(b, "_case_on")), SpectrumModels.baseTransLantern(diagonal, tall));
	}
	
	public static TexturedModel.Factory sugarStick(int age, UnaryOperator<Block> sugarBlock) {
		return TexturedModel.makeFactory(b -> new TextureMap().put(KEY0, getId(sugarBlock.apply(b))).put(KEY1, getId(Blocks.OAK_PLANKS)).put(PARTICLE, getId(sugarBlock.apply(b))), SpectrumModels.sugarStick(age));
	}
	
	public static TexturedModel.Factory parented(Identifier parentModelId) {
		return TexturedModel.makeFactory(b -> new TextureMap(), new Model(Optional.of(parentModelId), Optional.empty()));
	}
	
	public static TexturedModel.Factory carpet(UnaryOperator<Block> woolBlock, String woolSuffix) {
		return TexturedModel.makeFactory(b -> TextureMap.wool(ModelIds.getBlockSubModelId(woolBlock.apply(b), woolSuffix)), Models.CARPET);
	}
	
}
