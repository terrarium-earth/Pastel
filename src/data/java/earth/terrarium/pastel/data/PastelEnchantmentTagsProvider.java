package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PastelEnchantmentTagsProvider extends EnchantmentTagsProvider {


	public PastelEnchantmentTagsProvider(PackOutput p_341044_, CompletableFuture<HolderLookup.Provider> p_341146_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_341044_, p_341146_, PastelCommon.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		PastelEnchantments.provideEnchantmentTags(this::tag);
	}
	
}
