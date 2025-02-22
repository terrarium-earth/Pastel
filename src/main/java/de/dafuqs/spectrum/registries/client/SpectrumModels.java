package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.*;
import net.minecraft.data.client.*;
import net.minecraft.util.*;

import java.util.*;

public class SpectrumModels {
	// Item Templates
	public static final Model CLUSTER_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/cluster")), Optional.empty(), TextureKey.LAYER0);
	public static final Model LARGE_BUD_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/large_bud")), Optional.empty(), TextureKey.LAYER0);
	public static final Model MEDIUM_BUD_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/medium_bud")), Optional.empty(), TextureKey.LAYER0);
	public static final Model SMALL_BUD_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/small_bud")), Optional.empty(), TextureKey.LAYER0);
	
	// Block Templates
	public static final Model BASE_FLAT_LIGHT = new Model(Optional.of(SpectrumCommon.locate("templates/base_flat_light")), Optional.empty(), SpectrumTextureKeys.INNER, SpectrumTextureKeys.OUTER, TextureKey.PARTICLE);
	public static final Model BASE_FLAT_LIGHT_MIRRORED = new Model(Optional.of(SpectrumCommon.locate("templates/base_flat_light_mirrored")), Optional.empty(), SpectrumTextureKeys.INNER, SpectrumTextureKeys.OUTER, TextureKey.PARTICLE);
	public static final Model CHIME = new Model(Optional.of(SpectrumCommon.locate("templates/chime")), Optional.empty(), SpectrumTextureKeys.BASE, SpectrumTextureKeys.GEMSTONE);
	public static final Model COLORED_LAMP_ON = new Model(Optional.of(SpectrumCommon.locate("templates/colored_lamp_on")), Optional.empty(), SpectrumTextureKeys.INNER, SpectrumTextureKeys.OUTER);
	public static final Model CRYSTALLARIEUM_FARMABLE = new Model(Optional.of(SpectrumCommon.locate("templates/crystallarieum_farmable")), Optional.empty(), TextureKey.CROSS);
	public static final Model MOONSTONE_CHISELED = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled")), Optional.empty(), TextureKey.SIDE, SpectrumTextureKeys.LINE);
	public static final Model MOONSTONE_CHISELED_DOWN = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled_down")), Optional.empty(), TextureKey.SIDE, SpectrumTextureKeys.LINE);
	public static final Model MULTILAYER_LIGHT = new Model(Optional.of(SpectrumCommon.locate("templates/multilayer_light")), Optional.empty(), TextureKey.TOP, TextureKey.SIDE, TextureKey.INSIDE);
	public static final Model TRANSLUCENT_OUTER1 = new Model(Optional.of(SpectrumCommon.locate("templates/translucent_outer1")), Optional.empty(), TextureKey.TEXTURE);
	
	public static Model noxwoodLantern(String suffix) {
		return new Model(Optional.of(SpectrumCommon.locate("templates/noxwood_lamp" + suffix)), Optional.empty(), TextureKey.ALL);
	}
	
	// Block Builtins w/ Extra Keys
	public static final Model CUBE_BOTTOM_TOP_PARTICLE = new Model(Optional.of(Identifier.ofVanilla("block/cube_bottom_top")), Optional.empty(), TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.PARTICLE);
	public static final Model CUBE_BOTTOM_TOP_WALL = new Model(Optional.of(Identifier.ofVanilla("block/cube_bottom_top")), Optional.empty(), TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.WALL);
	
	// Vanilla
	public static final Model SPORE_BLOSSOM = new Model(Optional.of(Identifier.ofVanilla("block/spore_blossom")), Optional.empty(), SpectrumTextureKeys.FLOWER, TextureKey.PARTICLE);
	
}
