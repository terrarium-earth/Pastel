package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.builder.PedestalRecipeBuilder;
import earth.terrarium.pastel.registries.PastelResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;

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

    public void generateDynamicRecipe(String subId, Recipe<?> recipe, ICondition... conditions) {
        ctx.accept(PastelCommon.locate(prefix + subId), recipe, null, conditions);
    }

    public void generateAutoNamedDynamicRecipe(HolderLookup.Provider lookup, Recipe<?> recipe, ICondition... conditions) {
        var result = recipe.getResultItem(lookup);
        var id = BuiltInRegistries.ITEM.getKey(result.getItem()).getPath();
        generateDynamicRecipe(id, recipe, conditions);
    }

    public PrefixHelper subPrefix(String name) {
        if (this.prefix.isEmpty()) {
            return new PrefixHelper(this.ctx, name);
        } else {
            return new PrefixHelper(this.ctx, this.prefix + "/" + name);
        }
    }

    public PrefixHelper modIntegration(String modId) {
        var condition = new PastelResourceConditions.IntegrationPackActiveResourceCondition(modId);
        if (this.prefix.isEmpty()) {
            return new PrefixHelper(this.ctx.withConditions(condition), "mod_integration/" + modId);
        } else {
            return new PrefixHelper(this.ctx.withConditions(condition), "mod_integration/" + modId + "/" + this.prefix);
        }
    }

}