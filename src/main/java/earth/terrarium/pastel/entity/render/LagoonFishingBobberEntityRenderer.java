package earth.terrarium.pastel.entity.render;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.PastelFishingBobberEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LagoonFishingBobberEntityRenderer extends PastelFishingBobberEntityRenderer {
	
	protected final ResourceLocation TEXTURE = PastelCommon.locate("textures/entity/fishing_hooks/lagoon_fishing_hook.png");
	protected final ResourceLocation TEXTURE_OPEN_WATERS = PastelCommon.locate("textures/entity/fishing_hooks/lagoon_fishing_hook_open_waters.png");
	protected final RenderType LAYER = RenderType.entityCutout(TEXTURE);
	protected final RenderType LAYER_OPEN_WATERS = RenderType.entityCutout(TEXTURE_OPEN_WATERS);
	
	public LagoonFishingBobberEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	public ResourceLocation getTextureLocation(PastelFishingBobberEntity fishingBobberEntity) {
		if (fishingBobberEntity.isInOpenWater()) {
			return TEXTURE_OPEN_WATERS;
		} else {
			return TEXTURE;
		}
	}
	
	@Override
	public RenderType getLayer(PastelFishingBobberEntity bobber) {
		if (bobber.isOpenOrWaterAround(bobber.blockPosition())) {
			return LAYER_OPEN_WATERS;
		} else {
			return LAYER;
		}
	}
	
}
