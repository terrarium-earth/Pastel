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

public class SpawnerSpawnDelayChangeRecipe extends SpawnerChangeRecipe {

    protected static final int DEFAULT_MIN_DELAY = 200;

    protected static final int DEFAULT_MAX_DELAY = 800;

    protected static final int MIN_MIN_DELAY = 20;

    protected static final int MIN_MAX_DELAY = 40;

    protected static final float EXPONENT = 0.98F;

    public SpawnerSpawnDelayChangeRecipe() {
        super(IngredientStack.ofItems(PastelItems.MIDNIGHT_CHIP.get(), 4));
    }

    @Override
    public boolean canCraftWithBlockEntityTag(
        CustomData spawnerBlockEntityNbt,
        ItemStack leftBowlStack,
        ItemStack rightBowlStack
    ) {
        if (spawnerBlockEntityNbt == null) {
            return true;
        }
        CompoundTag nbt = spawnerBlockEntityNbt.getUnsafe();
        return (!nbt.contains("MinSpawnDelay") || nbt.getShort("MinSpawnDelay") > MIN_MIN_DELAY) && (!nbt
            .contains("MaxSpawnDelay") || nbt.getShort("MaxSpawnDelay") > MIN_MAX_DELAY);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.SPIRIT_INSTILLER_SPAWNER_SPAWN_DELAY_CHANGE;
    }

    @Override
    public Component getOutputLoreText() {
        return Component.translatable("recipe.pastel.spawner.lore.decreased_spawn_delay");
    }

    @Override
    public CompoundTag getSpawnerResultNbt(
        CompoundTag spawnerBlockEntityNbt,
        ItemStack firstBowlStack,
        ItemStack secondBowlStack
    ) {
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

        // 800 => 700 => 614 => 540 => 476 => 421 => 373 => 331 => ... => MIN_DELAY
        short minSpawnDelay = DEFAULT_MIN_DELAY;
        if (spawnerBlockEntityNbt.contains("MinSpawnDelay", Tag.TAG_SHORT)) {
            minSpawnDelay = spawnerBlockEntityNbt.getShort("MinSpawnDelay");
        }
        short maxSpawnDelay = DEFAULT_MAX_DELAY;
        if (spawnerBlockEntityNbt.contains("MaxSpawnDelay", Tag.TAG_SHORT)) {
            maxSpawnDelay = spawnerBlockEntityNbt.getShort("MaxSpawnDelay");
        }

        short newMinSpawnDelay = (short) Math.pow(minSpawnDelay, EXPONENT);
        if (newMinSpawnDelay == minSpawnDelay) {
            newMinSpawnDelay = (short) (minSpawnDelay - 1);
        }

        short newMaxSpawnDelay = (short) Math.pow(maxSpawnDelay, EXPONENT);
        if (newMaxSpawnDelay == maxSpawnDelay) {
            newMaxSpawnDelay = (short) (maxSpawnDelay - 1);
        }

        spawnerBlockEntityNbt.putShort("MinSpawnDelay", (short) Math.max(MIN_MIN_DELAY, newMinSpawnDelay));
        spawnerBlockEntityNbt.putShort("MaxSpawnDelay", (short) Math.max(MIN_MAX_DELAY, newMaxSpawnDelay));

        return spawnerBlockEntityNbt;
    }

}
