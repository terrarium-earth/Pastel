package de.dafuqs.spectrum.attachments.data.azure_dike;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.attachments.data.*;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.network.handling.*;

import java.util.*;

// TODO: The azure dike impl is dogshit. Rewrite ASAP.
public class AzureDikeData implements DikeShieldData {

	public static final Codec<AzureDikeData> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.FLOAT.fieldOf("maxProt").forGetter(AzureDikeData::getMaxProtection),
			Codec.FLOAT.fieldOf("currentProt").forGetter(AzureDikeData::getCurrentProtection),
			Codec.INT.fieldOf("rechargeTicks").forGetter(AzureDikeData::getTicksPerPointOfRecharge),
			Codec.INT.fieldOf("rechargeDelayPostHit").forGetter(AzureDikeData::getRechargeDelayTicksAfterGettingHit),
			Codec.INT.fieldOf("rechargeDelay").forGetter(AzureDikeData::getCurrentRechargeDelay)
	).apply(i, AzureDikeData::new));

	public static final IAttachmentCopyHandler<AzureDikeData> CLONER = (dike, holder, provider) -> {
		var newDike = new AzureDikeData();
		newDike.maxProt = dike.maxProt;
		newDike.rechargeTicks = dike.rechargeTicks;
		newDike.rechargeDelayPostTick = dike.rechargeDelayPostTick;
		return newDike;
	};

	public static final AttachmentType<AzureDikeData> ATTACHMENT =
			AttachmentType.builder(AzureDikeData::new).serialize(CODEC).copyOnDeath().copyHandler(CLONER).build();

	public final static int BASE_RECHARGE_DELAY_TICKS = 40;
	public final static int BASE_RECHARGE_DELAY_TICKS_AFTER_DAMAGE = 200;

	private float maxProt = 0;
	private int rechargeTicks = 0;
	private int rechargeDelayPostTick = 0;

	private float currentProt = 0;
	private int rechargeDelay = 0;

	public AzureDikeData() {}

	public AzureDikeData(float maxProt, float currentProt, int rechargeTicks, int rechargeDelayPostTick, int rechargeDelay) {
		this.maxProt = maxProt;
		this.rechargeTicks = rechargeTicks;
		this.rechargeDelayPostTick = rechargeDelayPostTick;
		this.currentProt = currentProt;
		this.rechargeDelay = rechargeDelay;
	}
	
	@Override
	public float getCurrentProtection() {
		return this.currentProt;
	}
	
	@Override
	public float getMaxProtection() {
		return this.maxProt;
	}
	
	@Override
	public int getTicksPerPointOfRecharge() {
		return this.rechargeTicks;
	}
	
	@Override
	public int getCurrentRechargeDelay() {
		return this.rechargeDelay;
	}
	
	@Override
	public int getRechargeDelayTicksAfterGettingHit() {
		return this.rechargeDelayPostTick;
	}
	
	@Override
	public float absorbDamage(float incomingDamage) {
		if (incomingDamage == 0)
			return 0;

		this.rechargeDelay = this.rechargeDelayPostTick;
		if (this.currentProt > 0) {
			float absorbedDamage = Math.min(currentProt, incomingDamage);
			this.currentProt -= absorbedDamage;
			
			return incomingDamage - absorbedDamage;
		} else {
			return incomingDamage;
		}
	}
	
	@Override
	public void set(float maxProtection, int rechargeDelayDefault, int fasterRechargeAfterDamageTicks, boolean resetCharge) {
		this.maxProt = maxProtection;
		this.rechargeTicks = rechargeDelayDefault;
		this.rechargeDelayPostTick = fasterRechargeAfterDamageTicks;
		this.rechargeDelay = this.rechargeTicks;
		if (resetCharge) {
			this.currentProt = 0;
		} else {
			this.currentProt = Math.min(this.currentProt, this.maxProt);
		}
	}

	public void serverTick(LivingEntity provider) {
		if (this.rechargeDelay > 0) {
			this.rechargeDelay--;

		} else if (this.currentProt < this.maxProt) {
			currentProt = Math.min(maxProt, currentProt + 1);
			this.rechargeDelay = this.rechargeTicks;

			AttachmentUtil.syncToTracking(new Payload(provider.getId(), this), provider.level(), provider.blockPosition());
			if (provider instanceof ServerPlayer serverPlayerEntity) {
				SpectrumAdvancementCriteria.AZURE_DIKE_CHARGE.trigger(serverPlayerEntity, this.currentProt, this.rechargeTicks, 1);
			}
		}
	}

	public record Payload(int entityId, float maxProt, float currentProt, int rechargeTicks, int rechargeDelayPostTick, int rechargeDelay) implements CustomPacketPayload {

		public Payload(int entityId, AzureDikeData attachment) {
			this(entityId, attachment.maxProt, attachment.currentProt, attachment.rechargeTicks, attachment.rechargeDelayPostTick, attachment.rechargeDelay);
		}

		public static final StreamCodec<FriendlyByteBuf, Payload> CODEC = StreamCodec.composite(
				ByteBufCodecs.INT, Payload::entityId,
				ByteBufCodecs.FLOAT, Payload::maxProt,
				ByteBufCodecs.FLOAT, Payload::currentProt,
				ByteBufCodecs.INT, Payload::rechargeTicks,
				ByteBufCodecs.INT, Payload::rechargeDelayPostTick,
				ByteBufCodecs.INT, Payload::rechargeDelay,
				Payload::new
		);

		public static final CustomPacketPayload.Type<Payload> TYPE = AttachmentUtil.create("dike");

		public static void execute(Payload payload, IPayloadContext context) {
			var level = context.player().level();
			Optional.ofNullable(level.getEntity(payload.entityId)).ifPresent(e -> e.setData(ATTACHMENT, new AzureDikeData(
					payload.maxProt, payload.currentProt, payload.rechargeTicks, payload.rechargeDelayPostTick(), payload.rechargeDelay
			)));
		}

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}
}
