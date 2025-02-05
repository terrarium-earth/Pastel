package de.dafuqs.spectrum.data;

import net.fabricmc.fabric.api.datagen.v1.*;

public class SpectrumDataGenerator implements DataGeneratorEntrypoint {
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpectrumEnchantmentTagProvider::new);
	}
	
}
