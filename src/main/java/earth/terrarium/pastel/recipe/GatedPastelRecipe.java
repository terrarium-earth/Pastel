package earth.terrarium.pastel.recipe;

import earth.terrarium.pastel.api.recipe.GatedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.Optional;

public abstract class GatedPastelRecipe<C extends RecipeInput> implements GatedRecipe<C> {

    public final String group;

    public final boolean secret;

    public final Optional<ResourceLocation> requiredAdvancementIdentifier;

    protected GatedPastelRecipe(
        String group,
        boolean secret,
        Optional<ResourceLocation> requiredAdvancementIdentifier
    ) {
        this.group = group;
        this.secret = secret;
        this.requiredAdvancementIdentifier = requiredAdvancementIdentifier;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public boolean isSecret() {
        return this.secret;
    }

    /**
     * The advancement the player has to have for the recipe be craftable
     *
     * @return The advancement identifier. A null value means the player is always able to craft this recipe
     */
    @Override
    public Optional<ResourceLocation> advancementID() {
        return this.requiredAdvancementIdentifier;
    }

    @Override
    public ResourceLocation typeAdvancementID() {
        return null;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    protected static ItemStack getDefaultStackWithCount(Item item, int count) {
        ItemStack stack = item.getDefaultInstance();
        stack.setCount(count);
        return stack;
    }

}
