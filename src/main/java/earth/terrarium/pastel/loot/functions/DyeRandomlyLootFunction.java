package earth.terrarium.pastel.loot.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.ColorHelper;
import earth.terrarium.pastel.loot.PastelLootFunctionTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class DyeRandomlyLootFunction extends LootItemConditionalFunction {
	
	public static final MapCodec<DyeRandomlyLootFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> commonFields(instance).and(instance.group(
			ColorHelper.CODEC.listOf().fieldOf("colors").forGetter((function) -> function.colors),
			Codec.BOOL.optionalFieldOf("show_in_tooltip", false).forGetter((function) -> function.showInTooltip))
	).apply(instance, DyeRandomlyLootFunction::new));
	
	final List<Integer> colors;
	final boolean showInTooltip;
	
	DyeRandomlyLootFunction(List<LootItemCondition> conditions, List<Integer> colors, boolean showInTooltip) {
		super(conditions);
		this.colors = colors;
		this.showInTooltip = showInTooltip;
	}
	
	@Override
	public LootItemFunctionType<DyeRandomlyLootFunction> getType() {
		return PastelLootFunctionTypes.DYE_RANDOMLY;
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		stack.get(DataComponents.DYED_COLOR);
		if (stack.is(ItemTags.DYEABLE)) {
			RandomSource random = context.getRandom();
			int color = this.colors.isEmpty() ? ColorHelper.getRandomColor(random.nextInt()) : this.colors.get(random.nextInt(this.colors.size()));
			
			DyedItemColor component = new DyedItemColor(color, showInTooltip);
			stack.set(DataComponents.DYED_COLOR, component);
		}
		
		return stack;
	}
	
}
