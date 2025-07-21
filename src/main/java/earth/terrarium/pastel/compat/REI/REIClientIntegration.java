package earth.terrarium.pastel.compat.REI;

import dev.architectury.event.EventResult;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.idols.FirestarterIdolBlock;
import earth.terrarium.pastel.blocks.idols.FreezingIdolBlock;
import earth.terrarium.pastel.compat.REI.plugins.AnvilCrushingCategory;
import earth.terrarium.pastel.compat.REI.plugins.AnvilCrushingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.BlockToBlockWithChanceDisplay;
import earth.terrarium.pastel.compat.REI.plugins.CinderhearthCategory;
import earth.terrarium.pastel.compat.REI.plugins.CinderhearthDisplay;
import earth.terrarium.pastel.compat.REI.plugins.CrystallarieumCategory;
import earth.terrarium.pastel.compat.REI.plugins.CrystallarieumDisplay;
import earth.terrarium.pastel.compat.REI.plugins.DragonrotConvertingCategory;
import earth.terrarium.pastel.compat.REI.plugins.DragonrotConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.EnchanterEnchantingCategory;
import earth.terrarium.pastel.compat.REI.plugins.EnchanterEnchantingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.EnchantmentUpgradeCategory;
import earth.terrarium.pastel.compat.REI.plugins.EnchantmentUpgradeDisplay;
import earth.terrarium.pastel.compat.REI.plugins.FreezingCategory;
import earth.terrarium.pastel.compat.REI.plugins.FreezingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.FusionShrineCategory;
import earth.terrarium.pastel.compat.REI.plugins.FusionShrineDisplay;
import earth.terrarium.pastel.compat.REI.plugins.HeatingCategory;
import earth.terrarium.pastel.compat.REI.plugins.HeatingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.HumusConvertingCategory;
import earth.terrarium.pastel.compat.REI.plugins.HumusConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.InkConvertingCategory;
import earth.terrarium.pastel.compat.REI.plugins.InkConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.LiquidCrystalConvertingCategory;
import earth.terrarium.pastel.compat.REI.plugins.LiquidCrystalConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.MidnightSolutionConvertingCategory;
import earth.terrarium.pastel.compat.REI.plugins.MidnightSolutionConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.NaturesStaffConversionsCategory;
import earth.terrarium.pastel.compat.REI.plugins.NaturesStaffConversionsDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PedestalCraftingCategory;
import earth.terrarium.pastel.compat.REI.plugins.PedestalCraftingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopBrewingCategory;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopBrewingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopCraftingCategory;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopCraftingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopReactingCategory;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopReactingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PrimordialFireBurningCategory;
import earth.terrarium.pastel.compat.REI.plugins.PrimordialFireBurningDisplay;
import earth.terrarium.pastel.compat.REI.plugins.SpiritInstillingCategory;
import earth.terrarium.pastel.compat.REI.plugins.SpiritInstillingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.TitrationBarrelCategory;
import earth.terrarium.pastel.compat.REI.plugins.TitrationBarrelDisplay;
import earth.terrarium.pastel.data_loaders.NaturesStaffConversionDataLoader;
import earth.terrarium.pastel.inventories.CinderhearthScreen;
import earth.terrarium.pastel.inventories.CinderhearthScreenHandler;
import earth.terrarium.pastel.inventories.PedestalScreen;
import earth.terrarium.pastel.inventories.PedestalScreenHandler;
import earth.terrarium.pastel.inventories.PotionWorkshopScreen;
import earth.terrarium.pastel.inventories.PotionWorkshopScreenHandler;
import earth.terrarium.pastel.recipe.InkConvertingRecipe;
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
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import earth.terrarium.pastel.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.ITitrationBarrelRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.stream.Stream;

