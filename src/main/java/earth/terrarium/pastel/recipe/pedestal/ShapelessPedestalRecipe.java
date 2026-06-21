package earth.terrarium.pastel.recipe.pedestal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.helpers.data.CodecHelper;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShapelessPedestalRecipe extends PedestalRecipe {

    public ShapelessPedestalRecipe(
            String group,
            boolean secret,
            Optional<ResourceLocation> requiredAdvancementIdentifier,
            PedestalTier tier,
            NonNullList<Ingredient> craftingInputs,
            Map<GemstoneColor, Integer> gemstonePowderInputs,
            ItemStack output,
            float experience,
            int craftingTime,
            boolean skipRecipeRemainders,
            boolean noBenefitsFromYieldUpgrades
    ) {
        super(
            group,
            secret,
            requiredAdvancementIdentifier,
            tier,
            craftingInputs,
            gemstonePowderInputs,
            output,
            experience,
            craftingTime,
            skipRecipeRemainders,
            noBenefitsFromYieldUpgrades
        );
    }

    @Override
    public boolean matches(PedestalRecipeInput recipeInput, Level world) {
        var input = recipeInput.getCraftingGridInput();
        if (input.ingredientCount() != this.inputs.size()) {
            return false;
        } else {
            var nonEmptyItems = new ArrayList<ItemStack>(input.ingredientCount());

            for (ItemStack item : input.items()) {
                if (!item.isEmpty()) {
                    nonEmptyItems.add(item);
                }
            }

            return RecipeMatcher.findMatches(nonEmptyItems, this.inputs) != null && super.matches(recipeInput, world);
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.SHAPELESS_PEDESTAL_RECIPE_SERIALIZER;
    }

    @Override
    public void consumeIngredients(PedestalBlockEntity pedestal, PedestalRecipeInput input) {
        super.consumeIngredients(pedestal, input);

        var inv = pedestal.getInventory();
        for (
            int slot : CRAFTING_GRID_SLOTS
        ) {
            for (
                Ingredient ingredient : this.inputs
            ) {
                ItemStack slotStack = inv.getStackInSlot(slot);
                if (ingredient.test(slotStack)) {
                    decrementGridSlot(pedestal, slot, 1, slotStack);
                    break;
                }
            }
        }
    }

    public static class Serializer implements RecipeSerializer<ShapelessPedestalRecipe> {

        public static final MapCodec<ShapelessPedestalRecipe> CODEC = RecordCodecBuilder
            .mapCodec(
                i -> i
                    .group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                        Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
                        ResourceLocation.CODEC
                            .optionalFieldOf("required_advancement")
                            .forGetter(recipe -> recipe.requiredAdvancementIdentifier),
                        PedestalTier.CODEC.optionalFieldOf("tier", PedestalTier.BASIC).forGetter(recipe -> recipe.tier),
                        NonNullList.codecOf(Ingredient.CODEC).fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                        CodecHelper
                            .registryMap(PastelRegistries.GEMSTONE_COLOR, Codec.INT)
                            .fieldOf("colors")
                            .forGetter(recipe -> recipe.powderInputs),
                        ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                        Codec.FLOAT.optionalFieldOf("experience", 0f).forGetter(recipe -> recipe.experience),
                        Codec.INT.optionalFieldOf("time", 200).forGetter(recipe -> recipe.craftingTime),
                        Codec.BOOL
                            .optionalFieldOf("skip_recipe_remainders", false)
                            .forGetter(recipe -> recipe.skipRecipeRemainders),
                        Codec.BOOL
                            .optionalFieldOf("disable_yield_upgrades", false)
                            .forGetter(recipe -> recipe.noBenefitsFromYieldUpgrades)
                    )
                    .apply(i, ShapelessPedestalRecipe::new)
            );

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessPedestalRecipe> STREAM_CODEC = PacketCodecHelper
            .tuple(
                ByteBufCodecs.STRING_UTF8,
                recipe -> recipe.group,
                ByteBufCodecs.BOOL,
                recipe -> recipe.secret,
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
                recipe -> recipe.requiredAdvancementIdentifier,
                PedestalTier.STREAM_CODEC,
                recipe -> recipe.tier,
                Ingredient.CONTENTS_STREAM_CODEC.apply(PacketCodecHelper.nonNullList()),
                recipe -> recipe.inputs,
                ByteBufCodecs
                    .map(
                        HashMap::new,
                        ByteBufCodecs.registry(PastelRegistries.GEMSTONE_COLOR.key()),
                        ByteBufCodecs.VAR_INT
                    ),
                recipe -> recipe.powderInputs,
                ItemStack.STREAM_CODEC,
                recipe -> recipe.output,
                ByteBufCodecs.FLOAT,
                recipe -> recipe.experience,
                ByteBufCodecs.VAR_INT,
                recipe -> recipe.craftingTime,
                ByteBufCodecs.BOOL,
                recipe -> recipe.skipRecipeRemainders,
                ByteBufCodecs.BOOL,
                recipe -> recipe.noBenefitsFromYieldUpgrades,
                ShapelessPedestalRecipe::new
            );

        @Override
        public MapCodec<ShapelessPedestalRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapelessPedestalRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
