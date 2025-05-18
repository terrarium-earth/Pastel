package de.dafuqs.spectrum.api.predicate.item;

import com.mojang.serialization.Codec;
import de.dafuqs.spectrum.components.JadeWineComponent;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public record SweetenedPredicate(boolean sweetened) implements SingleComponentItemPredicate<JadeWineComponent> {
	
	public static final Codec<SweetenedPredicate> CODEC = Codec.BOOL.xmap(SweetenedPredicate::new, SweetenedPredicate::sweetened);
	
	@Override
	public DataComponentType<JadeWineComponent> componentType() {
		return SpectrumDataComponentTypes.JADE_WINE;
	}
	
	@Override
	public boolean matches(ItemStack stack, JadeWineComponent component) {
		return component.sweetened() == sweetened;
	}
	
}
