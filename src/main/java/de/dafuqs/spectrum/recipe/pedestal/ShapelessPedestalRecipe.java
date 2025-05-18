package de.dafuqs.spectrum.recipe.pedestal;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.api.item.GemstoneColor;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.blocks.pedestal.PedestalBlockEntity;
import de.dafuqs.spectrum.blocks.pedestal.PedestalRecipeInput;
import de.dafuqs.spectrum.helpers.CodecHelper;
import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShapelessPedestalRecipe extends PedestalRecipe {
	
	public ShapelessPedestalRecipe(
			String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier,
			PedestalRecipeTier tier, List<IngredientStack> craftingInputs, Map<GemstoneColor, Integer> gemstonePowderInputs, ItemStack output,
			float experience, int craftingTime, boolean skipRecipeRemainders, boolean noBenefitsFromYieldUpgrades
	) {
		super(group, secret, requiredAdvancementIdentifier, tier, craftingInputs, gemstonePowderInputs, output, experience, craftingTime, skipRecipeRemainders, noBenefitsFromYieldUpgrades);
	}
	
	@Override
	public boolean matches(PedestalRecipeInput recipeInput, Level world) {
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
				ItemStack slotStack = pedestal.getItem(slot);
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
				ResourceLocation.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				PedestalRecipeTier.CODEC.optionalFieldOf("tier", PedestalRecipeTier.BASIC).forGetter(recipe -> recipe.tier),
				IngredientStack.Serializer.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
				CodecHelper.registryMap(SpectrumRegistries.GEMSTONE_COLOR, Codec.INT).fieldOf("colors").forGetter(recipe -> recipe.powderInputs),
				ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
				Codec.FLOAT.optionalFieldOf("experience", 0f).forGetter(recipe -> recipe.experience),
				Codec.INT.optionalFieldOf("time", 200).forGetter(recipe -> recipe.craftingTime),
				Codec.BOOL.optionalFieldOf("skip_recipe_remainders", false).forGetter(recipe -> recipe.skipRecipeRemainders),
				Codec.BOOL.optionalFieldOf("disable_yield_upgrades", false).forGetter(recipe -> recipe.noBenefitsFromYieldUpgrades)
		).apply(i, ShapelessPedestalRecipe::new));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessPedestalRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
				ByteBufCodecs.BOOL, recipe -> recipe.secret,
				ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				PedestalRecipeTier.PACKET_CODEC, recipe -> recipe.tier,
				IngredientStack.Serializer.PACKET_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.inputs,
				ByteBufCodecs.map(HashMap::new, ByteBufCodecs.registry(SpectrumRegistries.GEMSTONE_COLOR.key()), ByteBufCodecs.VAR_INT), recipe -> recipe.powderInputs,
				ItemStack.STREAM_CODEC, recipe -> recipe.output,
				ByteBufCodecs.FLOAT, recipe -> recipe.experience,
				ByteBufCodecs.VAR_INT, recipe -> recipe.craftingTime,
				ByteBufCodecs.BOOL, recipe -> recipe.skipRecipeRemainders,
				ByteBufCodecs.BOOL, recipe -> recipe.noBenefitsFromYieldUpgrades,
				ShapelessPedestalRecipe::new
		);
		
		@Override
		public MapCodec<ShapelessPedestalRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapelessPedestalRecipe> streamCodec() {
			return PACKET_CODEC;
		}
	}
	
}
