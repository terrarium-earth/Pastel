package earth.terrarium.pastel.recipe.pedestal;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockItem;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.recipe.GatedStackPastelRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class PedestalRecipe extends GatedStackPastelRecipe<PedestalRecipeInput> {

    public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("place_pedestal");

    public static final int[] CRAFTING_GRID_SLOTS = new int[] {
        0, 1, 2, 3, 4, 5, 6, 7, 8
    };

    protected final PedestalTier tier;

    protected final List<IngredientStack> inputs;

    protected final Map<GemstoneColor, Integer> powderInputs;

    protected final ItemStack output;

    protected final float experience;

    protected final int craftingTime;

    protected final boolean skipRecipeRemainders;

    protected final boolean noBenefitsFromYieldUpgrades;

    public PedestalRecipe(
        String group,
        boolean secret,
        Optional<ResourceLocation> requiredAdvancementIdentifier,
        PedestalTier tier,
        List<IngredientStack> inputs,
        Map<GemstoneColor, Integer> powderInputs,
        ItemStack output,
        float experience,
        int craftingTime,
        boolean skipRecipeRemainders,
        boolean noBenefitsFromYieldUpgrades
    ) {
        super(group, secret, requiredAdvancementIdentifier);

        this.tier = tier;
        this.inputs = inputs;
        this.powderInputs = powderInputs;
        this.output = output;
        this.experience = experience;
        this.craftingTime = craftingTime;
        this.skipRecipeRemainders = skipRecipeRemainders;
        this.noBenefitsFromYieldUpgrades = noBenefitsFromYieldUpgrades;

        registerInToastManager(getType(), this);
    }

    /**
     * When a recipe is set to output a pedestal block item
     * it is treated as an upgrade recipe. Meaning the item does not
     * get crafted, but the current pedestal replaced with the new one.
     */
    public boolean isStructureUpgrade(RegistryAccess access) {
        return getResultItem(access).getItem() instanceof PedestalBlockItem;
    }

    @Override
    public boolean matches(PedestalRecipeInput inv, Level world) {
        int topazPowderAmount = this.powderInputs.getOrDefault(PastelGemstoneColor.CYAN, 0);
        int amethystPowderAmount = this.powderInputs.getOrDefault(PastelGemstoneColor.MAGENTA, 0);
        int citrinePowderAmount = this.powderInputs.getOrDefault(PastelGemstoneColor.YELLOW, 0);
        int onyxPowderAmount = this.powderInputs.getOrDefault(PastelGemstoneColor.BLACK, 0);
        int moonstonePowderAmount = this.powderInputs.getOrDefault(PastelGemstoneColor.WHITE, 0);

        return ((topazPowderAmount == 0 || isStackAtLeast(
            inv.getItem(9),
            PastelItems.TOPAZ_POWDER.get(),
            topazPowderAmount
        )) && (amethystPowderAmount == 0 || isStackAtLeast(
            inv.getItem(10),
            PastelItems.AMETHYST_POWDER.get(),
            amethystPowderAmount
        )) && (citrinePowderAmount == 0 || isStackAtLeast(
            inv.getItem(11),
            PastelItems.CITRINE_POWDER.get(),
            citrinePowderAmount
        )) && (onyxPowderAmount == 0 || isStackAtLeast(
            inv.getItem(12),
            PastelItems.ONYX_POWDER.get(),
            onyxPowderAmount
        )) && (moonstonePowderAmount == 0 || isStackAtLeast(
            inv.getItem(13),
            PastelItems.MOONSTONE_POWDER.get(),
            moonstonePowderAmount
        )));
    }

    private boolean isStackAtLeast(ItemStack sourceItemStack, Item item, int amount) {
        return sourceItemStack.is(item) && sourceItemStack.getCount() >= amount;
    }

    @Override
    public List<IngredientStack> getIngredientStacks() {
        return inputs;
    }

    @Override
    public ItemStack assemble(PedestalRecipeInput inventory, HolderLookup.Provider registryManager) {
        return this.output.copy();
    }

    public PedestalTier getTier() {
        return this.tier;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryManager) {
        return this.output;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(PastelBlocks.PEDESTAL_BASIC_AMETHYST.get());
    }

    @Override
    public RecipeType<?> getType() {
        return PastelRecipeTypes.PEDESTAL;
    }

    public int getPowder(GemstoneColor gemstoneColor) {
        return powderInputs.getOrDefault(gemstoneColor, 0);
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    public float getExperience() {
        return this.experience;
    }

    public Map<GemstoneColor, Integer> getPowderInputs() {
        return this.powderInputs;
    }

    /**
     * Returns a sound event matching for this recipe.
     * Dependent on the amount of gemstone dust used in it
     *
     * @return The sound effect to play when this recipe is finished
     */
    public SoundEvent getSoundEvent(RandomSource random) {
        List<SoundEvent> choices = new ArrayList<>();

        for (
            int i = 0;
            i < this.powderInputs.getOrDefault(PastelGemstoneColor.MAGENTA, 0);
            i++
        ) {
            choices.add(PastelSounds.PEDESTAL_CRAFTING_FINISHED_AMETHYST);
        }
        for (
            int i = 0;
            i < this.powderInputs.getOrDefault(PastelGemstoneColor.YELLOW, 0);
            i++
        ) {
            choices.add(PastelSounds.PEDESTAL_CRAFTING_FINISHED_CITRINE);
        }
        for (
            int i = 0;
            i < this.powderInputs.getOrDefault(PastelGemstoneColor.CYAN, 0);
            i++
        ) {
            choices.add(PastelSounds.PEDESTAL_CRAFTING_FINISHED_TOPAZ);
        }
        for (
            int i = 0;
            i < this.powderInputs.getOrDefault(PastelGemstoneColor.BLACK, 0);
            i++
        ) {
            choices.add(PastelSounds.PEDESTAL_CRAFTING_FINISHED_ONYX);
        }
        for (
            int i = 0;
            i < this.powderInputs.getOrDefault(PastelGemstoneColor.WHITE, 0);
            i++
        ) {
            choices.add(PastelSounds.PEDESTAL_CRAFTING_FINISHED_MOONSTONE);
        }

        if (choices.isEmpty()) {
            return PastelSounds.PEDESTAL_CRAFTING_FINISHED_GENERIC;
        } else {
            return choices.get(random.nextInt(choices.size()));
        }
    }

    public void consumeIngredients(PedestalBlockEntity pedestal, PedestalRecipeInput input) {
        var level = pedestal.getLevel();
        var inv = pedestal.getInventory();

        assert level != null;
        for (
            GemstoneColor color : PastelGemstoneColor.values()
        ) {
            double efficiency = pedestal
                .getUpgradeHolder()
                .getEffectiveValue(Upgradeable.UpgradeType.EFFICIENCY);
            int drain = Support.chanceRound(getPowder(color) / efficiency, level.random);

            inv.extractItem(PedestalBlockEntity.powderSlot(color), drain, false);
        }
    }

    // This remainder handling is 100% going to break at some point but honestly idc rn
    protected void decrementGridSlot(PedestalBlockEntity pedestal, int slot, int count, ItemStack invStack) {
        ItemStack remainder = this.skipRecipeRemainders() ? ItemStack.EMPTY : invStack.getCraftingRemainingItem();
        remainder.setCount(count);
        var inv = pedestal.getInventory();

        if (pedestal.getLevel() == null) return;
        if (remainder.isEmpty()) {
            invStack.shrink(count);
        } else {
            if (inv
                .getStackInSlot(slot)
                .getCount() == count) {
                inv.setStackInSlot(slot, remainder);
            } else {
                inv.extractItem(slot, count, false);

                ItemEntity itemEntity = new ItemEntity(
                    pedestal.getLevel(),
                    pedestal
                        .getBlockPos()
                        .getX() + 0.5,
                    pedestal
                        .getBlockPos()
                        .getY() + 1,
                    pedestal
                        .getBlockPos()
                        .getZ() + 0.5,
                    remainder
                );
                itemEntity.push(0, 0.05, 0);
                pedestal
                    .getLevel()
                    .addFreshEntity(itemEntity);
            }
        }
    }

    public boolean canCraft(PedestalBlockEntity pedestal) {
        Player playerEntity = pedestal.getOwnerIfOnline();

        if (playerEntity == null || !canPlayerCraft(playerEntity)) {
            return false;
        }

        return pedestal
            .getTier()
            .ordinal() >= tier.ordinal();
    }

    @Override
    public ResourceLocation typeAdvancementID() {
        return UNLOCK_IDENTIFIER;
    }

    @Override
    public boolean canPlayerCraft(Player playerEntity) {
        return this.tier.hasUnlocked(playerEntity) && requiredAdvancementIdentifier
            .map(a -> DatabankUtils.hasAdvancement(playerEntity, a))
            .orElse(true);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public String getRecipeTypeShortID() {
        return "pedestal";
    }

    public boolean allowsYield() {
        return !noBenefitsFromYieldUpgrades;
    }

    public boolean skipRecipeRemainders() {
        return this.skipRecipeRemainders;
    }

    public int getWidth() {
        return 3;
    }

    public int getHeight() {
        return 3;
    }

    public boolean isShapeless() {
        return true;
    }

    public int getGridSlotId(int index) {
        int width = getWidth();
        int x = index % width;
        int y = (index - x) / width;
        return 3 * y + x;
    }

}
