package earth.terrarium.pastel.data.recipe;

import com.cmdpro.databank.advancement.criteria.HasAdvancementCriteria;
import com.cmdpro.databank.registry.CriteriaTriggerRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

// God is dead
public abstract class RecipeUtil extends RecipeProvider {
    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item) {
        return RecipeProvider.has(item);
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return RecipeProvider.has(tag);
    }

    public static Criterion<?> hasAdvancement(ResourceLocation id) {
        return CriteriaTriggerRegistry.HAS_ADVANCEMENT
            .get()
            .createCriterion(new HasAdvancementCriteria.HasAdvancementCriteriaInstance(Optional.empty(), id));
    }

    public RecipeUtil(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static String nameFromInAndOut(ItemLike input, ItemLike output) {
        return nameInOutHelper(input, output, RecipeUtil::fromName);
    }

    public static String nameToInAndOut(ItemLike input, ItemLike output) {
        return nameInOutHelper(input, output, RecipeUtil::toName);
    }

    private static String fromName(String inName, String outName) {
        return outName + "_from_" + inName;
    }

    private static String toName(String inName, String outName) {
        return inName + "_to_" + outName;
    }

    private static String nameInOutHelper(ItemLike input, ItemLike output, BiFunction<String, String, String> func) {
        var inName = BuiltInRegistries.ITEM.getKey(input.asItem()).getPath();
        var outName = BuiltInRegistries.ITEM.getKey(output.asItem()).getPath();
        return func.apply(inName, outName);
    }
}
