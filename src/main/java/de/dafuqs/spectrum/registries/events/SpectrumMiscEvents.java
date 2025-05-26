package de.dafuqs.spectrum.registries.events;

import de.dafuqs.arrowhead.api.CrossbowShootingCallback;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.PrioritizedBlockInteraction;
import de.dafuqs.spectrum.api.item.PrioritizedEntityInteraction;
import de.dafuqs.spectrum.blocks.chests.CompactingChestBlockEntity;
import de.dafuqs.spectrum.blocks.idols.FirestarterIdolBlock;
import de.dafuqs.spectrum.blocks.pastel_network.Pastel;
import de.dafuqs.spectrum.components.InertiaComponent;
import de.dafuqs.spectrum.entity.spawners.ShootingStarSpawner;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.helpers.TimeHelper;
import de.dafuqs.spectrum.items.magic_items.ExchangeStaffItem;
import de.dafuqs.spectrum.items.tools.GlassCrestCrossbowItem;
import de.dafuqs.spectrum.items.tools.TuningStampItem;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.registries.client.SpectrumColorProviders;
import net.minecraft.util.profiling.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.server.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.fluids.*;
import net.neoforged.neoforge.resource.*;
import net.neoforged.neoforge.server.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.*;

public class SpectrumMiscEvents {

	/**
	 * Caches the luminance states from fluids as int
	 * for blocks that react to the light level of fluids
	 * like the fusion shrine lighting up with lava or liquid crystal
	 */
	public static final HashMap<Fluid, Integer> fluidLuminance = new HashMap<>();

