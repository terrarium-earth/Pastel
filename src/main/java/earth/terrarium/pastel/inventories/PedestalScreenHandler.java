package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.inventories.slots.DisabledSlot;
import earth.terrarium.pastel.inventories.slots.PedestalPreviewSlot;
import earth.terrarium.pastel.inventories.slots.StackFilterSlot;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PedestalScreenHandler extends RecipeBookMenu<RecipeInput, Recipe<RecipeInput>> {

    public record ScreenOpeningData(BlockPos pos, PedestalTier pedestalRecipeTier) {
        public static final StreamCodec<ByteBuf, ScreenOpeningData> STREAM_CODEC = StreamCodec
            .composite(
                BlockPos.STREAM_CODEC,
                ScreenOpeningData::pos,
                PedestalTier.STREAM_CODEC,
                ScreenOpeningData::pedestalRecipeTier,
                PedestalScreenHandler.ScreenOpeningData::new
            );
    }

    protected final Level world;

    private final PedestalBlockEntity pedestal;

    private final ContainerData data;

    private final RecipeBookType category;

    private final PedestalTier pedestalRecipeTier;

    // clientside
    public PedestalScreenHandler(int syncId, Inventory playerInventory, RegistryFriendlyByteBuf buf) {
        this(syncId, playerInventory, ScreenOpeningData.STREAM_CODEC.decode(buf));
    }

    public PedestalScreenHandler(int syncId, Inventory playerInventory, ScreenOpeningData data) {
        this(
            syncId,
            playerInventory,
            playerInventory.player.level().getBlockEntity(data.pos, PastelBlockEntities.PEDESTAL.get()).orElseThrow(),
            new SimpleContainerData(2),
            data.pedestalRecipeTier
        );
    }

    // serverside
    public PedestalScreenHandler(
        int syncId,
        Inventory pInv,
        PedestalBlockEntity blockEntity,
        ContainerData data,
        PedestalTier tier
    ) {
        this(PastelScreenHandlerTypes.PEDESTAL, RecipeBookType.CRAFTING, syncId, pInv, blockEntity, data, tier);
    }

    protected PedestalScreenHandler(
        MenuType<?> type,
        RecipeBookType recipeBookCategory,
        int i,
        Inventory pInv,
        PedestalBlockEntity pedestal,
        ContainerData data,
        PedestalTier tier
    ) {
        super(type, i);
        this.category = recipeBookCategory;
        this.data = data;
        this.world = pInv.player.level();

        this.pedestal = pedestal;
        this.pedestalRecipeTier = tier;

        checkContainerSize(pedestal, PedestalBlockEntity.SIZE);
        checkContainerDataCount(data, 2);

        // crafting slots
        for (
            int m = 0;
            m < 3;
            ++m
        ) {
            for (
                int n = 0;
                n < 3;
                ++n
            ) {
                addSlot(new Slot(pedestal, n + m * 3, 30 + n * 18, 19 + m * 18));
            }
        }

        // gemstone powder slots
        switch (getTier()) {
            case BASIC, SIMPLE -> {
                this.addSlot(new StackFilterSlot(pedestal, 9, 44 + 18, 77, PastelItems.TOPAZ_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 10, 44 + 2 * 18, 77, PastelItems.AMETHYST_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 11, 44 + 3 * 18, 77, PastelItems.CITRINE_POWDER.get()));
                this.addSlot(new DisabledSlot(pedestal, 12, -2000, 77));
                this.addSlot(new DisabledSlot(pedestal, 13, -2000, 77));
            }
            case ADVANCED -> {
                this.addSlot(new StackFilterSlot(pedestal, 9, 35 + 18, 77, PastelItems.TOPAZ_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 10, 35 + 2 * 18, 77, PastelItems.AMETHYST_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 11, 35 + 3 * 18, 77, PastelItems.CITRINE_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 12, 35 + 4 * 18, 77, PastelItems.ONYX_POWDER.get()));
                this.addSlot(new DisabledSlot(pedestal, 13, -2000, 77));
            }
            case COMPLEX -> {
                this.addSlot(new StackFilterSlot(pedestal, 9, 44, 77, PastelItems.TOPAZ_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 10, 44 + 18, 77, PastelItems.AMETHYST_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 11, 44 + 2 * 18, 77, PastelItems.CITRINE_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 12, 44 + 3 * 18, 77, PastelItems.ONYX_POWDER.get()));
                this.addSlot(new StackFilterSlot(pedestal, 13, 44 + 4 * 18, 77, PastelItems.MOONSTONE_POWDER.get()));
            }
        }

        // crafting tablet slot
        this
            .addSlot(
                new StackFilterSlot(pedestal, PedestalBlockEntity.TABLET, 93, 19, PastelItems.CRAFTING_TABLET.get())
            );

        // preview slot
        this.addSlot(new PedestalPreviewSlot(pedestal, 15, 127, 37));

        // player inventory
        int l;
        for (
            l = 0;
            l < 3;
            ++l
        ) {
            for (
                int k = 0;
                k < 9;
                ++k
            ) {
                this.addSlot(new Slot(pInv, k + l * 9 + 9, 8 + k * 18, 112 + l * 18));
            }
        }

        // player hotbar
        for (
            l = 0;
            l < 9;
            ++l
        ) {
            this.addSlot(new Slot(pInv, l, 8 + l * 18, 170));
        }

        this.addDataSlots(data);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents recipeMatcher) {
        //this.blockEntity.fillStackedContents(recipeMatcher); This probably does something important. I suppose
    }

    @Override
    public void clearCraftingContent() {
        for (
            int i = 0;
            i < 9;
            i++
        ) {
            this.getSlot(i).setByPlayer(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean recipeMatches(RecipeHolder<Recipe<RecipeInput>> recipe) {
        return pedestal != null && recipe.value().matches(pedestal.getInput(), world);
    }

    @Override
    public int getResultSlotIndex() {
        return 16;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 3;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public boolean stillValid(Player player) {
        return pedestal.stillValid(player);
    }

    @OnlyIn(
        Dist.CLIENT
    )
    public int getCraftingProgress() {
        float time = getCraftingTime();
        if (time < 0)
            return 0;

        return Math.round(time / getCraftingTimeTotal() * 24);
    }

    public boolean isCrafting() {
        return getCraftingTime() > 0;
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public RecipeBookType getRecipeBookType() {
        return this.category;
    }

    @Override
    public boolean shouldMoveToInventory(int index) {
        return index != 1;
    }

    // Shift-Clicking
    // 0-8: crafting slots
    // 9-13: powder slots
    // 14: crafting tablet
    // 15: preview slot
    // 16: hidden output slot
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack clickedStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack clickedStack = slot.getItem();
            clickedStackCopy = clickedStack.copy();

            if (index < 15) {
                // pedestal => player inv
                if (!this.moveItemStackTo(clickedStack, 16, 51, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (clickedStackCopy.is(PastelItems.TOPAZ_POWDER.get())) {
                if (!this.moveItemStackTo(clickedStack, 9, 10, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (clickedStackCopy.is(PastelItems.AMETHYST_POWDER.get())) {
                if (!this.moveItemStackTo(clickedStack, 10, 11, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (clickedStackCopy.is(PastelItems.CITRINE_POWDER.get())) {
                if (!this.moveItemStackTo(clickedStack, 11, 12, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (clickedStackCopy.is(PastelItems.ONYX_POWDER.get())) {
                if (!this.moveItemStackTo(clickedStack, 12, 13, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (clickedStackCopy.is(PastelItems.MOONSTONE_POWDER.get())) {
                if (!this.moveItemStackTo(clickedStack, 13, 14, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (clickedStackCopy.is(PastelItems.CRAFTING_TABLET.get())) {
                if (!this
                    .moveItemStackTo(clickedStack, PedestalBlockEntity.TABLET, PedestalBlockEntity.TABLET + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            // crafting grid
            if (!this.moveItemStackTo(clickedStack, 0, 9, false)) {
                pedestal.setChanged();
                return ItemStack.EMPTY;
            }

            if (clickedStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (clickedStack.getCount() == clickedStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, clickedStack);
        }

        return clickedStackCopy;
    }

    public int getCraftingTime() {
        return data.get(0);
    }

    public int getCraftingTimeTotal() {
        return data.get(1);
    }

    public PedestalTier getTier() {
        return this.pedestalRecipeTier;
    }

    public PedestalBlockEntity getBlockEntity() {
        return pedestal;
    }

    public BlockPos getBlockPos() {
        return pedestal.getBlockPos();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
    }

}
