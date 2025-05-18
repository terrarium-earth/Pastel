package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
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
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class SpectrumRecipeTypes {
	
	public static final RecipeType<PedestalRecipe> PEDESTAL = register("pedestal");
	public static final RecipeType<AnvilCrushingRecipe> ANVIL_CRUSHING = register("anvil_crushing");
	public static final RecipeType<FusionShrineRecipe> FUSION_SHRINE = register("fusion_shrine");
	public static final RecipeType<EnchanterRecipe> ENCHANTER = register("enchanter");
	public static final RecipeType<EnchantmentUpgradeRecipe> ENCHANTMENT_UPGRADE = register("enchantment_upgrade");
	public static final RecipeType<PotionWorkshopBrewingRecipe> POTION_WORKSHOP_BREWING = register("potion_workshop_brewing");
	public static final RecipeType<PotionWorkshopCraftingRecipe> POTION_WORKSHOP_CRAFTING = register("potion_workshop_crafting");
	public static final RecipeType<PotionWorkshopReactingRecipe> POTION_WORKSHOP_REACTING = register("potion_workshop_reacting");
	public static final RecipeType<GooConvertingRecipe> GOO_CONVERTING = register("goo_converting");
	public static final RecipeType<LiquidCrystalConvertingRecipe> LIQUID_CRYSTAL_CONVERTING = register("liquid_crystal_converting");
	public static final RecipeType<MidnightSolutionConvertingRecipe> MIDNIGHT_SOLUTION_CONVERTING = register("midnight_solution_converting");
	public static final RecipeType<DragonrotConvertingRecipe> DRAGONROT_CONVERTING = register("dragonrot_converting");
	public static final RecipeType<SpiritInstillerRecipe> SPIRIT_INSTILLING = register("spirit_instiller");
	public static final RecipeType<InkConvertingRecipe> INK_CONVERTING = register("ink_converting");
	public static final RecipeType<CrystallarieumRecipe> CRYSTALLARIEUM = register("crystallarieum_growing");
	public static final RecipeType<CinderhearthRecipe> CINDERHEARTH = register("cinderhearth");
	public static final RecipeType<ITitrationBarrelRecipe> TITRATION_BARREL = register("titration_barrel");
	public static final RecipeType<PrimordialFireBurningRecipe> PRIMORDIAL_FIRE_BURNING = register("primordial_fire_burning");
	
	private static <T extends Recipe<?>> RecipeType<T> register(String id) {
		return Registry.register(BuiltInRegistries.RECIPE_TYPE, SpectrumCommon.locate(id), new RecipeType<T>() {
			@Override
			public String toString() {
				return "spectrum:" + id;
			}
		});
	}
	
	public static void register() {

	}
	
}
