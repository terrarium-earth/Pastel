package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.registry.*;

import java.util.concurrent.*;

public class SpectrumItemTagProvider extends FabricTagProvider.ItemTagProvider {
	
	public SpectrumItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupFuture) {
		super(output, lookupFuture);
	}
	
	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		SpectrumEnchantments.provideTags(this::getOrCreateTagBuilder);
	}
	
}
