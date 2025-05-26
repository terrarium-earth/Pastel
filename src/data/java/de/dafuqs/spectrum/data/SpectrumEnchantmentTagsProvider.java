package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SpectrumEnchantmentTagsProvider extends EnchantmentTagsProvider {


	public SpectrumEnchantmentTagsProvider(PackOutput p_341044_, CompletableFuture<HolderLookup.Provider> p_341146_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_341044_, p_341146_, SpectrumCommon.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		SpectrumEnchantments.provideEnchantmentTags(this::tag);
	}
	
}
