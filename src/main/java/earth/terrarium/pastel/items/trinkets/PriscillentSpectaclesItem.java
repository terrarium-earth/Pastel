package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.attachments.data.SpectacleData;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelLevels;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class PriscillentSpectaclesItem extends PastelTrinketItem implements InkPowered, ExtendedItemBar {

    public PriscillentSpectaclesItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/glow_vision_goggles"));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);

        if (slotContext.entity() instanceof ServerPlayer player) {
            player.getData(SpectacleData.ATTACHMENT).tickServer();
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);

        if (slotContext.entity() instanceof ServerPlayer player) {
            player.getData(SpectacleData.ATTACHMENT).sync();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (InkPowered.canUseClient()) {
            tooltip.add(Component.translatable(
                "item.pastel.glow_vision_goggles.tooltip_with_ink", SpectacleData.INK_COST.color()
                                                                            .getColoredInkName()
            ));
        } else {
            tooltip.add(Component.translatable("item.pastel.glow_vision_goggles.tooltip"));
        }
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(SpectacleData.INK_COST.color());
    }

    @Override
    public int barCount(ItemStack stack) {
        return 1;
    }

    @Override
    public BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
        if (player == null || PastelTrinketItem.getFirstEquipped(player, PastelItems.GLOW_VISION_GOGGLES.get())
                                               .map(s -> s != stack).orElse(true))
            return ExtendedItemBar.PASS;

        var data = player.getData(SpectacleData.ATTACHMENT);
        var progress = (int) Math.floor(Mth.clampedLerp(
            0, 13, data.getRemainingDuration()
        ));
        return new BarSignature(2, 13, 13, progress, 1, 0xFFffd67d, 2, 0xFF0b0525);
    }
}
