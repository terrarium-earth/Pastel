package de.dafuqs.spectrum.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class HeldItemRendererMixin {

    @Shadow
    private ItemStack offHandItem;

    @Shadow
    private void renderOneHandedMap(PoseStack matrices, MultiBufferSource vertexConsumers, int light, float equipProgress, HumanoidArm arm, float swingProgress, ItemStack stack) { }

    @Shadow
    private void renderTwoHandedMap(PoseStack matrices, MultiBufferSource vertexConsumers, int light, float pitch, float equipProgress, float swingProgress) { }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "net/minecraft/world/item/ItemStack.is (Lnet/minecraft/world/item/Item;)Z", ordinal = 1), cancellable = true)
    private void spectrum$renderFirstPersonItem(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        if (item.is(SpectrumItems.ARTISANS_ATLAS)) {
            boolean isInMainHand = hand == InteractionHand.MAIN_HAND;
            if (isInMainHand && this.offHandItem.isEmpty()) {
                this.renderTwoHandedMap(matrices, vertexConsumers, light, pitch, equipProgress, swingProgress);
            } else {
                HumanoidArm arm = isInMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
                this.renderOneHandedMap(matrices, vertexConsumers, light, equipProgress, arm, swingProgress, item);
            }
            matrices.popPose();
            ci.cancel();
        }
    }

}
