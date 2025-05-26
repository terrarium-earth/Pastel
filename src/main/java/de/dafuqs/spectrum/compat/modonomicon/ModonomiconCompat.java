package de.dafuqs.spectrum.compat.modonomicon;

import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.modonomicon.data.BookConditionJsonLoader;
import com.klikli_dev.modonomicon.data.BookPageJsonLoader;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import com.klikli_dev.modonomicon.data.NetworkLoader;
import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.recipe.GatedRecipe;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookAnvilCrushingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookChecklistPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookCinderhearthSmeltingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookCollectionPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookConfirmationButtonPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookCrystallarieumGrowingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookEnchanterCraftingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookEnchanterUpgradingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookFluidConvertingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookFusionShrineCraftingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookHintPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookLinkPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookPedestalCraftingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookPotionWorkshopPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookPrimordialFireBurningPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookSnippetPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookSpiritInstillerCraftingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookStatusEffectPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.client.pages.BookTitrationBarrelFermentingPageRenderer;
import de.dafuqs.spectrum.compat.modonomicon.page_types.WebLinkEntry;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookChecklistPage;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookCollectionPage;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookConfirmationButtonPage;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookGatedRecipePage;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookHintPage;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookLinkPage;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookSnippetPage;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookStatusEffectPage;
import de.dafuqs.spectrum.compat.modonomicon.unlock_conditions.EnchantmentRegisteredCondition;
import de.dafuqs.spectrum.compat.modonomicon.unlock_conditions.NotCondition;
import de.dafuqs.spectrum.compat.modonomicon.unlock_conditions.RecipesLoadedAndUnlockedCondition;
import de.dafuqs.spectrum.recipe.anvil_crushing.AnvilCrushingRecipe;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumRecipe;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipe;
import de.dafuqs.spectrum.recipe.enchanter.EnchantmentUpgradeRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.DragonrotConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.GooConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import de.dafuqs.spectrum.recipe.fusion_shrine.FusionShrineRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.recipe.titration_barrel.TitrationBarrelRecipe;
import de.dafuqs.spectrum.registries.SpectrumRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class ModonomiconCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	// Entry Types
	public static final ResourceLocation WEB_LINK_ENTRY_TYPE = SpectrumCommon.locate("web_link");
	
    // Page Types
    public static final ResourceLocation ANVIL_CRUSHING_PAGE = SpectrumCommon.locate("anvil_crushing");
    public static final ResourceLocation PEDESTAL_CRAFTING_PAGE = SpectrumCommon.locate("pedestal_crafting");
    public static final ResourceLocation FUSION_SHRINE_CRAFTING_PAGE = SpectrumCommon.locate("fusion_shrine_crafting");
    public static final ResourceLocation ENCHANTER_CRAFTING_PAGE = SpectrumCommon.locate("enchanter_crafting");
    public static final ResourceLocation ENCHANTER_UPGRADING_PAGE = SpectrumCommon.locate("enchanter_upgrading");
    public static final ResourceLocation POTION_WORKSHOP_BREWING_PAGE = SpectrumCommon.locate("potion_workshop_brewing");
    public static final ResourceLocation POTION_WORKSHOP_CRAFTING_PAGE = SpectrumCommon.locate("potion_workshop_crafting");
    public static final ResourceLocation SPIRIT_INSTILLER_CRAFTING_PAGE = SpectrumCommon.locate("spirit_instiller_crafting");
    public static final ResourceLocation LIQUID_CRYSTAL_CONVERTING_PAGE = SpectrumCommon.locate("liquid_crystal_converting");
    public static final ResourceLocation MIDNIGHT_SOLUTION_CONVERTING_PAGE = SpectrumCommon.locate("midnight_solution_converting");
    public static final ResourceLocation DRAGONROT_CONVERTING_PAGE = SpectrumCommon.locate("dragonrot_converting");
	public static final ResourceLocation GOO_CONVERTING_PAGE = SpectrumCommon.locate("goo_converting");
    public static final ResourceLocation CRYSTALLARIEUM_GROWING_PAGE = SpectrumCommon.locate("crystallarieum_growing");
    public static final ResourceLocation CINDERHEARTH_SMELTING_PAGE = SpectrumCommon.locate("cinderhearth_smelting");
    public static final ResourceLocation TITRATION_BARREL_FERMENTING_PAGE = SpectrumCommon.locate("titration_barrel_fermenting");
    public static final ResourceLocation STATUS_EFFECT_PAGE = SpectrumCommon.locate("status_effect");
    public static final ResourceLocation HINT_PAGE = SpectrumCommon.locate("hint");
    public static final ResourceLocation CHECKLIST_PAGE = SpectrumCommon.locate("checklist");
    public static final ResourceLocation CONFIRMATION_BUTTON_PAGE = SpectrumCommon.locate("confirmation_button");
    public static final ResourceLocation SNIPPET_PAGE = SpectrumCommon.locate("snippet");
    public static final ResourceLocation LINK_PAGE = SpectrumCommon.locate("link");
    public static final ResourceLocation COLLECTION_PAGE = SpectrumCommon.locate("collection");
    public static final ResourceLocation PRIMORDIAL_FIRE_BURNING_PAGE = SpectrumCommon.locate("primordial_fire_burning");
    
    // Unlock Conditions
    public static final ResourceLocation ENCHANTMENT_REGISTERED = SpectrumCommon.locate("enchantment_registered");
    public static final ResourceLocation RECIPE_LOADED_AND_UNLOCKED = SpectrumCommon.locate("recipe_loaded_and_unlocked");
    public static final ResourceLocation NOT = SpectrumCommon.locate("not");
	
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
        registerGatedRecipePage(ANVIL_CRUSHING_PAGE, SpectrumRecipeTypes.ANVIL_CRUSHING, false);
        registerGatedRecipePage(PEDESTAL_CRAFTING_PAGE, SpectrumRecipeTypes.PEDESTAL, false);
        registerGatedRecipePage(FUSION_SHRINE_CRAFTING_PAGE, SpectrumRecipeTypes.FUSION_SHRINE, false);
        registerGatedRecipePage(ENCHANTER_CRAFTING_PAGE, SpectrumRecipeTypes.ENCHANTER, false);
        registerGatedRecipePage(ENCHANTER_UPGRADING_PAGE, SpectrumRecipeTypes.ENCHANTMENT_UPGRADE, false);
        registerGatedRecipePage(POTION_WORKSHOP_BREWING_PAGE, SpectrumRecipeTypes.POTION_WORKSHOP_BREWING, false);
        registerGatedRecipePage(POTION_WORKSHOP_CRAFTING_PAGE, SpectrumRecipeTypes.POTION_WORKSHOP_CRAFTING, false);
        registerGatedRecipePage(SPIRIT_INSTILLER_CRAFTING_PAGE, SpectrumRecipeTypes.SPIRIT_INSTILLING, true);
        registerGatedRecipePage(LIQUID_CRYSTAL_CONVERTING_PAGE, SpectrumRecipeTypes.LIQUID_CRYSTAL_CONVERTING, false);
        registerGatedRecipePage(MIDNIGHT_SOLUTION_CONVERTING_PAGE, SpectrumRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING, false);
        registerGatedRecipePage(DRAGONROT_CONVERTING_PAGE, SpectrumRecipeTypes.DRAGONROT_CONVERTING, false);
		registerGatedRecipePage(GOO_CONVERTING_PAGE, SpectrumRecipeTypes.GOO_CONVERTING, false);
        registerGatedRecipePage(CRYSTALLARIEUM_GROWING_PAGE, SpectrumRecipeTypes.CRYSTALLARIEUM, false);
        registerGatedRecipePage(CINDERHEARTH_SMELTING_PAGE, SpectrumRecipeTypes.CINDERHEARTH, false);
        registerGatedRecipePage(TITRATION_BARREL_FERMENTING_PAGE, SpectrumRecipeTypes.TITRATION_BARREL, true);
        registerGatedRecipePage(PRIMORDIAL_FIRE_BURNING_PAGE, SpectrumRecipeTypes.PRIMORDIAL_FIRE_BURNING, false);

        LoaderRegistry.registerPageLoader(STATUS_EFFECT_PAGE, (BookPageJsonLoader<?>) BookStatusEffectPage::fromJson, BookStatusEffectPage::fromNetwork);
        LoaderRegistry.registerPageLoader(HINT_PAGE, (BookPageJsonLoader<?>) BookHintPage::fromJson, BookHintPage::fromNetwork);
        LoaderRegistry.registerPageLoader(CHECKLIST_PAGE, (BookPageJsonLoader<?>) BookChecklistPage::fromJson, BookChecklistPage::fromNetwork);
        LoaderRegistry.registerPageLoader(CONFIRMATION_BUTTON_PAGE, (BookPageJsonLoader<?>) BookConfirmationButtonPage::fromJson, BookConfirmationButtonPage::fromNetwork);
        LoaderRegistry.registerPageLoader(SNIPPET_PAGE, (BookPageJsonLoader<?>) BookSnippetPage::fromJson, BookSnippetPage::fromNetwork);
        LoaderRegistry.registerPageLoader(LINK_PAGE, (BookPageJsonLoader<?>) BookLinkPage::fromJson, BookLinkPage::fromNetwork);
        LoaderRegistry.registerPageLoader(COLLECTION_PAGE, (BookPageJsonLoader<?>) BookCollectionPage::fromJson, BookCollectionPage::fromNetwork);
    }
    
    private void registerGatedRecipePage(ResourceLocation id, RecipeType<? extends GatedRecipe<?>> recipeType, boolean supportsTwoRecipesOnOnePage) {
        BookPageJsonLoader<?> fromJson = (entryId, json, provider) -> BookGatedRecipePage.fromJson(entryId, id, recipeType, json, supportsTwoRecipesOnOnePage, provider);
        NetworkLoader<? extends BookPage> fromNetwork = buffer -> BookGatedRecipePage.fromNetwork(id, recipeType, buffer);
        LoaderRegistry.registerPageLoader(id, fromJson, fromNetwork);
    }
	
	private void registerUnlockConditions() {
        LoaderRegistry.registerConditionLoader(ENCHANTMENT_REGISTERED, (BookConditionJsonLoader<?>) EnchantmentRegisteredCondition::fromJson, EnchantmentRegisteredCondition::fromNetwork);
        LoaderRegistry.registerConditionLoader(RECIPE_LOADED_AND_UNLOCKED, (BookConditionJsonLoader<?>) RecipesLoadedAndUnlockedCondition::fromJson, RecipesLoadedAndUnlockedCondition::fromNetwork);
        LoaderRegistry.registerConditionLoader(NOT, (BookConditionJsonLoader<?>) NotCondition::fromJson, NotCondition::fromNetwork);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void registerClient() {
        PageRendererRegistry.registerPageRenderer(ANVIL_CRUSHING_PAGE, p -> new BookAnvilCrushingPageRenderer((BookGatedRecipePage<AnvilCrushingRecipe>) p));
        PageRendererRegistry.registerPageRenderer(PEDESTAL_CRAFTING_PAGE, p -> new BookPedestalCraftingPageRenderer((BookGatedRecipePage<PedestalRecipe>) p));
        PageRendererRegistry.registerPageRenderer(FUSION_SHRINE_CRAFTING_PAGE, p -> new BookFusionShrineCraftingPageRenderer((BookGatedRecipePage<FusionShrineRecipe>) p));
        PageRendererRegistry.registerPageRenderer(ENCHANTER_CRAFTING_PAGE, p -> new BookEnchanterCraftingPageRenderer((BookGatedRecipePage<EnchanterRecipe>) p));
        PageRendererRegistry.registerPageRenderer(ENCHANTER_UPGRADING_PAGE, p -> new BookEnchanterUpgradingPageRenderer((BookGatedRecipePage<EnchantmentUpgradeRecipe>) p));
        PageRendererRegistry.registerPageRenderer(POTION_WORKSHOP_BREWING_PAGE, p -> new BookPotionWorkshopPageRenderer<>((BookGatedRecipePage<PotionWorkshopBrewingRecipe>) p));
        PageRendererRegistry.registerPageRenderer(POTION_WORKSHOP_CRAFTING_PAGE, p -> new BookPotionWorkshopPageRenderer<>((BookGatedRecipePage<PotionWorkshopCraftingRecipe>) p));
        PageRendererRegistry.registerPageRenderer(SPIRIT_INSTILLER_CRAFTING_PAGE, p -> new BookSpiritInstillerCraftingPageRenderer((BookGatedRecipePage<SpiritInstillerRecipe>) p));
        PageRendererRegistry.registerPageRenderer(CRYSTALLARIEUM_GROWING_PAGE, p -> new BookCrystallarieumGrowingPageRenderer((BookGatedRecipePage<CrystallarieumRecipe>) p));
        PageRendererRegistry.registerPageRenderer(CINDERHEARTH_SMELTING_PAGE, p -> new BookCinderhearthSmeltingPageRenderer((BookGatedRecipePage<CinderhearthRecipe>) p));
        PageRendererRegistry.registerPageRenderer(TITRATION_BARREL_FERMENTING_PAGE, p -> new BookTitrationBarrelFermentingPageRenderer((BookGatedRecipePage<TitrationBarrelRecipe>) p));
        PageRendererRegistry.registerPageRenderer(PRIMORDIAL_FIRE_BURNING_PAGE, p -> new BookPrimordialFireBurningPageRenderer<>((BookGatedRecipePage<PrimordialFireBurningRecipe>) p));
        
        PageRendererRegistry.registerPageRenderer(STATUS_EFFECT_PAGE, p -> new BookStatusEffectPageRenderer((BookStatusEffectPage) p));
        PageRendererRegistry.registerPageRenderer(HINT_PAGE, p -> new BookHintPageRenderer((BookHintPage) p));
        PageRendererRegistry.registerPageRenderer(CHECKLIST_PAGE, p -> new BookChecklistPageRenderer((BookChecklistPage) p));
        PageRendererRegistry.registerPageRenderer(CONFIRMATION_BUTTON_PAGE, p -> new BookConfirmationButtonPageRenderer((BookConfirmationButtonPage) p));
        PageRendererRegistry.registerPageRenderer(SNIPPET_PAGE, p -> new BookSnippetPageRenderer((BookSnippetPage) p));
        PageRendererRegistry.registerPageRenderer(LINK_PAGE, p -> new BookLinkPageRenderer((BookLinkPage) p));
        PageRendererRegistry.registerPageRenderer(COLLECTION_PAGE, p -> new BookCollectionPageRenderer((BookCollectionPage) p));

        PageRendererRegistry.registerPageRenderer(LIQUID_CRYSTAL_CONVERTING_PAGE, p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<LiquidCrystalConvertingRecipe>) p) {
            @Override
            public ResourceLocation getBackgroundTexture() {
                return SpectrumCommon.locate("textures/gui/guidebook/liquid_crystal.png");
            }
        });

        PageRendererRegistry.registerPageRenderer(MIDNIGHT_SOLUTION_CONVERTING_PAGE, p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<MidnightSolutionConvertingRecipe>) p) {
            @Override
            public ResourceLocation getBackgroundTexture() {
                return SpectrumCommon.locate("textures/gui/guidebook/midnight_solution.png");
            }
        });

        PageRendererRegistry.registerPageRenderer(DRAGONROT_CONVERTING_PAGE, p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<DragonrotConvertingRecipe>) p) {
            @Override
            public ResourceLocation getBackgroundTexture() {
                return SpectrumCommon.locate("textures/gui/guidebook/dragonrot.png");
            }
        });
		
		PageRendererRegistry.registerPageRenderer(GOO_CONVERTING_PAGE, p -> new BookFluidConvertingPageRenderer<>((BookGatedRecipePage<GooConvertingRecipe>) p) {
            @Override
            public ResourceLocation getBackgroundTexture() {
				return SpectrumCommon.locate("textures/gui/guidebook/goo.png");
            }
        });
    }

}
