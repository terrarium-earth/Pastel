package earth.terrarium.pastel.events;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Either;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.interaction.ItemProvider;
import earth.terrarium.pastel.api.render.DynamicItemRenderer;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.data_loaders.ParticleSpawnerParticlesDataLoader;
import earth.terrarium.pastel.data_loaders.dimension.ColorGradingLoader;
import earth.terrarium.pastel.data_loaders.dimension.EnvDataLoader;
import earth.terrarium.pastel.deeper_down.Environmental;
import earth.terrarium.pastel.deeper_down.HowlingSpireEffects;
import earth.terrarium.pastel.helpers.level.BuildingHelper;
import earth.terrarium.pastel.helpers.render.TooltipHelper;
import earth.terrarium.pastel.items.magic_items.BuildingStaffItem;
import earth.terrarium.pastel.items.magic_items.ConstructorsStaffItem;
import earth.terrarium.pastel.items.magic_items.ExchangeStaffItem;
import earth.terrarium.pastel.items.trinkets.InkDrainTrinketItem;
import earth.terrarium.pastel.mixin.client.accessors.WorldRendererAccessor;
import earth.terrarium.pastel.particle.render.ExtendedParticleManager;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelLevels;
import earth.terrarium.pastel.registries.client.PastelColorProviders;
import earth.terrarium.pastel.registries.client.PastelShaders;
import earth.terrarium.pastel.render.HudRenderers;
import earth.terrarium.pastel.render.item.SlotEffectDecorator;
import earth.terrarium.pastel.sound.AuraSoundInstance;
import earth.terrarium.pastel.sound.BiomeSoundInstance;
import earth.terrarium.pastel.sound.WorldAttenuation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Triplet;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PastelClientEvents {

    private static boolean postProcessWasOn = PastelCommon.CONFIG.PostProcess;

    private static void registerCustomItemRenderer(Item item, Supplier<DynamicItemRenderer> renderer) {
        DynamicItemRenderer.RENDERERS.put(item, renderer.get());
    }

    //
    public static void register(IEventBus pastelBus) {
        pastelBus.addListener(PastelClientEvents::onReloadClientResources);
        pastelBus.addListener(PastelColorProviders::registerBlocks);
        pastelBus.addListener(PastelColorProviders::registerItems);
        pastelBus.addListener(PastelClientEvents::addResourcePacks);
        pastelBus.addListener(PastelClientEvents::registerDecorators);

        NeoForge.EVENT_BUS.addListener(PastelClientEvents::onWorldRenderStart);
        NeoForge.EVENT_BUS.addListener(PastelClientEvents::onRenderBlockOutlines);
        NeoForge.EVENT_BUS.addListener(PastelClientEvents::onLogout);
        NeoForge.EVENT_BUS.addListener(PastelClientEvents::onLogin);
        NeoForge.EVENT_BUS.addListener(PastelClientEvents::onDrawTooltips);
        NeoForge.EVENT_BUS.addListener(PastelClientEvents::afterClientTick);
        NeoForge.EVENT_BUS.addListener(PastelClientEvents::onEntityTick);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, PastelClientEvents::modifyFog);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, PastelClientEvents::modifyFogColor);

        // registerCustomItemRenderer(PastelBlocks.BOTTOMLESS_BUNDLE.get().asItem(), BottomlessBundleItem
        // .Renderer::new); TODO unholy
        // registerCustomItemRenderer(PastelItems.OMNI_ACCELERATOR.get(), OmniAcceleratorItem.Renderer::new);
    }

    private static void registerDecorators(RegisterItemDecorationsEvent e) {
        slotEffect(PastelItems.INK_ASSORTMENT, e);
        slotEffect(PastelItems.CREATIVE_INK_ASSORTMENT, e);
        slotEffect(PastelItems.INK_FLASK, e);
        slotEffect(PastelItems.PIGMENT_PALETTE, e);
        slotEffect(PastelItems.ARTISTS_PALETTE, e);

        slotEffect(PastelItems.KNOTTED_SWORD, e);
        slotEffect(PastelItems.NECTAR_LANCE, e);
        slotEffect(PastelItems.DRACONIC_TWINSWORD, e);
        slotEffect(PastelItems.DRAGON_TALON, e);
        slotEffect(PastelItems.DREAMFLAYER, e);
        slotEffect(PastelItems.NIGHTFALLS_BLADE, e);

        slotEffect(PastelItems.NECTARDEW_BURGEON, e);
        slotEffect(PastelItems.AETHER_VESTIGES, e);
        slotEffect(PastelItems.SOOTHING_BOUQUET, e);
        slotEffect(PastelItems.MYSTERIOUS_COMPASS, e);
        slotEffect(PastelItems.MYSTERIOUS_LOCKET, e);

        slotEffect(PastelItems.FRACTAL_GLASS_CREST_BIDENT, e);
        slotEffect(PastelItems.FEROCIOUS_GLASS_CREST_BIDENT, e);
        slotEffect(PastelItems.GLASS_CREST_CROSSBOW, e);
        slotEffect(PastelItems.GLASS_CREST_ULTRA_GREATSWORD, e);
        slotEffect(PastelItems.OMNI_ACCELERATOR, e);

        slotEffect(PastelBlocks.CRYSTALLARIEUM, e);

        slotEffect(PastelItems.AETHER_GRACED_NECTAR_GLOVES, e);
        InkDrainTrinketItem.BY_COLOR.values()
                                    .forEach(i -> slotEffect(i, e));
    }

    private static void slotEffect(ItemLike item, RegisterItemDecorationsEvent event) {
        event.register(item, new SlotEffectDecorator());
    }

    private static void modifyFog(ViewportEvent.RenderFog event) {
        var state = Environmental.isActive();
        var far = event.getFarPlaneDistance();

        if (state.force())
            far *= 1.25F;

        var original = far;
        if (state.overrides) {
            far = Environmental.getFar(far);
            event.setNearPlaneDistance(Environmental.getNear(event.getNearPlaneDistance(), !state.force()));
        }

        if (state.force()) {
            event.setFogShape(FogShape.SPHERE);
            far = Math.min(far, original);
        }

        event.setFarPlaneDistance(far);
        event.setCanceled(state.overrides);
    }

    private static void modifyFogColor(ViewportEvent.ComputeFogColor event) {
        if (!Environmental.isActive().overrides) {
            return;
        }

        var red = event.getRed();
        var green = event.getGreen();
        var blue = event.getBlue();

        var envData = Environmental.getEnvData();
        var darkening = envData.brightMult();

        if (darkening < 1) {
            red *= darkening;
            green *= darkening;
            blue *= darkening;
        }

        var colors = new float[]{red, green, blue};
        Environmental.applyColor(colors);
        event.setRed(colors[0]);
        event.setGreen(colors[1]);
        event.setBlue(colors[2]);
    }

    private static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        var player = event.getPlayer();
        if (player.level()
                  .dimension()
                  .equals(PastelLevels.DIMENSION_KEY) && PastelCommon.CONFIG.PostProcess) {
            initializeColorGrading(Minecraft.getInstance());
        }
    }

    private static void addResourcePacks(AddPackFindersEvent event) {
        event.addPackFinders(
            PastelCommon.locate("pastel_style_amethyst"),
            PackType.CLIENT_RESOURCES,
            Component.literal("Pastel-Style Amethyst"),
            PackSource.BUILT_IN,
            false,
            Pack.Position.TOP
        );
    }

    private static void onEntityTick(EntityTickEvent.Post event) {
        var entity = event.getEntity();

        if (entity instanceof LivingEntity living) {
            PrimordialFireData.clientTick(living);
        }
    }

    private static void afterClientTick(ClientTickEvent.Post event) {
        var client = Minecraft.getInstance();
        var level = client.level;
        Entity cameraEntity = client.getCameraEntity();
        if (level == null || cameraEntity == null) {
            BiomeSoundInstance.clear();
            AuraSoundInstance.clear();
            return;
        }

        var inDim = level.dimension()
                         .equals(PastelLevels.DIMENSION_KEY);
        Holder<Biome> biome = level.getBiome(client.getCameraEntity()
                                                   .blockPosition());

        HowlingSpireEffects.clientTick(level, cameraEntity, biome);
        Environmental.tick(cameraEntity);
        WorldAttenuation.tick(level, cameraEntity, inDim);

        if (PastelCommon.CONFIG.PostProcess && inDim) {
            if (!postProcessWasOn) {
                initializeColorGrading(client);
                postProcessWasOn = true;
            }

            PastelShaders.updateDimensionShaders(level);
        } else if (postProcessWasOn) {
            PastelShaders.clearDimensionShaders();
            postProcessWasOn = false;
        }
    }

    private static void onDrawTooltips(RenderTooltipEvent.GatherComponents event) {
        var stack = event.getItemStack();
        var lines = event.getTooltipElements();

        if (stack.has(DataComponents.FOOD)) {
            if (BuiltInRegistries.ITEM.getKey(stack.getItem())
                                      .getNamespace()
                                      .equals(PastelCommon.MOD_ID)) {
                TooltipHelper.addFoodComponentEffectTooltip(stack, lines, Item.TooltipContext.EMPTY.tickRate());
            }
        }
        if (stack.is(PastelItemTags.COMING_SOON_TOOLTIP)) {
            lines.add(Either.left(Component.translatable("pastel.tooltip.coming_soon")
                                           .withStyle(ChatFormatting.RED)));
        }
    }

    private static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        Pastel.clearClientInstance();
        PastelShaders.clearDimensionShaders();
    }

    private static void onRenderBlockOutlines(RenderHighlightEvent.Block event) {
        boolean shouldCancel = false;
        var target = event.getTarget();
        var camera = event.getCamera();

        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            for (ItemStack handStack : client.player.getHandSlots()) {
                Item handItem = handStack.getItem();
                if (handItem instanceof ConstructorsStaffItem) {
                    shouldCancel = renderPlacementStaffOutline(
                        event.getPoseStack(), camera, camera.getPosition().x, camera.getPosition().y,
                        camera.getPosition().z, event.getMultiBufferSource(), target
                    );
                    break;
                } else if (handItem instanceof ExchangeStaffItem) {
                    shouldCancel = renderExchangeStaffOutline(
                        event.getPoseStack(), camera, camera.getPosition().x, camera.getPosition().y,
                        camera.getPosition().z, event.getMultiBufferSource(), handStack, target
                    );
                    break;
                }
            }
        }

        event.setCanceled(shouldCancel);
    }

    private static void onWorldRenderStart(RenderLevelStageEvent event) {
        var stage = event.getStage();

        if (stage == RenderLevelStageEvent.Stage.AFTER_SKY) {
            HudRenderers.clearItemStackOverlay();
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers()
                                                                   .bufferSource();

            if (stage == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
                ((ExtendedParticleManager) minecraft.particleEngine)
                    .render(
                        event.getPoseStack(), bufferSource, event.getCamera(), event.getPartialTick()
                                                                                    .getGameTimeDeltaTicks()
                    );
            } else if (stage == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
                Entity focusedEntity = event.getCamera()
                                            .getEntity();

                if (focusedEntity instanceof LivingEntity) {
                    Pastel.getClientInstance()
                          .renderLines(minecraft.level, event.getPoseStack(), bufferSource, event.getCamera());
                }
            }
        }
    }

    private static void onReloadClientResources(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ParticleSpawnerParticlesDataLoader.INSTANCE);
        event.registerReloadListener(ColorGradingLoader.INSTANCE);
        event.registerReloadListener(EnvDataLoader.INSTANCE);

        event.registerReloadListener(new ResourceManagerReloadListener() {
            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                BiomeSoundInstance.clear();
            }

            @Override
            public String getName() {
                return PastelCommon.MOD_ID + ":cache_clearer_client";
            }
        });
    }

    private static void initializeColorGrading(Minecraft client) {
        if (PastelShaders.colorGradingPostProcess.isEmpty()) {
            PastelShaders.colorGradingPostProcess = PastelShaders.loadPostProcess(
                client, PastelShaders.COLOR_GRADING_ID);
        }
    }

    private static boolean renderPlacementStaffOutline(
        PoseStack matrices, Camera camera, double d, double e, double f, MultiBufferSource consumers,
        @NotNull BlockHitResult hitResult
    ) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.level;
        Player player = client.player;
        if (player == null || world == null)
            return false;

        BlockPos lookingAtPos = hitResult.getBlockPos();
        BlockState lookingAtState = world.getBlockState(lookingAtPos);

        if (player.getMainHandItem()
                  .getItem() instanceof BuildingStaffItem staff && (player.isCreative() || staff.canInteractWith(
            lookingAtState, world, lookingAtPos, player))) {
            Block lookingAtBlock = lookingAtState.getBlock();
            Item item = lookingAtBlock.asItem();
            VoxelShape shape = Shapes.empty();

            if (item != Items.AIR) {
                int itemCountInInventory = Integer.MAX_VALUE;
                long inkLimit = Long.MAX_VALUE;
                if (!player.isCreative()) {
                    Triplet<Block, Item, Integer> inventoryItemAndCount
                        = BuildingHelper.getBuildingItemCountInInventoryIncludingSimilars(
                        player, lookingAtBlock, Integer.MAX_VALUE);
                    item = inventoryItemAndCount.getB();
                    itemCountInInventory = inventoryItemAndCount.getC();
                    inkLimit = InkPowered.getAvailableInk(player, ConstructorsStaffItem.USED_COLOR) /
                               ConstructorsStaffItem.INK_COST_PER_BLOCK;
                }

                boolean sneaking = player.isShiftKeyDown();
                if (itemCountInInventory == 0) {
                    HudRenderers.setItemStackToRender(new ItemStack(item), 0, false);
                } else if (inkLimit == 0) {
                    HudRenderers.setItemStackToRender(new ItemStack(item), 1, true);
                } else {
                    long usableCount = Math.min(itemCountInInventory, inkLimit);
                    List<BlockPos> positions = BuildingHelper.calculateBuildingStaffSelection(
                        world, lookingAtPos, hitResult.getDirection(), usableCount, ConstructorsStaffItem.getRange(
                            player), !sneaking
                    );
                    if (!positions.isEmpty()) {
                        for (BlockPos newPosition : positions) {
                            if (world.getWorldBorder()
                                     .isWithinBounds(newPosition)) {
                                BlockPos testPos = lookingAtPos.subtract(newPosition);
                                shape = Shapes.or(
                                    shape, lookingAtState.getShape(
                                                             world, lookingAtPos,
                                                             CollisionContext.of(camera.getEntity())
                                                         )
                                                         .move(
                                                             -testPos.getX(), -testPos.getY(), -testPos.getZ())
                                );
                            }
                        }

                        HudRenderers.setItemStackToRender(new ItemStack(item), positions.size(), false);
                        VertexConsumer linesBuffer = consumers.getBuffer(RenderType.lines());
                        WorldRendererAccessor.invokeRenderShape(
                            matrices, linesBuffer, shape, (double) lookingAtPos.getX() - d,
                            (double) lookingAtPos.getY() - e, (double) lookingAtPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F
                        );
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean renderExchangeStaffOutline(
        PoseStack matrices, Camera camera, double d, double e, double f, MultiBufferSource consumers,
        ItemStack exchangeStaffItemStack, BlockHitResult hitResult
    ) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.level;
        BlockPos lookingAtPos = hitResult.getBlockPos();
        BlockState lookingAtState = level.getBlockState(lookingAtPos);

        Player player = client.player;

        if (player == null)
            return false;

        if (player.getMainHandItem()
                  .getItem() instanceof BuildingStaffItem staff && (player.isCreative() || staff.canInteractWith(
            lookingAtState, level, lookingAtPos, player))) {
            Block lookingAtBlock = lookingAtState.getBlock();
            Optional<Block> exchangeBlock = ExchangeStaffItem.getStoredBlock(exchangeStaffItemStack);
            if (exchangeBlock.isPresent() && exchangeBlock.get() != lookingAtBlock) {
                Item exchangeBlockItem = exchangeBlock.get()
                                                      .asItem();
                VoxelShape shape = Shapes.empty();

                if (exchangeBlockItem != Items.AIR) {
                    int itemCountInInventory = Integer.MAX_VALUE;
                    long inkLimit = Integer.MAX_VALUE;
                    if (!player.isCreative()) {
                        Inventory playerInventory = player.getInventory();
                        itemCountInInventory = playerInventory.countItem(exchangeBlockItem);
                        for (
                            int i = 0; i < player.getInventory()
                                                 .getContainerSize(); i++
                        ) {
                            var currentStack = playerInventory.getItem(i);
                            ItemProvider itemProvider = currentStack.getCapability(ItemProvider.CAPABILITY);
                            if (itemProvider != null) {
                                itemCountInInventory += itemProvider.getItemCount(
                                    player, currentStack, exchangeBlockItem);
                            }
                        }
                        inkLimit = InkPowered.getAvailableInk(player, ExchangeStaffItem.USED_COLOR) /
                                   ExchangeStaffItem.INK_COST_PER_BLOCK;
                    }

                    if (itemCountInInventory == 0) {
                        HudRenderers.setItemStackToRender(new ItemStack(exchangeBlockItem), 0, false);
                    } else if (inkLimit == 0) {
                        HudRenderers.setItemStackToRender(new ItemStack(exchangeBlockItem), 1, true);
                    } else {
                        long usableCount = Math.min(itemCountInInventory, inkLimit);
                        List<BlockPos> positions = BuildingHelper.getConnectedBlocks(
                            level, lookingAtPos, usableCount, ExchangeStaffItem.getRange(player));
                        for (BlockPos newPosition : positions) {
                            if (level.getWorldBorder()
                                     .isWithinBounds(newPosition)) {
                                BlockPos testPos = lookingAtPos.subtract(newPosition);
                                shape = Shapes.or(
                                    shape, lookingAtState.getShape(
                                                             level, lookingAtPos,
                                                             CollisionContext.of(camera.getEntity())
                                                         )
                                                         .move(
                                                             -testPos.getX(), -testPos.getY(), -testPos.getZ())
                                );
                            }
                        }

                        HudRenderers.setItemStackToRender(new ItemStack(exchangeBlockItem), positions.size(), false);
                        VertexConsumer linesBuffer = consumers.getBuffer(RenderType.lines());
                        WorldRendererAccessor.invokeRenderShape(
                            matrices, linesBuffer, shape,
                            (double) lookingAtPos.getX() - d, (double) lookingAtPos.getY() - e,
                            (double) lookingAtPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F
                        );
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
