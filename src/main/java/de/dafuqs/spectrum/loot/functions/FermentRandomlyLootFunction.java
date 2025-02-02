package de.dafuqs.spectrum.loot.functions;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.TimeHelper;
import de.dafuqs.spectrum.loot.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import de.dafuqs.spectrum.recipe.titration_barrel.*;
import net.minecraft.item.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class FermentRandomlyLootFunction extends ConditionalLootFunction {
	
	public static final MapCodec<FermentRandomlyLootFunction> CODEC = RecordCodecBuilder.mapCodec(i -> addConditionsField(i).and(i.group(
			Codec.either(Identifier.CODEC, FermentationData.CODEC).fieldOf("fermentation").forGetter(c -> c.fermentation),
			LootNumberProviderTypes.CODEC.fieldOf("days_fermented").forGetter(c -> c.daysFermented),
			LootNumberProviderTypes.CODEC.fieldOf("thickness").forGetter(c -> c.thickness)
	)).apply(i, FermentRandomlyLootFunction::new));
	
	private final Either<Identifier, FermentationData> fermentation;
	private final LootNumberProvider daysFermented;
	private final LootNumberProvider thickness;
	
	public FermentRandomlyLootFunction(List<LootCondition> conditions, Either<Identifier, FermentationData> fermentation, LootNumberProvider daysFermented, LootNumberProvider thickness) {
		super(conditions);
		this.fermentation = fermentation;
		this.daysFermented = daysFermented;
		this.thickness = thickness;
	}
	
	public FermentRandomlyLootFunction(List<LootCondition> conditions, @NotNull Identifier fermentationRecipeIdentifier, LootNumberProvider daysFermented, LootNumberProvider thickness) {
		this(conditions, Either.left(fermentationRecipeIdentifier), daysFermented, thickness);
	}
	
	public FermentRandomlyLootFunction(List<LootCondition> conditions, @NotNull FermentationData fermentationData, LootNumberProvider daysFermented, LootNumberProvider thickness) {
		this(conditions, Either.right(fermentationData), daysFermented, thickness);
	}
	
	@Override
	public LootFunctionType<? extends ConditionalLootFunction> getType() {
		return SpectrumLootFunctionTypes.FERMENT_RANDOMLY;
	}
	
	@Override
	public ItemStack process(ItemStack stack, LootContext context) {
		FermentationData fermentationData = this.fermentation.map(
				id -> {
					var recipe = context.getWorld().getRecipeManager().get(id);
					if (recipe.isPresent() && recipe.get().value() instanceof TitrationBarrelRecipe titrationBarrelRecipe) {
						return titrationBarrelRecipe.getFermentationData();
					} else {
						SpectrumCommon.logError("A 'spectrum:ferment_randomly' loot function has set an invalid 'fermentation_recipe_id': " + id + " It has to match an existing Titration Barrel recipe.");
						return null;
					}
				},
				data -> this.fermentation.right().orElse(null)
		);
		if (fermentationData != null) {
			var origin = context.get(LootContextParameters.ORIGIN);
			if (origin != null) {
				BlockPos pos = BlockPos.ofFloored(origin);
				Biome biome = context.getWorld().getBiome(pos).value();
				float downfall = ((BiomeAccessor) (Object) biome).getWeather().downfall();
				return TitrationBarrelRecipe.getFermentedStack(fermentationData, this.thickness.nextInt(context), TimeHelper.secondsFromMinecraftDays(this.daysFermented.nextInt(context)), downfall, stack);
			} else {
				SpectrumCommon.logError("A 'spectrum:ferment_randomly' loot function does not have access to 'origin'.");
			}
		}
		return stack;
	}
	
	public static ConditionalLootFunction.Builder<?> builder(FermentationData fermentationData, LootNumberProvider daysFermented, LootNumberProvider thickness) {
		return builder((conditions) -> new FermentRandomlyLootFunction(conditions, fermentationData, daysFermented, thickness));
	}
	
	public static ConditionalLootFunction.Builder<?> builder(Identifier fermentationRecipeIdentifier, LootNumberProvider daysFermented, LootNumberProvider thickness) {
		return builder((conditions) -> new FermentRandomlyLootFunction(conditions, fermentationRecipeIdentifier, daysFermented, thickness));
	}
	
}
