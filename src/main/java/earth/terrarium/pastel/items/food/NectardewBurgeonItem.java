package earth.terrarium.pastel.items.food;

import com.cmdpro.databank.hidden.types.BlockHiddenType;
import com.cmdpro.databank.hidden.types.ItemHiddenType;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NectardewBurgeonItem extends Item implements SlotBackgroundEffect {

    private final Component lore;

    public NectardewBurgeonItem(Properties settings, String lore) {
        super(settings);
        this.lore = Component.translatable(lore)
                             .withStyle(ChatFormatting.GRAY);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(lore);
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        return ItemHiddenType.isVisible(stack.getItem(), player) ? SlotEffect.PULSE : SlotEffect.NONE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return ItemHiddenType.isVisible(stack.getItem(), player) ? PastelMobEffects.ETERNAL_SLUMBER_COLOR : 0x0;
    }
}
