package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.render.RenderHelper;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PedestalScreen extends AbstractContainerScreen<PedestalScreenHandler> {
	
	public static final ResourceLocation BACKGROUND1 = PastelCommon.locate("textures/gui/container/pedestal1.png");
	public static final ResourceLocation BACKGROUND2 = PastelCommon.locate("textures/gui/container/pedestal2.png");
	public static final ResourceLocation BACKGROUND3 = PastelCommon.locate("textures/gui/container/pedestal3.png");
	public static final ResourceLocation BACKGROUND4 = PastelCommon.locate("textures/gui/container/pedestal4.png");
	private final ResourceLocation backgroundTexture;

    public PedestalScreen(PedestalScreenHandler handler, Inventory playerInventory, Component title) {
		super(handler, playerInventory, title);
		this.imageHeight = 194;
		this.backgroundTexture = getBackgroundTextureForTier(this.menu.getTier());
	}
	
	@Contract(pure = true)
	public static ResourceLocation getBackgroundTextureForTier(@NotNull PedestalTier recipeTier) {
		switch (recipeTier) {
			case COMPLEX -> {
				return BACKGROUND4;
			}
			case ADVANCED -> {
				return BACKGROUND3;
			}
			case SIMPLE -> {
				return BACKGROUND2;
			}
			default -> {
				return BACKGROUND1;
			}
		}
	}
	
	@Override
	protected void renderLabels(GuiGraphics drawContext, int mouseX, int mouseY) {
		// draw "title" and "inventory" texts
		int titleX = (imageWidth - font.width(title)) / 2; // 8;
		int titleY = 7;
		Component title = this.title;
		int inventoryX = 8;
		int intInventoryY = 100;
		var tr = this.font;
		
		drawContext.drawString(tr, title, titleX, titleY, RenderHelper.GREEN_COLOR, false);
		drawContext.drawString(tr, this.playerInventoryTitle, inventoryX, intInventoryY, RenderHelper.GREEN_COLOR, false);
	}
	
	@Override
	protected void renderBg(GuiGraphics drawContext, float delta, int mouseX, int mouseY) {
		// background
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		drawContext.blit(backgroundTexture, x, y, 0, 0, imageWidth, imageHeight);
		
		// crafting arrow
		boolean isCrafting = this.menu.isCrafting();
		if (isCrafting) {
			int progressWidth = (this.menu).getCraftingProgress();
			// x+y: destination, u+v: original coordinates in texture file
			drawContext.blit(backgroundTexture, x + 88, y + 37, 176, 0, progressWidth + 1, 16);
		}
	}
	
	@Override
	public void render(GuiGraphics drawContext, int mouseX, int mouseY, float delta) {
		renderBackground(drawContext, mouseX, mouseY, delta);
		super.render(drawContext, mouseX, mouseY, delta);
		renderTooltip(drawContext, mouseX, mouseY);
	}
	
}
