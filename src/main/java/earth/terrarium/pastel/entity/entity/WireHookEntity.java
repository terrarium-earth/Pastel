package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.HookshotData;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WireHookEntity extends Projectile implements HookshotData.FrictionProvider {

    public static final float STIFFNESS = 0.5F;

    protected static final EntityDataAccessor<Boolean> HOOKED = SynchedEntityData
        .defineId(
            WireHookEntity.class,
            EntityDataSerializers.BOOLEAN
        );

    protected static final EntityDataAccessor<Boolean> RECALLED = SynchedEntityData
        .defineId(
            WireHookEntity.class,
            EntityDataSerializers.BOOLEAN
        );

    protected static final EntityDataAccessor<ItemStack> HOOKSHOT = SynchedEntityData
        .defineId(
            WireHookEntity.class,
            EntityDataSerializers.ITEM_STACK
        );

    private float neutral = -1F;

    private double lastRebound;

    private boolean grabbed;

    public WireHookEntity(EntityType<WireHookEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WireHookEntity(Player shooter, ItemStack hookshot, Level level) {
        this(PastelEntityTypes.WIRE_HOOK.get(), level);

        setPos(
            shooter.getX(),
            shooter.getY() + shooter.getBbHeight() * 0.667,
            shooter.getZ()
        );
        setRot(
            shooter.getYRot(),
            shooter.getXRot()
        );
        setOwner(shooter);
        entityData.set(HOOKSHOT, hookshot.copy());
        HookshotData.get(shooter).setLinkedHook(this);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        if (getOwner() instanceof Player shooter)
            HookshotData.get(shooter).setLinkedHook(this);
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

        var slack = distanceTo(player);
        if (slack > getMaxRange()) {
            if (slack > getMaxRange() * 2) {
                discard();
                player.playNotifySound(PastelSounds.SHATTER_LIGHT, SoundSource.PLAYERS, 0.75F, 1);
                return;
            }

            if (!isHooked() && !hasRecalled())
                recall();
        }

        if (isHooked()) {
            updateEquilibrium(player, slack);
            springMove(player);
        } else {
            setOldPosAndRot();
            updateRotation();
            moveTo(position().add(getDeltaMovement()));
        }

        if (hasRecalled()) {
            if (slack < 1.75) {
                remove(RemovalReason.DISCARDED);
                return;
            }

            var returnVector = player.position().subtract(position()).normalize();
            var eff = hookshotLevel(Enchantments.EFFICIENCY);
            setDeltaMovement(returnVector.scale(Math.min(Math.pow(slack / 3, 1.5), 4 + eff / 2F) + eff / 8F + 0.5));
        }
    }

    private int getMaxRange() {
        var piercing = hookshotLevel(Enchantments.PIERCING);
        return PastelCommon.CONFIG.WireHookRange * (piercing + 1);
    }

    public int hookshotLevel(ResourceKey<Enchantment> ench) {
        return Ench.getLevel(registryAccess(), ench, entityData.get(HOOKSHOT));
    }

    private boolean nearLowerLimit(LivingEntity owner, float slack) {
        if (getMaxRange() - slack < 2)
            return false;

        if (owner.getY() > getY() - 2)
            return false;

        return owner.onGround() || owner.isSwimming();
    }

    private boolean nearUpperLimit(LivingEntity owner, float slack) {
        return slack < getMaxRange() / 40F + owner.getBbHeight();
    }

    private void updateHookStatus() {
        if (isHooked() != grabbed) {
            grabbed = isHooked();

            if (grabbed) {
                playSound(PastelSounds.METAL_HIT);

                if (getOwner() != null)
                    neutral = getOwner().distanceTo(this) * 0.675F;
            } else {
                playSound(PastelSounds.METAL_TAP);
            }
        }
    }

    private void updateEquilibrium(Player player, float slack) {
        if (player.isShiftKeyDown() && player.jumping) {
            return;
        }

        if (player.isShiftKeyDown() && !nearLowerLimit(player, slack)) {
            neutral += chainSpeed();
        } else if (player.jumping && !nearUpperLimit(player, slack)) {
            neutral -= chainSpeed();
        }
    }

    private float chainSpeed() {
        var eff = hookshotLevel(Enchantments.EFFICIENCY);
        return 0.225F + 0.05F * eff;
    }

    @Override
    public float getFrictionMod() {
        var feather = hookshotLevel(Enchantments.FEATHER_FALLING);
        return 0.03F + feather * 0.005F;
    }

    private void springMove(Player player) {
        var displacement = springDisplacement(player);

        var rebound = reboundStrength(displacement);
        var stabilization = 1 - Math.min(Math.abs(rebound), 1);

        var offset = player
            .position()
            .multiply(1, 0, 1)
            .distanceTo(position().multiply(1, 0, 1)) / 1.5;

        stabilization *= 1 - Math.clamp(offset, 0, 1);
        var vel = player.getDeltaMovement().multiply(1, 0, 1).length() * 4;

        stabilization *= 1 - Math.clamp(vel, 0, 1);
        rebound = Math.clamp(rebound * 4, 0, player.getGravity() * 2);
        lastRebound = rebound * (1 - stabilization);

        var redux = -0.3 - hookshotLevel(Enchantments.POWER) * 0.05;
        player.addDeltaMovement(player.getDeltaMovement().multiply(0, stabilization * redux, 0));
        player.addDeltaMovement(displacement.normalize().scale(rebound));
        player.resetFallDistance();
    }

    private double reboundStrength(Vec3 displacement) {
        var stiffness = STIFFNESS + hookshotLevel(Enchantments.POWER) * 0.025F;
        return stiffness * (displacement.length() * 0.8F - neutral);
    }

    private @NotNull Vec3 springDisplacement(Player player) {
        var displacement = position().subtract(player.position()).multiply(1.5, 1, 1.5);

        if (displacement.y < 0)
            displacement = displacement.multiply(1, 0, 1);
        return displacement;
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
        moveTo(result.getLocation());
        entityData.set(HOOKED, true);

        super.onHitBlock(result);
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    private boolean ensureUniqueness() {
        if (level().isClientSide())
            return true;

        var player = (Player) getOwner();
        assert player != null;
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

        if (getOwner() instanceof Player player) {
            if (!player.level().isClientSide()) {
                var data = MiscPlayerData.get(player);
                data.initiateLungeState(PastelItems.WIRE_HOOK.get());
                data.sync();
            }

            if (grabbed)
                lunge(player);
        }
    }

    public void lunge(Player owner) {
        // No lunging without forward input
        if (Math.abs(owner.zza) < 0.5F)
            return;

        var dir = owner.getDeltaMovement().normalize();
        var mult = 1.2 + hookshotLevel(Enchantments.POWER) * 0.1;
        var motion = Math.clamp(owner.getDeltaMovement().length() * mult, 0.325, 2.25);

        if (owner.getY() > getY() || (owner.getGravity() > 0 && dir.y() < 0))
            dir = dir.multiply(1, 0, 1);

        if (owner.jumping) {
            dir = dir.add(0, 0.5, 0);
        } else {
            dir = dir.add(0, 0.1, 0);
        }

        dir = dir.multiply(1, lastRebound + 1, 1);

        owner.addDeltaMovement(dir.multiply(motion, motion / 3, motion));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HOOKED, false);
        builder.define(RECALLED, false);
        builder.define(HOOKSHOT, ItemStack.EMPTY);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hooked", entityData.get(HOOKED));
        compound.putBoolean("propelled", entityData.get(RECALLED));
        compound.putFloat("neutral", neutral);
        compound.putBoolean("grabbed", grabbed);
        compound.putDouble("lastRebound", lastRebound);
        compound.put("hookshot", entityData.get(HOOKSHOT).saveOptional(registryAccess()));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(HOOKED, compound.getBoolean("hooked"));
        entityData.set(RECALLED, compound.getBoolean("propelled"));
        ItemStack
            .parse(registryAccess(), compound.getCompound("hookshot"))
            .ifPresent(
                s -> entityData.set(HOOKSHOT, s)
            );
        neutral = compound.getFloat("neutral");
        grabbed = compound.getBoolean("grabbed");
        lastRebound = compound.getDouble("lastRebound");
    }
}
