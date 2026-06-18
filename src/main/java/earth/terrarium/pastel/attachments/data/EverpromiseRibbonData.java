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

public class EverpromiseRibbonData {

    public static final AttachmentType<Boolean> ATTACHMENT = AttachmentType
        .builder(() -> false)
        .serialize(Codec.BOOL)
        .build();

    public static void attachRibbon(LivingEntity livingEntity) {
        livingEntity.setData(ATTACHMENT, true);
    }

    public static boolean hasRibbon(LivingEntity livingEntity) {
        return livingEntity.getData(ATTACHMENT);
    }

    public record Payload(int entityId, boolean ribbon) implements CustomPacketPayload {

        public static final StreamCodec<FriendlyByteBuf, Payload> CODEC = StreamCodec
            .composite(
                ByteBufCodecs.INT,
                Payload::entityId,
                ByteBufCodecs.BOOL,
                Payload::ribbon,
                Payload::new
            );

        public static final Type<Payload> TYPE = AttachmentUtil.create("ribbon");

        public static void execute(Payload payload, IPayloadContext context) {
            var level = context
                .player()
                .level();
            Optional
                .ofNullable(level.getEntity(payload.entityId))
                .ifPresent(e -> e.setData(ATTACHMENT, payload.ribbon));
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
