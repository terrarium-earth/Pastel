package de.dafuqs.spectrum.compat.ae2;

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
import net.minecraft.sound.*;

import static de.dafuqs.spectrum.registries.SpectrumBlocks.*;

public class AE2Compat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Block SMALL_CERTUS_QUARTZ_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_certus_quartz_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(MapColor.TERRACOTTA_WHITE).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block LARGE_CERTUS_QUARTZ_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_certus_quartz_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_CERTUS_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block CERTUS_QUARTZ_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("certus_quartz_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_CERTUS_QUARTZ_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block SMALL_FLUIX_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_fluix_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.PURPLE_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block LARGE_FLUIX_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_fluix_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_FLUIX_BUD), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block FLUIX_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("fluix_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_FLUIX_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	
	public static Block PURE_CERTUS_QUARTZ_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_certus_quartz_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.3F).sounds(BlockSoundGroup.GLASS)), InkColors.YELLOW)));
	public static Block PURE_FLUIX_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_fluix_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.3F).sounds(BlockSoundGroup.GLASS)), InkColors.YELLOW)));
	
	public static Item PURE_CERTUS_QUARTZ = SpectrumItems.registerDeferred("pure_certus_quartz", new Item(IS.of()), InkColors.YELLOW);
	public static Item PURE_FLUIX = SpectrumItems.registerDeferred("pure_fluix", new Item(IS.of()), InkColors.YELLOW);
	
	@Override
	public void register() {
		SpectrumItems.ITEM_REGISTRAR.flush();
		SpectrumBlocks.COMMON_REGISTRAR.flush();
		
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_PURE_RESOURCES).register(entries -> {
			entries.add(PURE_CERTUS_QUARTZ);
			entries.add(SMALL_CERTUS_QUARTZ_BUD);
			entries.add(LARGE_CERTUS_QUARTZ_BUD);
			entries.add(CERTUS_QUARTZ_CLUSTER);
			entries.add(PURE_CERTUS_QUARTZ_BLOCK);
			
			entries.add(PURE_FLUIX);
			entries.add(SMALL_FLUIX_BUD);
			entries.add(LARGE_FLUIX_BUD);
			entries.add(FLUIX_CLUSTER);
			entries.add(PURE_FLUIX_BLOCK);
		});
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
	
}
