package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.attachments.data.azure_dike.DikeShieldData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeData;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public interface AzureDikeItem {
	
	int maxAzureDike(ItemStack stack);
	
	default float azureDikeRechargeSpeedModifier(ItemStack stack) {
		return 1.0F;
	}
	
	default float rechargeDelayAfterDamageModifier(ItemStack stack) {
		return 1.0F;
	}

	default float maxAzureDikeMultiplier(ItemStack stack) {
		return 1.0F;
	}
	
	default void recalculate(LivingEntity livingEntity) {
		Level world = livingEntity.level();
		if (!world.isClientSide) {
			DikeShieldData azureDikeComponent = AzureDikeProvider.getAzureDikeComponent(livingEntity);

			Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(livingEntity);
			if (curiosInventory.isPresent()) {
				int maxAzureDike = 0;
				float rechargeSpeedModifier = 1F;
				float rechargeDelayAfterDamageModifier = 1F;
				float maxAzureDikeMultiplier = 1F;
				for (SlotResult slot : curiosInventory.get().findCurios(stack -> stack.getItem() instanceof AzureDikeItem)) {
					ItemStack stack = slot.stack();
					AzureDikeItem azureDikeItem = (AzureDikeItem) stack.getItem();
					maxAzureDike += azureDikeItem.maxAzureDike(stack);
					rechargeSpeedModifier += azureDikeItem.azureDikeRechargeSpeedModifier(stack) - 1;
					rechargeDelayAfterDamageModifier += azureDikeItem.rechargeDelayAfterDamageModifier(stack) - 1;
					maxAzureDikeMultiplier += azureDikeItem.maxAzureDikeMultiplier(stack) - 1;
				}
				
				int ticksPerPointOfRecharge = (int) Math.max(1, AzureDikeData.BASE_RECHARGE_DELAY_TICKS / rechargeSpeedModifier);
				int rechargeDelayTicksAfterGettingHit = (int) Math.max(1, AzureDikeData.BASE_RECHARGE_DELAY_TICKS_AFTER_DAMAGE / rechargeDelayAfterDamageModifier);
				
				azureDikeComponent.set(Math.round((maxAzureDike * maxAzureDikeMultiplier)), ticksPerPointOfRecharge, rechargeDelayTicksAfterGettingHit, false);
			}
		}
	}
	
}
