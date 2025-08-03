package earth.terrarium.pastel.api.recipe;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.progression.UnlockToastManager;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface GatedRecipe<C extends RecipeInput> extends Recipe<C> {
	
	boolean isSecret();

    /**
     * The advancement this recipe is gated behind, or empty if it is unlocked by default
     */
	Optional<ResourceLocation> advancementID();

    /**
     * The advancement this recipe type is gated behind
     */
	ResourceLocation typeAdvancementID();
	
	String getRecipeTypeShortID();

	default boolean canPlayerCraft(Player playerEntity) {
        if (!DatabankUtils.hasAdvancement(playerEntity, typeAdvancementID()))
            return false;

        var advancement = advancementID();

        return advancement.map(adv -> DatabankUtils.hasAdvancement(playerEntity, adv))
                          .orElse(true);

    }
	
	default Component getSingleUnlockToastString() {
		return Component.translatable("pastel.toast." + getRecipeTypeShortID() + "_recipe_unlocked.title");
	}
	
	default Component getMultipleUnlockToastString() {
		return Component.translatable("pastel.toast." + getRecipeTypeShortID() + "_recipes_unlocked.title");
	}
	
	default void registerInToastManager(RecipeType<?> recipeType, GatedRecipe<C> gatedRecipe) {
		if (FMLLoader.getDist().isClient()) {
			registerInToastManagerClient(recipeType, gatedRecipe);
		}
	}
	
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
