package de.dafuqs.spectrum.api.interaction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.predicate.block.BrokenBlockPredicate;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import de.dafuqs.spectrum.registries.SpectrumRegistryKeys;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class ResonanceProcessor {
	
	public static boolean preventNextXPDrop;
	
	public static final Codec<ResonanceProcessor> CODEC = SpectrumRegistries.RESONANCE_PROCESSOR_TYPE.byNameCodec()
			.dispatch(ResonanceProcessor::getCodec, codec -> codec);
	
	public BrokenBlockPredicate blockPredicate;
	
	public ResonanceProcessor(BrokenBlockPredicate blockPredicate) {
		this.blockPredicate = blockPredicate;
	}
	
	public abstract boolean process(BlockState state, BlockEntity blockEntity, List<ItemStack> droppedStacks);
	
	public static void applyResonance(RegistryAccess drm, BlockState minedState, BlockEntity blockEntity, List<ItemStack> droppedStacks) {
		drm.registryOrThrow(SpectrumRegistryKeys.RESONANCE_PROCESSOR).forEach(entry -> entry.process(minedState, blockEntity, droppedStacks));
	}
	
	public abstract MapCodec<? extends ResonanceProcessor> getCodec();
	
}
