package de.dafuqs.spectrum.recipe.enchanter;

import java.util.*;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.enchantment.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

public class EnchantmentUpgradeRecipe extends GatedSpectrumRecipe<RecipeInput> implements IRecipeGenerator {
	
	private static final List<EnchantmentUpgradeRecipe> EXTRA_RECIPES = new ArrayList<>();
	
	private final Either<RegistryEntry<Enchantment>, RegistryKey<Enchantment>> enchantment;
	
	protected final int enchantmentDestinationLevel;
	protected final int requiredExperience;
	protected final Item requiredItem;
	protected final int requiredItemCount;
	
	protected final DefaultedList<Ingredient> inputs;
	protected final ItemStack output;
	
	public EnchantmentUpgradeRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier, Either<RegistryEntry<Enchantment>, RegistryKey<Enchantment>> enchantment, int enchantmentDestinationLevel, int requiredExperience, Item requiredItem, int requiredItemCount) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.enchantment = enchantment;
		this.enchantmentDestinationLevel = enchantmentDestinationLevel;
		this.requiredExperience = requiredExperience;
		this.requiredItem = requiredItem;
		this.requiredItemCount = requiredItemCount;
		
		if (enchantment.left().isPresent()) {
			DefaultedList<Ingredient> inputs = DefaultedList.ofSize(2, Ingredient.EMPTY);
			
			ItemStack ingredientStack = new ItemStack(Items.ENCHANTED_BOOK);
			ingredientStack.addEnchantment(enchantment.left().get(), enchantmentDestinationLevel - 1);
			inputs.set(0, Ingredient.ofStacks(ingredientStack));
			inputs.set(1, Ingredient.ofStacks(new ItemStack(requiredItem)));
			this.inputs = inputs;
			
			ItemStack outputStack = new ItemStack(Items.ENCHANTED_BOOK);
			outputStack.addEnchantment(enchantment.left().get(), enchantmentDestinationLevel);
			this.output = outputStack;
		} else {
			this.inputs = DefaultedList.of();
			this.output = ItemStack.EMPTY;
		}
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		if (inv.getSize() > 9) {
			if (!inputs.getFirst().test(inv.getStackInSlot(0))) {
				return false;
			}
			ItemEnchantmentsComponent enchantments = inv.getStackInSlot(0).getEnchantments();
			if (!enchantments.getEnchantments().contains(getEnchantment()) || enchantments.getLevel(getEnchantment()) != enchantmentDestinationLevel - 1) {
				return false;
			}
			if (this.getRequiredExperience() > 0
					&& (!(inv.getStackInSlot(1).getItem() instanceof ExperienceStorageItem)
					|| !(ExperienceStorageItem.getStoredExperience(inv.getStackInSlot(1)) >= this.getRequiredExperience()))) {
				return false;
			}
			
			Ingredient inputIngredient = inputs.get(1);
			int ingredientsFound = 0;
			for (int i = 1; i < 9; i++) {
				ItemStack currentStack = inv.getStackInSlot(i + 1);
				
				if (!currentStack.isEmpty()) {
					ItemStack slotStack = inv.getStackInSlot(i + 1);
					if (inputIngredient.test(slotStack)) {
						ingredientsFound += slotStack.getCount();
					} else {
						return false;
					}
				}
			}
			
			return ingredientsFound >= requiredItemCount;
		}
		return false;
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		return output.copy();
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return output;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.ENCHANTER);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.ENCHANTMENT_UPGRADE_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.ENCHANTMENT_UPGRADE;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return EnchanterRecipe.UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "enchantment_upgrade";
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return inputs;
	}
	
	public int getRequiredExperience() {
		return requiredExperience;
	}
	
	public Item getRequiredItem() {
		return requiredItem;
	}
	
	public int getRequiredItemCount() {
		return requiredItemCount;
	}
	
	public RegistryEntry<Enchantment> getEnchantment() {
		if (enchantment.left().isEmpty()) {
			throw new UnsupportedOperationException("Attempted to match a datagen enchantment upgrade");
		}
		return enchantment.left().get();
	}
	
	public int getEnchantmentDestinationLevel() {
		return enchantmentDestinationLevel;
	}
	
	public boolean requiresUnlockedOverEnchanting() {
		return this.enchantmentDestinationLevel > getEnchantment().value().getMaxLevel();
	}
	
	public EnchantUpgradeLevelEntry getLevelEntry() {
		return new EnchantUpgradeLevelEntry(this.requiredExperience, this.requiredItem, this.requiredItemCount);
	}
	
	@Override
	public List<EnchantmentUpgradeRecipe> getAdditionalRecipes() {
		return EXTRA_RECIPES;
	}
	
	@Override
	public Identifier transformRecipeId(Identifier original, int index) {
		return SpectrumCommon.locate(original.getPath() + "_level_" + (index + 2));
	}
	
	public static class Serializer implements RecipeSerializer<EnchantmentUpgradeRecipe> {
		
		public static final MapCodec<EnchantmentUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(c -> c.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(c -> c.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(c -> c.requiredAdvancementIdentifier),
				Codec.either(Enchantment.ENTRY_CODEC, RegistryKey.createCodec(RegistryKeys.ENCHANTMENT)).fieldOf("enchantment").forGetter(c -> c.enchantment),
				Codec.INT.fieldOf("destination_level").forGetter(c -> c.enchantmentDestinationLevel),
				Codec.INT.fieldOf("experience").forGetter(c -> c.requiredExperience),
				Registries.ITEM.getCodec().fieldOf("item").forGetter(c -> c.requiredItem),
				Codec.INT.fieldOf("item_count").forGetter(c -> c.requiredItemCount)
		).apply(i, EnchantmentUpgradeRecipe::new));
		
		public static final PacketCodec<RegistryByteBuf, EnchantmentUpgradeRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, c -> c.group,
				PacketCodecs.BOOL, c -> c.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), c -> c.requiredAdvancementIdentifier,
				PacketCodecs.either(Enchantment.ENTRY_PACKET_CODEC, RegistryKey.createPacketCodec(RegistryKeys.ENCHANTMENT)), c -> c.enchantment,
				PacketCodecs.VAR_INT, c -> c.enchantmentDestinationLevel,
				PacketCodecs.VAR_INT, c -> c.requiredExperience,
				PacketCodecs.registryValue(RegistryKeys.ITEM), c -> c.requiredItem,
				PacketCodecs.VAR_INT, c -> c.requiredItemCount,
				EnchantmentUpgradeRecipe::new
		);
		
		@Override
		public MapCodec<EnchantmentUpgradeRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, EnchantmentUpgradeRecipe> packetCodec() {
			return PACKET_CODEC;
		}
	}
	
	public record EnchantUpgradeLevelEntry(int experience, Item requiredItem, int count) {
		
		public static final MapCodec<EnchantUpgradeLevelEntry> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.INT.fieldOf("experience").forGetter(EnchantUpgradeLevelEntry::experience),
				Registries.ITEM.getCodec().fieldOf("item").forGetter(EnchantUpgradeLevelEntry::requiredItem),
				Codec.INT.fieldOf("item_count").forGetter(EnchantUpgradeLevelEntry::count)
		).apply(i, EnchantUpgradeLevelEntry::new));
		
		public static final PacketCodec<RegistryByteBuf, EnchantUpgradeLevelEntry> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.VAR_INT, EnchantUpgradeLevelEntry::experience,
				PacketCodecs.registryValue(RegistryKeys.ITEM), EnchantUpgradeLevelEntry::requiredItem,
				PacketCodecs.VAR_INT, EnchantUpgradeLevelEntry::count,
				EnchantUpgradeLevelEntry::new
		);
		
	}
	
}
