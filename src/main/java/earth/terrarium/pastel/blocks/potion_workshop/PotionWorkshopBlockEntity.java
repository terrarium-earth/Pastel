package earth.terrarium.pastel.blocks.potion_workshop;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.api.item.InkPoweredPotionFillable;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.capabilities.SidedCapabilityProvider;
import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import earth.terrarium.pastel.capabilities.item.StackHandlerView;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.helpers.level.ContainerWrapper;
import earth.terrarium.pastel.inventories.PotionWorkshopScreenHandler;
import earth.terrarium.pastel.progression.PastelCriteria;
import earth.terrarium.pastel.recipe.SimpleRecipeInput;
import earth.terrarium.pastel.recipe.potion_workshop.PotionMod;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopRecipe;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

public class PotionWorkshopBlockEntity extends BlockEntity
    implements
    MenuProvider,
    StackedContentsCompatible,
    PlayerOwned,
    SidedCapabilityProvider,
    ContainerWrapper {

    // 0: mermaids gem
    // 1: base ingredient
    // 2-4: potion ingredients
    // 5-8: reagents
    // 9-20: 12 inventory slots
    public static final int INVENTORY_SIZE = 22;

    public static final int MERMAIDS_GEM_INPUT_SLOT_ID = 0;

    public static final int BASE_INPUT_SLOT_ID = 1;

    public static final int FIRST_INGREDIENT_SLOT = 2;

    public static final int FIRST_REAGENT_SLOT = 5;

    public static final int FIRST_INVENTORY_SLOT = 9;

    public static final int INVENTORY_SLOT_COUNT = 12;

    public static final int[] INGREDIENT_SLOTS = IntStream
        .rangeClosed(2, 4)
        .toArray();

    public static final int[] REAGENT_SLOTS = IntStream
        .rangeClosed(5, 8)
        .toArray();

    private static final int[] ACCESSIBLE_SLOTS_UP = {
        0, 1, 2, 3, 4
    };

    private static final int[] ACCESSIBLE_SLOTS_SIDE_WITHOUT_UNLOCK = {
        5, 6, 7
    };

    private static final int[] ACCESSIBLE_SLOTS_SIDE_WITH_UNLOCK = {
        5, 6, 7, 8
    };

    private static final int[] ACCESSIBLE_SLOTS_DOWN = {
        9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
    };

    protected final ContainerData propertyDelegate;

    protected FriendlyStackHandler inventory;

    protected boolean inventoryChanged;

    protected RecipeHolder<? extends PotionWorkshopRecipe> currentRecipe;

    protected int brewTime;

    protected int brewTimeTotal;

    protected int potionColor;

    protected UUID ownerUUID;

    protected RecipeHolder<PotionWorkshopBrewingRecipe> lastBrewedRecipe;

    public PotionWorkshopBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.POTION_WORKSHOP.get(), pos, state);
        this.inventory = new FriendlyStackHandler(INVENTORY_SIZE);
        inventory.addListener(i -> inventoryChanged = true);

        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> PotionWorkshopBlockEntity.this.brewTime;
                    case 1 -> PotionWorkshopBlockEntity.this.brewTimeTotal;
                    default -> PotionWorkshopBlockEntity.this.potionColor;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PotionWorkshopBlockEntity.this.brewTime = value;
                    case 1 -> PotionWorkshopBlockEntity.this.brewTimeTotal = value;
                    case 2 -> PotionWorkshopBlockEntity.this.potionColor = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @SuppressWarnings(
        "unchecked"
    )
    public static void tick(
        Level world,
        BlockPos blockPos,
        BlockState blockState,
        PotionWorkshopBlockEntity potionWorkshopBlockEntity
    ) {
        // check recipe crafted last tick => performance
        boolean shouldMarkDirty = false;

        var calculatedRecipe = calculateRecipe(world, potionWorkshopBlockEntity);
        if (potionWorkshopBlockEntity.currentRecipe != calculatedRecipe) {
            potionWorkshopBlockEntity.currentRecipe = calculatedRecipe;
            potionWorkshopBlockEntity.brewTime = 0;
            if (potionWorkshopBlockEntity.currentRecipe != null) {
                potionWorkshopBlockEntity.brewTimeTotal = calculatedRecipe
                    .value()
                    .getCraftingTime();
                potionWorkshopBlockEntity.potionColor = calculatedRecipe
                    .value()
                    .getColor();
            }
            shouldMarkDirty = true;
        }
        potionWorkshopBlockEntity.inventoryChanged = false;

        if (calculatedRecipe != null) {
            // if crafting has not started: check if the inventory has enough room to start
            if (potionWorkshopBlockEntity.brewTime > 0 || hasRoomInOutputInventoryFor(
                potionWorkshopBlockEntity,
                calculatedRecipe
                    .value()
                    .getMinOutputCount(
                        potionWorkshopBlockEntity.inventory
                            .getStackInSlot(
                                (BASE_INPUT_SLOT_ID)
                            )
                    )
            )) {
                if (potionWorkshopBlockEntity.brewTime == potionWorkshopBlockEntity.brewTimeTotal) {
                    if (calculatedRecipe.value() instanceof PotionWorkshopBrewingRecipe) {
                        Item baseItem = potionWorkshopBlockEntity.inventory
                            .getStackInSlot(BASE_INPUT_SLOT_ID)
                            .getItem();
                        if (baseItem instanceof InkPoweredPotionFillable) {
                            fillPotionFillable(
                                potionWorkshopBlockEntity,
                                (RecipeHolder<PotionWorkshopBrewingRecipe>) calculatedRecipe
                            );
                        } else if (baseItem.equals(Items.ARROW)) {
                            createTippedArrows(
                                potionWorkshopBlockEntity,
                                (RecipeHolder<PotionWorkshopBrewingRecipe>) calculatedRecipe
                            );
                        } else {
                            brewRecipe(
                                potionWorkshopBlockEntity,
                                (RecipeHolder<PotionWorkshopBrewingRecipe>) calculatedRecipe
                            );
                        }
                    } else if (calculatedRecipe.value() instanceof PotionWorkshopCraftingRecipe) {
                        craftRecipe(
                            potionWorkshopBlockEntity,
                            (RecipeHolder<PotionWorkshopCraftingRecipe>) calculatedRecipe
                        );
                    }

                    potionWorkshopBlockEntity.brewTime = 0;
                    potionWorkshopBlockEntity.inventoryChanged = true;
                    potionWorkshopBlockEntity.playSound(SoundEvents.BREWING_STAND_BREW);
                } else {
                    potionWorkshopBlockEntity.brewTime++;
                }

                shouldMarkDirty = true;
            }
        }

        if (shouldMarkDirty) {
            setChanged(world, blockPos, blockState);
        }
    }

    public static boolean hasRoomInOutputInventoryFor(
        @NotNull PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        int count
    ) {
        for (
            int slotID : ACCESSIBLE_SLOTS_DOWN
        ) {
            if (potionWorkshopBlockEntity.inventory
                .getStackInSlot(slotID)
                .isEmpty()) {
                count--;
                if (count == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static @Nullable RecipeHolder<? extends PotionWorkshopRecipe> calculateRecipe(
        Level world,
        @NotNull PotionWorkshopBlockEntity workshop
    ) {
        if (!workshop.inventoryChanged) {
            if (workshop.currentRecipe != null) {
                var slot = workshop.inventory.getStackInSlot(BASE_INPUT_SLOT_ID);
                if (workshop.currentRecipe
                    .value() instanceof PotionWorkshopCraftingRecipe craftingRecipe && !craftingRecipe
                        .getBaseIngredient()
                        .test(slot) || workshop.currentRecipe.value() instanceof PotionWorkshopBrewingRecipe && slot
                            .isEmpty()) {
                    workshop.currentRecipe = null;
                    workshop.brewTime = 0;
                    workshop.setChanged();
                }
            }

            return workshop.currentRecipe;
        }

        RecipeHolder<? extends PotionWorkshopRecipe> newRecipe = null;
        var current = workshop.currentRecipe == null ? null : workshop.currentRecipe.value();
        if (current instanceof PotionWorkshopBrewingRecipe potionWorkshopBrewingRecipe && current
            .matches(
                workshop.getRecipeInput(),
                world
            )) {
            // we check for reagents here instead of the recipe itself because of performance
            if (isBrewingRecipeApplicable(
                potionWorkshopBrewingRecipe,
                workshop.inventory.getStackInSlot(BASE_INPUT_SLOT_ID),
                workshop
            )) {
                return workshop.currentRecipe;
            }
        } else if (current instanceof PotionWorkshopCraftingRecipe && current
            .matches(
                workshop.getRecipeInput(),
                world
            )) {
                newRecipe = workshop.currentRecipe;
            } else {
                // current recipe does not match last recipe
                // => search valid recipe
                var newPotionWorkshopBrewingRecipe = world
                    .getRecipeManager()
                    .getRecipeFor(
                        PastelRecipeTypes.POTION_WORKSHOP_BREWING,
                        workshop.getRecipeInput(),
                        world
                    )
                    .orElse(null);
                if (newPotionWorkshopBrewingRecipe != null) {
                    if (newPotionWorkshopBrewingRecipe
                        .value()
                        .canPlayerCraft(workshop.getOwnerIfOnline())) {
                        // we check for reagents here instead of the recipe itself for performance reasons
                        if (isBrewingRecipeApplicable(
                            newPotionWorkshopBrewingRecipe.value(),
                            workshop.inventory.getStackInSlot(BASE_INPUT_SLOT_ID),
                            workshop
                        )) {
                            return newPotionWorkshopBrewingRecipe;
                        }
                    }
                } else {
                    var newPotionWorkshopCraftingRecipe = world
                        .getRecipeManager()
                        .getRecipeFor(
                            PastelRecipeTypes.POTION_WORKSHOP_CRAFTING,
                            workshop.getRecipeInput(),
                            world
                        )
                        .orElse(null);
                    if (newPotionWorkshopCraftingRecipe != null) {
                        if (newPotionWorkshopCraftingRecipe
                            .value()
                            .canPlayerCraft(workshop.getOwnerIfOnline())) {
                            newRecipe = newPotionWorkshopCraftingRecipe;
                        }
                    }
                }
            }

        return newRecipe;
    }

    private static boolean hasUniqueReagents(PotionWorkshopBlockEntity potionWorkshopBlockEntity) {
        List<Item> reagentItems = new ArrayList<>();
        for (
            int slot : REAGENT_SLOTS
        ) {
            ItemStack reagentStack = potionWorkshopBlockEntity.inventory.getStackInSlot(slot);
            if (!reagentStack.isEmpty()) {
                if (reagentItems.contains(reagentStack.getItem())) {
                    return false;
                } else {
                    reagentItems.add(reagentStack.getItem());
                }
            }
        }
        return true;
    }

    private static boolean isBrewingRecipeApplicable(
        PotionWorkshopBrewingRecipe recipe,
        ItemStack baseIngredient,
        PotionWorkshopBlockEntity potionWorkshopBlockEntity
    ) {
        PotionMod potionMod = getPotionModFromReagents(potionWorkshopBlockEntity);
        return hasUniqueReagents(potionWorkshopBlockEntity) && recipe.recipeData
            .isApplicableTo(baseIngredient, potionMod) && !(potionMod
                .flags()
                .incurable() && recipe.recipeData
                    .statusEffect()
                    .is(PastelMobEffectTags.CANNOT_BE_INCURABLE));
    }

    private static void craftRecipe(
        PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        RecipeHolder<PotionWorkshopCraftingRecipe> recipe
    ) {
        var world = potionWorkshopBlockEntity.level;
        if (world == null) return;

        // consume ingredients
        decrementIngredientSlots(potionWorkshopBlockEntity);
        if (recipe
            .value()
            .consumesBaseIngredient()) {
            decrementBaseIngredientSlot(
                potionWorkshopBlockEntity,
                recipe
                    .value()
                    .getBaseIngredient()
                    .getCount()
            );
        }

        // output
        addToInventoryOrSpawn(
            potionWorkshopBlockEntity,
            recipe
                .value()
                .assemble(
                    potionWorkshopBlockEntity.getRecipeInput(),
                    potionWorkshopBlockEntity.level.registryAccess()
                )
        );
    }

    private static void brewRecipe(
        PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        RecipeHolder<PotionWorkshopBrewingRecipe> brewingRecipe
    ) {
        Level world = potionWorkshopBlockEntity.getLevel();
        if (world == null) return;

        // process reagents
        PotionMod potionMod = getPotionModFromReagents(potionWorkshopBlockEntity);

        int maxBrewedPotionsAmount = Support
            .chanceRound(
                brewingRecipe
                    .value()
                    .getModifiedYield(potionMod),
                world.random
            );
        int brewedAmount = Math
            .min(
                potionWorkshopBlockEntity.inventory
                    .getStackInSlot(BASE_INPUT_SLOT_ID)
                    .getCount(),
                maxBrewedPotionsAmount
            );

        // calculate outputs
        ItemStack bottles = potionWorkshopBlockEntity.inventory.getStackInSlot(BASE_INPUT_SLOT_ID);
        List<ItemStack> results = brewingRecipe
            .value()
            .getPotions(
                bottles,
                potionMod,
                potionWorkshopBlockEntity.lastBrewedRecipe,
                world.random,
                brewedAmount
            );

        // consume ingredients
        decrementIngredientSlots(potionWorkshopBlockEntity);
        decrementBaseIngredientSlot(potionWorkshopBlockEntity, brewedAmount);
        decrementReagentSlots(potionWorkshopBlockEntity);

        // trigger advancements for all brewed potions
        ServerPlayer serverPlayerEntity = (ServerPlayer) potionWorkshopBlockEntity.getOwnerIfOnline();
        if (brewedAmount <= 0) {
            PastelCriteria.POTION_WORKSHOP_BREWING.trigger(serverPlayerEntity, ItemStack.EMPTY, 0);
        } else {
            for (
                ItemStack potion : results
            ) {
                if (serverPlayerEntity != null) {
                    PastelCriteria.POTION_WORKSHOP_BREWING.trigger(serverPlayerEntity, potion, brewedAmount);
                    potion
                        .getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                        .potion()
                        .ifPresent(
                            p -> CriteriaTriggers.BREWED_POTION.trigger(serverPlayerEntity, p)
                        );
                }

                addToInventoryOrSpawn(potionWorkshopBlockEntity, potion);
            }
        }

        potionWorkshopBlockEntity.lastBrewedRecipe = brewingRecipe;
    }

    private static void createTippedArrows(
        PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        RecipeHolder<PotionWorkshopBrewingRecipe> brewingRecipe
    ) {
        Level world = potionWorkshopBlockEntity.getLevel();
        if (world == null) return;

        // process reagents
        PotionMod potionMod = getPotionModFromReagents(potionWorkshopBlockEntity);

        // the multiplication happening after the decimal chance rounding is not a mistake it is me being evil ~ Azzyy
        // we are nice to our players this one time ~Dafuqs
        int maxTippedArrowsAmount = Support
            .chanceRound(
                brewingRecipe
                    .value()
                    .getModifiedYield(potionMod) * PotionWorkshopBrewingRecipe.ARROW_COUNT_MULTIPLIER,
                world.random
            );
        int tippedAmount = Math
            .min(
                potionWorkshopBlockEntity.inventory
                    .getStackInSlot(BASE_INPUT_SLOT_ID)
                    .getCount(),
                maxTippedArrowsAmount
            );

        // calculate outputs
        ItemStack arrows = potionWorkshopBlockEntity.inventory.getStackInSlot(BASE_INPUT_SLOT_ID);
        ItemStack tippedArrows = brewingRecipe
            .value()
            .getTippedArrows(
                arrows,
                potionMod,
                potionWorkshopBlockEntity.lastBrewedRecipe,
                tippedAmount,
                world.random
            );

        // consume ingredients
        decrementIngredientSlots(potionWorkshopBlockEntity);
        decrementBaseIngredientSlot(potionWorkshopBlockEntity, tippedAmount);
        decrementReagentSlots(potionWorkshopBlockEntity);

        // trigger advancements for all brewed potions
        ServerPlayer serverPlayerEntity = (ServerPlayer) potionWorkshopBlockEntity.getOwnerIfOnline();
        InventoryHelper
            .addToInventory(
                potionWorkshopBlockEntity.inventory,
                tippedArrows,
                FIRST_INVENTORY_SLOT,
                FIRST_INVENTORY_SLOT + INVENTORY_SLOT_COUNT
            );
        if (serverPlayerEntity != null) {
            PastelCriteria.POTION_WORKSHOP_BREWING.trigger(serverPlayerEntity, tippedArrows, tippedArrows.getCount());
        }

        potionWorkshopBlockEntity.lastBrewedRecipe = brewingRecipe;
    }

    private static void fillPotionFillable(
        PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        RecipeHolder<PotionWorkshopBrewingRecipe> brewingRecipe
    ) {
        ItemStack potionFillableStack = potionWorkshopBlockEntity.inventory.getStackInSlot(BASE_INPUT_SLOT_ID);
        if (potionFillableStack
            .getItem() instanceof InkPoweredPotionFillable && potionWorkshopBlockEntity.level != null) {
            // process reagents
            PotionMod potionMod = getPotionModFromReagents(potionWorkshopBlockEntity);

            // consume ingredients
            decrementIngredientSlots(potionWorkshopBlockEntity);
            decrementReagentSlots(potionWorkshopBlockEntity);

            int maxBrewedPotionsAmount = Support
                .chanceRound(
                    brewingRecipe
                        .value()
                        .getModifiedYield(potionMod),
                    potionWorkshopBlockEntity.level.random
                );
            if (maxBrewedPotionsAmount < 1) {
                ServerPlayer serverPlayerEntity = (ServerPlayer) potionWorkshopBlockEntity.getOwnerIfOnline();
                if (serverPlayerEntity != null) {
                    PastelCriteria.POTION_WORKSHOP_BREWING.trigger(serverPlayerEntity, potionFillableStack, 0);
                }
                return;
            }

            brewingRecipe
                .value()
                .fillPotionFillable(
                    potionFillableStack,
                    potionMod,
                    potionWorkshopBlockEntity.lastBrewedRecipe,
                    potionWorkshopBlockEntity.level.random
                );
            potionWorkshopBlockEntity.inventory.setStackInSlot(BASE_INPUT_SLOT_ID, ItemStack.EMPTY);
            InventoryHelper
                .addToInventory(
                    potionWorkshopBlockEntity.inventory,
                    potionFillableStack,
                    FIRST_INVENTORY_SLOT,
                    FIRST_INVENTORY_SLOT + INVENTORY_SLOT_COUNT
                );

            // trigger advancements for all brewed potions
            ServerPlayer serverPlayerEntity = (ServerPlayer) potionWorkshopBlockEntity.getOwnerIfOnline();
            if (serverPlayerEntity != null) {
                PastelCriteria.POTION_WORKSHOP_BREWING.trigger(serverPlayerEntity, potionFillableStack, 1);
            }

            potionWorkshopBlockEntity.lastBrewedRecipe = brewingRecipe;
        }
    }

    private static PotionMod getPotionModFromReagents(PotionWorkshopBlockEntity potionWorkshopBlockEntity) {
        Level world = potionWorkshopBlockEntity.getLevel();
        var builder = new PotionMod.Builder();
        if (world != null) {
            for (
                int slot : REAGENT_SLOTS
            ) {
                ItemStack slotStack = potionWorkshopBlockEntity.inventory.getStackInSlot(slot);
                if (!slotStack.isEmpty()) {
                    PotionWorkshopReactingRecipe.combine(builder, slotStack, world.random);
                }
            }
        }
        return builder.build();
    }

    public static void decrementBaseIngredientSlot(
        @NotNull PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        int amount
    ) {
        if (amount > 0) {
            decrementUsingRemainder(
                potionWorkshopBlockEntity,
                potionWorkshopBlockEntity.inventory.getStackInSlot(BASE_INPUT_SLOT_ID),
                amount
            );
        }
    }

    public static void decrementIngredientSlots(@NotNull PotionWorkshopBlockEntity workshop) {
        workshop.inventory
            .getStackInSlot(MERMAIDS_GEM_INPUT_SLOT_ID)
            .shrink(1);
        if (workshop.level == null) return;

        var recipe = workshop.currentRecipe;
        int requiredExperience = recipe
            .value()
            .getRequiredExperience();
        for (
            IngredientStack ingredientStack : recipe
                .value()
                .getOtherIngredients()
        ) {
            for (
                int slot : INGREDIENT_SLOTS
            ) {
                ItemStack slotStack = workshop.inventory.getStackInSlot(slot);
                if (ingredientStack.test(slotStack)) {
                    // if the recipe requires experience: remove XP from the item (like the experience bottle recipe)

                    var storage = Optional
                        .ofNullable(
                            slotStack
                                .getCapability(
                                    PastelCapabilities.Misc.XP,
                                    workshop.level.registryAccess()
                                )
                        );

                    int fre = requiredExperience;
                    if (storage
                        .map(s -> s.extractOrFail(fre))
                        .orElse(false)) {
                        requiredExperience = 0;
                    } else {
                        decrementUsingRemainder(workshop, slotStack, 1);
                    }

                    break;
                }
            }
        }
    }

    public static void decrementReagentSlots(@NotNull PotionWorkshopBlockEntity potionWorkshopBlockEntity) {
        for (
            int i : REAGENT_SLOTS
        ) {
            ItemStack currentStack = potionWorkshopBlockEntity.inventory.getStackInSlot(i);
            if (!currentStack.isEmpty()) {
                decrementUsingRemainder(potionWorkshopBlockEntity, currentStack, 1);
            }
        }
    }

    private static void decrementUsingRemainder(
        @NotNull PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        ItemStack currentStack,
        int amount
    ) {
        ItemStack currentRemainder = currentStack.getCraftingRemainingItem();
        currentStack.shrink(amount);
        if (!currentRemainder.isEmpty()) {
            addToInventoryOrSpawn(potionWorkshopBlockEntity, currentRemainder);
        }
    }

    private static void addToInventoryOrSpawn(
        @NotNull PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        ItemStack currentRemainder
    ) {
        currentRemainder = InventoryHelper
            .offerToInventory(
                potionWorkshopBlockEntity.inventory,
                currentRemainder,
                FIRST_INVENTORY_SLOT,
                FIRST_INVENTORY_SLOT + INVENTORY_SLOT_COUNT
            );
        if (!currentRemainder.isEmpty()) {
            Containers
                .dropItemStack(
                    potionWorkshopBlockEntity.level,
                    potionWorkshopBlockEntity.worldPosition.getX(),
                    potionWorkshopBlockEntity.worldPosition.getY(),
                    potionWorkshopBlockEntity.worldPosition.getZ(),
                    currentRemainder
                );
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        inventory.deserializeNBT(registryLookup, nbt.getCompound("inventory"));
        this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
        if (nbt.contains("LastBrewedRecipe") && this.getLevel() != null) {
            var id = ResourceLocation.parse(nbt.getString("LastBrewedRecipe"));
            var optRecipe = registryLookup
                .lookup(Registries.RECIPE)
                .flatMap(impl -> impl.get(ResourceKey.create(Registries.RECIPE, id)));
            if (optRecipe.isPresent() && optRecipe
                .get()
                .value() instanceof PotionWorkshopBrewingRecipe brewingRecipe) {
                this.lastBrewedRecipe = new RecipeHolder<>(id, brewingRecipe);
            }
        } else {
            this.lastBrewedRecipe = null;
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        nbt.put("inventory", inventory.serializeNBT(registryLookup));
        PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
        if (this.lastBrewedRecipe != null) {
            nbt
                .putString(
                    "LastBrewedRecipe",
                    this.lastBrewedRecipe
                        .id()
                        .toString()
                );
        }
    }

    private void playSound(SoundEvent soundEvent) {
        if (level == null) return;
        RandomSource random = level.random;
        level
            .playSound(
                null,
                worldPosition.getX(),
                worldPosition.getY(),
                worldPosition.getZ(),
                soundEvent,
                SoundSource.BLOCKS,
                0.9F + random.nextFloat() * 0.2F,
                0.9F + random.nextFloat() * 0.15F
            );
    }

    @Override
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public SimpleRecipeInput getRecipeInput() {
        return new SimpleRecipeInput(inventory.getInternalList());
    }

    @Override
    public void fillStackedContents(StackedContents recipeMatcher) {
        recipeMatcher.accountStack(this.inventory.getStackInSlot(2));
        recipeMatcher.accountStack(this.inventory.getStackInSlot(3));
        recipeMatcher.accountStack(this.inventory.getStackInSlot(4));
    }

    @Override
    public void setOwner(Player playerEntity) {
        this.ownerUUID = playerEntity.getUUID();
        setChanged();
    }

    private boolean hasFourthReagentSlotUnlocked(Player playerEntity) {
        if (playerEntity == null) {
            return false;
        } else {
            return DatabankUtils
                .hasAdvancement(
                    playerEntity,
                    PastelAdvancements.Milestones.UNLOCK_FOURTH_POTION_WORKSHOP_REAGENT_SLOT
                );
        }
    }

    private boolean hasFourthReagentSlotUnlocked() {
        if (this.ownerUUID == null) {
            return false;
        } else {
            return hasFourthReagentSlotUnlocked(getOwnerIfOnline());
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.pastel.potion_workshop");
    }

    @Nullable @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new PotionWorkshopScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public IItemHandler exposeItemHandlers(Direction dir) {
        if (dir == Direction.UP) {
            return new StackHandlerView(inventory, 0, ACCESSIBLE_SLOTS_UP.length)
                .disableExtraction()
                .addFilter(MERMAIDS_GEM_INPUT_SLOT_ID, Ingredient.of(PastelItems.MERMAIDS_GEM.get()));
        }

        return new StackHandlerView(inventory, ACCESSIBLE_SLOTS_DOWN[0], ACCESSIBLE_SLOTS_DOWN.length);
    }

    @Override
    public FriendlyStackHandler getHandlerForScreens() {
        return inventory;
    }
}
