package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.entity.entity.KindlingEntity;
import earth.terrarium.pastel.entity.models.KindlingEntityModel;
import earth.terrarium.pastel.registries.client.SpectrumModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;

@OnlyIn(Dist.CLIENT)
public class KindlingEntityArmorFeatureRenderer extends RenderLayer<KindlingEntity, KindlingEntityModel> {
	
	public static final ResourceLocation TEXTURE_DIAMOND = SpectrumCommon.locate("textures/entity/kindling/armor/diamond.png");
	public static final ResourceLocation TEXTURE_GOLD = SpectrumCommon.locate("textures/entity/kindling/armor/gold.png");
	public static final ResourceLocation TEXTURE_IRON = SpectrumCommon.locate("textures/entity/kindling/armor/iron.png");
	public static final ResourceLocation TEXTURE_LEATHER = SpectrumCommon.locate("textures/entity/kindling/armor/leather.png");
	
	private final KindlingEntityModel model;
	
	public KindlingEntityArmorFeatureRenderer(RenderLayerParent<KindlingEntity, KindlingEntityModel> context, EntityModelSet loader) {
		super(context);
		this.model = new KindlingEntityModel(loader.bakeLayer(SpectrumModelLayers.KINDLING_ARMOR));
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int i, KindlingEntity kindlingEntity, float f, float g, float h, float j, float k, float l) {
		ItemStack itemStack = kindlingEntity.getBodyArmorItem();
		
		if (itemStack.getItem() instanceof AnimalArmorItem armorItem) {
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(kindlingEntity, f, g, h);
			this.model.setupAnim(kindlingEntity, f, g, j, k, l);
			var color = itemStack.is(ItemTags.DYEABLE) ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(itemStack, DyedItemColor.LEATHER_COLOR)) : -1;
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCull(getTextureForArmor(armorItem)));
			this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, color);
		}
	}
	
	public static ResourceLocation getTextureForArmor(AnimalArmorItem item) {
		if (item == Items.DIAMOND_HORSE_ARMOR) {
			return TEXTURE_DIAMOND;
		}
		if (item == Items.GOLDEN_HORSE_ARMOR) {
			return TEXTURE_GOLD;
		}
		if (item == Items.IRON_HORSE_ARMOR) {
			return TEXTURE_IRON;
		}
		return TEXTURE_LEATHER;
	}
	
}
