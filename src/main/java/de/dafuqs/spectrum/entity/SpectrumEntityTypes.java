package de.dafuqs.spectrum.entity;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.thrown.*;
import net.minecraft.registry.*;

public class SpectrumEntityTypes {
	
	public static final EntityType<LivingMarkerEntity> LIVING_MARKER = register("living_marker", 0, 2147483647, false, EntityDimensions.changing(0F, 0F), true, LivingMarkerEntity::new);
	public static final EntityType<ShootingStarEntity> SHOOTING_STAR = register("shooting_star", 15, 20, true, EntityDimensions.changing(0.8F, 0.8F), true, ShootingStarEntity::new);
	public static final EntityType<SeatEntity> SEAT = register("seat", 8, 10, false, EntityDimensions.changing(0.01F, 0.01F), true, SeatEntity::new);
	public static final EntityType<FloatBlockEntity> FLOAT_BLOCK = register("float_block", 10, 20, true, EntityDimensions.changing(0.98F, 0.98F), true, FloatBlockEntity::new);
	public static final EntityType<PhantomFrameEntity> PHANTOM_FRAME = register("phantom_frame", 10, 2147483647, false, EntityDimensions.changing(0.5F, 0.5F), false, PhantomFrameEntity::new);
	public static final EntityType<PhantomGlowFrameEntity> GLOW_PHANTOM_FRAME = register("glow_phantom_frame", 10, 2147483647, false, EntityDimensions.changing(0.5F, 0.5F), false, PhantomGlowFrameEntity::new);
	public static final EntityType<? extends ThrownItemEntity> BLOCK_FLOODER_PROJECTILE = register("block_flooder_projectile", 4, 10, true, EntityDimensions.changing(0.25F, 0.25F), true, BlockFlooderProjectile::new);
	public static final EntityType<InkProjectileEntity> INK_PROJECTILE = register("ink_projectile", 4, 10, true, EntityDimensions.changing(0.3F, 0.3F), true, InkProjectileEntity::new);
	public static final EntityType<LagoonFishingBobberEntity> LAGOON_FISHING_BOBBER = register("lagoon_fishing_bobber", EntityType.Builder.<LagoonFishingBobberEntity>create(LagoonFishingBobberEntity::new, SpawnGroup.MISC).disableSaving().disableSummon().makeFireImmune().dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(5));
	public static final EntityType<MoltenFishingBobberEntity> MOLTEN_FISHING_BOBBER = register("molten_fishing_bobber", EntityType.Builder.<MoltenFishingBobberEntity>create(MoltenFishingBobberEntity::new, SpawnGroup.MISC).disableSaving().disableSummon().makeFireImmune().dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(5));
	public static final EntityType<BedrockFishingBobberEntity> BEDROCK_FISHING_BOBBER = register("bedrock_fishing_bobber", EntityType.Builder.<BedrockFishingBobberEntity>create(BedrockFishingBobberEntity::new, SpawnGroup.MISC).disableSaving().disableSummon().makeFireImmune().dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(5));
	public static final EntityType<? extends ItemEntity> FIREPROOF_ITEM = register("fireproof_item", 6, 20, true, EntityDimensions.changing(0.25F, 0.25F), true, FireproofItemEntity::new);
	public static final EntityType<EggLayingWoolyPigEntity> EGG_LAYING_WOOLY_PIG = register("egg_laying_wooly_pig", EntityType.Builder.create(EggLayingWoolyPigEntity::new, SpawnGroup.CREATURE).dimensions(0.9F, 1.3F).maxTrackingRange(10));
	public static final EntityType<GlassArrowEntity> GLASS_ARROW = register("glass_arrow", EntityType.Builder.<GlassArrowEntity>create(GlassArrowEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).maxTrackingRange(4).trackingTickInterval(20));
	public static final EntityType<MiningProjectileEntity> MINING_PROJECTILE = register("mining_projectile", 4, 10, true, EntityDimensions.changing(0.3F, 0.3F), true, MiningProjectileEntity::new);
	public static final EntityType<BidentEntity> BIDENT = register("bident", 4, 10, true, EntityDimensions.changing(0.5F, 0.5F), true, BidentEntity::new);
	public static final EntityType<BidentMirrorImageEntity> BIDENT_MIRROR_IMAGE = register("bident_mirror_image", 4, 10, true, EntityDimensions.changing(0.5F, 0.5F), true, BidentMirrorImageEntity::new);
	public static final EntityType<LightShardEntity> LIGHT_SHARD = register("light_shard", EntityType.Builder.<LightShardEntity>create(LightShardEntity::new, SpawnGroup.MISC).disableSaving().makeFireImmune().dimensions(0.75F, 0.75F).maxTrackingRange(4).trackingTickInterval(20));
	public static final EntityType<LightSpearEntity> LIGHT_SPEAR = register("light_spear", EntityType.Builder.<LightSpearEntity>create(LightSpearEntity::new, SpawnGroup.MISC).disableSaving().makeFireImmune().dimensions(0.75F, 0.75F).maxTrackingRange(4).trackingTickInterval(20));
	public static final EntityType<LightMineEntity> LIGHT_MINE = register("light_mine", EntityType.Builder.<LightMineEntity>create(LightMineEntity::new, SpawnGroup.MISC).disableSaving().makeFireImmune().dimensions(0.75F, 0.75F).maxTrackingRange(4).trackingTickInterval(20));
	public static final EntityType<MonstrosityEntity> MONSTROSITY = register("monstrosity", EntityType.Builder.create(MonstrosityEntity::new, SpawnGroup.MISC).makeFireImmune().spawnableFarFromPlayer().dimensions(6.0F, 6.0F).maxTrackingRange(10));
	public static final EntityType<PreservationTurretEntity> PRESERVATION_TURRET = register("preservation_turret", EntityType.Builder.create(PreservationTurretEntity::new, SpawnGroup.MONSTER).makeFireImmune().spawnableFarFromPlayer().dimensions(1.0F, 1.0F).maxTrackingRange(10));
	public static final EntityType<LizardEntity> LIZARD = register("lizard", EntityType.Builder.create(LizardEntity::new, SpawnGroup.MONSTER).dimensions(1.0F, 0.7F).maxTrackingRange(10));
	public static final EntityType<KindlingEntity> KINDLING = register("kindling", EntityType.Builder.create(KindlingEntity::new, SpawnGroup.CREATURE).dimensions(1.0F, 1.0F).passengerAttachments(0.5F).maxTrackingRange(10).makeFireImmune());
	public static final EntityType<KindlingCoughEntity> KINDLING_COUGH = register("kindling_cough", EntityType.Builder.<KindlingCoughEntity>create(KindlingCoughEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).makeFireImmune());
	public static final EntityType<EraserEntity> ERASER = register("eraser", EntityType.Builder.create(EraserEntity::new, SpawnGroup.MONSTER).dimensions(0.3F, 0.3F).maxTrackingRange(10));
	public static final EntityType<ItemProjectileEntity> ITEM_PROJECTILE = register("item_projectile", EntityType.Builder.<ItemProjectileEntity>create(ItemProjectileEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(6).trackingTickInterval(20));
	public static final EntityType<DragonTalonEntity> DRAGON_TALON = register("dragon_talon", 4, 10, true, EntityDimensions.changing(0.5F, 0.5F), true, DragonTalonEntity::new);
	public static final EntityType<DraconicTwinswordEntity> DRACONIC_TWINSWORD = register("draconic_twinsword", 6, 2, true, EntityDimensions.changing(0.5F, 0.5F), true, DraconicTwinswordEntity::new);
	
	public static void register() {
		FabricDefaultAttributeRegistry.register(EGG_LAYING_WOOLY_PIG, EggLayingWoolyPigEntity.createEggLayingWoolyPigAttributes());
		FabricDefaultAttributeRegistry.register(MONSTROSITY, MonstrosityEntity.createMonstrosityAttributes());
		FabricDefaultAttributeRegistry.register(PRESERVATION_TURRET, PreservationTurretEntity.createGuardianTurretAttributes());
		FabricDefaultAttributeRegistry.register(LIZARD, LizardEntity.createLizardAttributes());
		FabricDefaultAttributeRegistry.register(KINDLING, KindlingEntity.createKindlingAttributes());
		FabricDefaultAttributeRegistry.register(ERASER, EraserEntity.createEraserAttributes());
	}
	
	// TODO: migrate to FabricEntityTypeBuilder, so the "No data fixer registered for xxxx" errors go away
	public static <X extends Entity> EntityType<X> register(String name, int trackingDistance, int updateIntervalTicks, boolean alwaysUpdateVelocity, EntityDimensions size, boolean fireImmune, EntityType.EntityFactory<X> factory) {
		EntityType.Builder<X> builder = EntityType.Builder.create(factory, SpawnGroup.MISC).maxTrackingRange(trackingDistance).trackingTickInterval(updateIntervalTicks).alwaysUpdateVelocity(alwaysUpdateVelocity).dimensions(size.width(), size.height());
		if (fireImmune) {
			builder.makeFireImmune();
		}
		return Registry.register(Registries.ENTITY_TYPE, SpectrumCommon.locate(name), builder.build());
	}
	
	private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
		return Registry.register(Registries.ENTITY_TYPE, SpectrumCommon.locate(id), type.build(id));
	}
	
	static {
	}
	
}
