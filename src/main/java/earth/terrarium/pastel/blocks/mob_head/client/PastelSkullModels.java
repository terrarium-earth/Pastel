package earth.terrarium.pastel.blocks.mob_head.client;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
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
import earth.terrarium.pastel.entity.render.EggLayingWoolyPigEntityRenderer;
import earth.terrarium.pastel.entity.render.PreservationTurretEntityRenderer;
import earth.terrarium.pastel.entity.variants.KindlingVariant;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.Map;

public class PastelSkullModels {
	public static void registerModels(EntityRenderersEvent.CreateSkullModels event) {
		EntityModelSet modelSet = event.getEntityModelSet();

		// Vanilla
		event.registerSkullModel(PastelSkullType.ALLAY, new AllayHeadModel(modelSet.bakeLayer(PastelModelLayers.ALLAY_HEAD)));
		event.registerSkullModel(PastelSkullType.AXOLOTL_BLUE, new AxolotlHeadModel(modelSet.bakeLayer(PastelModelLayers.AXOLOTL_BLUE_HEAD)));
		event.registerSkullModel(PastelSkullType.AXOLOTL_CYAN, new AxolotlHeadModel(modelSet.bakeLayer(PastelModelLayers.AXOLOTL_CYAN_HEAD)));
		event.registerSkullModel(PastelSkullType.AXOLOTL_GOLD, new AxolotlHeadModel(modelSet.bakeLayer(PastelModelLayers.AXOLOTL_GOLD_HEAD)));
		event.registerSkullModel(PastelSkullType.AXOLOTL_LEUCISTIC, new AxolotlHeadModel(modelSet.bakeLayer(PastelModelLayers.AXOLOTL_LEUCISTIC_HEAD)));
		event.registerSkullModel(PastelSkullType.AXOLOTL_WILD, new AxolotlHeadModel(modelSet.bakeLayer(PastelModelLayers.AXOLOTL_WILD_HEAD)));
		event.registerSkullModel(PastelSkullType.BAT, new BatHeadModel(modelSet.bakeLayer(PastelModelLayers.BAT_HEAD)));
		event.registerSkullModel(PastelSkullType.BEE, new BeeHeadModel(modelSet.bakeLayer(PastelModelLayers.BEE_HEAD)));
		event.registerSkullModel(PastelSkullType.BLAZE, new BlazeHeadModel(modelSet.bakeLayer(PastelModelLayers.BLAZE_HEAD)));
		event.registerSkullModel(PastelSkullType.CAMEL, new CamelHeadModel(modelSet.bakeLayer(PastelModelLayers.CAMEL_HEAD)));
		event.registerSkullModel(PastelSkullType.CAT, new CatHeadModel(modelSet.bakeLayer(PastelModelLayers.CAT_HEAD)));
		event.registerSkullModel(PastelSkullType.CAVE_SPIDER, new SpiderHeadModel(modelSet.bakeLayer(PastelModelLayers.CAVE_SPIDER_HEAD)));
		event.registerSkullModel(PastelSkullType.CHICKEN, new ChickenHeadModel(modelSet.bakeLayer(PastelModelLayers.CHICKEN_HEAD)));
		event.registerSkullModel(PastelSkullType.COW, new CowHeadModel(modelSet.bakeLayer(PastelModelLayers.COW_HEAD)));
		event.registerSkullModel(PastelSkullType.DOLPHIN, new DolphinHeadModel(modelSet.bakeLayer(PastelModelLayers.DOLPHIN_HEAD)));
		event.registerSkullModel(PastelSkullType.DONKEY, new HorseHeadModel(modelSet.bakeLayer(PastelModelLayers.DONKEY_HEAD)));
		event.registerSkullModel(PastelSkullType.DROWNED, new DrownedHeadModel(modelSet.bakeLayer(PastelModelLayers.DROWNED_HEAD)));
		event.registerSkullModel(PastelSkullType.ELDER_GUARDIAN, new GuardianHeadModel(modelSet.bakeLayer(PastelModelLayers.ELDER_GUARDIAN_HEAD)));
		event.registerSkullModel(PastelSkullType.ENDERMAN, new EndermanHeadModel(modelSet.bakeLayer(PastelModelLayers.ENDERMAN_HEAD)));
		event.registerSkullModel(PastelSkullType.ENDERMITE, new EndermiteHeadModel(modelSet.bakeLayer(PastelModelLayers.ENDERMITE_HEAD)));
		event.registerSkullModel(PastelSkullType.EVOKER, new IllagerHeadModel(modelSet.bakeLayer(PastelModelLayers.EVOKER_HEAD)));
		event.registerSkullModel(PastelSkullType.FOX, new FoxHeadModel(modelSet.bakeLayer(PastelModelLayers.FOX_HEAD)));
		event.registerSkullModel(PastelSkullType.FOX_ARCTIC, new FoxHeadModel(modelSet.bakeLayer(PastelModelLayers.FOX_ARCTIC_HEAD)));
		event.registerSkullModel(PastelSkullType.FROG_COLD, new FrogHeadModel(modelSet.bakeLayer(PastelModelLayers.FROG_COLD_HEAD)));
		event.registerSkullModel(PastelSkullType.FROG_TEMPERATE, new FrogHeadModel(modelSet.bakeLayer(PastelModelLayers.FROG_TEMPERATE_HEAD)));
		event.registerSkullModel(PastelSkullType.FROG_WARM, new FrogHeadModel(modelSet.bakeLayer(PastelModelLayers.FROG_WARM_HEAD)));
		event.registerSkullModel(PastelSkullType.GHAST, new GhastHeadModel(modelSet.bakeLayer(PastelModelLayers.GHAST_HEAD)));
		event.registerSkullModel(PastelSkullType.GLOW_SQUID, new SquidHeadModel(modelSet.bakeLayer(PastelModelLayers.GLOW_SQUID_HEAD)));
		event.registerSkullModel(PastelSkullType.GOAT, new GoatHeadModel(modelSet.bakeLayer(PastelModelLayers.GOAT_HEAD)));
		event.registerSkullModel(PastelSkullType.GUARDIAN, new GuardianHeadModel(modelSet.bakeLayer(PastelModelLayers.GUARDIAN_HEAD)));
		event.registerSkullModel(PastelSkullType.HOGLIN, new HoglinHeadModel(modelSet.bakeLayer(PastelModelLayers.HOGLIN_HEAD)));
		event.registerSkullModel(PastelSkullType.HORSE, new HorseHeadModel(modelSet.bakeLayer(PastelModelLayers.HORSE_HEAD)));
		event.registerSkullModel(PastelSkullType.HUSK, new ZombieHeadModel(modelSet.bakeLayer(PastelModelLayers.HUSK_HEAD)));
		event.registerSkullModel(PastelSkullType.ILLUSIONER, new IllagerHeadModel(modelSet.bakeLayer(PastelModelLayers.ILLUSIONER_HEAD)));
		event.registerSkullModel(PastelSkullType.IRON_GOLEM, new IronGolemHeadModel(modelSet.bakeLayer(PastelModelLayers.IRON_GOLEM_HEAD)));
		event.registerSkullModel(PastelSkullType.LLAMA, new LlamaHeadModel(modelSet.bakeLayer(PastelModelLayers.LLAMA_HEAD)));
		event.registerSkullModel(PastelSkullType.MAGMA_CUBE, new SlimeHeadModel(modelSet.bakeLayer(PastelModelLayers.MAGMA_CUBE_HEAD)));
		event.registerSkullModel(PastelSkullType.MOOSHROOM_BROWN, new CowHeadModel(modelSet.bakeLayer(PastelModelLayers.MOOSHROOM_BROWN_HEAD)));
		event.registerSkullModel(PastelSkullType.MOOSHROOM_RED, new CowHeadModel(modelSet.bakeLayer(PastelModelLayers.MOOSHROOM_RED_HEAD)));
		event.registerSkullModel(PastelSkullType.MULE, new HorseHeadModel(modelSet.bakeLayer(PastelModelLayers.MULE_HEAD)));
		event.registerSkullModel(PastelSkullType.OCELOT, new CatHeadModel(modelSet.bakeLayer(PastelModelLayers.OCELOT_HEAD)));
		event.registerSkullModel(PastelSkullType.PANDA, new PandaHeadModel(modelSet.bakeLayer(PastelModelLayers.PANDA_HEAD))); // pandas have variants
		event.registerSkullModel(PastelSkullType.PARROT_BLUE, new ParrotHeadModel(modelSet.bakeLayer(PastelModelLayers.PARROT_BLUE_HEAD)));
		event.registerSkullModel(PastelSkullType.PARROT_CYAN, new ParrotHeadModel(modelSet.bakeLayer(PastelModelLayers.PARROT_CYAN_HEAD))); // cyan vs. yellow_blue
		event.registerSkullModel(PastelSkullType.PARROT_GRAY, new ParrotHeadModel(modelSet.bakeLayer(PastelModelLayers.PARROT_GRAY_HEAD))); // gray vs.grey
		event.registerSkullModel(PastelSkullType.PARROT_GREEN, new ParrotHeadModel(modelSet.bakeLayer(PastelModelLayers.PARROT_GREEN_HEAD)));
		event.registerSkullModel(PastelSkullType.PARROT_RED, new ParrotHeadModel(modelSet.bakeLayer(PastelModelLayers.PARROT_RED_HEAD))); // red vs. red_blue
		event.registerSkullModel(PastelSkullType.PHANTOM, new PhantomHeadModel(modelSet.bakeLayer(PastelModelLayers.PHANTOM_HEAD)));
		event.registerSkullModel(PastelSkullType.PIG, new PigHeadModel(modelSet.bakeLayer(PastelModelLayers.PIG_HEAD)));
		event.registerSkullModel(PastelSkullType.PILLAGER, new IllagerHeadModel(modelSet.bakeLayer(PastelModelLayers.PILLAGER_HEAD)));
		event.registerSkullModel(PastelSkullType.POLAR_BEAR, new BearHeadModel(modelSet.bakeLayer(PastelModelLayers.POLAR_BEAR_HEAD)));
		event.registerSkullModel(PastelSkullType.PUFFERFISH, new PufferFishHeadModel(modelSet.bakeLayer(PastelModelLayers.PUFFERFISH_HEAD))); // other fish?
		event.registerSkullModel(PastelSkullType.RABBIT, new RabbitHeadModel(modelSet.bakeLayer(PastelModelLayers.RABBIT_HEAD))); // rabbits have variant
		event.registerSkullModel(PastelSkullType.RAVAGER, new RavagerHeadModel(modelSet.bakeLayer(PastelModelLayers.RAVAGER_HEAD)));
		event.registerSkullModel(PastelSkullType.SALMON, new SalmonHeadModel(modelSet.bakeLayer(PastelModelLayers.SALMON_HEAD))); // other fish?
		event.registerSkullModel(PastelSkullType.SHEEP, new SheepHeadModel(modelSet.bakeLayer(PastelModelLayers.SHEEP_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_BLACK, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_BLACK_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_BLUE, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_BLUE_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_BROWN, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_BROWN_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_CYAN, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_CYAN_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_GRAY, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_GRAY_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_GREEN, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_GREEN_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_LIGHT_BLUE, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_LIGHT_BLUE_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_LIGHT_GRAY, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_LIGHT_GRAY_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_LIME, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_LIME_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_MAGENTA, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_MAGENTA_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_ORANGE, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_ORANGE_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_PINK, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_PINK_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_PURPLE, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_PURPLE_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_RED, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_RED_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_WHITE, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_WHITE_HEAD)));
		event.registerSkullModel(PastelSkullType.SHULKER_YELLOW, new ShulkerHeadModel(modelSet.bakeLayer(PastelModelLayers.SHULKER_YELLOW_HEAD)));
		event.registerSkullModel(PastelSkullType.SILVERFISH, new SilverfishHeadModel(modelSet.bakeLayer(PastelModelLayers.SILVERFISH_HEAD)));
		event.registerSkullModel(PastelSkullType.SKELETON_HORSE, new HorseHeadModel(modelSet.bakeLayer(PastelModelLayers.SKELETON_HORSE_HEAD)));
		event.registerSkullModel(PastelSkullType.SLIME, new SlimeHeadModel(modelSet.bakeLayer(PastelModelLayers.SLIME_HEAD)));
		event.registerSkullModel(PastelSkullType.SNIFFER, new SnifferHeadModel(modelSet.bakeLayer(PastelModelLayers.SNIFFER_HEAD)));
		event.registerSkullModel(PastelSkullType.SNOW_GOLEM, new ZombieHeadModel(modelSet.bakeLayer(PastelModelLayers.SNOW_GOLEM_HEAD)));
		event.registerSkullModel(PastelSkullType.SPIDER, new SpiderHeadModel(modelSet.bakeLayer(PastelModelLayers.SPIDER_HEAD)));
		event.registerSkullModel(PastelSkullType.SQUID, new SquidHeadModel(modelSet.bakeLayer(PastelModelLayers.SQUID_HEAD)));

		event.registerSkullModel(PastelSkullType.STRAY, new PastelSkullModel(modelSet.bakeLayer(PastelModelLayers.STRAY_HEAD)));
		event.registerSkullModel(PastelSkullType.STRIDER, new StriderHeadModel(modelSet.bakeLayer(PastelModelLayers.STRIDER_HEAD)));
		event.registerSkullModel(PastelSkullType.TADPOLE, new TadpoleHeadModel(modelSet.bakeLayer(PastelModelLayers.TADPOLE_HEAD)));
		event.registerSkullModel(PastelSkullType.TROPICAL_FISH, new TropicalFishHeadModel(modelSet.bakeLayer(PastelModelLayers.TROPICAL_FISH_HEAD), modelSet.bakeLayer(PastelModelLayers.TROPICAL_FISH_HEAD_PATTERN))); // oof
		event.registerSkullModel(PastelSkullType.TURTLE, new TurtleHeadModel(modelSet.bakeLayer(PastelModelLayers.TURTLE_HEAD)));
		event.registerSkullModel(PastelSkullType.VEX, new VexHeadModel(modelSet.bakeLayer(PastelModelLayers.VEX_HEAD)));
		event.registerSkullModel(PastelSkullType.VILLAGER, new VillagerHeadModel(modelSet.bakeLayer(PastelModelLayers.VILLAGER_HEAD)));
		event.registerSkullModel(PastelSkullType.VINDICATOR, new IllagerHeadModel(modelSet.bakeLayer(PastelModelLayers.VINDICATOR_HEAD)));
		event.registerSkullModel(PastelSkullType.WANDERING_TRADER, new VillagerHeadModel(modelSet.bakeLayer(PastelModelLayers.WANDERING_TRADER_HEAD)));
		event.registerSkullModel(PastelSkullType.WARDEN, new WardenHeadModel(modelSet.bakeLayer(PastelModelLayers.WARDEN_HEAD)));
		event.registerSkullModel(PastelSkullType.WITCH, new WitchHeadModel(modelSet.bakeLayer(PastelModelLayers.WITCH_HEAD)));
		event.registerSkullModel(PastelSkullType.WITHER, new ZombieHeadModel(modelSet.bakeLayer(PastelModelLayers.WITHER_HEAD)));
		event.registerSkullModel(PastelSkullType.WOLF, new WolfHeadModel(modelSet.bakeLayer(PastelModelLayers.WOLF_HEAD)));
		event.registerSkullModel(PastelSkullType.ZOGLIN, new HoglinHeadModel(modelSet.bakeLayer(PastelModelLayers.ZOGLIN_HEAD)));
		event.registerSkullModel(PastelSkullType.ZOMBIE_HORSE, new HorseHeadModel(modelSet.bakeLayer(PastelModelLayers.ZOMBIE_HORSE_HEAD)));
		event.registerSkullModel(PastelSkullType.ZOMBIE_VILLAGER, new VillagerHeadModel(modelSet.bakeLayer(PastelModelLayers.ZOMBIE_VILLAGER_HEAD)));
		event.registerSkullModel(PastelSkullType.ZOMBIFIED_PIGLIN, new PiglinHeadModel(modelSet.bakeLayer(PastelModelLayers.ZOMBIFIED_PIGLIN_HEAD)));
		event.registerSkullModel(PastelSkullType.PIGLIN_BRUTE, new PiglinHeadModel(modelSet.bakeLayer(PastelModelLayers.PIGLIN_BRUTE_HEAD)));

		event.registerSkullModel(PastelSkullType.ARMADILLO, new ArmadilloHeadModel(modelSet.bakeLayer(PastelModelLayers.ARMADILLO_HEAD)));
		event.registerSkullModel(PastelSkullType.BREEZE, new BreezeHeadModel(modelSet.bakeLayer(PastelModelLayers.BREEZE_HEAD)));
		event.registerSkullModel(PastelSkullType.BOGGED, new BoggedHeadModel(modelSet.bakeLayer(PastelModelLayers.BOGGED_HEAD)));

		// Spectrum
		event.registerSkullModel(PastelSkullType.EGG_LAYING_WOOLY_PIG, new EggLayingWoolyPigHeadModel(modelSet.bakeLayer(PastelModelLayers.EGG_LAYING_WOOLY_PIG_HEAD)));
		event.registerSkullModel(PastelSkullType.ERASER, new SpiderHeadModel(modelSet.bakeLayer(PastelModelLayers.ERASER_HEAD)));
		event.registerSkullModel(PastelSkullType.KINDLING, new KindlingHeadModel(modelSet.bakeLayer(PastelModelLayers.KINDLING_HEAD)));
		event.registerSkullModel(PastelSkullType.LIZARD_BLACK, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.BLACK.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_BLUE, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.BLUE.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_BROWN, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.BROWN.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_CYAN, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.CYAN.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_GRAY, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.GRAY.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_GREEN, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.GREEN.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_LIGHT_BLUE, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.LIGHT_BLUE.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_LIGHT_GRAY, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.LIGHT_GRAY.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_LIME, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.LIME.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_MAGENTA, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.MAGENTA.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_ORANGE, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.ORANGE.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_PINK, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.PINK.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_PURPLE, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.PURPLE.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_RED, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.RED.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_WHITE, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.WHITE.getColorInt()));
		event.registerSkullModel(PastelSkullType.LIZARD_YELLOW, new LizardHeadModel(modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelSet.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.YELLOW.getColorInt()));
		event.registerSkullModel(PastelSkullType.PRESERVATION_TURRET, new PreservationTurretHeadModel(modelSet.bakeLayer(PastelModelLayers.PRESERVATION_TURRET_HEAD)));
	}

	public static void registerTextures(@SuppressWarnings("unused") FMLClientSetupEvent event) {
		Map<SkullBlock.Type, ResourceLocation> textures = SkullBlockRenderer.SKIN_BY_TYPE;

		// Vanilla
		textures.put(PastelSkullType.ALLAY, ResourceLocation.parse("textures/entity/allay/allay.png"));
		textures.put(PastelSkullType.AXOLOTL_BLUE, ResourceLocation.parse("textures/entity/axolotl/axolotl_blue.png"));
		textures.put(PastelSkullType.AXOLOTL_CYAN, ResourceLocation.parse("textures/entity/axolotl/axolotl_cyan.png"));
		textures.put(PastelSkullType.AXOLOTL_GOLD, ResourceLocation.parse("textures/entity/axolotl/axolotl_gold.png"));
		textures.put(PastelSkullType.AXOLOTL_LEUCISTIC, ResourceLocation.parse("textures/entity/axolotl/axolotl_lucy.png"));
		textures.put(PastelSkullType.AXOLOTL_WILD, ResourceLocation.parse("textures/entity/axolotl/axolotl_wild.png"));
		textures.put(PastelSkullType.BAT, ResourceLocation.parse("textures/entity/bat.png"));
		textures.put(PastelSkullType.BEE, ResourceLocation.parse("textures/entity/bee/bee.png"));
		textures.put(PastelSkullType.BLAZE, ResourceLocation.parse("textures/entity/blaze.png"));
		textures.put(PastelSkullType.CAMEL, ResourceLocation.parse("textures/entity/camel/camel.png"));
		textures.put(PastelSkullType.CAT, ResourceLocation.parse("textures/entity/cat/tabby.png"));
		textures.put(PastelSkullType.CAVE_SPIDER, ResourceLocation.parse("textures/entity/spider/cave_spider.png"));
		textures.put(PastelSkullType.CHICKEN, ResourceLocation.parse("textures/entity/chicken.png"));
		textures.put(PastelSkullType.COW, ResourceLocation.parse("textures/entity/cow/cow.png"));
		textures.put(PastelSkullType.DOLPHIN, ResourceLocation.parse("textures/entity/dolphin.png"));
		textures.put(PastelSkullType.DONKEY, ResourceLocation.parse("textures/entity/horse/donkey.png"));
		textures.put(PastelSkullType.DROWNED, ResourceLocation.parse("textures/entity/zombie/drowned.png"));
		textures.put(PastelSkullType.ELDER_GUARDIAN, ResourceLocation.parse("textures/entity/guardian_elder.png"));
		textures.put(PastelSkullType.ENDERMAN, ResourceLocation.parse("textures/entity/enderman/enderman.png"));
		textures.put(PastelSkullType.ENDERMITE, ResourceLocation.parse("textures/entity/endermite.png"));
		textures.put(PastelSkullType.EVOKER, ResourceLocation.parse("textures/entity/illager/evoker.png"));
		textures.put(PastelSkullType.FOX, ResourceLocation.parse("textures/entity/fox/fox.png"));
		textures.put(PastelSkullType.FOX_ARCTIC, ResourceLocation.parse("textures/entity/fox/snow_fox.png"));
		textures.put(PastelSkullType.FROG_COLD, ResourceLocation.parse("textures/entity/frog/cold_frog.png"));
		textures.put(PastelSkullType.FROG_TEMPERATE, ResourceLocation.parse("textures/entity/frog/temperate_frog.png"));
		textures.put(PastelSkullType.FROG_WARM, ResourceLocation.parse("textures/entity/frog/warm_frog.png"));
		textures.put(PastelSkullType.GHAST, ResourceLocation.parse("textures/entity/ghast/ghast.png"));
		textures.put(PastelSkullType.GLOW_SQUID, ResourceLocation.parse("textures/entity/squid/glow_squid.png"));
		textures.put(PastelSkullType.GOAT, ResourceLocation.parse("textures/entity/goat/goat.png"));
		textures.put(PastelSkullType.GUARDIAN, ResourceLocation.parse("textures/entity/guardian.png"));
		textures.put(PastelSkullType.HOGLIN, ResourceLocation.parse("textures/entity/hoglin/hoglin.png"));
		textures.put(PastelSkullType.HORSE, ResourceLocation.parse("textures/entity/horse/horse_chestnut.png"));
		textures.put(PastelSkullType.HUSK, ResourceLocation.parse("textures/entity/zombie/husk.png"));
		textures.put(PastelSkullType.ILLUSIONER, ResourceLocation.parse("textures/entity/illager/illusioner.png"));
		textures.put(PastelSkullType.IRON_GOLEM, ResourceLocation.parse("textures/entity/iron_golem/iron_golem.png"));
		textures.put(PastelSkullType.LLAMA, ResourceLocation.parse("textures/entity/llama/gray.png"));
		textures.put(PastelSkullType.MAGMA_CUBE, ResourceLocation.parse("textures/entity/slime/magmacube.png"));
		textures.put(PastelSkullType.MOOSHROOM_BROWN, ResourceLocation.parse("textures/entity/cow/brown_mooshroom.png"));
		textures.put(PastelSkullType.MOOSHROOM_RED, ResourceLocation.parse("textures/entity/cow/red_mooshroom.png"));
		textures.put(PastelSkullType.MULE, ResourceLocation.parse("textures/entity/horse/mule.png"));
		textures.put(PastelSkullType.OCELOT, ResourceLocation.parse("textures/entity/cat/ocelot.png"));
		textures.put(PastelSkullType.PANDA, ResourceLocation.parse("textures/entity/panda/panda.png")); // pandas have variants
		textures.put(PastelSkullType.PARROT_BLUE, ResourceLocation.parse("textures/entity/parrot/parrot_blue.png"));
		textures.put(PastelSkullType.PARROT_CYAN, ResourceLocation.parse("textures/entity/parrot/parrot_yellow_blue.png")); // cyan vs. yellow_blue
		textures.put(PastelSkullType.PARROT_GRAY, ResourceLocation.parse("textures/entity/parrot/parrot_grey.png")); // gray vs.grey
		textures.put(PastelSkullType.PARROT_GREEN, ResourceLocation.parse("textures/entity/parrot/parrot_green.png"));
		textures.put(PastelSkullType.PARROT_RED, ResourceLocation.parse("textures/entity/parrot/parrot_red_blue.png")); // red vs. red_blue
		textures.put(PastelSkullType.PHANTOM, ResourceLocation.parse("textures/entity/phantom.png"));
		textures.put(PastelSkullType.PIG, ResourceLocation.parse("textures/entity/pig/pig.png"));
		textures.put(PastelSkullType.PILLAGER, ResourceLocation.parse("textures/entity/illager/pillager.png"));
		textures.put(PastelSkullType.POLAR_BEAR, ResourceLocation.parse("textures/entity/bear/polarbear.png"));
		textures.put(PastelSkullType.PUFFERFISH, ResourceLocation.parse("textures/entity/fish/pufferfish.png")); // other fish?
		textures.put(PastelSkullType.RABBIT, ResourceLocation.parse("textures/entity/rabbit/brown.png")); // rabbits have variant
		textures.put(PastelSkullType.RAVAGER, ResourceLocation.parse("textures/entity/illager/ravager.png"));
		textures.put(PastelSkullType.SALMON, ResourceLocation.parse("textures/entity/fish/salmon.png")); // other fish?
		textures.put(PastelSkullType.SHEEP, ResourceLocation.parse("textures/entity/sheep/sheep.png"));
		textures.put(PastelSkullType.SHULKER, ResourceLocation.parse("textures/entity/shulker/shulker.png"));
		textures.put(PastelSkullType.SHULKER_BLACK, ResourceLocation.parse("textures/entity/shulker/shulker_black.png"));
		textures.put(PastelSkullType.SHULKER_BLUE, ResourceLocation.parse("textures/entity/shulker/shulker_blue.png"));
		textures.put(PastelSkullType.SHULKER_BROWN, ResourceLocation.parse("textures/entity/shulker/shulker_brown.png"));
		textures.put(PastelSkullType.SHULKER_CYAN, ResourceLocation.parse("textures/entity/shulker/shulker_cyan.png"));
		textures.put(PastelSkullType.SHULKER_GRAY, ResourceLocation.parse("textures/entity/shulker/shulker_gray.png"));
		textures.put(PastelSkullType.SHULKER_GREEN, ResourceLocation.parse("textures/entity/shulker/shulker_green.png"));
		textures.put(PastelSkullType.SHULKER_LIGHT_BLUE, ResourceLocation.parse("textures/entity/shulker/shulker_light_blue.png"));
		textures.put(PastelSkullType.SHULKER_LIGHT_GRAY, ResourceLocation.parse("textures/entity/shulker/shulker_light_gray.png"));
		textures.put(PastelSkullType.SHULKER_LIME, ResourceLocation.parse("textures/entity/shulker/shulker_lime.png"));
		textures.put(PastelSkullType.SHULKER_MAGENTA, ResourceLocation.parse("textures/entity/shulker/shulker_magenta.png"));
		textures.put(PastelSkullType.SHULKER_ORANGE, ResourceLocation.parse("textures/entity/shulker/shulker_orange.png"));
		textures.put(PastelSkullType.SHULKER_PINK, ResourceLocation.parse("textures/entity/shulker/shulker_pink.png"));
		textures.put(PastelSkullType.SHULKER_PURPLE, ResourceLocation.parse("textures/entity/shulker/shulker_purple.png"));
		textures.put(PastelSkullType.SHULKER_RED, ResourceLocation.parse("textures/entity/shulker/shulker_red.png"));
		textures.put(PastelSkullType.SHULKER_WHITE, ResourceLocation.parse("textures/entity/shulker/shulker_white.png"));
		textures.put(PastelSkullType.SHULKER_YELLOW, ResourceLocation.parse("textures/entity/shulker/shulker_yellow.png"));
		textures.put(PastelSkullType.SILVERFISH, ResourceLocation.parse("textures/entity/silverfish.png"));
		textures.put(PastelSkullType.SKELETON_HORSE, ResourceLocation.parse("textures/entity/horse/horse_skeleton.png"));
		textures.put(PastelSkullType.SLIME, ResourceLocation.parse("textures/entity/slime/slime.png"));
		textures.put(PastelSkullType.SNIFFER, ResourceLocation.parse("textures/entity/sniffer/sniffer.png"));
		textures.put(PastelSkullType.SNOW_GOLEM, ResourceLocation.parse("textures/entity/snow_golem.png"));
		textures.put(PastelSkullType.SPIDER, ResourceLocation.parse("textures/entity/spider/spider.png"));
		textures.put(PastelSkullType.SQUID, ResourceLocation.parse("textures/entity/squid/squid.png"));
		textures.put(PastelSkullType.STRAY, ResourceLocation.parse("textures/entity/skeleton/stray.png"));
		textures.put(PastelSkullType.STRIDER, ResourceLocation.parse("textures/entity/strider/strider.png"));
		textures.put(PastelSkullType.TADPOLE, ResourceLocation.parse("textures/entity/tadpole/tadpole.png"));
		textures.put(PastelSkullType.TROPICAL_FISH, ResourceLocation.parse("textures/entity/fish/tropical_a.png")); // oof
		textures.put(PastelSkullType.TURTLE, ResourceLocation.parse("textures/entity/turtle/big_sea_turtle.png"));
		textures.put(PastelSkullType.VEX, ResourceLocation.parse("textures/entity/illager/vex.png"));
		textures.put(PastelSkullType.VILLAGER, ResourceLocation.parse("textures/entity/villager/villager.png"));
		textures.put(PastelSkullType.VINDICATOR, ResourceLocation.parse("textures/entity/illager/vindicator.png"));
		textures.put(PastelSkullType.WANDERING_TRADER, ResourceLocation.parse("textures/entity/wandering_trader.png"));
		textures.put(PastelSkullType.WARDEN, ResourceLocation.parse("textures/entity/warden/warden.png"));
		textures.put(PastelSkullType.WITCH, ResourceLocation.parse("textures/entity/witch.png"));
		textures.put(PastelSkullType.WITHER, ResourceLocation.parse("textures/entity/wither/wither.png"));
		textures.put(PastelSkullType.WOLF, ResourceLocation.parse("textures/entity/wolf/wolf.png"));
		textures.put(PastelSkullType.ZOGLIN, ResourceLocation.parse("textures/entity/hoglin/zoglin.png"));
		textures.put(PastelSkullType.ZOMBIE_HORSE, ResourceLocation.parse("textures/entity/horse/horse_zombie.png"));
		textures.put(PastelSkullType.ZOMBIE_VILLAGER, ResourceLocation.parse("textures/entity/zombie_villager/zombie_villager.png"));
		textures.put(PastelSkullType.ZOMBIFIED_PIGLIN, ResourceLocation.parse("textures/entity/piglin/zombified_piglin.png"));
		textures.put(PastelSkullType.PIGLIN_BRUTE, ResourceLocation.parse("textures/entity/piglin/piglin_brute.png"));

		textures.put(PastelSkullType.ARMADILLO, ResourceLocation.parse("textures/entity/armadillo.png"));
		textures.put(PastelSkullType.BREEZE, ResourceLocation.parse("textures/entity/breeze/breeze.png"));
		textures.put(PastelSkullType.BOGGED, ResourceLocation.parse("textures/entity/skeleton/bogged.png"));

		// Spectrum
		textures.put(PastelSkullType.EGG_LAYING_WOOLY_PIG, EggLayingWoolyPigEntityRenderer.TEXTURE);
		textures.put(PastelSkullType.ERASER, PastelCommon.locate("textures/entity/eraser/eraser_combined.png"));
		textures.put(PastelSkullType.KINDLING, KindlingVariant.DEFAULT.getDefaultTexture());
		textures.put(PastelSkullType.LIZARD_BLACK, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_BLUE, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_BROWN, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_CYAN, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_GRAY, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_GREEN, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_LIGHT_BLUE, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_LIGHT_GRAY, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_LIME, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_MAGENTA, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_ORANGE, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_PINK, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_PURPLE, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_RED, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_WHITE, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.LIZARD_YELLOW, LizardHeadModel.HEAD_TEXTURE);
		textures.put(PastelSkullType.PRESERVATION_TURRET, PreservationTurretEntityRenderer.TEXTURE);

	}
}
