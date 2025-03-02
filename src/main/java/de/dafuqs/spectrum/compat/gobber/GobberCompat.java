package de.dafuqs.spectrum.compat.gobber;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item_group.*;
import de.dafuqs.spectrum.blocks.crystallarieum.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.registries.SpectrumItems.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.*;
import net.minecraft.item.*;

import static de.dafuqs.spectrum.registries.SpectrumBlocks.*;

public class GobberCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Block SMALL_GLOBETTE_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_globette_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.BLUE_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block LARGE_GLOBETTE_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_globette_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_BUD), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block GLOBETTE_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("globette_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block SMALL_GLOBETTE_NETHER_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_globette_nether_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.RED_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block LARGE_GLOBETTE_NETHER_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_globette_nether_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_NETHER_BUD), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block GLOBETTE_NETHER_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("globette_nether_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_NETHER_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block SMALL_GLOBETTE_END_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_globette_end_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.GREEN_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block LARGE_GLOBETTE_END_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_globette_end_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_END_BUD), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block GLOBETTE_END_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("globette_end_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_GLOBETTE_END_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	
	public static Block PURE_GLOBETTE_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_globette_block", new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), InkColors.BLUE)));
	public static Block PURE_GLOBETTE_NETHER_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_globette_nether_block", new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), InkColors.RED)));
	public static Block PURE_GLOBETTE_END_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_globette_end_block", new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), InkColors.GREEN)));
	
	public static Item PURE_GLOBETTE = SpectrumItems.registerDeferred("pure_globette", new Item(IS.of()), InkColors.BLUE);
	public static Item PURE_GLOBETTE_NETHER = SpectrumItems.registerDeferred("pure_globette_nether", new Item(IS.of()), InkColors.RED);
	public static Item PURE_GLOBETTE_END = SpectrumItems.registerDeferred("pure_globette_end", new Item(IS.of()), InkColors.GREEN);
	
	@Override
	public void register() {
		SpectrumItems.REGISTRAR.flush();
		SpectrumBlocks.COMMON_REGISTRAR.flush();
		
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_PURE_RESOURCES).register(entries -> {
			entries.add(PURE_GLOBETTE);
			entries.add(SMALL_GLOBETTE_BUD);
			entries.add(LARGE_GLOBETTE_BUD);
			entries.add(GLOBETTE_CLUSTER);
			entries.add(PURE_GLOBETTE_BLOCK);
			
			entries.add(PURE_GLOBETTE_NETHER);
			entries.add(SMALL_GLOBETTE_NETHER_BUD);
			entries.add(LARGE_GLOBETTE_NETHER_BUD);
			entries.add(GLOBETTE_NETHER_CLUSTER);
			entries.add(PURE_GLOBETTE_NETHER_BLOCK);
			
			entries.add(PURE_GLOBETTE_END);
			entries.add(SMALL_GLOBETTE_END_BUD);
			entries.add(LARGE_GLOBETTE_END_BUD);
			entries.add(GLOBETTE_END_CLUSTER);
			entries.add(PURE_GLOBETTE_END_BLOCK);
		});
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
}
