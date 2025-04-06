package de.dafuqs.spectrum.recipe.crystallarieum;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.block.*;
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

public class CrystallarieumRecipe extends GatedSpectrumRecipe<SingleStackRecipeInput> {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/blocks/crystallarieum");
	
	protected final static Map<BlockState, RecipeEntry<CrystallarieumRecipe>> STATE_CACHE = new HashMap<>();
	protected static final FluidVariant LIQUID_CRYSTAL = FluidVariant.of(SpectrumFluids.LIQUID_CRYSTAL);
	
	protected final Ingredient ingredient;
	protected final List<BlockState> growthStages;
	protected final int secondsPerGrowthStage;
	protected final InkColor inkColor;
	protected final int inkPerSecond;
	protected final boolean growsWithoutCatalyst;
	protected final List<CrystallarieumCatalyst> catalysts;
	protected final FluidVariant medium;
	protected final List<ItemStack> additionalResults; // these aren't actual results, but recipe managers will treat it as such, showing this recipe as a way to get them. Use for drops of the growth blocks, for example
	
	public CrystallarieumRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier, Ingredient ingredient, List<BlockState> growthStages, int secondsPerGrowthStage, InkColor inkColor, int inkPerSecond, boolean growsWithoutCatalyst, List<CrystallarieumCatalyst> catalysts, FluidVariant medium, List<ItemStack> additionalResults) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.ingredient = ingredient;
		this.growthStages = growthStages;
		this.secondsPerGrowthStage = secondsPerGrowthStage;
		this.inkColor = inkColor;
		this.inkPerSecond = inkPerSecond;
		this.growsWithoutCatalyst = growsWithoutCatalyst;
		this.catalysts = catalysts;
		this.medium = medium;
		this.additionalResults = additionalResults;
		
		registerInToastManager(getType(), this);
	}
	
	@Nullable
	public static RecipeEntry<CrystallarieumRecipe> getRecipeForState(World world, BlockState state) {
		return STATE_CACHE.computeIfAbsent(state, s -> {
			var recipes = world.getRecipeManager().listAllOfType(SpectrumRecipeTypes.CRYSTALLARIEUM);
			for (var recipe : recipes) {
				if (recipe.value().growthStages.contains(s))
					return recipe;
			}
			return null;
		});
	}
	
	@Override
	public boolean matches(SingleStackRecipeInput input, World world) {
		return ingredient.test(input.getStackInSlot(0));
	}
	
	@Override
	@Deprecated
	public ItemStack craft(SingleStackRecipeInput inv, RegistryWrapper.WrapperLookup registryLookup) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryLookup) {
		List<BlockState> states = getGrowthStages();
		return states.getLast().getBlock().asItem().getDefaultStack();
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.CRYSTALLARIEUM);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.CRYSTALLARIEUM_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.CRYSTALLARIEUM;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "crystallarieum_growing";
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(ingredient);
		return defaultedList;
	}
	
	public FluidVariant getFluidMedium() {
		return medium;
	}
	
	public Ingredient getIngredientStack() {
		return ingredient;
	}
	
	public CrystallarieumCatalyst getCatalyst(ItemStack itemStack) {
		for (CrystallarieumCatalyst catalyst : this.catalysts) {
			if (catalyst.ingredient().test(itemStack)) {
				return catalyst;
			}
		}
		return CrystallarieumCatalyst.EMPTY;
	}
	
	public List<BlockState> getGrowthStages() {
		return growthStages;
	}
	
	public int getSecondsPerGrowthStage() {
		return secondsPerGrowthStage;
	}
	
	public InkColor getInkColor() {
		return inkColor;
	}
	
	public int getInkPerSecond() {
		return inkPerSecond;
	}
	
	public boolean growsWithoutCatalyst() {
		return growsWithoutCatalyst;
	}
	
	public List<CrystallarieumCatalyst> getCatalysts() {
		return this.catalysts;
	}
	
	public List<ItemStack> getAdditionalResults() {
		return additionalResults;
	}
	
	public Optional<BlockState> getNextState(RecipeEntry<CrystallarieumRecipe> recipe, BlockState currentState) {
		for (Iterator<BlockState> it = recipe.value().getGrowthStages().iterator(); it.hasNext(); ) {
			BlockState state = it.next();
			if (state.equals(currentState)) {
				if (it.hasNext()) {
					return Optional.of(it.next());
				}
			}
		}
		return Optional.empty();
	}
	
	public static class Serializer implements RecipeSerializer<CrystallarieumRecipe> {
		
		private static final MapCodec<CrystallarieumRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
				BlockState.CODEC.listOf().fieldOf("growth_stage_states").forGetter(recipe -> recipe.growthStages),
				Codec.INT.fieldOf("seconds_per_growth_stage").forGetter(recipe -> recipe.secondsPerGrowthStage),
				InkColor.CODEC.fieldOf("ink_color").forGetter(recipe -> recipe.inkColor),
				Codec.INT.xmap(
						d -> d == 0 ? 0 : (int) Math.pow(2, d - 1),
						e -> e
				).fieldOf("ink_cost_tier").forGetter(recipe -> recipe.inkPerSecond),
				Codec.BOOL.optionalFieldOf("grows_without_catalyst", false).forGetter(recipe -> recipe.growsWithoutCatalyst),
				CrystallarieumCatalyst.CODEC.listOf().fieldOf("catalysts").forGetter(recipe -> recipe.catalysts),
				FluidVariant.CODEC.optionalFieldOf("fluid_medium", LIQUID_CRYSTAL).forGetter(recipe -> recipe.medium),
				ItemStack.CODEC.listOf().optionalFieldOf("additional_recipe_manager_results", ImmutableList.of()).forGetter(recipe -> recipe.additionalResults)
		).apply(i, CrystallarieumRecipe::new));
		
		private static final PacketCodec<RegistryByteBuf, CrystallarieumRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, recipe -> recipe.group,
				PacketCodecs.BOOL, recipe -> recipe.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				Ingredient.PACKET_CODEC, recipe -> recipe.ingredient,
				PacketCodecHelper.BLOCK_STATE.collect(PacketCodecs.toList()), recipe -> recipe.growthStages,
				PacketCodecs.VAR_INT, recipe -> recipe.secondsPerGrowthStage,
				InkColor.PACKET_CODEC, recipe -> recipe.inkColor,
				PacketCodecs.VAR_INT, recipe -> recipe.inkPerSecond,
				PacketCodecs.BOOL, recipe -> recipe.growsWithoutCatalyst,
				CrystallarieumCatalyst.PACKET_CODEC.collect(PacketCodecs.toList()), recipe -> recipe.catalysts,
				FluidVariant.PACKET_CODEC, recipe -> recipe.medium,
				ItemStack.PACKET_CODEC.collect(PacketCodecs.toList()), recipe -> recipe.additionalResults,
				CrystallarieumRecipe::new
		);
		
		@Override
		public MapCodec<CrystallarieumRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, CrystallarieumRecipe> packetCodec() {
			return PACKET_CODEC;
		}
	}
	
}
