package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.item.ItemPickupListener;
import earth.terrarium.pastel.api.item.SplitDamageHandler;
import earth.terrarium.pastel.capabilities.AreaMiningHandler;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.capabilities.SidedCapabilityProvider;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.function.Supplier;

import static earth.terrarium.pastel.registries.PastelBlockEntities.AMPHORA;
import static earth.terrarium.pastel.registries.PastelBlockEntities.BLACK_HOLE_CHEST;
import static earth.terrarium.pastel.registries.PastelBlockEntities.BLOCK_PLACER;
import static earth.terrarium.pastel.registries.PastelBlockEntities.BOTTOMLESS_BUNDLE;
import static earth.terrarium.pastel.registries.PastelBlockEntities.CINDERHEARTH;
import static earth.terrarium.pastel.registries.PastelBlockEntities.COLOR_PICKER;
import static earth.terrarium.pastel.registries.PastelBlockEntities.COMPACTING_CHEST;
import static earth.terrarium.pastel.registries.PastelBlockEntities.CRYSTALLARIEUM;
import static earth.terrarium.pastel.registries.PastelBlockEntities.CRYSTAL_APOTHECARY;
import static earth.terrarium.pastel.registries.PastelBlockEntities.ENCHANTER;
import static earth.terrarium.pastel.registries.PastelBlockEntities.FABRICATION_CHEST;
import static earth.terrarium.pastel.registries.PastelBlockEntities.FUSION_SHRINE;
import static earth.terrarium.pastel.registries.PastelBlockEntities.HEARTBOUND_CHEST;
import static earth.terrarium.pastel.registries.PastelBlockEntities.ITEM_BOWL;
import static earth.terrarium.pastel.registries.PastelBlockEntities.PEDESTAL;
import static earth.terrarium.pastel.registries.PastelBlockEntities.POTION_WORKSHOP;
import static earth.terrarium.pastel.registries.PastelBlockEntities.SPIRIT_INSTILLER;
import static earth.terrarium.pastel.registries.PastelBlockEntities.TITRATION_BARREL;

public class PastelCapabilityHandlers {

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
        standardBlockBE(ITEM_BOWL, event);
        standardBlockBE(COLOR_PICKER, event);
        containerBlockBE(CRYSTAL_APOTHECARY, event);

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
        event
            .registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, v) -> new FluidHandlerItemStackSimple.Consumable(
                    () -> PastelDataComponentTypes.MERMAIDS_GEM,
                    stack,
                    1000
                ),
                PastelItems.MERMAIDS_GEM.get()
            );

        event
            .registerItem(
                PastelCapabilities.Pickup.ITEM,
                (stack, v) -> (ItemPickupListener) stack.getItem(),
                PastelBlocks.BOTTOMLESS_BUNDLE.asItem()
            );

        event
            .registerItem(
                PastelCapabilities.Misc.MINING,
                (stack, v) -> (AreaMiningHandler) stack.getItem(),
                PastelItems.MALACHITE_WORKSTAFF,
                PastelItems.GLASS_CREST_WORKSTAFF
            );

        event
            .registerItem(
                PastelCapabilities.Misc.SPLIT_DAMAGE,
                (stack, v) -> (SplitDamageHandler) stack.getItem(),
                PastelItems.GLASS_CREST_ULTRA_GREATSWORD,
                PastelItems.MALACHITE_BIDENT,
                PastelItems.FRACTAL_GLASS_CREST_BIDENT,
                PastelItems.FEROCIOUS_GLASS_CREST_BIDENT,
                PastelItems.DRAGON_TALON,
                PastelItems.DREAMFLAYER,
                PastelItems.KNOTTED_SWORD,
                PastelItems.NECTAR_LANCE
            );

        event.registerItem(PastelCapabilities.Misc.XP, KnowledgeGemItem.Wrapper::new, PastelItems.KNOWLEDGE_GEM);
    }

    private static void containerBlockBE(
        Supplier<? extends BlockEntityType<? extends Container>> type,
        RegisterCapabilitiesEvent event
    ) {
        event
            .registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                type.get(),
                (container, dir) -> new InvWrapper(container)
            );
    }

    private static void standardBlockBE(
        Supplier<? extends BlockEntityType<? extends SidedCapabilityProvider>> type,
        RegisterCapabilitiesEvent event
    ) {
        event
            .registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                type.get(),
                SidedCapabilityProvider::exposeItemHandlersChecked
            );
    }

    private static void standardFluidBE(
        Supplier<? extends BlockEntityType<? extends SidedCapabilityProvider>> type,
        RegisterCapabilitiesEvent event
    ) {
        event
            .registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                type.get(),
                SidedCapabilityProvider::exposeFluidHandlersChecked
            );
    }
}
