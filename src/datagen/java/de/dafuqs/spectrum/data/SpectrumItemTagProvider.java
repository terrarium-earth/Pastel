package de.dafuqs.spectrum.data;

import java.util.concurrent.*;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.core.*;
import net.minecraft.registry.tag.*;

public class SpectrumItemTagProvider extends FabricTagProvider.ItemTagProvider {
	
	public SpectrumItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupFuture) {
		super(output, lookupFuture);
	}
	
	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
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
