package de.dafuqs.spectrum.mixin.client.accessors;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.class)
public interface RenderLayerAccessor {
	
	@Invoker
	static RenderType.CompositeRenderType invokeCreate(String name, VertexFormat vertexFormat, VertexFormat.Mode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderType.CompositeState phases) {
		throw new IllegalStateException();
	}
	
}