package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.entity.entity.*;
import net.fabricmc.api.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.joml.*;

@Environment(EnvType.CLIENT)
public class LightShardEntityRenderer extends EntityRenderer<LightShardEntity> {

    public LightShardEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
    
    @Override
    public void render(LightShardEntity shard, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
	
		var age = shard.age;
        var alpha = MathHelper.clamp(1 - MathHelper.lerp(tickDelta, shard.getVanishingProgress(age - 1), shard.getVanishingProgress(age)), 0F, 1F);
		var scaleFactor = MathHelper.sin((age + tickDelta) / 16F) / 10F + shard.getScaleOffset();
		
		matrices.multiply(this.dispatcher.getRotation());
		matrices.scale(scaleFactor, scaleFactor, scaleFactor);
		
		VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(getTexture(shard)));
		MatrixStack.Entry matrix = matrices.peek();
		Matrix4f positions = matrix.getPositionMatrix();
        
        consumer.vertex(positions, 0, 0, 0).color(1f, 1f, 1f, alpha).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
        consumer.vertex(positions, 1, 0, 0).color(1f, 1f, 1f, alpha).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
        consumer.vertex(positions, 1, 1, 0).color(1f, 1f, 1f, alpha).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
        consumer.vertex(positions, 0, 1, 0).color(1f, 1f, 1f, alpha).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0, 1, 0);
        
        matrices.pop();
        
        super.render(shard, yaw, tickDelta, matrices, vertexConsumers, light);
    }
    
    @Override
    public Identifier getTexture(LightShardEntity entity) {
        return entity.getTexture();
    }
}
