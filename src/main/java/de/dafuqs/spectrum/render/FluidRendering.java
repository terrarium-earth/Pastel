package de.dafuqs.spectrum.render;

import net.fabricmc.fabric.api.transfer.v1.client.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.*;
import org.jetbrains.annotations.*;
import org.joml.*;

public class FluidRendering {
	
	public static void renderFluid(VertexConsumer builder, Matrix4f pos, Sprite sprite, int light, int overlay, float x1, float x2, float y, float z1, float z2, int[] color) {
		// Convert block size to pixel size
		final float px1 = x1 * 16;
		final float px2 = x2 * 16;
		final float pz1 = z1 * 16;
		final float pz2 = z2 * 16;
		
		final float u1 = sprite.getFrameU(px1);
		final float u2 = sprite.getFrameU(px2);
		final float v1 = sprite.getFrameV(pz1);
		final float v2 = sprite.getFrameV(pz2);
		builder.vertex(pos, x1, y, z2).color(color[1], color[2], color[3], color[0]).texture(u1, v2).overlay(overlay).light(light).normal(0f, 1f, 0f);
		builder.vertex(pos, x2, y, z2).color(color[1], color[2], color[3], color[0]).texture(u2, v2).overlay(overlay).light(light).normal(0f, 1f, 0f);
		builder.vertex(pos, x2, y, z1).color(color[1], color[2], color[3], color[0]).texture(u2, v1).overlay(overlay).light(light).normal(0f, 1f, 0f);
		builder.vertex(pos, x1, y, z1).color(color[1], color[2], color[3], color[0]).texture(u1, v1).overlay(overlay).light(light).normal(0f, 1f, 0f);
	}
	
	public static int colorOf(FluidVariant fluid, @Nullable BlockEntity entity) {
		return entity == null ? FluidVariantRendering.getColor(fluid, null, null) : FluidVariantRendering.getColor(fluid, entity.getWorld(), entity.getPos());
	}
	
	public static int[] unpackColorOf(FluidVariant fluid, @Nullable BlockEntity entity) {
		return unpackColor(colorOf(fluid, entity));
	}
	
	public static int[] unpackColor(int color) {
		final int[] colors = new int[4];
		colors[0] = color >> 24 & 0xff; // alpha
		colors[1] = color >> 16 & 0xff; // red
		colors[2] = color >> 8 & 0xff; // green
		colors[3] = color & 0xff; // blue
		return colors;
	}
}
