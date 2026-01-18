package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.data.models.PastelBlockModels;
import earth.terrarium.pastel.data.models.PastelItemModels;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class PastelModelProvider extends AbstractModelProvider{

    public PastelModelProvider(PackOutput output) {
        super(output, PastelCommon.MOD_ID);
    }

    @Override
    protected void generateBlockModels(BlockModelGenerators generators) {
        PastelBlockModels.generateBlockModels(generators);
    }

    @Override
    protected void generateItemModels(ItemModelGenerators generators) {
        PastelBlockModels.generateItemModels(generators);
        PastelItemModels.generateItemModels(generators);
    }
}
