package de.dafuqs.spectrum.compat.gobber;

import de.dafuqs.spectrum.blocks.crystallarieum.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.data.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.registries.SpectrumItems.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class GobberCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Block SMALL_GLOBETTE_BUD = SpectrumBlocks.registerClusterBlock("small_globette_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.BLUE_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.BLUE);
	public static Block LARGE_GLOBETTE_BUD = SpectrumBlocks.registerClusterBlock("large_globette_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_BUD), SpectrumClusterBlock.GrowthStage.LARGE), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.BLUE);
	public static Block GLOBETTE_CLUSTER = SpectrumBlocks.registerClusterBlock("globette_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.BLUE);
	public static Block SMALL_GLOBETTE_NETHER_BUD = SpectrumBlocks.registerClusterBlock("small_globette_nether_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.RED_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.RED);
	public static Block LARGE_GLOBETTE_NETHER_BUD = SpectrumBlocks.registerClusterBlock("large_globette_nether_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_NETHER_BUD), SpectrumClusterBlock.GrowthStage.LARGE), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.RED);
	public static Block GLOBETTE_NETHER_CLUSTER = SpectrumBlocks.registerClusterBlock("globette_nether_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_NETHER_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.RED);
	public static Block SMALL_GLOBETTE_END_BUD = SpectrumBlocks.registerClusterBlock("small_globette_end_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.GREEN_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.GREEN);
	public static Block LARGE_GLOBETTE_END_BUD = SpectrumBlocks.registerClusterBlock("large_globette_end_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_END_BUD), SpectrumClusterBlock.GrowthStage.LARGE), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.GREEN);
	public static Block GLOBETTE_END_CLUSTER = SpectrumBlocks.registerClusterBlock("globette_end_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_END_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.GREEN);
	
	public static Block PURE_GLOBETTE_BLOCK = SpectrumBlocks.registerCustom("pure_globette_block", new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), DyeColor.BLUE, SpectrumModelProvider::registerSimpleCubeAllBlockModel);
	public static Block PURE_GLOBETTE_NETHER_BLOCK = SpectrumBlocks.registerCustom("pure_globette_nether_block", new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), DyeColor.RED, SpectrumModelProvider::registerSimpleCubeAllBlockModel);
	public static Block PURE_GLOBETTE_END_BLOCK = SpectrumBlocks.registerCustom("pure_globette_end_block", new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), DyeColor.GREEN, SpectrumModelProvider::registerSimpleCubeAllBlockModel);
	
	public static Item PURE_GLOBETTE = SpectrumItems.registerDeferred("pure_globette", new Item(IS.of()), DyeColor.BLUE);
	public static Item PURE_GLOBETTE_NETHER = SpectrumItems.registerDeferred("pure_globette_nether", new Item(IS.of()), DyeColor.RED);
	public static Item PURE_GLOBETTE_END = SpectrumItems.registerDeferred("pure_globette_end", new Item(IS.of()), DyeColor.GREEN);
	
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
