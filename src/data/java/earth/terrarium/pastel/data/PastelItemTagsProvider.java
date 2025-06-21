package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PastelItemTagsProvider extends ItemTagsProvider {

	public PastelItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags, PastelCommon.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		PastelEnchantments.provideItemTags(this::tag);
		
		tag(PastelItemTags.COOKBOOKS).add(
			PastelItems.BREWERS_HANDBOOK.get(),
			PastelItems.IMBRIFER_COOKBOOK.get(),
			PastelItems.IMPERIAL_COOKBOOK.get(),
			PastelItems.MELOCHITES_COOKBOOK_VOL_1.get(),
			PastelItems.MELOCHITES_COOKBOOK_VOL_2.get(),
			PastelItems.POISONERS_HANDBOOK.get()
		);
		
		tag(ItemTags.BOOKSHELF_BOOKS)
				.addTag(PastelItemTags.COOKBOOKS)
				.add(
					PastelItems.GILDED_BOOK.get(),
					PastelItems.GUIDEBOOK.get()
				);
	}
	
}
