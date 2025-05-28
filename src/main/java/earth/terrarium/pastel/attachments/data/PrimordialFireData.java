package earth.terrarium.pastel.attachments.data;

import com.mojang.serialization.*;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.helpers.SpectrumEnchantmentHelper;
import earth.terrarium.pastel.registries.SpectrumDamageTypes;
import earth.terrarium.pastel.registries.SpectrumEntityTypeTags;
import earth.terrarium.pastel.sound.OnPrimordialFireSoundInstance;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.network.handling.*;

import java.util.Optional;

public class PrimordialFireData {

	public static final AttachmentType<Long> ATTACHMENT =
			AttachmentType.builder(() -> 0L).serialize(Codec.LONG).build();

	// 1% of max health as damage every tick as a base.
	public static final float BASE_PERCENT_DAMAGE = 0.01F;

	// Base damage reduction applied by fire resistance
	public static final float FIRE_RESISTANCE_DAMAGE_RESISTANCE = 0.25F;
	// Per-level damage reduction added by fire prot. Caps at 50%
	public static final float FIRE_PROT_DAMAGE_RESISTANCE = 0.05F;
	
	@OnlyIn(Dist.CLIENT)
	private static Optional<OnPrimordialFireSoundInstance> soundInstance;
	
	/* prevent the static initializer from attempting to write to the client-only field in common code */
	static {
		if (EffectiveSide.get().isClient()) soundInstance = Optional.empty();
	}

	private static void sync(LivingEntity entity) {
		AttachmentUtil.syncToTracking(new Payload(entity.getId(), entity.getData(ATTACHMENT)), entity.level(), entity.blockPosition());
	}

	public static void setPrimordialFireTicks(LivingEntity entity, long ticks) {
		entity.setData(ATTACHMENT, ticks);
		sync(entity);
	}

	public static void addPrimordialFireTicks(LivingEntity entity, int ticks) {
		int i = SpectrumEnchantmentHelper.getEquipmentLevel(entity.level().registryAccess(), Enchantments.FIRE_PROTECTION, entity);
		if (i > 0) {
			ticks -= Mth.floor(ticks * i * 0.15F);
		}

		entity.setData( ATTACHMENT, entity.getData(ATTACHMENT) + ticks);
		sync(entity);
	}
	
	public static boolean isOnPrimordialFire(LivingEntity entity) {
		return entity.getData(ATTACHMENT) > 0;
	}
	
	public static boolean putOut(LivingEntity entity) {
		if (entity.getData(ATTACHMENT) == 0)
			return false;

		entity.setData(ATTACHMENT, 0L);
		sync(entity);
		return true;
	}

	public static void serverTick(LivingEntity entity) {
		long primordialFireTicks = entity.getData(ATTACHMENT);

		if (primordialFireTicks == 0)
			return;

		//Immune creatures get spared. If we ever add any.
		if (entity.getType().is(SpectrumEntityTypeTags.PRIMORDIAL_FIRE_IMMUNE)) {
			entity.setData(ATTACHMENT, 0L);
			return;
		}

		if (!isAffectingConstruct(entity)) {
			var damageScaling = getDamageHealthScaling(entity);
			entity.hurt(SpectrumDamageTypes.primordialFire(entity.level()), AzureDikeProvider.absorbDamage(entity, damageScaling * entity.getMaxHealth()));
		}
		//Primordial fire is so strong because it rends the soul. No soul = just slightly spicier fire
		//Constructs have no soul, thus you get 2 dps and no more
		else if (entity.tickCount % 10 == 0) {
			entity.hurt(SpectrumDamageTypes.primordialFire(entity.level()), 1);
		}


		primordialFireTicks -= entity.getFluidHeight(FluidTags.WATER) > 0 ? 3 : 1;
		// was on fire, but is not any longer

		entity.setData(ATTACHMENT, primordialFireTicks);
		if (primordialFireTicks <= 0) {
			sync(entity);
		}
	}

	public static boolean isAffectingConstruct(LivingEntity entity) {
		return entity.getType().is(SpectrumEntityTypeTags.SOULLESS);
	}

