package earth.terrarium.pastel.blocks;

import com.google.common.collect.AbstractIterator;
import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelLevels;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class DeeperDownPortalBlock extends Block {

    public static final MapCodec<DeeperDownPortalBlock> CODEC = simpleCodec(DeeperDownPortalBlock::new);

    private final static ResourceLocation CREATE_PORTAL_ADVANCEMENT_IDENTIFIER = PastelCommon.locate(
        "midgame/open_fissure");
    private final static String CREATE_PORTAL_ADVANCEMENT_CRITERION = "opened_fissure";

    public static final BooleanProperty FACING_UP = BlockStateProperties.UP;

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4D, 16.0D);
    protected static final VoxelShape SHAPE_UP = Block.box(0.0D, 4D, 0.0D, 16.0D, 16.0D, 16.0D);

    public DeeperDownPortalBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(FACING_UP, false));
    }

    @Override
    public MapCodec<? extends DeeperDownPortalBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onPlace(state, world, pos, oldState, notify);

        if (!world.isClientSide) { // that should be a given, but in modded you never know
            PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
                (ServerLevel) world, Vec3.atCenterOf(pos), PastelParticleTypes.VOID_FOG, 30, new Vec3(0.5, 0.0, 0.5),
                Vec3.ZERO
            );
            if (!hasNeighboringPortals(world, pos)) {
                world.playSound(
                    null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    PastelSounds.FISSURE_OPEN, SoundSource.BLOCKS, 0.75F, 0.75F
                );

                for (Player nearbyPlayer : world.getEntities(
                    EntityType.PLAYER, AABB.ofSize(Vec3.atCenterOf(pos), 16D, 16D, 16D), LivingEntity::isAlive)) {
                    Support.grantAdvancementCriterion(
                        (ServerPlayer) nearbyPlayer, CREATE_PORTAL_ADVANCEMENT_IDENTIFIER,
                        CREATE_PORTAL_ADVANCEMENT_CRITERION
                    );
                }
            }
        }
    }

    @Override
    public ItemInteractionResult useItemOn(
        ItemStack handStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult hit
    ) {
        if (handStack.is(PastelItems.BEDROCK_DUST.get())) {
            if (world.isClientSide) {
                return ItemInteractionResult.SUCCESS;
            } else {
                BlockState placedState = Blocks.BEDROCK.defaultBlockState();
                world.setBlockAndUpdate(pos, placedState);
                world.playSound(
                    null, pos, placedState.getSoundType()
                                          .getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F
                );
                return ItemInteractionResult.CONSUME;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private boolean hasNeighboringPortals(Level world, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (world.getBlockState(pos.relative(direction))
                     .is(this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING_UP) ? SHAPE_UP : SHAPE;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING_UP);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (world instanceof ServerLevel serverWorld && entity.canUsePortal(false) && !entity.isOnPortalCooldown()) {

            entity.setPortalCooldown();
            ResourceKey<Level> currentWorldKey = world.dimension();

            if (currentWorldKey == Level.NETHER) {
                // teleport between top/bottom of the nether
                boolean facingUp = state.getValue(FACING_UP); // true of on top of nether

                if (facingUp) {
                    BlockPos portalPos = new BlockPos(pos.getX(), world.getMinBuildHeight(), pos.getZ());
                    if (!world.getBlockState(portalPos)
                              .is(PastelBlocks.IMBRIFER_PORTAL.get())) {
                        world.setBlockAndUpdate(
                            portalPos, PastelBlocks.IMBRIFER_PORTAL.get()
                                                                      .defaultBlockState()
                                                                      .setValue(FACING_UP, false)
                        );
                    }

                    if (entity instanceof Player) {
                        makeRoomAround(world, portalPos, 4, 2, true, BlockTags.BASE_STONE_NETHER);
                    }

                    BlockPos targetPos = portalPos.above(2);
                    entity.changeDimension(
                        new DimensionTransition(
                            serverWorld, Vec3.atCenterOf(targetPos), Vec3.ZERO, entity.getYRot(), entity.getXRot(),
                            DimensionTransition.DO_NOTHING
                        ));
                    teleportToSafePosition(serverWorld, entity, targetPos, 3);
                } else {
                    BlockPos portalPos = new BlockPos(
                        pos.getX(), world.getMinBuildHeight() + world.dimensionType()
                                                                     .logicalHeight() - 1, pos.getZ()
                    );
                    if (!world.getBlockState(portalPos)
                              .is(PastelBlocks.IMBRIFER_PORTAL.get())) {
                        world.setBlockAndUpdate(
                            portalPos, PastelBlocks.IMBRIFER_PORTAL.get()
                                                                      .defaultBlockState()
                                                                      .setValue(FACING_UP, true)
                        );
                    }

                    if (entity instanceof Player) {
                        makeRoomAround(world, portalPos, 4, 2, false, BlockTags.BASE_STONE_NETHER);
                    }

                    BlockPos targetPos = portalPos.below(3);
                    entity.changeDimension(
                        new DimensionTransition(
                            serverWorld, Vec3.atCenterOf(targetPos), Vec3.ZERO, entity.getYRot(), entity.getXRot(),
                            DimensionTransition.DO_NOTHING
                        ));
                    teleportToSafePosition(serverWorld, entity, targetPos.below(), 5);
                }

                return;
            }

            if (currentWorldKey == Level.OVERWORLD) {
                // => teleport to DD
                ServerLevel targetWorld = serverWorld.getServer()
                                                     .getLevel(PastelLevels.DIMENSION_KEY);
                if (targetWorld != null) {
                    BlockPos portalPos = new BlockPos(pos.getX(), targetWorld.getMaxBuildHeight() - 1, pos.getZ());
                    if (!targetWorld.getBlockState(portalPos)
                                    .is(PastelBlocks.IMBRIFER_PORTAL.get())) {
                        targetWorld.setBlockAndUpdate(
                            portalPos, PastelBlocks.IMBRIFER_PORTAL.get()
                                                                      .defaultBlockState()
                                                                      .setValue(FACING_UP, true)
                        );
                    }

                    if (entity instanceof Player) {
                        makeRoomAround(targetWorld, portalPos, 4, 2, false, PastelBlockTags.BASE_STONE_DEEPER_DOWN);
                    }

                    BlockPos targetPos = portalPos.below(3);
                    entity.changeDimension(new DimensionTransition(
                        targetWorld, Vec3.atCenterOf(targetPos), Vec3.ZERO, entity.getYRot(), entity.getXRot(),
                        DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
                    ));
                    teleportToSafePosition(targetWorld, entity, targetPos.below(), 5);

                    return;
                }
            }

            // => teleport to Overworld
            ServerLevel targetWorld = serverWorld.getServer()
                                                 .getLevel(Level.OVERWORLD);
            if (targetWorld != null) {
                BlockPos portalPos = new BlockPos(pos.getX(), targetWorld.getMinBuildHeight(), pos.getZ());
                if (!targetWorld.getBlockState(portalPos)
                                .is(PastelBlocks.IMBRIFER_PORTAL.get())) {
                    targetWorld.setBlockAndUpdate(
                        portalPos, PastelBlocks.IMBRIFER_PORTAL.get()
                                                                  .defaultBlockState()
                                                                  .setValue(FACING_UP, false)
                    );
                }
                makeRoomAround(targetWorld, portalPos, 4, 2, true, BlockTags.BASE_STONE_OVERWORLD);

                BlockPos targetPos = portalPos.above(2);
                entity.changeDimension(new DimensionTransition(
                    targetWorld, Vec3.atCenterOf(targetPos), Vec3.ZERO, entity.getYRot(), entity.getXRot(),
                    DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
                ));
                teleportToSafePosition(targetWorld, entity, targetPos, 3);
            }
        }
    }

    public void makeRoomAround(
        Level world, BlockPos blockPos, int height, int maxWidth, boolean pointingUp, TagKey<Block> tagToClear) {
        BlockState state = world.getBlockState(blockPos);
        if (state.getCollisionShape(world, blockPos)
                 .isEmpty() && state.getCollisionShape(world, blockPos.above())
                                    .isEmpty()) {
            return;
        }

        for (BlockPos pos : iterateVerticalCone(blockPos, height, maxWidth, pointingUp)) {
            if (world.getBlockEntity(pos) != null) {
                continue;
            }

            state = world.getBlockState(pos);
            if (state.is(Blocks.BEDROCK) || state.is(tagToClear)) {
                world.destroyBlock(pos, true, null);
            }

        }
    }

    public static Iterable<BlockPos> iterateVerticalCone(
        BlockPos center, int height, int maxWidth, boolean pointingUp) {
        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();

        return () -> new AbstractIterator<>() {
            int xOffset = 0;
            int yOffset = 0;
            int zOffset = 0;
            int currentMaxWidth = 0;

            private final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            protected BlockPos computeNext() {
                if (yOffset > height) {
                    return this.endOfData();
                }

                this.pos.set(x + xOffset, pointingUp ? y + yOffset : y - yOffset, z + zOffset);

                zOffset++;
                if (zOffset > currentMaxWidth) {
                    zOffset = -currentMaxWidth;
                    xOffset++;
                    if (xOffset > currentMaxWidth) {
                        xOffset = -currentMaxWidth;
                        yOffset++;
                        currentMaxWidth = Math.min(yOffset, maxWidth);
                    }
                }

                return pos;
            }
        };
    }

    public void teleportToSafePosition(Level world, Entity entity, BlockPos targetPos, int maxRadius) {
        for (BlockPos bp : BlockPos.withinManhattan(targetPos, maxRadius, maxRadius, maxRadius)) {
            entity.setPos(Vec3.atBottomCenterOf(bp));
            if (world.getBlockState(bp.below())
                     .getCollisionShape(world, bp.below()) == Shapes.block()
                && world.noCollision(entity)
                && entity.getY() < (double) world.getMaxBuildHeight()
                && entity.getY() > (double) world.getMinBuildHeight()) {

                entity.teleportTo(bp.getX() + 0.5, bp.getY() + 0.5, bp.getZ() + 0.5);
                return;
            }
        }

        world.removeBlock(targetPos.above(1), false);
        world.removeBlock(targetPos, false);
        world.setBlockAndUpdate(targetPos.below(1), Blocks.COBBLED_DEEPSLATE.defaultBlockState());
        entity.teleportTo(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!state.getValue(DeeperDownPortalBlock.FACING_UP) || random.nextInt(8) == 0) {
            spawnVoidFogParticle(world, pos, random);
        }
    }

    private static void spawnVoidFogParticle(Level world, BlockPos pos, RandomSource random) {
        double d = (double) pos.getX() + random.nextDouble();
        double e = (double) pos.getY() + 0.3D;
        double f = (double) pos.getZ() + random.nextDouble();
        world.addParticle(PastelParticleTypes.VOID_FOG, d, e, f, 0.0D, 0.1D, 0.0D);
    }

}
