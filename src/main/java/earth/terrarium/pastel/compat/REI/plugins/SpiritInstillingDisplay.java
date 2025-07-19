package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.REIHelper;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.helpers.render.LoreHelper;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.SpawnerChangeRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class SpiritInstillingDisplay extends PastelDisplay {
	
	protected final float experience;
	protected final int craftingTime;
	
	public SpiritInstillingDisplay(@NotNull RecipeHolder<SpiritInstillerRecipe> recipe) {
		super(recipe, REIHelper.toEntryIngredients(recipe.value().getIngredientStacks()), Collections.singletonList(buildOutput(recipe.value())));
		this.experience = recipe.value().getExperience();
		this.craftingTime = recipe.value().getCraftingTime();
	}
	
	public static EntryIngredient buildOutput(SpiritInstillerRecipe recipe) {
		if (recipe instanceof SpawnerChangeRecipe spawnerChangeRecipe) {
			ItemStack outputStack = recipe.getResultItem(BasicDisplay.registryAccess());
			LoreHelper.setLore(outputStack, spawnerChangeRecipe.getOutputLoreText());
			return EntryIngredients.of(outputStack);
		} else {
			return EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()));
		}
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return PastelPlugins.SPIRIT_INSTILLER;
	}
	
	@Override
    public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return DatabankUtils.hasAdvancement(client.player, SpiritInstillerRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
	}
	
}
