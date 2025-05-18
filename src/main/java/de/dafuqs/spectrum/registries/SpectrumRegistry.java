package de.dafuqs.spectrum.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.helpers.CodecHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

public class SpectrumRegistry<T> extends MappedRegistry<T> {
	
	public SpectrumRegistry(ResourceKey<? extends Registry<T>> key, Lifecycle lifecycle) {
		super(key, lifecycle);
	}
	
	@Override
	public Codec<T> byNameCodec() {
		return this.referenceHolderWithLifecycle().flatComapMap(Holder.Reference::value, value -> this.safeCastToReference(this.wrapAsHolder(value)));
	}
	
	@Override
	public Codec<Holder<T>> holderByNameCodec() {
		return this.referenceHolderWithLifecycle().flatComapMap(entry -> entry, this::safeCastToReference);
	}
	
	protected Codec<Holder.Reference<T>> referenceHolderWithLifecycle() {
		return CodecHelper.SPECTRUM_DEFAULTED_IDENTIFIER.comapFlatMap(
				id -> this.getHolder(id).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Unknown registry key in " + this.key() + ": " + id)),
				entry -> entry.key().location());
	}
	
	protected DataResult<Holder.Reference<T>> safeCastToReference(Holder<T> entry) {
		return entry instanceof Holder.Reference<T> reference
				? DataResult.success(reference)
				: DataResult.error(() -> "Unregistered holder in " + this.key() + ": " + entry);
	}
	
	@Nullable
	public T get(@Nullable String id) {
		return id == null ? null : get(SpectrumCommon.ofSpectrumDefaulted(id));
	}
	
}