package de.dafuqs.spectrum;

import de.dafuqs.revelationary.api.advancements.ClientAdvancementPacketCallback;
import de.dafuqs.revelationary.api.revelations.RevealingCallback;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.compat.ears.EarsCompat;
import de.dafuqs.spectrum.entity.SpectrumEntityRenderers;
import de.dafuqs.spectrum.inventories.SpectrumScreenHandlerTypes;
import de.dafuqs.spectrum.particle.SpectrumParticleFactories;
import de.dafuqs.spectrum.progression.UnlockToastManager;
import de.dafuqs.spectrum.progression.toast.RevelationToast;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumDimensions;
import de.dafuqs.spectrum.registries.SpectrumFluids;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import de.dafuqs.spectrum.registries.SpectrumTooltips;
import de.dafuqs.spectrum.registries.client.SpectrumArmorRenderers;
import de.dafuqs.spectrum.registries.client.SpectrumClientEventListeners;
import de.dafuqs.spectrum.registries.client.SpectrumModelLayers;
import de.dafuqs.spectrum.registries.client.SpectrumModelPredicateProviders;
import de.dafuqs.spectrum.registries.client.SpectrumTooltipComponents;
import de.dafuqs.spectrum.render.HudRenderers;
import de.dafuqs.spectrum.render.SkyLerper;
import de.dafuqs.spectrum.render.capes.WorthinessChecker;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.*;

import java.util.Set;

import static de.dafuqs.spectrum.SpectrumCommon.CONFIG;
import static de.dafuqs.spectrum.SpectrumCommon.logInfo;

@OnlyIn(Dist.CLIENT)
@Mod(value = SpectrumCommon.MOD_ID, dist = Dist.CLIENT)
public class SpectrumClient implements RevealingCallback, ClientAdvancementPacketCallback {

	public static final SkyLerper skyLerper = new SkyLerper();

	public SpectrumClient() {
		logInfo("Starting Client Startup");

		logInfo("Registering Model Layers...");
		SpectrumModelLayers.register();

		logInfo("Setting up Block Rendering...");
		SpectrumBlocks.registerClient();

		logInfo("Setting up client side Mod Compat...");
		SpectrumIntegrationPacks.registerClient();

		logInfo("Setting up Fluid Rendering...");
		SpectrumFluids.registerClient();

		logInfo("Setting up GUIs...");
		NeoForge.EVENT_BUS.register(SpectrumScreenHandlerTypes.class);

		logInfo("Setting up ItemPredicates...");
		SpectrumModelPredicateProviders.registerClient();

		logInfo("Setting up Block Entity Renderers...");
		SpectrumBlockEntities.registerClient();
		logInfo("Setting up Entity Renderers...");
		SpectrumEntityRenderers.registerClient();

		//logInfo("Registering Server to Client Package Receivers...");
		//SpectrumS2CPackets.registerS2CReceivers();

		logInfo("Registering Particle Factories...");
		SpectrumParticleFactories.register();

		logInfo("Registering Overlays...");
		HudRenderers.register();

		logInfo("Registering Item Tooltips...");
		NeoForge.EVENT_BUS.addListener(SpectrumTooltipComponents::registerTooltipComponents);

		logInfo("Registering Dimension Effects...");
		SpectrumDimensions.registerClient();

		logInfo("Registering Event Listeners...");
		SpectrumClientEventListeners.register();

		if (CONFIG.AddItemTooltips) {
			SpectrumTooltips.register();
		}

		if (ModList.get().isLoaded("ears")) {
			logInfo("Registering Ears Compat...");
			EarsCompat.register();
		}

		logInfo("Registering Armor Renderers...");
		SpectrumArmorRenderers.register();
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
					RevelationToast.showRevelationToast(Minecraft.getInstance(), new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.asItem()), SpectrumSoundEvents.NEW_REVELATION);
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