@REIPluginClient
public class REIClientIntegration implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new PedestalCraftingCategory());
        registry.add(new AnvilCrushingCategory());
        registry.add(new FusionShrineCategory());
        registry.add(new NaturesStaffConversionsCategory());
        registry.add(new EnchanterEnchantingCategory());
        registry.add(new EnchantmentUpgradeCategory());
        registry.add(new PotionWorkshopBrewingCategory());
        registry.add(new PotionWorkshopCraftingCategory());
        registry.add(new PotionWorkshopReactingCategory());
        registry.add(new SpiritInstillingCategory());
        registry.add(new HumusConvertingCategory());
        registry.add(new LiquidCrystalConvertingCategory());
        registry.add(new MidnightSolutionConvertingCategory());
        registry.add(new DragonrotConvertingCategory());
        registry.add(new HeatingCategory());
        registry.add(new FreezingCategory());
        registry.add(new InkConvertingCategory());
        registry.add(new CrystallarieumCategory());
        registry.add(new CinderhearthCategory());
        registry.add(new TitrationBarrelCategory());
        registry.add(new PrimordialFireBurningCategory());

        EntryIngredient pedestals = EntryIngredient.of(
            EntryStacks.of(PastelBlocks.PEDESTAL_BASIC_TOPAZ.get()),
            EntryStacks.of(PastelBlocks.PEDESTAL_BASIC_AMETHYST.get()),
            EntryStacks.of(PastelBlocks.PEDESTAL_BASIC_CITRINE.get()),
            EntryStacks.of(PastelBlocks.PEDESTAL_ALL_BASIC.get()),
            EntryStacks.of(PastelBlocks.PEDESTAL_ONYX.get()),
            EntryStacks.of(PastelBlocks.PEDESTAL_MOONSTONE.get())
        );
        registry.addWorkstations(PastelPlugins.PEDESTAL_CRAFTING, pedestals);
        if (PastelCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
            registry.addWorkstations(BuiltinPlugin.CRAFTING, pedestals);
        }

        registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(PastelItems.CRAFTING_TABLET.get()));
        registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(PastelBlocks.FABRICATION_CHEST.get()));
        registry.addWorkstations(BuiltinPlugin.BLASTING, EntryStacks.of(PastelBlocks.CINDERHEARTH.get()));

        registry.addWorkstations(
            PastelPlugins.ANVIL_CRUSHING, EntryStacks.of(Blocks.ANVIL),
            EntryStacks.of(PastelBlocks.BEDROCK_ANVIL.get()), EntryStacks.of(PastelBlocks.STRATINE_FLOATBLOCK.get()),
            EntryStacks.of(PastelBlocks.PALTAERIA_FLOATBLOCK.get())
        );
        registry.addWorkstations(
            PastelPlugins.FUSION_SHRINE, EntryIngredient.of(
                EntryStacks.of(PastelBlocks.FUSION_SHRINE_BASALT.get()),
                EntryStacks.of(PastelBlocks.FUSION_SHRINE_CALCITE.get())
            )
        );
        registry.addWorkstations(PastelPlugins.NATURES_STAFF, EntryStacks.of(PastelItems.NATURES_STAFF.get()));
        registry.addWorkstations(PastelPlugins.HEATING, EntryStacks.of(PastelBlocks.BLAZE_IDOL.get()));
        registry.addWorkstations(PastelPlugins.FREEZING, EntryStacks.of(PastelBlocks.POLAR_BEAR_IDOL.get()));
        registry.addWorkstations(PastelPlugins.ENCHANTER_CRAFTING, EntryStacks.of(PastelBlocks.ENCHANTER.get()));
        registry.addWorkstations(PastelPlugins.ENCHANTMENT_UPGRADE, EntryStacks.of(PastelBlocks.ENCHANTER.get()));
        registry.addWorkstations(PastelPlugins.HUMUS_CONVERTING, EntryStacks.of(PastelItems.HUMUS_BUCKET.get()));
        registry.addWorkstations(
            PastelPlugins.LIQUID_CRYSTAL_CONVERTING, EntryStacks.of(PastelItems.LIQUID_CRYSTAL_BUCKET.get()));
        registry.addWorkstations(
            PastelPlugins.MIDNIGHT_SOLUTION_CONVERTING, EntryStacks.of(PastelItems.MIDNIGHT_SOLUTION_BUCKET.get()));
        registry.addWorkstations(
            PastelPlugins.DRAGONROT_CONVERTING, EntryStacks.of(PastelItems.DRAGONROT_BUCKET.get()));
        registry.addWorkstations(PastelPlugins.SPIRIT_INSTILLER, EntryStacks.of(PastelBlocks.SPIRIT_INSTILLER.get()));
        registry.addWorkstations(PastelPlugins.INK_CONVERTING, EntryStacks.of(PastelBlocks.COLOR_PICKER.get()));
        registry.addWorkstations(PastelPlugins.CRYSTALLARIEUM, EntryStacks.of(PastelBlocks.CRYSTALLARIEUM.get()));
        registry.addWorkstations(
            PastelPlugins.POTION_WORKSHOP_BREWING, EntryStacks.of(PastelBlocks.POTION_WORKSHOP.get()));
        registry.addWorkstations(
            PastelPlugins.POTION_WORKSHOP_CRAFTING, EntryStacks.of(PastelBlocks.POTION_WORKSHOP.get()));
        registry.addWorkstations(
            PastelPlugins.POTION_WORKSHOP_REACTING, EntryStacks.of(PastelBlocks.POTION_WORKSHOP.get()));
        registry.addWorkstations(PastelPlugins.CINDERHEARTH, EntryStacks.of(PastelBlocks.CINDERHEARTH.get()));
        registry.addWorkstations(PastelPlugins.TITRATION_BARREL, EntryStacks.of(PastelBlocks.TITRATION_BARREL.get()));
        registry.addWorkstations(
            PastelPlugins.PRIMORDIAL_FIRE_BURNING, EntryStacks.of(PastelItems.DOOMBLOOM_SEED.get()),
            EntryStacks.of(PastelItems.PRIMORDIAL_LIGHTER.get()),
            EntryStacks.of(PastelBlocks.INCANDESCENT_AMALGAM.get()), EntryStacks.of(PastelItems.PIPE_BOMB.get())
        );
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(
            AnvilCrushingRecipe.class, PastelRecipeTypes.ANVIL_CRUSHING, AnvilCrushingDisplay::new);
        registry.registerRecipeFiller(PedestalRecipe.class, PastelRecipeTypes.PEDESTAL, PedestalCraftingDisplay::new);
        registry.registerRecipeFiller(
            FusionShrineRecipe.class, PastelRecipeTypes.FUSION_SHRINE, FusionShrineDisplay::new);
        registry.registerRecipeFiller(
            EnchanterCraftingRecipe.class, PastelRecipeTypes.ENCHANTER, EnchanterEnchantingDisplay::new);
        registry.registerRecipeFiller(
            EnchantmentUpgradeRecipe.class, PastelRecipeTypes.ENCHANTMENT_UPGRADE, EnchantmentUpgradeDisplay::new);
        registry.registerRecipeFiller(
            PotionWorkshopBrewingRecipe.class, PastelRecipeTypes.POTION_WORKSHOP_BREWING,
            PotionWorkshopBrewingDisplay::new
        );
        registry.registerRecipeFiller(
            PotionWorkshopCraftingRecipe.class, PastelRecipeTypes.POTION_WORKSHOP_CRAFTING,
            PotionWorkshopCraftingDisplay::new
        );
        registry.registerRecipeFiller(
            SpiritInstillerRecipe.class, PastelRecipeTypes.SPIRIT_INSTILLING, SpiritInstillingDisplay::new);
        registry.registerRecipeFiller(
            HumusConvertingRecipe.class, PastelRecipeTypes.HUMUS_CONVERTING, HumusConvertingDisplay::new);
        registry.registerRecipeFiller(
            LiquidCrystalConvertingRecipe.class, PastelRecipeTypes.LIQUID_CRYSTAL_CONVERTING,
            LiquidCrystalConvertingDisplay::new
        );
        registry.registerRecipeFiller(
            MidnightSolutionConvertingRecipe.class, PastelRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING,
            MidnightSolutionConvertingDisplay::new
        );
        registry.registerRecipeFiller(
            DragonrotConvertingRecipe.class, PastelRecipeTypes.DRAGONROT_CONVERTING, DragonrotConvertingDisplay::new);
        registry.registerRecipeFiller(
            InkConvertingRecipe.class, PastelRecipeTypes.INK_CONVERTING, InkConvertingDisplay::new);
        registry.registerRecipeFiller(
            PotionWorkshopReactingRecipe.class, PastelRecipeTypes.POTION_WORKSHOP_REACTING,
            PotionWorkshopReactingDisplay::new
        );
        registry.registerRecipeFiller(
            CrystallarieumRecipe.class, PastelRecipeTypes.CRYSTALLARIEUM, CrystallarieumDisplay::new);
        registry.registerRecipeFiller(
            CinderhearthRecipe.class, PastelRecipeTypes.CINDERHEARTH, CinderhearthDisplay::new);
        registry.registerRecipeFiller(
            ITitrationBarrelRecipe.class, PastelRecipeTypes.TITRATION_BARREL, TitrationBarrelDisplay::new);
        registry.registerRecipeFiller(
            PrimordialFireBurningRecipe.class, PastelRecipeTypes.PRIMORDIAL_FIRE_BURNING,
            PrimordialFireBurningDisplay::new
        );

        NaturesStaffConversionDataLoader.CONVERSIONS.forEach((key, value) -> registry.add(
            new NaturesStaffConversionsDisplay(
                EntryStacks.of(key), EntryStacks.of(value.getBlock()),
                NaturesStaffConversionDataLoader.UNLOCK_IDENTIFIERS.getOrDefault(
                    key, null)
            )));
        FreezingIdolBlock.FREEZING_STATE_MAP.forEach((key, value) -> registry.add(new FreezingDisplay(
            BlockToBlockWithChanceDisplay.blockToEntryStack(key.getBlock()),
            BlockToBlockWithChanceDisplay.blockToEntryStack(value.getA()
                                                                 .getBlock()), value.getB()
        )));
        FreezingIdolBlock.FREEZING_MAP.forEach((key, value) -> registry.add(new FreezingDisplay(
            BlockToBlockWithChanceDisplay.blockToEntryStack(key), BlockToBlockWithChanceDisplay.blockToEntryStack(
            value.getA()
                 .getBlock()), value.getB()
        )));
        FirestarterIdolBlock.BURNING_MAP.forEach((key, value) -> registry.add(new HeatingDisplay(
            BlockToBlockWithChanceDisplay.blockToEntryStack(key), BlockToBlockWithChanceDisplay.blockToEntryStack(
            value.getA()
                 .getBlock()), value.getB()
        )));


        registry.registerVisibilityPredicate((category, display) -> {
            // do not list recipes in REI at all, until they are unlocked
            // secret recipes are never shown
            if (display instanceof GatedRecipeDisplay gatedRecipeDisplay) {
                if (!gatedRecipeDisplay.isUnlocked() &&
                    (PastelCommon.CONFIG.REIListsRecipesAsNotUnlocked || gatedRecipeDisplay.isSecret())) {
                    return EventResult.interruptFalse();
                }
            }
            return EventResult.pass();
        });

    }

    /**
     * Where in the screens gui the player has to click
     * to get to the recipe overview
     * Only use for recipe types that are crafted in a gui
     */
    @Override
    public void registerScreens(ScreenRegistry registry) {
        // Since the pedestal can craft both vanilla and pedestal recipes
        // we split the "arrow" part of the gui into two parts => faster access
        registry.registerContainerClickArea(
            new Rectangle(89, 37, 10, 15), PedestalScreen.class, BuiltinPlugin.CRAFTING);
        registry.registerContainerClickArea(
            new Rectangle(100, 37, 11, 15), PedestalScreen.class, PastelPlugins.PEDESTAL_CRAFTING);

        registry.registerContainerClickArea(
            new Rectangle(28, 41, 10, 42), PotionWorkshopScreen.class, PastelPlugins.POTION_WORKSHOP_BREWING);
        registry.registerContainerClickArea(
            new Rectangle(28, 41, 10, 42), PotionWorkshopScreen.class, PastelPlugins.POTION_WORKSHOP_CRAFTING);

        registry.registerContainerClickArea(
            new Rectangle(35, 33, 22, 15), CinderhearthScreen.class, PastelPlugins.CINDERHEARTH);
        registry.registerContainerClickArea(
            new Rectangle(35, 33, 22, 15), CinderhearthScreen.class, BuiltinPlugin.BLASTING);

        registry.registerDecider(REIOverlayDecider.INSTANCE);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        // REI input magic to prevent moving incorrect amount of gem powder yet still complain about a lack of such
        registry.register(SimpleTransferHandlerExtension.create(
            PedestalScreenHandler.class, PastelPlugins.PEDESTAL_CRAFTING,
            new SimpleTransferHandler.IntRange(0, 9),
            List.of(new SimpleTransferHandler.IntRange(9, 15), new SimpleTransferHandler.IntRange(16, 52))
        ));
        if (PastelCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
            registry.register(SimpleTransferHandlerExtension.create(
                PedestalScreenHandler.class, BuiltinPlugin.CRAFTING,
                new SimpleTransferHandler.IntRange(0, 9), new SimpleTransferHandler.IntRange(16, 52)
            ));
        }
        registry.register(SimpleTransferHandlerExtension.create(
            CinderhearthScreenHandler.class, PastelPlugins.CINDERHEARTH,
            new SimpleTransferHandler.IntRange(2, 3), new SimpleTransferHandler.IntRange(11, 47)
        ));
        registry.register(SimpleTransferHandlerExtension.create(
            CinderhearthScreenHandler.class, BuiltinPlugin.BLASTING,
            new SimpleTransferHandler.IntRange(2, 3), new SimpleTransferHandler.IntRange(11, 47)
        ));
        registry.register(SimpleTransferHandlerExtension.create(
            PotionWorkshopScreenHandler.class, PastelPlugins.POTION_WORKSHOP_BREWING,
            new SimpleTransferHandler.IntRange(0, 9), new SimpleTransferHandler.IntRange(21, 57)
        ));
        registry.register(SimpleTransferHandlerExtension.create(
            PotionWorkshopScreenHandler.class, PastelPlugins.POTION_WORKSHOP_CRAFTING,
            new SimpleTransferHandler.IntRange(0, 9), new SimpleTransferHandler.IntRange(21, 57)
        ));
    }

    @SuppressWarnings("UnstableApiUsage")
    interface SimpleTransferHandlerExtension extends SimpleTransferHandler {
        // Because REI decided to give the create method with the inventory slots argument a different container
        // class type.
        // Pretty much identical to the original otherwise (except with slot handling changed to resemble the EMI
        // counterpart)
        static <C extends AbstractContainerMenu, D extends Display> SimpleTransferHandler create(
            Class<? extends C> containerClass,
            CategoryIdentifier<D> categoryIdentifier,
            SimpleTransferHandler.IntRange inputSlots,
            SimpleTransferHandler.IntRange inventorySlots
        ) {
            return new SimpleTransferHandler() {
                @Override
                public ApplicabilityResult checkApplicable(Context context) {
                    if (!containerClass.isInstance(context.getMenu())
                        || !categoryIdentifier.equals(context.getDisplay()
                                                             .getCategoryIdentifier())
                        || context.getContainerScreen() == null) {
                        return ApplicabilityResult.createNotApplicable();
                    } else {
                        return ApplicabilityResult.createApplicable();
                    }
                }

                @Override
                public Iterable<SlotAccessor> getInputSlots(Context context) {
                    return context.getMenu()
                        .slots.subList(inputSlots.min(), inputSlots.maxExclusive())
                              .stream()
                              .map(SlotAccessor::fromSlot)
                              .toList();
                }

                @Override
                public Iterable<SlotAccessor> getInventorySlots(Context context) {
                    return context.getMenu()
                        .slots.subList(inventorySlots.min(), inventorySlots.maxExclusive())
                              .stream()
                              .map(SlotAccessor::fromSlot)
                              .toList();
                }
            };
        }

        static <C extends AbstractContainerMenu, D extends Display> SimpleTransferHandler create(
            Class<? extends C> containerClass,
            CategoryIdentifier<D> categoryIdentifier,
            SimpleTransferHandler.IntRange inputSlots,
            List<IntRange> inventorySlotsRanges
        ) {
            return new SimpleTransferHandler() {
                @Override
                public ApplicabilityResult checkApplicable(Context context) {
                    if (!containerClass.isInstance(context.getMenu())
                        || !categoryIdentifier.equals(context.getDisplay()
                                                             .getCategoryIdentifier())
                        || context.getContainerScreen() == null) {
                        return ApplicabilityResult.createNotApplicable();
                    } else {
                        return ApplicabilityResult.createApplicable();
                    }
                }

                @Override
                public Iterable<SlotAccessor> getInputSlots(Context context) {
                    return context.getMenu()
                        .slots.subList(inputSlots.min(), inputSlots.maxExclusive())
                              .stream()
                              .map(SlotAccessor::fromSlot)
                              .toList();
                }

                @Override
                public Iterable<SlotAccessor> getInventorySlots(Context context) {
                    Stream<SlotAccessor> s = Stream.empty();
                    for (SimpleTransferHandler.IntRange range : inventorySlotsRanges) {
                        s = Stream.concat(
                            s, context.getMenu()
                                .slots.subList(range.min(), range.maxExclusive())
                                      .stream()
                                      .map(SlotAccessor::fromSlot)
                        );
                    }
                    return s.toList();
                }
            };
        }
    }
}