	public static void register() {
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::onReloadResources);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::serverStart);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::startServerLevelTick);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::endServerTick);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::interactEntity);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::blockUse);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::blockBreak);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::tagReload);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::leftClickBlock);
		NeoForge.EVENT_BUS.addListener(SpectrumMiscEvents::registerTillable);

		// Doesn't seem to have an actual equivalent?
		// EnchantmentEvents.ALLOW_ENCHANTING.register((registryEntry, itemStack, enchantingContext) -> {
		// 	if (registryEntry.is(SpectrumEnchantments.INDESTRUCTIBLE) && itemStack.is(SpectrumItemTags.INDESTRUCTIBLE_BLACKLISTED)) {
		// 		return TriState.FALSE;
		// 	}
		// 	return TriState.DEFAULT;
		// });

		// hiii arrowhead!!!! >w<
		CrossbowShootingCallback.register((level, shooter, crossbow, projectile) -> {
			int snipingLevel = SpectrumEnchantmentHelper.getLevel(level.registryAccess(), SpectrumEnchantments.SNIPING, crossbow);
			if (snipingLevel > 0) {
				projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.25F * snipingLevel)); // TODO: is this a sensible value?
			}

			if (crossbow.getItem() instanceof GlassCrestCrossbowItem && GlassCrestCrossbowItem.isOvercharged(crossbow)) {
				Vec3 particleVelocity = projectile.getDeltaMovement().scale(0.05);

				if (GlassCrestCrossbowItem.getOvercharge(crossbow) > 0.99F) {
					PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) level,
							projectile.position(), ParticleTypes.SCRAPE, 5,
							Vec3.ZERO, particleVelocity);
					PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) level,
							projectile.position(), ParticleTypes.WAX_OFF, 5,
							Vec3.ZERO, particleVelocity);
					PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) level,
							projectile.position(), ParticleTypes.WAX_ON, 5,
							Vec3.ZERO, particleVelocity);
					PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) level,
							projectile.position(), ParticleTypes.GLOW, 5,
							Vec3.ZERO, particleVelocity);

					if (shooter instanceof ServerPlayer serverPlayerEntity) {
						Support.grantAdvancementCriterion(serverPlayerEntity,
								SpectrumCommon.locate("lategame/shoot_fully_overcharged_crossbow"),
								"shot_fully_overcharged_crossbow");
					}
					if (projectile instanceof AbstractArrow persistentProjectileEntity) {
						persistentProjectileEntity.setBaseDamage(persistentProjectileEntity.getBaseDamage() * 1.5);
					}
				}

				PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) level,
						projectile.position(), ParticleTypes.FIREWORK, 10,
						Vec3.ZERO, particleVelocity);

				GlassCrestCrossbowItem.unOvercharge(crossbow);
			}
		});
	}

	private static void registerTillable(BlockEvent.BlockToolModificationEvent event) {
		var context = event.getContext();
		var originalState = event.getState();
		var tool = context.getItemInHand();
		var action = event.getItemAbility();

		if (action != ItemAbilities.HOE_TILL || !tool.canPerformAction(action))
			return;

		if (originalState.is(SpectrumBlocks.SLUSH) || originalState.is(SpectrumBlocks.OVERGROWN_SLUSH)) {
			event.setFinalState(SpectrumBlocks.TILLED_SLUSH.defaultBlockState());
		}
		else if(originalState.is(SpectrumBlocks.SHALE_CLAY)) {
			event.setFinalState(SpectrumBlocks.TILLED_SHALE_CLAY.defaultBlockState());
		}
	}

	private static void onReloadResources(AddReloadListenerEvent event) {
		event.addListener(new ContextAwareReloadListener() {
			@Override
			public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
				return CompletableFuture.runAsync(() -> {
					CompactingChestBlockEntity.clearCache();
					SpectrumCommon.CACHED_ITEM_TAG_MAP.clear();

					if (ServerLifecycleHooks.getCurrentServer() != null) {
						//injectEnchantmentUpgradeRecipes(ServerLifecycleHooks.getCurrentServer());
						FirestarterIdolBlock.addBlockSmeltingRecipes(ServerLifecycleHooks.getCurrentServer());
					}
				});
			}

			@Override
			public String getName() {
				return SpectrumCommon.MOD_ID + ":cache_clearer";
			}
		});
	}

	private static void serverStart(ServerStartedEvent event) {
		var server = event.getServer();

		SpectrumCommon.logInfo("Injecting dynamic recipes into recipe manager...");
		FirestarterIdolBlock.addBlockSmeltingRecipes(server);
	}

	private static void startServerLevelTick(LevelTickEvent.Pre event) {
		var level = event.getLevel();

		if (level.isClientSide())
			return;

		if (!level.tickRateManager().runsNormally()) {
			return;
		}

		// these would actually be nicer to have as Spawners in ServerWorld
		// to have them run in tickSpawners()
		// but getting them in there would require some ugly mixins

		if (level.getGameTime() % 100 == 0) {
			if (TimeHelper.getTimeOfDay(level).isNight()) { // 90 chances in a night
				if (SpectrumCommon.CONFIG.ShootingStarWorlds.contains(level.dimension().location().toString())) {
					ShootingStarSpawner.INSTANCE.tick((ServerLevel) level, true, true);
				}
			}
		}
	}

	private static void endServerTick(ServerTickEvent.Post event) {
		var server = event.getServer();

		if (!server.tickRateManager().runsNormally()) {
			return;
		}

		try {
			Pastel.getServerInstance().tick();
		} catch (Exception e) {
			SpectrumCommon.logError("Error in the Pastel Network transmission loop.");
			e.printStackTrace();
		}

		PlayerList playerManager = server.getPlayerList();
		for (ServerPlayer player : playerManager.getPlayers()) {
			Level level = player.level();
			if (!player.isCreative() && !player.isSpectator() && level.dimension() == SpectrumDimensions.DIMENSION_KEY && player.getY() > level.getMaxBuildHeight()) {
				player.hurt(player.damageSources().fellOutOfWorld(), 10.0F);
				if (player.isDeadOrDying()) {
					Support.grantAdvancementCriterion(player, "lategame/get_killed_while_out_of_deeper_down_bounds", "get_rekt");
				}
			}
		}
	}

	private static void interactEntity(PlayerInteractEvent.EntityInteract event) {
		var player = event.getEntity();
		var hand = player.getUsedItemHand();
		var entity = event.getTarget();

		ItemStack handStack = player.getItemInHand(hand);
		if (handStack.getItem() instanceof PrioritizedEntityInteraction && entity instanceof LivingEntity livingEntity) {
			event.setCancellationResult(handStack.interactLivingEntity(player, livingEntity, hand));
			if (event.getCancellationResult().indicateItemUse())
				event.setCanceled(true);
        }
	}

	private static void blockUse(PlayerInteractEvent.RightClickBlock event) {
		var player = event.getEntity();
		var hand = player.getUsedItemHand();
		var hitResult = event.getHitVec();

		ItemStack handStack = player.getItemInHand(hand);
		if (handStack.getItem() instanceof PrioritizedBlockInteraction) {
			event.setCancellationResult(handStack.useOn(new UseOnContext(player, hand, hitResult)));
			if (event.getCancellationResult().indicateItemUse())
				event.setCanceled(true);
		}
	}

	private static void blockBreak(BlockEvent.BreakEvent event) {
		var player = (ServerPlayer) event.getPlayer();
		var state = event.getState();

		ItemStack stack = player.getItemInHand(player.getUsedItemHand());
		if (SpectrumEnchantmentHelper.hasEnchantment(player.level().registryAccess(), SpectrumEnchantments.INERTIA, stack)) {
			InertiaComponent inertia = stack.getOrDefault(SpectrumDataComponentTypes.INERTIA, InertiaComponent.DEFAULT);
			long inertiaAmount = state.is(inertia.lastMined()) ? inertia.count() + 1 : 1;
			stack.set(SpectrumDataComponentTypes.INERTIA, new InertiaComponent(state.getBlock(), inertiaAmount));

			SpectrumAdvancementCriteria.INERTIA_USED.trigger(player, state, inertiaAmount);
		}

		SpectrumAdvancementCriteria.BLOCK_BROKEN.trigger(player, state);

	}

	private static void tagReload(TagsUpdatedEvent event) {
		if (event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED)
			SpectrumColorProviders.resetToggleableProviders();
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

					if (storedBlock.isPresent()
							&& storedBlock.get() != targetBlockState.getBlock()
							&& storedBlock.get().asItem() != Items.AIR
							&& ExchangeStaffItem.exchange(level, pos, player, storedBlock.get(), player.getMainHandItem(), true, direction)) {

						event.setCanceled(true);
						return;
					}
					event.setCanceled(true);
				}
				level.playSound(null, player.blockPosition(), SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 1.0F, 1.0F);
			} else if (mainHandStack.getItem() instanceof TuningStampItem tuningStampItem) {
				if (mainHandStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).contains(TuningStampItem.DATA))
					tuningStampItem.clearData(Optional.of(player), mainHandStack);
				event.setCanceled(true);
			}
		}
	}

	public static int getFluidLuminance(FluidStack fluid) {
		return fluid.getFluidType().getLightLevel(fluid);
	}

}
