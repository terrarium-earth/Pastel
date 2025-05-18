package de.dafuqs.spectrum.recipe.crystallarieum;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.recipe.GatedSpectrumRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumFluids;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import de.dafuqs.spectrum.registries.SpectrumRecipeTypes;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrystallarieumRecipe extends GatedSpectrumRecipe<SingleRecipeInput> {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/blocks/crystallarieum");
	
	protected final static Map<BlockState, RecipeHolder<CrystallarieumRecipe>> STATE_CACHE = new HashMap<>();
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
	
	public CrystallarieumRecipe(String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, Ingredient ingredient, List<BlockState> growthStages, int secondsPerGrowthStage, InkColor inkColor, int inkPerSecond, boolean growsWithoutCatalyst, List<CrystallarieumCatalyst> catalysts, FluidVariant medium, List<ItemStack> additionalResults) {
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
	public static RecipeHolder<CrystallarieumRecipe> getRecipeForState(Level world, BlockState state) {
		return STATE_CACHE.computeIfAbsent(state, s -> {
			var recipes = world.getRecipeManager().getAllRecipesFor(SpectrumRecipeTypes.CRYSTALLARIEUM);
			for (var recipe : recipes) {
				if (recipe.value().growthStages.contains(s))
					return recipe;
			}
			return null;
		});
	}
	
	@Override
	public boolean matches(SingleRecipeInput input, Level world) {
		return ingredient.test(input.getItem(0));
	}
	
	@Override
	@Deprecated
	public ItemStack assemble(SingleRecipeInput inv, HolderLookup.Provider registryLookup) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryLookup) {
		List<BlockState> states = getGrowthStages();
		return states.getLast().getBlock().asItem().getDefaultInstance();
	}
	
	@Override
	public ItemStack getToastSymbol() {
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
	public ResourceLocation getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "crystallarieum_growing";
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
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
	
	public Optional<BlockState> getNextState(RecipeHolder<CrystallarieumRecipe> recipe, BlockState currentState) {
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
				ResourceLocation.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
				BlockState.CODEC.listOf().fieldOf("growth_stage_states").forGetter(recipe -> recipe.growthStages),
				Codec.INT.fieldOf("seconds_per_growth_stage").forGetter(recipe -> recipe.secondsPerGrowthStage),
				InkColor.CODEC.fieldOf("ink_color").forGetter(recipe -> recipe.inkColor),
				Codec.INT.xmap(
						d -> d == 0 ? 0 : (1 << (d - 1)),
						e -> e == 0 ? 0 : (31 - Integer.numberOfLeadingZeros(e)) + 1
				).fieldOf("ink_cost_tier").forGetter(recipe -> recipe.inkPerSecond),
				Codec.BOOL.optionalFieldOf("grows_without_catalyst", false).forGetter(recipe -> recipe.growsWithoutCatalyst),
				CrystallarieumCatalyst.CODEC.listOf().fieldOf("catalysts").forGetter(recipe -> recipe.catalysts),
				FluidVariant.CODEC.optionalFieldOf("fluid_medium", LIQUID_CRYSTAL).forGetter(recipe -> recipe.medium),
				ItemStack.CODEC.listOf().optionalFieldOf("additional_recipe_manager_results", ImmutableList.of()).forGetter(recipe -> recipe.additionalResults)
		).apply(i, CrystallarieumRecipe::new));
		
		private static final StreamCodec<RegistryFriendlyByteBuf, CrystallarieumRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
				ByteBufCodecs.BOOL, recipe -> recipe.secret,
				ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.ingredient,
				PacketCodecHelper.BLOCK_STATE.apply(ByteBufCodecs.list()), recipe -> recipe.growthStages,
				ByteBufCodecs.VAR_INT, recipe -> recipe.secondsPerGrowthStage,
				InkColor.PACKET_CODEC, recipe -> recipe.inkColor,
				ByteBufCodecs.VAR_INT, recipe -> recipe.inkPerSecond,
				ByteBufCodecs.BOOL, recipe -> recipe.growsWithoutCatalyst,
				CrystallarieumCatalyst.PACKET_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.catalysts,
				FluidVariant.PACKET_CODEC, recipe -> recipe.medium,
				ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.additionalResults,
				CrystallarieumRecipe::new
		);
		
		@Override
		public MapCodec<CrystallarieumRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, CrystallarieumRecipe> streamCodec() {
			return PACKET_CODEC;
		}
	}
	
}
