package de.dafuqs.spectrum.inventories.widgets;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.blocks.energy.ColorPickerBlockEntity;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static de.dafuqs.spectrum.helpers.RenderHelper.fillQuad;

@Environment(EnvType.CLIENT)
public class ColorSelectionWidget extends AbstractWidget {
	
	protected final ColorPickerBlockEntity colorPicker;
	
	@Nullable
	private Consumer<Optional<Holder<InkColor>>> changedListener;
	protected final Screen screen;
	
	final List<Tuple<InkColor, Boolean>> usableColors = new ArrayList<>(); // stores if a certain color should be displayed
	
	final int selectedDotX;
	final int selectedDotY;
	
	public ColorSelectionWidget(int x, int y, int selectedDotX, int selectedDotY, Screen screen, ColorPickerBlockEntity colorPicker) {
		this(x, y, selectedDotX, selectedDotY, screen, colorPicker, InkColors.BUILTIN_COLORS);
	}
	
	public ColorSelectionWidget(int x, int y, int selectedDotX, int selectedDotY, Screen screen, ColorPickerBlockEntity colorPicker, List<InkColor> availableColors) {
		super(x, y, 56, 14, Component.literal(""));
		this.colorPicker = colorPicker;
		this.selectedDotX = selectedDotX;
		this.selectedDotY = selectedDotY;
		this.screen = screen;
		
		for (InkColor inkColor : availableColors) {
			usableColors.add(new Tuple<>(inkColor, AdvancementHelper.hasAdvancementClient(inkColor.getRequiredAdvancement())));
		}
	}
	
	public void setChangedListener(@Nullable Consumer<Optional<Holder<InkColor>>> changedListener) {
		this.changedListener = changedListener;
	}
	
	private void onChanged(Optional<Holder<InkColor>> newColor) {
		if (this.changedListener != null) {
			this.changedListener.accept(newColor);
		}
	}
	
	@Override
	protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		// draw selection icons
		int i = -1;
		int currentX = this.getX() + 1;
		int currentY = this.getY() + 1;
		for (var color : usableColors) {
			if (color.getB()) {
				fillQuad(context.pose(), currentX, currentY, 5, 5, color.getA().getColorVec());
			}
			i = i + 1;
			currentX = currentX + 7;
			if (i == 7) {
				currentY = currentY + 7;
				currentX = this.getX() + 1;
			}
		}
		
		// draw currently selected icon
		this.colorPicker.getSelectedColor().ifPresent(inkColor -> fillQuad(context.pose(), selectedDotX, selectedDotY, 4, 4, inkColor.value().getColorVec()));
	}
	
	@Override
	@SuppressWarnings("DataFlowIssue")
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
		Minecraft client = Minecraft.getInstance();
		
		if (isUnselection(mouseX, mouseY)) {
			client.player.playSound(SpectrumSoundEvents.BUTTON_CLICK, 1.0F, 1.0F);
			onChanged(Optional.empty());
		}
		
		boolean colorSelectionClicked = mouseX >= (double) this.getX() && mouseX < (double) (this.getX() + this.width) && mouseY >= (double) this.getY() && mouseY < (double) (this.getY() + this.height);
		if (colorSelectionClicked && button == 0) {
			int xOffset = Mth.floor(mouseX) - this.getX();
			int yOffset = Mth.floor(mouseY) - this.getY();
			
			int horizontalColorOffset = xOffset / 7;
			int verticalColorOffset = yOffset / 7;
			int newColorIndex = horizontalColorOffset + verticalColorOffset * 8;
			
			var clickedColor = usableColors.get(newColorIndex);
			if (clickedColor.getB()) {
				client.player.playSound(SpectrumSoundEvents.BUTTON_CLICK, 1.0F, 1.0F);
				onChanged(Optional.of(SpectrumRegistries.INK_COLOR.wrapAsHolder(clickedColor.getA())));
			} else {
				client.player.playSound(SpectrumSoundEvents.USE_FAIL, 1.0F, 1.0F);
				onChanged(Optional.empty());
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput builder) {
		builder.add(NarratedElementType.TITLE, Component.translatable("spectrum.narration.color_selection", this.colorPicker.getSelectedColor()));
	}
	
	private boolean isUnselection(double mouseX, double mouseY) {
		return mouseX >= (double) selectedDotX && mouseX < (double) (selectedDotX + 4) && mouseY >= (double) selectedDotY && mouseY < (double) (selectedDotY + 4);
	}
	
	public boolean isMouseOver(double mouseX, double mouseY) {
		return super.isMouseOver(mouseX, mouseY) || (this.active && this.visible && isUnselection(mouseX, mouseY));
	}
	
	public void drawMouseoverTooltip(GuiGraphics drawContext, int mouseX, int mouseY) {
		Minecraft client = Minecraft.getInstance();
		boolean overUnselection = mouseX >= (double) selectedDotX && mouseX < (double) (selectedDotX + 4) && mouseY >= (double) selectedDotY && mouseY < (double) (selectedDotY + 4);
		if (overUnselection) {
			drawContext.renderTooltip(client.font, List.of(Component.translatable("spectrum.tooltip.ink_powered.unselect_color")), Optional.empty(), getX(), getY());
		} else {
			int xOffset = Mth.floor(mouseX) - this.getX();
			int yOffset = Mth.floor(mouseY) - this.getY();
			
			int horizontalColorOffset = xOffset / 7;
			int verticalColorOffset = yOffset / 7;
			int newColorIndex = horizontalColorOffset + verticalColorOffset * 8;
			
			var hoveredColor = usableColors.get(newColorIndex);
			if (hoveredColor.getB()) {
				drawContext.renderTooltip(client.font, List.of(hoveredColor.getA().getName()), Optional.empty(), getX(), getY());
			} else {
				drawContext.renderTooltip(client.font, List.of(Component.translatable("spectrum.tooltip.ink_powered.unselect_color")), Optional.empty(), getX(), getY());
			}
		}
	}
}
