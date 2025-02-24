package de.dafuqs.spectrum.blocks.present;

import com.mojang.authlib.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.items.bundles.*;
import de.dafuqs.spectrum.items.tooltip.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.screen.slot.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

import java.util.*;
import java.util.stream.*;

public class PresentBlockItem extends PlaceableBundleBlockItem {
	
	public static final int MAX_STORAGE_STACKS = 5;
	
	public PresentBlockItem(Block block, Settings settings) {
		super(new ExtendedBundleComponent(MAX_STORAGE_STACKS), block, settings);
	}
	
	@Override
	protected boolean canPlace(ItemPlacementContext context, BlockState state) {
		return isWrapped(context.getStack()) && super.canPlace(context, state);
	}
	
	public static void setOwner(ItemStack itemStack, PlayerEntity giver) {
		var profile = new GameProfile(giver.getUuid(), giver.getName().getString());
		itemStack.set(DataComponentTypes.PROFILE, new ProfileComponent(profile));
	}
	
	public static Optional<ProfileComponent> getOwner(ItemStack itemStack) {
		return Optional.ofNullable(itemStack.get(DataComponentTypes.PROFILE));
	}
	
	public static boolean isEmpty(ItemStack itemStack) {
		return itemStack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT).isEmpty();
	}
	
	public static boolean isWrapped(ItemStack itemStack) {
		return getWrapData(itemStack).wrapped();
	}
	
	public static WrappedPresentComponent getWrapData(ItemStack itemStack) {
		return itemStack.getOrDefault(SpectrumDataComponentTypes.WRAPPED_PRESENT, WrappedPresentComponent.DEFAULT);
	}
	
	public static void wrap(ItemStack itemStack, PresentBlock.WrappingPaper wrappingPaper, Map<Integer, Integer> colors) {
		itemStack.set(SpectrumDataComponentTypes.WRAPPED_PRESENT, new WrappedPresentComponent(true, wrappingPaper, colors));
	}
	
	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		return !isCraftingInventory(slot) && super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (isWrapped(itemStack)) {
			super.use(world, user, hand);
		}
		return TypedActionResult.pass(itemStack);
	}
	
	// CraftingInventory does not recalculate the recipe after inputting / retrieving stacks from the present.
	// The recipes output will still hold the original present data from when it was put into the crafting grid
	// If the player then puts / receives items from the present they are able to duplicate items
	private boolean isCraftingInventory(Slot slot) {
		return slot.inventory instanceof CraftingInventory;
	}
	
	@Override
	public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
		super.onCraftByPlayer(stack, world, player);
		if (player != null) {
			setOwner(stack, player);
		}
	}
	
	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		return !isWrapped(stack) && super.isItemBarVisible(stack);
	}
	
	public static Stream<ItemStack> getBundledStacks(ItemStack stack) {
		return stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT).stream();
	}
	
	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		if (isWrapped(stack)) {
			return Optional.empty();
		}
		
		// TODO: Use BundleTooltipComponent and such instead
		var list = DefaultedList.ofSize(MAX_STORAGE_STACKS, ItemStack.EMPTY);
		var stacks = getBundledStacks(stack).toList();
		for (int i = 0; i < stacks.size(); i++)
			list.set(i, stacks.get(i));
		return Optional.of(new PresentTooltipData(list));
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		boolean wrapped = isWrapped(stack);
		if (wrapped) {
			var gifter = getOwner(stack);
			if (gifter.isPresent()) {
				gifter.get().name().ifPresent(name -> tooltip.add((Text.translatable("block.spectrum.present.tooltip.wrapped.giver", name).formatted(Formatting.GRAY))));
				if (type.isAdvanced()) {
					gifter.get().id().ifPresent(id -> tooltip.add((Text.literal("UUID: " + id).formatted(Formatting.GRAY))));
				}
			} else {
				tooltip.add((Text.translatable("block.spectrum.present.tooltip.wrapped").formatted(Formatting.GRAY)));
			}
		} else {
			tooltip.add((Text.translatable("block.spectrum.present.tooltip.description").formatted(Formatting.GRAY)));
			tooltip.add((Text.translatable("block.spectrum.present.tooltip.description2").formatted(Formatting.GRAY)));
			tooltip.add((Text.translatable("item.minecraft.bundle.fullness", getBundledStacks(stack).count(), MAX_STORAGE_STACKS)).formatted(Formatting.GRAY));
		}
	}
	
	@Override
	public boolean canBeNested() {
		return false;
	}
	
}
