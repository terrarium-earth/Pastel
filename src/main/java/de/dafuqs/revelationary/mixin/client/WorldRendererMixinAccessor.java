package de.dafuqs.revelationary.mixin.client;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface WorldRendererMixinAccessor {
	@Invoker
	void invokeSetSectionDirty(int x, int y, int z, boolean important);
}