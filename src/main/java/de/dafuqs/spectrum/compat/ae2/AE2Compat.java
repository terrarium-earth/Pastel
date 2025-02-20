package de.dafuqs.spectrum.compat.ae2;

import de.dafuqs.spectrum.blocks.crystallarieum.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.registries.SpectrumItems.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

public class AE2Compat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Block SMALL_CERTUS_QUARTZ_BUD = SpectrumBlocks.registerClusterBlock("small_certus_quartz_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(MapColor.TERRACOTTA_WHITE).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.YELLOW);
	public static Block LARGE_CERTUS_QUARTZ_BUD = SpectrumBlocks.registerClusterBlock("large_certus_quartz_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_CERTUS_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.LARGE), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.YELLOW);
	public static Block CERTUS_QUARTZ_CLUSTER = SpectrumBlocks.registerClusterBlock("certus_quartz_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_CERTUS_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.YELLOW);
	public static Block SMALL_FLUIX_BUD = SpectrumBlocks.registerClusterBlock("small_fluix_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.PURPLE_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.YELLOW);
	public static Block LARGE_FLUIX_BUD = SpectrumBlocks.registerClusterBlock("large_fluix_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_FLUIX_BUD), SpectrumClusterBlock.GrowthStage.LARGE), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.YELLOW);
	public static Block FLUIX_CLUSTER = SpectrumBlocks.registerClusterBlock("fluix_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_FLUIX_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.YELLOW);
	
	public static Block PURE_CERTUS_QUARTZ_BLOCK = SpectrumBlocks.registerSimple("pure_certus_quartz_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.3F).sounds(BlockSoundGroup.GLASS)), DyeColor.YELLOW);
	public static Block PURE_FLUIX_BLOCK = SpectrumBlocks.registerSimple("pure_fluix_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.3F).sounds(BlockSoundGroup.GLASS)), DyeColor.YELLOW);
	
	public static Item PURE_CERTUS_QUARTZ = SpectrumItems.registerDeferred("pure_certus_quartz", new Item(IS.of()), DyeColor.YELLOW);
	public static Item PURE_FLUIX = SpectrumItems.registerDeferred("pure_fluix", new Item(IS.of()), DyeColor.YELLOW);
	
	@Override
	public void register() {
		SpectrumItems.REGISTRAR.flush();
		SpectrumBlocks.COMMON_REGISTRAR.flush();
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
	
}
