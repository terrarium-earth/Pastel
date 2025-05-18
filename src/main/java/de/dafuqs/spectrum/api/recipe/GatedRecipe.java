package de.dafuqs.spectrum.api.recipe;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.progression.UnlockToastManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface GatedRecipe<C extends RecipeInput> extends Recipe<C> {
	
	boolean isSecret();
	
	Optional<ResourceLocation> getRequiredAdvancementIdentifier();
	
	ResourceLocation getRecipeTypeUnlockIdentifier();
	
	String getRecipeTypeShortID();
	
	default boolean canPlayerCraft(Player playerEntity) {
		return AdvancementHelper.hasAdvancement(playerEntity, getRecipeTypeUnlockIdentifier())
				&& AdvancementHelper.hasAdvancement(playerEntity, getRequiredAdvancementIdentifier().orElse(null));
	}
	
	default Component getSingleUnlockToastString() {
		return Component.translatable("spectrum.toast." + getRecipeTypeShortID() + "_recipe_unlocked.title");
	}
	
	default Component getMultipleUnlockToastString() {
		return Component.translatable("spectrum.toast." + getRecipeTypeShortID() + "_recipes_unlocked.title");
	}
	
	default void registerInToastManager(RecipeType<?> recipeType, GatedRecipe<C> gatedRecipe) {
		if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) {
			registerInToastManagerClient(recipeType, gatedRecipe);
		}
	}
	
	@Environment(EnvType.CLIENT)
	private void registerInToastManagerClient(RecipeType<?> recipeType, GatedRecipe<C> gatedRecipe) {
		UnlockToastManager.registerGatedRecipe(recipeType, gatedRecipe);
	}
	
	default @Nullable Component getSecretHintText(ResourceLocation id) {
		if (isSecret()) {
			String secretHintLangKey = id.toLanguageKey("recipe", "hint").replace("/", ".");
			return Language.getInstance().has(secretHintLangKey) ? Component.translatable(secretHintLangKey) : null;
		}
		return null;
	}
	
}
