package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import earth.terrarium.pastel.helpers.MobEffectHelper;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EffectRenderingInventoryScreen.class)
public class AbstractInventoryScreenMixin {
	
	@ModifyVariable(method = "renderBackgrounds", at = @At("STORE"))
	private MobEffectInstance saveEffect(MobEffectInstance value, @Share("effect") LocalRef<MobEffectInstance> effect) {
		effect.set(value);
		return value;
	}
	
	@ModifyArg(method = "renderBackgrounds", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blitSprite (Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0))
	private ResourceLocation modifyWideBackground(ResourceLocation texture, @Local MobEffectInstance effect) {
		return MobEffectHelper.getTextureLocation(texture, effect, MobEffectHelper.RenderType.GUI_LARGE);
	}
	
	@ModifyArg(method = "renderBackgrounds", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blitSprite (Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1))
	private ResourceLocation modifyBackground(ResourceLocation texture, @Share("effect") LocalRef<MobEffectInstance> effect) {
		return MobEffectHelper.getTextureLocation(texture, effect.get(), MobEffectHelper.RenderType.GUI_SMALL);
	}
	
}
