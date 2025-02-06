package de.dafuqs.spectrum.api.interaction;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.predicate.block.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

import java.util.*;

public abstract class ResonanceProcessor {
	
	public static boolean preventNextXPDrop;
	
	public static final Codec<ResonanceProcessor> CODEC = SpectrumRegistries.RESONANCE_DROP_PROCESSOR_TYPES.getCodec()
			.dispatch(ResonanceProcessor::getCodec, codec -> codec);
	
	public BrokenBlockPredicate blockPredicate;
	
	public ResonanceProcessor(BrokenBlockPredicate blockPredicate) {
		this.blockPredicate = blockPredicate;
	}
	
	public abstract boolean process(BlockState state, BlockEntity blockEntity, List<ItemStack> droppedStacks);
	
	public static void applyResonance(DynamicRegistryManager drm, BlockState minedState, BlockEntity blockEntity, List<ItemStack> droppedStacks) {
		drm.get(SpectrumRegistries.RESONANCE_DROPS_KEY).forEach(entry -> entry.process(minedState, blockEntity, droppedStacks));
	}
	
	public abstract MapCodec<? extends ResonanceProcessor> getCodec();
	
}
