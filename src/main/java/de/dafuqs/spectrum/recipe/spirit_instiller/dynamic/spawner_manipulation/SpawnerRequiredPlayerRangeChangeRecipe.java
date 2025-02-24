package de.dafuqs.spectrum.recipe.spirit_instiller.dynamic.spawner_manipulation;


import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.recipe.*;
import net.minecraft.text.*;

public class SpawnerRequiredPlayerRangeChangeRecipe extends SpawnerChangeRecipe {
	protected static final int DEFAULT_DETECTION_RANGE = 16;
	protected static final int MAX_DETECTION_RANGE = 64;
	
	public SpawnerRequiredPlayerRangeChangeRecipe() {
		super(IngredientStack.ofItems(4, SpectrumItems.STRATINE_GEM));
	}
	
	@Override
	public boolean canCraftWithBlockEntityTag(NbtComponent spawnerBlockEntityNbt, ItemStack leftBowlStack, ItemStack rightBowlStack) {
		if (spawnerBlockEntityNbt == null) {
			return true;
		}
		if (spawnerBlockEntityNbt.contains("RequiredPlayerRange")) {
			return spawnerBlockEntityNbt.copyNbt().getShort("RequiredPlayerRange") < MAX_DETECTION_RANGE;
		}
		return true;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SPIRIT_INSTILLER_SPAWNER_SPAWNER_PLAYER_RANGE_CHANGE;
	}
	
	@Override
	public Text getOutputLoreText() {
		return Text.translatable("recipe.spectrum.spawner.lore.increased_required_player_range");
	}
	
	@Override
	public NbtCompound getSpawnerResultNbt(NbtCompound spawnerBlockEntityNbt, ItemStack firstBowlStack, ItemStack secondBowlStack) {
		// Default spawner tag:
		/* BlockEntityTag: {
			MaxNearbyEntities: 6s,
			RequiredPlayerRange: 16s,
			SpawnCount: 4s,
			SpawnData: {entity: {id: "minecraft:xxx"}},
			MaxSpawnDelay: 800s,
			SpawnRange: 4s,
			MinSpawnDelay: 200s,
			SpawnPotentials: []
		   }
		 */
		
		short requiredPlayerRange = DEFAULT_DETECTION_RANGE;
		if (spawnerBlockEntityNbt.contains("RequiredPlayerRange", NbtElement.SHORT_TYPE)) {
			requiredPlayerRange = spawnerBlockEntityNbt.getShort("RequiredPlayerRange");
		}
		
		short newRequiredPlayerRange = (short) Math.pow(requiredPlayerRange, 1.02);
		if (newRequiredPlayerRange == requiredPlayerRange) {
			newRequiredPlayerRange = (short) (requiredPlayerRange + 1);
		}
		
		spawnerBlockEntityNbt.putShort("RequiredPlayerRange", (short) Math.min(MAX_DETECTION_RANGE, newRequiredPlayerRange));
		
		return spawnerBlockEntityNbt;
	}
	
}
