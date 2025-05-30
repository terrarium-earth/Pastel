package earth.terrarium.pastel;

import de.dafuqs.revelationary.Revelationary;
import de.dafuqs.revelationary.RevelationaryNetworking;
import earth.terrarium.pastel.api.color.ColorRegistry;
import earth.terrarium.pastel.api.energy.color.InkColorMixes;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.attachments.*;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.compat.SpectrumIntegrationPacks;
import earth.terrarium.pastel.config.SpectrumConfig;
import earth.terrarium.pastel.data_loaders.CrystalApothecarySimulationsDataLoader;
import earth.terrarium.pastel.data_loaders.EntityFishingDataLoader;
import earth.terrarium.pastel.data_loaders.NaturesStaffConversionDataLoader;
import earth.terrarium.pastel.entity.SpectrumEntitySubPredicateTypes;
import earth.terrarium.pastel.entity.SpectrumEntityTypes;
import earth.terrarium.pastel.entity.SpectrumTrackedDataHandlerRegistry;
import earth.terrarium.pastel.events.SpectrumGameEvents;
import earth.terrarium.pastel.events.SpectrumPositionSources;
import earth.terrarium.pastel.inventories.SpectrumScreenHandlerTypes;
import earth.terrarium.pastel.loot.SpectrumLootContextTypes;
import earth.terrarium.pastel.loot.SpectrumLootFunctionTypes;
import earth.terrarium.pastel.loot.SpectrumLootPoolModifiers;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.networking.SpectrumS2CPackets;
import earth.terrarium.pastel.particle.SpectrumParticleTypes;
import earth.terrarium.pastel.progression.SpectrumAdvancementCriteria;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.registries.events.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.*;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod(SpectrumCommon.MOD_ID)
public class SpectrumCommon {
	
	public static final String MOD_ID = "pastel";
	
	public static final Logger LOGGER = LoggerFactory.getLogger("pastel");
	public static final Map<ResourceLocation, TagKey<Item>> CACHED_ITEM_TAG_MAP = new HashMap<>();
	public static SpectrumConfig CONFIG;
	
	public static void logInfo(String message) {
		LOGGER.info("[pastel] {}", message);
	}
	
	public static void logWarning(String message) {
		LOGGER.warn("[pastel] {}", message);
	}
	
