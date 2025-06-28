package earth.terrarium.pastel.recipe.enchanter;

import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.capabilities.ExperienceHandler;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntity;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import earth.terrarium.pastel.helpers.Ench;
import earth.terrarium.pastel.helpers.PacketCodecHelper;
import earth.terrarium.pastel.recipe.RecipeScaling;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class EnchantmentUpgradeRecipe extends EnchanterRecipe {
	
	protected final Either<Holder<Enchantment>, ResourceKey<Enchantment>> either;
	protected final int levelCap;
	protected final Ingredient bulkItem;
	protected final RecipeScaling.ScalingData itemScaling, xpScaling;
	
	protected final NonNullList<Ingredient> inputs;
	protected final ItemStack output;
	
	public EnchantmentUpgradeRecipe(
			String group,
			boolean secret,
			Optional<ResourceLocation> requiredAdvancementIdentifier,
			Either<Holder<Enchantment>, ResourceKey<Enchantment>> enchantmentEntry,
			int levelCap,
			Ingredient bulkItem,
			RecipeScaling.ScalingData xpScaling,
			RecipeScaling.ScalingData itemScaling
	) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.either = enchantmentEntry;
		this.levelCap = levelCap;
		this.bulkItem = bulkItem;
		this.itemScaling = itemScaling;
		this.xpScaling = xpScaling;
		
		NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
		
		if (enchantmentEntry.left().isPresent()) {
			var enchantment = enchantmentEntry.left().get();
			var baseMax = enchantment.value().getMaxLevel();
			if (levelCap < baseMax)
				throw new JsonParseException("Level Cap cannot be lower than the Enchantment's base level (levelCap " + levelCap + "< enchantment's" + baseMax + ")");
			
			ItemStack ingredientStack = new ItemStack(Items.ENCHANTED_BOOK);
			ingredientStack.enchant(enchantment, levelCap - 1);
			inputs.set(0, Ingredient.of(ingredientStack));
			inputs.set(1, bulkItem);
			this.inputs = inputs;
			
			ItemStack outputStack = new ItemStack(Items.ENCHANTED_BOOK);
			outputStack.enchant(enchantmentEntry.left().get(), levelCap);
			this.output = outputStack;
		}
		else {
			this.inputs = NonNullList.create();
			this.output = ItemStack.EMPTY;
		}
	}
	
	
	@Override
	public boolean matches(RecipeInput inv, Level level) {
		var center = inv.getItem(EnchanterBlockEntity.CENTER);
		if (either.left().isEmpty())
			throw new UnsupportedOperationException("Attempted to match a datagen enchantment upgrade");

		var enchantment = either.left().get();

		//Check if the book matches
		if (!inputs.getFirst().test(center)) {
			return false;
		}

		var enchantments = center.get(DataComponents.STORED_ENCHANTMENTS);
		if (enchantments == null) {
			return false;
		}

		var bookLevel = enchantments.getLevel(enchantment);

		if (!enchantments.keySet().contains(enchantment) || bookLevel >= levelCap) {
			return false;
		}

		// Check XP requirements
		var requiredXp = xpScaling.apply(bookLevel);
		var availableXp = Optional.ofNullable(inv.getItem(EnchanterBlockEntity.XP_STORAGE)
						.getCapability(PastelCapabilities.Misc.XP, level.registryAccess()))
				.map(ExperienceHandler::getStoredAmount)
				.orElse(0);

		if (availableXp < requiredXp)
			return false;

		// Finally, check the ingredients
		int bulkInput = 0;
		for (int i = 2; i < 10; i++) {
			ItemStack currentStack = inv.getItem(i);

			if (!currentStack.isEmpty()) {
				ItemStack slotStack = inv.getItem(i);
				if (bulkItem.test(slotStack)) {
					bulkInput += slotStack.getCount();
				} else {
					return false;
				}
			}
		}

		return bulkInput >= itemScaling.apply(bookLevel);
	}
	
	@Override
	public ItemStack assemble(RecipeInput inv, HolderLookup.Provider drm) {
		var stack = inv.getItem(EnchanterBlockEntity.CENTER).copy();

		if (stack.isEmpty())
			return ItemStack.EMPTY;

		var curLevel = stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(getEnchantment());
		Ench.addOrUpgradeEnchantment(stack, getEnchantment(), curLevel + 1, false, false);
		return stack;
	}

	@Override
	public void consumeIngredients(EnchanterBlockEntity enchanter, HolderLookup.Provider lookup, double scaling) {
		if (scaling == 0)
			PastelCommon.logWarning("Performed enchantment upgrade recipe with scaling of 0. This is concerning!");

		var itemDrain = itemScaling.apply(scaling);
		var xpDrain = xpScaling.apply(scaling);
		var inv = enchanter.getInputs();

		for (int i = 2; i < 10; i++) {
			if (bulkItem.test(inv.getStackInSlot(i))) {
				itemDrain -= inv.extractItem(i, itemDrain, false).getCount();
			}

			if (itemDrain == 0)
				break;
		}

		Optional.ofNullable(inv.getStackInSlot(EnchanterBlockEntity.XP_STORAGE)
						.getCapability(PastelCapabilities.Misc.XP, lookup))
				.map(storage -> storage.extract(xpDrain, false));
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryManager) {
		return output;
	}
	
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(PastelBlocks.ENCHANTER.get());
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.ENCHANTMENT_UPGRADE_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return PastelRecipeTypes.ENCHANTMENT_UPGRADE;
	}
	
	@Override
	public ResourceLocation getRecipeTypeUnlockIdentifier() {
		return EnchanterCraftingRecipe.UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "enchantment_upgrade";
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}
	
	// Janky Hack
	public Item getBulkItem() {
		var match = bulkItem.getItems();
		if (match != null && match.length > 0)
			return bulkItem.getItems()[0].getItem();
		return Items.AIR;
	}
	
	public int getBaseXPCost() {
		return xpScaling.apply(1);
	}
	
	public int getBaseItemCost() {
		return itemScaling.apply(1);
	}
	
	public RecipeScaling.ScalingData getXpScaling() {
		return xpScaling;
	}
	
	public RecipeScaling.ScalingData getItemScaling() {
		return itemScaling;
	}

	@Override
	public int getCraftingTime(double scaling) {
		return itemScaling.apply(scaling);
	}

	@Override
	public boolean noDiscounts() {
		return true;
	}

	public Holder<Enchantment> getEnchantment() {
		if (either.left().isEmpty()) {
			throw new UnsupportedOperationException("Attempted to match a datagen enchantment upgrade");
		}
		return either.left().get();
	}
	
	public int getLevelCap() {
		return levelCap;
	}
	
	public boolean isInNormalRange(int level) {
		if (either.left().isEmpty()) {
			throw new UnsupportedOperationException("Attempted to match a datagen enchantment upgrade");
		}
		return level < this.either.left().get().value().getMaxLevel();
	}
	
	public static class Serializer implements RecipeSerializer<EnchantmentUpgradeRecipe> {
		
		public static final MapCodec<EnchantmentUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				ResourceLocation.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Codec.either(Enchantment.CODEC, ResourceKey.codec(Registries.ENCHANTMENT)).fieldOf("enchantment").forGetter(c -> c.either),
				Codec.INT.fieldOf("level_cap").forGetter(recipe -> recipe.levelCap),
				Ingredient.CODEC_NONEMPTY.fieldOf("bulk_item").forGetter(recipe -> recipe.bulkItem),
				RecipeScaling.CODEC.fieldOf("xp_scaling").forGetter(recipe -> recipe.xpScaling),
				RecipeScaling.CODEC.fieldOf("item_scaling").forGetter(recipe -> recipe.itemScaling)
		).apply(i, EnchantmentUpgradeRecipe::new));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentUpgradeRecipe> STREAM_CODEC = PacketCodecHelper.tuple(
				ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
				ByteBufCodecs.BOOL, recipe -> recipe.secret,
				ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				ByteBufCodecs.either(Enchantment.STREAM_CODEC, ResourceKey.streamCodec(Registries.ENCHANTMENT)), c -> c.either,
				ByteBufCodecs.VAR_INT, recipe -> recipe.levelCap,
				Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.bulkItem,
				RecipeScaling.STREAM_CODEC, recipe -> recipe.xpScaling,
				RecipeScaling.STREAM_CODEC, recipe -> recipe.itemScaling,
				EnchantmentUpgradeRecipe::new
		);
		
		@Override
		public MapCodec<EnchantmentUpgradeRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, EnchantmentUpgradeRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
