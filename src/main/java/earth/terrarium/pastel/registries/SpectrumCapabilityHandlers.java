package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.capabilities.*;
import net.minecraft.world.level.block.entity.*;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.fluids.capability.templates.*;

import java.util.function.Supplier;

import static earth.terrarium.pastel.registries.SpectrumBlockEntities.*;

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
        standardBlockBE(BOTTOMLESS_BUNDLE, event);

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

    public static void registerItems(RegisterCapabilitiesEvent event) {
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, v) -> new FluidHandlerItemStackSimple.Consumable(() -> SpectrumDataComponentTypes.MERMAIDS_GEM, stack, 1000),
                SpectrumItems.MERMAIDS_GEM.get());

        event.registerItem(PastelCapabilities.Miscellaneous.MINING, (stack, v) -> (AreaMiningHandler) stack.getItem(), SpectrumItems.MALACHITE_WORKSTAFF, SpectrumItems.GLASS_CREST_WORKSTAFF);
    }

    private static void standardBlockBE(Supplier<? extends BlockEntityType<? extends SidedCapabilityProvider>> type, RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                type.get(),
                SidedCapabilityProvider::exposeItemHandlers
        );
    }

    private static void standardFluidBE(Supplier<? extends BlockEntityType<? extends SidedCapabilityProvider>> type, RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                type.get(),
                SidedCapabilityProvider::exposeFluidHandlers
        );
    }
}
