package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// gets thrown as copy instead of getting removed from the player's inv
public class FractalBidentItem extends MalachiteBidentItem implements SlotBackgroundEffect, InkPowered {

    public static final InkCost MIRROR_IMAGE_COST = new InkCost(InkColors.WHITE, 25);

    public FractalBidentItem(
        Item.Properties settings, double attackSpeed, double damage, float armorPierce, float protPierce) {
        super(settings, attackSpeed, damage, armorPierce, protPierce);
    }

    @Override
    public boolean isThrownAsMirrorImage(ItemStack stack, ServerLevel world, Player player) {
        return !isDisabled(stack) && InkPowered.tryDrainEnergy(player, MIRROR_IMAGE_COST);
    }

    @Override
    public float getThrowSpeed(ItemStack stack) {
        return isDisabled(stack) ? super.getThrowSpeed(stack) : 5.0F;
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(MIRROR_IMAGE_COST.color());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.fractal_glass_crest_bident.tooltip")
                             .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.pastel.fractal_glass_crest_bident.tooltip2")
                             .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.pastel.fractal_glass_crest_bident.tooltip3")
                             .withStyle(ChatFormatting.GRAY));
        addInkPoweredTooltip(tooltip);
    }

    @Override
    public boolean canBeDisabled() {
        return true;
    }

    @Override
    public SlotBackgroundEffect.SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        var usable = InkPowered.hasAvailableInk(player, MIRROR_IMAGE_COST);
        return usable ? SlotBackgroundEffect.SlotEffect.BORDER_FADE : SlotBackgroundEffect.SlotEffect.NONE;
    }

    @Override
    public float getProtReduction(LivingEntity target, ItemStack stack) {
        return 0.25F;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return InkColors.PURPLE_COLOR;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.EFFICIENCY) ||
               enchantment.is(Enchantments.POWER);
    }

}
