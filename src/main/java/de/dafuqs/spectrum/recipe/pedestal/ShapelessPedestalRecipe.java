package de.dafuqs.spectrum.recipe.pedestal;


import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class ShapelessPedestalRecipe extends PedestalRecipe {
	
	public ShapelessPedestalRecipe(
			String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier,
			PedestalRecipeTier tier, List<IngredientStack> craftingInputs, Map<GemstoneColor, Integer> gemstonePowderInputs, ItemStack output,
			float experience, int craftingTime, boolean skipRecipeRemainders, boolean noBenefitsFromYieldUpgrades
	) {
		super(group, secret, requiredAdvancementIdentifier, tier, craftingInputs, gemstonePowderInputs, output, experience, craftingTime, skipRecipeRemainders, noBenefitsFromYieldUpgrades);
	}
	
	@Override
	public boolean matches(PedestalRecipeInput recipeInput, World world) {
		return matchIngredientStacksExclusively(recipeInput, getIngredientStacks(), recipeInput.getCraftingGridSlots()) && super.matches(recipeInput, world);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SHAPELESS_PEDESTAL_RECIPE_SERIALIZER;
	}
	
	@Override
	public void consumeIngredients(PedestalBlockEntity pedestal) {
		super.consumeIngredients(pedestal);
		
		for (int slot : CRAFTING_GRID_SLOTS) {
			for (IngredientStack ingredientStack : this.inputs) {
				ItemStack slotStack = pedestal.getStack(slot);
				if (ingredientStack.test(slotStack)) {
					decrementGridSlot(pedestal, slot, ingredientStack.getCount(), slotStack);
					break;
				}
			}
		}
	}
	
	public static class Serializer implements RecipeSerializer<ShapelessPedestalRecipe> {
		
		public static final MapCodec<ShapelessPedestalRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				PedestalRecipeTier.CODEC.optionalFieldOf("tier", PedestalRecipeTier.BASIC).forGetter(recipe -> recipe.tier),
				IngredientStack.Serializer.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
				CodecHelper.registryMap(SpectrumRegistries.GEMSTONE_COLOR, Codec.INT).fieldOf("colors").forGetter(recipe -> recipe.powderInputs),
				ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
				Codec.FLOAT.optionalFieldOf("experience", 0f).forGetter(recipe -> recipe.experience),
				Codec.INT.optionalFieldOf("time", 200).forGetter(recipe -> recipe.craftingTime),
				Codec.BOOL.optionalFieldOf("skip_recipe_remainders", false).forGetter(recipe -> recipe.skipRecipeRemainders),
				Codec.BOOL.optionalFieldOf("disable_yield_upgrades", false).forGetter(recipe -> recipe.noBenefitsFromYieldUpgrades)
		).apply(i, ShapelessPedestalRecipe::new));
		
		public static final PacketCodec<RegistryByteBuf, ShapelessPedestalRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, recipe -> recipe.group,
				PacketCodecs.BOOL, recipe -> recipe.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				PedestalRecipeTier.PACKET_CODEC, recipe -> recipe.tier,
				IngredientStack.Serializer.PACKET_CODEC.collect(PacketCodecs.toList()), recipe -> recipe.inputs,
				PacketCodecs.map(HashMap::new, PacketCodecs.registryValue(SpectrumRegistries.GEMSTONE_COLOR.getKey()), PacketCodecs.VAR_INT), recipe -> recipe.powderInputs,
				ItemStack.PACKET_CODEC, recipe -> recipe.output,
				PacketCodecs.FLOAT, recipe -> recipe.experience,
				PacketCodecs.VAR_INT, recipe -> recipe.craftingTime,
				PacketCodecs.BOOL, recipe -> recipe.skipRecipeRemainders,
				PacketCodecs.BOOL, recipe -> recipe.noBenefitsFromYieldUpgrades,
				ShapelessPedestalRecipe::new
		);
		
		@Override
		public MapCodec<ShapelessPedestalRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, ShapelessPedestalRecipe> packetCodec() {
			return PACKET_CODEC;
		}
	}
	
}
