package earth.terrarium.pastel.api.predicate.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleItem;
import earth.terrarium.pastel.progression.advancement.LongRange;
import earth.terrarium.pastel.registries.SpectrumDataComponentTypes;
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
		return template.test(component.variant()) && count.test(component.count());
	}
	
}
