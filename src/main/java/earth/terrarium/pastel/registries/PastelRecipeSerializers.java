package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.recipe.cantrip.DegradingRecipe;
import earth.terrarium.pastel.recipe.EmptyRecipeSerializer;
import earth.terrarium.pastel.recipe.InkConvertingRecipe;
import earth.terrarium.pastel.recipe.anvil_crushing.AnvilCrushingRecipe;
import earth.terrarium.pastel.recipe.cantrip.HealingRecipe;
import earth.terrarium.pastel.recipe.cinderhearth.CinderhearthRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearCraftingTabletRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearEnderSpliceRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearInkRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearPotionFillableRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ColorEverpromiseRibbonRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.RepairAnythingRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.WrapPresentRecipe;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchanterCraftingRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.FluidConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.HumusConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.dynamic.MeatToRottenFleshRecipe;
import earth.terrarium.pastel.recipe.fusion_shrine.FusionShrineRecipe;
import earth.terrarium.pastel.recipe.fusion_shrine.dynamic.ShootingStarHardeningRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapelessPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.dynamic.EnderCanvasLargeRecipe;
import earth.terrarium.pastel.recipe.pedestal.dynamic.EnderCanvasRecipe;
import earth.terrarium.pastel.recipe.pedestal.dynamic.StarCandyRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import earth.terrarium.pastel.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import earth.terrarium.pastel.recipe.primordial_fire_burning.dynamic.EnchantedBookUnsoulingRecipe;
import earth.terrarium.pastel.recipe.primordial_fire_burning.dynamic.MemoryDementiaRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.HardcorePlayerRevivalRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.MemoryToHeadRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.SpawnerCreatureChangeRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.SpawnerMaxNearbyEntitiesChangeRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.SpawnerRequiredPlayerRangeChangeRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.SpawnerSpawnCountChangeRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.SpawnerSpawnDelayChangeRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.dynamic.AquaRegiaRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.dynamic.CheongRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.dynamic.JadeWineRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.dynamic.NecteredViognierRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.dynamic.SuspiciousBrewRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PastelRecipeSerializers {

    private static final DeferredRegister<RecipeSerializer<?>> REGISTRAR = DeferredRegister.create(
        Registries.RECIPE_SERIALIZER, PastelCommon.MOD_ID);

    // VANILLA
    public static final RecipeSerializer<RepairAnythingRecipe> REPAIR_ANYTHING_SERIALIZER = register(
        "repair_anything", new EmptyRecipeSerializer<>(RepairAnythingRecipe::new));
    public static final RecipeSerializer<ClearInkRecipe> CLEAR_INK_SERIALIZER = register(
        "clear_ink", new EmptyRecipeSerializer<>(ClearInkRecipe::new));
    public static final RecipeSerializer<ClearEnderSpliceRecipe> CLEAR_ENDER_SPLICE_SERIALIZER = register(
        "clear_ender_splice", new EmptyRecipeSerializer<>(ClearEnderSpliceRecipe::new));
    public static final RecipeSerializer<ClearPotionFillableRecipe> CLEAR_POTION_FILLABLE_SERIALIZER = register(
        "clear_potion_fillable", new EmptyRecipeSerializer<>(ClearPotionFillableRecipe::new));
    public static final RecipeSerializer<ClearCraftingTabletRecipe> CLEAR_CRAFTING_TABLET_SERIALIZER = register(
        "clear_crafting_tablet", new EmptyRecipeSerializer<>(ClearCraftingTabletRecipe::new));
    public static final RecipeSerializer<ColorEverpromiseRibbonRecipe> COLOR_EVERPROMISE_RIBBON_SERIALIZER = register(
        "color_everpromise_ribbon", new EmptyRecipeSerializer<>(ColorEverpromiseRibbonRecipe::new));
    public static final RecipeSerializer<WrapPresentRecipe> WRAP_PRESENT_SERIALIZER = register(
        "wrap_present", new EmptyRecipeSerializer<>(WrapPresentRecipe::new));

    // Pedestal
    public static final RecipeSerializer<ShapedPedestalRecipe> SHAPED_PEDESTAL_RECIPE_SERIALIZER = register(
        "pedestal", new ShapedPedestalRecipe.Serializer());
    public static final RecipeSerializer<ShapelessPedestalRecipe> SHAPELESS_PEDESTAL_RECIPE_SERIALIZER = register(
        "pedestal_shapeless", new ShapelessPedestalRecipe.Serializer());
    public static final RecipeSerializer<StarCandyRecipe> PEDESTAL_STAR_CANDY = register(
        "pedestal_star_candy", new EmptyRecipeSerializer<>(StarCandyRecipe::new));
    public static final RecipeSerializer<EnderCanvasRecipe> PEDESTAL_ENDER_CANVAS = register(
        "pedestal_ender_canvas", new EmptyRecipeSerializer<>(EnderCanvasRecipe::new));
    public static final RecipeSerializer<EnderCanvasLargeRecipe> PEDESTAL_ENDER_CANVAS_LARGE = register(
        "pedestal_ender_canvas_large", new EmptyRecipeSerializer<>(EnderCanvasLargeRecipe::new));

    // Anvil Crushing
    public static final RecipeSerializer<AnvilCrushingRecipe> ANVIL_CRUSHING_RECIPE_SERIALIZER = register(
        "anvil_crushing", new AnvilCrushingRecipe.Serializer());

    // Fusion Shrine
    public static final RecipeSerializer<FusionShrineRecipe> FUSION_SHRINE_RECIPE_SERIALIZER = register(
        "fusion_shrine", new FusionShrineRecipe.Serializer());
    public static final RecipeSerializer<ShootingStarHardeningRecipe> SHOOTING_STAR_HARDENING = register(
        "shooting_star_hardening", new EmptyRecipeSerializer<>(ShootingStarHardeningRecipe::new));

    // Enchanter
    public static final RecipeSerializer<EnchanterCraftingRecipe> ENCHANTER_RECIPE_SERIALIZER = register(
        "enchanter", new EnchanterCraftingRecipe.Serializer());
    public static final RecipeSerializer<EnchantmentUpgradeRecipe> ENCHANTMENT_UPGRADE_RECIPE_SERIALIZER = register(
        "enchantment_upgrade", new EnchantmentUpgradeRecipe.Serializer());

    // Potion Workshop
    public static final RecipeSerializer<PotionWorkshopBrewingRecipe> POTION_WORKSHOP_BREWING_RECIPE_SERIALIZER
        = register("potion_workshop_brewing", new PotionWorkshopBrewingRecipe.Serializer());
    public static final RecipeSerializer<PotionWorkshopCraftingRecipe> POTION_WORKSHOP_CRAFTING_RECIPE_SERIALIZER
        = register("potion_workshop_crafting", new PotionWorkshopCraftingRecipe.Serializer());
    public static final RecipeSerializer<PotionWorkshopReactingRecipe> POTION_WORKSHOP_REACTING_SERIALIZER = register(
        "potion_workshop_reacting", new PotionWorkshopReactingRecipe.Serializer());

    // Fluid converting
    public static final FluidConvertingRecipe.Serializer<HumusConvertingRecipe> HUMUS_CONVERTING_SERIALIZER = register(
        "humus_converting", new FluidConvertingRecipe.Serializer<>(HumusConvertingRecipe::new));
    public static final FluidConvertingRecipe.Serializer<LiquidCrystalConvertingRecipe>
        LIQUID_CRYSTAL_CONVERTING_SERIALIZER = register(
        "liquid_crystal_converting", new FluidConvertingRecipe.Serializer<>(LiquidCrystalConvertingRecipe::new));
    public static final FluidConvertingRecipe.Serializer<MidnightSolutionConvertingRecipe>
        MIDNIGHT_SOLUTION_CONVERTING_SERIALIZER = register(
        "midnight_solution_converting", new FluidConvertingRecipe.Serializer<>(MidnightSolutionConvertingRecipe::new));
    public static final FluidConvertingRecipe.Serializer<DragonrotConvertingRecipe> DRAGONROT_CONVERTING_SERIALIZER
        = register("dragonrot_converting", new FluidConvertingRecipe.Serializer<>(DragonrotConvertingRecipe::new));
    public static final RecipeSerializer<MeatToRottenFleshRecipe> DRAGONROT_MEAT_TO_ROTTEN_FLESH = register(
        "meat_rotting", new EmptyRecipeSerializer<>(MeatToRottenFleshRecipe::new));

    // Spirit Instiller
    public static final RecipeSerializer<SpiritInstillerRecipe> SPIRIT_INSTILLING_SERIALIZER = register(
        "spirit_instiller", new SpiritInstillerRecipe.Serializer());
    public static final RecipeSerializer<SpawnerCreatureChangeRecipe> SPIRIT_INSTILLER_SPAWNER_CREATURE_CHANGE
        = register(
        "spirit_instiller_spawner_creature_change", new EmptyRecipeSerializer<>(SpawnerCreatureChangeRecipe::new));
    public static final RecipeSerializer<SpawnerMaxNearbyEntitiesChangeRecipe>
        SPIRIT_INSTILLER_SPAWNER_MAX_NEARBY_ENTITIES_CHANGE = register(
        "spirit_instiller_spawner_max_nearby_entities_change",
        new EmptyRecipeSerializer<>(SpawnerMaxNearbyEntitiesChangeRecipe::new)
    );
    public static final RecipeSerializer<SpawnerRequiredPlayerRangeChangeRecipe>
        SPIRIT_INSTILLER_SPAWNER_SPAWNER_PLAYER_RANGE_CHANGE = register(
        "spirit_instiller_spawner_spawner_player_range_change",
        new EmptyRecipeSerializer<>(SpawnerRequiredPlayerRangeChangeRecipe::new)
    );
    public static final RecipeSerializer<SpawnerSpawnCountChangeRecipe> SPIRIT_INSTILLER_SPAWNER_SPAWN_COUNT_CHANGE
        = register(
        "spirit_instiller_spawner_spawn_count_change", new EmptyRecipeSerializer<>(SpawnerSpawnCountChangeRecipe::new));
    public static final RecipeSerializer<SpawnerSpawnDelayChangeRecipe> SPIRIT_INSTILLER_SPAWNER_SPAWN_DELAY_CHANGE
        = register(
        "spirit_instiller_spawner_spawn_delay_change", new EmptyRecipeSerializer<>(SpawnerSpawnDelayChangeRecipe::new));
    public static final RecipeSerializer<HardcorePlayerRevivalRecipe> SPIRIT_INSTILLER_HARDCORE_PLAYER_REVIVAL
        = register(
        "spirit_instiller_hardcore_player_revival", new EmptyRecipeSerializer<>(HardcorePlayerRevivalRecipe::new));
    public static final RecipeSerializer<MemoryToHeadRecipe> SPIRIT_INSTILLER_MEMORY_TO_HEAD = register(
        "spirit_instiller_memory_to_head", new EmptyRecipeSerializer<>(MemoryToHeadRecipe::new));

    // Color Picker
    public static final RecipeSerializer<InkConvertingRecipe> INK_CONVERTING_RECIPE_SERIALIZER = register(
        "ink_converting", new InkConvertingRecipe.Serializer());

    // Crystallarieum
    public static final RecipeSerializer<CrystallarieumRecipe> CRYSTALLARIEUM_RECIPE_SERIALIZER = register(
        "crystallarieum_growing", new CrystallarieumRecipe.Serializer());

    // Cinderhearth
    public static final RecipeSerializer<CinderhearthRecipe> CINDERHEARTH_RECIPE_SERIALIZER = register(
        "cinderhearth", new CinderhearthRecipe.Serializer());

    // Titration Barrel
    public static final RecipeSerializer<TitrationBarrelRecipe> TITRATION_BARREL = register(
        "titration_barrel", new TitrationBarrelRecipe.Serializer());
    public static final RecipeSerializer<JadeWineRecipe> TITRATION_BARREL_JADE_WINE = register(
        "titration_barrel_jade_wine", new EmptyRecipeSerializer<>(JadeWineRecipe::new));
    public static final RecipeSerializer<AquaRegiaRecipe> TITRATION_BARREL_AQUA_REGIA = register(
        "titration_barrel_aqua_regia", new EmptyRecipeSerializer<>(AquaRegiaRecipe::new));
    public static final RecipeSerializer<NecteredViognierRecipe> TITRATION_BARREL_NECTERED_VIOGNIER = register(
        "titration_barrel_nectered_viognier", new EmptyRecipeSerializer<>(NecteredViognierRecipe::new));
    public static final RecipeSerializer<SuspiciousBrewRecipe> TITRATION_BARREL_SUSPICIOUS_BREW = register(
        "titration_barrel_suspicious_brew", new EmptyRecipeSerializer<>(SuspiciousBrewRecipe::new));
    public static final RecipeSerializer<CheongRecipe> TITRATION_BARREL_CHEONG = register(
        "titration_barrel_cheong", new EmptyRecipeSerializer<>(CheongRecipe::new));

    // Primordial Fire
    public static final RecipeSerializer<PrimordialFireBurningRecipe> PRIMORDIAL_FIRE_BURNING_RECIPE_SERIALIZER
        = register("primordial_fire_burning", new PrimordialFireBurningRecipe.Serializer());
    public static final RecipeSerializer<MemoryDementiaRecipe> MEMORY_DEMENTIA = register(
        "memory_dementia", new EmptyRecipeSerializer<>(MemoryDementiaRecipe::new));
    public static final RecipeSerializer<EnchantedBookUnsoulingRecipe> ENCHANTED_BOOK_UNSOULING = register(
        "enchanted_book_unsouling", new EnchantedBookUnsoulingRecipe.Serializer());

    // Ink Cantrips
    public static final RecipeSerializer<HealingRecipe> CANTRIP_HEALING_RECIPE_SERIALIZER
        = register("cantrip_healing", new HealingRecipe.Serializer());
    public static final RecipeSerializer<DegradingRecipe> CANTRIP_DEGRADING_RECIPE_SERIALIZER
        = register("cantrip_degrading", new DegradingRecipe.Serializer());


    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        REGISTRAR.register(id, () -> serializer);
        return serializer;
    }

    public static void register(IEventBus bus) {
        REGISTRAR.register(bus);
    }

}
