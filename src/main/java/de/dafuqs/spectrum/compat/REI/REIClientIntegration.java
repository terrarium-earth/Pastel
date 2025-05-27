package de.dafuqs.spectrum.compat.REI;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.blocks.idols.FirestarterIdolBlock;
import de.dafuqs.spectrum.blocks.idols.FreezingIdolBlock;
import de.dafuqs.spectrum.compat.REI.plugins.AnvilCrushingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.AnvilCrushingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.BlockToBlockWithChanceDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.CinderhearthCategory;
import de.dafuqs.spectrum.compat.REI.plugins.CinderhearthDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.CrystallarieumCategory;
import de.dafuqs.spectrum.compat.REI.plugins.CrystallarieumDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.DragonrotConvertingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.DragonrotConvertingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.EnchanterEnchantingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.EnchanterEnchantingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.EnchantmentUpgradeCategory;
import de.dafuqs.spectrum.compat.REI.plugins.EnchantmentUpgradeDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.FreezingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.FreezingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.FusionShrineCategory;
import de.dafuqs.spectrum.compat.REI.plugins.FusionShrineDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.GooConvertingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.GooConvertingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.HeatingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.HeatingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.InkConvertingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.InkConvertingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.LiquidCrystalConvertingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.LiquidCrystalConvertingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.MidnightSolutionConvertingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.MidnightSolutionConvertingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.NaturesStaffConversionsCategory;
import de.dafuqs.spectrum.compat.REI.plugins.NaturesStaffConversionsDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.PedestalCraftingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.PedestalCraftingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.PotionWorkshopBrewingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.PotionWorkshopBrewingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.PotionWorkshopCraftingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.PotionWorkshopCraftingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.PotionWorkshopReactingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.PotionWorkshopReactingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.PrimordialFireBurningCategory;
import de.dafuqs.spectrum.compat.REI.plugins.PrimordialFireBurningDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.SpiritInstillingCategory;
import de.dafuqs.spectrum.compat.REI.plugins.SpiritInstillingDisplay;
import de.dafuqs.spectrum.compat.REI.plugins.TitrationBarrelCategory;
import de.dafuqs.spectrum.compat.REI.plugins.TitrationBarrelDisplay;
import de.dafuqs.spectrum.data_loaders.NaturesStaffConversionDataLoader;
import de.dafuqs.spectrum.inventories.CinderhearthScreen;
import de.dafuqs.spectrum.inventories.CinderhearthScreenHandler;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.inventories.PedestalScreenHandler;
import de.dafuqs.spectrum.inventories.PotionWorkshopScreen;
import de.dafuqs.spectrum.inventories.PotionWorkshopScreenHandler;
import de.dafuqs.spectrum.recipe.InkConvertingRecipe;
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
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.recipe.titration_barrel.ITitrationBarrelRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeTypes;
import dev.architectury.event.EventResult;
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
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
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
		registry.add(new GooConvertingCategory());
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
				EntryStacks.of(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ.get()),
				EntryStacks.of(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.get()),
				EntryStacks.of(SpectrumBlocks.PEDESTAL_BASIC_CITRINE.get()),
				EntryStacks.of(SpectrumBlocks.PEDESTAL_ALL_BASIC.get()),
				EntryStacks.of(SpectrumBlocks.PEDESTAL_ONYX.get()),
				EntryStacks.of(SpectrumBlocks.PEDESTAL_MOONSTONE.get())
		);
		registry.addWorkstations(SpectrumPlugins.PEDESTAL_CRAFTING, pedestals);
		if (SpectrumCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
			registry.addWorkstations(BuiltinPlugin.CRAFTING, pedestals);
		}
		
		registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(SpectrumItems.CRAFTING_TABLET));
		registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(SpectrumBlocks.FABRICATION_CHEST.get()));
		registry.addWorkstations(BuiltinPlugin.BLASTING, EntryStacks.of(SpectrumBlocks.CINDERHEARTH.get()));
		
		registry.addWorkstations(SpectrumPlugins.ANVIL_CRUSHING, EntryStacks.of(Blocks.ANVIL), EntryStacks.of(SpectrumBlocks.BEDROCK_ANVIL.get()), EntryStacks.of(SpectrumBlocks.STRATINE_FLOATBLOCK.get()), EntryStacks.of(SpectrumBlocks.PALTAERIA_FLOATBLOCK.get()));
		registry.addWorkstations(SpectrumPlugins.FUSION_SHRINE, EntryIngredient.of(EntryStacks.of(SpectrumBlocks.FUSION_SHRINE_BASALT.get()), EntryStacks.of(SpectrumBlocks.FUSION_SHRINE_CALCITE.get())));
		registry.addWorkstations(SpectrumPlugins.NATURES_STAFF, EntryStacks.of(SpectrumItems.NATURES_STAFF));
		registry.addWorkstations(SpectrumPlugins.HEATING, EntryStacks.of(SpectrumBlocks.BLAZE_IDOL.get()));
		registry.addWorkstations(SpectrumPlugins.FREEZING, EntryStacks.of(SpectrumBlocks.POLAR_BEAR_IDOL.get()));
		registry.addWorkstations(SpectrumPlugins.ENCHANTER_CRAFTING, EntryStacks.of(SpectrumBlocks.ENCHANTER.get()));
		registry.addWorkstations(SpectrumPlugins.ENCHANTMENT_UPGRADE, EntryStacks.of(SpectrumBlocks.ENCHANTER.get()));
		registry.addWorkstations(SpectrumPlugins.GOO_CONVERTING, EntryStacks.of(SpectrumItems.GOO_BUCKET));
		registry.addWorkstations(SpectrumPlugins.LIQUID_CRYSTAL_CONVERTING, EntryStacks.of(SpectrumItems.LIQUID_CRYSTAL_BUCKET));
		registry.addWorkstations(SpectrumPlugins.MIDNIGHT_SOLUTION_CONVERTING, EntryStacks.of(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET));
		registry.addWorkstations(SpectrumPlugins.DRAGONROT_CONVERTING, EntryStacks.of(SpectrumItems.DRAGONROT_BUCKET));
		registry.addWorkstations(SpectrumPlugins.SPIRIT_INSTILLER, EntryStacks.of(SpectrumBlocks.SPIRIT_INSTILLER.get()));
		registry.addWorkstations(SpectrumPlugins.INK_CONVERTING, EntryStacks.of(SpectrumBlocks.COLOR_PICKER.get()));
		registry.addWorkstations(SpectrumPlugins.CRYSTALLARIEUM, EntryStacks.of(SpectrumBlocks.CRYSTALLARIEUM.get()));
		registry.addWorkstations(SpectrumPlugins.POTION_WORKSHOP_BREWING, EntryStacks.of(SpectrumBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstations(SpectrumPlugins.POTION_WORKSHOP_CRAFTING, EntryStacks.of(SpectrumBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstations(SpectrumPlugins.POTION_WORKSHOP_REACTING, EntryStacks.of(SpectrumBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstations(SpectrumPlugins.CINDERHEARTH, EntryStacks.of(SpectrumBlocks.CINDERHEARTH.get()));
		registry.addWorkstations(SpectrumPlugins.TITRATION_BARREL, EntryStacks.of(SpectrumBlocks.TITRATION_BARREL.get()));
		registry.addWorkstations(SpectrumPlugins.PRIMORDIAL_FIRE_BURNING, EntryStacks.of(SpectrumItems.DOOMBLOOM_SEED), EntryStacks.of(SpectrumItems.PRIMORDIAL_LIGHTER), EntryStacks.of(SpectrumBlocks.INCANDESCENT_AMALGAM.get()), EntryStacks.of(SpectrumItems.PIPE_BOMB));
	}
	
	@Override
	public void registerDisplays(DisplayRegistry registry) {
		registry.registerRecipeFiller(AnvilCrushingRecipe.class, SpectrumRecipeTypes.ANVIL_CRUSHING, AnvilCrushingDisplay::new);
		registry.registerRecipeFiller(PedestalRecipe.class, SpectrumRecipeTypes.PEDESTAL, PedestalCraftingDisplay::new);
		registry.registerRecipeFiller(FusionShrineRecipe.class, SpectrumRecipeTypes.FUSION_SHRINE, FusionShrineDisplay::new);
		registry.registerRecipeFiller(EnchanterRecipe.class, SpectrumRecipeTypes.ENCHANTER, EnchanterEnchantingDisplay::new);
		registry.registerRecipeFiller(EnchantmentUpgradeRecipe.class, SpectrumRecipeTypes.ENCHANTMENT_UPGRADE, EnchantmentUpgradeDisplay::new);
		registry.registerRecipeFiller(PotionWorkshopBrewingRecipe.class, SpectrumRecipeTypes.POTION_WORKSHOP_BREWING, PotionWorkshopBrewingDisplay::new);
		registry.registerRecipeFiller(PotionWorkshopCraftingRecipe.class, SpectrumRecipeTypes.POTION_WORKSHOP_CRAFTING, PotionWorkshopCraftingDisplay::new);
		registry.registerRecipeFiller(SpiritInstillerRecipe.class, SpectrumRecipeTypes.SPIRIT_INSTILLING, SpiritInstillingDisplay::new);
		registry.registerRecipeFiller(GooConvertingRecipe.class, SpectrumRecipeTypes.GOO_CONVERTING, GooConvertingDisplay::new);
		registry.registerRecipeFiller(LiquidCrystalConvertingRecipe.class, SpectrumRecipeTypes.LIQUID_CRYSTAL_CONVERTING, LiquidCrystalConvertingDisplay::new);
		registry.registerRecipeFiller(MidnightSolutionConvertingRecipe.class, SpectrumRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING, MidnightSolutionConvertingDisplay::new);
		registry.registerRecipeFiller(DragonrotConvertingRecipe.class, SpectrumRecipeTypes.DRAGONROT_CONVERTING, DragonrotConvertingDisplay::new);
		registry.registerRecipeFiller(InkConvertingRecipe.class, SpectrumRecipeTypes.INK_CONVERTING, InkConvertingDisplay::new);
		registry.registerRecipeFiller(PotionWorkshopReactingRecipe.class, SpectrumRecipeTypes.POTION_WORKSHOP_REACTING, PotionWorkshopReactingDisplay::new);
		registry.registerRecipeFiller(CrystallarieumRecipe.class, SpectrumRecipeTypes.CRYSTALLARIEUM, CrystallarieumDisplay::new);
		registry.registerRecipeFiller(CinderhearthRecipe.class, SpectrumRecipeTypes.CINDERHEARTH, CinderhearthDisplay::new);
		registry.registerRecipeFiller(ITitrationBarrelRecipe.class, SpectrumRecipeTypes.TITRATION_BARREL, TitrationBarrelDisplay::new);
		registry.registerRecipeFiller(PrimordialFireBurningRecipe.class, SpectrumRecipeTypes.PRIMORDIAL_FIRE_BURNING, PrimordialFireBurningDisplay::new);
		
		NaturesStaffConversionDataLoader.CONVERSIONS.forEach((key, value) -> registry.add(new NaturesStaffConversionsDisplay(EntryStacks.of(key), EntryStacks.of(value.getBlock()), NaturesStaffConversionDataLoader.UNLOCK_IDENTIFIERS.getOrDefault(key, null))));
		FreezingIdolBlock.FREEZING_STATE_MAP.forEach((key, value) -> registry.add(new FreezingDisplay(BlockToBlockWithChanceDisplay.blockToEntryStack(key.getBlock()), BlockToBlockWithChanceDisplay.blockToEntryStack(value.getA().getBlock()), value.getB())));
		FreezingIdolBlock.FREEZING_MAP.forEach((key, value) -> registry.add(new FreezingDisplay(BlockToBlockWithChanceDisplay.blockToEntryStack(key), BlockToBlockWithChanceDisplay.blockToEntryStack(value.getA().getBlock()), value.getB())));
		FirestarterIdolBlock.BURNING_MAP.forEach((key, value) -> registry.add(new HeatingDisplay(BlockToBlockWithChanceDisplay.blockToEntryStack(key), BlockToBlockWithChanceDisplay.blockToEntryStack(value.getA().getBlock()), value.getB())));
		
		
		registry.registerVisibilityPredicate((category, display) -> {
			// do not list recipes in REI at all, until they are unlocked
			// secret recipes are never shown
			if (display instanceof GatedRecipeDisplay gatedRecipeDisplay) {
				if (!gatedRecipeDisplay.isUnlocked() && (SpectrumCommon.CONFIG.REIListsRecipesAsNotUnlocked || gatedRecipeDisplay.isSecret())) {
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
		registry.registerContainerClickArea(new Rectangle(89, 37, 10, 15), PedestalScreen.class, BuiltinPlugin.CRAFTING);
		registry.registerContainerClickArea(new Rectangle(100, 37, 11, 15), PedestalScreen.class, SpectrumPlugins.PEDESTAL_CRAFTING);
		
		registry.registerContainerClickArea(new Rectangle(28, 41, 10, 42), PotionWorkshopScreen.class, SpectrumPlugins.POTION_WORKSHOP_BREWING);
		registry.registerContainerClickArea(new Rectangle(28, 41, 10, 42), PotionWorkshopScreen.class, SpectrumPlugins.POTION_WORKSHOP_CRAFTING);
		
		registry.registerContainerClickArea(new Rectangle(35, 33, 22, 15), CinderhearthScreen.class, SpectrumPlugins.CINDERHEARTH);
		registry.registerContainerClickArea(new Rectangle(35, 33, 22, 15), CinderhearthScreen.class, BuiltinPlugin.BLASTING);
		
		registry.registerDecider(REIOverlayDecider.INSTANCE);
	}

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void registerTransferHandlers(TransferHandlerRegistry registry) {
		// REI input magic to prevent moving incorrect amount of gem powder yet still complain about a lack of such
		registry.register(SimpleTransferHandlerExtension.create(PedestalScreenHandler.class, SpectrumPlugins.PEDESTAL_CRAFTING,
				new SimpleTransferHandler.IntRange(0, 9),
				List.of(new SimpleTransferHandler.IntRange(9, 15), new SimpleTransferHandler.IntRange(16, 52))));
		if (SpectrumCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
			registry.register(SimpleTransferHandlerExtension.create(PedestalScreenHandler.class, BuiltinPlugin.CRAFTING,
					new SimpleTransferHandler.IntRange(0, 9), new SimpleTransferHandler.IntRange(16, 52)));
		}
		registry.register(SimpleTransferHandlerExtension.create(CinderhearthScreenHandler.class, SpectrumPlugins.CINDERHEARTH,
				new SimpleTransferHandler.IntRange(2, 3), new SimpleTransferHandler.IntRange(11, 47)));
		registry.register(SimpleTransferHandlerExtension.create(CinderhearthScreenHandler.class, BuiltinPlugin.BLASTING,
				new SimpleTransferHandler.IntRange(2, 3), new SimpleTransferHandler.IntRange(11, 47)));
		registry.register(SimpleTransferHandlerExtension.create(PotionWorkshopScreenHandler.class, SpectrumPlugins.POTION_WORKSHOP_BREWING,
				new SimpleTransferHandler.IntRange(0, 9), new SimpleTransferHandler.IntRange(21, 57)));
		registry.register(SimpleTransferHandlerExtension.create(PotionWorkshopScreenHandler.class, SpectrumPlugins.POTION_WORKSHOP_CRAFTING,
				new SimpleTransferHandler.IntRange(0, 9), new SimpleTransferHandler.IntRange(21, 57)));
	}

	@SuppressWarnings("UnstableApiUsage")
	interface SimpleTransferHandlerExtension extends SimpleTransferHandler {
		// Because REI decided to give the create method with the inventory slots argument a different container class type.
		// Pretty much identical to the original otherwise (except with slot handling changed to resemble the EMI counterpart)
		static <C extends AbstractContainerMenu, D extends Display> SimpleTransferHandler create(Class<? extends C> containerClass,
																								 CategoryIdentifier<D> categoryIdentifier,
																								 SimpleTransferHandler.IntRange inputSlots,
																								 SimpleTransferHandler.IntRange inventorySlots) {
			return new SimpleTransferHandler() {
				@Override
				public ApplicabilityResult checkApplicable(Context context) {
					if (!containerClass.isInstance(context.getMenu())
							|| !categoryIdentifier.equals(context.getDisplay().getCategoryIdentifier())
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
							.stream().map(SlotAccessor::fromSlot).toList();
				}

				@Override
				public Iterable<SlotAccessor> getInventorySlots(Context context) {
					return context.getMenu()
							.slots.subList(inventorySlots.min(), inventorySlots.maxExclusive())
							.stream().map(SlotAccessor::fromSlot).toList();
				}
			};
		}
		static <C extends AbstractContainerMenu, D extends Display> SimpleTransferHandler create(Class<? extends C> containerClass,
																						 CategoryIdentifier<D> categoryIdentifier,
																						 SimpleTransferHandler.IntRange inputSlots,
																						 List<IntRange> inventorySlotsRanges) {
			return new SimpleTransferHandler() {
				@Override
				public ApplicabilityResult checkApplicable(Context context) {
					if (!containerClass.isInstance(context.getMenu())
							|| !categoryIdentifier.equals(context.getDisplay().getCategoryIdentifier())
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
							.stream().map(SlotAccessor::fromSlot).toList();
				}

				@Override
				public Iterable<SlotAccessor> getInventorySlots(Context context) {
					Stream<SlotAccessor> s = Stream.empty();
					for (SimpleTransferHandler.IntRange range : inventorySlotsRanges) {
						s = Stream.concat(s, context.getMenu()
								.slots.subList(range.min(), range.maxExclusive())
								.stream().map(SlotAccessor::fromSlot));
					}
					return s.toList();
				}
			};
		}
	}
}
