package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SpectrumBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	
	public SpectrumBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		SpectrumBlockTags.provideTags(this::getOrCreateTagBuilder);
	}
	
}