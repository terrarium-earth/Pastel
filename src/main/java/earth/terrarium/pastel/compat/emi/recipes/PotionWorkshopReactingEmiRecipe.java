package earth.terrarium.pastel.compat.emi.recipes;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.api.recipe.DescriptiveGatedRecipe;
import earth.terrarium.pastel.compat.emi.SpectrumEmiRecipe;
import earth.terrarium.pastel.compat.emi.SpectrumEmiRecipeCategories;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopRecipe;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;

import java.util.List;

public class PotionWorkshopReactingEmiRecipe extends EmiInfoRecipe {
	private final DescriptiveGatedRecipe<?> recipe;
	
	public PotionWorkshopReactingEmiRecipe(DescriptiveGatedRecipe<?> recipe) {
		super(List.of(EmiStack.of(recipe.getItem())), List.of(recipe.getDescription()), EmiPort.getId(recipe));
		this.recipe = recipe;
	}
	
	public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, PotionWorkshopRecipe.UNLOCK_IDENTIFIER) && AdvancementHelper.hasAdvancement(client.player, recipe.getRequiredAdvancementIdentifier().orElse(null));
	}
	
	@Override
	public EmiRecipeCategory getCategory() {
		return SpectrumEmiRecipeCategories.POTION_WORKSHOP_REACTING;
	}
	
	@Override
	public int getDisplayHeight() {
		if (isUnlocked()) {
			return super.getDisplayHeight();
		} else {
			return 32;
		}
	}
	
	@Override
	public void addWidgets(WidgetHolder widgets) {
		if (!isUnlocked()) {
			widgets.addText(SpectrumEmiRecipe.HIDDEN_LINE_1, getDisplayWidth() / 2, getDisplayHeight() / 2 - 8, 0x3f3f3f, false).horizontalAlign(Alignment.CENTER);
			widgets.addText(SpectrumEmiRecipe.HIDDEN_LINE_2, getDisplayWidth() / 2, getDisplayHeight() / 2 + 2, 0x3f3f3f, false).horizontalAlign(Alignment.CENTER);
		} else {
			super.addWidgets(widgets);
		}
	}
}
