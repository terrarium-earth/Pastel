package earth.terrarium.pastel.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.azure_dike.DikeShieldData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.*;

@OnlyIn(Dist.CLIENT)
public class HudRenderers {

	public static final ResourceLocation PLAYER_DIKE = PastelCommon.locate("player_dike");
	
	private static final Component missingInkText = Component.translatable("item.pastel.constructors_staff.tooltip.missing_ink");
	private static final Component noneText = Component.translatable("item.pastel.constructors_staff.tooltip.none_in_inventory");
	
	private static ItemStack itemStackToRender;
	private static int amount;
	private static boolean missingInk;

	public static void registerLayers(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, PLAYER_DIKE, new AzureDikeLayer());
	}

	public static void registerInjects(RenderGuiLayerEvent.Post event) {
		if (event.getName().equals(VanillaGuiLayers.CROSSHAIR))
			renderSelectedStaffStack(event.getGuiGraphics());
	}
	
	private static final int DIKE_HEARTS_PER_ROW = 10;
	private static final int DIKE_PER_ROW = 20;

	private static class AzureDikeLayer implements LayeredDraw.Layer {

		@Override
		public void render(GuiGraphics gui, DeltaTracker deltaTracker) {
			if (Minecraft.getInstance().gameMode == null || !Minecraft.getInstance().gameMode.canHurtPlayer())
				return;

			var cameraPlayer = Minecraft.getInstance().player;
			var x = gui.guiWidth() / 2 - 91;
			var y = gui.guiWidth() / 2 - 22;
			
			DikeShieldData azureDikeComponent = AzureDikeProvider.getAzureDikeComponent(cameraPlayer);
			int maxCharges = (int) Math.ceil(azureDikeComponent.getMaxProtection());
			if (maxCharges > 0) {
				int charges = (int) Math.ceil(azureDikeComponent.getCurrentProtection());

				boolean blink = false;
				if (cameraPlayer.getLastDamageSource()!=null && cameraPlayer.invulnerableTime / 3 > 0) {
					blink = (cameraPlayer.level().getGameTime() >> 2) % 2 == 0;
				}

				int totalDikeCanisters = (maxCharges - 1) / DIKE_PER_ROW;
				int filledDikeCanisters = (charges - 1) / DIKE_PER_ROW;
				int displayedDike = (charges - 1) % DIKE_PER_ROW + 1;
				int dikeHeartOutlinesThisRow = totalDikeCanisters > filledDikeCanisters ? DIKE_HEARTS_PER_ROW : (((maxCharges - 1) % DIKE_PER_ROW / 2) + 1);

				boolean renderBackRow = filledDikeCanisters > 0;
				boolean hasArmor = cameraPlayer.getArmorValue() > 0;

				var texture = DikeShieldData.AZURE_DIKE_BAR_TEXTURE;

				x += PastelCommon.CONFIG.AzureDikeHudOffsetX;
				y += hasArmor ? PastelCommon.CONFIG.AzureDikeHudOffsetYWithArmor : PastelCommon.CONFIG.AzureDikeHudOffsetY;

				RenderSystem.enableBlend();

				// back row
				if (renderBackRow) {
					for (int i = displayedDike / 2; i < 10; i++) {
						gui.blit(texture, x + i * 8, y, 36, 9, 9, 9, 256, 256); // "back row" icon
					}
				}

				// outline
				for (int i = 0; i < dikeHeartOutlinesThisRow; i++) {
					if (renderBackRow) {
						if (blink) {
							gui.blit(texture, x + i * 8, y, 54, 9, 9, 9, 256, 256); // background
						} else {
							gui.blit(texture, x + i * 8, y, 45, 9, 9, 9, 256, 256); // background
						}
					} else {
						if (blink) {
							gui.blit(texture, x + i * 8, y, 9, 9, 9, 9, 256, 256); // background
						} else {
							gui.blit(texture, x + i * 8, y, 0, 9, 9, 9, 256, 256); // background
						}
					}
				}

				// hearts
				for (int i = 0; i < displayedDike; i += 2) {
					if (i + 1 < displayedDike) {
						gui.blit(texture, x + i * 4, y, 18, 9, 9, 9, 256, 256); // full charge icon
					} else {
						gui.blit(texture, x + i * 4, y, 27, 9, 9, 9, 256, 256); // half charge icon
					}
				}

				// canisters
				for (int i = 0; i < filledDikeCanisters; i++) {
					gui.blit(texture, x + i * 6, y - 9, 0, 0, 9, 9, 256, 256); // full canisters
				}
				for (int i = filledDikeCanisters; i < totalDikeCanisters; i++) {
					gui.blit(texture, x + i * 6, y - 9, 9, 0, 9, 9, 256, 256); // empty canisters
				}

				RenderSystem.disableBlend();
			}
		}
	}
	
	private static void renderSelectedStaffStack(GuiGraphics gui) {
		Minecraft client = Minecraft.getInstance();
		if (amount > -1 && itemStackToRender != null) {
			// Render the item stack next to the cursor
			Window window = Minecraft.getInstance().getWindow();
			int x = window.getGuiScaledWidth() / 2 + 3;
			int y = window.getGuiScaledHeight() / 2 + 3;
			
			var poseStack = gui.pose();
			poseStack.pushPose();
			poseStack.scale(0.5F, 0.5F, 1F);
			
			var textRenderer = client.font;
			gui.renderItem(itemStackToRender, (x + 8) * 2, (y + 8) * 2); // TODO: make this render 2x the size, so it spans all 2 rows of text
			poseStack.scale(2F, 2F, 1F);
			gui.drawString(textRenderer, itemStackToRender.getHoverName(), x + 18, y + 8, 0xFFFFFF, false);
			if (amount == 0) {
				gui.drawString(textRenderer, noneText, x + 18, y + 19, 0xDDDDDD, false);
			} else if (missingInk) {
				gui.drawString(textRenderer, missingInkText, x + 18, y + 19, 0xDDDDDD, false);
			} else {
				gui.drawString(textRenderer, amount + "x", x + 18, y + 19, 0xDDDDDD, false);
			}
			poseStack.popPose();
		}
	}
	
	public static void setItemStackToRender(ItemStack itemStack, int amount, boolean missingInk) {
		HudRenderers.itemStackToRender = itemStack;
		HudRenderers.amount = amount;
		HudRenderers.missingInk = missingInk;
	}
	
	public static void clearItemStackOverlay() {
		HudRenderers.amount = -1;
	}
	
}
