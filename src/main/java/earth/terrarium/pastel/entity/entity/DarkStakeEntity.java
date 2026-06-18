package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.api.block.WardDisruptableBlock;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static earth.terrarium.pastel.blocks.geology.AzureCrystalBlock.WARDED;

public class DarkStakeEntity extends AbstractArrow implements ItemSupplier {
    public static final int EFFECT_RADIUS = 10;

    private double baseDamage = 2.0f;

    private int life = 0;

    @Nullable private UUID ownerUUID;

    @Nullable private Entity cachedOwner;

    @Override
    public double getBaseDamage() {
        return baseDamage;
    }

    public DarkStakeEntity(EntityType<? extends DarkStakeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public DarkStakeEntity(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        super(PastelEntityTypes.DARK_STAKE.get(), shooter, level, pickupItemStack, null);
    }

    public DarkStakeEntity(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(PastelEntityTypes.DARK_STAKE.get(), x, y, z, level, pickupItemStack, pickupItemStack);
    }

    // they are short-lived once in the ground, to make them less annoying to fight against
    @Override
    protected void tickDespawn() {
        if (inGround) this.life++;
        if (this.life >= 200) {
            this.discard();
        }
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return PastelSounds.SHATTER_HEAVY;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos
            .betweenClosedStream(new AABB(result.getBlockPos()).inflate(EFFECT_RADIUS))
            .forEach(pos -> {
                var state = level().getBlockState(pos);
                if (state.getBlock() instanceof WardDisruptableBlock disruptableBlock)
                    disruptableBlock.onWardDisrupt(pos, state, level(), this);
                else if (state.is(PastelBlockTags.WARD_DISRUPTABLE) && this.level() instanceof ServerLevel serverLevel)
                    serverLevel
                        .setBlock(
                            pos,
                            state
                                .setValue(
                                    WARDED,
                                    false
                                ),
                            Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_SUPPRESS_DROPS
                        );
            });
    }

    @Override
    public void setOwner(@Nullable Entity owner) {
        if (owner != null) {
            this.ownerUUID = owner.getUUID();
            this.cachedOwner = owner;
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getItem() {
        return PastelItems.DARK_STAKE.toStack(1);
    }

    @Nullable @Override
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.ownerUUID != null && this.level() instanceof ServerLevel serverlevel) {
            this.cachedOwner = serverlevel.getEntity(this.ownerUUID);
            return this.cachedOwner;
        } else {
            return null;
        }
    }

    // this is like 99% copied from AbstractArrow but we have to do it to set the DamageSource. it was this or a mixin
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float f = (float) this
            .getDeltaMovement()
            .length();
        double d0 = this.baseDamage;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this
            .damageSources()
            .source(PastelDamageTypes.DARK_STAKE, entity1, this);

        int j = Mth.ceil(Mth.clamp((double) f * d0, 0.0, 2.147483647E9));

        if (this.isCritArrow()) {
            long k = (long) this.random.nextInt(j / 2 + 2);
            j = (int) Math.min(k + (long) j, 2147483647L);
        }

        if (entity1 instanceof LivingEntity livingentity1) {
            livingentity1.setLastHurtMob(entity);
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int i = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.igniteForSeconds(5.0F);
        }

        if (entity.hurt(damagesource, (float) j)) {
            if (flag) {
                return;
            }

            if (entity instanceof Player player) {
                player.disableShield();
            }

            if (entity instanceof LivingEntity livingentity) {
                if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                }

                this.doKnockback(livingentity, damagesource);
                if (this.level() instanceof ServerLevel serverlevel1) {
                    EnchantmentHelper
                        .doPostAttackEffectsWithItemSource(
                            serverlevel1,
                            livingentity,
                            damagesource,
                            this.getWeaponItem()
                        );
                }

                this.doPostHurtEffects(livingentity);
                if (livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this
                    .isSilent()) {
                    ((ServerPlayer) entity1).connection
                        .send(
                            new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F)
                        );
                }
            }

            this.playSound(getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        } else {
            entity.setRemainingFireTicks(i);
            this.deflect(ProjectileDeflection.REVERSE, entity, this.getOwner(), false);
            this
                .setDeltaMovement(
                    this
                        .getDeltaMovement()
                        .scale(0.2)
                );
            if (!this.level().isClientSide && this
                .getDeltaMovement()
                .lengthSqr() < 1.0E-7) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            }
        }
    }

}
