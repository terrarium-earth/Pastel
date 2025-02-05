package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import de.dafuqs.additionalentityattributes.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.cca.*;
import de.dafuqs.spectrum.registries.*;
import dev.emi.trinkets.api.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.entry.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AshenCircletItem extends SpectrumTrinketItem {
	
	public static final int FIRE_RESISTANCE_EFFECT_DURATION = 600;
	public static final long COOLDOWN_TICKS = 3000;
	
	public static final double LAVA_MOVEMENT_SPEED_MOD = 0.4; // vanilla uses 0.5 to slow the player down to half its speed
	public static final double LAVA_VIEW_DISTANCE_MOD = 24.0;
	
	public AshenCircletItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/ashen_circlet"));
	}
	
	public static long getCooldownTicks(@NotNull ItemStack ashenCircletStack, @NotNull World world) {
		var last = ashenCircletStack.getOrDefault(SpectrumDataComponentTypes.LAST_COOLDOWN_START, 0L);
		return Math.max(0, last + COOLDOWN_TICKS - world.getTime());
	}
	
	private static void setCooldown(@NotNull ItemStack ashenCircletStack, @NotNull World world) {
		ashenCircletStack.set(SpectrumDataComponentTypes.LAST_COOLDOWN_START, world.getTime());
	}
	
	public static void grantFireResistance(@NotNull ItemStack ashenCircletStack, @NotNull LivingEntity livingEntity) {
		if (!livingEntity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, FIRE_RESISTANCE_EFFECT_DURATION, 0, true, true));
			livingEntity.getWorld().playSound(null, livingEntity.getBlockPos(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setCooldown(ashenCircletStack, livingEntity.getWorld());
		}
	}
	
	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.tick(stack, slot, entity);
		if (entity.isOnFire()) {
			entity.setFireTicks(0);
		}
		if (getCooldownTicks(stack, entity.getWorld()) == 0 && OnPrimordialFireComponent.putOut(entity)) {
			entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setCooldown(stack, entity.getWorld());
		}
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip2").formatted(Formatting.GRAY));
		
		var world = MinecraftClient.getInstance().world;
		if (world != null) {
			long cooldownTicks = getCooldownTicks(stack, world);
			if (cooldownTicks == 0) {
				tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip.cooldown_full"));
			} else {
				tooltip.add(Text.translatable("item.spectrum.ashen_circlet.tooltip.cooldown_seconds", cooldownTicks / 20));
			}
		}
	}
	
	public static Identifier LAVA_SPEED_ATTRIBUTE_ID = SpectrumCommon.locate("ashen_circlet_lava_speed");
	public static Identifier LAVA_VISIBILITY_ATTRIBUTE_ID = SpectrumCommon.locate("ashen_circlet_lava_visibility");
	
	@Override
	public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
		Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
		
		modifiers.put(AdditionalEntityAttributes.LAVA_SPEED, new EntityAttributeModifier(LAVA_SPEED_ATTRIBUTE_ID, LAVA_MOVEMENT_SPEED_MOD, EntityAttributeModifier.Operation.ADD_VALUE));
		modifiers.put(AdditionalEntityAttributes.LAVA_VISIBILITY, new EntityAttributeModifier(LAVA_VISIBILITY_ATTRIBUTE_ID, LAVA_VIEW_DISTANCE_MOD, EntityAttributeModifier.Operation.ADD_VALUE));
		
		return modifiers;
	}
	
}
