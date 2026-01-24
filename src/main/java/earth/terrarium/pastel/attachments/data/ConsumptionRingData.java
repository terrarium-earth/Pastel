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

public class ConsumptionRingData {
    // same as the jeopardant, this will be checked every time an entity is hurt by a player, so we don't want to
    // iterate over the entire curios inv every time something takes damage
    public static final AttachmentType<Boolean> ATTACHMENT = AttachmentType.builder(() -> false)
                                                                           .serialize(Codec.BOOL)
                                                                           .build();
    public static void setHasRing(LivingEntity entity,boolean hasRing){
        entity.setData(ATTACHMENT,hasRing);
        sync(entity);
    }

    private static void sync(LivingEntity entity) {
        AttachmentUtil.syncToTracking(
            new Payload(entity.getId(), entity.getData(ATTACHMENT)), entity.level(), entity.blockPosition());
    }

    public record Payload(int entityId, boolean hasRing) implements CustomPacketPayload {

        public static final StreamCodec<FriendlyByteBuf, Payload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            Payload::entityId,
            ByteBufCodecs.BOOL,
            Payload::hasRing,
            Payload::new
        );

        public static final CustomPacketPayload.Type<Payload> TYPE = AttachmentUtil.create("ring_of_consumption");

        public static void execute(Payload payload, IPayloadContext context) {
            var level = context.player()
                               .level();
            Optional.ofNullable(level.getEntity(payload.entityId))
                    .ifPresent(e -> e.setData(ATTACHMENT, payload.hasRing));
        }

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
