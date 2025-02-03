package de.dafuqs.spectrum.api.predicate.item;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.predicate.item.*;

import java.util.*;

public record InfusedBeveragePredicate(Optional<String> variant) implements ComponentSubPredicate<InfusedBeverageComponent> {
	
	public static final Codec<InfusedBeveragePredicate> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.optionalFieldOf("variant").forGetter(c -> c.variant)
	).apply(i, InfusedBeveragePredicate::new));
	
	@Override
	public ComponentType<InfusedBeverageComponent> getComponentType() {
		return SpectrumDataComponentTypes.INFUSED_BEVERAGE;
	}
	
	@Override
	public boolean test(ItemStack stack, InfusedBeverageComponent component) {
		return variant.isEmpty() || component.variant().equals(variant.get());
	}
}
