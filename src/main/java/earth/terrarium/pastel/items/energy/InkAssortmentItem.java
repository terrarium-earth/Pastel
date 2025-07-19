package earth.terrarium.pastel.items.energy;

import earth.terrarium.pastel.api.energy.InkStorageItem;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.storage.IndividualCappedInkStorage;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InkAssortmentItem extends Item implements InkStorageItem<IndividualCappedInkStorage>, ExtendedItemBar {
	
	private final long maxEnergy;
	
	public InkAssortmentItem(Properties settings, long maxEnergy) {
		super(settings);
		this.maxEnergy = maxEnergy;
	}
	
	@Override
	public Drainability getDrainability() {
		return Drainability.ALWAYS;
	}
	
	@Override
	public IndividualCappedInkStorage getEnergyStorage(ItemStack itemStack) {
		var storage = itemStack.get(PastelDataComponentTypes.INK_STORAGE);
		if (storage != null)
			return new IndividualCappedInkStorage(storage.maxPerColor(), storage.storedEnergy());
		return new IndividualCappedInkStorage(this.maxEnergy);
	}
	
	// Omitting this would crash outside the dev env o.O
	@Override
	public ItemStack getDefaultInstance() {
		return super.getDefaultInstance();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		getEnergyStorage(stack).addTooltip(tooltip);
	}
	
	@Override
	public int barCount(ItemStack stack) {
		return 1;
	}
	
	@Override
	public ExtendedItemBar.BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
		var storage = getEnergyStorage(stack);
		var colors = new ArrayList<InkColor>();
		
		if (player == null || storage.isEmpty())
			return ExtendedItemBar.PASS;
		
		var time = player.level().getGameTime() % 864000;
		
		for (InkColor inkColor : PastelRegistries.INK_COLOR) {
			if (storage.getEnergy(inkColor) > 0)
				colors.add(inkColor);
		}
		
		var progress = Support.getSensiblePercent(storage.getCurrentTotal(), storage.getMaxTotal(), 14);
		if (colors.size() == 1) {
			var color = colors.getFirst();
			return new ExtendedItemBar.BarSignature(1, 13, 14, progress, 1, ColorHelper.colorVecToRGB(color.getColorVec()) | 0xFF000000, 2, DEFAULT_BACKGROUND_COLOR);
		}
		
		var delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
		var curColor = colors.get((int) (time % (30L * colors.size()) / 30));
		var nextColor = colors.get((int) ((time % (30L * colors.size()) / 30 + 1) % colors.size()));
		
		
		var blendFactor = (((float) time + delta) % 30) / 30F;
		var blendedColor = ColorHelper.interpolate(curColor.getTextColorVec(), nextColor.getTextColorVec(), blendFactor);
		
		return new ExtendedItemBar.BarSignature(1, 13, 14, progress, 1, blendedColor, 2, DEFAULT_BACKGROUND_COLOR);
	}
}
