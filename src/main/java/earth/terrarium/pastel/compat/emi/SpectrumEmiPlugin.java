package earth.terrarium.pastel.compat.emi;

import dev.emi.emi.api.widget.Bounds;
import earth.terrarium.pastel.SpectrumCommon;
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
import earth.terrarium.pastel.compat.emi.recipes.SpectrumWorldInteractionRecipe;
import earth.terrarium.pastel.compat.emi.recipes.SpiritInstillingEmiRecipeGated;
import earth.terrarium.pastel.compat.emi.recipes.TitrationBarrelEmiRecipeGated;
import earth.terrarium.pastel.data_loaders.NaturesStaffConversionDataLoader;
import earth.terrarium.pastel.inventories.BlackHoleChestScreen;
import earth.terrarium.pastel.inventories.FilteringScreen;
import earth.terrarium.pastel.inventories.SpectrumScreenHandlerTypes;
import earth.terrarium.pastel.inventories.slots.ShadowSlot;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.GooConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import earth.terrarium.pastel.registries.SpectrumAdvancements;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumFluids;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumRecipeTypes;
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
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
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

public class SpectrumEmiPlugin implements EmiPlugin {
	
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
		registry.addCategory(SpectrumEmiRecipeCategories.PEDESTAL_CRAFTING);
		registry.addCategory(SpectrumEmiRecipeCategories.ANVIL_CRUSHING);
		registry.addCategory(SpectrumEmiRecipeCategories.FUSION_SHRINE);
		registry.addCategory(SpectrumEmiRecipeCategories.NATURES_STAFF);
		registry.addCategory(SpectrumEmiRecipeCategories.ENCHANTER);
		registry.addCategory(SpectrumEmiRecipeCategories.ENCHANTMENT_UPGRADE);
		registry.addCategory(SpectrumEmiRecipeCategories.POTION_WORKSHOP_BREWING);
		registry.addCategory(SpectrumEmiRecipeCategories.POTION_WORKSHOP_CRAFTING);
		registry.addCategory(SpectrumEmiRecipeCategories.POTION_WORKSHOP_REACTING);
		registry.addCategory(SpectrumEmiRecipeCategories.SPIRIT_INSTILLER);
		registry.addCategory(SpectrumEmiRecipeCategories.GOO_CONVERTING);
		registry.addCategory(SpectrumEmiRecipeCategories.LIQUID_CRYSTAL_CONVERTING);
		registry.addCategory(SpectrumEmiRecipeCategories.MIDNIGHT_SOLUTION_CONVERTING);
		registry.addCategory(SpectrumEmiRecipeCategories.DRAGONROT_CONVERTING);
		registry.addCategory(SpectrumEmiRecipeCategories.HEATING);
		registry.addCategory(SpectrumEmiRecipeCategories.FREEZING);
		registry.addCategory(SpectrumEmiRecipeCategories.INK_CONVERTING);
		registry.addCategory(SpectrumEmiRecipeCategories.CRYSTALLARIEUM);
		registry.addCategory(SpectrumEmiRecipeCategories.CINDERHEARTH);
		registry.addCategory(SpectrumEmiRecipeCategories.TITRATION_BARREL);
		registry.addCategory(SpectrumEmiRecipeCategories.PRIMORDIAL_FIRE_BURNING);
		
