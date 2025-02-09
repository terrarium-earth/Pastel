package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.*;
import net.minecraft.data.client.*;

import java.util.*;

public class SpectrumModels {
	
	public static final Model MULTILAYER_LIGHT = new Model(Optional.of(SpectrumCommon.locate("templates/multilayer_light")), Optional.empty(), TextureKey.TOP, TextureKey.SIDE, TextureKey.INSIDE);
	public static final Model MOONSTONE_CHISELED = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled")), Optional.empty(), TextureKey.SIDE, SpectrumTextureKeys.LINE);
	public static final Model MOONSTONE_CHISELED_DOWN = new Model(Optional.of(SpectrumCommon.locate("templates/moonstone_chiseled_down")), Optional.empty(), TextureKey.SIDE, SpectrumTextureKeys.LINE);
	
}
