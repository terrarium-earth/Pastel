package earth.terrarium.pastel.recipe.spirit_instiller.dynamic;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import earth.terrarium.pastel.loot.modifiers.TreasureHunterModifier;
import earth.terrarium.pastel.recipe.InstanceRecipeInput;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;

import java.util.Optional;

public class MemoryToHeadRecipe extends SpiritInstillerRecipe {

    public MemoryToHeadRecipe() {
        super(
            "", false, Optional.of(PastelCommon.locate("unlocks/memory_to_head")),
            IngredientStack.ofItems(PastelBlocks.MEMORY.get()
                                                       .asItem()),
            IngredientStack.ofItems(PastelItems.VEGETAL.get(), 4),
            IngredientStack.ofItems(PastelItems.QUITOXIC_POWDER.get(), 4),
            new ItemStack(Blocks.ZOMBIE_HEAD), 200, 1, true
        );
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.SPIRIT_INSTILLER_MEMORY_TO_HEAD;
    }

    @Override
    public boolean matches(InstanceRecipeInput input, Level world) {
        if (bowlMatches(input))
            return input.getItem(CENTER)
                        .is(PastelBlocks.MEMORY.asItem());

        return false;
    }

    @Override
    public ItemStack assemble(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, HolderLookup.Provider drm) {
        SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
        ItemStack resultStack = ItemStack.EMPTY;
        ServerLevel world = (ServerLevel) spiritInstillerBlockEntity.getLevel();
        BlockPos pos = spiritInstillerBlockEntity.getBlockPos();

        Optional<Entity> entity = MemoryBlockEntity.hatchEntity(world, pos, spiritInstillerBlockEntity.getItem(0));
        if (entity.isPresent()) {
            var proposed = TreasureHunterModifier.tryGetHead(entity.get());

            if (proposed.isPresent())
                resultStack = new ItemStack(proposed.get());

            entity.get()
                  .discard();
        }

        spawnXPAndGrantAdvancements(
            resultStack, spiritInstillerBlockEntity, spiritInstillerBlockEntity.getUpgradeHolder(), world, pos);
        return resultStack;
    }

    @Override
    public boolean canCraftWithStacks(RecipeInput inventory, Level level) {
        ItemStack instillerStack = inventory.getItem(0);

        var entity = MemoryBlockEntity.hatchEntity((ServerLevel) level, BlockPos.ZERO, instillerStack);
        return entity.filter(e -> TreasureHunterModifier.tryGetHead(e)
                                                        .isPresent())
                     .isPresent();

    }

}
