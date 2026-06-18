package earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlockEntity;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import earth.terrarium.pastel.recipe.InstanceRecipeInput;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;

public abstract class SpawnerChangeRecipe extends SpiritInstillerRecipe {

    public SpawnerChangeRecipe(
        IngredientStack ingredient,
        IngredientStack ingredient2,
        Optional<ResourceLocation> requiredAdvancementIdentifier
    ) {
        super(
            "spawner_manipulation",
            false,
            requiredAdvancementIdentifier,
            IngredientStack.ofItems(Items.SPAWNER),
            ingredient,
            ingredient2,
            Items.SPAWNER.getDefaultInstance(),
            200,
            0,
            true
        );
    }

    public SpawnerChangeRecipe(IngredientStack ingredient) {
        super(
            "spawner_manipulation",
            false,
            Optional.of(PastelAdvancements.Milestones.UNLOCK_SPAWNER_MANIPULATION),
            IngredientStack.ofItems(Items.SPAWNER),
            ingredient,
            IngredientStack.ofItems(PastelItems.VEGETAL.get(), 4),
            Items.SPAWNER.getDefaultInstance(),
            200,
            0,
            true
        );
    }

    @Override
    public ItemStack assemble(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, HolderLookup.Provider drm) {
        SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
        ItemStack resultStack = ItemStack.EMPTY;
        var world = spiritInstillerBlockEntity.getLevel();
        if (world == null) return ItemStack.EMPTY;
        BlockEntity leftBowlBlockEntity = world
            .getBlockEntity(
                SpiritInstillerBlockEntity.getItemBowlPos(spiritInstillerBlockEntity, false)
            );
        BlockEntity rightBowlBlockEntity = world
            .getBlockEntity(
                SpiritInstillerBlockEntity.getItemBowlPos(spiritInstillerBlockEntity, true)
            );
        if (leftBowlBlockEntity instanceof ItemBowlBlockEntity leftBowl && rightBowlBlockEntity instanceof ItemBowlBlockEntity rightBowl) {
            BlockPos pos = spiritInstillerBlockEntity.getBlockPos();

            ItemStack firstBowlStack = leftBowl.getItem(0);
            ItemStack secondBowlStack = rightBowl.getItem(0);
            ItemStack spawnerStack = spiritInstillerBlockEntity.getItem(0);

            // TODO - Review
            CompoundTag spawnerNbt = spawnerStack
                .getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY)
                .copyTag();

            spawnerNbt = getSpawnerResultNbt(spawnerNbt, firstBowlStack, secondBowlStack);

            resultStack = spawnerStack.copy();
            resultStack.setCount(1);

            BlockEntity.addEntityType(spawnerNbt, BlockEntityType.MOB_SPAWNER);
            resultStack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(spawnerNbt));

            spawnXPAndGrantAdvancements(
                resultStack,
                spiritInstillerBlockEntity,
                spiritInstillerBlockEntity.getUpgradeHolder(),
                world,
                pos
            );
        }
        return resultStack;
    }

    @Override
    public boolean canCraftWithStacks(RecipeInput inventory, Level level) {
        CustomData blockEntityComponent = inventory
            .getItem(0)
            .getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
        return canCraftWithBlockEntityTag(blockEntityComponent, inventory.getItem(1), inventory.getItem(2));
    }

    public abstract boolean canCraftWithBlockEntityTag(
        CustomData spawnerBlockEntityNbt,
        ItemStack leftBowlStack,
        ItemStack rightBowlStack
    );

    public abstract CompoundTag getSpawnerResultNbt(
        CompoundTag spawnerBlockEntityNbt,
        ItemStack secondBowlStack,
        ItemStack centerStack
    );

    public abstract Component getOutputLoreText();

}
