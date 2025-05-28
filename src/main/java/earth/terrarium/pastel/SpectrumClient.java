package earth.terrarium.pastel;

import de.dafuqs.revelationary.api.advancements.ClientAdvancementPacketCallback;
import de.dafuqs.revelationary.api.revelations.RevealingCallback;
import earth.terrarium.pastel.compat.SpectrumIntegrationPacks;
import earth.terrarium.pastel.compat.ears.EarsCompat;
import earth.terrarium.pastel.config.*;
import earth.terrarium.pastel.entity.SpectrumEntityRenderers;
import earth.terrarium.pastel.inventories.SpectrumScreenHandlerTypes;
import earth.terrarium.pastel.particle.SpectrumParticleFactories;
import earth.terrarium.pastel.progression.UnlockToastManager;
import earth.terrarium.pastel.progression.toast.RevelationToast;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumDimensions;
import earth.terrarium.pastel.registries.SpectrumFluids;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import earth.terrarium.pastel.registries.SpectrumTooltips;
import earth.terrarium.pastel.registries.client.SpectrumArmorRenderers;
import earth.terrarium.pastel.registries.client.SpectrumClientEventListeners;
import earth.terrarium.pastel.registries.client.SpectrumModelLayers;
import earth.terrarium.pastel.registries.client.SpectrumModelPredicateProviders;
import earth.terrarium.pastel.registries.client.SpectrumTooltipComponents;
import earth.terrarium.pastel.render.HudRenderers;
import earth.terrarium.pastel.render.SkyLerper;
import earth.terrarium.pastel.render.armor.*;
import earth.terrarium.pastel.render.capes.WorthinessChecker;
import me.shedaniel.autoconfig.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.*;
import net.neoforged.neoforge.common.*;

import java.util.Set;

import static earth.terrarium.pastel.SpectrumCommon.CONFIG;
import static earth.terrarium.pastel.SpectrumCommon.logInfo;

public class SpectrumClient implements RevealingCallback, ClientAdvancementPacketCallback {

	public static final SkyLerper skyLerper = new SkyLerper();

	public SpectrumClient(IEventBus pastelBus, ModContainer container) {
		logInfo("Starting Client Startup");

		logInfo("Registering Model Layers...");
		pastelBus.addListener(SpectrumModelLayers::register);

		logInfo("Setting up Block Rendering...");
		SpectrumBlocks.registerClient();

		logInfo("Setting up client side Mod Compat...");
		SpectrumIntegrationPacks.registerClient();

		logInfo("Setting up Fluid Rendering...");
		pastelBus.addListener(SpectrumFluids::registerClient);
		pastelBus.addListener(SpectrumFluids::clientSetup);

		logInfo("Setting up GUIs...");
		NeoForge.EVENT_BUS.register(SpectrumScreenHandlerTypes.class);

		logInfo("Setting up ItemPredicates...");
		SpectrumModelPredicateProviders.registerClient();

		logInfo("Setting up Block Entity Renderers...");
		SpectrumBlockEntities.registerClient();
		logInfo("Setting up Entity Renderers...");
		SpectrumEntityRenderers.registerClient();
		NeoForge.EVENT_BUS.addListener(BedrockCapeRenderer::register);

		//logInfo("Registering Server to Client Package Receivers...");
		//SpectrumS2CPackets.registerS2CReceivers();

		logInfo("Registering Particle Factories...");
		pastelBus.addListener(SpectrumParticleFactories::register);

		logInfo("Registering Overlays...");
		pastelBus.addListener(HudRenderers::registerLayers);
		NeoForge.EVENT_BUS.addListener(HudRenderers::registerInjects);

		logInfo("Registering Item Tooltips...");
		NeoForge.EVENT_BUS.addListener(SpectrumTooltipComponents::registerTooltipComponents);

		logInfo("Registering Dimension Effects...");
		SpectrumDimensions.registerClient();

		logInfo("Registering Client Event Listeners...");
		SpectrumClientEventListeners.register(pastelBus);

		container.registerExtensionPoint(IConfigScreenFactory.class, (v, parent) -> AutoConfig.getConfigScreen(SpectrumConfig.class, parent).get());

		if (CONFIG.AddItemTooltips) {
			NeoForge.EVENT_BUS.addListener(SpectrumTooltips::register);
		}

		if (ModList.get().isLoaded("ears")) {
			logInfo("Registering Ears Compat...");
			EarsCompat.register();
		}

		logInfo("Registering Armor Renderers...");
		pastelBus.addListener(SpectrumArmorRenderers::register);
		WorthinessChecker.init();

		RevealingCallback.register(this);
		ClientAdvancementPacketCallback.registerCallback(this);

		logInfo("Client startup completed!");
	}

	@Override
	public void trigger(Set<ResourceLocation> advancements, Set<Block> blocks, Set<Item> items, boolean isJoinPacket) {
		if (!isJoinPacket) {
			for (Block block : blocks) {
				if (BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(SpectrumCommon.MOD_ID)) {
					RevelationToast.showRevelationToast(Minecraft.getInstance(), new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.get().asItem()), SpectrumSoundEvents.NEW_REVELATION);
					break;
				}
			}
		}
	}

	@Override
	public void onClientAdvancementPacket(Set<ResourceLocation> gottenAdvancements, Set<ResourceLocation> removedAdvancements, boolean isJoinPacket) {
		if (!isJoinPacket) {
			UnlockToastManager.processAdvancements(gottenAdvancements);
		}
	}
	
}
