package earth.terrarium.pastel.recipe.pedestal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.helpers.data.CodecHelper;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShapedPedestalRecipe extends PedestalRecipe {

    protected final int width;

    protected final int height;

    protected final RawShapedPedestalRecipe rawShapedRecipe;

    public ShapedPedestalRecipe(
        String group,
        boolean secret,
        Optional<ResourceLocation> requiredAdvancementIdentifier,
        PedestalTier tier,
        RawShapedPedestalRecipe rawShapedRecipe,
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
            rawShapedRecipe.getIngredients(),
            gemstonePowderInputs,
            output,
            experience,
            craftingTime,
            skipRecipeRemainders,
            noBenefitsFromYieldUpgrades
        );

        this.rawShapedRecipe = rawShapedRecipe;
        this.width = rawShapedRecipe.getWidth();
        this.height = rawShapedRecipe.getHeight();
    }

    @Override
    public boolean matches(PedestalRecipeInput inv, Level world) {
        return rawShapedRecipe.matches(inv.getCraftingGridInput()) && super.matches(inv, world);
    }

    @Override
    public void consumeIngredients(PedestalBlockEntity pedestal, PedestalRecipeInput input) {
        super.consumeIngredients(pedestal, input);

        boolean mirrored = rawShapedRecipe.matches(input.getCraftingGridInput(), true);
        var inv = pedestal.getInventory();
        var positioned = CraftingInput.ofPositioned(3, 3, inv.getInternalList());

        for (
            int x = 0;
            x < this.width;
            x++
        ) {
            for (
                int y = 0;
                y < this.height;
                y++
            ) {
                int ingredientStackId = (mirrored ? ((this.width - 1) - x) : x) + this.width * y;
                int slot = (x + positioned.left()) + 3 * (y + positioned.top());

                var ingredient = this.inputs.get(ingredientStackId);
                var stack = inv.getStackInSlot(slot);

                if (!stack.isEmpty()) {
                    decrementGridSlot(pedestal, slot, ingredient.getCount(), stack);
                }
            }
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.SHAPED_PEDESTAL_RECIPE_SERIALIZER;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public boolean isShapeless() {
        return false;
    }

    public static class Serializer implements RecipeSerializer<ShapedPedestalRecipe> {

        public static final MapCodec<ShapedPedestalRecipe> CODEC = RecordCodecBuilder
            .mapCodec(
                i -> i
                    .group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                        Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
                        ResourceLocation.CODEC
                            .optionalFieldOf("required_advancement")
                            .forGetter(recipe -> recipe.requiredAdvancementIdentifier),
                        PedestalTier.CODEC.optionalFieldOf("tier", PedestalTier.BASIC).forGetter(recipe -> recipe.tier),
                        RawShapedPedestalRecipe.CODEC.forGetter(recipe -> recipe.rawShapedRecipe),
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
                    .apply(i, ShapedPedestalRecipe::new)
            );

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedPedestalRecipe> STREAM_CODEC = PacketCodecHelper
            .tuple(
                ByteBufCodecs.STRING_UTF8,
                recipe -> recipe.group,
                ByteBufCodecs.BOOL,
                recipe -> recipe.secret,
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
                recipe -> recipe.requiredAdvancementIdentifier,
                PedestalTier.STREAM_CODEC,
                recipe -> recipe.tier,
                RawShapedPedestalRecipe.STREAM_CODEC,
                recipe -> recipe.rawShapedRecipe,
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
                ShapedPedestalRecipe::new
            );

        @Override
        public MapCodec<ShapedPedestalRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedPedestalRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
