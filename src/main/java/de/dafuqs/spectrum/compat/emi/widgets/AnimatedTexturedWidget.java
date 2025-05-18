package de.dafuqs.spectrum.compat.emi.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetTooltipHolder;
import dev.emi.emi.runtime.EmiDrawContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.function.BiFunction;

public class AnimatedTexturedWidget extends Widget implements WidgetTooltipHolder<AnimatedTexturedWidget> {
	
	protected final ResourceLocation texture;
	protected final int x, y;
	protected final int textureWidth, textureHeight;
	
	private final int animationCount;
	private final double animationDurationMS;
	
	private BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier = (mouseX, mouseY) -> List.of();
	
	public AnimatedTexturedWidget(ResourceLocation texture, int x, int y, int textureWidth, int textureHeight, int animationDurationMS) {
		super();
		
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		
		this.animationDurationMS = animationDurationMS;
		this.animationCount = textureHeight / textureWidth;
	}
	
	@Override
	public Bounds getBounds() {
		return new Bounds(x, y, textureWidth, textureWidth);
	}
	
	@Override
	public AnimatedTexturedWidget tooltip(BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier) {
		this.tooltipSupplier = tooltipSupplier;
		return this;
	}
	
	@Override
	public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
		return tooltipSupplier.apply(mouseX, mouseY);
	}
	
	@Override
	public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
		EmiDrawContext context = EmiDrawContext.wrap(draw);
		
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		
		if (animationDurationMS > 0) {
			int index = Mth.ceil((System.currentTimeMillis() / (animationDurationMS / animationCount) % animationCount));
			context.drawTexture(texture, x, y, 0, textureWidth, index * textureWidth, textureWidth, textureWidth, textureWidth, textureHeight);
		} else {
			context.drawTexture(texture, x, y, 0, textureWidth, textureWidth, textureWidth, textureWidth, textureWidth, textureHeight);
		}
	}
	
}
