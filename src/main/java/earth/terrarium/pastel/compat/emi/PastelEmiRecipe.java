package earth.terrarium.pastel.compat.emi;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PastelEmiRecipe implements EmiRecipe {
	public static final Component HIDDEN_LINE_1 = Component.translatable("container.pastel.rei.pedestal_crafting.recipe_not_unlocked_line_1");
	public static final Component HIDDEN_LINE_2 = Component.translatable("container.pastel.rei.pedestal_crafting.recipe_not_unlocked_line_2");
	public final EmiRecipeCategory category;
	public final ResourceLocation recipeTypeUnlockIdentifier, recipeIdentifier;
	public final int width, height;
	public List<EmiIngredient> inputs = List.of();
	public List<EmiStack> outputs = List.of();

	public PastelEmiRecipe(EmiRecipeCategory category, ResourceLocation recipeTypeUnlockIdentifier, ResourceLocation recipeIdentifier, int width, int height) {
		this.category = category;
		this.recipeTypeUnlockIdentifier = recipeTypeUnlockIdentifier;
		this.recipeIdentifier = recipeIdentifier;
		this.width = width;
		this.height = height;
	}

	public RegistryAccess getRegistryManager() {
		return Minecraft.getInstance().level.registryAccess();
	}

	public boolean isUnlocked() {
		return recipeTypeUnlockIdentifier == null || hasAdvancement(recipeTypeUnlockIdentifier);
	}

	public boolean hasAdvancement(ResourceLocation advancement) {
		Minecraft client = Minecraft.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, advancement);
	}

	protected static Component getCraftingTimeText(int time) {
		if (time == 20) {
			return Component.translatable("container.pastel.rei.crafting_time_one_second", 1);
		} else {
			return Component.translatable("container.pastel.rei.crafting_time", (time / 20));
		}
	}

	protected static Component getCraftingTimeText(int time, float experience) {
		// special handling for "1 second". Looks nicer
		if (time == 20) {
			return Component.translatable("container.pastel.rei.crafting_time_one_second_and_xp", 1, experience);
		} else {
			return Component.translatable("container.pastel.rei.crafting_time_and_xp", (time / 20), experience);
		}
	}

	public abstract void addUnlockedWidgets(WidgetHolder widgets);

	@Override
	public EmiRecipeCategory getCategory() {
		return category;
	}

	@Override
	public @Nullable ResourceLocation getId() {
		return recipeIdentifier;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return inputs;
	}

	@Override
	public List<EmiStack> getOutputs() {
		return outputs;
	}

	@Override
	public int getDisplayWidth() {
		if (isUnlocked()) {
			return width;
		} else {
			Minecraft client = Minecraft.getInstance();
			return Math.max(client.font.width(HIDDEN_LINE_1), client.font.width(HIDDEN_LINE_2)) + 8;
		}
	}

	@Override
	public int getDisplayHeight() {
		if (isUnlocked()) {
			return height;
		} else {
			return 32;
		}
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		if (!isUnlocked()) {
			widgets.addText(HIDDEN_LINE_1, getDisplayWidth() / 2, getDisplayHeight() / 2 - 8, 0x3f3f3f, false).horizontalAlign(Alignment.CENTER);
			widgets.addText(HIDDEN_LINE_2, getDisplayWidth() / 2, getDisplayHeight() / 2 + 2, 0x3f3f3f, false).horizontalAlign(Alignment.CENTER);
		} else {
			addUnlockedWidgets(widgets);
		}
	}

	@Override
	public boolean supportsRecipeTree() {
		return EmiRecipe.super.supportsRecipeTree() && isUnlocked();
	}

}
