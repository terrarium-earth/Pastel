package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiGraphics.class)
public abstract class DrawContextMixin {
	
	@WrapWithCondition(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.fill (Lnet/minecraft/client/renderer/RenderType;IIIII)V", ordinal = 0))
	protected boolean disableVanillaBackground(GuiGraphics instance, RenderType layer, int x1, int y1, int x2, int y2, int color, @Local(argsOnly = true) ItemStack stack) {
		if (stack.getItem() instanceof ExtendedItemBar provider) {
			return provider.allowVanillaDurabilityBarRendering(Minecraft.getInstance().player, stack);
		}
		return true;
	}
	
	@WrapWithCondition(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.fill (Lnet/minecraft/client/renderer/RenderType;IIIII)V", ordinal = 1))
	protected boolean disableVanillaBar(GuiGraphics instance, RenderType layer, int x1, int y1, int x2, int y2, int color, @Local(argsOnly = true) ItemStack stack) {
		if (stack.getItem() instanceof ExtendedItemBar bar) {
			return bar.allowVanillaDurabilityBarRendering(Minecraft.getInstance().player, stack);
		}
		return true;
	}
}
