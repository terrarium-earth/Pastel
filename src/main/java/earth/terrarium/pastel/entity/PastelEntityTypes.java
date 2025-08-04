package earth.terrarium.pastel.entity;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PastelEntityTypes {

    private static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(
        Registries.ENTITY_TYPE, PastelCommon.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<LivingMarkerEntity>> LIVING_MARKER = register(
        "living_marker", 0, 2147483647, false, EntityDimensions.scalable(0F, 0F), true, LivingMarkerEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<ShootingStarEntity>> SHOOTING_STAR = register(
        "shooting_star", 15, 20, true, EntityDimensions.scalable(0.8F, 0.8F), true, ShootingStarEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<SeatEntity>> SEAT = register(
        "seat", 8, 10, false, EntityDimensions.scalable(0.01F, 0.01F), true, SeatEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<FloatBlockEntity>> FLOAT_BLOCK = register(
        "float_block", 10, 20, true, EntityDimensions.scalable(0.98F, 0.98F), true, FloatBlockEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<PhantomFrameEntity>> PHANTOM_FRAME = register(
        "phantom_frame", 10, 2147483647, false, EntityDimensions.scalable(0.5F, 0.5F), false, PhantomFrameEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<PhantomGlowFrameEntity>> GLOW_PHANTOM_FRAME = register(
        "glow_phantom_frame", 10, 2147483647, false, EntityDimensions.scalable(0.5F, 0.5F), false,
        PhantomGlowFrameEntity::new
    );
    public static final DeferredHolder<EntityType<?>, EntityType<BlockFlooderProjectile>> BLOCK_FLOODER_PROJECTILE
        = register(
        "block_flooder_projectile", 4, 10, true, EntityDimensions.scalable(0.25F, 0.25F), true,
        BlockFlooderProjectile::new
    );
    public static final DeferredHolder<EntityType<?>, EntityType<WireHookEntity>> WIRE_HOOK
        = register(
        "wire_hook", 8, 10, true, EntityDimensions.scalable(0.25F, 0.25F), true,
        WireHookEntity::new
    );
    public static final DeferredHolder<EntityType<?>, EntityType<InkProjectileEntity>> INK_PROJECTILE = register(
        "ink_projectile", 4, 10, true, EntityDimensions.scalable(0.3F, 0.3F), true, InkProjectileEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<LagoonFishingBobberEntity>> LAGOON_FISHING_BOBBER
        = register(
        "lagoon_fishing_bobber", EntityType.Builder.<LagoonFishingBobberEntity>of(
                                               LagoonFishingBobberEntity::new, MobCategory.MISC)
                                                   .noSave()
                                                   .noSummon()
                                                   .fireImmune()
                                                   .sized(0.25F, 0.25F)
                                                   .clientTrackingRange(4)
                                                   .updateInterval(5)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<MoltenFishingBobberEntity>> MOLTEN_FISHING_BOBBER
        = register(
        "molten_fishing_bobber", EntityType.Builder.<MoltenFishingBobberEntity>of(
                                               MoltenFishingBobberEntity::new, MobCategory.MISC)
                                                   .noSave()
                                                   .noSummon()
                                                   .fireImmune()
                                                   .sized(0.25F, 0.25F)
                                                   .clientTrackingRange(4)
                                                   .updateInterval(5)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<BedrockFishingBobberEntity>> BEDROCK_FISHING_BOBBER
        = register(
        "bedrock_fishing_bobber", EntityType.Builder.<BedrockFishingBobberEntity>of(
                                                BedrockFishingBobberEntity::new, MobCategory.MISC)
                                                    .noSave()
                                                    .noSummon()
                                                    .fireImmune()
                                                    .sized(0.25F, 0.25F)
                                                    .clientTrackingRange(4)
                                                    .updateInterval(5)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<FireproofItemEntity>> FIREPROOF_ITEM = register(
        "fireproof_item", 6, 20, true, EntityDimensions.scalable(0.25F, 0.25F), true, FireproofItemEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<EggLayingWoolyPigEntity>> EGG_LAYING_WOOLY_PIG
        = register(
        "egg_laying_wooly_pig", EntityType.Builder.of(EggLayingWoolyPigEntity::new, MobCategory.CREATURE)
                                                  .sized(0.9F, 1.3F)
                                                  .clientTrackingRange(10)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<GlassArrowEntity>> GLASS_ARROW = register(
        "glass_arrow", EntityType.Builder.<GlassArrowEntity>of(GlassArrowEntity::new, MobCategory.MISC)
                                         .sized(0.5F, 0.5F)
                                         .clientTrackingRange(4)
                                         .updateInterval(1)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<MiningProjectileEntity>> MINING_PROJECTILE = register(
        "mining_projectile", 4, 10, true, EntityDimensions.scalable(0.3F, 0.3F), true, MiningProjectileEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<BidentEntity>> BIDENT = register(
        "bident", 4, 10, true, EntityDimensions.scalable(0.5F, 0.5F), true, BidentEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<BidentMirrorImageEntity>> BIDENT_MIRROR_IMAGE
        = register(
        "bident_mirror_image", 4, 10, true, EntityDimensions.scalable(0.5F, 0.5F), true, BidentMirrorImageEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<LightShardEntity>> LIGHT_SHARD = register(
        "light_shard", EntityType.Builder.<LightShardEntity>of(LightShardEntity::new, MobCategory.MISC)
                                         .noSave()
                                         .fireImmune()
                                         .sized(0.75F, 0.75F)
                                         .clientTrackingRange(6)
                                         .updateInterval(1)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<LightSpearEntity>> LIGHT_SPEAR = register(
        "light_spear", EntityType.Builder.<LightSpearEntity>of(LightSpearEntity::new, MobCategory.MISC)
                                         .noSave()
                                         .fireImmune()
                                         .sized(0.75F, 0.75F)
                                         .clientTrackingRange(4)
                                         .updateInterval(20)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<LightMineEntity>> LIGHT_MINE = register(
        "light_mine", EntityType.Builder.<LightMineEntity>of(
                                    LightMineEntity::new, MobCategory.MISC)
                                        .noSave()
                                        .fireImmune()
                                        .sized(
                                            0.75F, 0.75F)
                                        .clientTrackingRange(4)
                                        .updateInterval(20)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<PreservationTurretEntity>> PRESERVATION_TURRET
        = register(
        "preservation_turret", EntityType.Builder.of(PreservationTurretEntity::new, MobCategory.MONSTER)
                                                 .fireImmune()
                                                 .canSpawnFarFromPlayer()
                                                 .sized(1.0F, 1.0F)
                                                 .clientTrackingRange(10)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<LizardEntity>> LIZARD = register(
        "lizard", EntityType.Builder.of(
                                LizardEntity::new, MobCategory.MONSTER)
                                    .sized(
                                        1.0F, 0.7F)
                                    .clientTrackingRange(10)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<KindlingEntity>> KINDLING = register(
        "kindling", EntityType.Builder.of(
                                  KindlingEntity::new, MobCategory.CREATURE)
                                      .sized(
                                          1.0F, 1.0F)
                                      .passengerAttachments(0.5F)
                                      .clientTrackingRange(10)
                                      .fireImmune()
    );
    public static final DeferredHolder<EntityType<?>, EntityType<KindlingCoughEntity>> KINDLING_COUGH = register(
        "kindling_cough", EntityType.Builder.<KindlingCoughEntity>of(KindlingCoughEntity::new, MobCategory.MISC)
                                            .sized(0.25F, 0.25F)
                                            .clientTrackingRange(4)
                                            .updateInterval(10)
                                            .fireImmune()
    );
    public static final DeferredHolder<EntityType<?>, EntityType<EraserEntity>> ERASER = register(
        "eraser", EntityType.Builder.of(
                                EraserEntity::new, MobCategory.MONSTER)
                                    .sized(
                                        0.3F, 0.3F)
                                    .clientTrackingRange(10)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<ItemProjectileEntity>> ITEM_PROJECTILE = register(
        "item_projectile", EntityType.Builder.<ItemProjectileEntity>of(ItemProjectileEntity::new, MobCategory.MISC)
                                             .sized(0.25F, 0.25F)
                                             .clientTrackingRange(6)
                                             .updateInterval(20)
    );
    public static final DeferredHolder<EntityType<?>, EntityType<DragonTalonEntity>> DRAGON_TALON = register(
        "dragon_talon", 4, 10, true, EntityDimensions.scalable(0.5F, 0.5F), true, DragonTalonEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<DraconicTwinswordEntity>> DRACONIC_TWINSWORD
        = register(
        "draconic_twinsword", 6, 2, true, EntityDimensions.scalable(0.5F, 0.5F), true, DraconicTwinswordEntity::new);

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(
            EGG_LAYING_WOOLY_PIG.get(), EggLayingWoolyPigEntity.createEggLayingWoolyPigAttributes()
                                                               .build()
        );
        event.put(
            PRESERVATION_TURRET.get(), PreservationTurretEntity.createGuardianTurretAttributes()
                                                               .build()
        );
        event.put(
            LIZARD.get(), LizardEntity.createLizardAttributes()
                                      .build()
        );
        event.put(
            KINDLING.get(), KindlingEntity.createKindlingAttributes()
                                          .build()
        );
        event.put(
            ERASER.get(), EraserEntity.createEraserAttributes()
                                      .build()
        );
    }

    public static void register(IEventBus pastelBus) {
        pastelBus.addListener(PastelEntityTypes::registerAttributes);

        REGISTER.register(pastelBus);
    }

    public static <X extends Entity> DeferredHolder<EntityType<?>, EntityType<X>> register(
        String name, int trackingDistance, int updateIntervalTicks, boolean alwaysUpdateVelocity, EntityDimensions size,
        boolean fireImmune, EntityType.EntityFactory<X> factory
    ) {
        return REGISTER.register(
            name, () -> {
                EntityType.Builder<X> builder = EntityType.Builder.of(factory, MobCategory.MISC)
                                                                  .clientTrackingRange(trackingDistance)
                                                                  .updateInterval(updateIntervalTicks)
                                                                  .setShouldReceiveVelocityUpdates(alwaysUpdateVelocity)
                                                                  .sized(size.width(), size.height());

                if (fireImmune) {
                    builder.fireImmune();
                }

                return builder.build(PastelCommon.MOD_ID + "." + name);
            }
        );
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
        String id, EntityType.Builder<T> type) {
        return REGISTER.register(id, () -> type.build(PastelCommon.MOD_ID + "." + id));
    }


}
