package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.ClientRevelationHolder;
import de.dafuqs.revelationary.RevelationRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(Item.class)
public abstract class ItemMixin {
	@Inject(method = "getName", at = @At("HEAD"), cancellable = true)
	public void revelationary$getCloakedName(ItemStack stack, CallbackInfoReturnable<Component> callbackInfoReturnable) {
		Item thisItem = (Item) (Object) this;
		if (ClientRevelationHolder.isCloaked(thisItem)) {
			callbackInfoReturnable.setReturnValue(RevelationRegistry.getTranslationString(thisItem));
		}
	}
}
