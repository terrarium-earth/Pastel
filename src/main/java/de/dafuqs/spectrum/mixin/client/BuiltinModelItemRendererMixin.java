package de.dafuqs.spectrum.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlock;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullType;
import de.dafuqs.spectrum.blocks.mob_head.client.SpectrumSkullBlockEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BlockEntityWithoutLevelRenderer.class)
public abstract class BuiltinModelItemRendererMixin {
	
	@Inject(method = "renderByItem", at = @At("HEAD"), cancellable = true)
	private void getModel(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, CallbackInfo ci) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			if (blockItem.getBlock() instanceof SpectrumSkullBlock spectrumSkullBlock) {
				SpectrumSkullType spectrumSkullType = spectrumSkullBlock.getType();
				SpectrumSkullBlockEntityRenderer.renderModels(0.0F, matrices, vertexConsumers, light, spectrumSkullType, null, 180.0F);
				ci.cancel();
			}
		}
	}
	
}
