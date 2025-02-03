package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.registry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;
import java.util.stream.*;

@Mixin(RegistryLoader.class)
public class RegistryLoaderMixin {
	
	@WrapOperation(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/registry/RegistryLoader;DYNAMIC_REGISTRIES:Ljava/util/List;"))
	private static void injectDynamicRegistries(List<RegistryLoader.Entry<?>> value, Operation<Void> original) {
		Stream<RegistryLoader.Entry<?>> originalStream = value.stream();
		Stream<RegistryLoader.Entry<?>> spectrumStream = Stream.concat(SpectrumRegistries.DYNAMIC_SYNCED.stream(), SpectrumRegistries.DYNAMIC_UNSYNCED.stream());
		original.call(Stream.concat(originalStream, spectrumStream).toList());
	}
	
	@WrapOperation(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/registry/RegistryLoader;SYNCED_REGISTRIES:Ljava/util/List;"))
	private static void injectSyncedRegistries(List<RegistryLoader.Entry<?>> value, Operation<Void> original) {
		Stream<RegistryLoader.Entry<?>> originalStream = value.stream();
		Stream<RegistryLoader.Entry<?>> spectrumStream = SpectrumRegistries.DYNAMIC_SYNCED.stream();
		original.call(Stream.concat(originalStream, spectrumStream).toList());
	}
	
	@Mixin(RegistryLoader.Entry.class)
	static class Entry {
		
		@WrapOperation(method = "getLoader(Lcom/mojang/serialization/Lifecycle;Ljava/util/Map;)Lnet/minecraft/registry/RegistryLoader$Loader;", at = @At(value = "NEW", target = "Lnet/minecraft/registry/SimpleRegistry;"))
		public <T> SimpleRegistry<T> constructSpectrumRegistry(RegistryKey<? extends Registry<T>> key, Lifecycle lifecycle, Operation<SimpleRegistry<T>> original) {
			SimpleRegistry<T> registry = original.call(key, lifecycle);
			if (registry.getKey().getValue().getNamespace().equals(SpectrumCommon.MOD_ID)) {
				registry = new SpectrumRegistry<>(registry.getKey(), registry.getLifecycle());
			}
			return registry;
		}
		
	}
	
}
