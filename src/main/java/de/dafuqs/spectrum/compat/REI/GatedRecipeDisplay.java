package de.dafuqs.spectrum.compat.REI;

import me.shedaniel.rei.api.common.display.Display;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public interface GatedRecipeDisplay extends Display {
	
	boolean isUnlocked();
	
	boolean isSecret();
	
	@Nullable
	Component getSecretHintText();
	
}
