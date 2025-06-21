package earth.terrarium.pastel.recipe.potion_workshop;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.item.ExperienceStorageItem;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.potion_workshop.PotionWorkshopBlockEntity;
import earth.terrarium.pastel.helpers.PacketCodecHelper;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class PotionWorkshopCraftingRecipe extends PotionWorkshopRecipe {
	
	protected final IngredientStack baseIngredient;
	protected final boolean consumeBaseIngredient;
	protected final int requiredExperience;
	protected final ItemStack output;
	
	public PotionWorkshopCraftingRecipe(
			String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, int craftingTime, int color,
			IngredientStack ingredient1, IngredientStack ingredient2, IngredientStack ingredient3,
			IngredientStack baseIngredient, boolean consumeBaseIngredient, int requiredExperience, ItemStack output
	) {
		super(group, secret, requiredAdvancementIdentifier, craftingTime, color, ingredient1, ingredient2, ingredient3);
		this.output = output;
		this.baseIngredient = baseIngredient;
		this.requiredExperience = requiredExperience;
		this.consumeBaseIngredient = consumeBaseIngredient;
		
		registerInToastManager(getType(), this);
	}
	
	public IngredientStack getBaseIngredient() {
		return baseIngredient;
	}
	
	public boolean consumesBaseIngredient() {
		return consumeBaseIngredient;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.POTION_WORKSHOP_CRAFTING_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return PastelRecipeTypes.POTION_WORKSHOP_CRAFTING;
	}
	
	@Override
	public boolean usesReagents() {
		return false;
	}
	
	@Override
	public int getRequiredExperience() {
		return this.requiredExperience;
	}
	
	@Override
	public ItemStack assemble(RecipeInput inventory, HolderLookup.Provider drm) {
		return output.copy();
	}
	
	@Override
	public boolean isValidBaseIngredient(ItemStack itemStack) {
		return baseIngredient.test(itemStack);
	}
	
	@Override
	public List<IngredientStack> getIngredientStacks() {
		NonNullList<IngredientStack> defaultedList = NonNullList.create();
		defaultedList.add(IngredientStack.ofItems(PastelItems.MERMAIDS_GEM.get()));
		defaultedList.add(this.baseIngredient);
		addIngredientStacks(defaultedList);
		return defaultedList;
	}
	
	@Override
	public boolean matches(@NotNull RecipeInput inv, Level world) {
		if (enoughExperienceSupplied(inv)) {
			return super.matches(inv, world);
		}
		return false;
	}
	
	// we just test for a single ExperienceStorageItem here instead
	// of iterating over every item. The specification mentions that
	// Only one is supported and just a single ExperienceStorageItem
	// should be used per recipe, tough
	private boolean enoughExperienceSupplied(RecipeInput inv) {
		if (this.requiredExperience > 0) {
			for (int i : new int[]{PotionWorkshopBlockEntity.BASE_INPUT_SLOT_ID, PotionWorkshopBlockEntity.FIRST_INGREDIENT_SLOT,
					PotionWorkshopBlockEntity.FIRST_INGREDIENT_SLOT + 1, PotionWorkshopBlockEntity.FIRST_INGREDIENT_SLOT + 2}) {
				
				if ((inv.getItem(i).getItem() instanceof ExperienceStorageItem)) {
					return ExperienceStorageItem.getStoredExperience(inv.getItem(i)) >= requiredExperience;
				}
			}
		}
		return true;
	}
	
	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryManager) {
		return output;
	}
	
	@Override
	public int getMinOutputCount(ItemStack itemStack) {
		return 1;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "potion_workshop_crafting";
	}
	
	public static class Serializer implements RecipeSerializer<PotionWorkshopCraftingRecipe> {
		
		public static final MapCodec<PotionWorkshopCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(c -> c.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(c -> c.secret),
				ResourceLocation.CODEC.optionalFieldOf("required_advancement").forGetter(c -> c.requiredAdvancementIdentifier),
				Codec.INT.optionalFieldOf("time", 200).forGetter(c -> c.craftingTime),
				Codec.INT.optionalFieldOf("color", 0xc03058).forGetter(c -> c.color),
				IngredientStack.CODEC.fieldOf("ingredient1").forGetter(c -> c.ingredient1),
				IngredientStack.CODEC.optionalFieldOf("ingredient2", IngredientStack.EMPTY).forGetter(c -> c.ingredient2),
				IngredientStack.CODEC.optionalFieldOf("ingredient3", IngredientStack.EMPTY).forGetter(c -> c.ingredient3),
				IngredientStack.CODEC.fieldOf("base_ingredient").forGetter(c -> c.baseIngredient),
				Codec.BOOL.optionalFieldOf("use_up_base_ingredient", true).forGetter(c -> c.consumeBaseIngredient),
				Codec.INT.optionalFieldOf("required_experience", 0).forGetter(c -> c.requiredExperience),
				ItemStack.CODEC.fieldOf("result").forGetter(c -> c.output)
		).apply(i, PotionWorkshopCraftingRecipe::new));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, PotionWorkshopCraftingRecipe> STREAM_CODEC = PacketCodecHelper.tuple(
				ByteBufCodecs.STRING_UTF8, c -> c.group,
				ByteBufCodecs.BOOL, c -> c.secret,
				ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), c -> c.requiredAdvancementIdentifier,
				ByteBufCodecs.VAR_INT, c -> c.craftingTime,
				ByteBufCodecs.VAR_INT, c -> c.color,
				IngredientStack.STREAM_CODEC, c -> c.ingredient1,
				IngredientStack.STREAM_CODEC, c -> c.ingredient2,
				IngredientStack.STREAM_CODEC, c -> c.ingredient3,
				IngredientStack.STREAM_CODEC, c -> c.baseIngredient,
				ByteBufCodecs.BOOL, c -> c.consumeBaseIngredient,
				ByteBufCodecs.VAR_INT, c -> c.requiredExperience,
				ItemStack.STREAM_CODEC, c -> c.output,
				PotionWorkshopCraftingRecipe::new
		);
		
		@Override
		public MapCodec<PotionWorkshopCraftingRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, PotionWorkshopCraftingRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
	
}
