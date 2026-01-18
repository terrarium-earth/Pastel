package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;

public class PastelNetworkBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators){
        PastelModelHelper.cutout(PastelBlocks.CONNECTION_NODE);
        PastelModelHelper.cutout(PastelBlocks.PROVIDER_NODE);
        PastelModelHelper.cutout(PastelBlocks.STORAGE_NODE);
        PastelModelHelper.cutout(PastelBlocks.BUFFER_NODE);
        PastelModelHelper.cutout(PastelBlocks.SENDER_NODE);
        PastelModelHelper.cutout(PastelBlocks.GATHER_NODE);

        PastelModelHelper.predefinedItemModel(generators,PastelBlocks.CONNECTION_NODE);
        PastelModelHelper.predefinedItemModel(generators,PastelBlocks.PROVIDER_NODE);
        PastelModelHelper.predefinedItemModel(generators,PastelBlocks.STORAGE_NODE);
        PastelModelHelper.predefinedItemModel(generators,PastelBlocks.BUFFER_NODE);
        PastelModelHelper.predefinedItemModel(generators,PastelBlocks.SENDER_NODE);
        PastelModelHelper.predefinedItemModel(generators,PastelBlocks.GATHER_NODE);

        PastelModelHelper.defaultUpFacing(generators, PastelBlocks.CONNECTION_NODE,PastelTexturedModels.parented(PastelModels.PASTEL_GENERIC_NODE));
        PastelModelHelper.defaultUpFacing(generators, PastelBlocks.PROVIDER_NODE,PastelTexturedModels.parented(PastelModels.PASTEL_PUSH_NODE));
        PastelModelHelper.defaultUpFacing(generators, PastelBlocks.STORAGE_NODE,PastelTexturedModels.parented(PastelModels.PASTEL_STORE_NODE));
        PastelModelHelper.defaultUpFacing(generators, PastelBlocks.BUFFER_NODE,PastelTexturedModels.parented(PastelModels.PASTEL_STORE_NODE));
        PastelModelHelper.defaultUpFacing(generators, PastelBlocks.SENDER_NODE,PastelTexturedModels.parented(PastelModels.PASTEL_PUSH_NODE));
        PastelModelHelper.defaultUpFacing(generators, PastelBlocks.GATHER_NODE,PastelTexturedModels.parented(PastelModels.PASTEL_PULL_NODE));
    }
}
