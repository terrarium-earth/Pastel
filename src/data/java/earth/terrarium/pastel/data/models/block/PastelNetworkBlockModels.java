package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;

public class PastelNetworkBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.CONNECTION_NODE);
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.PROVIDER_NODE);
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.STORAGE_NODE);
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.BUFFER_NODE);
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.SENDER_NODE);
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.GATHER_NODE);

        PastelModelHelper.BLOCK
            .defaultUpFacing(
                generators,
                PastelBlocks.CONNECTION_NODE,
                PastelTexturedModels.parented(PastelModels.PASTEL_GENERIC_NODE)
            );
        PastelModelHelper.BLOCK
            .defaultUpFacing(
                generators,
                PastelBlocks.PROVIDER_NODE,
                PastelTexturedModels.parented(PastelModels.PASTEL_PUSH_NODE)
            );
        PastelModelHelper.BLOCK
            .defaultUpFacing(
                generators,
                PastelBlocks.STORAGE_NODE,
                PastelTexturedModels.parented(PastelModels.PASTEL_STORE_NODE)
            );
        PastelModelHelper.BLOCK
            .defaultUpFacing(
                generators,
                PastelBlocks.BUFFER_NODE,
                PastelTexturedModels.parented(PastelModels.PASTEL_STORE_NODE)
            );
        PastelModelHelper.BLOCK
            .defaultUpFacing(
                generators,
                PastelBlocks.SENDER_NODE,
                PastelTexturedModels.parented(PastelModels.PASTEL_PUSH_NODE)
            );
        PastelModelHelper.BLOCK
            .defaultUpFacing(
                generators,
                PastelBlocks.GATHER_NODE,
                PastelTexturedModels.parented(PastelModels.PASTEL_PULL_NODE)
            );
    }
}
