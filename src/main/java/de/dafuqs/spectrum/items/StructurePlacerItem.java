package de.dafuqs.spectrum.items;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import de.dafuqs.spectrum.api.item.CreativeOnlyItem;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.registries.SpectrumMultiblocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Rotation;

import java.util.List;

public class StructurePlacerItem extends Item implements CreativeOnlyItem {
	
	protected final ResourceLocation multiBlockIdentifier;
	
	public StructurePlacerItem(Properties settings, ResourceLocation multiBlockIdentifier) {
		super(settings);
		this.multiBlockIdentifier = multiBlockIdentifier;
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getPlayer() != null && context.getPlayer().isCreative()) {
			Multiblock multiblock = SpectrumMultiblocks.get(multiBlockIdentifier);
			if (multiblock != null) {
				Rotation blockRotation = Support.rotationFromDirection(context.getHorizontalDirection());
				multiblock.place(context.getLevel(), context.getClickedPos().above(), blockRotation);
				return InteractionResult.CONSUME;
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		CreativeOnlyItem.appendTooltip(tooltip);
	}
	
}
