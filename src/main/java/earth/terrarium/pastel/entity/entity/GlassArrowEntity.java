package earth.terrarium.pastel.entity.entity;

import com.cmdpro.databank.misc.ColorGradient;
import com.cmdpro.databank.misc.TrailLeftoverHandler;
import com.cmdpro.databank.misc.TrailRender;
import com.cmdpro.databank.rendering.RenderHandler;
import com.cmdpro.databank.rendering.RenderTypeHandler;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.entity.PastelTrackedDataHandlerRegistry;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.items.tools.GlassArrowVariant;
import earth.terrarium.pastel.registries.PastelRegistries;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.EventHooks;

import java.util.Optional;


public class GlassArrowEntity extends AbstractArrow {

    private static final String VARIANT_STRING = "variant";
    public static final float DAMAGE_MODIFIER = 1.5F;
    private static final EntityDataAccessor<GlassArrowVariant> VARIANT = SynchedEntityData.defineId(
        GlassArrowEntity.class, PastelTrackedDataHandlerRegistry.GLASS_ARROW_VARIANT);

    private final IntOpenHashSet pierced = new IntOpenHashSet();
    private Optional<Entity> homingTarget = Optional.empty();
    private boolean laterHits = false;

    public GlassArrowEntity(EntityType<? extends GlassArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public GlassArrowEntity(Level world, LivingEntity owner, ItemStack stack, ItemStack shotFrom) {
        super(PastelEntityTypes.GLASS_ARROW.get(), owner, world, stack, shotFrom);
    }

    public GlassArrowEntity(Level world, double x, double y, double z, ItemStack stack, ItemStack shotFrom) {
        super(PastelEntityTypes.GLASS_ARROW.get(), x, y, z, world, stack, shotFrom);
    }

    @Override
    public void tick() {
        if (getVariant().getAttributes().homing()) {
            updateHomingTarget();
            tryHome();
        }

        var backtrack = getDeltaMovement().reverse();
        super.tick();

        if (tickCount > 400 || inGroundTime >= 20) {
            playSound(SoundEvents.GLASS_BREAK, 0.75F, 0.9F + getRandom().nextFloat() * 0.2F);
            discard();
        }

        if (isFirstHit())
            return;

        handlePiercing(backtrack);
    }

    public int getInGroundTime() {
        return inGroundTime;
    }

    private void tryHome() {
        if (homingTarget.isPresent()) {
            var target = homingTarget.get();
            var homeVector = target
                .position()
                .add(0, target.getEyeHeight() * 0.75, 0)
                .subtract(position())
                .normalize();
            var deltaDeltaDeltaMovement = homeVector.scale(0.65);
            setDeltaMovement(getDeltaMovement().add(deltaDeltaDeltaMovement));
        }
    }

    private void updateHomingTarget() {
        if (homingTarget.isPresent()) {
            if (!homingTarget.get().isAlive()) {
                homingTarget = Optional.empty();
            }
            else {
                return;
            }
        }

        var vision = getBoundingBox().expandTowards(getDeltaMovement().scale(5)).inflate(9);
        homingTarget = level().getEntities(this, vision, this::canHomeTowards).stream().reduce(
            (a, b) -> distanceTo(a) <= distanceTo(b) ? a : b
        );
    }

    private void handlePiercing(Vec3 backtrack) {
        for (Entity proposal : level().getEntities(this, getBoundingBox()
            .expandTowards(backtrack).inflate(1), this::canHitEntity)) {

            var bounds = proposal.getBoundingBox().inflate(0.3F);
            if (bounds.clip(position(), position().add(backtrack)).isPresent()) {
                var hit = new EntityHitResult(proposal);

                if (EventHooks.onProjectileImpact(this, hit))
                    continue;

                onHit(hit);
            }
        }
    }

    @Override
    protected double getDefaultGravity() {
        if (getDeltaMovement().multiply(1, 0, 1).length() < 0.125)
            return super.getDefaultGravity() / 3;

        return 0.0;
    }

    public boolean isFirstHit() {
        return !laterHits;
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return !pierced.contains(target.getId()) && super.canHitEntity(target);
    }

    protected boolean canHomeTowards(Entity target) {
        if (target == getOwner())
            return false;

        return target.getType().getCategory() == MobCategory.MONSTER && canHitEntity(target);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return PastelSoundEvents.SHATTER_LIGHT;
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        var level = this.level();
        var target = hit.getEntity();

        var attributes = getVariant().getAttributes();
        var damage = (float) (attributes.damage() * getBaseDamage() * DAMAGE_MODIFIER);
        var owner = getOwner();
        var source = damageSources().source(attributes.type(), owner);

        if (level instanceof ServerLevel sl)
            damage = EnchantmentHelper.modifyDamage(sl, this.getWeaponItem(), target, source, damage);

        if (getWeaponItem() != null)
            damage *= 1 + (Ench.getLevel(level().registryAccess(), Enchantments.POWER, getWeaponItem()) * 0.5F);

        damage = Support.refineDamage((float) (damage * getDeltaMovement().length()));


        if (owner instanceof LivingEntity lvo)
            lvo.setLastHurtMob(target);

        if (target.hurt(source, damage)) {
            postHurt(target, source);
        }

        if (!attributes.piercing()) {
            this.deflect(ProjectileDeflection.REVERSE, target, this.getOwner(), false);
            this.setDeltaMovement(this.getDeltaMovement().normalize().scale(0.1));
        }

        postHit(hit);
    }

    protected void postHurt(Entity entity, DamageSource source) {
        var owner = getOwner();

        if (!(entity instanceof LivingEntity target))
            return;

        doKnockback(target, source);
        if (level() instanceof ServerLevel sl) {
            EnchantmentHelper.doPostAttackEffectsWithItemSource(sl, target, source, this.getWeaponItem());
        }

        doPostHurtEffects(target);
        if (target instanceof Player && owner instanceof ServerPlayer && !this.isSilent()) {
            ((ServerPlayer) owner).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
        }

        if (getVariant().getAttributes().piercing())
            pierced.add(target.getId());

        playSound(getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (getVariant().getAttributes().homing() && homingTarget.isPresent())
            return;

        super.onHitBlock(blockHitResult);
        postHit(blockHitResult);
    }

    private void postHit(HitResult hit) {
        getVariant().getAttributes().postHit().accept(this, hit, level());
        laterHits = true;
    }

    public static void pull(Entity puller, Entity pulled, double pullStrength) {
        double d = puller.getX() - pulled.getX();
        double e = puller.getY() - pulled.getY();
        double f = puller.getZ() - pulled.getZ();

        double pullStrengthModifier = 1.0;
        if (pulled instanceof LivingEntity livingEntity) {
            pullStrengthModifier = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        }

        Vec3 additionalVelocity = new Vec3(
            d * pullStrength, e * pullStrength + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08D,
            f * pullStrength
        ).scale(pullStrengthModifier);
        pulled.push(additionalVelocity.x, additionalVelocity.y, additionalVelocity.z);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return getVariant().getArrow()
                           .getDefaultInstance();
    }

    @Override
    protected float getWaterInertia() {
        return 1F;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, GlassArrowVariant.MALACHITE);
    }

    public GlassArrowVariant getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    protected void doKnockback(LivingEntity target, DamageSource source) {
        var modifier = getVariant().getAttributes().kb();

        if (modifier <= 0)
            return;

        double d0 = this.level() instanceof ServerLevel serverlevel
        ? EnchantmentHelper.modifyKnockback(serverlevel, getWeaponItem(), target, source, modifier)
        : 0.0F;
        if (d0 > 0.0) {
            double d1 = Math.max(0.0, 1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize()
                            .scale(d0 * 0.6 * d1);
            if (vec3.lengthSqr() > 0.0) {
                target.push(vec3.x, 0.1, vec3.z);
            }
        }
    }

    public void setVariant(GlassArrowVariant variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString(
            VARIANT_STRING, PastelRegistries.GLASS_ARROW_VARIANT.getKey(this.getVariant())
                                                                .toString()
        );
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        GlassArrowVariant variant = PastelRegistries.GLASS_ARROW_VARIANT.get(
            ResourceLocation.tryParse(nbt.getString(VARIANT_STRING)));
        if (variant != null) {
            this.setVariant(variant);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onClientRemoval() {
        super.onClientRemoval();
        TrailRender render = getTrail();
        if (render != null) {
            TrailLeftoverHandler.addTrail(render, RenderHandler.createBufferSource(), LightTexture.FULL_BRIGHT, getGradient());
            shouldRenderTrail = false;
        }
    }

    private boolean shouldRenderTrail = true;
    private TrailRender trail;
    public ColorGradient getGradient() {
        return getVariant().getGradient();
    }

    public TrailRender getTrail() {
        if (!shouldRenderTrail) {
            return null;
        }
        if (trail == null) {
            trail = new TrailRender(position(), 60, 60, 0.15f, PastelCommon.locate("textures/misc/trail/trail.png"),
                                    RenderTypeHandler::transparent
            ).setShrink(true).startTicking();
        }
        return trail;
    }

}
