package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.deeper_down.ColorGrading;
import earth.terrarium.pastel.registries.PastelDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.Optional;

public class PastelShaders {
	
	public static final ResourceLocation COLOR_GRADING_ID = PastelCommon.locate("shaders/post/dd_color_grading.json");
	public static Optional<PostChain> colorGradingPostProcess = Optional.empty();
	
	private static final String[] COLOR_GRADING_UNIFORMS = new String[] {"Saturation", "Rubedo", "ColorTemperature", "DesaturateThreshold", "BloomThreshold"};
	
	public static Optional<PostChain> loadPostProcess(Minecraft client, ResourceLocation id) {
		PostChain post = null;
		try {
			post = new PostChain(client.getTextureManager(), client.getResourceManager(), client.getMainRenderTarget(), id);
		} catch (IOException e) {
			PastelCommon.LOGGER.error("Failed to load post-process shader [{}]", COLOR_GRADING_ID);
			PastelCommon.LOGGER.error("", e);
		}
		
		if (post != null)
			post.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
		
		return Optional.ofNullable(post);
	}
	
	public static void updateDimensionShaders(ClientLevel world) {
		if (!world.dimension().equals(PastelDimensions.DIMENSION_KEY))
			return;
		
		colorGradingPostProcess.ifPresent(pps -> {
			for (int i = 0; i < 5; i++) {
				pps.setUniform(COLOR_GRADING_UNIFORMS[i], ColorGrading.GRADING_OUT[i]);
			}
		});
	}
	
	public static void clearDimensionShaders() {
		if (colorGradingPostProcess.isPresent()) {
			colorGradingPostProcess.get().close();
			colorGradingPostProcess = Optional.empty();
		}
	}
	
	public static void resizeShaders(int width, int height) {
		colorGradingPostProcess.ifPresent(pps -> pps.resize(width, height));
	}
}
