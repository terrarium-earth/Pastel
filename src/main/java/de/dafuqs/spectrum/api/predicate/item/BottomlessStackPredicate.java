package de.dafuqs.spectrum.api.predicate.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.blocks.bottomless_bundle.BottomlessBundleItem;
import de.dafuqs.spectrum.progression.advancement.LongRange;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public record BottomlessStackPredicate(ItemPredicate template, LongRange count) implements SingleComponentItemPredicate<BottomlessBundleItem.BottomlessStack> {
	
	public static Codec<BottomlessStackPredicate> CODEC = RecordCodecBuilder.create(i -> i.group(
			ItemPredicate.CODEC.optionalFieldOf("variant", ItemPredicate.Builder.item().build()).forGetter(c -> c.template),
			LongRange.CODEC.optionalFieldOf("count", LongRange.ANY).forGetter(c -> c.count)
	).apply(i, BottomlessStackPredicate::new));
	
	@Override
	public DataComponentType<BottomlessBundleItem.BottomlessStack> componentType() {
		return SpectrumDataComponentTypes.BOTTOMLESS_STACK;
	}
	
	@Override
	public boolean matches(ItemStack stack, BottomlessBundleItem.BottomlessStack component) {
		return template.test(component.variant().toStack()) && count.test(component.count());
	}
	
}
