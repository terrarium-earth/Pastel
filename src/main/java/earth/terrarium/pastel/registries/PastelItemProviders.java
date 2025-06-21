package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.interaction.ItemProvider;
import earth.terrarium.pastel.api.item.ItemStorage;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.capabilities.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;

public class PastelItemProviders {
	
	public static void register(IEventBus bus) {
		bus.addListener((RegisterCapabilitiesEvent event) -> {
			event.registerItem(ItemProvider.CAPABILITY, (ignored, ignored2) -> iterableProvider((player, stack) ->
					stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).nonEmptyItems()), Items.SHULKER_BOX);
			
			event.registerItem(ItemProvider.CAPABILITY, (ignored, ignored2) -> iterableProvider((player, stack) ->
					stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY).items()), Items.BUNDLE);
			
			event.registerItem(ItemProvider.CAPABILITY, (ignored, ignored2) -> new ItemProvider() {
				@Override
				public int provideItems(Player player, ItemStack stack, Item requestedItem, int amount) {
					var storage = ItemStorage.load(stack);
					if (!storage.stack(1).is(requestedItem))
						return 0;

					return (int) storage.extractPure(amount);
				}
				
				@Override
				public int getItemCount(Player player, ItemStack stack, Item requestedItem) {
					var storage = ItemStorage.load(stack);
					if (!storage.getReference().asItem().equals(requestedItem))
						return 0;
					return (int) Math.min(Integer.MAX_VALUE, storage.getCount());
				}
			}, PastelBlocks.BOTTOMLESS_BUNDLE);
			
			// BAG_OF_HOLDING only works server side
			// the client does not know about the content of the ender chest, unless opened
			event.registerItem(ItemProvider.CAPABILITY, (ignored, ignored2) -> iterableProvider((player, stack) ->
					player == null ? List.of() : player.getEnderChestInventory().getItems()), PastelItems.BAG_OF_HOLDING);
		});
	}
	
	public static ItemProvider iterableProvider(BiFunction<@Nullable Player, ItemStack, Iterable<ItemStack>> iterableFactory) {
		return new ItemProvider() {
			@Override
			public int provideItems(Player player, ItemStack stack, Item requestedItem, int amount) {
				int removedCount = 0;
				for (ItemStack s : iterableFactory.apply(player, stack)) {
					if (s.is(requestedItem)) {
						int amountToRemove = Math.min(s.getCount(), amount - removedCount);
						s.shrink(amountToRemove);
						removedCount += amountToRemove;
						if (removedCount == amount) {
							break;
						}
					}
				}
				return removedCount;
			}
			
			@Override
			public int getItemCount(Player player, ItemStack stack, Item requestedItem) {
				int count = 0;
				for (ItemStack s : iterableFactory.apply(player, stack)) {
					if (s.is(requestedItem)) {
						count += s.getCount();
					}
				}
				return count;
			}
		};
	}
	
}