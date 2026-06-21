package earth.terrarium.pastel.data.recipe.builder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public abstract class GatedRecipeBuilder<C extends GatedRecipeBuilder<C>> extends SimpleRecipeBuilder<C> {
    protected boolean secret = false;
    @Nullable
    protected ResourceLocation requiredAdvancementIdentifier;

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
}
