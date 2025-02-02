package de.dafuqs.spectrum.recipe.cinderhearth;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

import java.util.*;

public class CinderhearthRecipe extends GatedStackSpectrumRecipe<SingleStackRecipeInput> {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/blocks/cinderhearth");
	
	protected final IngredientStack ingredient;
	protected final int time;
	protected final float experience;
	protected final List<Pair<ItemStack, Float>> resultsWithChance;
	
	public CinderhearthRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier, IngredientStack ingredient, int time, float experience, List<Pair<ItemStack, Float>> resultsWithChance) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.ingredient = ingredient;
		this.time = time;
		this.experience = experience;
		this.resultsWithChance = resultsWithChance;
		
		registerInToastManager(getType(), this);
	}
	
	@Override
	public boolean matches(SingleStackRecipeInput input, World world) {
		return ingredient.test(input.getStackInSlot(0));
	}
	
	@Override
	@Deprecated
	public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup registryLookup) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryLookup) {
		return resultsWithChance.getFirst().getLeft();
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.CINDERHEARTH);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.CINDERHEARTH_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.CINDERHEARTH;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "cinderhearth";
	}
	
	@Override
	public List<IngredientStack> getIngredientStacks() {
		return List.of(this.ingredient);
	}
	
	public float getExperience() {
		return experience;
	}
	
	public int getCraftingTime() {
		return time;
	}
	
	public List<ItemStack> getRolledOutputs(Random random, float yieldMod) {
		List<ItemStack> output = new ArrayList<>();
		for (Pair<ItemStack, Float> possibleOutput : resultsWithChance) {
			float chance = possibleOutput.getRight();
			if (chance >= 1.0 || random.nextFloat() < chance * yieldMod) {
				ItemStack currentOutputStack = possibleOutput.getLeft();
				if (yieldMod > 1) {
					int totalCount = Support.getIntFromDecimalWithChance(currentOutputStack.getCount() * yieldMod, random);
					while (totalCount > 0) { // if the rolled count exceeds the max stack size we need to split them (unstackable items, counts > 64, ...)
						int count = Math.min(totalCount, currentOutputStack.getMaxCount());
						ItemStack outputStack = currentOutputStack.copy();
						outputStack.setCount(count);
						output.add(outputStack);
						totalCount -= count;
					}
				} else {
					output.add(currentOutputStack.copy());
				}
			}
		}
		return output;
	}
	
	public List<ItemStack> getPossibleOutputs() {
		List<ItemStack> outputs = new ArrayList<>();
		for (Pair<ItemStack, Float> pair : resultsWithChance) {
			outputs.add(pair.getLeft());
		}
		return outputs;
	}
	
	public List<Pair<ItemStack, Float>> getResultsWithChance() {
		return resultsWithChance;
	}
	
	public static class Serializer implements RecipeSerializer<CinderhearthRecipe> {
		
		public static final MapCodec<CinderhearthRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				IngredientStack.Serializer.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
				Codec.INT.fieldOf("time").forGetter(recipe -> recipe.time),
				Codec.FLOAT.optionalFieldOf("experience", 0f).forGetter(recipe -> recipe.experience),
				CodecHelper.mapPair(
						ItemStack.CODEC.fieldOf("result"),
						Codec.FLOAT.optionalFieldOf("chance", 1.0f)
				).codec().listOf().fieldOf("results").forGetter(recipe -> recipe.resultsWithChance)
		).apply(i, CinderhearthRecipe::new));
		
		public static final PacketCodec<RegistryByteBuf, CinderhearthRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, recipe -> recipe.group,
				PacketCodecs.BOOL, recipe -> recipe.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				IngredientStack.Serializer.PACKET_CODEC, recipe -> recipe.ingredient,
				PacketCodecs.VAR_INT, recipe -> recipe.time,
				PacketCodecs.FLOAT, recipe -> recipe.experience,
				PacketCodecHelper.pair(ItemStack.PACKET_CODEC, PacketCodecs.FLOAT).collect(PacketCodecs.toList()), recipe -> recipe.resultsWithChance,
				CinderhearthRecipe::new
		);
		
		@Override
		public MapCodec<CinderhearthRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, CinderhearthRecipe> packetCodec() {
			return PACKET_CODEC;
		}
	}
	
}
