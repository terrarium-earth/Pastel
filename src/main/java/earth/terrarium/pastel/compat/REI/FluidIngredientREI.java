package earth.terrarium.pastel.compat.REI;

import dev.architectury.fluid.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.world.level.material.Fluids.EMPTY;

public class FluidIngredientREI {
    // ALWAYS pass FluidIngredient.EMPTY INSTEAD OF null
    // DO NOT pass(OR EVEN USE AT ALL) hacked-in weird Ingredients.
    // Only use ones provided by FluidIngredient.of() or FluidIngredient.EMPTY.
    public static EntryIngredient into(@NotNull FluidIngredient ingredient) {
        Objects.requireNonNull(ingredient);
        // Return empty stack if ingredient is empty.
        // Semi-redundant: the sole caller of this *checks if input is empty*.
        if (ingredient.isEmpty())
            return EntryIngredients.of(EMPTY);

        var stacks = ingredient.getStacks();
        EntryIngredient.Builder builder = EntryIngredient.builder(stacks.length);

        for (var stack : stacks) {
            builder.add(EntryStacks.of(FluidStack.create(stack.getFluid(), stack.getAmount())));
        }

        return builder.build();
    }
}
