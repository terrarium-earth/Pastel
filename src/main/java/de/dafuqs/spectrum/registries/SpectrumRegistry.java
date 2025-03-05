package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import org.jetbrains.annotations.*;

public class SpectrumRegistry<T> extends SimpleRegistry<T> {
	
	public SpectrumRegistry(RegistryKey<? extends Registry<T>> key, Lifecycle lifecycle) {
		super(key, lifecycle);
	}
	
	@Override
	public Codec<T> getCodec() {
		return this.getReferenceEntryCodec().flatComapMap(RegistryEntry.Reference::value, value -> this.validateReference(this.getEntry(value)));
	}
	
	@Override
	public Codec<RegistryEntry<T>> getEntryCodec() {
		return this.getReferenceEntryCodec().flatComapMap(entry -> entry, this::validateReference);
	}
	
	protected Codec<RegistryEntry.Reference<T>> getReferenceEntryCodec() {
		return CodecHelper.SPECTRUM_DEFAULTED_IDENTIFIER.comapFlatMap(
				id -> this.getEntry(id).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Unknown registry key in " + this.getKey() + ": " + id)),
				entry -> entry.registryKey().getValue());
	}
	
	protected DataResult<RegistryEntry.Reference<T>> validateReference(RegistryEntry<T> entry) {
		return entry instanceof RegistryEntry.Reference<T> reference
				? DataResult.success(reference)
				: DataResult.error(() -> "Unregistered holder in " + this.getKey() + ": " + entry);
	}
	
	@Nullable
	public T get(@Nullable String id) {
		return id == null ? null : get(SpectrumCommon.ofSpectrumDefaulted(id));
	}
	
}