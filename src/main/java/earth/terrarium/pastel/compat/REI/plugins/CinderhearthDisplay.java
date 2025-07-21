package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.compat.REI.REIHelper;
import earth.terrarium.pastel.recipe.cinderhearth.CinderhearthRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CinderhearthDisplay extends PastelDisplay {
	
	protected final float experience;
	protected final int craftingTime;
	protected final List<Tuple<ItemStack, Float>> outputsWithChance;
	
	public CinderhearthDisplay(@NotNull RecipeHolder<CinderhearthRecipe> recipe) {
		super(recipe, REIHelper.toEntryIngredients(recipe.value().getIngredientStacks()), List.of(EntryIngredients.ofItemStacks(recipe.value().getPossibleOutputs())));
		this.outputsWithChance = recipe.value().getResultsWithChance();
		this.experience = recipe.value().getExperience();
		this.craftingTime = recipe.value().getCraftingTime();
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return PastelPlugins.CINDERHEARTH;
	}
	
	@Override
	public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return DatabankUtils.hasAdvancement(client.player, CinderhearthRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
	}
	
}
