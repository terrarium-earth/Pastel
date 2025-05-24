package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SpectrumBlockTagsProvider extends BlockTagsProvider {

	public SpectrumBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, SpectrumCommon.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		SpectrumBlockTags.provideTags(this::getOrCreateTagBuilder);
	}
	
}