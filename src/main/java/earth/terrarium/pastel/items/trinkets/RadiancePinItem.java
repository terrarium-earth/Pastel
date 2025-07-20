package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class RadiancePinItem extends PastelTrinketItem {

    public static final int CHECK_EVERY_X_TICKS = 20;
    public static final int MAX_LIGHT_LEVEL = 7;
    public static final BlockState LIGHT_BLOCK_STATE = PastelBlocks.DECAYING_LIGHT_BLOCK.get()
                                                                                        .defaultBlockState()
                                                                                        .setValue(LightBlock.LEVEL, 15);
    public static final BlockState LIGHT_BLOCK_STATE_WATER = PastelBlocks.DECAYING_LIGHT_BLOCK.get()
                                                                                              .defaultBlockState()
                                                                                              .setValue(
                                                                                                  LightBlock.LEVEL, 15)
                                                                                              .setValue(
                                                                                                  LightBlock.WATERLOGGED,
                                                                                                  true
                                                                                              );

    public RadiancePinItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/radiance_pin"));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.radiance_pin.tooltip")
                             .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        LivingEntity entity = slotContext.entity();
        Level world = entity.level();
        if (!world.isClientSide && world.getGameTime() % CHECK_EVERY_X_TICKS == 0) {
            if (entity instanceof Player playerEntity && playerEntity.isSpectator()) {
                return;
            }
            BlockPos pos = entity.blockPosition();
            if (!GenericClaimModsCompat.canPlaceBlock(world, pos, entity)) {
                return;
            }

            if (!world.isOutsideBuildHeight(pos) && world.getMaxLocalRawBrightness(pos) <= MAX_LIGHT_LEVEL) {
                BlockState currentState = world.getBlockState(pos);
                boolean placed = false;
                if (currentState.isAir()) {
                    world.setBlock(pos, LIGHT_BLOCK_STATE, 3);
                    placed = true;
                } else if (currentState.equals(Blocks.WATER.defaultBlockState())) {
                    world.setBlock(pos, LIGHT_BLOCK_STATE_WATER, 3);
                    placed = true;
                } else if (currentState.is(PastelBlocks.DECAYING_LIGHT_BLOCK.get())) {
                    if (currentState.getValue(LightBlock.WATERLOGGED)) {
                        world.setBlock(pos, LIGHT_BLOCK_STATE_WATER, 3);
                    } else {
                        world.setBlock(pos, LIGHT_BLOCK_STATE, 3);
                    }
                    placed = true;
                }
                if (placed) {
                    sendSmallLightCreatedParticle((ServerLevel) world, pos);
                    world.playSound(
                        null, entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ() + 0.5,
                        PastelSoundEvents.RADIANCE_STAFF_PLACE, SoundSource.PLAYERS, 0.08F,
                        0.9F + world.random.nextFloat() * 0.2F
                    );
                }
            }
        }
    }

    public static void sendSmallLightCreatedParticle(ServerLevel world, BlockPos blockPos) {
        PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
            world, Vec3.atCenterOf(blockPos),
            PastelParticleTypes.SHIMMERSTONE_SPARKLE,
            4,
            Vec3.ZERO,
            new Vec3(0.1, 0.1, 0.1)
        );
    }

}
