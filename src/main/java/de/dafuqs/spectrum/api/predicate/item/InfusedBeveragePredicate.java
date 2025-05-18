package de.dafuqs.spectrum.api.predicate.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.components.InfusedBeverageComponent;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record InfusedBeveragePredicate(Optional<String> variant) implements SingleComponentItemPredicate<InfusedBeverageComponent> {
	
	public static final Codec<InfusedBeveragePredicate> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.optionalFieldOf("variant").forGetter(c -> c.variant)
	).apply(i, InfusedBeveragePredicate::new));
	
	@Override
	public DataComponentType<InfusedBeverageComponent> componentType() {
		return SpectrumDataComponentTypes.INFUSED_BEVERAGE;
	}
	
	@Override
	public boolean matches(ItemStack stack, InfusedBeverageComponent component) {
		return variant.isEmpty() || component.variant().equals(variant.get());
	}
}
