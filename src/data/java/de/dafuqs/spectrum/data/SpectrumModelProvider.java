package de.dafuqs.spectrum.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class SpectrumModelProvider extends FabricModelProvider {
	
	public SpectrumModelProvider(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		SpectrumModelHelper.BLOCK_STATE_MODEL_REGISTRAR.flush(blockStateModelGenerator);
	}
	
	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		SpectrumModelHelper.ITEM_MODEL_REGISTRAR.flush(itemModelGenerator);
	}
}