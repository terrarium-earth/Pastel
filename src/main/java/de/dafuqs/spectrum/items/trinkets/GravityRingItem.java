package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.Multimap;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.storage.FixedSingleInkStorage;
import de.dafuqs.spectrum.api.item.GravitableItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public abstract class GravityRingItem extends InkDrainTrinketItem implements GravitableItem {
	
	public GravityRingItem(Properties settings, ResourceLocation unlockIdentifier, InkColor inkColor) {
		super(settings, unlockIdentifier, inkColor);
	}
	
	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, ResourceLocation slotIdentifier) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
		
		FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
		long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
		double knockbackResistance = getBonus(storedInk) / 10D;
		if (knockbackResistance != 0) {
			modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(getAttributeID(), negativeGravity() ? -knockbackResistance : knockbackResistance, AttributeModifier.Operation.ADD_VALUE));
		}
		
		return modifiers;
	}
	
	protected abstract ResourceLocation getAttributeID();
	
	protected abstract boolean negativeGravity();
	
	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.tick(stack, slot, entity);
		applyGravity(stack, entity.level(), entity);
	}
	
	public double getBonus(long storedInk) {
		if (storedInk < 100) {
			return 0;
		} else {
			return 1 + (int) (Math.log(storedInk / 100.0f) / Math.log(8));
		}
	}
	
	@Override
	public float getGravityMod(ItemStack stack) {
		FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
		long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
		float bonus = ((float) getBonus(storedInk)) / 100F;
		return negativeGravity() ? bonus : -bonus;
	}
	
}
