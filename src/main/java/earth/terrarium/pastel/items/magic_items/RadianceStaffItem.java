package earth.terrarium.pastel.items.magic_items;

import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class RadianceStaffItem extends Item implements InkPowered {

    public static final int USE_DURATION = 20;
    public static final int REACH_STEP_DISTANCE = 4;
    public static final int MAX_REACH_STEPS = 8;
    public static final int PLACEMENT_TRIES_PER_STEP = 4;
    public static final int MIN_LIGHT_LEVEL = 10;

    public static final InkCost INK_COST = new InkCost(InkColors.YELLOW, 10);
    public static final ItemStack COST = new ItemStack(PastelItems.SHIMMERSTONE_GEM.get(), 1);

    public RadianceStaffItem(Properties settings) {
        super(settings);
    }

    public static boolean placeLight(Level world, BlockPos targetPos, ServerPlayer playerEntity) {
        if (!GenericClaimModsCompat.canPlaceBlock(world, targetPos, playerEntity)) {
            return false;
        }

        BlockState targetBlockState = world.getBlockState(targetPos);
        if (targetBlockState.isAir()) {
            if (playerEntity.isCreative() || InkPowered.tryDrainEnergy(playerEntity, INK_COST) ||
                InventoryHelper.removeFromInventoryWithRemainders(playerEntity, COST)) {
                world.setBlock(
                    targetPos, PastelBlocks.SHIMMERSTONE_LIGHT.get()
                                                            .defaultBlockState(), 3
                );
                return true;
            }
        } else if (targetBlockState.is(Blocks.WATER)) {
            if (playerEntity.isCreative() || InkPowered.tryDrainEnergy(playerEntity, INK_COST) ||
                InventoryHelper.removeFromInventoryWithRemainders(playerEntity, COST)) {
                world.setBlock(
                    targetPos, PastelBlocks.SHIMMERSTONE_LIGHT.get()
                                                            .defaultBlockState()
                                                            .setValue(WATERLOGGED, true), 3
                );
                return true;
            }
        }
        return false;
    }

    public static void playParticles(
        Level world, BlockPos targetPos, ServerPlayer playerEntity, int useTimes, int iteration) {
        float pitch;
        if (useTimes % 2 == 0) { // high ding <=> deep ding
            pitch = Math.min(1.35F, 0.7F + 0.1F * useTimes);
        } else {
            pitch = Math.min(1.5F, 0.7F + 0.1F * useTimes);
        }
        PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
            (ServerLevel) world, Vec3.atCenterOf(targetPos), PastelParticleTypes.SHIMMERSTONE_SPARKLE, 20, Vec3.ZERO,
            new Vec3(0.3, 0.3, 0.3)
        );
    }

    public static void playDenySound(Level world, Player playerEntity) {
        world.playSound(
            null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), PastelSounds.USE_FAIL,
            SoundSource.PLAYERS, 1.0F, 0.8F + playerEntity.getRandom()
                                                          .nextFloat() * 0.4F
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        if (InkPowered.canUseClient()) {
            tooltip.add(Component.translatable(
                "item.pastel.radiance_staff.tooltip.ink", INK_COST.color()
                                                                  .getColoredInkName()
            ));
        } else {
            tooltip.add(Component.translatable("item.pastel.radiance_staff.tooltip"));
        }
        tooltip.add(Component.translatable("item.pastel.radiance_staff.tooltip2"));
        tooltip.add(Component.translatable("item.pastel.radiance_staff.tooltip3"));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        if (!level.isClientSide) {

            level.playSound(
                null, user.getX(), user.getY(), user.getZ(), PastelSounds.RADIANCE_STAFF_CHARGING,
                SoundSource.PLAYERS, 1.0F, Support.varFloatCentered(level.random, 0.1F)
            );
        }
        return ItemUtils.startUsingInstantly(level, user, hand);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        // trigger the items' usage action every x ticks
        if (user instanceof ServerPlayer serverPlayerEntity && user.getTicksUsingItem() > USE_DURATION &&
            user.getTicksUsingItem() % USE_DURATION == 0) {
            usage(world, serverPlayerEntity);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        Player user = context.getPlayer();
        if (user == null) {
            return InteractionResult.PASS;
        }

        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();

        if (!level.getBlockState(pos)
                  .is(PastelBlocks.SHIMMERSTONE_LIGHT.get())) { // those get destroyed instead
            BlockPos targetPos = pos.relative(direction);
            if (placeLight(level, targetPos, (ServerPlayer) user)) {
                RadianceStaffItem.playParticles(
                    level, targetPos, (ServerPlayer) user, level.random.nextInt(5), level.random.nextInt(5));

                playPlaceSound(level, user, Support.varFloatCentered(level.random, 0.2F),
                               Support.varFloatCentered(level.random, 0.125F));
            } else {
                RadianceStaffItem.playDenySound(level, user);
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    public void usage(Level level, ServerPlayer user) {
        int useTimes = (user.getTicksUsingItem() / USE_DURATION);
        int maxCheckDistance = Math.min(MAX_REACH_STEPS, useTimes);

        BlockPos sourcePos = user.blockPosition();
        Vec3 cameraVec = user.getViewVector(0);

        int successes = 0;
        for (int iteration = 1; iteration < maxCheckDistance; iteration++) {
            BlockPos targetPos = sourcePos.offset(
                Mth.floor(cameraVec.x * iteration * REACH_STEP_DISTANCE),
                Mth.floor(cameraVec.y * iteration * REACH_STEP_DISTANCE),
                Mth.floor(cameraVec.z * iteration * REACH_STEP_DISTANCE)
            );

            for (int tries = 0; tries < PLACEMENT_TRIES_PER_STEP; tries++) {
                targetPos = targetPos.offset(
                    iteration - level.getRandom()
                                     .nextInt(2 * iteration),
                    iteration - level.getRandom()
                                     .nextInt(2 * iteration),
                    iteration - level.getRandom()
                                     .nextInt(2 * iteration)
                );

                if (level.getBrightness(LightLayer.BLOCK, targetPos) < MIN_LIGHT_LEVEL) {
                    if (placeLight(level, targetPos, user)) {
                        playParticles(level, targetPos, user, useTimes, iteration);
                        successes++;
                        break;
                    }
                }
            }
        }

        float pitch = level.random.nextFloat() * 0.09F  + successes * 0.1F;
        float vol = (float) Math.max(0.75, successes * 0.1F + 0.25F);
        playPlaceSound(level, user, vol, 1F + pitch);
        playPlaceSound(level, user, vol, 1F - pitch);
    }

    private static void playPlaceSound(Level level, Player user, float vol, float pitch) {
        level.playSound(
            null, user,
            PastelSounds.RADIANCE_STAFF_PLACE, SoundSource.PLAYERS,
            vol, pitch
        );
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(INK_COST.color());
    }
}
