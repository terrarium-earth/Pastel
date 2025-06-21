package earth.terrarium.pastel.compat.emi;

import earth.terrarium.pastel.api.recipe.GatedRecipe;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class GatedSpectrumEmiRecipe<T extends GatedRecipe<?>> extends PastelEmiRecipe {
	
	public static final Component SECRET = Component.translatable("container.pastel.rei.pedestal_crafting.secret_recipe");
	public static final Component SECRET_HINT = Component.translatable("container.pastel.rei.pedestal_crafting.secret_recipe.hint");
	
	public final @Nullable Component secretHintText;
	
	public final T recipe;
	
	public GatedSpectrumEmiRecipe(EmiRecipeCategory category, T recipe, int width, int height) {
		super(category, recipe.getRecipeTypeUnlockIdentifier(), EmiPort.getId(recipe), width, height);
		this.recipe = recipe;
		this.outputs = List.of(EmiStack.of(recipe.getResultItem(getRegistryManager())));
		this.secretHintText = recipe.getSecretHintText(getId());
	}
	
	@Override
	public boolean isUnlocked() {
		return hasAdvancement(recipe.getRequiredAdvancementIdentifier().orElse(null)) && super.isUnlocked();
	}
	
	@Override
	public boolean hideCraftable() {
		return recipe.isSecret() || super.hideCraftable();
	}
	
	@Override
	public void addWidgets(WidgetHolder widgets) {
		if (recipe.isSecret() && isUnlocked()) {
			if (secretHintText == null) {
				widgets.addText(SECRET, getDisplayWidth() / 2, getDisplayHeight() / 2, 0x3f3f3f, false).horizontalAlign(TextWidget.Alignment.CENTER);
			} else {
				widgets.addText(SECRET_HINT, getDisplayWidth() / 2, getDisplayHeight() / 2 - 8, 0x3f3f3f, false).horizontalAlign(TextWidget.Alignment.CENTER);
				widgets.addText(secretHintText, getDisplayWidth() / 2, getDisplayHeight() / 2 + 2, 0x3f3f3f, false).horizontalAlign(TextWidget.Alignment.CENTER);
			}
		} else {
			super.addWidgets(widgets);
		}
	}
	
}
