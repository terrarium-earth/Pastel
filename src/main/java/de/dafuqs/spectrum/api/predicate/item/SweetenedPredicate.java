package de.dafuqs.spectrum.api.predicate.item;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.predicate.item.*;

public record SweetenedPredicate(boolean sweetened) implements ComponentSubPredicate<JadeWineComponent> {
	
	public static final Codec<SweetenedPredicate> CODEC = Codec.BOOL.xmap(SweetenedPredicate::new, SweetenedPredicate::sweetened);
	
	@Override
	public ComponentType<JadeWineComponent> getComponentType() {
		return SpectrumDataComponentTypes.JADE_WINE;
	}
	
	@Override
	public boolean test(ItemStack stack, JadeWineComponent component) {
		return component.sweetened() == sweetened;
	}
	
}
