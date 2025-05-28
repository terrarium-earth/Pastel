package earth.terrarium.pastel.data;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.registries.SpectrumBlockTags;
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
		SpectrumBlockTags.provideTags(this::tag);
	}
	
}