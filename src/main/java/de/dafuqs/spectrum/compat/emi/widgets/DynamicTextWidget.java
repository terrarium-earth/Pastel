package de.dafuqs.spectrum.compat.emi.widgets;

import dev.emi.emi.api.widget.*;
import dev.emi.emi.runtime.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

import java.util.function.*;

public class DynamicTextWidget extends TextWidget {
	
	private static final Minecraft CLIENT = Minecraft.getInstance();
	private final Function<Minecraft, Tuple<FormattedCharSequence, Integer>> textSupplier;
	
	public DynamicTextWidget(Function<Minecraft, Tuple<FormattedCharSequence, Integer>> textSupplier, int x, int y, boolean shadow) {
		super(textSupplier.apply(CLIENT).getA(), x, y, 0, shadow);
		this.textSupplier = textSupplier;
	}
	
	@Override
	public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
		EmiDrawContext context = EmiDrawContext.wrap(draw);
		context.push();
		int xOff = horizontalAlignment.offset(CLIENT.font.width(text));
		int yOff = verticalAlignment.offset(CLIENT.font.lineHeight);
		context.matrices().translate(xOff, yOff, 300);
		
		var pair = textSupplier.apply(CLIENT);
		
		if (shadow) {
			context.drawTextWithShadow(pair.getA(), x, y, pair.getB());
		} else {
			context.drawText(pair.getA(), x, y, pair.getB());
		}
		context.pop();
	}
}
