package earth.terrarium.pastel;

import com.cmdpro.databank.DatabankUtils;
import com.cmdpro.databank.misc.VersionChangeHelper;
import earth.terrarium.pastel.api.color.ColorRegistry;
import earth.terrarium.pastel.api.energy.color.InkColorMixes;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.attachments.PastelDataAttachments;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.config.PastelConfig;
import earth.terrarium.pastel.data_loaders.CrystalApothecarySimulationsDataLoader;
import earth.terrarium.pastel.data_loaders.EntityFishingDataLoader;
import earth.terrarium.pastel.data_loaders.NaturesStaffConversionDataLoader;
import earth.terrarium.pastel.entity.PastelEntitySubPredicateTypes;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.events.PastelDamageEvents;
import earth.terrarium.pastel.events.PastelEffectEvents;
import earth.terrarium.pastel.events.PastelEnchantmentEvents;
import earth.terrarium.pastel.events.PastelEntityEvents;
import earth.terrarium.pastel.events.PastelEquipmentEvents;
import earth.terrarium.pastel.events.PastelMiscEvents;
import earth.terrarium.pastel.events.PastelPlayerEvents;
import earth.terrarium.pastel.events.game.PastelGameEvents;
import earth.terrarium.pastel.events.game.PastelPositionSources;
import earth.terrarium.pastel.inventories.PastelScreenHandlerTypes;
import earth.terrarium.pastel.loot.PastelLootContextTypes;
import earth.terrarium.pastel.loot.PastelLootFunctionTypes;
import earth.terrarium.pastel.loot.PastelLootModifiers;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.networking.PastelS2CPackets;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.progression.PastelCriteria;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod(PastelCommon.MOD_ID)
public class PastelCommon {

    public static final String MOD_ID = "pastel";

