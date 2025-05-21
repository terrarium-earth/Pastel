package de.dafuqs.spectrum;

import de.dafuqs.spectrum.api.color.ColorRegistry;
import de.dafuqs.spectrum.api.energy.color.InkColorMixes;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.blocks.pastel_network.Pastel;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.compat.reverb.DimensionReverb;
import de.dafuqs.spectrum.config.SpectrumConfig;
import de.dafuqs.spectrum.data_loaders.CrystalApothecarySimulationsDataLoader;
import de.dafuqs.spectrum.data_loaders.EntityFishingDataLoader;
import de.dafuqs.spectrum.data_loaders.NaturesStaffConversionDataLoader;
import de.dafuqs.spectrum.entity.SpectrumEntitySubPredicateTypes;
import de.dafuqs.spectrum.entity.SpectrumEntityTypes;
import de.dafuqs.spectrum.entity.SpectrumTrackedDataHandlerRegistry;
import de.dafuqs.spectrum.events.SpectrumGameEvents;
import de.dafuqs.spectrum.events.SpectrumPositionSources;
import de.dafuqs.spectrum.inventories.SpectrumScreenHandlerTypes;
import de.dafuqs.spectrum.loot.SpectrumLootContextTypes;
import de.dafuqs.spectrum.loot.SpectrumLootFunctionTypes;
import de.dafuqs.spectrum.loot.SpectrumLootPoolModifiers;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.networking.SpectrumS2CPackets;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.registries.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.server.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod(SpectrumCommon.MOD_ID)
public class SpectrumCommon {
	
	public static final String MOD_ID = "spectrum";
	
	public static final Logger LOGGER = LoggerFactory.getLogger("Spectrum");
	public static final Map<ResourceLocation, TagKey<Item>> CACHED_ITEM_TAG_MAP = new HashMap<>();
	public static SpectrumConfig CONFIG;
	
	public static void logInfo(String message) {
		LOGGER.info("[Spectrum] {}", message);
	}
	
	public static void logWarning(String message) {
		LOGGER.warn("[Spectrum] {}", message);
	}
	
	public static void logError(String message) {
		LOGGER.error("[Spectrum] {}", message);
	}
	
	public static ResourceLocation locate(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}
	
	/**
	 * This is the Spectrum analogue of Identifier.of, but instead of defaulting to the namespace 'minecraft', it defaults to 'spectrum'.
	 *
	 * @param id The stringified identifier to parse
	 * @return The parsed identifier
	 */
	public static ResourceLocation ofSpectrumDefaulted(String id) {
		int i = id.indexOf(':');
		String path = id.substring(i + 1);
		String namespace = i > 0 ? id.substring(0, i) : SpectrumCommon.MOD_ID;
		return ResourceLocation.fromNamespaceAndPath(namespace, path);
	}
	
	// Miscellaneous registrars
	public static final DeferredRegistrar FUEL_REGISTRAR = new DeferredRegistrar();
	
	static {
		//Set up config
		logInfo("Loading config file...");
		AutoConfig.register(SpectrumConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(SpectrumConfig.class).getConfig();
		logInfo("Finished loading config file.");
	}

	public SpectrumCommon(IEventBus modEventBus) {
		logInfo("Starting Common Startup");
		
		// Register internals
		SpectrumRegistries.register();
		InkColors.register();
		InkColorMixes.register();
		SpectrumEntityAttributes.register();
		SpectrumLoadConditions.register(modEventBus);
		IngredientStack.register(modEventBus);
		
		logInfo("Registering Component Types...");
		SpectrumDataComponentTypes.register();
		
		logInfo("Registering Block / Item Color Registries...");
		ColorRegistry.registerColorRegistries();
		
		// Register ALL the stuff
		logInfo("Registering Status Effects...");
		SpectrumStatusEffects.register();
		logInfo("Registering Advancement Criteria...");
		SpectrumAdvancementCriteria.register();
		logInfo("Registering Particle Types...");
		SpectrumParticleTypes.register();
		logInfo("Registering Sound Events...");
		SpectrumSoundEvents.register();
		logInfo("Registering BlockSound Groups...");
		SpectrumBlockSoundGroups.register();
		logInfo("Registering Fluids...");
		SpectrumFluids.register();
		logInfo("Registering Armor Materials...");
		SpectrumArmorMaterials.register();
		logInfo("Registering Blocks...");
		SpectrumBlocks.register();
		logInfo("Registering Items...");
		SpectrumPotions.register();
		SpectrumItems.register();
		SpectrumItemGroups.register();
		logInfo("Registering Block Entities...");
		SpectrumBlockEntities.register();
		logInfo("Registering Capabilities...");
		NeoForge.EVENT_BUS.addListener(SpectrumCapabilityHandlers::registerBlocks);
		
		// Pastel
		logInfo("Registering Pastel Upgrades...");
		SpectrumPastelUpgrades.register();
		logInfo("Registering Stamp Categories...");
		SpectrumStampDataCategories.register();
		
		// Worldgen
		logInfo("Registering Features...");
		SpectrumFeatures.register();
		logInfo("Registering Biome Modifications...");
		SpectrumPlacedFeatures.addBiomeModifications();
		logInfo("Registering Structure Types...");
		SpectrumStructureTypes.register();
		
		// Dimension
		logInfo("Registering Dimension...");
		SpectrumDimensions.register();
		
		// Dimension effects
		logInfo("Registering Dimension Sound Effects...");
		DimensionReverb.setup();
		
		// Recipes
		logInfo("Registering Recipe Types...");
		SpectrumRecipeScalings.init();
		SpectrumFusionShrineWorldEffects.register();
		SpectrumRecipeTypes.register();
		SpectrumRecipeSerializers.register();
		
		// Loot
		logInfo("Registering Loot Conditions & Functions...");
		SpectrumLootContextTypes.register();
		SpectrumLootFunctionTypes.register();
		
		logInfo("Setting up server side Mod Compat...");
		SpectrumIntegrationPacks.register();
		
		// GUI
		logInfo("Registering Screen Handler Types...");
		SpectrumScreenHandlerTypes.registerMenus();
		
		logInfo("Registering Default Item Stack Damage Immunities...");
		SpectrumItemDamageImmunities.registerDefaultItemStackImmunities();
		logInfo("Registering Enchantment Drops...");
		SpectrumLootPoolModifiers.setup();
		logInfo("Registering Variant Specific Predicates...");
		SpectrumItemSubPredicateTypes.register();
		SpectrumEntitySubPredicateTypes.register();
		
		logInfo("Registering Blocks and Items to Fuel Registry...");
		FUEL_REGISTRAR.flush();
		
		logInfo("Registering Entities...");
		SpectrumTrackedDataHandlerRegistry.register();
		SpectrumEntityTypes.register();
		
		logInfo("Registering Omni Accelerator Projectiles & Behaviors...");
		SpectrumOmniAcceleratorProjectiles.register();
		SpectrumItemProjectileBehaviors.register();
		
		SpectrumEntityColorProcessors.register();
		SpectrumItemProviders.register();
		
		logInfo("Registering Commands...");
		SpectrumCommands.register();
		
		logInfo("Registering Networking Packets...");
		NeoForge.EVENT_BUS.register(SpectrumC2SPackets.class);
		NeoForge.EVENT_BUS.register(SpectrumS2CPackets.class);
		
		logInfo("Registering Data Loaders...");
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(NaturesStaffConversionDataLoader.INSTANCE);
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(EntityFishingDataLoader.INSTANCE);
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(CrystalApothecarySimulationsDataLoader.INSTANCE);


		ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
			Pastel.clearServerInstance();
		});
		
