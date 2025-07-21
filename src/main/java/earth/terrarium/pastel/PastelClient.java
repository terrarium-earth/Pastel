package earth.terrarium.pastel;

import com.cmdpro.databank.advancement.ClientAdvancementListener;
import com.cmdpro.databank.hidden.ClientHiddenListener;
import com.cmdpro.databank.hidden.Hidden;
import com.cmdpro.databank.hidden.types.BlockHiddenType;
import earth.terrarium.pastel.blocks.mob_head.client.PastelSkullModels;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.compat.ears.EarsCompat;
import earth.terrarium.pastel.config.PastelConfig;
import earth.terrarium.pastel.deeper_down.EnvironmentalOverrides;
import earth.terrarium.pastel.entity.PastelEntityRenderers;
import earth.terrarium.pastel.events.PastelClientEvents;
import earth.terrarium.pastel.inventories.PastelScreenHandlerTypes;
import earth.terrarium.pastel.particle.PastelParticleFactories;
import earth.terrarium.pastel.progression.UnlockToastManager;
import earth.terrarium.pastel.progression.toast.RevelationToast;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import earth.terrarium.pastel.registries.PastelTooltips;
import earth.terrarium.pastel.registries.client.PastelArmorRenderers;
import earth.terrarium.pastel.registries.client.PastelDimensionsClient;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import earth.terrarium.pastel.registries.client.PastelModelPredicateProviders;
import earth.terrarium.pastel.registries.client.PastelTooltipComponents;
import earth.terrarium.pastel.render.HudRenderers;
import earth.terrarium.pastel.render.SkyLerper;
import earth.terrarium.pastel.render.armor.BedrockCapeRenderer;
import earth.terrarium.pastel.render.capes.WorthinessChecker;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashSet;
import java.util.List;

import static earth.terrarium.pastel.PastelCommon.CONFIG;
import static earth.terrarium.pastel.PastelCommon.logInfo;

public class PastelClient implements ClientHiddenListener, ClientAdvancementListener {

	public static final SkyLerper skyLerper = new SkyLerper();

	public PastelClient(IEventBus pastelBus, ModContainer container) {
		if (FMLLoader.getDist().isDedicatedServer()) {
			return;
		}

		logInfo("Starting Client Startup");

		logInfo("Registering Model Layers...");
		pastelBus.addListener(PastelModelLayers::register);

		logInfo("Setting up Block Rendering...");
		pastelBus.addListener(PastelBlocks::registerClient);

		logInfo("Setting up client side Mod Compat...");
		PastelIntegrationPacks.registerClient();

		logInfo("Setting up Fluid Rendering...");
		pastelBus.addListener(PastelFluids::registerClient);
		pastelBus.addListener(PastelFluids::clientSetup);

		logInfo("Setting up GUIs...");
		pastelBus.register(PastelScreenHandlerTypes.class);

		logInfo("Setting up ItemPredicates...");
		pastelBus.addListener(PastelModelPredicateProviders::registerClient);

		logInfo("Setting up Block Entity Renderers...");
		pastelBus.addListener(PastelBlockEntities::registerClient);
		logInfo("Setting up Entity Renderers...");
		pastelBus.addListener(PastelEntityRenderers::registerClient);
		pastelBus.addListener(BedrockCapeRenderer::registerLayers);

		logInfo("Registering Particle Factories...");
		pastelBus.addListener(PastelParticleFactories::register);

		logInfo("Registering Overlays...");
		pastelBus.addListener(HudRenderers::registerLayers);
		NeoForge.EVENT_BUS.addListener(HudRenderers::registerInjects);

		logInfo("Registering Item Tooltips...");
		pastelBus.addListener(PastelTooltipComponents::registerTooltipComponents);

		logInfo("Registering Dimension Effects...");
		pastelBus.addListener(PastelDimensionsClient::registerClient);
		EnvironmentalOverrides.init();

		logInfo("Registering Mob head models...");
		pastelBus.addListener(PastelSkullModels::registerModels);
		pastelBus.addListener(PastelSkullModels::registerTextures);

		logInfo("Registering Client Event Listeners...");
		PastelClientEvents.register(pastelBus);

		container.registerExtensionPoint(IConfigScreenFactory.class, (v, parent) -> AutoConfig.getConfigScreen(PastelConfig.class, parent).get());

		if (CONFIG.AddItemTooltips) {
			NeoForge.EVENT_BUS.addListener(PastelTooltips::register);
		}

		if (ModList.get().isLoaded("ears")) {
			logInfo("Registering Ears Compat...");
			EarsCompat.register();
		}

		logInfo("Registering Armor Renderers...");
		pastelBus.addListener(PastelArmorRenderers::register);
		WorthinessChecker.init();

		ADVANCEMENT_LISTENERS.add(this);
		HIDDEN_LISTENERS.add(this);

		logInfo("Client startup completed!");
	}
	
	@Override
	public void onUnhide(List<Hidden> unlocked) {
		for (Hidden i : unlocked) {
			if (i.type instanceof BlockHiddenType.BlockHiddenTypeInstance type) {
				if (BuiltInRegistries.BLOCK.getKey(type.original).getNamespace().equals(PastelCommon.MOD_ID)) {
					RevelationToast.showRevelationToast(Minecraft.getInstance(), new ItemStack(PastelBlocks.PEDESTAL_BASIC_AMETHYST.get().asItem()), PastelSoundEvents.NEW_REVELATION);
					break;
				}
			}
		}
	}
	
	@Override
	public void onUnlock(List<ResourceLocation> unlocked) {
		UnlockToastManager.processAdvancements(new HashSet<>(unlocked));
	}
}
