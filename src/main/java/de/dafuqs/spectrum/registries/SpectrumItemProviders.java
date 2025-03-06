package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class SpectrumItemProviders {
	
	public static void register() {
		ItemProviderRegistry.register(Items.SHULKER_BOX, iterableProvider((player, stack) ->
				stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()));

		ItemProviderRegistry.register(Items.BUNDLE, iterableProvider((player, stack) ->
				stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT).iterate()));
		
		ItemProviderRegistry.register(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem(), new ItemProvider() {
			@Override
			public int provideItems(PlayerEntity player, ItemStack stack, Item requestedItem, int amount) {
				var builder = BottomlessBundleItem.BottomlessStack.Builder.of(player.getWorld(), stack);
				var removed = builder.remove(amount);
				if (!removed.isOf(requestedItem))
					return 0;
				builder.buildAndSet(stack);
				return removed.getCount();
			}
			
			@Override
			public int getItemCount(PlayerEntity player, ItemStack stack, Item requestedItem) {
				var bottomlessStack = stack.getOrDefault(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessBundleItem.BottomlessStack.DEFAULT);
				if (!bottomlessStack.variant().isOf(requestedItem))
					return 0;
				return (int) Math.min(Integer.MAX_VALUE, bottomlessStack.count());
			}
		});
		
		// BAG_OF_HOLDING only works server side
		// the client does not know about the content of the ender chest, unless opened
		ItemProviderRegistry.register(SpectrumItems.BAG_OF_HOLDING, iterableProvider((player, stack) ->
				player == null ? List.of() : player.getEnderChestInventory().getHeldStacks()));
		
	}
	
	public static ItemProvider iterableProvider(BiFunction<@Nullable PlayerEntity, ItemStack, Iterable<ItemStack>> iterableFactory) {
		return new ItemProvider() {
			@Override
			public int provideItems(PlayerEntity player, ItemStack stack, Item requestedItem, int amount) {
				int removedCount = 0;
				for (ItemStack s : iterableFactory.apply(player, stack)) {
					if (s.isOf(requestedItem)) {
						int amountToRemove = Math.min(s.getCount(), amount - removedCount);
						s.decrement(amountToRemove);
						removedCount += amountToRemove;
						if (removedCount == amount) {
							break;
						}
					}
				}
				return removedCount;
			}
			
			@Override
			public int getItemCount(PlayerEntity player, ItemStack stack, Item requestedItem) {
				int count = 0;
				for (ItemStack s : iterableFactory.apply(player, stack)) {
					if (s.isOf(requestedItem)) {
						count += s.getCount();
					}
				}
				return count;
			}
		};
	}
	
}