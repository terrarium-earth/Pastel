package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.blocks.PrimordialFireBlock;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class KindlingCoughEntity extends Projectile {

    protected static final float DAMAGE = 10.0F;
    protected static final int FIRE_TICKS_ON_HIT = 30;

    public KindlingCoughEntity(EntityType<? extends KindlingCoughEntity> entityType, Level world) {
        super(entityType, world);
    }

    public KindlingCoughEntity(Level world, LivingEntity owner) {
        this(PastelEntityTypes.KINDLING_COUGH.get(), world);
        this.setOwner(owner);
        this.setPos(
            owner.getX() - (owner.getBbWidth() + 1.0F) * 0.5 * Mth.sin(owner.yBodyRot * 0.017453292F),
            owner.getEyeY() - 0.1,
            owner.getZ() + (owner.getBbWidth() + 1.0F) * 0.5 * (double) Mth.cos(owner.yBodyRot * 0.017453292F)
        );
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 vec3d = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.onHit(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        if (this.level()
                .getBlockStates(this.getBoundingBox())
                .noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3d.scale(0.99));
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement()
                                          .add(0.0, -0.06, 0.0));
            }

            this.setPos(d, e, f);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        Entity hitEntity = entityHitResult.getEntity();
        if (hitEntity instanceof LivingEntity livingEntity) {
            PrimordialFireData.addPrimordialFireTicks(livingEntity, FIRE_TICKS_ON_HIT);
        } else {
            hitEntity.setRemainingFireTicks(FIRE_TICKS_ON_HIT);
        }

        if (this.getOwner() instanceof LivingEntity owner) {
            hitEntity.hurt(PastelDamageTypes.kindlingCough(this.level(), owner), DAMAGE);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);

        if (!this.level()
                 .isClientSide()) {
            PrimordialFireBlock.tryPlacePrimordialFire(
                this.level(), blockHitResult.getBlockPos()
                                            .relative(blockHitResult.getDirection()), blockHitResult.getDirection()
            );
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double velX = packet.getXa();
        double velY = packet.getYa();
        double velZ = packet.getZa();

        for (int i = 0; i < 7; ++i) {
            double g = 0.4 + 0.1 * (double) i;
            this.level()
                .addParticle(
                    PastelParticleTypes.PRIMORDIAL_FLAME, this.getX(), this.getY(), this.getZ(), velX * g, velY,
                    velZ * g
                );
        }

        this.setDeltaMovement(velX, velY, velZ);
    }

}
