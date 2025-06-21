package earth.terrarium.pastel.api.block;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.GatedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLLoader;

import java.util.Map;
import java.util.Optional;

public interface GatedGuidebookPage {
	
	// unlocks that would be reported in the sanity check,
	// but are manually proven to be ok (since the entry advancement + page advancement match all required criteria already)
	// format: <page identifier> : <page unlock>
	Map<ResourceLocation, ResourceLocation> SANITY_WHITELIST = Map.of(
			PastelCommon.locate("resources/jade_vines"), PastelCommon.locate("midgame/build_spirit_instiller_structure"), // covered by entry adv + page adv
			PastelCommon.locate("cuisine/tarts"), PastelCommon.locate("craft_salted_jaramel_trifle_or_tart"), // secret recipe showing up once crafted
			PastelCommon.locate("cuisine/trifles"), PastelCommon.locate("craft_salted_jaramel_trifle_or_tart"), // secret recipe showing up once crafted
			PastelCommon.locate("resources/bloodstone"), PastelCommon.locate("unlocks/blocks/crystallarieum"), // covered by entry adv + page adv
			PastelCommon.locate("resources/malachite"), PastelCommon.locate("unlocks/blocks/crystallarieum"), // covered by entry adv + page adv
			PastelCommon.locate("creating_life/egg_laying_wooly_pig"), PastelCommon.locate("midgame/remember_egg_laying_wooly_pig") // recipe should only be revealed after remembering it
	);
	
	static void runSanityCheck(ResourceLocation entryId, int pageNr, String pageAdvancement, GatedRecipe<?>... recipes) {
		if (!FMLLoader.isProduction()) {
			if (pageAdvancement != null && !pageAdvancement.isEmpty()) {
				for (GatedRecipe<?> recipe : recipes) {
					if (recipe == null) {
						PastelCommon.logWarning("Guidebook page " + entryId + " page " + pageNr + " is missing its recipe");
						continue;
					}
					Optional<ResourceLocation> recipeAdvId = recipe.getRequiredAdvancementIdentifier();
					ResourceLocation combinedAdvId = recipeAdvId.orElse(recipe.getRecipeTypeUnlockIdentifier());
					if (combinedAdvId == null) {
						PastelCommon.logWarning("Guidebook page " + entryId + "[" + pageNr + "] references advancement " + pageAdvancement + " for a recipe that does not have an unlock: " + recipeAdvId);
						continue;
					}
					if (SANITY_WHITELIST.containsKey(entryId) && SANITY_WHITELIST.get(entryId).equals(ResourceLocation.parse(pageAdvancement))) {
						continue;
					}
					if (!combinedAdvId.toString().equals(pageAdvancement)) {
						PastelCommon.logWarning("Guidebook page " + entryId + "[" + pageNr + "] references advancement " + pageAdvancement + " that differs from the one set in the recipe: " + recipeAdvId);
					}
				}
			}
		}
	}
	
}
