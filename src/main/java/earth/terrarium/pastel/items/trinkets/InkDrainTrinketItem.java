package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.api.energy.InkStorage;
import earth.terrarium.pastel.api.energy.InkStorageItem;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.storage.FixedSingleInkStorage;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.components.InkStorageComponent;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InkDrainTrinketItem extends PastelTrinketItem
    implements InkStorageItem<FixedSingleInkStorage>, ExtendedItemBar, SlotBackgroundEffect {

    /**
     * TODO: set to the original value again, once ink networking is in. Currently the original max value cannot be
     * achieved.
     * Players WILL grind out that amount of pigment in some way and will then complain
     * <p>
     * lmao trueee ~ Azzyypaaras.
     */
    public static final int MAX_INK = 3276800; // 1677721600;
    public final InkColor inkColor;

    public static final Map<InkColor, Item> BY_COLOR = new HashMap<>();

    public InkDrainTrinketItem(Properties settings, ResourceLocation unlockIdentifier, InkColor inkColor) {
        super(settings, unlockIdentifier);
        this.inkColor = inkColor;

        if (BY_COLOR.containsKey(inkColor))
            throw new IllegalStateException("Attempted to register multiple ink upgrade-ables to the same color");

        BY_COLOR.put(inkColor, this);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
        long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());

        if (storedInk >= MAX_INK) {
            tooltip.add(Component.translatable("pastel.tooltip.ink_drain.tooltip.maxed_out")
                                 .withStyle(ChatFormatting.GRAY));
        } else {
            long nextStepInk;
            int pow = 0;
            do {
                nextStepInk = (long) (100 * Math.pow(8, pow));
                pow++;
            } while (storedInk >= nextStepInk);

            tooltip.add(Component.translatable(
                                     "pastel.tooltip.ink_drain.tooltip.ink_for_next_step", storedInk,
                                     inkStorage.getStoredColor()
                                               .getColoredInkName(),
                                     Support.getShortenedNumberString(nextStepInk - storedInk)
                                 )
                                 .withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isMaxedOut(stack);
    }

    private boolean isMaxedOut(ItemStack stack) {
        return getEnergyStorage(stack).isFull();
    }

    // Omitting this would crash outside the dev env o.O
    @Override
    public ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }

    @Override
    public Drainability getDrainability() {
        return Drainability.NEVER;
    }

    @Override
    public FixedSingleInkStorage getEnergyStorage(ItemStack itemStack) {
        var storage = itemStack.get(PastelDataComponentTypes.INK_STORAGE);
        if (storage != null)
            for (var entry : storage.storedEnergy()
                                    .entrySet())
                return new FixedSingleInkStorage(storage.maxEnergyTotal(), entry.getKey(), entry.getValue());
        return new FixedSingleInkStorage(MAX_INK, inkColor);
    }

    @Override
    public void setEnergyStorage(ItemStack itemStack, InkStorage storage) {
        itemStack.set(PastelDataComponentTypes.INK_STORAGE, new InkStorageComponent(storage));
        itemStack.set(
            DataComponents.RARITY, storage.isFull() ? Rarity.EPIC : super.getDefaultInstance()
                                                                         .get(DataComponents.RARITY)
        );
    }

    @Override
    public ItemStack getFullStack() {
        return InkStorageItem.super.getFullStack();
    }

    @Override
    public int barCount(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean allowVanillaDurabilityBarRendering(@Nullable Player player, ItemStack stack) {
        return false;
    }

    @Override
    public BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
        var inkTank = getEnergyStorage(stack);
        var progress = (int) Math.round(
            Mth.clampedLerp(0, 14, Math.log(inkTank.getEnergy(inkColor) / 100.0f) / Math.log(8) / 5.0F));

        if (progress == 0 || progress == 14)
            return PASS;

        return new BarSignature(
            1, 13, 14, progress, 1, inkColor.getTextColorInt(), 2, ExtendedItemBar.DEFAULT_BACKGROUND_COLOR);
    }

    @Override
    public float getEffectOpacity(@Nullable Player player, ItemStack stack, float tickDelta) {
        var inkTank = getEnergyStorage(stack);
        return (float) (Math.log(inkTank.getEnergy(inkColor) / 100.0f) / Math.log(8) / 5.0F);
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        var inkTank = getEnergyStorage(stack);
        return inkTank.isFull() ? SlotEffect.PULSE : SlotEffect.NONE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return inkColor.getColorInt();
    }
}
