package earth.terrarium.pastel.data.recipe.builder.pedestal;

import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class PedestalRecipeBuilder<C extends PedestalRecipeBuilder<C>> extends GatedRecipeBuilder<C> {
    protected PedestalTier tier = PedestalTier.BASIC;
    protected int craftingTime = 200;
    protected boolean disableYieldUpgrades = false;
    protected boolean skipRemainders = false;
    protected final Map<GemstoneColor, Integer> powderInputs = new HashMap<>();
    protected float experience = 0.0f;

    public PedestalRecipeBuilder(ItemStack result) {
        super(result);
    }

    public PedestalTier getTier() {
        return this.tier;
    }

    // SERIOUSLY! Don't pass null!
    public C tier(PedestalTier tier) {
        Objects.requireNonNull(tier, "tier may not be null");
        this.tier = tier;
        return self();
    }

    public C basic() {
        return tier(PedestalTier.BASIC);
    }

    public C simple() {
        return tier(PedestalTier.SIMPLE);
    }

    public C advanced() {
        return tier(PedestalTier.ADVANCED);
    }

    public C complex() {
        return tier(PedestalTier.COMPLEX);
    }

    public C craftingTime(int craftingTime) {
        this.craftingTime = craftingTime;
        return self();
    }

    public C disableYieldUpgrades(boolean disableYieldUpgrades) {
        this.disableYieldUpgrades = disableYieldUpgrades;
        return self();
    }

    public C skipRemainders(boolean skipRemainders) {
        this.skipRemainders = skipRemainders;
        return self();
    }

    public C color(GemstoneColor color, int amount) {
        this.powderInputs.put(color, amount);
        return self();
    }

    public C cyan(int cyan) {
        return color(PastelGemstoneColor.CYAN, cyan);
    }

    public C magenta(int magenta) {
        return color(PastelGemstoneColor.MAGENTA, magenta);
    }

    public C yellow(int yellow) {
        return color(PastelGemstoneColor.YELLOW, yellow);
    }

    public C black(int black) {
        return color(PastelGemstoneColor.BLACK, black);
    }

    public C white(int white) {
        return color(PastelGemstoneColor.WHITE, white);
    }

    public C replaceColorsWith(Map<GemstoneColor, Integer> colors) {
        this.powderInputs.clear();
        this.powderInputs.putAll(colors);
        return self();
    }

    public C experience(float experience) {
        this.experience = experience;
        return self();
    }
}
