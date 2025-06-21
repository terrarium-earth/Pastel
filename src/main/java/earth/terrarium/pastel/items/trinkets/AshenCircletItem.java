package earth.terrarium.pastel.items.trinkets;

import com.google.common.collect.Multimap;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import top.theillusivec4.curios.api.SlotContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AshenCircletItem extends PastelTrinketItem {
	
	public static final int FIRE_RESISTANCE_EFFECT_DURATION = 600;
	public static final long COOLDOWN_TICKS = 3000;
	
	public static final double LAVA_MOVEMENT_SPEED_MOD = 0.4; // vanilla uses 0.5 to slow the player down to half its speed
	public static final double LAVA_VIEW_DISTANCE_MOD = 24.0;
	
	public AshenCircletItem(Properties settings) {
		super(settings, PastelCommon.locate("unlocks/trinkets/ashen_circlet"));
	}
	
	public static long getCooldownTicks(@NotNull ItemStack ashenCircletStack, @NotNull Level world) {
		var last = ashenCircletStack.getOrDefault(PastelDataComponentTypes.LAST_COOLDOWN_START, 0L);
		return Math.max(0, last + COOLDOWN_TICKS - world.getGameTime());
	}
	
	private static void setCooldown(@NotNull ItemStack ashenCircletStack, @NotNull Level world) {
		ashenCircletStack.set(PastelDataComponentTypes.LAST_COOLDOWN_START, world.getGameTime());
	}
	
	public static void grantFireResistance(@NotNull ItemStack ashenCircletStack, @NotNull LivingEntity livingEntity) {
		if (!livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, FIRE_RESISTANCE_EFFECT_DURATION, 0, true, true));
			livingEntity.level().playSound(null, livingEntity.blockPosition(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
			setCooldown(ashenCircletStack, livingEntity.level());
		}
	}

	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		super.curioTick(slotContext, stack);
		LivingEntity entity = slotContext.entity();

		if (entity.isOnFire()) {
			entity.setRemainingFireTicks(0);
		}
		if (getCooldownTicks(stack, entity.level()) == 0 && PrimordialFireData.putOut(entity)) {
			entity.level().playSound(null, entity.blockPosition(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
			setCooldown(stack, entity.level());
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.ashen_circlet.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.ashen_circlet.tooltip2").withStyle(ChatFormatting.GRAY));
		
		var world = Minecraft.getInstance().level;
		if (world != null) {
			long cooldownTicks = getCooldownTicks(stack, world);
			if (cooldownTicks == 0) {
				tooltip.add(Component.translatable("item.pastel.ashen_circlet.tooltip.cooldown_full"));
			} else {
				tooltip.add(Component.translatable("item.pastel.ashen_circlet.tooltip.cooldown_seconds", cooldownTicks / 20));
			}
		}
	}
	
	public static ResourceLocation LAVA_SPEED_ATTRIBUTE_ID = PastelCommon.locate("ashen_circlet_lava_speed");
	public static ResourceLocation LAVA_VISIBILITY_ATTRIBUTE_ID = PastelCommon.locate("ashen_circlet_lava_visibility");

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);
		
		modifiers.put(AdditionalEntityAttributes.LAVA_SPEED, new AttributeModifier(LAVA_SPEED_ATTRIBUTE_ID, LAVA_MOVEMENT_SPEED_MOD, AttributeModifier.Operation.ADD_VALUE));
		modifiers.put(AdditionalEntityAttributes.LAVA_VISIBILITY, new AttributeModifier(LAVA_VISIBILITY_ATTRIBUTE_ID, LAVA_VIEW_DISTANCE_MOD, AttributeModifier.Operation.ADD_VALUE));
		
		return modifiers;
	}
	
}
