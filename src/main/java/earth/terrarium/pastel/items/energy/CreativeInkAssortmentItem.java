package earth.terrarium.pastel.items.energy;

import earth.terrarium.pastel.api.energy.InkStorage;
import earth.terrarium.pastel.api.energy.InkStorageBlockEntity;
import earth.terrarium.pastel.api.energy.InkStorageItem;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.storage.CreativeInkStorage;
import earth.terrarium.pastel.api.item.CreativeOnlyItem;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CreativeInkAssortmentItem extends Item
    implements
    InkStorageItem<CreativeInkStorage>,
    CreativeOnlyItem,
    SlotBackgroundEffect {

    public CreativeInkAssortmentItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!world.isClientSide) {
            BlockEntity blockEntity = world.getBlockEntity(context.getClickedPos());
            if (blockEntity instanceof InkStorageBlockEntity<?> inkStorageBlockEntity) {
                inkStorageBlockEntity
                    .getEnergyStorage()
                    .fillCompletely();
                inkStorageBlockEntity.setInkDirty();
                blockEntity.setChanged();
            }
        }
        return super.useOn(context);
    }

    @Override
    public Drainability getDrainability() {
        return Drainability.ALWAYS;
    }

    @Override
    public CreativeInkStorage getEnergyStorage(ItemStack itemStack) {
        return new CreativeInkStorage();
    }

    // Omitting this would crash outside the dev env o.O
    @Override
    public ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }

    @Override
    public void setEnergyStorage(ItemStack itemStack, InkStorage storage) {
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        CreativeOnlyItem.appendTooltip(tooltip);
        getEnergyStorage(stack).addTooltip(tooltip);
    }

    @Override
    public SlotBackgroundEffect.SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        return SlotEffect.BORDER_FADE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float delta) {
        var colors = new ArrayList<InkColor>();

        if (player == null)
            return 0;

        var time = player
            .level()
            .getGameTime() % 864000;

        for (
            InkColor inkColor : PastelRegistries.INK_COLOR
        ) {
            colors.add(inkColor);
        }

        if (colors.size() == 1) {
            var color = colors.getFirst();
            return ColorHelper.colorVecToRGB(color.getColorVec());
        }

        var curColor = colors.get((int) (time % (30L * colors.size()) / 30));
        var nextColor = colors.get((int) ((time % (30L * colors.size()) / 30 + 1) % colors.size()));
        var blendFactor = (((float) time + delta) % 30) / 30F;

        return ColorHelper.interpolate(curColor.getTextColorVec(), nextColor.getTextColorVec(), blendFactor);
    }
}
