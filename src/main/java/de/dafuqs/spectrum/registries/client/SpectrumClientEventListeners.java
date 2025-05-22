package de.dafuqs.spectrum.registries.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.energy.InkPowered;
import de.dafuqs.spectrum.api.interaction.ItemProvider;
import de.dafuqs.spectrum.api.interaction.ItemProviderRegistry;
import de.dafuqs.spectrum.api.render.DynamicItemRenderer;
import de.dafuqs.spectrum.api.render.DynamicRenderModel;
import de.dafuqs.spectrum.blocks.bottomless_bundle.BottomlessBundleItem;
import de.dafuqs.spectrum.blocks.pastel_network.Pastel;
import de.dafuqs.spectrum.data_loaders.ParticleSpawnerParticlesDataLoader;
import de.dafuqs.spectrum.deeper_down.DimensionRenderEffects;
import de.dafuqs.spectrum.deeper_down.HowlingSpireEffects;
import de.dafuqs.spectrum.helpers.BuildingHelper;
import de.dafuqs.spectrum.helpers.TooltipHelper;
import de.dafuqs.spectrum.items.magic_items.BuildingStaffItem;
import de.dafuqs.spectrum.items.magic_items.ConstructorsStaffItem;
import de.dafuqs.spectrum.items.magic_items.ExchangeStaffItem;
import de.dafuqs.spectrum.items.tools.OmniAcceleratorItem;
import de.dafuqs.spectrum.mixin.accessors.WorldRendererAccessor;
import de.dafuqs.spectrum.particle.render.ExtendedParticleManager;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumDimensions;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.render.HudRenderers;
import de.dafuqs.spectrum.sound.BiomeAttenuatingSoundInstance;
import de.dafuqs.spectrum.sound.BlockAuraSoundInstance;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Triplet;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class SpectrumClientEventListeners {
	
	// TODO: Move to API package
	public static final ObjectOpenHashSet<ModelResourceLocation> CUSTOM_ITEM_MODELS = new ObjectOpenHashSet<>();
	private static boolean postProcessWasOn = SpectrumCommon.CONFIG.PostProcess;
	
	private static void registerCustomItemRenderer(String id, Item item, Supplier<DynamicItemRenderer> renderer) {
		CUSTOM_ITEM_MODELS.add(new ModelResourceLocation(SpectrumCommon.locate(id), "inventory"));
		DynamicItemRenderer.RENDERERS.put(item, renderer.get());
	}
	//
	public static void register() {
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(ParticleSpawnerParticlesDataLoader.INSTANCE);

		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			private final ResourceLocation id = SpectrumCommon.locate("cache_clearer_client");

			@Override
			public void onResourceManagerReload(ResourceManager manager) {
				UnlockToastManager.clear();
			}

			@Override
			public ResourceLocation getFabricId() {
				return id;
			}
		});
		
		registerCustomItemRenderer("bottomless_bundle", SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem(), BottomlessBundleItem.Renderer::new);
		registerCustomItemRenderer("omni_accelerator", SpectrumItems.OMNI_ACCELERATOR, OmniAcceleratorItem.Renderer::new);
		
		WorldRenderEvents.START.register(context -> HudRenderers.clearItemStackOverlay());
		
		WorldRenderEvents.AFTER_ENTITIES.register(context -> ((ExtendedParticleManager) Minecraft.getInstance().particleEngine).render(context.matrixStack(), context.consumers(), context.camera(), context.tickCounter().getGameTimeDeltaPartialTick(true)));
		WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
			Entity focusedEntity = context.camera().getEntity();
			
			if (focusedEntity instanceof LivingEntity livingEntity) {
				boolean paintbrushInHand = livingEntity.getMainHandItem().is(SpectrumItems.PAINTBRUSH) || livingEntity.getOffhandItem().is(SpectrumItems.PAINTBRUSH);
				Pastel.getClientInstance().renderLines(context, livingEntity, paintbrushInHand);
			}
		});
		WorldRenderEvents.BLOCK_OUTLINE.register(SpectrumClientEventListeners::renderExtendedBlockOutline);
		BiomeAttenuatingSoundInstance.clear();

		ModelLoadingPlugin.register((ctx) -> {
			ctx.modifyModelAfterBake().register((orig, c) -> {
				ModelResourceLocation id = c.topLevelId();
				if (id instanceof ModelResourceLocation mid && CUSTOM_ITEM_MODELS.contains(mid)) {
					return new DynamicRenderModel(orig);
				}
				return orig;
			});
		});
		
		ClientLifecycleEvents.CLIENT_STARTED.register(minecraftClient -> SpectrumColorProviders.registerClient());
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> Pastel.clearClientInstance());
		
		ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
			if (stack.has(DataComponents.FOOD)) {
				if (BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals(SpectrumCommon.MOD_ID)) {
					TooltipHelper.addFoodComponentEffectTooltip(stack, lines, tooltipContext.tickRate());
				}
			}
			if (stack.is(SpectrumItemTags.COMING_SOON_TOOLTIP)) {
				lines.add(Component.translatable("spectrum.tooltip.coming_soon").withStyle(ChatFormatting.RED));
			}
		});
		
		ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> {
			if (SpectrumCommon.CONFIG.PostProcess && world.dimension().equals(SpectrumDimensions.DIMENSION_KEY)) {
				initializeColorGrading(client);
			}
			else  {
				SpectrumShaders.clearDimensionShaders();
			}
		});
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			var world = client.level;
			Entity cameraEntity = client.getCameraEntity();
			if (world == null || cameraEntity == null) {
				BiomeAttenuatingSoundInstance.clear();
				BlockAuraSoundInstance.clear();
				return;
			}
			
			Holder<Biome> biome = world.getBiome(client.getCameraEntity().blockPosition());

			HowlingSpireEffects.clientTick(world, cameraEntity, biome);
			DimensionRenderEffects.clientTick(world, cameraEntity, biome);
			
			if (SpectrumCommon.CONFIG.PostProcess) {
				if (!postProcessWasOn) {
					initializeColorGrading(client);
					postProcessWasOn = true;
				}
				
				SpectrumShaders.updateDimensionShaders(world);
			}
			else if (postProcessWasOn) {
				SpectrumShaders.clearDimensionShaders();
				postProcessWasOn = false;
			}
		});
	}
	
	private static void initializeColorGrading(Minecraft client) {
		if (SpectrumShaders.colorGradingPostProcess.isEmpty()) {
			SpectrumShaders.colorGradingPostProcess = SpectrumShaders.loadPostProcess(client, SpectrumShaders.COLOR_GRADING_ID);
		}
	}
	
	private static boolean renderExtendedBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext hitResult) {
		boolean shouldCancel = false;
		Minecraft client = Minecraft.getInstance();
		if (client.player != null && context.blockOutlines()) {
			for (ItemStack handStack : client.player.getHandSlots()) {
				Item handItem = handStack.getItem();
				if (handItem instanceof ConstructorsStaffItem) {
					if (hitResult != null && client.hitResult instanceof BlockHitResult blockHitResult) {
						shouldCancel = renderPlacementStaffOutline(context.matrixStack(), context.camera(), hitResult.cameraX(), hitResult.cameraY(), hitResult.cameraZ(), context.consumers(), blockHitResult);
					}
					break;
				} else if (handItem instanceof ExchangeStaffItem) {
					if (hitResult != null) {
						shouldCancel = renderExchangeStaffOutline(context.matrixStack(), context.camera(), hitResult.cameraX(), hitResult.cameraY(), hitResult.cameraZ(), context.consumers(), handStack, hitResult);
					}
					break;
				}
			}
		}
		
		return !shouldCancel;
	}
	
	private static boolean renderPlacementStaffOutline(PoseStack matrices, Camera camera, double d, double e, double f, MultiBufferSource consumers, @NotNull BlockHitResult hitResult) {
		Minecraft client = Minecraft.getInstance();
		ClientLevel world = client.level;
		Player player = client.player;
		if (player == null || world == null)
			return false;
		
		BlockPos lookingAtPos = hitResult.getBlockPos();
		BlockState lookingAtState = world.getBlockState(lookingAtPos);
		
		if (player.getMainHandItem().getItem() instanceof BuildingStaffItem staff && (player.isCreative() || staff.canInteractWith(lookingAtState, world, lookingAtPos, player))) {
			Block lookingAtBlock = lookingAtState.getBlock();
			Item item = lookingAtBlock.asItem();
			VoxelShape shape = Shapes.empty();
			
			if (item != Items.AIR) {
				int itemCountInInventory = Integer.MAX_VALUE;
				long inkLimit = Long.MAX_VALUE;
				if (!player.isCreative()) {
					Triplet<Block, Item, Integer> inventoryItemAndCount = BuildingHelper.getBuildingItemCountInInventoryIncludingSimilars(player, lookingAtBlock, Integer.MAX_VALUE);
					item = inventoryItemAndCount.getB();
					itemCountInInventory = inventoryItemAndCount.getC();
					inkLimit = InkPowered.getAvailableInk(player, ConstructorsStaffItem.USED_COLOR) / ConstructorsStaffItem.INK_COST_PER_BLOCK;
				}
				
				boolean sneaking = player.isShiftKeyDown();
				if (itemCountInInventory == 0) {
					HudRenderers.setItemStackToRender(new ItemStack(item), 0, false);
				} else if (inkLimit == 0) {
					HudRenderers.setItemStackToRender(new ItemStack(item), 1, true);
				} else {
					long usableCount = Math.min(itemCountInInventory, inkLimit);
					List<BlockPos> positions = BuildingHelper.calculateBuildingStaffSelection(world, lookingAtPos, hitResult.getDirection(), usableCount, ConstructorsStaffItem.getRange(player), !sneaking);
					if (!positions.isEmpty()) {
						for (BlockPos newPosition : positions) {
							if (world.getWorldBorder().isWithinBounds(newPosition)) {
								BlockPos testPos = lookingAtPos.subtract(newPosition);
								shape = Shapes.or(shape, lookingAtState.getShape(world, lookingAtPos, CollisionContext.of(camera.getEntity())).move(-testPos.getX(), -testPos.getY(), -testPos.getZ()));
							}
						}
						
						HudRenderers.setItemStackToRender(new ItemStack(item), positions.size(), false);
						VertexConsumer linesBuffer = consumers.getBuffer(RenderType.lines());
						WorldRendererAccessor.invokeRenderShape(matrices, linesBuffer, shape, (double) lookingAtPos.getX() - d, (double) lookingAtPos.getY() - e, (double) lookingAtPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private static boolean renderExchangeStaffOutline(PoseStack matrices, Camera camera, double d, double e, double f, MultiBufferSource consumers, ItemStack exchangeStaffItemStack, WorldRenderContext.BlockOutlineContext hitResult) {
		Minecraft client = Minecraft.getInstance();
		ClientLevel world = client.level;
		BlockPos lookingAtPos = hitResult.blockPos();
		BlockState lookingAtState = hitResult.blockState();
		
		Player player = client.player;
		
		if (player == null || world == null)
			return false;
		
		if (player.getMainHandItem().getItem() instanceof BuildingStaffItem staff && (player.isCreative() || staff.canInteractWith(lookingAtState, world, lookingAtPos, player))) {
			Block lookingAtBlock = lookingAtState.getBlock();
			Optional<Block> exchangeBlock = ExchangeStaffItem.getStoredBlock(exchangeStaffItemStack);
			if (exchangeBlock.isPresent() && exchangeBlock.get() != lookingAtBlock) {
				Item exchangeBlockItem = exchangeBlock.get().asItem();
				VoxelShape shape = Shapes.empty();
				
				if (exchangeBlockItem != Items.AIR) {
					int itemCountInInventory = Integer.MAX_VALUE;
					long inkLimit = Integer.MAX_VALUE;
					if (!player.isCreative()) {
						Inventory playerInventory = player.getInventory();
						itemCountInInventory = playerInventory.countItem(exchangeBlockItem);
						for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
							var currentStack = playerInventory.getItem(i);
							ItemProvider itemProvider = ItemProviderRegistry.getProvider(currentStack);
							if (itemProvider != null) {
								itemCountInInventory += itemProvider.getItemCount(player, currentStack, exchangeBlockItem);
							}
						}
						inkLimit = InkPowered.getAvailableInk(player, ExchangeStaffItem.USED_COLOR) / ExchangeStaffItem.INK_COST_PER_BLOCK;
					}
					
					if (itemCountInInventory == 0) {
						HudRenderers.setItemStackToRender(new ItemStack(exchangeBlockItem), 0, false);
					} else if (inkLimit == 0) {
						HudRenderers.setItemStackToRender(new ItemStack(exchangeBlockItem), 1, true);
					} else {
						long usableCount = Math.min(itemCountInInventory, inkLimit);
						List<BlockPos> positions = BuildingHelper.getConnectedBlocks(world, lookingAtPos, usableCount, ExchangeStaffItem.getRange(player));
						for (BlockPos newPosition : positions) {
							if (world.getWorldBorder().isWithinBounds(newPosition)) {
								BlockPos testPos = lookingAtPos.subtract(newPosition);
								shape = Shapes.or(shape, lookingAtState.getShape(world, lookingAtPos, CollisionContext.of(camera.getEntity())).move(-testPos.getX(), -testPos.getY(), -testPos.getZ()));
							}
						}
						
						HudRenderers.setItemStackToRender(new ItemStack(exchangeBlockItem), positions.size(), false);
						VertexConsumer linesBuffer = consumers.getBuffer(RenderType.lines());
						WorldRendererAccessor.invokeRenderShape(matrices, linesBuffer, shape,
								(double) lookingAtPos.getX() - d, (double) lookingAtPos.getY() - e,
								(double) lookingAtPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
