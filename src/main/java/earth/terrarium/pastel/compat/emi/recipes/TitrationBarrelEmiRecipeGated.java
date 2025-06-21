package earth.terrarium.pastel.compat.emi.recipes;

import earth.terrarium.pastel.compat.emi.FluidIngredientEmi;
import earth.terrarium.pastel.compat.emi.GatedSpectrumEmiRecipe;
import earth.terrarium.pastel.compat.emi.PastelEmiRecipeCategories;
import earth.terrarium.pastel.recipe.titration_barrel.ITitrationBarrelRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import dev.emi.emi.api.widget.WidgetHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TitrationBarrelEmiRecipeGated extends GatedSpectrumEmiRecipe<ITitrationBarrelRecipe> {
	
	protected final @Nullable List<EmiStack> displayedStacks;
	
	public TitrationBarrelEmiRecipeGated(ITitrationBarrelRecipe recipe) {
		super(PastelEmiRecipeCategories.TITRATION_BARREL, recipe, 136, 50);
		inputs = new ArrayList<>();
		if (!recipe.getFluidInput().isEmpty()) {
			inputs.add(FluidIngredientEmi.into(recipe.getFluidInput()));
		}
		inputs.addAll(recipe.getIngredientStacks().stream().map(s -> EmiIngredient.of(s.getItems().map(EmiStack::of).toList())).toList());
		
		displayedStacks = buildFermentationOutputVariations(recipe);
	}
	
	private static List<EmiStack> buildFermentationOutputVariations(ITitrationBarrelRecipe recipe) {
		if (recipe instanceof TitrationBarrelRecipe titrationBarrelRecipe && titrationBarrelRecipe.getFermentationData() != null) {
			return titrationBarrelRecipe.getOutputVariations(TitrationBarrelRecipe.FERMENTATION_DURATION_DISPLAY_TIME_MULTIPLIERS).stream().map(EmiStack::of).toList();
		}
		return null;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addUnlockedWidgets(WidgetHolder widgets) {
		// input slots
		int startX = Math.max(10, 40 - inputs.size() * 10);
		int startY = (inputs.size() > 3 ? 0 : 10);
		for (int i = 0; i < inputs.size(); i++) {
			int x = startX + (i % 3) * 20;
			int y = startY + (i / 3) * 20;
			widgets.addSlot(inputs.get(i), x, y);
		}
		
		EmiIngredient tapping = EmiStack.of(recipe.getTappingItem());
		if (tapping.isEmpty()) {
			widgets.addFillingArrow(70, 10, recipe.getMinFermentationTimeHours() * 20 * 50);
		} else {
			widgets.addFillingArrow(70, 2, recipe.getMinFermentationTimeHours() * 20 * 50);
			widgets.addSlot(tapping, 74, 20);
		}
		
		if (displayedStacks == null) {
			widgets.addSlot(outputs.getFirst(), 100, 5).large(true).recipeContext(this);
		} else if (Minecraft.getInstance().level != null) {
			widgets.addGeneratedSlot(random -> displayedStacks.get((int) (Minecraft.getInstance().level.getGameTime() % displayedStacks.size())), 1, 100, 5).large(true).recipeContext(this);
		}
		
		Component text = TitrationBarrelRecipe.getDurationText(recipe.getMinFermentationTimeHours(), recipe.getFermentationData());
		widgets.addText(text, width / 2, 40, 0x3f3f3f, false).horizontalAlign(Alignment.CENTER);
	}
}
