package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.builder.PedestalRecipeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.Recipe;

public class PrefixHelper {
    private final String prefix;
    private final RecipeOutput ctx;

    PrefixHelper(RecipeOutput ctx, String prefix) {
        if (prefix.isEmpty()) {
            this.prefix = "";
        } else {
            this.prefix = prefix + "/";
        }
        this.ctx = ctx;
    }

    public void generateRecipe(String subId, RecipeBuilder builder) {
        builder.save(ctx, PastelCommon.locate(prefix + subId));
    }


    public void generateAutoNamedRecipe(PedestalRecipeBuilder<?> builder) {
        var id = BuiltInRegistries.ITEM.getKey(builder.getResult()).getPath();
        generateRecipe(id, builder);
    }

    // cant auto name this without providers (haha, no.)
    public void generateDynamicRecipe(String subId, Recipe<?> recipe) {
        ctx.accept(PastelCommon.locate(prefix + subId), recipe, null);
    }

    public PrefixHelper subPrefix(String name) {
        if (this.prefix.isEmpty()) {
            return new PrefixHelper(this.ctx, name);
        } else {
            return new PrefixHelper(this.ctx, this.prefix + "/" + name);
        }
    }

}