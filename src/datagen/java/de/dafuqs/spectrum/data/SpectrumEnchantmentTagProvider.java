package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SpectrumEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
	
	public SpectrumEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}
	
	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		SpectrumEnchantments.provideEnchantmentTags(this::getOrCreateTagBuilder);
	}
	
}
