package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public abstract class SpectrumTrinketItem extends Item {
	
	private final ResourceLocation unlockIdentifier;
	
	public SpectrumTrinketItem(Properties settings, ResourceLocation unlockIdentifier) {
		super(settings);
		this.unlockIdentifier = unlockIdentifier;
	}
	
	public static boolean hasEquipped(SlotContext slotContext, Item item) {
		return hasEquipped(slotContext.entity(), item);
	}
	
	public static boolean hasEquipped(LivingEntity entity, Item item) {
		return getFirstEquipped(entity, item).isPresent();
	}
	
	public static Optional<ItemStack> getFirstEquipped(LivingEntity entity, Item item) {
		return CuriosApi.getCuriosInventory(entity).flatMap(inventory -> inventory.findFirstCurio(item)).map(SlotResult::stack);
	}
	
	public ResourceLocation getUnlockIdentifier() {
		return this.unlockIdentifier;
	}

	public boolean canEquipMoreThanOne() {
		return false;
	}
	
	@Override
	public void onBreak(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.onBreak(stack, slot, entity);
		if (entity instanceof ServerPlayer serverPlayerEntity) {
			SpectrumAdvancementCriteria.TRINKET_CHANGE.trigger(serverPlayerEntity);
		}
	}
	
}
