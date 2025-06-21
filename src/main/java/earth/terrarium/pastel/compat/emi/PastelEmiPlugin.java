package earth.terrarium.pastel.compat.emi;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.block.FilterConfigurable;
import earth.terrarium.pastel.blocks.idols.FirestarterIdolBlock;
import earth.terrarium.pastel.blocks.idols.FreezingIdolBlock;
import earth.terrarium.pastel.compat.emi.handlers.CinderhearthRecipeHandler;
import earth.terrarium.pastel.compat.emi.handlers.CraftingTabletRecipeHandler;
import earth.terrarium.pastel.compat.emi.handlers.PedestalRecipeHandler;
import earth.terrarium.pastel.compat.emi.handlers.PotionWorkshopRecipeHandler;
import earth.terrarium.pastel.compat.emi.recipes.AnvilCrushingEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.BlockToBlockWithChanceEmiRecipe;
import earth.terrarium.pastel.compat.emi.recipes.CinderhearthEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.CrystallarieumEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.EnchanterEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.EnchantmentUpgradeEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.FluidConvertingEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.FusionShrineEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.InkConvertingEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.PedestalCraftingEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.PotionWorkshopEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.PotionWorkshopReactingEmiRecipe;
import earth.terrarium.pastel.compat.emi.recipes.PrimordialFireBurningEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.PastelWorldInteractionRecipe;
import earth.terrarium.pastel.compat.emi.recipes.SpiritInstillingEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.TitrationBarrelEmiRecipeGated;
import earth.terrarium.pastel.data_loaders.NaturesStaffConversionDataLoader;
import earth.terrarium.pastel.inventories.BlackHoleChestScreen;
import earth.terrarium.pastel.inventories.FilteringScreen;
import earth.terrarium.pastel.inventories.PastelScreenHandlerTypes;
import earth.terrarium.pastel.inventories.slots.ShadowSlot;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.GooConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import dev.emi.emi.api.EmiDragDropHandler;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.ItemEmiStack;
import dev.emi.emi.config.FluidUnit;
import dev.emi.emi.runtime.EmiReloadLog;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PastelEmiPlugin implements EmiPlugin {

	@Override
	public void register(EmiRegistry registry) {
		registerCategories(registry);
		registerRecipes(registry);
		registerRecipeHandlers(registry);
		registerDragDropHandlers(registry);
	}

	@SuppressWarnings("UnstableApiUsage")
	public void registerDragDropHandlers(EmiRegistry registry) {
		// Registering here since this is a trivial solution.
		var handlerOne = new EmiDragDropHandler.SlotBased<>((_ignored, slot) -> slot instanceof ShadowSlot && slot.container instanceof FilterConfigurable.FilterInventory,
				(screen, slot, ingredient) -> {
					if (ingredient instanceof ItemEmiStack stack)
						((FilterConfigurable.FilterInventory) slot.container).getClicker().clickShadowSlot(screen.getMenu().containerId, slot, stack.getItemStack());
				});

		registerDragDropHandler(registry, BlackHoleChestScreen.class, handlerOne);
		registerDragDropHandler(registry, FilteringScreen.class, handlerOne);
	}

	// Variant erasure BS
	@SuppressWarnings("unchecked")
	private void registerDragDropHandler(EmiRegistry registry, Class<? extends AbstractContainerScreen<?>> clazz, EmiDragDropHandler<AbstractContainerScreen<?>> handler) {
		registry.addDragDropHandler((Class<AbstractContainerScreen<?>>) clazz, handler);
	}

	public void registerCategories(EmiRegistry registry) {
		registry.addCategory(PastelEmiRecipeCategories.PEDESTAL_CRAFTING);
		registry.addCategory(PastelEmiRecipeCategories.ANVIL_CRUSHING);
		registry.addCategory(PastelEmiRecipeCategories.FUSION_SHRINE);
		registry.addCategory(PastelEmiRecipeCategories.NATURES_STAFF);
		registry.addCategory(PastelEmiRecipeCategories.ENCHANTER);
		registry.addCategory(PastelEmiRecipeCategories.ENCHANTMENT_UPGRADE);
		registry.addCategory(PastelEmiRecipeCategories.POTION_WORKSHOP_BREWING);
		registry.addCategory(PastelEmiRecipeCategories.POTION_WORKSHOP_CRAFTING);
		registry.addCategory(PastelEmiRecipeCategories.POTION_WORKSHOP_REACTING);
		registry.addCategory(PastelEmiRecipeCategories.SPIRIT_INSTILLER);
		registry.addCategory(PastelEmiRecipeCategories.GOO_CONVERTING);
		registry.addCategory(PastelEmiRecipeCategories.LIQUID_CRYSTAL_CONVERTING);
		registry.addCategory(PastelEmiRecipeCategories.MIDNIGHT_SOLUTION_CONVERTING);
		registry.addCategory(PastelEmiRecipeCategories.DRAGONROT_CONVERTING);
		registry.addCategory(PastelEmiRecipeCategories.HEATING);
		registry.addCategory(PastelEmiRecipeCategories.FREEZING);
		registry.addCategory(PastelEmiRecipeCategories.INK_CONVERTING);
		registry.addCategory(PastelEmiRecipeCategories.CRYSTALLARIEUM);
		registry.addCategory(PastelEmiRecipeCategories.CINDERHEARTH);
		registry.addCategory(PastelEmiRecipeCategories.TITRATION_BARREL);
		registry.addCategory(PastelEmiRecipeCategories.PRIMORDIAL_FIRE_BURNING);

		EmiIngredient pedestals = EmiIngredient.of(List.of(
				EmiStack.of(PastelBlocks.PEDESTAL_BASIC_TOPAZ.get()),
				EmiStack.of(PastelBlocks.PEDESTAL_BASIC_AMETHYST.get()),
				EmiStack.of(PastelBlocks.PEDESTAL_BASIC_CITRINE.get()),
				EmiStack.of(PastelBlocks.PEDESTAL_ALL_BASIC.get()),
				EmiStack.of(PastelBlocks.PEDESTAL_ONYX.get()),
				EmiStack.of(PastelBlocks.PEDESTAL_MOONSTONE.get())
		));
		registry.addWorkstation(PastelEmiRecipeCategories.PEDESTAL_CRAFTING, pedestals);
		if (PastelCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
			registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, pedestals);
		}

		registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(PastelItems.CRAFTING_TABLET.get()));
		registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(PastelBlocks.FABRICATION_CHEST.get()));
		registry.addWorkstation(VanillaEmiRecipeCategories.BLASTING, EmiStack.of(PastelBlocks.CINDERHEARTH.get()));

		registry.addWorkstation(PastelEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(Blocks.ANVIL));
		registry.addWorkstation(PastelEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(PastelBlocks.BEDROCK_ANVIL.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(PastelBlocks.STRATINE_FLOATBLOCK.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(PastelBlocks.PALTAERIA_FLOATBLOCK.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.FUSION_SHRINE, EmiStack.of(PastelBlocks.FUSION_SHRINE_BASALT.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.FUSION_SHRINE, EmiStack.of(PastelBlocks.FUSION_SHRINE_CALCITE.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.NATURES_STAFF, EmiStack.of(PastelItems.NATURES_STAFF.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.HEATING, EmiStack.of(PastelBlocks.BLAZE_IDOL.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.FREEZING, EmiStack.of(PastelBlocks.POLAR_BEAR_IDOL.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.ENCHANTER, EmiStack.of(PastelBlocks.ENCHANTER.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.ENCHANTMENT_UPGRADE, EmiStack.of(PastelBlocks.ENCHANTER.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.GOO_CONVERTING, EmiStack.of(PastelItems.GOO_BUCKET.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.LIQUID_CRYSTAL_CONVERTING, EmiStack.of(PastelItems.LIQUID_CRYSTAL_BUCKET.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.MIDNIGHT_SOLUTION_CONVERTING, EmiStack.of(PastelItems.MIDNIGHT_SOLUTION_BUCKET.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.DRAGONROT_CONVERTING, EmiStack.of(PastelItems.DRAGONROT_BUCKET.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.SPIRIT_INSTILLER, EmiStack.of(PastelBlocks.SPIRIT_INSTILLER.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.INK_CONVERTING, EmiStack.of(PastelBlocks.COLOR_PICKER.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.CRYSTALLARIEUM, EmiStack.of(PastelBlocks.CRYSTALLARIEUM.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.POTION_WORKSHOP_BREWING, EmiStack.of(PastelBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.POTION_WORKSHOP_CRAFTING, EmiStack.of(PastelBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.POTION_WORKSHOP_REACTING, EmiStack.of(PastelBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.CINDERHEARTH, EmiStack.of(PastelBlocks.CINDERHEARTH.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.TITRATION_BARREL, EmiStack.of(PastelBlocks.TITRATION_BARREL.get()));
		registry.addWorkstation(PastelEmiRecipeCategories.PRIMORDIAL_FIRE_BURNING, EmiIngredient.of(List.of(EmiStack.of(PastelItems.DOOMBLOOM_SEED.get()), EmiStack.of(PastelItems.PRIMORDIAL_LIGHTER.get()), EmiStack.of(PastelBlocks.INCANDESCENT_AMALGAM.get()), EmiStack.of(PastelItems.PIPE_BOMB.get()))));
	}

	public void registerRecipes(EmiRegistry registry) {
		// TODO: Register our recipes ourselves
		// right now dev.emi.emi.VanillaPlugin handles them
		// which does not process the unlock check
		//addAll(registry, RecipeType.CRAFTING, ShapedGatedCraftingEMIRecipe::new);
		//addAll(registry, RecipeType.CRAFTING, ShapelessGatedCraftingEMIRecipe::new);

		addAll(registry, PastelRecipeTypes.ANVIL_CRUSHING, AnvilCrushingEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.PEDESTAL, PedestalCraftingEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.FUSION_SHRINE, FusionShrineEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.ENCHANTER, r -> new EnchanterEmiRecipeGated(PastelEmiRecipeCategories.ENCHANTER, r));
		addAll(registry, PastelRecipeTypes.ENCHANTMENT_UPGRADE, r -> new EnchantmentUpgradeEmiRecipeGated(PastelEmiRecipeCategories.ENCHANTMENT_UPGRADE, r));
		addAll(registry, PastelRecipeTypes.POTION_WORKSHOP_BREWING, r -> new PotionWorkshopEmiRecipeGated(PastelEmiRecipeCategories.POTION_WORKSHOP_BREWING, r));
		addAll(registry, PastelRecipeTypes.POTION_WORKSHOP_CRAFTING, r -> new PotionWorkshopEmiRecipeGated(PastelEmiRecipeCategories.POTION_WORKSHOP_CRAFTING, r));
		addAll(registry, PastelRecipeTypes.POTION_WORKSHOP_REACTING, PotionWorkshopReactingEmiRecipe::new);
		addAll(registry, PastelRecipeTypes.SPIRIT_INSTILLING, SpiritInstillingEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.GOO_CONVERTING, r -> new FluidConvertingEmiRecipeGated(PastelEmiRecipeCategories.GOO_CONVERTING, r));
		addAll(registry, PastelRecipeTypes.LIQUID_CRYSTAL_CONVERTING, r -> new FluidConvertingEmiRecipeGated(PastelEmiRecipeCategories.LIQUID_CRYSTAL_CONVERTING, r));
		addAll(registry, PastelRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING, r -> new FluidConvertingEmiRecipeGated(PastelEmiRecipeCategories.MIDNIGHT_SOLUTION_CONVERTING, r));
		addAll(registry, PastelRecipeTypes.DRAGONROT_CONVERTING, r -> new FluidConvertingEmiRecipeGated(PastelEmiRecipeCategories.DRAGONROT_CONVERTING, r));
		addAll(registry, PastelRecipeTypes.INK_CONVERTING, InkConvertingEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.CRYSTALLARIEUM, CrystallarieumEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.CINDERHEARTH, CinderhearthEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.TITRATION_BARREL, TitrationBarrelEmiRecipeGated::new);
		addAll(registry, PastelRecipeTypes.PRIMORDIAL_FIRE_BURNING, PrimordialFireBurningEmiRecipeGated::new);

		FreezingIdolBlock.FREEZING_STATE_MAP.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key.getBlock());
			EmiStack out = EmiStack.of(value.getA().getBlock()).setChance(value.getB());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("freezing", key.getBlock()); // The synthetic IDs generated here assume there will never be multiple conversions of the same block with different states
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(PastelEmiRecipeCategories.FREEZING, id, in, out, PastelAdvancements.UNLOCK_IDOLS));
		});
		FreezingIdolBlock.FREEZING_MAP.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key);
			EmiStack out = EmiStack.of(value.getA().getBlock()).setChance(value.getB());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("freezing", key);
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(PastelEmiRecipeCategories.FREEZING, id, in, out, PastelAdvancements.UNLOCK_IDOLS));
		});
		FirestarterIdolBlock.BURNING_MAP.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key);
			EmiStack out = EmiStack.of(value.getA().getBlock()).setChance(value.getB());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("heating", key);
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(PastelEmiRecipeCategories.HEATING, id, in, out, PastelAdvancements.UNLOCK_IDOLS));
		});
		NaturesStaffConversionDataLoader.CONVERSIONS.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key);
			EmiStack out = EmiStack.of(value.getBlock());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("natures_staff", key);
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(PastelEmiRecipeCategories.NATURES_STAFF, id, in, out, PastelAdvancements.UNLOCK_NATURES_STAFF));
		});

		//WorldInteractionRecipe
		EmiStack water = EmiStack.of(Fluids.WATER, FluidUnit.BUCKET);
		EmiStack lava = EmiStack.of(Fluids.LAVA, FluidUnit.BUCKET);
		EmiStack dragonrot = EmiStack.of(PastelFluids.DRAGONROT.get(), FluidUnit.BUCKET);
		EmiStack liquidCrystal = EmiStack.of(PastelFluids.LIQUID_CRYSTAL.get(), FluidUnit.BUCKET);
		EmiStack midnightSolution = EmiStack.of(PastelFluids.MIDNIGHT_SOLUTION.get(), FluidUnit.BUCKET);
		EmiStack mud = EmiStack.of(PastelFluids.GOO.get(), FluidUnit.BUCKET);
		EmiStack waterCatalyst = water.copy().setRemainder(water);
		EmiStack lavaCatalyst = lava.copy().setRemainder(lava);
		EmiStack dragonrotCatalyst = dragonrot.copy().setRemainder(dragonrot);
		EmiStack liquidCrystalCatalyst = liquidCrystal.copy().setRemainder(liquidCrystal);
		EmiStack midnightSolutionCatalyst = midnightSolution.copy().setRemainder(midnightSolution);
		EmiStack mudCatalyst = mud.copy().setRemainder(mud);
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", PastelBlocks.SLUSH.get()))
				.leftInput(dragonrotCatalyst)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(PastelBlocks.SLUSH.get()))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.BLACKSTONE))
				.leftInput(dragonrotCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.BLACKSTONE))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.COARSE_DIRT))
				.leftInput(dragonrotCatalyst)
				.rightInput(mudCatalyst, false)
				.output(EmiStack.of(Blocks.COARSE_DIRT))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", PastelBlocks.FLAYED_EARTH.get()))
				.leftInput(dragonrotCatalyst)
				.rightInput(liquidCrystalCatalyst, false)
				.output(EmiStack.of(PastelBlocks.FLAYED_EARTH.get()))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", PastelBlocks.HORNSLAKE.get()))
				.leftInput(dragonrotCatalyst)
				.rightInput(midnightSolutionCatalyst, false)
				.output(EmiStack.of(PastelBlocks.HORNSLAKE.get()))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(MidnightSolutionConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", PastelBlocks.FROSTBITE_CRYSTAL.get()))
				.leftInput(liquidCrystal)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(PastelBlocks.FROSTBITE_CRYSTAL.get()))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.CALCITE))
				.leftInput(liquidCrystalCatalyst)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(Blocks.CALCITE))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", PastelBlocks.BLAZING_CRYSTAL.get()))
				.leftInput(liquidCrystal)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(PastelBlocks.BLAZING_CRYSTAL.get()))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.COBBLED_DEEPSLATE))
				.leftInput(liquidCrystalCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.COBBLED_DEEPSLATE))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.CLAY))
				.leftInput(liquidCrystalCatalyst)
				.rightInput(mudCatalyst, false)
				.output(EmiStack.of(Blocks.CLAY))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.TERRACOTTA))
				.leftInput(midnightSolutionCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.TERRACOTTA))
				.requiredAdvancement(MidnightSolutionConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.DIRT))
				.leftInput(mudCatalyst)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(Blocks.DIRT))
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> PastelWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.MUD))
				.leftInput(mudCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.MUD))
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
	}

	public void registerRecipeHandlers(EmiRegistry registry) {
		registry.addRecipeHandler(PastelScreenHandlerTypes.PEDESTAL, new PedestalRecipeHandler());
		registry.addRecipeHandler(PastelScreenHandlerTypes.CRAFTING_TABLET, new CraftingTabletRecipeHandler());
		registry.addRecipeHandler(PastelScreenHandlerTypes.CINDERHEARTH, new CinderhearthRecipeHandler());
		registry.addRecipeHandler(PastelScreenHandlerTypes.POTION_WORKSHOP, new PotionWorkshopRecipeHandler());
	}

	public static ResourceLocation syntheticId(String type, Block block) {
		ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
		// Note that all recipe ids here start with "spectrum:/" which is legal, but impossible to represent with real files
		return ResourceLocation.parse("pastel:/" + type + "/" + blockId.getNamespace() + "/" + blockId.getPath());
	}

	public <C extends RecipeInput, T extends Recipe<C>> void addAll(EmiRegistry registry, RecipeType<T> type, Function<T, EmiRecipe> constructor) {
		for (RecipeHolder<T> entry : registry.getRecipeManager().getAllRecipesFor(type)) {
			T recipe = entry.value();
			registry.addRecipe(constructor.apply(recipe));
		}
	}

	private static void addRecipeSafe(EmiRegistry registry, Supplier<EmiRecipe> supplier) {
		try {
			registry.addRecipe(supplier.get());
		} catch (Throwable e) {
			EmiReloadLog.warn("Exception thrown when parsing EMI recipe (no ID available)");
			EmiReloadLog.error(e);
		}
	}

}