    public static final Logger LOGGER = LoggerFactory.getLogger("pastel");
    public static final Map<ResourceLocation, TagKey<Item>> CACHED_ITEM_TAG_MAP = new HashMap<>();
    public static PastelConfig CONFIG;

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
     * This is the Spectrum analogue of Identifier.of, but instead of defaulting to the namespace 'minecraft', it
     * defaults to 'spectrum'.
     *
     * @param id The stringified identifier to parse
     * @return The parsed identifier
     */
    public static ResourceLocation ofPastel(String id) {
        int i = id.indexOf(':');
        String path = id.substring(i + 1);
        String namespace = i > 0 ? id.substring(0, i) : PastelCommon.MOD_ID;
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    static {
        //Set up config
        logInfo("Loading config file...");
        AutoConfig.register(PastelConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(PastelConfig.class)
                           .getConfig();
        logInfo("Finished loading config file.");
    }

    public PastelCommon(IEventBus pastelBus, ModContainer container) {
        logInfo("Starting Common Startup");
        NeoForgeMod.enableMilkFluid();

        // Register internals
        pastelBus.addListener(PastelRegistries::register);
        pastelBus.addListener(PastelRegistries::registerDyn);
        InkColors.register(pastelBus);
        InkColorMixes.register();
        PastelEntityAttributes.register(pastelBus);
        PastelLoadConditions.register(pastelBus);
        IngredientStack.register(pastelBus);

        logInfo("Registering Component Types...");
        PastelDataComponentTypes.register(pastelBus);

        logInfo("Registering Block / Item Color Registries...");
        ColorRegistry.registerColorRegistries();

        // Register ALL the stuff
        logInfo("Registering Status Effects...");
        PastelMobEffects.register(pastelBus);
        logInfo("Registering Advancement Criteria...");
        PastelCriteria.register(pastelBus);
        logInfo("Registering Particle Types...");
        PastelParticleTypes.register(pastelBus);
        logInfo("Registering Sound Events...");
        PastelSounds.register(pastelBus);
        logInfo("Registering BlockSound Groups...");
        PastelBlockSoundGroups.register();
        logInfo("Registering Fluids...");
        PastelFluids.register(pastelBus);
        logInfo("Registering Armor Materials...");
        PastelArmorMaterials.register(pastelBus);
        logInfo("Registering Registry Aliases...");
        PastelRegistryAliases.registerAliases();
        logInfo("Registering Blocks...");
        PastelBlocks.registerCommon(pastelBus);
        logInfo("Registering Items...");
        PastelPotions.register(pastelBus);
        PastelItems.register(pastelBus);
        PastelItemGroups.register(pastelBus);
        pastelBus.addListener(PastelItemGroups::registerSpawnEggs);

        logInfo("Registering Block Entities...");
        PastelBlockEntities.register(pastelBus);
        pastelBus.addListener(PastelBlockEntities::registerAdditionalTypes);

        logInfo("Registering Capabilities...");
        pastelBus.addListener(PastelCapabilityHandlers::registerBlocks);
        pastelBus.addListener(PastelCapabilityHandlers::registerItems);

        logInfo("Registering Data Attachments...");
        PastelDataAttachments.register(pastelBus);

        // Pastel
        logInfo("Registering Pastel Upgrades...");
        PastelPastelUpgrades.register(pastelBus);
        logInfo("Registering Stamp Categories...");
        PastelStampDataCategories.register(pastelBus);
        PastelPresentUnpackBehaviors.register();

        // Worldgen
        logInfo("Registering Features...");
        PastelFeatures.register(pastelBus);
        logInfo("Registering Structure Types...");
        PastelStructureTypes.register(pastelBus);

        // Dimension
        logInfo("Registering Dimension...");
        PastelLevels.register();

        // Recipes
        logInfo("Registering Recipe Types...");
        PastelRecipeScalings.init();
        PastelFusionShrineWorldEffects.register(pastelBus);
        PastelRecipeTypes.register(pastelBus);
        PastelRecipeSerializers.register(pastelBus);

        // Loot
        logInfo("Registering Loot Conditions & Functions...");
        PastelLootContextTypes.register();
        PastelLootConditions.register(pastelBus);
        PastelLootFunctionTypes.register(pastelBus);
        PastelLootModifiers.register(pastelBus);

        logInfo("Setting up server side Mod Compat...");
        PastelIntegrationPacks.register(pastelBus);

        // GUI
        logInfo("Registering Screen Handler Types...");
        PastelScreenHandlerTypes.registerMenus(pastelBus);

        logInfo("Registering Default Item Stack Damage Immunities...");
        PastelItemDamageImmunities.registerDefaultItemStackImmunities();
        logInfo("Registering Variant Specific Predicates...");
        PastelItemSubPredicateTypes.register(pastelBus);
        PastelEntitySubPredicateTypes.register(pastelBus);


        logInfo("Registering Entities...");
        PastelTrackedDataHandlers.register(pastelBus);
        PastelEntityTypes.register(pastelBus);

        logInfo("Registering Omni Accelerator Projectiles & Behaviors...");
        PastelOmniAcceleratorProjectiles.register();
        PastelItemProjectileBehaviors.register();

        PastelEntityColorProcessors.register();
        PastelItemProviders.register(pastelBus);

        logInfo("Registering Commands...");
        NeoForge.EVENT_BUS.addListener(PastelCommands::register);

        logInfo("Registering Networking Packets...");
        pastelBus.addListener(PastelC2SPackets::register);

        pastelBus.addListener(
            RegisterPayloadHandlersEvent.class, (event) -> {
                PayloadRegistrar registrar = event.registrar("1");

                PastelS2CPackets.register(registrar);
            }
        );

        logInfo("Registering Data Loaders...");
        NeoForge.EVENT_BUS.addListener(PastelCommon::registerReloadListeners);

        NeoForge.EVENT_BUS.addListener(Pastel::clearServerInstance);

        logInfo("Adding to Fabric's Registries...");
        //PastelFlammableBlocks.register(); TODO find an event that makes this not shit your pants

        logInfo("Registering Game Events...");
        PastelGameEvents.register(pastelBus);
        PastelPositionSources.register(pastelBus);

        logInfo("Registering Dispenser, Resonance & Present Unwrap Behaviors...");
        // PastelDispenserBehaviors.register(); TODO these two also need to be initialized later
        // PastelPresentUnpackBehaviors.register();
        PastelResonanceProcessorTypes.register(pastelBus);

        logInfo("Registering Resource Conditions...");
        PastelResourceConditions.register(pastelBus);
        logInfo("Registering Structure WeightedPool Element Types...");
        PastelStructurePoolElementTypes.register(pastelBus);
        logInfo("Registering Event Listeners...");
        PastelMiscEvents.register();
        PastelEntityEvents.register();
        PastelDamageEvents.register();
        PastelPlayerEvents.register();
        PastelEquipmentEvents.register();
        PastelEffectEvents.register(pastelBus);
        PastelEnchantmentEvents.register();
        logInfo("Registering Tree Decorator Types...");
        PastelTreeDecoratorTypes.register(pastelBus);

        pastelBus.addListener(PastelMiscEvents::loadComplete);

        PastelDataMaps.register();

        // Builtin Resource Packs
        logInfo("Registering Builtin Resource Packs...");

        logInfo("Common startup completed!");

        new PastelClient(pastelBus, container);

        VersionChangeHelper.registerPlayerListener(MOD_ID, (modId, from, to, player) -> {
            DatabankUtils.recheckAdvancements(player);
        });
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
     * since PastelCommon.getSidedServer() is null
     */
    public static Optional<RecipeManager> getRecipeManager(@Nullable Level world) {
        return Optional.ofNullable(world)
                       .map(Level::getRecipeManager)
                       .or(() -> Optional.ofNullable(PastelCommon.getSidedServer())
                                         .map(MinecraftServer::getRecipeManager));
    }

    @Nullable
    public static RegistryAccess getRegistryAccess() {
        if (getSidedServer() == null)
            return null;

        return getSidedServer().registryAccess();
    }

    @Nullable
    public static MinecraftServer getSidedServer() {
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            return ServerLifecycleHooks.getCurrentServer();
        } else {
            return PastelSided.getClientServer();
        }
    }

}
