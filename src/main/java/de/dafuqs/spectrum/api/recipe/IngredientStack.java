package de.dafuqs.spectrum.api.recipe;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.predicate.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class IngredientStack implements CustomIngredient {
	
	private final Ingredient ingredient;
	private final ComponentPredicate componentPredicate;
	private final ComponentChanges previewComponents;
	private final int count;
	
	// These are from the codec, to handle encoding
	private Item item = null;
	private TagKey<Item> tag = null;
	
	public static final IngredientStack EMPTY = new IngredientStack(Ingredient.EMPTY, ComponentPredicate.EMPTY, ComponentChanges.EMPTY, 0);
	
	public IngredientStack(Ingredient ingredient, ComponentPredicate componentPredicate, ComponentChanges previewComponents, int count) {
		this.ingredient = ingredient;
		this.componentPredicate = componentPredicate;
		this.previewComponents = previewComponents;
		this.count = count;
	}
	
	private IngredientStack(Ingredient ingredient) {
		this(ingredient, ComponentPredicate.EMPTY, ComponentChanges.EMPTY, 1);
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
	
	public static IngredientStack ofItems(Item item, int count) {
		IngredientStack ingredientStack = new IngredientStack(Ingredient.ofItems(item), ComponentPredicate.EMPTY, ComponentChanges.EMPTY, count);
		ingredientStack.item = item;
		return ingredientStack;
	}
	
	public static IngredientStack ofTag(TagKey<Item> tag) {
		return new IngredientStack(Ingredient.fromTag(tag));
	}
	
	public static IngredientStack ofTag(TagKey<Item> tag, int count) {
		IngredientStack ingredientStack = new IngredientStack(Ingredient.fromTag(tag), ComponentPredicate.EMPTY, ComponentChanges.EMPTY, count);
		ingredientStack.tag = tag;
		return ingredientStack;
	}
	
	@Override
	public boolean test(ItemStack itemStack) {
		return this.ingredient.test(itemStack)
				&& this.count <= itemStack.getCount()
				&& this.componentPredicate.test(itemStack.getComponents());
	}
	
	@Nullable
	private List<ItemStack> matchingStacks;
	
	@Override
	public List<ItemStack> getMatchingStacks() {
		if (this.matchingStacks == null) {
			ItemStack[] matchingStacks = this.ingredient.getMatchingStacks();
			List<ItemStack> stacks = new ArrayList<>(matchingStacks.length);
			for (ItemStack is : matchingStacks) {
				ItemStack stack = new ItemStack(is.getItem(), count);
				stack.applyChanges(previewComponents);
				stacks.add(stack);
			}
			this.matchingStacks = stacks;
		}
		return this.matchingStacks;
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
				MapCodec.assumeMapUnsafe(Ingredient.DISALLOW_EMPTY_CODEC).forGetter(IngredientStack::getIngredient),
				ComponentPredicate.CODEC.optionalFieldOf("components", ComponentPredicate.EMPTY).forGetter(o -> o.componentPredicate),
				ComponentChanges.CODEC.optionalFieldOf("preview_components", ComponentChanges.EMPTY).forGetter(o -> o.previewComponents),
				Codec.INT.optionalFieldOf("count", 1).forGetter(o -> o.count)
		).apply(i, IngredientStack::new));
		
		public static final Codec<IngredientStack> CODEC = Codec.withAlternative(
				MAP_CODEC.codec(),
				Codec.xor(Registries.ITEM.getCodec(), TagKey.codec(RegistryKeys.ITEM)).xmap(
						either -> either.map(IngredientStack::ofItems, IngredientStack::ofTag),
						ingredientStack -> ingredientStack.item != null ? Either.left(ingredientStack.item) : Either.right(ingredientStack.tag)
				)
		);
		
		public static final PacketCodec<RegistryByteBuf, IngredientStack> PACKET_CODEC = PacketCodec.tuple(
				Ingredient.PACKET_CODEC, o -> o.ingredient,
				ComponentPredicate.PACKET_CODEC, o -> o.componentPredicate,
				ComponentChanges.PACKET_CODEC, o -> o.previewComponents,
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
