package earth.terrarium.pastel.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class InGameOverlayRendererMixin {
    
    @Inject(method = "renderScreenEffect", at = @At(value = "HEAD"))
    private static void spectrum$renderPrimordialFire(Minecraft client, PoseStack matrices, CallbackInfo ci) {
        if (!client.player.isSpectator()) {
            if (PrimordialFireData.isOnPrimordialFire(client.player)) {
                renderPrimordialFireOverlay(client, matrices);
            }
        }
    }
    
    @Inject(method = "renderFire", at = @At(value = "HEAD"), cancellable = true)
    private static void spectrum$cancelFireOverlayWithPrimordialFire(Minecraft client, PoseStack matrices, CallbackInfo ci) {
        if (PrimordialFireData.isOnPrimordialFire(client.player)) {
            ci.cancel();
        }
    }

    // [VanillaCopy] uses different texture for fire overlay
    @Unique
    private static void renderPrimordialFireOverlay(Minecraft client, PoseStack matrices) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        TextureAtlasSprite sprite = client.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(PastelCommon.locate("block/primordial_fire_1"));
        RenderSystem.setShaderTexture(0, sprite.atlasLocation());
        float f = sprite.getU0();
        float g = sprite.getU1();
        float h = (f + g) / 2.0F;
        float i = sprite.getV0();
        float j = sprite.getV1();
        float k = (i + j) / 2.0F;
        float l = sprite.uvShrinkRatio();
        float m = Mth.lerp(l, f, h);
        float n = Mth.lerp(l, g, h);
        float o = Mth.lerp(l, i, k);
        float p = Mth.lerp(l, j, k);
        
        for (int r = 0; r < 2; ++r) {
            matrices.pushPose();
            matrices.translate((float) (-(r * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            matrices.mulPose(Axis.YP.rotationDegrees((float) (r * 2 - 1) * 10.0F));
            Matrix4f matrix4f = matrices.last().pose();
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.addVertex(matrix4f, -0.5F, -0.5F, -0.5F).setUv(n, p).setColor(1.0F, 1.0F, 1.0F, 0.9F);
            bufferBuilder.addVertex(matrix4f, 0.5F, -0.5F, -0.5F).setUv(m, p).setColor(1.0F, 1.0F, 1.0F, 0.9F);
            bufferBuilder.addVertex(matrix4f, 0.5F, 0.5F, -0.5F).setUv(m, o).setColor(1.0F, 1.0F, 1.0F, 0.9F);
            bufferBuilder.addVertex(matrix4f, -0.5F, 0.5F, -0.5F).setUv(n, o).setColor(1.0F, 1.0F, 1.0F, 0.9F);
            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            matrices.popPose();
        }
        
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
		
    }
    
}
