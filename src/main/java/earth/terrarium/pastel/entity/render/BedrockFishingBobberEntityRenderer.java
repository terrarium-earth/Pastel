package earth.terrarium.pastel.entity.render;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.PastelFishingBobberEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BedrockFishingBobberEntityRenderer extends PastelFishingBobberEntityRenderer {
	
	protected static final ResourceLocation TEXTURE = PastelCommon.locate("textures/entity/fishing_hooks/bedrock_fishing_hook.png");
	protected static final RenderType LAYER = RenderType.entityCutout(TEXTURE);
	
	public BedrockFishingBobberEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	public ResourceLocation getTextureLocation(PastelFishingBobberEntity fishingBobberEntity) {
		return TEXTURE;
	}
	
	@Override
	public RenderType getLayer(PastelFishingBobberEntity bobber) {
		return LAYER;
	}
	
}
