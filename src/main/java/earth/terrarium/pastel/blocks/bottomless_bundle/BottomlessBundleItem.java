package earth.terrarium.pastel.blocks.bottomless_bundle;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.ItemPickupListener;
import earth.terrarium.pastel.api.item.ItemReference;
import earth.terrarium.pastel.api.item.ItemStorage;
import earth.terrarium.pastel.api.render.DynamicItemRenderer;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.items.tooltip.ItemStorageTooltipData;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.LockCode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;

/**
 * Implementation Detail <p> While it may appear otherwise, the count a bottomless bundle can store may never exceed
 * {@link Integer#MAX_VALUE}
 */
public class BottomlessBundleItem extends BlockItem implements ItemPickupListener, ItemStorage.LimitCallback {

    private static final long MAX_STORED_AMOUNT_BASE = 20000;

    public BottomlessBundleItem(Block block, Item.Properties settings) {
        super(block, settings.component(PastelDataComponentTypes.ITEM_STORAGE, ItemStorage.Component.DEFAULT));
    }

    public static long getMaxStoredAmount(int powerLevel) {
        return MAX_STORED_AMOUNT_BASE * (int) Math.pow(10, Math.min(5, powerLevel)); // to not exceed int max
    }

    private static boolean dropOneBundledStack(ItemStack bundle, Player player) {
        var storage = ItemStorage.load(bundle);
        var dropped = storage.extract(storage.stackSize());
        if (dropped.isEmpty())
            return false;

        player.drop(dropped, true);
        storage.save(bundle);
        return true;
    }

    public static boolean isLocked(ItemStack itemStack) {
        return itemStack.has(DataComponents.LOCK);
    }

    public static ItemReference getStoredReference(ItemStack bundle) {
        return ItemStorage.load(bundle)
                          .getReference();
    }