		logInfo("Adding to Fabric's Registries...");
		SpectrumFlammableBlocks.register();
		SpectrumStrippableBlocks.register();
		SpectrumWaxableBlocks.register();
		SpectrumTillableBlocks.register();
		SpectrumCompostableBlocks.register();
		
		logInfo("Registering Game Events...");
		SpectrumGameEvents.register();
		SpectrumPositionSources.register();
		
		logInfo("Registering Dispenser, Resonance & Present Unwrap Behaviors...");
		SpectrumDispenserBehaviors.register();
		SpectrumPresentUnpackBehaviors.register();
		SpectrumResonanceProcessorTypes.register();
		
		logInfo("Registering Resource Conditions...");
		SpectrumResourceConditions.register();
		logInfo("Registering Structure WeightedPool Element Types...");
		SpectrumStructurePoolElementTypes.register();
		logInfo("Registering Event Listeners...");
		SpectrumEventListeners.register();
		logInfo("Registering Path Node Types...");
		SpectrumPathNodeTypes.register();
		logInfo("Registering Tree Decorator Types...");
		SpectrumTreeDecoratorTypes.register();
		
		//noinspection
		ItemStorage.SIDED.registerForBlockEntity((be, d) -> Storage.empty(), SpectrumBlockEntities.HEARTBOUND_CHEST);
		//noinspection
		ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.storage, SpectrumBlockEntities.BOTTOMLESS_BUNDLE);
		//noinspection
		FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, SpectrumBlockEntities.FUSION_SHRINE);
		//noinspection
		FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.getFluidStorage(), SpectrumBlockEntities.TITRATION_BARREL);
		
		// Builtin Resource Packs
		logInfo("Registering Builtin Resource Packs...");

		if (modContainer.isPresent()) {
			ResourceManagerHelper.registerBuiltinResourcePack(locate("spectrum_style_amethyst"), modContainer.get(), Component.nullToEmpty("Spectrum Style Amethyst"), ResourcePackActivationType.NORMAL);
			ResourceManagerHelper.registerBuiltinResourcePack(locate("spectrum_generation_1"), modContainer.get(), Component.nullToEmpty("Generation 1 Spectrum textures"), ResourcePackActivationType.NORMAL);
			ResourceManagerHelper.registerBuiltinResourcePack(locate("spectrum_programmer_art"), modContainer.get(), Component.nullToEmpty("Spectrum's Programmer Art"), ResourcePackActivationType.NORMAL);
		}

		logInfo("Common startup completed!");
	}
	
	/**
	 * When initializing a block entity, world can still be null
	 * Therefore we use the RecipeManager reference from MinecraftServer
	 * This in turn does not work on clients connected to dedicated servers, though
	 * since ServerLifecycleHooks.getCurrentServer() is null
	 */
	public static Optional<RecipeManager> getRecipeManager(@Nullable Level world) {
		return Optional.ofNullable(world).map(Level::getRecipeManager).or(() -> Optional.ofNullable(ServerLifecycleHooks.getCurrentServer()).map(MinecraftServer::getRecipeManager));
	}
	
}
