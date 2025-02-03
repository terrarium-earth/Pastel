package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.progression.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.world.*;

public record PairedFoodComponent(Item item, boolean consumeAndApplyRequiredStack, FoodComponent bonusFoodComponent) {
	
	//TODO what is bonusFoodComponent used for?
	public static final Codec<PairedFoodComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Registries.ITEM.getCodec().fieldOf("item").forGetter(PairedFoodComponent::item),
			Codec.BOOL.optionalFieldOf("consume_and_apply_required_stack", true).forGetter(PairedFoodComponent::consumeAndApplyRequiredStack),
			FoodComponent.CODEC.optionalFieldOf("bonus_food_component", new FoodComponent.Builder().build()).forGetter(PairedFoodComponent::bonusFoodComponent)
	).apply(instance, PairedFoodComponent::new));
	
	public static final PacketCodec<RegistryByteBuf, PairedFoodComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.registryValue(RegistryKeys.ITEM), PairedFoodComponent::item,
			PacketCodecs.BOOL, PairedFoodComponent::consumeAndApplyRequiredStack,
			FoodComponent.PACKET_CODEC, PairedFoodComponent::bonusFoodComponent,
			PairedFoodComponent::new
	);
	
	public void tryEatFood(World world, LivingEntity livingEntity, ItemStack eatenStack) {
		if (!(livingEntity instanceof PlayerEntity player)) {
			return;
		}
		
		// does the entity have a matching stack in their inv?
		int requiredSlotStack = -1;
		for (int i = 0; i < player.getInventory().size(); i++) {
			if (player.getInventory().getStack(i).isOf(this.item)) {
				requiredSlotStack = i;
				break;
			}
		}
		if (requiredSlotStack == -1) {
			return;
		}
		ItemStack foundRequiredStack = player.getInventory().getStack(requiredSlotStack);
		
		// should the required stack be consumed, too?
		if (consumeAndApplyRequiredStack) {
			FoodComponent component = foundRequiredStack.get(DataComponentTypes.FOOD);
			if (component != null) {
				player.eatFood(world, foundRequiredStack, component);
			}
		}
		
		if (player instanceof ServerPlayerEntity serverPlayer) {
			SpectrumAdvancementCriteria.CONDITIONAL_FOOD_EATEN.trigger(serverPlayer, eatenStack, foundRequiredStack);
		}
	}
	
}
