package earth.terrarium.pastel.items;

import com.klikli_dev.modonomicon.client.gui.book.BookAddress;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.registries.SpectrumItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CookbookItem extends Item {
	
	public BookAddress bookAddress;
	private final int toolTipColor;
	
	public CookbookItem(Properties settings, BookAddress bookAddress) {
		this(settings, bookAddress, -1);
	}
	
	public CookbookItem(Properties settings, BookAddress bookAddress, int toolTipColor) {
		super(settings);
		this.bookAddress = bookAddress;
		this.toolTipColor = toolTipColor;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (!world.isClientSide) {
			user.awardStat(Stats.ITEM_USED.get(this));
			
			return InteractionResultHolder.success(user.getItemInHand(hand));
		} else {
			try {
				openGuidebookPage(this.bookAddress);
			} catch (NullPointerException e) {
				SpectrumCommon.logError(user.getName().getString() + " used a CookbookItem to open the guidebook page " + this.bookAddress + " but it does not exist");
			}
		}
		
		return InteractionResultHolder.consume(user.getItemInHand(hand));
	}
	
	private void openGuidebookPage(BookAddress address) {
		if (SpectrumItems.GUIDEBOOK.get() instanceof GuidebookItem guidebook) {
			guidebook.openGuidebook(address);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		if (toolTipColor == -1) {
			tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
			return;
		}

		tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(s -> s.withColor(toolTipColor)));
	}
	
}
