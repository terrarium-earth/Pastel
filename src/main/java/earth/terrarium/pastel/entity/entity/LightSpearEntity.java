package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.entity.SpectrumEntityTypes;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class LightSpearEntity extends LightShardBaseEntity {
    
    public LightSpearEntity(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }
	
	public LightSpearEntity(Level world, LivingEntity owner, float damage, int lifeSpanTicks) {
		super(SpectrumEntityTypes.LIGHT_SPEAR.get(), world, owner, 48, damage, lifeSpanTicks);
	}

    @Override
    public void tick() {
        super.tick();
		
		targetEntity.ifPresent(entity -> this.lookAt(EntityAnchorArgument.Anchor.EYES, entity.position()));
	}

	@Override
	public ResourceLocation getTextureLocation() {
		return SpectrumCommon.locate("textures/entity/projectile/light_spear.png");
	}
	
	public static void summonBarrage(Level world, @Nullable LivingEntity user, @Nullable LivingEntity target, Predicate<LivingEntity> targetPredicate, Vec3 position, IntProvider count) {
		summonBarrageInternal(world, user, () -> new LightSpearEntity(world, user, 12.0F, 200), target, targetPredicate, position, count);
	}

}
