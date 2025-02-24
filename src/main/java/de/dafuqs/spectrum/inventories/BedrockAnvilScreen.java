package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.networking.c2s_payloads.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.component.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.lwjgl.glfw.*;

@Environment(EnvType.CLIENT)
public class BedrockAnvilScreen extends ForgingScreen<BedrockAnvilScreenHandler> {
	
	private static final Identifier TEXTURE = SpectrumCommon.locate("textures/gui/container/bedrock_anvil.png");
	private final PlayerEntity player;
	private TextFieldWidget nameField;
	private TextFieldWidget loreField;
	
	public BedrockAnvilScreen(BedrockAnvilScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
		this.player = inventory.player;
		
		this.titleX = 60;
		this.titleY = this.titleY + 2;
		this.playerInventoryTitleY = 95;
		this.backgroundHeight = 190;
	}
	
	@Override
	protected void setup() {
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		
		this.nameField = new TextFieldWidget(this.textRenderer, i + 62, j + 24, 98, 12, Text.translatable("container.spectrum.bedrock_anvil"));
		this.nameField.setEditableColor(-1);
		this.nameField.setUneditableColor(-1);
		this.nameField.setDrawsBackground(false);
		this.nameField.setMaxLength(AnvilScreenHandler.MAX_NAME_LENGTH);
		this.nameField.setChangedListener(this::onRenamed);
		this.nameField.setText("");
		this.addSelectableChild(this.nameField);
		this.nameField.setEditable((this.handler).getSlot(0).hasStack());
		
		this.loreField = new TextFieldWidget(this.textRenderer, i + 45, j + 76, 116, 12, Text.translatable("container.spectrum.bedrock_anvil.lore"));
		this.loreField.setEditableColor(-1);
		this.loreField.setUneditableColor(-1);
		this.loreField.setDrawsBackground(false);
		this.loreField.setMaxLength(BedrockAnvilScreenHandler.MAX_LORE_LENGTH);
		this.loreField.setChangedListener(this::onLoreChanged);
		this.loreField.setText("");
		this.addSelectableChild(this.loreField);
		this.loreField.setEditable((this.handler).getSlot(0).hasStack());
		
		this.nameField.setEditable(false);
		this.loreField.setEditable(false);
	}
	
	@Override
	protected void setInitialFocus() {
		this.setInitialFocus(this.nameField);
	}
	
	@Override
	public void resize(MinecraftClient client, int width, int height) {
		String name = this.nameField.getText();
		String lore = this.loreField.getText();
		this.init(client, width, height);
		this.nameField.setText(name);
		this.loreField.setText(lore);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_TAB) {
			super.keyPressed(keyCode, scanCode, modifiers);
			return true;
		}
		
