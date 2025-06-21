package earth.terrarium.pastel.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.blocks.mob_head.client.PastelSkullBlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(BlockEntityWithoutLevelRenderer.class)
public abstract class BuiltinModelItemRendererMixin {
	
	@Inject(method = "renderByItem", at = @At("HEAD"), cancellable = true)
	private void getModel(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, CallbackInfo ci) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			if (blockItem.getBlock() instanceof PastelSkullBlock pastelSkullBlock) {
				PastelSkullType pastelSkullType = pastelSkullBlock.getType();
				PastelSkullBlockEntityRenderer.renderModels(0.0F, matrices, vertexConsumers, light, pastelSkullType, null, 180.0F);
				ci.cancel();
			}
		}
	}
	
}
