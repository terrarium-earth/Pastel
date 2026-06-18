package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.mob_head.client.models.AllayHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.ArmadilloHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.AxolotlHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.BatHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.BearHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.BeeHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.BlazeHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.BoggedHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.BreezeHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.CamelHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.CatHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.ChickenHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.CowHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.DolphinHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.DrownedHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.EggLayingWoolyPigHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.EndermanHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.EndermiteHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.EraserHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.FoxHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.FrogHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.GhastHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.GoatHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.GuardianHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.HoglinHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.HorseHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.IllagerHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.IronGolemHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.KindlingHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.LizardHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.LlamaHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.PandaHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.ParrotHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.PhantomHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.PigHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.PiglinHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.PreservationTurretHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.PufferFishHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.RabbitHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.RavagerHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.SalmonHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.SheepHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.ShulkerHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.SilverfishHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.SlimeHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.SnifferHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.SpiderHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.SquidHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.StrayHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.StriderHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.TadpoleHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.TropicalFishHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.TurtleHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.VexHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.VillagerHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.WardenHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.WitchHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.WolfHeadModel;
import earth.terrarium.pastel.blocks.mob_head.client.models.ZombieHeadModel;
import earth.terrarium.pastel.entity.models.EggLayingWoolyPigEntityModel;
import earth.terrarium.pastel.entity.models.EggLayingWoolyPigHatEntityModel;
import earth.terrarium.pastel.entity.models.EggLayingWoolyPigWoolEntityModel;
import earth.terrarium.pastel.entity.models.EraserEntityModel;
import earth.terrarium.pastel.entity.models.KindlingCoughEntityModel;
import earth.terrarium.pastel.entity.models.KindlingEntityModel;
import earth.terrarium.pastel.entity.models.LizardEntityModel;
import earth.terrarium.pastel.entity.models.PreservationTurretEntityModel;
import earth.terrarium.pastel.entity.render.GlassArrowEntityRenderer;
import earth.terrarium.pastel.render.armor.BedrockArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ClientHooks;

public class PastelModelLayers {

    /**
     * Entities
     */
    public static final ModelLayerLocation WOOLY_PIG = new ModelLayerLocation(
        PastelCommon.locate("egg_laying_wooly_pig"),
        "main"
    );

    public static final ModelLayerLocation WOOLY_PIG_HAT = new ModelLayerLocation(
        PastelCommon.locate("egg_laying_wooly_pig"),
        "hat"
    );

    public static final ModelLayerLocation WOOLY_PIG_WOOL = new ModelLayerLocation(
        PastelCommon.locate("egg_laying_wooly_pig"),
        "wool"
    );

    public static final ModelLayerLocation PRESERVATION_TURRET = new ModelLayerLocation(
        PastelCommon.locate("preservation_turret"),
        "main"
    );

    public static final ModelLayerLocation LIZARD_SCALES = new ModelLayerLocation(
        PastelCommon.locate("lizard"),
        "main"
    );

    public static final ModelLayerLocation LIZARD_FRILLS = new ModelLayerLocation(
        PastelCommon.locate("lizard"),
        "frills"
    );

    public static final ModelLayerLocation LIZARD_HORNS = new ModelLayerLocation(
        PastelCommon.locate("lizard"),
        "horns"
    );

    public static final ModelLayerLocation KINDLING = new ModelLayerLocation(PastelCommon.locate("kindling"), "main");

    public static final ModelLayerLocation KINDLING_SADDLE = new ModelLayerLocation(
        PastelCommon.locate("kindling_saddle"),
        "main"
    );

    public static final ModelLayerLocation KINDLING_ARMOR = new ModelLayerLocation(
        PastelCommon.locate("kindling_armor"),
        "main"
    );

    public static final ModelLayerLocation KINDLING_COUGH = new ModelLayerLocation(
        PastelCommon.locate("kindling_cough"),
        "main"
    );

    public static final ModelLayerLocation ERASER = new ModelLayerLocation(PastelCommon.locate("eraser"), "body");

    public static final ModelLayerLocation GLASS_ARROW = new ModelLayerLocation(
        PastelCommon.locate("glass_arrow"),
        "main"
    );

