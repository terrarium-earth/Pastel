package earth.terrarium.pastel.items.bundles;

import earth.terrarium.pastel.components.ExtendedBundleComponent;
import earth.terrarium.pastel.registries.SpectrumDataComponentTypes;
import earth.terrarium.pastel.registries.SpectrumItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class PlaceableBundleBlockItem extends BlockItem {
	
	private final ExtendedBundleComponent component;
	
	public PlaceableBundleBlockItem(ExtendedBundleComponent component, Block block, Properties settings) {
		super(block, settings);
		this.component = component;
	}
	
	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		stack.set(SpectrumDataComponentTypes.EXTENDED_BUNDLE, component);
		return stack;
	}
	
	@Override
	public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.get().overrideStackedOnOther(stack, slot, clickType, player);
	}
	
	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.get().overrideOtherStackedOnMe(stack, otherStack, slot, clickType, player, cursorStackReference);
	}
	
	@Override
	public boolean isBarVisible(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.get().isBarVisible(stack);
	}
	
	@Override
	public int getBarWidth(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.get().getBarWidth(stack);
	}
	
	@Override
	public int getBarColor(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.get().getBarColor(stack);
	}
	
	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.get().getTooltipImage(stack);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
        SpectrumItems.EXTENDED_BUNDLE_ITEM.get().appendHoverText(stack, context, tooltip, type);
	}
	
	@Override
	public void onDestroyed(ItemEntity entity) {
        SpectrumItems.EXTENDED_BUNDLE_ITEM.get().onDestroyed(entity);
		super.onDestroyed(entity);
	}
	
}
