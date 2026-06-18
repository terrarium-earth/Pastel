package earth.terrarium.pastel.attachments.data;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public class LastKillData {

    public static final AttachmentType<Long> ATTACHMENT = AttachmentType
        .builder(() -> 0L)
        .serialize(Codec.LONG)
        .build();

    public static void rememberKillTick(LivingEntity livingEntity, long tick) {
        if (tick == livingEntity.getData(ATTACHMENT)) return; // ratelimit to prevent piercing projectiles from obliterating your tps with packets
        livingEntity.setData(ATTACHMENT, tick);
        AttachmentUtil
            .syncToTracking(
                new Payload(livingEntity.getId(), tick),
                livingEntity.level(),
                livingEntity.blockPosition()
            );
    }

    public static long getLastKillTick(LivingEntity livingEntity) {
        return livingEntity.getData(ATTACHMENT);
    }

    public record Payload(int entityId, long killTime) implements CustomPacketPayload {

        public static final StreamCodec<FriendlyByteBuf, Payload> CODEC = StreamCodec
            .composite(
                ByteBufCodecs.INT,
                Payload::entityId,
                ByteBufCodecs.VAR_LONG,
                Payload::killTime,
                Payload::new
            );

        public static final CustomPacketPayload.Type<Payload> TYPE = AttachmentUtil.create("last_kill");

        public static void execute(Payload payload, IPayloadContext context) {
            var level = context
                .player()
                .level();
            Optional
                .ofNullable(level.getEntity(payload.entityId))
                .ifPresent(e -> e.setData(ATTACHMENT, payload.killTime));
        }

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
