package earth.terrarium.pastel.recipe.enchanter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.capabilities.ExperienceHandler;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntity;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class EnchanterCraftingRecipe extends EnchanterRecipe {

    public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("midgame/build_enchanting_structure");

    //TODO: these should be migrated to IngredientStacks
    protected final List<Ingredient> inputs; // first input is the center, all others around clockwise
    protected final ItemStack output;

    protected final int requiredExperience;
    protected final int craftingTime;
    protected final boolean noDiscounts;
    // copy all modified components from the first stack in the ingredients to the output stack
    protected final boolean copyComponents;

    public EnchanterCraftingRecipe(
        String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, List<Ingredient> inputs,
        ItemStack output, int craftingTime, int requiredExperience, boolean noDiscounts, boolean copyComponents
    ) {
        super(group, secret, requiredAdvancementIdentifier);

        this.inputs = inputs;
        this.output = output;
        this.requiredExperience = requiredExperience;
        this.craftingTime = craftingTime;
        this.noDiscounts = noDiscounts;
        this.copyComponents = copyComponents;

        registerInToastManager(getType(), this);
    }

    @Override
    public boolean matches(RecipeInput inv, Level level) {
        var center = inv.getItem(EnchanterBlockEntity.CENTER);
        var availableXp = Optional.ofNullable(inv.getItem(EnchanterBlockEntity.XP_STORAGE)
                                                 .getCapability(PastelCapabilities.Misc.XP, level.registryAccess()))
                                  .map(ExperienceHandler::getStoredAmount)
                                  .orElse(0);

        // the item on the enchanter
        if (!inputs.getFirst()
                   .test(center)) {
            return false;
        }

        if (requiredExperience > availableXp) {
            return false;
        }

        var matches = new HashSet<Integer>();
        for (Ingredient ingredient : inputs.subList(1, 9)) {
            for (int slot = 1; slot < 9; slot++) {
                if (matches.contains(slot))
                    continue;

                if (!ingredient.test(inv.getItem(slot + 1)))
                    continue;

                matches.add(slot);
                break;
            }
        }

        return matches.size() == 8; // Any less and some pedestals were invalid, any more and what
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider drm) {
        if (this.copyComponents) {
            return inv.getItem(0)
                      .transmuteCopy(output.getItem(), output.getCount());
        }
        return output.copy();
    }

    @Override
    public void consumeIngredients(EnchanterBlockEntity enchanter, HolderLookup.Provider lookup, double scaling) {
        var actioned = new HashSet<Integer>();
        var inv = enchanter.getInputs();
        var output = 1D;

        if (!noDiscounts) {
            output = 1 / enchanter.getUpgrades()
                                  .getEffectiveValue(Upgradeable.UpgradeType.EFFICIENCY);
        }

        for (Ingredient ingredient : inputs.subList(1, 9)) {
            for (int slot = 1; slot < 9; slot++) {
                if (actioned.contains(slot))
                    continue;

                if (!ingredient.test(inv.getStackInSlot(slot + 1)))
                    continue;

                assert enchanter.getLevel() != null;
                inv.extractItem(slot + 1, Support.chanceRound(output, enchanter.getLevel().random), false);
                actioned.add(slot);
                break;
            }
        }

        Optional.ofNullable(inv.getStackInSlot(EnchanterBlockEntity.XP_STORAGE)
                               .getCapability(PastelCapabilities.Misc.XP, lookup))
                .map(storage -> storage.extract(requiredExperience, false));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryManager) {
        return output;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(PastelBlocks.ENCHANTER.get());
    }

    @Override
    public ResourceLocation getRecipeTypeUnlockIdentifier() {
        return UNLOCK_IDENTIFIER;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.ENCHANTER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return PastelRecipeTypes.ENCHANTER;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(inputs.size());
        ingredients.addAll(inputs);
        return ingredients;
    }

    public int getRequiredExperience() {
        return requiredExperience;
    }

    public int getCraftingTime(double unused) {
        return this.craftingTime;
    }

    public boolean noDiscounts() {
        return noDiscounts;
    }

    @Override
    public String getRecipeTypeShortID() {
        return "enchanter";
    }

    public static class Serializer implements RecipeSerializer<EnchanterCraftingRecipe> {

        public static final MapCodec<EnchanterCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                                                                                                            Codec.STRING.optionalFieldOf("group", "")
                                                                                                                        .forGetter(recipe -> recipe.group),
                                                                                                            Codec.BOOL.optionalFieldOf("secret", false)
                                                                                                                      .forGetter(recipe -> recipe.secret),
                                                                                                            ResourceLocation.CODEC.optionalFieldOf("required_advancement")
                                                                                                                                  .forGetter(recipe -> recipe.requiredAdvancementIdentifier),
                                                                                                            Ingredient.CODEC_NONEMPTY.listOf()
                                                                                                                                     .optionalFieldOf("ingredients", List.of())
                                                                                                                                     .forGetter(recipe -> recipe.inputs),
                                                                                                            ItemStack.CODEC.fieldOf("result")
                                                                                                                           .forGetter(recipe -> recipe.output),
                                                                                                            Codec.INT.optionalFieldOf("required_experience", 0)
                                                                                                                     .forGetter(recipe -> recipe.requiredExperience),
                                                                                                            Codec.INT.optionalFieldOf("time", 200)
                                                                                                                     .forGetter(recipe -> recipe.craftingTime),
                                                                                                            Codec.BOOL.optionalFieldOf("disable_yield_and_efficiency_upgrades", false)
                                                                                                                      .forGetter(recipe -> recipe.noDiscounts),
                                                                                                            Codec.BOOL.optionalFieldOf("copy_components", false)
                                                                                                                      .forGetter(recipe -> recipe.copyComponents)
                                                                                                        )
                                                                                                        .apply(
                                                                                                            i,
                                                                                                            EnchanterCraftingRecipe::new
                                                                                                        ));

        public static final StreamCodec<RegistryFriendlyByteBuf, EnchanterCraftingRecipe> STREAM_CODEC
            = PacketCodecHelper.tuple(
            ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
            ByteBufCodecs.BOOL, recipe -> recipe.secret,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), recipe -> recipe.requiredAdvancementIdentifier,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.inputs,
            ItemStack.STREAM_CODEC, recipe -> recipe.output,
            ByteBufCodecs.VAR_INT, recipe -> recipe.requiredExperience,
            ByteBufCodecs.VAR_INT, recipe -> recipe.craftingTime,
            ByteBufCodecs.BOOL, recipe -> recipe.noDiscounts,
            ByteBufCodecs.BOOL, recipe -> recipe.copyComponents,
            EnchanterCraftingRecipe::new
        );

        @Override
        public MapCodec<EnchanterCraftingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EnchanterCraftingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

    }
}
