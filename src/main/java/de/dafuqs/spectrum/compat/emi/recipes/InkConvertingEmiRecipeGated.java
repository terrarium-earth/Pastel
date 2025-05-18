package de.dafuqs.spectrum.compat.emi.recipes;

import de.dafuqs.spectrum.compat.emi.GatedSpectrumEmiRecipe;
import de.dafuqs.spectrum.compat.emi.SpectrumEmiRecipeCategories;
import de.dafuqs.spectrum.recipe.InkConvertingRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;

public class InkConvertingEmiRecipeGated extends GatedSpectrumEmiRecipe<InkConvertingRecipe> {
	
	public InkConvertingEmiRecipeGated(InkConvertingRecipe recipe) {
		super(SpectrumEmiRecipeCategories.INK_CONVERTING, recipe, 136, 20);
		this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
	}
	
	@Override
	public void addUnlockedWidgets(WidgetHolder widgets) {
		widgets.addSlot(inputs.getFirst(), 0, 1);
		
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 22, 1);

		// output amount & required time
		Component colorText = Component.translatable("container.spectrum.rei.ink_converting.color", recipe.getInkColor().getName());
		Component amountText = Component.translatable("container.spectrum.rei.ink_converting.amount", recipe.getInkAmount());
		widgets.addText(colorText, 50, 1, 0x3f3f3f, false);
		widgets.addText(amountText, 50, 11, 0x3f3f3f, false);
	}
}
