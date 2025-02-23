package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.color.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.block_flooder.*;
import de.dafuqs.spectrum.blocks.boom.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.blocks.cinderhearth.*;
import de.dafuqs.spectrum.blocks.conditional.*;
import de.dafuqs.spectrum.blocks.conditional.amaranth.*;
import de.dafuqs.spectrum.blocks.conditional.blood_orchid.*;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.*;
import de.dafuqs.spectrum.blocks.conditional.resonant_lily.*;
import de.dafuqs.spectrum.blocks.crystallarieum.*;
import de.dafuqs.spectrum.blocks.decay.*;
import de.dafuqs.spectrum.blocks.decoration.*;
import de.dafuqs.spectrum.blocks.deeper_down.*;
import de.dafuqs.spectrum.blocks.deeper_down.flora.*;
import de.dafuqs.spectrum.blocks.deeper_down.groundcover.*;
import de.dafuqs.spectrum.blocks.enchanter.*;
import de.dafuqs.spectrum.blocks.ender.*;
import de.dafuqs.spectrum.blocks.energy.*;
import de.dafuqs.spectrum.blocks.farming.*;
import de.dafuqs.spectrum.blocks.fluid.*;
import de.dafuqs.spectrum.blocks.fusion_shrine.*;
import de.dafuqs.spectrum.blocks.gemstone.*;
import de.dafuqs.spectrum.blocks.geology.*;
import de.dafuqs.spectrum.blocks.gravity.*;
import de.dafuqs.spectrum.blocks.idols.*;
import de.dafuqs.spectrum.blocks.item_bowl.*;
import de.dafuqs.spectrum.blocks.item_roundel.*;
import de.dafuqs.spectrum.blocks.jade_vines.*;
import de.dafuqs.spectrum.blocks.lava_sponge.*;
import de.dafuqs.spectrum.blocks.memory.*;
import de.dafuqs.spectrum.blocks.mob_head.*;
import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.blocks.potion_workshop.*;
import de.dafuqs.spectrum.blocks.present.*;
import de.dafuqs.spectrum.blocks.redstone.*;
import de.dafuqs.spectrum.blocks.rock_candy.*;
import de.dafuqs.spectrum.blocks.shooting_star.*;
import de.dafuqs.spectrum.blocks.spirit_instiller.*;
import de.dafuqs.spectrum.blocks.spirit_sallow.*;
import de.dafuqs.spectrum.blocks.statues.*;
import de.dafuqs.spectrum.blocks.structure.*;
import de.dafuqs.spectrum.blocks.titration_barrel.*;
import de.dafuqs.spectrum.blocks.upgrade.*;
import de.dafuqs.spectrum.blocks.weathering.*;
import de.dafuqs.spectrum.data.*;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.items.conditional.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.fabricmc.fabric.api.object.builder.v1.block.type.*;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.*;
import net.minecraft.block.enums.*;
import net.minecraft.block.piston.*;
import net.minecraft.client.render.*;
import net.minecraft.data.client.*;
import net.minecraft.data.family.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.projectile.thrown.*;
import net.minecraft.item.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.property.Properties;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import net.minecraft.world.gen.feature.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

import static de.dafuqs.spectrum.SpectrumCommon.*;
import static de.dafuqs.spectrum.data.SpectrumModelProvider.*;
import static de.dafuqs.spectrum.registries.SpectrumItems.*;
import static net.minecraft.block.Blocks.*;

@SuppressWarnings({"unused"})
public class SpectrumBlocks {
	
	private static Settings settings(MapColor mapColor, BlockSoundGroup blockSoundGroup, float strength) {
		return AbstractBlock.Settings.create().mapColor(mapColor).sounds(blockSoundGroup).strength(strength);
	}
	
	private static Settings settings(MapColor mapColor, BlockSoundGroup blockSoundGroup, float strength, float resistance) {
		return settings(mapColor, blockSoundGroup, strength).resistance(resistance);
	}
	
	private static Settings craftingBlock(MapColor mapColor, BlockSoundGroup blockSoundGroup) {
		return settings(mapColor, blockSoundGroup, 5.0F, 8.0F).solidBlock(SpectrumBlocks::never).blockVision(SpectrumBlocks::never).nonOpaque().requiresTool();
	}
	
	public static final DeferredRegistrar COMMON_REGISTRAR = new DeferredRegistrar();
	public static final DeferredRegistrar CLIENT_REGISTRAR = new DeferredRegistrar();
	
	public static final Block PEDESTAL_BASIC_TOPAZ = new PedestalBlock(craftingBlock(MapColor.DIAMOND_BLUE, SpectrumBlockSoundGroups.TOPAZ_BLOCK), BuiltinPedestalVariant.BASIC_TOPAZ);
	public static final Block PEDESTAL_BASIC_AMETHYST = new PedestalBlock(craftingBlock(MapColor.PURPLE, BlockSoundGroup.AMETHYST_BLOCK), BuiltinPedestalVariant.BASIC_AMETHYST);
	public static final Block PEDESTAL_BASIC_CITRINE = new PedestalBlock(craftingBlock(MapColor.YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK), BuiltinPedestalVariant.BASIC_CITRINE);
	public static final Block PEDESTAL_ALL_BASIC = new PedestalBlock(craftingBlock(MapColor.PURPLE, BlockSoundGroup.AMETHYST_BLOCK), BuiltinPedestalVariant.CMY);
	public static final Block PEDESTAL_ONYX = new PedestalBlock(craftingBlock(MapColor.BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK), BuiltinPedestalVariant.ONYX);
	public static final Block PEDESTAL_MOONSTONE = new PedestalBlock(craftingBlock(MapColor.WHITE, SpectrumBlockSoundGroups.MOONSTONE_BLOCK), BuiltinPedestalVariant.MOONSTONE);
	
