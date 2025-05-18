package de.dafuqs.spectrum.items.energy;

import de.dafuqs.spectrum.api.energy.InkStorageItem;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.storage.IndividualCappedInkStorage;
import de.dafuqs.spectrum.api.render.ExtendedItemBarProvider;
import de.dafuqs.spectrum.helpers.SpectrumColorHelper;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
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

public class InkAssortmentItem extends Item implements InkStorageItem<IndividualCappedInkStorage>, ExtendedItemBarProvider {
	
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
		var storage = itemStack.get(SpectrumDataComponentTypes.INK_STORAGE);
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
	public ExtendedItemBarProvider.BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
		var storage = getEnergyStorage(stack);
		var colors = new ArrayList<InkColor>();
		
		if (player == null || storage.isEmpty())
			return ExtendedItemBarProvider.PASS;
		
		var time = player.level().getGameTime() % 864000;
		
		for (InkColor inkColor : SpectrumRegistries.INK_COLOR) {
			if (storage.getEnergy(inkColor) > 0)
				colors.add(inkColor);
		}
		
		var progress = Support.getSensiblePercent(storage.getCurrentTotal(), storage.getMaxTotal(), 14);
		if (colors.size() == 1) {
			var color = colors.getFirst();
			return new ExtendedItemBarProvider.BarSignature(1, 13, 14, progress, 1, SpectrumColorHelper.colorVecToRGB(color.getColorVec()) | 0xFF000000, 2, DEFAULT_BACKGROUND_COLOR);
		}
		
		var delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
		var curColor = colors.get((int) (time % (30L * colors.size()) / 30));
		var nextColor = colors.get((int) ((time % (30L * colors.size()) / 30 + 1) % colors.size()));
		
		
		var blendFactor = (((float) time + delta) % 30) / 30F;
		var blendedColor = SpectrumColorHelper.interpolate(curColor.getTextColorVec(), nextColor.getTextColorVec(), blendFactor);
		
		return new ExtendedItemBarProvider.BarSignature(1, 13, 14, progress, 1, blendedColor, 2, DEFAULT_BACKGROUND_COLOR);
	}
}
