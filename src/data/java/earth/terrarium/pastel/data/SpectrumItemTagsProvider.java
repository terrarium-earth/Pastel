package earth.terrarium.pastel.data;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.registries.SpectrumEnchantments;
import earth.terrarium.pastel.registries.SpectrumItemTags;
import earth.terrarium.pastel.registries.SpectrumItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SpectrumItemTagsProvider extends ItemTagsProvider {

	public SpectrumItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags, SpectrumCommon.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		SpectrumEnchantments.provideItemTags(this::tag);
		
		tag(SpectrumItemTags.COOKBOOKS).add(
			SpectrumItems.BREWERS_HANDBOOK.get(),
			SpectrumItems.IMBRIFER_COOKBOOK.get(),
			SpectrumItems.IMPERIAL_COOKBOOK.get(),
			SpectrumItems.MELOCHITES_COOKBOOK_VOL_1.get(),
			SpectrumItems.MELOCHITES_COOKBOOK_VOL_2.get(),
			SpectrumItems.POISONERS_HANDBOOK.get()
		);
		
		tag(ItemTags.BOOKSHELF_BOOKS)
				.addTag(SpectrumItemTags.COOKBOOKS)
				.add(
					SpectrumItems.GILDED_BOOK.get(),
					SpectrumItems.GUIDEBOOK.get()
				);
	}
	
}
