package earth.terrarium.pastel.attachments.data;

import com.mojang.serialization.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.network.handling.*;

import java.util.*;

public class LastKillData {
	
	public static final AttachmentType<Long> ATTACHMENT =
			AttachmentType.builder(() -> 0L).serialize(Codec.LONG).build();
	
	public static void rememberKillTick(LivingEntity livingEntity, long tick) {
		livingEntity.setData(ATTACHMENT, tick);
		AttachmentUtil.syncToTracking(new Payload(livingEntity.getId(), tick), livingEntity.level(), livingEntity.blockPosition());
	}
	
	public static long getLastKillTick(LivingEntity livingEntity) {
		return livingEntity.getData(ATTACHMENT);
	}

	public record Payload(int entityId, long killTime) implements CustomPacketPayload {

		public static final StreamCodec<FriendlyByteBuf, Payload> CODEC = StreamCodec.composite(
				ByteBufCodecs.INT, Payload::entityId,
				ByteBufCodecs.VAR_LONG, Payload::killTime,
				Payload::new);

		public static final CustomPacketPayload.Type<Payload> TYPE = AttachmentUtil.create("lastKill");

		@OnlyIn(Dist.CLIENT)
		public static void execute(Payload payload, IPayloadContext context) {
			var level = context.player().level();
			Optional.ofNullable(level.getEntity(payload.entityId)).ifPresent(e -> e.setData(ATTACHMENT, payload.killTime));
		}

		@Override
		public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}
}
