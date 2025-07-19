package earth.terrarium.pastel.compat.REI.plugins;

import me.shedaniel.clothconfig2.ClothConfigInitializer;
import me.shedaniel.clothconfig2.api.ScissorsHandler;
import me.shedaniel.clothconfig2.api.scroll.ScrollingContainer;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.util.FormattedCharSequence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Mostly a copy of REIs ScrollableTextWidget
 * But since that is private here a public implementation
 * <a href="https://github.com/shedaniel/RoughlyEnoughItems/blob/7.x-1.18/default-plugin/src/main/java/me/shedaniel/rei/plugin/client/categories/DefaultInformationCategory.java">REI</a>
 */
public class ScrollableTextWidget extends WidgetWithBounds {
	private final Rectangle bounds;
	private final List<FormattedCharSequence> texts;
	
	private final ScrollingContainer scrolling = new ScrollingContainer() {
		@Override
		public me.shedaniel.math.Rectangle getBounds() {
			Rectangle bounds = ScrollableTextWidget.this.getBounds();
			return new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
		}
		
		@Override
		public int getMaxScrollHeight() {
			int i = 2;
			for (FormattedCharSequence entry : texts) {
				i += entry == null ? 4 : font.lineHeight;
			}
			return i;
		}
	};
	
	public ScrollableTextWidget(Rectangle bounds, List<FormattedCharSequence> texts) {
		this.bounds = Objects.requireNonNull(bounds);
		this.texts = texts;
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		if (containsMouse(mouseX, mouseY)) {
			scrolling.offset(ClothConfigInitializer.getScrollStep() * -verticalAmount, true);
			return true;
		}
		return false;
	}
	
	@Override
	public List<? extends GuiEventListener> children() {
		return Collections.emptyList();
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (scrolling.updateDraggingState(mouseX, mouseY, button))
			return true;
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (scrolling.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
			return true;
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}
	
	@Override
	public Rectangle getBounds() {
		return bounds;
	}
	
	@Override
	public void render(GuiGraphics drawContext, int mouseX, int mouseY, float delta) {
		scrolling.updatePosition(delta);
		Rectangle innerBounds = scrolling.getScissorBounds();
		ScissorsHandler.INSTANCE.scissor(innerBounds);
		int currentY = -scrolling.scrollAmountInt() + innerBounds.y;
		for (FormattedCharSequence text : texts) {
			if (text != null && currentY + font.lineHeight >= innerBounds.y && currentY <= innerBounds.getMaxY()) {

				drawContext.drawString(font, text, innerBounds.x + 2, currentY + 2, REIRuntime.getInstance().isDarkThemeEnabled() ? 0xFFBBBBBB : 0xFF090909, false);
			}
			currentY += text == null ? 4 : font.lineHeight;
		}
		ScissorsHandler.INSTANCE.removeLastScissor();
		ScissorsHandler.INSTANCE.scissor(scrolling.getBounds());
		scrolling.renderScrollBar(drawContext, 0xff000000, 1, REIRuntime.getInstance().isDarkThemeEnabled() ? 0.8f : 1f);
		ScissorsHandler.INSTANCE.removeLastScissor();
	}
	
}