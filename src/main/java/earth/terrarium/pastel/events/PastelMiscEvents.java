package earth.terrarium.pastel.events;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.PastelSided;
import earth.terrarium.pastel.api.item.PrioritizedBlockInteraction;
import earth.terrarium.pastel.api.item.PrioritizedEntityInteraction;
import earth.terrarium.pastel.attachments.data.InertiaData;
import earth.terrarium.pastel.blocks.idols.FirestarterIdolBlock;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.entity.spawners.ShootingStarSpawner;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.helpers.interaction.TimeHelper;
import earth.terrarium.pastel.inventories.AutoCraftingMode;
import earth.terrarium.pastel.items.magic_items.ExchangeStaffItem;
import earth.terrarium.pastel.items.tools.GlassCrestCrossbowItem;
import earth.terrarium.pastel.items.tools.TuningStampItem;
import earth.terrarium.pastel.items.tools.WorkstaffItem;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.progression.PastelCriteria;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.registries.client.PastelColorProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class PastelMiscEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::onReloadResources);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::serverStart);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::startServerLevelTick);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::endServerTick);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::interactEntity);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::blockUse);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, PastelMiscEvents::handleAoEMining);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::updateInertia);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::tagReload);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::leftClickBlock);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::registerTillable);
        NeoForge.EVENT_BUS.addListener(PastelMiscEvents::viridanShimmer);

        // Doesn't seem to have an actual equivalent?
        // EnchantmentEvents.ALLOW_ENCHANTING.register((registryEntry, itemStack, enchantingContext) -> {
        // 	if (registryEntry.is(PastelEnchantments.INDESTRUCTIBLE) && itemStack.is(PastelItemTags
        // 	.INDESTRUCTIBLE_BLACKLISTED)) {
        // 		return TriState.FALSE;
        // 	}
        // 	return TriState.DEFAULT;
        // });
    }

    public static void loadComplete(FMLLoadCompleteEvent event) {
        // Biomes
        PastelCommon.logInfo("Registering biome placements...");
        PastelBiomes.registerBiomePlacements();
    }

    public static void onCrossbowShot(LivingEntity shooter, Projectile projectile) {
        ItemStack crossbow = shooter.getItemInHand(shooter.getUsedItemHand());
        Level level = shooter.level();

        if (crossbow.getItem() != PastelItems.GLASS_CREST_CROSSBOW.get() || !GlassCrestCrossbowItem.isOvercharged(
            crossbow)) {
            return;
        }

        Vec3 particleVelocity = projectile.getDeltaMovement()
                                          .scale(0.05);

        if (GlassCrestCrossbowItem.getOvercharge(crossbow) > 0.99F) {
            PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
                (ServerLevel) level,
                projectile.position(),
                ParticleTypes.SCRAPE,
                5, Vec3.ZERO,
                particleVelocity
            );
            PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
                (ServerLevel) level,
                projectile.position(),
                ParticleTypes.WAX_OFF,
                5, Vec3.ZERO,
                particleVelocity
            );
            PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
                (ServerLevel) level,
                projectile.position(),
                ParticleTypes.WAX_ON,
                5, Vec3.ZERO,
                particleVelocity
            );
            PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
                (ServerLevel) level,
                projectile.position(),
                ParticleTypes.GLOW,
                5, Vec3.ZERO,
                particleVelocity
            );

            if (shooter instanceof ServerPlayer serverPlayerEntity) {
                Support.grantAdvancementCriterion(
                    serverPlayerEntity, PastelCommon.locate("lategame/shoot_fully_overcharged_crossbow"),
                    "shot_fully_overcharged_crossbow"
                );
            }
            if (projectile instanceof AbstractArrow persistentProjectileEntity) {
                persistentProjectileEntity.setBaseDamage(persistentProjectileEntity.getBaseDamage() * 1.5);
            }
        }

        PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
            (ServerLevel) level,
            projectile.position(),
            ParticleTypes.FIREWORK,
            10, Vec3.ZERO,
            particleVelocity
        );

        GlassCrestCrossbowItem.unOvercharge(crossbow);
    }

    private static void registerTillable(BlockEvent.BlockToolModificationEvent event) {
        var context = event.getContext();
        var originalState = event.getState();
        var tool = context.getItemInHand();
        var action = event.getItemAbility();

        if (action != ItemAbilities.HOE_TILL || !tool.canPerformAction(action)) return;

        if (originalState.is(PastelBlocks.SLUSH.get()) || originalState.is(PastelBlocks.OVERGROWN_SLUSH.get())) {
            event.setFinalState(PastelBlocks.TILLED_SLUSH.get()
                                                         .defaultBlockState());
        } else if (originalState.is(PastelBlocks.SHALE_CLAY.get())) {
            event.setFinalState(PastelBlocks.TILLED_SHALE_CLAY.get()
                                                              .defaultBlockState());
        }
    }

    private static void onReloadResources(AddReloadListenerEvent event) {
        event.addListener(new ResourceManagerReloadListener() {
            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                if (!FMLEnvironment.dist.isDedicatedServer()) PastelSided.clearToastManager();

                AutoCraftingMode.clearCache();
                PastelCommon.CACHED_ITEM_TAG_MAP.clear();

                if (PastelCommon.getSidedServer() != null) {
                    //injectEnchantmentUpgradeRecipes(PastelCommon.getSidedServer());
                    FirestarterIdolBlock.addBlockSmeltingRecipes(PastelCommon.getSidedServer());
                }
            }

            @Override
            public String getName() {
                return PastelCommon.MOD_ID + ":cache_clearer";
            }
        });
    }

    private static void serverStart(ServerStartedEvent event) {
        var server = event.getServer();

        PastelCommon.logInfo("Injecting dynamic recipes into recipe manager...");
        FirestarterIdolBlock.addBlockSmeltingRecipes(server);
    }

    private static void startServerLevelTick(LevelTickEvent.Pre event) {
        var level = event.getLevel();

        if (level.isClientSide()) return;

        if (!level.tickRateManager()
                  .runsNormally()) {
            return;
        }

        // these would actually be nicer to have as Spawners in ServerWorld
        // to have them run in tickSpawners()
        // but getting them in there would require some ugly mixins

        if (level.getGameTime() % 100 == 0) {
            if (TimeHelper.getTimeOfDay(level)
                          .isNight()) { // 90 chances in a night
                if (PastelCommon.CONFIG.ShootingStarWorlds.contains(level.dimension()
                                                                         .location()
                                                                         .toString())) {
                    ShootingStarSpawner.INSTANCE.tick((ServerLevel) level, true, true);
                }
            }
        }
    }

    private static void endServerTick(ServerTickEvent.Post event) {
        var server = event.getServer();

        if (!server.tickRateManager()
                   .runsNormally()) {
            return;
        }

        try {
            Pastel.getServerInstance()
                  .tick();
        } catch (Exception e) {
            PastelCommon.logError("Error in the Pastel Network transmission loop.");
            e.printStackTrace();
        }

        PlayerList playerManager = server.getPlayerList();
        for (ServerPlayer player : playerManager.getPlayers()) {
            Level level = player.level();
            if (!player.isCreative() && !player.isSpectator() && level.dimension() == PastelLevels.DIMENSION_KEY &&
                player.getY() > level.getMaxBuildHeight()) {
                player.hurt(
                    player.damageSources()
                          .fellOutOfWorld(), 10.0F
                );
                if (player.isDeadOrDying()) {
                    Support.grantAdvancementCriterion(
                        player, "lategame/get_killed_while_out_of_deeper_down_bounds", "get_rekt");
                }
            }
        }
    }

    private static void interactEntity(PlayerInteractEvent.EntityInteract event) {
        var player = event.getEntity();
        var hand = player.getUsedItemHand();
        var entity = event.getTarget();

        ItemStack handStack = player.getItemInHand(hand);
        if (handStack.getItem() instanceof PrioritizedEntityInteraction &&
            entity instanceof LivingEntity livingEntity) {
            event.setCancellationResult(handStack.interactLivingEntity(player, livingEntity, hand));
            if (event.getCancellationResult()
                     .indicateItemUse()) event.setCanceled(true);
        }
    }

    private static void blockUse(PlayerInteractEvent.RightClickBlock event) {
        var player = event.getEntity();
        var hand = player.getUsedItemHand();
        var hitResult = event.getHitVec();

        ItemStack handStack = player.getItemInHand(hand);
        if (handStack.getItem() instanceof PrioritizedBlockInteraction) {
            event.setCancellationResult(handStack.useOn(new UseOnContext(player, hand, hitResult)));
            if (event.getCancellationResult()
                     .indicateItemUse()) event.setCanceled(true);
        }
    }

    private static final Set<BlockPos> AREA_TARGETS = new HashSet<>();

    private static void viridanShimmer(BlockEvent.EntityPlaceEvent event) {
        if (!event.getPlacedBlock()
                  .is(PastelBlockTags.VIRIDIAN_CRYSTAL_PURITY_SOURCES)) return;
        var pos = event.getBlockSnapshot()
                       .getPos();
        var level = event.getLevel();
        var random = level.getRandom();
        for (BlockPos testPos : BlockPos.withinManhattan(pos, 10, 10, 10)) {
            if (!level.isOutsideBuildHeight(testPos) && level.getBlockState(testPos)
                                                             .is(PastelBlocks.VIRIDIAN_CRYSTAL)) {
                if (level instanceof ServerLevel serverLevel) // always true apparently
                    serverLevel.sendParticles(
                        PastelParticleTypes.LIGHT_TRAIL, testPos.getX(), testPos.getY(),
                        testPos.getZ(), 4, 0.5, 0.5, 0.5, 0.1
                    );
            }
        }
    }

    private static void handleAoEMining(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        var original = event.getPos();

        if (AREA_TARGETS.contains(original)) return; // No recursion

        var cap = player.getMainHandItem()
                        .getCapability(PastelCapabilities.Misc.MINING);
        if (cap == null) return;

        var aoe = cap.getMiningArea(player, player.getMainHandItem(), original);
        var reach = Math.max(Math.max(aoe.getX(), aoe.getY()), aoe.getZ());

        if (aoe.equals(Vec3i.ZERO)) return;

        var start = switch (player.getNearestViewDirection()
                                  .getAxis()) {
            case X -> original.offset(aoe.getZ(), aoe.getY(), aoe.getX());
            case Y -> original.offset(aoe.getX(), aoe.getZ(), aoe.getY());
            case Z -> original.offset(aoe.getX(), aoe.getY(), aoe.getZ());
        };
        var end = switch (player.getNearestViewDirection()
                                .getAxis()) {
            case X -> original.offset(0, -aoe.getY(), -aoe.getX());
            case Y -> original.offset(-aoe.getX(), 0, -aoe.getY());
            case Z -> original.offset(-aoe.getX(), -aoe.getY(), 0);
        };

        Predicate<BlockState> minableBlocksPredicate = state -> {
            boolean suitableTool = !state.requiresCorrectToolForDrops() || event.getPlayer()
                                                                                .getMainHandItem()
                                                                                .isCorrectToolForDrops(state);
            boolean suitableSpeed = event.getPlayer()
                                         .getMainHandItem()
                                         .getDestroySpeed(state) > 1;
            return suitableTool && suitableSpeed;
        };

        BlockPos.betweenClosedStream(start, end)
                .filter(pos -> !pos.equals(original) && player.canInteractWithBlock(pos, 1.0 + reach) &&
                               minableBlocksPredicate.test(event.getPlayer()
                                                                .level()
                                                                .getBlockState(pos)))
                .peek(AREA_TARGETS::add)
                .peek(player.gameMode::destroyBlock)
                .forEach(AREA_TARGETS::remove);
    }

    private static void updateInertia(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        var state = event.getState();
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());

        RegistryAccess lookup = player.level()
                                      .registryAccess();
        var inertia = Ench.getLevel(lookup, PastelEnchantments.INERTIA, stack);
        if (inertia > 0) {
            player.getData(InertiaData.ATTACHMENT)
                  .record(
                      state, player.level()
                                   .getGameTime(), inertia
                  );
        }

        PastelCriteria.BLOCK_BROKEN.trigger(player, state);
    }

    private static void tagReload(TagsUpdatedEvent event) {
        if (event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED)
            PastelColorProviders.resetToggleableProviders();
    }

    private static void leftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        var player = event.getEntity();
        var level = event.getLevel();
        var pos = event.getPos();
        var direction = event.getFace();
        var action = event.getAction();

        if (!level.isClientSide && !player.isSpectator() && action == PlayerInteractEvent.LeftClickBlock.Action.START) {

            ItemStack mainHandStack = player.getMainHandItem();
            if (mainHandStack.getItem() instanceof ExchangeStaffItem exchangeStaffItem) {
                BlockState targetBlockState = level.getBlockState(pos);
                if (exchangeStaffItem.canInteractWith(targetBlockState, level, pos, player)) {
                    Optional<Block> storedBlock = ExchangeStaffItem.getStoredBlock(player.getMainHandItem());

                    if (storedBlock.isPresent() && storedBlock.get() != targetBlockState.getBlock() && storedBlock.get()
                                                                                                                  .asItem() !=
                                                                                                       Items.AIR &&
                        ExchangeStaffItem.exchange(
                            level, pos, player, storedBlock.get(), player.getMainHandItem(), true, direction)) {

                        event.setCanceled(true);
                        return;
                    }
                    event.setCanceled(true);
                }
                level.playSound(
                    null, player.blockPosition(), SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else if (mainHandStack.getItem() instanceof TuningStampItem tuningStampItem) {
                if (mainHandStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                                 .contains(TuningStampItem.DATA)) tuningStampItem.clearData(
                    Optional.of(player), mainHandStack);
                event.setCanceled(true);
            }
        }
    }

    public static int getFluidLuminance(FluidStack fluid) {
        return fluid.getFluidType()
                    .getLightLevel(fluid);
    }

}
