package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.*;
import de.dafuqs.spectrum.compat.REI.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.enchanter.*;
import de.dafuqs.spectrum.registries.*;
import me.shedaniel.rei.api.common.category.*;
import me.shedaniel.rei.api.common.display.basic.*;
import me.shedaniel.rei.api.common.entry.*;
import me.shedaniel.rei.api.common.util.*;
import net.minecraft.client.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class EnchantmentUpgradeDisplay extends EnchanterDisplay {
	
	protected final RegistryEntry<Enchantment> enchantment;
	
	static final int XP_INDEX = 8;
	static final int OVERXP_INDEX = 9;
	static final int NORMAL_INDEX = 10;
	static final int OVERCHANT_INDEX = 11;
	
	final int levelCap;
	final int maxNormal;
	final Text transKey;
	final RecipeScaling.ScalingData itemScaling;
	final RecipeScaling.ScalingData xpScaling;
	EntryIngredient normalOutputs, overchantOutputs; // this fucking sucks lmao
	int index = 1; // THIS IS EVEN WORSE
	
	public EnchantmentUpgradeDisplay(@NotNull RecipeEntry<EnchantmentUpgradeRecipe> recipeEntry) {
		super(recipeEntry, buildIngredients(recipeEntry.value()), recipeEntry.value().getResult(BasicDisplay.registryAccess()));
		
		var recipe = recipeEntry.value();
		enchantment = recipe.getEnchantment();
		levelCap = recipe.getLevelCap();
		maxNormal = enchantment.value().getMaxLevel();
		
		itemScaling = recipe.getItemScaling();
		xpScaling = recipe.getXPScaling();
		transKey = enchantment.value().description().copy().styled(s -> {
			s.withItalic(true);
			s.withColor(EnchantmentUpgradeCategory.NORMAL_COLOR);
			return s;
		});
		
		buildOutputs(recipe);
	}
	
	private static List<EntryIngredient> buildIngredients(EnchantmentUpgradeRecipe recipe) {
		List<EntryIngredient> inputs = new ArrayList<>();
		
		var enchant = recipe.getEnchantment();
		var levelCap = recipe.getLevelCap();
		var maxNormal = enchant.value().getMaxLevel();
		
		// You go first
		int requiredItemCountSplit = recipe.getBaseItemCost() / 8;
		int requiredItemCountModulo = recipe.getBaseItemCost() % 8;
		for (int i = 0; i < 8; i++) {
			int addAmount = i < requiredItemCountModulo ? 1 : 0;
			inputs.add(EntryIngredients.of(recipe.getBulkItem(), requiredItemCountSplit + addAmount));
		}
		
		// XP and Books
		var xpNormal = new ArrayList<ItemStack>();
		var xpOver = new ArrayList<ItemStack>();
		var enchNormal = new ArrayList<ItemStack>();
		var enchOver = new ArrayList<ItemStack>();
		
		for (int i = 1; i < levelCap; i++) {
			ItemStack gem = KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getXPScaling().apply(i), true);
			
			if (i < maxNormal) {
				appendBookStack(enchant, i, enchNormal);
				xpNormal.add(gem);
			}
			appendBookStack(enchant, i, enchOver);
			xpOver.add(gem);
		}
		
		inputs.add(EntryIngredients.ofItemStacks(xpNormal)); // The XP gem
		inputs.add(EntryIngredients.ofItemStacks(xpOver)); // The XP gem
		inputs.add(EntryIngredients.ofItemStacks(enchNormal)); // The center stack
		inputs.add(EntryIngredients.ofItemStacks(enchOver));
		return inputs;
	}
	
	private void buildOutputs(EnchantmentUpgradeRecipe recipe) {
		var enchNormal = new ArrayList<ItemStack>();
		var enchOver = new ArrayList<ItemStack>();
		
		for (int i = 2; i <= levelCap; i++) {
			if (i <= maxNormal)
				appendBookStack(recipe.getEnchantment(), i, enchNormal);
			appendBookStack(recipe.getEnchantment(), i, enchOver);
		}
		
		normalOutputs = EntryIngredients.ofItemStacks(enchNormal);
		overchantOutputs = EntryIngredients.ofItemStacks(enchOver);
	}
	
	private static void appendBookStack(RegistryEntry<Enchantment> enchant, int i, ArrayList<ItemStack> enchIn) {
		var enchStack = new ItemStack(Items.ENCHANTED_BOOK);
		var builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
		builder.set(enchant, i);
		enchStack.set(DataComponentTypes.STORED_ENCHANTMENTS, builder.build());
		enchIn.add(enchStack);
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.ENCHANTMENT_UPGRADE;
	}
	
	@Override
	public boolean isUnlocked() {
		MinecraftClient client = MinecraftClient.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, EnchanterRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
	}
}
