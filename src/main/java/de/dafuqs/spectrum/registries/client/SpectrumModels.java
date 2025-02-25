package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.jade_vines.*;
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
	
	// Vanilla Items
	
	public static final Identifier SKULL_ITEM = Identifier.ofVanilla("item/template_skull");
	
	// Block Templates
	public static final Model BASE_FLAT_LIGHT = new Model(Optional.of(SpectrumCommon.locate("templates/base_flat_light")), Optional.empty(), INNER, OUTER, PARTICLE);
	public static final Model BASE_FLAT_LIGHT_MIRRORED = new Model(Optional.of(SpectrumCommon.locate("templates/base_flat_light_mirrored")), Optional.empty(), INNER, OUTER, PARTICLE);
	public static final Model BASE_PYLON_BODY = new Model(Optional.of(SpectrumCommon.locate("templates/base_pylon_body")), Optional.empty(), SIDE, END);
	public static final Model BASE_TRANS_LIGHT_CORE = new Model(Optional.of(SpectrumCommon.locate("templates/base_trans_light_core")), Optional.empty(), GLASS, CASE, BASE, ENDS, SHELL, FILAMENT);
	public static final Model CHIME = new Model(Optional.of(SpectrumCommon.locate("templates/chime")), Optional.empty(), BASE, GEMSTONE);
	public static final Model CUSHION = new Model(Optional.of(SpectrumCommon.locate("templates/cushion")), Optional.empty(), SIDE, TOP, BOTTOM);
	public static final Model COLORED_LAMP_ON = new Model(Optional.of(SpectrumCommon.locate("templates/colored_lamp_on")), Optional.empty(), INNER, OUTER);
	public static final Model COMPLEX_ORIENTABLE = new Model(Optional.of(SpectrumCommon.locate("templates/complex_orientable")), Optional.empty(), SIDE, TOP, BOTTOM, FRONT, BACK, PARTICLE);
	public static final Model CRYSTALLARIEUM_FARMABLE = new Model(Optional.of(SpectrumCommon.locate("templates/crystallarieum_farmable")), Optional.empty(), CROSS);
	public static final Model DOUBLE_CROSS = new Model(Optional.of(SpectrumCommon.locate("templates/double_cross")), Optional.empty(), CROSS);
	public static final Model JADE_VINE_BULB = new Model(Optional.of(SpectrumCommon.locate("templates/jade_vine_bulb")), Optional.empty(), FLOWER, PARTICLE);
	public static final Model JADE_VINE_ROOTS = new Model(Optional.of(SpectrumCommon.locate("templates/jade_vine_roots")), Optional.empty(), FLOWER, PARTICLE);
	public static final Model MOONSTONE_CHISELED = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled")), Optional.empty(), SIDE, LINE);
	public static final Model MOONSTONE_CHISELED_DOWN = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled_down")), Optional.empty(), SIDE, LINE);
	public static final Model MULTILAYER_LIGHT = new Model(Optional.of(SpectrumCommon.locate("templates/multilayer_light")), Optional.empty(), SIDE, TOP, INSIDE);
	public static final Model OVERGROWN = new Model(Optional.of(SpectrumCommon.locate("templates/overgrown")), Optional.empty(), SIDE, TOP, BOTTOM, FRONDS);
	public static final Model REDSTONE_TIMER = new Model(Optional.of(SpectrumCommon.locate("templates/redstone_timer_base")), Optional.empty(), LIGHT);
	public static final Model REDSTONE_TRANSCEIVER_CHANNEL = new Model(Optional.of(SpectrumCommon.locate("templates/redstone_transceiver_channel_base")), Optional.empty(), ALL);
	public static final Model REDSTONE_TRANSCEIVER_SENDER = new Model(Optional.of(SpectrumCommon.locate("templates/redstone_transceiver_sender_base")), Optional.empty(), LIGHT);
	public static final Model REDSTONE_TRANSCEIVER_RECEIVER = new Model(Optional.of(SpectrumCommon.locate("templates/redstone_transceiver_receiver_base")), Optional.empty(), LIGHT);
	public static final Model SHOOTING_STAR = new Model(Optional.of(SpectrumCommon.locate("templates/shooting_star")), Optional.empty(), SIDE, CORE);
	public static final Model TRANSLUCENT_OUTER1 = new Model(Optional.of(SpectrumCommon.locate("templates/translucent_outer1")), Optional.empty(), TEXTURE);
	
	public static final Identifier PASTEL_GENERIC_NODE = SpectrumCommon.locate("pastel/generic_node");
	public static final Identifier PASTEL_PUSH_NODE = SpectrumCommon.locate("pastel/push_node");
	public static final Identifier PASTEL_PULL_NODE = SpectrumCommon.locate("pastel/pull_node");
	public static final Identifier PASTEL_STORE_NODE = SpectrumCommon.locate("pastel/store_node");
	
	public static final Identifier BALCITE_PYLON_PEDESTAL = SpectrumCommon.locate("block/balcite_pylon_pedestal");
	public static final Identifier MOB_BLOCK = SpectrumCommon.locate("block/mob_block");
	public static final Identifier MOB_BLOCK_COOLDOWN = SpectrumCommon.locate("block/mob_block_cooldown");
	public static final Identifier MOB_HEAD = SpectrumCommon.locate("block/mob_head");
	
	public static Model baseTransLantern(boolean diagonal, boolean tall) {
		return new Model(Optional.of(SpectrumCommon.locate("templates/base_trans_lantern" + (diagonal ? "_diagonal" : "") + (tall ? "_tall" : "_small"))), Optional.empty(), GLASS, CASE);
	}
	
	public static Model noxwoodLantern(String suffix) {
		return new Model(Optional.of(SpectrumCommon.locate("templates/noxwood_lamp" + suffix)), Optional.empty(), ALL);
	}
	
	public static Model sugarStick(int age) {
		return new Model(Optional.of(SpectrumCommon.locate("templates/sugar_stick" + age)), Optional.empty(), KEY0, KEY1, PARTICLE);
	}
	
	public static Model jadeVines(JadeVinePlantBlock.JadeVinesPlantPart part) {
		return new Model(Optional.of(SpectrumCommon.locate("templates/jade_vines_" + part.asString())), Optional.empty(), FLOWER, PARTICLE);
	}
	
	// Block Builtins w/ Extra Keys
	public static final Model CUBE_BOTTOM_TOP_PARTICLE = new Model(Optional.of(Identifier.ofVanilla("block/cube_bottom_top")), Optional.empty(), TOP, BOTTOM, SIDE, PARTICLE);
	public static final Model CUBE_BOTTOM_TOP_WALL = new Model(Optional.of(Identifier.ofVanilla("block/cube_bottom_top")), Optional.empty(), TOP, BOTTOM, SIDE, WALL);
	
	// Vanilla Blocks
	public static final Model DAYLIGHT_DETECTOR = new Model(Optional.of(Identifier.ofVanilla("block/template_daylight_detector")), Optional.empty(), SIDE, TOP);
	public static final Model SPORE_BLOSSOM = new Model(Optional.of(Identifier.ofVanilla("block/spore_blossom")), Optional.empty(), FLOWER, PARTICLE);
	
}
