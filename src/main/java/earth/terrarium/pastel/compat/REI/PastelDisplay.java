package earth.terrarium.pastel.compat.REI;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.recipe.GatedRecipe;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public abstract class PastelDisplay extends BasicDisplay implements GatedRecipeDisplay {
	
	private final ResourceLocation requiredAdvancementIdentifier;
	private final boolean secret;
	private final @Nullable Component secretHintText;
	
	// 1 input => 1 output
	public PastelDisplay(RecipeHolder<? extends GatedRecipe<?>> recipe, Ingredient input, ItemStack output) {
		this(recipe, Collections.singletonList(EntryIngredients.ofIngredient(input)), Collections.singletonList(EntryIngredients.of(output)));
	}
	
	// n inputs => 1 output
	public PastelDisplay(RecipeHolder<? extends GatedRecipe<?>> recipe, List<EntryIngredient> inputs, ItemStack output) {
		this(recipe, inputs, Collections.singletonList(EntryIngredients.of(output)));
	}
	
	// n inputs => m outputs
	public PastelDisplay(RecipeHolder<? extends GatedRecipe<?>> recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
		super(inputs, outputs);
		this.secret = recipe.value().isSecret();
		this.requiredAdvancementIdentifier = recipe.value().advancementID().orElse(null);
		// FIXME
		//this.secretHintText = recipe.getSecretHintText(id);
		this.secretHintText = null;
	}
	
	@Override
	public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		if (client.player == null || requiredAdvancementIdentifier == null)
			return true;

		return DatabankUtils.hasAdvancement(client.player, this.requiredAdvancementIdentifier);
	}
	
	@Override
	public boolean isSecret() {
		return this.secret;
	}
	
	public @Nullable Component getSecretHintText() {
		return this.secretHintText;
	}
	
}
