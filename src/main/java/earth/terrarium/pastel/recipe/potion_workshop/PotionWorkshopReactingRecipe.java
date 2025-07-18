package earth.terrarium.pastel.recipe.potion_workshop;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.recipe.DescriptiveGatedRecipe;
import earth.terrarium.pastel.helpers.data.CodecHelper;
import earth.terrarium.pastel.recipe.GatedPastelRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PotionWorkshopReactingRecipe extends GatedPastelRecipe<RecipeInput>
    implements DescriptiveGatedRecipe<RecipeInput> {

    protected static final HashMap<Item, List<PotionMod>> reagents = new HashMap<>();

    protected final Item item;
    protected final List<PotionMod> modifiers;

    public PotionWorkshopReactingRecipe(
        String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, Item item,
        List<PotionMod> modifiers
    ) {
        super(group, secret, requiredAdvancementIdentifier);
        this.item = item;
        this.modifiers = modifiers;

        reagents.put(item, modifiers);

        registerInToastManager(getType(), this);
    }

    @Override
    public boolean matches(@NotNull RecipeInput inv, Level world) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput inventory, HolderLookup.Provider registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryManager) {
        return item.getDefaultInstance();
    }

    @Override
    public ItemStack getToastSymbol() {
        return PastelBlocks.POTION_WORKSHOP.get()
                                           .asItem()
                                           .getDefaultInstance();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.POTION_WORKSHOP_REACTING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return PastelRecipeTypes.POTION_WORKSHOP_REACTING;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
        defaultedList.add(Ingredient.of(this.item));
        return defaultedList;
    }

    @Override
    public ResourceLocation getRecipeTypeUnlockIdentifier() {
        return PotionWorkshopRecipe.UNLOCK_IDENTIFIER;
    }

    @Override
    public String getRecipeTypeShortID() {
        return "potion_workshop_reacting";
    }

    @Override
    public Component getDescription() {
        ResourceLocation identifier = BuiltInRegistries.ITEM.getKey(this.item);
        return Component.translatable(
            "pastel.rei.potion_workshop_reacting." + identifier.getNamespace() + "." + identifier.getPath());
    }

    @Override
    public Item getItem() {
        return this.item;
    }

    public static boolean isReagent(Item item) {
        return reagents.containsKey(item);
    }

    public static PotionMod.Builder combine(PotionMod.Builder builder, ItemStack reagentStack, RandomSource random) {
        Item reagent = reagentStack.getItem();
        List<PotionMod> reagentMods = reagents.getOrDefault(reagent, null);
        if (reagentMods != null)
            builder.combine(reagentMods.get(random.nextInt(reagentMods.size())));
        return builder;
    }

    public static class Serializer implements RecipeSerializer<PotionWorkshopReactingRecipe> {

        public static final MapCodec<PotionWorkshopReactingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                                                                                                                 Codec.STRING.optionalFieldOf("group", "")
                                                                                                                             .forGetter(c -> c.group),
                                                                                                                 Codec.BOOL.optionalFieldOf("secret", false)
                                                                                                                           .forGetter(c -> c.secret),
                                                                                                                 ResourceLocation.CODEC.optionalFieldOf("required_advancement")
                                                                                                                                       .forGetter(c -> c.requiredAdvancementIdentifier),
                                                                                                                 BuiltInRegistries.ITEM.byNameCodec()
                                                                                                                                       .fieldOf("item")
                                                                                                                                       .forGetter(c -> c.item),
                                                                                                                 CodecHelper.singleOrList(PotionMod.CODEC)
                                                                                                                            .fieldOf("modifiers")
                                                                                                                            .forGetter(c -> c.modifiers)
                                                                                                             )
                                                                                                             .apply(
                                                                                                                 i,
                                                                                                                 PotionWorkshopReactingRecipe::new
                                                                                                             ));

        public static final StreamCodec<RegistryFriendlyByteBuf, PotionWorkshopReactingRecipe> STREAM_CODEC
            = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, c -> c.group,
            ByteBufCodecs.BOOL, c -> c.secret,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), c -> c.requiredAdvancementIdentifier,
            ByteBufCodecs.registry(Registries.ITEM), c -> c.item,
            PotionMod.STREAM_CODEC.apply(ByteBufCodecs.list()), c -> c.modifiers,
            PotionWorkshopReactingRecipe::new
        );

        @Override
        public MapCodec<PotionWorkshopReactingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, PotionWorkshopReactingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

    }

}
