package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.data.recipe.builder.pedestal.PedestalRecipeBuilder;
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
    private final HolderLookup.Provider lookup;

    PrefixHelper(RecipeOutput ctx, HolderLookup.Provider lookup, String prefix) {
        if (prefix.isEmpty()) {
            this.prefix = "";
        } else {
            this.prefix = prefix + "/";
        }
        this.ctx = ctx;
        this.lookup = lookup;
    }

    public HolderLookup.Provider getLookup() {
        return this.lookup;
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

    public void generateAutoNamedDynamicRecipe(Recipe<?> recipe, ICondition... conditions) {
        var result = recipe.getResultItem(lookup);
        var id = BuiltInRegistries.ITEM.getKey(result.getItem()).getPath();
        generateDynamicRecipe(id, recipe, conditions);
    }

    public PrefixHelper subPrefix(String name) {
        if (this.prefix.isEmpty()) {
            return new PrefixHelper(this.ctx, this.lookup, name);
        } else {
            return new PrefixHelper(this.ctx, this.lookup, this.prefix + "/" + name);
        }
    }

    public PrefixHelper modIntegration(String modId) {
        var condition = new PastelResourceConditions.IntegrationPackActiveResourceCondition(modId);
        var newCtx = this.ctx.withConditions(condition);
        var prefixPrefix = "mod_integration/" + modId;
        if (this.prefix.isEmpty()) {
            return new PrefixHelper(newCtx, this.lookup, prefixPrefix);
        } else {
            return new PrefixHelper(newCtx, this.lookup, prefixPrefix + "/" + this.prefix);
        }
    }

}