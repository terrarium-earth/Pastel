package de.dafuqs.spectrum.loot.functions;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.loot.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.function.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.math.random.Random;

import java.util.*;

public class DyeRandomlyLootFunction extends ConditionalLootFunction {
	
	public static final MapCodec<DyeRandomlyLootFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> addConditionsField(instance).and(instance.group(
			ColorHelper.CODEC.listOf().fieldOf("colors").forGetter((function) -> function.colors),
			Codec.BOOL.optionalFieldOf("show_in_tooltip", false).forGetter((function) -> function.showInTooltip))
	).apply(instance, DyeRandomlyLootFunction::new));
	
	final List<Integer> colors;
	final boolean showInTooltip;
	
	DyeRandomlyLootFunction(List<LootCondition> conditions, List<Integer> colors, boolean showInTooltip) {
		super(conditions);
		this.colors = colors;
		this.showInTooltip = showInTooltip;
	}
	
	@Override
	public LootFunctionType<DyeRandomlyLootFunction> getType() {
		return SpectrumLootFunctionTypes.DYE_RANDOMLY;
	}
	
	@Override
	public ItemStack process(ItemStack stack, LootContext context) {
		stack.get(DataComponentTypes.DYED_COLOR);
		if (stack.isIn(ItemTags.DYEABLE)) {
			Random random = context.getRandom();
			int color = this.colors.isEmpty() ? ColorHelper.getRandomColor(random.nextInt()) : this.colors.get(random.nextInt(this.colors.size()));
			
			DyedColorComponent component = new DyedColorComponent(color, showInTooltip);
			stack.set(DataComponentTypes.DYED_COLOR, component);
		}
		
		return stack;
	}
	
}
