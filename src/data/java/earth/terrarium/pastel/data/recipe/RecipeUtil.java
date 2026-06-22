package earth.terrarium.pastel.data.recipe;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

// God is dead
public abstract class RecipeUtil extends RecipeProvider {
    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item) {
        return RecipeProvider.has(item);
    }
    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return RecipeProvider.has(tag);
    }

    public RecipeUtil(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }
}
