package de.dafuqs.spectrum.blocks.fusion_shrine;

import de.dafuqs.spectrum.render.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.math.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import org.joml.Math;
import org.joml.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class FusionShrineBlockEntityRenderer<T extends FusionShrineBlockEntity> implements BlockEntityRenderer<T> {
	
	@SuppressWarnings("unused")
    public FusionShrineBlockEntityRenderer(Context ctx) {
	}
	
	@Override
	public void render(FusionShrineBlockEntity fusionShrineBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		// the fluid in the shrine
		FluidVariant fluidVariant = fusionShrineBlockEntity.getFluidVariant();
		if (!fluidVariant.isBlank()) {
			matrixStack.push();
			Sprite sprite = FluidVariantRendering.getSprite(fluidVariant);
			int[] colors = FluidRendering.unpackColorOf(fluidVariant, fusionShrineBlockEntity);
			FluidRendering.renderFluid(vertexConsumerProvider.getBuffer(RenderLayer.getTranslucent()), matrixStack.peek().getPositionMatrix(), sprite, light, overlay, 2, 14, 0.9F, 2, 14, colors);
			matrixStack.pop();
		}
		
		if (!fusionShrineBlockEntity.isEmpty()) {
			// the floating item stacks
			List<ItemStack> inventoryStacks = new ArrayList<>();
			
			for (int i = 0; i < fusionShrineBlockEntity.size(); i++) {
				ItemStack stack = fusionShrineBlockEntity.getStack(i);
				if (!stack.isEmpty()) {
					inventoryStacks.add(stack);
				}
			}
			
			float time = fusionShrineBlockEntity.getWorld().getTime() % 500000 + tickDelta;
			double radiant = Math.toRadians(360.0F / inventoryStacks.size());
			float distance = 1.2F;
			
			for (int i = 0; i < inventoryStacks.size(); i++) {
				matrixStack.push();
				double currentRadiant = radiant * i + (radiant * (time / 16.0) / (8.0F / inventoryStacks.size()));
				double height = Math.sin((time + currentRadiant) / 8.0) / 3.0; // item height
				matrixStack.translate(distance * Math.sin(currentRadiant) + 0.5, 1.5 + height, distance * Math.cos(currentRadiant) + 0.5); // position offset
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((time) * 2)); // item stack rotation
				
				MinecraftClient.getInstance().getItemRenderer().renderItem(inventoryStacks.get(i), ModelTransformationMode.GROUND, light, overlay, matrixStack, vertexConsumerProvider, fusionShrineBlockEntity.getWorld(), 0);
				matrixStack.pop();
			}
		}
	}
	
}
