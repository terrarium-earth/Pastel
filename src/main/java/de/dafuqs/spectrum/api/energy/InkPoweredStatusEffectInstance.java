package de.dafuqs.spectrum.api.energy;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class InkPoweredStatusEffectInstance {
	
	public static final Codec<InkPoweredStatusEffectInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
			StatusEffectInstance.CODEC.fieldOf("effect").forGetter(c -> c.statusEffectInstance),
			InkCost.CODEC.fieldOf("ink_cost").forGetter(c -> c.cost),
			Codec.INT.optionalFieldOf("custom_color", -1).forGetter(c -> c.customColor),
			Codec.BOOL.optionalFieldOf("unidentifiable", false).forGetter(c -> c.unidentifiable),
			Codec.BOOL.optionalFieldOf("incurable", false).forGetter(c -> c.incurable)
	).apply(i, InkPoweredStatusEffectInstance::new));
	
	public static final PacketCodec<RegistryByteBuf, InkPoweredStatusEffectInstance> PACKET_CODEC = PacketCodec.tuple(
			StatusEffectInstance.PACKET_CODEC, c -> c.statusEffectInstance,
			InkCost.PACKET_CODEC, c -> c.cost,
			PacketCodecs.VAR_INT, c -> c.customColor,
			PacketCodecs.BOOL, c -> c.unidentifiable,
			PacketCodecs.BOOL, c -> c.incurable,
			InkPoweredStatusEffectInstance::new
	);
	
	public static final String NBT_KEY = "InkPoweredStatusEffects";
	
	private final StatusEffectInstance statusEffectInstance;
	private final InkCost cost;
	private final int customColor; // -1: use effect default
	private final boolean unidentifiable;
	//TODO why can't this use StatusEffectInstance's mixed-in incurable?
	private final boolean incurable;
	
	public InkPoweredStatusEffectInstance(StatusEffectInstance statusEffectInstance, InkCost cost, int customColor, boolean unidentifiable, boolean incurable) {
		this.statusEffectInstance = statusEffectInstance;
		this.cost = cost;
		this.customColor = customColor;
		this.unidentifiable = unidentifiable;
		this.incurable = incurable;
		if (incurable) statusEffectInstance.spectrum$setIncurable(true);
	}
	
	public StatusEffectInstance getStatusEffectInstance() {
		return statusEffectInstance;
	}
	
	public InkCost getInkCost() {
		return cost;
	}
	
	public static List<InkPoweredStatusEffectInstance> getEffects(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.INK_POWERED, InkPoweredComponent.DEFAULT).effects();
	}
	
	public static void setEffects(ItemStack stack, List<InkPoweredStatusEffectInstance> effects) {
		stack.set(SpectrumDataComponentTypes.INK_POWERED, new InkPoweredComponent(effects));
	}
	
	public static void buildTooltip(List<Text> tooltip, List<InkPoweredStatusEffectInstance> effects, MutableText attributeModifierText, boolean showDuration, float tickRate) {
		if (!effects.isEmpty()) {
			List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifiers = Lists.newArrayList();
			for (InkPoweredStatusEffectInstance entry : effects) {
				if (entry.isUnidentifiable()) {
					tooltip.add(Text.translatable("item.spectrum.potion.tooltip.unidentifiable"));
					continue;
				}
				
				StatusEffectInstance effect = entry.getStatusEffectInstance();
				InkCost cost = entry.getInkCost();
				
				MutableText mutableText = Text.translatable(effect.getTranslationKey());
				if (effect.getAmplifier() > 0) {
					mutableText = Text.translatable("potion.withAmplifier", mutableText, Text.translatable("potion.potency." + effect.getAmplifier()));
				}
				if (showDuration && effect.getDuration() > 20) {
					mutableText = Text.translatable("potion.withDuration", mutableText, StatusEffectUtil.getDurationText(effect, 1.0F, tickRate));
				}
				mutableText.formatted(effect.getEffectType().value().getCategory().getFormatting());
				mutableText.append(Text.translatable("spectrum.tooltip.ink_cost", Support.getShortenedNumberString(cost.cost()), cost.color().getColoredInkName()).formatted(Formatting.GRAY));
				if (entry.isIncurable()) {
					mutableText.append(Text.translatable("item.spectrum.potion.tooltip.incurable"));
				}
				tooltip.add(mutableText);
				
				effect.getEffectType().value().forEachAttributeModifier(effect.getAmplifier(), (attribute, modifier) ->
						attributeModifiers.add(new Pair<>(attribute, modifier))
				);
			}
			
			if (!attributeModifiers.isEmpty()) {
				tooltip.add(Text.empty());
				tooltip.add(attributeModifierText.formatted(Formatting.DARK_PURPLE));
				
				for (var pair : attributeModifiers) {
					var translatedAttribute = Text.translatable(pair.getLeft().value().getTranslationKey());
					var mutableText = pair.getRight();
					
					double statusEffect = mutableText.value();
					double d;
					if (mutableText.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE && mutableText.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
						d = mutableText.value();
					} else {
						d = mutableText.value() * 100.0D;
					}
					
					if (statusEffect > 0.0D) {
						tooltip.add((Text.translatable("attribute.modifier.plus." + mutableText.operation().getId(), AttributeModifiersComponent.DECIMAL_FORMAT.format(d), translatedAttribute)).formatted(Formatting.BLUE));
					} else if (statusEffect < 0.0D) {
						d *= -1.0D;
						tooltip.add((Text.translatable("attribute.modifier.take." + mutableText.operation().getId(), AttributeModifiersComponent.DECIMAL_FORMAT.format(d), translatedAttribute)).formatted(Formatting.RED));
					}
				}
			}
		}
	}
	
	public int getColor() {
		if (this.customColor == -1) {
			return statusEffectInstance.getEffectType().value().getColor();
		}
		return this.customColor;
	}
	
	public boolean isUnidentifiable() {
		return this.unidentifiable;
	}
	
	public boolean isIncurable() {
		return this.incurable;
	}
}
