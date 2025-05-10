package de.dafuqs.spectrum.compat.emi.widgets;

import dev.emi.emi.api.widget.*;
import dev.emi.emi.runtime.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.function.*;

public class DynamicTextWidget extends TextWidget {
	
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	private final Function<MinecraftClient, Pair<OrderedText, Integer>> textSupplier;
	
	public DynamicTextWidget(Function<MinecraftClient, Pair<OrderedText, Integer>> textSupplier, int x, int y, boolean shadow) {
		super(textSupplier.apply(CLIENT).getLeft(), x, y, 0, shadow);
		this.textSupplier = textSupplier;
	}
	
	@Override
	public void render(DrawContext draw, int mouseX, int mouseY, float delta) {
		EmiDrawContext context = EmiDrawContext.wrap(draw);
		context.push();
		int xOff = horizontalAlignment.offset(CLIENT.textRenderer.getWidth(text));
		int yOff = verticalAlignment.offset(CLIENT.textRenderer.fontHeight);
		context.matrices().translate(xOff, yOff, 300);
		
		var pair = textSupplier.apply(CLIENT);
		
		if (shadow) {
			context.drawTextWithShadow(pair.getLeft(), x, y, pair.getRight());
		} else {
			context.drawText(pair.getLeft(), x, y, pair.getRight());
		}
		context.pop();
	}
}
