package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import dev.emi.trinkets.api.*;
import net.fabricmc.api.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class TakeOffBeltItem extends SpectrumTrinketItem {
	
	public static final int CHARGE_TIME_TICKS = 20;
	public static final int MAX_CHARGES = 8;
	
	private static final HashMap<LivingEntity, Long> sneakingTimes = new HashMap<>();
	
	public TakeOffBeltItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/take_off_belt"));
	}
	
	public static int getJumpBoostAmplifier(int sneakTime, int powerEnchantmentLevel) {
		return (int) Math.floor(sneakTime * (2.0 + powerEnchantmentLevel * 0.5));
	}
	
	public static int getCurrentCharge(PlayerEntity playerEntity) {
		if (sneakingTimes.containsKey(playerEntity)) {
			return (int) (playerEntity.getWorld().getTime() - sneakingTimes.get(playerEntity)) / CHARGE_TIME_TICKS;
		}
		return 0;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.take_off_belt.tooltip").formatted(Formatting.GRAY));
	}
	
	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		World world = entity.getWorld();
		super.tick(stack, slot, entity);
		
		if (!world.isClient) {
			if (entity.isSneaking() && entity.isOnGround()) {
				if (sneakingTimes.containsKey(entity)) {
					long sneakTicks = world.getTime() - sneakingTimes.get(entity);
					if (sneakTicks % CHARGE_TIME_TICKS == 0) {
						if (sneakTicks > CHARGE_TIME_TICKS * MAX_CHARGES) {
							world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SpectrumSoundEvents.USE_FAIL, SoundCategory.NEUTRAL, 4.0F, 1.05F);
							PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerWorld) world, entity.getPos(), SpectrumParticleTypes.BLACK_CRAFTING, 20, new Vec3d(0, 0, 0), new Vec3d(0.1, 0.05, 0.1));
							entity.removeStatusEffect(StatusEffects.JUMP_BOOST);
						} else {
							int sneakTimeMod = (int) sneakTicks / CHARGE_TIME_TICKS;
							
							world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_HIT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
							for (Vec3d vec : VectorPattern.SIXTEEN.getVectors()) {
								PlayParticleWithExactVelocityPayload.playParticleWithExactVelocity((ServerWorld) world, entity.getPos(), SpectrumParticleTypes.LIQUID_CRYSTAL_SPARKLE, 1, vec.multiply(0.5));
							}
							
							int powerEnchantmentLevel = SpectrumEnchantmentHelper.getLevel(world.getRegistryManager(), Enchantments.POWER, stack);
							int featherFallingEnchantmentLevel = SpectrumEnchantmentHelper.getLevel(world.getRegistryManager(), Enchantments.FEATHER_FALLING, stack);
							entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, CHARGE_TIME_TICKS, getJumpBoostAmplifier(sneakTimeMod, powerEnchantmentLevel), true, true));
							if (featherFallingEnchantmentLevel > 0) {
								entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, CHARGE_TIME_TICKS + featherFallingEnchantmentLevel * 20, 0, true, true));
							}
						}
					}
				} else {
					sneakingTimes.put(entity, world.getTime());
					if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
						PlayTakeOffBeltSoundInstancePayload.sendPlayTakeOffBeltSoundInstance(serverPlayerEntity);
					}
				}
			} else if (world.getTime() % CHARGE_TIME_TICKS == 0 && sneakingTimes.containsKey(entity)) {
				long lastSneakingTime = sneakingTimes.get(entity);
				if (lastSneakingTime < world.getTime() + CHARGE_TIME_TICKS) {
					sneakingTimes.remove(entity);
				}
			}
		}
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantability() {
		return 8;
	}
	
}
