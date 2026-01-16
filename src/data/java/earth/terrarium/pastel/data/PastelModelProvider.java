package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class PastelModelProvider extends AbstractModelProvider{

    public PastelModelProvider(PackOutput output) {
        super(output, PastelCommon.MOD_ID);
    }

    @Override
    protected void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.BLOCK_STATE_MODEL_REGISTRAR.flush(generators);
    }

    @Override
    protected void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.ITEM_MODEL_REGISTRAR.flush(generators);
    }
}