	public static void logError(String message) {
		LOGGER.error("[pastel] {}", message);
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
	
	static {
		//Set up config
		logInfo("Loading config file...");
		AutoConfig.register(SpectrumConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(SpectrumConfig.class).getConfig();
		logInfo("Finished loading config file.");
	}

	public SpectrumCommon(IEventBus pastelBus, ModContainer container) {
		Revelationary.onInitialize(pastelBus);

		logInfo("Starting Common Startup");
		
		// Register internals
		SpectrumRegistries.register(pastelBus);
		InkColors.register(pastelBus);
		InkColorMixes.register();
		SpectrumEntityAttributes.register(pastelBus);
		SpectrumLoadConditions.register(pastelBus);
		IngredientStack.register(pastelBus);
		
		logInfo("Registering Component Types...");
		SpectrumDataComponentTypes.register(pastelBus);
		
		logInfo("Registering Block / Item Color Registries...");
		ColorRegistry.registerColorRegistries();
		
		// Register ALL the stuff
		logInfo("Registering Status Effects...");
		SpectrumStatusEffects.register(pastelBus);
		logInfo("Registering Advancement Criteria...");
		SpectrumAdvancementCriteria.register(pastelBus);
		logInfo("Registering Particle Types...");
		SpectrumParticleTypes.register(pastelBus);
		logInfo("Registering Sound Events...");
		SpectrumSoundEvents.register(pastelBus);
		logInfo("Registering BlockSound Groups...");
		SpectrumBlockSoundGroups.register();
		logInfo("Registering Fluids...");
		SpectrumFluids.register(pastelBus);
		logInfo("Registering Armor Materials...");
		SpectrumArmorMaterials.register(pastelBus);
		logInfo("Registering Blocks...");
		SpectrumBlocks.registerCommon(pastelBus);
		logInfo("Registering Items...");
		SpectrumPotions.register(pastelBus);
		SpectrumItems.register(pastelBus);
		SpectrumItemGroups.register(pastelBus);

		logInfo("Registering Block Entities...");
		SpectrumBlockEntities.register(pastelBus);
		pastelBus.addListener(SpectrumBlockEntities::registerAdditionalTypes);

		logInfo("Registering Capabilities...");
		pastelBus.addListener(SpectrumCapabilityHandlers::registerBlocks);
		pastelBus.addListener(SpectrumCapabilityHandlers::registerItems);

		logInfo("Registering Data Attachments...");
		SpectrumStoredData.register(pastelBus);

		// Pastel
		logInfo("Registering Pastel Upgrades...");
		SpectrumPastelUpgrades.register();
		logInfo("Registering Stamp Categories...");
		SpectrumStampDataCategories.register(pastelBus);
		
		// Worldgen
		logInfo("Registering Features...");
		SpectrumFeatures.register(pastelBus);
		logInfo("Registering Structure Types...");
		SpectrumStructureTypes.register(pastelBus);
		
		// Dimension
		logInfo("Registering Dimension...");
		SpectrumDimensions.register();
		
		// Recipes
		logInfo("Registering Recipe Types...");
		SpectrumRecipeScalings.init();
		SpectrumFusionShrineWorldEffects.register(pastelBus);
		SpectrumRecipeTypes.register(pastelBus);
		SpectrumRecipeSerializers.register(pastelBus);
		
		// Loot
		logInfo("Registering Loot Conditions & Functions...");
		SpectrumLootContextTypes.register();
		SpectrumLootFunctionTypes.register(pastelBus);
		
		logInfo("Setting up server side Mod Compat...");
		SpectrumIntegrationPacks.register(pastelBus);
		
		// GUI
		logInfo("Registering Screen Handler Types...");
		SpectrumScreenHandlerTypes.registerMenus(pastelBus);
		
		logInfo("Registering Default Item Stack Damage Immunities...");
		SpectrumItemDamageImmunities.registerDefaultItemStackImmunities();
		logInfo("Registering Enchantment Drops...");
		NeoForge.EVENT_BUS.addListener(SpectrumLootPoolModifiers::loadLootTable);
		logInfo("Registering Variant Specific Predicates...");
		SpectrumItemSubPredicateTypes.register(pastelBus);
		SpectrumEntitySubPredicateTypes.register(pastelBus);

		
		logInfo("Registering Entities...");
		SpectrumTrackedDataHandlerRegistry.register(pastelBus);
		SpectrumEntityTypes.register(pastelBus);
		
		logInfo("Registering Omni Accelerator Projectiles & Behaviors...");
		SpectrumOmniAcceleratorProjectiles.register();
		SpectrumItemProjectileBehaviors.register();
		
		SpectrumEntityColorProcessors.register();
		SpectrumItemProviders.register(pastelBus);
		
		logInfo("Registering Commands...");
		NeoForge.EVENT_BUS.addListener(SpectrumCommands::register);
		
		logInfo("Registering Networking Packets...");
		pastelBus.addListener(SpectrumC2SPackets::register);
		
		pastelBus.addListener(RegisterPayloadHandlersEvent.class, (event) -> {
			PayloadRegistrar registrar = event.registrar("1");

			RevelationaryNetworking.register(registrar);
			SpectrumS2CPackets.register(registrar);
		});
		
		logInfo("Registering Data Loaders...");
		NeoForge.EVENT_BUS.addListener(SpectrumCommon::registerReloadListeners);

		NeoForge.EVENT_BUS.addListener(Pastel::clearServerInstance);
		
		logInfo("Adding to Fabric's Registries...");
		//SpectrumFlammableBlocks.register(); TODO find an event that makes this not shit your pants

		logInfo("Registering Game Events...");
		SpectrumGameEvents.register(pastelBus);
		SpectrumPositionSources.register(pastelBus);
		
		logInfo("Registering Dispenser, Resonance & Present Unwrap Behaviors...");
		// SpectrumDispenserBehaviors.register(); TODO these two also need to be initialized later
		// SpectrumPresentUnpackBehaviors.register();
		SpectrumResonanceProcessorTypes.register(pastelBus);
		
		logInfo("Registering Resource Conditions...");
		SpectrumResourceConditions.register(pastelBus);
		logInfo("Registering Structure WeightedPool Element Types...");
		SpectrumStructurePoolElementTypes.register(pastelBus);
		logInfo("Registering Event Listeners...");
		SpectrumMiscEvents.register();
		SpectrumEntityEvents.register();
		SpectrumEquipmentEvents.register();
		logInfo("Registering Tree Decorator Types...");
		SpectrumTreeDecoratorTypes.register(pastelBus);
		
		PastelDataMaps.register();
		
		// Builtin Resource Packs
		logInfo("Registering Builtin Resource Packs...");

		logInfo("Common startup completed!");

		new SpectrumClient(pastelBus, container);
	}

	private static void registerReloadListeners(AddReloadListenerEvent event) {
		event.addListener(NaturesStaffConversionDataLoader.INSTANCE);
		event.addListener(EntityFishingDataLoader.INSTANCE);
		event.addListener(CrystalApothecarySimulationsDataLoader.INSTANCE);
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
