package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.attachments.data.HookshotData;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;


public class WireHookEntity extends Projectile {

    public static final float STIFFNESS = 0.5F;
    public static final int DEFAULT_MAX_RANGE = 48;

    protected static final EntityDataAccessor<Boolean> HOOKED = SynchedEntityData.defineId(
        WireHookEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> RECALLED = SynchedEntityData.defineId(
        WireHookEntity.class, EntityDataSerializers.BOOLEAN);

    private float neutral = -1F;
    private boolean grabbed;

    public WireHookEntity(EntityType<WireHookEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WireHookEntity(Player shooter, double x, double y, double z, Level level) {
        this(PastelEntityTypes.WIRE_HOOK.get(), level);

        setPos(x, y, z);
        setOwner(shooter);
        HookshotData.get(shooter).setLinkedHook(getUUID());
    }

    @Override
    public void tick() {
        super.tick();

        if (!ensureUniqueness())
            return;

        processCollision();
        updateHookStatus();

        if (!(getOwner() instanceof Player player))
            return;

        if (isHooked()) {
            updateEquilibrium(player);
            springMove(player);
        }
        else {
            setOldPosAndRot();
            moveTo(position().add(getDeltaMovement()));
        }

        if (hasRecalled()) {
            var distance = distanceTo(player);

            if (distance < 1.75) {
                remove(RemovalReason.DISCARDED);
                return;
            }

            var returnVector = player.position().subtract(position()).normalize();
            setDeltaMovement(returnVector.scale(Math.min(Math.pow(distance / 3, 1.5), 4) + 0.5));
        }
    }

    private void updateHookStatus() {
        if (isHooked() != grabbed) {
            grabbed = isHooked();

            if (grabbed) {
                playSound(PastelSounds.METAL_HIT);

                if (getOwner() != null)
                    neutral = getOwner().distanceTo(this) * 0.5F;
            }
            else {
                playSound(PastelSounds.METAL_TAP);
            }
        }
    }

    private void updateEquilibrium(Player player) {
        if (player.isShiftKeyDown() && player.jumping) {
            return;
        }

        if (player.isShiftKeyDown()) {
            neutral += 0.2F;
        }
        else if(player.jumping) {
            neutral -= 0.2F;
        }
    }

    private void springMove(Player player) {
        var displacement = position().subtract(player.position()).multiply(1.5, 1, 1.5);

        if (displacement.y < 0)
            displacement = displacement.multiply(1, 0, 1);

        var rebound = STIFFNESS * (displacement.length() * 0.8F - neutral);
        var stabilization = 1 - Math.min(Math.abs(rebound), 1);

        var offset = player.position().multiply(1, 0, 1)
                           .distanceTo(position().multiply(1, 0, 1)) / 1.5;

        stabilization *= 1 - Math.clamp(offset, 0, 1);

        var vel = player.getDeltaMovement().multiply(1, 0, 1).length() * 4;

        stabilization *= 1 - Math.clamp(vel, 0, 1);

        rebound = Math.clamp(rebound * 4, 0, player.getGravity() * 2);
        player.addDeltaMovement(player.getDeltaMovement().multiply(0, stabilization * -0.4, 0));

        player.addDeltaMovement(displacement.normalize().scale(rebound));
    }

    private void processCollision() {
        var pos = position();
        var nextPos = pos.add(getDeltaMovement());
        var hit = level().clip(new ClipContext(pos, nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        if (hit.getType() != HitResult.Type.MISS)
            hitTargetOrDeflectSelf(hit);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (hasRecalled())
            return;

        setDeltaMovement(0, 0, 0);
        entityData.set(HOOKED, true);

        super.onHitBlock(result);
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    @Override
    public boolean canBeCollidedWith() {
        return isHooked();
    }

    private boolean ensureUniqueness() {
        if (level().isClientSide())
            return true;

        var player = (Player) getOwner();
        var data = HookshotData.get(player);
        if (data.getLinkedHook().map(id -> !id.equals(getUUID())).orElse(true)) {
            discard();
            return false;
        }
        return true;
    }

    public boolean isHooked() {
        return entityData.get(HOOKED);
    }

    public boolean hasRecalled() {
        return entityData.get(RECALLED);
    }

    public void recall() {
        entityData.set(RECALLED, true);
        entityData.set(HOOKED, false);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HOOKED, false);
        builder.define(RECALLED, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hooked", entityData.get(HOOKED));
        compound.putBoolean("propelled", entityData.get(RECALLED));
        compound.putFloat("neutral", neutral);
        compound.getBoolean("grabbed");
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(HOOKED, compound.getBoolean("hooked"));
        entityData.set(RECALLED, compound.getBoolean("propelled"));
        neutral = compound.getFloat("neutral");
        grabbed = compound.getBoolean("grabbed");
    }
}
