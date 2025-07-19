package earth.terrarium.pastel.mixin.client;

import earth.terrarium.pastel.components.CustomPotionDataComponent;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mixin({PotionItem.class, LingeringPotionItem.class, TippedArrowItem.class})
public abstract class PotionItemClientMixin {

    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void makePotionUnidentifiable(
        ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type, CallbackInfo ci) {
        CustomPotionDataComponent component = stack.get(PastelDataComponentTypes.CUSTOM_POTION_DATA);
        if (component != null && component.unidentifiable()) {
            tooltip.add(Component.translatable("item.pastel.potion.tooltip.unidentifiable"));
            ci.cancel();
        }
    }

}
