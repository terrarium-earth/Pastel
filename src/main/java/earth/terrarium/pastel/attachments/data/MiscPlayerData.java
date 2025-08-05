package earth.terrarium.pastel.attachments.data;

import com.cmdpro.databank.misc.ColorGradient;
import com.cmdpro.databank.misc.TrailRender;
import com.cmdpro.databank.rendering.RenderHandler;
import com.cmdpro.databank.rendering.RenderTypeHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.entity.PlayerEntityAccessor;
import earth.terrarium.pastel.api.item.HasColorGradient;
import earth.terrarium.pastel.api.item.SleepAlteringItem;
import earth.terrarium.pastel.networking.s2c_payloads.SyncMentalPresencePayload;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

/**
 * Because not every niche thing can have its own component
 */
@SuppressWarnings("unchecked")
public class MiscPlayerData {

    public static final Codec<MiscPlayerData> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                          Codec.INT.fieldOf(
                                                                                              "ticksBeforeSleep")
                                                                                                   .forGetter(m -> m.ticksBeforeSleep),
                                                                                          Codec.INT.fieldOf(
                                                                                              "sleepingWindow")
                                                                                                   .forGetter(m -> m.sleepingWindow),
                                                                                          Codec.INT.fieldOf(
                                                                                              "sleepInvincibility")
                                                                                                   .forGetter(m -> m.sleepInvincibility),
                                                                                          BuiltInRegistries.ITEM.byNameCodec()
                                                                                                                .optionalFieldOf("sleepConsumable")
                                                                                                                .forGetter(m -> (Optional<Item>) (Object) m.sleepConsumable)
                                                                                      )
                                                                                      .apply(
                                                                                          i,
                                                                                          MiscPlayerData::ofCodec
                                                                                      ));

    public static final AttachmentType<MiscPlayerData> ATTACHMENT =
        AttachmentType.builder((holder) -> new MiscPlayerData((Player) holder))
                      .serialize(CODEC)
                      .build();

    private Player player;

    // Sleep
    private int ticksBeforeSleep = -1, sleepingWindow = -1, sleepInvincibility;
    private double lastSyncedSleepPotency = -2;
    private Optional<SleepAlteringItem> sleepConsumable = Optional.empty();

    // Sword mechanics
    private boolean isLunging, wasLunging, bHopWindow, perfectCounter;
    private Item lungeItem;
    private int parryTicks;

    public MiscPlayerData(@NotNull Player player) {
        this.player = player;
    }

    private MiscPlayerData() {
    }

    public static MiscPlayerData ofCodec(
        int ticksBeforeSleep, int sleepingWindow, int sleepInvincibility, Optional<Item> sleepConsumable) {
        var data = new MiscPlayerData();
        data.ticksBeforeSleep = ticksBeforeSleep;
        data.sleepingWindow = sleepingWindow;
        data.sleepInvincibility = sleepInvincibility;

        data.sleepConsumable = (Optional<SleepAlteringItem>) (Object) sleepConsumable;
        return data;
    }

    private TrailRender lungeTrail;
    public TrailRender getLungeTrail() {
        if (lungeTrail == null) {
            lungeTrail = new TrailRender(player.getBoundingBox().getCenter(), 10, 10, 0.2f, PastelCommon.locate("textures/misc/trail/trail.png"),
                                    RenderTypeHandler::transparent
            ).setShrink(true);
        }
        return lungeTrail;
    }

    public void tick() {
        tickSleep();
        tickSwordMechanics();

        if (!player.level()
                   .isClientSide()) {
            var fortitude = player.getAttributeValue(PastelEntityAttributes.MENTAL_PRESENCE);
            if (lastSyncedSleepPotency != fortitude) {
                lastSyncedSleepPotency = fortitude;
                SyncMentalPresencePayload.sendMentalPresenceSync((ServerPlayer) player, fortitude);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void renderAdditional(RenderLevelStageEvent event) {
        PoseStack poseStack = event.getPoseStack();
        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(true);
        ColorGradient lungeGradient = null;
        if (lungeItem instanceof HasColorGradient hasColorGradient) {
            lungeGradient = hasColorGradient.getColorGradient(HasColorGradient.LUNGE);
        }
        if (lungeGradient != null) {
            TrailRender lungeTrail = getLungeTrail();
            poseStack.pushPose();
            poseStack.translate(-event.getCamera().getPosition().x, -event.getCamera().getPosition().y, -event.getCamera().getPosition().z);
            double d0 = Mth.lerp(partialTick, player.xOld, player.getX());
            double d1 = Mth.lerp(partialTick, player.yOld, player.getY());
            double d2 = Mth.lerp(partialTick, player.zOld, player.getZ());
            if (isLunging) {
                lungeTrail.position = new Vec3(d0, d1 + (player.getBoundingBox().getYsize() / 2f), d2);
            }
            lungeTrail.render(poseStack, RenderHandler.createBufferSource(), LightTexture.FULL_BRIGHT, lungeGradient);
            poseStack.popPose();
        }
    }

    private boolean isInModifiedMotionState() {
        return player.onGround() || player.isSwimming() || player.isFallFlying() || player.getAbilities().flying;
    }

    public void initiateLungeState(ItemLike item) {
        isLunging = true;
        bHopWindow = true;
        lungeItem = item.asItem();
    }

    public void endLunge() {
        isLunging = false;
        bHopWindow = false;
    }

    public boolean isLunging() {
        return isLunging;
    }

    public void setParryTicks(int ticks) {
        parryTicks = ticks;
    }

    public void markForPerfectCounter() {
        perfectCounter = true;
    }

    public boolean consumePerfectCounter() {
        if (perfectCounter) {
            perfectCounter = false;
            return true;
        }

        return false;
    }

    public boolean isParrying() {
        return parryTicks > 0;
    }

    private void tickSwordMechanics() {
        if (parryTicks > 1) {
            parryTicks--;
        } else if (parryTicks == 1) {
            parryTicks = 0;
            consumePerfectCounter();
        }

        if (!bHopWindow && isLunging) {
            if (isInModifiedMotionState()) {
                isLunging = false;
            } else {
                bHopWindow = true;
            }
        } else if (isLunging && isInModifiedMotionState()) {
            bHopWindow = false;
        }
        if (!wasLunging && isLunging) {
            if (player.level().isClientSide) {
                TrailRender lungeTrail = getLungeTrail();
                if (lungeTrail != null) {
                    lungeTrail.reset();
                    lungeTrail.position = player.getBoundingBox().getCenter();
                }
            }
        }
        wasLunging = isLunging;
        if (player.level().isClientSide) {
            getLungeTrail().tick();
        }
    }

    public float getFrictionModifiers() {
        var hookData = HookshotData.get(player);
        if (hookData.getFrictionModifier() > 0)
            return hookData.getFrictionModifier();

        return isLunging ? 0.04F : 0F;

    }

    private void tickSleep() {
        if (ticksBeforeSleep > 0) {
            ticksBeforeSleep--;

            if (ticksBeforeSleep == 0) {
                player.startSleeping(player.blockPosition());
                ((PlayerEntityAccessor) player).setSleepTimer(0);
                var world = player.level();
                if (!world.isClientSide()) {
                    ((ServerLevel) world).updateSleepingPlayerList();
                    sync();
                }
            }
        }

        if (sleepInvincibility > 0) {
            sleepInvincibility--;
        }

        if (ticksBeforeSleep != 0)
            return;

        if (sleepingWindow > 0) {
            sleepingWindow--;
            if (sleepingWindow == 0) {
                failSleep();
            }
        }
    }

    private void failSleep() {
        if (!player.level()
                   .isClientSide()) {
            player.stopSleeping();
            resetSleepingState(true);
        }
    }

    public boolean isSleeping() {
        return ticksBeforeSleep == 0 && sleepingWindow > 0;
    }

    public boolean shouldLieDown() {
        return ticksBeforeSleep > 0;
    }

    public void notifyHit() {
        if (sleepInvincibility <= 0) {
            resetSleepingState(true);
        }
    }

    public void resetSleepingState(boolean canApplyPenalties) {
        if (ticksBeforeSleep == -1)
            return;

        ticksBeforeSleep = -1;
        sleepingWindow = -1;
        sleepInvincibility = -1;

        if (canApplyPenalties)
            sleepConsumable.ifPresent(p -> p.applyPenalties(player));

        sleepConsumable = Optional.empty();
        sync();
    }

    public void setSleepTimers(int wait, int window, int invulnTicks) {
        ticksBeforeSleep = wait;
        sleepingWindow = window;
        sleepInvincibility = invulnTicks;
        sync();
    }

    public void setLastSleepItem(@NotNull SleepAlteringItem item) {
        this.sleepConsumable = Optional.of(item);
    }

    public void sync() {
        AttachmentUtil.syncToTracking(
            new Payload(
                player.getUUID(), ticksBeforeSleep, sleepingWindow, sleepInvincibility,
                (Optional<Item>) (Object) sleepConsumable, new LungeData(isLunging, Optional.ofNullable(lungeItem))
            ), player.level(), player.blockPosition()
        );
    }

    public static MiscPlayerData get(@NotNull Player player) {
        var data = player.getData(ATTACHMENT);
        if (data.player == null)
            data.player = player;

        return data;
    }

    public void setLastSyncedSleepPotency(double lastSyncedSleepPotency) {
        this.lastSyncedSleepPotency = lastSyncedSleepPotency;
    }

    public double getLastSyncedSleepPotency() {
        return lastSyncedSleepPotency;
    }

    public record Payload(
        UUID id, int ticksBeforeSleep, int sleepingWindow, int sleepInvincibility, Optional<Item> sleepConsumable, LungeData lungeData
    ) implements CustomPacketPayload {

        public static final StreamCodec<RegistryFriendlyByteBuf, Payload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, Payload::id,
            ByteBufCodecs.INT, Payload::ticksBeforeSleep,
            ByteBufCodecs.INT, Payload::sleepingWindow,
            ByteBufCodecs.INT, Payload::sleepInvincibility,
            ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ITEM)), Payload::sleepConsumable,
            LungeData.CODEC, Payload::lungeData,
            Payload::new
        );

        public static final Type<Payload> TYPE = AttachmentUtil.create("player_misc");

        public static void execute(Payload payload, IPayloadContext context) {
            var player = context.player()
                                .level()
                                .getPlayerByUUID(payload.id);

            if (player == null)
                return;

            var data = player.getData(ATTACHMENT);
            data.ticksBeforeSleep = payload.ticksBeforeSleep();
            data.sleepingWindow = payload.sleepingWindow();
            data.sleepInvincibility = payload.sleepInvincibility();
            data.sleepConsumable = (Optional<SleepAlteringItem>) (Object) payload.sleepConsumable;
            data.isLunging = payload.lungeData.isLunging;
            data.lungeItem = payload.lungeData.lungeItem.orElse(null);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
    public record LungeData(boolean isLunging, Optional<Item> lungeItem) {
        public static final StreamCodec<RegistryFriendlyByteBuf, LungeData> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, LungeData::isLunging,
            ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ITEM)), LungeData::lungeItem,
            LungeData::new
        );
    }
}
