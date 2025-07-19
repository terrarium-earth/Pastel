package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.InkConvertingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InkConvertingDisplay extends PastelDisplay {
	
	protected final InkColor color;
	protected final long amount;
	
	public InkConvertingDisplay(@NotNull RecipeHolder<InkConvertingRecipe> recipe) {
		super(recipe, EntryIngredients.ofIngredients(recipe.value().getIngredients()), List.of());
		this.color = recipe.value().getInkColor();
		this.amount = recipe.value().getInkAmount();
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return PastelPlugins.INK_CONVERTING;
	}
	
	@Override
    public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return DatabankUtils.hasAdvancement(client.player, InkConvertingRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
	}
	
}
