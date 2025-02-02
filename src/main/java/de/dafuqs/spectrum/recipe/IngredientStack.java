package de.dafuqs.spectrum.recipe;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.predicate.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

import java.util.*;

public class IngredientStack implements CustomIngredient {
	
	private final Ingredient ingredient;
	private final ComponentPredicate componentPredicate;
	private final int count;
	
	// Thse are from the codec, to handle encoding
	private Item item = null;
	private TagKey<Item> tag = null;
	
	public static final IngredientStack EMPTY = new IngredientStack(Ingredient.EMPTY, null, 0);
	
	public IngredientStack(Ingredient ingredient, ComponentPredicate componentPredicate, int count) {
		this.ingredient = ingredient;
		this.componentPredicate = componentPredicate;
		this.count = count;
	}
	
	public IngredientStack(Ingredient ingredient) {
		this(ingredient, null, 1);
	}
	
	public int getCount() {
		return count;
	}
	
	public Ingredient getIngredient() {
		return ingredient;
	}
	
	public static IngredientStack of(Ingredient ingredient) {
		return new IngredientStack(ingredient);
	}
	
	public static IngredientStack ofItems(Item item) {
		return new IngredientStack(Ingredient.ofItems(item));
	}
	
	public static IngredientStack ofItems(int count, Item item) {
		IngredientStack ingredientStack = new IngredientStack(Ingredient.ofItems(item), null, count);
		ingredientStack.item = item;
		return ingredientStack;
	}
	
	public static IngredientStack ofTag(TagKey<Item> tag) {
		return new IngredientStack(Ingredient.fromTag(tag));
	}
	
	public static IngredientStack ofTag(TagKey<Item> tag, int count) {
		IngredientStack ingredientStack = new IngredientStack(Ingredient.fromTag(tag), null, count);
		ingredientStack.tag = tag;
		return ingredientStack;
	}
	
	@Override
	public boolean test(ItemStack itemStack) {
		return this.ingredient.test(itemStack) && this.componentPredicate.test(itemStack.getComponents()) && this.count == itemStack.getCount();
	}
	
	@Override
	public List<ItemStack> getMatchingStacks() {
		return Arrays.stream(this.ingredient.getMatchingStacks()).toList();
	}
	
	@Override
	public boolean requiresTesting() {
		return true;
	}
	
	public boolean isEmpty() {
		return this == EMPTY || this.ingredient.isEmpty();
	}
	
	@Override
	public CustomIngredientSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}
	
	public static class Serializer implements CustomIngredientSerializer<IngredientStack> {
		
		public static Serializer INSTANCE = new Serializer();
		
		public static final MapCodec<IngredientStack> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(o -> o.ingredient),
				ComponentPredicate.CODEC.fieldOf("components").forGetter(o -> o.componentPredicate),
				Codec.INT.fieldOf("count").forGetter(o -> o.count)
		).apply(i, IngredientStack::new));
		
		public static final Codec<IngredientStack> CODEC = Codec.withAlternative(
				Codec.xor(Registries.ITEM.getCodec(), TagKey.codec(RegistryKeys.ITEM)).xmap(
						either -> either.map(IngredientStack::ofItems, IngredientStack::ofTag),
						ingredientStack -> ingredientStack.item != null ? Either.left(ingredientStack.item) : Either.right(ingredientStack.tag)
				),
				Codec.withAlternative(
						Ingredient.DISALLOW_EMPTY_CODEC.xmap(IngredientStack::of, IngredientStack::getIngredient),
						MAP_CODEC.codec()
				)
		);
		
		public static final PacketCodec<RegistryByteBuf, IngredientStack> PACKET_CODEC = PacketCodec.tuple(
				Ingredient.PACKET_CODEC, o -> o.ingredient,
				ComponentPredicate.PACKET_CODEC, o -> o.componentPredicate,
				PacketCodecs.VAR_INT, o -> o.count,
				IngredientStack::new
		);
		
		@Override
		public Identifier getIdentifier() {
			return Identifier.of("spectrum:ingredient_stack");
		}
		
		@Override
		public MapCodec<IngredientStack> getCodec(boolean allowEmpty) {
			return MAP_CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, IngredientStack> getPacketCodec() {
			return PACKET_CODEC;
		}
	}
}
