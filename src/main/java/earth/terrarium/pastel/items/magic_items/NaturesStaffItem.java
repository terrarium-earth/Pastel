package earth.terrarium.pastel.items.magic_items;

import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.interaction.NaturesStaffTriggered;
import earth.terrarium.pastel.blocks.TallCropBlock;
import earth.terrarium.pastel.blocks.deeper_down.flora.DoomBloomBlock;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.data_loaders.NaturesStaffConversionDataLoader;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.progression.PastelCriteria;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import earth.terrarium.pastel.sound.NaturesStaffUseSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static earth.terrarium.pastel.blocks.TallCropBlock.HALF;

public class NaturesStaffItem extends Item implements InkPowered {

    public static final ItemStack ITEM_COST = new ItemStack(PastelItems.VEGETAL.get(), 1);
    public static final InkCost INK_COST = new InkCost(InkColors.LIME, 20);

    public NaturesStaffItem(Properties settings) {
        super(settings);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        int efficiencyLevel = Ench.getLevel(context.registries(), Enchantments.EFFICIENCY, stack);
        if (efficiencyLevel == 0) {
            if (InkPowered.canUseClient()) {
                tooltip.add(Component.translatable(
                    "item.pastel.natures_staff.tooltip_with_ink", INK_COST.color()
                                                                          .getColoredInkName()
                ));
            } else {
                tooltip.add(Component.translatable("item.pastel.natures_staff.tooltip"));
            }
        } else {
            int chancePercent = (int) (getInkCostMod(context.registries(), stack) * 100);
            if (InkPowered.canUseClient()) {
                tooltip.add(Component.translatable(
                    "item.pastel.natures_staff.tooltip_with_ink_and_chance", INK_COST.color()
                                                                                     .getColoredInkName(), chancePercent
                ));
            } else {
                tooltip.add(Component.translatable("item.pastel.natures_staff.tooltip_with_chance", chancePercent));
            }
        }

        tooltip.add(Component.translatable("item.pastel.natures_staff.tooltip_lure"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (canUse(user)) {
            if (world.isClientSide) {
                startSoundInstance(user);
            }
            ItemUtils.startUsingInstantly(world, user, hand);
        }
        return super.use(world, user, hand);
    }

    @OnlyIn(Dist.CLIENT)
    public void startSoundInstance(Player user) {
        Minecraft.getInstance()
                 .getSoundManager()
                 .play(new NaturesStaffUseSoundInstance(user));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 20000;
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        // trigger the item's usage action every x ticks
        if (remainingUseTicks % 10 != 0) {
            return;
        }
        if (!(user instanceof Player player)) {
            user.releaseUsingItem();
            return;
        }
        if (!canUse(player)) {
            user.releaseUsingItem();
        }

        if (!world.isClientSide) {
            HitResult hitResult = Support.playerBlockInteractionRaycast(world, user, player);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                useOn(new UseOnContext(
                    world, player, player.getUsedItemHand(), player.getItemInHand(player.getUsedItemHand()),
                    (BlockHitResult) hitResult
                ));
            }
        }
    }

    public float getInkCostMod(HolderLookup.Provider lookup, ItemStack itemStack) {
        return 3.0F / (3.0F + Ench.getLevel(lookup, Enchantments.EFFICIENCY, itemStack));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();

        Player user = context.getPlayer();
        if (user == null) {
            return InteractionResult.FAIL;
        }

        if (world.isClientSide) {
            if (canUse(user)) {
                return InteractionResult.PASS;
            } else {
                playDenySound(world, user);
                return InteractionResult.FAIL;
            }
        }

        if (user.getTicksUsingItem() < 2) {
            return InteractionResult.PASS;
        }

        if (user instanceof ServerPlayer player) {
            ItemStack stack = context.getItemInHand();
            BlockPos blockPos = context.getClickedPos();

            if (!GenericClaimModsCompat.canInteract(world, blockPos, user)) {
                playDenySound(world, context.getPlayer());
                return InteractionResult.FAIL;
            }

            if (user.getTicksUsingItem() % 10 == 0) {
                spawnParticlesAndEffect(world, context.getClickedPos());

                boolean success = false;
                BlockState sourceState = world.getBlockState(blockPos);

                if (sourceState.getBlock() instanceof NaturesStaffTriggered naturesStaffTriggered &&
                    naturesStaffTriggered.canUseNaturesStaff(world, blockPos, sourceState)) {
                    if (naturesStaffTriggered.onNaturesStaffUse(world, blockPos, sourceState, player)) {
                        success = true;
                    }
                } else {
                    // loaded as convertible? => convert
                    BlockState destinationState = NaturesStaffConversionDataLoader.getConvertedBlockState(
                        sourceState.getBlock());
                    if (destinationState != null) {
                        if (destinationState.getBlock() instanceof SimpleWaterloggedBlock) {
                            if (touchesWater(world, blockPos)) {
                                destinationState = destinationState.setValue(CoralPlantBlock.WATERLOGGED, true);
                            } else {
                                destinationState = destinationState.setValue(CoralPlantBlock.WATERLOGGED, false);
                            }
                        }
                        world.setBlock(blockPos, destinationState, 3);

                        payForUse(player, stack);
                        success = true;
                    } else if (sourceState.is(PastelBlockTags.NATURES_STAFF_STACKABLE)) {
                        // blockstate marked as stackable => stack more on top!
                        int i = 0;
                        BlockState state;
                        do {
                            state = world.getBlockState(context.getClickedPos()
                                                               .above(i));
                            i++;
                        } while (state.is(sourceState.getBlock()));

                        BlockPos targetPos = context.getClickedPos()
                                                    .above(i - 1);
                        if (tryPlaceBlock(sourceState, world, targetPos, Direction.DOWN, Direction.UP)) {
                            success = true;
                        }
                    } else if (sourceState.is(PastelBlockTags.NATURES_STAFF_SPREADABLE)) {
                        RandomSource random = world.getRandom();

                        for (int i = 0; i < 5; i++) {
                            BlockPos randomOffsetPos = blockPos.offset(
                                random.nextIntBetweenInclusive(-3, 3), random.nextIntBetweenInclusive(-3, 3),
                                random.nextIntBetweenInclusive(-3, 3)
                            );
                            if (tryPlaceBlock(
                                sourceState, world, randomOffsetPos, Direction.getRandom(random),
                                Direction.getRandom(random)
                            )) {
                                success = true;
                                break;
                            }
                        }
                    } else if (sourceState.isRandomlyTicking() && sourceState.is(
                        PastelBlockTags.NATURES_STAFF_TICKABLE)) {
                        // random tickable and whitelisted? => tick
                        // without whitelist we would be able to tick budding blocks, ...

                        if (world instanceof ServerLevel) {
                            sourceState.randomTick((ServerLevel) world, blockPos, world.random);
                        }
                        success = true;
                    } else if (world instanceof ServerLevel level && tryHarvest(
                        sourceState, blockPos, player, level, new BlockHitResult(
                            context.getClickLocation(), context.getClickedFace(), blockPos, context.isInside()),
                        context.getItemInHand()
                    )) {
                        success = true;
                    } else if (BoneMealItem.growCrop(Items.BONE_MEAL.getDefaultInstance(), world, blockPos)) {
                        // fertilizable => grow!
                        success = true;
                    } else {
                        if (sourceState.isFaceSturdy(world, blockPos, context.getClickedFace()) &&
                            BoneMealItem.growWaterPlant(
                                Items.BONE_MEAL.getDefaultInstance(), world,
                                blockPos.relative(context.getClickedFace()),
                                context.getClickedFace()
                            )) {
                            success = true;
                        }
                    }
                }

                if (success) {
                    payForUse(player, stack);
                    PastelCriteria.NATURES_STAFF_USE.trigger(player, sourceState, world.getBlockState(blockPos));
                    return InteractionResult.CONSUME;
                }
            }

        }

        return InteractionResult.PASS;
    }

    private boolean tryHarvest(
        BlockState state, BlockPos pos, ServerPlayer player, ServerLevel level, BlockHitResult hitResult,
        ItemStack naturesStaff
    ) {
        boolean growable = false;
        Block block = state.getBlock();
        int tall = 0;

        // handle amaranth and such
        if (block instanceof TallCropBlock) {
            tall = 1;
            if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
                tall = 2;
                pos = pos.below();
                state = level.getBlockState(pos);
                hitResult = new BlockHitResult(
                    hitResult.getLocation()
                             .relative(Direction.DOWN, 1), hitResult.getDirection(), pos, hitResult.isInside()
                );
                block = state.getBlock();
            }
        }

        // nether wart isn't bonemealable, but it acts like it is, so we special case it
        if (state.is(Blocks.NETHER_WART)) {
            growable = state.getValue(NetherWartBlock.AGE) < 3;
        } else if (block instanceof BonemealableBlock bonemealableBlock) {
            growable = bonemealableBlock.isValidBonemealTarget(level, pos, state);
        }

        // if the block isn't valid, or it still has room to grow, or we're on the client somehow, we shouldn't do 
        // anything. also, some blocks are bonemealable, but aren't actually crops, so we don't do 
        // anything to them here
        if (level.isClientSide() || growable || !state.is(PastelBlockTags.NATURES_STAFF_HARVEST_WHITELIST))
            return false;


        // this allows us to benefit from fortune
        var drops = state.getDrops(new LootParams.Builder(level).withParameter(
                                                                    LootContextParams.ORIGIN, new Vec3(
                                                                        pos.getX(),
                                                                        pos.getY(),
                                                                        pos.getZ()
                                                                    )
                                                                )
                                                                .withParameter(LootContextParams.BLOCK_STATE, state)
                                                                .withParameter(LootContextParams.THIS_ENTITY, player)
                                                                .withParameter(LootContextParams.TOOL, naturesStaff));

        BlockState restoreTo = block.defaultBlockState();
        // preserve the direction of orientable blocks
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            restoreTo = restoreTo.setValue(
                BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
        }

        // vanilla crops like wheat use this state instead
        if (state.hasProperty(BlockStateProperties.AGE_7)) {
            restoreTo = state.setValue(BlockStateProperties.AGE_7, 0);
        }

        level.setBlockAndUpdate(pos, restoreTo);
        if (tall > 0)
            level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
        ItemStack seeds = state.getCloneItemStack(hitResult, level, pos, player);
        for (ItemStack drop : drops) {
            if (drop.is(seeds.getItem())) drop.shrink(1);
            if (drop.isEmpty()) continue;
            Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), drop);
        }

        var enchantments = level.registryAccess()
                                .lookup(Registries.ENCHANTMENT);
        if (enchantments.isPresent()) {
            var silk = enchantments.get()
                                   .get(Enchantments.SILK_TOUCH);
            if (silk.isPresent() && block instanceof DoomBloomBlock && naturesStaff.getEnchantmentLevel(Holder.direct(
                silk.get()
                    .value())) < 1) {
                DoomBloomBlock.explode(level, pos, state);
            }
        }

        return true;
    }

    private boolean tryPlaceBlock(BlockState blockState, Level world, BlockPos pos, Direction facing, Direction side) {
        BlockState targetState = blockState.getBlock()
                                           .getStateForPlacement(
                                               new DirectionalPlaceContext(world, pos, facing, ItemStack.EMPTY, side));
        if (targetState != null && world.getBlockState(pos)
                                        .canBeReplaced() && !world.isOutsideBuildHeight(pos.getY()) &&
            targetState.canSurvive(world, pos)) {
            world.setBlockAndUpdate(pos, targetState);

            world.levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(targetState));
            world.playSound(
                null, pos, targetState.getSoundType()
                                      .getPlaceSound(), SoundSource.PLAYERS, 1.0F, 0.9F + world.getRandom()
                                                                                               .nextFloat() * 0.2F
            );
            world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 15);
            return true;
        }
        return false;
    }

    private static boolean touchesWater(Level world, BlockPos blockPos) {
        return world.getFluidState(blockPos.north())
                    .is(FluidTags.WATER) || world.getFluidState(blockPos.east())
                                                 .is(FluidTags.WATER) || world.getFluidState(blockPos.south())
                                                                              .is(FluidTags.WATER) ||
               world.getFluidState(blockPos.west())
                    .is(FluidTags.WATER);
    }

    private static void spawnParticlesAndEffect(Level world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(PastelBlockTags.NATURES_STAFF_STACKABLE)) {
            int i = 0;
            while (world.getBlockState(blockPos.above(i))
                        .is(blockState.getBlock())) {
                world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos.above(i), 15);
                i++;
            }
            world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 15);
            BoneMealItem.addGrowthParticles(world, blockPos.above(i + 1), 5);
            for (
                int j = 1; world.getBlockState(blockPos.below(j))
                                .is(blockState.getBlock()); j++
            ) {
                world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos.below(j), 15);
            }
        } else {
            world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 15);
        }
    }

    private boolean payForUse(Player player, ItemStack stack) {
        boolean paid = player.isCreative(); // free for creative players
        if (!paid) { // try pay with ink
            paid = InkPowered.tryDrainEnergy(
                player, INK_COST, getInkCostMod(
                    player.level()
                          .registryAccess(), stack
                )
            );
        }
        if (!paid && player.getInventory()
                           .contains(ITEM_COST)) {  // try pay with item
            int efficiencyLevel = Ench.getLevel(
                player.level()
                      .registryAccess(), Enchantments.EFFICIENCY, stack
            );
            if (efficiencyLevel == 0) {
                paid = InventoryHelper.removeFromInventoryWithRemainders(player, ITEM_COST);
            } else {
                paid = player.getRandom()
                             .nextFloat() > (2.0 / (2 + efficiencyLevel)) ||
                       InventoryHelper.removeFromInventoryWithRemainders(player, ITEM_COST);
            }
        }
        return paid;
    }

    private static boolean canUse(Player player) {
        return player.isCreative() || InkPowered.hasAvailableInk(player, INK_COST) || player.getInventory()
                                                                                            .contains(ITEM_COST);
    }

    private void playDenySound(@NotNull Level world, @NotNull Player playerEntity) {
        world.playSound(
            null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), PastelSounds.USE_FAIL,
            SoundSource.PLAYERS, 1.0F, 0.8F + playerEntity.getRandom()
                                                          .nextFloat() * 0.4F
        );
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(INK_COST.color());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.EFFICIENCY) ||
               enchantment.is(Enchantments.FORTUNE) || enchantment.is(Enchantments.SILK_TOUCH);
    }

}
