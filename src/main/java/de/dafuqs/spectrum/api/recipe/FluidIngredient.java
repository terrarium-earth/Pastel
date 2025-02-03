package de.dafuqs.spectrum.api.recipe;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.stream.*;

//TODO Refactor this to use registryEntryList or something, or at least an Either
public class FluidIngredient {
	
	public static final MapCodec<FluidIngredient> MAP_CODEC = Codec.mapEither(
			Registries.FLUID.getCodec().fieldOf("fluid"),
			Identifier.CODEC.fieldOf("tag")
	).xmap(FluidIngredient::new, c -> c.ingredient);
	
	public static final Codec<FluidIngredient> CODEC = MAP_CODEC.codec();
	
	public static final PacketCodec<RegistryByteBuf, FluidIngredient> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.either(PacketCodecs.registryValue(RegistryKeys.FLUID), Identifier.PACKET_CODEC), o -> o.ingredient,
			FluidIngredient::new
	);
	
	private final Either<Fluid, Identifier> ingredient;
	// Compare against EMPTY to check if empty.
	// In order to represent an empty value, specifically use this field.
	public static FluidIngredient EMPTY = new FluidIngredient(Either.left(Fluids.EMPTY));
	
	// Can't be both fluid and tag, so ONLY use the provided of() methods
	// NOTE: ALL FluidIngredient-related code assumes that either:
	// 1. there are always EITHER the fluid OR the fluid tag, NOT both
	// 2. the object is empty AND the object is EQUAL TO FluidIngredient.EMPTY.
	// Violation of either of those results in either an AssertionError or
	// undefined behavior. As such, don't even allow creation of invalid obj.
	// FluidIngredient objects with unknown/invalid tags are considered valid.
	private FluidIngredient(Either<Fluid, Identifier> ingredient) {
		this.ingredient = ingredient;
	}
	
	// NOTE: This is for testing. Doesn't explicitly handle invalid FluidInput.
	@Override
	public String toString() {
		if (this == EMPTY)
			return "FluidIngredient.EMPTY";
		if (this.ingredient.left().isPresent())
			return String.format("FluidIngredient[fluid=%s]", this.ingredient.left().get());
		assert this.ingredient.right().isPresent();
		return String.format("FluidIngredient[tag=%s]", this.ingredient.right().get());
	}
	
	public static FluidIngredient of(@NotNull Fluid fluid) {
		return new FluidIngredient(Either.left(fluid));
	}
	
	public static FluidIngredient of(@NotNull TagKey<Fluid> tag) {
		return new FluidIngredient(Either.right(tag.id()));
	}
	
	public static FluidIngredient of(@NotNull Identifier tag) {
		return new FluidIngredient(Either.right(tag));
	}
	
	public Optional<Fluid> fluid() {
		return this.ingredient.left();
	}
	
	public Optional<TagKey<Fluid>> tag() {
		return this.ingredient.right().flatMap(tag ->
				Registries.FLUID.streamTags().filter(tagKey -> tagKey.id().equals(tag)).findFirst());
	}
	
	public boolean isTag() {
		return this.ingredient.right().isPresent();
	}
	
	public Identifier id() {
		return ingredient.map(Registries.FLUID::getId, tag -> tag);
	}
	
	// Vanilla-friendly compatibility method.
	// Represents this FluidIngredient as bucket stack(s).
	public @NotNull Ingredient into() {
		return this.ingredient.map(
				fluid -> fluid == Fluids.EMPTY ? Ingredient.EMPTY : Ingredient.ofStacks(fluid.getBucketItem().getDefaultStack()),
				tag -> {
					// Handle custom fluid registries
					// in the case of FluidIngredient objects created by other mods.
					Registry<Fluid> registry = Registries.FLUID;
					if (registry == null) return Ingredient.empty();
					Optional<RegistryEntryList.Named<Fluid>> optional = registry.getEntryList(tag().get());
					if (optional.isEmpty()) return Ingredient.empty();
					RegistryEntryList.Named<Fluid> list = optional.get();
					Stream<ItemStack> stacks = list.stream().map((entry) -> entry.value().getBucketItem().getDefaultStack());
					return Ingredient.ofStacks(stacks);
				});
	}
	
	public boolean test(@NotNull Fluid fluid) {
		return this.ingredient.map(fl -> fl == fluid, tag -> fluid.getDefaultState().isIn(tag().get()));
	}
	
	public boolean test(@NotNull FluidVariant variant) {
		return test(variant.getFluid());
	}
	
}
