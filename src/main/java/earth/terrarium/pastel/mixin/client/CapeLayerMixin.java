package earth.terrarium.pastel.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.compat.vanityslots.VanitySlotsCompat;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public class CapeLayerMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        AbstractClientPlayer livingEntity,
        float limbSwing,
        float limbSwingAmount,
        float partialTicks,
        float ageInTicks,
        float netHeadYaw,
        float headPitch,
        CallbackInfo callbackInfo
    ) {
        ItemStack chestStack = VanitySlotsCompat.getEquippedStack(livingEntity, EquipmentSlot.CHEST);
        if (chestStack.getItem() == PastelItems.BEDROCK_CHESTPLATE.get()) {
            // TODO - Cancel for now, as the new armor tailoring system is not implemented yet
            callbackInfo.cancel();
        }
    }
}
