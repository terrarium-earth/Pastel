package earth.terrarium.pastel.items.trinkets;

import com.google.common.collect.Multimap;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.render.SlotBackgroundEffectProvider;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelStatusEffectTags;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class AetherGracedNectarGlovesItem extends AzureDikeTrinketItem implements SlotBackgroundEffectProvider {

	public static final int HARMFUL_EFFECT_COST = 7;
	public static ResourceLocation MENTAL_PRESENCE_ATTRIBUTE_ID = PastelCommon.locate("nectar_gloves_sleep");
	
	public AetherGracedNectarGlovesItem(Properties settings, ResourceLocation unlockIdentifier) {
		super(settings, unlockIdentifier);
	}

	@Override
	public int maxAzureDike(ItemStack stack) {
		return 10;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.aether_graced_nectar_gloves.tooltip"));
		tooltip.add(Component.translatable("item.pastel.aether_graced_nectar_gloves.tooltip2"));
	}

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);
		modifiers.put(PastelEntityAttributes.MENTAL_PRESENCE, new AttributeModifier(MENTAL_PRESENCE_ATTRIBUTE_ID, -1F, AttributeModifier.Operation.ADD_VALUE));
		return modifiers;
	}

	public static boolean testEffectFor(LivingEntity entity, Holder<MobEffect> effect) {
		if (effect.value().isBeneficial())
			return false;
		
		if (effect.is(PastelStatusEffectTags.BYPASSES_NECTAR_GLOVES))
			return false;

		return hasEquipped(entity, PastelItems.AETHER_GRACED_NECTAR_GLOVES.get()) && (effect.value().getCategory() == MobEffectCategory.HARMFUL || effect == PastelStatusEffects.FRENZY);
	}

	public static boolean tryBlockEffect(LivingEntity entity, int cost) {
		if (AzureDikeProvider.getAzureDikeCharges(entity) == 0)
			return false;

		return AzureDikeProvider.absorbDamage(entity, cost) == 0;
	}

	@Override
	public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		return SlotEffect.BORDER_FADE;
	}

	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		return PastelStatusEffects.ETERNAL_SLUMBER_COLOR;
	}
}