    public static long getStoredAmount(ItemStack bundle) {
        return ItemStorage.load(bundle)
                          .getCount();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (user.isShiftKeyDown()) {
            ItemStack handStack = user.getItemInHand(hand);
            if (handStack.has(DataComponents.LOCK)) {
                handStack.remove(DataComponents.LOCK);
                if (world.isClientSide) {
                    playZipSound(user, 0.8F);
                }
            } else {
                handStack.set(DataComponents.LOCK, LockCode.NO_LOCK);
                if (world.isClientSide) {
                    playZipSound(user, 1.0F);
                }
            }
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        } else if (dropOneBundledStack(itemStack, user)) {
            this.playDropContentsSound(user);
            user.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer()
                   .isShiftKeyDown())
            return super.useOn(context);
        return InteractionResult.PASS;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return true;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack bundle) {
        return Optional.of(new ItemStorageTooltipData(ItemStorage.load(bundle)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        boolean locked = isLocked(stack);
        long storedAmount = getStoredAmount(stack);
        if (storedAmount == 0) {
            tooltip.add(Component.translatable("item.pastel.bottomless_bundle.tooltip.empty")
                                 .withStyle(ChatFormatting.GRAY));
            if (locked) {
                tooltip.add(
                    Component.translatable("item.pastel.bottomless_bundle.tooltip.locked")
                             .withStyle(ChatFormatting.GRAY));
            }
        } else {
            var variant = getStoredReference(stack);
            var powerLevel = context.registries()
                                    .lookup(Registries.ENCHANTMENT)
                                    .flatMap(impl -> impl.get(Enchantments.POWER))
                                    .map(ench -> EnchantmentHelper.getItemEnchantmentLevel(ench, stack))
                                    .orElse(0);
            String totalStacks = Support.getShortenedNumberString(storedAmount / (float) variant.asItem()
                                                                                                .getDefaultMaxStackSize());
            tooltip.add(Component.translatable(
                                     "item.pastel.bottomless_bundle.tooltip.count", storedAmount,
                                     getMaxStoredAmount(powerLevel), totalStacks
                                 )
                                 .withStyle(ChatFormatting.GRAY));
            if (locked) {
                tooltip.add(Component.translatable("item.pastel.bottomless_bundle.tooltip.locked")
                                     .withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.translatable(
                                         "item.pastel.bottomless_bundle.tooltip.enter_inventory",
                                         variant.asItem()
                                                .getDescription()
                                                .getString()
                                     )
                                     .withStyle(ChatFormatting.GRAY));
            }
        }
        if (EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.DELETES_OVERFLOW)) {
            tooltip.add(Component.translatable("item.pastel.bottomless_bundle.tooltip.voiding"));
        }
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        var storage = entity.getItem()
                            .get(PastelDataComponentTypes.ITEM_STORAGE);
        if (storage != null) {
            entity.getItem()
                  .set(PastelDataComponentTypes.ITEM_STORAGE, ItemStorage.Component.DEFAULT);
            ItemUtils.onContainerDestroyed(
                entity, () -> new ItemStorage.IterableView(new ItemStorage(storage.reference(), storage.count())));
        }
    }

    /**
     * When the bundle is clicked onto another stack
     */
    @Override
    public boolean overrideStackedOnOther(ItemStack bundle, Slot slot, ClickAction clickType, Player player) {
        if (bundle.getCount() != 1 || clickType != ClickAction.SECONDARY) {
            return false;
        }

        var slotStack = slot.getItem();
        var storage = ItemStorage.load(bundle);
        if (slotStack.isEmpty()) {
            var removed = storage.extract(storage.stackSize());
            if (!removed.isEmpty()) {
                this.playRemoveOneSound(player);
                var remainder = slot.safeInsert(removed);
                storage.increment(remainder.getCount());

            }
        } else if (slotStack.getItem()
                            .canFitInsideContainerItems()) {
            var inserted = storage.insert(slotStack);
            if (inserted > 0) {
                this.playInsertSound(player);
                slotStack.shrink(inserted);
            } else {
                return false;
            }
        }

        storage.save(bundle);
        return true;
    }

    /**
     * When a stack is right-clicked onto the bundle
     */
    @Override
    public boolean overrideOtherStackedOnMe(
        ItemStack bundle, ItemStack otherStack, Slot slot, ClickAction clickType, Player player,
        SlotAccess cursorStackReference
    ) {
        if (bundle.getCount() != 1 || clickType != ClickAction.SECONDARY || !slot.allowModification(player)) {
            return false;
        }

        var storage = ItemStorage.load(bundle);
        if (otherStack.isEmpty()) {
            var removed = storage.extract(storage.stackSize());
            if (!removed.isEmpty()) {
                this.playRemoveOneSound(player);
                cursorStackReference.set(removed);
            }
        } else {
            var inserted = storage.insert(otherStack);
            if (inserted > 0) {
                otherStack.shrink(inserted);
                this.playInsertSound(player);
            } else {
                return false;
            }
        }

        storage.save(bundle);
        return true;
    }

    @Override
    public void inventoryTick(ItemStack bundle, Level world, Entity entity, int slot, boolean selected) {
        // We unbundle, tick and then rebundle the stack, in case inventory tick would modify components, count or
        // other properties
        // The slot isn't technically correct, since it's the slot of the bundle, not that of the bundled stack
        var storage = ItemStorage.load(bundle);
        ItemStack bundled = storage.stack(1);
        var original = (int) Math.min(storage.getCount(), Integer.MAX_VALUE);
        ItemStack ticked = bundled.copyWithCount(original);
        ticked.inventoryTick(world, entity, slot, selected);
        if (!ItemStack.isSameItemSameComponents(bundled, ticked) || ticked.getCount() != original) {
            storage.setReference(ItemReference.of(ticked));
            storage.setCount(storage.getCount() - (original - ticked.getCount()));
            storage.save(bundle);
        }
    }


    @Override
    public boolean accepts(Optional<ItemStack> listener, ItemStack proposal) {
        assert listener.isPresent();
        var bundle = listener.get();

        if (proposal.getCapability(PastelCapabilities.Pickup.ITEM) != null)
            return false;

        var reference = getStoredReference(bundle);
        return !reference.isEmpty() && reference.permits(proposal);
    }

    @Override
    public ItemStack receive(Optional<ItemStack> listener, ItemStack stack, Optional<Entity> unused) {
        assert listener.isPresent();
        var bundle = listener.get();

        if (isLocked(bundle)) {
            return stack;
        }

        var storage = ItemStorage.load(bundle);
        var inserted = storage.insert(stack);
        storage.save(bundle);

        if (inserted == stack.getCount())
            return ItemStack.EMPTY;

        stack.shrink(inserted);
        return stack;
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(
            SoundEvents.BUNDLE_REMOVE_ONE, 0.8F,
            0.8F + entity.level()
                         .getRandom()
                         .nextFloat() * 0.4F
        );
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(
            SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level()
                                                          .getRandom()
                                                          .nextFloat() * 0.4F
        );
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(
            SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F,
            0.8F + entity.level()
                         .getRandom()
                         .nextFloat() * 0.4F
        );
    }

    private void playZipSound(Entity entity, float basePitch) {
        entity.playSound(
            PastelSounds.BOTTOMLESS_BUNDLE_ZIP, 0.8F,
            basePitch + entity.level()
                              .getRandom()
                              .nextFloat() * 0.4F
        );
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 5;
    }

    @Override
    public long updateLimit(
        ItemStack holder
    ) { // Frankly this is just horrible and I should be killed with hammers for it
        if (PastelCommon.getRegistryAccess() == null)
            return MAX_STORED_AMOUNT_BASE;

        return getMaxStoredAmount(PastelCommon.getRegistryAccess()
                                              .lookup(Registries.ENCHANTMENT)
                                              .flatMap(impl -> impl.get(Enchantments.POWER))
                                              .map(ench -> EnchantmentHelper.getItemEnchantmentLevel(ench, holder))
                                              .orElse(0));
    }

    public static class BottomlessBundlePlacementDispenserBehavior extends OptionalDispenseItemBehavior {

        @Override
        @SuppressWarnings("resource")
        protected ItemStack execute(BlockSource pointer, ItemStack stack) {
            this.setSuccess(false);
            if (stack.getItem() instanceof BottomlessBundleItem bottomlessBundleItem) {
                Direction direction = pointer.state()
                                             .getValue(DispenserBlock.FACING);
                BlockPos blockPos = pointer.pos()
                                           .relative(direction);
                Direction direction2 = pointer.level()
                                              .isEmptyBlock(blockPos.below()) ? direction : Direction.UP;

                try {
                    this.setSuccess(bottomlessBundleItem.place(
                                                            new DirectionalPlaceContext(pointer.level(), blockPos,
                                                                                        direction, stack, direction2))
                                                        .consumesAction());
                } catch (Exception e) {
                    PastelCommon.logError("Error trying to place bottomless bundle at " + blockPos + " : " + e);
                }
            }
            return stack;
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Renderer implements DynamicItemRenderer {
        public Renderer() {
        }

        @Override
        public void render(
            ItemRenderer renderer, ItemStack stack, ItemDisplayContext mode, boolean leftHanded,
            PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay
        ) {
            if (mode != ItemDisplayContext.GUI
                || getStoredAmount(stack) <= 0)
                return;
            var bundledStack = ItemStorage.load(stack)
                                          .stack(1);
            Minecraft client = Minecraft.getInstance();
            BakedModel bundledModel = renderer.getModel(bundledStack, client.level, client.player, 0);

            matrices.pushPose();
            matrices.scale(0.5F, 0.5F, 0.5F);
            matrices.translate(0.5F, 0.5F, 0.5F);
            renderer.render(bundledStack, mode, leftHanded, matrices, vertexConsumers, light, overlay, bundledModel);
            matrices.popPose();
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.POWER) || enchantment.is(
            PastelEnchantments.VOIDING);
    }

}
