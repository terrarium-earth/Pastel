package earth.terrarium.pastel.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.compat.REI.GatedSpectrumDisplay;
import earth.terrarium.pastel.recipe.fluid_converting.FluidConvertingRecipe;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public abstract class FluidConvertingDisplay extends GatedSpectrumDisplay {
	
	public <T extends FluidConvertingRecipe> FluidConvertingDisplay(RecipeHolder<T> recipe) {
		super(recipe, recipe.value().getIngredients().getFirst(), recipe.value().getResultItem(BasicDisplay.registryAccess()));
	}
	
	public final EntryIngredient getIn() {
		return getInputEntries().getFirst();
	}
	
	public final EntryIngredient getOut() {
		return getOutputEntries().getFirst();
	}
	
	@Override
    public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, getUnlockIdentifier()) && super.isUnlocked();
	}
	
	public abstract ResourceLocation getUnlockIdentifier();
	
}
