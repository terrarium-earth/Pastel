package earth.terrarium.pastel.compat.REI;

import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class REIHelper {

    public static List<EntryIngredient> toEntryIngredientsSized(List<SizedIngredient> sizedIngredients) {
        return sizedIngredients
            .stream()
            .map(REIHelper::ofSizedIngredient)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public static EntryIngredient ofSizedIngredient(@Nullable SizedIngredient sizedIngredient) {
        if (sizedIngredient == null) {
            return EntryIngredients.of(ItemStack.EMPTY);
        } else {
            return EntryIngredients
                .ofItemStacks(
                    Arrays
                        .stream(sizedIngredient.getItems())
                        .toList()
                );
        }

    }

    public static EntryIngredient ofFluidIngredient(FluidIngredient fluidIngredient) {
        var fluids = Arrays
            .stream(fluidIngredient.getStacks())
            .map(stack -> EntryStacks.of(stack.getFluid(), stack.getAmount()));
        return EntryIngredient.of(fluids.toList());
    }

}
