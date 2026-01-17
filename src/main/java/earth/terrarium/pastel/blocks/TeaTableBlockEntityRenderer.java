package earth.terrarium.pastel.blocks;

import com.cmdpro.databank.model.DatabankModel;
import com.cmdpro.databank.model.DatabankModels;
import com.cmdpro.databank.model.blockentity.DatabankBlockEntityModel;
import com.cmdpro.databank.model.blockentity.DatabankBlockEntityRenderer;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TeaTableBlockEntityRenderer extends DatabankBlockEntityRenderer<TeaTableBlockEntity> {

    public TeaTableBlockEntityRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(new TeaTableModel());
    }

    public static class TeaTableModel extends DatabankBlockEntityModel<TeaTableBlockEntity> {
        public DatabankModel model;

        @Override
        public ResourceLocation getTextureLocation() {
            return PastelCommon.locate("textures/block/tea_table.png"); // to be replaced with something based on current cloth color
        }

        @Override
        public void setupModelPose(TeaTableBlockEntity teaTableBlockEntity, float partialTick) {
            teaTableBlockEntity.animState.updateAnimDefinitions(getModel());
            animate(teaTableBlockEntity.animState);
        }

        @Override
        public DatabankModel getModel() {
            if (model == null)
                model = DatabankModels.models.get(PastelCommon.locate("tea_table"));
            return model;
        }
    }
}
