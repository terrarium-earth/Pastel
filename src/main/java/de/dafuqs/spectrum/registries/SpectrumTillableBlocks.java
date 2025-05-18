package de.dafuqs.spectrum.registries;

import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.world.item.HoeItem;

public class SpectrumTillableBlocks {
	
	public static void register() {
		TillableBlockRegistry.register(SpectrumBlocks.SLUSH, HoeItem::onlyIfAirAbove, SpectrumBlocks.TILLED_SLUSH.defaultBlockState());
		TillableBlockRegistry.register(SpectrumBlocks.OVERGROWN_SLUSH, HoeItem::onlyIfAirAbove, SpectrumBlocks.TILLED_SLUSH.defaultBlockState());
		TillableBlockRegistry.register(SpectrumBlocks.SHALE_CLAY, HoeItem::onlyIfAirAbove, SpectrumBlocks.TILLED_SHALE_CLAY.defaultBlockState());
	}
	
}
