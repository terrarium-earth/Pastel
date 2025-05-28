package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.entity.SpectrumEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class LightShardEntity extends LightShardBaseEntity {
	
	private static final ResourceLocation[] SPRITES;
	private final int spriteId;

	public LightShardEntity(EntityType<? extends Projectile> entityType, Level world) {
		super(entityType, world);
		scaleOffset /= 1.5F;
		spriteId = world.getRandom().nextInt(4);
	}
	
	public LightShardEntity(Level world, LivingEntity owner, float damageMod, float lifeSpanTicks) {
		super(SpectrumEntityTypes.LIGHT_SHARD.get(), world, owner, 48, damageMod, lifeSpanTicks);
		scaleOffset /= 1.5F;
		spriteId = world.getRandom().nextInt(4);
	}
	
	public static void summonBarrage(Level world, @Nullable LivingEntity user, @Nullable LivingEntity target, Predicate<LivingEntity> targetPredicate, Vec3 position, IntProvider count) {
		summonBarrage(world, user, position, target, targetPredicate, count, () -> new LightShardEntity(world, user, 0.5F, 200));
	}
	
	public static void summonBarrage(Level world, @Nullable LivingEntity user, Vec3 position, @Nullable LivingEntity target, Predicate<LivingEntity> targetPredicate, IntProvider count, Supplier<LightShardBaseEntity> supplier) {
		summonBarrageInternal(world, user, supplier, target, targetPredicate, position, count);
	}
	
	@Override
	public ResourceLocation getTextureLocation() {
		return SPRITES[spriteId];
	}
	
	static {
		SPRITES = new ResourceLocation[4];
		SPRITES[0] = SpectrumCommon.locate("textures/entity/projectile/light_shard_0.png");
		SPRITES[1] = SpectrumCommon.locate("textures/entity/projectile/light_shard_1.png");
		SPRITES[2] = SpectrumCommon.locate("textures/entity/projectile/light_shard_2.png");
		SPRITES[3] = SpectrumCommon.locate("textures/entity/projectile/light_shard_3.png");
	}
}
