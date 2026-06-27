package earth.terrarium.pastel.data.recipe.builder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.Nullable;
import java.util.*;

// Adapted from the neoforge docs
// <https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/#data-generation>
public abstract class SimpleRecipeBuilder<C extends SimpleRecipeBuilder<C>> implements RecipeBuilder {
    protected final ItemStack result;
    private final List<ICondition> conditions = new ArrayList<>();
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    protected String group = "";

    public SimpleRecipeBuilder(ItemStack result) {
        this.result = result;
    }

    // ??? Im a lobotimite who uses scala
    // If i was doing scala I'd just do `this.type`
    @SuppressWarnings("unchecked")
    protected final C self() {
        return (C)this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    public String getDefaultName() {
        var id = BuiltInRegistries.ITEM.getKey(this.getResult());
        return id.getPath();
    }

    public static String recipeName(RecipeBuilder recipe) {
        if (recipe instanceof SimpleRecipeBuilder<?> builder) {
            return builder.getDefaultName();
        }
        var id = BuiltInRegistries.ITEM.getKey(recipe.getResult());
        return id.getPath();
    }

    @Override
    public C unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return self();
    }

    @Override
    public C group(@Nullable String group) {
        this.group = Objects.requireNonNullElse(group, "");
        return self();
    }

    @CanIgnoreReturnValue
    public C neoCondition(ICondition condition) {
        this.conditions.add(condition);
        return self();
    }

    public C neoConditions(ICondition... conditions) {
        this.conditions.addAll(Arrays.stream(conditions).toList());
        return self();
    }

    protected ICondition[] conditions() {
        return this.conditions.toArray(ICondition[]::new);
    }


}
