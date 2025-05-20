package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.api.block.FilterConfigurable;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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

public class SpectrumScreenHandlerTypes {
	
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
	
	public static MenuType<GenericSpectrumContainerScreenHandler> GENERIC_TIER1_9X3;
	public static MenuType<GenericSpectrumContainerScreenHandler> GENERIC_TIER2_9X3;
	public static MenuType<GenericSpectrumContainerScreenHandler> GENERIC_TIER3_9X3;
	
	public static MenuType<GenericSpectrumContainerScreenHandler> GENERIC_TIER1_9X6;
	public static MenuType<GenericSpectrumContainerScreenHandler> GENERIC_TIER2_9X6;
	public static MenuType<GenericSpectrumContainerScreenHandler> GENERIC_TIER3_9X6;
	
	public static MenuType<Spectrum3x3ContainerScreenHandler> GENERIC_TIER1_3X3;
	public static MenuType<Spectrum3x3ContainerScreenHandler> GENERIC_TIER2_3X3;
	public static MenuType<Spectrum3x3ContainerScreenHandler> GENERIC_TIER3_3X3;
	
	public static <T extends AbstractContainerMenu> MenuType<T> registerSimple(ResourceLocation id, MenuType.MenuSupplier<T> factory) {
		MenuType<T> type = new MenuType<>(factory, FeatureFlags.VANILLA_SET);
		return Registry.register(BuiltInRegistries.MENU, id, type);
	}
	
	public static <T extends AbstractContainerMenu, D> MenuType<T> registerExtended(ResourceLocation id, IContainerFactory<T> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> packetCodec) {
		return Registry.register(BuiltInRegistries.MENU, id, IMenuTypeExtension.create(factory));
	}
	
