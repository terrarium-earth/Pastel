package earth.terrarium.pastel.compat.REI;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.REI.plugins.AnvilCrushingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.CinderhearthDisplay;
import earth.terrarium.pastel.compat.REI.plugins.CrystallarieumDisplay;
import earth.terrarium.pastel.compat.REI.plugins.DragonrotConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.EnchanterEnchantingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.EnchantmentUpgradeDisplay;
import earth.terrarium.pastel.compat.REI.plugins.FreezingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.FusionShrineDisplay;
import earth.terrarium.pastel.compat.REI.plugins.HeatingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.HumusConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.InkConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.LiquidCrystalConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.MidnightSolutionConvertingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.NaturesStaffConversionsDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PedestalCraftingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopBrewingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopCraftingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PotionWorkshopReactingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.PrimordialFireBurningDisplay;
import earth.terrarium.pastel.compat.REI.plugins.SpiritInstillingDisplay;
import earth.terrarium.pastel.compat.REI.plugins.TitrationBarrelDisplay;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

public class PastelPlugins {

    public static final CategoryIdentifier<PedestalCraftingDisplay> PEDESTAL_CRAFTING = CategoryIdentifier
        .of(
            PastelCommon.locate("pedestal_crafting")
        );

    public static final CategoryIdentifier<AnvilCrushingDisplay> ANVIL_CRUSHING = CategoryIdentifier
        .of(
            PastelCommon.locate("anvil_crushing")
        );

    public static final CategoryIdentifier<FusionShrineDisplay> FUSION_SHRINE = CategoryIdentifier
        .of(
            PastelCommon.locate("fusion_shrine")
        );

    public static final CategoryIdentifier<NaturesStaffConversionsDisplay> NATURES_STAFF = CategoryIdentifier
        .of(
            PastelCommon.locate("natures_staff_conversions")
        );

    public static final CategoryIdentifier<EnchanterEnchantingDisplay> ENCHANTER_CRAFTING = CategoryIdentifier
        .of(
            PastelCommon.locate("enchanter")
        );

    public static final CategoryIdentifier<EnchantmentUpgradeDisplay> ENCHANTMENT_UPGRADE = CategoryIdentifier
        .of(
            PastelCommon.locate("enchantment_upgrade")
        );

    public static final CategoryIdentifier<PotionWorkshopBrewingDisplay> POTION_WORKSHOP_BREWING = CategoryIdentifier
        .of(PastelCommon.locate("potion_workshop_brewing"));

    public static final CategoryIdentifier<PotionWorkshopCraftingDisplay> POTION_WORKSHOP_CRAFTING = CategoryIdentifier
        .of(PastelCommon.locate("potion_workshop_crafting"));

    public static final CategoryIdentifier<PotionWorkshopReactingDisplay> POTION_WORKSHOP_REACTING = CategoryIdentifier
        .of(PastelCommon.locate("potion_workshop_reacting"));

    public static final CategoryIdentifier<SpiritInstillingDisplay> SPIRIT_INSTILLER = CategoryIdentifier
        .of(
            PastelCommon.locate("spirit_instiller")
        );

    public static final CategoryIdentifier<HumusConvertingDisplay> HUMUS_CONVERTING = CategoryIdentifier
        .of(
            PastelCommon.locate("humus_converting")
        );

    public static final CategoryIdentifier<LiquidCrystalConvertingDisplay> LIQUID_CRYSTAL_CONVERTING = CategoryIdentifier
        .of(PastelCommon.locate("liquid_crystal_converting"));

    public static final CategoryIdentifier<MidnightSolutionConvertingDisplay> MIDNIGHT_SOLUTION_CONVERTING = CategoryIdentifier
        .of(PastelCommon.locate("midnight_solution_converting"));

    public static final CategoryIdentifier<DragonrotConvertingDisplay> DRAGONROT_CONVERTING = CategoryIdentifier
        .of(
            PastelCommon.locate("dragonrot_converting")
        );

    public static final CategoryIdentifier<HeatingDisplay> HEATING = CategoryIdentifier
        .of(
            PastelCommon.locate("heating")
        );

    public static final CategoryIdentifier<FreezingDisplay> FREEZING = CategoryIdentifier
        .of(
            PastelCommon.locate("freezing")
        );

    public static final CategoryIdentifier<InkConvertingDisplay> INK_CONVERTING = CategoryIdentifier
        .of(
            PastelCommon.locate("ink_converting")
        );

    public static final CategoryIdentifier<CrystallarieumDisplay> CRYSTALLARIEUM = CategoryIdentifier
        .of(
            PastelCommon.locate("crystallarieum")
        );

    public static final CategoryIdentifier<CinderhearthDisplay> CINDERHEARTH = CategoryIdentifier
        .of(
            PastelCommon.locate("cinderhearth")
        );

    public static final CategoryIdentifier<TitrationBarrelDisplay> TITRATION_BARREL = CategoryIdentifier
        .of(
            PastelCommon.locate("titration_barrel")
        );

    public static final CategoryIdentifier<PrimordialFireBurningDisplay> PRIMORDIAL_FIRE_BURNING = CategoryIdentifier
        .of(PastelCommon.locate("primordial_fire_burning"));

}
