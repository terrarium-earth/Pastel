package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.*;
import earth.terrarium.pastel.api.block.FilterConfigurable;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.extensions.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.registries.*;

public class PastelScreenHandlerTypes {

    private static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(
        Registries.MENU, PastelCommon.MOD_ID);

    public static MenuType<PaintbrushScreenHandler> PAINTBRUSH;
    public static MenuType<WorkstaffScreenHandler> WORKSTAFF;

    public static MenuType<PedestalScreenHandler> PEDESTAL;
    public static MenuType<CraftingTabletScreenHandler> CRAFTING_TABLET;
    public static MenuType<FabricationChestScreenHandler> FABRICATION_CHEST;
    public static MenuType<BedrockAnvilScreenHandler> BEDROCK_ANVIL;
    public static MenuType<ParticleSpawnerScreenHandler> PARTICLE_SPAWNER;
    public static MenuType<CompactingChestScreenHandler> COMPACTING_CHEST;
    public static MenuType<BlackHoleChestScreenHandler> BLACK_HOLE_CHEST;
    public static MenuType<PotionWorkshopScreenHandler> POTION_WORKSHOP;
    public static MenuType<ColorPickerScreenHandler> COLOR_PICKER;
    public static MenuType<CinderhearthScreenHandler> CINDERHEARTH;
    public static MenuType<FilteringScreenHandler> FILTERING;
    public static MenuType<BagOfHoldingScreenHandler> BAG_OF_HOLDING;

    public static MenuType<GenericPastelContainerScreenHandler> GENERIC_TIER1_9X3;
    public static MenuType<GenericPastelContainerScreenHandler> GENERIC_TIER2_9X3;
    public static MenuType<GenericPastelContainerScreenHandler> GENERIC_TIER3_9X3;

    public static MenuType<GenericPastelContainerScreenHandler> GENERIC_TIER1_9X6;
    public static MenuType<GenericPastelContainerScreenHandler> GENERIC_TIER2_9X6;
    public static MenuType<GenericPastelContainerScreenHandler> GENERIC_TIER3_9X6;

    public static MenuType<Pastel3x3ContainerScreenHandler> GENERIC_TIER1_3X3;
    public static MenuType<Pastel3x3ContainerScreenHandler> GENERIC_TIER2_3X3;
    public static MenuType<Pastel3x3ContainerScreenHandler> GENERIC_TIER3_3X3;

    public static <T extends AbstractContainerMenu> MenuType<T> registerSimple(
        ResourceLocation id, MenuType.MenuSupplier<T> factory) {
        MenuType<T> type = new MenuType<>(factory, FeatureFlags.VANILLA_SET);
        REGISTER.register(id.getPath(), () -> type);
        return type;
    }

    public static <T extends AbstractContainerMenu, D> MenuType<T> registerExtended(
        ResourceLocation id, IContainerFactory<T> factory,
        StreamCodec<? super RegistryFriendlyByteBuf, D> packetCodec
    ) {
        var type = IMenuTypeExtension.create(factory);
        REGISTER.register(id.getPath(), () -> type);
        return type;
    }