	public static void registerMenus() {
		PAINTBRUSH = registerSimple(SpectrumScreenHandlerIDs.PAINTBRUSH, PaintbrushScreenHandler::new);
		WORKSTAFF = registerSimple(SpectrumScreenHandlerIDs.WORKSTAFF, WorkstaffScreenHandler::new);
		
		PEDESTAL = registerExtended(SpectrumScreenHandlerIDs.PEDESTAL, PedestalScreenHandler::new, PedestalScreenHandler.ScreenOpeningData.STREAM_CODEC);
		PARTICLE_SPAWNER = registerExtended(SpectrumScreenHandlerIDs.PARTICLE_SPAWNER, ParticleSpawnerScreenHandler::new, BlockPos.STREAM_CODEC);
		COMPACTING_CHEST = registerExtended(SpectrumScreenHandlerIDs.COMPACTING_CHEST, CompactingChestScreenHandler::new, BlockPos.STREAM_CODEC);
		BLACK_HOLE_CHEST = registerExtended(SpectrumScreenHandlerIDs.BLACK_HOLE_CHEST, BlackHoleChestScreenHandler::new, FilterConfigurable.ExtendedDataWithPos.STREAM_CODEC);
		COLOR_PICKER = registerExtended(SpectrumScreenHandlerIDs.COLOR_PICKER, ColorPickerScreenHandler::new, ColorPickerScreenHandler.ScreenOpeningData.STREAM_CODEC);
		CINDERHEARTH = registerExtended(SpectrumScreenHandlerIDs.CINDERHEARTH, CinderhearthScreenHandler::new, BlockPos.STREAM_CODEC);
		FILTERING = registerExtended(SpectrumScreenHandlerIDs.FILTERING, FilteringScreenHandler::new, FilterConfigurable.ExtendedData.STREAM_CODEC);
		BAG_OF_HOLDING = registerSimple(SpectrumScreenHandlerIDs.BAG_OF_HOLDING, BagOfHoldingScreenHandler::new);
		
		CRAFTING_TABLET = registerSimple(SpectrumScreenHandlerIDs.CRAFTING_TABLET, CraftingTabletScreenHandler::new);
		FABRICATION_CHEST = registerSimple(SpectrumScreenHandlerIDs.FABRICATION_CHEST, FabricationChestScreenHandler::new);
		BEDROCK_ANVIL = registerSimple(SpectrumScreenHandlerIDs.BEDROCK_ANVIL, BedrockAnvilScreenHandler::new);
		POTION_WORKSHOP = registerSimple(SpectrumScreenHandlerIDs.POTION_WORKSHOP, PotionWorkshopScreenHandler::new);
		
		GENERIC_TIER1_9X3 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER1_9x3, GenericSpectrumContainerScreenHandler::createGeneric9x3_Tier1);
		GENERIC_TIER2_9X3 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER2_9x3, GenericSpectrumContainerScreenHandler::createGeneric9x3_Tier2);
		GENERIC_TIER3_9X3 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER3_9x3, GenericSpectrumContainerScreenHandler::createGeneric9x3_Tier3);
		
		GENERIC_TIER1_9X6 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER1_9x6, GenericSpectrumContainerScreenHandler::createGeneric9x6_Tier1);
		GENERIC_TIER2_9X6 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER2_9x6, GenericSpectrumContainerScreenHandler::createGeneric9x6_Tier2);
		GENERIC_TIER3_9X6 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER3_9x6, GenericSpectrumContainerScreenHandler::createGeneric9x6_Tier3);
		
		GENERIC_TIER1_3X3 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER1_3X3, Spectrum3x3ContainerScreenHandler::createTier1);
		GENERIC_TIER2_3X3 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER2_3X3, Spectrum3x3ContainerScreenHandler::createTier2);
		GENERIC_TIER3_3X3 = registerSimple(SpectrumScreenHandlerIDs.GENERIC_TIER3_3X3, Spectrum3x3ContainerScreenHandler::createTier3);
	}

	@SubscribeEvent
	public static void registerScreens(RegisterMenuScreensEvent event) {
		event.register(SpectrumScreenHandlerTypes.PAINTBRUSH, PaintbrushScreen::new);
		event.register(SpectrumScreenHandlerTypes.WORKSTAFF, WorkstaffScreen::new);
		
		event.register(SpectrumScreenHandlerTypes.PEDESTAL, PedestalScreen::new);
		event.register(SpectrumScreenHandlerTypes.CRAFTING_TABLET, CraftingTabletScreen::new);
		event.register(SpectrumScreenHandlerTypes.FABRICATION_CHEST, FabricationChestScreen::new);
		event.register(SpectrumScreenHandlerTypes.BEDROCK_ANVIL, BedrockAnvilScreen::new);
		event.register(SpectrumScreenHandlerTypes.PARTICLE_SPAWNER, ParticleSpawnerScreen::new);
		event.register(SpectrumScreenHandlerTypes.COMPACTING_CHEST, CompactingChestScreen::new);
		event.register(SpectrumScreenHandlerTypes.BLACK_HOLE_CHEST, BlackHoleChestScreen::new);
		event.register(SpectrumScreenHandlerTypes.POTION_WORKSHOP, PotionWorkshopScreen::new);
		event.register(SpectrumScreenHandlerTypes.COLOR_PICKER, ColorPickerScreen::new);
		event.register(SpectrumScreenHandlerTypes.CINDERHEARTH, CinderhearthScreen::new);
		event.register(SpectrumScreenHandlerTypes.FILTERING, FilteringScreen::new);
		event.register(SpectrumScreenHandlerTypes.BAG_OF_HOLDING, ContainerScreen::new);
		
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER1_9X3, SpectrumGenericContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER2_9X3, SpectrumGenericContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER3_9X3, SpectrumGenericContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER1_9X6, SpectrumGenericContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER2_9X6, SpectrumGenericContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER3_9X6, SpectrumGenericContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER1_3X3, Spectrum3x3ContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER2_3X3, Spectrum3x3ContainerScreen::new);
		event.register(SpectrumScreenHandlerTypes.GENERIC_TIER3_3X3, Spectrum3x3ContainerScreen::new);
	}
	
}
