package de.dafuqs.spectrum.attachments.data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.entity.PlayerEntityAccessor;
import de.dafuqs.spectrum.api.item.SleepAlteringItem;
import de.dafuqs.spectrum.networking.s2c_payloads.SyncMentalPresencePayload;
import de.dafuqs.spectrum.registries.SpectrumEntityAttributes;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.network.handling.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Because not every niche thing can have its own component
 */
@SuppressWarnings("unchecked")
public class MiscPlayerData {

    public static final Codec<MiscPlayerData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("ticksBeforeSleep").forGetter(m -> m.ticksBeforeSleep),
            Codec.INT.fieldOf("sleepingWindow").forGetter(m -> m.sleepingWindow),
            Codec.INT.fieldOf("sleepInvincibility").forGetter(m -> m.sleepInvincibility),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("sleepConsumable").forGetter(m -> (Optional<Item>) (Object) m.sleepConsumable)
    ).apply(i, MiscPlayerData::ofCodec));

    public static final AttachmentType<MiscPlayerData> ATTACHMENT =
            AttachmentType.builder((holder) -> new MiscPlayerData((Player) holder)).serialize(CODEC).build();

    private Player player;

    // Sleep
    private int ticksBeforeSleep = -1, sleepingWindow = -1, sleepInvincibility;
    private double lastSyncedSleepPotency = -2;
    private Optional<SleepAlteringItem> sleepConsumable = Optional.empty();

    // Sword mechanics
    private boolean isLunging, bHopWindow, perfectCounter;
    private int parryTicks;

    public MiscPlayerData(@NotNull Player player) {
        this.player = player;
    }

    private MiscPlayerData() {}

    public static MiscPlayerData ofCodec(int ticksBeforeSleep, int sleepingWindow, int sleepInvincibility, Optional<Item> sleepConsumable) {
        var data = new MiscPlayerData();
        data.ticksBeforeSleep = ticksBeforeSleep;
        data.sleepingWindow = sleepingWindow;
        data.sleepInvincibility = sleepInvincibility;

        data.sleepConsumable = (Optional<SleepAlteringItem>) (Object) sleepConsumable;
        return data;
    }

    public void tick() {
        tickSleep();
        tickSwordMechanics();

        if (!player.level().isClientSide()) {
            var fortitude = player.getAttributeValue(SpectrumEntityAttributes.MENTAL_PRESENCE);
            if (lastSyncedSleepPotency != fortitude) {
                lastSyncedSleepPotency = fortitude;
                SyncMentalPresencePayload.sendMentalPresenceSync((ServerPlayer) player, fortitude);
            }
        }
    }

    private boolean isInModifiedMotionState() {
        return player.onGround() || player.isSwimming() || player.isFallFlying() || player.getAbilities().flying;
    }

    public void initiateLungeState() {
        isLunging = true;
        bHopWindow = true;
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
        }
        else if (parryTicks == 1) {
            parryTicks = 0;
            consumePerfectCounter();
        }

        if (!bHopWindow && isLunging) {
            if (isInModifiedMotionState()) {
                isLunging = false;
            }
            else {
                bHopWindow = true;
            }
        }
        else if (isLunging && isInModifiedMotionState()) {
            bHopWindow = false;
        }
    }

    public float getFrictionModifiers() {
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
        if (!player.level().isClientSide()) {
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
        AttachmentUtil.syncToTracking(new Payload(player.getUUID(), ticksBeforeSleep, sleepingWindow, sleepInvincibility, (Optional<Item>) (Object) sleepConsumable),player.level(), player.blockPosition());
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

    public record Payload(UUID id, int ticksBeforeSleep, int sleepingWindow, int sleepInvincibility, Optional<Item> sleepConsumable) implements CustomPacketPayload {

        public static final StreamCodec<RegistryFriendlyByteBuf, Payload> CODEC = StreamCodec.composite(
                UUIDUtil.STREAM_CODEC, Payload::id,
                ByteBufCodecs.INT, Payload::ticksBeforeSleep,
                ByteBufCodecs.INT, Payload::sleepingWindow,
                ByteBufCodecs.INT, Payload::sleepInvincibility,
                ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ITEM)), Payload::sleepConsumable,
                Payload::new
        );

        public static final Type<Payload> TYPE = AttachmentUtil.create("playerMisc");

        public static void execute(Payload payload, IPayloadContext context) {
            var player = context.player().level().getPlayerByUUID(payload.id);

            if (player == null)
                return;

            var data = player.getData(ATTACHMENT);
            data.ticksBeforeSleep = payload.ticksBeforeSleep();
            data.sleepingWindow = payload.sleepingWindow();
            data.sleepInvincibility = payload.sleepInvincibility();
            data.sleepConsumable = (Optional<SleepAlteringItem>) (Object) payload.sleepConsumable;
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