		EmiIngredient pedestals = EmiIngredient.of(List.of(
				EmiStack.of(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ.get()),
				EmiStack.of(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.get()),
				EmiStack.of(SpectrumBlocks.PEDESTAL_BASIC_CITRINE.get()),
				EmiStack.of(SpectrumBlocks.PEDESTAL_ALL_BASIC.get()),
				EmiStack.of(SpectrumBlocks.PEDESTAL_ONYX.get()),
				EmiStack.of(SpectrumBlocks.PEDESTAL_MOONSTONE.get())
		));
		registry.addWorkstation(SpectrumEmiRecipeCategories.PEDESTAL_CRAFTING, pedestals);
		if (SpectrumCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
			registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, pedestals);
		}
		
		registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(SpectrumItems.CRAFTING_TABLET.get()));
		registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(SpectrumBlocks.FABRICATION_CHEST.get()));
		registry.addWorkstation(VanillaEmiRecipeCategories.BLASTING, EmiStack.of(SpectrumBlocks.CINDERHEARTH.get()));
		
		registry.addWorkstation(SpectrumEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(Blocks.ANVIL));
		registry.addWorkstation(SpectrumEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(SpectrumBlocks.BEDROCK_ANVIL.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(SpectrumBlocks.STRATINE_FLOATBLOCK.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.ANVIL_CRUSHING, EmiStack.of(SpectrumBlocks.PALTAERIA_FLOATBLOCK.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.FUSION_SHRINE, EmiStack.of(SpectrumBlocks.FUSION_SHRINE_BASALT.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.FUSION_SHRINE, EmiStack.of(SpectrumBlocks.FUSION_SHRINE_CALCITE.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.NATURES_STAFF, EmiStack.of(SpectrumItems.NATURES_STAFF.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.HEATING, EmiStack.of(SpectrumBlocks.BLAZE_IDOL.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.FREEZING, EmiStack.of(SpectrumBlocks.POLAR_BEAR_IDOL.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.ENCHANTER, EmiStack.of(SpectrumBlocks.ENCHANTER.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.ENCHANTMENT_UPGRADE, EmiStack.of(SpectrumBlocks.ENCHANTER.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.GOO_CONVERTING, EmiStack.of(SpectrumItems.GOO_BUCKET.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.LIQUID_CRYSTAL_CONVERTING, EmiStack.of(SpectrumItems.LIQUID_CRYSTAL_BUCKET.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.MIDNIGHT_SOLUTION_CONVERTING, EmiStack.of(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.DRAGONROT_CONVERTING, EmiStack.of(SpectrumItems.DRAGONROT_BUCKET.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.SPIRIT_INSTILLER, EmiStack.of(SpectrumBlocks.SPIRIT_INSTILLER.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.INK_CONVERTING, EmiStack.of(SpectrumBlocks.COLOR_PICKER.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.CRYSTALLARIEUM, EmiStack.of(SpectrumBlocks.CRYSTALLARIEUM.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.POTION_WORKSHOP_BREWING, EmiStack.of(SpectrumBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.POTION_WORKSHOP_CRAFTING, EmiStack.of(SpectrumBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.POTION_WORKSHOP_REACTING, EmiStack.of(SpectrumBlocks.POTION_WORKSHOP.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.CINDERHEARTH, EmiStack.of(SpectrumBlocks.CINDERHEARTH.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.TITRATION_BARREL, EmiStack.of(SpectrumBlocks.TITRATION_BARREL.get()));
		registry.addWorkstation(SpectrumEmiRecipeCategories.PRIMORDIAL_FIRE_BURNING, EmiIngredient.of(List.of(EmiStack.of(SpectrumItems.DOOMBLOOM_SEED.get()), EmiStack.of(SpectrumItems.PRIMORDIAL_LIGHTER.get()), EmiStack.of(SpectrumBlocks.INCANDESCENT_AMALGAM.get()), EmiStack.of(SpectrumItems.PIPE_BOMB.get()))));
	}
	
	public void registerRecipes(EmiRegistry registry) {
		// TODO: Register our recipes ourselves
		// right now dev.emi.emi.VanillaPlugin handles them
		// which does not process the unlock check
		//addAll(registry, RecipeType.CRAFTING, ShapedGatedCraftingEMIRecipe::new);
		//addAll(registry, RecipeType.CRAFTING, ShapelessGatedCraftingEMIRecipe::new);
		
		addAll(registry, SpectrumRecipeTypes.ANVIL_CRUSHING, AnvilCrushingEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.PEDESTAL, PedestalCraftingEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.FUSION_SHRINE, FusionShrineEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.ENCHANTER, r -> new EnchanterEmiRecipeGated(SpectrumEmiRecipeCategories.ENCHANTER, r));
		addAll(registry, SpectrumRecipeTypes.ENCHANTMENT_UPGRADE, r -> new EnchantmentUpgradeEmiRecipeGated(SpectrumEmiRecipeCategories.ENCHANTMENT_UPGRADE, r));
		addAll(registry, SpectrumRecipeTypes.POTION_WORKSHOP_BREWING, r -> new PotionWorkshopEmiRecipeGated(SpectrumEmiRecipeCategories.POTION_WORKSHOP_BREWING, r));
		addAll(registry, SpectrumRecipeTypes.POTION_WORKSHOP_CRAFTING, r -> new PotionWorkshopEmiRecipeGated(SpectrumEmiRecipeCategories.POTION_WORKSHOP_CRAFTING, r));
		addAll(registry, SpectrumRecipeTypes.POTION_WORKSHOP_REACTING, PotionWorkshopReactingEmiRecipe::new);
		addAll(registry, SpectrumRecipeTypes.SPIRIT_INSTILLING, SpiritInstillingEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.GOO_CONVERTING, r -> new FluidConvertingEmiRecipeGated(SpectrumEmiRecipeCategories.GOO_CONVERTING, r));
		addAll(registry, SpectrumRecipeTypes.LIQUID_CRYSTAL_CONVERTING, r -> new FluidConvertingEmiRecipeGated(SpectrumEmiRecipeCategories.LIQUID_CRYSTAL_CONVERTING, r));
		addAll(registry, SpectrumRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING, r -> new FluidConvertingEmiRecipeGated(SpectrumEmiRecipeCategories.MIDNIGHT_SOLUTION_CONVERTING, r));
		addAll(registry, SpectrumRecipeTypes.DRAGONROT_CONVERTING, r -> new FluidConvertingEmiRecipeGated(SpectrumEmiRecipeCategories.DRAGONROT_CONVERTING, r));
		addAll(registry, SpectrumRecipeTypes.INK_CONVERTING, InkConvertingEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.CRYSTALLARIEUM, CrystallarieumEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.CINDERHEARTH, CinderhearthEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.TITRATION_BARREL, TitrationBarrelEmiRecipeGated::new);
		addAll(registry, SpectrumRecipeTypes.PRIMORDIAL_FIRE_BURNING, PrimordialFireBurningEmiRecipeGated::new);
		
		FreezingIdolBlock.FREEZING_STATE_MAP.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key.getBlock());
			EmiStack out = EmiStack.of(value.getA().getBlock()).setChance(value.getB());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("freezing", key.getBlock()); // The synthetic IDs generated here assume there will never be multiple conversions of the same block with different states
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(SpectrumEmiRecipeCategories.FREEZING, id, in, out, SpectrumAdvancements.UNLOCK_IDOLS));
		});
		FreezingIdolBlock.FREEZING_MAP.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key);
			EmiStack out = EmiStack.of(value.getA().getBlock()).setChance(value.getB());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("freezing", key);
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(SpectrumEmiRecipeCategories.FREEZING, id, in, out, SpectrumAdvancements.UNLOCK_IDOLS));
		});
		FirestarterIdolBlock.BURNING_MAP.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key);
			EmiStack out = EmiStack.of(value.getA().getBlock()).setChance(value.getB());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("heating", key);
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(SpectrumEmiRecipeCategories.HEATING, id, in, out, SpectrumAdvancements.UNLOCK_IDOLS));
		});
		NaturesStaffConversionDataLoader.CONVERSIONS.forEach((key, value) -> {
			EmiStack in = EmiStack.of(key);
			EmiStack out = EmiStack.of(value.getBlock());
			if (in.isEmpty() || out.isEmpty()) {
				return;
			}
			ResourceLocation id = syntheticId("natures_staff", key);
			registry.addRecipe(new BlockToBlockWithChanceEmiRecipe(SpectrumEmiRecipeCategories.NATURES_STAFF, id, in, out, SpectrumAdvancements.UNLOCK_NATURES_STAFF));
		});
		
		//WorldInteractionRecipe
		EmiStack water = EmiStack.of(Fluids.WATER, FluidUnit.BUCKET);
		EmiStack lava = EmiStack.of(Fluids.LAVA, FluidUnit.BUCKET);
		EmiStack dragonrot = EmiStack.of(SpectrumFluids.DRAGONROT.get(), FluidUnit.BUCKET);
		EmiStack liquidCrystal = EmiStack.of(SpectrumFluids.LIQUID_CRYSTAL.get(), FluidUnit.BUCKET);
		EmiStack midnightSolution = EmiStack.of(SpectrumFluids.MIDNIGHT_SOLUTION.get(), FluidUnit.BUCKET);
		EmiStack mud = EmiStack.of(SpectrumFluids.GOO.get(), FluidUnit.BUCKET);
		EmiStack waterCatalyst = water.copy().setRemainder(water);
		EmiStack lavaCatalyst = lava.copy().setRemainder(lava);
		EmiStack dragonrotCatalyst = dragonrot.copy().setRemainder(dragonrot);
		EmiStack liquidCrystalCatalyst = liquidCrystal.copy().setRemainder(liquidCrystal);
		EmiStack midnightSolutionCatalyst = midnightSolution.copy().setRemainder(midnightSolution);
		EmiStack mudCatalyst = mud.copy().setRemainder(mud);
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", SpectrumBlocks.SLUSH.get()))
				.leftInput(dragonrotCatalyst)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(SpectrumBlocks.SLUSH.get()))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.BLACKSTONE))
				.leftInput(dragonrotCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.BLACKSTONE))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.COARSE_DIRT))
				.leftInput(dragonrotCatalyst)
				.rightInput(mudCatalyst, false)
				.output(EmiStack.of(Blocks.COARSE_DIRT))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", SpectrumBlocks.FLAYED_EARTH.get()))
				.leftInput(dragonrotCatalyst)
				.rightInput(liquidCrystalCatalyst, false)
				.output(EmiStack.of(SpectrumBlocks.FLAYED_EARTH.get()))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", SpectrumBlocks.HORNSLAKE.get()))
				.leftInput(dragonrotCatalyst)
				.rightInput(midnightSolutionCatalyst, false)
				.output(EmiStack.of(SpectrumBlocks.HORNSLAKE.get()))
				.requiredAdvancement(DragonrotConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(MidnightSolutionConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", SpectrumBlocks.FROSTBITE_CRYSTAL.get()))
				.leftInput(liquidCrystal)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(SpectrumBlocks.FROSTBITE_CRYSTAL.get()))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.CALCITE))
				.leftInput(liquidCrystalCatalyst)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(Blocks.CALCITE))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", SpectrumBlocks.BLAZING_CRYSTAL.get()))
				.leftInput(liquidCrystal)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(SpectrumBlocks.BLAZING_CRYSTAL.get()))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.COBBLED_DEEPSLATE))
				.leftInput(liquidCrystalCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.COBBLED_DEEPSLATE))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.CLAY))
				.leftInput(liquidCrystalCatalyst)
				.rightInput(mudCatalyst, false)
				.output(EmiStack.of(Blocks.CLAY))
				.requiredAdvancement(LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER)
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.TERRACOTTA))
				.leftInput(midnightSolutionCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.TERRACOTTA))
				.requiredAdvancement(MidnightSolutionConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.DIRT))
				.leftInput(mudCatalyst)
				.rightInput(waterCatalyst, false)
				.output(EmiStack.of(Blocks.DIRT))
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
		addRecipeSafe(registry, () -> SpectrumWorldInteractionRecipe.customBuilder()
				.id(syntheticId("world/fluid_interaction", Blocks.MUD))
				.leftInput(mudCatalyst)
				.rightInput(lavaCatalyst, false)
				.output(EmiStack.of(Blocks.MUD))
				.requiredAdvancement(GooConvertingRecipe.UNLOCK_IDENTIFIER)
				.build());
	}
	
	public void registerRecipeHandlers(EmiRegistry registry) {
		registry.addRecipeHandler(SpectrumScreenHandlerTypes.PEDESTAL, new PedestalRecipeHandler());
		registry.addRecipeHandler(SpectrumScreenHandlerTypes.CRAFTING_TABLET, new CraftingTabletRecipeHandler());
		registry.addRecipeHandler(SpectrumScreenHandlerTypes.CINDERHEARTH, new CinderhearthRecipeHandler());
		registry.addRecipeHandler(SpectrumScreenHandlerTypes.POTION_WORKSHOP, new PotionWorkshopRecipeHandler());
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
