package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.capabilities.*;
import net.minecraft.world.level.block.entity.*;
import net.neoforged.neoforge.capabilities.*;

import static de.dafuqs.spectrum.registries.SpectrumBlockEntities.*;

public class SpectrumCapabilityHandlers {

    public static void registerBlocks(RegisterCapabilitiesEvent event) {
        // ITEMS
        // - structures
        standardBlockBE(FUSION_SHRINE, event);
        standardBlockBE(PEDESTAL, event);
        standardBlockBE(SPIRIT_INSTILLER, event);
        standardBlockBE(ENCHANTER, event);

        // - blocks
        standardBlockBE(CINDERHEARTH, event);
        standardBlockBE(CRYSTALLARIEUM, event);
        standardBlockBE(TITRATION_BARREL, event);
        standardBlockBE(POTION_WORKSHOP, event);
        standardBlockBE(BLOCK_PLACER, event);

        // - chests
        standardBlockBE(BLACK_HOLE_CHEST, event);
        standardBlockBE(FABRICATION_CHEST, event);
        standardBlockBE(COMPACTING_CHEST, event);
        standardBlockBE(HEARTBOUND_CHEST, event);
        standardBlockBE(AMPHORA, event);

        // FLUIDS
        // - I am not categorizing this lmao
        standardFluidBE(FUSION_SHRINE, event);
        standardFluidBE(CRYSTALLARIEUM, event);
        standardFluidBE(TITRATION_BARREL, event);
    }

    private static void standardBlockBE(BlockEntityType<? extends SidedCapabilityProvider> type, RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                type,
                SidedCapabilityProvider::exposeItemHandlers
        );
    }

    private static void standardFluidBE(BlockEntityType<? extends SidedCapabilityProvider> type, RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                type,
                SidedCapabilityProvider::exposeFluidHandlers
        );
    }
}
