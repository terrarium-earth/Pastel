package de.dafuqs.spectrum.recipe.pedestal;


import java.util.*;

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

public class ShapedPedestalRecipe extends PedestalRecipe {
	
	protected final int width;
	protected final int height;
	protected final RawShapedPedestalRecipe rawShapedRecipe;
	
	public ShapedPedestalRecipe(
			String group,
			boolean secret,
			Optional<Identifier> requiredAdvancementIdentifier,
			PedestalRecipeTier tier,
			RawShapedPedestalRecipe rawShapedRecipe,
			Map<GemstoneColor, Integer> gemstonePowderInputs,
			ItemStack output,
			float experience,
			int craftingTime,
			boolean skipRecipeRemainders,
			boolean noBenefitsFromYieldUpgrades
	) {
		super(group, secret, requiredAdvancementIdentifier, tier, rawShapedRecipe.getIngredients(), gemstonePowderInputs, output, experience, craftingTime, skipRecipeRemainders, noBenefitsFromYieldUpgrades);
		
		this.rawShapedRecipe = rawShapedRecipe;
		this.width = rawShapedRecipe.getWidth();
		this.height = rawShapedRecipe.getHeight();
	}
	
	@Override
	public boolean matches(PedestalRecipeInput inv, World world) {
		return rawShapedRecipe.matches(inv.getCraftingGridInput()) && super.matches(inv, world);
	}
	
	@Override
	public void consumeIngredients(PedestalBlockEntity pedestal) {
		super.consumeIngredients(pedestal);
		
		boolean mirrored = rawShapedRecipe.matches(pedestal.createRecipeInput().getCraftingGridInput(), true);
		CraftingRecipeInput.Positioned positioned = pedestal.createPositionedInput();
		
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				int ingredientStackId = (mirrored ? ((this.width - 1) - x) : x) + this.width * y;
				int slot = (x + positioned.left()) + 3 * (y + positioned.top());
				
				IngredientStack ingredientStackAtPos = this.inputs.get(ingredientStackId);
				ItemStack slotStack = pedestal.getStack(slot);
				if (!ingredientStackAtPos.test(slotStack)) {
					SpectrumCommon.logError("Looks like DaFuqs or Electro fucked up Spectrums Pedestal recipe matching. Go open up a report with the recipe that was crafted and an image of the pedestals contents, please! :)");
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
				RawShapedPedestalRecipe.CODEC.forGetter(recipe -> recipe.rawShapedRecipe),
				CodecHelper.registryMap(SpectrumRegistries.GEMSTONE_COLOR, Codec.INT).fieldOf("colors").forGetter(recipe -> recipe.powderInputs),
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
				RawShapedPedestalRecipe.PACKET_CODEC, recipe -> recipe.rawShapedRecipe,
				PacketCodecs.map(HashMap::new, PacketCodecs.registryValue(SpectrumRegistries.GEMSTONE_COLOR.getKey()), PacketCodecs.VAR_INT), recipe -> recipe.powderInputs,
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