	public static final Block FUSION_SHRINE_BASALT = new FusionShrineBlock(craftingBlock(MapColor.BLACK, BlockSoundGroup.BASALT).luminance(value -> value.get(FusionShrineBlock.LIGHT_LEVEL)));
	public static final Block FUSION_SHRINE_CALCITE = new FusionShrineBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE).luminance(value -> value.get(FusionShrineBlock.LIGHT_LEVEL)));
	
	public static final Block ENCHANTER = new EnchanterBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	public static final Block ITEM_BOWL_BASALT = new ItemBowlBlock(craftingBlock(MapColor.BLACK, BlockSoundGroup.BASALT));
	public static final Block ITEM_BOWL_CALCITE = new ItemBowlBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	public static final Block ITEM_ROUNDEL = new ItemRoundelBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	public static final Block POTION_WORKSHOP = new PotionWorkshopBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	public static final Block SPIRIT_INSTILLER = new SpiritInstillerBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	public static final CrystallarieumBlock CRYSTALLARIEUM = new CrystallarieumBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	public static final Block CINDERHEARTH = new CinderhearthBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	
	public static final Block COLOR_PICKER = new ColorPickerBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	public static final Block CRYSTAL_APOTHECARY = new CrystalApothecaryBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE));
	
	private static Settings gemstone(MapColor mapColor, BlockSoundGroup blockSoundGroup, int luminance) {
		return settings(mapColor, blockSoundGroup, 1.5F).solid().nonOpaque().luminance((state) -> luminance).pistonBehavior(PistonBehavior.DESTROY);
	}
	
	private static Settings gemstoneBlock(MapColor mapColor, BlockSoundGroup blockSoundGroup) {
		return settings(mapColor, blockSoundGroup, 1.5F).requiresTool();
	}
	
	public static <T extends SpectrumClusterBlock> BlockRegistrar<T> cluster(BlockRegistrar<T> registrar, Model model) {
		return cutout(registrar).withBlockModel((ctx, block) -> {
			switch (block.getGrowthStage()) {
				case SpectrumClusterBlock.GrowthStage.SMALL -> SpectrumModels.SMALL_BUD_ITEM.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block), ctx.modelCollector);
				case SpectrumClusterBlock.GrowthStage.MEDIUM -> SpectrumModels.MEDIUM_BUD_ITEM.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block), ctx.modelCollector);
				case SpectrumClusterBlock.GrowthStage.LARGE -> SpectrumModels.LARGE_BUD_ITEM.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block), ctx.modelCollector);
				case SpectrumClusterBlock.GrowthStage.CLUSTER -> SpectrumModels.CLUSTER_ITEM.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block), ctx.modelCollector);
			}
			return VariantsBlockStateSupplier.create(block, createModelVariant(TexturedModel.makeFactory(TextureMap::cross, model).upload(block, ctx.modelCollector))).coordinate(createUpDefaultFacingVariantMap());
		});
	}
	
	public static final SpectrumClusterBlock TOPAZ_CLUSTER = register(cluster(blockWithItem("topaz_cluster", new SpectrumClusterBlock(gemstone(MapColor.CYAN, SpectrumBlockSoundGroups.TOPAZ_CLUSTER, 8), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.CYAN), Models.CROSS));
	public static final SpectrumClusterBlock LARGE_TOPAZ_BUD = register(cluster(blockWithItem("large_topaz_bud", new SpectrumClusterBlock(gemstone(MapColor.CYAN, SpectrumBlockSoundGroups.LARGE_TOPAZ_BUD, 6), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.CYAN), Models.CROSS));
	public static final SpectrumClusterBlock MEDIUM_TOPAZ_BUD = register(cluster(blockWithItem("medium_topaz_bud", new SpectrumClusterBlock(gemstone(MapColor.CYAN, SpectrumBlockSoundGroups.MEDIUM_TOPAZ_BUD, 4), SpectrumClusterBlock.GrowthStage.MEDIUM), DyeColor.CYAN), Models.CROSS));
	public static final SpectrumClusterBlock SMALL_TOPAZ_BUD = register(cluster(blockWithItem("small_topaz_bud", new SpectrumClusterBlock(gemstone(MapColor.CYAN, SpectrumBlockSoundGroups.SMALL_TOPAZ_BUD, 2), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.CYAN), Models.CROSS));
	public static final SpectrumBuddingBlock BUDDING_TOPAZ = register(simple(blockWithItem("budding_topaz", new SpectrumBuddingBlock(gemstoneBlock(MapColor.CYAN, SpectrumBlockSoundGroups.TOPAZ_BLOCK).pistonBehavior(PistonBehavior.DESTROY).ticksRandomly(), SMALL_TOPAZ_BUD, MEDIUM_TOPAZ_BUD, LARGE_TOPAZ_BUD, TOPAZ_CLUSTER, SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_HIT, SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_CHIME), DyeColor.CYAN)));
	public static final SpectrumGemstoneBlock TOPAZ_BLOCK = register(blockWithItem("topaz_block", new SpectrumGemstoneBlock(gemstoneBlock(MapColor.CYAN, SpectrumBlockSoundGroups.TOPAZ_BLOCK), SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_HIT, SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_CHIME), DyeColor.CYAN)
			.withBlockModel(SpectrumModelProvider::simpleMirroredBlockModel));
	
	public static final SpectrumClusterBlock CITRINE_CLUSTER = register(cluster(blockWithItem("citrine_cluster", new SpectrumClusterBlock(gemstone(MapColor.YELLOW, SpectrumBlockSoundGroups.CITRINE_CLUSTER, 9), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.YELLOW), Models.CROSS));
	public static final SpectrumClusterBlock LARGE_CITRINE_BUD = register(cluster(blockWithItem("large_citrine_bud", new SpectrumClusterBlock(gemstone(MapColor.YELLOW, SpectrumBlockSoundGroups.LARGE_CITRINE_BUD, 7), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.YELLOW), Models.CROSS));
	public static final SpectrumClusterBlock MEDIUM_CITRINE_BUD = register(cluster(blockWithItem("medium_citrine_bud", new SpectrumClusterBlock(gemstone(MapColor.YELLOW, SpectrumBlockSoundGroups.MEDIUM_CITRINE_BUD, 5), SpectrumClusterBlock.GrowthStage.MEDIUM), DyeColor.YELLOW), Models.CROSS));
	public static final SpectrumClusterBlock SMALL_CITRINE_BUD = register(cluster(blockWithItem("small_citrine_bud", new SpectrumClusterBlock(gemstone(MapColor.YELLOW, SpectrumBlockSoundGroups.SMALL_CITRINE_BUD, 3), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.YELLOW), Models.CROSS));
	public static final SpectrumBuddingBlock BUDDING_CITRINE = register(simple(blockWithItem("budding_citrine", new SpectrumBuddingBlock(gemstoneBlock(MapColor.YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK).pistonBehavior(PistonBehavior.DESTROY).ticksRandomly(), SMALL_CITRINE_BUD, MEDIUM_CITRINE_BUD, LARGE_CITRINE_BUD, CITRINE_CLUSTER, SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_CHIME), DyeColor.YELLOW)));
	public static final SpectrumGemstoneBlock CITRINE_BLOCK = register(blockWithItem("citrine_block", new SpectrumGemstoneBlock(gemstoneBlock(MapColor.YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK), SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_CHIME), DyeColor.YELLOW)
			.withBlockModel(SpectrumModelProvider::simpleMirroredBlockModel));
	
	public static final SpectrumClusterBlock ONYX_CLUSTER = register(cluster(blockWithItem("onyx_cluster", new SpectrumClusterBlock(gemstone(MapColor.BLACK, SpectrumBlockSoundGroups.ONYX_CLUSTER, 6), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.BLACK), Models.CROSS));
	public static final SpectrumClusterBlock LARGE_ONYX_BUD = register(cluster(blockWithItem("large_onyx_bud", new SpectrumClusterBlock(gemstone(MapColor.BLACK, SpectrumBlockSoundGroups.LARGE_ONYX_BUD, 5), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.BLACK), Models.CROSS));
	public static final SpectrumClusterBlock MEDIUM_ONYX_BUD = register(cluster(blockWithItem("medium_onyx_bud", new SpectrumClusterBlock(gemstone(MapColor.BLACK, SpectrumBlockSoundGroups.MEDIUM_ONYX_BUD, 3), SpectrumClusterBlock.GrowthStage.MEDIUM), DyeColor.BLACK), Models.CROSS));
	public static final SpectrumClusterBlock SMALL_ONYX_BUD = register(cluster(blockWithItem("small_onyx_bud", new SpectrumClusterBlock(gemstone(MapColor.BLACK, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 1), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.BLACK), Models.CROSS));
	public static final SpectrumBuddingBlock BUDDING_ONYX = register(simple(blockWithItem("budding_onyx", new SpectrumBuddingBlock(gemstoneBlock(MapColor.BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK).pistonBehavior(PistonBehavior.DESTROY).ticksRandomly(), SMALL_ONYX_BUD, MEDIUM_ONYX_BUD, LARGE_ONYX_BUD, ONYX_CLUSTER, SpectrumSoundEvents.BLOCK_ONYX_BLOCK_HIT, SpectrumSoundEvents.BLOCK_ONYX_BLOCK_CHIME), DyeColor.BLACK)));
	public static final SpectrumGemstoneBlock ONYX_BLOCK = register(blockWithItem("onyx_block", new SpectrumGemstoneBlock(gemstoneBlock(MapColor.BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK), SpectrumSoundEvents.BLOCK_ONYX_BLOCK_HIT, SpectrumSoundEvents.BLOCK_ONYX_BLOCK_CHIME), DyeColor.BLACK)
			.withBlockModel(SpectrumModelProvider::simpleMirroredBlockModel));
	
	public static final SpectrumClusterBlock MOONSTONE_CLUSTER = register(cluster(blockWithItem("moonstone_cluster", new SpectrumClusterBlock(gemstone(MapColor.WHITE, SpectrumBlockSoundGroups.MOONSTONE_CLUSTER, 15), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.WHITE), Models.CROSS));
	public static final SpectrumClusterBlock LARGE_MOONSTONE_BUD = register(cluster(blockWithItem("large_moonstone_bud", new SpectrumClusterBlock(gemstone(MapColor.WHITE, SpectrumBlockSoundGroups.LARGE_MOONSTONE_BUD, 12), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.WHITE), Models.CROSS));
	public static final SpectrumClusterBlock MEDIUM_MOONSTONE_BUD = register(cluster(blockWithItem("medium_moonstone_bud", new SpectrumClusterBlock(gemstone(MapColor.WHITE, SpectrumBlockSoundGroups.MEDIUM_MOONSTONE_BUD, 9), SpectrumClusterBlock.GrowthStage.MEDIUM), DyeColor.WHITE), Models.CROSS));
	public static final SpectrumClusterBlock SMALL_MOONSTONE_BUD = register(cluster(blockWithItem("small_moonstone_bud", new SpectrumClusterBlock(gemstone(MapColor.WHITE, SpectrumBlockSoundGroups.SMALL_MOONSTONE_BUD, 6), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.WHITE), Models.CROSS));
	public static final SpectrumBuddingBlock BUDDING_MOONSTONE = register(simple(blockWithItem("budding_moonstone", new SpectrumBuddingBlock(gemstoneBlock(MapColor.WHITE, SpectrumBlockSoundGroups.MOONSTONE_BLOCK).pistonBehavior(PistonBehavior.DESTROY).ticksRandomly(), SMALL_MOONSTONE_BUD, MEDIUM_MOONSTONE_BUD, LARGE_MOONSTONE_BUD, MOONSTONE_CLUSTER, SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_CHIME), DyeColor.WHITE)));
	public static final SpectrumGemstoneBlock MOONSTONE_BLOCK = register(blockWithItem("moonstone_block", new SpectrumGemstoneBlock(gemstoneBlock(MapColor.WHITE, SpectrumBlockSoundGroups.MOONSTONE_BLOCK), SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_CHIME), DyeColor.WHITE)
			.withBlockModel(SpectrumModelProvider::simpleMirroredBlockModel));
	
	public static final ColoredFallingBlock TOPAZ_POWDER_BLOCK = register(simple(blockWithItem("topaz_powder_block", new ColoredFallingBlock(new ColorCode(DyeColor.CYAN.getFireworkColor()), AbstractBlock.Settings.copy(Blocks.SAND).mapColor(MapColor.CYAN)), DyeColor.CYAN)));
	public static final ColoredFallingBlock AMETHYST_POWDER_BLOCK = register(simple(blockWithItem("amethyst_powder_block", new ColoredFallingBlock(new ColorCode(DyeColor.MAGENTA.getFireworkColor()), AbstractBlock.Settings.copy(Blocks.SAND).mapColor(MapColor.MAGENTA)), DyeColor.MAGENTA)));
	public static final ColoredFallingBlock CITRINE_POWDER_BLOCK = register(simple(blockWithItem("citrine_powder_block", new ColoredFallingBlock(new ColorCode(DyeColor.YELLOW.getFireworkColor()), AbstractBlock.Settings.copy(Blocks.SAND).mapColor(MapColor.YELLOW)), DyeColor.YELLOW)));
	public static final ColoredFallingBlock ONYX_POWDER_BLOCK = register(simple(blockWithItem("onyx_powder_block", new ColoredFallingBlock(new ColorCode(DyeColor.BLACK.getFireworkColor()), AbstractBlock.Settings.copy(Blocks.SAND).mapColor(MapColor.BLACK)), DyeColor.BLACK)));
	public static final ColoredFallingBlock MOONSTONE_POWDER_BLOCK = register(simple(blockWithItem("moonstone_powder_block", new ColoredFallingBlock(new ColorCode(DyeColor.WHITE.getFireworkColor()), AbstractBlock.Settings.copy(Blocks.SAND).mapColor(MapColor.WHITE)), DyeColor.WHITE)));
	
	public static final Block VEGETAL_BLOCK = register(singleton(blockWithItem("vegetal_block", new Block(settings(MapColor.PALE_GREEN, BlockSoundGroup.FUNGUS, 2.0F).nonOpaque()), DyeColor.GREEN), TexturedModel.makeFactory(TextureMap::texture, SpectrumModels.TRANSLUCENT_OUTER1)));
	public static final Block NEOLITH_BLOCK = register(blockWithItem("neolith_block", new SpectrumFacingBlock(settings(MapColor.PURPLE, BlockSoundGroup.COPPER, 6.0F).requiresTool().instrument(NoteBlockInstrument.BASEDRUM).luminance(state -> 13).postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always)), DyeColor.PINK)
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block, createModelVariant(TexturedModel.CUBE_BOTTOM_TOP.upload(block, ctx.modelCollector))).coordinate(createUpDefaultFacingVariantMap())));
	public static final Block BEDROCK_STORAGE_BLOCK = register(simple(blockWithItem("bedrock_storage_block", new BlockWithTooltip(settings(MapColor.STONE_GRAY, BlockSoundGroup.STONE, 100.0F, 3600.0F).pistonBehavior(PistonBehavior.BLOCK).requiresTool().instrument(NoteBlockInstrument.BASEDRUM), Text.translatable("spectrum.tooltip.dragon_and_wither_immune")), IS.of(Rarity.UNCOMMON), DyeColor.BLACK)));
	
	public static final SpectrumClusterBlock BISMUTH_CLUSTER = register(cluster(blockWithItem("bismuth_cluster", new SpectrumClusterBlock(gemstone(MapColor.DARK_AQUA, BlockSoundGroup.CHAIN, 8), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final SpectrumClusterBlock LARGE_BISMUTH_BUD = register(cluster(blockWithItem("large_bismuth_bud", new BismuthBudBlock(gemstone(MapColor.DARK_AQUA, BlockSoundGroup.CHAIN, 6).ticksRandomly(), SpectrumClusterBlock.GrowthStage.LARGE, BISMUTH_CLUSTER), IS.of(Rarity.UNCOMMON), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final SpectrumClusterBlock SMALL_BISMUTH_BUD = register(cluster(blockWithItem("small_bismuth_bud", new BismuthBudBlock(gemstone(MapColor.DARK_AQUA, BlockSoundGroup.CHAIN, 4).ticksRandomly(), SpectrumClusterBlock.GrowthStage.SMALL, LARGE_BISMUTH_BUD), IS.of(Rarity.UNCOMMON), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block BISMUTH_BLOCK = register(simple(blockWithItem("bismuth_block", new Block(gemstoneBlock(MapColor.DARK_AQUA, BlockSoundGroup.CHAIN)), DyeColor.CYAN)));
	
	// DD BLOCKS
	private static final float BLACKSLAG_HARDNESS = 5.0F;
	private static final float BLACKSLAG_RESISTANCE = 7.0F;
	
	private static Settings blackslag(BlockSoundGroup blockSoundGroup) {
		return settings(MapColor.GRAY, blockSoundGroup, BLACKSLAG_HARDNESS, BLACKSLAG_RESISTANCE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool();
	}
	
	public static final Block BLACKSLAG = register(blockWithItem("blackslag", new BlackslagBlock(blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK)
			.withBlockModel((ctx, block) -> createMirroredVariantsSupplier(block, TexturedModel.END_FOR_TOP_CUBE_COLUMN, SpectrumTexturedModels.CUBE_COLUMN_MIRRORED, ctx.modelCollector).coordinate(createAxisRotatedVariantMap())));
	public static final Block BLACKSLAG_STAIRS = register(blockWithItem("blackslag_stairs", new StairsBlock(BLACKSLAG.getDefaultState(), blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK));
	public static final Block BLACKSLAG_SLAB = register(blockWithItem("blackslag_slab", new SlabBlock(blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK));
	public static final Block BLACKSLAG_WALL = register(blockWithItem("blackslag_wall", new WallBlock(blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK));
	public static final BlockFamily BLACKSLAG_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(BLACKSLAG).stairs(BLACKSLAG_STAIRS).slab(BLACKSLAG_SLAB).wall(BLACKSLAG_WALL).build(), SpectrumTexturedModels.cubeBottomTopWall(b -> b, "", b -> b, "_top", b -> b, "_top", b -> b, ""));
	
	public static final Block INFESTED_BLACKSLAG = register(parented(blockWithItem("infested_blackslag", new InfestedBlock(BLACKSLAG, blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK), BLACKSLAG));
	
	public static final Block COBBLED_BLACKSLAG = register(blockWithItem("cobbled_blackslag", new Block(blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK));
	public static final Block COBBLED_BLACKSLAG_STAIRS = register(blockWithItem("cobbled_blackslag_stairs", new StairsBlock(COBBLED_BLACKSLAG.getDefaultState(), blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK));
	public static final Block COBBLED_BLACKSLAG_SLAB = register(blockWithItem("cobbled_blackslag_slab", new SlabBlock(blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK));
	public static final Block COBBLED_BLACKSLAG_WALL = register(blockWithItem("cobbled_blackslag_wall", new WallBlock(blackslag(BlockSoundGroup.DEEPSLATE)), DyeColor.BLACK));
	public static final BlockFamily COBBLED_BLACKSLAG_FAMILY = registerBlockFamily(new BlockFamily.Builder(COBBLED_BLACKSLAG).stairs(COBBLED_BLACKSLAG_STAIRS).slab(COBBLED_BLACKSLAG_SLAB).wall(COBBLED_BLACKSLAG_WALL).build());
	
	public static final Block BLACKSLAG_TILES = register(blockWithItem("blackslag_tiles", new Block(blackslag(BlockSoundGroup.DEEPSLATE_TILES)), DyeColor.BLACK));
	public static final Block BLACKSLAG_TILE_STAIRS = register(blockWithItem("blackslag_tile_stairs", new StairsBlock(BLACKSLAG_TILES.getDefaultState(), Settings.copy(BLACKSLAG_TILES)), DyeColor.BLACK));
	public static final Block BLACKSLAG_TILE_SLAB = register(blockWithItem("blackslag_tile_slab", new SlabBlock(Settings.copy(BLACKSLAG_TILES)), DyeColor.BLACK));
	public static final Block BLACKSLAG_TILE_WALL = register(blockWithItem("blackslag_tile_wall", new WallBlock(Settings.copy(BLACKSLAG_TILES)), DyeColor.BLACK));
	public static final Block CRACKED_BLACKSLAG_TILES = register(blockWithItem("cracked_blackslag_tiles", new Block(Settings.copy(BLACKSLAG_TILES)), DyeColor.BLACK));
	public static final BlockFamily BLACKSLAG_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(BLACKSLAG_TILES).stairs(BLACKSLAG_TILE_STAIRS).slab(BLACKSLAG_TILE_SLAB).wall(BLACKSLAG_TILE_WALL).cracked(CRACKED_BLACKSLAG_TILES).build());
	
	public static final Block BLACKSLAG_BRICKS = register(blockWithItem("blackslag_bricks", new Block(blackslag(BlockSoundGroup.DEEPSLATE_BRICKS)), DyeColor.BLACK));
	public static final Block BLACKSLAG_BRICK_STAIRS = register(blockWithItem("blackslag_brick_stairs", new StairsBlock(BLACKSLAG_BRICKS.getDefaultState(), Settings.copy(BLACKSLAG_BRICKS)), DyeColor.BLACK));
	public static final Block BLACKSLAG_BRICK_SLAB = register(blockWithItem("blackslag_brick_slab", new SlabBlock(Settings.copy(BLACKSLAG_BRICKS)), DyeColor.BLACK));
	public static final Block BLACKSLAG_BRICK_WALL = register(blockWithItem("blackslag_brick_wall", new WallBlock(Settings.copy(BLACKSLAG_BRICKS)), DyeColor.BLACK));
	public static final Block CRACKED_BLACKSLAG_BRICKS = register(blockWithItem("cracked_blackslag_bricks", new Block(Settings.copy(BLACKSLAG_BRICKS)), DyeColor.BLACK));
	public static final BlockFamily BLACKSLAG_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(BLACKSLAG_BRICKS).stairs(BLACKSLAG_BRICK_STAIRS).slab(BLACKSLAG_BRICK_SLAB).wall(BLACKSLAG_BRICK_WALL).cracked(CRACKED_BLACKSLAG_BRICKS).build());
	
	public static final Block POLISHED_BLACKSLAG = register(blockWithItem("polished_blackslag", new Block(blackslag(BlockSoundGroup.POLISHED_DEEPSLATE)), DyeColor.BLACK));
	public static final Block POLISHED_BLACKSLAG_STAIRS = register(blockWithItem("polished_blackslag_stairs", new StairsBlock(POLISHED_BLACKSLAG.getDefaultState(), Settings.copy(POLISHED_BLACKSLAG)), DyeColor.BLACK));
	public static final Block POLISHED_BLACKSLAG_SLAB = register(blockWithItem("polished_blackslag_slab", new SlabBlock(Settings.copy(POLISHED_BLACKSLAG)), DyeColor.BLACK));
	public static final Block POLISHED_BLACKSLAG_WALL = register(blockWithItem("polished_blackslag_wall", new WallBlock(Settings.copy(POLISHED_BLACKSLAG)), DyeColor.BLACK));
	public static final Block POLISHED_BLACKSLAG_BUTTON = register(blockWithItem("polished_blackslag_button", new ButtonBlock(SpectrumBlockSetTypes.POLISHED_BLACKSLAG, 5, Settings.create().noCollision().strength(0.5F)), DyeColor.BLACK));
	public static final Block POLISHED_BLACKSLAG_PRESSURE_PLATE = register(blockWithItem("polished_blackslag_pressure_plate", new PressurePlateBlock(SpectrumBlockSetTypes.POLISHED_BLACKSLAG, Settings.create().mapColor(MapColor.BLACK).requiresTool().noCollision().strength(0.5F)), DyeColor.BLACK));
	public static final Block CHISELED_POLISHED_BLACKSLAG = register(blockWithItem("chiseled_polished_blackslag", new Block(blackslag(BlockSoundGroup.DEEPSLATE_BRICKS)), DyeColor.BLACK));
	public static final BlockFamily POLISHED_BLACKSLAG_FAMILY = registerBlockFamily(new BlockFamily.Builder(POLISHED_BLACKSLAG).stairs(POLISHED_BLACKSLAG_STAIRS).slab(POLISHED_BLACKSLAG_SLAB).wall(POLISHED_BLACKSLAG_WALL).button(POLISHED_BLACKSLAG_BUTTON).pressurePlate(POLISHED_BLACKSLAG_PRESSURE_PLATE).chiseled(CHISELED_POLISHED_BLACKSLAG).build());
	
	public static final Block POLISHED_BLACKSLAG_PILLAR = register(axisRotated(blockWithItem("polished_blackslag_pillar", new PillarBlock(Settings.copy(BLACKSLAG_BRICKS)), DyeColor.BLACK), TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideEnd(b, "", CHISELED_POLISHED_BLACKSLAG, ""), Models.CUBE_COLUMN)));
	public static final Block ANCIENT_CHISELED_POLISHED_BLACKSLAG = register(simple(blockWithItem("ancient_chiseled_polished_blackslag", new Block(blackslag(BlockSoundGroup.DEEPSLATE_BRICKS)), DyeColor.BLACK)));
	
	public static final Block SHALE_CLAY = register(singleton(blockWithItem("shale_clay", new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, blackslag(BlockSoundGroup.MUD_BRICKS)), DyeColor.BROWN), TexturedModel.CUBE_COLUMN));
	public static final Block TILLED_SHALE_CLAY = register(singleton(blockWithItem("tilled_shale_clay", new TilledShaleClayBlock(Settings.copy(SHALE_CLAY), SHALE_CLAY.getDefaultState()), DyeColor.BROWN), SpectrumTexturedModels.farmland(b -> SHALE_CLAY, "_side", b -> b, "")));
	
	public static final Block POLISHED_SHALE_CLAY = register(blockWithItem("polished_shale_clay", new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem("polished_shale_clay_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.UNAFFECTED, POLISHED_SHALE_CLAY.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block POLISHED_SHALE_CLAY_SLAB = register(blockWithItem("polished_shale_clay_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.UNAFFECTED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily POLISHED_SHALE_CLAY_FAMILY = registerBlockFamily(new BlockFamily.Builder(POLISHED_SHALE_CLAY).stairs(POLISHED_SHALE_CLAY_STAIRS).slab(POLISHED_SHALE_CLAY_SLAB).build());
	
	public static final Block EXPOSED_POLISHED_SHALE_CLAY = register(blockWithItem("exposed_polished_shale_clay", new WeatheringBlock(Weathering.WeatheringLevel.EXPOSED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block EXPOSED_POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem("exposed_polished_shale_clay_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.EXPOSED, EXPOSED_POLISHED_SHALE_CLAY.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block EXPOSED_POLISHED_SHALE_CLAY_SLAB = register(blockWithItem("exposed_polished_shale_clay_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.EXPOSED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily EXPOSED_POLISHED_SHALE_CLAY_FAMILY = registerBlockFamily(new BlockFamily.Builder(EXPOSED_POLISHED_SHALE_CLAY).stairs(EXPOSED_POLISHED_SHALE_CLAY_STAIRS).slab(EXPOSED_POLISHED_SHALE_CLAY_SLAB).build());
	
	public static final Block WEATHERED_POLISHED_SHALE_CLAY = register(blockWithItem("weathered_polished_shale_clay", new WeatheringBlock(Weathering.WeatheringLevel.WEATHERED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block WEATHERED_POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem("weathered_polished_shale_clay_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.WEATHERED, WEATHERED_POLISHED_SHALE_CLAY.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block WEATHERED_POLISHED_SHALE_CLAY_SLAB = register(blockWithItem("weathered_polished_shale_clay_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.WEATHERED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily WEATHERED_POLISHED_SHALE_CLAY_FAMILY = registerBlockFamily(new BlockFamily.Builder(WEATHERED_POLISHED_SHALE_CLAY).stairs(WEATHERED_POLISHED_SHALE_CLAY_STAIRS).slab(WEATHERED_POLISHED_SHALE_CLAY_SLAB).build());
	
	public static final Block SHALE_CLAY_BRICKS = register(blockWithItem("shale_clay_bricks", new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block SHALE_CLAY_BRICK_STAIRS = register(blockWithItem("shale_clay_brick_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.UNAFFECTED, SHALE_CLAY_BRICKS.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block SHALE_CLAY_BRICK_SLAB = register(blockWithItem("shale_clay_brick_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.UNAFFECTED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily SHALE_CLAY_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(SHALE_CLAY_BRICKS).stairs(SHALE_CLAY_BRICK_STAIRS).slab(SHALE_CLAY_BRICK_SLAB).build());
	
	public static final Block EXPOSED_SHALE_CLAY_BRICKS = register(blockWithItem("exposed_shale_clay_bricks", new WeatheringBlock(Weathering.WeatheringLevel.EXPOSED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block EXPOSED_SHALE_CLAY_BRICK_STAIRS = register(blockWithItem("exposed_shale_clay_brick_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.EXPOSED, EXPOSED_SHALE_CLAY_BRICKS.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block EXPOSED_SHALE_CLAY_BRICK_SLAB = register(blockWithItem("exposed_shale_clay_brick_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.EXPOSED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily EXPOSED_SHALE_CLAY_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(EXPOSED_SHALE_CLAY_BRICKS).stairs(EXPOSED_SHALE_CLAY_BRICK_STAIRS).slab(EXPOSED_SHALE_CLAY_BRICK_SLAB).build());
	
	public static final Block WEATHERED_SHALE_CLAY_BRICKS = register(blockWithItem("weathered_shale_clay_bricks", new WeatheringBlock(Weathering.WeatheringLevel.WEATHERED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block WEATHERED_SHALE_CLAY_BRICK_STAIRS = register(blockWithItem("weathered_shale_clay_brick_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.WEATHERED, WEATHERED_SHALE_CLAY_BRICKS.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block WEATHERED_SHALE_CLAY_BRICK_SLAB = register(blockWithItem("weathered_shale_clay_brick_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.WEATHERED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily WEATHERED_SHALE_CLAY_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(WEATHERED_SHALE_CLAY_BRICKS).stairs(WEATHERED_SHALE_CLAY_BRICK_STAIRS).slab(WEATHERED_SHALE_CLAY_BRICK_SLAB).build());
	
	public static final Block SHALE_CLAY_TILES = register(blockWithItem("shale_clay_tiles", new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block SHALE_CLAY_TILE_STAIRS = register(blockWithItem("shale_clay_tile_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.UNAFFECTED, SHALE_CLAY_TILES.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block SHALE_CLAY_TILE_SLAB = register(blockWithItem("shale_clay_tile_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.UNAFFECTED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily SHALE_CLAY_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(SHALE_CLAY_TILES).stairs(SHALE_CLAY_TILE_STAIRS).slab(SHALE_CLAY_TILE_SLAB).build());
	
	public static final Block EXPOSED_SHALE_CLAY_TILES = register(blockWithItem("exposed_shale_clay_tiles", new WeatheringBlock(Weathering.WeatheringLevel.EXPOSED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block EXPOSED_SHALE_CLAY_TILE_STAIRS = register(blockWithItem("exposed_shale_clay_tile_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.EXPOSED, EXPOSED_SHALE_CLAY_TILES.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block EXPOSED_SHALE_CLAY_TILE_SLAB = register(blockWithItem("exposed_shale_clay_tile_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.EXPOSED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily EXPOSED_SHALE_CLAY_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(EXPOSED_SHALE_CLAY_TILES).stairs(EXPOSED_SHALE_CLAY_TILE_STAIRS).slab(EXPOSED_SHALE_CLAY_TILE_SLAB).build());
	
	public static final Block WEATHERED_SHALE_CLAY_TILES = register(blockWithItem("weathered_shale_clay_tiles", new WeatheringBlock(Weathering.WeatheringLevel.WEATHERED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block WEATHERED_SHALE_CLAY_TILE_STAIRS = register(blockWithItem("weathered_shale_clay_tile_stairs", new WeatheringStairsBlock(Weathering.WeatheringLevel.WEATHERED, WEATHERED_SHALE_CLAY_TILES.getDefaultState(), Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final Block WEATHERED_SHALE_CLAY_TILE_SLAB = register(blockWithItem("weathered_shale_clay_tile_slab", new WeatheringSlabBlock(Weathering.WeatheringLevel.WEATHERED, Settings.copy(SHALE_CLAY)), DyeColor.BROWN));
	public static final BlockFamily WEATHERED_SHALE_CLAY_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(WEATHERED_SHALE_CLAY_TILES).stairs(WEATHERED_SHALE_CLAY_TILE_STAIRS).slab(WEATHERED_SHALE_CLAY_TILE_SLAB).build());
	
	public static final Block ROCK_CRYSTAL = register(simple(blockWithItem("rock_crystal", new Block(settings(MapColor.OFF_WHITE, BlockSoundGroup.NETHER_BRICKS, 200F).requiresTool()), DyeColor.BROWN)));
	
	public static final Block PYRITE = register(axisRotated(blockWithItem("pyrite", new PillarBlock(settings(MapColor.TERRACOTTA_YELLOW, BlockSoundGroup.CHAIN, 50.0F).requiresTool()), DyeColor.BROWN), TexturedModel.CUBE_COLUMN));
	public static final Block PYRITE_SLAB = register(blockWithItem("pyrite_slab", new SlabBlock(Settings.copy(PYRITE)), DyeColor.BROWN));
	public static final Block PYRITE_STAIRS = register(blockWithItem("pyrite_stairs", new StairsBlock(PYRITE.getDefaultState(), Settings.copy(PYRITE)), DyeColor.BROWN));
	public static final Block PYRITE_WALL = register(blockWithItem("pyrite_wall", new WallBlock(Settings.copy(PYRITE)), DyeColor.BROWN));
	public static final BlockFamily PYRITE_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(PYRITE).stairs(PYRITE_STAIRS).slab(PYRITE_SLAB).wall(PYRITE_WALL).build(), TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottomWall(b, "_side", b, "_top", b, "_top", b, "_side"), Models.CUBE_ALL));
	
	public static final Block PYRITE_PILE = new PillarBlock(Settings.copy(PYRITE));
	public static final Block PYRITE_PLATING = new Block(Settings.copy(PYRITE));
	public static final Block PYRITE_TUBING = new PillarBlock(Settings.copy(PYRITE));
	public static final Block PYRITE_RELIEF = new PillarBlock(Settings.copy(PYRITE));
	public static final Block PYRITE_STACK = new Block(Settings.copy(PYRITE));
	public static final Block PYRITE_PANELING = new Block(Settings.copy(PYRITE));
	public static final Block PYRITE_VENT = new Block(Settings.copy(PYRITE));
	public static final Block PYRITE_RIPPER = new PyriteRipperBlock(Settings.copy(PYRITE).nonOpaque().allowsSpawning(SpectrumBlocks::never).blockVision(SpectrumBlocks::never));
	public static final Block PYRITE_PROJECTOR = new ProjectorBlock(Settings.copy(PYRITE), "pyrite_projector_projection", 16, 14, 1.375F, 1F, 16F);
	
	public static final Block PYRITE_TILES = register(simple(blockWithItem("pyrite_tiles", new Block(Settings.copy(PYRITE)), DyeColor.BROWN)));
	public static final Block PYRITE_TILES_SLAB = register(blockWithItem("pyrite_tiles_slab", new SlabBlock(Settings.copy(PYRITE_TILES)), DyeColor.BROWN));
	public static final Block PYRITE_TILES_STAIRS = register(blockWithItem("pyrite_tiles_stairs", new StairsBlock(PYRITE_TILES.getDefaultState(), Settings.copy(PYRITE_TILES)), DyeColor.BROWN));
	public static final Block PYRITE_TILES_WALL = register(blockWithItem("pyrite_tiles_wall", new WallBlock(Settings.copy(PYRITE_TILES)), DyeColor.BROWN));
	public static final BlockFamily PYRITE_TILE_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(PYRITE_TILES).stairs(PYRITE_TILES_STAIRS).slab(PYRITE_TILES_SLAB).wall(PYRITE_TILES_WALL).build(), TexturedModel.makeFactory(b -> TextureMap.all(PYRITE_PLATING), Models.CUBE_ALL));
	
	public static final Block DRAGONBONE = register(axisRotated(blockWithItem("dragonbone", new DragonboneBlock(Settings.copy(Blocks.BONE_BLOCK).strength(-1.0F, 22.0F).pistonBehavior(PistonBehavior.BLOCK)), DyeColor.GREEN), TexturedModel.CUBE_COLUMN));
	public static final Block CRACKED_DRAGONBONE = register(axisRotated(blockWithItem("cracked_dragonbone", new PillarBlock(Settings.copy(Blocks.BONE_BLOCK).strength(100.0F, 1200.0F).pistonBehavior(PistonBehavior.BLOCK)), DyeColor.GREEN), TexturedModel.CUBE_COLUMN));
	
	public static final Block POLISHED_BONE_ASH = register(blockWithItem("polished_bone_ash", new Block(AbstractBlock.Settings.copy(CRACKED_DRAGONBONE).hardness(1500.0F).mapColor(DyeColor.WHITE)), DyeColor.CYAN));
	public static final Block POLISHED_BONE_ASH_STAIRS = register(blockWithItem("polished_bone_ash_stairs", new StairsBlock(POLISHED_BONE_ASH.getDefaultState(), Settings.copy(POLISHED_BONE_ASH)), DyeColor.CYAN));
	public static final Block POLISHED_BONE_ASH_SLAB = register(blockWithItem("polished_bone_ash_slab", new SlabBlock(Settings.copy(POLISHED_BONE_ASH)), DyeColor.CYAN));
	public static final Block POLISHED_BONE_ASH_WALL = register(blockWithItem("polished_bone_ash_wall", new WallBlock(Settings.copy(POLISHED_BONE_ASH)), DyeColor.CYAN));
	public static final BlockFamily POLISHED_BONE_ASH_FAMILY = registerBlockFamily(new BlockFamily.Builder(POLISHED_BONE_ASH).stairs(POLISHED_BONE_ASH_STAIRS).slab(POLISHED_BONE_ASH_SLAB).wall(POLISHED_BONE_ASH_WALL).build());
	
	public static final Block POLISHED_BONE_ASH_PILLAR = register(axisRotated(blockWithItem("polished_bone_ash_pillar", new PillarBlock(AbstractBlock.Settings.copy(POLISHED_BONE_ASH)), DyeColor.CYAN), TexturedModel.CUBE_COLUMN));
	public static final Block BONE_ASH_SHINGLES = register(blockWithItem("bone_ash_shingles", new ShinglesBlock(AbstractBlock.Settings.copy(POLISHED_BONE_ASH).nonOpaque()), DyeColor.CYAN)
			.withBlockModel((ctx, block) -> createVariantsSupplier(block, ModelIds.getBlockModelId(block)).coordinate(createEastDefaultHorizontalFacingVariantMap())));
	
	public static final Block BONE_ASH_BRICKS = register(blockWithItem("bone_ash_bricks", new Block(AbstractBlock.Settings.copy(POLISHED_BONE_ASH)), DyeColor.CYAN));
	public static final Block BONE_ASH_BRICK_STAIRS = register(blockWithItem("bone_ash_brick_stairs", new StairsBlock(BONE_ASH_BRICKS.getDefaultState(), Settings.copy(BONE_ASH_BRICKS)), DyeColor.CYAN));
	public static final Block BONE_ASH_BRICK_SLAB = register(blockWithItem("bone_ash_brick_slab", new SlabBlock(Settings.copy(BONE_ASH_BRICKS)), DyeColor.CYAN));
	public static final Block BONE_ASH_BRICK_WALL = register(blockWithItem("bone_ash_brick_wall", new WallBlock(Settings.copy(BONE_ASH_BRICKS)), DyeColor.CYAN));
	public static final BlockFamily BONE_ASH_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(BONE_ASH_BRICKS).stairs(BONE_ASH_BRICK_STAIRS).slab(BONE_ASH_BRICK_SLAB).wall(BONE_ASH_BRICK_WALL).build());
	
	public static final Block BONE_ASH_TILES = register(blockWithItem("bone_ash_tiles", new Block(AbstractBlock.Settings.copy(POLISHED_BONE_ASH)), DyeColor.CYAN));
	public static final Block BONE_ASH_TILE_STAIRS = register(blockWithItem("bone_ash_tile_stairs", new StairsBlock(BONE_ASH_TILES.getDefaultState(), Settings.copy(BONE_ASH_TILES)), DyeColor.CYAN));
	public static final Block BONE_ASH_TILE_SLAB = register(blockWithItem("bone_ash_tile_slab", new SlabBlock(Settings.copy(BONE_ASH_TILES)), DyeColor.CYAN));
	public static final Block BONE_ASH_TILE_WALL = register(blockWithItem("bone_ash_tile_wall", new WallBlock(Settings.copy(BONE_ASH_TILES)), DyeColor.CYAN));
	public static final BlockFamily BONE_ASH_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(BONE_ASH_TILES).stairs(BONE_ASH_TILE_STAIRS).slab(BONE_ASH_TILE_SLAB).wall(BONE_ASH_TILE_WALL).build());
	
	public static final Block SLUSH = register(simple(blockWithItem("slush", new PillarBlock(blackslag(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)), DyeColor.BROWN)));
	public static final Block OVERGROWN_SLUSH = register(blockWithItem("overgrown_slush", new SlushVegetationBlock(blackslag(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)), DyeColor.BROWN)
			.withBlockModel((ctx, block) -> {
				TextureMap base = SpectrumTextureMaps.sideTopBottomParticle(block, "_side", block, "_top", SLUSH, "", block, "_top");
				TextureMap snow = SpectrumTextureMaps.sideTopBottomParticle(block, "_snow_side", block, "_snow_top", SLUSH, "", block, "_snow_top");
				return VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.SNOWY)
						.register(false, createHorizontalRotationVariantList(SpectrumModels.CUBE_BOTTOM_TOP_PARTICLE.upload(block, base, ctx.modelCollector)))
						.register(true, createHorizontalRotationVariantList(SpectrumModels.CUBE_BOTTOM_TOP_PARTICLE.upload(block, "_snow", snow, ctx.modelCollector))));
			}));
	public static final Block TILLED_SLUSH = register(singleton(blockWithItem("tilled_slush", new TilledSlushBlock(Settings.copy(SLUSH), SLUSH.getDefaultState()), DyeColor.BROWN), SpectrumTexturedModels.farmland(b -> SLUSH, "", b -> b, "")));
	
	public static final Block BLACK_MATERIA = new BlackMateriaBlock(settings(MapColor.TERRACOTTA_BLACK, BlockSoundGroup.SAND, 0.0F).instrument(NoteBlockInstrument.SNARE).ticksRandomly());
	public static final Block BLACK_SLUDGE = register(simple(blockWithItem("black_sludge", new Block(settings(MapColor.TERRACOTTA_BLACK, BlockSoundGroup.SAND, 0.5F).instrument(NoteBlockInstrument.SNARE)), DyeColor.GRAY)));
	public static final Block SAG_LEAF = new BlackSludgePlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).mapColor(MapColor.TERRACOTTA_BLACK));
	public static final Block SAG_BUBBLE = new BlackSludgePlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).mapColor(MapColor.TERRACOTTA_BLACK));
	public static final Block SMALL_SAG_BUBBLE = new BlackSludgePlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).mapColor(MapColor.TERRACOTTA_BLACK));
	
	public static final PrimordialFireBlock PRIMORDIAL_FIRE = new PrimordialFireBlock(Settings.copy(Blocks.FIRE).mapColor(MapColor.PURPLE).luminance((state) -> 10));
	public static final Block PRIMORDIAL_TORCH = new TorchBlock(SpectrumParticleTypes.PRIMORDIAL_FLAME, Settings.copy(Blocks.SOUL_TORCH).luminance(s -> 13));
	public static final Block PRIMORDIAL_WALL_TORCH = new WallTorchBlock(SpectrumParticleTypes.PRIMORDIAL_FLAME, Settings.copy(SOUL_WALL_TORCH).luminance(s -> 13));
	
	public static <T extends Block> BlockRegistrar<T> moonstoneChiseled(BlockRegistrar<T> registrar, Identifier capTexture) {
		return registrar.withBlockModel((ctx, block) -> {
			TextureMap textureMap = SpectrumTextureMaps.sideLine(capTexture, TextureMap.getId(block));
			Identifier base = SpectrumModels.MOONSTONE_CHISELED.upload(block, textureMap, ctx.modelCollector);
			Identifier down = SpectrumModels.MOONSTONE_CHISELED_DOWN.upload(block, "_down", textureMap, ctx.modelCollector);
			ctx.registerParentedItemModel(block, down);
			return VariantsBlockStateSupplier.create(block).coordinate(createDownDefaultFacingVariantMap(ModelIds.getBlockModelId(block), ModelIds.getBlockSubModelId(block, "_down")));
		});
	}
	
	public static final Block SMOOTH_BASALT_STAIRS = register(blockWithItem("smooth_basalt_stairs", new StairsBlock(Blocks.BASALT.getDefaultState(), AbstractBlock.Settings.copy(Blocks.BASALT)), DyeColor.BROWN));
	public static final Block SMOOTH_BASALT_SLAB = register(blockWithItem("smooth_basalt_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.BASALT)), DyeColor.BROWN));
	public static final Block SMOOTH_BASALT_WALL = register(blockWithItem("smooth_basalt_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.BASALT)), DyeColor.BROWN));
	public static final BlockFamily SMOOTH_BASALT_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(SMOOTH_BASALT).stairs(SMOOTH_BASALT_STAIRS).slab(SMOOTH_BASALT_SLAB).wall(SMOOTH_BASALT_WALL).build(), TexturedModel.CUBE_ALL);
	
	public static final Block POLISHED_BASALT = register(blockWithItem("polished_basalt", new Block(settings(MapColor.BLACK, BlockSoundGroup.BASALT, 2.0F, 5.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresTool()), DyeColor.BROWN));
	public static final Block POLISHED_BASALT_STAIRS = register(blockWithItem("polished_basalt_stairs", new StairsBlock(POLISHED_BASALT.getDefaultState(), AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN));
	public static final Block POLISHED_BASALT_SLAB = register(blockWithItem("polished_basalt_slab", new SlabBlock(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN));
	public static final Block POLISHED_BASALT_WALL = register(blockWithItem("polished_basalt_wall", new WallBlock(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN));
	public static final Block POLISHED_BASALT_BUTTON = register(blockWithItem("polished_basalt_button", new ButtonBlock(SpectrumBlockSetTypes.POLISHED_BASALT, 5, Settings.create().noCollision().strength(0.5F)), DyeColor.BROWN));
	public static final Block POLISHED_BASALT_PRESSURE_PLATE = register(blockWithItem("polished_basalt_pressure_plate", new PressurePlateBlock(SpectrumBlockSetTypes.POLISHED_BASALT, Settings.create().mapColor(MapColor.BLACK).solid().instrument(NoteBlockInstrument.BASEDRUM).requiresTool().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY)), DyeColor.BROWN));
	public static final Block CHISELED_POLISHED_BASALT = register(blockWithItem("chiseled_polished_basalt", new Block(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN));
	public static final BlockFamily POLISHED_BASALT_FAMILY = registerBlockFamily(new BlockFamily.Builder(POLISHED_BASALT).stairs(POLISHED_BASALT_STAIRS).slab(POLISHED_BASALT_SLAB).wall(POLISHED_BASALT_WALL).button(POLISHED_BASALT_BUTTON).pressurePlate(POLISHED_BASALT_PRESSURE_PLATE).chiseled(CHISELED_POLISHED_BASALT).build());
	
	public static final Block POLISHED_BASALT_PILLAR = register(axisRotated(blockWithItem("polished_basalt_pillar", new PillarBlock(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN), TexturedModel.CUBE_COLUMN));
	public static final Block POLISHED_BASALT_CREST = register(blockWithItem("polished_basalt_crest", new CardinalFacingBlock(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN)
			.withBlockModel((ctx, block) -> createVariantsSupplier(ctx, block, TexturedModel.CUBE_COLUMN).coordinate(createCardinalFacingVariantMap())));
	public static final Block NOTCHED_POLISHED_BASALT = register(singleton(blockWithItem("notched_polished_basalt", new Block(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN), TexturedModel.CUBE_COLUMN));
	
	public static final Block BASALT_BRICKS = register(blockWithItem("basalt_bricks", new Block(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN));
	public static final Block BASALT_BRICK_STAIRS = register(blockWithItem("basalt_brick_stairs", new StairsBlock(BASALT_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(BASALT_BRICKS)), DyeColor.BROWN));
	public static final Block BASALT_BRICK_SLAB = register(blockWithItem("basalt_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(BASALT_BRICKS)), DyeColor.BROWN));
	public static final Block BASALT_BRICK_WALL = register(blockWithItem("basalt_brick_wall", new WallBlock(AbstractBlock.Settings.copy(BASALT_BRICKS)), DyeColor.BROWN));
	public static final Block CRACKED_BASALT_BRICKS = register(blockWithItem("cracked_basalt_bricks", new Block(AbstractBlock.Settings.copy(BASALT_BRICKS)), DyeColor.BROWN));
	public static final BlockFamily BASALT_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(BASALT_BRICKS).stairs(BASALT_BRICK_STAIRS).slab(BASALT_BRICK_SLAB).wall(BASALT_BRICK_WALL).cracked(CRACKED_BASALT_BRICKS).build());
	
	public static final Block BASALT_TILES = register(blockWithItem("basalt_tiles", new Block(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN));
	public static final Block BASALT_TILE_STAIRS = register(blockWithItem("basalt_tile_stairs", new StairsBlock(BASALT_TILES.getDefaultState(), AbstractBlock.Settings.copy(BASALT_TILES)), DyeColor.BROWN));
	public static final Block BASALT_TILE_SLAB = register(blockWithItem("basalt_tile_slab", new SlabBlock(AbstractBlock.Settings.copy(BASALT_TILES)), DyeColor.BROWN));
	public static final Block BASALT_TILE_WALL = register(blockWithItem("basalt_tile_wall", new WallBlock(AbstractBlock.Settings.copy(BASALT_TILES)), DyeColor.BROWN));
	public static final Block CRACKED_BASALT_TILES = register(blockWithItem("cracked_basalt_tiles", new Block(AbstractBlock.Settings.copy(BASALT_TILES)), DyeColor.BROWN));
	public static final BlockFamily BASALT_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(BASALT_TILES).stairs(BASALT_TILE_STAIRS).slab(BASALT_TILE_SLAB).wall(BASALT_TILE_WALL).cracked(CRACKED_BASALT_TILES).build());
	
	public static final Block PLANED_BASALT = register(blockWithItem("planed_basalt", new Block(AbstractBlock.Settings.copy(POLISHED_BASALT)), DyeColor.BROWN));
	public static final Block PLANED_BASALT_STAIRS = register(blockWithItem("planed_basalt_stairs", new StairsBlock(PLANED_BASALT.getDefaultState(), AbstractBlock.Settings.copy(PLANED_BASALT)), DyeColor.BROWN));
	public static final Block PLANED_BASALT_SLAB = register(blockWithItem("planed_basalt_slab", new SlabBlock(AbstractBlock.Settings.copy(PLANED_BASALT)), DyeColor.BROWN));
	public static final Block PLANED_BASALT_WALL = register(blockWithItem("planed_basalt_wall", new WallBlock(AbstractBlock.Settings.copy(PLANED_BASALT)), DyeColor.BROWN));
	public static final BlockFamily PLANED_BASALT_FAMILY = registerBlockFamily(new BlockFamily.Builder(PLANED_BASALT).stairs(PLANED_BASALT_STAIRS).slab(PLANED_BASALT_SLAB).wall(PLANED_BASALT_WALL).build());
	
	public static final Block TOPAZ_CHISELED_BASALT = register(simple(blockWithItem("topaz_chiseled_basalt", new Block(AbstractBlock.Settings.copy(BASALT_BRICKS).luminance(s -> 6)), DyeColor.CYAN)));
	public static final Block AMETHYST_CHISELED_BASALT = register(simple(blockWithItem("amethyst_chiseled_basalt", new Block(AbstractBlock.Settings.copy(BASALT_BRICKS).luminance(s -> 5)), DyeColor.MAGENTA)));
	public static final Block CITRINE_CHISELED_BASALT = register(simple(blockWithItem("citrine_chiseled_basalt", new Block(AbstractBlock.Settings.copy(BASALT_BRICKS).luminance(s -> 7)), DyeColor.YELLOW)));
	public static final Block ONYX_CHISELED_BASALT = register(simple(blockWithItem("onyx_chiseled_basalt", new Block(AbstractBlock.Settings.copy(BASALT_BRICKS).luminance(s -> 3)), DyeColor.BLACK)));
	public static final Block MOONSTONE_CHISELED_BASALT = register(moonstoneChiseled(blockWithItem("moonstone_chiseled_basalt", new SpectrumLineFacingBlock(AbstractBlock.Settings.copy(BASALT_BRICKS).luminance(s -> 12)), DyeColor.WHITE), SpectrumTextures.BASALT_CAP));
	
	public static final Block CALCITE_STAIRS = register(blockWithItem("calcite_stairs", new StairsBlock(Blocks.CALCITE.getDefaultState(), AbstractBlock.Settings.copy(Blocks.CALCITE)), DyeColor.BROWN));
	public static final Block CALCITE_SLAB = register(blockWithItem("calcite_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CALCITE)), DyeColor.BROWN));
	public static final Block CALCITE_WALL = register(blockWithItem("calcite_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.CALCITE)), DyeColor.BROWN));
	public static final BlockFamily CALCITE_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(Blocks.CALCITE).stairs(CALCITE_STAIRS).slab(CALCITE_SLAB).wall(CALCITE_WALL).build(), TexturedModel.CUBE_ALL);
	
	public static final Block POLISHED_CALCITE = register(blockWithItem("polished_calcite", new Block(settings(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CALCITE, 2.0F, 5.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresTool()), DyeColor.BROWN));
	public static final Block POLISHED_CALCITE_STAIRS = register(blockWithItem("polished_calcite_stairs", new StairsBlock(POLISHED_CALCITE.getDefaultState(), AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN));
	public static final Block POLISHED_CALCITE_SLAB = register(blockWithItem("polished_calcite_slab", new SlabBlock(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN));
	public static final Block POLISHED_CALCITE_WALL = register(blockWithItem("polished_calcite_wall", new WallBlock(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN));
	public static final Block POLISHED_CALCITE_BUTTON = register(blockWithItem("polished_calcite_button", new ButtonBlock(SpectrumBlockSetTypes.POLISHED_CALCITE, 5, Settings.create().noCollision().strength(0.5F)), DyeColor.BROWN));
	public static final Block POLISHED_CALCITE_PRESSURE_PLATE = register(blockWithItem("polished_calcite_pressure_plate", new PressurePlateBlock(SpectrumBlockSetTypes.POLISHED_CALCITE, Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).solid().instrument(NoteBlockInstrument.BASEDRUM).requiresTool().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY)), DyeColor.BROWN));
	public static final Block CHISELED_POLISHED_CALCITE = register(blockWithItem("chiseled_polished_calcite", new Block(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN));
	public static final BlockFamily POLISHED_CALCITE_FAMILY = registerBlockFamily(new BlockFamily.Builder(POLISHED_CALCITE).stairs(POLISHED_CALCITE_STAIRS).slab(POLISHED_CALCITE_SLAB).wall(POLISHED_CALCITE_WALL).button(POLISHED_CALCITE_BUTTON).pressurePlate(POLISHED_CALCITE_PRESSURE_PLATE).chiseled(CHISELED_POLISHED_CALCITE).build());
	
	public static final Block POLISHED_CALCITE_PILLAR = register(axisRotated(blockWithItem("polished_calcite_pillar", new PillarBlock(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN), TexturedModel.CUBE_COLUMN));
	public static final Block POLISHED_CALCITE_CREST = register(blockWithItem("polished_calcite_crest", new CardinalFacingBlock(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN)
			.withBlockModel((ctx, block) -> createVariantsSupplier(ctx, block, TexturedModel.CUBE_COLUMN).coordinate(createCardinalFacingVariantMap())));
	public static final Block NOTCHED_POLISHED_CALCITE = register(singleton(blockWithItem("notched_polished_calcite", new Block(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN), TexturedModel.CUBE_COLUMN));
	
	public static final Block CALCITE_BRICKS = register(blockWithItem("calcite_bricks", new Block(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN));
	public static final Block CALCITE_BRICK_STAIRS = register(blockWithItem("calcite_brick_stairs", new StairsBlock(CALCITE_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(CALCITE_BRICKS)), DyeColor.BROWN));
	public static final Block CALCITE_BRICK_SLAB = register(blockWithItem("calcite_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(CALCITE_BRICKS)), DyeColor.BROWN));
	public static final Block CALCITE_BRICK_WALL = register(blockWithItem("calcite_brick_wall", new WallBlock(AbstractBlock.Settings.copy(CALCITE_BRICKS)), DyeColor.BROWN));
	public static final Block CRACKED_CALCITE_BRICKS = register(blockWithItem("cracked_calcite_bricks", new Block(AbstractBlock.Settings.copy(CALCITE_BRICKS)), DyeColor.BROWN));
	public static final BlockFamily CALCITE_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(CALCITE_BRICKS).stairs(CALCITE_BRICK_STAIRS).slab(CALCITE_BRICK_SLAB).wall(CALCITE_BRICK_WALL).cracked(CRACKED_CALCITE_BRICKS).build());
	
	public static final Block CALCITE_TILES = register(blockWithItem("calcite_tiles", new Block(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN));
	public static final Block CALCITE_TILE_STAIRS = register(blockWithItem("calcite_tile_stairs", new StairsBlock(CALCITE_TILES.getDefaultState(), AbstractBlock.Settings.copy(CALCITE_TILES)), DyeColor.BROWN));
	public static final Block CALCITE_TILE_SLAB = register(blockWithItem("calcite_tile_slab", new SlabBlock(AbstractBlock.Settings.copy(CALCITE_TILES)), DyeColor.BROWN));
	public static final Block CALCITE_TILE_WALL = register(blockWithItem("calcite_tile_wall", new WallBlock(AbstractBlock.Settings.copy(CALCITE_TILES)), DyeColor.BROWN));
	public static final Block CRACKED_CALCITE_TILES = register(blockWithItem("cracked_calcite_tiles", new Block(AbstractBlock.Settings.copy(CALCITE_TILES)), DyeColor.BROWN));
	public static final BlockFamily CALCITE_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(CALCITE_TILES).stairs(CALCITE_TILE_STAIRS).slab(CALCITE_TILE_SLAB).wall(CALCITE_TILE_WALL).cracked(CRACKED_CALCITE_TILES).build());
	
	public static final Block PLANED_CALCITE = register(blockWithItem("planed_calcite", new Block(AbstractBlock.Settings.copy(POLISHED_CALCITE)), DyeColor.BROWN));
	public static final Block PLANED_CALCITE_STAIRS = register(blockWithItem("planed_calcite_stairs", new StairsBlock(PLANED_CALCITE.getDefaultState(), AbstractBlock.Settings.copy(PLANED_CALCITE)), DyeColor.BROWN));
	public static final Block PLANED_CALCITE_SLAB = register(blockWithItem("planed_calcite_slab", new SlabBlock(AbstractBlock.Settings.copy(PLANED_CALCITE)), DyeColor.BROWN));
	public static final Block PLANED_CALCITE_WALL = register(blockWithItem("planed_calcite_wall", new WallBlock(AbstractBlock.Settings.copy(PLANED_CALCITE)), DyeColor.BROWN));
	public static final BlockFamily PLANED_CALCITE_FAMILY = registerBlockFamily(new BlockFamily.Builder(PLANED_CALCITE).stairs(PLANED_CALCITE_STAIRS).slab(PLANED_CALCITE_SLAB).wall(PLANED_CALCITE_WALL).build());
	
	public static final Block TOPAZ_CHISELED_CALCITE = register(simple(blockWithItem("topaz_chiseled_calcite", new Block(AbstractBlock.Settings.copy(CALCITE_BRICKS).luminance(s -> 6)), DyeColor.CYAN)));
	public static final Block AMETHYST_CHISELED_CALCITE = register(simple(blockWithItem("amethyst_chiseled_calcite", new Block(AbstractBlock.Settings.copy(CALCITE_BRICKS).luminance(s -> 5)), DyeColor.MAGENTA)));
	public static final Block CITRINE_CHISELED_CALCITE = register(simple(blockWithItem("citrine_chiseled_calcite", new Block(AbstractBlock.Settings.copy(CALCITE_BRICKS).luminance(s -> 7)), DyeColor.YELLOW)));
	public static final Block ONYX_CHISELED_CALCITE = register(simple(blockWithItem("onyx_chiseled_calcite", new Block(AbstractBlock.Settings.copy(CALCITE_BRICKS).luminance(s -> 3)), DyeColor.BLACK)));
	public static final Block MOONSTONE_CHISELED_CALCITE = register(moonstoneChiseled(blockWithItem("moonstone_chiseled_calcite", new SpectrumLineFacingBlock(AbstractBlock.Settings.copy(CALCITE_BRICKS).luminance(s -> 12)), DyeColor.WHITE), SpectrumTextures.CALCITE_CAP));
	
	public static PillarBlock registerGemstoneLight(String name, Block gemBlock, Block baseBlock, Identifier capTexture, DyeColor color) {
		return register(translucent(axisRotated(blockWithItem(name, new PillarBlock(AbstractBlock.Settings.copy(baseBlock).luminance(s -> 15).nonOpaque().solid()), color), TexturedModel.makeFactory(block -> SpectrumTextureMaps.sideTopInside(TextureMap.getId(block), capTexture, TextureMap.getId(gemBlock)), SpectrumModels.MULTILAYER_LIGHT))));
	}
	
	public static final Block TOPAZ_BASALT_LIGHT = registerGemstoneLight("topaz_basalt_light", TOPAZ_BLOCK, POLISHED_BASALT, SpectrumTextures.BASALT_CAP, DyeColor.CYAN);
	public static final Block AMETHYST_BASALT_LIGHT = registerGemstoneLight("amethyst_basalt_light", AMETHYST_BLOCK, POLISHED_BASALT, SpectrumTextures.BASALT_CAP, DyeColor.MAGENTA);
	public static final Block CITRINE_BASALT_LIGHT = registerGemstoneLight("citrine_basalt_light", CITRINE_BLOCK, POLISHED_BASALT, SpectrumTextures.BASALT_CAP, DyeColor.YELLOW);
	public static final Block ONYX_BASALT_LIGHT = registerGemstoneLight("onyx_basalt_light", ONYX_BLOCK, POLISHED_BASALT, SpectrumTextures.BASALT_CAP, DyeColor.BLACK);
	public static final Block MOONSTONE_BASALT_LIGHT = registerGemstoneLight("moonstone_basalt_light", MOONSTONE_BLOCK, POLISHED_BASALT, SpectrumTextures.BASALT_CAP, DyeColor.WHITE);
	public static final Block TOPAZ_CALCITE_LIGHT = registerGemstoneLight("topaz_calcite_light", TOPAZ_BLOCK, POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, DyeColor.CYAN);
	public static final Block AMETHYST_CALCITE_LIGHT = registerGemstoneLight("amethyst_calcite_light", AMETHYST_BLOCK, POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, DyeColor.MAGENTA);
	public static final Block CITRINE_CALCITE_LIGHT = registerGemstoneLight("citrine_calcite_light", CITRINE_BLOCK, POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, DyeColor.YELLOW);
	public static final Block ONYX_CALCITE_LIGHT = registerGemstoneLight("onyx_calcite_light", ONYX_BLOCK, POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, DyeColor.BLACK);
	public static final Block MOONSTONE_CALCITE_LIGHT = registerGemstoneLight("moonstone_calcite_light", MOONSTONE_BLOCK, POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, DyeColor.WHITE);
	
	// GLASS
	private static Settings gemstoneGlass(BlockSoundGroup soundGroup, MapColor mapColor) {
		return AbstractBlock.Settings.copy(Blocks.GLASS).sounds(soundGroup).mapColor(mapColor);
	}
	
	public static <T extends Block> BlockRegistrar<T> glassPane(BlockRegistrar<T> registrar, Block glassBlock) {
		return translucent(registrar).withBlockModel((ctx, block) -> glassPaneBlockModel(ctx, block, glassBlock));
	}
	
	public static final Block TOPAZ_GLASS = register(translucent(simple(blockWithItem("topaz_glass", new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.TOPAZ_CLUSTER, MapColor.CYAN), BuiltinGemstoneColor.CYAN), DyeColor.CYAN))));
	public static final Block AMETHYST_GLASS = register(translucent(simple(blockWithItem("amethyst_glass", new GemstoneGlassBlock(gemstoneGlass(BlockSoundGroup.AMETHYST_CLUSTER, MapColor.MAGENTA), BuiltinGemstoneColor.MAGENTA), DyeColor.MAGENTA))));
	public static final Block CITRINE_GLASS = register(translucent(simple(blockWithItem("citrine_glass", new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.CITRINE_CLUSTER, MapColor.YELLOW), BuiltinGemstoneColor.YELLOW), DyeColor.YELLOW))));
	public static final Block ONYX_GLASS = register(translucent(simple(blockWithItem("onyx_glass", new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.ONYX_CLUSTER, MapColor.BLACK), BuiltinGemstoneColor.BLACK), DyeColor.BLACK))));
	public static final Block MOONSTONE_GLASS = register(translucent(simple(blockWithItem("moonstone_glass", new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.MOONSTONE_CLUSTER, MapColor.WHITE), BuiltinGemstoneColor.WHITE), DyeColor.WHITE))));
	public static final Block RADIANT_GLASS = register(translucent(simple(blockWithItem("radiant_glass", new RadiantGlassBlock(gemstoneGlass(BlockSoundGroup.GLASS, MapColor.PALE_YELLOW).luminance(value -> 12)), DyeColor.WHITE))));
	
	public static final Block TOPAZ_GLASS_PANE = register(glassPane(blockWithItem("topaz_glass_pane", new PaneBlock(gemstoneGlass(SpectrumBlockSoundGroups.TOPAZ_CLUSTER, MapColor.CYAN)), DyeColor.CYAN), TOPAZ_GLASS));
	public static final Block AMETHYST_GLASS_PANE = register(glassPane(blockWithItem("amethyst_glass_pane", new PaneBlock(gemstoneGlass(BlockSoundGroup.AMETHYST_CLUSTER, MapColor.MAGENTA)), DyeColor.MAGENTA), AMETHYST_GLASS));
	public static final Block CITRINE_GLASS_PANE = register(glassPane(blockWithItem("citrine_glass_pane", new PaneBlock(gemstoneGlass(SpectrumBlockSoundGroups.CITRINE_CLUSTER, MapColor.YELLOW)), DyeColor.YELLOW), CITRINE_GLASS));
	public static final Block ONYX_GLASS_PANE = register(glassPane(blockWithItem("onyx_glass_pane", new PaneBlock(gemstoneGlass(SpectrumBlockSoundGroups.ONYX_CLUSTER, MapColor.BLACK)), DyeColor.BLACK), ONYX_GLASS));
	public static final Block MOONSTONE_GLASS_PANE = register(glassPane(blockWithItem("moonstone_glass_pane", new PaneBlock(gemstoneGlass(SpectrumBlockSoundGroups.MOONSTONE_CLUSTER, MapColor.WHITE)), DyeColor.WHITE), MOONSTONE_GLASS));
	public static final Block RADIANT_GLASS_PANE = register(glassPane(blockWithItem("radiant_glass_pane", new PaneBlock(gemstoneGlass(BlockSoundGroup.GLASS, MapColor.PALE_YELLOW).luminance(value -> 12)), DyeColor.WHITE), RADIANT_GLASS));
	
	public static final Block ETHEREAL_PLATFORM = new EtherealPlatformBlock(gemstoneGlass(BlockSoundGroup.AMETHYST_BLOCK, MapColor.CLEAR).pistonBehavior(PistonBehavior.NORMAL));
	public static final Block UNIVERSE_SPYHOLE = new TransparentBlock(settings(MapColor.CLEAR, SpectrumBlockSoundGroups.CITRINE_BLOCK, 1.5F).requiresTool().blockVision(SpectrumBlocks::never));
	
	private static Settings chime(AbstractBlock block) {
		return AbstractBlock.Settings.copy(block).pistonBehavior(PistonBehavior.DESTROY).hardness(1.0F).nonOpaque();
	}
	
	public static final Block TOPAZ_CHIME = register(singleton(blockWithItem("topaz_chime", new GemstoneChimeBlock(chime(TOPAZ_CLUSTER), SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_CHIME, SpectrumParticleTypes.CYAN_SPARKLE_RISING), DyeColor.CYAN), SpectrumTexturedModels.CHIME));
	public static final Block AMETHYST_CHIME = register(singleton(blockWithItem("amethyst_chime", new GemstoneChimeBlock(chime(Blocks.AMETHYST_CLUSTER), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SpectrumParticleTypes.MAGENTA_SPARKLE_RISING), DyeColor.MAGENTA), SpectrumTexturedModels.CHIME));
	public static final Block CITRINE_CHIME = register(singleton(blockWithItem("citrine_chime", new GemstoneChimeBlock(chime(CITRINE_CLUSTER), SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_CHIME, SpectrumParticleTypes.YELLOW_SPARKLE_RISING), DyeColor.YELLOW), SpectrumTexturedModels.CHIME));
	public static final Block ONYX_CHIME = register(singleton(blockWithItem("onyx_chime", new GemstoneChimeBlock(chime(ONYX_CLUSTER), SpectrumSoundEvents.BLOCK_ONYX_BLOCK_CHIME, SpectrumParticleTypes.BLACK_SPARKLE_RISING), DyeColor.BLACK), SpectrumTexturedModels.CHIME));
	public static final Block MOONSTONE_CHIME = register(singleton(blockWithItem("moonstone_chime", new GemstoneChimeBlock(chime(MOONSTONE_CLUSTER), SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_CHIME, SpectrumParticleTypes.WHITE_SPARKLE_RISING), DyeColor.WHITE), SpectrumTexturedModels.CHIME));
	
	private static Settings decostone(AbstractBlock block) {
		return AbstractBlock.Settings.copy(block).nonOpaque();
	}
	
	public static final Block TOPAZ_DECOSTONE = new PylonBlock(decostone(TOPAZ_BLOCK));
	public static final Block AMETHYST_DECOSTONE = new PylonBlock(decostone(Blocks.AMETHYST_BLOCK));
	public static final Block CITRINE_DECOSTONE = new PylonBlock(decostone(CITRINE_BLOCK));
	public static final Block ONYX_DECOSTONE = new PylonBlock(decostone(ONYX_BLOCK));
	public static final Block MOONSTONE_DECOSTONE = new PylonBlock(decostone(MOONSTONE_BLOCK));
	
	public static final Block SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("semi_permeable_glass", new AlternatePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(Blocks.GLASS), Blocks.GLASS, false), DyeColor.WHITE), Blocks.GLASS)));
	public static final Block TINTED_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("tinted_semi_permeable_glass", new AlternatePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(Blocks.TINTED_GLASS), Blocks.TINTED_GLASS, true), DyeColor.BLACK), Blocks.TINTED_GLASS)));
	public static final Block RADIANT_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("radiant_semi_permeable_glass", new AlternatePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(SpectrumBlocks.RADIANT_GLASS), SpectrumBlocks.RADIANT_GLASS, false), DyeColor.YELLOW), SpectrumBlocks.RADIANT_GLASS)));
	public static final Block TOPAZ_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("topaz_semi_permeable_glass", new GemstonePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(SpectrumBlocks.TOPAZ_GLASS), BuiltinGemstoneColor.CYAN), DyeColor.CYAN), TOPAZ_GLASS)));
	public static final Block AMETHYST_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("amethyst_semi_permeable_glass", new GemstonePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(SpectrumBlocks.AMETHYST_GLASS), BuiltinGemstoneColor.MAGENTA), DyeColor.MAGENTA), AMETHYST_GLASS)));
	public static final Block CITRINE_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("citrine_semi_permeable_glass", new GemstonePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(SpectrumBlocks.CITRINE_GLASS), BuiltinGemstoneColor.YELLOW), DyeColor.YELLOW), CITRINE_GLASS)));
	public static final Block ONYX_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("onyx_semi_permeable_glass", new GemstonePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(SpectrumBlocks.ONYX_GLASS), BuiltinGemstoneColor.BLACK), DyeColor.BLACK), ONYX_GLASS)));
	public static final Block MOONSTONE_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("moonstone_semi_permeable_glass", new GemstonePlayerOnlyGlassBlock(AbstractBlock.Settings.copy(SpectrumBlocks.MOONSTONE_GLASS), BuiltinGemstoneColor.WHITE), DyeColor.WHITE), MOONSTONE_GLASS)));
	
	// MELON
	public static final RegistryKey<Block> GLISTERING_MELON = registerCustom(keyOf("glistering_melon"), key ->
			COMMON_REGISTRAR.defer(() -> registerBlockWithItem(key, new Block(AbstractBlock.Settings.copy(Blocks.MELON)), IS.of(), DyeColor.LIME)));
	public static final RegistryKey<Block> ATTACHED_GLISTERING_MELON_STEM = registerCustom(keyOf("attached_glistering_melon_stem"), key -> {
		COMMON_REGISTRAR.defer(() -> Registry.register(Registries.BLOCK, key, new AttachedStemBlock(keyOf("glistering_melon_stem"), GLISTERING_MELON, SpectrumItems.GLISTERING_MELON_SEEDS, AbstractBlock.Settings.copy(Blocks.ATTACHED_MELON_STEM))));
		CLIENT_REGISTRAR.defer(() -> BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(key), RenderLayer.getCutout()));
	});
	public static final RegistryKey<Block> GLISTERING_MELON_STEM = registerCustom(keyOf("glistering_melon_stem"), key -> {
		COMMON_REGISTRAR.defer(() -> Registry.register(Registries.BLOCK, key, new StemBlock(GLISTERING_MELON, ATTACHED_GLISTERING_MELON_STEM, SpectrumItems.GLISTERING_MELON_SEEDS, AbstractBlock.Settings.copy(Blocks.MELON_STEM))));
		CLIENT_REGISTRAR.defer(() -> BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(key), RenderLayer.getCutout()));
	});
	
	public static final Block PRESENT = new PresentBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL));
	public static final Block TITRATION_BARREL = new TitrationBarrelBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).mapColor(MapColor.RED));
	
	public static final Block PARAMETRIC_MINING_DEVICE = new ParametricMiningDeviceBlock(AbstractBlock.Settings.copy(BLACKSLAG).nonOpaque().breakInstantly());
	public static final Block THREAT_CONFLUX = new ThreatConfluxBlock(AbstractBlock.Settings.copy(BLACKSLAG).nonOpaque().breakInstantly());
	
	public static final Block BLOCK_FLOODER = new BlockFlooderBlock(settings(MapColor.LIGHT_BLUE_GRAY, BlockSoundGroup.ROOTED_DIRT, 0.0F));
	public static final Block BOTTOMLESS_BUNDLE = new BottomlessBundleBlock(settings(MapColor.PALE_PURPLE, BlockSoundGroup.WOOL, 1.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY));
	public static final Block WAND_LIGHT_BLOCK = new WandLightBlock(AbstractBlock.Settings.copy(Blocks.LIGHT).sounds(SpectrumBlockSoundGroups.WAND_LIGHT).breakInstantly());
	public static final Block DECAYING_LIGHT_BLOCK = new DecayingLightBlock(AbstractBlock.Settings.copy(WAND_LIGHT_BLOCK).ticksRandomly());
	
	private static Settings decay(MapColor mapColor, BlockSoundGroup soundGroup, float strength, float resistance, PistonBehavior pistonBehavior) {
		return settings(mapColor, soundGroup, strength, resistance).pistonBehavior(pistonBehavior).ticksRandomly().allowsSpawning((state, world, pos, type) -> false);
	}
	
	public static <T extends Block> BlockRegistrar<T> decay(BlockRegistrar<T> registrar) {
		return registrar.withBlockModel((ctx, block) -> {
			Identifier none = Models.CUBE_ALL.upload(block, "_none", SpectrumTextureMaps.all(block, "_none"), ctx.modelCollector);
			Identifier def = Models.CUBE_ALL.upload(block, "_default", SpectrumTextureMaps.all(block, "_default"), ctx.modelCollector);
			return VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(DecayBlock.CONVERSION)
					.register(DecayBlock.Conversion.NONE, createModelVariant(none))
					.register(DecayBlock.Conversion.DEFAULT, createModelVariant(def))
					.register(DecayBlock.Conversion.SPECIAL, createModelVariant(def)));
		});
	}
	
	public static final Block FADING = register(decay(block("fading", new FadingBlock(decay(MapColor.DARK_GREEN, BlockSoundGroup.GRASS, 0.5F, 0.5F, PistonBehavior.DESTROY)))));
	public static final Block FAILING = register(decay(block("failing", new FailingBlock(decay(MapColor.BLACK, BlockSoundGroup.STONE, 20.0F, 50.0F, PistonBehavior.BLOCK)))));
	public static final Block RUIN = register(decay(block("ruin", new RuinBlock(decay(MapColor.BLACK, BlockSoundGroup.STONE, 100.0F, 3600000.0F, PistonBehavior.BLOCK)))));
	public static final Block FORFEITURE = register(decay(block("forfeiture", new ForfeitureBlock(decay(MapColor.BLACK, BlockSoundGroup.STONE, 100.0F, 3600000.0F, PistonBehavior.BLOCK)))));
	public static final Block DECAY_AWAY = register(simple(block("decay_away", new DecayAwayBlock(AbstractBlock.Settings.copy(Blocks.DIRT).pistonBehavior(PistonBehavior.DESTROY)))));
	
	// ROCK CANDY
	private static Settings rockCandy(AbstractBlock block) {
		return AbstractBlock.Settings.copy(block).pistonBehavior(PistonBehavior.DESTROY).hardness(0.5F).luminance(ROCK_CANDY_LUMINANCE).ticksRandomly();
	}
	
	private static final ToIntFunction<BlockState> ROCK_CANDY_LUMINANCE = state -> Math.max(15, state.get(Properties.AGE_2) * 3 + (state.get(SugarStickBlock.LOGGED) == FluidLogging.State.LIQUID_CRYSTAL ? LiquidCrystalFluidBlock.LUMINANCE : 8));
	public static final Block SUGAR_STICK = new SugarStickBlock(rockCandy(SMALL_AMETHYST_BUD), RockCandy.RockCandyVariant.SUGAR);
	public static final Block TOPAZ_SUGAR_STICK = new SugarStickBlock(rockCandy(SpectrumBlocks.SMALL_TOPAZ_BUD), RockCandy.RockCandyVariant.TOPAZ);
	public static final Block AMETHYST_SUGAR_STICK = new SugarStickBlock(rockCandy(Blocks.SMALL_AMETHYST_BUD), RockCandy.RockCandyVariant.AMETHYST);
	public static final Block CITRINE_SUGAR_STICK = new SugarStickBlock(rockCandy(SpectrumBlocks.SMALL_CITRINE_BUD), RockCandy.RockCandyVariant.CITRINE);
	public static final Block ONYX_SUGAR_STICK = new SugarStickBlock(rockCandy(SpectrumBlocks.SMALL_ONYX_BUD), RockCandy.RockCandyVariant.ONYX);
	public static final Block MOONSTONE_SUGAR_STICK = new SugarStickBlock(rockCandy(SpectrumBlocks.SMALL_MOONSTONE_BUD), RockCandy.RockCandyVariant.MOONSTONE);
	
	// PASTEL NETWORK
	private static Settings pastelNode(BlockSoundGroup soundGroup) {
		return settings(MapColor.CLEAR, soundGroup, 1.5F).nonOpaque().requiresTool();
	}
	
	public static final Block CONNECTION_NODE = new PastelNodeBlock(pastelNode(BlockSoundGroup.AMETHYST_CLUSTER), PastelNodeType.CONNECTION);
	public static final Block PROVIDER_NODE = new PastelNodeBlock(pastelNode(BlockSoundGroup.AMETHYST_CLUSTER), PastelNodeType.PROVIDER);
	public static final Block STORAGE_NODE = new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.TOPAZ_CLUSTER), PastelNodeType.STORAGE);
	public static final Block BUFFER_NODE = new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.TOPAZ_CLUSTER), PastelNodeType.BUFFER);
	public static final Block SENDER_NODE = new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.CITRINE_CLUSTER), PastelNodeType.SENDER);
	public static final Block GATHER_NODE = new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.ONYX_CLUSTER), PastelNodeType.GATHER);
	
	// COLORED BLOCK FAMILIES
	
	public static ColoredPlankBlock registerColoredPlanks(String name, DyeColor dyeColor) {
		return register(blockWithItem(name, new ColoredPlankBlock(copyWithMapColor(OAK_PLANKS, dyeColor.getMapColor()), dyeColor), dyeColor));
	}
	
	public static ColoredStairsBlock registerColoredStairs(String name, ColoredPlankBlock baseBlock) {
		return register(blockWithItem(name, new ColoredStairsBlock(baseBlock.getDefaultState(), copyWithMapColor(OAK_STAIRS, baseBlock.getDefaultMapColor()), baseBlock.getColor()), baseBlock.getColor()));
	}
	
	public static ColoredPressurePlateBlock registerColoredPressurePlate(String name, ColoredPlankBlock baseBlock) {
		return register(blockWithItem(name, new ColoredPressurePlateBlock(copyWithMapColor(OAK_PRESSURE_PLATE, baseBlock.getDefaultMapColor()), baseBlock.getColor()), baseBlock.getColor()));
	}
	
	public static ColoredFenceBlock registerColoredFence(String name, ColoredPlankBlock baseBlock) {
		return register(blockWithItem(name, new ColoredFenceBlock(copyWithMapColor(OAK_FENCE, baseBlock.getDefaultMapColor()), baseBlock.getColor()), baseBlock.getColor()));
	}
	
	public static ColoredFenceGateBlock registerColoredFenceGate(String name, ColoredPlankBlock baseBlock) {
		return register(blockWithItem(name, new ColoredFenceGateBlock(copyWithMapColor(OAK_FENCE_GATE, baseBlock.getDefaultMapColor()), baseBlock.getColor()), baseBlock.getColor()));
	}
	
	public static ColoredWoodenButtonBlock registerColoredButton(String name, ColoredPlankBlock baseBlock) {
		return register(blockWithItem(name, new ColoredWoodenButtonBlock(copyWithMapColor(OAK_BUTTON, baseBlock.getDefaultMapColor()), baseBlock.getColor()), baseBlock.getColor()));
	}
	
	public static ColoredSlabBlock registerColoredSlab(String name, ColoredPlankBlock baseBlock) {
		return register(blockWithItem(name, new ColoredSlabBlock(copyWithMapColor(OAK_SLAB, baseBlock.getDefaultMapColor()), baseBlock.getColor()), baseBlock.getColor()));
	}
	
	public static final ColoredPlankBlock BLACK_PLANKS = registerColoredPlanks("black_planks", DyeColor.BLACK);
	public static final ColoredStairsBlock BLACK_STAIRS = registerColoredStairs("black_stairs", BLACK_PLANKS);
	public static final ColoredPressurePlateBlock BLACK_PRESSURE_PLATE = registerColoredPressurePlate("black_pressure_plate", BLACK_PLANKS);
	public static final ColoredFenceBlock BLACK_FENCE = registerColoredFence("black_fence", BLACK_PLANKS);
	public static final ColoredFenceGateBlock BLACK_FENCE_GATE = registerColoredFenceGate("black_fence_gate", BLACK_PLANKS);
	public static final ColoredWoodenButtonBlock BLACK_BUTTON = registerColoredButton("black_button", BLACK_PLANKS);
	public static final ColoredSlabBlock BLACK_SLAB = registerColoredSlab("black_slab", BLACK_PLANKS);
	public static final BlockFamily BLACK_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(BLACK_PLANKS).stairs(BLACK_STAIRS).pressurePlate(BLACK_PRESSURE_PLATE).fence(BLACK_FENCE).fenceGate(BLACK_FENCE_GATE).button(BLACK_BUTTON).slab(BLACK_SLAB).build());
	
	public static final ColoredPlankBlock BLUE_PLANKS = registerColoredPlanks("blue_planks", DyeColor.BLUE);
	public static final ColoredStairsBlock BLUE_STAIRS = registerColoredStairs("blue_stairs", BLUE_PLANKS);
	public static final ColoredPressurePlateBlock BLUE_PRESSURE_PLATE = registerColoredPressurePlate("blue_pressure_plate", BLUE_PLANKS);
	public static final ColoredFenceBlock BLUE_FENCE = registerColoredFence("blue_fence", BLUE_PLANKS);
	public static final ColoredFenceGateBlock BLUE_FENCE_GATE = registerColoredFenceGate("blue_fence_gate", BLUE_PLANKS);
	public static final ColoredWoodenButtonBlock BLUE_BUTTON = registerColoredButton("blue_button", BLUE_PLANKS);
	public static final ColoredSlabBlock BLUE_SLAB = registerColoredSlab("blue_slab", BLUE_PLANKS);
	public static final BlockFamily BLUE_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(BLUE_PLANKS).stairs(BLUE_STAIRS).pressurePlate(BLUE_PRESSURE_PLATE).fence(BLUE_FENCE).fenceGate(BLUE_FENCE_GATE).button(BLUE_BUTTON).slab(BLUE_SLAB).build());
	
	public static final ColoredPlankBlock BROWN_PLANKS = registerColoredPlanks("brown_planks", DyeColor.BROWN);
	public static final ColoredStairsBlock BROWN_STAIRS = registerColoredStairs("brown_stairs", BROWN_PLANKS);
	public static final ColoredPressurePlateBlock BROWN_PRESSURE_PLATE = registerColoredPressurePlate("brown_pressure_plate", BROWN_PLANKS);
	public static final ColoredFenceBlock BROWN_FENCE = registerColoredFence("brown_fence", BROWN_PLANKS);
	public static final ColoredFenceGateBlock BROWN_FENCE_GATE = registerColoredFenceGate("brown_fence_gate", BROWN_PLANKS);
	public static final ColoredWoodenButtonBlock BROWN_BUTTON = registerColoredButton("brown_button", BROWN_PLANKS);
	public static final ColoredSlabBlock BROWN_SLAB = registerColoredSlab("brown_slab", BROWN_PLANKS);
	public static final BlockFamily BROWN_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(BROWN_PLANKS).stairs(BROWN_STAIRS).pressurePlate(BROWN_PRESSURE_PLATE).fence(BROWN_FENCE).fenceGate(BROWN_FENCE_GATE).button(BROWN_BUTTON).slab(BROWN_SLAB).build());
	
	public static final ColoredPlankBlock CYAN_PLANKS = registerColoredPlanks("cyan_planks", DyeColor.CYAN);
	public static final ColoredStairsBlock CYAN_STAIRS = registerColoredStairs("cyan_stairs", CYAN_PLANKS);
	public static final ColoredPressurePlateBlock CYAN_PRESSURE_PLATE = registerColoredPressurePlate("cyan_pressure_plate", CYAN_PLANKS);
	public static final ColoredFenceBlock CYAN_FENCE = registerColoredFence("cyan_fence", CYAN_PLANKS);
	public static final ColoredFenceGateBlock CYAN_FENCE_GATE = registerColoredFenceGate("cyan_fence_gate", CYAN_PLANKS);
	public static final ColoredWoodenButtonBlock CYAN_BUTTON = registerColoredButton("cyan_button", CYAN_PLANKS);
	public static final ColoredSlabBlock CYAN_SLAB = registerColoredSlab("cyan_slab", CYAN_PLANKS);
	public static final BlockFamily CYAN_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(CYAN_PLANKS).stairs(CYAN_STAIRS).pressurePlate(CYAN_PRESSURE_PLATE).fence(CYAN_FENCE).fenceGate(CYAN_FENCE_GATE).button(CYAN_BUTTON).slab(CYAN_SLAB).build());
	
	public static final ColoredPlankBlock GRAY_PLANKS = registerColoredPlanks("gray_planks", DyeColor.GRAY);
	public static final ColoredStairsBlock GRAY_STAIRS = registerColoredStairs("gray_stairs", GRAY_PLANKS);
	public static final ColoredPressurePlateBlock GRAY_PRESSURE_PLATE = registerColoredPressurePlate("gray_pressure_plate", GRAY_PLANKS);
	public static final ColoredFenceBlock GRAY_FENCE = registerColoredFence("gray_fence", GRAY_PLANKS);
	public static final ColoredFenceGateBlock GRAY_FENCE_GATE = registerColoredFenceGate("gray_fence_gate", GRAY_PLANKS);
	public static final ColoredWoodenButtonBlock GRAY_BUTTON = registerColoredButton("gray_button", GRAY_PLANKS);
	public static final ColoredSlabBlock GRAY_SLAB = registerColoredSlab("gray_slab", GRAY_PLANKS);
	public static final BlockFamily GRAY_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(GRAY_PLANKS).stairs(GRAY_STAIRS).pressurePlate(GRAY_PRESSURE_PLATE).fence(GRAY_FENCE).fenceGate(GRAY_FENCE_GATE).button(GRAY_BUTTON).slab(GRAY_SLAB).build());
	
	public static final ColoredPlankBlock GREEN_PLANKS = registerColoredPlanks("green_planks", DyeColor.GREEN);
	public static final ColoredStairsBlock GREEN_STAIRS = registerColoredStairs("green_stairs", GREEN_PLANKS);
	public static final ColoredPressurePlateBlock GREEN_PRESSURE_PLATE = registerColoredPressurePlate("green_pressure_plate", GREEN_PLANKS);
	public static final ColoredFenceBlock GREEN_FENCE = registerColoredFence("green_fence", GREEN_PLANKS);
	public static final ColoredFenceGateBlock GREEN_FENCE_GATE = registerColoredFenceGate("green_fence_gate", GREEN_PLANKS);
	public static final ColoredWoodenButtonBlock GREEN_BUTTON = registerColoredButton("green_button", GREEN_PLANKS);
	public static final ColoredSlabBlock GREEN_SLAB = registerColoredSlab("green_slab", GREEN_PLANKS);
	public static final BlockFamily GREEN_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(GREEN_PLANKS).stairs(GREEN_STAIRS).pressurePlate(GREEN_PRESSURE_PLATE).fence(GREEN_FENCE).fenceGate(GREEN_FENCE_GATE).button(GREEN_BUTTON).slab(GREEN_SLAB).build());
	
	public static final ColoredPlankBlock LIGHT_BLUE_PLANKS = registerColoredPlanks("light_blue_planks", DyeColor.LIGHT_BLUE);
	public static final ColoredStairsBlock LIGHT_BLUE_STAIRS = registerColoredStairs("light_blue_stairs", LIGHT_BLUE_PLANKS);
	public static final ColoredPressurePlateBlock LIGHT_BLUE_PRESSURE_PLATE = registerColoredPressurePlate("light_blue_pressure_plate", LIGHT_BLUE_PLANKS);
	public static final ColoredFenceBlock LIGHT_BLUE_FENCE = registerColoredFence("light_blue_fence", LIGHT_BLUE_PLANKS);
	public static final ColoredFenceGateBlock LIGHT_BLUE_FENCE_GATE = registerColoredFenceGate("light_blue_fence_gate", LIGHT_BLUE_PLANKS);
	public static final ColoredWoodenButtonBlock LIGHT_BLUE_BUTTON = registerColoredButton("light_blue_button", LIGHT_BLUE_PLANKS);
	public static final ColoredSlabBlock LIGHT_BLUE_SLAB = registerColoredSlab("light_blue_slab", LIGHT_BLUE_PLANKS);
	public static final BlockFamily LIGHT_BLUE_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(LIGHT_BLUE_PLANKS).stairs(LIGHT_BLUE_STAIRS).pressurePlate(LIGHT_BLUE_PRESSURE_PLATE).fence(LIGHT_BLUE_FENCE).fenceGate(LIGHT_BLUE_FENCE_GATE).button(LIGHT_BLUE_BUTTON).slab(LIGHT_BLUE_SLAB).build());
	
	public static final ColoredPlankBlock LIGHT_GRAY_PLANKS = registerColoredPlanks("light_gray_planks", DyeColor.LIGHT_GRAY);
	public static final ColoredStairsBlock LIGHT_GRAY_STAIRS = registerColoredStairs("light_gray_stairs", LIGHT_GRAY_PLANKS);
	public static final ColoredPressurePlateBlock LIGHT_GRAY_PRESSURE_PLATE = registerColoredPressurePlate("light_gray_pressure_plate", LIGHT_GRAY_PLANKS);
	public static final ColoredFenceBlock LIGHT_GRAY_FENCE = registerColoredFence("light_gray_fence", LIGHT_GRAY_PLANKS);
	public static final ColoredFenceGateBlock LIGHT_GRAY_FENCE_GATE = registerColoredFenceGate("light_gray_fence_gate", LIGHT_GRAY_PLANKS);
	public static final ColoredWoodenButtonBlock LIGHT_GRAY_BUTTON = registerColoredButton("light_gray_button", LIGHT_GRAY_PLANKS);
	public static final ColoredSlabBlock LIGHT_GRAY_SLAB = registerColoredSlab("light_gray_slab", LIGHT_GRAY_PLANKS);
	public static final BlockFamily LIGHT_GRAY_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(LIGHT_GRAY_PLANKS).stairs(LIGHT_GRAY_STAIRS).pressurePlate(LIGHT_GRAY_PRESSURE_PLATE).fence(LIGHT_GRAY_FENCE).fenceGate(LIGHT_GRAY_FENCE_GATE).button(LIGHT_GRAY_BUTTON).slab(LIGHT_GRAY_SLAB).build());
	
	public static final ColoredPlankBlock LIME_PLANKS = registerColoredPlanks("lime_planks", DyeColor.LIME);
	public static final ColoredStairsBlock LIME_STAIRS = registerColoredStairs("lime_stairs", LIME_PLANKS);
	public static final ColoredPressurePlateBlock LIME_PRESSURE_PLATE = registerColoredPressurePlate("lime_pressure_plate", LIME_PLANKS);
	public static final ColoredFenceBlock LIME_FENCE = registerColoredFence("lime_fence", LIME_PLANKS);
	public static final ColoredFenceGateBlock LIME_FENCE_GATE = registerColoredFenceGate("lime_fence_gate", LIME_PLANKS);
	public static final ColoredWoodenButtonBlock LIME_BUTTON = registerColoredButton("lime_button", LIME_PLANKS);
	public static final ColoredSlabBlock LIME_SLAB = registerColoredSlab("lime_slab", LIME_PLANKS);
	public static final BlockFamily LIME_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(LIME_PLANKS).stairs(LIME_STAIRS).pressurePlate(LIME_PRESSURE_PLATE).fence(LIME_FENCE).fenceGate(LIME_FENCE_GATE).button(LIME_BUTTON).slab(LIME_SLAB).build());
	
	public static final ColoredPlankBlock MAGENTA_PLANKS = registerColoredPlanks("magenta_planks", DyeColor.MAGENTA);
	public static final ColoredStairsBlock MAGENTA_STAIRS = registerColoredStairs("magenta_stairs", MAGENTA_PLANKS);
	public static final ColoredPressurePlateBlock MAGENTA_PRESSURE_PLATE = registerColoredPressurePlate("magenta_pressure_plate", MAGENTA_PLANKS);
	public static final ColoredFenceBlock MAGENTA_FENCE = registerColoredFence("magenta_fence", MAGENTA_PLANKS);
	public static final ColoredFenceGateBlock MAGENTA_FENCE_GATE = registerColoredFenceGate("magenta_fence_gate", MAGENTA_PLANKS);
	public static final ColoredWoodenButtonBlock MAGENTA_BUTTON = registerColoredButton("magenta_button", MAGENTA_PLANKS);
	public static final ColoredSlabBlock MAGENTA_SLAB = registerColoredSlab("magenta_slab", MAGENTA_PLANKS);
	public static final BlockFamily MAGENTA_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(MAGENTA_PLANKS).stairs(MAGENTA_STAIRS).pressurePlate(MAGENTA_PRESSURE_PLATE).fence(MAGENTA_FENCE).fenceGate(MAGENTA_FENCE_GATE).button(MAGENTA_BUTTON).slab(MAGENTA_SLAB).build());
	
	public static final ColoredPlankBlock ORANGE_PLANKS = registerColoredPlanks("orange_planks", DyeColor.ORANGE);
	public static final ColoredStairsBlock ORANGE_STAIRS = registerColoredStairs("orange_stairs", ORANGE_PLANKS);
	public static final ColoredPressurePlateBlock ORANGE_PRESSURE_PLATE = registerColoredPressurePlate("orange_pressure_plate", ORANGE_PLANKS);
	public static final ColoredFenceBlock ORANGE_FENCE = registerColoredFence("orange_fence", ORANGE_PLANKS);
	public static final ColoredFenceGateBlock ORANGE_FENCE_GATE = registerColoredFenceGate("orange_fence_gate", ORANGE_PLANKS);
	public static final ColoredWoodenButtonBlock ORANGE_BUTTON = registerColoredButton("orange_button", ORANGE_PLANKS);
	public static final ColoredSlabBlock ORANGE_SLAB = registerColoredSlab("orange_slab", ORANGE_PLANKS);
	public static final BlockFamily ORANGE_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(ORANGE_PLANKS).stairs(ORANGE_STAIRS).pressurePlate(ORANGE_PRESSURE_PLATE).fence(ORANGE_FENCE).fenceGate(ORANGE_FENCE_GATE).button(ORANGE_BUTTON).slab(ORANGE_SLAB).build());
	
	public static final ColoredPlankBlock PINK_PLANKS = registerColoredPlanks("pink_planks", DyeColor.PINK);
	public static final ColoredStairsBlock PINK_STAIRS = registerColoredStairs("pink_stairs", PINK_PLANKS);
	public static final ColoredPressurePlateBlock PINK_PRESSURE_PLATE = registerColoredPressurePlate("pink_pressure_plate", PINK_PLANKS);
	public static final ColoredFenceBlock PINK_FENCE = registerColoredFence("pink_fence", PINK_PLANKS);
	public static final ColoredFenceGateBlock PINK_FENCE_GATE = registerColoredFenceGate("pink_fence_gate", PINK_PLANKS);
	public static final ColoredWoodenButtonBlock PINK_BUTTON = registerColoredButton("pink_button", PINK_PLANKS);
	public static final ColoredSlabBlock PINK_SLAB = registerColoredSlab("pink_slab", PINK_PLANKS);
	public static final BlockFamily PINK_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(PINK_PLANKS).stairs(PINK_STAIRS).pressurePlate(PINK_PRESSURE_PLATE).fence(PINK_FENCE).fenceGate(PINK_FENCE_GATE).button(PINK_BUTTON).slab(PINK_SLAB).build());
	
	public static final ColoredPlankBlock PURPLE_PLANKS = registerColoredPlanks("purple_planks", DyeColor.PURPLE);
	public static final ColoredStairsBlock PURPLE_STAIRS = registerColoredStairs("purple_stairs", PURPLE_PLANKS);
	public static final ColoredPressurePlateBlock PURPLE_PRESSURE_PLATE = registerColoredPressurePlate("purple_pressure_plate", PURPLE_PLANKS);
	public static final ColoredFenceBlock PURPLE_FENCE = registerColoredFence("purple_fence", PURPLE_PLANKS);
	public static final ColoredFenceGateBlock PURPLE_FENCE_GATE = registerColoredFenceGate("purple_fence_gate", PURPLE_PLANKS);
	public static final ColoredWoodenButtonBlock PURPLE_BUTTON = registerColoredButton("purple_button", PURPLE_PLANKS);
	public static final ColoredSlabBlock PURPLE_SLAB = registerColoredSlab("purple_slab", PURPLE_PLANKS);
	public static final BlockFamily PURPLE_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(PURPLE_PLANKS).stairs(PURPLE_STAIRS).pressurePlate(PURPLE_PRESSURE_PLATE).fence(PURPLE_FENCE).fenceGate(PURPLE_FENCE_GATE).button(PURPLE_BUTTON).slab(PURPLE_SLAB).build());
	
	public static final ColoredPlankBlock RED_PLANKS = registerColoredPlanks("red_planks", DyeColor.RED);
	public static final ColoredStairsBlock RED_STAIRS = registerColoredStairs("red_stairs", RED_PLANKS);
	public static final ColoredPressurePlateBlock RED_PRESSURE_PLATE = registerColoredPressurePlate("red_pressure_plate", RED_PLANKS);
	public static final ColoredFenceBlock RED_FENCE = registerColoredFence("red_fence", RED_PLANKS);
	public static final ColoredFenceGateBlock RED_FENCE_GATE = registerColoredFenceGate("red_fence_gate", RED_PLANKS);
	public static final ColoredWoodenButtonBlock RED_BUTTON = registerColoredButton("red_button", RED_PLANKS);
	public static final ColoredSlabBlock RED_SLAB = registerColoredSlab("red_slab", RED_PLANKS);
	public static final BlockFamily RED_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(RED_PLANKS).stairs(RED_STAIRS).pressurePlate(RED_PRESSURE_PLATE).fence(RED_FENCE).fenceGate(RED_FENCE_GATE).button(RED_BUTTON).slab(RED_SLAB).build());
	
	public static final ColoredPlankBlock WHITE_PLANKS = registerColoredPlanks("white_planks", DyeColor.WHITE);
	public static final ColoredStairsBlock WHITE_STAIRS = registerColoredStairs("white_stairs", WHITE_PLANKS);
	public static final ColoredPressurePlateBlock WHITE_PRESSURE_PLATE = registerColoredPressurePlate("white_pressure_plate", WHITE_PLANKS);
	public static final ColoredFenceBlock WHITE_FENCE = registerColoredFence("white_fence", WHITE_PLANKS);
	public static final ColoredFenceGateBlock WHITE_FENCE_GATE = registerColoredFenceGate("white_fence_gate", WHITE_PLANKS);
	public static final ColoredWoodenButtonBlock WHITE_BUTTON = registerColoredButton("white_button", WHITE_PLANKS);
	public static final ColoredSlabBlock WHITE_SLAB = registerColoredSlab("white_slab", WHITE_PLANKS);
	public static final BlockFamily WHITE_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(WHITE_PLANKS).stairs(WHITE_STAIRS).pressurePlate(WHITE_PRESSURE_PLATE).fence(WHITE_FENCE).fenceGate(WHITE_FENCE_GATE).button(WHITE_BUTTON).slab(WHITE_SLAB).build());
	
	public static final ColoredPlankBlock YELLOW_PLANKS = registerColoredPlanks("yellow_planks", DyeColor.YELLOW);
	public static final ColoredStairsBlock YELLOW_STAIRS = registerColoredStairs("yellow_stairs", YELLOW_PLANKS);
	public static final ColoredPressurePlateBlock YELLOW_PRESSURE_PLATE = registerColoredPressurePlate("yellow_pressure_plate", YELLOW_PLANKS);
	public static final ColoredFenceBlock YELLOW_FENCE = registerColoredFence("yellow_fence", YELLOW_PLANKS);
	public static final ColoredFenceGateBlock YELLOW_FENCE_GATE = registerColoredFenceGate("yellow_fence_gate", YELLOW_PLANKS);
	public static final ColoredWoodenButtonBlock YELLOW_BUTTON = registerColoredButton("yellow_button", YELLOW_PLANKS);
	public static final ColoredSlabBlock YELLOW_SLAB = registerColoredSlab("yellow_slab", YELLOW_PLANKS);
	public static final BlockFamily YELLOW_COLORED_PLANKS_FAMILY = registerBlockFamily(new BlockFamily.Builder(YELLOW_PLANKS).stairs(YELLOW_STAIRS).pressurePlate(YELLOW_PRESSURE_PLATE).fence(YELLOW_FENCE).fenceGate(YELLOW_FENCE_GATE).button(YELLOW_BUTTON).slab(YELLOW_SLAB).build());
	
	//DD FLORA
	public static Settings overgrownBlackslag(MapColor color, BlockSoundGroup soundGroup) {
		return settings(color, soundGroup, BLACKSLAG_HARDNESS, BLACKSLAG_RESISTANCE).ticksRandomly();
	}
	
	public static final Block SAWBLADE_GRASS = new BlackslagVegetationBlock(overgrownBlackslag(MapColor.PALE_YELLOW, BlockSoundGroup.AZALEA_LEAVES));
	public static final Block SHIMMEL = new BlackslagVegetationBlock(overgrownBlackslag(MapColor.TERRACOTTA_GRAY, BlockSoundGroup.WART_BLOCK));
	public static final Block OVERGROWN_BLACKSLAG = new BlackslagVegetationBlock(overgrownBlackslag(MapColor.DARK_GREEN, BlockSoundGroup.VINE).velocityMultiplier(0.925F));
	public static final Block ROTTEN_GROUND = new RottenGroundBlock(Settings.copy(Blocks.MUD).mapColor(MapColor.PALE_PURPLE).sounds(BlockSoundGroup.HONEY).velocityMultiplier(0.775F).jumpVelocityMultiplier(0.9F));
	
	public static final float ASH_STRENGTH = 2F;
	
	public static Settings ash(BlockSoundGroup soundGroup) {
		return settings(MapColor.OFF_WHITE, soundGroup, ASH_STRENGTH, ASH_STRENGTH).requiresTool();
	}
	
	public static final Block ASHEN_BLACKSLAG = register(singleton(blockWithItem("ashen_blackslag", new BlackslagBlock(blackslag(BlockSoundGroup.DEEPSLATE).mapColor(MapColor.OFF_WHITE)), DyeColor.LIGHT_GRAY), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_side", b -> b, "_top", b -> BLACKSLAG, "_top", b -> b, "_top")));
	public static final Block ASH = register(blockWithItem("ash", new AshBlock(ash(BlockSoundGroup.POWDER_SNOW)), DyeColor.GRAY)
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block,
					createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "").upload(block, "", ctx.modelCollector)),
					createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "2").upload(block, "2", ctx.modelCollector)),
					createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "3").upload(block, "3", ctx.modelCollector)),
					createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "4").upload(block, "4", ctx.modelCollector))
			)));
	public static final Block ASH_PILE = register(blockWithItem("ash_pile", new AshPileBlock(ash(BlockSoundGroup.POWDER_SNOW).replaceable().blockVision((state, world, pos) -> state.get(SnowBlock.LAYERS) >= 8).pistonBehavior(PistonBehavior.DESTROY)), DyeColor.LIGHT_GRAY)
			.withBlockModel((ctx, block) -> {
				ctx.registerParentedItemModel(block, ModelIds.getBlockSubModelId(block, "_height2"));
				return VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.LAYERS).registerVariants(height -> {
					Identifier ash = TextureMap.getId(ASH);
					Identifier ash2 = TextureMap.getSubId(ASH, "2");
					Identifier ash3 = TextureMap.getSubId(ASH, "3");
					Identifier ash4 = TextureMap.getSubId(ASH, "4");
					if (height == 8) return List.of(createModelVariant(ash), createModelVariant(ash2), createModelVariant(ash3), createModelVariant(ash4));
					Model layerModel = new Model(Optional.of(ModelIds.getBlockSubModelId(Blocks.SNOW, "_height" + height * 2)), Optional.empty(), TextureKey.PARTICLE, TextureKey.TEXTURE);
					return List.of(
							createModelVariant(layerModel.upload(SpectrumCommon.locate("block/ash_pile_height" + height * 2), TextureMap.all(ash), ctx.modelCollector)),
							createModelVariant(layerModel.upload(SpectrumCommon.locate("block/ash2_pile_height" + height * 2), TextureMap.all(ash2), ctx.modelCollector)),
							createModelVariant(layerModel.upload(SpectrumCommon.locate("block/ash3_pile_height" + height * 2), TextureMap.all(ash3), ctx.modelCollector)),
							createModelVariant(layerModel.upload(SpectrumCommon.locate("block/ash4_pile_height" + height * 2), TextureMap.all(ash4), ctx.modelCollector))
					);
				}));
			}));
	
	public static final Block VARIA_SPROUT = register(cutout(blockWithItem("varia_sprout", new AshFloraBlock(settings(MapColor.WHITE, BlockSoundGroup.NETHER_STEM, 0F).breakInstantly().luminance(state -> 11).offset(OffsetType.XZ).dynamicBounds().noCollision().postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always)), DyeColor.WHITE))
			.withBlockModel((ctx, block) -> {
				ctx.registerItemModel(block);
				return VariantsBlockStateSupplier.create(block,
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "").upload(block, "", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_2").upload(block, "_2", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_3").upload(block, "_3", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_4").upload(block, "_4", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_5").upload(block, "_5", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_6").upload(block, "_6", ctx.modelCollector))
				);
			}));
	
	public static final ToIntFunction<BlockState> LANTERN_LIGHT_PROVIDER = (state -> state.get(RedstoneLampBlock.LIT) ? 15 : 0);
	
	public static FungusBlock registerNoxshroom(String name, RegistryKey<ConfiguredFeature<?, ?>> feature, MapColor mapColor) {
		return register(cutout(blockWithItem(name, new FungusBlock(feature, SHIMMEL, settings(mapColor, BlockSoundGroup.FUNGUS, 0.0F).noCollision()), DyeColor.LIME))
				.withBlockModel((ctx, block) -> {
					ctx.registerItemModel(block);
					return VariantsBlockStateSupplier.create(block,
							createModelVariant(SpectrumTexturedModels.cross(b -> b, "_type_1").upload(block, "_type_1", ctx.modelCollector)),
							createModelVariant(SpectrumTexturedModels.cross(b -> b, "_type_2").upload(block, "_type_2", ctx.modelCollector)),
							createModelVariant(SpectrumTexturedModels.cross(b -> b, "_type_3").upload(block, "_type_3", ctx.modelCollector))
					);
				}));
	}
	
	public static final FungusBlock SLATE_NOXSHROOM = registerNoxshroom("slate_noxshroom", SpectrumConfiguredFeatures.SLATE_NOXFUNGUS, MapColor.GRAY);
	public static final FungusBlock EBONY_NOXSHROOM = registerNoxshroom("ebony_noxshroom", SpectrumConfiguredFeatures.EBONY_NOXFUNGUS, MapColor.TERRACOTTA_BLACK);
	public static final FungusBlock IVORY_NOXSHROOM = registerNoxshroom("ivory_noxshroom", SpectrumConfiguredFeatures.IVORY_NOXFUNGUS, MapColor.OFF_WHITE);
	public static final FungusBlock CHESTNUT_NOXSHROOM = registerNoxshroom("chestnut_noxshroom", SpectrumConfiguredFeatures.CHESTNUT_NOXFUNGUS, MapColor.DULL_RED);
	
	public static final FlowerPotBlock POTTED_SLATE_NOXSHROOM = register(pottedPlant(block("potted_slate_noxshroom", new FlowerPotBlock(SLATE_NOXSHROOM, pottedPlant())), false));
	public static final FlowerPotBlock POTTED_EBONY_NOXSHROOM = register(pottedPlant(block("potted_ebony_noxshroom", new FlowerPotBlock(EBONY_NOXSHROOM, pottedPlant())), false));
	public static final FlowerPotBlock POTTED_IVORY_NOXSHROOM = register(pottedPlant(block("potted_ivory_noxshroom", new FlowerPotBlock(IVORY_NOXSHROOM, pottedPlant())), false));
	public static final FlowerPotBlock POTTED_CHESTNUT_NOXSHROOM = register(pottedPlant(block("potted_chestnut_noxshroom", new FlowerPotBlock(CHESTNUT_NOXSHROOM, pottedPlant())), false));
	
	public static Settings noxcap(MapColor color) {
		return settings(color, BlockSoundGroup.NETHER_STEM, 4.0F).instrument(NoteBlockInstrument.BASS);
	}
	
	public static PillarBlock registerNoxwoodLightBlock(String name, Block gillsBlock, MapColor color) {
		return register(translucent(axisRotated(blockWithItem(name, new PillarBlock(noxcap(color).luminance(state -> 15)), DyeColor.LIME), TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopInside(b, "", b, "_top", gillsBlock, ""), SpectrumModels.MULTILAYER_LIGHT))));
	}
	
	public static <T extends AmphoraBlock> T registerNoxwoodAmphora(String name, T amphoraBlock, Block lightBlock, DyeColor color) {
		return register(blockWithItem(name, amphoraBlock, color)
				.withBlockModel((ctx, block) -> {
					Identifier base = SpectrumTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top", b -> lightBlock, "_top").upload(block, ctx.modelCollector);
					Identifier open = SpectrumTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top_open", b -> lightBlock, "_top").upload(block, "_open", ctx.modelCollector);
					return VariantsBlockStateSupplier.create(block).coordinate(createUpDefaultFacingVariantMap()).coordinate(createBooleanModelMap(Properties.OPEN, open, base));
				}));
	}
	
	public static <T extends Block> T registerNoxwoodLamp(String name, T redstoneLampBlock, DyeColor color) {
		return register(blockWithItem(name, redstoneLampBlock, color)
				.withBlockModel((ctx, block) -> {
					Identifier off = SpectrumTexturedModels.cubeAll(b -> b, "_off").upload(block, "_off", ctx.modelCollector);
					Identifier on = SpectrumTexturedModels.cubeAll(b -> b, "_on").upload(block, "_on", ctx.modelCollector);
					ctx.registerParentedItemModel(block, ModelIds.getBlockSubModelId(block, "_off"));
					return VariantsBlockStateSupplier.create(block).coordinate(createBooleanModelMap(Properties.LIT, on, off));
				}));
	}
	
	public static <T extends FlexLanternBlock> T registerNoxwoodLantern(String name, T flexLanternBlock, DyeColor color) {
		return register(cutout(blockWithItem(name, flexLanternBlock, color))
				.withBlockModel((ctx, block) -> {
					Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), SpectrumTextureMaps.layer0(block.asItem(), "_item"), ctx.modelCollector);
					return VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.HANGING, DiagonalBlock.DIAGONAL, FlexLanternBlock.TALL).register((hanging, diagonal, tall) -> {
						String suffix = (hanging ? "_hanging" : "") + (diagonal ? "_diagonal" : "") + (tall ? "_tall" : "_small");
						return createModelVariant(SpectrumModels.noxwoodLantern(suffix).upload(block, suffix, TextureMap.all(block), ctx.modelCollector));
					}));
				}));
	}
	
	private static final int NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS = 30;
	
	public static final PillarBlock STRIPPED_SLATE_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_slate_noxcap_stem", new PillarBlock(noxcap(MapColor.GRAY)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block STRIPPED_SLATE_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_slate_noxcap_hyphae", new PillarBlock(noxcap(MapColor.GRAY)), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> STRIPPED_SLATE_NOXCAP_STEM, "", b -> STRIPPED_SLATE_NOXCAP_STEM, "")));
	public static final PillarBlock SLATE_NOXCAP_STEM = register(axisRotated(blockWithItem("slate_noxcap_stem", new StrippingLootPillarBlock(noxcap(MapColor.GRAY), STRIPPED_SLATE_NOXCAP_STEM, SpectrumLootTables.SLATE_NOXCAP_STRIPPING), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block SLATE_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("slate_noxcap_hyphae", new StrippingLootPillarBlock(noxcap(MapColor.GRAY), STRIPPED_SLATE_NOXCAP_HYPHAE, SpectrumLootTables.SLATE_NOXCAP_STRIPPING), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> SLATE_NOXCAP_STEM, "", b -> SLATE_NOXCAP_STEM, "")));
	public static final Block SLATE_NOXCAP_BLOCK = register(singleton(blockWithItem("slate_noxcap_block", new Block(noxcap(MapColor.GRAY)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final PillarBlock SLATE_NOXCAP_GILLS = register(axisRotated(blockWithItem("slate_noxcap_gills", new PillarBlock(noxcap(MapColor.DIAMOND_BLUE).luminance(state -> 9).emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	
	public static final Block SLATE_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("slate_noxwood_pillar", new PillarBlock(noxcap(MapColor.GRAY)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block SLATE_NOXWOOD_LAMP = registerNoxwoodLamp("slate_noxwood_lamp", new RedstoneLampBlock(noxcap(MapColor.GRAY).luminance(LANTERN_LIGHT_PROVIDER)), DyeColor.LIME);
	public static final Block SLATE_NOXWOOD_LIGHT = registerNoxwoodLightBlock("slate_noxwood_light", SLATE_NOXCAP_GILLS, MapColor.GRAY);
	public static final Block SLATE_NOXWOOD_AMPHORA = registerNoxwoodAmphora("slate_noxwood_amphora", new AmphoraBlock(noxcap(MapColor.GRAY)), SLATE_NOXWOOD_LIGHT, DyeColor.LIME);
	public static final Block SLATE_NOXWOOD_LANTERN = registerNoxwoodLantern("slate_noxwood_lantern", new FlexLanternBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(s -> 13).pistonBehavior(PistonBehavior.DESTROY)), DyeColor.LIME);
	
	public static final Block SLATE_NOXWOOD_PLANKS = register(blockWithItem("slate_noxwood_planks", new Block(noxcap(MapColor.GRAY)), DyeColor.LIME));
	public static final StairsBlock SLATE_NOXWOOD_STAIRS = register(blockWithItem("slate_noxwood_stairs", new StairsBlock(SLATE_NOXWOOD_PLANKS.getDefaultState(), noxcap(MapColor.GRAY)), DyeColor.LIME));
	public static final SlabBlock SLATE_NOXWOOD_SLAB = register(blockWithItem("slate_noxwood_slab", new SlabBlock(noxcap(MapColor.GRAY)), DyeColor.LIME));
	public static final FenceBlock SLATE_NOXWOOD_FENCE = register(blockWithItem("slate_noxwood_fence", new FenceBlock(noxcap(MapColor.GRAY)), DyeColor.LIME));
	public static final FenceGateBlock SLATE_NOXWOOD_FENCE_GATE = register(blockWithItem("slate_noxwood_fence_gate", new FenceGateBlock(SpectrumWoodTypes.SLATE_NOXWOOD, noxcap(MapColor.GRAY)), DyeColor.LIME));
	public static final Block SLATE_NOXWOOD_DOOR = register(cutout(blockWithItem("slate_noxwood_door", new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.GRAY)), DyeColor.LIME)));
	public static final Block SLATE_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("slate_noxwood_trapdoor", new TrapdoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.GRAY)), DyeColor.LIME)));
	public static final Block SLATE_NOXWOOD_BUTTON = register(blockWithItem("slate_noxwood_button", new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.GRAY)), DyeColor.LIME));
	public static final Block SLATE_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("slate_noxwood_pressure_plate", new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.GRAY)), DyeColor.LIME));
	public static final BlockFamily SLATE_NOXWOOD_FAMILY = registerBlockFamily(new BlockFamily.Builder(SLATE_NOXWOOD_PLANKS).stairs(SLATE_NOXWOOD_STAIRS).slab(SLATE_NOXWOOD_SLAB).fence(SLATE_NOXWOOD_FENCE).fenceGate(SLATE_NOXWOOD_FENCE_GATE).door(SLATE_NOXWOOD_DOOR).trapdoor(SLATE_NOXWOOD_TRAPDOOR).button(SLATE_NOXWOOD_BUTTON).pressurePlate(SLATE_NOXWOOD_PRESSURE_PLATE).build());
	
	public static final PillarBlock STRIPPED_EBONY_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_ebony_noxcap_stem", new PillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block STRIPPED_EBONY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_ebony_noxcap_hyphae", new PillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> STRIPPED_EBONY_NOXCAP_STEM, "", b -> STRIPPED_EBONY_NOXCAP_STEM, "")));
	public static final PillarBlock EBONY_NOXCAP_STEM = register(axisRotated(blockWithItem("ebony_noxcap_stem", new StrippingLootPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK), STRIPPED_EBONY_NOXCAP_STEM, SpectrumLootTables.EBONY_NOXCAP_STRIPPING), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block EBONY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("ebony_noxcap_hyphae", new StrippingLootPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK), STRIPPED_EBONY_NOXCAP_HYPHAE, SpectrumLootTables.EBONY_NOXCAP_STRIPPING), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> EBONY_NOXCAP_STEM, "", b -> EBONY_NOXCAP_STEM, "")));
	public static final Block EBONY_NOXCAP_BLOCK = register(singleton(blockWithItem("ebony_noxcap_block", new Block(noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final PillarBlock EBONY_NOXCAP_GILLS = register(axisRotated(blockWithItem("ebony_noxcap_gills", new PillarBlock(noxcap(MapColor.DIAMOND_BLUE).luminance(state -> 9).emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	
	public static final Block EBONY_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("ebony_noxwood_pillar", new PillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block EBONY_NOXWOOD_LAMP = registerNoxwoodLamp("ebony_noxwood_lamp", new RedstoneLampBlock(noxcap(MapColor.TERRACOTTA_BLACK).luminance(LANTERN_LIGHT_PROVIDER)), DyeColor.LIME);
	public static final Block EBONY_NOXWOOD_LIGHT = registerNoxwoodLightBlock("ebony_noxwood_light", EBONY_NOXCAP_GILLS, MapColor.TERRACOTTA_BLACK);
	public static final Block EBONY_NOXWOOD_AMPHORA = registerNoxwoodAmphora("ebony_noxwood_amphora", new AmphoraBlock(noxcap(MapColor.TERRACOTTA_BLACK)), EBONY_NOXWOOD_LIGHT, DyeColor.LIME);
	public static final Block EBONY_NOXWOOD_LANTERN = registerNoxwoodLantern("ebony_noxwood_lantern", new FlexLanternBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(s -> 13).pistonBehavior(PistonBehavior.DESTROY)), DyeColor.LIME);
	
	public static final Block EBONY_NOXWOOD_PLANKS = register(blockWithItem("ebony_noxwood_planks", new Block(noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME));
	public static final StairsBlock EBONY_NOXWOOD_STAIRS = register(blockWithItem("ebony_noxwood_stairs", new StairsBlock(EBONY_NOXWOOD_PLANKS.getDefaultState(), noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME));
	public static final SlabBlock EBONY_NOXWOOD_SLAB = register(blockWithItem("ebony_noxwood_slab", new SlabBlock(noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME));
	public static final FenceBlock EBONY_NOXWOOD_FENCE = register(blockWithItem("ebony_noxwood_fence", new FenceBlock(noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME));
	public static final FenceGateBlock EBONY_NOXWOOD_FENCE_GATE = register(blockWithItem("ebony_noxwood_fence_gate", new FenceGateBlock(SpectrumWoodTypes.EBONY_NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME));
	public static final Block EBONY_NOXWOOD_DOOR = register(cutout(blockWithItem("ebony_noxwood_door", new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME)));
	public static final Block EBONY_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("ebony_noxwood_trapdoor", new TrapdoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME)));
	public static final Block EBONY_NOXWOOD_BUTTON = register(blockWithItem("ebony_noxwood_button", new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME));
	public static final Block EBONY_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("ebony_noxwood_pressure_plate", new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), DyeColor.LIME));
	public static final BlockFamily EBONY_NOXWOOD_FAMILY = registerBlockFamily(new BlockFamily.Builder(EBONY_NOXWOOD_PLANKS).stairs(EBONY_NOXWOOD_STAIRS).slab(EBONY_NOXWOOD_SLAB).fence(EBONY_NOXWOOD_FENCE).fenceGate(EBONY_NOXWOOD_FENCE_GATE).door(EBONY_NOXWOOD_DOOR).trapdoor(EBONY_NOXWOOD_TRAPDOOR).button(EBONY_NOXWOOD_BUTTON).pressurePlate(EBONY_NOXWOOD_PRESSURE_PLATE).build());
	
	public static final PillarBlock STRIPPED_IVORY_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_ivory_noxcap_stem", new PillarBlock(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block STRIPPED_IVORY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_ivory_noxcap_hyphae", new PillarBlock(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> STRIPPED_IVORY_NOXCAP_STEM, "", b -> STRIPPED_IVORY_NOXCAP_STEM, "")));
	public static final PillarBlock IVORY_NOXCAP_STEM = register(axisRotated(blockWithItem("ivory_noxcap_stem", new StrippingLootPillarBlock(noxcap(MapColor.OFF_WHITE), STRIPPED_IVORY_NOXCAP_STEM, SpectrumLootTables.IVORY_NOXCAP_STRIPPING), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block IVORY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("ivory_noxcap_hyphae", new StrippingLootPillarBlock(noxcap(MapColor.OFF_WHITE), STRIPPED_IVORY_NOXCAP_HYPHAE, SpectrumLootTables.IVORY_NOXCAP_STRIPPING), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> IVORY_NOXCAP_STEM, "", b -> IVORY_NOXCAP_STEM, "")));
	public static final Block IVORY_NOXCAP_BLOCK = register(singleton(blockWithItem("ivory_noxcap_block", new Block(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final PillarBlock IVORY_NOXCAP_GILLS = register(axisRotated(blockWithItem("ivory_noxcap_gills", new PillarBlock(noxcap(MapColor.DIAMOND_BLUE).luminance(state -> 9).emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	
	public static final Block IVORY_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("ivory_noxwood_pillar", new PillarBlock(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block IVORY_NOXWOOD_LAMP = registerNoxwoodLamp("ivory_noxwood_lamp", new RedstoneLampBlock(noxcap(MapColor.OFF_WHITE).luminance(LANTERN_LIGHT_PROVIDER)), DyeColor.LIME);
	public static final Block IVORY_NOXWOOD_LIGHT = registerNoxwoodLightBlock("ivory_noxwood_light", IVORY_NOXCAP_GILLS, MapColor.OFF_WHITE);
	public static final Block IVORY_NOXWOOD_AMPHORA = registerNoxwoodAmphora("ivory_noxwood_amphora", new AmphoraBlock(noxcap(MapColor.OFF_WHITE)), IVORY_NOXWOOD_LIGHT, DyeColor.LIME);
	public static final Block IVORY_NOXWOOD_LANTERN = registerNoxwoodLantern("ivory_noxwood_lantern", new FlexLanternBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(s -> 13).pistonBehavior(PistonBehavior.DESTROY)), DyeColor.LIME);
	
	public static final Block IVORY_NOXWOOD_PLANKS = register(blockWithItem("ivory_noxwood_planks", new Block(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME));
	public static final StairsBlock IVORY_NOXWOOD_STAIRS = register(blockWithItem("ivory_noxwood_stairs", new StairsBlock(IVORY_NOXWOOD_PLANKS.getDefaultState(), noxcap(MapColor.OFF_WHITE)), DyeColor.LIME));
	public static final SlabBlock IVORY_NOXWOOD_SLAB = register(blockWithItem("ivory_noxwood_slab", new SlabBlock(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME));
	public static final FenceBlock IVORY_NOXWOOD_FENCE = register(blockWithItem("ivory_noxwood_fence", new FenceBlock(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME));
	public static final FenceGateBlock IVORY_NOXWOOD_FENCE_GATE = register(blockWithItem("ivory_noxwood_fence_gate", new FenceGateBlock(SpectrumWoodTypes.CHESTNUT_NOXWOOD, noxcap(MapColor.OFF_WHITE)), DyeColor.LIME));
	public static final Block IVORY_NOXWOOD_DOOR = register(cutout(blockWithItem("ivory_noxwood_door", new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.OFF_WHITE)), DyeColor.LIME)));
	public static final Block IVORY_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("ivory_noxwood_trapdoor", new TrapdoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.OFF_WHITE)), DyeColor.LIME)));
	public static final Block IVORY_NOXWOOD_BUTTON = register(blockWithItem("ivory_noxwood_button", new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.OFF_WHITE)), DyeColor.LIME));
	public static final Block IVORY_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("ivory_noxwood_pressure_plate", new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.OFF_WHITE)), DyeColor.LIME));
	public static final BlockFamily IVORY_NOXWOOD_FAMILY = registerBlockFamily(new BlockFamily.Builder(IVORY_NOXWOOD_PLANKS).stairs(IVORY_NOXWOOD_STAIRS).slab(IVORY_NOXWOOD_SLAB).fence(IVORY_NOXWOOD_FENCE).fenceGate(IVORY_NOXWOOD_FENCE_GATE).door(IVORY_NOXWOOD_DOOR).trapdoor(IVORY_NOXWOOD_TRAPDOOR).button(IVORY_NOXWOOD_BUTTON).pressurePlate(IVORY_NOXWOOD_PRESSURE_PLATE).build());
	
	public static final PillarBlock STRIPPED_CHESTNUT_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_chestnut_noxcap_stem", new PillarBlock(noxcap(MapColor.DULL_RED)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block STRIPPED_CHESTNUT_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_chestnut_noxcap_hyphae", new PillarBlock(noxcap(MapColor.OFF_WHITE)), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> STRIPPED_CHESTNUT_NOXCAP_STEM, "", b -> STRIPPED_CHESTNUT_NOXCAP_STEM, "")));
	public static final PillarBlock CHESTNUT_NOXCAP_STEM = register(axisRotated(blockWithItem("chestnut_noxcap_stem", new StrippingLootPillarBlock(noxcap(MapColor.DULL_RED), STRIPPED_CHESTNUT_NOXCAP_STEM, SpectrumLootTables.CHESTNUT_NOXCAP_STRIPPING), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block CHESTNUT_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("chestnut_noxcap_hyphae", new StrippingLootPillarBlock(noxcap(MapColor.OFF_WHITE), STRIPPED_CHESTNUT_NOXCAP_HYPHAE, SpectrumLootTables.CHESTNUT_NOXCAP_STRIPPING), DyeColor.LIME), SpectrumTexturedModels.cubeColumn(b -> CHESTNUT_NOXCAP_STEM, "", b -> CHESTNUT_NOXCAP_STEM, "")));
	public static final Block CHESTNUT_NOXCAP_BLOCK = register(singleton(blockWithItem("chestnut_noxcap_block", new Block(noxcap(MapColor.DULL_RED)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final PillarBlock CHESTNUT_NOXCAP_GILLS = register(axisRotated(blockWithItem("chestnut_noxcap_gills", new PillarBlock(noxcap(MapColor.DIAMOND_BLUE).luminance(state -> 9).emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	
	public static final Block CHESTNUT_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("chestnut_noxwood_pillar", new PillarBlock(noxcap(MapColor.DULL_RED)), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block CHESTNUT_NOXWOOD_LAMP = registerNoxwoodLamp("chestnut_noxwood_lamp", new RedstoneLampBlock(noxcap(MapColor.DULL_RED).luminance(LANTERN_LIGHT_PROVIDER)), DyeColor.LIME);
	public static final Block CHESTNUT_NOXWOOD_LIGHT = registerNoxwoodLightBlock("chestnut_noxwood_light", CHESTNUT_NOXCAP_GILLS, MapColor.DULL_RED);
	public static final Block CHESTNUT_NOXWOOD_AMPHORA = registerNoxwoodAmphora("chestnut_noxwood_amphora", new AmphoraBlock(noxcap(MapColor.DULL_RED)), CHESTNUT_NOXWOOD_LIGHT, DyeColor.LIME);
	public static final Block CHESTNUT_NOXWOOD_LANTERN = registerNoxwoodLantern("chestnut_noxwood_lantern", new FlexLanternBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(s -> 13).pistonBehavior(PistonBehavior.DESTROY)), DyeColor.LIME);
	
	public static final Block CHESTNUT_NOXWOOD_PLANKS = register(blockWithItem("chestnut_noxwood_planks", new Block(noxcap(MapColor.DULL_RED)), DyeColor.LIME));
	public static final StairsBlock CHESTNUT_NOXWOOD_STAIRS = register(blockWithItem("chestnut_noxwood_stairs", new StairsBlock(CHESTNUT_NOXWOOD_PLANKS.getDefaultState(), noxcap(MapColor.DULL_RED)), DyeColor.LIME));
	public static final SlabBlock CHESTNUT_NOXWOOD_SLAB = register(blockWithItem("chestnut_noxwood_slab", new SlabBlock(noxcap(MapColor.DULL_RED)), DyeColor.LIME));
	public static final FenceBlock CHESTNUT_NOXWOOD_FENCE = register(blockWithItem("chestnut_noxwood_fence", new FenceBlock(noxcap(MapColor.DULL_RED)), DyeColor.LIME));
	public static final FenceGateBlock CHESTNUT_NOXWOOD_FENCE_GATE = register(blockWithItem("chestnut_noxwood_fence_gate", new FenceGateBlock(SpectrumWoodTypes.IVORY_NOXWOOD, noxcap(MapColor.DULL_RED)), DyeColor.LIME));
	public static final Block CHESTNUT_NOXWOOD_DOOR = register(cutout(blockWithItem("chestnut_noxwood_door", new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.DULL_RED)), DyeColor.LIME)));
	public static final Block CHESTNUT_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("chestnut_noxwood_trapdoor", new TrapdoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.DULL_RED)), DyeColor.LIME)));
	public static final Block CHESTNUT_NOXWOOD_BUTTON = register(blockWithItem("chestnut_noxwood_button", new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.DULL_RED)), DyeColor.LIME));
	public static final Block CHESTNUT_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("chestnut_noxwood_pressure_plate", new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.DULL_RED)), DyeColor.LIME));
	public static final BlockFamily CHESTNUT_NOXWOOD_FAMILY = registerBlockFamily(new BlockFamily.Builder(CHESTNUT_NOXWOOD_PLANKS).stairs(CHESTNUT_NOXWOOD_STAIRS).slab(CHESTNUT_NOXWOOD_SLAB).fence(CHESTNUT_NOXWOOD_FENCE).fenceGate(CHESTNUT_NOXWOOD_FENCE_GATE).door(CHESTNUT_NOXWOOD_DOOR).trapdoor(CHESTNUT_NOXWOOD_TRAPDOOR).button(CHESTNUT_NOXWOOD_BUTTON).pressurePlate(CHESTNUT_NOXWOOD_PRESSURE_PLATE).build());
	
	public static Settings galaWood(MapColor color) {
		return settings(color, BlockSoundGroup.CHERRY_WOOD, 30.0F).instrument(NoteBlockInstrument.BASS).burnable();
	}
	
	public static final WeepingGalaSprigBlock WEEPING_GALA_SPRIG = register(tintableCross(blockWithItem("weeping_gala_sprig", new WeepingGalaSprigBlock(copyWithMapColor(OAK_SAPLING, MapColor.BRIGHT_TEAL)), DyeColor.LIME), false)
			.withItemModel((ctx, block) -> Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block.asItem()), ctx.writer)));
	public static final FlowerPotBlock POTTED_WEEPING_GALA_SPRIG = register(pottedPlant(block("potted_weeping_gala_sprig", new FlowerPotBlock(WEEPING_GALA_SPRIG, pottedPlant())), false));
	
	public static final Block WEEPING_GALA_LEAVES = register(singleton(blockWithItem("weeping_gala_leaves", new LeavesBlock(copyWithMapColor(OAK_LEAVES, MapColor.BRIGHT_TEAL)), DyeColor.LIME), TexturedModel.LEAVES));
	public static final Block WEEPING_GALA_LOG = register(log(blockWithItem("weeping_gala_log", new PillarBlock(galaWood(MapColor.BROWN)), DyeColor.LIME)));
	public static final Block STRIPPED_WEEPING_GALA_LOG = register(log(blockWithItem("stripped_weeping_gala_log", new PillarBlock(galaWood(MapColor.BROWN)), DyeColor.LIME)));
	public static final Block WEEPING_GALA_WOOD = register(wood(blockWithItem("weeping_gala_wood", new PillarBlock(galaWood(MapColor.BROWN)), DyeColor.LIME), WEEPING_GALA_LOG));
	public static final Block STRIPPED_WEEPING_GALA_WOOD = register(wood(blockWithItem("stripped_weeping_gala_wood", new PillarBlock(galaWood(MapColor.BROWN)), DyeColor.LIME), STRIPPED_WEEPING_GALA_LOG));
	
	public static final Block WEEPING_GALA_FRONDS = register(tintableCross(block("weeping_gala_fronds", new WeepingGalaFrondsBlock(AbstractBlock.Settings.copy(WEEPING_GALA_LEAVES).noCollision())), false));
	public static final Block WEEPING_GALA_FRONDS_PLANT = register(cutout(block("weeping_gala_fronds_plant", new WeepingGalaFrondsTipBlock(AbstractBlock.Settings.copy(WEEPING_GALA_LEAVES).noCollision().luminance(s -> s.get(WeepingGalaFrondsTipBlock.FORM).getLuminance()))))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(WeepingGalaFrondsTipBlock.FORM)
					.register(WeepingGalaFrondsTipBlock.Form.TIP, createModelVariant(SpectrumTexturedModels.cross(b -> WEEPING_GALA_FRONDS, "_tip").upload(block, ctx.modelCollector)))
					.register(WeepingGalaFrondsTipBlock.Form.SPRIG, createModelVariant(SpectrumTexturedModels.cross(b -> WEEPING_GALA_FRONDS, "_sprig").upload(block, "_sprig", ctx.modelCollector)))
					.register(WeepingGalaFrondsTipBlock.Form.RESIN, createModelVariant(SpectrumTexturedModels.cross(b -> WEEPING_GALA_FRONDS, "_sprig_resin").upload(block, "_resin", ctx.modelCollector))))));
	
	public static final BlockSetType GALA_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.CHERRY).build(SpectrumCommon.locate("gala"));
	public static final WoodType GALA_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.CHERRY).build(SpectrumCommon.locate("gala"), GALA_BLOCK_SET_TYPE);
	
	public static final Block WEEPING_GALA_PLANKS = register(blockWithItem("weeping_gala_planks", new Block(galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final Block WEEPING_GALA_STAIRS = register(blockWithItem("weeping_gala_stairs", new StairsBlock(WEEPING_GALA_PLANKS.getDefaultState(), galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final Block WEEPING_GALA_SLAB = register(blockWithItem("weeping_gala_slab", new SlabBlock(galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final Block WEEPING_GALA_FENCE = register(blockWithItem("weeping_gala_fence", new FenceBlock(galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final Block WEEPING_GALA_FENCE_GATE = register(blockWithItem("weeping_gala_fence_gate", new FenceGateBlock(GALA_WOOD_TYPE, galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final Block WEEPING_GALA_DOOR = register(blockWithItem("weeping_gala_door", new DoorBlock(GALA_BLOCK_SET_TYPE, galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final Block WEEPING_GALA_TRAPDOOR = register(blockWithItem("weeping_gala_trapdoor", new TrapdoorBlock(GALA_BLOCK_SET_TYPE, galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final Block WEEPING_GALA_BUTTON = register(blockWithItem("weeping_gala_button", Blocks.createWoodenButtonBlock(GALA_BLOCK_SET_TYPE), DyeColor.LIME));
	public static final Block WEEPING_GALA_PRESSURE_PLATE = register(blockWithItem("weeping_gala_pressure_plate", new PressurePlateBlock(GALA_BLOCK_SET_TYPE, galaWood(MapColor.BROWN)), DyeColor.LIME));
	public static final BlockFamily WEEPING_GALA_FAMILY = registerBlockFamily(new BlockFamily.Builder(WEEPING_GALA_PLANKS).stairs(WEEPING_GALA_STAIRS).slab(WEEPING_GALA_SLAB).fence(WEEPING_GALA_FENCE).fenceGate(WEEPING_GALA_FENCE_GATE).door(WEEPING_GALA_DOOR).trapdoor(WEEPING_GALA_TRAPDOOR).button(WEEPING_GALA_BUTTON).pressurePlate(WEEPING_GALA_PRESSURE_PLATE).build());
	
	public static final Block WEEPING_GALA_PILLAR = new PillarBlock(galaWood(MapColor.BROWN));
	public static final Block WEEPING_GALA_BARREL = new BarrelBlock(galaWood(MapColor.BROWN));
	public static final Block WEEPING_GALA_AMPHORA = new AmphoraBlock(galaWood(MapColor.BROWN));
	public static final Block WEEPING_GALA_LANTERN = new FlexLanternBlock(galaWood(MapColor.BROWN).luminance(state -> 13).pistonBehavior(PistonBehavior.DESTROY));
	public static final Block WEEPING_GALA_LAMP = new RedstoneLampBlock(galaWood(MapColor.BROWN).luminance(LANTERN_LIGHT_PROVIDER));
	public static final Block WEEPING_GALA_LIGHT = new PillarBlock(galaWood(MapColor.BROWN).luminance(state -> 15));
	
	public static Settings basalMarble() {
		return settings(MapColor.GRAY, BlockSoundGroup.DRIPSTONE_BLOCK, 8.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresTool();
	}
	
	public static final Block BASAL_MARBLE = register(axisRotated(blockWithItem("basal_marble", new PillarBlock(basalMarble()), DyeColor.BROWN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BASAL_MARBLE_STAIRS = register(blockWithItem("basal_marble_stairs", new StairsBlock(BASAL_MARBLE.getDefaultState(), basalMarble()), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_SLAB = register(blockWithItem("basal_marble_slab", new SlabBlock(basalMarble()), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_WALL = register(blockWithItem("basal_marble_wall", new WallBlock(basalMarble()), DyeColor.BROWN));
	public static final BlockFamily BASAL_MARBLE_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(BASAL_MARBLE).stairs(BASAL_MARBLE_STAIRS).slab(BASAL_MARBLE_SLAB).wall(BASAL_MARBLE_WALL).build(), TexturedModel.CUBE_ALL);
	
	public static final Block BASAL_MARBLE_PILLAR = register(axisRotated(blockWithItem("basal_marble_pillar", new PillarBlock(basalMarble()), DyeColor.BROWN), TexturedModel.CUBE_COLUMN));
	
	public static final Block POLISHED_BASAL_MARBLE = register(defaultFacingUp(blockWithItem("polished_basal_marble", new SpectrumFacingBlock(basalMarble()), DyeColor.BROWN), TexturedModel.CUBE_BOTTOM_TOP));
	public static final Block POLISHED_BASAL_MARBLE_STAIRS = register(blockWithItem("polished_basal_marble_stairs", new StairsBlock(POLISHED_BASAL_MARBLE.getDefaultState(), basalMarble()), DyeColor.BROWN));
	public static final Block POLISHED_BASAL_MARBLE_SLAB = register(blockWithItem("polished_basal_marble_slab", new SlabBlock(basalMarble()), DyeColor.BROWN));
	public static final Block POLISHED_BASAL_MARBLE_WALL = register(blockWithItem("polished_basal_marble_wall", new WallBlock(basalMarble()), DyeColor.BROWN));
	public static final BlockFamily POLISHED_BASAL_MARBLE_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(POLISHED_BASAL_MARBLE).stairs(POLISHED_BASAL_MARBLE_STAIRS).slab(POLISHED_BASAL_MARBLE_SLAB).wall(POLISHED_BASAL_MARBLE_WALL).build(), TexturedModel.makeFactory(b -> SpectrumTextureMaps.sideTopBottomWall(b, "_side", b, "_top", b, "_bottom", b, "_side"), Models.CUBE_BOTTOM_TOP));
	
	public static final Block BASAL_MARBLE_TILES = register(blockWithItem("basal_marble_tiles", new Block(basalMarble()), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_TILE_STAIRS = register(blockWithItem("basal_marble_tile_stairs", new StairsBlock(BASAL_MARBLE_TILES.getDefaultState(), Settings.copy(BASAL_MARBLE_TILES)), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_TILE_SLAB = register(blockWithItem("basal_marble_tile_slab", new SlabBlock(Settings.copy(BASAL_MARBLE_TILES)), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_TILE_WALL = register(blockWithItem("basal_marble_tile_wall", new WallBlock(Settings.copy(BASAL_MARBLE_TILES)), DyeColor.BROWN));
	public static final BlockFamily BASAL_MARBLE_TILE_FAMILY = registerBlockFamily(new BlockFamily.Builder(BASAL_MARBLE_TILES).stairs(BASAL_MARBLE_TILE_STAIRS).slab(BASAL_MARBLE_TILE_SLAB).wall(BASAL_MARBLE_TILE_WALL).build());
	
	public static final Block BASAL_MARBLE_BRICKS = register(blockWithItem("basal_marble_bricks", new Block(basalMarble()), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_BRICK_STAIRS = register(blockWithItem("basal_marble_brick_stairs", new StairsBlock(BASAL_MARBLE_BRICKS.getDefaultState(), Settings.copy(BASAL_MARBLE_BRICKS)), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_BRICK_SLAB = register(blockWithItem("basal_marble_brick_slab", new SlabBlock(Settings.copy(BASAL_MARBLE_BRICKS)), DyeColor.BROWN));
	public static final Block BASAL_MARBLE_BRICK_WALL = register(blockWithItem("basal_marble_brick_wall", new WallBlock(Settings.copy(BASAL_MARBLE_BRICKS)), DyeColor.BROWN));
	public static final BlockFamily BASAL_MARBLE_BRICK_FAMILY = registerBlockFamily(new BlockFamily.Builder(BASAL_MARBLE_BRICKS).stairs(BASAL_MARBLE_BRICK_STAIRS).slab(BASAL_MARBLE_BRICK_SLAB).wall(BASAL_MARBLE_BRICK_WALL).build());
	
	public static final Block LONGING_CHIMERA = register(blockWithItem("longing_chimera", new GrotesqueBlock(basalMarble().nonOpaque(), 12, 15, "block.spectrum.longing_chimera.tooltip"), DyeColor.BROWN)
			.withBlockModel((ctx, block) -> createVariantsSupplier(block, ModelIds.getBlockModelId(block)).coordinate(createNorthDefaultHorizontalRotationStates())));
	
	public static SmallDragonjagBlock registerSmallDragonjagBlock(String name, Dragonjag.Variant variant) {
		return register(cutout(singleton(blockWithItem(name, new SmallDragonjagBlock(settings(variant.getMapColor(), BlockSoundGroup.GRASS, 1.0F), variant), DyeColor.LIME), SpectrumTexturedModels.doubleCross(b -> b, "")))
				.withItemModel((ctx, block) -> Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block), ctx.writer)));
	}
	
	public static final Block SMALL_RED_DRAGONJAG = registerSmallDragonjagBlock("small_red_dragonjag", Dragonjag.Variant.RED);
	public static final Block SMALL_YELLOW_DRAGONJAG = registerSmallDragonjagBlock("small_yellow_dragonjag", Dragonjag.Variant.YELLOW);
	public static final Block SMALL_PINK_DRAGONJAG = registerSmallDragonjagBlock("small_pink_dragonjag", Dragonjag.Variant.PINK);
	public static final Block SMALL_PURPLE_DRAGONJAG = registerSmallDragonjagBlock("small_purple_dragonjag", Dragonjag.Variant.PURPLE);
	public static final Block SMALL_BLACK_DRAGONJAG = registerSmallDragonjagBlock("small_black_dragonjag", Dragonjag.Variant.BLACK);
	
	public static TallDragonjagBlock registerTallDragonjagBlock(String name, Dragonjag.Variant variant) {
		return register(cutout(block(name, new TallDragonjagBlock(settings(variant.getMapColor(), BlockSoundGroup.GRASS, 1.0F), variant)))
				.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block)
						.coordinate(BlockStateVariantMap.create(TallPlantBlock.HALF, TallDragonjagBlock.DEAD).register((half, dead) -> {
							String suffix = (half == DoubleBlockHalf.UPPER ? "_top" : "_bottom") + (dead ? "_dead" : "");
							return createModelVariant((half == DoubleBlockHalf.UPPER ? SpectrumTexturedModels.cross(b -> b, suffix) : SpectrumTexturedModels.doubleCross(b -> b, suffix)).upload(block, suffix, ctx.modelCollector));
						}))));
	}
	
	public static final Block TALL_YELLOW_DRAGONJAG = registerTallDragonjagBlock("tall_yellow_dragonjag", Dragonjag.Variant.YELLOW);
	public static final Block TALL_RED_DRAGONJAG = registerTallDragonjagBlock("tall_red_dragonjag", Dragonjag.Variant.RED);
	public static final Block TALL_PINK_DRAGONJAG = registerTallDragonjagBlock("tall_pink_dragonjag", Dragonjag.Variant.PINK);
	public static final Block TALL_PURPLE_DRAGONJAG = registerTallDragonjagBlock("tall_purple_dragonjag", Dragonjag.Variant.PURPLE);
	public static final Block TALL_BLACK_DRAGONJAG = registerTallDragonjagBlock("tall_black_dragonjag", Dragonjag.Variant.BLACK);
	
	//Flora
	public static final Block ALOE = register(cutout(block("aloe", new AloeBlock(settings(MapColor.DARK_GREEN, BlockSoundGroup.GRASS, 1.0F).noCollision().ticksRandomly().nonOpaque())))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.AGE_4)
					.register(age -> createModelVariant(SpectrumTexturedModels.cross(b -> b, age.toString()).upload(block, age.toString(), ctx.modelCollector))))));
	public static final Block SAWBLADE_HOLLY_BUSH = register(cutout(block("sawblade_holly_bush", new SawbladeHollyBushBlock(settings(MapColor.TERRACOTTA_GREEN, BlockSoundGroup.GRASS, 0.0F).noCollision().ticksRandomly().nonOpaque().luminance(s -> s.get(SawbladeHollyBushBlock.AGE) == SawbladeHollyBushBlock.MAX_AGE ? 10 : 0))))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.AGE_7)
					.register(0, createModelVariant(SpectrumTexturedModels.cross(b -> b, "0").upload(block, "_stage0", ctx.modelCollector)))
					.register(1, createModelVariant(SpectrumTexturedModels.cross(b -> b, "1").upload(block, "_stage1", ctx.modelCollector)))
					.register(2, createModelVariant(ModelIds.getBlockSubModelId(block, "_stage1")))
					.register(3, createModelVariant(SpectrumTexturedModels.cross(b -> b, "2").upload(block, "_stage2", ctx.modelCollector)))
					.register(4, createModelVariant(ModelIds.getBlockSubModelId(block, "_stage2")))
					.register(5, createModelVariant(ModelIds.getBlockSubModelId(block, "_stage2")))
					.register(6, createModelVariant(ModelIds.getBlockSubModelId(block, "_stage2")))
					.register(7, createModelVariant(SpectrumTexturedModels.cross(b -> b, "3").upload(block, "_stage3", ctx.modelCollector))))));
	public static final Block BRISTLE_SPROUTS = register(cutout(blockWithItem("bristle_sprouts", new BristleSproutsBlock(settings(MapColor.PALE_GREEN, BlockSoundGroup.GRASS, 0.0F).noCollision().nonOpaque().offset(OffsetType.XZ).replaceable()), DyeColor.LIME))
			.withBlockModel((ctx, block) -> {
				ctx.registerItemModel(block, "_1");
				return VariantsBlockStateSupplier.create(block,
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_1").upload(block, "_1", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_2").upload(block, "_2", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_3").upload(block, "_3", ctx.modelCollector)),
						createModelVariant(SpectrumTexturedModels.cross(b -> b, "_4").upload(block, "_4", ctx.modelCollector)));
			}));
	public static final Block DOOMBLOOM = register(cutout(block("doombloom", new DoomBloomBlock(SpectrumStatusEffects.STIFFNESS, 8, settings(MapColor.PALE_GREEN, BlockSoundGroup.GRASS, 0.0F).ticksRandomly().noCollision().luminance((state) -> state.get(DoomBloomBlock.AGE) * 2).nonOpaque())))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.AGE_4)
					.register(age -> createModelVariant(SpectrumTexturedModels.cross(b -> b, age.toString()).upload(block, age.toString(), ctx.modelCollector))))));
	public static final Block SNAPPING_IVY = register(cutout(blockWithItem("snapping_ivy", new SnappingIvyBlock(settings(MapColor.PALE_GREEN, BlockSoundGroup.GRASS, 3.0F).noCollision().nonOpaque()), DyeColor.RED))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block)
					.coordinate(createBooleanModelMap(SnappingIvyBlock.SNAPPED, SpectrumModels.SNAPPING_IVY_SNAPPED, SpectrumModels.SNAPPING_IVY))
					.coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_AXIS)
							.register(Direction.Axis.X, BlockStateVariant.create())
							.register(Direction.Axis.Z, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90)))));
	
	public static final Block ABYSSAL_VINES = register(cutout(block("abyssal_vines", new AbyssalVineBlock(settings(MapColor.DARK_GREEN, BlockSoundGroup.CAVE_VINES, 2.0F).noCollision().offset(OffsetType.XYZ).ticksRandomly().nonOpaque().luminance(state -> state.get(Properties.BERRIES) ? 13 : 0))))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(TriStateVineBlock.LIFE_STAGE, AbyssalVineBlock.BERRIES).register((stage, berries) -> {
				String suffix = (stage == TriStateVineBlock.LifeStage.STALK ? "" : "_tip") + (berries ? "_fruiting" : "");
				if (stage == TriStateVineBlock.LifeStage.MATURE) return createModelVariant(ModelIds.getBlockSubModelId(block, suffix));
				return createModelVariant(SpectrumTexturedModels.cross(b -> b, suffix).upload(block, suffix, ctx.modelCollector));
			}))));
	public static final Block NIGHTDEW = register(cutout(block("nightdew", new NightdewBlock(settings(MapColor.TEAL, BlockSoundGroup.CAVE_VINES, 0.0F).noCollision().offset(OffsetType.XYZ).ticksRandomly().nonOpaque().breakInstantly())))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(TriStateVineBlock.LIFE_STAGE).register(stage -> {
				String suffix = (stage == TriStateVineBlock.LifeStage.STALK ? "" : "_tip");
				if (stage == TriStateVineBlock.LifeStage.MATURE) return createModelVariant(ModelIds.getBlockSubModelId(block, suffix));
				return createModelVariant(SpectrumTexturedModels.cross(b -> b, suffix).upload(block, suffix, ctx.modelCollector));
			}))));
	public static final Block SWEET_PEA = register(simplePlant(blockWithItem("sweet_pea", new FlowerBlock(StatusEffects.NIGHT_VISION, 5, settings(MapColor.MAGENTA, BlockSoundGroup.GRASS, 0.0F).offset(OffsetType.XZ).noCollision().nonOpaque().luminance(s -> 11).postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always)), DyeColor.YELLOW)));
	public static final Block APRICOTTI = register(simplePlant(blockWithItem("apricotti", new FlowerBlock(StatusEffects.GLOWING, 5, settings(MapColor.ORANGE, BlockSoundGroup.GRASS, 0.0F).offset(OffsetType.XZ).noCollision().nonOpaque().luminance(s -> 11).postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always)), DyeColor.YELLOW)));
	public static final Block HUMMING_BELL = register(simplePlant(blockWithItem("humming_bell", new FlowerBlock(SpectrumStatusEffects.LIGHTWEIGHT, 5, settings(MapColor.LIGHT_BLUE, BlockSoundGroup.GRASS, 0.0F).offset(OffsetType.XZ).noCollision().nonOpaque().luminance(s -> 9).postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always)), DyeColor.LIME)));
	
	public static final Block HUMMINGSTONE_GLASS = register(translucent(simple(blockWithItem("hummingstone_glass", new TransparentBlock(settings(MapColor.PALE_YELLOW, BlockSoundGroup.GLASS, 5.0F, 100.0F).nonOpaque().requiresTool()), DyeColor.LIGHT_BLUE))));
	public static final Block HUMMINGSTONE_GLASS_PANE = register(glassPane(blockWithItem("hummingstone_glass_pane", new PaneBlock(Settings.copy(HUMMINGSTONE_GLASS)), DyeColor.LIGHT_BLUE), HUMMINGSTONE_GLASS));
	public static final Block HUMMINGSTONE = register(translucent(blockWithItem("hummingstone", new HummingstoneBlock(Settings.copy(HUMMINGSTONE_GLASS).luminance((state) -> 14)), DyeColor.LIGHT_BLUE))
			.withBlockModel((ctx, block) -> VariantsBlockStateSupplier.create(block)
					.coordinate(createBooleanModelMap(HummingstoneBlock.HUMMING,
							SpectrumTexturedModels.cubeAll(b -> b, "_humming").upload(block, "_humming", ctx.modelCollector),
							TexturedModel.CUBE_ALL.upload(block, ctx.modelCollector)))));
	public static final Block WAXED_HUMMINGSTONE = register(translucent(parented(blockWithItem("waxed_hummingstone", new TransparentBlock(Settings.copy(HUMMINGSTONE)), DyeColor.LIGHT_BLUE), HUMMINGSTONE)));
	
	public static final Block MOSS_BALL = register(cutout(blockWithItem("moss_ball", new MossBallBlock(settings(MapColor.DARK_GREEN, BlockSoundGroup.WET_GRASS, 1F).noCollision().nonOpaque().offset(OffsetType.XYZ)), DyeColor.GREEN))
			.withBlockModel((ctx, block) -> {
				List<BlockStateVariant> variants = new ArrayList<>(createHorizontalRotationVariantList(SpectrumModels.MOSS_BALL_TUFT));
				variants.add(BlockStateVariant.create().put(VariantSettings.MODEL, SpectrumModels.MOSS_BALL).put(VariantSettings.WEIGHT, 4));
				return VariantsBlockStateSupplier.create(block, variants.toArray(BlockStateVariant[]::new));
			}));
	public static final Block GIANT_MOSS_BALL = register(blockWithItem("giant_moss_ball", new GiantMossBallBlock(settings(MapColor.DARK_GREEN, BlockSoundGroup.WET_GRASS, 10F).noCollision().nonOpaque().offset(OffsetType.XYZ)), DyeColor.GREEN)
			.withBlockModel((ctx, block) -> createVariantsSupplier(block, SpectrumModels.GIANT_MOSS_BALL)));
	
	public static final Block RESPLENDENT_BLOCK = register(defaultFacingUp(blockWithItem("resplendent_block", new CushionedFacingBlock(Settings.copy(Blocks.RED_WOOL)), IS.of(Rarity.UNCOMMON), DyeColor.YELLOW), TexturedModel.CUBE_BOTTOM_TOP));
	public static final Block RESPLENDENT_CUSHION = register(singleton(blockWithItem("resplendent_cushion", new CushionBlock(Settings.copy(RESPLENDENT_BLOCK).nonOpaque().allowsSpawning(SpectrumBlocks::never)), IS.of(Rarity.UNCOMMON), DyeColor.YELLOW), SpectrumTexturedModels.CUSHION));
	public static final Block RESPLENDENT_CARPET = register(singleton(blockWithItem("resplendent_carpet", new CushionedCarpetBlock(Settings.copy(Blocks.RED_CARPET)), IS.of(Rarity.UNCOMMON), DyeColor.YELLOW), TexturedModel.CARPET));
	public static final Block RESPLENDENT_BED = register(cutout(blockWithItem("resplendent_bed", new SpectrumBedBlock(DyeColor.RED, Settings.copy(Blocks.RED_BED)), IS.of(1, Rarity.UNCOMMON), DyeColor.YELLOW))
			.withBlockModel((ctx, block) -> {
				ctx.excludeFromSimpleItemModelGeneration(block);
				return VariantsBlockStateSupplier.create(block)
						.coordinate(createSouthDefaultHorizontalRotationStates())
						.coordinate(BlockStateVariantMap.create(BedBlock.PART)
								.register(BedPart.HEAD, createModelVariant(ModelIds.getBlockSubModelId(block, "_head")))
								.register(BedPart.FOOT, createModelVariant(ModelIds.getBlockSubModelId(block, "_foot"))));
			}));
	
	// JADE VINES
	public static Settings jadeVine() {
		return settings(MapColor.PALE_GREEN, BlockSoundGroup.WOOL, 0.1F).noCollision().nonOpaque();
	}
	
	public static final Block JADE_VINE_ROOTS = new JadeVineRootsBlock(jadeVine().ticksRandomly().luminance((state) -> state.get(JadeVineRootsBlock.DEAD) ? 0 : 4));
	public static final Block JADE_VINE_BULB = new JadeVineBulbBlock(jadeVine().luminance((state) -> state.get(JadeVineBulbBlock.DEAD) ? 0 : 5));
	public static final Block JADE_VINES = new JadeVinePlantBlock(jadeVine().luminance((state) -> state.get(JadeVinePlantBlock.AGE) == 0 ? 0 : 5));
	public static final Block JADE_VINE_PETAL_BLOCK = new JadeVinePetalBlock(jadeVine().luminance(state -> 3));
	public static final Block JADE_VINE_PETAL_CARPET = new CarpetBlock(jadeVine().luminance(state -> 3));
	
	public static final Block NEPHRITE_BLOSSOM_STEM = new NephriteBlossomStemBlock(settings(MapColor.PINK, BlockSoundGroup.WOOL, 2.0F).nonOpaque().noCollision());
	public static final Block NEPHRITE_BLOSSOM_LEAVES = new NephriteBlossomLeavesBlock(settings(MapColor.PINK, BlockSoundGroup.GRASS, 0.2F).nonOpaque().ticksRandomly().luminance(state -> 13));
	public static final Block NEPHRITE_BLOSSOM_BULB = new NephriteBlossomBulbBlock(AbstractBlock.Settings.copy(NEPHRITE_BLOSSOM_STEM));
	
	public static Settings jadeite() {
		return settings(MapColor.WHITE_GRAY, BlockSoundGroup.WOOL, 0.1F).noCollision().nonOpaque().luminance(state -> 12).postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always);
	}
	
	public static final Block JADEITE_LOTUS_STEM = new JadeiteLotusStemBlock(settings(MapColor.BLACK, BlockSoundGroup.WOOL, 2.0F).nonOpaque().noCollision());
	public static final Block JADEITE_LOTUS_FLOWER = new JadeiteFlowerBlock(settings(MapColor.WHITE, BlockSoundGroup.WOOL, 2.0F).luminance(state -> 14).postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always));
	public static final Block JADEITE_LOTUS_BULB = new JadeiteLotusBulbBlock(AbstractBlock.Settings.copy(JADEITE_LOTUS_STEM).nonOpaque());
	public static final Block JADEITE_PETAL_BLOCK = new JadeVinePetalBlock(jadeite());
	public static final Block JADEITE_PETAL_CARPET = new CarpetBlock(jadeite());
	
	private static Settings ore() {
		return AbstractBlock.Settings.copy(Blocks.IRON_ORE);
	}
	
	private static Settings deepslateOre() {
		return AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE);
	}
	
	private static Settings blackslagOre() {
		return AbstractBlock.Settings.copy(BLACKSLAG).strength(BLACKSLAG_HARDNESS * 1.5F, BLACKSLAG_RESISTANCE * 2F).requiresTool();
	}
	
	private static Settings netherrackOre() {
		return AbstractBlock.Settings.copy(Blocks.NETHERRACK).strength(3.0F, 3.0F).sounds(BlockSoundGroup.NETHER_ORE).requiresTool();
	}
	
	private static Settings endstoneOre() {
		return AbstractBlock.Settings.copy(Blocks.END_STONE).strength(3.0F, 3.0F).requiresTool();
	}
	
	public static final Block SHIMMERSTONE_ORE = register(simple(blockWithItem("shimmerstone_ore", new ShimmerstoneOreBlock(UniformIntProvider.create(2, 4), ore().ticksRandomly(), SpectrumAdvancements.REVEAL_SHIMMERSTONE, Blocks.STONE.getDefaultState()), DyeColor.YELLOW)));
	public static final Block DEEPSLATE_SHIMMERSTONE_ORE = register(simple(blockWithItem("deepslate_shimmerstone_ore", new ShimmerstoneOreBlock(UniformIntProvider.create(2, 4), deepslateOre().ticksRandomly(), SpectrumAdvancements.REVEAL_SHIMMERSTONE, Blocks.DEEPSLATE.getDefaultState()), DyeColor.YELLOW)));
	public static final Block BLACKSLAG_SHIMMERSTONE_ORE = register(singleton(blockWithItem("blackslag_shimmerstone_ore", new ShimmerstoneOreBlock(UniformIntProvider.create(2, 4), blackslagOre().ticksRandomly(), SpectrumAdvancements.REVEAL_SHIMMERSTONE, BLACKSLAG.getDefaultState()), DyeColor.YELLOW), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block SHIMMERSTONE_BLOCK = register(simple(blockWithItem("shimmerstone_block", new ShimmerstoneBlock(settings(MapColor.YELLOW, BlockSoundGroup.GLASS, 2.0F).luminance((state) -> 15)), DyeColor.YELLOW)));
	
	public static final AzuriteOreBlock AZURITE_ORE = register(simpleMirrored(blockWithItem("azurite_ore", new AzuriteOreBlock(UniformIntProvider.create(4, 7), ore().ticksRandomly(), SpectrumAdvancements.REVEAL_AZURITE, Blocks.STONE.getDefaultState()), DyeColor.BLUE)));
	public static final Block DEEPSLATE_AZURITE_ORE = register(simpleMirrored(blockWithItem("deepslate_azurite_ore", new AzuriteOreBlock(UniformIntProvider.create(4, 7), deepslateOre().ticksRandomly(), SpectrumAdvancements.REVEAL_AZURITE, Blocks.DEEPSLATE.getDefaultState()), DyeColor.BLUE)));
	public static final Block BLACKSLAG_AZURITE_ORE = register(simpleMirrored(blockWithItem("blackslag_azurite_ore", new AzuriteOreBlock(UniformIntProvider.create(4, 7), blackslagOre().ticksRandomly(), SpectrumAdvancements.REVEAL_AZURITE, SpectrumBlocks.BLACKSLAG.getDefaultState()), DyeColor.BLUE)));
	public static final Block AZURITE_BLOCK = register(defaultFacingUp(blockWithItem("azurite_block", new SpectrumFacingBlock(AbstractBlock.Settings.copy(Blocks.LAPIS_BLOCK).mapColor(MapColor.BLUE)), DyeColor.BLUE), TexturedModel.CUBE_BOTTOM_TOP));
	public static final Block AZURITE_CLUSTER = register(cluster(blockWithItem("azurite_cluster", new SpectrumClusterBlock(gemstone(MapColor.BLUE, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 2), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), DyeColor.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_AZURITE_BUD = register(cluster(blockWithItem("large_azurite_bud", new SpectrumClusterBlock(gemstone(MapColor.BLUE, SpectrumBlockSoundGroups.LARGE_ONYX_BUD, 3), SpectrumClusterBlock.GrowthStage.LARGE), IS.of(Rarity.UNCOMMON), DyeColor.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_AZURITE_BUD = register(cluster(blockWithItem("small_azurite_bud", new SpectrumClusterBlock(gemstone(MapColor.BLUE, SpectrumBlockSoundGroups.ONYX_CLUSTER, 5), SpectrumClusterBlock.GrowthStage.SMALL), IS.of(Rarity.UNCOMMON), DyeColor.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	
	public static final Block MALACHITE_ORE = register(simple(blockWithItem("malachite_ore", new CloakedOreBlock(UniformIntProvider.create(7, 11), ore(), SpectrumAdvancements.REVEAL_MALACHITE, Blocks.STONE.getDefaultState()), IS.of(Rarity.UNCOMMON), DyeColor.GREEN)));
	public static final Block DEEPSLATE_MALACHITE_ORE = register(simple(blockWithItem("deepslate_malachite_ore", new CloakedOreBlock(UniformIntProvider.create(7, 11), deepslateOre(), SpectrumAdvancements.REVEAL_MALACHITE, Blocks.DEEPSLATE.getDefaultState()), IS.of(Rarity.UNCOMMON), DyeColor.GREEN)));
	public static final Block BLACKSLAG_MALACHITE_ORE = register(singleton(blockWithItem("blackslag_malachite_ore", new CloakedOreBlock(UniformIntProvider.create(7, 11), blackslagOre(), SpectrumAdvancements.REVEAL_MALACHITE, BLACKSLAG.getDefaultState()), IS.of(Rarity.UNCOMMON), DyeColor.GREEN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block MALACHITE_BLOCK = register(defaultFacingUp(blockWithItem("malachite_block", new SpectrumFacingBlock(gemstoneBlock(MapColor.EMERALD_GREEN, BlockSoundGroup.CHAIN)), IS.of(Rarity.UNCOMMON), DyeColor.GREEN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block MALACHITE_CLUSTER = register(cluster(blockWithItem("malachite_cluster", new SpectrumClusterBlock(gemstone(MapColor.EMERALD_GREEN, BlockSoundGroup.CHAIN, 9), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), DyeColor.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_MALACHITE_BUD = register(cluster(blockWithItem("large_malachite_bud", new SpectrumClusterBlock(gemstone(MapColor.EMERALD_GREEN, BlockSoundGroup.CHAIN, 7), SpectrumClusterBlock.GrowthStage.LARGE), IS.of(Rarity.UNCOMMON), DyeColor.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_MALACHITE_BUD = register(cluster(blockWithItem("small_malachite_bud", new SpectrumClusterBlock(gemstone(MapColor.EMERALD_GREEN, BlockSoundGroup.CHAIN, 5), SpectrumClusterBlock.GrowthStage.SMALL), IS.of(Rarity.UNCOMMON), DyeColor.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	
	public static final Block BLOODSTONE_BLOCK = register(defaultFacingUp(blockWithItem("bloodstone_block", new SpectrumFacingBlock(gemstoneBlock(MapColor.RED, SpectrumBlockSoundGroups.ONYX_CLUSTER)), IS.of(Rarity.UNCOMMON), DyeColor.RED), TexturedModel.CUBE_COLUMN));
	public static final Block BLOODSTONE_CLUSTER = register(cluster(blockWithItem("bloodstone_cluster", new SpectrumClusterBlock(gemstone(MapColor.RED, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 6), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), DyeColor.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_BLOODSTONE_BUD = register(cluster(blockWithItem("large_bloodstone_bud", new SpectrumClusterBlock(gemstone(MapColor.RED, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 4), SpectrumClusterBlock.GrowthStage.LARGE), IS.of(Rarity.UNCOMMON), DyeColor.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_BLOODSTONE_BUD = register(cluster(blockWithItem("small_bloodstone_bud", new SpectrumClusterBlock(gemstone(MapColor.RED, SpectrumBlockSoundGroups.ONYX_CLUSTER, 3), SpectrumClusterBlock.GrowthStage.SMALL), IS.of(Rarity.UNCOMMON), DyeColor.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	
	public static final Block STRATINE_ORE = new CloakedOreBlock(UniformIntProvider.create(3, 5), netherrackOre(), SpectrumAdvancements.REVEAL_STRATINE, Blocks.NETHERRACK.getDefaultState());
	public static final Block PALTAERIA_ORE = new CloakedOreBlock(UniformIntProvider.create(2, 4), endstoneOre(), SpectrumAdvancements.REVEAL_PALTAERIA, Blocks.END_STONE.getDefaultState());
	
	private static Settings gravityBlock(MapColor mapColor) {
		return settings(mapColor, BlockSoundGroup.METAL, 4.0F, 6.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresTool();
	}
	
	public static final FloatBlock PALTAERIA_FLOATBLOCK = new FloatBlock(gravityBlock(MapColor.LIGHT_BLUE), 0.2F);
	public static final FloatBlock STRATINE_FLOATBLOCK = new FloatBlock(gravityBlock(MapColor.DARK_RED), -0.2F);
	public static final FloatBlock HOVER_BLOCK = new FloatBlock(gravityBlock(MapColor.DIAMOND_BLUE), 0.0F);
	
	public static final Block BLACKSLAG_COAL_ORE = register(singleton(blockWithItem("blackslag_coal_ore", new ExperienceDroppingBlock(UniformIntProvider.create(0, 2), blackslagOre()), DyeColor.BLACK), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_COPPER_ORE = register(singleton(blockWithItem("blackslag_copper_ore", new ExperienceDroppingBlock(ConstantIntProvider.create(0), blackslagOre()), DyeColor.BLACK), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_IRON_ORE = register(singleton(blockWithItem("blackslag_iron_ore", new ExperienceDroppingBlock(ConstantIntProvider.create(0), blackslagOre()), DyeColor.BROWN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_GOLD_ORE = register(singleton(blockWithItem("blackslag_gold_ore", new ExperienceDroppingBlock(ConstantIntProvider.create(0), blackslagOre()), DyeColor.YELLOW), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_LAPIS_ORE = register(singleton(blockWithItem("blackslag_lapis_ore", new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), blackslagOre()), DyeColor.BLUE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_DIAMOND_ORE = register(singleton(blockWithItem("blackslag_diamond_ore", new ExperienceDroppingBlock(UniformIntProvider.create(3, 7), blackslagOre()), DyeColor.LIGHT_BLUE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_REDSTONE_ORE = register(singleton(blockWithItem("blackslag_redstone_ore", new RedstoneOreBlock(blackslagOre().ticksRandomly().luminance(createLightLevelFromLitBlockState(9))), DyeColor.RED), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_EMERALD_ORE = register(singleton(blockWithItem("blackslag_emerald_ore", new ExperienceDroppingBlock(UniformIntProvider.create(3, 7), blackslagOre()), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	
	// FUNCTIONAL BLOCKS
	public static final Block HEARTBOUND_CHEST = new HeartboundChestBlock(settings(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.STONE, -1.0F, 3600000.0F).requiresTool().nonOpaque());
	public static final Block COMPACTING_CHEST = new CompactingChestBlock(settings(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.STONE, 4.0F, 4.0F).requiresTool().nonOpaque());
	public static final Block FABRICATION_CHEST = new FabricationChestBlock(settings(MapColor.ORANGE, BlockSoundGroup.STONE, 4.0F, 4.0F).requiresTool().nonOpaque());
	public static final Block BLACK_HOLE_CHEST = new BlackHoleChestBlock(settings(MapColor.BLACK, BlockSoundGroup.STONE, 4.0F, 4.0F).requiresTool().nonOpaque());
	public static final Block PARTICLE_SPAWNER = new ParticleSpawnerBlock(settings(MapColor.TERRACOTTA_WHITE, BlockSoundGroup.AMETHYST_BLOCK, 5.0F, 6.0F).requiresTool().nonOpaque());
	public static final Block CREATIVE_PARTICLE_SPAWNER = new CreativeParticleSpawnerBlock(AbstractBlock.Settings.copy(SpectrumBlocks.PARTICLE_SPAWNER).strength(-1.0F, 3600000.8F).dropsNothing());
	public static final Block BEDROCK_ANVIL = new BedrockAnvilBlock(AbstractBlock.Settings.copy(Blocks.ANVIL).requiresTool().strength(8.0F, 8.0F).sounds(BlockSoundGroup.METAL));
	
	// SOLID LIQUID CRYSTAL
	public static final Block FROSTBITE_CRYSTAL = new Block(AbstractBlock.Settings.copy(Blocks.GLOWSTONE).mapColor(MapColor.LIGHT_BLUE_GRAY));
	public static final Block BLAZING_CRYSTAL = new Block(AbstractBlock.Settings.copy(Blocks.GLOWSTONE).mapColor(MapColor.ORANGE));
	
	public static final Block QUITOXIC_REEDS = new QuitoxicReedsBlock(settings(MapColor.CLEAR, BlockSoundGroup.GRASS, 0.0F).noCollision().offset(AbstractBlock.OffsetType.XYZ).ticksRandomly().luminance(state -> state.get(QuitoxicReedsBlock.LOGGED).getLuminance()));
	public static final Block MERMAIDS_BRUSH = new MermaidsBrushBlock(settings(MapColor.CLEAR, BlockSoundGroup.WET_GRASS, 0.0F).noCollision().ticksRandomly().luminance(state -> state.get(MermaidsBrushBlock.LOGGED).getLuminance()));
	public static final Block RADIATING_ENDER = new RadiatingEnderBlock(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK).mapColor(MapColor.PURPLE));
	public static final Block AMARANTH = new AmaranthCropBlock(settings(MapColor.CLEAR, BlockSoundGroup.CROP, 0.0F).noCollision().ticksRandomly());
	
	public static final Block MEMORY = new MemoryBlock(settings(MapColor.CLEAR, BlockSoundGroup.AMETHYST_BLOCK, 0.0F).blockVision(SpectrumBlocks::never).nonOpaque().ticksRandomly());
	public static final Block CRACKED_END_PORTAL_FRAME = new CrackedEndPortalFrameBlock(settings(MapColor.PALE_PURPLE, BlockSoundGroup.GLASS, -1.0F, 3600000.0F).instrument(NoteBlockInstrument.BASEDRUM).luminance((state) -> 1));
	public static final Block LAVA_SPONGE = new LavaSpongeBlock(AbstractBlock.Settings.copy(Blocks.SPONGE).mapColor(MapColor.ORANGE));
	public static final Block WET_LAVA_SPONGE = new WetLavaSpongeBlock(AbstractBlock.Settings.copy(Blocks.WET_SPONGE).mapColor(MapColor.ORANGE).luminance(s -> 9).emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always));
	
	public static final Block LIGHT_LEVEL_DETECTOR = new BlockLightDetectorBlock(AbstractBlock.Settings.copy(Blocks.DAYLIGHT_DETECTOR));
	public static final Block WEATHER_DETECTOR = new WeatherDetectorBlock(AbstractBlock.Settings.copy(Blocks.DAYLIGHT_DETECTOR));
	public static final Block ITEM_DETECTOR = new ItemDetectorBlock(AbstractBlock.Settings.copy(Blocks.DAYLIGHT_DETECTOR));
	public static final Block PLAYER_DETECTOR = new PlayerDetectorBlock(AbstractBlock.Settings.copy(Blocks.DAYLIGHT_DETECTOR));
	public static final Block CREATURE_DETECTOR = new EntityDetectorBlock(AbstractBlock.Settings.copy(Blocks.DAYLIGHT_DETECTOR));
	public static final Block REDSTONE_CALCULATOR = new RedstoneCalculatorBlock(AbstractBlock.Settings.copy(Blocks.REPEATER));
	public static final Block REDSTONE_TIMER = new RedstoneTimerBlock(AbstractBlock.Settings.copy(Blocks.REPEATER));
	public static final Block REDSTONE_TRANSCEIVER = new RedstoneTransceiverBlock(AbstractBlock.Settings.copy(Blocks.REPEATER));
	public static final Block BLOCK_PLACER = new BlockPlacerBlock(AbstractBlock.Settings.copy(Blocks.DISPENSER));
	public static final Block BLOCK_DETECTOR = new BlockDetectorBlock(AbstractBlock.Settings.copy(Blocks.DISPENSER));
	public static final Block BLOCK_BREAKER = new BlockBreakerBlock(AbstractBlock.Settings.copy(Blocks.DISPENSER));
	public static final EnderDropperBlock ENDER_DROPPER = new EnderDropperBlock(AbstractBlock.Settings.copy(Blocks.DROPPER).mapColor(MapColor.GRAY).requiresTool().strength(15F, 60.0F));
	public static final Block ENDER_HOPPER = new EnderHopperBlock(AbstractBlock.Settings.copy(Blocks.HOPPER).mapColor(MapColor.GRAY).requiresTool().strength(15F, 60.0F));
	
	public static final Block OMINOUS_SAPLING = new OminousSaplingBlock(AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));
	
	public static final Block SPIRIT_SALLOW_LEAVES = register(singleton(blockWithItem("spirit_sallow_leaves", new SpiritSallowLeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.OFF_WHITE).luminance((state) -> 8)), DyeColor.GREEN), TexturedModel.LEAVES));
	public static final Block SPIRIT_SALLOW_LOG = register(log(blockWithItem("spirit_sallow_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).mapColor(MapColor.GRAY)), DyeColor.GREEN)));
	public static final Block SPIRIT_SALLOW_ROOTS = register(blockWithItem("spirit_sallow_roots", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).mapColor(MapColor.GRAY)), DyeColor.GREEN)
			.withBlockModel((ctx, block) -> {
				TextureMap textureMap = SpectrumTextureMaps.sideEnd(block, "", block, "");
				Identifier vertical = Models.CUBE_COLUMN.upload(block, textureMap, ctx.modelCollector);
				Identifier horizontal = Models.CUBE_COLUMN_HORIZONTAL.upload(block, textureMap, ctx.modelCollector);
				return BlockStateModelGenerator.createAxisRotatedBlockState(block, vertical, horizontal);
			}));
	public static final Block SPIRIT_SALLOW_HEART = register(singleton(blockWithItem("spirit_sallow_heart", new Block(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).mapColor(MapColor.GRAY).luminance(s -> 11)), DyeColor.GREEN), SpectrumTexturedModels.cubeColumn(b -> b, "", b -> SPIRIT_SALLOW_LOG, "_top")));
	
	public static final Block SACRED_SOIL = new ExtraTickFarmlandBlock(AbstractBlock.Settings.copy(Blocks.FARMLAND).mapColor(MapColor.LIGHT_BLUE_GRAY), Blocks.DIRT.getDefaultState());
	
	private static Settings spiritVines(MapColor mapColor) {
		return settings(mapColor, BlockSoundGroup.CAVE_VINES, 0.0F).noCollision();
	}
	
	public static final SpiritVinesPlantBlock CYAN_SPIRIT_SALLOW_VINES_PLANT = new SpiritVinesPlantBlock(spiritVines(MapColor.CYAN), BuiltinGemstoneColor.CYAN);
	public static final SpiritVinesPlantStemBlock CYAN_SPIRIT_SALLOW_VINES = new SpiritVinesPlantStemBlock(spiritVines(MapColor.CYAN), BuiltinGemstoneColor.CYAN);
	public static final SpiritVinesPlantBlock MAGENTA_SPIRIT_SALLOW_VINES_PLANT = new SpiritVinesPlantBlock(spiritVines(MapColor.MAGENTA), BuiltinGemstoneColor.MAGENTA);
	public static final SpiritVinesPlantStemBlock MAGENTA_SPIRIT_SALLOW_VINES = new SpiritVinesPlantStemBlock(spiritVines(MapColor.MAGENTA), BuiltinGemstoneColor.MAGENTA);
	public static final SpiritVinesPlantBlock YELLOW_SPIRIT_SALLOW_VINES_PLANT = new SpiritVinesPlantBlock(spiritVines(MapColor.YELLOW), BuiltinGemstoneColor.YELLOW);
	public static final SpiritVinesPlantStemBlock YELLOW_SPIRIT_SALLOW_VINES = new SpiritVinesPlantStemBlock(spiritVines(MapColor.YELLOW), BuiltinGemstoneColor.YELLOW);
	public static final SpiritVinesPlantBlock BLACK_SPIRIT_SALLOW_VINES_PLANT = new SpiritVinesPlantBlock(spiritVines(MapColor.TERRACOTTA_BLACK), BuiltinGemstoneColor.BLACK);
	public static final SpiritVinesPlantStemBlock BLACK_SPIRIT_SALLOW_VINES = new SpiritVinesPlantStemBlock(spiritVines(MapColor.TERRACOTTA_BLACK), BuiltinGemstoneColor.BLACK);
	public static final SpiritVinesPlantBlock WHITE_SPIRIT_SALLOW_VINES_PLANT = new SpiritVinesPlantBlock(spiritVines(MapColor.TERRACOTTA_WHITE), BuiltinGemstoneColor.WHITE);
	public static final SpiritVinesPlantStemBlock WHITE_SPIRIT_SALLOW_VINES = new SpiritVinesPlantStemBlock(spiritVines(MapColor.TERRACOTTA_WHITE), BuiltinGemstoneColor.WHITE);
	
	public static final Block STUCK_STORM_STONE = new StuckStormStoneBlock(settings(MapColor.CLEAR, BlockSoundGroup.SMALL_AMETHYST_BUD, 0.0F).noCollision().nonOpaque().suffocates(SpectrumBlocks::never).noBlockBreakParticles().blockVision(SpectrumBlocks::never).replaceable());
	public static final Block DEEPER_DOWN_PORTAL = new DeeperDownPortalBlock(settings(MapColor.BLACK, BlockSoundGroup.INTENTIONALLY_EMPTY, -1.0F, 3600000.0F).pistonBehavior(PistonBehavior.BLOCK).luminance(state -> 8).dropsNothing());
	
	private static Settings upgrade() {
		return AbstractBlock.Settings.copy(SpectrumBlocks.POLISHED_BASALT).solid();
	}
	
	public static final Block UPGRADE_SPEED = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 1, DyeColor.MAGENTA);
	public static final Block UPGRADE_SPEED2 = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 2, DyeColor.MAGENTA);
	public static final Block UPGRADE_SPEED3 = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 8, DyeColor.MAGENTA);
	public static final Block UPGRADE_EFFICIENCY = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EFFICIENCY, 1, DyeColor.YELLOW);
	public static final Block UPGRADE_EFFICIENCY2 = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EFFICIENCY, 4, DyeColor.YELLOW);
	public static final Block UPGRADE_YIELD = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.YIELD, 1, DyeColor.CYAN);
	public static final Block UPGRADE_YIELD2 = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.YIELD, 4, DyeColor.CYAN);
	public static final Block UPGRADE_EXPERIENCE = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EXPERIENCE, 1, DyeColor.PURPLE);
	public static final Block UPGRADE_EXPERIENCE2 = new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EXPERIENCE, 4, DyeColor.PURPLE);
	
	public static final Block REDSTONE_SAND = new RedstoneGravityBlock(AbstractBlock.Settings.copy(Blocks.SAND).mapColor(MapColor.BRIGHT_RED));
	public static final Block ENDER_GLASS = new EnderGlassBlock(AbstractBlock.Settings.copy(Blocks.GLASS).mapColor(MapColor.PURPLE).nonOpaque().solidBlock(SpectrumBlocks::never)
			.allowsSpawning((state, world, pos, entityType) -> state.get(EnderGlassBlock.TRANSPARENCY_STATE) == EnderGlassBlock.TransparencyState.SOLID)
			.suffocates((state, world, pos) -> state.get(EnderGlassBlock.TRANSPARENCY_STATE) == EnderGlassBlock.TransparencyState.SOLID)
			.blockVision((state, world, pos) -> state.get(EnderGlassBlock.TRANSPARENCY_STATE) == EnderGlassBlock.TransparencyState.SOLID));
	public static final Block CLOVER = new CloverBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XZ));
	public static final Block FOUR_LEAF_CLOVER = new FourLeafCloverBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XZ));
	
	private static final UniformIntProvider gemOreExperienceProvider = UniformIntProvider.create(1, 4);
	public static final Block TOPAZ_ORE = register(simple(blockWithItem("topaz_ore", new GemstoneOreBlock(gemOreExperienceProvider, ore(), BuiltinGemstoneColor.CYAN, SpectrumAdvancements.COLLECT_TOPAZ, Blocks.STONE.getDefaultState()), DyeColor.CYAN)));
	public static final Block AMETHYST_ORE = register(simple(blockWithItem("amethyst_ore", new GemstoneOreBlock(gemOreExperienceProvider, ore(), BuiltinGemstoneColor.MAGENTA, SpectrumAdvancements.COLLECT_AMETHYST, Blocks.STONE.getDefaultState()), DyeColor.MAGENTA)));
	public static final Block CITRINE_ORE = register(simple(blockWithItem("citrine_ore", new GemstoneOreBlock(gemOreExperienceProvider, ore(), BuiltinGemstoneColor.YELLOW, SpectrumAdvancements.COLLECT_CITRINE, Blocks.STONE.getDefaultState()), DyeColor.YELLOW)));
	public static final Block ONYX_ORE = register(simple(blockWithItem("onyx_ore", new GemstoneOreBlock(gemOreExperienceProvider, ore(), BuiltinGemstoneColor.BLACK, SpectrumAdvancements.CREATE_ONYX, Blocks.STONE.getDefaultState()), DyeColor.BLACK)));
	public static final Block MOONSTONE_ORE = register(simple(blockWithItem("moonstone_ore", new GemstoneOreBlock(gemOreExperienceProvider, ore(), BuiltinGemstoneColor.WHITE, SpectrumAdvancements.COLLECT_MOONSTONE, Blocks.STONE.getDefaultState()), DyeColor.WHITE)));
	
	public static final Block DEEPSLATE_TOPAZ_ORE = register(simple(blockWithItem("deepslate_topaz_ore", new GemstoneOreBlock(gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.CYAN, SpectrumAdvancements.COLLECT_TOPAZ, Blocks.DEEPSLATE.getDefaultState()), DyeColor.CYAN)));
	public static final Block DEEPSLATE_AMETHYST_ORE = register(simple(blockWithItem("deepslate_amethyst_ore", new GemstoneOreBlock(gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.MAGENTA, SpectrumAdvancements.COLLECT_AMETHYST, Blocks.DEEPSLATE.getDefaultState()), DyeColor.MAGENTA)));
	public static final Block DEEPSLATE_CITRINE_ORE = register(simple(blockWithItem("deepslate_citrine_ore", new GemstoneOreBlock(gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.YELLOW, SpectrumAdvancements.COLLECT_CITRINE, Blocks.DEEPSLATE.getDefaultState()), DyeColor.YELLOW)));
	public static final Block DEEPSLATE_ONYX_ORE = register(simple(blockWithItem("deepslate_onyx_ore", new GemstoneOreBlock(gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.BLACK, SpectrumAdvancements.CREATE_ONYX, Blocks.DEEPSLATE.getDefaultState()), DyeColor.BLACK)));
	public static final Block DEEPSLATE_MOONSTONE_ORE = register(simple(blockWithItem("deepslate_moonstone_ore", new GemstoneOreBlock(gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.WHITE, SpectrumAdvancements.COLLECT_MOONSTONE, Blocks.DEEPSLATE.getDefaultState()), DyeColor.WHITE)));
	
	public static final Block BLACKSLAG_TOPAZ_ORE = register(singleton(blockWithItem("blackslag_topaz_ore", new GemstoneOreBlock(gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.CYAN, SpectrumAdvancements.COLLECT_TOPAZ, SpectrumBlocks.BLACKSLAG.getDefaultState()), DyeColor.CYAN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_AMETHYST_ORE = register(singleton(blockWithItem("blackslag_amethyst_ore", new GemstoneOreBlock(gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.MAGENTA, SpectrumAdvancements.COLLECT_AMETHYST, SpectrumBlocks.BLACKSLAG.getDefaultState()), DyeColor.MAGENTA), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_CITRINE_ORE = register(singleton(blockWithItem("blackslag_citrine_ore", new GemstoneOreBlock(gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.YELLOW, SpectrumAdvancements.COLLECT_CITRINE, SpectrumBlocks.BLACKSLAG.getDefaultState()), DyeColor.YELLOW), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_ONYX_ORE = register(singleton(blockWithItem("blackslag_onyx_ore", new GemstoneOreBlock(gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.BLACK, SpectrumAdvancements.CREATE_ONYX, SpectrumBlocks.BLACKSLAG.getDefaultState()), DyeColor.BLACK), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLACKSLAG_MOONSTONE_ORE = register(singleton(blockWithItem("blackslag_moonstone_ore", new GemstoneOreBlock(gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.WHITE, SpectrumAdvancements.COLLECT_MOONSTONE, SpectrumBlocks.BLACKSLAG.getDefaultState()), DyeColor.WHITE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	
	private static Settings gemStorageBlock(MapColor mapColor, BlockSoundGroup soundGroup) {
		return settings(mapColor, soundGroup, 5.0F, 6.0F);
	}
	
	public static final Block TOPAZ_STORAGE_BLOCK = register(singleton(blockWithItem("topaz_storage_block", new Block(gemStorageBlock(MapColor.CYAN, SpectrumBlockSoundGroups.TOPAZ_BLOCK)), DyeColor.CYAN), TexturedModel.SIDE_TOP_BOTTOM_WALL));
	public static final Block AMETHYST_STORAGE_BLOCK = register(singleton(blockWithItem("amethyst_storage_block", new Block(gemStorageBlock(MapColor.MAGENTA, BlockSoundGroup.AMETHYST_BLOCK)), DyeColor.MAGENTA), TexturedModel.SIDE_TOP_BOTTOM_WALL));
	public static final Block CITRINE_STORAGE_BLOCK = register(singleton(blockWithItem("citrine_storage_block", new Block(gemStorageBlock(MapColor.YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK)), DyeColor.YELLOW), TexturedModel.SIDE_TOP_BOTTOM_WALL));
	public static final Block ONYX_STORAGE_BLOCK = register(singleton(blockWithItem("onyx_storage_block", new Block(gemStorageBlock(MapColor.BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK)), DyeColor.BLACK), TexturedModel.SIDE_TOP_BOTTOM_WALL));
	public static final Block MOONSTONE_STORAGE_BLOCK = register(singleton(blockWithItem("moonstone_storage_block", new Block(gemStorageBlock(MapColor.WHITE, SpectrumBlockSoundGroups.MOONSTONE_BLOCK)), DyeColor.WHITE), TexturedModel.SIDE_TOP_BOTTOM_WALL));
//	public static final Block SPECTRAL_SHARD_BLOCK = register(simple(blockWithItem("spectral_shard_block", new SpectrumGemstoneBlock(gemstoneBlock(MapColor.DIAMOND_BLUE, SpectrumBlockSoundGroups.SPECTRAL_BLOCK), SpectrumSoundEvents.SPECTRAL_BLOCK_HIT, SpectrumSoundEvents.SPECTRAL_BLOCK_CHIME), IS.of(Rarity.RARE), DyeColor.WHITE)));
//	public static final Block SPECTRAL_SHARD_STORAGE_BLOCK = register(simple(blockWithItem("spectral_shard_storage_block", new Block(gemStorageBlock(MapColor.OFF_WHITE, SpectrumBlockSoundGroups.SPECTRAL_BLOCK)), IS.of(Rarity.RARE), DyeColor.WHITE)));
	
	private static AbstractBlock.Settings copyWithMapColor(Block baseBlock, MapColor color) {
		return AbstractBlock.Settings.copy(baseBlock).mapColor(color);
	}
	
	public static Settings pottedPlant() {
		return Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY);
	}
	
	public static final AmaranthBushelBlock AMARANTH_BUSHEL = register(tintableCross(blockWithItem("amaranth_bushel", new AmaranthBushelBlock(SpectrumStatusEffects.NOURISHING, 8, settings(MapColor.CLEAR, BlockSoundGroup.CROP, 0.0F).noCollision()), DyeColor.RED), false)
			.withItemModel((ctx, block) -> Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block.asItem()), ctx.writer)));
	
	public static final PottedAmaranthBushelBlock POTTED_AMARANTH_BUSHEL = register(pottedPlant(block("potted_amaranth_bushel", new PottedAmaranthBushelBlock(AMARANTH_BUSHEL, pottedPlant())), false));
	
	public static final ResonantLilyBlock RESONANT_LILY = register(simplePlant(blockWithItem("resonant_lily", new ResonantLilyBlock(StatusEffects.REGENERATION, 5, AbstractBlock.Settings.copy(Blocks.POPPY).mapColor(MapColor.WHITE)), DyeColor.GREEN)));
	
	public static final PottedResonantLilyBlock POTTED_RESONANT_LILY = register(pottedPlant(block("potted_resonant_lily", new PottedResonantLilyBlock(RESONANT_LILY, pottedPlant())), false));
	
	public static final BloodOrchidBlock BLOOD_ORCHID = register(cutout(blockWithItem("blood_orchid", new BloodOrchidBlock(SpectrumStatusEffects.FRENZY, 10, AbstractBlock.Settings.copy(Blocks.POPPY).offset(AbstractBlock.OffsetType.NONE).ticksRandomly()), DyeColor.RED))
			.withBlockModel((ctx, block) -> {
				ctx.registerItemModel(block, "5");
				return VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(BloodOrchidBlock.AGE)
						.register(stage -> createModelVariant(SpectrumTexturedModels.tintableCross(b -> b, stage.toString(), false).upload(block, stage.toString(), ctx.modelCollector))));
			}));
	public static final PottedBloodOrchidBlock POTTED_BLOOD_ORCHID = register(cutout(singleton(block("potted_blood_orchid", new PottedBloodOrchidBlock(BLOOD_ORCHID, pottedPlant())), SpectrumTexturedModels.flowerPotCross(b -> BLOOD_ORCHID, "5", false))));
	
	public static ColoredSaplingBlock registerColoredSapling(String name, DyeColor dyeColor) {
		return register(simplePlant(blockWithItem(name, new ColoredSaplingBlock(copyWithMapColor(OAK_SAPLING, dyeColor.getMapColor()), dyeColor), dyeColor)));
	}
	
	public static final ColoredSaplingBlock BLACK_SAPLING = registerColoredSapling("black_sapling", DyeColor.BLACK);
	public static final ColoredSaplingBlock BLUE_SAPLING = registerColoredSapling("blue_sapling", DyeColor.BLUE);
	public static final ColoredSaplingBlock BROWN_SAPLING = registerColoredSapling("brown_sapling", DyeColor.BROWN);
	public static final ColoredSaplingBlock CYAN_SAPLING = registerColoredSapling("cyan_sapling", DyeColor.CYAN);
	public static final ColoredSaplingBlock GRAY_SAPLING = registerColoredSapling("gray_sapling", DyeColor.GRAY);
	public static final ColoredSaplingBlock GREEN_SAPLING = registerColoredSapling("green_sapling", DyeColor.GREEN);
	public static final ColoredSaplingBlock LIGHT_BLUE_SAPLING = registerColoredSapling("light_blue_sapling", DyeColor.LIGHT_BLUE);
	public static final ColoredSaplingBlock LIGHT_GRAY_SAPLING = registerColoredSapling("light_gray_sapling", DyeColor.LIGHT_GRAY);
	public static final ColoredSaplingBlock LIME_SAPLING = registerColoredSapling("lime_sapling", DyeColor.LIME);
	public static final ColoredSaplingBlock MAGENTA_SAPLING = registerColoredSapling("magenta_sapling", DyeColor.MAGENTA);
	public static final ColoredSaplingBlock ORANGE_SAPLING = registerColoredSapling("orange_sapling", DyeColor.ORANGE);
	public static final ColoredSaplingBlock PINK_SAPLING = registerColoredSapling("pink_sapling", DyeColor.PINK);
	public static final ColoredSaplingBlock PURPLE_SAPLING = registerColoredSapling("purple_sapling", DyeColor.PURPLE);
	public static final ColoredSaplingBlock RED_SAPLING = registerColoredSapling("red_sapling", DyeColor.RED);
	public static final ColoredSaplingBlock WHITE_SAPLING = registerColoredSapling("white_sapling", DyeColor.WHITE);
	public static final ColoredSaplingBlock YELLOW_SAPLING = registerColoredSapling("yellow_sapling", DyeColor.YELLOW);
	
	public static PottedColoredSaplingBlock registerPottedColoredSapling(String name, ColoredSaplingBlock saplingBlock) {
		return register(pottedPlant(block(name, new PottedColoredSaplingBlock(saplingBlock, pottedPlant(), saplingBlock.getColor())), false));
	}
	
	public static final PottedColoredSaplingBlock POTTED_BLACK_SAPLING = registerPottedColoredSapling("potted_black_sapling", BLACK_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_BLUE_SAPLING = registerPottedColoredSapling("potted_blue_sapling", BLUE_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_BROWN_SAPLING = registerPottedColoredSapling("potted_brown_sapling", BROWN_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_CYAN_SAPLING = registerPottedColoredSapling("potted_cyan_sapling", CYAN_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_GRAY_SAPLING = registerPottedColoredSapling("potted_gray_sapling", GRAY_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_GREEN_SAPLING = registerPottedColoredSapling("potted_green_sapling", GREEN_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_LIGHT_BLUE_SAPLING = registerPottedColoredSapling("potted_light_blue_sapling", LIGHT_BLUE_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_LIGHT_GRAY_SAPLING = registerPottedColoredSapling("potted_light_gray_sapling", LIGHT_GRAY_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_LIME_SAPLING = registerPottedColoredSapling("potted_lime_sapling", LIME_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_MAGENTA_SAPLING = registerPottedColoredSapling("potted_magenta_sapling", MAGENTA_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_ORANGE_SAPLING = registerPottedColoredSapling("potted_orange_sapling", ORANGE_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_PINK_SAPLING = registerPottedColoredSapling("potted_pink_sapling", PINK_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_PURPLE_SAPLING = registerPottedColoredSapling("potted_purple_sapling", PURPLE_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_RED_SAPLING = registerPottedColoredSapling("potted_red_sapling", RED_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_WHITE_SAPLING = registerPottedColoredSapling("potted_white_sapling", WHITE_SAPLING);
	public static final PottedColoredSaplingBlock POTTED_YELLOW_SAPLING = registerPottedColoredSapling("potted_yellow_sapling", YELLOW_SAPLING);
	
	public static ColoredLogBlock registerColoredLog(String name, DyeColor color) {
		return register(log(blockWithItem(name, new ColoredLogBlock(copyWithMapColor(OAK_LOG, color.getMapColor()), color), color)));
	}
	
	public static final ColoredLogBlock BLACK_LOG = registerColoredLog("black_log", DyeColor.BLACK);
	public static final ColoredLogBlock BLUE_LOG = registerColoredLog("blue_log", DyeColor.BLUE);
	public static final ColoredLogBlock BROWN_LOG = registerColoredLog("brown_log", DyeColor.BROWN);
	public static final ColoredLogBlock CYAN_LOG = registerColoredLog("cyan_log", DyeColor.CYAN);
	public static final ColoredLogBlock GRAY_LOG = registerColoredLog("gray_log", DyeColor.GRAY);
	public static final ColoredLogBlock GREEN_LOG = registerColoredLog("green_log", DyeColor.GREEN);
	public static final ColoredLogBlock LIGHT_BLUE_LOG = registerColoredLog("light_blue_log", DyeColor.LIGHT_BLUE);
	public static final ColoredLogBlock LIGHT_GRAY_LOG = registerColoredLog("light_gray_log", DyeColor.LIGHT_GRAY);
	public static final ColoredLogBlock LIME_LOG = registerColoredLog("lime_log", DyeColor.LIME);
	public static final ColoredLogBlock MAGENTA_LOG = registerColoredLog("magenta_log", DyeColor.MAGENTA);
	public static final ColoredLogBlock ORANGE_LOG = registerColoredLog("orange_log", DyeColor.ORANGE);
	public static final ColoredLogBlock PINK_LOG = registerColoredLog("pink_log", DyeColor.PINK);
	public static final ColoredLogBlock PURPLE_LOG = registerColoredLog("purple_log", DyeColor.PURPLE);
	public static final ColoredLogBlock RED_LOG = registerColoredLog("red_log", DyeColor.RED);
	public static final ColoredLogBlock WHITE_LOG = registerColoredLog("white_log", DyeColor.WHITE);
	public static final ColoredLogBlock YELLOW_LOG = registerColoredLog("yellow_log", DyeColor.YELLOW);
	
	public static ColoredWoodBlock registerColoredWood(String name, ColoredLogBlock logBlock) {
		return register(wood(blockWithItem(name, new ColoredWoodBlock(copyWithMapColor(OAK_WOOD, logBlock.getDefaultMapColor()), logBlock.getColor()), logBlock.getColor()), logBlock));
	}
	
	public static final ColoredWoodBlock BLACK_WOOD = registerColoredWood("black_wood", BLACK_LOG);
	public static final ColoredWoodBlock BLUE_WOOD = registerColoredWood("blue_wood", BLUE_LOG);
	public static final ColoredWoodBlock BROWN_WOOD = registerColoredWood("brown_wood", BROWN_LOG);
	public static final ColoredWoodBlock CYAN_WOOD = registerColoredWood("cyan_wood", CYAN_LOG);
	public static final ColoredWoodBlock GRAY_WOOD = registerColoredWood("gray_wood", GRAY_LOG);
	public static final ColoredWoodBlock GREEN_WOOD = registerColoredWood("green_wood", GREEN_LOG);
	public static final ColoredWoodBlock LIGHT_BLUE_WOOD = registerColoredWood("light_blue_wood", LIGHT_BLUE_LOG);
	public static final ColoredWoodBlock LIGHT_GRAY_WOOD = registerColoredWood("light_gray_wood", LIGHT_GRAY_LOG);
	public static final ColoredWoodBlock LIME_WOOD = registerColoredWood("lime_wood", LIME_LOG);
	public static final ColoredWoodBlock MAGENTA_WOOD = registerColoredWood("magenta_wood", MAGENTA_LOG);
	public static final ColoredWoodBlock ORANGE_WOOD = registerColoredWood("orange_wood", ORANGE_LOG);
	public static final ColoredWoodBlock PINK_WOOD = registerColoredWood("pink_wood", PINK_LOG);
	public static final ColoredWoodBlock PURPLE_WOOD = registerColoredWood("purple_wood", PURPLE_LOG);
	public static final ColoredWoodBlock RED_WOOD = registerColoredWood("red_wood", RED_LOG);
	public static final ColoredWoodBlock WHITE_WOOD = registerColoredWood("white_wood", WHITE_LOG);
	public static final ColoredWoodBlock YELLOW_WOOD = registerColoredWood("yellow_wood", YELLOW_LOG);
	
	public static ColoredStrippedLogBlock registerColoredStrippedLog(String name, DyeColor color) {
		return register(log(blockWithItem(name, new ColoredStrippedLogBlock(copyWithMapColor(STRIPPED_OAK_LOG, color.getMapColor()), color), color)));
	}
	
	public static final ColoredStrippedLogBlock STRIPPED_BLACK_LOG = registerColoredStrippedLog("stripped_black_log", DyeColor.BLACK);
	public static final ColoredStrippedLogBlock STRIPPED_BLUE_LOG = registerColoredStrippedLog("stripped_blue_log", DyeColor.BLUE);
	public static final ColoredStrippedLogBlock STRIPPED_BROWN_LOG = registerColoredStrippedLog("stripped_brown_log", DyeColor.BROWN);
	public static final ColoredStrippedLogBlock STRIPPED_CYAN_LOG = registerColoredStrippedLog("stripped_cyan_log", DyeColor.CYAN);
	public static final ColoredStrippedLogBlock STRIPPED_GRAY_LOG = registerColoredStrippedLog("stripped_gray_log", DyeColor.GRAY);
	public static final ColoredStrippedLogBlock STRIPPED_GREEN_LOG = registerColoredStrippedLog("stripped_green_log", DyeColor.GREEN);
	public static final ColoredStrippedLogBlock STRIPPED_LIGHT_BLUE_LOG = registerColoredStrippedLog("stripped_light_blue_log", DyeColor.LIGHT_BLUE);
	public static final ColoredStrippedLogBlock STRIPPED_LIGHT_GRAY_LOG = registerColoredStrippedLog("stripped_light_gray_log", DyeColor.LIGHT_GRAY);
	public static final ColoredStrippedLogBlock STRIPPED_LIME_LOG = registerColoredStrippedLog("stripped_lime_log", DyeColor.LIME);
	public static final ColoredStrippedLogBlock STRIPPED_MAGENTA_LOG = registerColoredStrippedLog("stripped_magenta_log", DyeColor.MAGENTA);
	public static final ColoredStrippedLogBlock STRIPPED_ORANGE_LOG = registerColoredStrippedLog("stripped_orange_log", DyeColor.ORANGE);
	public static final ColoredStrippedLogBlock STRIPPED_PINK_LOG = registerColoredStrippedLog("stripped_pink_log", DyeColor.PINK);
	public static final ColoredStrippedLogBlock STRIPPED_PURPLE_LOG = registerColoredStrippedLog("stripped_purple_log", DyeColor.PURPLE);
	public static final ColoredStrippedLogBlock STRIPPED_RED_LOG = registerColoredStrippedLog("stripped_red_log", DyeColor.RED);
	public static final ColoredStrippedLogBlock STRIPPED_WHITE_LOG = registerColoredStrippedLog("stripped_white_log", DyeColor.WHITE);
	public static final ColoredStrippedLogBlock STRIPPED_YELLOW_LOG = registerColoredStrippedLog("stripped_yellow_log", DyeColor.YELLOW);
	
	public static ColoredStrippedWoodBlock registerColoredStrippedWood(String name, ColoredStrippedLogBlock logBlock) {
		return register(wood(blockWithItem(name, new ColoredStrippedWoodBlock(copyWithMapColor(STRIPPED_OAK_WOOD, logBlock.getDefaultMapColor()), logBlock.getColor()), logBlock.getColor()), logBlock));
	}
	
	public static final ColoredStrippedWoodBlock STRIPPED_BLACK_WOOD = registerColoredStrippedWood("stripped_black_wood", STRIPPED_BLACK_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_BLUE_WOOD = registerColoredStrippedWood("stripped_blue_wood", STRIPPED_BLUE_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_BROWN_WOOD = registerColoredStrippedWood("stripped_brown_wood", STRIPPED_BROWN_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_CYAN_WOOD = registerColoredStrippedWood("stripped_cyan_wood", STRIPPED_CYAN_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_GRAY_WOOD = registerColoredStrippedWood("stripped_gray_wood", STRIPPED_GRAY_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_GREEN_WOOD = registerColoredStrippedWood("stripped_green_wood", STRIPPED_GREEN_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_LIGHT_BLUE_WOOD = registerColoredStrippedWood("stripped_light_blue_wood", STRIPPED_LIGHT_BLUE_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_LIGHT_GRAY_WOOD = registerColoredStrippedWood("stripped_light_gray_wood", STRIPPED_LIGHT_GRAY_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_LIME_WOOD = registerColoredStrippedWood("stripped_lime_wood", STRIPPED_LIME_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_MAGENTA_WOOD = registerColoredStrippedWood("stripped_magenta_wood", STRIPPED_MAGENTA_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_ORANGE_WOOD = registerColoredStrippedWood("stripped_orange_wood", STRIPPED_ORANGE_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_PINK_WOOD = registerColoredStrippedWood("stripped_pink_wood", STRIPPED_PINK_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_PURPLE_WOOD = registerColoredStrippedWood("stripped_purple_wood", STRIPPED_PURPLE_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_RED_WOOD = registerColoredStrippedWood("stripped_red_wood", STRIPPED_RED_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_WHITE_WOOD = registerColoredStrippedWood("stripped_white_wood", STRIPPED_WHITE_LOG);
	public static final ColoredStrippedWoodBlock STRIPPED_YELLOW_WOOD = registerColoredStrippedWood("stripped_yellow_wood", STRIPPED_YELLOW_LOG);
	
	public static ColoredLeavesBlock registerColoredLeaves(String name, DyeColor color) {
		return register(singleton(blockWithItem(name, new ColoredLeavesBlock(copyWithMapColor(OAK_LEAVES, color.getMapColor()), color), color), TexturedModel.LEAVES));
	}
	
	public static final ColoredLeavesBlock BLACK_LEAVES = registerColoredLeaves("black_leaves", DyeColor.BLACK);
	public static final ColoredLeavesBlock BLUE_LEAVES = registerColoredLeaves("blue_leaves", DyeColor.BLUE);
	public static final ColoredLeavesBlock BROWN_LEAVES = registerColoredLeaves("brown_leaves", DyeColor.BROWN);
	public static final ColoredLeavesBlock CYAN_LEAVES = registerColoredLeaves("cyan_leaves", DyeColor.CYAN);
	public static final ColoredLeavesBlock GRAY_LEAVES = registerColoredLeaves("gray_leaves", DyeColor.GRAY);
	public static final ColoredLeavesBlock GREEN_LEAVES = registerColoredLeaves("green_leaves", DyeColor.GREEN);
	public static final ColoredLeavesBlock LIGHT_BLUE_LEAVES = registerColoredLeaves("light_blue_leaves", DyeColor.LIGHT_BLUE);
	public static final ColoredLeavesBlock LIGHT_GRAY_LEAVES = registerColoredLeaves("light_gray_leaves", DyeColor.LIGHT_GRAY);
	public static final ColoredLeavesBlock LIME_LEAVES = registerColoredLeaves("lime_leaves", DyeColor.LIME);
	public static final ColoredLeavesBlock MAGENTA_LEAVES = registerColoredLeaves("magenta_leaves", DyeColor.MAGENTA);
	public static final ColoredLeavesBlock ORANGE_LEAVES = registerColoredLeaves("orange_leaves", DyeColor.ORANGE);
	public static final ColoredLeavesBlock PINK_LEAVES = registerColoredLeaves("pink_leaves", DyeColor.PINK);
	public static final ColoredLeavesBlock PURPLE_LEAVES = registerColoredLeaves("purple_leaves", DyeColor.PURPLE);
	public static final ColoredLeavesBlock RED_LEAVES = registerColoredLeaves("red_leaves", DyeColor.RED);
	public static final ColoredLeavesBlock WHITE_LEAVES = registerColoredLeaves("white_leaves", DyeColor.WHITE);
	public static final ColoredLeavesBlock YELLOW_LEAVES = registerColoredLeaves("yellow_leaves", DyeColor.YELLOW);
	
	public static GlowBlock registerGlowBlock(String name, DyeColor color) {
		return register(simple(blockWithItem(name, new GlowBlock(settings(color.getMapColor(), BlockSoundGroup.BASALT, 2.5F).requiresTool().luminance(state -> 1).postProcess(SpectrumBlocks::always).emissiveLighting(SpectrumBlocks::always), color), color)));
	}
	
	public static final Block BLACK_GLOWBLOCK = registerGlowBlock("black_glowblock", DyeColor.BLACK);
	public static final Block BLUE_GLOWBLOCK = registerGlowBlock("blue_glowblock", DyeColor.BLUE);
	public static final Block BROWN_GLOWBLOCK = registerGlowBlock("brown_glowblock", DyeColor.BROWN);
	public static final Block CYAN_GLOWBLOCK = registerGlowBlock("cyan_glowblock", DyeColor.CYAN);
	public static final Block GRAY_GLOWBLOCK = registerGlowBlock("gray_glowblock", DyeColor.GRAY);
	public static final Block GREEN_GLOWBLOCK = registerGlowBlock("green_glowblock", DyeColor.GREEN);
	public static final Block LIGHT_BLUE_GLOWBLOCK = registerGlowBlock("light_blue_glowblock", DyeColor.LIGHT_BLUE);
	public static final Block LIGHT_GRAY_GLOWBLOCK = registerGlowBlock("light_gray_glowblock", DyeColor.LIGHT_GRAY);
	public static final Block LIME_GLOWBLOCK = registerGlowBlock("lime_glowblock", DyeColor.LIME);
	public static final Block MAGENTA_GLOWBLOCK = registerGlowBlock("magenta_glowblock", DyeColor.MAGENTA);
	public static final Block ORANGE_GLOWBLOCK = registerGlowBlock("orange_glowblock", DyeColor.ORANGE);
	public static final Block PINK_GLOWBLOCK = registerGlowBlock("pink_glowblock", DyeColor.PINK);
	public static final Block PURPLE_GLOWBLOCK = registerGlowBlock("purple_glowblock", DyeColor.PURPLE);
	public static final Block RED_GLOWBLOCK = registerGlowBlock("red_glowblock", DyeColor.RED);
	public static final Block WHITE_GLOWBLOCK = registerGlowBlock("white_glowblock", DyeColor.WHITE);
	public static final Block YELLOW_GLOWBLOCK = registerGlowBlock("yellow_glowblock", DyeColor.YELLOW);
	
	public static ColoredLightBlock registerColoredLightBlock(String name, DyeColor color) {
		return register(translucent(blockWithItem(name, new ColoredLightBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP).mapColor(color.getMapColor()), color), color))
				.withBlockModel((ctx, block) -> {
					Identifier off = TexturedModel.CUBE_ALL.upload(block, ctx.modelCollector);
					Identifier on = SpectrumModels.COLORED_LAMP_ON.upload(block, "_on", SpectrumTextureMaps.innerOuter(block, "_on", block, "_outer"), ctx.modelCollector);
					return VariantsBlockStateSupplier.create(block).coordinate(createBooleanModelMap(Properties.LIT, on, off));
				}));
	}
	
	public static final Block BLACK_LAMP = registerColoredLightBlock("black_lamp", DyeColor.BLACK);
	public static final Block BLUE_LAMP = registerColoredLightBlock("blue_lamp", DyeColor.BLUE);
	public static final Block BROWN_LAMP = registerColoredLightBlock("brown_lamp", DyeColor.BROWN);
	public static final Block CYAN_LAMP = registerColoredLightBlock("cyan_lamp", DyeColor.CYAN);
	public static final Block GRAY_LAMP = registerColoredLightBlock("gray_lamp", DyeColor.GRAY);
	public static final Block GREEN_LAMP = registerColoredLightBlock("green_lamp", DyeColor.GREEN);
	public static final Block LIGHT_BLUE_LAMP = registerColoredLightBlock("light_blue_lamp", DyeColor.LIGHT_BLUE);
	public static final Block LIGHT_GRAY_LAMP = registerColoredLightBlock("light_gray_lamp", DyeColor.LIGHT_GRAY);
	public static final Block LIME_LAMP = registerColoredLightBlock("lime_lamp", DyeColor.LIME);
	public static final Block MAGENTA_LAMP = registerColoredLightBlock("magenta_lamp", DyeColor.MAGENTA);
	public static final Block ORANGE_LAMP = registerColoredLightBlock("orange_lamp", DyeColor.ORANGE);
	public static final Block PINK_LAMP = registerColoredLightBlock("pink_lamp", DyeColor.PINK);
	public static final Block PURPLE_LAMP = registerColoredLightBlock("purple_lamp", DyeColor.PURPLE);
	public static final Block RED_LAMP = registerColoredLightBlock("red_lamp", DyeColor.RED);
	public static final Block WHITE_LAMP = registerColoredLightBlock("white_lamp", DyeColor.WHITE);
	public static final Block YELLOW_LAMP = registerColoredLightBlock("yellow_lamp", DyeColor.YELLOW);
	
	public static PigmentBlock registerPigmentBlock(String name, DyeColor color) {
		return register(simple(blockWithItem(name, new PigmentBlock(settings(color.getMapColor(), BlockSoundGroup.WOOL, 1.0F), color), color)));
	}
	
	public static final Block BLACK_BLOCK = registerPigmentBlock("black_block", DyeColor.BLACK);
	public static final Block BLUE_BLOCK = registerPigmentBlock("blue_block", DyeColor.BLUE);
	public static final Block BROWN_BLOCK = registerPigmentBlock("brown_block", DyeColor.BROWN);
	public static final Block CYAN_BLOCK = registerPigmentBlock("cyan_block", DyeColor.CYAN);
	public static final Block GRAY_BLOCK = registerPigmentBlock("gray_block", DyeColor.GRAY);
	public static final Block GREEN_BLOCK = registerPigmentBlock("green_block", DyeColor.GREEN);
	public static final Block LIGHT_BLUE_BLOCK = registerPigmentBlock("light_blue_block", DyeColor.LIGHT_BLUE);
	public static final Block LIGHT_GRAY_BLOCK = registerPigmentBlock("light_gray_block", DyeColor.LIGHT_GRAY);
	public static final Block LIME_BLOCK = registerPigmentBlock("lime_block", DyeColor.LIME);
	public static final Block MAGENTA_BLOCK = registerPigmentBlock("magenta_block", DyeColor.MAGENTA);
	public static final Block ORANGE_BLOCK = registerPigmentBlock("orange_block", DyeColor.ORANGE);
	public static final Block PINK_BLOCK = registerPigmentBlock("pink_block", DyeColor.PINK);
	public static final Block PURPLE_BLOCK = registerPigmentBlock("purple_block", DyeColor.PURPLE);
	public static final Block RED_BLOCK = registerPigmentBlock("red_block", DyeColor.RED);
	public static final Block WHITE_BLOCK = registerPigmentBlock("white_block", DyeColor.WHITE);
	public static final Block YELLOW_BLOCK = registerPigmentBlock("yellow_block", DyeColor.YELLOW);
	
	public static ColoredSporeBlossomBlock registerColoredSporeBlossomBlock(String name, DyeColor color) {
		return register(cutout(singleton(blockWithItem(name, new ColoredSporeBlossomBlock(AbstractBlock.Settings.copy(Blocks.SPORE_BLOSSOM).mapColor(color.getMapColor()), color), color), TexturedModel.makeFactory(b -> SpectrumTextureMaps.flowerParticle(b, "", b, ""), SpectrumModels.SPORE_BLOSSOM))));
	}
	
	public static final Block BLACK_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("black_spore_blossom", DyeColor.BLACK);
	public static final Block BLUE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("blue_spore_blossom", DyeColor.BLUE);
	public static final Block BROWN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("brown_spore_blossom", DyeColor.BROWN);
	public static final Block CYAN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("cyan_spore_blossom", DyeColor.CYAN);
	public static final Block GRAY_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("gray_spore_blossom", DyeColor.GRAY);
	public static final Block GREEN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("green_spore_blossom", DyeColor.GREEN);
	public static final Block LIGHT_BLUE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("light_blue_spore_blossom", DyeColor.LIGHT_BLUE);
	public static final Block LIGHT_GRAY_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("light_gray_spore_blossom", DyeColor.LIGHT_GRAY);
	public static final Block LIME_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("lime_spore_blossom", DyeColor.LIME);
	public static final Block MAGENTA_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("magenta_spore_blossom", DyeColor.MAGENTA);
	public static final Block ORANGE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("orange_spore_blossom", DyeColor.ORANGE);
	public static final Block PINK_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("pink_spore_blossom", DyeColor.PINK);
	public static final Block PURPLE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("purple_spore_blossom", DyeColor.PURPLE);
	public static final Block RED_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("red_spore_blossom", DyeColor.RED);
	public static final Block WHITE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("white_spore_blossom", DyeColor.WHITE);
	public static final Block YELLOW_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("yellow_spore_blossom", DyeColor.YELLOW);
	
	public static ShimmerstoneLightBlock registerShimmerstoneLight(String name, BlockSoundGroup soundGroup, Supplier<Identifier> outerSupplier) {
		return register(blockWithItem(name, new ShimmerstoneLightBlock(settings(MapColor.CLEAR, soundGroup, 1.0F).nonOpaque().requiresTool().luminance(state -> 15)), DyeColor.YELLOW)
				.withBlockModel((ctx, block) -> {
					Identifier outer = outerSupplier.get();
					Identifier base = SpectrumModels.BASE_FLAT_LIGHT.upload(block, SpectrumTextureMaps.innerOuterParticle(SpectrumTextures.SHIMMERSTONE_LIGHT, outer, outer), ctx.modelCollector);
					Identifier mirrored = SpectrumModels.BASE_FLAT_LIGHT_MIRRORED.upload(block, "_mirrored", SpectrumTextureMaps.innerOuterParticle(SpectrumTextures.SHIMMERSTONE_LIGHT, outer, outer), ctx.modelCollector);
					return VariantsBlockStateSupplier.create(block).coordinate(createNorthDefaultRotationStates()).coordinate(createBooleanModelMap(Properties.INVERTED, mirrored, base));
				}));
	}
	
	public static final Block STONE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("stone_shimmerstone_light", BlockSoundGroup.STONE, () -> SpectrumTextures.STONE_FLAT_LIGHT);
	public static final Block BASALT_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("basalt_shimmerstone_light", BlockSoundGroup.BASALT, () -> SpectrumTextures.BASALT_FLAT_LIGHT);
	public static final Block CALCITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("calcite_shimmerstone_light", BlockSoundGroup.CALCITE, () -> SpectrumTextures.CALCITE_FLAT_LIGHT);
	public static final Block DEEPSLATE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("deepslate_shimmerstone_light", BlockSoundGroup.DEEPSLATE, () -> SpectrumTextures.DEEPSLATE_FLAT_LIGHT);
	public static final Block BLACKSLAG_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("blackslag_shimmerstone_light", BlockSoundGroup.DEEPSLATE, () -> SpectrumTextures.BLACKSLAG_FLAT_LIGHT);
	public static final Block GRANITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("granite_shimmerstone_light", BlockSoundGroup.STONE, () -> ModelIds.getBlockModelId(POLISHED_GRANITE));
	public static final Block DIORITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("diorite_shimmerstone_light", BlockSoundGroup.STONE, () -> ModelIds.getBlockModelId(POLISHED_DIORITE));
	public static final Block ANDESITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("andesite_shimmerstone_light", BlockSoundGroup.STONE, () -> ModelIds.getBlockModelId(POLISHED_ANDESITE));
	
	// CRYSTALLARIEUM
	private static Settings crystallarieumGrowable(Block baseBlock) {
		return AbstractBlock.Settings.copy(baseBlock).strength(1.5F).nonOpaque().solid().requiresTool().pistonBehavior(PistonBehavior.DESTROY);
	}
	
	public static final Block SMALL_COAL_BUD = register(cluster(blockWithItem("small_coal_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.COAL_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_COAL_BUD = register(cluster(blockWithItem("large_coal_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_COAL_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block COAL_CLUSTER = register(cluster(blockWithItem("coal_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_COAL_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_IRON_BUD = register(cluster(blockWithItem("small_iron_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.IRON_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_IRON_BUD = register(cluster(blockWithItem("large_iron_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_IRON_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block IRON_CLUSTER = register(cluster(blockWithItem("iron_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_IRON_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_GOLD_BUD = register(cluster(blockWithItem("small_gold_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.GOLD_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_GOLD_BUD = register(cluster(blockWithItem("large_gold_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GOLD_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block GOLD_CLUSTER = register(cluster(blockWithItem("gold_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GOLD_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_DIAMOND_BUD = register(cluster(blockWithItem("small_diamond_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.DIAMOND_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_DIAMOND_BUD = register(cluster(blockWithItem("large_diamond_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_DIAMOND_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block DIAMOND_CLUSTER = register(cluster(blockWithItem("diamond_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_DIAMOND_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_EMERALD_BUD = register(cluster(blockWithItem("small_emerald_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.EMERALD_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_EMERALD_BUD = register(cluster(blockWithItem("large_emerald_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_EMERALD_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block EMERALD_CLUSTER = register(cluster(blockWithItem("emerald_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_EMERALD_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_REDSTONE_BUD = register(cluster(blockWithItem("small_redstone_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.REDSTONE_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_REDSTONE_BUD = register(cluster(blockWithItem("large_redstone_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_REDSTONE_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block REDSTONE_CLUSTER = register(cluster(blockWithItem("redstone_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_REDSTONE_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_LAPIS_BUD = register(cluster(blockWithItem("small_lapis_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.LAPIS_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.PURPLE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_LAPIS_BUD = register(cluster(blockWithItem("large_lapis_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_LAPIS_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.PURPLE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LAPIS_CLUSTER = register(cluster(blockWithItem("lapis_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_LAPIS_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.PURPLE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_COPPER_BUD = register(cluster(blockWithItem("small_copper_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.COPPER_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_COPPER_BUD = register(cluster(blockWithItem("large_copper_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_COPPER_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block COPPER_CLUSTER = register(cluster(blockWithItem("copper_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_COPPER_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_QUARTZ_BUD = register(cluster(blockWithItem("small_quartz_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.QUARTZ_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_QUARTZ_BUD = register(cluster(blockWithItem("large_quartz_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block QUARTZ_CLUSTER = register(cluster(blockWithItem("quartz_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_NETHERITE_SCRAP_BUD = register(cluster(blockWithItem("small_netherite_scrap_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.ANCIENT_DEBRIS), SpectrumClusterBlock.GrowthStage.SMALL), IS.of().fireproof(), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_NETHERITE_SCRAP_BUD = register(cluster(blockWithItem("large_netherite_scrap_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_NETHERITE_SCRAP_BUD), SpectrumClusterBlock.GrowthStage.LARGE), IS.of().fireproof(), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block NETHERITE_SCRAP_CLUSTER = register(cluster(blockWithItem("netherite_scrap_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_NETHERITE_SCRAP_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of().fireproof(), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_ECHO_BUD = register(cluster(blockWithItem("small_echo_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.SCULK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_ECHO_BUD = register(cluster(blockWithItem("large_echo_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_ECHO_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block ECHO_CLUSTER = register(cluster(blockWithItem("echo_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_ECHO_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_GLOWSTONE_BUD = register(cluster(blockWithItem("small_glowstone_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.GLOWSTONE).luminance(state -> 4), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_GLOWSTONE_BUD = register(cluster(blockWithItem("large_glowstone_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOWSTONE_BUD).luminance(state -> 8), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block GLOWSTONE_CLUSTER = register(cluster(blockWithItem("glowstone_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOWSTONE_BUD).luminance(state -> 14), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block SMALL_PRISMARINE_BUD = register(cluster(blockWithItem("small_prismarine_bud", new SpectrumClusterBlock(crystallarieumGrowable(Blocks.SCULK), SpectrumClusterBlock.GrowthStage.SMALL), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block LARGE_PRISMARINE_BUD = register(cluster(blockWithItem("large_prismarine_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_PRISMARINE_BUD), SpectrumClusterBlock.GrowthStage.LARGE), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final Block PRISMARINE_CLUSTER = register(cluster(blockWithItem("prismarine_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_PRISMARINE_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), DyeColor.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	
	public static final Block PURE_COAL_BLOCK = register(simple(blockWithItem("pure_coal_block", new Block(AbstractBlock.Settings.copy(Blocks.COAL_BLOCK)), DyeColor.BROWN)));
	public static final Block PURE_IRON_BLOCK = register(simple(blockWithItem("pure_iron_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), DyeColor.BROWN)));
	public static final Block PURE_GOLD_BLOCK = register(simple(blockWithItem("pure_gold_block", new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)), DyeColor.BROWN)));
	public static final Block PURE_DIAMOND_BLOCK = register(simple(blockWithItem("pure_diamond_block", new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)), DyeColor.CYAN)));
	public static final Block PURE_EMERALD_BLOCK = register(simple(blockWithItem("pure_emerald_block", new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)), DyeColor.CYAN)));
	public static final Block PURE_REDSTONE_BLOCK = register(simple(blockWithItem("pure_redstone_block", new PureRedstoneBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_BLOCK)), DyeColor.RED)));
	public static final Block PURE_LAPIS_BLOCK = register(simple(blockWithItem("pure_lapis_block", new Block(AbstractBlock.Settings.copy(Blocks.LAPIS_BLOCK)), DyeColor.PURPLE)));
	public static final Block PURE_COPPER_BLOCK = register(simple(blockWithItem("pure_copper_block", new Block(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK)), DyeColor.BROWN)));
	public static final Block PURE_QUARTZ_BLOCK = register(simple(blockWithItem("pure_quartz_block", new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), DyeColor.BROWN)));
	public static final Block PURE_GLOWSTONE_BLOCK = register(simple(blockWithItem("pure_glowstone_block", new Block(AbstractBlock.Settings.copy(Blocks.GLOWSTONE)), DyeColor.YELLOW)));
	public static final Block PURE_PRISMARINE_BLOCK = register(simple(blockWithItem("pure_prismarine_block", new Block(AbstractBlock.Settings.copy(Blocks.PRISMARINE)), DyeColor.CYAN)));
	public static final Block PURE_NETHERITE_SCRAP_BLOCK = register(simple(blockWithItem("pure_netherite_scrap_block", new Block(AbstractBlock.Settings.copy(Blocks.ANCIENT_DEBRIS)), IS.of().fireproof(), DyeColor.BROWN)));
	public static final Block PURE_ECHO_BLOCK = register(simple(blockWithItem("pure_echo_block", new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)), DyeColor.BROWN)));
	
	private static Settings preservationBlock() {
		return settings(MapColor.LIGHT_BLUE_GRAY, BlockSoundGroup.STONE, -1.0F, 3600000.0F).instrument(NoteBlockInstrument.BASEDRUM).dropsNothing().allowsSpawning(SpectrumBlocks::never);
	}
	
	public static final Block PRESERVATION_CONTROLLER = new PreservationControllerBlock(preservationBlock().luminance(state -> 1).emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always));
	public static final Block DIKE_GATE = new DikeGateBlock(preservationBlock().luminance(state -> 3).sounds(BlockSoundGroup.GLASS).nonOpaque().emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always).solidBlock(SpectrumBlocks::never).suffocates(SpectrumBlocks::never).blockVision(SpectrumBlocks::never));
	public static final Block DREAM_GATE = new DreamGateBlock(preservationBlock().luminance(state -> 3).sounds(BlockSoundGroup.GLASS).nonOpaque().emissiveLighting(SpectrumBlocks::always).postProcess(SpectrumBlocks::always).solidBlock(SpectrumBlocks::never).suffocates(SpectrumBlocks::never).blockVision(SpectrumBlocks::never));
	public static final Block INVISIBLE_WALL = new InvisibleWallBlock(preservationBlock().luminance(state -> 3).sounds(BlockSoundGroup.GLASS).nonOpaque().blockVision(SpectrumBlocks::never));
	public static final Block PRESERVATION_CHEST = new TreasureChestBlock(preservationBlock());
	
	public static final Block DOWNSTONE = new Block(preservationBlock()); // "raw" preservation stone, used in the Deeper Down bottom in place of bedrock
	
	public static final Block PRESERVATION_STONE = register(blockWithItem("preservation_stone", new Block(preservationBlock()), DyeColor.BLUE));
	public static final Block PRESERVATION_STAIRS = register(blockWithItem("preservation_stairs", new StairsBlock(PRESERVATION_STONE.getDefaultState(), preservationBlock()), DyeColor.BLUE));
	public static final Block PRESERVATION_SLAB = register(blockWithItem("preservation_slab", new SlabBlock(preservationBlock()), DyeColor.BLUE));
	public static final Block PRESERVATION_WALL = register(blockWithItem("preservation_wall", new WallBlock(preservationBlock()), DyeColor.BLUE));
	public static final BlockFamily PRESERVATION_STONE_FAMILY = registerBlockFamilyExceptBase(new BlockFamily.Builder(PRESERVATION_STONE).stairs(PRESERVATION_STAIRS).slab(PRESERVATION_SLAB).wall(PRESERVATION_WALL).build(), TexturedModel.CUBE_ALL);
	
	public static final Block POWDER_CHISELED_PRESERVATION_STONE = new Block(preservationBlock().luminance(state -> 2));
	public static final Block DIKE_CHISELED_PRESERVATION_STONE = new Block(preservationBlock().luminance(state -> 6));
	public static final Block DREAM_CHISELED_PRESERVATION_STONE = new Block(preservationBlock().luminance(state -> 6));
	public static final Block DEEP_LIGHT_CHISELED_PRESERVATION_STONE = new DeepLightBlock(preservationBlock().luminance(state -> 2));
	public static final Block TREASURE_ITEM_BOWL = new TreasureItemBowlBlock(preservationBlock().nonOpaque().solidBlock(SpectrumBlocks::never).suffocates(SpectrumBlocks::never).blockVision(SpectrumBlocks::never));
	public static final Block DIKE_GATE_FOUNTAIN = new SpectrumFacingBlock(preservationBlock());
	public static final Block PRESERVATION_BRICKS = new Block(preservationBlock());
	public static final Block SHIMMERING_PRESERVATION_BRICKS = new Block(preservationBlock().luminance(s -> 5));
	public static final Block COURIER_STATUE = new StatueBlock(preservationBlock());
	public static final Block MANXI = new ManxiBlock(preservationBlock().nonOpaque().noCollision().dropsNothing());
	
	public static final Block BLACK_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("black_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.BLACK), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BLUE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("blue_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.BLUE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block BROWN_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("brown_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.BROWN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block CYAN_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("cyan_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.CYAN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block GRAY_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("gray_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.GRAY), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block GREEN_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("green_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.GREEN), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block LIGHT_BLUE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("light_blue_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.LIGHT_BLUE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block LIGHT_GRAY_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("light_gray_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.LIGHT_GRAY), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block LIME_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("lime_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.LIME), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block MAGENTA_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("magenta_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.MAGENTA), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block ORANGE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("orange_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.ORANGE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block PINK_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("pink_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.PINK), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block PURPLE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("purple_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.PURPLE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block RED_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("red_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.RED), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block WHITE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("white_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.WHITE), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	public static final Block YELLOW_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("yellow_chiseled_preservation_stone", new Block(preservationBlock()), DyeColor.YELLOW), TexturedModel.END_FOR_TOP_CUBE_COLUMN));
	
	public static final Block PRESERVATION_GLASS = register(translucent(simple(blockWithItem("preservation_glass", new TransparentBlock(preservationBlock().sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(SpectrumBlocks::never).suffocates(SpectrumBlocks::never).blockVision(SpectrumBlocks::never)), DyeColor.BLUE))));
	public static final Block TINTED_PRESERVATION_GLASS = register(translucent(simple(blockWithItem("tinted_preservation_glass", new TintedGlassBlock(AbstractBlock.Settings.copy(PRESERVATION_GLASS)), DyeColor.BLUE))));
	public static final Block PRESERVATION_ROUNDEL = new PreservationRoundelBlock(preservationBlock().nonOpaque());
	public static final Block PRESERVATION_BLOCK_DETECTOR = new PreservationBlockDetectorBlock(preservationBlock());
	
	private static Settings shootingStar() {
		return AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque();
	}
	
	public static final ShootingStarBlock GLISTERING_SHOOTING_STAR = new ShootingStarBlock(shootingStar(), ShootingStar.Type.GLISTERING);
	public static final ShootingStarBlock FIERY_SHOOTING_STAR = new ShootingStarBlock(shootingStar(), ShootingStar.Type.FIERY);
	public static final ShootingStarBlock COLORFUL_SHOOTING_STAR = new ShootingStarBlock(shootingStar(), ShootingStar.Type.COLORFUL);
	public static final ShootingStarBlock PRISTINE_SHOOTING_STAR = new ShootingStarBlock(shootingStar(), ShootingStar.Type.PRISTINE);
	public static final ShootingStarBlock GEMSTONE_SHOOTING_STAR = new ShootingStarBlock(shootingStar(), ShootingStar.Type.GEMSTONE);
	public static final Block STARDUST_BLOCK = new ColoredFallingBlock(new ColorCode(DyeColor.PURPLE.getFireworkColor()), AbstractBlock.Settings.copy(Blocks.SAND).mapColor(MapColor.PURPLE));
	
	public static final Block INCANDESCENT_AMALGAM = new IncandescentAmalgamBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque());
	
	private static Settings idol(BlockSoundGroup soundGroup) {
		return settings(MapColor.TERRACOTTA_WHITE, soundGroup, 3.0F).requiresTool().nonOpaque();
	}
	
	public static final Block AXOLOTL_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.AXOLOTL_IDOL), ParticleTypes.HEART, StatusEffects.REGENERATION, 0, 100); // heals 2 hp / 1 heart
	public static final Block BAT_IDOL = new AoEStatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.BAT_IDOL), ParticleTypes.INSTANT_EFFECT, StatusEffects.GLOWING, 0, 200, 8);
	public static final Block BEE_IDOL = new BonemealingIdolBlock(idol(SpectrumBlockSoundGroups.BEE_IDOL), ParticleTypes.DRIPPING_HONEY);
	public static final Block BLAZE_IDOL = new FirestarterIdolBlock(idol(SpectrumBlockSoundGroups.BLAZE_IDOL), ParticleTypes.FLAME);
	public static final Block CAT_IDOL = new FallDamageNegatingIdolBlock(idol(SpectrumBlockSoundGroups.CAT_IDOL), ParticleTypes.ENCHANTED_HIT);
	public static final Block CHICKEN_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.CHICKEN_IDOL), ParticleTypes.ENCHANTED_HIT, StatusEffects.SLOW_FALLING, 0, 100);
	public static final Block COW_IDOL = new MilkingIdolBlock(idol(SpectrumBlockSoundGroups.COW_IDOL), ParticleTypes.ENCHANTED_HIT, 6);
	public static final Block CREEPER_IDOL = new ExplosionIdolBlock(idol(SpectrumBlockSoundGroups.CREEPER_IDOL), ParticleTypes.EXPLOSION, 3, false, Explosion.DestructionType.DESTROY);
	public static final Block ENDER_DRAGON_IDOL = new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.ENDER_DRAGON_IDOL), ParticleTypes.DRAGON_BREATH, EntityType.DRAGON_FIREBALL, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, 6.0F, 1.1F) {
		@Override
		public ProjectileEntity createProjectile(ServerWorld world, BlockPos mobBlockPos, Position position, Direction side) {
			LivingMarkerEntity markerEntity = new LivingMarkerEntity(SpectrumEntityTypes.LIVING_MARKER, world);
			markerEntity.setPos(position.getX(), position.getY(), position.getZ());
			
			Vec3d targetPosition = Vec3d.ofCenter(mobBlockPos.offset(side, 50));
			var velocity = targetPosition.subtract(markerEntity.getPos());
			
			DragonFireballEntity entity = new DragonFireballEntity(world, markerEntity, velocity);
			
			markerEntity.discard();
			return entity;
		}
	};
	public static final Block ENDERMAN_IDOL = new RandomTeleportingIdolBlock(idol(SpectrumBlockSoundGroups.ENDERMAN_IDOL), ParticleTypes.REVERSE_PORTAL, 16, 16);
	public static final Block ENDERMITE_IDOL = new LineTeleportingIdolBlock(idol(SpectrumBlockSoundGroups.ENDERMITE_IDOL), ParticleTypes.REVERSE_PORTAL, 16);
	public static final Block EVOKER_IDOL = new EntitySummoningIdolBlock(idol(SpectrumBlockSoundGroups.EVOKER_IDOL), ParticleTypes.ANGRY_VILLAGER, EntityType.VEX) {
		@Override
		public void afterSummon(ServerWorld world, Entity entity) {
			((VexEntity) entity).setLifeTicks(20 * (30 + world.random.nextInt(90)));
		}
	};
	public static final Block FISH_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.FISH_IDOL), ParticleTypes.SPLASH, StatusEffects.WATER_BREATHING, 0, 200);
	public static final Block FOX_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.FOX_IDOL), ParticleTypes.ENCHANTED_HIT, StatusEffects.HASTE, 0, 200);
	public static final Block GHAST_IDOL = new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.GHAST_IDOL), ParticleTypes.SMOKE, EntityType.FIREBALL, SoundEvents.ENTITY_GHAST_SHOOT, 6.0F, 1.1F) {
		@Override
		public ProjectileEntity createProjectile(ServerWorld world, BlockPos mobBlockPos, Position position, Direction side) {
			LivingMarkerEntity markerEntity = new LivingMarkerEntity(SpectrumEntityTypes.LIVING_MARKER, world);
			markerEntity.setPos(position.getX(), position.getY(), position.getZ());
			
			Vec3d targetPosition = Vec3d.ofCenter(mobBlockPos.offset(side, 50));
			var velocity = targetPosition.subtract(markerEntity.getPos());
			
			FireballEntity entity = new FireballEntity(world, markerEntity, velocity, 1);
			
			markerEntity.discard();
			return entity;
		}
	};
	public static final Block GLOW_SQUID_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.GLOW_SQUID_IDOL), ParticleTypes.GLOW_SQUID_INK, StatusEffects.GLOWING, 0, 200);
	public static final Block GOAT_IDOL = new KnockbackIdolBlock(idol(SpectrumBlockSoundGroups.GOAT_IDOL), ParticleTypes.ENCHANTED_HIT, 5.0F, 0.5F); // knocks mostly sideways
	public static final Block GUARDIAN_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.GUARDIAN_IDOL), ParticleTypes.BUBBLE, StatusEffects.MINING_FATIGUE, 2, 200);
	public static final Block HORSE_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.HORSE_IDOL), ParticleTypes.INSTANT_EFFECT, StatusEffects.STRENGTH, 0, 100);
	public static final Block ILLUSIONER_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.ILLUSIONER_IDOL), ParticleTypes.ANGRY_VILLAGER, StatusEffects.INVISIBILITY, 0, 100);
	public static final Block OCELOT_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.OCELOT_IDOL), ParticleTypes.INSTANT_EFFECT, StatusEffects.NIGHT_VISION, 0, 100);
	public static final Block PARROT_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.PARROT_IDOL), ParticleTypes.INSTANT_EFFECT, StatusEffects.ABSORPTION, 0, 100);
	public static final Block PHANTOM_IDOL = new InsomniaIdolBlock(idol(SpectrumBlockSoundGroups.PHANTOM_IDOL), ParticleTypes.POOF, 24000); // +1 ingame day without sleep
	public static final Block PIG_IDOL = new FeedingIdolBlock(idol(SpectrumBlockSoundGroups.PIG_IDOL), ParticleTypes.INSTANT_EFFECT, 6);
	public static final Block PIGLIN_IDOL = new PiglinTradeIdolBlock(idol(SpectrumBlockSoundGroups.PIGLIN_IDOL), ParticleTypes.HEART);
	public static final Block POLAR_BEAR_IDOL = new FreezingIdolBlock(idol(SpectrumBlockSoundGroups.POLAR_BEAR_IDOL), ParticleTypes.SNOWFLAKE);
	public static final Block PUFFERFISH_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.PUFFERFISH_IDOL), ParticleTypes.SPLASH, StatusEffects.NAUSEA, 0, 200);
	public static final Block RABBIT_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.RABBIT_IDOL), ParticleTypes.INSTANT_EFFECT, StatusEffects.JUMP_BOOST, 3, 100);
	public static final Block SHEEP_IDOL = new ShearingIdolBlock(idol(SpectrumBlockSoundGroups.SHEEP_IDOL), ParticleTypes.ENCHANTED_HIT, 6);
	public static final Block SHULKER_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.SHULKER_IDOL), ParticleTypes.END_ROD, StatusEffects.LEVITATION, 0, 100);
	public static final Block SILVERFISH_IDOL = new SilverfishInsertingIdolBlock(idol(SpectrumBlockSoundGroups.SILVERFISH_IDOL), ParticleTypes.EXPLOSION);
	public static final Block SKELETON_IDOL = new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.SKELETON_IDOL), ParticleTypes.INSTANT_EFFECT, EntityType.ARROW, SoundEvents.ENTITY_ARROW_SHOOT, 6.0F, 1.1F) {
		@Override
		public ProjectileEntity createProjectile(ServerWorld world, BlockPos mobBlockPos, Position position, Direction side) {
			ArrowEntity arrowEntity = new ArrowEntity(world, position.getX(), position.getY(), position.getZ(), ItemStack.EMPTY, null);
			arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
			return arrowEntity;
		}
	};
	public static final Block SLIME_IDOL = new SlimeSizingIdolBlock(idol(SpectrumBlockSoundGroups.SLIME_IDOL), ParticleTypes.ITEM_SLIME, 6, 8);
	public static final Block SNOW_GOLEM_IDOL = new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.SNOW_GOLEM_IDOL), ParticleTypes.SNOWFLAKE, EntityType.SNOWBALL, SoundEvents.ENTITY_ARROW_SHOOT, 3.0F, 1.1F) {
		@Override
		public ProjectileEntity createProjectile(ServerWorld world, BlockPos mobBlockPos, Position position, Direction side) {
			world.playSound(null, mobBlockPos.getX(), mobBlockPos.getY(), mobBlockPos.getZ(), SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, SoundCategory.BLOCKS, 1.0F, 0.4F / world.random.nextFloat() * 0.4F + 0.8F);
			return new SnowballEntity(world, position.getX(), position.getY(), position.getZ());
		}
	};
	public static final Block SPIDER_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.SPIDER_IDOL), ParticleTypes.ENCHANTED_HIT, StatusEffects.POISON, 0, 100);
	public static final Block SQUID_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.SQUID_IDOL), ParticleTypes.SQUID_INK, StatusEffects.BLINDNESS, 0, 200);
	public static final Block STRAY_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.STRAY_IDOL), ParticleTypes.ENCHANTED_HIT, StatusEffects.SLOWNESS, 2, 100);
	public static final Block STRIDER_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.STRIDER_IDOL), ParticleTypes.DRIPPING_LAVA, StatusEffects.FIRE_RESISTANCE, 0, 200);
	public static final Block TURTLE_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.TURTLE_IDOL), ParticleTypes.DRIPPING_WATER, StatusEffects.RESISTANCE, 1, 200);
	public static final Block WITCH_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.WITCH_IDOL), ParticleTypes.ENCHANTED_HIT, StatusEffects.WEAKNESS, 0, 200);
	public static final Block WITHER_IDOL = new ExplosionIdolBlock(idol(SpectrumBlockSoundGroups.WITHER_IDOL), ParticleTypes.EXPLOSION, 7.0F, true, Explosion.DestructionType.DESTROY);
	public static final Block WITHER_SKELETON_IDOL = new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.WITHER_SKELETON_IDOL), ParticleTypes.ENCHANTED_HIT, StatusEffects.WITHER, 0, 100);
	public static final Block ZOMBIE_IDOL = new VillagerConvertingIdolBlock(idol(SpectrumBlockSoundGroups.ZOMBIE_IDOL), ParticleTypes.ENCHANTED_HIT);
	
	// FLUIDS
	private static Settings fluid(MapColor mapColor) {
		return settings(mapColor, BlockSoundGroup.INTENTIONALLY_EMPTY, 100.0F).replaceable().noCollision().pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid();
	}
	
	public static final Block LIQUID_CRYSTAL = new LiquidCrystalFluidBlock(SpectrumFluids.LIQUID_CRYSTAL, SpectrumBlocks.BLAZING_CRYSTAL.getDefaultState(), fluid(MapColor.DULL_PINK).luminance((state) -> LiquidCrystalFluidBlock.LUMINANCE).replaceable());
	public static final Block GOO = new GooFluidBlock(SpectrumFluids.GOO, Blocks.MUD.getDefaultState(), fluid(MapColor.TERRACOTTA_BROWN).replaceable());
	public static final Block MIDNIGHT_SOLUTION = new MidnightSolutionFluidBlock(SpectrumFluids.MIDNIGHT_SOLUTION, SpectrumBlocks.BLACK_MATERIA.getDefaultState(), fluid(MapColor.DARK_AQUA).replaceable());
	public static final Block DRAGONROT = new DragonrotFluidBlock(SpectrumFluids.DRAGONROT, Blocks.BLACKSTONE.getDefaultState(), fluid(MapColor.PALE_PURPLE).luminance((state) -> 15).replaceable());
	
	static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return false;
	}
	
	static boolean always(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
	static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return false;
	}
	
	static void registerBlock(String name, Block block) {
		registerBlock(RegistryKey.of(RegistryKeys.BLOCK, locate(name)), block);
	}
	
	static void registerBlock(RegistryKey<Block> key, Block block) {
		Registry.register(Registries.BLOCK, key, block);
	}
	
	static void registerBlockItem(String name, BlockItem blockItem, DyeColor dyeColor) {
		Registry.register(Registries.ITEM, locate(name), blockItem);
		ItemColors.ITEM_COLORS.registerColorMapping(blockItem, dyeColor);
	}
	
	public static void registerBlockWithItem(String name, Block block, Item.Settings itemSettings, DyeColor dyeColor) {
		Registry.register(Registries.BLOCK, locate(name), block);
		BlockItem blockItem = new BlockItem(block, itemSettings);
		Registry.register(Registries.ITEM, locate(name), blockItem);
		ItemColors.ITEM_COLORS.registerColorMapping(blockItem, dyeColor);
	}
	
	public static void registerBlockWithItem(RegistryKey<Block> key, Block block, Item.Settings itemSettings, DyeColor dyeColor) {
		Registry.register(Registries.BLOCK, key, block);
		var blockItem = new BlockItem(block, itemSettings);
		Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, key.getValue()), blockItem);
		ItemColors.ITEM_COLORS.registerColorMapping(blockItem, dyeColor);
	}
	
	public static void registerBlockWithItem(String name, Block block, BlockItem blockItem, DyeColor dyeColor) {
		Registry.register(Registries.BLOCK, locate(name), block);
		Registry.register(Registries.ITEM, locate(name), blockItem);
		ItemColors.ITEM_COLORS.registerColorMapping(blockItem, dyeColor);
	}
	
	public static RegistryKey<Block> keyOf(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, locate(name));
	}
	
	public static <T> T registerCustom(T data, Consumer<T> callback) {
		callback.accept(data);
		return data;
	}
	
	public static void register() {
		registerBlockWithItem("pedestal_basic_topaz", PEDESTAL_BASIC_TOPAZ, new PedestalBlockItem(PEDESTAL_BASIC_TOPAZ, IS.of(1), BuiltinPedestalVariant.BASIC_TOPAZ, "item.spectrum.pedestal.tooltip.basic_topaz"), DyeColor.WHITE);
		registerBlockWithItem("pedestal_basic_amethyst", PEDESTAL_BASIC_AMETHYST, new PedestalBlockItem(PEDESTAL_BASIC_AMETHYST, IS.of(1), BuiltinPedestalVariant.BASIC_AMETHYST, "item.spectrum.pedestal.tooltip.basic_amethyst"), DyeColor.WHITE);
		registerBlockWithItem("pedestal_basic_citrine", PEDESTAL_BASIC_CITRINE, new PedestalBlockItem(PEDESTAL_BASIC_CITRINE, IS.of(1), BuiltinPedestalVariant.BASIC_CITRINE, "item.spectrum.pedestal.tooltip.basic_citrine"), DyeColor.WHITE);
		registerBlockWithItem("pedestal_all_basic", PEDESTAL_ALL_BASIC, new PedestalBlockItem(PEDESTAL_ALL_BASIC, IS.of(1), BuiltinPedestalVariant.CMY, "item.spectrum.pedestal.tooltip.all_basic"), DyeColor.WHITE);
		registerBlockWithItem("pedestal_onyx", PEDESTAL_ONYX, new PedestalBlockItem(PEDESTAL_ONYX, IS.of(1), BuiltinPedestalVariant.ONYX, "item.spectrum.pedestal.tooltip.onyx"), DyeColor.WHITE);
		registerBlockWithItem("pedestal_moonstone", PEDESTAL_MOONSTONE, new PedestalBlockItem(PEDESTAL_MOONSTONE, IS.of(1), BuiltinPedestalVariant.MOONSTONE, "item.spectrum.pedestal.tooltip.moonstone"), DyeColor.WHITE);
		registerBlockWithItem("fusion_shrine_basalt", FUSION_SHRINE_BASALT, IS.of(1), DyeColor.GRAY);
		registerBlockWithItem("fusion_shrine_calcite", FUSION_SHRINE_CALCITE, IS.of(1), DyeColor.GRAY);
		registerBlockWithItem("enchanter", ENCHANTER, IS.of(1), DyeColor.PURPLE);
		registerBlockWithItem("item_bowl_basalt", ITEM_BOWL_BASALT, IS.of(16), DyeColor.PINK);
		registerBlockWithItem("item_bowl_calcite", ITEM_BOWL_CALCITE, IS.of(16), DyeColor.PINK);
		registerBlockWithItem("item_roundel", ITEM_ROUNDEL, IS.of(16), DyeColor.PINK);
		registerBlockWithItem("potion_workshop", POTION_WORKSHOP, IS.of(1), DyeColor.PURPLE);
		registerBlockWithItem("spirit_instiller", SPIRIT_INSTILLER, IS.of(1), DyeColor.WHITE);
		registerBlockWithItem("crystallarieum", CRYSTALLARIEUM, IS.of(1), DyeColor.BROWN);
		registerBlockWithItem("cinderhearth", CINDERHEARTH, IS.of(1).fireproof(), DyeColor.ORANGE);
		registerBlockWithItem("crystal_apothecary", CRYSTAL_APOTHECARY, IS.of(8), DyeColor.GREEN);
		registerBlockWithItem("color_picker", COLOR_PICKER, IS.of(8), DyeColor.GREEN);
		
		registerBlockWithItem("upgrade_speed", UPGRADE_SPEED, new UpgradeBlockItem(UPGRADE_SPEED, IS.of(16), "upgrade_speed"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_speed2", UPGRADE_SPEED2, new UpgradeBlockItem(UPGRADE_SPEED2, IS.of(16, Rarity.UNCOMMON), "upgrade_speed2"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_speed3", UPGRADE_SPEED3, new UpgradeBlockItem(UPGRADE_SPEED3, IS.of(16, Rarity.RARE), "upgrade_speed3"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_efficiency", UPGRADE_EFFICIENCY, new UpgradeBlockItem(UPGRADE_EFFICIENCY, IS.of(16, Rarity.UNCOMMON), "upgrade_efficiency"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_efficiency2", UPGRADE_EFFICIENCY2, new UpgradeBlockItem(UPGRADE_EFFICIENCY2, IS.of(16, Rarity.RARE), "upgrade_efficiency2"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_yield", UPGRADE_YIELD, new UpgradeBlockItem(UPGRADE_YIELD, IS.of(16, Rarity.UNCOMMON), "upgrade_yield"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_yield2", UPGRADE_YIELD2, new UpgradeBlockItem(UPGRADE_YIELD2, IS.of(16, Rarity.RARE), "upgrade_yield2"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_experience", UPGRADE_EXPERIENCE, new UpgradeBlockItem(UPGRADE_EXPERIENCE, IS.of(16), "upgrade_experience"), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("upgrade_experience2", UPGRADE_EXPERIENCE2, new UpgradeBlockItem(UPGRADE_EXPERIENCE2, IS.of(16, Rarity.UNCOMMON), "upgrade_experience2"), DyeColor.LIGHT_GRAY);
		
		registerPastelNetworkNodes(IS.of(16));
		registerStoneBlocks(IS.of());
		
		registerShootingStarBlocks(IS.of(1, Rarity.UNCOMMON));
		
		registerOreBlocks(IS.of(), IS.of().fireproof());
		registerOreStorageBlocks(IS.of(), IS.of().fireproof());
		registerDecoStones(IS.of());
		registerDDFlora(IS.of());
		registerRedstone(IS.of());
		registerMagicalBlocks(IS.of());
		registerMobBlocks(IS.of());
		registerJadeVineBlocks(IS.of());
		registerSugarSticks(IS.of());
		registerStructureBlocks(IS.of());
		registerSpiritTree(IS.of());
		
		// Fluids + Products
		registerBlock("goo", GOO);
		registerBlock("liquid_crystal", LIQUID_CRYSTAL);
		registerBlock("midnight_solution", MIDNIGHT_SOLUTION);
		registerBlock("dragonrot", DRAGONROT);
		
		registerBlockWithItem("black_materia", BLACK_MATERIA, IS.of(), DyeColor.GRAY);
		registerBlockWithItem("frostbite_crystal", FROSTBITE_CRYSTAL, IS.of(), DyeColor.LIGHT_BLUE);
		registerBlockWithItem("blazing_crystal", BLAZING_CRYSTAL, IS.of().fireproof(), DyeColor.ORANGE);
		registerBlockWithItem("clover", CLOVER, IS.of(), DyeColor.LIME);
		registerBlockWithItem("four_leaf_clover", FOUR_LEAF_CLOVER, new FourLeafCloverItem(FOUR_LEAF_CLOVER, IS.of(), SpectrumAdvancements.REVEAL_FOUR_LEAF_CLOVER, CLOVER.asItem()), DyeColor.LIME);
		registerBlockWithItem("incandescent_amalgam", INCANDESCENT_AMALGAM, new IncandescentAmalgamItem(INCANDESCENT_AMALGAM, IS.of(16).food(SpectrumFoodComponents.INCANDESCENT_AMALGAM)), DyeColor.RED);
		
		// Worldgen
		registerBlockWithItem("quitoxic_reeds", QUITOXIC_REEDS, IS.of(), DyeColor.PURPLE);
		registerBlockWithItem("radiating_ender", RADIATING_ENDER, IS.of(), DyeColor.PURPLE);
		
		registerBlock("amaranth", AMARANTH);
		
		registerBlockWithItem("bedrock_anvil", BEDROCK_ANVIL, IS.of(), DyeColor.BLACK);
		registerBlockWithItem("cracked_end_portal_frame", CRACKED_END_PORTAL_FRAME, IS.of().fireproof(), DyeColor.PURPLE);
		
		registerBlockWithItem("memory", MEMORY, new MemoryItem(MEMORY, IS.of(Rarity.UNCOMMON)), DyeColor.LIGHT_GRAY);
		
		// Technical Blocks without items
		registerBlock("mermaids_brush", MERMAIDS_BRUSH);
		registerBlock("sag_leaf", SAG_LEAF);
		registerBlock("sag_bubble", SAG_BUBBLE);
		registerBlock("small_sag_bubble", SMALL_SAG_BUBBLE);
		
		registerBlock("primordial_fire", PRIMORDIAL_FIRE);
		registerBlockWithItem("primordial_torch", PRIMORDIAL_TORCH, new VerticallyAttachableBlockItem(PRIMORDIAL_TORCH, PRIMORDIAL_WALL_TORCH, IS.of(), Direction.DOWN), DyeColor.ORANGE);
		registerBlock("primordial_wall_torch", PRIMORDIAL_WALL_TORCH);
		registerBlock("deeper_down_portal", DEEPER_DOWN_PORTAL);
		registerBlock("stuck_storm_stone", STUCK_STORM_STONE);
		registerBlock("wand_light", WAND_LIGHT_BLOCK);
		registerBlock("decaying_light", DECAYING_LIGHT_BLOCK);
		registerBlock("block_flooder", BLOCK_FLOODER);
		registerBlockWithItem("bottomless_bundle", BOTTOMLESS_BUNDLE, new BottomlessBundleItem(BOTTOMLESS_BUNDLE, IS.of(1)), DyeColor.LIGHT_GRAY);
		
		COMMON_REGISTRAR.flush();
		
		registerMobHeads(IS.of());
	}
	
	private static void registerDDFlora(Item.Settings settings) {
		registerBlockWithItem("sawblade_grass", SAWBLADE_GRASS, settings, DyeColor.LIME);
		registerBlockWithItem("overgrown_blackslag", OVERGROWN_BLACKSLAG, settings, DyeColor.LIME);
		registerBlockWithItem("shimmel", SHIMMEL, settings, DyeColor.LIME);
		registerBlockWithItem("rotten_ground", ROTTEN_GROUND, settings, DyeColor.LIME);
		
		registerBlockWithItem("weeping_gala_pillar", WEEPING_GALA_PILLAR, settings, DyeColor.LIME);
		registerBlockWithItem("weeping_gala_barrel", WEEPING_GALA_BARREL, settings, DyeColor.LIME);
		registerBlockWithItem("weeping_gala_amphora", WEEPING_GALA_AMPHORA, settings, DyeColor.LIME);
		registerBlockWithItem("weeping_gala_lantern", WEEPING_GALA_LANTERN, settings, DyeColor.LIME);
		registerBlockWithItem("weeping_gala_lamp", WEEPING_GALA_LAMP, settings, DyeColor.LIME);
		registerBlockWithItem("weeping_gala_light", WEEPING_GALA_LIGHT, settings, DyeColor.LIME);
	}
	
	private static void registerRedstone(Item.Settings settings) {
		registerBlockWithItem("light_level_detector", LIGHT_LEVEL_DETECTOR, settings, DyeColor.RED);
		registerBlockWithItem("weather_detector", WEATHER_DETECTOR, settings, DyeColor.RED);
		registerBlockWithItem("item_detector", ITEM_DETECTOR, settings, DyeColor.RED);
		registerBlockWithItem("player_detector", PLAYER_DETECTOR, settings, DyeColor.RED);
		registerBlockWithItem("creature_detector", CREATURE_DETECTOR, settings, DyeColor.RED);
		
		registerBlockWithItem("redstone_timer", REDSTONE_TIMER, settings, DyeColor.RED);
		registerBlockWithItem("redstone_calculator", REDSTONE_CALCULATOR, settings, DyeColor.RED);
		registerBlockWithItem("redstone_transceiver", REDSTONE_TRANSCEIVER, settings, DyeColor.RED);
		
		registerBlockWithItem("redstone_sand", REDSTONE_SAND, settings, DyeColor.RED);
		registerBlockWithItem("ender_glass", ENDER_GLASS, settings, DyeColor.PURPLE);
		
		registerBlockWithItem("block_placer", BLOCK_PLACER, settings, DyeColor.CYAN);
		registerBlockWithItem("block_detector", BLOCK_DETECTOR, settings, DyeColor.CYAN);
		registerBlockWithItem("block_breaker", BLOCK_BREAKER, settings, DyeColor.CYAN);
	}
	
	private static void registerMagicalBlocks(Item.Settings settings) {
		registerBlockWithItem("heartbound_chest", HEARTBOUND_CHEST, settings, DyeColor.BLUE);
		registerBlockWithItem("compacting_chest", COMPACTING_CHEST, settings, DyeColor.YELLOW);
		registerBlockWithItem("fabrication_chest", FABRICATION_CHEST, settings, DyeColor.YELLOW);
		registerBlockWithItem("black_hole_chest", BLACK_HOLE_CHEST, settings, DyeColor.LIGHT_GRAY);
		
		registerBlockWithItem("ender_hopper", ENDER_HOPPER, settings, DyeColor.PURPLE);
		registerBlockWithItem("ender_dropper", ENDER_DROPPER, settings, DyeColor.PURPLE);
		registerBlockWithItem("particle_spawner", PARTICLE_SPAWNER, settings, DyeColor.PINK);
		registerBlockWithItem("creative_particle_spawner", CREATIVE_PARTICLE_SPAWNER, new BlockItem(CREATIVE_PARTICLE_SPAWNER, IS.of(Rarity.EPIC)), DyeColor.PINK);
		
		registerBlockWithItem("lava_sponge", LAVA_SPONGE, IS.of().fireproof(), DyeColor.ORANGE);
		registerBlockWithItem("wet_lava_sponge", WET_LAVA_SPONGE, new WetLavaSpongeItem(WET_LAVA_SPONGE, IS.of(1).fireproof().recipeRemainder(LAVA_SPONGE.asItem())), DyeColor.ORANGE);
		
		registerBlockWithItem("ethereal_platform", ETHEREAL_PLATFORM, settings, DyeColor.LIGHT_GRAY);
		registerBlockWithItem("universe_spyhole", UNIVERSE_SPYHOLE, settings, DyeColor.LIGHT_GRAY);
		registerBlockWithItem("present", PRESENT, new PresentBlockItem(PRESENT, IS.of(1)), DyeColor.LIGHT_GRAY);
		registerBlockWithItem("titration_barrel", TITRATION_BARREL, settings, DyeColor.MAGENTA);
		
		registerBlockWithItem("parametric_mining_device", PARAMETRIC_MINING_DEVICE, new ParametricMiningDeviceItem(PARAMETRIC_MINING_DEVICE, IS.of(8)), DyeColor.RED);
		registerBlockWithItem("threat_conflux", THREAT_CONFLUX, new ThreatConfluxItem(THREAT_CONFLUX, IS.of(8)), DyeColor.RED);
	}
	
	private static void registerSpiritTree(Item.Settings settings) {
		registerBlockWithItem("ominous_sapling", OMINOUS_SAPLING, new OminousSaplingBlockItem(OMINOUS_SAPLING, settings), DyeColor.GREEN);
		
		registerBlock("cyan_spirit_sallow_vines_head", CYAN_SPIRIT_SALLOW_VINES);
		registerBlock("magenta_spirit_sallow_vines_head", MAGENTA_SPIRIT_SALLOW_VINES);
		registerBlock("yellow_spirit_sallow_vines_head", YELLOW_SPIRIT_SALLOW_VINES);
		registerBlock("black_spirit_sallow_vines_head", BLACK_SPIRIT_SALLOW_VINES);
		registerBlock("white_spirit_sallow_vines_head", WHITE_SPIRIT_SALLOW_VINES);
		
		registerBlock("cyan_spirit_sallow_vines_body", CYAN_SPIRIT_SALLOW_VINES_PLANT);
		registerBlock("magenta_spirit_sallow_vines_body", MAGENTA_SPIRIT_SALLOW_VINES_PLANT);
		registerBlock("yellow_spirit_sallow_vines_body", YELLOW_SPIRIT_SALLOW_VINES_PLANT);
		registerBlock("black_spirit_sallow_vines_body", BLACK_SPIRIT_SALLOW_VINES_PLANT);
		registerBlock("white_spirit_sallow_vines_body", WHITE_SPIRIT_SALLOW_VINES_PLANT);
		
		registerBlockWithItem("sacred_soil", SACRED_SOIL, settings, DyeColor.LIME);
	}
	
	private static void registerOreBlocks(Item.Settings settings, Item.Settings settingsFireproof) {
		registerBlockWithItem("stratine_ore", STRATINE_ORE, new FloatBlockItem(STRATINE_ORE, settingsFireproof, -0.01F), DyeColor.RED);
		registerBlockWithItem("paltaeria_ore", PALTAERIA_ORE, new FloatBlockItem(PALTAERIA_ORE, settings, 0.01F), DyeColor.CYAN);
	}
	
	private static void registerOreStorageBlocks(Item.Settings settings, Item.Settings settingsFireproof) {
		registerBlockWithItem("stratine_floatblock", STRATINE_FLOATBLOCK, new FloatBlockItem(STRATINE_FLOATBLOCK, settingsFireproof, -0.02F), DyeColor.RED);
		registerBlockWithItem("paltaeria_floatblock", PALTAERIA_FLOATBLOCK, new FloatBlockItem(PALTAERIA_FLOATBLOCK, settings, 0.02F), DyeColor.CYAN);
		registerBlockWithItem("hover_block", HOVER_BLOCK, new FloatBlockItem(HOVER_BLOCK, settings, 0F) {
			@Override
			public double applyGravity(ItemStack stack, World world, Entity entity) {
				return 0;
			}
			
			@Override
			public void applyGravity(ItemStack stack, World world, ItemEntity itemEntity) {
				itemEntity.setNoGravity(true);
			}
		}, DyeColor.GREEN);
	}
	
	private static void registerDecoStones(Item.Settings settings) {
		registerBlockWithItem("amethyst_decostone", AMETHYST_DECOSTONE, settings, DyeColor.MAGENTA);
		registerBlockWithItem("topaz_decostone", TOPAZ_DECOSTONE, settings, DyeColor.CYAN);
		registerBlockWithItem("citrine_decostone", CITRINE_DECOSTONE, settings, DyeColor.YELLOW);
		registerBlockWithItem("onyx_decostone", ONYX_DECOSTONE, settings, DyeColor.BLACK);
		registerBlockWithItem("moonstone_decostone", MOONSTONE_DECOSTONE, settings, DyeColor.WHITE);
	}
	
	private static void registerStoneBlocks(Item.Settings settings) {
		registerBlockWithItem("pyrite_pile", PYRITE_PILE, settings, DyeColor.BROWN);
		registerBlockWithItem("pyrite_plating", PYRITE_PLATING, settings, DyeColor.BROWN);
		registerBlockWithItem("pyrite_tubing", PYRITE_TUBING, settings, DyeColor.BROWN);
		registerBlockWithItem("pyrite_relief", PYRITE_RELIEF, settings, DyeColor.BROWN);
		registerBlockWithItem("pyrite_stack", PYRITE_STACK, settings, DyeColor.BROWN);
		registerBlockWithItem("pyrite_paneling", PYRITE_PANELING, settings, DyeColor.BROWN);
		registerBlockWithItem("pyrite_vent", PYRITE_VENT, settings, DyeColor.BROWN);
		registerBlockWithItem("pyrite_ripper", PYRITE_RIPPER, settings, DyeColor.RED);
		registerBlockWithItem("pyrite_projector", PYRITE_PROJECTOR, settings, DyeColor.YELLOW);
	}
	
	public static void registerShootingStarBlocks(Item.Settings settings) {
		registerBlockWithItem("glistering_shooting_star", GLISTERING_SHOOTING_STAR, new ShootingStarItem(GLISTERING_SHOOTING_STAR, settings), DyeColor.PURPLE);
		registerBlockWithItem("fiery_shooting_star", FIERY_SHOOTING_STAR, new ShootingStarItem(FIERY_SHOOTING_STAR, settings), DyeColor.PURPLE);
		registerBlockWithItem("colorful_shooting_star", COLORFUL_SHOOTING_STAR, new ShootingStarItem(COLORFUL_SHOOTING_STAR, settings), DyeColor.PURPLE);
		registerBlockWithItem("pristine_shooting_star", PRISTINE_SHOOTING_STAR, new ShootingStarItem(PRISTINE_SHOOTING_STAR, settings), DyeColor.PURPLE);
		registerBlockWithItem("gemstone_shooting_star", GEMSTONE_SHOOTING_STAR, new ShootingStarItem(GEMSTONE_SHOOTING_STAR, settings), DyeColor.PURPLE);
		
		registerBlockWithItem("stardust_block", STARDUST_BLOCK, settings, DyeColor.BLACK);
	}
	
	public static void registerPastelNetworkNodes(Item.Settings settings) {
		registerBlockWithItem("connection_node", CONNECTION_NODE, settings, DyeColor.LIGHT_GRAY);
		registerBlockWithItem("provider_node", PROVIDER_NODE, settings, DyeColor.MAGENTA);
		registerBlockWithItem("storage_node", STORAGE_NODE, settings, DyeColor.CYAN);
		registerBlockWithItem("buffer_node", BUFFER_NODE, settings, DyeColor.GREEN);
		registerBlockWithItem("sender_node", SENDER_NODE, settings, DyeColor.YELLOW);
		registerBlockWithItem("gather_node", GATHER_NODE, settings, DyeColor.BLACK);
	}
	
	private static void registerStructureBlocks(Item.Settings settings) {
		registerBlockWithItem("downstone", DOWNSTONE, settings, DyeColor.BLUE);
		
		registerBlockWithItem("preservation_bricks", PRESERVATION_BRICKS, settings, DyeColor.BLUE);
		registerBlockWithItem("shimmering_preservation_bricks", SHIMMERING_PRESERVATION_BRICKS, settings, DyeColor.BLUE);
		registerBlockWithItem("powder_chiseled_preservation_stone", POWDER_CHISELED_PRESERVATION_STONE, settings, DyeColor.BLUE);
		registerBlockWithItem("dike_chiseled_preservation_stone", DIKE_CHISELED_PRESERVATION_STONE, settings, DyeColor.BLUE);
		registerBlockWithItem("dream_chiseled_preservation_stone", DREAM_CHISELED_PRESERVATION_STONE, settings, DyeColor.BLUE);
		registerBlockWithItem("deep_light_chiseled_preservation_stone", DEEP_LIGHT_CHISELED_PRESERVATION_STONE, settings, DyeColor.BLUE);
		registerBlockWithItem("preservation_roundel", PRESERVATION_ROUNDEL, settings, DyeColor.BLUE);
		registerBlockWithItem("preservation_block_detector", PRESERVATION_BLOCK_DETECTOR, settings, DyeColor.BLUE);
		registerBlockWithItem("item_bowl_enlightenment", TREASURE_ITEM_BOWL, settings, DyeColor.BLUE);
		registerBlockWithItem("dike_gate_fountain", DIKE_GATE_FOUNTAIN, settings, DyeColor.BLUE);
		registerBlockWithItem("dike_gate", DIKE_GATE, settings, DyeColor.BLUE);
		registerBlockWithItem("dream_gate", DREAM_GATE, settings, DyeColor.BLUE);
		registerBlockWithItem("preservation_controller", PRESERVATION_CONTROLLER, settings, DyeColor.BLUE);
		
		registerBlockWithItem("invisible_wall", INVISIBLE_WALL, settings, DyeColor.BLUE);
		registerBlockWithItem("courier_statue", COURIER_STATUE, settings, DyeColor.BLUE);
		registerBlock("manxi", MANXI);
		registerBlockWithItem("preservation_chest", PRESERVATION_CHEST, settings, DyeColor.BLUE);
	}
	
	private static void registerJadeVineBlocks(Item.Settings settings) {
		registerBlock("jade_vine_roots", JADE_VINE_ROOTS);
		registerBlock("jade_vine_bulb", JADE_VINE_BULB);
		registerBlock("jade_vines", JADE_VINES);
		registerBlockWithItem("jade_vine_petal_block", JADE_VINE_PETAL_BLOCK, settings, DyeColor.LIME);
		registerBlockWithItem("jade_vine_petal_carpet", JADE_VINE_PETAL_CARPET, settings, DyeColor.LIME);
		
		registerBlockWithItem("nephrite_blossom_stem", NEPHRITE_BLOSSOM_STEM, settings, DyeColor.PINK);
		registerBlockWithItem("nephrite_blossom_leaves", NEPHRITE_BLOSSOM_LEAVES, settings, DyeColor.PINK);
		registerBlockWithItem("nephrite_blossom_bulb", NEPHRITE_BLOSSOM_BULB, IS.of(16), DyeColor.PINK);
		
		registerBlockWithItem("jadeite_lotus_stem", JADEITE_LOTUS_STEM, settings, DyeColor.LIME);
		registerBlockWithItem("jadeite_lotus_flower", JADEITE_LOTUS_FLOWER, IS.of(16), DyeColor.LIME);
		registerBlockWithItem("jadeite_lotus_bulb", JADEITE_LOTUS_BULB, IS.of(16), DyeColor.LIME);
		registerBlockWithItem("jadeite_petal_block", JADEITE_PETAL_BLOCK, settings, DyeColor.LIME);
		registerBlockWithItem("jadeite_petal_carpet", JADEITE_PETAL_CARPET, settings, DyeColor.LIME);
	}
	
	private static void registerSugarSticks(Item.Settings settings) {
		registerBlockWithItem("sugar_stick", SUGAR_STICK, settings, DyeColor.PINK);
		registerBlockWithItem("topaz_sugar_stick", TOPAZ_SUGAR_STICK, settings, DyeColor.PINK);
		registerBlockWithItem("amethyst_sugar_stick", AMETHYST_SUGAR_STICK, settings, DyeColor.PINK);
		registerBlockWithItem("citrine_sugar_stick", CITRINE_SUGAR_STICK, settings, DyeColor.PINK);
		registerBlockWithItem("onyx_sugar_stick", ONYX_SUGAR_STICK, settings, DyeColor.PINK);
		registerBlockWithItem("moonstone_sugar_stick", MOONSTONE_SUGAR_STICK, settings, DyeColor.PINK);
	}
	
	private static void registerMobBlocks(Item.Settings settings) {
		registerBlockWithItem("axolotl_idol", AXOLOTL_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("bat_idol", BAT_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("bee_idol", BEE_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("blaze_idol", BLAZE_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("cat_idol", CAT_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("chicken_idol", CHICKEN_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("cow_idol", COW_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("creeper_idol", CREEPER_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("ender_dragon_idol", ENDER_DRAGON_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("enderman_idol", ENDERMAN_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("endermite_idol", ENDERMITE_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("evoker_idol", EVOKER_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("fish_idol", FISH_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("fox_idol", FOX_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("ghast_idol", GHAST_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("glow_squid_idol", GLOW_SQUID_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("goat_idol", GOAT_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("guardian_idol", GUARDIAN_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("horse_idol", HORSE_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("illusioner_idol", ILLUSIONER_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("ocelot_idol", OCELOT_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("parrot_idol", PARROT_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("phantom_idol", PHANTOM_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("pig_idol", PIG_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("piglin_idol", PIGLIN_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("polar_bear_idol", POLAR_BEAR_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("pufferfish_idol", PUFFERFISH_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("rabbit_idol", RABBIT_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("sheep_idol", SHEEP_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("shulker_idol", SHULKER_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("silverfish_idol", SILVERFISH_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("skeleton_idol", SKELETON_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("slime_idol", SLIME_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("snow_golem_idol", SNOW_GOLEM_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("spider_idol", SPIDER_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("squid_idol", SQUID_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("stray_idol", STRAY_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("strider_idol", STRIDER_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("turtle_idol", TURTLE_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("witch_idol", WITCH_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("wither_idol", WITHER_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("wither_skeleton_idol", WITHER_SKELETON_IDOL, settings, DyeColor.PINK);
		registerBlockWithItem("zombie_idol", ZOMBIE_IDOL, settings, DyeColor.PINK);
	}
	
	// All the mob heads vanilla is missing
	private static void registerMobHeads(Item.Settings settings) {
		for (SpectrumSkullType type : SpectrumSkullType.values()) {
			Block head = new SpectrumSkullBlock(type, AbstractBlock.Settings.copy(Blocks.SKELETON_SKULL).instrument(NoteBlockInstrument.CUSTOM_HEAD));
			registerBlock(type.name().toLowerCase(Locale.ROOT) + "_head", head);
			Block wallHead = new SpectrumWallSkullBlock(type, AbstractBlock.Settings.copy(Blocks.SKELETON_SKULL).dropsLike(head));
			registerBlock(type.name().toLowerCase(Locale.ROOT) + "_wall_head", wallHead);
			BlockItem headItem = new SpectrumSkullBlockItem(head, wallHead, (settings), type);
			registerBlockItem(type.name().toLowerCase(Locale.ROOT) + "_head", headItem, DyeColor.GRAY);
		}
	}
	
	public static void registerClient() {
		
		// Crafting Stations
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PEDESTAL_BASIC_AMETHYST, PEDESTAL_BASIC_CITRINE, PEDESTAL_BASIC_TOPAZ, PEDESTAL_ALL_BASIC, PEDESTAL_ONYX, PEDESTAL_MOONSTONE);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), FUSION_SHRINE_BASALT, FUSION_SHRINE_CALCITE);
		BlockRenderLayerMap.INSTANCE.putBlock(ENCHANTER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(POTION_WORKSHOP, RenderLayer.getTranslucent());
		
		// Pastel
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CONNECTION_NODE, PROVIDER_NODE, SENDER_NODE, STORAGE_NODE, BUFFER_NODE, GATHER_NODE);
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ENDER_GLASS, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PARTICLE_SPAWNER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CREATIVE_PARTICLE_SPAWNER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CRYSTALLARIEUM, RenderLayer.getTranslucent());
		
		// Weeping Gala
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), SpectrumBlocks.WEEPING_GALA_LANTERN, SpectrumBlocks.WEEPING_GALA_LIGHT);
		
		// Chimes
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.TOPAZ_CHIME, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.AMETHYST_CHIME, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CITRINE_CHIME, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.MOONSTONE_CHIME, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ONYX_CHIME, RenderLayer.getTranslucent());
		
		// Others
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), SpectrumBlocks.PRIMORDIAL_FIRE, SpectrumBlocks.PRIMORDIAL_TORCH, SpectrumBlocks.PRIMORDIAL_WALL_TORCH);
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PRESENT, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.OMINOUS_SAPLING, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ITEM_BOWL_BASALT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ITEM_BOWL_CALCITE, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.TREASURE_ITEM_BOWL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ITEM_ROUNDEL, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.MEMORY, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.VEGETAL_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.LONGING_CHIMERA, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.JADE_VINE_ROOTS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.JADE_VINE_BULB, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.JADE_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.JADE_VINE_PETAL_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.JADE_VINE_PETAL_CARPET, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), NEPHRITE_BLOSSOM_LEAVES, NEPHRITE_BLOSSOM_BULB, NEPHRITE_BLOSSOM_STEM);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), JADEITE_LOTUS_FLOWER, JADEITE_LOTUS_BULB, JADEITE_LOTUS_STEM, JADEITE_PETAL_BLOCK, JADEITE_PETAL_CARPET);
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.AMARANTH, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.DIKE_GATE, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.DREAM_GATE, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PRESERVATION_CONTROLLER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.INVISIBLE_WALL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.COURIER_STATUE, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.COLOR_PICKER, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.REDSTONE_TIMER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.REDSTONE_TRANSCEIVER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.REDSTONE_CALCULATOR, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.BLACK_HOLE_CHEST, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.FABRICATION_CHEST, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.QUITOXIC_REEDS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.MERMAIDS_BRUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.STUCK_STORM_STONE, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CLOVER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.FOUR_LEAF_CLOVER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ETHEREAL_PLATFORM, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.UNIVERSE_SPYHOLE, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.BOTTOMLESS_BUNDLE, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SAG_LEAF, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SAG_BUBBLE, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SMALL_SAG_BUBBLE, RenderLayer.getCutout());
		
		// Mob Blocks
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.AXOLOTL_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.BAT_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.BEE_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.BLAZE_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CAT_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CHICKEN_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.COW_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CREEPER_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ENDER_DRAGON_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ENDERMAN_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ENDERMITE_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.EVOKER_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.FISH_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.FOX_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.GHAST_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.GLOW_SQUID_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.GOAT_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.GUARDIAN_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.HORSE_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ILLUSIONER_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.OCELOT_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PARROT_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PHANTOM_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PIG_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PIGLIN_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.POLAR_BEAR_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PUFFERFISH_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.RABBIT_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SHEEP_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SHULKER_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SILVERFISH_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SKELETON_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SLIME_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SNOW_GOLEM_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SPIDER_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.SQUID_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.STRAY_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.STRIDER_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.TURTLE_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.WITCH_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.WITHER_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.WITHER_SKELETON_IDOL, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.ZOMBIE_IDOL, RenderLayer.getTranslucent());
		
		// Shooting stars
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.COLORFUL_SHOOTING_STAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.FIERY_SHOOTING_STAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.GEMSTONE_SHOOTING_STAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.GLISTERING_SHOOTING_STAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PRISTINE_SHOOTING_STAR, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.INCANDESCENT_AMALGAM, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CYAN_SPIRIT_SALLOW_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.CYAN_SPIRIT_SALLOW_VINES_PLANT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.MAGENTA_SPIRIT_SALLOW_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.MAGENTA_SPIRIT_SALLOW_VINES_PLANT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.YELLOW_SPIRIT_SALLOW_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.YELLOW_SPIRIT_SALLOW_VINES_PLANT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.BLACK_SPIRIT_SALLOW_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.BLACK_SPIRIT_SALLOW_VINES_PLANT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.WHITE_SPIRIT_SALLOW_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.WHITE_SPIRIT_SALLOW_VINES_PLANT, RenderLayer.getCutout());
		
		BlockRenderLayerMap.INSTANCE.putBlock(SpectrumBlocks.PYRITE_RIPPER, RenderLayer.getCutoutMipped());
		
		CLIENT_REGISTRAR.flush();
	}
	
	public static <T extends Block> BlockRegistrar<T> block(String name, T block) {
		COMMON_REGISTRAR.defer(block, b -> Registry.register(Registries.BLOCK, SpectrumCommon.locate(name), block));
		return new BlockRegistrar<>(block, null);
	}
	
	public static <T extends Block> BlockRegistrar<T> blockWithItem(String name, T block, DyeColor color) {
		return blockWithItem(name, block, IS.DEFAULT, color);
	}
	
	public static <T extends Block> BlockRegistrar<T> blockWithItem(String name, T block, Item.Settings settings, DyeColor color) {
		Identifier id = SpectrumCommon.locate(name);
		BlockItem item = new BlockItem(block, settings);
		COMMON_REGISTRAR.defer(() -> {
			Registry.register(Registries.BLOCK, id, block);
			Registry.register(Registries.ITEM, id, item);
			ItemColors.ITEM_COLORS.registerColorMapping(item, color);
		});
		return new BlockRegistrar<>(block, item);
	}
	
	public static <T extends Block> T register(BlockRegistrar<T> registrar) {
		return registrar.block();
	}
	
	public static <T extends Block> BlockRegistrar<T> cutout(BlockRegistrar<T> registrar) {
		return registrar.withCutoutRenderLayer();
	}
	
	public static <T extends Block> BlockRegistrar<T> translucent(BlockRegistrar<T> registrar) {
		return registrar.withTranslucentRenderLayer();
	}
	
	public static <T extends Block> BlockRegistrar<T> simple(BlockRegistrar<T> registrar) {
		return singleton(registrar, TexturedModel.CUBE_ALL);
	}
	
	public static <T extends Block> BlockRegistrar<T> simpleMirrored(BlockRegistrar<T> registrar) {
		return registrar.withBlockModel((ctx, block) -> createMirroredVariantsSupplier(block, TexturedModel.CUBE_ALL, TexturedModel.CUBE_MIRRORED_ALL, ctx.modelCollector));
	}
	
	public static <T extends Block> BlockRegistrar<T> singleton(BlockRegistrar<T> registrar, TexturedModel.Factory factory) {
		return registrar.withBlockModel((ctx, block) -> singletonBlockModel(ctx, block, factory));
	}
	
	public static <T extends Block> BlockRegistrar<T> parented(BlockRegistrar<T> registrar, Block parentBlock) {
		return registrar.withBlockModel((ctx, block) -> {
			ctx.registerParentedItemModel(block, ModelIds.getBlockModelId(parentBlock));
			return createVariantsSupplier(block, ModelIds.getBlockModelId(parentBlock));
		});
	}
	
	public static <T extends Block> BlockRegistrar<T> axisRotated(BlockRegistrar<T> registrar, TexturedModel.Factory factory) {
		return registrar.withBlockModel((ctx, block) -> createVariantsSupplier(ctx, block, factory).coordinate(createAxisRotatedVariantMap()));
	}
	
	public static <T extends Block> BlockRegistrar<T> defaultFacingUp(BlockRegistrar<T> registrar, TexturedModel.Factory factory) {
		return registrar.withBlockModel((ctx, block) -> createVariantsSupplier(ctx, block, factory).coordinate(ctx.createUpDefaultFacingVariantMap()));
	}
	
	public static <T extends Block> BlockRegistrar<T> tintableCross(BlockRegistrar<T> registrar, boolean tinted) {
		return cutout(registrar).withBlockModel((ctx, block) -> tintableCrossBlockModel(ctx, block, tinted));
	}
	
	public static <T extends Block> BlockRegistrar<T> simplePlant(BlockRegistrar<T> registrar) {
		return cutout(registrar).withBlockModel((ctx, block) -> {
			Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(block), ctx.modelCollector);
			return tintableCrossBlockModel(ctx, block, false);
		});
	}
	
	public static <T extends FlowerPotBlock> BlockRegistrar<T> pottedPlant(BlockRegistrar<T> registrar, boolean tinted) {
		return cutout(registrar).withBlockModel((ctx, block) -> pottedPlantBlockModel(ctx, block, tinted));
	}
	
	public static <T extends Block> BlockRegistrar<T> log(BlockRegistrar<T> registrar) {
		return registrar.withBlockModel(SpectrumModelProvider::logBlockModel);
	}
	
	public static <T extends Block> BlockRegistrar<T> wood(BlockRegistrar<T> registrar, Block logBlock) {
		return registrar.withBlockModel((ctx, block) -> woodBlockModel(ctx, block, logBlock));
	}
	
	public record BlockRegistrar<T extends Block>(@NotNull T block, @Nullable Item item) {
		
		public BlockRegistrar<T> with(Consumer<T> callback) {
			callback.accept(block);
			return this;
		}
		
		public BlockRegistrar<T> withCutoutRenderLayer() {
			CLIENT_REGISTRAR.defer(() -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
			return this;
		}
		
		public BlockRegistrar<T> withTranslucentRenderLayer() {
			CLIENT_REGISTRAR.defer(() -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
			return this;
		}
		
		public BlockRegistrar<T> withBlockModel(BiFunction<BlockStateModelGenerator, ? super T, BlockStateSupplier> callback) {
			BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.blockStateCollector.accept(callback.apply(ctx, block)));
			return this;
		}
		
		public BlockRegistrar<T> withItemModel(BiConsumer<ItemModelGenerator, ? super T> callback) {
			if (this.item != null) {
				ITEM_MODEL_REGISTRAR.defer(ctx -> callback.accept(ctx, block));
			}
			return this;
		}
		
	}
	
}