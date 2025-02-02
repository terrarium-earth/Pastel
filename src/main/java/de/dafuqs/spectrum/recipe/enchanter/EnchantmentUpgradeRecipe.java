package de.dafuqs.spectrum.recipe.enchanter;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.item.*;
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

import java.util.*;

public class EnchantmentUpgradeRecipe extends GatedSpectrumRecipe<RecipeInput> {
	
	protected final RegistryEntry<Enchantment> enchantmentEntry;
	protected final int enchantmentDestinationLevel;
	protected final int requiredExperience;
	protected final Item requiredItem;
	protected final int requiredItemCount;
	
	protected final DefaultedList<Ingredient> inputs;
	protected final ItemStack output;
	
	public EnchantmentUpgradeRecipe(
			String group,
			boolean secret,
			Optional<Identifier> requiredAdvancementIdentifier,
			RegistryEntry<Enchantment> enchantmentEntry,
			int enchantmentDestinationLevel,
			int requiredExperience,
			Item requiredItem,
			int requiredItemCount
	) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.enchantmentEntry = enchantmentEntry;
		this.enchantmentDestinationLevel = enchantmentDestinationLevel;
		this.requiredExperience = requiredExperience;
		this.requiredItem = requiredItem;
		this.requiredItemCount = requiredItemCount;
		
		DefaultedList<Ingredient> inputs = DefaultedList.ofSize(2, Ingredient.EMPTY);
		
		ItemStack ingredientStack = new ItemStack(Items.ENCHANTED_BOOK);
		ingredientStack.addEnchantment(enchantmentEntry, enchantmentDestinationLevel - 1);
		inputs.set(0, Ingredient.ofStacks(ingredientStack));
		inputs.set(1, Ingredient.ofStacks(new ItemStack(requiredItem)));
		this.inputs = inputs;
		
		ItemStack outputStack = new ItemStack(Items.ENCHANTED_BOOK);
		outputStack.addEnchantment(enchantmentEntry, enchantmentDestinationLevel);
		this.output = outputStack;
	}
	
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		if (inv.getSize() > 9) {
			if (!inputs.getFirst().test(inv.getStackInSlot(0))) {
				return false;
			}
			ItemEnchantmentsComponent enchantments = inv.getStackInSlot(0).getEnchantments();
			if (!enchantments.getEnchantments().contains(enchantmentEntry) || enchantments.getLevel(enchantmentEntry) != enchantmentDestinationLevel - 1) {
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
		return enchantmentEntry;
	}
	
	public int getEnchantmentDestinationLevel() {
		return enchantmentDestinationLevel;
	}
	
	public boolean requiresUnlockedOverEnchanting() {
		return this.enchantmentDestinationLevel > this.enchantmentEntry.value().getMaxLevel();
	}
	
	public static EnchantmentUpgradeRecipe createRecipes(String group, boolean secret, Optional<Identifier> requiredAdvancementId, RegistryEntry<Enchantment> enchantEntry, List<EnchantUpgradeLevelEntry> enchantUpgradeLevelEntries) {
		List<EnchantmentUpgradeRecipe> recipes = new ArrayList<>();
		for (EnchantUpgradeLevelEntry enchantUpgradeLevelEntry : enchantUpgradeLevelEntries) {
			recipes.add(new EnchantmentUpgradeRecipe(
					group, secret, requiredAdvancementId, enchantEntry, enchantUpgradeLevelEntries.size(), enchantUpgradeLevelEntry.experience(), enchantUpgradeLevelEntry.requiredItem(), enchantUpgradeLevelEntry.count()
			));
		}
		return recipes.getFirst();
	}
	
	public List<EnchantUpgradeLevelEntry> getDefaultLevelEntry() {
		return List.of(new EnchantUpgradeLevelEntry(
				this.requiredExperience, this.requiredItem, this.requiredItemCount
		));
	}
	
	public static class Serializer implements RecipeSerializer<EnchantmentUpgradeRecipe> {
		
		public static final MapCodec<EnchantmentUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Enchantment.ENTRY_CODEC.fieldOf("enchantment").forGetter(recipe -> recipe.enchantmentEntry),
				EnchantUpgradeLevelEntry.CODEC.listOf().fieldOf("levels").forGetter(EnchantmentUpgradeRecipe::getDefaultLevelEntry)
		).apply(i, EnchantmentUpgradeRecipe::createRecipes));
		
		public static final PacketCodec<RegistryByteBuf, EnchantmentUpgradeRecipe> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.STRING, recipe -> recipe.group,
				PacketCodecs.BOOL, recipe -> recipe.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
				Enchantment.ENTRY_PACKET_CODEC, recipe -> recipe.enchantmentEntry,
				EnchantUpgradeLevelEntry.PACKET_CODEC.collect(PacketCodecs.toList()), EnchantmentUpgradeRecipe::getDefaultLevelEntry,
				EnchantmentUpgradeRecipe::createRecipes
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
		
		public static final Codec<EnchantUpgradeLevelEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
				Codec.INT.fieldOf("experience").forGetter(EnchantUpgradeLevelEntry::experience),
				Registries.ITEM.getCodec().fieldOf("required_item").forGetter(EnchantUpgradeLevelEntry::requiredItem),
				Codec.INT.fieldOf("count").forGetter(EnchantUpgradeLevelEntry::count)
		).apply(i, EnchantUpgradeLevelEntry::new));
		
		public static final PacketCodec<RegistryByteBuf, EnchantUpgradeLevelEntry> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.VAR_INT, EnchantUpgradeLevelEntry::experience,
				PacketCodecs.registryValue(RegistryKeys.ITEM), EnchantUpgradeLevelEntry::requiredItem,
				PacketCodecs.VAR_INT, EnchantUpgradeLevelEntry::count,
				EnchantUpgradeLevelEntry::new
		);
		
	}
	
}
