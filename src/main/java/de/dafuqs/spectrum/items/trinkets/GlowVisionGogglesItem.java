package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.energy.InkCost;
import de.dafuqs.spectrum.api.energy.InkPowered;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.helpers.InventoryHelper;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import dev.emi.trinkets.api.SlotReference;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GlowVisionGogglesItem extends SpectrumTrinketItem implements InkPowered {
	
	public static final InkCost INK_COST = new InkCost(InkColors.LIGHT_BLUE, 20);
	public static final ItemStack ITEM_COST = new ItemStack(Items.GLOW_INK_SAC, 1);
	
	public GlowVisionGogglesItem(Properties settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/glow_vision_goggles"));
	}
	
	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.tick(stack, slot, entity);
		
		Level world = entity.level();
		if (world != null && !world.isClientSide && world.getGameTime() % 20 == 0) {
			if (entity instanceof ServerPlayer serverPlayerEntity) {
				giveEffect(world, serverPlayerEntity);
			}
		}
	}
	
	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.onEquip(stack, slot, entity);
		Level world = entity.level();
		if (world != null && !world.isClientSide && entity instanceof ServerPlayer serverPlayerEntity) {
			giveEffect(world, serverPlayerEntity);
		}
	}
	
	private static void giveEffect(Level world, ServerPlayer serverPlayerEntity) {
		int lightLevelAtPlayerPos = world.getMaxLocalRawBrightness(serverPlayerEntity.blockPosition());
		
		if (lightLevelAtPlayerPos < 7) {
			MobEffectInstance nightVisionInstance = serverPlayerEntity.getEffect(MobEffects.NIGHT_VISION);
			if (nightVisionInstance == null || nightVisionInstance.getDuration() < 220) { // prevent "night vision running out" flashing
				// no / short night vision => search for glow ink sac and add night vision if found
				
				boolean paid = serverPlayerEntity.isCreative();
				if (!paid) { // try pay with ink
					paid = InkPowered.tryDrainEnergy(serverPlayerEntity, INK_COST);
				}
				if (!paid) {  // try pay with item
					paid = InventoryHelper.removeFromInventoryWithRemainders(serverPlayerEntity, ITEM_COST);
				}
				
				if (paid) {
					MobEffectInstance newNightVisionInstance = new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * SpectrumCommon.CONFIG.GlowVisionGogglesDuration, 0, true, true);
					serverPlayerEntity.addEffect(newNightVisionInstance);
					world.playSound(null, serverPlayerEntity, SpectrumSoundEvents.ITEM_ARMOR_EQUIP_GLOW_VISION, SoundSource.PLAYERS, 0.2F, 1.0F);
				}
			}
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		if (InkPowered.canUseClient()) {
			tooltip.add(Component.translatable("item.spectrum.glow_vision_goggles.tooltip_with_ink", INK_COST.color().getColoredInkName()));
		} else {
			tooltip.add(Component.translatable("item.spectrum.glow_vision_goggles.tooltip"));
		}
	}
	
	@Override
	public List<InkColor> getUsedColors() {
		return List.of(INK_COST.color());
	}
}
