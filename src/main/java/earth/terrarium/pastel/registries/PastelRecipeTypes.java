package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.recipe.InkConvertingRecipe;
import earth.terrarium.pastel.recipe.anvil_crushing.AnvilCrushingRecipe;
import earth.terrarium.pastel.recipe.cinderhearth.CinderhearthRecipe;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchanterRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.GooConvertingRecipe;
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
import net.minecraft.core.registries.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class PastelRecipeTypes {

	private static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, PastelCommon.MOD_ID);

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
		var type = new RecipeType<T>() {
			@Override
			public String toString() {
				return "pastel:" + id;
			}
		};

		REGISTER.register(id, () -> type);
		return type;
	}
	
	public static void register(IEventBus bus) {
		REGISTER.register(bus);
	}
	
}
