package earth.terrarium.pastel.loot.functions;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.interaction.TimeHelper;
import earth.terrarium.pastel.loot.PastelLootFunctionTypes;
import earth.terrarium.pastel.mixin.accessors.BiomeAccessor;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FermentRandomlyLootFunction extends LootItemConditionalFunction {
	
	public static final MapCodec<FermentRandomlyLootFunction> CODEC = RecordCodecBuilder.mapCodec(i -> commonFields(i).and(i.group(
			Codec.either(ResourceLocation.CODEC, FermentationData.CODEC).fieldOf("fermentation").forGetter(c -> c.fermentation),
			NumberProviders.CODEC.fieldOf("days_fermented").forGetter(c -> c.daysFermented),
			NumberProviders.CODEC.fieldOf("thickness").forGetter(c -> c.thickness)
	)).apply(i, FermentRandomlyLootFunction::new));
	
	private final Either<ResourceLocation, FermentationData> fermentation;
	private final NumberProvider daysFermented;
	private final NumberProvider thickness;
	
	public FermentRandomlyLootFunction(List<LootItemCondition> conditions, Either<ResourceLocation, FermentationData> fermentation, NumberProvider daysFermented, NumberProvider thickness) {
		super(conditions);
		this.fermentation = fermentation;
		this.daysFermented = daysFermented;
		this.thickness = thickness;
	}
	
	public FermentRandomlyLootFunction(List<LootItemCondition> conditions, @NotNull ResourceLocation fermentationRecipeIdentifier, NumberProvider daysFermented, NumberProvider thickness) {
		this(conditions, Either.left(fermentationRecipeIdentifier), daysFermented, thickness);
	}
	
	public FermentRandomlyLootFunction(List<LootItemCondition> conditions, @NotNull FermentationData fermentationData, NumberProvider daysFermented, NumberProvider thickness) {
		this(conditions, Either.right(fermentationData), daysFermented, thickness);
	}
	
	@Override
	public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
		return PastelLootFunctionTypes.FERMENT_RANDOMLY;
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		FermentationData fermentationData = this.fermentation.map(
				id -> {
					var recipe = context.getLevel().getRecipeManager().byKey(id);
					if (recipe.isPresent() && recipe.get().value() instanceof TitrationBarrelRecipe titrationBarrelRecipe) {
						return titrationBarrelRecipe.getFermentationData();
					} else {
						PastelCommon.logError("A 'pastel:ferment_randomly' loot function has set an invalid 'fermentation_recipe_id': " + id + " It has to match an existing Titration Barrel recipe.");
						return null;
					}
				},
				data -> this.fermentation.right().orElse(null)
		);
		if (fermentationData != null) {
			var origin = context.getParamOrNull(LootContextParams.ORIGIN);
			if (origin != null) {
				BlockPos pos = BlockPos.containing(origin);
				Biome biome = context.getLevel().getBiome(pos).value();
				float downfall = ((BiomeAccessor) (Object) biome).getClimateSettings().downfall();
				return TitrationBarrelRecipe.getFermentedStack(fermentationData, this.thickness.getInt(context), TimeHelper.secondsFromMinecraftDays(this.daysFermented.getInt(context)), downfall, stack);
			} else {
				PastelCommon.logError("A 'pastel:ferment_randomly' loot function does not have access to 'origin'.");
			}
		}
		return stack;
	}
	
	public static LootItemConditionalFunction.Builder<?> builder(FermentationData fermentationData, NumberProvider daysFermented, NumberProvider thickness) {
		return simpleBuilder((conditions) -> new FermentRandomlyLootFunction(conditions, fermentationData, daysFermented, thickness));
	}
	
	public static LootItemConditionalFunction.Builder<?> builder(ResourceLocation fermentationRecipeIdentifier, NumberProvider daysFermented, NumberProvider thickness) {
		return simpleBuilder((conditions) -> new FermentRandomlyLootFunction(conditions, fermentationRecipeIdentifier, daysFermented, thickness));
	}
	
}
