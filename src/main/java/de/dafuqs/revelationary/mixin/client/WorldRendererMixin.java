package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.api.revelations.WorldRendererAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@OnlyIn(Dist.CLIENT)
@Mixin(value = LevelRenderer.class, priority = 900)
public abstract class WorldRendererMixin implements WorldRendererAccessor {
	@Shadow
	private ViewArea viewArea;
	
	@Shadow
	public abstract void needsUpdate();
	
	/**
	 * When triggered on client side lets the client redraw ALL chunks
	 * Warning: Costly + LagSpike!
	 */
	public void revelationary$rebuildAllChunks() {
		if (ModList.get().isLoaded("sodium")) {
			rebuildAllChunksSodium();
			return;
		}
		
		if (Minecraft.getInstance().level != null) {
			if (Minecraft.getInstance().levelRenderer != null && Minecraft.getInstance().player != null) {
				for (SectionRenderDispatcher.RenderSection chunk : this.viewArea.sections) {
					chunk.setDirty(true);
				}
				needsUpdate();
			}
		}
	}
	
	@Unique
	private static void rebuildAllChunksSodium() {
		Level world = Minecraft.getInstance().level;
		if (world == null) {
			return;
		}
		
		LevelRenderer worldRenderer = Minecraft.getInstance().levelRenderer;
		if (worldRenderer == null) {
			return;
		}
		
		WorldRendererMixinAccessor wra = (de.dafuqs.revelationary.mixin.client.WorldRendererMixinAccessor) worldRenderer;
		LocalPlayer clientPlayerEntity = Minecraft.getInstance().player;
		ChunkPos chunkPos = clientPlayerEntity.chunkPosition();
		int viewDistance = Minecraft.getInstance().options.renderDistance().get();
		
		int startY = world.getMinSection();
		int endY = world.getMaxSection();
		
		for (int x = -viewDistance; x < viewDistance; x++) {
			for (int z = -viewDistance; z < viewDistance; z++) {
				LevelChunk chunk = Minecraft.getInstance().level.getChunkSource().getChunk(chunkPos.x + x, chunkPos.z + z, false);
				if (chunk != null) {
					for (int y = startY; y <= endY; y++) {
						wra.invokeSetSectionDirty(chunk.getPos().x, y, chunk.getPos().z, false);
					}
				}
			}
		}
	}
}