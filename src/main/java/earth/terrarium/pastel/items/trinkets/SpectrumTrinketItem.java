package earth.terrarium.pastel.items.trinkets;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.progression.SpectrumAdvancementCriteria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Optional;

public abstract class SpectrumTrinketItem extends Item implements ICurioItem {
	
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

	@Override
	public boolean canEquip(SlotContext slotContext, ItemStack stack) {
		if (slotContext.entity() instanceof Player playerEntity) {
			// does the player have the matching advancement?
			if (AdvancementHelper.hasAdvancement(playerEntity, getUnlockIdentifier())) {
				// Can only a single trinket of that type be equipped at once?
				if (!canEquipMoreThanOne() && hasEquipped(slotContext.entity(), this)) {
					return false;
				}
				return ICurioItem.super.canEquip(slotContext, stack);
			}
		}
		return false;
	}

	public boolean canEquipMoreThanOne() {
		return false;
	}

	@Override
	public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
		ICurioItem.super.onEquip(slotContext, prevStack, stack);
		if (slotContext.entity() instanceof ServerPlayer serverPlayerEntity) {
			SpectrumAdvancementCriteria.TRINKET_CHANGE.trigger(serverPlayerEntity);
		}
	}

	@Override
	public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
		ICurioItem.super.onUnequip(slotContext, newStack, stack);

		if (slotContext.entity() instanceof ServerPlayer serverPlayerEntity) {
			SpectrumAdvancementCriteria.TRINKET_CHANGE.trigger(serverPlayerEntity);
		}
	}
}
