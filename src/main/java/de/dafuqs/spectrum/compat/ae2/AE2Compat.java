package de.dafuqs.spectrum.compat.ae2;

import de.dafuqs.spectrum.blocks.crystallarieum.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.*;
import net.minecraft.client.render.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

import static de.dafuqs.spectrum.registries.SpectrumBlocks.*;
import static de.dafuqs.spectrum.registries.SpectrumItems.*;

public class AE2Compat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Block SMALL_CERTUS_QUARTZ_BUD;
	public static Block LARGE_CERTUS_QUARTZ_BUD;
	public static Block CERTUS_QUARTZ_CLUSTER;
	public static Block SMALL_FLUIX_BUD;
	public static Block LARGE_FLUIX_BUD;
	public static Block FLUIX_CLUSTER;
	
	public static Block PURE_CERTUS_QUARTZ_BLOCK;
	public static Block PURE_FLUIX_BLOCK;
	
	public static Item PURE_CERTUS_QUARTZ = SpectrumItems.registerDeferred("pure_certus_quartz", new Item(IS.of()), DyeColor.YELLOW);
	public static Item PURE_FLUIX = SpectrumItems.registerDeferred("pure_fluix", new Item(IS.of()), DyeColor.YELLOW);
	
	@Override
	public void register() {
		// BLOCKS
		SMALL_CERTUS_QUARTZ_BUD = new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(MapColor.TERRACOTTA_WHITE).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL);
		LARGE_CERTUS_QUARTZ_BUD = new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_CERTUS_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.LARGE);
		CERTUS_QUARTZ_CLUSTER = new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_CERTUS_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER);
		SMALL_FLUIX_BUD = new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.PURPLE_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL);
		LARGE_FLUIX_BUD = new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_FLUIX_BUD), SpectrumClusterBlock.GrowthStage.LARGE);
		FLUIX_CLUSTER = new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_FLUIX_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER);
		
		PURE_CERTUS_QUARTZ_BLOCK = new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.3F).sounds(BlockSoundGroup.GLASS));
		PURE_FLUIX_BLOCK = new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.3F).sounds(BlockSoundGroup.GLASS));
		
		registerBlockWithItem("small_certus_quartz_bud", SMALL_CERTUS_QUARTZ_BUD, IS.of(), DyeColor.YELLOW);
		registerBlockWithItem("large_certus_quartz_bud", LARGE_CERTUS_QUARTZ_BUD, IS.of(), DyeColor.YELLOW);
		registerBlockWithItem("certus_quartz_cluster", CERTUS_QUARTZ_CLUSTER, IS.of(), DyeColor.YELLOW);
		registerBlockWithItem("small_fluix_bud", SMALL_FLUIX_BUD, IS.of(), DyeColor.YELLOW);
		registerBlockWithItem("large_fluix_bud", LARGE_FLUIX_BUD, IS.of(), DyeColor.YELLOW);
		registerBlockWithItem("fluix_cluster", FLUIX_CLUSTER, IS.of(), DyeColor.YELLOW);
		
		registerBlockWithItem("pure_certus_quartz_block", PURE_CERTUS_QUARTZ_BLOCK, IS.of(), DyeColor.YELLOW);
		registerBlockWithItem("pure_fluix_block", PURE_FLUIX_BLOCK, IS.of(), DyeColor.YELLOW);
		
		SpectrumItems.DEFERRER.flush();
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void registerClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(SMALL_CERTUS_QUARTZ_BUD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LARGE_CERTUS_QUARTZ_BUD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(CERTUS_QUARTZ_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SMALL_FLUIX_BUD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LARGE_FLUIX_BUD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(FLUIX_CLUSTER, RenderLayer.getCutout());
	}
	
}
