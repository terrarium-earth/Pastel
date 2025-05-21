package de.dafuqs.spectrum.blocks.fusion_shrine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.dafuqs.spectrum.render.FluidRendering;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.*;
import net.neoforged.neoforge.client.textures.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Math;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FusionShrineBlockEntityRenderer<T extends FusionShrineBlockEntity> implements BlockEntityRenderer<T> {
	
	@SuppressWarnings("unused")
    public FusionShrineBlockEntityRenderer(Context ctx) {
	}
	
	@Override
	public void render(FusionShrineBlockEntity shrine, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light, int overlay) {
		// the fluid in the shrine
		var fluid = shrine.getTank().getFluid();
		if (!fluid.isEmpty()) {
			poseStack.pushPose();
			var renderData = IClientFluidTypeExtensions.of(fluid.getFluid());
			TextureAtlasSprite sprite = FluidSpriteCache.getSprite(renderData.getStillTexture(fluid));
			int[] colors = FluidRendering.unpackColor(renderData.getTintColor(fluid.getFluid().defaultFluidState(), shrine.getLevel(), shrine.getBlockPos()));
			FluidRendering.renderFluid(vertexConsumerProvider.getBuffer(RenderType.translucent()), poseStack.last().pose(), sprite, light, overlay, 2, 14, 0.9F, 2, 14, colors);
			poseStack.popPose();
		}
		
		if (!shrine.isEmpty()) {
			// the floating item stacks
			List<ItemStack> inventoryStacks = new ArrayList<>();
			
			for (int i = 0; i < shrine.getContainerSize(); i++) {
				ItemStack stack = shrine.getItem(i);
				if (!stack.isEmpty()) {
					inventoryStacks.add(stack);
				}
			}
			
			float time = shrine.getLevel().getGameTime() % 500000 + tickDelta;
			double radiant = Math.toRadians(360.0F / inventoryStacks.size());
			float distance = 1.2F;
			
			for (int i = 0; i < inventoryStacks.size(); i++) {
				poseStack.pushPose();
				double currentRadiant = radiant * i + (radiant * (time / 16.0) / (8.0F / inventoryStacks.size()));
				double height = Math.sin((time + currentRadiant) / 8.0) / 3.0; // item height
				poseStack.translate(distance * Math.sin(currentRadiant) + 0.5, 1.5 + height, distance * Math.cos(currentRadiant) + 0.5); // position offset
				poseStack.mulPose(Axis.YP.rotationDegrees((time) * 2)); // item stack rotation
				
				Minecraft.getInstance().getItemRenderer().renderStatic(inventoryStacks.get(i), ItemDisplayContext.GROUND, light, overlay, poseStack, vertexConsumerProvider, shrine.getLevel(), 0);
				poseStack.popPose();
			}
		}
	}
	
}
