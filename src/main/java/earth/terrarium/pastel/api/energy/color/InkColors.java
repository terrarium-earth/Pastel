package earth.terrarium.pastel.api.energy.color;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.List;

public class InkColors {
	
	public static final ResourceLocation BASE_ADVANCEMENT_ID = PastelCommon.locate("midgame/pastel_midgame");
	public static final ResourceLocation BLACK_ADVANCEMENT_ID = PastelCommon.locate("midgame/pastel_midgame");
	public static final ResourceLocation WHITE_ADVANCEMENT_ID = PastelCommon.locate("lategame/collect_moonstone");

	private static final DeferredRegister<InkColor> REGISTER = DeferredRegister.create(PastelRegistryKeys.INK_COLOR, PastelCommon.MOD_ID);

	/**
	 * A lot of places where color is displayed have black backgrounds, which would make displaying normal black on them... daft.
	 * <p>
	 * So, instead, we use something closer to midnight solution in shade.
	 */
	public static final int BLACK_TEXT_COLOR = 0xff302951;

	public static final int CYAN_COLOR = 0xff5bffed;
	public static final int MAGENTA_COLOR = 0xffff4ff6;
	public static final int YELLOW_COLOR = 0xffeded00;
	public static final int BLACK_COLOR = 0xff020106;
	public static final int WHITE_COLOR = 0xffffffff;
	public static final int ORANGE_COLOR = 0xfff97b2d;
	public static final int LIME_COLOR = 0xff98ff37;
	public static final int PINK_COLOR = 0xffff9fc6;
	public static final int RED_COLOR = 0xfff12a34;
	public static final int LIGHT_BLUE_COLOR = 0xff7a9eff;
	public static final int GREEN_COLOR = 0xff526b0f;
	public static final int BLUE_COLOR = 0xff2432ff;
	public static final int PURPLE_COLOR = 0xff802bc4;
	public static final int BROWN_COLOR = 0xff70400d;
	public static final int LIGHT_GRAY_COLOR = 0xffadadad;
	public static final int GRAY_COLOR = 0xff464646;

	public static final InkColor CYAN = register(new InkColor(DyeColor.CYAN, "cyan", CYAN_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor LIGHT_BLUE = register(new InkColor(DyeColor.LIGHT_BLUE, "light_blue", LIGHT_BLUE_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor BLUE = register(new InkColor(DyeColor.BLUE, "blue", BLUE_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor PURPLE = register(new InkColor(DyeColor.PURPLE, "purple", PURPLE_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor MAGENTA = register(new InkColor(DyeColor.MAGENTA, "magenta", MAGENTA_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor PINK = register(new InkColor(DyeColor.PINK, "pink", PINK_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor RED = register(new InkColor(DyeColor.RED, "red", RED_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor ORANGE = register(new InkColor(DyeColor.ORANGE, "orange", ORANGE_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor YELLOW = register(new InkColor(DyeColor.YELLOW, "yellow", YELLOW_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor LIME = register(new InkColor(DyeColor.LIME, "lime", LIME_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor GREEN = register(new InkColor(DyeColor.GREEN, "green", GREEN_COLOR, BASE_ADVANCEMENT_ID));
	public static final InkColor BROWN = register(new InkColor(DyeColor.BROWN, "brown", BROWN_COLOR, BLACK_ADVANCEMENT_ID));
	public static final InkColor BLACK = register(new InkColor(DyeColor.BLACK, "black", BLACK_COLOR, BLACK_TEXT_COLOR, BLACK_ADVANCEMENT_ID));
	public static final InkColor GRAY = register(new InkColor(DyeColor.GRAY, "gray", GRAY_COLOR, WHITE_ADVANCEMENT_ID));
	public static final InkColor LIGHT_GRAY = register(new InkColor(DyeColor.LIGHT_GRAY, "light_gray", LIGHT_GRAY_COLOR, WHITE_ADVANCEMENT_ID));
	public static final InkColor WHITE = register(new InkColor(DyeColor.WHITE, "white", WHITE_COLOR, WHITE_ADVANCEMENT_ID));
	
	// in case an addon adds new colors
	// for places where we have to use a fixed size list, like GUIs with limited space
	public static final List<InkColor> BUILTIN_COLORS = List.of(
			InkColors.CYAN, InkColors.LIGHT_BLUE, InkColors.BLUE, InkColors.PURPLE,
			InkColors.MAGENTA, InkColors.PINK, InkColors.RED, InkColors.ORANGE,
			InkColors.YELLOW, InkColors.LIME, InkColors.GREEN, InkColors.BROWN,
			InkColors.BLACK, InkColors.GRAY, InkColors.LIGHT_GRAY, InkColors.WHITE
	);
	
	protected static InkColor register(InkColor inkColor) {
		REGISTER.register(inkColor.name, () -> inkColor);
		return inkColor;
	}
	
	public static void register(IEventBus bus) {
		REGISTER.register(bus);
	}
	
	public static Iterable<InkColor> all() {
		return PastelRegistries.INK_COLOR;
	}
	
	public static List<InkColor> elementals() {
		return PastelRegistries.INK_COLOR.getTag(InkColorTags.ELEMENTAL_COLORS).map(entries -> entries.stream().map(Holder::value).toList()).orElseGet(() -> List.of(CYAN, MAGENTA, YELLOW, WHITE, BLACK));
	}
	
	public static List<InkColor> compounds() {
		return PastelRegistries.INK_COLOR.getTag(InkColorTags.COMPOUND_COLORS).map(entries -> entries.stream().map(Holder::value).toList()).orElseGet(() -> List.of(LIGHT_BLUE, BLUE, PURPLE, PINK, RED, ORANGE, LIME, GREEN, BROWN, GRAY, LIGHT_GRAY));
	}
	
}
