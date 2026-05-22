package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.jade_vines.JadeVinePlantBlock;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

import static earth.terrarium.pastel.registries.client.PastelTextureKeys.BASE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.CASE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.CORE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.ENDS;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.FILAMENT;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.FLOWER;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.FRONDS;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.GEMSTONE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.GLASS;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.INNER;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.KEY0;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.KEY1;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.LAYER3;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.LIGHT;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.LINE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.OUTER;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.SHELL;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.SHRINE;
import static net.minecraft.data.models.model.TextureSlot.ALL;
import static net.minecraft.data.models.model.TextureSlot.BACK;
import static net.minecraft.data.models.model.TextureSlot.BOTTOM;
import static net.minecraft.data.models.model.TextureSlot.CROSS;
import static net.minecraft.data.models.model.TextureSlot.END;
import static net.minecraft.data.models.model.TextureSlot.FIRE;
import static net.minecraft.data.models.model.TextureSlot.FRONT;
import static net.minecraft.data.models.model.TextureSlot.INSIDE;
import static net.minecraft.data.models.model.TextureSlot.LAYER0;
import static net.minecraft.data.models.model.TextureSlot.LAYER1;
import static net.minecraft.data.models.model.TextureSlot.LAYER2;
import static net.minecraft.data.models.model.TextureSlot.PARTICLE;
import static net.minecraft.data.models.model.TextureSlot.SIDE;
import static net.minecraft.data.models.model.TextureSlot.TEXTURE;
import static net.minecraft.data.models.model.TextureSlot.TOP;
import static net.minecraft.data.models.model.TextureSlot.WALL;

public class PastelModels {

    // Item Templates
    public static final ModelTemplate CLUSTER_ITEM = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates_item/cluster")), Optional.empty(), LAYER0);
    public static final ModelTemplate LARGE_BUD_ITEM = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates_item/large_bud")), Optional.empty(), LAYER0);
    public static final ModelTemplate MEDIUM_BUD_ITEM = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates_item/medium_bud")), Optional.empty(), LAYER0);
    public static final ModelTemplate SMALL_BUD_ITEM = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates_item/small_bud")), Optional.empty(), LAYER0);

