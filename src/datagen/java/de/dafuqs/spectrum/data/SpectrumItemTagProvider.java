package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class SpectrumItemTagProvider extends FabricTagProvider.ItemTagProvider {
	
	public SpectrumItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupFuture) {
		super(output, lookupFuture);
	}
	
	@Override
	protected void addTags(HolderLookup.Provider provider) {
		SpectrumEnchantments.provideItemTags(this::getOrCreateTagBuilder);
		
		getOrCreateTagBuilder(SpectrumItemTags.COOKBOOKS).add(
				SpectrumItems.BREWERS_HANDBOOK,
				SpectrumItems.IMBRIFER_COOKBOOK,
				SpectrumItems.IMPERIAL_COOKBOOK,
				SpectrumItems.MELOCHITES_COOKBOOK_VOL_1,
				SpectrumItems.MELOCHITES_COOKBOOK_VOL_2,
				SpectrumItems.POISONERS_HANDBOOK);
		
		getOrCreateTagBuilder(ItemTags.BOOKSHELF_BOOKS)
				.addTag(SpectrumItemTags.COOKBOOKS)
				.add(SpectrumItems.GILDED_BOOK, SpectrumItems.GUIDEBOOK);
	}
	
}
