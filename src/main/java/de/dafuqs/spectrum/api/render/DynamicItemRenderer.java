package de.dafuqs.spectrum.api.render;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

// Similar to FAPIs DynamicItemRenderer, except with a little more information.
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface DynamicItemRenderer {
    
    Object2ObjectArrayMap<Item, DynamicItemRenderer> RENDERERS = new Object2ObjectArrayMap<>();
    
    /**
     * Renders an item stack.
     *
     * @param renderer        the currently used ItemRenderer instance
     * @param stack           the rendered item stack
     * @param mode            the model transformation mode
     * @param leftHanded      the handedness boolean in the original render methods arguments
     * @param matrices        the matrix stack
     * @param vertexConsumers the vertex consumer provider
     * @param light           packed lightmap coordinates
     * @param overlay         the overlay UV passed to {@link com.mojang.blaze3d.vertex.VertexConsumer#setOverlay(int)}
     * @param model           the original model [use this to render the underlying item model]
     */
    void render(ItemRenderer renderer, ItemStack stack, ItemDisplayContext mode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, BakedModel model);
    
}
