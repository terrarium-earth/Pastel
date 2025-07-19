package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.registries.PastelFluidTags;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeepingCircletItem extends PastelTrinketItem {
	
	private final static int TRIGGER_EVERY_X_TICKS = 40;
	private final static int EFFECT_DURATION = TRIGGER_EVERY_X_TICKS + 10;
	
	private final static int HEAL_AXOLOTLS_EVERY_X_TICKS = 160;
	private final static int MAX_AXOLOTL_DISTANCE = 12;
	private final static int AXOLOTL_HEALING = 2;
	
	public WeepingCircletItem(Properties settings) {
		super(settings, PastelCommon.locate("unlocks/trinkets/weeping_circlet"));
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.weeping_circlet.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.weeping_circlet.tooltip2").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.weeping_circlet.tooltip3").withStyle(ChatFormatting.GRAY));
	}

	@Override
	public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
		super.onEquip(slotContext, prevStack, stack);
		doEffects(slotContext.entity(), true);
	}
	
	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		super.curioTick(slotContext, stack);
		doEffects(slotContext.entity(), false);
	}
	
	private void doEffects(LivingEntity entity, boolean always) {
		Level world = entity.level();
		if (!world.isClientSide) {
			long time = entity.level().getGameTime();
			if (entity.getEyeInFluidType().canSwim(entity)) {
				if (always || time % TRIGGER_EVERY_X_TICKS == 0) {
					entity.setAirSupply(entity.getMaxAirSupply());
					entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, EFFECT_DURATION, 1, true, true));
					entity.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, EFFECT_DURATION, 0, true, true));
				}
				if ((always || time % HEAL_AXOLOTLS_EVERY_X_TICKS == 0) && entity instanceof ServerPlayer serverPlayerEntity) {
					healLovingAxolotls(serverPlayerEntity);
				}
			}
		}
	}
	
	private void healLovingAxolotls(@NotNull ServerPlayer entity) {
		Level world = entity.level();
		List<Axolotl> nearbyAxolotls = entity.level().getEntities(EntityType.AXOLOTL, AABB.ofSize(entity.position(), MAX_AXOLOTL_DISTANCE, MAX_AXOLOTL_DISTANCE, MAX_AXOLOTL_DISTANCE), LivingEntity::isAlive);
		for (Axolotl axolotlEntity : nearbyAxolotls) {
			if (axolotlEntity.getHealth() < axolotlEntity.getMaxHealth() && axolotlEntity.getLoveCause() != null && axolotlEntity.getLoveCause().equals(entity)) {
				axolotlEntity.heal(AXOLOTL_HEALING);
				entity.playSound(PastelSoundEvents.BLOCK_CITRINE_BLOCK_CHIME, 1.0F, 0.9F + world.random.nextFloat() * 0.2F);
				PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) axolotlEntity.level(), axolotlEntity.position(), ParticleTypes.WAX_OFF, 10, new Vec3(0.5, 0.5, 0.5), new Vec3(0, 0, 0));
			}
		}
	}
	
}
