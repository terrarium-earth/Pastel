package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.*;
import net.minecraft.data.client.*;
import net.minecraft.util.*;

import java.util.*;

import static de.dafuqs.spectrum.registries.client.SpectrumTextureKeys.*;
import static net.minecraft.data.client.TextureKey.*;

public class SpectrumModels {
	// Item Templates
	public static final Model CLUSTER_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/cluster")), Optional.empty(), LAYER0);
	public static final Model LARGE_BUD_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/large_bud")), Optional.empty(), LAYER0);
	public static final Model MEDIUM_BUD_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/medium_bud")), Optional.empty(), LAYER0);
	public static final Model SMALL_BUD_ITEM = new Model(Optional.of(SpectrumCommon.locate("templates_item/small_bud")), Optional.empty(), LAYER0);
	
	// Block Templates
	public static final Model BASE_FLAT_LIGHT = new Model(Optional.of(SpectrumCommon.locate("templates/base_flat_light")), Optional.empty(), INNER, OUTER, PARTICLE);
	public static final Model BASE_FLAT_LIGHT_MIRRORED = new Model(Optional.of(SpectrumCommon.locate("templates/base_flat_light_mirrored")), Optional.empty(), INNER, OUTER, PARTICLE);
	public static final Model BASE_TRANS_LIGHT_CORE = new Model(Optional.of(SpectrumCommon.locate("templates/base_trans_light_core")), Optional.empty(), GLASS, CASE, BASE, ENDS, SHELL, FILAMENT);
	public static final Model CHIME = new Model(Optional.of(SpectrumCommon.locate("templates/chime")), Optional.empty(), BASE, GEMSTONE);
	public static final Model CUSHION = new Model(Optional.of(SpectrumCommon.locate("templates/cushion")), Optional.empty(), SIDE, TOP, BOTTOM);
	public static final Model COLORED_LAMP_ON = new Model(Optional.of(SpectrumCommon.locate("templates/colored_lamp_on")), Optional.empty(), INNER, OUTER);
	public static final Model CRYSTALLARIEUM_FARMABLE = new Model(Optional.of(SpectrumCommon.locate("templates/crystallarieum_farmable")), Optional.empty(), CROSS);
	public static final Model DOUBLE_CROSS = new Model(Optional.of(SpectrumCommon.locate("templates/double_cross")), Optional.empty(), CROSS);
	public static final Model MOONSTONE_CHISELED = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled")), Optional.empty(), SIDE, LINE);
	public static final Model MOONSTONE_CHISELED_DOWN = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled_down")), Optional.empty(), SIDE, LINE);
	public static final Model MULTILAYER_LIGHT = new Model(Optional.of(SpectrumCommon.locate("templates/multilayer_light")), Optional.empty(), SIDE, TOP, INSIDE);
	public static final Model OVERGROWN = new Model(Optional.of(SpectrumCommon.locate("templates/overgrown")), Optional.empty(), SIDE, TOP, BOTTOM, FRONDS);
	public static final Model TRANSLUCENT_OUTER1 = new Model(Optional.of(SpectrumCommon.locate("templates/translucent_outer1")), Optional.empty(), TEXTURE);
	
	public static final Identifier GIANT_MOSS_BALL = SpectrumCommon.locate("block/giant_moss_ball");
	public static final Identifier MOSS_BALL = SpectrumCommon.locate("block/moss_ball");
	public static final Identifier MOSS_BALL_TUFT = SpectrumCommon.locate("block/moss_ball_tuft");
	public static final Identifier SNAPPING_IVY = SpectrumCommon.locate("block/snapping_ivy");
	public static final Identifier SNAPPING_IVY_SNAPPED = SpectrumCommon.locate("block/snapping_ivy_snapped");
	
	public static Model noxwoodLantern(String suffix) {
		return new Model(Optional.of(SpectrumCommon.locate("templates/noxwood_lamp" + suffix)), Optional.empty(), ALL);
	}
	
	// Block Builtins w/ Extra Keys
	public static final Model CUBE_BOTTOM_TOP_PARTICLE = new Model(Optional.of(Identifier.ofVanilla("block/cube_bottom_top")), Optional.empty(), TOP, BOTTOM, SIDE, PARTICLE);
	public static final Model CUBE_BOTTOM_TOP_WALL = new Model(Optional.of(Identifier.ofVanilla("block/cube_bottom_top")), Optional.empty(), TOP, BOTTOM, SIDE, WALL);
	
	// Vanilla
	public static final Model SPORE_BLOSSOM = new Model(Optional.of(Identifier.ofVanilla("block/spore_blossom")), Optional.empty(), FLOWER, PARTICLE);
	
}
