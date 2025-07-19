package earth.terrarium.pastel.compat.emi.recipes;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.compat.emi.PastelEmiRecipe;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.Function;

public class PastelWorldInteractionRecipe extends EmiWorldInteractionRecipe {
    private final List<ResourceLocation> requiredAdvancementIdentifier = Lists.newArrayList();

    protected PastelWorldInteractionRecipe(PastelWorldInteractionRecipe.Builder builder) {
        super(builder.superbuilder);
        requiredAdvancementIdentifier.addAll(builder.requiredAdvancementIdentifier);
    }

    public static PastelWorldInteractionRecipe.Builder customBuilder() {
        return new PastelWorldInteractionRecipe.Builder();
    }

    public boolean hasAdvancement(ResourceLocation advancement) {
        Minecraft client = Minecraft.getInstance();
        return DatabankUtils.hasAdvancement(client.player, advancement);
    }

    public boolean isUnlocked() {
        if (requiredAdvancementIdentifier.isEmpty()) return true;
        for (ResourceLocation id : requiredAdvancementIdentifier) {
            if (!hasAdvancement(id)) return false;
        }
        return true;
    }

    @Override
    public int getDisplayWidth() {
        if (isUnlocked()) {
            return super.getDisplayWidth();
        } else {
            return 125;
        }
    }

    @Override
    public int getDisplayHeight() {
        if (isUnlocked()) {
            return super.getDisplayHeight();
        } else {
            return 18;
        }
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        if (!isUnlocked()) {
            widgets.addText(
                       PastelEmiRecipe.HIDDEN_LINE_1, getDisplayWidth() / 2, getDisplayHeight() / 2 - 9, 0x3f3f3f,
                       false)
                   .horizontalAlign(TextWidget.Alignment.CENTER);
            widgets.addText(
                       PastelEmiRecipe.HIDDEN_LINE_2, getDisplayWidth() / 2, getDisplayHeight() / 2 + 1, 0x3f3f3f,
                       false)
                   .horizontalAlign(TextWidget.Alignment.CENTER);
        } else {
            super.addWidgets(widgets);
        }
    }

    public static class Builder {
        //Use a combinatorial relationship to build the parent class builder because its constructor is private.
        private final EmiWorldInteractionRecipe.Builder superbuilder;
        private final List<ResourceLocation> requiredAdvancementIdentifier = Lists.newArrayList();

        private Builder() {
            this.superbuilder = EmiWorldInteractionRecipe.builder();
        }

        public PastelWorldInteractionRecipe build() {
            return new PastelWorldInteractionRecipe(this);
        }

        public PastelWorldInteractionRecipe.Builder id(ResourceLocation id) {
            superbuilder.id(id);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder leftInput(EmiIngredient stack) {
            superbuilder.leftInput(stack);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder leftInput(
            EmiIngredient stack, Function<SlotWidget, SlotWidget> mutator) {
            superbuilder.leftInput(stack, mutator);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder rightInput(EmiIngredient stack, boolean catalyst) {
            superbuilder.rightInput(stack, catalyst);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder rightInput(
            EmiIngredient stack, boolean catalyst, Function<SlotWidget, SlotWidget> mutator) {
            superbuilder.rightInput(stack, catalyst, mutator);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder output(EmiStack stack) {
            superbuilder.output(stack);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder output(EmiStack stack, Function<SlotWidget, SlotWidget> mutator) {
            superbuilder.output(stack, mutator);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder supportsRecipeTree(boolean supportsRecipeTree) {
            superbuilder.supportsRecipeTree(supportsRecipeTree);
            return this;
        }

        public PastelWorldInteractionRecipe.Builder requiredAdvancement(ResourceLocation advId) {
            requiredAdvancementIdentifier.add(advId);
            return this;
        }
    }
}