	/**
	 * Primordial fire's base DPS is 1/t, for a kill time of 5 seconds on a base hp player.
	 */
	public static float getDamageHealthScaling(LivingEntity entity) {
		float baseDamage = BASE_PERCENT_DAMAGE;

		//Bosses have great and exceptional souls that can resist a lot more.
		//95% less damage to them before reductions and caps
		if (entity.getType().is(Tags.EntityTypes.BOSSES))
			baseDamage /= 20F;

        return baseDamage * getDamagePenalties(entity) * getDamageBonuses(entity);
	}

	public static float getDamagePenalties(LivingEntity entity) {
		//fire prot has a cap of 50% DR, requiring fire protection 10 on an armor piece
		float fireProt = Math.min(FIRE_PROT_DAMAGE_RESISTANCE * SpectrumEnchantmentHelper.getEquipmentLevel(entity.level().registryAccess(), Enchantments.FIRE_PROTECTION, entity), 0.5F);
		int fireResLevel = Optional.ofNullable(entity.getEffect(MobEffects.FIRE_RESISTANCE)).map(MobEffectInstance::getAmplifier).orElse(-1) + 1;
		float fireRes = 0;

		// flat 25% for a start on fire res
		if (fireResLevel > 0)
			fireRes = FIRE_RESISTANCE_DAMAGE_RESISTANCE;

		//Fire resistance has diminishing returns
		for (int i = 1; i < fireResLevel; i++) {
			fireRes += (float) (0.05 * (i) + (0.25F * Math.pow(0.5F, i)));
		}

		//Fire immune entities can have a lil res, as a treat
		float immunityReduction = entity.fireImmune() ? 0.25F : 0;

		//Primordial fire has an overall cap of 90% DR
		return Math.max(1 - (fireRes + fireProt + immunityReduction), 0.10F);
	}

	/**
	 * Here for completeness.
	 * <p>
	 * Unused... for now...
	 */
	public static float getDamageBonuses(LivingEntity entity) {
		return 1F;
	}

	@OnlyIn(Dist.CLIENT)
	public static void clientTick(LivingEntity entity) {
		var primordialFireTicks = entity.getData(ATTACHMENT);

		if (primordialFireTicks > 0) {
			if (entity.equals(Minecraft.getInstance().player) && primordialFireTicks > 2 && soundInstance.isEmpty()) {
				soundInstance = Optional.of(new OnPrimordialFireSoundInstance((Player) entity));
				Minecraft.getInstance().getSoundManager().play(soundInstance.get());
			}

			double fluidHeight = entity.getFluidHeight(FluidTags.WATER);
			if (fluidHeight > 0) {

				Level world = entity.level();
				RandomSource random = world.random;
				Vec3 pos = entity.position();

				for (int i = 0; i < 2; i++) {
					world.addParticle(ParticleTypes.BUBBLE_POP, entity.getRandomX(1), pos.y() + Math.min(fluidHeight, entity.getBbHeight()) * random.nextFloat(), entity.getRandomZ(1), 0.0, 0.04, 0.0);
					world.addParticle(ParticleTypes.SMOKE, entity.getRandomX(1), pos.y() + Math.min(fluidHeight, entity.getBbHeight()) * random.nextFloat(), entity.getRandomZ(1), 0.0, 0.04, 0.0);
				}
				if (world.random.nextInt(12) == 0) {
					entity.playSound(SoundEvents.FIRE_EXTINGUISH, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F);
				}
			}
		} else if (entity.equals(Minecraft.getInstance().player) && soundInstance.isPresent()) {
			soundInstance = Optional.empty();
		}
	}

	public record Payload(int entityId, long burnTicks) implements CustomPacketPayload {

		public static final StreamCodec<FriendlyByteBuf, Payload> CODEC = StreamCodec.composite(
				ByteBufCodecs.INT, Payload::entityId,
				ByteBufCodecs.VAR_LONG, Payload::burnTicks,
				Payload::new);

		public static final CustomPacketPayload.Type<Payload> TYPE = AttachmentUtil.create("primfire");

		@OnlyIn(Dist.CLIENT)
		public static void execute(Payload payload, IPayloadContext context) {
			var level = context.player().level();
			Optional.ofNullable(level.getEntity(payload.entityId)).ifPresent(e -> e.setData(ATTACHMENT, payload.burnTicks));
		}

		@Override
		public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}
}
