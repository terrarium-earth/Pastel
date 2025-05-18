package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.ClientRevelationHolder;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelShaper.class)
public abstract class ItemModelsMixin {
    @Shadow
    public abstract BakedModel getItemModel(ItemStack stack);

    @Inject(method = "getItemModel", at = @At("HEAD"), cancellable = true)
    private void revelationary$getModel(ItemStack itemStack, CallbackInfoReturnable<BakedModel> callbackInfoReturnable) {
        if (ClientRevelationHolder.isCloaked(itemStack.getItem())) {
            Item destinationItem = ClientRevelationHolder.getCloakTarget(itemStack.getItem());
            BakedModel overriddenModel = getItemModel(destinationItem.getDefaultInstance());
            callbackInfoReturnable.setReturnValue(overriddenModel);
        }
    }
}
