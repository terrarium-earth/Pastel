package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public abstract class SpectrumTrinketItem extends TrinketItem {
	
	private final ResourceLocation unlockIdentifier;
	
	public SpectrumTrinketItem(Properties settings, ResourceLocation unlockIdentifier) {
		super(settings);
		this.unlockIdentifier = unlockIdentifier;
	}
	
	public static boolean hasEquipped(Object entity, Item item) {
		if (entity instanceof LivingEntity livingEntity) {
			return hasEquipped(livingEntity, item);
		}
		return false;
	}
	
	public static boolean hasEquipped(LivingEntity entity, Item item) {
		Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(entity);
		return trinketComponent.map(component -> component.isEquipped(item)).orElse(false);
	}
	
	public static Optional<ItemStack> getFirstEquipped(LivingEntity entity, Item item) {
		Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(entity);
		if (trinketComponent.isPresent()) {
			List<Tuple<SlotReference, ItemStack>> stacks = trinketComponent.get().getEquipped(item);
			if (!stacks.isEmpty()) {
				return Optional.of(stacks.getFirst().getB());
			}
		}
		return Optional.empty();
	}
	
	public ResourceLocation getUnlockIdentifier() {
		return this.unlockIdentifier;
	}
	
	@Override
	public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof Player playerEntity) {
			// does the player have the matching advancement?
			if (AdvancementHelper.hasAdvancement(playerEntity, getUnlockIdentifier())) {
				// Can only a single trinket of that type be equipped at once?
				if (!canEquipMoreThanOne() && hasEquipped(entity, this)) {
					return false;
				}
				return super.canEquip(stack, slot, entity);
			}
		}
		return false;
	}
	
	public boolean canEquipMoreThanOne() {
		return false;
	}
	
	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.onEquip(stack, slot, entity);
		if (entity instanceof ServerPlayer serverPlayerEntity) {
			SpectrumAdvancementCriteria.TRINKET_CHANGE.trigger(serverPlayerEntity);
		}
	}
	
	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.onUnequip(stack, slot, entity);
		if (entity instanceof ServerPlayer serverPlayerEntity) {
			SpectrumAdvancementCriteria.TRINKET_CHANGE.trigger(serverPlayerEntity);
		}
	}
	
	@Override
	public void onBreak(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.onBreak(stack, slot, entity);
		if (entity instanceof ServerPlayer serverPlayerEntity) {
			SpectrumAdvancementCriteria.TRINKET_CHANGE.trigger(serverPlayerEntity);
		}
	}
	
}
