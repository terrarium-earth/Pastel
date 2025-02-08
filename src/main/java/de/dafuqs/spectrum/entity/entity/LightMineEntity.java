package de.dafuqs.spectrum.entity.entity;

import com.google.common.collect.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.nbt.*;
import net.minecraft.particle.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class LightMineEntity extends LightShardBaseEntity {

    private static final int NO_POTION_COLOR = -1;
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(LightMineEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private boolean colorSet;

    protected final Set<StatusEffectInstance> effects = Sets.newHashSet();

	public LightMineEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

    public LightMineEntity(World world, LivingEntity owner, Optional<LivingEntity> target, float detectionRange, float damage, float lifeSpanTicks) {
        super(SpectrumEntityTypes.LIGHT_MINE, world, owner, target, detectionRange, damage, lifeSpanTicks);
    }

    public static void summonBarrage(World world, @NotNull LivingEntity user, @Nullable LivingEntity target, List<StatusEffectInstance> effects) {
        summonBarrage(world, user, target, effects, user.getEyePos(), DEFAULT_COUNT_PROVIDER);
    }

    public static void summonBarrage(World world, @Nullable LivingEntity user, @Nullable LivingEntity target, List<StatusEffectInstance> effects, Vec3d position, IntProvider count) {
        summonBarrageInternal(world, user, () -> {
            LightMineEntity entity = new LightMineEntity(world, user, Optional.ofNullable(target), 8, 1.0F, 800);
            entity.setEffects(effects);
            return entity;
        }, position, count);
    }

    public void setEffects(List<StatusEffectInstance> effects) {
        this.effects.addAll(effects);
        if (this.effects.isEmpty()) {
            setColor(16777215);
        } else {
            setColor(PotionContentsComponent.getColor(this.effects));
        }
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }
    
    private void setColor(int color) {
        this.colorSet = true;
        this.dataTracker.set(COLOR, color);
    }
    
    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        
        if (this.colorSet) {
            nbt.putInt("Color", this.getColor());
        }
        if (!this.effects.isEmpty()) {
			CodecHelper.writeNbt(nbt, "custom_potion_effects", StatusEffectInstance.CODEC.listOf(), this.effects.stream().toList());
        }
    }
    
    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
		
		this.setEffects(CodecHelper.fromNbt(StatusEffectInstance.CODEC.listOf(), nbt.get("custom_potion_effects"), List.of()));
    
        if (nbt.contains("Color", NbtElement.NUMBER_TYPE)) {
            this.setColor(nbt.getInt("Color"));
        } else {
            this.colorSet = false;
            if (this.effects.isEmpty()) {
                this.dataTracker.set(COLOR, NO_POTION_COLOR);
            } else {
                this.dataTracker.set(COLOR, PotionContentsComponent.getColor(this.effects));
            }
        }
    }
    
    @Override
    public Identifier getTexture() {
        return SpectrumCommon.locate("textures/entity/projectile/light_mine.png");
    }
    
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(COLOR, NO_POTION_COLOR);
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient() && this.age % 4 == 0) {
            this.spawnParticles();
        }
    }
    
    private void spawnParticles() {
        if (!this.effects.isEmpty()) {
            int color = this.getColor();
            this.getWorld().addParticle(EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, color), this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    protected void onHitEntity(LivingEntity attacked) {
        super.onHitEntity(attacked);
        
        Entity attacker = this.getEffectCause();

        Iterator<StatusEffectInstance> var3 = this.effects.iterator();
        StatusEffectInstance statusEffectInstance;
        while (var3.hasNext()) {
            statusEffectInstance = var3.next();
            attacked.addStatusEffect(new StatusEffectInstance(statusEffectInstance.getEffectType(), Math.max(statusEffectInstance.getDuration() / 8, 1), statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles()), attacker);
        }
        if (!this.effects.isEmpty()) {
            var3 = this.effects.iterator();
            while (var3.hasNext()) {
                statusEffectInstance = var3.next();
                attacked.addStatusEffect(statusEffectInstance, attacker);
            }
        }
    }
    
}
