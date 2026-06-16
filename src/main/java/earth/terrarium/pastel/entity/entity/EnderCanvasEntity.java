package earth.terrarium.pastel.entity.entity;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.components.EnderSpliceComponent;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.items.magic_items.EnderSpliceItem;
import earth.terrarium.pastel.registries.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.level.NoteBlockEvent;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;

public class EnderCanvasEntity extends HangingEntity implements VariantHolder<EnderCanvasEntity.EnderCanvasVariant> {

    public CanvasWorkaroundPlayerEntity cachedPlayer;
    private static final EntityDataAccessor<EnderSpliceComponent> SPLICE_DATA = SynchedEntityData.defineId(
        EnderCanvasEntity.class,
        PastelTrackedDataHandlers.ENDER_SPLICE_COMPONENT
    );
    private static final EntityDataAccessor<EnderCanvasEntity.EnderCanvasVariant> VARIANT = SynchedEntityData.defineId(
        EnderCanvasEntity.class, PastelTrackedDataHandlers.ENDER_CANVAS_VARIANT);
    public boolean resonant;


    public EnderCanvasEntity(EntityType<? extends EnderCanvasEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(SPLICE_DATA, EnderSpliceComponent.DEFAULT);
        builder.define(VARIANT, EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (VARIANT.equals(key)) {
            this.recalculateBoundingBox();
        }
    }

    public EnderCanvasEntity.EnderCanvasVariant getVariant() {
        return getEntityData().get(VARIANT);
    }

    public void setVariant(EnderCanvasEntity.EnderCanvasVariant variant) {
        getEntityData().set(VARIANT, variant);
    }

    public EnderSpliceComponent getSpliceData() {
        return getEntityData().get(SPLICE_DATA);
    }

    public void setSpliceData(EnderSpliceComponent spliceData) {
        getEntityData().set(SPLICE_DATA, spliceData);
    }

    public static Optional<EnderCanvasEntity> createNew(
        Level level, BlockPos pos, Direction direction,
        EnderSpliceComponent component, EnderCanvasEntity.EnderCanvasVariant variant, boolean resonant
    ) {
        EnderCanvasEntity enderCanvasEntity = new EnderCanvasEntity(PastelEntityTypes.ENDER_CANVAS.get(), level);
        enderCanvasEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
        enderCanvasEntity.setDirection(direction);
        enderCanvasEntity.setSpliceData(component);
        enderCanvasEntity.setVariant(variant);
        enderCanvasEntity.resonant = resonant;
        return Optional.of(enderCanvasEntity);
    }

    @Override
    protected AABB calculateBoundingBox(BlockPos pos, Direction direction) {
        Vec3 relativePos = Vec3.atCenterOf(pos)
                               .relative(this.direction, -0.46875);
        EnderCanvasEntity.EnderCanvasVariant variant = getVariant();
        double xOffset = variant == EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE ? 0.5 : 0;
        Direction xDir = this.direction.getCounterClockWise();
        Vec3 adjustedPos = relativePos.relative(xDir, xOffset)
                                      .relative(Direction.UP, 0.5);
        Direction.Axis axis = this.direction.getAxis();
        double xThickness = axis == Direction.Axis.X ? 0.0625
                                                     : (variant == EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE
                                                        ? 2d : 1d);
        double zThickness = axis == Direction.Axis.Z ? 0.0625
                                                     : (variant == EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE
                                                        ? 2d : 1d);
        return AABB.ofSize(adjustedPos, xThickness, 2d, zThickness);
    }

    @Override
    protected void recalculateBoundingBox() {
        if (this.direction != null) {
            AABB aabb = this.calculateBoundingBox(this.pos, this.direction);
            Vec3 vec3 = aabb.getCenter();
            this.setPosRaw(vec3.x, vec3.y, vec3.z);
            this.setBoundingBox(aabb);
        }
    }

    @Override
    protected void setDirection(Direction facingDirection) {
        Objects.requireNonNull(facingDirection);
        Validate.isTrue(facingDirection.getAxis()
                                       .isHorizontal());
        this.direction = facingDirection;
        this.setYRot((float) (this.direction.get2DDataValue() * 90));
        this.yRotO = this.getYRot();
        this.recalculateBoundingBox();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level()
                .isClientSide()) return;
        EnderCanvasEntity.EnderCanvasVariant variant = getVariant();
        // no teleporting creepers on your friends
        List<? extends Entity> list = (variant == EnderCanvasEntity.EnderCanvasVariant.PORTRAIT)
                                      ? level().getEntitiesOfClass(
            ServerPlayer.class, this.getBoundingBox(), EntitySelector.ENTITY_STILL_ALIVE) : this.level()
                                                                                                .getEntities(
                                                                                                    this,
                                                                                                    this.getBoundingBox(),
                                                                                                    EntitySelector.ENTITY_STILL_ALIVE
                                                                                                );
        if (list.isEmpty()) return;
        Level targetLevel;
        Vec3 targetPos;
        MinecraftServer server = Objects.requireNonNull(getServer());
        EnderSpliceComponent spliceData = getSpliceData();
        if (spliceData.targetGameProfile()
                      .isPresent()) {
            ServerPlayer targetPlayer = server.getPlayerList()
                                              .getPlayer(spliceData.targetGameProfile()
                                                                   .get()
                                                                   .getId());
            // player is probably offline, don't warp to them
            if (targetPlayer == null) return;
            targetLevel = targetPlayer.level();
            targetPos = targetPlayer.position();
        } else if (spliceData.pos()
                             .isPresent() && spliceData.dimension()
                                                       .isPresent()) {
            targetLevel = server.getLevel(spliceData.dimension()
                                                    .get());
            targetPos = spliceData.pos()
                                  .get();
        } else {
            return;
        }
        // we can't warp to a dimension that doesn't exist
        if (targetLevel == null) return;

        boolean canWarp = resonant || PastelCommon.isSameDimension(level(), targetLevel);

        for (Entity entity : list) {
            boolean narcissus = false;
            Vec3i towardsPainting = this.direction.getOpposite()
                                                  .getNormal();
            if (entity.getDeltaMovement()
                      .normalize()
                      .dot(Vec3.atLowerCornerOf(towardsPainting)) >= 0) {
                if (entity instanceof ServerPlayer player) {
                    narcissus = (spliceData.targetGameProfile()
                                           .isPresent() &&
                                 player.getGameProfile()
                                       .equals(
                                           spliceData.targetGameProfile()
                                                     .get()));
                }
                if (entity instanceof ServerPlayer player && (!canWarp || narcissus)) {
                    AdvancementHolder coyoteAdvancement = server.getAdvancements()
                                                                .get(PastelAdvancements.Midgame.RUN_INTO_WALL);
                    if (coyoteAdvancement != null) player.getAdvancements()
                                                         .award(coyoteAdvancement, "run_into_wall");
                }
                if (canWarp && !narcissus)
                    teleportEntityToPos(level(), entity, targetLevel, targetPos);
            }
        }
    }

