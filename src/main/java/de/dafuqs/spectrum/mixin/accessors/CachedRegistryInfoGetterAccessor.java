package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RegistryOps.HolderLookupAdapter.class)
public interface CachedRegistryInfoGetterAccessor {
	
	@Accessor
	HolderLookup.Provider getLookupProvider();
	
}
