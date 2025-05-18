package de.dafuqs.spectrum.items.energy;

import de.dafuqs.spectrum.api.energy.InkStorageItem;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.storage.SingleInkStorage;
import de.dafuqs.spectrum.api.item.LoomPatternProvider;
import de.dafuqs.spectrum.api.render.ExtendedItemBarProvider;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.registries.SpectrumBannerPatterns;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InkFlaskItem extends Item implements InkStorageItem<SingleInkStorage>, LoomPatternProvider, ExtendedItemBarProvider {
	
	private final long maxEnergy;
	
	public InkFlaskItem(Properties settings, long maxEnergy) {
		super(settings);
		this.maxEnergy = maxEnergy;
	}
	
	@Override
	public Drainability getDrainability() {
		return Drainability.ALWAYS;
	}
	
	@Override
	public SingleInkStorage getEnergyStorage(ItemStack itemStack) {
		var storage = itemStack.get(SpectrumDataComponentTypes.INK_STORAGE);
		if (storage != null)
			for (var entry : storage.storedEnergy().entrySet())
				return new SingleInkStorage(storage.maxEnergyTotal(), entry.getKey(), entry.getValue());
		return new SingleInkStorage(this.maxEnergy);
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
		addBannerPatternProviderTooltip(tooltip);
	}
	
	public ItemStack getFullStack(InkColor color) {
		ItemStack stack = this.getDefaultInstance();
		SingleInkStorage storage = getEnergyStorage(stack);
		storage.fillCompletely();
		storage.convertColor(color);
		setEnergyStorage(stack, storage);
		return stack;
	}
	
	@Override
	public ResourceKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.INK_FLASK;
	}
	
	@Override
	public int barCount(ItemStack stack) {
		return 1;
	}
	
	@Override
	public BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
		var storage = getEnergyStorage(stack);
		
		if (storage.isEmpty())
			return ExtendedItemBarProvider.PASS;
		
		var color = storage.getStoredColor();
		var progress = Support.getSensiblePercent(storage.getCurrentTotal(), storage.getMaxTotal(), 14);
		return new BarSignature(1, 13, 14, progress, 1, color.getColorInt(), 2, ExtendedItemBarProvider.DEFAULT_BACKGROUND_COLOR);
	}
}