    private void teleportEntityToPos(Level world, Entity entity, Level targetWorld, Vec3 targetPos) {
        Vec3 currentPos = entity.position();
        Player player = entity instanceof Player playerEntity ? playerEntity : null;
        if (targetWorld instanceof ServerLevel targetServerWorld) {
            world.playSound(
                player, currentPos.x(), currentPos.y(), currentPos.z(), PastelSounds.PLAYER_TELEPORTS,
                SoundSource.PLAYERS, 1.0F, 1.0F
            );

            if (!world.dimension()
                      .equals(targetWorld.dimension())) {
                entity.changeDimension(new DimensionTransition(
                    targetServerWorld, targetPos.add(0, 0.25, 0), new Vec3(0, 0, 0), entity.getYRot(), entity.getXRot(),
                    DimensionTransition.DO_NOTHING
                ));
            } else {
                entity.teleportTo(
                    targetPos.x(), targetPos.y + 0.25, targetPos.z); // +0.25 makes it look way more lively
            }
            world.playSound(
                player, targetPos.x(), targetPos.y, targetPos.z, PastelSounds.PLAYER_TELEPORTS,
                SoundSource.PLAYERS, 1.0F, 1.0F
            );

            // make sure the sound plays even when the player currently teleports
            if (player instanceof ServerPlayer) {
                world.playSound(
                    null, player.blockPosition(), PastelSounds.PLAYER_TELEPORTS, SoundSource.PLAYERS, 1.0F,
                    1.0F
                );
                world.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        } else {
            world.playSound(
                null, currentPos.x(), currentPos.y(), currentPos.z(), PastelSounds.USE_FAIL,
                SoundSource.PLAYERS, 1.0F, 1.0F
            );
        }
    }

    @Override
    public void dropItem(@Nullable Entity brokenEntity) {
        if (this.level()
                .getGameRules()
                .getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
            if (brokenEntity instanceof Player $$1 && $$1.hasInfiniteMaterials()) {
                return;
            }

            var stack = getPickResult();
            if (stack != null)
                this.spawnAtLocation(stack);
        }
    }

    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
    }