    // Item Builtins w/ Extra Keys
    public static final ModelTemplate HANDHELD_THREE_LAYERS = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("item/handheld")), Optional.empty(), LAYER0, LAYER1, LAYER2);
    public static final ModelTemplate GENERATED_FOUR_LAYERS = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("item/generated")), Optional.empty(), LAYER0, LAYER1, LAYER2,
        LAYER3
    );

    // Vanilla Items
    public static final ResourceLocation SKULL_ITEM = ResourceLocation.withDefaultNamespace("item/template_skull");
    public static final ResourceLocation SPAWN_EGG = ResourceLocation.withDefaultNamespace("item/template_spawn_egg");

    // Block Templates
    public static final ModelTemplate SHIMMERSTONE_LIGHT = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/shimmerstone_light")), Optional.empty(), INNER, OUTER, PARTICLE);
    public static final ModelTemplate SHIMMERSTONE_LIGHT_MIRRORED = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/shimmerstone_light_mirrored")), Optional.empty(), INNER, OUTER,
        PARTICLE
    );
    public static final ModelTemplate BASE_PYLON_BODY = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/base_pylon_body")), Optional.empty(), SIDE, END);
    public static final ModelTemplate BASE_TRANS_LIGHT_CORE = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/base_trans_light_core")), Optional.empty(), GLASS, CASE, BASE, ENDS,
        SHELL, FILAMENT
    );
    public static final ModelTemplate BOWL = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/base_bowl")), Optional.empty(), SIDE, TOP, BOTTOM, INNER);
    public static final ModelTemplate CHIME = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/chime")), Optional.empty(), BASE, GEMSTONE);
    public static final ModelTemplate CUSHION = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/cushion")), Optional.empty(), SIDE, TOP, BOTTOM);
    public static final ModelTemplate COLORED_LAMP_ON = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/colored_lamp_on")), Optional.empty(), INNER, OUTER);
    public static final ModelTemplate COMPLEX_ORIENTABLE = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/complex_orientable")), Optional.empty(), SIDE, TOP, BOTTOM, FRONT,
        BACK, PARTICLE
    );
    public static final ModelTemplate CRYSTALLARIEUM_FARMABLE = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/crystallarieum_farmable")), Optional.empty(), CROSS);
    public static final ModelTemplate DOUBLE_CROSS = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/double_cross")), Optional.empty(), CROSS);
    public static final ModelTemplate FUSION_SHRINE = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/base_fusion_shrine")), Optional.empty(), SHRINE, PARTICLE);
    public static final ModelTemplate JADE_VINE_BULB = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/jade_vine_bulb")), Optional.empty(), FLOWER, PARTICLE);
    public static final ModelTemplate JADE_VINE_ROOTS = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/jade_vine_roots")), Optional.empty(), FLOWER, PARTICLE);
    public static final ModelTemplate MOONSTONE_CHISELED = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/moonstone_chiseled")), Optional.empty(), SIDE, LINE);
    public static final ModelTemplate MOONSTONE_CHISELED_DOWN = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/moonstone_chiseled_down")), Optional.empty(), SIDE, LINE);
    public static final ModelTemplate MULTILAYER_LIGHT = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/multilayer_light")), Optional.empty(), SIDE, TOP, INSIDE);
    public static final ModelTemplate OVERGROWN = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/overgrown")), Optional.empty(), SIDE, TOP, BOTTOM, FRONDS);
    public static final ModelTemplate PARTICLE_SPAWNER = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/particle_spawner")), Optional.empty(), TOP);
    public static final ModelTemplate PEDESTAL = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/pedestal")), Optional.empty(), PastelTextureKeys.PEDESTAL, PARTICLE);
    public static final ModelTemplate PRESENT = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/present")), Optional.empty(), TEXTURE, PARTICLE);
    public static final ModelTemplate REDSTONE_TIMER = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/redstone_timer_base")), Optional.empty(), LIGHT);
    public static final ModelTemplate REDSTONE_TRANSCEIVER_CHANNEL = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/redstone_transceiver_channel_base")), Optional.empty(), ALL);
    public static final ModelTemplate REDSTONE_TRANSCEIVER_SENDER = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/redstone_transceiver_sender_base")), Optional.empty(), LIGHT);
    public static final ModelTemplate REDSTONE_TRANSCEIVER_RECEIVER = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/redstone_transceiver_receiver_base")), Optional.empty(), LIGHT);
    public static final ModelTemplate ROUNDEL = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/base_roundel")), Optional.empty(), ALL);
    public static final ModelTemplate SHOOTING_STAR = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/shooting_star")), Optional.empty(), SIDE, CORE);
    public static final ModelTemplate SLAB_DETECTOR = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/slab_detector")), Optional.empty(), TOP, SIDE);
    public static final ModelTemplate TRANSLUCENT_OUTER1 = new ModelTemplate(
        Optional.of(PastelCommon.locate("templates/translucent_outer1")), Optional.empty(), TEXTURE);

    public static final ResourceLocation PASTEL_GENERIC_NODE = PastelCommon.locate("pastel/generic_node");
    public static final ResourceLocation PASTEL_PUSH_NODE = PastelCommon.locate("pastel/push_node");
    public static final ResourceLocation PASTEL_PULL_NODE = PastelCommon.locate("pastel/pull_node");
    public static final ResourceLocation PASTEL_INK_GENERIC_NODE = PastelCommon.locate("pastel/ink_generic_node");
    public static final ResourceLocation PASTEL_INK_PUSH_NODE = PastelCommon.locate("pastel/ink_push_node");
    public static final ResourceLocation PASTEL_INK_PULL_NODE = PastelCommon.locate("pastel/ink_pull_node");
    public static final ResourceLocation PASTEL_STORE_NODE = PastelCommon.locate("pastel/store_node");

    public static final ResourceLocation BALCITE_PYLON_PEDESTAL = PastelCommon.locate("block/balcite_pylon_pedestal");
    public static final ResourceLocation MOB_BLOCK = PastelCommon.locate("block/mob_block");
    public static final ResourceLocation MOB_BLOCK_COOLDOWN = PastelCommon.locate("block/mob_block_cooldown");
    public static final ResourceLocation MOB_HEAD = PastelCommon.locate("block/mob_head");
    public static final ResourceLocation TEA_TABLE = PastelCommon.locate("block/tea_table");

    public static ModelTemplate baseTransLantern(boolean diagonal, boolean tall) {
        return new ModelTemplate(
            Optional.of(PastelCommon.locate(
                "templates/base_trans_lantern" + (diagonal ? "_diagonal" : "") + (tall ? "_tall" : "_small"))),
                                 Optional.empty(), GLASS, CASE
        );
    }

    public static ModelTemplate noxwoodLantern(String suffix) {
        return new ModelTemplate(
            Optional.of(PastelCommon.locate("templates/noxwood_lamp" + suffix)), Optional.empty(), ALL);
    }

    public static ModelTemplate sugarStick(int age) {
        return new ModelTemplate(
            Optional.of(PastelCommon.locate("templates/sugar_stick" + age)), Optional.empty(), KEY0, KEY1, PARTICLE);
    }

    public static ModelTemplate jadeVines(JadeVinePlantBlock.JadeVinesPlantPart part) {
        return new ModelTemplate(
            Optional.of(PastelCommon.locate("templates/jade_vines_" + part.getSerializedName())), Optional.empty(),
            FLOWER, PARTICLE
        );
    }

    // Block Builtins w/ Extra Keys
    public static final ModelTemplate CUBE_BOTTOM_TOP_PARTICLE = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/cube_bottom_top")), Optional.empty(), TOP, BOTTOM,
        SIDE, PARTICLE
    );
    public static final ModelTemplate CUBE_BOTTOM_TOP_WALL = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/cube_bottom_top")), Optional.empty(), TOP, BOTTOM,
        SIDE, WALL
    );

    // Vanilla Blocks
    public static final ModelTemplate DAYLIGHT_DETECTOR = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/template_daylight_detector")), Optional.empty(), SIDE,
        TOP
    );
    public static final ModelTemplate FIRE_FLOOR = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/template_fire_floor")), Optional.empty(), FIRE);
    public static final ModelTemplate FIRE_SIDE = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/template_fire_side")), Optional.empty(), FIRE);
    public static final ModelTemplate FIRE_SIDE_ALT = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/template_fire_side_alt")), Optional.empty(), FIRE);
    public static final ModelTemplate FIRE_UP = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/template_fire_up")), Optional.empty(), FIRE);
    public static final ModelTemplate FIRE_UP_ALT = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/template_fire_up_alt")), Optional.empty(), FIRE);
    public static final ModelTemplate SPORE_BLOSSOM = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("block/spore_blossom")), Optional.empty(), FLOWER, PARTICLE);

}
