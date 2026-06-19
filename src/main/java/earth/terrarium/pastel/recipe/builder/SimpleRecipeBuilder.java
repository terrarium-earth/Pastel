package earth.terrarium.pastel.recipe.builder;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Adapted from the neoforge docs
// <https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/#data-generation>
public abstract class SimpleRecipeBuilder<C extends SimpleRecipeBuilder<C>> implements RecipeBuilder {
    protected final ItemStack result;
    protected final List<ICondition> conditions = new ArrayList<>();
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    public SimpleRecipeBuilder(ItemStack result) {
        this.result = result;
    }

    // ??? Im a lobotimite who uses scala
    // If i was doing scala I'd just do `this.type`
    protected abstract C self();

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public C unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return self();
    }

    @Override
    public C group(@Nullable String group) {
        this.group = group;
        return self();
    }

    public C neoCondition(ICondition condition) {
        this.conditions.add(condition);
        return self();
    }
}
