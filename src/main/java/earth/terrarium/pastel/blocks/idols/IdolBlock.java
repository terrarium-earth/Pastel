package earth.terrarium.pastel.blocks.idols;

import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class IdolBlock extends Block {

    public static final BooleanProperty COOLDOWN = BooleanProperty.create("cooldown");
    public final ParticleOptions particleEffect;

    public IdolBlock(Properties settings, ParticleOptions particleEffect) {
        super(settings);
        this.particleEffect = particleEffect;
        registerDefaultState(getStateDefinition().any()
                                                 .setValue(COOLDOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN);
    }

    @Override
    public void appendHoverText(
        ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("block.pastel.mob_block.tooltip")
                             .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResult useWithoutItem(
        BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide) {
            if (!hasCooldown(state) && trigger((ServerLevel) world, pos, state, player, hit.getDirection())) {
                playTriggerParticles((ServerLevel) world, pos);
                playTriggerSound(world, pos);
                triggerCooldown(world, pos);
            }
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        world.setBlockAndUpdate(
            pos, world.getBlockState(pos)
                      .setValue(COOLDOWN, false)
        );
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
        if (!world.isClientSide && !hasCooldown(state)) {
            if (trigger((ServerLevel) world, pos, state, entity, Direction.UP)) {
                playTriggerParticles((ServerLevel) world, pos);
                playTriggerSound(world, pos);
                triggerCooldown(world, pos);
            }
        }
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide) {
            BlockPos hitPos = hit.getBlockPos();
            if (!hasCooldown(state) && trigger(
                (ServerLevel) world, hitPos, state, projectile.getOwner(), hit.getDirection())) {
                playTriggerParticles((ServerLevel) world, hit.getBlockPos());
                playTriggerSound(world, hitPos);
                triggerCooldown(world, hitPos);
            }
        }
    }

    public abstract boolean trigger(
        ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side);

    public void playTriggerParticles(ServerLevel world, BlockPos blockPos) {
        PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
            world, new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.2, blockPos.getZ() + 0.5), particleEffect, 10,
            new Vec3(0.5, 0.5, 0.5), new Vec3(0.2, 0.08, 0.2)
        );
    }

    public void playTriggerSound(Level world, BlockPos blockPos) {
        world.playSound(null, blockPos, this.soundType.getPlaceSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public boolean hasCooldown(BlockState state) {
        return state.getValue(COOLDOWN);
    }

    public void triggerCooldown(Level world, BlockPos pos) {
        world.setBlockAndUpdate(
            pos, world.getBlockState(pos)
                      .setValue(COOLDOWN, true)
        );
        world.scheduleTick(pos, this, getCooldownTicks());
    }

    public int getCooldownTicks() {
        return 40;
    }

    public Vec3 getOutputLocation(BlockPos pos, Direction direction) {
        double d = pos.getX() + 0.7D * (double) direction.getStepX();
        double e = pos.getY() + 0.7D * (double) direction.getStepY();
        double f = pos.getZ() + 0.7D * (double) direction.getStepZ();
        return new Vec3(d, e, f);
    }

}
