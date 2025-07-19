package earth.terrarium.pastel.recipe;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InstanceRecipeInput<T> extends SimpleRecipeInput {

    private final T instance;

    public InstanceRecipeInput(List<ItemStack> items, T instance) {
        super(items);
        this.instance = instance;
    }

    public T getInstance() {
        return this.instance;
    }

}