		Element focused = this.getFocused();
		return (focused == null || !focused.keyPressed(keyCode, scanCode, modifiers)) || focused instanceof TextFieldWidget textFieldWidget && textFieldWidget.isActive() || super.keyPressed(keyCode, scanCode, modifiers);

	}
	
	private void onRenamed(String name) {
		Slot slot = this.handler.getSlot(0);
		if (slot.hasStack()) {
			String string = name;
			if (!slot.getStack().contains(DataComponentTypes.CUSTOM_NAME) && string.equals(slot.getStack().getName().getString())) {
				string = "";
			}
			
			if ((this.handler).setNewItemName(string)) {
				ClientPlayNetworking.send(new RenameItemInBedrockAnvilPayload(name));
			}
		}
	}
	
	private void onLoreChanged(String lore) {
		Slot slot = this.handler.getSlot(0);
		if (slot.hasStack()) {
			String string = lore;
			if (!LoreHelper.hasLore(slot.getStack()) && string.equals(LoreHelper.getStringFromLoreTextArray(LoreHelper.getLoreList(slot.getStack())))) {
				string = "";
			}
			
			if (this.handler.setNewItemLore(string)) {
				ClientPlayNetworking.send(new AddLoreBedrockAnvilPayload(lore));
			}
		}
	}
	
	@Override
	protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
		super.drawForeground(context, mouseX, mouseY);
		
		context.drawText(textRenderer, Text.translatable("container.spectrum.bedrock_anvil.lore"), playerInventoryTitleX, 76, 4210752, false);
		
		int levelCost = (this.handler).getLevelCost();
		if (levelCost > 0 || this.handler.getSlot(2).hasStack()) {
			int textColor = 8453920;
			Text costText;
			if (!handler.getSlot(2).hasStack()) {
				costText = null;
			} else {
				costText = Text.translatable("container.repair.cost", levelCost);
				if (!handler.getSlot(2).canTakeItems(this.player)) {
					textColor = 16736352;
				}
			}
			
			if (costText != null) {
				int k = this.backgroundWidth - 8 - this.textRenderer.getWidth(costText) - 2;
				context.fill(k - 2, 67 + 24, this.backgroundWidth - 8, 79 + 24, 1325400064);
				context.drawText(textRenderer, costText, k, 93, textColor, true);
			}
		}
	}
	
	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		super.drawBackground(context, delta, mouseX, mouseY);
		
		// the text field backgrounds
		boolean hasStack = handler.getSlot(0).hasStack();
		context.drawTexture(TEXTURE, this.x + 59, this.y + 20, 0, this.backgroundHeight + (hasStack ? 0 : 16), 110, 16);
		context.drawTexture(TEXTURE, this.x + 42, this.y + 72, 0, this.backgroundHeight + (hasStack ? 32 : 48), 127, 16);
	}
	
	@Override
	public void renderForeground(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		this.nameField.render(drawContext, mouseX, mouseY, delta);
		this.loreField.render(drawContext, mouseX, mouseY, delta);
	}
	
	@Override
	protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {
		if ((this.handler.getSlot(0).hasStack() || this.handler.getSlot(1).hasStack()) && !this.handler.getSlot(this.handler.getResultSlotIndex()).hasStack()) {
			context.drawTexture(TEXTURE, x + 99, y + 45, this.backgroundWidth, 0, 28, 21);
		}
	}
	
	@Override
	public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
		// TODO: test & cleanup pigment code
		if (slotId == 0) {
			boolean stackEmpty = stack.isEmpty();
			
			this.nameField.setText(stack.isEmpty() ? "" : stack.getName().getString());
			if (!(this.handler.getSlot(1).getStack().getItem() instanceof PigmentItem)) {
				if (stack.getName() instanceof MutableText mutableText) {
					if (mutableText.getStyle().getColor() == null) {
						this.nameField.setEditableColor(-1);
					} else {
						this.nameField.setEditableColor(mutableText.getStyle().getColor().getRgb());
					}
				} else {
					this.nameField.setEditableColor(-1);
				}
			}
			
			this.nameField.setEditable(!stack.isEmpty());
			this.nameField.setFocusUnlocked(!stackEmpty);
			
			this.loreField.setText(stackEmpty ? "" : LoreHelper.getStringFromLoreTextArray(LoreHelper.getLoreList(stack)));
			this.loreField.setEditable(!stackEmpty);
			this.nameField.setFocusUnlocked(!stackEmpty);
			
			this.setFocused(this.nameField);
		}
		if (slotId == 1) {
			if (stack.getItem() instanceof PigmentItem pigmentItem) {
				this.nameField.setEditableColor(pigmentItem.getInkColor().getColorInt());
			} else {
				if (this.handler.getSlot(0).getStack().getName() instanceof MutableText mutableText) {
					if (mutableText.getStyle().getColor() == null) {
						this.nameField.setEditableColor(-1);
					} else {
						this.nameField.setEditableColor(mutableText.getStyle().getColor().getRgb());
					}
				} else {
					this.nameField.setEditableColor(-1);
				}
			}
		}
	}
	
}
