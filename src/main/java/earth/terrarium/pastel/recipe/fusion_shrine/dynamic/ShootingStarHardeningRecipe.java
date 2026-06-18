package earth.terrarium.pastel.recipe.fusion_shrine.dynamic;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.FusionShrineRecipeWorldEffect;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlockEntity;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStarItem;
import earth.terrarium.pastel.recipe.fusion_shrine.FusionShrineRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShootingStarHardeningRecipe extends FusionShrineRecipe {

    public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("collect_all_shooting_star_variants");

    public static final Component DESCRIPTION = Component
        .translatable(
            "pastel.recipe.fusion_shrine.explanation.shooting_star_hardening"
        );

    public ShootingStarHardeningRecipe() {
        super(
            "",
            false,
            Optional.of(UNLOCK_IDENTIFIER),
            List.of(IngredientStack.ofTag(PastelItemTags.SHOOTING_STARS), IngredientStack.ofItems(Items.DIAMOND)),
            FluidIngredient.of(Fluids.WATER),
            getHardenedShootingStar(),
            5,
            100,
            true,
            true,
            true,
            new ArrayList<>(),
            FusionShrineRecipeWorldEffect.NOTHING,
            new ArrayList<>(),
            FusionShrineRecipeWorldEffect.NOTHING,
            DESCRIPTION
        );
    }

    private static ItemStack getHardenedShootingStar() {
        ItemStack stack = PastelBlocks.GLISTERING_SHOOTING_STAR
            .get()
            .asItem()
            .getDefaultInstance();
        ShootingStarItem.setHardened(stack);
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.SHOOTING_STAR_HARDENING;
    }

    @Override
    public void craft(Level world, FusionShrineBlockEntity fusionShrineBlockEntity) {
        ItemStack shootingStarStack = ItemStack.EMPTY;
        ItemStack diamondStack = ItemStack.EMPTY;

        for (
            int j = 0;
            j < fusionShrineBlockEntity.getContainerSize();
            ++j
        ) {
            ItemStack itemStack = fusionShrineBlockEntity.getItem(j);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof ShootingStarItem) {
                    shootingStarStack = itemStack;
                } else if (itemStack.is(Items.DIAMOND)) {
                    diamondStack = itemStack;
                }
            }
        }

        if (!shootingStarStack.isEmpty() && !diamondStack.isEmpty()) {
            int craftedAmount = Math.min(shootingStarStack.getCount(), diamondStack.getCount());

            ItemStack hardenedStack = shootingStarStack.copy();
            ShootingStarItem.setHardened(hardenedStack);

            shootingStarStack.shrink(craftedAmount);
            diamondStack.shrink(craftedAmount);

            spawnCraftingResultAndXP(world, fusionShrineBlockEntity, hardenedStack, craftedAmount); // spawn results
        }
    }

}
