package earth.terrarium.pastel.blocks.mob_head.client;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlockEntity;
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
import earth.terrarium.pastel.entity.render.EggLayingWoolyPigEntityRenderer;
import earth.terrarium.pastel.entity.render.PreservationTurretEntityRenderer;
import earth.terrarium.pastel.entity.variants.KindlingVariant;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PastelSkullBlockEntityRenderer implements BlockEntityRenderer<PastelSkullBlockEntity> {

	private static Map<SkullBlock.Type, List<Tuple<PastelSkullModel, ResourceLocation>>> MODELS = new HashMap<>();

    public PastelSkullBlockEntityRenderer(BlockEntityRendererProvider.Context renderContext) {
        MODELS = getModels(renderContext.getModelSet());
    }

	public static Map<SkullBlock.Type, List<Tuple<PastelSkullModel, ResourceLocation>>> getModels(EntityModelSet modelLoader) {
		ImmutableMap.Builder<SkullBlock.Type, List<Tuple<PastelSkullModel, ResourceLocation>>> builder = ImmutableBiMap.builder();

        // Vanilla
		builder.put(PastelSkullType.ALLAY, List.of(new Tuple<>(new AllayHeadModel(modelLoader.bakeLayer(PastelModelLayers.ALLAY_HEAD)), ResourceLocation.parse("textures/entity/allay/allay.png"))));
		builder.put(PastelSkullType.AXOLOTL_BLUE, List.of(new Tuple<>(new AxolotlHeadModel(modelLoader.bakeLayer(PastelModelLayers.AXOLOTL_BLUE_HEAD)), ResourceLocation.parse("textures/entity/axolotl/axolotl_blue.png"))));
		builder.put(PastelSkullType.AXOLOTL_CYAN, List.of(new Tuple<>(new AxolotlHeadModel(modelLoader.bakeLayer(PastelModelLayers.AXOLOTL_CYAN_HEAD)), ResourceLocation.parse("textures/entity/axolotl/axolotl_cyan.png"))));
		builder.put(PastelSkullType.AXOLOTL_GOLD, List.of(new Tuple<>(new AxolotlHeadModel(modelLoader.bakeLayer(PastelModelLayers.AXOLOTL_GOLD_HEAD)), ResourceLocation.parse("textures/entity/axolotl/axolotl_gold.png"))));
		builder.put(PastelSkullType.AXOLOTL_LEUCISTIC, List.of(new Tuple<>(new AxolotlHeadModel(modelLoader.bakeLayer(PastelModelLayers.AXOLOTL_LEUCISTIC_HEAD)), ResourceLocation.parse("textures/entity/axolotl/axolotl_lucy.png"))));
		builder.put(PastelSkullType.AXOLOTL_WILD, List.of(new Tuple<>(new AxolotlHeadModel(modelLoader.bakeLayer(PastelModelLayers.AXOLOTL_WILD_HEAD)), ResourceLocation.parse("textures/entity/axolotl/axolotl_wild.png"))));
		builder.put(PastelSkullType.BAT, List.of(new Tuple<>(new BatHeadModel(modelLoader.bakeLayer(PastelModelLayers.BAT_HEAD)), ResourceLocation.parse("textures/entity/bat.png"))));
		builder.put(PastelSkullType.BEE, List.of(new Tuple<>(new BeeHeadModel(modelLoader.bakeLayer(PastelModelLayers.BEE_HEAD)), ResourceLocation.parse("textures/entity/bee/bee.png"))));
		builder.put(PastelSkullType.BLAZE, List.of(new Tuple<>(new BlazeHeadModel(modelLoader.bakeLayer(PastelModelLayers.BLAZE_HEAD)), ResourceLocation.parse("textures/entity/blaze.png"))));
		builder.put(PastelSkullType.CAMEL, List.of(new Tuple<>(new CamelHeadModel(modelLoader.bakeLayer(PastelModelLayers.CAMEL_HEAD)), ResourceLocation.parse("textures/entity/camel/camel.png"))));
		builder.put(PastelSkullType.CAT, List.of(new Tuple<>(new CatHeadModel(modelLoader.bakeLayer(PastelModelLayers.CAT_HEAD)), ResourceLocation.parse("textures/entity/cat/tabby.png"))));
		builder.put(PastelSkullType.CAVE_SPIDER, List.of(new Tuple<>(new SpiderHeadModel(modelLoader.bakeLayer(PastelModelLayers.CAVE_SPIDER_HEAD)), ResourceLocation.parse("textures/entity/spider/cave_spider.png"))));
		builder.put(PastelSkullType.CHICKEN, List.of(new Tuple<>(new ChickenHeadModel(modelLoader.bakeLayer(PastelModelLayers.CHICKEN_HEAD)), ResourceLocation.parse("textures/entity/chicken.png"))));
		builder.put(PastelSkullType.COW, List.of(new Tuple<>(new CowHeadModel(modelLoader.bakeLayer(PastelModelLayers.COW_HEAD)), ResourceLocation.parse("textures/entity/cow/cow.png"))));
		builder.put(PastelSkullType.DOLPHIN, List.of(new Tuple<>(new DolphinHeadModel(modelLoader.bakeLayer(PastelModelLayers.DOLPHIN_HEAD)), ResourceLocation.parse("textures/entity/dolphin.png"))));
		builder.put(PastelSkullType.DONKEY, List.of(new Tuple<>(new HorseHeadModel(modelLoader.bakeLayer(PastelModelLayers.DONKEY_HEAD)), ResourceLocation.parse("textures/entity/horse/donkey.png"))));
		builder.put(PastelSkullType.DROWNED, List.of(
				new Tuple<>(new DrownedHeadModel(modelLoader.bakeLayer(PastelModelLayers.DROWNED_HEAD)), ResourceLocation.parse("textures/entity/zombie/drowned.png")),
				new Tuple<>(new DrownedHeadModel(modelLoader.bakeLayer(PastelModelLayers.DROWNED_HEAD_OUTER)), ResourceLocation.parse("textures/entity/zombie/drowned_outer_layer.png"))
		));
		builder.put(PastelSkullType.ELDER_GUARDIAN, List.of(new Tuple<>(new GuardianHeadModel(modelLoader.bakeLayer(PastelModelLayers.ELDER_GUARDIAN_HEAD)), ResourceLocation.parse("textures/entity/guardian_elder.png"))));
		builder.put(PastelSkullType.ENDERMAN, List.of(
				new Tuple<>(new EndermanHeadModel(modelLoader.bakeLayer(PastelModelLayers.ENDERMAN_HEAD)), ResourceLocation.parse("textures/entity/enderman/enderman.png")),
				new Tuple<>(new EndermanHeadModel(modelLoader.bakeLayer(PastelModelLayers.ENDERMAN_HEAD_OVERLAY)), ResourceLocation.parse("textures/entity/enderman/enderman_eyes.png"))
		));
		builder.put(PastelSkullType.ENDERMITE, List.of(new Tuple<>(new EndermiteHeadModel(modelLoader.bakeLayer(PastelModelLayers.ENDERMITE_HEAD)), ResourceLocation.parse("textures/entity/endermite.png"))));
		builder.put(PastelSkullType.EVOKER, List.of(new Tuple<>(new IllagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.EVOKER_HEAD)), ResourceLocation.parse("textures/entity/illager/evoker.png"))));
		builder.put(PastelSkullType.FOX, List.of(new Tuple<>(new FoxHeadModel(modelLoader.bakeLayer(PastelModelLayers.FOX_HEAD)), ResourceLocation.parse("textures/entity/fox/fox.png"))));
		builder.put(PastelSkullType.FOX_ARCTIC, List.of(new Tuple<>(new FoxHeadModel(modelLoader.bakeLayer(PastelModelLayers.FOX_ARCTIC_HEAD)), ResourceLocation.parse("textures/entity/fox/snow_fox.png"))));
		builder.put(PastelSkullType.FROG_COLD, List.of(new Tuple<>(new FrogHeadModel(modelLoader.bakeLayer(PastelModelLayers.FROG_COLD_HEAD)), ResourceLocation.parse("textures/entity/frog/cold_frog.png"))));
		builder.put(PastelSkullType.FROG_TEMPERATE, List.of(new Tuple<>(new FrogHeadModel(modelLoader.bakeLayer(PastelModelLayers.FROG_TEMPERATE_HEAD)), ResourceLocation.parse("textures/entity/frog/temperate_frog.png"))));
		builder.put(PastelSkullType.FROG_WARM, List.of(new Tuple<>(new FrogHeadModel(modelLoader.bakeLayer(PastelModelLayers.FROG_WARM_HEAD)), ResourceLocation.parse("textures/entity/frog/warm_frog.png"))));
		builder.put(PastelSkullType.GHAST, List.of(new Tuple<>(new GhastHeadModel(modelLoader.bakeLayer(PastelModelLayers.GHAST_HEAD)), ResourceLocation.parse("textures/entity/ghast/ghast.png"))));
		builder.put(PastelSkullType.GLOW_SQUID, List.of(new Tuple<>(new SquidHeadModel(modelLoader.bakeLayer(PastelModelLayers.GLOW_SQUID_HEAD)), ResourceLocation.parse("textures/entity/squid/glow_squid.png"))));
		builder.put(PastelSkullType.GOAT, List.of(new Tuple<>(new GoatHeadModel(modelLoader.bakeLayer(PastelModelLayers.GOAT_HEAD)), ResourceLocation.parse("textures/entity/goat/goat.png"))));
		builder.put(PastelSkullType.GUARDIAN, List.of(new Tuple<>(new GuardianHeadModel(modelLoader.bakeLayer(PastelModelLayers.GUARDIAN_HEAD)), ResourceLocation.parse("textures/entity/guardian.png"))));
		builder.put(PastelSkullType.HOGLIN, List.of(new Tuple<>(new HoglinHeadModel(modelLoader.bakeLayer(PastelModelLayers.HOGLIN_HEAD)), ResourceLocation.parse("textures/entity/hoglin/hoglin.png"))));
		builder.put(PastelSkullType.HORSE, List.of(new Tuple<>(new HorseHeadModel(modelLoader.bakeLayer(PastelModelLayers.HORSE_HEAD)), ResourceLocation.parse("textures/entity/horse/horse_chestnut.png"))));
		builder.put(PastelSkullType.HUSK, List.of(new Tuple<>(new ZombieHeadModel(modelLoader.bakeLayer(PastelModelLayers.HUSK_HEAD)), ResourceLocation.parse("textures/entity/zombie/husk.png"))));
		builder.put(PastelSkullType.ILLUSIONER, List.of(new Tuple<>(new IllagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.ILLUSIONER_HEAD)), ResourceLocation.parse("textures/entity/illager/illusioner.png"))));
		builder.put(PastelSkullType.IRON_GOLEM, List.of(new Tuple<>(new IronGolemHeadModel(modelLoader.bakeLayer(PastelModelLayers.IRON_GOLEM_HEAD)), ResourceLocation.parse("textures/entity/iron_golem/iron_golem.png"))));
		builder.put(PastelSkullType.LLAMA, List.of(new Tuple<>(new LlamaHeadModel(modelLoader.bakeLayer(PastelModelLayers.LLAMA_HEAD)), ResourceLocation.parse("textures/entity/llama/gray.png"))));
		builder.put(PastelSkullType.MAGMA_CUBE, List.of(new Tuple<>(new SlimeHeadModel(modelLoader.bakeLayer(PastelModelLayers.MAGMA_CUBE_HEAD)), ResourceLocation.parse("textures/entity/slime/magmacube.png"))));
		builder.put(PastelSkullType.MOOSHROOM_BROWN, List.of(new Tuple<>(new CowHeadModel(modelLoader.bakeLayer(PastelModelLayers.MOOSHROOM_BROWN_HEAD)), ResourceLocation.parse("textures/entity/cow/brown_mooshroom.png"))));
		builder.put(PastelSkullType.MOOSHROOM_RED, List.of(new Tuple<>(new CowHeadModel(modelLoader.bakeLayer(PastelModelLayers.MOOSHROOM_RED_HEAD)), ResourceLocation.parse("textures/entity/cow/red_mooshroom.png"))));
		builder.put(PastelSkullType.MULE, List.of(new Tuple<>(new HorseHeadModel(modelLoader.bakeLayer(PastelModelLayers.MULE_HEAD)), ResourceLocation.parse("textures/entity/horse/mule.png"))));
		builder.put(PastelSkullType.OCELOT, List.of(new Tuple<>(new CatHeadModel(modelLoader.bakeLayer(PastelModelLayers.OCELOT_HEAD)), ResourceLocation.parse("textures/entity/cat/ocelot.png"))));
		builder.put(PastelSkullType.PANDA, List.of(new Tuple<>(new PandaHeadModel(modelLoader.bakeLayer(PastelModelLayers.PANDA_HEAD)), ResourceLocation.parse("textures/entity/panda/panda.png")))); // pandas have variants
		builder.put(PastelSkullType.PARROT_BLUE, List.of(new Tuple<>(new ParrotHeadModel(modelLoader.bakeLayer(PastelModelLayers.PARROT_BLUE_HEAD)), ResourceLocation.parse("textures/entity/parrot/parrot_blue.png"))));
		builder.put(PastelSkullType.PARROT_CYAN, List.of(new Tuple<>(new ParrotHeadModel(modelLoader.bakeLayer(PastelModelLayers.PARROT_CYAN_HEAD)), ResourceLocation.parse("textures/entity/parrot/parrot_yellow_blue.png")))); // cyan vs. yellow_blue
		builder.put(PastelSkullType.PARROT_GRAY, List.of(new Tuple<>(new ParrotHeadModel(modelLoader.bakeLayer(PastelModelLayers.PARROT_GRAY_HEAD)), ResourceLocation.parse("textures/entity/parrot/parrot_grey.png")))); // gray vs.grey
		builder.put(PastelSkullType.PARROT_GREEN, List.of(new Tuple<>(new ParrotHeadModel(modelLoader.bakeLayer(PastelModelLayers.PARROT_GREEN_HEAD)), ResourceLocation.parse("textures/entity/parrot/parrot_green.png"))));
		builder.put(PastelSkullType.PARROT_RED, List.of(new Tuple<>(new ParrotHeadModel(modelLoader.bakeLayer(PastelModelLayers.PARROT_RED_HEAD)), ResourceLocation.parse("textures/entity/parrot/parrot_red_blue.png")))); // red vs. red_blue
		builder.put(PastelSkullType.PHANTOM, List.of(new Tuple<>(new PhantomHeadModel(modelLoader.bakeLayer(PastelModelLayers.PHANTOM_HEAD)), ResourceLocation.parse("textures/entity/phantom.png"))));
		builder.put(PastelSkullType.PIG, List.of(new Tuple<>(new PigHeadModel(modelLoader.bakeLayer(PastelModelLayers.PIG_HEAD)), ResourceLocation.parse("textures/entity/pig/pig.png"))));
		builder.put(PastelSkullType.PILLAGER, List.of(new Tuple<>(new IllagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.PILLAGER_HEAD)), ResourceLocation.parse("textures/entity/illager/pillager.png"))));
		builder.put(PastelSkullType.POLAR_BEAR, List.of(new Tuple<>(new BearHeadModel(modelLoader.bakeLayer(PastelModelLayers.POLAR_BEAR_HEAD)), ResourceLocation.parse("textures/entity/bear/polarbear.png"))));
		builder.put(PastelSkullType.PUFFERFISH, List.of(new Tuple<>(new PufferFishHeadModel(modelLoader.bakeLayer(PastelModelLayers.PUFFERFISH_HEAD)), ResourceLocation.parse("textures/entity/fish/pufferfish.png")))); // other fish?
		builder.put(PastelSkullType.RABBIT, List.of(new Tuple<>(new RabbitHeadModel(modelLoader.bakeLayer(PastelModelLayers.RABBIT_HEAD)), ResourceLocation.parse("textures/entity/rabbit/brown.png")))); // rabbits have variant
		builder.put(PastelSkullType.RAVAGER, List.of(new Tuple<>(new RavagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.RAVAGER_HEAD)), ResourceLocation.parse("textures/entity/illager/ravager.png"))));
		builder.put(PastelSkullType.SALMON, List.of(new Tuple<>(new SalmonHeadModel(modelLoader.bakeLayer(PastelModelLayers.SALMON_HEAD)), ResourceLocation.parse("textures/entity/fish/salmon.png")))); // other fish?
		builder.put(PastelSkullType.SHEEP, List.of(new Tuple<>(new SheepHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHEEP_HEAD)), ResourceLocation.parse("textures/entity/sheep/sheep.png"))));
		builder.put(PastelSkullType.SHULKER, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker.png"))));
		builder.put(PastelSkullType.SHULKER_BLACK, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_BLACK_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_black.png"))));
		builder.put(PastelSkullType.SHULKER_BLUE, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_BLUE_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_blue.png"))));
		builder.put(PastelSkullType.SHULKER_BROWN, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_BROWN_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_brown.png"))));
		builder.put(PastelSkullType.SHULKER_CYAN, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_CYAN_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_cyan.png"))));
		builder.put(PastelSkullType.SHULKER_GRAY, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_GRAY_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_gray.png"))));
		builder.put(PastelSkullType.SHULKER_GREEN, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_GREEN_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_green.png"))));
		builder.put(PastelSkullType.SHULKER_LIGHT_BLUE, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_LIGHT_BLUE_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_light_blue.png"))));
		builder.put(PastelSkullType.SHULKER_LIGHT_GRAY, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_LIGHT_GRAY_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_light_gray.png"))));
		builder.put(PastelSkullType.SHULKER_LIME, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_LIME_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_lime.png"))));
		builder.put(PastelSkullType.SHULKER_MAGENTA, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_MAGENTA_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_magenta.png"))));
		builder.put(PastelSkullType.SHULKER_ORANGE, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_ORANGE_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_orange.png"))));
		builder.put(PastelSkullType.SHULKER_PINK, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_PINK_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_pink.png"))));
		builder.put(PastelSkullType.SHULKER_PURPLE, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_PURPLE_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_purple.png"))));
		builder.put(PastelSkullType.SHULKER_RED, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_RED_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_red.png"))));
		builder.put(PastelSkullType.SHULKER_WHITE, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_WHITE_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_white.png"))));
		builder.put(PastelSkullType.SHULKER_YELLOW, List.of(new Tuple<>(new ShulkerHeadModel(modelLoader.bakeLayer(PastelModelLayers.SHULKER_YELLOW_HEAD)), ResourceLocation.parse("textures/entity/shulker/shulker_yellow.png"))));
		builder.put(PastelSkullType.SILVERFISH, List.of(new Tuple<>(new SilverfishHeadModel(modelLoader.bakeLayer(PastelModelLayers.SILVERFISH_HEAD)), ResourceLocation.parse("textures/entity/silverfish.png"))));
		builder.put(PastelSkullType.SKELETON_HORSE, List.of(new Tuple<>(new HorseHeadModel(modelLoader.bakeLayer(PastelModelLayers.SKELETON_HORSE_HEAD)), ResourceLocation.parse("textures/entity/horse/horse_skeleton.png"))));
		builder.put(PastelSkullType.SLIME, List.of(new Tuple<>(new SlimeHeadModel(modelLoader.bakeLayer(PastelModelLayers.SLIME_HEAD)), ResourceLocation.parse("textures/entity/slime/slime.png"))));
		builder.put(PastelSkullType.SNIFFER, List.of(new Tuple<>(new SnifferHeadModel(modelLoader.bakeLayer(PastelModelLayers.SNIFFER_HEAD)), ResourceLocation.parse("textures/entity/sniffer/sniffer.png"))));
		builder.put(PastelSkullType.SNOW_GOLEM, List.of(new Tuple<>(new ZombieHeadModel(modelLoader.bakeLayer(PastelModelLayers.SNOW_GOLEM_HEAD)), ResourceLocation.parse("textures/entity/snow_golem.png"))));
		builder.put(PastelSkullType.SPIDER, List.of(new Tuple<>(new SpiderHeadModel(modelLoader.bakeLayer(PastelModelLayers.SPIDER_HEAD)), ResourceLocation.parse("textures/entity/spider/spider.png"))));
		builder.put(PastelSkullType.SQUID, List.of(new Tuple<>(new SquidHeadModel(modelLoader.bakeLayer(PastelModelLayers.SQUID_HEAD)), ResourceLocation.parse("textures/entity/squid/squid.png"))));
		builder.put(PastelSkullType.STRAY, List.of(
				new Tuple<>(new StrayHeadModel(modelLoader.bakeLayer(PastelModelLayers.STRAY_HEAD)), ResourceLocation.parse("textures/entity/skeleton/stray.png")),
				new Tuple<>(new StrayHeadModel(modelLoader.bakeLayer(PastelModelLayers.STRAY_HEAD_OVERLAY)), ResourceLocation.parse("textures/entity/skeleton/stray_overlay.png"))
		));
		builder.put(PastelSkullType.STRIDER, List.of(new Tuple<>(new StriderHeadModel(modelLoader.bakeLayer(PastelModelLayers.STRIDER_HEAD)), ResourceLocation.parse("textures/entity/strider/strider.png"))));
		builder.put(PastelSkullType.TADPOLE, List.of(new Tuple<>(new TadpoleHeadModel(modelLoader.bakeLayer(PastelModelLayers.TADPOLE_HEAD)), ResourceLocation.parse("textures/entity/tadpole/tadpole.png"))));
		builder.put(PastelSkullType.TROPICAL_FISH, List.of(new Tuple<>(new TropicalFishHeadModel(modelLoader.bakeLayer(PastelModelLayers.TROPICAL_FISH_HEAD), modelLoader.bakeLayer(PastelModelLayers.TROPICAL_FISH_HEAD_PATTERN)), ResourceLocation.parse("textures/entity/fish/tropical_a.png")))); // oof
		builder.put(PastelSkullType.TURTLE, List.of(new Tuple<>(new TurtleHeadModel(modelLoader.bakeLayer(PastelModelLayers.TURTLE_HEAD)), ResourceLocation.parse("textures/entity/turtle/big_sea_turtle.png"))));
		builder.put(PastelSkullType.VEX, List.of(new Tuple<>(new VexHeadModel(modelLoader.bakeLayer(PastelModelLayers.VEX_HEAD)), ResourceLocation.parse("textures/entity/illager/vex.png"))));
		builder.put(PastelSkullType.VILLAGER, List.of(new Tuple<>(new VillagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.VILLAGER_HEAD)), ResourceLocation.parse("textures/entity/villager/villager.png"))));
		builder.put(PastelSkullType.VINDICATOR, List.of(new Tuple<>(new IllagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.VINDICATOR_HEAD)), ResourceLocation.parse("textures/entity/illager/vindicator.png"))));
		builder.put(PastelSkullType.WANDERING_TRADER, List.of(new Tuple<>(new VillagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.WANDERING_TRADER_HEAD)), ResourceLocation.parse("textures/entity/wandering_trader.png"))));
		builder.put(PastelSkullType.WARDEN, List.of(
				new Tuple<>(new WardenHeadModel(modelLoader.bakeLayer(PastelModelLayers.WARDEN_HEAD)), ResourceLocation.parse("textures/entity/warden/warden.png")),
				new Tuple<>(new WardenHeadModel(modelLoader.bakeLayer(PastelModelLayers.WARDEN_HEAD_BIOLUMINESCENT)), ResourceLocation.parse("textures/entity/warden/warden_bioluminescent_layer.png")),
				new Tuple<>(new WardenHeadModel(modelLoader.bakeLayer(PastelModelLayers.WARDEN_HEAD_PULSATING_SPOTS_1)), ResourceLocation.parse("textures/entity/warden/warden_pulsating_spots_1.png")),
				new Tuple<>(new WardenHeadModel(modelLoader.bakeLayer(PastelModelLayers.WARDEN_HEAD_PULSATING_SPOTS_2)), ResourceLocation.parse("textures/entity/warden/warden_pulsating_spots_2.png"))
		));
		builder.put(PastelSkullType.WITCH, List.of(new Tuple<>(new WitchHeadModel(modelLoader.bakeLayer(PastelModelLayers.WITCH_HEAD)), ResourceLocation.parse("textures/entity/witch.png"))));
		builder.put(PastelSkullType.WITHER, List.of(new Tuple<>(new ZombieHeadModel(modelLoader.bakeLayer(PastelModelLayers.WITHER_HEAD)), ResourceLocation.parse("textures/entity/wither/wither.png"))));
		builder.put(PastelSkullType.WOLF, List.of(new Tuple<>(new WolfHeadModel(modelLoader.bakeLayer(PastelModelLayers.WOLF_HEAD)), ResourceLocation.parse("textures/entity/wolf/wolf.png"))));
		builder.put(PastelSkullType.ZOGLIN, List.of(new Tuple<>(new HoglinHeadModel(modelLoader.bakeLayer(PastelModelLayers.ZOGLIN_HEAD)), ResourceLocation.parse("textures/entity/hoglin/zoglin.png"))));
		builder.put(PastelSkullType.ZOMBIE_HORSE, List.of(new Tuple<>(new HorseHeadModel(modelLoader.bakeLayer(PastelModelLayers.ZOMBIE_HORSE_HEAD)), ResourceLocation.parse("textures/entity/horse/horse_zombie.png"))));
		builder.put(PastelSkullType.ZOMBIE_VILLAGER, List.of(new Tuple<>(new VillagerHeadModel(modelLoader.bakeLayer(PastelModelLayers.ZOMBIE_VILLAGER_HEAD)), ResourceLocation.parse("textures/entity/zombie_villager/zombie_villager.png"))));
		builder.put(PastelSkullType.ZOMBIFIED_PIGLIN, List.of(new Tuple<>(new PiglinHeadModel(modelLoader.bakeLayer(PastelModelLayers.ZOMBIFIED_PIGLIN_HEAD)), ResourceLocation.parse("textures/entity/piglin/zombified_piglin.png"))));
		builder.put(PastelSkullType.PIGLIN_BRUTE, List.of(new Tuple<>(new PiglinHeadModel(modelLoader.bakeLayer(PastelModelLayers.PIGLIN_BRUTE_HEAD)), ResourceLocation.parse("textures/entity/piglin/piglin_brute.png"))));

		builder.put(PastelSkullType.ARMADILLO, List.of(new Tuple<>(new ArmadilloHeadModel(modelLoader.bakeLayer(PastelModelLayers.ARMADILLO_HEAD)), ResourceLocation.parse("textures/entity/armadillo.png"))));
		builder.put(PastelSkullType.BREEZE, List.of(new Tuple<>(new BreezeHeadModel(modelLoader.bakeLayer(PastelModelLayers.BREEZE_HEAD)), ResourceLocation.parse("textures/entity/breeze/breeze.png"))));
		builder.put(PastelSkullType.BOGGED, List.of(
				new Tuple<>(new BoggedHeadModel(modelLoader.bakeLayer(PastelModelLayers.BOGGED_HEAD)), ResourceLocation.parse("textures/entity/skeleton/bogged.png")),
				new Tuple<>(new BoggedHeadModel(modelLoader.bakeLayer(PastelModelLayers.BOGGED_HEAD_OVERLAY)), ResourceLocation.parse("textures/entity/skeleton/bogged_overlay.png")
				)));

        // Spectrum
		builder.put(PastelSkullType.EGG_LAYING_WOOLY_PIG, List.of(new Tuple<>(new EggLayingWoolyPigHeadModel(modelLoader.bakeLayer(PastelModelLayers.EGG_LAYING_WOOLY_PIG_HEAD)), EggLayingWoolyPigEntityRenderer.TEXTURE)));
		builder.put(PastelSkullType.ERASER, List.of(new Tuple<>(new SpiderHeadModel(modelLoader.bakeLayer(PastelModelLayers.ERASER_HEAD)), PastelCommon.locate("textures/entity/eraser/eraser_combined.png"))));
		builder.put(PastelSkullType.KINDLING, List.of(new Tuple<>(new KindlingHeadModel(modelLoader.bakeLayer(PastelModelLayers.KINDLING_HEAD)), KindlingVariant.DEFAULT.getDefaultTexture())));
		builder.put(PastelSkullType.LIZARD_BLACK, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.BLACK.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_BLUE, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.BLUE.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_BROWN, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.BROWN.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_CYAN, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.CYAN.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_GRAY, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.GRAY.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_GREEN, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.GREEN.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_LIGHT_BLUE, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.LIGHT_BLUE.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_LIGHT_GRAY, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.LIGHT_GRAY.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_LIME, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.LIME.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_MAGENTA, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.MAGENTA.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_ORANGE, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.ORANGE.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_PINK, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.PINK.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_PURPLE, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.PURPLE.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_RED, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.RED.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_WHITE, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.WHITE.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.LIZARD_YELLOW, List.of(new Tuple<>(new LizardHeadModel(modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD), modelLoader.bakeLayer(PastelModelLayers.LIZARD_HEAD_FRILLS), InkColors.YELLOW.getColorInt()), LizardHeadModel.HEAD_TEXTURE)));
		builder.put(PastelSkullType.PRESERVATION_TURRET, List.of(new Tuple<>(new PreservationTurretHeadModel(modelLoader.bakeLayer(PastelModelLayers.PRESERVATION_TURRET_HEAD)), PreservationTurretEntityRenderer.TEXTURE)));

        return builder.build();
    }

    @Override
    public void render(PastelSkullBlockEntity pastelSkullBlockEntity, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light, int j) {
        BlockState blockState = pastelSkullBlockEntity.getBlockState();
        Direction direction = null;
        float yaw = 22.5F;
        if (blockState.getBlock() instanceof WallSkullBlock) {
            direction = blockState.getValue(WallSkullBlock.FACING);
            yaw *= (2 + direction.get2DDataValue()) * 4;
        } else {
            yaw *= blockState.getValue(SkullBlock.ROTATION);
        }
        PastelSkullType skullType = PastelSkullBlock.getSkullType(pastelSkullBlockEntity.getBlockState().getBlock());

		renderModels(tickDelta, poseStack, vertexConsumerProvider, light, skullType, direction, yaw);

	}

	public static void renderModels(float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light, PastelSkullType skullType, Direction direction, float yaw) {
		List<Tuple<PastelSkullModel, ResourceLocation>> model = MODELS.get(skullType);
		for (Tuple<PastelSkullModel, ResourceLocation> entry : model) {
			RenderType renderLayer = RenderType.entityCutoutNoCullZOffset(entry.getB());
			renderSkull(direction, yaw, tickDelta, poseStack, vertexConsumerProvider, light, entry.getA(), renderLayer);
		}
	}

	private static void renderSkull(@Nullable Direction direction, float yaw, float animationProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, PastelSkullModel model, RenderType renderLayer) {
        matrices.pushPose();
        if (direction == null) {
            matrices.translate(0.5D, 0.0D, 0.5D);
        } else {
            matrices.translate((0.5F - (float) direction.getStepX() * 0.25F), 0.25D, (0.5F - (float) direction.getStepZ() * 0.25F));
        }

        matrices.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        model.setupAnim(animationProgress, yaw, 0.0F);
        model.render(matrices, vertexConsumer, vertexConsumers, light, OverlayTexture.NO_OVERLAY, -1);
        matrices.popPose();
    }

}
