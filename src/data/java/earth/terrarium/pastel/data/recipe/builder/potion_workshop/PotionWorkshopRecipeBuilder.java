package earth.terrarium.pastel.data.recipe.builder.potion_workshop;

import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.data.recipe.builder.SimpleRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
public abstract class PotionWorkshopRecipeBuilder<C extends PotionWorkshopRecipeBuilder<C>> extends GatedRecipeBuilder<C> {
    protected int color = 0;
    protected int craftingTime = 200;

    @Nullable
    protected SizedIngredient ingredient1 = null;
    @Nullable
    protected SizedIngredient ingredient2 = null;
    @Nullable
    protected SizedIngredient ingredient3 = null;

    public PotionWorkshopRecipeBuilder(
            ItemStack result
    ) {
        super(result);
    }

    public C ingredient1(SizedIngredient ingredient1) {
        this.ingredient1 = ingredient1;
        return (C)this;
    }

    public C ingredient1(ItemLike item) {
        return ingredient1(item, 1);
    }
    public C ingredient1(ItemLike item, int count) {
        return ingredient1(SizedIngredient.of(item, count));
    }

    public C ingredient1(TagKey<Item> tag) {
        return ingredient1(tag, 1);
    }

    public C ingredient1(TagKey<Item> tag, int count) {
        return ingredient1(SizedIngredient.of(tag, count));
    }

    public C ingredient2(SizedIngredient ingredient2) {
        this.ingredient2 = ingredient2;
        return (C)this;
    }

    public C ingredient2(ItemLike item) {
        return ingredient2(item, 1);
    }
    public C ingredient2(ItemLike item, int count) {
        return ingredient2(SizedIngredient.of(item, count));
    }

    public C ingredient2(TagKey<Item> tag) {
        return ingredient2(tag, 1);
    }

    public C ingredient2(TagKey<Item> tag, int count) {
        return ingredient2(SizedIngredient.of(tag, count));
    }

    public C ingredient3(SizedIngredient ingredient3) {
        this.ingredient3 = ingredient3;
        return (C)this;
    }

    public C ingredient3(ItemLike item) {
        return ingredient3(item, 1);
    }
    public C ingredient3(ItemLike item, int count) {
        return ingredient3(SizedIngredient.of(item, count));
    }

    public C ingredient3(TagKey<Item> tag) {
        return ingredient3(tag, 1);
    }

    public C ingredient3(TagKey<Item> tag, int count) {
        return ingredient3(SizedIngredient.of(tag, count));
    }

    public C craftingTime(int craftingTime) {
        this.craftingTime = craftingTime;
        return (C)this;
    }

    public C color(int color) {
        this.color = color;
        return (C)this;
    }
}
