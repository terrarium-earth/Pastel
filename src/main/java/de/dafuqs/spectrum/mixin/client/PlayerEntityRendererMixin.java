package de.dafuqs.spectrum.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.spectrum.attachments.data.MiscPlayerData;
import de.dafuqs.spectrum.items.tools.LightGreatswordItem;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerRenderer.class)
public class PlayerEntityRendererMixin {

    @ModifyExpressionValue(method = "getArmPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"))
    private static UseAnim modifyUseAction(UseAnim original, @Local ItemStack itemStack, @Local(argsOnly = true) AbstractClientPlayer player) {
        if (itemStack.getItem() == SpectrumItems.NIGHT_SALTS) {
            return UseAnim.TOOT_HORN;
        }
        return original;
    }

    @ModifyReturnValue(method = "getArmPose", at = @At(value = "RETURN", ordinal = 1))
    private static HumanoidModel.ArmPose cumAction(HumanoidModel.ArmPose original, @Local ItemStack itemStack, @Local(argsOnly = true) AbstractClientPlayer player) {
        if (itemStack.getItem() instanceof LightGreatswordItem) {
            return HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }

        return original;
    }

    @ModifyReturnValue(method = "getArmPose", at = @At(value = "TAIL"))
    private static HumanoidModel.ArmPose lungeAction(HumanoidModel.ArmPose original, @Local(argsOnly = true) AbstractClientPlayer player) {
        if (MiscPlayerData.get(player).isLunging()) {
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }

        return original;
    }
}
