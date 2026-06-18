package earth.terrarium.pastel.items.magic_items;

import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.helpers.level.BuildingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import oshi.util.tuples.Triplet;

import java.util.List;

public class ConstructorsStaffItem extends BuildingStaffItem {

    public static final int INK_COST_PER_BLOCK = 1;

    public static final int CREATIVE_RANGE = 10;

    public ConstructorsStaffItem(Properties settings) {
        super(settings);
    }

    public static int getRange(Player playerEntity) {
        if (playerEntity == null || playerEntity.isCreative()) {
            return CREATIVE_RANGE;
        } else {
            return 10;
        }
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip
            .add(
                Component
                    .translatable(
                        "item.pastel.constructors_staff.tooltip.range",
                        getRange(Minecraft.getInstance().player)
                    )
                    .withStyle(ChatFormatting.GRAY)
            );
        tooltip
            .add(
                Component.translatable("item.pastel.constructors_staff.tooltip.crouch").withStyle(ChatFormatting.GRAY)
            );
        addInkPoweredTooltip(tooltip);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState targetBlockState = world.getBlockState(pos);

        if ((player != null && this
            .canInteractWith(targetBlockState, context.getLevel(), context.getClickedPos(), context.getPlayer()))) {
            Block blockToPlace = targetBlockState.getBlock();
            Item itemToConsume;

            long count;
            if (player.isCreative()) {
                itemToConsume = blockToPlace.asItem();
                count = Integer.MAX_VALUE;
            } else {
                Triplet<Block, Item, Integer> replaceData = countSuitableReplacementItems(
                    player,
                    blockToPlace,
                    false,
                    INK_COST_PER_BLOCK
                );
                blockToPlace = replaceData.getA();
                itemToConsume = replaceData.getB();
                count = replaceData.getC();
            }

            if (count > 0) {
                Direction side = context.getClickedFace();
                int maxRange = getRange(player);
                int range = (int) Math.min(maxRange, player.isCreative() ? maxRange : count);
                boolean sneaking = player.isShiftKeyDown();
                List<BlockPos> targetPositions = BuildingHelper
                    .calculateBuildingStaffSelection(world, pos, side, count, range, !sneaking);
                if (targetPositions.isEmpty()) {
                    return InteractionResult.FAIL;
                }

                if (!world.isClientSide) {
                    placeBlocksAndDecrementInventory(player, world, blockToPlace, itemToConsume, side, targetPositions);
                }

                return InteractionResult.SUCCESS;
            }
        } else {
            if (player != null) {
                world
                    .playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.DISPENSER_FAIL,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                    );
            }
        }

        return InteractionResult.FAIL;
    }

    protected static void placeBlocksAndDecrementInventory(
        Player player,
        Level world,
        Block blockToPlace,
        Item itemToConsume,
        Direction side,
        List<BlockPos> targetPositions
    ) {
        int placedBlocks = 0;
        for (
            BlockPos position : targetPositions
        ) {
            // Only place blocks where you are allowed to do so
            if (!GenericClaimModsCompat.canPlaceBlock(world, position, player))
                continue;

            BlockState originalState = world.getBlockState(position);
            if (originalState.isAir() || originalState.getBlock() instanceof LiquidBlock || (originalState
                .canBeReplaced() && originalState.getCollisionShape(world, position).isEmpty())) {
                BlockState stateToPlace = blockToPlace
                    .getStateForPlacement(
                        new BuildingStaffPlacementContext(
                            world,
                            player,
                            new BlockHitResult(Vec3.atBottomCenterOf(position), side, position, false)
                        )
                    );
                if (stateToPlace != null && stateToPlace.canSurvive(world, position)) {
                    if (world.setBlockAndUpdate(position, stateToPlace)) {
                        if (placedBlocks == 0) {
                            world
                                .playSound(
                                    null,
                                    player.blockPosition(),
                                    stateToPlace.getSoundType().getPlaceSound(),
                                    SoundSource.PLAYERS,
                                    stateToPlace.getSoundType().getVolume(),
                                    stateToPlace.getSoundType().getPitch()
                                );
                        }
                        placedBlocks++;
                    }
                }
            }
        }

        if (!player.isCreative()) {
            InventoryHelper.removeFromInventoryWithRemainders(player, new ItemStack(itemToConsume, placedBlocks));
            InkPowered
                .tryDrainEnergy(
                    player,
                    USED_COLOR,
                    (long) targetPositions.size() * ConstructorsStaffItem.INK_COST_PER_BLOCK
                );
        }
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(USED_COLOR);
    }

}