    /**
     * Mob Heads
     */
    public static final ModelLayerLocation ALLAY_HEAD = new ModelLayerLocation(
        PastelCommon.locate("allay_head"),
        "main"
    );

    public static final ModelLayerLocation AXOLOTL_BLUE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("axolotl_blue_head"),
        "main"
    );

    public static final ModelLayerLocation AXOLOTL_CYAN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("axolotl_cyan_head"),
        "main"
    );

    public static final ModelLayerLocation AXOLOTL_GOLD_HEAD = new ModelLayerLocation(
        PastelCommon.locate("axolotl_gold_head"),
        "main"
    );

    public static final ModelLayerLocation AXOLOTL_LEUCISTIC_HEAD = new ModelLayerLocation(
        PastelCommon.locate("axolotl_lucy_head"),
        "main"
    );

    public static final ModelLayerLocation AXOLOTL_WILD_HEAD = new ModelLayerLocation(
        PastelCommon.locate("axolotl_wild_head"),
        "main"
    );

    public static final ModelLayerLocation BAT_HEAD = new ModelLayerLocation(PastelCommon.locate("bat_head"), "main");

    public static final ModelLayerLocation BEE_HEAD = new ModelLayerLocation(PastelCommon.locate("bee_head"), "main");

    public static final ModelLayerLocation BLAZE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("blaze_head"),
        "main"
    );

    public static final ModelLayerLocation CAMEL_HEAD = new ModelLayerLocation(
        PastelCommon.locate("camel_head"),
        "main"
    );

    public static final ModelLayerLocation CAT_HEAD = new ModelLayerLocation(PastelCommon.locate("cat_head"), "main");

    public static final ModelLayerLocation CAVE_SPIDER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("cave_spider_head"),
        "main"
    );

    public static final ModelLayerLocation CHICKEN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("chicken_head"),
        "main"
    );

    public static final ModelLayerLocation COW_HEAD = new ModelLayerLocation(PastelCommon.locate("cow_head"), "main");

    public static final ModelLayerLocation DOLPHIN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("dolphin_head"),
        "main"
    );

    public static final ModelLayerLocation DONKEY_HEAD = new ModelLayerLocation(
        PastelCommon.locate("donkey_head"),
        "main"
    );

    public static final ModelLayerLocation DROWNED_HEAD = new ModelLayerLocation(
        PastelCommon.locate("drowned_head"),
        "main"
    );

    public static final ModelLayerLocation DROWNED_HEAD_OUTER = new ModelLayerLocation(
        PastelCommon.locate("drowned_head"),
        "overlay"
    );

    public static final ModelLayerLocation ELDER_GUARDIAN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("elder_guardian_head"),
        "main"
    );

    public static final ModelLayerLocation ENDERMAN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("enderman_head"),
        "main"
    );

    public static final ModelLayerLocation ENDERMITE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("endermite_head"),
        "main"
    );

    public static final ModelLayerLocation EVOKER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("evoker_head"),
        "main"
    );

    public static final ModelLayerLocation FOX_ARCTIC_HEAD = new ModelLayerLocation(
        PastelCommon.locate("fox_arctic_head"),
        "main"
    );

    public static final ModelLayerLocation FOX_HEAD = new ModelLayerLocation(PastelCommon.locate("fox_head"), "main");

    public static final ModelLayerLocation FROG_COLD_HEAD = new ModelLayerLocation(
        PastelCommon.locate("frog_cold_head"),
        "main"
    );

    public static final ModelLayerLocation FROG_TEMPERATE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("frog_temperate_head"),
        "main"
    );

    public static final ModelLayerLocation FROG_WARM_HEAD = new ModelLayerLocation(
        PastelCommon.locate("frog_warm_head"),
        "main"
    );

    public static final ModelLayerLocation GHAST_HEAD = new ModelLayerLocation(
        PastelCommon.locate("ghast_head"),
        "main"
    );

    public static final ModelLayerLocation GLOW_SQUID_HEAD = new ModelLayerLocation(
        PastelCommon.locate("glow_squid_head"),
        "main"
    );

    public static final ModelLayerLocation GOAT_HEAD = new ModelLayerLocation(PastelCommon.locate("goat_head"), "main");

    public static final ModelLayerLocation GUARDIAN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("guardian_head"),
        "main"
    );

    public static final ModelLayerLocation HOGLIN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("hoglin_head"),
        "main"
    );

    public static final ModelLayerLocation HORSE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("horse_head"),
        "main"
    );

    public static final ModelLayerLocation HUSK_HEAD = new ModelLayerLocation(PastelCommon.locate("husk_head"), "main");

    public static final ModelLayerLocation ILLUSIONER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("illusioner_head"),
        "main"
    );

    public static final ModelLayerLocation IRON_GOLEM_HEAD = new ModelLayerLocation(
        PastelCommon.locate("iron_golem_head"),
        "main"
    );

    public static final ModelLayerLocation LLAMA_HEAD = new ModelLayerLocation(
        PastelCommon.locate("llama_head"),
        "main"
    );

    public static final ModelLayerLocation MAGMA_CUBE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("magma_cube_head"),
        "main"
    );

    public static final ModelLayerLocation MOOSHROOM_BROWN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("mooshroom_brown_head"),
        "main"
    );

    public static final ModelLayerLocation MOOSHROOM_RED_HEAD = new ModelLayerLocation(
        PastelCommon.locate("mooshroom_red_head"),
        "main"
    );

    public static final ModelLayerLocation MULE_HEAD = new ModelLayerLocation(PastelCommon.locate("mule_head"), "main");

    public static final ModelLayerLocation OCELOT_HEAD = new ModelLayerLocation(
        PastelCommon.locate("ocelot_head"),
        "main"
    );

    public static final ModelLayerLocation PANDA_HEAD = new ModelLayerLocation(
        PastelCommon.locate("panda_head"),
        "main"
    );

    public static final ModelLayerLocation PARROT_BLUE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("parrot_blue_head"),
        "main"
    );

    public static final ModelLayerLocation PARROT_CYAN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("parrot_cyan_head"),
        "main"
    );

    public static final ModelLayerLocation PARROT_GRAY_HEAD = new ModelLayerLocation(
        PastelCommon.locate("parrot_gray_head"),
        "main"
    );

    public static final ModelLayerLocation PARROT_GREEN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("parrot_green_head"),
        "main"
    );

    public static final ModelLayerLocation PARROT_RED_HEAD = new ModelLayerLocation(
        PastelCommon.locate("parrot_red_head"),
        "main"
    );

    public static final ModelLayerLocation PHANTOM_HEAD = new ModelLayerLocation(
        PastelCommon.locate("phantom_head"),
        "main"
    );

    public static final ModelLayerLocation PIG_HEAD = new ModelLayerLocation(PastelCommon.locate("pig_head"), "main");

    public static final ModelLayerLocation PILLAGER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("pillager_head"),
        "main"
    );

    public static final ModelLayerLocation POLAR_BEAR_HEAD = new ModelLayerLocation(
        PastelCommon.locate("polar_bear_head"),
        "main"
    );

    public static final ModelLayerLocation PUFFERFISH_HEAD = new ModelLayerLocation(
        PastelCommon.locate("pufferfish_head"),
        "main"
    );

    public static final ModelLayerLocation RABBIT_HEAD = new ModelLayerLocation(
        PastelCommon.locate("rabbit_head"),
        "main"
    );

    public static final ModelLayerLocation RAVAGER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("ravager_head"),
        "main"
    );

    public static final ModelLayerLocation SALMON_HEAD = new ModelLayerLocation(
        PastelCommon.locate("salmon_head"),
        "main"
    );

    public static final ModelLayerLocation SHEEP_HEAD = new ModelLayerLocation(
        PastelCommon.locate("sheep_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_BLACK_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_black_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_BLUE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_blue_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_BROWN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_brown_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_CYAN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_cyan_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_GRAY_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_gray_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_GREEN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_green_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_LIGHT_BLUE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_light_blue_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_LIGHT_GRAY_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_light_gray_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_LIME_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_lime_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_MAGENTA_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_magenta_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_ORANGE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_orange_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_PINK_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_pink_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_PURPLE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_purple_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_RED_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_red_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_WHITE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_white_head"),
        "main"
    );

    public static final ModelLayerLocation SHULKER_YELLOW_HEAD = new ModelLayerLocation(
        PastelCommon.locate("shulker_yellow_head"),
        "main"
    );

    public static final ModelLayerLocation SILVERFISH_HEAD = new ModelLayerLocation(
        PastelCommon.locate("silverfish_head"),
        "main"
    );

    public static final ModelLayerLocation SKELETON_HORSE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("skeleton_horse_head"),
        "main"
    );

    public static final ModelLayerLocation SLIME_HEAD = new ModelLayerLocation(
        PastelCommon.locate("slime_head"),
        "main"
    );

    public static final ModelLayerLocation SNIFFER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("sniffer_head"),
        "main"
    );

    public static final ModelLayerLocation SNOW_GOLEM_HEAD = new ModelLayerLocation(
        PastelCommon.locate("snow_golem_head"),
        "main"
    );

    public static final ModelLayerLocation SPIDER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("spider_head"),
        "main"
    );

    public static final ModelLayerLocation SQUID_HEAD = new ModelLayerLocation(
        PastelCommon.locate("squid_head"),
        "main"
    );

    public static final ModelLayerLocation STRAY_HEAD = new ModelLayerLocation(
        PastelCommon.locate("stray_head"),
        "main"
    );

    public static final ModelLayerLocation STRIDER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("strider_head"),
        "main"
    );

    public static final ModelLayerLocation TADPOLE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("tadpole_head"),
        "main"
    );

    public static final ModelLayerLocation TROPICAL_FISH_HEAD = new ModelLayerLocation(
        PastelCommon.locate("tropical_fish_head"),
        "main"
    );

    public static final ModelLayerLocation TROPICAL_FISH_HEAD_PATTERN = new ModelLayerLocation(
        PastelCommon.locate("tropical_fish_head"),
        "pattern"
    );

    public static final ModelLayerLocation TURTLE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("turtle_head"),
        "main"
    );

    public static final ModelLayerLocation VEX_HEAD = new ModelLayerLocation(PastelCommon.locate("vex_head"), "main");

    public static final ModelLayerLocation VILLAGER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("villager_head"),
        "main"
    );

    public static final ModelLayerLocation VINDICATOR_HEAD = new ModelLayerLocation(
        PastelCommon.locate("vindicator_head"),
        "main"
    );

    public static final ModelLayerLocation WANDERING_TRADER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("wandering_trader_head"),
        "main"
    );

    public static final ModelLayerLocation WARDEN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("warden_head"),
        "main"
    );

    public static final ModelLayerLocation WITCH_HEAD = new ModelLayerLocation(
        PastelCommon.locate("witch_head"),
        "main"
    );

    public static final ModelLayerLocation WITHER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("wither_head"),
        "main"
    );

    public static final ModelLayerLocation WOLF_HEAD = new ModelLayerLocation(PastelCommon.locate("wolf_head"), "main");

    public static final ModelLayerLocation ZOGLIN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("zoglin_head"),
        "main"
    );

    public static final ModelLayerLocation ZOMBIE_HORSE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("zombie_horse_head"),
        "main"
    );

    public static final ModelLayerLocation ZOMBIE_VILLAGER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("zombie_villager_head"),
        "main"
    );

    public static final ModelLayerLocation ZOMBIFIED_PIGLIN_HEAD = new ModelLayerLocation(
        PastelCommon.locate("zombified_piglin_head"),
        "main"
    );

    public static final ModelLayerLocation ARMADILLO_HEAD = new ModelLayerLocation(
        PastelCommon.locate("armadillo_head"),
        "main"
    );

    public static final ModelLayerLocation BREEZE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("breeze_head"),
        "main"
    );

    public static final ModelLayerLocation BOGGED_HEAD = new ModelLayerLocation(
        PastelCommon.locate("bogged_head"),
        "main"
    );

    public static final ModelLayerLocation PIGLIN_BRUTE_HEAD = new ModelLayerLocation(
        PastelCommon.locate("piglin_brute_head"),
        "main"
    );

    public static final ModelLayerLocation EGG_LAYING_WOOLY_PIG_HEAD = new ModelLayerLocation(
        PastelCommon.locate("egg_laying_wooly_pig_head"),
        "main"
    );

    public static final ModelLayerLocation ERASER_HEAD = new ModelLayerLocation(
        PastelCommon.locate("eraser_head"),
        "body"
    );

    public static final ModelLayerLocation KINDLING_HEAD = new ModelLayerLocation(
        PastelCommon.locate("kindling_head"),
        "main"
    );

    public static final ModelLayerLocation LIZARD_HEAD = new ModelLayerLocation(
        PastelCommon.locate("lizard_head"),
        "main"
    );

    public static final ModelLayerLocation LIZARD_HEAD_FRILLS = new ModelLayerLocation(
        PastelCommon.locate("lizard_head"),
        "frills"
    );

    public static final ModelLayerLocation PRESERVATION_TURRET_HEAD = new ModelLayerLocation(
        PastelCommon.locate("preservation_turret_head"),
        "main"
    );

    /**
     * Armor
     */
    public static final ModelLayerLocation MAIN_BEDROCK_LAYER = new ModelLayerLocation(
        PastelCommon.locate("bedrock_armor"),
        "main"
    );

    public static final ResourceLocation BEDROCK_ARMOR_MAIN_ID = PastelCommon
        .locate(
            "textures/armor/bedrock_armor_main.png"
        );

    public static void register(FMLClientSetupEvent event) {
        ClientHooks.registerLayerDefinition(WOOLY_PIG, EggLayingWoolyPigEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(WOOLY_PIG_HAT, EggLayingWoolyPigHatEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(WOOLY_PIG_WOOL, EggLayingWoolyPigWoolEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PRESERVATION_TURRET, PreservationTurretEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(LIZARD_SCALES, LizardEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(LIZARD_FRILLS, LizardEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(LIZARD_HORNS, LizardEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(KINDLING, KindlingEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(KINDLING_SADDLE, KindlingEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(KINDLING_ARMOR, KindlingEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(KINDLING_COUGH, KindlingCoughEntityModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ERASER, EraserEntityModel::getTexturedModelData);

        ClientHooks.registerLayerDefinition(GLASS_ARROW, GlassArrowEntityRenderer.Model::createBodyLayer);

        ClientHooks.registerLayerDefinition(ALLAY_HEAD, AllayHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(AXOLOTL_BLUE_HEAD, AxolotlHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(AXOLOTL_CYAN_HEAD, AxolotlHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(AXOLOTL_GOLD_HEAD, AxolotlHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(AXOLOTL_WILD_HEAD, AxolotlHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(AXOLOTL_LEUCISTIC_HEAD, AxolotlHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(BAT_HEAD, BatHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(BEE_HEAD, BeeHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(BLAZE_HEAD, BlazeHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(CAT_HEAD, CatHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(CAVE_SPIDER_HEAD, SpiderHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(CHICKEN_HEAD, ChickenHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(TROPICAL_FISH_HEAD, TropicalFishHeadModel::getTexturedModelData);
        ClientHooks
            .registerLayerDefinition(
                TROPICAL_FISH_HEAD_PATTERN,
                TropicalFishHeadModel::getTexturedModelDataPattern
            );
        ClientHooks.registerLayerDefinition(COW_HEAD, CowHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(DONKEY_HEAD, HorseHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(DROWNED_HEAD, DrownedHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ELDER_GUARDIAN_HEAD, GuardianHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ENDERMAN_HEAD, EndermanHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ENDERMITE_HEAD, EndermiteHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(EVOKER_HEAD, IllagerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(FOX_HEAD, FoxHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(FOX_ARCTIC_HEAD, FoxHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(FROG_COLD_HEAD, FrogHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(FROG_TEMPERATE_HEAD, FrogHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(FROG_WARM_HEAD, FrogHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(GHAST_HEAD, GhastHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(GLOW_SQUID_HEAD, SquidHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(GOAT_HEAD, GoatHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(GUARDIAN_HEAD, GuardianHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(HOGLIN_HEAD, HoglinHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(HORSE_HEAD, HorseHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(HUSK_HEAD, ZombieHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ILLUSIONER_HEAD, IllagerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(IRON_GOLEM_HEAD, IronGolemHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(LLAMA_HEAD, LlamaHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(MAGMA_CUBE_HEAD, SlimeHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(MOOSHROOM_BROWN_HEAD, CowHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(MOOSHROOM_RED_HEAD, CowHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(MULE_HEAD, HorseHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(OCELOT_HEAD, CatHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PANDA_HEAD, PandaHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PARROT_BLUE_HEAD, ParrotHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PARROT_CYAN_HEAD, ParrotHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PARROT_GRAY_HEAD, ParrotHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PARROT_GREEN_HEAD, ParrotHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PARROT_RED_HEAD, ParrotHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PHANTOM_HEAD, PhantomHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PIG_HEAD, PigHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(POLAR_BEAR_HEAD, BearHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PUFFERFISH_HEAD, PufferFishHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(RABBIT_HEAD, RabbitHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(RAVAGER_HEAD, RavagerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SALMON_HEAD, SalmonHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHEEP_HEAD, SheepHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_BLACK_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_BLUE_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_BROWN_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_CYAN_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_GRAY_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_GREEN_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_LIGHT_BLUE_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_LIGHT_GRAY_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_LIME_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_MAGENTA_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_ORANGE_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_PINK_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_PURPLE_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_RED_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_WHITE_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SHULKER_YELLOW_HEAD, ShulkerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SILVERFISH_HEAD, SilverfishHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SLIME_HEAD, SlimeHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SNOW_GOLEM_HEAD, ZombieHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SPIDER_HEAD, SpiderHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SQUID_HEAD, SquidHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(STRAY_HEAD, StrayHeadModel::createMobHeadLayer);
        ClientHooks.registerLayerDefinition(STRIDER_HEAD, StriderHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(TADPOLE_HEAD, TadpoleHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(TURTLE_HEAD, TurtleHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(VEX_HEAD, VexHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(VILLAGER_HEAD, VillagerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(VINDICATOR_HEAD, IllagerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(WANDERING_TRADER_HEAD, VillagerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(WARDEN_HEAD, WardenHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(WITCH_HEAD, WitchHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(WITHER_HEAD, ZombieHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(WOLF_HEAD, WolfHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ZOGLIN_HEAD, HoglinHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ZOMBIE_VILLAGER_HEAD, VillagerHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ZOMBIFIED_PIGLIN_HEAD, PiglinHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ARMADILLO_HEAD, ArmadilloHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(BREEZE_HEAD, BreezeHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(BOGGED_HEAD, BoggedHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PIGLIN_BRUTE_HEAD, PiglinHeadModel::getTexturedModelData);

        ClientHooks.registerLayerDefinition(CAMEL_HEAD, CamelHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SNIFFER_HEAD, SnifferHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(SKELETON_HORSE_HEAD, HorseHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ZOMBIE_HORSE_HEAD, HorseHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(DOLPHIN_HEAD, DolphinHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(PILLAGER_HEAD, IllagerHeadModel::getTexturedModelData);

        ClientHooks
            .registerLayerDefinition(
                EGG_LAYING_WOOLY_PIG_HEAD,
                EggLayingWoolyPigHeadModel::getTexturedModelData
            );
        ClientHooks.registerLayerDefinition(KINDLING_HEAD, KindlingHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(ERASER_HEAD, EraserHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(LIZARD_HEAD, LizardHeadModel::getTexturedModelData);
        ClientHooks.registerLayerDefinition(LIZARD_HEAD_FRILLS, LizardHeadModel::getTexturedModelDataFrills);
        ClientHooks
            .registerLayerDefinition(
                PRESERVATION_TURRET_HEAD,
                PreservationTurretHeadModel::getTexturedModelData
            );

        ClientHooks
            .registerLayerDefinition(
                MAIN_BEDROCK_LAYER,
                () -> LayerDefinition.create(BedrockArmorModel.getModelData(), 128, 128)
            );
    }

}
