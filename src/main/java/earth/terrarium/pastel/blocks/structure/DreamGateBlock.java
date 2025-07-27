package earth.terrarium.pastel.blocks.structure;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DreamGateBlock extends DikeGateBlock {

    public static final MapCodec<DreamGateBlock> CODEC = simpleCodec(DreamGateBlock::new);

    public DreamGateBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends DreamGateBlock> codec() {
        return CODEC;
    }

    @Override
    @Deprecated
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity instanceof LivingEntity livingEntity) {

                if (entity instanceof Player player && player.isCreative()) {
                    return Shapes.empty();
                }

                var sleep = SleepStatusEffect.getGeneralSleepResistanceIfEntityHasSoporificEffect(livingEntity);
                if (sleep != -1) {
                    if (entity instanceof ServerPlayer player) {
                        Support.grantAdvancementCriterion(
                            player, "lategame/enter_strange_preservation_ruin", "enter_dream_gate");
                    }

                    return Shapes.empty();
                }
            }
        }
        return Shapes.block();
    }

    @Override
    public void punishEntityWithoutAzureDike(BlockGetter world, BlockPos pos, Entity entity, boolean decreasedSounds) {
        if (world instanceof ServerLevel serverWorld && entity instanceof LivingEntity livingEntity) {

            if (livingEntity instanceof Player player && player.getAbilities().instabuild)
                return;

            var sleep = SleepStatusEffect.getGeneralSleepResistanceIfEntityHasSoporificEffect(livingEntity);
            if (sleep == -1 && serverWorld.getGameTime() % 5 == 0) {
                entity.hurt(PastelDamageTypes.sleep(serverWorld, null), 2);
                PlayParticleWithExactVelocityPayload.playParticles(
                    serverWorld, pos, PastelParticleTypes.AZURE_DIKE_RUNES, 10);
                if (entity instanceof ServerPlayer serverPlayerEntity &&
                    (!decreasedSounds || ((ServerLevel) world).getGameTime() % 10 == 0)) {
                    serverPlayerEntity.playNotifySound(PastelSounds.USE_FAIL, SoundSource.PLAYERS, 0.75F, 1.0F);
                }
            }
        }
    }
}
