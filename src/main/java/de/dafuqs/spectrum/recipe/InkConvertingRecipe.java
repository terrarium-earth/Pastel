package de.dafuqs.spectrum.recipe;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

import java.util.*;

public class InkConvertingRecipe extends GatedSpectrumRecipe<RecipeInput> {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("midgame/place_color_picker");
	protected static final List<Item> INPUT_ITEMS = new ArrayList<>();
	
	protected final Ingredient inputIngredient;
	protected final InkColor color;
	protected final long amount;
	
	public InkConvertingRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier, Ingredient inputIngredient, InkColor color, long amount) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.inputIngredient = inputIngredient;
		this.color = color;
		this.amount = amount;
		
		for (ItemStack itemStack : inputIngredient.getMatchingStacks()) {
			Item item = itemStack.getItem();
			if (!INPUT_ITEMS.contains(item)) {
				INPUT_ITEMS.add(item);
			}
		}
		
		registerInToastManager(getType(), this);
	}
	
	public static boolean isInput(Item item) {
		return INPUT_ITEMS.contains(item);
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		return this.inputIngredient.test(inv.getStackInSlot(0));
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.COLOR_PICKER);
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.INK_CONVERTING_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.INK_CONVERTING;
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return DefaultedList.copyOf(Ingredient.empty(), this.inputIngredient);
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "ink_converting";
	}
	
	public InkColor getInkColor() {
		return this.color;
	}
	
	public long getInkAmount() {
		return this.amount;
	}
	
	public static class Serializer implements RecipeSerializer<InkConvertingRecipe> {
		
		public static final MapCodec<InkConvertingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.inputIngredient),
				InkColor.CODEC.fieldOf("ink_color").forGetter(recipe -> recipe.color),
				Codec.LONG.fieldOf("amount").forGetter(recipe -> recipe.amount)
		).apply(i, InkConvertingRecipe::new));
		
		public static final PacketCodec<RegistryByteBuf, InkConvertingRecipe> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.STRING, recipe -> recipe.group,
				PacketCodecs.BOOL, recipe -> recipe.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				Ingredient.PACKET_CODEC, recipe -> recipe.inputIngredient,
				InkColor.PACKET_CODEC, recipe -> recipe.color,
				PacketCodecs.VAR_LONG, recipe -> recipe.amount,
				InkConvertingRecipe::new
		);
		
		@Override
		public MapCodec<InkConvertingRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, InkConvertingRecipe> packetCodec() {
			return PACKET_CODEC;
		}
		
	}
	
}
