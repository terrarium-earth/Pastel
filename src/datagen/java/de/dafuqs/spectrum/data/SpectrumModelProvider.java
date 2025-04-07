package de.dafuqs.spectrum.data;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.data.client.*;

public class SpectrumModelProvider extends FabricModelProvider {
	
	public SpectrumModelProvider(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		SpectrumModelHelper.BLOCK_STATE_MODEL_REGISTRAR.flush(blockStateModelGenerator);
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		SpectrumModelHelper.ITEM_MODEL_REGISTRAR.flush(itemModelGenerator);
	}
}