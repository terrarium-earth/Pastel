package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.registry.*;

import java.util.concurrent.*;

public class SpectrumEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
	
	public SpectrumEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}
	
	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		SpectrumEnchantments.provideEnchantmentTags(this::getOrCreateTagBuilder);
	}
	
}
