package earth.terrarium.pastel.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
	
	@Shadow
	public Camera camera;
	
	@Shadow
	private static void fireVertex(PoseStack.Pose entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
	}
	
	@Inject(method = "renderFlame", at = @At(value = "HEAD"), cancellable = true)
	public void render(PoseStack matrices, MultiBufferSource vertexConsumers, Entity entity, Quaternionf rotation, CallbackInfo ci) {
		if (entity instanceof LivingEntity livingEntity && PrimordialFireData.isOnPrimordialFire(livingEntity)) {
			ci.cancel();
		}
	}
	
	
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.AFTER))
	public <E extends Entity> void render(E entity, double x, double y, double z, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
		if (entity instanceof LivingEntity livingEntity && PrimordialFireData.isOnPrimordialFire(livingEntity)) {
			renderPrimordialFire(matrices, vertexConsumers, entity);
		}
	}
	
	@Unique
	private void renderPrimordialFire(PoseStack matrices, MultiBufferSource vertexConsumers, Entity entity) {
		TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(PastelCommon.locate("block/primordial_fire_0"));
		TextureAtlasSprite sprite2 = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(PastelCommon.locate("block/primordial_fire_1"));
		matrices.pushPose();
		float f = entity.getBbWidth() * 1.4F;
		matrices.scale(f, f, f);
		float g = 0.5F;
		float i = entity.getBbHeight() / f;
		float j = 0.0F;
		matrices.mulPose(Axis.YP.rotationDegrees(-this.camera.getYRot()));
		matrices.translate(0.0, 0.0, (-0.3F + (float) ((int) i) * 0.02F));
		float k = 0.0F;
		int l = 0;
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(Sheets.cutoutBlockSheet());
		
		for (PoseStack.Pose entry = matrices.last(); i > 0.0F; ++l) {
			TextureAtlasSprite sprite3 = l % 2 == 0 ? sprite : sprite2;
			float m = sprite3.getU0();
			float n = sprite3.getV0();
			float o = sprite3.getU1();
			float p = sprite3.getV1();
			if (l / 2 % 2 == 0) {
				float q = o;
				o = m;
				m = q;
			}
			
			fireVertex(entry, vertexConsumer, g - 0.0F, 0.0F - j, k, o, p);
			fireVertex(entry, vertexConsumer, -g - 0.0F, 0.0F - j, k, m, p);
			fireVertex(entry, vertexConsumer, -g - 0.0F, 1.4F - j, k, m, n);
			fireVertex(entry, vertexConsumer, g - 0.0F, 1.4F - j, k, o, n);
			i -= 0.45F;
			j -= 0.45F;
			g *= 0.9F;
			k += 0.03F;
		}
		
		matrices.popPose();
	}
	
	
}
