package earth.terrarium.pastel.compat.emi.recipes;

import earth.terrarium.pastel.compat.emi.GatedSpectrumEmiRecipe;
import earth.terrarium.pastel.compat.emi.PastelEmiRecipeCategories;
import earth.terrarium.pastel.helpers.LoreHelper;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.SpawnerChangeRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpiritInstillingEmiRecipeGated extends GatedSpectrumEmiRecipe<SpiritInstillerRecipe> {
	
	public SpiritInstillingEmiRecipeGated(SpiritInstillerRecipe recipe) {
		super(PastelEmiRecipeCategories.SPIRIT_INSTILLER, recipe, 116, 48);
		inputs = recipe.getIngredientStacks().stream().map(s -> EmiIngredient.of(s.getItems().map(EmiStack::of).toList())).toList();
		
		if (recipe instanceof SpawnerChangeRecipe spawnerChangeRecipe) {
			ItemStack outputStack = recipe.getResultItem(getRegistryManager());
			LoreHelper.setLore(outputStack, spawnerChangeRecipe.getOutputLoreText());
			outputs = List.of(EmiStack.of(outputStack));
		}
	}
	
	@Override
	public void addUnlockedWidgets(WidgetHolder widgets) {
		widgets.addSlot(inputs.get(SpiritInstillerRecipe.FIRST), 0, 0);
		widgets.addSlot(inputs.get(SpiritInstillerRecipe.CENTER), 20, 0);
		widgets.addSlot(inputs.get(SpiritInstillerRecipe.SECOND), 40, 0);
		
		widgets.addSlot(EmiStack.of(PastelBlocks.ITEM_BOWL_CALCITE.get()), 0, 17).drawBack(false);
		widgets.addSlot(EmiStack.of(PastelBlocks.SPIRIT_INSTILLER.get()), 20, 17).drawBack(false);
		widgets.addSlot(EmiStack.of(PastelBlocks.ITEM_BOWL_CALCITE.get()), 40, 17).drawBack(false);
		
		if (!outputs.isEmpty()) {
			widgets.addSlot(outputs.getFirst(), 90, 4).large(true).recipeContext(this);
		}
		
		widgets.addFillingArrow(60, 9, recipe.getCraftingTime() * 50);

		widgets.addText(getCraftingTimeText(recipe.getCraftingTime(), recipe.getExperience()), 0, 39, 0x3f3f3f, false);
	}
}
