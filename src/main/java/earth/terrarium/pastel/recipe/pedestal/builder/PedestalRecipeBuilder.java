package earth.terrarium.pastel.recipe.pedestal.builder;

import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class PedestalRecipeBuilder<C extends PedestalRecipeBuilder<C>> extends GatedRecipeBuilder<C> {
    @Nullable
    protected PedestalTier tier;
    protected int craftingTime = 200;
    protected boolean ignoreYieldUpgrades = false;
    protected boolean skipRemainders = false;
    protected final Map<GemstoneColor, Integer> powderInputs = new HashMap<>();
    protected float experience = 0.0f;

    public PedestalRecipeBuilder(ItemStack result) {
        super(result);
    }

    @Nullable
    public PedestalTier getTier() {
        return this.tier;
    }

    // SERIOUSLY! Don't pass null!
    public C tier(PedestalTier tier) {
        Objects.requireNonNull(tier, "tier may not be null");
        this.tier = tier;
        return self();
    }

    public C craftingTime(int craftingTime) {
        this.craftingTime = craftingTime;
        return self();
    }

    public C ignoreYieldUpgrades(boolean ignoreYieldUpgrades) {
        this.ignoreYieldUpgrades = ignoreYieldUpgrades;
        return self();
    }

    public C skipRemainders(boolean skipRemainders) {
        this.skipRemainders = skipRemainders;
        return self();
    }

    public C powderInput(GemstoneColor color, int amount) {
        this.powderInputs.put(color, amount);
        return self();
    }

    public C experience(float experience) {
        this.experience = experience;
        return self();
    }
}
