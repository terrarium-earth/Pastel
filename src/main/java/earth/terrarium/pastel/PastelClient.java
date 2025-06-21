package earth.terrarium.pastel;

import de.dafuqs.revelationary.api.advancements.ClientAdvancementPacketCallback;
import de.dafuqs.revelationary.api.revelations.RevealingCallback;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.compat.ears.EarsCompat;
import earth.terrarium.pastel.config.PastelConfig;
import earth.terrarium.pastel.entity.PastelEntityRenderers;
import earth.terrarium.pastel.inventories.PastelScreenHandlerTypes;
import earth.terrarium.pastel.particle.PastelParticleFactories;
import earth.terrarium.pastel.progression.UnlockToastManager;
import earth.terrarium.pastel.progression.toast.RevelationToast;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import earth.terrarium.pastel.registries.PastelTooltips;
import earth.terrarium.pastel.registries.client.PastelDimensionsClient;
import earth.terrarium.pastel.registries.client.PastelArmorRenderers;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import earth.terrarium.pastel.registries.client.PastelModelPredicateProviders;
import earth.terrarium.pastel.registries.client.PastelTooltipComponents;
import earth.terrarium.pastel.registries.events.PastelClientEvents;
import earth.terrarium.pastel.render.HudRenderers;
import earth.terrarium.pastel.render.SkyLerper;
import earth.terrarium.pastel.render.armor.BedrockCapeRenderer;
import earth.terrarium.pastel.render.capes.WorthinessChecker;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Set;

import static earth.terrarium.pastel.PastelCommon.CONFIG;
import static earth.terrarium.pastel.PastelCommon.logInfo;

public class PastelClient implements RevealingCallback, ClientAdvancementPacketCallback {

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

		//logInfo("Registering Server to Client Package Receivers...");
		//PastelS2CPackets.registerS2CReceivers();

		logInfo("Registering Particle Factories...");
		pastelBus.addListener(PastelParticleFactories::register);

		logInfo("Registering Overlays...");
		pastelBus.addListener(HudRenderers::registerLayers);
		NeoForge.EVENT_BUS.addListener(HudRenderers::registerInjects);

		logInfo("Registering Item Tooltips...");
		pastelBus.addListener(PastelTooltipComponents::registerTooltipComponents);

		logInfo("Registering Dimension Effects...");
		pastelBus.addListener(PastelDimensionsClient::registerClient);

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

		RevealingCallback.register(this);
		ClientAdvancementPacketCallback.registerCallback(this);

		logInfo("Client startup completed!");
	}

	@Override
	public void trigger(Set<ResourceLocation> advancements, Set<Block> blocks, Set<Item> items, boolean isJoinPacket) {
		if (!isJoinPacket) {
			for (Block block : blocks) {
				if (BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(PastelCommon.MOD_ID)) {
					RevelationToast.showRevelationToast(Minecraft.getInstance(), new ItemStack(PastelBlocks.PEDESTAL_BASIC_AMETHYST.get().asItem()), PastelSoundEvents.NEW_REVELATION);
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
