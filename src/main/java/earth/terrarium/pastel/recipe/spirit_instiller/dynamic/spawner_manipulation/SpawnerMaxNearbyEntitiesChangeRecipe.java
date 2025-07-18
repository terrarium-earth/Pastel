package earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation;


import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SpawnerMaxNearbyEntitiesChangeRecipe extends SpawnerChangeRecipe {
    protected static final int DEFAULT_MAX_ENTITIES = 6;
    protected static final int MAX_MAX_ENTITIES = 40;

    public SpawnerMaxNearbyEntitiesChangeRecipe() {
        super(IngredientStack.ofItems(PastelItems.MERMAIDS_GEM.get(), 4));
    }

    @Override
    public boolean canCraftWithBlockEntityTag(
        CustomData spawnerBlockEntityNbt, ItemStack leftBowlStack, ItemStack rightBowlStack) {
        if (spawnerBlockEntityNbt == null) {
            return true;
        }
        if (spawnerBlockEntityNbt.contains("MaxNearbyEntities")) {
            return spawnerBlockEntityNbt.copyTag()
                                        .getShort("MaxNearbyEntities") < MAX_MAX_ENTITIES;
        }
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.SPIRIT_INSTILLER_SPAWNER_MAX_NEARBY_ENTITIES_CHANGE;
    }

    @Override
    public Component getOutputLoreText() {
        return Component.translatable("recipe.pastel.spawner.lore.increased_max_nearby_entities");
    }


    @Override
    public CompoundTag getSpawnerResultNbt(
        CompoundTag spawnerBlockEntityNbt, ItemStack firstBowlStack, ItemStack secondBowlStack) {
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

        short maxNearbyEntities = DEFAULT_MAX_ENTITIES;
        if (spawnerBlockEntityNbt.contains("MaxNearbyEntities", Tag.TAG_SHORT)) {
            maxNearbyEntities = spawnerBlockEntityNbt.getShort("MaxNearbyEntities");
        }
        spawnerBlockEntityNbt.putShort("MaxNearbyEntities", (short) Math.min(MAX_MAX_ENTITIES, maxNearbyEntities + 1));

        return spawnerBlockEntityNbt;
    }

}
