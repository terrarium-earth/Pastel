package earth.terrarium.pastel.items.energy;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkStorageItem;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.energy.storage.TotalCappedElementalMixingInkStorage;
import earth.terrarium.pastel.api.item.LoomPatternProvider;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.registries.PastelBannerPatterns;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArtistsPaletteItem extends PastelTrinketItem implements InkStorageItem<TotalCappedElementalMixingInkStorage>, LoomPatternProvider, ExtendedItemBar {
	
	private final long maxEnergyTotal;
	
	public ArtistsPaletteItem(Properties settings, long maxEnergyTotal) {
		super(settings, PastelCommon.locate("unlocks/trinkets/artists_palette"));
		this.maxEnergyTotal = maxEnergyTotal;
	}
	
	@Override
	public Drainability getDrainability() {
		return Drainability.PLAYER_ONLY;
	}
	
	@Override
	public TotalCappedElementalMixingInkStorage getEnergyStorage(ItemStack itemStack) {
		var storage = itemStack.get(PastelDataComponentTypes.INK_STORAGE);
		if (storage != null)
			return new TotalCappedElementalMixingInkStorage(storage.maxEnergyTotal(), storage.storedEnergy());
		return new TotalCappedElementalMixingInkStorage(this.maxEnergyTotal, Map.of());
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
		tooltip.add(Component.translatable("item.pastel.pigment_palette.tooltip.target").withStyle(ChatFormatting.GRAY));
		getEnergyStorage(stack).addTooltip(tooltip);
		addBannerPatternProviderTooltip(tooltip);
	}
	
	@Override
	public ResourceKey<BannerPattern> getPattern() {
		return PastelBannerPatterns.PALETTE;
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
		
		for (InkColor inkColor : InkColors.elementals()) {
			if (storage.getEnergy(inkColor) > 0)
				colors.add(inkColor);
		}
		
		var progress = Support.getSensiblePercent(storage.getCurrentTotal(), storage.getMaxTotal(), 14);
		if (colors.size() == 1) {
			var color = colors.getFirst();
			return new ExtendedItemBar.BarSignature(1, 13, 14, progress, 1, color.getColorInt() | 0xFF000000, 2, DEFAULT_BACKGROUND_COLOR);
		}

		if(colors.isEmpty()) return new ExtendedItemBar.BarSignature(1, 13, 14, progress, 1, 0xFF000000, 2, DEFAULT_BACKGROUND_COLOR);

		var delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
		var curColor = colors.get((int) (time % (30L * colors.size()) / 30));
		var nextColor = colors.get((int) ((time % (30L * colors.size()) / 30 + 1) % colors.size()));
		
		
		var blendFactor = (((float) time + delta) % 30) / 30F;
		var blendedColor = ColorHelper.interpolate(curColor.getTextColorVec(), nextColor.getTextColorVec(), blendFactor);
		
		return new ExtendedItemBar.BarSignature(1, 13, 14, progress, 1, blendedColor, 2, DEFAULT_BACKGROUND_COLOR);
	}
	
}
