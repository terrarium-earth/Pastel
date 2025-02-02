package de.dafuqs.spectrum.recipe.fluid_converting;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.recipe.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class FluidConvertingRecipe extends GatedSpectrumRecipe<RecipeInput> {
	
	protected final Ingredient input;
	protected final ItemStack output;
	
	public FluidConvertingRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier, @NotNull Ingredient input, ItemStack output) {
		super(group, secret, requiredAdvancementIdentifier);
		this.input = input;
		this.output = output;
	}
	
	@Override
	public boolean matches(@NotNull RecipeInput inv, World world) {
		return this.input.test(inv.getStackInSlot(0));
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		return output.copy();
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return output;
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(this.input);
		return defaultedList;
	}
	
	public static class Serializer<T extends FluidConvertingRecipe> implements RecipeSerializer<T> {
		
		private final MapCodec<T> codec;
		private final PacketCodec<RegistryByteBuf, T> packetCodec;
		
		public Serializer(Function5<String, Boolean, Optional<Identifier>, Ingredient, ItemStack, T> factory) {
			codec = RecordCodecBuilder.mapCodec(i -> i.group(
					Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
					Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
					Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
					Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
					ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
			).apply(i, factory));
			
			packetCodec = PacketCodec.tuple(
					PacketCodecs.STRING, recipe -> recipe.group,
					PacketCodecs.BOOL, recipe -> recipe.secret,
					PacketCodecs.optional(Identifier.PACKET_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
					Ingredient.PACKET_CODEC, recipe -> recipe.input,
					ItemStack.PACKET_CODEC, recipe -> recipe.output,
					factory
			);
		}
		
		@Override
		public MapCodec<T> codec() {
			return codec;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, T> packetCodec() {
			return packetCodec;
		}
	}
	
}
