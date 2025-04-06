package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.*;
import de.dafuqs.spectrum.compat.REI.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.recipe.enchanter.*;
import de.dafuqs.spectrum.registries.*;
import me.shedaniel.rei.api.common.category.*;
import me.shedaniel.rei.api.common.display.basic.*;
import me.shedaniel.rei.api.common.entry.*;
import me.shedaniel.rei.api.common.util.*;
import net.minecraft.client.*;
import net.minecraft.enchantment.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.entry.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class EnchantmentUpgradeDisplay extends EnchanterDisplay {
	
	protected final RegistryEntry<Enchantment> enchantment;
	protected final int enchantmentDestinationLevel;
	
	protected final int requiredExperience;
	protected final int requiredItemCount;
	
	public EnchantmentUpgradeDisplay(@NotNull RecipeEntry<EnchantmentUpgradeRecipe> recipe) {
		super(recipe, buildIngredients(recipe.value()), recipe.value().getResult(BasicDisplay.registryAccess()));
		
		this.enchantment = recipe.value().getEnchantment();
		this.enchantmentDestinationLevel = recipe.value().getLevelCap();
		this.requiredItemCount = recipe.value().getBaseItemCost();
		this.requiredExperience = recipe.value().getBaseXPCost();
	}
	
	private static List<EntryIngredient> buildIngredients(EnchantmentUpgradeRecipe recipe) {
		List<EntryIngredient> inputs = new ArrayList<>();
		inputs.add(EntryIngredients.ofIngredient(recipe.getIngredients().getFirst())); // the center stack
		int requiredItemCountSplit = recipe.getBaseItemCost() / 8;
		int requiredItemCountModulo = recipe.getBaseItemCost() % 8;
		for (int i = 0; i < 8; i++) {
			int addAmount = i < requiredItemCountModulo ? 1 : 0;
			inputs.add(EntryIngredients.of(recipe.getBulkItem(), requiredItemCountSplit + addAmount));
		}
		
		inputs.add(EntryIngredients.of(KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getBaseXPCost(), true)));
		return inputs;
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.ENCHANTMENT_UPGRADE;
	}
	
	@Override
    public boolean isUnlocked() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (!AdvancementHelper.hasAdvancement(client.player, EnchanterRecipe.UNLOCK_IDENTIFIER) || !super.isUnlocked()) {
			return false;
		}
		if (enchantmentDestinationLevel > enchantment.value().getMaxLevel()) {
			return AdvancementHelper.hasAdvancement(client.player, SpectrumAdvancements.OVERENCHANTING);
		} else {
			return true;
		}
	}
	
}
