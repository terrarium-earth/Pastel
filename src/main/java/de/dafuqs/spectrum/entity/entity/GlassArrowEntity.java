package de.dafuqs.spectrum.entity.entity;

import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.items.tools.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.spells.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.particle.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class GlassArrowEntity extends PersistentProjectileEntity {
	
	private static final String VARIANT_STRING = "variant";
	private static final TrackedData<GlassArrowVariant> VARIANT = DataTracker.registerData(GlassArrowEntity.class, SpectrumTrackedDataHandlerRegistry.GLASS_ARROW_VARIANT);
	
	public static final float DAMAGE_MODIFIER = 1.25F;
	
	public GlassArrowEntity(EntityType<? extends GlassArrowEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public GlassArrowEntity(World world, LivingEntity owner, ItemStack stack, ItemStack shotFrom) {
		super(SpectrumEntityTypes.GLASS_ARROW, owner, world, stack, shotFrom);
		setDamage(getDamage() * DAMAGE_MODIFIER);
	}
	
	public GlassArrowEntity(World world, double x, double y, double z, ItemStack stack, ItemStack shotFrom) {
		super(SpectrumEntityTypes.GLASS_ARROW, x, y, z, world, stack, shotFrom);
		setDamage(getDamage() * DAMAGE_MODIFIER);
	}
	
	@Override
	public void applyDamageModifier(float damageModifier) {
		super.applyDamageModifier(damageModifier);
		setDamage(getDamage() * DAMAGE_MODIFIER);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (this.getWorld().isClient()) {
			if (!this.inGround || this.getWorld().getTime() % 8 == 0) {
				spawnParticles(1);
			}
		}
	}
	
	@Override
	public boolean hasNoGravity() {
		return this.getVelocity().lengthSquared() > 0.1F;
	}
	
	private void spawnParticles(int amount) {
		ParticleEffect particleType = this.getVariant().getParticleEffect();
		if (particleType != null) {
			for (int j = 0; j < amount; ++j) {
				this.getWorld().addParticle(particleType, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0, 0, 0);
			}
		}
	}
	
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		LivingEntity livingEntityToResetHurtTime = null;
		World world = this.getWorld();
		
		// additional effects depending on arrow type
		// mundane glass arrows do not have additional effects
		GlassArrowVariant variant = getVariant();
		if (variant == GlassArrowVariant.TOPAZ) {
			if (!this.getWorld().isClient() && this.getOwner() != null) {
				Entity entity = entityHitResult.getEntity();
				pullEntityClose(this.getOwner(), entity, 0.2);
			}
		} else if (variant == GlassArrowVariant.AMETHYST) {
			if (!this.getWorld().isClient()) {
				Entity entity = entityHitResult.getEntity();
				entity.setFrozenTicks(200);
			}
		} else if (variant == GlassArrowVariant.ONYX) {
			Entity entity = entityHitResult.getEntity();
			if (entity instanceof LivingEntity livingEntity) {
				// we're resetting hurtTime here for once so onEntityHit() can deal damage
				// and also resetting after that again so the target is damageable again after this
				livingEntity.hurtTime = 0;
				livingEntityToResetHurtTime = livingEntity;
				livingEntity.damageShield(20);
				livingEntity.damageArmor(world.getDamageSources().magic(), 20);
			}
		} else if (variant == GlassArrowVariant.MOONSTONE) {
			MoonstoneStrike.create(world, this, null, this.getX(), this.getY(), this.getZ(), 4);
		}
		
		super.onEntityHit(entityHitResult);
		
		if (livingEntityToResetHurtTime != null) {
			livingEntityToResetHurtTime.hurtTime = 0;
		}
		
		this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.75F, 0.9F + world.random.nextFloat() * 0.2F);
		this.remove(RemovalReason.DISCARDED);
	}
	
	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (getVariant() == GlassArrowVariant.MOONSTONE) {
			MoonstoneStrike.create(this.getWorld(), this, null, this.getX(), this.getY(), this.getZ(), 4);
		}
	}
	
	protected static void pullEntityClose(Entity shooter, Entity entityToPull, double pullStrength) {
		double d = shooter.getX() - entityToPull.getX();
		double e = shooter.getY() - entityToPull.getY();
		double f = shooter.getZ() - entityToPull.getZ();
		
		double pullStrengthModifier = 1.0;
		if (entityToPull instanceof LivingEntity livingEntity) {
			pullStrengthModifier = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
		}
		
		Vec3d additionalVelocity = new Vec3d(d * pullStrength, e * pullStrength + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08D, f * pullStrength).multiply(pullStrengthModifier);
		entityToPull.addVelocity(additionalVelocity.x, additionalVelocity.y, additionalVelocity.z);
	}
	
	@Override
	protected ItemStack getDefaultItemStack() {
		return getVariant().getArrow().getDefaultStack();
	}
	
	/**
	 * Glass Arrows pass through translucent blocks as if it were air
	 */
	@Override
	protected void onCollision(HitResult hitResult) {
		HitResult.Type type = hitResult.getType();
		if (type == HitResult.Type.BLOCK) {
			BlockPos hitPos = ((BlockHitResult) hitResult).getBlockPos();
			BlockState state = this.getWorld().getBlockState(hitPos);
			if (!state.isSolidBlock(this.getWorld(), hitPos)) {
				return;
			}
		}
		super.onCollision(hitResult);
	}
	
	/**
	 * Glass Arrows pass through water almost effortlessly
	 */
	@Override
	protected float getDragInWater() {
		return 0.1F;
	}
	
	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(VARIANT, GlassArrowVariant.MALACHITE);
	}
	
	public GlassArrowVariant getVariant() {
		return this.dataTracker.get(VARIANT);
	}
	
	@Override
	protected void knockback(LivingEntity target, DamageSource source) {
		double punch = getVariant() == GlassArrowVariant.CITRINE ? 5 : 0;
		punch += getWeaponStack() != null && getWorld() instanceof ServerWorld serverWorld
						? EnchantmentHelper.modifyKnockback(serverWorld, getWeaponStack(), target, source, 0.0F)
						: 0.0F;
		if (punch > 0.0) {
			double e = Math.max(0.0, 1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
			Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply(punch * 0.6 * e);
			if (vec3d.lengthSquared() > 0.0) {
				target.addVelocity(vec3d.x, 0.1, vec3d.z);
			}
		}
	}
	
	public void setVariant(GlassArrowVariant variant) {
		this.dataTracker.set(VARIANT, variant);
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString(VARIANT_STRING, SpectrumRegistries.GLASS_ARROW_VARIANT.getId(this.getVariant()).toString());
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		GlassArrowVariant variant = SpectrumRegistries.GLASS_ARROW_VARIANT.get(Identifier.tryParse(nbt.getString(VARIANT_STRING)));
		if (variant != null) {
			this.setVariant(variant);
		}
	}
	
}
