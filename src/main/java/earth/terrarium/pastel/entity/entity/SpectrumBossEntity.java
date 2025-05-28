package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.helpers.EntityHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SpectrumBossEntity extends PathfinderMob {
	
	private static final EntityDataAccessor<Integer> INVINCIBILITY_TICKS = SynchedEntityData.defineId(SpectrumBossEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossBar;
	
	protected SpectrumBossEntity(EntityType<? extends SpectrumBossEntity> entityType, Level world) {
		super(entityType, world);
		this.bossBar = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
		this.setHealth(this.getMaxHealth());
	}
	
	public boolean hasInvincibilityTicks() {
		return this.entityData.get(INVINCIBILITY_TICKS) > 0;
	}
	
	public void setInvincibilityTicks(int ticks) {
		this.entityData.set(INVINCIBILITY_TICKS, ticks);
	}
	
	public void tickInvincibility() {
		entityData.set(INVINCIBILITY_TICKS, Math.max(0, this.entityData.get(INVINCIBILITY_TICKS) - 1));
	}
	
	@Override
	public boolean isPickable() {
		return super.isPickable() && !hasInvincibilityTicks();
	}
	
	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(INVINCIBILITY_TICKS, 0);
	}
	
	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}
	
	@Override
	public boolean canAttackType(EntityType<?> type) {
		return true;
	}
	
	protected boolean isNonVanillaKillCommandDamage(DamageSource source, float amount) {
		if (source.is(DamageTypes.FELL_OUT_OF_WORLD) || amount != Float.MAX_VALUE) {
			return false;
		}
		
		Thread currentThread = Thread.currentThread();
		StackTraceElement[] stackTrace = currentThread.getStackTrace();
		
		int i = 0;
		for (StackTraceElement element : stackTrace) {
			if (element.getClassName().contains("net.minecraft")) {
				// this is a vanilla or admin /kill
				this.remove(RemovalReason.KILLED);
				this.gameEvent(GameEvent.ENTITY_DIE);
				return false;
			}
			if (i > 3) {
				// not called from KillCommand? heresy
				return true;
			}
			i++;
		}
		return false;
	}
	
	@Override
	public void actuallyHurt(DamageSource source, float amount) {
		// called when damage was dealt
		Entity dealer = source.getEntity();
		if (!hasInvincibilityTicks() && dealer instanceof Player && EntityHelper.isRealPlayerProjectileOrPet(dealer)) {
			super.actuallyHurt(source, amount);
			this.setInvincibilityTicks(20);
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		if (this.hasCustomName()) {
			this.bossBar.setName(this.getDisplayName());
		}
	}
	
	@Override
	public void die(DamageSource damageSource) {
		super.die(damageSource);

		// grant the kill to all players close by players
		// => should they battle in a team the kill counts for all players
		// instead of just the one that did the killing blow like in vanilla
		Level world = this.level();
		if (!world.isClientSide) {
			for (Player closeByPlayer : this.level().getEntities(EntityType.PLAYER, getBoundingBox().inflate(24), Entity::isAlive)) {
				CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger((ServerPlayer) closeByPlayer, this, damageSource);
			}
		}
	}
	
	@Override
	protected void dropAllDeathLoot(ServerLevel world, DamageSource source) {
		Entity entity = source.getEntity();
		if (EntityHelper.isRealPlayerProjectileOrPet(entity)) {
			super.dropAllDeathLoot(world, source);
		}
	}
	
	@Override
	public boolean checkSpawnRules(LevelAccessor world, MobSpawnType spawnReason) {
		return true;
	}
	
	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}
	
	@Override
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.bossBar.setName(this.getDisplayName());
	}
	
	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossBar.addPlayer(player);
	}
	
	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossBar.removePlayer(player);
	}
	
	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}
	
	@Override
	public void checkDespawn() {
		if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
			this.discard();
		} else {
			this.noActionTime = 0;
		}
	}
	
	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
	}
	
	@Override
	protected float getSoundVolume() {
		return 4.0F;
	}
	
	@Override
	public boolean addEffect(MobEffectInstance effect, @Nullable Entity source) {
		return false;
	}
	
	@Override
	protected boolean canRide(Entity entity) {
		return false;
	}
	
	@Override
	public boolean canUsePortal(boolean allowVehicles) {
		return false;
	}
	
	@Override
	public boolean canAttack(LivingEntity target) {
		return target.canBeSeenAsEnemy();
	}
	
	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}
	
	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}
	
	@Override
	protected SoundEvent getSwimSplashSound() {
		return SoundEvents.HOSTILE_SPLASH;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.HOSTILE_DEATH;
	}
	
	@Override
	public LivingEntity.Fallsounds getFallSounds() {
		return new LivingEntity.Fallsounds(SoundEvents.HOSTILE_SMALL_FALL, SoundEvents.HOSTILE_BIG_FALL);
	}
	
	@Override
	public boolean canBeLeashed() {
		return false;
	}
	
	@Override
	public void makeStuckInBlock(BlockState state, Vec3 multiplier) {
	}
	
	@Override
	public boolean shouldDropExperience() {
		return true;
	}
	
	@Override
	protected boolean shouldDropLoot() {
		return true;
	}
	
}
