package earth.terrarium.pastel.compat.modonomicon;

import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.modonomicon.data.BookConditionJsonLoader;
import com.klikli_dev.modonomicon.data.BookPageJsonLoader;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import com.klikli_dev.modonomicon.data.NetworkLoader;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.GatedRecipe;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookAnvilCrushingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookChecklistPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookCinderhearthSmeltingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookCollectionPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookCrystallarieumGrowingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookEnchanterCraftingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookEnchanterUpgradingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookFluidConvertingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookFusionShrineCraftingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookHintPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookLinkPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookPedestalCraftingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookPotionWorkshopPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookPrimordialFireBurningPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookSnippetPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookSpiritInstillerCraftingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookStatusEffectPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.client.pages.BookTitrationBarrelFermentingPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.page_types.WebLinkEntry;
import earth.terrarium.pastel.compat.modonomicon.pages.BookChecklistPage;
import earth.terrarium.pastel.compat.modonomicon.pages.BookCollectionPage;
import earth.terrarium.pastel.compat.modonomicon.pages.BookGatedRecipePage;
import earth.terrarium.pastel.compat.modonomicon.pages.BookHintPage;
import earth.terrarium.pastel.compat.modonomicon.pages.BookLinkPage;
import earth.terrarium.pastel.compat.modonomicon.pages.BookSnippetPage;
import earth.terrarium.pastel.compat.modonomicon.pages.BookStatusEffectPage;
import earth.terrarium.pastel.compat.modonomicon.unlock_conditions.EnchantmentRegisteredCondition;
import earth.terrarium.pastel.compat.modonomicon.unlock_conditions.NotCondition;
import earth.terrarium.pastel.compat.modonomicon.unlock_conditions.RecipesLoadedAndUnlockedCondition;
import earth.terrarium.pastel.recipe.anvil_crushing.AnvilCrushingRecipe;
import earth.terrarium.pastel.recipe.cinderhearth.CinderhearthRecipe;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchanterCraftingRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.HumusConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import earth.terrarium.pastel.recipe.fusion_shrine.FusionShrineRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import earth.terrarium.pastel.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class ModonomiconCompat extends PastelIntegrationPacks.ModIntegrationPack {

    // Entry Types
    public static final ResourceLocation WEB_LINK_ENTRY_TYPE = PastelCommon.locate("web_link");

    // Page Types
    public static final ResourceLocation ANVIL_CRUSHING_PAGE = PastelCommon.locate("anvil_crushing");

    public static final ResourceLocation PEDESTAL_CRAFTING_PAGE = PastelCommon.locate("pedestal_crafting");

    public static final ResourceLocation FUSION_SHRINE_CRAFTING_PAGE = PastelCommon.locate("fusion_shrine_crafting");

    public static final ResourceLocation ENCHANTER_CRAFTING_PAGE = PastelCommon.locate("enchanter_crafting");

    public static final ResourceLocation ENCHANTER_UPGRADING_PAGE = PastelCommon.locate("enchanter_upgrading");

    public static final ResourceLocation POTION_WORKSHOP_BREWING_PAGE = PastelCommon.locate("potion_workshop_brewing");

    public static final ResourceLocation POTION_WORKSHOP_CRAFTING_PAGE = PastelCommon
        .locate(
            "potion_workshop_crafting"
        );

    public static final ResourceLocation SPIRIT_INSTILLER_CRAFTING_PAGE = PastelCommon
        .locate(
            "spirit_instiller_crafting"
        );

    public static final ResourceLocation LIQUID_CRYSTAL_CONVERTING_PAGE = PastelCommon
        .locate(
            "liquid_crystal_converting"
        );

    public static final ResourceLocation MIDNIGHT_SOLUTION_CONVERTING_PAGE = PastelCommon
        .locate(
            "midnight_solution_converting"
        );

    public static final ResourceLocation DRAGONROT_CONVERTING_PAGE = PastelCommon.locate("dragonrot_converting");

    public static final ResourceLocation HUMUS_CONVERTING_PAGE = PastelCommon.locate("humus_converting");

    public static final ResourceLocation CRYSTALLARIEUM_GROWING_PAGE = PastelCommon.locate("crystallarieum_growing");

    public static final ResourceLocation CINDERHEARTH_SMELTING_PAGE = PastelCommon.locate("cinderhearth_smelting");

    public static final ResourceLocation TITRATION_BARREL_FERMENTING_PAGE = PastelCommon
        .locate(
            "titration_barrel_fermenting"
        );

    public static final ResourceLocation STATUS_EFFECT_PAGE = PastelCommon.locate("status_effect");

    public static final ResourceLocation HINT_PAGE = PastelCommon.locate("hint");

    public static final ResourceLocation CHECKLIST_PAGE = PastelCommon.locate("checklist");

    public static final ResourceLocation SNIPPET_PAGE = PastelCommon.locate("snippet");

    public static final ResourceLocation LINK_PAGE = PastelCommon.locate("link");

    public static final ResourceLocation COLLECTION_PAGE = PastelCommon.locate("collection");

    public static final ResourceLocation PRIMORDIAL_FIRE_BURNING_PAGE = PastelCommon.locate("primordial_fire_burning");

    // Unlock Conditions
    public static final ResourceLocation ENCHANTMENT_REGISTERED = PastelCommon.locate("enchantment_registered");

    public static final ResourceLocation RECIPE_LOADED_AND_UNLOCKED = PastelCommon.locate("recipe_loaded_and_unlocked");

    public static final ResourceLocation NOT = PastelCommon.locate("not");

    @Override
    public void register() {
        registerPageTypes();
        registerPages();
        registerUnlockConditions();
    }

    private void registerPageTypes() {
        LoaderRegistry.registerEntryType(WEB_LINK_ENTRY_TYPE, WebLinkEntry::fromJson, WebLinkEntry::fromNetwork);
    }

    private void registerPages() {
        registerGatedRecipePage(ANVIL_CRUSHING_PAGE, PastelRecipeTypes.ANVIL_CRUSHING, false);
        registerGatedRecipePage(PEDESTAL_CRAFTING_PAGE, PastelRecipeTypes.PEDESTAL, false);
        registerGatedRecipePage(FUSION_SHRINE_CRAFTING_PAGE, PastelRecipeTypes.FUSION_SHRINE, false);
        registerGatedRecipePage(ENCHANTER_CRAFTING_PAGE, PastelRecipeTypes.ENCHANTER, false);
        registerGatedRecipePage(ENCHANTER_UPGRADING_PAGE, PastelRecipeTypes.ENCHANTMENT_UPGRADE, false);
        registerGatedRecipePage(POTION_WORKSHOP_BREWING_PAGE, PastelRecipeTypes.POTION_WORKSHOP_BREWING, false);
        registerGatedRecipePage(POTION_WORKSHOP_CRAFTING_PAGE, PastelRecipeTypes.POTION_WORKSHOP_CRAFTING, false);
        registerGatedRecipePage(SPIRIT_INSTILLER_CRAFTING_PAGE, PastelRecipeTypes.SPIRIT_INSTILLING, true);
        registerGatedRecipePage(LIQUID_CRYSTAL_CONVERTING_PAGE, PastelRecipeTypes.LIQUID_CRYSTAL_CONVERTING, false);
        registerGatedRecipePage(
            MIDNIGHT_SOLUTION_CONVERTING_PAGE,
            PastelRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING,
            false
        );
        registerGatedRecipePage(DRAGONROT_CONVERTING_PAGE, PastelRecipeTypes.DRAGONROT_CONVERTING, false);
        registerGatedRecipePage(HUMUS_CONVERTING_PAGE, PastelRecipeTypes.HUMUS_CONVERTING, false);
        registerGatedRecipePage(CRYSTALLARIEUM_GROWING_PAGE, PastelRecipeTypes.CRYSTALLARIEUM, false);
        registerGatedRecipePage(CINDERHEARTH_SMELTING_PAGE, PastelRecipeTypes.CINDERHEARTH, false);
        registerGatedRecipePage(TITRATION_BARREL_FERMENTING_PAGE, PastelRecipeTypes.TITRATION_BARREL, true);
        registerGatedRecipePage(PRIMORDIAL_FIRE_BURNING_PAGE, PastelRecipeTypes.PRIMORDIAL_FIRE_BURNING, false);

        LoaderRegistry
            .registerPageLoader(
                STATUS_EFFECT_PAGE,
                (BookPageJsonLoader<?>) BookStatusEffectPage::fromJson,
                BookStatusEffectPage::fromNetwork
            );
        LoaderRegistry
            .registerPageLoader(
                HINT_PAGE,
                (BookPageJsonLoader<?>) BookHintPage::fromJson,
                BookHintPage::fromNetwork
            );
        LoaderRegistry
            .registerPageLoader(
                CHECKLIST_PAGE,
                (BookPageJsonLoader<?>) BookChecklistPage::fromJson,
                BookChecklistPage::fromNetwork
            );
        LoaderRegistry
            .registerPageLoader(
                SNIPPET_PAGE,
                (BookPageJsonLoader<?>) BookSnippetPage::fromJson,
                BookSnippetPage::fromNetwork
            );
        LoaderRegistry
            .registerPageLoader(
                LINK_PAGE,
                (BookPageJsonLoader<?>) BookLinkPage::fromJson,
                BookLinkPage::fromNetwork
            );
        LoaderRegistry
            .registerPageLoader(
                COLLECTION_PAGE,
                (BookPageJsonLoader<?>) BookCollectionPage::fromJson,
                BookCollectionPage::fromNetwork
            );
    }

    private void registerGatedRecipePage(
        ResourceLocation id,
        RecipeType<? extends GatedRecipe<?>> recipeType,
        boolean supportsTwoRecipesOnOnePage
    ) {
        BookPageJsonLoader<?> fromJson = (entryId, json, provider) -> BookGatedRecipePage
            .fromJson(
                entryId,
                id,
                recipeType,
                json,
                supportsTwoRecipesOnOnePage,
                provider
            );
        NetworkLoader<? extends BookPage> fromNetwork = buffer -> BookGatedRecipePage
            .fromNetwork(
                id,
                recipeType,
                buffer
            );
        LoaderRegistry.registerPageLoader(id, fromJson, fromNetwork);
    }

    private void registerUnlockConditions() {
        LoaderRegistry
            .registerConditionLoader(
                ENCHANTMENT_REGISTERED,
                (BookConditionJsonLoader<?>) EnchantmentRegisteredCondition::fromJson,
                EnchantmentRegisteredCondition::fromNetwork
            );
        LoaderRegistry
            .registerConditionLoader(
                RECIPE_LOADED_AND_UNLOCKED,
                (BookConditionJsonLoader<?>) RecipesLoadedAndUnlockedCondition::fromJson,
                RecipesLoadedAndUnlockedCondition::fromNetwork
            );
        LoaderRegistry
            .registerConditionLoader(
                NOT,
                (BookConditionJsonLoader<?>) NotCondition::fromJson,
                NotCondition::fromNetwork
            );
    }

    @Override
    @SuppressWarnings(
        "unchecked"
    )
    public void registerClient() {
        PageRendererRegistry
            .registerPageRenderer(
                ANVIL_CRUSHING_PAGE,
                p -> new BookAnvilCrushingPageRenderer((BookGatedRecipePage<AnvilCrushingRecipe>) p)
            );
        PageRendererRegistry
            .registerPageRenderer(
                PEDESTAL_CRAFTING_PAGE,
                p -> new BookPedestalCraftingPageRenderer((BookGatedRecipePage<PedestalRecipe>) p)
            );
        PageRendererRegistry
            .registerPageRenderer(
                FUSION_SHRINE_CRAFTING_PAGE,
                p -> new BookFusionShrineCraftingPageRenderer(
                    (BookGatedRecipePage<FusionShrineRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                ENCHANTER_CRAFTING_PAGE,
                p -> new BookEnchanterCraftingPageRenderer(
                    (BookGatedRecipePage<EnchanterCraftingRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                ENCHANTER_UPGRADING_PAGE,
                p -> new BookEnchanterUpgradingPageRenderer(
                    (BookGatedRecipePage<EnchantmentUpgradeRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                POTION_WORKSHOP_BREWING_PAGE,
                p -> new BookPotionWorkshopPageRenderer<>(
                    (BookGatedRecipePage<PotionWorkshopBrewingRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                POTION_WORKSHOP_CRAFTING_PAGE,
                p -> new BookPotionWorkshopPageRenderer<>(
                    (BookGatedRecipePage<PotionWorkshopCraftingRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                SPIRIT_INSTILLER_CRAFTING_PAGE,
                p -> new BookSpiritInstillerCraftingPageRenderer(
                    (BookGatedRecipePage<SpiritInstillerRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                CRYSTALLARIEUM_GROWING_PAGE,
                p -> new BookCrystallarieumGrowingPageRenderer(
                    (BookGatedRecipePage<CrystallarieumRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                CINDERHEARTH_SMELTING_PAGE,
                p -> new BookCinderhearthSmeltingPageRenderer(
                    (BookGatedRecipePage<CinderhearthRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                TITRATION_BARREL_FERMENTING_PAGE,
                p -> new BookTitrationBarrelFermentingPageRenderer(
                    (BookGatedRecipePage<TitrationBarrelRecipe>) p
                )
            );
        PageRendererRegistry
            .registerPageRenderer(
                PRIMORDIAL_FIRE_BURNING_PAGE,
                p -> new BookPrimordialFireBurningPageRenderer<>(
                    (BookGatedRecipePage<PrimordialFireBurningRecipe>) p
                )
            );

        PageRendererRegistry
            .registerPageRenderer(
                STATUS_EFFECT_PAGE,
                p -> new BookStatusEffectPageRenderer((BookStatusEffectPage) p)
            );
        PageRendererRegistry.registerPageRenderer(HINT_PAGE, p -> new BookHintPageRenderer((BookHintPage) p));
        PageRendererRegistry
            .registerPageRenderer(
                CHECKLIST_PAGE,
                p -> new BookChecklistPageRenderer((BookChecklistPage) p)
            );
        PageRendererRegistry.registerPageRenderer(SNIPPET_PAGE, p -> new BookSnippetPageRenderer((BookSnippetPage) p));
        PageRendererRegistry.registerPageRenderer(LINK_PAGE, p -> new BookLinkPageRenderer((BookLinkPage) p));
        PageRendererRegistry
            .registerPageRenderer(
                COLLECTION_PAGE,
                p -> new BookCollectionPageRenderer((BookCollectionPage) p)
            );

        PageRendererRegistry
            .registerPageRenderer(
                LIQUID_CRYSTAL_CONVERTING_PAGE,
                p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<LiquidCrystalConvertingRecipe>) p) {
                    @Override
                    public ResourceLocation getBackgroundTexture() {
                        return PastelCommon.locate("textures/gui/guidebook/liquid_crystal.png");
                    }
                }
            );

        PageRendererRegistry
            .registerPageRenderer(
                MIDNIGHT_SOLUTION_CONVERTING_PAGE,
                p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<MidnightSolutionConvertingRecipe>) p) {
                    @Override
                    public ResourceLocation getBackgroundTexture() {
                        return PastelCommon.locate("textures/gui/guidebook/midnight_solution.png");
                    }
                }
            );

        PageRendererRegistry
            .registerPageRenderer(
                DRAGONROT_CONVERTING_PAGE,
                p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<DragonrotConvertingRecipe>) p) {
                    @Override
                    public ResourceLocation getBackgroundTexture() {
                        return PastelCommon.locate("textures/gui/guidebook/dragonrot.png");
                    }
                }
            );

        PageRendererRegistry
            .registerPageRenderer(
                HUMUS_CONVERTING_PAGE,
                p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<HumusConvertingRecipe>) p) {
                    @Override
                    public ResourceLocation getBackgroundTexture() {
                        return PastelCommon.locate("textures/gui/guidebook/humus.png");
                    }
                }
            );
    }

}
