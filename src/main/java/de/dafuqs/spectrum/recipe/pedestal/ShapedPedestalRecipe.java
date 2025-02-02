package de.dafuqs.spectrum.recipe.pedestal;


import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import oshi.util.tuples.*;

import java.util.*;

public class ShapedPedestalRecipe extends PedestalRecipe {
	
	protected final int width;
	protected final int height;
	
	public ShapedPedestalRecipe(
			String group,
			boolean secret,
			Optional<Identifier> requiredAdvancementIdentifier,
			PedestalRecipeTier tier,
			int width,
			int height,
			List<IngredientStack> inputs,
			Map<GemstoneColor, Integer> gemstonePowderInputs,
			ItemStack output,
			float experience,
			int craftingTime,
			boolean skipRecipeRemainders,
			boolean noBenefitsFromYieldUpgrades
	) {
		super(group, secret, requiredAdvancementIdentifier, tier, inputs, gemstonePowderInputs, output, experience, craftingTime, skipRecipeRemainders, noBenefitsFromYieldUpgrades);
		
		this.width = width;
		this.height = height;
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		return getRecipeOrientation(inv) != null && super.matches(inv, world);
	}
	
	// Triplet<XOffset, YOffset, Flipped>
	public Triplet<Integer, Integer, Boolean> getRecipeOrientation(RecipeInput inv) {
		for (int i = 0; i <= 3 - this.width; ++i) {
			for (int j = 0; j <= 3 - this.height; ++j) {
				if (this.matchesPattern(inv, i, j, true)) {
					return new Triplet<>(i, j, true);
				}
				if (this.matchesPattern(inv, i, j, false)) {
					return new Triplet<>(i, j, false);
				}
			}
		}
		return null;
	}
	
	public boolean matchesPattern(RecipeInput inv, int offsetX, int offsetY, boolean flipped) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				int k = i - offsetX;
				int l = j - offsetY;
				IngredientStack ingredient = IngredientStack.EMPTY;
				if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
					if (flipped) {
						ingredient = this.inputs.get(this.width - k - 1 + l * this.width);
					} else {
						ingredient = this.inputs.get(k + l * this.width);
					}
				}
				
				if (!ingredient.test(inv.getStackInSlot(i + j * 3))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void consumeIngredients(PedestalBlockEntity pedestal) {
		super.consumeIngredients(pedestal);
		
		Triplet<Integer, Integer, Boolean> orientation = getRecipeOrientation(pedestal.recipeInput);
		if (orientation == null) {
			return;
		}
		
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				int ingredientStackId = orientation.getC() ? ((this.width - 1) - x) + this.width * y : x + this.width * y;
				int slot = (x + orientation.getA()) + 3 * (y + orientation.getB());
				
				IngredientStack ingredientStackAtPos = this.inputs.get(ingredientStackId);
				ItemStack slotStack = pedestal.getStack(slot);
				if (!ingredientStackAtPos.test(slotStack)) {
					SpectrumCommon.logError("Looks like DaFuqs fucked up Spectrums Pedestal recipe matching. Go open up a report with the recipe that was crafted and an image of the pedestals contents, please! :)");
				}
				
				if (!slotStack.isEmpty()) {
					decrementGridSlot(pedestal, slot, ingredientStackAtPos.getCount(), slotStack);
				}
			}
		}
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SHAPED_PEDESTAL_RECIPE_SERIALIZER;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public boolean isShapeless() {
		return false;
	}
	
	public static class Serializer implements RecipeSerializer<ShapedPedestalRecipe> {
		
		public static final MapCodec<ShapedPedestalRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				PedestalRecipeTier.CODEC.optionalFieldOf("tier", PedestalRecipeTier.BASIC).forGetter(recipe -> recipe.tier),
				Codec.INT.fieldOf("width").forGetter(recipe -> recipe.width),
				Codec.INT.fieldOf("height").forGetter(recipe -> recipe.height),
				IngredientStack.Serializer.CODEC.codec().listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
				Codec.simpleMap(SpectrumRegistries.GEMSTONE_COLORS.getCodec(), Codec.INT, SpectrumRegistries.GEMSTONE_COLORS).forGetter(recipe -> recipe.powderInputs),
				ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
				Codec.FLOAT.optionalFieldOf("experience", 0f).forGetter(recipe -> recipe.experience),
				Codec.INT.optionalFieldOf("time", 200).forGetter(recipe -> recipe.craftingTime),
				Codec.BOOL.optionalFieldOf("skip_recipe_remainders", false).forGetter(recipe -> recipe.skipRecipeRemainders),
				Codec.BOOL.optionalFieldOf("disable_yield_upgrades", false).forGetter(recipe -> recipe.noBenefitsFromYieldUpgrades)
		).apply(i, ShapedPedestalRecipe::new));
		
		public static final PacketCodec<RegistryByteBuf, ShapedPedestalRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, recipe -> recipe.group,
				PacketCodecs.BOOL, recipe -> recipe.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				PedestalRecipeTier.PACKET_CODEC, recipe -> recipe.tier,
				PacketCodecs.VAR_INT, recipe -> recipe.width,
				PacketCodecs.VAR_INT, recipe -> recipe.height,
				IngredientStack.Serializer.PACKET_CODEC.collect(PacketCodecs.toList()), recipe -> recipe.inputs,
				PacketCodecs.map(HashMap::new, PacketCodecs.registryValue(SpectrumRegistries.GEMSTONE_COLORS_KEY), PacketCodecs.VAR_INT), recipe -> recipe.powderInputs,
				ItemStack.PACKET_CODEC, recipe -> recipe.output,
				PacketCodecs.FLOAT, recipe -> recipe.experience,
				PacketCodecs.VAR_INT, recipe -> recipe.craftingTime,
				PacketCodecs.BOOL, recipe -> recipe.skipRecipeRemainders,
				PacketCodecs.BOOL, recipe -> recipe.noBenefitsFromYieldUpgrades,
				ShapedPedestalRecipe::new
		);
		
		@Override
		public MapCodec<ShapedPedestalRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, ShapedPedestalRecipe> packetCodec() {
			return PACKET_CODEC;
		}
	}
	
}
