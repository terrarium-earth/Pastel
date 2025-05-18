package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.Multimap;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumEntityAttributes;
import de.dafuqs.spectrum.registries.SpectrumStatusEffectTags;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WhispyCircletItem extends SpectrumTrinketItem {
	
	private final static int TRIGGER_EVERY_X_TICKS = 100;
	private final static int NEGATIVE_EFFECT_SHORTENING_TICKS = 200;
	
	public WhispyCircletItem(Properties settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/whispy_circlet"));
	}
	
	public static void removeSingleStatusEffect(@NotNull LivingEntity entity, MobEffectCategory category) {
		Collection<MobEffectInstance> currentEffects = entity.getActiveEffects();
		if (currentEffects.isEmpty()) {
			return;
		}

		List<MobEffectInstance> negativeEffects = new ArrayList<>();
		for (MobEffectInstance statusEffectInstance : currentEffects) {
			Holder<MobEffect> effect = statusEffectInstance.getEffect();
			if (effect.value().getCategory() == category && !effect.is(SpectrumStatusEffectTags.SOPORIFIC) && !effect.is(SpectrumStatusEffectTags.BYPASSES_WHISPY_CIRCLET)) {
				negativeEffects.add(statusEffectInstance);
			}
		}
		
		if (negativeEffects.isEmpty()) {
			return;
		}
		
		Level world = entity.level();
		int randomIndex = world.random.nextInt(negativeEffects.size());
		entity.removeEffect(negativeEffects.get(randomIndex).getEffect());
	}
	
	public static void removeNegativeStatusEffects(@NotNull LivingEntity entity) {
		Set<Holder<MobEffect>> effectsToRemove = new HashSet<>();
		for (var instance : entity.getActiveEffects()) {
			if (affects(instance.getEffect())) {
				effectsToRemove.add(instance.getEffect());
			}
		}
		
		for (Holder<MobEffect> effect : effectsToRemove) {
			entity.removeEffect(effect);
		}
	}
	
	public static void shortenNegativeStatusEffects(@NotNull LivingEntity entity, int duration) {
		Collection<MobEffectInstance> newEffects = new ArrayList<>();
		Collection<Holder<MobEffect>> effectTypesToClear = new ArrayList<>();
		
		// remove them first, so hidden "stacked" effects are preserved
		for (MobEffectInstance instance : entity.getActiveEffects()) {
			if (affects(instance.getEffect())) {
				int newDurationTicks = instance.getDuration() - duration;
				if (newDurationTicks > 0) {
					newEffects.add(new MobEffectInstance(instance.getEffect(), newDurationTicks, instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
				}
				if (!effectTypesToClear.contains(instance.getEffect())) {
					effectTypesToClear.add(instance.getEffect());
				}
			}
		}
		
		for (var effectTypeToClear : effectTypesToClear) {
			entity.removeEffect(effectTypeToClear);
		}
		for (MobEffectInstance newEffect : newEffects) {
			entity.addEffect(newEffect);
		}
	}
	
	public static boolean affects(Holder<MobEffect> effect) {
		return effect.value().getCategory() == MobEffectCategory.HARMFUL && !effect.is(SpectrumStatusEffectTags.BYPASSES_WHISPY_CIRCLET);
	}
	
	public static void preventPhantomSpawns(@NotNull ServerPlayer serverPlayerEntity) {
		serverPlayerEntity.getStats().setValue(serverPlayerEntity, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), 0);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.spectrum.whispy_circlet.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.spectrum.whispy_circlet.tooltip2").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.spectrum.whispy_circlet.tooltip3").withStyle(ChatFormatting.GRAY));
	}
	
	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		super.curioTick(slotContext, stack);
		LivingEntity entity = slotContext.entity();

		Level world = entity.level();
		if (!world.isClientSide) {
			long time = entity.level().getGameTime();
			if (time % TRIGGER_EVERY_X_TICKS == 0) {
				shortenNegativeStatusEffects(entity, NEGATIVE_EFFECT_SHORTENING_TICKS);
			}
			if (time % 10000 == 0 && entity instanceof ServerPlayer serverPlayerEntity) {
				preventPhantomSpawns(serverPlayerEntity);
			}
		}
	}
	
	public static final ResourceLocation ATTRIBUTE_ID = SpectrumCommon.locate("whispy_circlet_mental_presence");
	
	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);
		modifiers.put(SpectrumEntityAttributes.MENTAL_PRESENCE, new AttributeModifier(ATTRIBUTE_ID, 0.3, AttributeModifier.Operation.ADD_VALUE));
		return modifiers;
	}

}
