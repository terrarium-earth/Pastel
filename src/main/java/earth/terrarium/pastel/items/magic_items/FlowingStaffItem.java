package earth.terrarium.pastel.items.magic_items;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.interaction.ItemProvider;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleItem;
import earth.terrarium.pastel.components.FlowingStaffComponent;
import earth.terrarium.pastel.components.StoredBlockEntityComponent;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;

public class FlowingStaffItem extends BuildingStaffItem {
    public static final int INK_COST_PER_BLOCK = 1;
    public static final int ADDITIONAL_INK_COST_PER_BLOCK_CONJURED = 2;
    public static final int INK_COST_FOR_CARRY = 50;
    public static final int RANGE = 10;
    public static final int TICKS_PER_ACTION = 2;
    public List<BlockPos> blockPosCache = new ArrayList<>();

    public FlowingStaffItem(Properties settings) {
        super(settings);
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(USED_COLOR);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        var data = stack.getOrDefault(PastelDataComponentTypes.FLOWING_STAFF, FlowingStaffComponent.DEFAULT);
        if (Ench.hasEnchantment(context.registries(), PastelEnchantments.RESONANCE, stack)) {
            tooltip.add(data.blockName()
                            .isPresent() ? Component.translatable("item.pastel.flowing_staff.tooltip.stored")
                                                    .append(data.blockName()
                                                                .get())
                                         : Component.translatable("item.pastel.flowing_staff.tooltip.pickup"));
        } else {
            tooltip.add(Component.translatable("item.pastel.flowing_staff.tooltip.setcorners", RANGE)
                                 .withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("item.pastel.flowing_staff.tooltip.crouch")
                                 .withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("item.pastel.flowing_staff.tooltip.use")
                                 .withStyle(ChatFormatting.GRAY));
            if (data.pos1()
                    .isPresent()) tooltip.add(Component.translatable(
                                                           "item.pastel.flowing_staff.tooltip.pos1", data.pos1()
                                                                                                         .get()
                                                                                                         .getX(),
                                                           data.pos1()
                                                                                                                      .get()
                                                                                                                      .getY(), data.pos1()
                                                                                                                                   .get()
                                                                                                                                   .getZ()
                                                       )
                                                       .withStyle(ChatFormatting.GRAY));
            if (data.pos2()
                    .isPresent()) tooltip.add(Component.translatable(
                                                           "item.pastel.flowing_staff.tooltip.pos2", data.pos2()
                                                                                                         .get()
                                                                                                         .getX(),
                                                           data.pos2()
                                                                                                                      .get()
                                                                                                                      .getY(), data.pos2()
                                                                                                                                   .get()
                                                                                                                                   .getZ()
                                                       )
                                                       .withStyle(ChatFormatting.GRAY));

        }
        addInkPoweredTooltip(tooltip);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel()
                   .isClientSide()) return InteractionResult.CONSUME;
        var player = context.getPlayer();
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.CONSUME;
        var stack = context.getItemInHand();
        var data = stack.getOrDefault(PastelDataComponentTypes.FLOWING_STAFF, FlowingStaffComponent.DEFAULT);
        if (Ench.hasEnchantment(
            context.getLevel()
                   .registryAccess(), PastelEnchantments.RESONANCE, stack
        )) {
            var level = context.getLevel();
            if (!(level instanceof ServerLevel serverLevel)) return InteractionResult.CONSUME;
            var storedBlockData = stack.get(PastelDataComponentTypes.STORED_BLOCK_ENTITY);
            if (storedBlockData == null && serverPlayer.isCrouching()) {
                // store a block
                var pos = context.getClickedPos();
                var bulokEntee = context.getLevel()
                                        .getBlockEntity(pos);
                var state = level.getBlockState(pos);
                if (bulokEntee == null || state.getBlock()
                                               .defaultDestroyTime() <= -1 || serverLevel.getBlockState(pos)
                                                                                         .is(PastelBlockTags.FLOWING_STAFF_MOVE_BLACKLIST) ||
                    !InkPowered.tryDrainEnergy(player, USED_COLOR, INK_COST_FOR_CARRY))
                    return InteractionResult.CONSUME;
                stack.set(
                    PastelDataComponentTypes.STORED_BLOCK_ENTITY,
                    new StoredBlockEntityComponent(state, bulokEntee.saveCustomOnly(level.registryAccess()))
                );
                serverLevel.removeBlockEntity(pos);
                var flowingData = stack.getOrDefault(
                    PastelDataComponentTypes.FLOWING_STAFF, FlowingStaffComponent.DEFAULT);
                stack.set(
                    PastelDataComponentTypes.FLOWING_STAFF, new FlowingStaffComponent(
                        flowingData.pos1(), flowingData.pos2(), flowingData.cornerSwitch(), Optional.of(state.getBlock()
                                                                                                             .getName())
                    )
                );
                serverLevel.setBlock(
                    pos, Blocks.AIR.defaultBlockState(),
                    Block.UPDATE_CLIENTS | Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_NEIGHBORS
                );
                return InteractionResult.CONSUME;
            } else if (storedBlockData != null) {
                // place a block
                var pos = context.getClickedPos()
                                 .relative(context.getClickedFace());
                if (!serverLevel.getBlockState(pos)
                                .canBeReplaced()) return InteractionResult.CONSUME;
                var state = storedBlockData.state();
                if (state.getBlock() instanceof EntityBlock entityBlock) {
                    if (state.hasProperty(ChestBlock.TYPE)) {
                        state = state.setValue(ChestBlock.TYPE, ChestType.SINGLE);
                    }
                    if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                        state = state.setValue(
                            BlockStateProperties.HORIZONTAL_FACING, player.getDirection()
                                                                          .getOpposite()
                        );
                    }
                    serverLevel.setBlock(pos, state, Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
                    state.getBlock()
                         .setPlacedBy(level, pos, state, player, ItemStack.EMPTY);
                    var blockEntity = entityBlock.newBlockEntity(pos, state);
                    if (blockEntity != null) {
                        blockEntity.loadCustomOnly(storedBlockData.blockEntityData(), level.registryAccess());
                        level.setBlockEntity(blockEntity);
                    }
                }
                stack.remove(PastelDataComponentTypes.STORED_BLOCK_ENTITY);
                var flowingData = stack.getOrDefault(
                    PastelDataComponentTypes.FLOWING_STAFF, FlowingStaffComponent.DEFAULT);
                stack.set(
                    PastelDataComponentTypes.FLOWING_STAFF, new FlowingStaffComponent(
                        flowingData.pos1(), flowingData.pos2(), flowingData.cornerSwitch(), Optional.empty())
                );
                return InteractionResult.CONSUME;
            }
        } else {
            if (serverPlayer.isCrouching()) {
                // crouch rclick block: set corner, set next click to be the other corner
                if (data.cornerSwitch()) stack.set(
                    PastelDataComponentTypes.FLOWING_STAFF, new FlowingStaffComponent(
                        data.pos1(), Optional.of(context.getClickedPos()), false, data.blockName())
                );
                else stack.set(
                    PastelDataComponentTypes.FLOWING_STAFF,
                    new FlowingStaffComponent(Optional.of(context.getClickedPos()), data.pos2(), true, data.blockName())
                );
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() || Ench.hasEnchantment(level.registryAccess(), PastelEnchantments.RESONANCE, stack))
            return;
        // if both positions are selected and we're holding it and sneaking, play some particles around the edges to
        // indicate the selected area
        var data = stack.getOrDefault(PastelDataComponentTypes.FLOWING_STAFF, FlowingStaffComponent.DEFAULT);
        if (!isSelected || !(entity instanceof LocalPlayer player) || !player.input.shiftKeyDown || data.pos1()
                                                                                                        .isEmpty() ||
            data.pos2()
                .isEmpty() || !level.isInWorldBounds(data.pos1()
                                                         .get()) || !level.isInWorldBounds(data.pos2()
                                                                                               .get()) || Math.abs(
            data.pos1()
                .get()
                .getX() - data.pos2()
                              .get()
                              .getX()) > RANGE || Math.abs(data.pos1()
                                                               .get()
                                                               .getY() - data.pos2()
                                                                             .get()
                                                                             .getY()) > RANGE || Math.abs(data.pos1()
                                                                                                              .get()
                                                                                                              .getZ() -
                                                                                                          data.pos2()
                                                                                                              .get()
                                                                                                              .getZ()) >
                                                                                                 RANGE) return;
        var pos1 = data.pos1()
                       .get();
        var pos2 = data.pos2()
                       .get();
        for (BlockPos pos : BlockPos.betweenClosed(pos1, pos2)) {
            var centerPos = pos.getCenter();
            level.addAlwaysVisibleParticle(
                PastelParticleTypes.SHIMMERSTONE_SPARKLE, centerPos.x, centerPos.y, centerPos.z, 0, 0, 0);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);
        if (Ench.hasEnchantment(level.registryAccess(), PastelEnchantments.RESONANCE, stack)) {
            return InteractionResultHolder.pass(stack);
        } else {
            var data = stack.getOrDefault(PastelDataComponentTypes.FLOWING_STAFF, FlowingStaffComponent.DEFAULT);
            var pos1 = data.pos1();
            var pos2 = data.pos2();
            // rclick: do checks, then start filling
            if (pos1.isEmpty() || pos2.isEmpty() || !level.isInWorldBounds(pos1.get()) || !level.isInWorldBounds(
                pos2.get()) || Math.abs(pos1.get()
                                            .getX() - pos2.get()
                                                          .getX()) > RANGE || Math.abs(pos1.get()
                                                                                           .getY() - pos2.get()
                                                                                                         .getY()) >
                                                                              RANGE || Math.abs(pos1.get()
                                                                                                    .getZ() - pos2.get()
                                                                                                                  .getZ()) >
                                                                                       RANGE) {
                level.playSound(null, player.blockPosition(), PastelSounds.USE_FAIL, SoundSource.PLAYERS, 1.0f, 1.0f);
                return InteractionResultHolder.consume(stack);
            }
            blockPosCache.clear();
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(stack);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration % TICKS_PER_ACTION != 0) return;
        // we've already done the preliminary checks, but we should make sure the player is still nearby and the data
        // hasn't been altered untowardly
        var data = stack.getOrDefault(PastelDataComponentTypes.FLOWING_STAFF, FlowingStaffComponent.DEFAULT);
        if (data.pos1()
                .isEmpty() || data.pos2()
                                  .isEmpty() || !(livingEntity instanceof Player player)) {
            livingEntity.stopUsingItem();
            return;
        }
        var pos1 = data.pos1()
                       .get();
        var pos2 = data.pos2()
                       .get();
        if (blockPosCache.size() >= BlockPos.betweenClosedStream(pos1, pos2)
                                            .toList()
                                            .size()) {
            player.stopUsingItem();
            return;
        }
        var otherHandStack = player.getItemInHand(player.getItemInHand(InteractionHand.MAIN_HAND)
                                                        .equals(stack) ? InteractionHand.OFF_HAND
                                                                       : InteractionHand.MAIN_HAND);
        var cost = INK_COST_PER_BLOCK;
        // because of this we know they have at least one block to place
        BlockItem itemToPlace = null;
        ItemProvider itemProvider = otherHandStack.getCapability(ItemProvider.CAPABILITY);
        if (itemProvider != null) {
            List<BlockItem> containedItems = new ArrayList<>();
            itemProvider.getContainedItems(player, otherHandStack)
                        .forEach((item) -> {
                            if (item instanceof BlockItem blockItem) containedItems.add(blockItem);
                        });
            if (!containedItems.isEmpty()) itemToPlace = containedItems.get(player.getRandom()
                                                                                  .nextInt(0, containedItems.size()));
        } else if (otherHandStack.getItem() instanceof BlockItem blockItem) {
            itemToPlace = blockItem;
        }
        if (itemToPlace == null) {
            cost += ADDITIONAL_INK_COST_PER_BLOCK_CONJURED;
            itemToPlace = (BlockItem) Blocks.COBBLESTONE.asItem();
        }
        // more than twice the max area away - what are you doing
        if (pos1.distToCenterSqr(player.position()) > 3 * RANGE * RANGE) {
            player.stopUsingItem();
            return;
        }
        if (!(InkPowered.getAvailableInk(player, USED_COLOR) > cost)) {
            player.stopUsingItem();
            return;
        }
        // select a pos to check...
        BlockPos pos;
        do {
            pos = BlockPos.randomBetweenClosed(
                              level.getRandom(), 1, Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(),
                                                                                                 pos2.getY()),
                              Math.min(pos1.getZ(), pos2.getZ()), Math.max(pos1.getX(), pos2.getX()),
                              Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ())
                          )
                          .iterator()
                          .next();
            if (blockPosCache.contains(pos)) pos = null;

            if (pos != null && !level.getBlockState(pos)
                                     .canBeReplaced()) {
                blockPosCache.add(pos);
                pos = null;
            }
            if (blockPosCache.size() >= BlockPos.betweenClosedStream(pos1, pos2)
                                                .toList()
                                                .size()) {
                player.stopUsingItem();
                return;
            }
        } while (pos == null);

        blockPosCache.add(pos);
        // ok, so we've now run a whole battery of checks, we know that they have an item to place, a pos to place it at
        // and aren't trying anything funny. time to actually do stuff
        level.playSound(
            null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.PLAYERS, 1.0f, 1.0f);
        if (!level.isClientSide()) {
            if (InkPowered.tryDrainEnergy(player, USED_COLOR, cost) &&
                (cost != INK_COST_PER_BLOCK || InventoryHelper.removeFromInventoryWithRemainders(
                    player, new ItemStack(itemToPlace, 1)))) {
                level.setBlock(
                    pos, itemToPlace.getBlock()
                                    .defaultBlockState(), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS
                );
            } else {
                player.stopUsingItem();
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(PastelEnchantments.RESONANCE);
    }
}