    public static void registerMenus(IEventBus bus) {
        PAINTBRUSH = registerSimple(PastelScreenHandlerIDs.PAINTBRUSH, PaintbrushScreenHandler::new);
        WORKSTAFF = registerSimple(PastelScreenHandlerIDs.WORKSTAFF, WorkstaffScreenHandler::new);

        PEDESTAL = registerExtended(
            PastelScreenHandlerIDs.PEDESTAL, PedestalScreenHandler::new,
            PedestalScreenHandler.ScreenOpeningData.STREAM_CODEC
        );
        PARTICLE_SPAWNER = registerExtended(
            PastelScreenHandlerIDs.PARTICLE_SPAWNER, ParticleSpawnerScreenHandler::new, BlockPos.STREAM_CODEC);
        COMPACTING_CHEST = registerExtended(
            PastelScreenHandlerIDs.COMPACTING_CHEST, CompactingChestScreenHandler::new, BlockPos.STREAM_CODEC);
        BLACK_HOLE_CHEST = registerExtended(
            PastelScreenHandlerIDs.BLACK_HOLE_CHEST, BlackHoleChestScreenHandler::new,
            FilterConfigurable.ExtendedDataWithPos.STREAM_CODEC
        );
        COLOR_PICKER = registerExtended(
            PastelScreenHandlerIDs.COLOR_PICKER, ColorPickerScreenHandler::new,
            ColorPickerScreenHandler.ScreenOpeningData.STREAM_CODEC
        );
        CINDERHEARTH = registerExtended(
            PastelScreenHandlerIDs.CINDERHEARTH, CinderhearthScreenHandler::new, BlockPos.STREAM_CODEC);
        FILTERING = registerExtended(
            PastelScreenHandlerIDs.FILTERING, FilteringScreenHandler::new,
            FilterConfigurable.ExtendedData.STREAM_CODEC
        );
        BAG_OF_HOLDING = registerSimple(PastelScreenHandlerIDs.BAG_OF_HOLDING, BagOfHoldingScreenHandler::new);

        CRAFTING_TABLET = registerSimple(PastelScreenHandlerIDs.CRAFTING_TABLET, CraftingTabletScreenHandler::new);
        FABRICATION_CHEST = registerSimple(
            PastelScreenHandlerIDs.FABRICATION_CHEST, FabricationChestScreenHandler::new);
        BEDROCK_ANVIL = registerSimple(PastelScreenHandlerIDs.BEDROCK_ANVIL, BedrockAnvilScreenHandler::new);
        POTION_WORKSHOP = registerSimple(PastelScreenHandlerIDs.POTION_WORKSHOP, PotionWorkshopScreenHandler::new);

        GENERIC_TIER1_9X3 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER1_9x3, GenericPastelContainerScreenHandler::createGeneric9x3_Tier1);
        GENERIC_TIER2_9X3 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER2_9x3, GenericPastelContainerScreenHandler::createGeneric9x3_Tier2);
        GENERIC_TIER3_9X3 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER3_9x3, GenericPastelContainerScreenHandler::createGeneric9x3_Tier3);

        GENERIC_TIER1_9X6 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER1_9x6, GenericPastelContainerScreenHandler::createGeneric9x6_Tier1);
        GENERIC_TIER2_9X6 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER2_9x6, GenericPastelContainerScreenHandler::createGeneric9x6_Tier2);
        GENERIC_TIER3_9X6 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER3_9x6, GenericPastelContainerScreenHandler::createGeneric9x6_Tier3);

        GENERIC_TIER1_3X3 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER1_3X3, Pastel3x3ContainerScreenHandler::createTier1);
        GENERIC_TIER2_3X3 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER2_3X3, Pastel3x3ContainerScreenHandler::createTier2);
        GENERIC_TIER3_3X3 = registerSimple(
            PastelScreenHandlerIDs.GENERIC_TIER3_3X3, Pastel3x3ContainerScreenHandler::createTier3);
        REGISTER.register(bus);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(PastelScreenHandlerTypes.PAINTBRUSH, PaintbrushScreen::new);
        event.register(PastelScreenHandlerTypes.WORKSTAFF, WorkstaffScreen::new);

        event.register(PastelScreenHandlerTypes.PEDESTAL, PedestalScreen::new);
        event.register(PastelScreenHandlerTypes.CRAFTING_TABLET, CraftingTabletScreen::new);
        event.register(PastelScreenHandlerTypes.FABRICATION_CHEST, FabricationChestScreen::new);
        event.register(PastelScreenHandlerTypes.BEDROCK_ANVIL, BedrockAnvilScreen::new);
        event.register(PastelScreenHandlerTypes.PARTICLE_SPAWNER, ParticleSpawnerScreen::new);
        event.register(PastelScreenHandlerTypes.COMPACTING_CHEST, CompactingChestScreen::new);
        event.register(PastelScreenHandlerTypes.BLACK_HOLE_CHEST, BlackHoleChestScreen::new);
        event.register(PastelScreenHandlerTypes.POTION_WORKSHOP, PotionWorkshopScreen::new);
        event.register(PastelScreenHandlerTypes.COLOR_PICKER, ColorPickerScreen::new);
        event.register(PastelScreenHandlerTypes.CINDERHEARTH, CinderhearthScreen::new);
        event.register(PastelScreenHandlerTypes.FILTERING, FilteringScreen::new);
        event.register(PastelScreenHandlerTypes.BAG_OF_HOLDING, ContainerScreen::new);

        event.register(PastelScreenHandlerTypes.GENERIC_TIER1_9X3, PastelGenericContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER2_9X3, PastelGenericContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER3_9X3, PastelGenericContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER1_9X6, PastelGenericContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER2_9X6, PastelGenericContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER3_9X6, PastelGenericContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER1_3X3, Pastel3x3ContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER2_3X3, Pastel3x3ContainerScreen::new);
        event.register(PastelScreenHandlerTypes.GENERIC_TIER3_3X3, Pastel3x3ContainerScreen::new);
    }

}
