package earth.terrarium.pastel.data.recipe.builder;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

public abstract class GatedRecipeBuilder<C extends GatedRecipeBuilder<C>> extends SimpleRecipeBuilder<C> {
    protected boolean secret = false;
    @Nullable
    private ResourceLocation requiredAdvancementIdentifier;

    public GatedRecipeBuilder(ItemStack result) {
        super(result);
    }

    public C secret(boolean secret) {
        this.secret = secret;
        return self();
    }

    public C requiredAdvancement(@Nullable ResourceLocation requiredAdvancement) {
        this.requiredAdvancementIdentifier = requiredAdvancement;
        return self();
    }

    private Optional<ResourceLocation> getRequiredAdvancement() {
        return Optional.ofNullable(this.requiredAdvancementIdentifier);
    }

    protected void saveHelperGated(RecipeOutput ctx, ResourceLocation id, Function<Optional<ResourceLocation>, Recipe<?>> recipeMaker) {
        if (!this.criteria.isEmpty() && this.requiredAdvancementIdentifier != null) {
            throw new IllegalStateException("Required Advancement Identifier and Criteria are mutually exclusive!");
        }

        Recipe<?> result;
        AdvancementHolder holder = null;

        if (!this.criteria.isEmpty()) {
            var builder = ctx.advancement();
            this.criteria.forEach(builder::addCriterion);
            var daId = id.withPrefix("pastel/recipes");
            holder = builder.build(daId);
            result = recipeMaker.apply(Optional.of(daId));
        } else {
            result = recipeMaker.apply(this.getRequiredAdvancement());
        }

        ctx.accept(id, result, holder, this.conditions());

    }
}
