package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.block.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

@SuppressWarnings("unused")
public class SpectrumSaplingGenerators {
	
	public static final Identifier WHITE_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/white");
	public static final SaplingGenerator WHITE_COLORED_SAPLING_GENERATOR = registerRegular(WHITE_COLORED_SAPLING_ID);
	public static final Identifier ORANGE_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/orange");
	public static final SaplingGenerator ORANGE_COLORED_SAPLING_GENERATOR = registerRegular(ORANGE_COLORED_SAPLING_ID);
	public static final Identifier MAGENTA_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/magenta");
	public static final SaplingGenerator MAGENTA_COLORED_SAPLING_GENERATOR = registerRegular(MAGENTA_COLORED_SAPLING_ID);
	public static final Identifier LIGHT_BLUE_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/light_blue");
	public static final SaplingGenerator LIGHT_BLUE_COLORED_SAPLING_GENERATOR = registerRegular(LIGHT_BLUE_COLORED_SAPLING_ID);
	public static final Identifier YELLOW_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/yellow");
	public static final SaplingGenerator YELLOW_COLORED_SAPLING_GENERATOR = registerRegular(YELLOW_COLORED_SAPLING_ID);
	public static final Identifier LIME_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/lime");
	public static final SaplingGenerator LIME_COLORED_SAPLING_GENERATOR = registerRegular(LIME_COLORED_SAPLING_ID);
	public static final Identifier PINK_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/pink");
	public static final SaplingGenerator PINK_COLORED_SAPLING_GENERATOR = registerRegular(PINK_COLORED_SAPLING_ID);
	public static final Identifier GRAY_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/gray");
	public static final SaplingGenerator GRAY_COLORED_SAPLING_GENERATOR = registerRegular(GRAY_COLORED_SAPLING_ID);
	public static final Identifier LIGHT_GRAY_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/light_gray");
	public static final SaplingGenerator LIGHT_GRAY_COLORED_SAPLING_GENERATOR = registerRegular(LIGHT_GRAY_COLORED_SAPLING_ID);
	public static final Identifier CYAN_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/cyan");
	public static final SaplingGenerator CYAN_COLORED_SAPLING_GENERATOR = registerRegular(CYAN_COLORED_SAPLING_ID);
	public static final Identifier PURPLE_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/purple");
	public static final SaplingGenerator PURPLE_COLORED_SAPLING_GENERATOR = registerRegular(PURPLE_COLORED_SAPLING_ID);
	public static final Identifier BLUE_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/blue");
	public static final SaplingGenerator BLUE_COLORED_SAPLING_GENERATOR = registerRegular(BLUE_COLORED_SAPLING_ID);
	public static final Identifier BROWN_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/brown");
	public static final SaplingGenerator BROWN_COLORED_SAPLING_GENERATOR = registerRegular(BROWN_COLORED_SAPLING_ID);
	public static final Identifier GREEN_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/green");
	public static final SaplingGenerator GREEN_COLORED_SAPLING_GENERATOR = registerRegular(GREEN_COLORED_SAPLING_ID);
	public static final Identifier RED_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/red");
	public static final SaplingGenerator RED_COLORED_SAPLING_GENERATOR = registerRegular(RED_COLORED_SAPLING_ID);
	public static final Identifier BLACK_COLORED_SAPLING_ID = SpectrumCommon.locate("colored_trees/black");
	public static final SaplingGenerator BLACK_COLORED_SAPLING_GENERATOR = registerRegular(BLACK_COLORED_SAPLING_ID);
	
	public static final Identifier WEEPING_GALA_SAPLING_ID = SpectrumCommon.locate("weeping_gala");
	public static final SaplingGenerator WEEPING_GALA_SAPLING_GENERATOR = registerRegular(WEEPING_GALA_SAPLING_ID);
	
	public static SaplingGenerator registerRegular(Identifier id) {
		return new SaplingGenerator(id.getPath(), Optional.empty(), Optional.of(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id)), Optional.empty());
    }

}
