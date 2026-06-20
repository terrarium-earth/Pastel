package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record InfusedBeverageComponent(String variant, int color) implements TooltipProvider {

    public static final InfusedBeverageComponent DEFAULT = new InfusedBeverageComponent("unknown", 0xfff4c6cb);

    public static final InfusedBeverageComponent ADVOCAAT = new InfusedBeverageComponent("advocaat",   15191723);

    public static final InfusedBeverageComponent ALE = new InfusedBeverageComponent("ale", 14847529);

    public static final InfusedBeverageComponent APPLE_CIDER = new InfusedBeverageComponent("apple_cider", 15032656);

    public static final InfusedBeverageComponent APPLE_LIQUOR = new InfusedBeverageComponent("apple_liquor", 16729462);

    public static final InfusedBeverageComponent ARTEMISA = new InfusedBeverageComponent("artemisa", 8055418);

    public static final InfusedBeverageComponent BEER = new InfusedBeverageComponent("beer", 16762703);

    public static final InfusedBeverageComponent BERRY_CIDER = new InfusedBeverageComponent("berry_cider", 14819906);

    public static final InfusedBeverageComponent BERRY_LIQUOR = new InfusedBeverageComponent("berry_liquor", 16729462);

    public static final InfusedBeverageComponent CAMOMILLESQUE = new InfusedBeverageComponent("camomillesque", 15517767);

    public static final InfusedBeverageComponent DAMASSINE = new InfusedBeverageComponent("damassine", 7558132);

    public static final InfusedBeverageComponent ENCHANTED_APPLE_CIDER = new InfusedBeverageComponent("enchanted_apple_cider",  16745539);

    public static final InfusedBeverageComponent FRUIT_SHNAPS = new InfusedBeverageComponent("fruit_shnaps", 14450014);

    public static final InfusedBeverageComponent GATORWINE = new InfusedBeverageComponent("gatorwine", 16745538);

    public static final InfusedBeverageComponent GIN = new InfusedBeverageComponent("gin", 16437201);

    // NOTE: This was broken in the recipe file, with its variant listed as (w+)
    // might result in breakage in upgraded worlds?
    public static final InfusedBeverageComponent GLASS_PEACH_CIDER = new InfusedBeverageComponent("glass_peach_cider", 15573110);

    public static final InfusedBeverageComponent GLASS_PEACH_LIQUOR = new InfusedBeverageComponent("glass_peach_liquor", 13723654);

    public static final InfusedBeverageComponent GLOW_BERRY_CIDER = new InfusedBeverageComponent("glow_berry_cider", 16769123);

    public static final InfusedBeverageComponent GLOW_BERRY_LIQUOR = new InfusedBeverageComponent("glow_berry_liquor", 16760721);

    public static final InfusedBeverageComponent GOLDEN_APPLE_CIDER = new InfusedBeverageComponent("golden_apple_cider", 15560510);

    public static final InfusedBeverageComponent HARE_BANE_CREME = new InfusedBeverageComponent("hare_bane_creme", 16035689);

    public static final InfusedBeverageComponent INCUBUS_CREAM = new InfusedBeverageComponent("incubus_cream", 14173082);

    public static final InfusedBeverageComponent LAGER = new InfusedBeverageComponent("lager", 12080430);

    public static final InfusedBeverageComponent MALT_BEER = new InfusedBeverageComponent("malt_beer", 8272724);

    public static final InfusedBeverageComponent MEAD = new InfusedBeverageComponent("mead", 16757819);

    public static final InfusedBeverageComponent MINT_CREAM = new InfusedBeverageComponent("mint_cream", 2752440);

    public static final InfusedBeverageComponent MINT_SCHNAPPS = new InfusedBeverageComponent("mint_schnapps", 1231480);

    public static final InfusedBeverageComponent MOONSHINE = new InfusedBeverageComponent("moonshine", 14151663);

    public static final InfusedBeverageComponent MYCEYLON_APPLE_JUIDE = new InfusedBeverageComponent("myceylon_apple_juice", 12018987);

    public static final InfusedBeverageComponent MYCEYLON_LIQUOR = new InfusedBeverageComponent("myceylon_liquor", 16040760);

    public static final InfusedBeverageComponent NIGHT_CREAM = new InfusedBeverageComponent("night_cream", 12982783);

    public static final InfusedBeverageComponent PLUM_CIDER = new InfusedBeverageComponent("plum_cider", 13453929);

    public static final InfusedBeverageComponent PLUM_LIQUOR = new InfusedBeverageComponent("plum_liquor", 13255032);

    public static final InfusedBeverageComponent POISONOUS_VODKA = new InfusedBeverageComponent("poisonous_vodka", 14938088);

    public static final InfusedBeverageComponent PORTER = new InfusedBeverageComponent("porter", 4857125);

    public static final InfusedBeverageComponent RABBIT_POISON = new InfusedBeverageComponent("rabbit_poison", 9518003);

    public static final InfusedBeverageComponent RUM = new InfusedBeverageComponent("rum", 10505507);

    public static final InfusedBeverageComponent SARSAPARILLA = new InfusedBeverageComponent("sarsaparilla", 45289259);

    public static final InfusedBeverageComponent SAWBLADE_HOLLY_CIDER = new InfusedBeverageComponent("sawblade_holly_cider", 15573110);

    public static final InfusedBeverageComponent SAWBLADE_HOLLY_LIQUOR = new InfusedBeverageComponent("sawblade_holly_liquor", 13723654);

    public static final InfusedBeverageComponent SPIKED_MULLET_WINE = new InfusedBeverageComponent("spiked_mullet_wine", 10365209);

    public static final InfusedBeverageComponent TEQUILA = new InfusedBeverageComponent("tequila", 13496958);

    public static final InfusedBeverageComponent VELVET_BRANDY = new InfusedBeverageComponent("velvet_brandy", 15383295);

    public static final InfusedBeverageComponent VERDIGRIS_WINE = new InfusedBeverageComponent("verdigris_wine", 4237444);

    public static final InfusedBeverageComponent VODKA = new InfusedBeverageComponent("vodka", 14938088);

    public static final Codec<InfusedBeverageComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    Codec.STRING
                        .optionalFieldOf("variant", "unknown")
                        .forGetter(InfusedBeverageComponent::variant),
                    ColorHelper.CODEC
                        .optionalFieldOf("color", 0xfff4c6cb)
                        .forGetter(InfusedBeverageComponent::color)
                )
                .apply(
                    i,
                    InfusedBeverageComponent::new
                )
        );

    public static final StreamCodec<ByteBuf, InfusedBeverageComponent> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.STRING_UTF8,
            InfusedBeverageComponent::variant,
            ByteBufCodecs.VAR_INT,
            InfusedBeverageComponent::color,
            InfusedBeverageComponent::new
        );

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        tooltip
            .accept(
                Component
                    .translatable("item.pastel.infused_beverage.tooltip.variant." + variant)
                    .withStyle(ChatFormatting.YELLOW)
            );
    }

}
