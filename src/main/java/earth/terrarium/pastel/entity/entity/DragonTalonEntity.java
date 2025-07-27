package earth.terrarium.pastel.entity.entity;

import com.cmdpro.databank.misc.ColorGradient;
import earth.terrarium.pastel.api.item.SlotReservingItem;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.items.tools.DragonTalonItem;
import earth.terrarium.pastel.mixin.accessors.PersistentProjectileEntityAccessor;
import earth.terrarium.pastel.mixin.accessors.TridentEntityAccessor;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelMobEffects;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class DragonTalonEntity extends BidentBaseEntity {

    private static final EntityDataAccessor<Boolean> HIT = SynchedEntityData.defineId(
        DragonTalonEntity.class, EntityDataSerializers.BOOLEAN);

    public DragonTalonEntity(Level world) {
        this(PastelEntityTypes.DRAGON_TALON.get(), world);
    }

    public DragonTalonEntity(EntityType<? extends ThrownTrident> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        var pos = blockHitResult.getBlockPos();
        var state = level().getBlockState(pos);

        if (state.is(Blocks.SLIME_BLOCK) && getDeltaMovement().lengthSqr() > 1) {
            switch (blockHitResult.getDirection()
                                  .getAxis()) {
                case X -> setDeltaMovement(getDeltaMovement().multiply(-1, 1, 1));
                case Y -> setDeltaMovement(getDeltaMovement().multiply(1, -1, 1));
                case Z -> setDeltaMovement(getDeltaMovement().multiply(1, 1, -1));
            }
            playSound(PastelSounds.METAL_HIT, 1, 1.5F);
            return;
        }

        super.onHitBlock(blockHitResult);
        if (entityData.get(HIT) || isNoPhysics())
            return;

        entityData.set(HIT, true);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        // TODO: this is in big parts identical to DraconicTwinswordEntity.onEntityHit(). Dedup needed
        ItemStack stack = getTrackedStack();
        Entity attacked = entityHitResult.getEntity();
        if (attacked.getType() == EntityType.ENDERMAN) {
            return;
        }
        Entity owner = this.getOwner();

        float damage = 2.0F;
        DamageSource damageSource = PastelDamageTypes.impaling(level(), this, owner);
        if (level() instanceof ServerLevel serverWorld) {
            damage *= EnchantmentHelper.modifyDamage(serverWorld, stack, attacked, damageSource, getDamage(stack));
        }

        if (attacked.hurt(damageSource, damage)) {
            if (level() instanceof ServerLevel serverWorld) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverWorld, attacked, damageSource, stack);
            }
            if (attacked instanceof LivingEntity livingAttacked) {
                this.doPostHurtEffects(livingAttacked);
            }
        }

        ((TridentEntityAccessor) this).setDealtDamage(true);
        recall();
        this.setDeltaMovement(this.getDeltaMovement()
                                  .multiply(-0.01, -0.1, -0.01));
        float g = 1.0F;

        this.playSound(PastelSounds.IMPALING_HIT, g, 1.0F);
    }

    private float getDamage(ItemStack stack) {
        //TODO can we use a built in function for this?
        var damage = new MutableDouble(0);
        var key = Attributes.ATTACK_DAMAGE.unwrapKey()
                                          .orElse(null);
        var base = Attributes.ATTACK_DAMAGE.value()
                                           .getDefaultValue();
        var modifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        modifiers.forEach(
            EquipmentSlot.MAINHAND, (attribute, modifier) -> {
                if (attribute.is(key)) {
                    var value = modifier.amount();
                    damage.addAndGet(switch (modifier.operation()) {
                        case ADD_VALUE -> value;
                        case ADD_MULTIPLIED_BASE -> value * base;
                        case ADD_MULTIPLIED_TOTAL -> value * damage.getValue();
                    });
                }
            }
        );
        return damage.getValue()
                     .floatValue();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        if (getOwner() == null)
            return;

        var owner = getOwner();
        var difMod = 4F;
        var airborne = !owner.onGround();
        var sneaking = owner.isShiftKeyDown();
        var inertia = Ench.getLevel(
            owner.level()
                 .registryAccess(), PastelEnchantments.INERTIA, getTrackedStack()
        );

        if (sneaking)
            difMod *= 3;

        if (airborne)
            difMod /= 2;

        if (inertia > 0) {
            difMod *= inertia * 1.5F + 1;
        }

        var sizeDif = getVolumeDif(target, difMod);
        yoink(target, getOwner().position(), 0.25 * sizeDif, 0.175);

        if (airborne)
            yoink(owner, target.position(), 0.125 / sizeDif, 0.16);
    }

    private float getVolumeDif(LivingEntity target, float pullMod) {
        if (getOwner() == null) return 0;
        var ownerBox = getOwner().getBoundingBox();
        var targetBox = target.getBoundingBox();
        float ownerVolume = (float) (ownerBox.getXsize() * ownerBox.getYsize() * ownerBox.getZsize());
        float targetVolume = (float) (targetBox.getXsize() * targetBox.getYsize() * targetBox.getZsize());

        return Math.max(Math.min(ownerVolume / (targetVolume / pullMod), 0.8F), 0.5F);
    }

    public void recall() {
        var owner = getOwner();
        if (entityData.get(HIT) && !isNoPhysics()) {
            yoink(owner, position(), 0.125, 0.165);
        }

        if (Ench.hasEnchantment(level().registryAccess(), Enchantments.CHANNELING, getTrackedStack()) &&
            owner != null) {
            if (level() instanceof ServerLevel world) {
                for (int i = 0; i < 10; i++) {
                    world.sendParticles(
                        ParticleTypes.GLOW,
                        getRandomX(1),
                        getY() + getBbHeight() * random.nextFloat(),
                        getRandomZ(1),
                        1 + random.nextInt(2), 0, random.nextFloat() + 0.25F, 0, 0
                    );
                }

                world.playSeededSound(
                    null, position().x, position().y, position().z, PastelSounds.ELECTRIC_DISCHARGE,
                    SoundSource.AMBIENT, 1F, 0.6F + random.nextFloat() * 0.2F, 0
                );
            }
            remove(RemovalReason.DISCARDED);
            return;
        }

        getEntityData().set(TridentEntityAccessor.getLoyalty(), (byte) 4);
        setNoPhysics(true);
    }

    public void yoink(@Nullable Entity yoinked, Vec3 target, double xMod, double yMod) {
        if (yoinked == null)
            return;

        var yoinkPos = yoinked.position();
        var heightDif = Math.abs(yoinkPos.y - target.y);
        var velocity = target.subtract(yoinkPos);
        var sneaking = yoinked.isShiftKeyDown();
        var bonusMod = 1f;

        if (yoinked instanceof LivingEntity livingYoink) {
            bonusMod /= Optional.ofNullable(livingYoink.getEffect(PastelMobEffects.DENSITY))
                                .map(effect -> effect.getAmplifier() + 2)
                                .orElse(1);
            bonusMod *= Optional.ofNullable(livingYoink.getEffect(PastelMobEffects.LIGHTWEIGHT))
                                .map(effect -> (effect.getAmplifier() + 2) / 1.5F)
                                .orElse(1F);
        }

        if (!yoinked.onGround()) {
            yMod += 0.05;
            xMod -= 0.015;
        }

        yMod = Math.max(0.0725, yMod * (1 - (heightDif * 0.024)));

        xMod *= bonusMod;
        yMod *= bonusMod;

        if (yoinked == getOwner() && yoinkPos.y > target.y && !sneaking)
            yMod = 0;

        yoinked.setDeltaMovement(velocity.multiply(xMod, yMod, xMod)
                                         .add(0, sneaking ? 0 : 0.25, 0));
        yoinked.fallDistance = 0F;
        yoinked.hurtMarked = true;
        yoinked.hasImpulse = true;
    }

    @Override
    public void tickDespawn() {
        if (!getRootStack().isEmpty())
            return;

        var life = ((PersistentProjectileEntityAccessor) this).getLife() + 1;
        ((PersistentProjectileEntityAccessor) this).setLife(life);
        if (life >= 1200) {
            this.discard();
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        var rootStack = getRootStack();
        if (!rootStack.isEmpty()) {
            SlotReservingItem.free(rootStack);
        }
        super.remove(reason);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HIT, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(HIT, nbt.getBoolean("hit"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("hit", this.entityData.get(HIT));
    }

    private ItemStack getRootStack() {
        if (getOwner() instanceof Player player) {
            return DragonTalonItem.findThrownStack(player, uuid);
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean tryPickup(Player player) {
        var rootStack = DragonTalonItem.findThrownStack(player, uuid);
        if (!rootStack.isEmpty()) {
            SlotReservingItem.free(rootStack);
            return true;
        } else if (player == getOwner()) {
            discard();
        }
        return false;
    }

    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ItemStack stack) {
        return null;
    }

    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ItemStack stack, float yOffset) {
        return null;
    }
    @Override
    public ColorGradient getGradient() {
        return new ColorGradient(
            new Color(255, 158, 60),
            new Color(255, 77, 38)
        ).fadeAlpha(1, 0).fadeAlpha(0, 0, 1, 0.05f);
    }
}