    @Override
    public void moveTo(double x, double y, double z, float yaw, float pitch) {
        this.setPos(x, y, z);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.setPos(x, y, z);
    }

    @Override
    public Vec3 trackingPosition() {
        return Vec3.atLowerCornerOf(this.pos);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.setDirection(Direction.from3DDataValue(packet.getData()));
    }

    @Override
    public ItemStack getPickResult() {
        ItemStack stack = new ItemStack(
            PastelItems.ENDER_CANVAS, 1, DataComponentPatch.builder()
                                                           .set(
                                                               PastelDataComponentTypes.ENDER_CANVAS_VARIANT,
                                                               getVariant()
                                                           )
                                                           .set(
                                                               PastelDataComponentTypes.ENDER_SPLICE,
                                                               getSpliceData()
                                                           )
                                                           .build()
        );
        if (resonant)
            stack.enchant(registryAccess().holderOrThrow(PastelEnchantments.RESONANCE), 1);
        return stack;
    }

    @Override
    public boolean save(CompoundTag compound) {
        compound.put(
            "SpliceData", EnderSpliceComponent.CODEC.encodeStart(NbtOps.INSTANCE, getSpliceData())
                                                    .getOrThrow()
        );
        compound.put(
            "Variant", EnderCanvasEntity.EnderCanvasVariant.CODEC.encodeStart(NbtOps.INSTANCE, getVariant())
                                                                 .getOrThrow()
        );
        compound.putBoolean("Resonant", resonant);
        compound.put("Direction", Direction.CODEC.encodeStart(NbtOps.INSTANCE, direction)
                                                 .getOrThrow()
        );
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        setDirection(Direction.CODEC.parse(NbtOps.INSTANCE,compound.get("Direction")).getOrThrow());
        if (compound.contains("SpliceData")) {
            setSpliceData(EnderSpliceComponent.CODEC.parse(NbtOps.INSTANCE, compound.get("SpliceData"))
                                                    .getOrThrow());
        }
        if (compound.contains("Variant")) {
            setVariant(EnderCanvasEntity.EnderCanvasVariant.CODEC.parse(NbtOps.INSTANCE, compound.get("Variant"))
                                                                 .getOrThrow());
        }
        resonant = compound.getBoolean("Resonant");
    }

    public enum EnderCanvasVariant implements StringRepresentable {
        PORTRAIT,
        LANDSCAPESMALL,
        LANDSCAPELARGE;

        @Override
        public String getSerializedName() {
            return this == PORTRAIT ? "portrait" : (this == LANDSCAPESMALL ? "landscapesmall" : "landscapelarge");
        }

        public static final Codec<EnderCanvasVariant> CODEC = StringRepresentable.fromValues(
            EnderCanvasVariant::values);
        public static final IntFunction<EnderCanvasVariant> BY_ID = ByIdMap.continuous(
            EnderCanvasVariant::ordinal, EnderCanvasVariant.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, EnderCanvasVariant> STREAM_CODEC = ByteBufCodecs.idMapper(
            BY_ID, EnderCanvasVariant::ordinal);
    }

}
