package earth.terrarium.pastel.registries;

import com.cmdpro.databank.misc.ColorGradient;
import com.mojang.datafixers.util.Pair;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.CreativeOnlyItem;
import earth.terrarium.pastel.blocks.gravity.FloatItem;
import earth.terrarium.pastel.blocks.jade_vines.GerminatedJadeVineBulbItem;
import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.components.PairedFoodComponent;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.items.ConcealingOilsItem;
import earth.terrarium.pastel.items.CookbookItem;
import earth.terrarium.pastel.items.DecayPlacerItem;
import earth.terrarium.pastel.items.EmptyFluidEntityBucketItem;
import earth.terrarium.pastel.items.GuidebookItem;
import earth.terrarium.pastel.items.ItemWithGlint;
import earth.terrarium.pastel.items.ItemWithLoomPattern;
import earth.terrarium.pastel.items.ItemWithTooltip;
import earth.terrarium.pastel.items.MidnightAberrationItem;
import earth.terrarium.pastel.items.MysteriousCompassItem;
import earth.terrarium.pastel.items.MysteriousLocketItem;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.items.StructurePlacerItem;
import earth.terrarium.pastel.items.armor.BedrockArmorItem;
import earth.terrarium.pastel.items.armor.GemstoneArmorItem;
import earth.terrarium.pastel.items.bundles.ExtendedBundleItem;
import earth.terrarium.pastel.items.conditional.GemstonePowderItem;
import earth.terrarium.pastel.items.conditional.StormStoneItem;
import earth.terrarium.pastel.items.energy.ArtistsPaletteItem;
import earth.terrarium.pastel.items.energy.CreativeInkAssortmentItem;
import earth.terrarium.pastel.items.energy.InkAssortmentItem;
import earth.terrarium.pastel.items.energy.InkFlaskItem;
import earth.terrarium.pastel.items.energy.PigmentPaletteItem;
import earth.terrarium.pastel.items.food.AliasedTooltipItem;
import earth.terrarium.pastel.items.food.AzaleaTeaItem;
import earth.terrarium.pastel.items.food.BloodOrchidDrinkItem;
import earth.terrarium.pastel.items.food.ClottedCreamItem;
import earth.terrarium.pastel.items.food.DrinkItem;
import earth.terrarium.pastel.items.food.EnchantedStarCandyItem;
import earth.terrarium.pastel.items.food.FreigeistItem;
import earth.terrarium.pastel.items.food.MoonstruckNectarItem;
import earth.terrarium.pastel.items.food.NectardewBurgeonItem;
import earth.terrarium.pastel.items.food.RestorationTeaItem;
import earth.terrarium.pastel.items.food.SedativesItem;
import earth.terrarium.pastel.items.food.StackableStewItem;
import earth.terrarium.pastel.items.food.StarCandyItem;
import earth.terrarium.pastel.items.food.beverages.BeverageItem;
import earth.terrarium.pastel.items.food.beverages.EvernectarItem;
import earth.terrarium.pastel.items.food.beverages.JadeWineItem;
import earth.terrarium.pastel.items.food.beverages.RepriseItem;
import earth.terrarium.pastel.items.food.beverages.SuspiciousBrewItem;
import earth.terrarium.pastel.items.item_frame.PhantomFrameItem;
import earth.terrarium.pastel.items.item_frame.PhantomGlowFrameItem;
import earth.terrarium.pastel.items.magic_items.BagOfHoldingItem;
import earth.terrarium.pastel.items.magic_items.BlockFlooderItem;
import earth.terrarium.pastel.items.magic_items.CelestialPocketWatchItem;
import earth.terrarium.pastel.items.magic_items.ConstructorsStaffItem;
import earth.terrarium.pastel.items.magic_items.CraftingTabletItem;
import earth.terrarium.pastel.items.magic_items.EnchantmentCanvasItem;
import earth.terrarium.pastel.items.magic_items.EnderSpliceItem;
import earth.terrarium.pastel.items.magic_items.EverpromiseRibbonItem;
import earth.terrarium.pastel.items.magic_items.ExchangeStaffItem;
import earth.terrarium.pastel.items.magic_items.GildedBookItem;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.items.magic_items.NaturesStaffItem;
import earth.terrarium.pastel.items.magic_items.PaintbrushItem;
import earth.terrarium.pastel.items.magic_items.PerturbedEyeItem;
import earth.terrarium.pastel.items.magic_items.PipeBombItem;
import earth.terrarium.pastel.items.magic_items.RadianceStaffItem;
import earth.terrarium.pastel.items.magic_items.StaffOfRemembranceItem;
import earth.terrarium.pastel.items.magic_items.ampoules.AzuriteGlassAmpouleItem;
import earth.terrarium.pastel.items.magic_items.ampoules.BloodstoneGlassAmpouleItem;
import earth.terrarium.pastel.items.magic_items.ampoules.MalachiteGlassAmpouleItem;
import earth.terrarium.pastel.items.map.ArtisansAtlasItem;
import earth.terrarium.pastel.items.misc.AetherVestigesItem;
import earth.terrarium.pastel.items.misc.AshItem;
import earth.terrarium.pastel.items.tools.BedrockAxeItem;
import earth.terrarium.pastel.items.tools.BedrockBowItem;
import earth.terrarium.pastel.items.tools.BedrockCrossbowItem;
import earth.terrarium.pastel.items.tools.BedrockFishingRodItem;
import earth.terrarium.pastel.items.tools.BedrockHoeItem;
import earth.terrarium.pastel.items.tools.BedrockShearsItem;
import earth.terrarium.pastel.items.tools.BedrockShovelItem;
import earth.terrarium.pastel.items.tools.BedrockSwordItem;
import earth.terrarium.pastel.items.tools.DraconicTwinswordItem;
import earth.terrarium.pastel.items.tools.DragonTalonItem;
import earth.terrarium.pastel.items.tools.DreamflayerItem;
import earth.terrarium.pastel.items.tools.FerociousBidentItem;
import earth.terrarium.pastel.items.tools.FractalBidentItem;
import earth.terrarium.pastel.items.tools.GlassArrowItem;
import earth.terrarium.pastel.items.tools.GlassArrowVariant;
import earth.terrarium.pastel.items.tools.GlassCrestCrossbowItem;
import earth.terrarium.pastel.items.tools.GlassCrestGreatswordItem;
import earth.terrarium.pastel.items.tools.GlassCrestWorkstaffItem;
import earth.terrarium.pastel.items.tools.GlintlessPickaxe;
import earth.terrarium.pastel.items.tools.GreatswordItem;
import earth.terrarium.pastel.items.tools.LagoonRodItem;
import earth.terrarium.pastel.items.tools.LightGreatswordItem;
import earth.terrarium.pastel.items.tools.MalachiteBidentItem;
import earth.terrarium.pastel.items.tools.MalachiteCrossbowItem;
import earth.terrarium.pastel.items.tools.MoltenRodItem;
import earth.terrarium.pastel.items.tools.NectarLanceItem;
import earth.terrarium.pastel.items.tools.NightSaltsItem;
import earth.terrarium.pastel.items.tools.NightfallsBladeItem;
import earth.terrarium.pastel.items.tools.OblivionPickaxeItem;
import earth.terrarium.pastel.items.tools.OmniAcceleratorItem;
import earth.terrarium.pastel.items.tools.PastelPickaxeItem;
import earth.terrarium.pastel.items.tools.PreenchantedMultiToolItem;
import earth.terrarium.pastel.items.tools.PrimordialLighterItem;
import earth.terrarium.pastel.items.tools.RazorFalchionItem;
import earth.terrarium.pastel.items.tools.SoothingBouquetItem;
import earth.terrarium.pastel.items.tools.TuningStampItem;
import earth.terrarium.pastel.items.tools.WorkstaffItem;
import earth.terrarium.pastel.items.trinkets.AetherGracedNectarGlovesItem;
import earth.terrarium.pastel.items.trinkets.AshenCircletItem;
import earth.terrarium.pastel.items.trinkets.AttackRingItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeAmuletItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeBeltItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeCoreItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeRingItem;
import earth.terrarium.pastel.items.trinkets.CircletOfArroganceItem;
import earth.terrarium.pastel.items.trinkets.CottonCloudBootsItem;
import earth.terrarium.pastel.items.trinkets.ExtraHealthRingItem;
import earth.terrarium.pastel.items.trinkets.ExtraMiningSpeedRingItem;
import earth.terrarium.pastel.items.trinkets.ExtraReachGlovesItem;
import earth.terrarium.pastel.items.trinkets.GleamingPinItem;
import earth.terrarium.pastel.items.trinkets.GlowVisionGogglesItem;
import earth.terrarium.pastel.items.trinkets.InkDrainTrinketItem;
import earth.terrarium.pastel.items.trinkets.LaurelsOfSerenityItem;
import earth.terrarium.pastel.items.trinkets.NeatRingItem;
import earth.terrarium.pastel.items.trinkets.PotionPendantItem;
import earth.terrarium.pastel.items.trinkets.PuffCircletItem;
import earth.terrarium.pastel.items.trinkets.RadiancePinItem;
import earth.terrarium.pastel.items.trinkets.RingOfAerialGraceItem;
import earth.terrarium.pastel.items.trinkets.RingOfDenserStepsItem;
import earth.terrarium.pastel.items.trinkets.SevenLeagueBootsItem;
import earth.terrarium.pastel.items.trinkets.TakeOffBeltItem;
import earth.terrarium.pastel.items.trinkets.TotemPendantItem;
import earth.terrarium.pastel.items.trinkets.WeepingCircletItem;
import earth.terrarium.pastel.items.trinkets.WhispyCircletItem;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static earth.terrarium.pastel.PastelCommon.CONFIG;
import static earth.terrarium.pastel.PastelCommon.locate;
import static net.minecraft.world.item.Items.BLACK_DYE;
import static net.minecraft.world.item.Items.BLUE_DYE;
import static net.minecraft.world.item.Items.BROWN_DYE;
import static net.minecraft.world.item.Items.BUCKET;
import static net.minecraft.world.item.Items.BUNDLE;
import static net.minecraft.world.item.Items.CREEPER_BANNER_PATTERN;
import static net.minecraft.world.item.Items.CYAN_DYE;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;
import static net.minecraft.world.item.Items.GOLDEN_CARROT;
import static net.minecraft.world.item.Items.GRAY_DYE;
import static net.minecraft.world.item.Items.GREEN_DYE;
import static net.minecraft.world.item.Items.LIGHT_BLUE_DYE;
import static net.minecraft.world.item.Items.LIGHT_GRAY_DYE;
import static net.minecraft.world.item.Items.LIME_DYE;
import static net.minecraft.world.item.Items.MAGENTA_DYE;
import static net.minecraft.world.item.Items.ORANGE_DYE;
import static net.minecraft.world.item.Items.PINK_DYE;
import static net.minecraft.world.item.Items.PURPLE_DYE;
import static net.minecraft.world.item.Items.RED_DYE;
import static net.minecraft.world.item.Items.WHITE_DYE;
import static net.minecraft.world.item.Items.YELLOW_DYE;

//TODO: I am not sure how our tools are implemented rn but they REALLY should be migrated to working off of tool
// components. ~ Azzyy (whom will not be the one doing this)
public class PastelItems {

    public static final DeferredRegister.Items ITEM_REGISTRAR = DeferredRegister.createItems(PastelCommon.MOD_ID);
    public static final List<Pair<ItemLike, Integer>> BURN_TIMES = new ArrayList<>();

    // Main items
    public static final DeferredItem<Item> GUIDEBOOK = register(
        simple(item("guidebook", () -> new GuidebookItem(IS.of(1)), InkColors.WHITE)));
    public static final DeferredItem<Item> PAINTBRUSH = register(
        item("paintbrush", () -> new PaintbrushItem(IS.of(1)), InkColors.WHITE));
    public static final DeferredItem<Item> TUNING_STAMP = register(
        item("tuning_stamp", () -> new TuningStampItem(IS.of(1)), InkColors.WHITE));
    public static final DeferredItem<Item> CRAFTING_TABLET = register(
        simple(item("crafting_tablet", () -> new CraftingTabletItem(IS.of(1)), InkColors.LIGHT_GRAY)));

    // Structure placers
    public static final DeferredItem<Item> PEDESTAL_TIER_1_STRUCTURE_PLACER = register(simple(item(
        "pedestal_tier_1_structure_placer", () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.PEDESTAL_SIMPLE),
        InkColors.WHITE
    )));
    public static final DeferredItem<Item> PEDESTAL_TIER_2_STRUCTURE_PLACER = register(simple(item(
        "pedestal_tier_2_structure_placer",
        () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.PEDESTAL_ADVANCED), InkColors.WHITE
    )));
    public static final DeferredItem<Item> PEDESTAL_TIER_3_STRUCTURE_PLACER = register(simple(item(
        "pedestal_tier_3_structure_placer", () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.PEDESTAL_COMPLEX),
        InkColors.WHITE
    )));
    public static final DeferredItem<Item> FUSION_SHRINE_STRUCTURE_PLACER = register(simple(item(
        "fusion_shrine_structure_placer", () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.FUSION_SHRINE),
        InkColors.WHITE
    )));
    public static final DeferredItem<Item> ENCHANTER_STRUCTURE_PLACER = register(simple(item(
        "enchanter_structure_placer", () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.ENCHANTER),
        InkColors.WHITE
    )));
    public static final DeferredItem<Item> SPIRIT_INSTILLER_STRUCTURE_PLACER = register(simple(item(
        "spirit_instiller_structure_placer",
        () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.SPIRIT_INSTILLER), InkColors.WHITE
    )));
    public static final DeferredItem<Item> CINDERHEARTH_STRUCTURE_PLACER = register(simple(item(
        "cinderhearth_structure_placer", () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.CINDERHEARTH),
        InkColors.WHITE
    )));

    // Gem shards and powders
    public static final DeferredItem<Item> TOPAZ_SHARD = register(simple(item("topaz_shard", () -> new Item(IS.of()), InkColors.CYAN)));
    public static final DeferredItem<Item> CITRINE_SHARD = register(simple(item("citrine_shard", () -> new Item(IS.of()), InkColors.YELLOW)));
    public static final DeferredItem<Item> ONYX_SHARD = register(simple(item("onyx_shard", () -> new Item(IS.of()), InkColors.BLACK)));
    public static final DeferredItem<Item> MOONSTONE_SHARD = register(simple(item("moonstone_shard", () -> new Item(IS.of()), InkColors.WHITE)));
    public static final DeferredItem<Item> SPECTRAL_SHARD = register(simple(item("spectral_shard", () -> new Item(IS.of(Rarity.RARE)), InkColors.WHITE)));

    public static final DeferredItem<Item> TOPAZ_POWDER = register(simple(item("topaz_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.CYAN), InkColors.CYAN)));
    public static final DeferredItem<Item> AMETHYST_POWDER = register(simple(item("amethyst_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.MAGENTA), InkColors.MAGENTA)));
    public static final DeferredItem<Item> CITRINE_POWDER = register(simple(item("citrine_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.YELLOW), InkColors.YELLOW)));
    public static final DeferredItem<Item> ONYX_POWDER = register(simple(item("onyx_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.BLACK), InkColors.BLACK)));
    public static final DeferredItem<Item> MOONSTONE_POWDER = register(simple(item("moonstone_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.WHITE), InkColors.WHITE)));

    // Pigment
    public static final DeferredItem<Item> WHITE_PIGMENT = register(
        simple(item("white_pigment", () -> new PigmentItem(IS.of(), InkColors.WHITE, WHITE_DYE), InkColors.WHITE)));
    public static final DeferredItem<Item> ORANGE_PIGMENT = register(
        simple(item("orange_pigment", () -> new PigmentItem(IS.of(), InkColors.ORANGE, ORANGE_DYE), InkColors.ORANGE)));
    public static final DeferredItem<Item> MAGENTA_PIGMENT = register(simple(
        item("magenta_pigment", () -> new PigmentItem(IS.of(), InkColors.MAGENTA, MAGENTA_DYE), InkColors.MAGENTA)));
    public static final DeferredItem<Item> LIGHT_BLUE_PIGMENT = register(simple(item(
        "light_blue_pigment", () -> new PigmentItem(IS.of(), InkColors.LIGHT_BLUE, LIGHT_BLUE_DYE),
        InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> YELLOW_PIGMENT = register(
        simple(item("yellow_pigment", () -> new PigmentItem(IS.of(), InkColors.YELLOW, YELLOW_DYE), InkColors.YELLOW)));
    public static final DeferredItem<Item> LIME_PIGMENT = register(
        simple(item("lime_pigment", () -> new PigmentItem(IS.of(), InkColors.LIME, LIME_DYE), InkColors.LIME)));
    public static final DeferredItem<Item> PINK_PIGMENT = register(
        simple(item("pink_pigment", () -> new PigmentItem(IS.of(), InkColors.PINK, PINK_DYE), InkColors.PINK)));
    public static final DeferredItem<Item> GRAY_PIGMENT = register(
        simple(item("gray_pigment", () -> new PigmentItem(IS.of(), InkColors.GRAY, GRAY_DYE), InkColors.GRAY)));
    public static final DeferredItem<Item> LIGHT_GRAY_PIGMENT = register(simple(item(
        "light_gray_pigment", () -> new PigmentItem(IS.of(), InkColors.LIGHT_GRAY, LIGHT_GRAY_DYE),
        InkColors.LIGHT_GRAY
    )));
    public static final DeferredItem<Item> CYAN_PIGMENT = register(
        simple(item("cyan_pigment", () -> new PigmentItem(IS.of(), InkColors.CYAN, CYAN_DYE), InkColors.CYAN)));
    public static final DeferredItem<Item> PURPLE_PIGMENT = register(
        simple(item("purple_pigment", () -> new PigmentItem(IS.of(), InkColors.PURPLE, PURPLE_DYE), InkColors.PURPLE)));
    public static final DeferredItem<Item> BLUE_PIGMENT = register(
        simple(item("blue_pigment", () -> new PigmentItem(IS.of(), InkColors.BLUE, BLUE_DYE), InkColors.BLUE)));
    public static final DeferredItem<Item> BROWN_PIGMENT = register(
        simple(item("brown_pigment", () -> new PigmentItem(IS.of(), InkColors.BROWN, BROWN_DYE), InkColors.BROWN)));
    public static final DeferredItem<Item> GREEN_PIGMENT = register(
        simple(item("green_pigment", () -> new PigmentItem(IS.of(), InkColors.GREEN, GREEN_DYE), InkColors.GREEN)));
    public static final DeferredItem<Item> RED_PIGMENT = register(
        simple(item("red_pigment", () -> new PigmentItem(IS.of(), InkColors.RED, RED_DYE), InkColors.RED)));
    public static final DeferredItem<Item> BLACK_PIGMENT = register(
        simple(item("black_pigment", () -> new PigmentItem(IS.of(), InkColors.BLACK, BLACK_DYE), InkColors.BLACK)));

    // Preenchanted tools
    public static final DeferredItem<PreenchantedMultiToolItem> MULTITOOL = register(handheld(item(
        "multitool", () -> new PreenchantedMultiToolItem(
            Tiers.IRON, 2, -2.4F, IS.of(Rarity.UNCOMMON)
                                    .durability(Tiers.IRON.getUses())
        ), InkColors.BROWN
    )));
    public static final DeferredItem<GlintlessPickaxe> TENDER_PICKAXE = register(handheld(item(
        "tender_pickaxe", () -> new GlintlessPickaxe(
            PastelToolMaterial.LOW_HEALTH, IS.of(Rarity.UNCOMMON)
                                             .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                                             .attributes(PickaxeItem.createAttributes(
                                                 PastelToolMaterial.LOW_HEALTH, 1, -2.8F))
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(Enchantments.SILK_TOUCH, 1);
            }
        }, InkColors.BLUE
    )));
    public static final DeferredItem<GlintlessPickaxe> LUCKY_PICKAXE = register(handheld(item(
        "lucky_pickaxe", () -> new GlintlessPickaxe(
            PastelToolMaterial.LOW_HEALTH, IS.of(Rarity.UNCOMMON)
                                             .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                                             .attributes(PickaxeItem.createAttributes(
                                                 PastelToolMaterial.LOW_HEALTH, 1, -2.8F))
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(Enchantments.FORTUNE, 3);
            }
        }, InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<RazorFalchionItem> RAZOR_FALCHION = register(handheld(item(
        "razor_falchion", () -> new RazorFalchionItem(
            PastelToolMaterial.LOW_HEALTH, IS.of(Rarity.UNCOMMON)
                                             .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                                             .attributes(SwordItem.createAttributes(
                                                 PastelToolMaterial.LOW_HEALTH, 4, -2.2F))
        ), InkColors.RED
    )));
    public static final DeferredItem<OblivionPickaxeItem> OBLIVION_PICKAXE = register(handheld(item(
        "oblivion_pickaxe", () -> new OblivionPickaxeItem(
            PastelToolMaterial.VOIDING, IS.of(Rarity.UNCOMMON)
                                          .durability(PastelToolMaterial.VOIDING.getUses())
                                          .attributes(PickaxeItem.createAttributes(
                                              PastelToolMaterial.VOIDING, 1, -2.8F))
        ), InkColors.GRAY
    )));
    public static final DeferredItem<GlintlessPickaxe> RESONANT_PICKAXE = register(handheld(item(
        "resonant_pickaxe", () -> new GlintlessPickaxe(
            PastelToolMaterial.LOW_HEALTH_MINING_LEVEL_4, IS.of(Rarity.UNCOMMON)
                                                            .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                                                            .attributes(PickaxeItem.createAttributes(
                                                                PastelToolMaterial.LOW_HEALTH_MINING_LEVEL_4, 1, -2.8F))
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(PastelEnchantments.RESONANCE, 1);
            }
        }, InkColors.WHITE
    )));
    public static final DeferredItem<GlintlessPickaxe> DRAGONRENDING_PICKAXE = register(handheld(item(
        "dragonrending_pickaxe", () -> new GlintlessPickaxe(
            PastelToolMaterial.DRACONIC, IS.of(Rarity.UNCOMMON)
                                           .durability(PastelToolMaterial.DRACONIC.getUses())
                                           .attributes(PickaxeItem.createAttributes(
                                               PastelToolMaterial.DRACONIC, 1, -2.8F))
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(PastelEnchantments.RAZING, 3);
            }
        }, InkColors.WHITE
    )));
    public static final DeferredItem<LagoonRodItem> LAGOON_ROD = register(item(
        "lagoon_rod", () -> new LagoonRodItem(IS.of()
                                                .durability(256)), InkColors.LIGHT_BLUE
    ));
    public static final DeferredItem<MoltenRodItem> MOLTEN_ROD = register(item(
        "molten_rod", () -> new MoltenRodItem(IS.of()
                                                .durability(256)), InkColors.ORANGE
    ));

    // Bedrock Tools
    public static final DeferredItem<PastelPickaxeItem> BEDROCK_PICKAXE = register(handheld(item(
        "bedrock_pickaxe", () -> new PastelPickaxeItem(
            PastelToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON)
                                          .attributes(PickaxeItem.createAttributes(
                                              PastelToolMaterial.BEDROCK, 1, -2.8F))
                                          .fireResistant()
                                          .durability(PastelToolMaterial.BEDROCK.getUses())
                                          .component(
                                              DataComponents.UNBREAKABLE, new Unbreakable(false))
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(Enchantments.SILK_TOUCH, 1);
            }
        }, InkColors.BLACK
    )));
    public static final DeferredItem<BedrockAxeItem> BEDROCK_AXE = register(item(
        "bedrock_axe", () -> new BedrockAxeItem(
            PastelToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON)
                                          .attributes(AxeItem.createAttributes(PastelToolMaterial.BEDROCK, 5, -3.0F))
                                          .fireResistant()
                                          .durability(PastelToolMaterial.BEDROCK.getUses())
                                          .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
        ), InkColors.BLACK
    ));
    public static final DeferredItem<BedrockShovelItem> BEDROCK_SHOVEL = register(handheld(item(
        "bedrock_shovel", () -> new BedrockShovelItem(
            PastelToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON)
                                          .attributes(ShovelItem.createAttributes(PastelToolMaterial.BEDROCK, 1, -3.0F))
                                          .fireResistant()
                                          .durability(PastelToolMaterial.BEDROCK.getUses())
                                          .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
        ), InkColors.BLACK
    )));
    public static final DeferredItem<BedrockSwordItem> BEDROCK_SWORD = register(item(
        "bedrock_sword", () -> new BedrockSwordItem(
            PastelToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON)
                                          .attributes(SwordItem.createAttributes(PastelToolMaterial.BEDROCK, 4, -2.4F))
                                          .fireResistant()
                                          .durability(PastelToolMaterial.BEDROCK.getUses())
                                          .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
        ), InkColors.BLACK
    ));
    public static final DeferredItem<BedrockHoeItem> BEDROCK_HOE = register(handheld(item(
        "bedrock_hoe", () -> new BedrockHoeItem(
            PastelToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON)
                                          .attributes(HoeItem.createAttributes(PastelToolMaterial.BEDROCK, 2, -0.0F))
                                          .fireResistant()
                                          .durability(PastelToolMaterial.BEDROCK.getUses())
                                          .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
        ), InkColors.BLACK
    )));
    public static final DeferredItem<BedrockBowItem> BEDROCK_BOW = register(item(
        "bedrock_bow", () -> new BedrockBowItem(IS.of(Rarity.UNCOMMON)
                                                  .fireResistant()
                                                  .durability(PastelToolMaterial.BEDROCK.getUses())
                                                  .component(DataComponents.UNBREAKABLE, new Unbreakable(false))),
        InkColors.BLACK
    ));
    public static final DeferredItem<BedrockCrossbowItem> BEDROCK_CROSSBOW = register(item(
        "bedrock_crossbow", () -> new BedrockCrossbowItem(IS.of(Rarity.UNCOMMON)
                                                            .fireResistant()
                                                            .durability(PastelToolMaterial.BEDROCK.getUses())
                                                            .component(
                                                                DataComponents.UNBREAKABLE, new Unbreakable(false))),
        InkColors.BLACK
    ));
    public static final DeferredItem<BedrockShearsItem> BEDROCK_SHEARS = register(simple(item(
        "bedrock_shears", () -> new BedrockShearsItem(IS.of(Rarity.UNCOMMON)
                                                        .fireResistant()
                                                        .durability(PastelToolMaterial.BEDROCK.getUses())
                                                        .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                                                        .component(
                                                            DataComponents.TOOL, ShearsItem.createToolProperties())),
        InkColors.BLACK
    )));
    public static final DeferredItem<BedrockFishingRodItem> BEDROCK_FISHING_ROD = register(item(
        "bedrock_fishing_rod", () -> new BedrockFishingRodItem(IS.of(Rarity.UNCOMMON)
                                                                 .fireResistant()
                                                                 .durability(PastelToolMaterial.BEDROCK.getUses())
                                                                 .component(
                                                                     DataComponents.UNBREAKABLE,
                                                                     new Unbreakable(false)
                                                                 )), InkColors.BLACK
    ));

    public static final DeferredItem<WorkstaffItem> MALACHITE_WORKSTAFF = register(item(
        "malachite_workstaff", () -> new WorkstaffItem(
            PastelToolMaterial.MALACHITE, 1, -3.2F, IS.of(1, Rarity.UNCOMMON)), InkColors.GREEN
    ));
    public static final DeferredItem<GreatswordItem> MALACHITE_ULTRA_GREATSWORD = register(item(
        "malachite_ultra_greatsword",
        () -> new GreatswordItem(PastelToolMaterial.MALACHITE, 7, -2.8F, 1.0F, IS.of(1, Rarity.UNCOMMON)),
        InkColors.GREEN
    ));
    public static final DeferredItem<MalachiteCrossbowItem> MALACHITE_CROSSBOW = register(item(
        "malachite_crossbow", () -> new MalachiteCrossbowItem(IS.of(1, Rarity.UNCOMMON)
                                                                .fireResistant()
                                                                .durability(PastelToolMaterial.MALACHITE.getUses())),
        InkColors.GREEN
    ));
    public static final DeferredItem<MalachiteBidentItem> MALACHITE_BIDENT = register(item(
        "malachite_bident", () -> new MalachiteBidentItem(
            IS.active(
                  1, Rarity.UNCOMMON)
              .durability(PastelToolMaterial.MALACHITE.getUses()), -2.4, 9, 0.25F, 0F
        ), InkColors.GREEN
    ));

    // variants by socketing a moonstone core
    public static final DeferredItem<GlassCrestWorkstaffItem> GLASS_CREST_WORKSTAFF = register(item(
        "glass_crest_workstaff",
        () -> new GlassCrestWorkstaffItem(PastelToolMaterial.GLASS_CREST, 1, -2.8F, IS.of(1, Rarity.UNCOMMON)),
        InkColors.WHITE
    ));
    public static final DeferredItem<GlassCrestGreatswordItem> GLASS_CREST_ULTRA_GREATSWORD = register(item(
        "glass_crest_ultra_greatsword",
        () -> new GlassCrestGreatswordItem(PastelToolMaterial.GLASS_CREST, 5, -2.8F, 1.0F, IS.of(1, Rarity.UNCOMMON)),
        InkColors.WHITE
    ));
    public static final DeferredItem<GlassCrestCrossbowItem> GLASS_CREST_CROSSBOW = register(item(
        "glass_crest_crossbow", () -> new GlassCrestCrossbowItem(IS.of(1, Rarity.UNCOMMON)
                                                                   .fireResistant()
                                                                   .durability(
                                                                       PastelToolMaterial.GLASS_CREST.getUses())),
        InkColors.WHITE
    ));
    public static final DeferredItem<FerociousBidentItem> FEROCIOUS_GLASS_CREST_BIDENT = register(item(
        "ferocious_glass_crest_bident", () -> new FerociousBidentItem(
            IS.active(1, Rarity.UNCOMMON)
              .durability(PastelToolMaterial.GLASS_CREST.getUses()), -2.2, 13, 0.33F, 0.33F
        ), InkColors.WHITE
    ));
    public static final DeferredItem<FractalBidentItem> FRACTAL_GLASS_CREST_BIDENT = register(item(
        "fractal_glass_crest_bident", () -> new FractalBidentItem(
            IS.active(1, Rarity.UNCOMMON)
              .durability(PastelToolMaterial.GLASS_CREST.getUses()), -2.4, 6.5, 0.25F, 0.25F
        ), InkColors.WHITE
    ));

    public static final DeferredItem<Item> MALACHITE_GLASS_ARROW = register(simple(item(
        "malachite_glass_arrow", () -> new GlassArrowItem(
            IS.of(Rarity.UNCOMMON), GlassArrowVariant.MALACHITE, ColoredCraftingParticleEffect.LIME), InkColors.GREEN
    )));
    public static final DeferredItem<Item> TOPAZ_GLASS_ARROW = register(simple(item(
        "topaz_glass_arrow", () -> new GlassArrowItem(
            IS.of(Rarity.UNCOMMON), GlassArrowVariant.TOPAZ, ColoredCraftingParticleEffect.CYAN), InkColors.CYAN
    )));
    public static final DeferredItem<Item> AMETHYST_GLASS_ARROW = register(simple(item(
        "amethyst_glass_arrow", () -> new GlassArrowItem(
            IS.of(Rarity.UNCOMMON), GlassArrowVariant.AMETHYST, ColoredCraftingParticleEffect.MAGENTA),
        InkColors.MAGENTA
    )));
    public static final DeferredItem<Item> CITRINE_GLASS_ARROW = register(simple(item(
        "citrine_glass_arrow", () -> new GlassArrowItem(
            IS.of(Rarity.UNCOMMON), GlassArrowVariant.CITRINE, ColoredCraftingParticleEffect.YELLOW), InkColors.YELLOW
    )));
    public static final DeferredItem<Item> ONYX_GLASS_ARROW = register(simple(item(
        "onyx_glass_arrow", () -> new GlassArrowItem(
            IS.of(Rarity.UNCOMMON), GlassArrowVariant.ONYX, ColoredCraftingParticleEffect.BLACK), InkColors.BLACK
    )));
    public static final DeferredItem<Item> MOONSTONE_GLASS_ARROW = register(simple(item(
        "moonstone_glass_arrow", () -> new GlassArrowItem(
            IS.of(Rarity.UNCOMMON), GlassArrowVariant.MOONSTONE, ColoredCraftingParticleEffect.WHITE), InkColors.WHITE
    )));

    public static final DeferredItem<Item> OMNI_ACCELERATOR = register(item(
        "omni_accelerator", () -> new OmniAcceleratorItem(IS.of(1, Rarity.UNCOMMON)
                                                            .component(
                                                                DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)),
        InkColors.YELLOW
    ));

    public static final DeferredItem<Item> AZURITE_GLASS_AMPOULE = register(simple(
        item("azurite_glass_ampoule", () -> new AzuriteGlassAmpouleItem(IS.of(Rarity.UNCOMMON)), InkColors.BLUE)));
    public static final DeferredItem<Item> BLOODSTONE_GLASS_AMPOULE = register(simple(item(
        "bloodstone_glass_ampoule", () -> new BloodstoneGlassAmpouleItem(IS.of(Rarity.UNCOMMON)
                                                                           .attributes(
                                                                               BloodstoneGlassAmpouleItem.createAttributeModifiers())),
        InkColors.RED
    )));
    public static final DeferredItem<Item> MALACHITE_GLASS_AMPOULE = register(layered(
        item("malachite_glass_ampoule", () -> new MalachiteGlassAmpouleItem(IS.of(Rarity.UNCOMMON)), InkColors.GREEN),
        "_base", "_overlay"
    ));

    // Special tools
    // TODO: set attribute modifiers similarly to how vanilla swords do it
    public static final DeferredItem<DreamflayerItem> DREAMFLAYER = register(item(
        "dreamflayer", () -> new DreamflayerItem(
            PastelToolMaterial.DREAMFLAYER, 3, -1.8F, IS.of(1, Rarity.UNCOMMON)), InkColors.RED
    ));
    public static final DeferredItem<NightfallsBladeItem> NIGHTFALLS_BLADE = register(item(
        "nightfalls_blade", () -> new NightfallsBladeItem(Tiers.DIAMOND, 3, -2.4F, IS.of(1, Rarity.UNCOMMON)),
        InkColors.GRAY
    ).withItemModel((ctx, item) -> PastelModelHelper.registerLayeredItemModel(
        ctx, item, PastelModels.HANDHELD_THREE_LAYERS, "", "_tint", "_overlay")));
    public static final DeferredItem<DraconicTwinswordItem> DRACONIC_TWINSWORD = register(item(
        "draconic_twinsword", () -> new DraconicTwinswordItem(
            PastelToolMaterial.DRACONIC, 6, -3.0F, IS.of(1, Rarity.RARE)), InkColors.YELLOW
    ));
    public static final DeferredItem<DragonTalonItem> DRAGON_TALON = register(item(
        "dragon_talon", () -> new DragonTalonItem(
            PastelToolMaterial.DRACONIC, -3.0, -1.0, IS.of(1, Rarity.RARE)
                                                       .durability(PastelToolMaterial.DRACONIC.getUses())
        ), InkColors.YELLOW
    ));
    public static final DeferredItem<LightGreatswordItem> KNOTTED_SWORD = register(item(
        "knotted_sword", () -> new LightGreatswordItem(
            PastelToolMaterial.VERDIGRIS, 3, -2.4F, 0.25F, 0.5F, 0xFFd4d6ff,
            new ColorGradient(new Color(139, 255, 213), new Color(0, 178, 183)).fadeAlpha(1, 0).fadeAlpha(0, 0, 1, 0.05f), IS.of(1, Rarity.UNCOMMON)
                                                                                   .durability(
                                                                                   PastelToolMaterial.VERDIGRIS.getUses())
        ), InkColors.GREEN
    ));
    public static final DeferredItem<NectarLanceItem> NECTAR_LANCE = register(item(
        "nectar_lance", () -> new NectarLanceItem(
            PastelToolMaterial.NECTAR, 0, -2.4F, 0.5F, 1.5F, 0xFFf8e8ff,
            new ColorGradient(new Color(246, 192, 255), new Color(155, 0, 204)).fadeAlpha(1, 0).fadeAlpha(0, 0, 1, 0.05f), IS.of(1, Rarity.EPIC)
                                                                                                   .durability(
                                                                               PastelToolMaterial.NECTAR.getUses())
        ), InkColors.PURPLE
    ));

    // Bedrock Armor
    public static final DeferredItem<BedrockArmorItem> BEDROCK_HELMET = register(simple(item(
        "bedrock_helmet", () -> new BedrockArmorItem(
            PastelArmorMaterials.BEDROCK, ArmorItem.Type.HELMET, IS.of(Rarity.UNCOMMON)
                                                                   .fireResistant()
                                                                   .durability(70 * 13)
                                                                   .component(
                                                                       DataComponents.UNBREAKABLE, new Unbreakable(
                                                                           false)
                                                                   )
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(Enchantments.PROJECTILE_PROTECTION, 5);
            }
        }, InkColors.BLACK
    )));
    public static final DeferredItem<BedrockArmorItem> BEDROCK_CHESTPLATE = register(simple(item(
        "bedrock_chestplate", () -> new BedrockArmorItem(
            PastelArmorMaterials.BEDROCK, ArmorItem.Type.CHESTPLATE, IS.of(Rarity.UNCOMMON)
                                                                       .fireResistant()
                                                                       .durability(70 * 15)
                                                                       .component(
                                                                           DataComponents.UNBREAKABLE, new Unbreakable(
                                                                               false)
                                                                       )
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(Enchantments.PROTECTION, 5);
            }
        }, InkColors.BLACK
    )));
    public static final DeferredItem<BedrockArmorItem> BEDROCK_LEGGINGS = register(simple(item(
        "bedrock_leggings", () -> new BedrockArmorItem(
            PastelArmorMaterials.BEDROCK, ArmorItem.Type.LEGGINGS, IS.of(Rarity.UNCOMMON)
                                                                     .fireResistant()
                                                                     .durability(70 * 16)
                                                                     .component(
                                                                         DataComponents.UNBREAKABLE, new Unbreakable(
                                                                             false)
                                                                     )
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(Enchantments.BLAST_PROTECTION, 5);
            }
        }, InkColors.BLACK
    )));
    public static final DeferredItem<BedrockArmorItem> BEDROCK_BOOTS = register(simple(item(
        "bedrock_boots", () -> new BedrockArmorItem(
            PastelArmorMaterials.BEDROCK, ArmorItem.Type.BOOTS, IS.of(Rarity.UNCOMMON)
                                                                  .fireResistant()
                                                                  .durability(70 * 11)
                                                                  .component(
                                                                      DataComponents.UNBREAKABLE, new Unbreakable(
                                                                          false)
                                                                  )
        ) {
            @Override
            public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                return Map.of(Enchantments.FIRE_PROTECTION, 5);
            }
        }, InkColors.BLACK
    )));

    // Armor
    public static final DeferredItem<GemstoneArmorItem> FETCHLING_HELMET = register(simple(item(
        "fetchling_helmet", () -> new GemstoneArmorItem(
            PastelArmorMaterials.GEMSTONE, ArmorItem.Type.HELMET, IS.of(Rarity.UNCOMMON)
                                                                    .durability(9 * 13)
        ), InkColors.BLUE
    )));
    public static final DeferredItem<GemstoneArmorItem> FEROCIOUS_CHESTPLATE = register(simple(item(
        "ferocious_chestplate", () -> new GemstoneArmorItem(
            PastelArmorMaterials.GEMSTONE, ArmorItem.Type.CHESTPLATE, IS.of(Rarity.UNCOMMON)
                                                                        .durability(9 * 15)
        ), InkColors.BLUE
    )));
    public static final DeferredItem<GemstoneArmorItem> SYLPH_LEGGINGS = register(simple(item(
        "sylph_leggings", () -> new GemstoneArmorItem(
            PastelArmorMaterials.GEMSTONE, ArmorItem.Type.LEGGINGS, IS.of(Rarity.UNCOMMON)
                                                                      .durability(9 * 16)
        ), InkColors.BLUE
    )));
    public static final DeferredItem<GemstoneArmorItem> OREAD_BOOTS = register(simple(item(
        "oread_boots", () -> new GemstoneArmorItem(
            PastelArmorMaterials.GEMSTONE, ArmorItem.Type.BOOTS, IS.of(Rarity.UNCOMMON)
                                                                   .durability(9 * 11)
        ), InkColors.BLUE
    )));

    // Decay drops
    public static final DeferredItem<Item> VEGETAL = register(simple(burnable(
        item(
            "vegetal", () -> new ItemWithLoomPattern(
                IS.of(), PastelBannerPatterns.VEGETAL),
            InkColors.LIME
        ), 800
    )));
    public static final DeferredItem<Item> NEOLITH = register(simple(item(
        "neolith", () -> new ItemWithLoomPattern(
            IS.of(Rarity.UNCOMMON),
            PastelBannerPatterns.NEOLITH
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> BEDROCK_DUST = register(simple(item(
        "bedrock_dust", () -> new ItemWithLoomPattern(
            IS.of(Rarity.UNCOMMON),
            PastelBannerPatterns.BEDROCK_DUST
        ), InkColors.BLACK
    )));

    public static final DeferredItem<MidnightAberrationItem> MIDNIGHT_ABERRATION = register(simple(item(
        "midnight_aberration", () -> new MidnightAberrationItem(
            IS.of(Rarity.UNCOMMON)),
        InkColors.GRAY
    )));
    public static final DeferredItem<Item> MIDNIGHT_CHIP = register(simple(item(
        "midnight_chip", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.GRAY
    )));

    public static final DeferredItem<Item> BISMUTH_FLAKE = register(simple(item(
        "bismuth_flake", () -> new Item(IS.of(Rarity.UNCOMMON)),
        InkColors.CYAN
    )));
    public static final DeferredItem<Item> BISMUTH_CRYSTAL = register(simple(item(
        "bismuth_crystal", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.CYAN
    )));
    public static final DeferredItem<Item> RAW_MALACHITE = register(simple(item(
        "raw_malachite", () -> new Item(IS.of(Rarity.UNCOMMON)),
        InkColors.GREEN
    )));
    public static final DeferredItem<Item> PURE_MALACHITE = register(simple(item(
        "pure_malachite", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.GREEN
    )));

    // Fluid Buckets
    public static final DeferredItem<Item> LIQUID_CRYSTAL_BUCKET = register(simple(item(
        "liquid_crystal_bucket", () -> new BucketItem(
            PastelFluids.LIQUID_CRYSTAL.get(), IS.of(1)
                                                 .craftRemainder(BUCKET)
        ), InkColors.LIGHT_GRAY
    )));
    public static final DeferredItem<Item> HUMUS_BUCKET = register(simple(item(
        "humus_bucket", () -> new BucketItem(
            PastelFluids.HUMUS.get(), IS.of(1)
                                        .craftRemainder(BUCKET)
        ), InkColors.BROWN
    )));
    public static final DeferredItem<Item> MIDNIGHT_SOLUTION_BUCKET = register(simple(item(
        "midnight_solution_bucket", () -> new BucketItem(
            PastelFluids.MIDNIGHT_SOLUTION.get(), IS.of(1)
                                                    .craftRemainder(BUCKET)
        ), InkColors.GRAY
    )));
    public static final DeferredItem<Item> DRAGONROT_BUCKET = register(simple(item(
        "dragonrot_bucket", () -> new BucketItem(
            PastelFluids.DRAGONROT.get(), IS.of(1)
                                            .craftRemainder(BUCKET)
        ), InkColors.LIGHT_GRAY
    )));

    // Decay bottles
    public static final DeferredItem<Item> BOTTLE_OF_FADING = register(simple(item(
        "bottle_of_fading", () -> new DecayPlacerItem(
            PastelBlocks.FADING.get(), IS.of(16), List.of(Component.translatable(
            "item.pastel.bottle_of_fading.tooltip"))
        ), InkColors.GRAY
    )));
    public static final DeferredItem<Item> BOTTLE_OF_FAILING = register(simple(item(
        "bottle_of_failing", () -> new DecayPlacerItem(
            PastelBlocks.FAILING.get(), IS.of(16), List.of(Component.translatable(
            "item.pastel.bottle_of_failing.tooltip"))
        ), InkColors.GRAY
    )));
    public static final DeferredItem<Item> BOTTLE_OF_RUIN = register(simple(item(
        "bottle_of_ruin", () -> new DecayPlacerItem(
            PastelBlocks.RUIN.get(), IS.of(16), List.of(Component.translatable("item.pastel.bottle_of_ruin.tooltip"))),
        InkColors.GRAY
    )));
    public static final DeferredItem<Item> BOTTLE_OF_FORFEITURE = register(simple(item(
        "bottle_of_forfeiture", () -> new DecayPlacerItem(
            PastelBlocks.FORFEITURE.get(), IS.of(16), List.of(
            CreativeOnlyItem.DESCRIPTION, Component.translatable("item.pastel.bottle_of_forfeiture.tooltip"))
        ), InkColors.GRAY
    )));
    public static final DeferredItem<Item> BOTTLE_OF_DECAY_AWAY = register(simple(item(
        "bottle_of_decay_away", () -> new DecayPlacerItem(
            PastelBlocks.DECAY_AWAY.get(), IS.of(16), List.of(Component.translatable(
            "item.pastel.bottle_of_decay_away.tooltip"))
        ), InkColors.PINK
    )));

    // Resources
    public static final DeferredItem<Item> SHIMMERSTONE_GEM = register(simple(item(
        "shimmerstone_gem", () -> new ItemWithLoomPattern(
            IS.of(), PastelBannerPatterns.SHIMMERSTONE
        ), InkColors.YELLOW
    )));
    public static final DeferredItem<Item> RAW_AZURITE = register(simple(item(
        "raw_azurite", () -> new ItemWithLoomPattern(
            IS.of(),
            PastelBannerPatterns.RAW_AZURITE
        ), InkColors.BLUE
    )));
    public static final DeferredItem<Item> PURE_AZURITE = register(simple(item(
        "pure_azurite", () -> new Item(
            IS.of()),
        InkColors.BLUE
    )));
    public static final DeferredItem<FloatItem> PALTAERIA_FRAGMENTS = register(simple(item(
        "paltaeria_fragments", () -> new FloatItem(
            IS.of(), 0.00125F
        ), InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<FloatItem> PALTAERIA_GEM = register(simple(item(
        "paltaeria_gem", () -> new FloatItem(
            IS.of(16), 0.01F
        ), InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<FloatItem> STRATINE_FRAGMENTS = register(simple(item(
        "stratine_fragments", () -> new FloatItem(
            IS.of()
              .fireResistant(), -0.00125F
        ), InkColors.RED
    )));
    public static final DeferredItem<FloatItem> STRATINE_GEM = register(simple(item(
        "stratine_gem", () -> new FloatItem(
            IS.of(16)
              .fireResistant(), -0.01F
        ), InkColors.RED
    )));
    public static final DeferredItem<Item> PYRITE_CHUNK = register(
        simple(item("pyrite_chunk", () -> new Item(IS.of()), InkColors.PURPLE)));
    public static final DeferredItem<Item> DRAGONBONE_CHUNK = register(simple(item(
        "dragonbone_chunk", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.GRAY
    )));
    public static final DeferredItem<Item> BONE_ASH = register(simple(item(
        "bone_ash", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.GRAY
    )));
    public static final DeferredItem<Item> RESPLENDENT_FEATHER = register(simple(item(
        "resplendent_feather", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.YELLOW
    )));
    public static final DeferredItem<Item> RAW_BLOODSTONE = register(simple(item(
        "raw_bloodstone", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.RED
    )));
    public static final DeferredItem<Item> PURE_BLOODSTONE = register(simple(item(
        "pure_bloodstone", () -> new Item(
            IS.of(Rarity.UNCOMMON)), InkColors.RED
    )));
    public static final DeferredItem<Item> DOWNSTONE_FRAGMENTS = register(simple(item(
        "downstone_fragments", () -> new Item(
            IS.of(16, Rarity.UNCOMMON)), InkColors.LIGHT_GRAY
    )));
    public static final DeferredItem<Item> RESONANCE_SHARD = register(simple(item(
        "resonance_shard", () -> new Item(
            IS.of(16, Rarity.UNCOMMON)), InkColors.WHITE
    )));
    public static final DeferredItem<Item> AETHER_VESTIGES = register(simple(item(
        "aether_vestiges", () -> new AetherVestigesItem(
            IS.of(1, Rarity.EPIC)
              .fireResistant(), "item.pastel.aether_vestiges.tooltip"
        ), InkColors.WHITE
    )));

    public static final DeferredItem<Item> QUITOXIC_POWDER = register(simple(item(
        "quitoxic_powder", () -> new Item(IS.of()),
        InkColors.PURPLE
    )));
    public static final DeferredItem<Item> STORM_STONE = register(simple(item(
        "storm_stone", () -> new StormStoneItem(IS.of()),
        InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> MERMAIDS_GEM = register(simple(item(
        "mermaids_gem", () -> new ItemNameBlockItem(
            PastelBlocks.MERMAIDS_BRUSH.get(), IS.of()
                                                 .component(
                                                     PastelDataComponentTypes.MERMAIDS_GEM, SimpleFluidContent.copyOf(
                                                         new FluidStack(Fluids.WATER, 1000))
                                                 )
        ), InkColors.YELLOW
    )));
    public static final DeferredItem<Item> STAR_FRAGMENT = register(simple(item(
        "star_fragment", () -> new Item(IS.of(16)),
        InkColors.PURPLE
    )));
    public static final DeferredItem<Item> STARDUST = register(simple(item(
        "stardust", () -> new ItemWithLoomPattern(
            IS.of(), PastelBannerPatterns.SHIMMER),
        InkColors.PURPLE
    )));
    public static final DeferredItem<Item> ASH_FLAKES = register(
        simple(item("ash_flakes", () -> new AshItem(IS.of()), InkColors.LIGHT_GRAY)));

    public static final DeferredItem<Item> HIBERNATING_JADE_VINE_BULB = register(simple(item(
        "hibernating_jade_vine_bulb",
        () -> new ItemWithTooltip(IS.of(16), "item.pastel.hibernating_jade_vine_bulb.tooltip"), InkColors.GRAY
    )));
    public static final DeferredItem<Item> GERMINATED_JADE_VINE_BULB = register(simple(item(
        "germinated_jade_vine_bulb", () -> new GerminatedJadeVineBulbItem(
            IS.of(16)), InkColors.LIME
    )));
    public static final DeferredItem<Item> JADE_VINE_PETALS = register(simple(item(
        "jade_vine_petals", () -> new ItemWithLoomPattern(
            IS.of(), PastelBannerPatterns.JADE_VINE),
        InkColors.LIME
    ))); // TODO: Funky unlock?
    public static final DeferredItem<Item> JADEITE_PETALS = register(
        simple(item("jadeite_petals", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.BROWN)));
    public static final DeferredItem<Item> BLOOD_ORCHID_PETAL = register(simple(item(
        "blood_orchid_petal", () -> new Item(
            IS.of()), InkColors.RED
    )));

    public static final DeferredItem<Item> BLOODBOIL_SYRUP = register(simple(item(
        "bloodboil_syrup", () -> new BloodOrchidDrinkItem(IS.of()
                                                            .food(PastelFoodComponents.BLOODBOIL_SYRUP)
                                                            .craftRemainder(GLASS_BOTTLE)), InkColors.RED
    )));
    public static final DeferredItem<Item> MILKY_RESIN = register(
        simple(item("milky_resin", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.LIGHT_GRAY)));

    // Food & drinks
    public static final DeferredItem<Item> MOONSTRUCK_NECTAR = register(simple(item(
        "moonstruck_nectar", () -> new MoonstruckNectarItem(IS.of(Rarity.UNCOMMON)
                                                              .food(PastelFoodComponents.MOONSTRUCK_NECTAR)
                                                              .craftRemainder(GLASS_BOTTLE)), InkColors.LIME
    )));
    public static final DeferredItem<Item> JADE_JELLY = register(simple(item(
        "jade_jelly", () -> new ItemWithTooltip(
            IS.of()
              .food(PastelFoodComponents.JADE_JELLY), "item.pastel.jade_jelly.tooltip"
        ), InkColors.LIME
    )));
    public static final DeferredItem<Item> GLASS_PEACH = register(simple(item(
        "glass_peach", () -> new ItemWithTooltip(
            IS.of()
              .food(PastelFoodComponents.GLASS_PEACH), "item.pastel.glass_peach.tooltip"
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> FISSURE_PLUM = register(simple(item(
        "fissure_plum", () -> new AliasedTooltipItem(
            PastelBlocks.ABYSSAL_VINES.get(), IS.of()
                                                .food(PastelFoodComponents.FISSURE_PLUM),
            "item.pastel.fissure_plum.tooltip"
        ), InkColors.BROWN
    )));
    public static final DeferredItem<Item> NIGHTDEW_SPROUT = register(simple(item(
        "nightdew_sprout", () -> new AliasedTooltipItem(
            PastelBlocks.NIGHTDEW.get(), IS.of()
                                           .food(PastelFoodComponents.NIGHTDEW_SPROUT),
            "item.pastel.nightdew_sprout.tooltip"
        ), InkColors.PURPLE
    )));
    public static final DeferredItem<Item> NECTARDEW_BURGEON = register(simple(item(
        "nectardew_burgeon", () -> new NectardewBurgeonItem(
            IS.of()
              .food(PastelFoodComponents.NECTARDEW_BURGEON), "item.pastel.nectardew_burgeon.tooltip"
        ), InkColors.PURPLE
    )));
    public static final DeferredItem<Item> RESTORATION_TEA = register(simple(item(
        "restoration_tea", () -> new RestorationTeaItem(IS.of(16)
                                                          .food(PastelFoodComponents.RESTORATION_TEA)
                                                          .craftRemainder(GLASS_BOTTLE)
                                                          .component(
                                                              PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                                                              teaSconeBonus(
                                                                  PastelFoodComponents.RESTORATION_TEA_SCONE_BONUS)
                                                          )), InkColors.PINK
    )));
    public static final DeferredItem<Item> CLOTTED_CREAM = register(simple(item(
        "clotted_cream", () -> new ClottedCreamItem(
            IS.of()
              .food(PastelFoodComponents.CLOTTED_CREAM), new String[]{
            "item.pastel.clotted_cream.tooltip", "item.pastel.clotted_cream.tooltip2"
        }
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> FRESH_CHOCOLATE = register(simple(item(
        "fresh_chocolate", () -> new Item(IS.of()
                                            .food(PastelFoodComponents.FRESH_CHOCOLATE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> HOT_CHOCOLATE = register(simple(item(
        "hot_chocolate", () -> new DrinkItem(IS.of(16)
                                               .food(PastelFoodComponents.HOT_CHOCOLATE)
                                               .component(
                                                   PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                                                   teaSconeBonus(PastelFoodComponents.HOT_CHOCOLATE_SCONE_BONUS)
                                               )), InkColors.PINK
    )));
    public static final DeferredItem<Item> KARAK_CHAI = register(simple(item(
        "karak_chai", () -> new DrinkItem(IS.of(16)
                                            .food(PastelFoodComponents.KARAK_CHAI)
                                            .component(
                                                PastelDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(
                                                    PastelFoodComponents.KARAK_CHAI_SCONE_BONUS)
                                            )), InkColors.PINK
    )));
    public static final DeferredItem<Item> AZALEA_TEA = register(simple(item(
        "azalea_tea", () -> new AzaleaTeaItem(IS.of(16)
                                                .food(PastelFoodComponents.AZALEA_TEA)
                                                .component(
                                                    PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                                                    teaSconeBonus(PastelFoodComponents.AZALEA_TEA_SCONE_BONUS)
                                                )), InkColors.PURPLE
    )));
    public static final DeferredItem<Item> BODACIOUS_BERRY_BAR = register(simple(item(
        "bodacious_berry_bar", () -> new Item(IS.of()
                                                .food(PastelFoodComponents.BODACIOUS_BERRY_BAR)), InkColors.PINK
    )));
    public static final DeferredItem<Item> DEMON_TEA = register(simple(item(
        "demon_tea", () -> new BloodOrchidDrinkItem(IS.of(16)
                                                      .food(PastelFoodComponents.DEMON_TEA)
                                                      .component(
                                                          PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                                                          teaSconeBonus(PastelFoodComponents.DEMON_TEA_SCONE_BONUS)
                                                      )), InkColors.RED
    )));
    public static final DeferredItem<Item> SCONE = register(simple(item(
        "scone", () -> new Item(IS.of()
                                  .food(PastelFoodComponents.SCONE)), InkColors.PINK
    )));

    public static final DeferredItem<Item> CHEONG = register(layered(
        item(
            "cheong", () -> new ItemWithTooltip(
                IS.of()
                  .food(PastelFoodComponents.CHEONG), "item.pastel.cheong.tooltip"
            ), InkColors.PINK
        ), "", "_overlay", "_cap"
    ));
    public static final DeferredItem<Item> MERMAIDS_JAM = register(simple(item(
        "mermaids_jam", () -> new Item(IS.of()
                                         .food(PastelFoodComponents.MERMAIDS_JAM)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MERMAIDS_POPCORN = register(simple(item(
        "mermaids_popcorn", () -> new ItemWithTooltip(
            IS.of()
              .food(PastelFoodComponents.MERMAIDS_POPCORN), "item.pastel.mermaids_popcorn.tooltip"
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> LE_FISHE_AU_CHOCOLAT = register(simple(item(
        "le_fishe_au_chocolat", () -> new Item(IS.of()
                                                 .food(PastelFoodComponents.LE_FISHE_AU_CHOCOLAT)), InkColors.PINK
    )));
//	public static final Item STUFFED_PETALS = register(simple(item("stuffed_petals", () -> new Item(IS.of().food
//	(PastelFoodComponents.STUFFED_PETALS)), InkColors.PINK)));
//	public static final Item PASTICHE = register(simple(item("pastiche", () -> new Item(IS.of().food
//	(PastelFoodComponents.PASTICHE)), InkColors.PINK)));
//	public static final Item VITTORIAS_ROAST = register(simple(item("vittorias_roast", () -> new Item(IS.of().food
//	(PastelFoodComponents.VITTORIAS_ROAST)), InkColors.PINK)));

    //	public static final Item VITTORIAS_ROAST = register(simple(item("vittorias_roast", () -> new Item(IS.of().food
    //	(PastelFoodComponents.VITTORIAS_ROAST)), InkColors.PINK)));

    public static final DeferredItem<Item> INFUSED_BEVERAGE = register(layered(
        item(
            "infused_beverage", () -> new BeverageItem(IS.of(16)
                                                         .food(PastelFoodComponents.BEVERAGE)
                                                         .craftRemainder(GLASS_BOTTLE)
                                                         .component(
                                                             DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                                                         .component(
                                                             PastelDataComponentTypes.INFUSED_BEVERAGE,
                                                             InfusedBeverageComponent.DEFAULT
                                                         )), InkColors.PINK
        ), "", "_highlight"
    ));
    public static final DeferredItem<Item> SUSPICIOUS_BREW = register(simple(item(
        "suspicious_brew", () -> new SuspiciousBrewItem(IS.of(16)
                                                          .food(PastelFoodComponents.BEVERAGE)
                                                          .craftRemainder(GLASS_BOTTLE)), InkColors.LIME
    )));
    public static final DeferredItem<Item> REPRISE = register(simple(item(
        "reprise", () -> new RepriseItem(IS.of(16)
                                           .food(PastelFoodComponents.BEVERAGE)
                                           .craftRemainder(GLASS_BOTTLE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> PURE_ALCOHOL = register(simple(burnable(
        item(
            "pure_alcohol", () -> new DrinkItem(IS.of(16, Rarity.UNCOMMON)
                                                  .food(PastelFoodComponents.PURE_ALCOHOL)
                                                  .craftRemainder(GLASS_BOTTLE)), InkColors.WHITE
        ), 16000
    )));
    public static final DeferredItem<Item> JADE_WINE = register(simple(item(
        "jade_wine", () -> new JadeWineItem(IS.of(16, Rarity.UNCOMMON)
                                              .food(PastelFoodComponents.BEVERAGE)
                                              .craftRemainder(GLASS_BOTTLE)), InkColors.LIME
    )));
    public static final DeferredItem<Item> CHRYSOCOLLA = register(simple(burnable(
        item(
            "chrysocolla", () -> new DrinkItem(IS.of(16, Rarity.UNCOMMON)
                                                 .food(PastelFoodComponents.PURE_ALCOHOL)
                                                 .craftRemainder(GLASS_BOTTLE)), InkColors.LIME
        ), 16000
    )));

    public static final DeferredItem<Item> HONEY_PASTRY = register(simple(item(
        "honey_pastry", () -> new Item(IS.of()
                                         .food(PastelFoodComponents.HONEY_PASTRY)), InkColors.PINK
    )));
    public static final DeferredItem<Item> LUCKY_ROLL = register(simple(item(
        "lucky_roll", () -> new Item(IS.of(16)
                                       .food(PastelFoodComponents.LUCKY_ROLL)), InkColors.PINK
    )));
    public static final DeferredItem<Item> TRIPLE_MEAT_POT_PIE = register(simple(item(
        "triple_meat_pot_pie", () -> new Item(IS.of(8)
                                                .food(PastelFoodComponents.TRIPLE_MEAT_POT_PIE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> GLISTERING_JELLY_TEA = register(simple(item(
        "glistering_jelly_tea", () -> new DrinkItem(IS.of(16)
                                                      .food(PastelFoodComponents.GLISTERING_JELLY_TEA)
                                                      .craftRemainder(GLASS_BOTTLE)
                                                      .component(
                                                          PastelDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(
                                                              PastelFoodComponents.GLISTERING_JELLY_TEA_SCONE_BONUS)
                                                      )), InkColors.PINK
    )));
    public static final DeferredItem<Item> FREIGEIST = register(simple(item(
        "freigeist", () -> new FreigeistItem(IS.of(16)
                                               .food(PastelFoodComponents.FREIGEIST)
                                               .craftRemainder(GLASS_BOTTLE)), InkColors.RED
    )));
    public static final DeferredItem<Item> DIVINATION_HEART = register(simple(item(
        "divination_heart", () -> new Item(IS.of()
                                             .food(PastelFoodComponents.DIVINATION_HEART)), InkColors.RED
    )));

    public static final DeferredItem<Item> STAR_CANDY = register(simple(item(
        "star_candy", () -> new StarCandyItem(IS.of(Rarity.UNCOMMON)
                                                .food(PastelFoodComponents.STAR_CANDY)), InkColors.PINK
    )));
    public static final DeferredItem<Item> ENCHANTED_STAR_CANDY = register(simple(item(
        "enchanted_star_candy", () -> new EnchantedStarCandyItem(IS.of(Rarity.UNCOMMON)
                                                                   .food(PastelFoodComponents.ENCHANTED_STAR_CANDY)),
        InkColors.PINK
    )));

    public static final DeferredItem<Item> ENCHANTED_GOLDEN_CARROT = register(parented(
        item(
            "enchanted_golden_carrot", () -> new ItemWithGlint(IS.of(Rarity.EPIC)
                                                                 .food(PastelFoodComponents.ENCHANTED_GOLDEN_CARROT)),
            InkColors.PINK
        ), GOLDEN_CARROT
    ));
    public static final DeferredItem<Item> JARAMEL = register(simple(item(
        "jaramel", () -> new Item(IS.of()
                                    .food(PastelFoodComponents.JARAMEL)), InkColors.PINK
    )));

    public static final DeferredItem<Item> JARAMEL_TART = register(simple(item(
        "jaramel_tart", () -> new Item(IS.of()
                                         .food(PastelFoodComponents.JARAMEL_TART)), InkColors.PINK
    )));
    public static final DeferredItem<Item> SALTED_JARAMEL_TART = register(simple(item(
        "salted_jaramel_tart", () -> new Item(IS.of()
                                                .food(PastelFoodComponents.SALTED_JARAMEL_TART)), InkColors.PINK
    )));
    public static final DeferredItem<Item> ASHEN_TART = register(simple(item(
        "ashen_tart", () -> new Item(IS.of()
                                       .food(PastelFoodComponents.ASHEN_TART)), InkColors.PINK
    )));
    public static final DeferredItem<Item> WEEPING_TART = register(simple(item(
        "weeping_tart", () -> new Item(IS.of()
                                         .food(PastelFoodComponents.WEEPING_TART)), InkColors.PINK
    )));
    public static final DeferredItem<Item> WHISPY_TART = register(simple(item(
        "whispy_tart", () -> new Item(IS.of()
                                        .food(PastelFoodComponents.WHISPY_TART)), InkColors.PINK
    )));
    public static final DeferredItem<Item> PUFF_TART = register(simple(item(
        "puff_tart", () -> new Item(IS.of()
                                      .food(PastelFoodComponents.PUFF_TART)), InkColors.PINK
    )));

    public static final DeferredItem<Item> JARAMEL_TRIFLE = register(simple(item(
        "jaramel_trifle", () -> new Item(IS.of()
                                           .food(PastelFoodComponents.JARAMEL_TRIFLE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> SALTED_JARAMEL_TRIFLE = register(simple(item(
        "salted_jaramel_trifle", () -> new Item(IS.of()
                                                  .food(PastelFoodComponents.SALTED_JARAMEL_TRIFLE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MONSTER_TRIFLE = register(simple(item(
        "monster_trifle", () -> new Item(IS.of()
                                           .food(PastelFoodComponents.MONSTER_TRIFLE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> DEMON_TRIFLE = register(simple(item(
        "demon_trifle", () -> new Item(IS.of()
                                         .food(PastelFoodComponents.DEMON_TRIFLE)), InkColors.PINK
    )));

    public static final DeferredItem<Item> MYCEYLON = register(simple(item(
        "myceylon", () -> new Item(IS.of()), InkColors.PINK)));
    public static final DeferredItem<Item> MYCEYLON_APPLE_PIE = register(simple(item(
        "myceylon_apple_pie", () -> new Item(IS.of()
                                               .food(PastelFoodComponents.MYCEYLON_APPLE_PIE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MYCEYLON_PUMPKIN_PIE = register(simple(item(
        "myceylon_pumpkin_pie", () -> new Item(IS.of()
                                                 .food(PastelFoodComponents.MYCEYLON_PUMPKIN_PIE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MYCEYLON_COOKIE = register(simple(item(
        "myceylon_cookie", () -> new Item(IS.of()
                                            .food(PastelFoodComponents.MYCEYLON_COOKIE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> ALOE_LEAF = register(simple(item(
        "aloe_leaf", () -> new ItemNameBlockItem(
            PastelBlocks.ALOE.get(), IS.of()
                                       .food(PastelFoodComponents.ALOE_LEAF)
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> SAWBLADE_HOLLY_BERRY = register(simple(item(
        "sawblade_holly_berry", () -> new ItemNameBlockItem(
            PastelBlocks.SAWBLADE_HOLLY_BUSH.get(), IS.of()
                                                      .food(Foods.SWEET_BERRIES)
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> PRICKLY_BAYLEAF = register(simple(item(
        "prickly_bayleaf", () -> new Item(IS.of()
                                            .food(PastelFoodComponents.PRICKLY_BAYLEAF)), InkColors.PINK
    )));
    public static final DeferredItem<Item> TRIPLE_MEAT_POT_STEW = register(simple(item(
        "triple_meat_pot_stew", () -> new StackableStewItem(IS.of(8)
                                                              .food(PastelFoodComponents.TRIPLE_MEAT_POT_STEW)),
        InkColors.PINK
    )));
    public static final DeferredItem<Item> DRAGONBONE_BROTH = register(simple(item(
        "dragonbone_broth", () -> new StackableStewItem(IS.of(8)
                                                          .food(PastelFoodComponents.DRAGONBONE_BROTH)), InkColors.GRAY
    )));
    public static final DeferredItem<Item> DOOMBLOOM_SEED = register(simple(item(
        "doombloom_seed", () -> new ItemNameBlockItem(
            PastelBlocks.DOOMBLOOM.get(), IS.of()
                                            .fireResistant()
        ), InkColors.BLACK
    )));

    public static final ResourceKey<Item> GLISTERING_MELON_SEEDS = simple(
        new ItemRegistrar<>("glistering_melon_seeds").withItem(
            () -> new ItemNameBlockItem(BuiltInRegistries.BLOCK.get(PastelBlocks.GLISTERING_MELON_STEM), IS.of()),
            InkColors.LIME
        )).itemKey();
    public static final DeferredItem<Item> AMARANTH_GRAINS = register(simple(
        item("amaranth_grains", () -> new ItemNameBlockItem(PastelBlocks.AMARANTH.get(), IS.of()), InkColors.LIME)));

    // Cookbooks
    public static final DeferredItem<Item> MELOCHITES_COOKBOOK_VOL_1 = register(simple(item(
        "melochites_cookbook_vol_1", () -> new CookbookItem(
            IS.of()
              .stacksTo(1)
              .rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(
            GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/melochites_cookbook_vol_1"))
        ), InkColors.PURPLE
    )));
    public static final DeferredItem<Item> MELOCHITES_COOKBOOK_VOL_2 = register(simple(item(
        "melochites_cookbook_vol_2", () -> new CookbookItem(
            IS.of()
              .stacksTo(1)
              .rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(
            GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/melochites_cookbook_vol_2"))
        ), InkColors.PURPLE
    )));
    public static final DeferredItem<Item> IMBRIFER_COOKBOOK = register(simple(item(
        "imbrifer_cookbook", () -> new CookbookItem(
            IS.of()
              .stacksTo(1)
              .rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(
            GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/imbrifer_cookbook"))
        ), InkColors.PURPLE
    )));
    public static final DeferredItem<Item> IMPERIAL_COOKBOOK = register(simple(item(
        "imperial_cookbook", () -> new CookbookItem(
            IS.of()
              .stacksTo(1)
              .rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(
            GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/imperial_cookbook"))
        ), InkColors.PURPLE
    )));
    public static final DeferredItem<Item> BREWERS_HANDBOOK = register(simple(item(
        "brewers_handbook", () -> new CookbookItem(
            IS.of()
              .stacksTo(1)
              .rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(
            GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/brewers_handbook"))
        ), InkColors.PURPLE
    )));
    //public static final Item VARIA_COOKBOOK = register(simple(item("varia_cookbook", () -> new CookbookItem(IS.of()
    // .maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, PastelCommon
    // .locate("cuisine/cookbooks/varia_cookbook"))), InkColors.PURPLE)));
    public static final DeferredItem<Item> POISONERS_HANDBOOK = register(simple(item(
        "poisoners_handbook", () -> new CookbookItem(
            IS.of()
              .stacksTo(1)
              .rarity(Rarity.EPIC), GuidebookItem.addressOf(
            GuidebookItem.CUISINE_CATEGORY_ID, locate("dimension/lore/poisoners_handbook")),
            PastelMobEffects.ETERNAL_SLUMBER_COLOR
        ), InkColors.PURPLE
    )));

    public static final DeferredItem<Item> AQUA_REGIA = register(simple(item(
        "aqua_regia", () -> new JadeWineItem(IS.of(16)
                                               .food(PastelFoodComponents.AQUA_REGIA)), InkColors.PINK
    )));
    public static final DeferredItem<Item> BAGNUN = register(simple(item(
        "bagnun", () -> new Item(IS.of()
                                   .food(PastelFoodComponents.BAGNUN)), InkColors.PINK
    )));
    public static final DeferredItem<Item> BANYASH = register(simple(item(
        "banyash", () -> new Item(IS.of()
                                    .food(PastelFoodComponents.BANYASH)), InkColors.PINK
    )));
    public static final DeferredItem<Item> BERLINER = register(simple(item(
        "berliner", () -> new Item(IS.of()
                                     .food(PastelFoodComponents.BERLINER)), InkColors.PINK
    )));
    public static final DeferredItem<Item> BRISTLE_MEAD = register(simple(item(
        "bristle_mead", () -> new BeverageItem(IS.of(16)
                                                 .food(PastelFoodComponents.BEVERAGE)
                                                 .component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)),
        InkColors.PINK
    )));
    public static final DeferredItem<Item> CHAUVE_SOURIS_AU_VIN = register(simple(item(
        "chauve_souris_au_vin", () -> new Item(IS.of()
                                                 .food(PastelFoodComponents.CHAUVE_SOURIS_AU_VIN)), InkColors.PINK
    )));
    public static final DeferredItem<Item> CRAWFISH = register(simple(item(
        "crawfish", () -> new Item(IS.of()
                                     .food(PastelFoodComponents.CRAWFISH)), InkColors.PINK
    )));
    public static final DeferredItem<Item> CRAWFISH_COCKTAIL = register(simple(item(
        "crawfish_cocktail", () -> new Item(IS.of()
                                              .food(PastelFoodComponents.CRAWFISH_COCKTAIL)), InkColors.PINK
    )));
    public static final DeferredItem<Item> CREAM_PASTRY = register(simple(item(
        "cream_pastry", () -> new Item(IS.of()
                                         .food(PastelFoodComponents.CREAM_PASTRY)), InkColors.PINK
    )));
    public static final DeferredItem<Item> FADED_KOI = register(simple(item(
        "faded_koi", () -> new Item(IS.of()
                                      .food(PastelFoodComponents.FADED_KOI)), InkColors.PINK
    )));
    public static final DeferredItem<Item> FISHCAKE = register(simple(item(
        "fishcake", () -> new Item(IS.of()
                                     .food(PastelFoodComponents.FISHCAKE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> LIZARD_MEAT = register(simple(item(
        "lizard_meat", () -> new Item(IS.of()
                                        .food(PastelFoodComponents.LIZARD_MEAT)), InkColors.PINK
    )));
    public static final DeferredItem<Item> COOKED_LIZARD_MEAT = register(simple(item(
        "cooked_lizard_meat", () -> new Item(IS.of()
                                               .food(PastelFoodComponents.COOKED_LIZARD_MEAT)), InkColors.PINK
    )));
    public static final DeferredItem<Item> GOLDEN_BRISTLE_TEA = register(simple(item(
        "golden_bristle_tea", () -> new DrinkItem(IS.of(16)
                                                    .food(PastelFoodComponents.GOLDEN_BRISTLE_TEA)
                                                    .component(
                                                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(
                                                            PastelFoodComponents.GOLDEN_BRISTLE_TEA_SCONE_BONUS)
                                                    )), InkColors.PINK
    )));
    public static final DeferredItem<Item> HARE_ROAST = register(simple(item(
        "hare_roast", () -> new Item(IS.of()
                                       .food(PastelFoodComponents.HARE_ROAST)), InkColors.PINK
    )));
    public static final DeferredItem<Item> JUNKET = register(simple(item(
        "junket", () -> new Item(IS.of()
                                   .food(PastelFoodComponents.JUNKET)), InkColors.PINK
    )));
    public static final DeferredItem<Item> KOI = register(simple(item(
        "koi", () -> new Item(IS.of()
                                .food(PastelFoodComponents.KOI)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MEATLOAF = register(simple(item(
        "meatloaf", () -> new Item(IS.of()
                                     .food(PastelFoodComponents.MEATLOAF)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MEATLOAF_SANDWICH = register(simple(item(
        "meatloaf_sandwich", () -> new Item(IS.of()
                                              .food(PastelFoodComponents.MEATLOAF_SANDWICH)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MELLOW_SHALLOT_SOUP = register(simple(item(
        "mellow_shallot_soup", () -> new Item(IS.of()
                                                .food(PastelFoodComponents.MELLOW_SHALLOT_SOUP)), InkColors.PINK
    )));
    public static final DeferredItem<Item> MORCHELLA = register(simple(item(
        "morchella", () -> new BeverageItem(IS.of(16)
                                              .food(PastelFoodComponents.BEVERAGE)
                                              .component(
                                                  DataComponents.POTION_CONTENTS, PotionContents.EMPTY)), InkColors.PINK
    )));
    public static final DeferredItem<Item> NECTERED_VIOGNIER = register(simple(item(
        "nectered_viognier", () -> new JadeWineItem(IS.of(16)
                                                      .food(PastelFoodComponents.NECTERED_VIOGNIER)), InkColors.PINK
    )));
    public static final DeferredItem<Item> PEACHES_FLAMBE = register(simple(item(
        "peaches_flambe", () -> new Item(IS.of()
                                           .food(PastelFoodComponents.PEACHES_FLAMBE)), InkColors.PINK
    )));
    public static final DeferredItem<Item> PEACH_CREAM = register(simple(item(
        "peach_cream", () -> new DrinkItem(IS.of(16)
                                             .food(PastelFoodComponents.PEACH_CREAM)
                                             .component(
                                                 PastelDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(
                                                     PastelFoodComponents.PEACH_CREAM_SCONE_BONUS)
                                             )), InkColors.PINK
    )));
    public static final DeferredItem<Item> PEACH_JAM = register(simple(item(
        "peach_jam", () -> new Item(IS.of()
                                      .food(PastelFoodComponents.PEACH_JAM)), InkColors.PINK
    )));
    public static final DeferredItem<Item> RABBIT_CREAM_PIE = register(simple(item(
        "rabbit_cream_pie", () -> new ItemWithTooltip(
            IS.of()
              .food(PastelFoodComponents.RABBIT_CREAM_PIE), "item.pastel.rabbit_cream_pie.tooltip"
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> SEDATIVES = register(simple(item(
        "sedatives", () -> new SedativesItem(
            IS.of()
              .food(PastelFoodComponents.SEDATIVES), "item.pastel.sedatives.tooltip"
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> SLUSHSLIDE = register(simple(item(
        "slushslide", () -> new DrinkItem(
            IS.of(16)
              .food(PastelFoodComponents.SLUSHSLIDE), "item.pastel.slushslide.tooltip"
        ), InkColors.PINK
    )));
    public static final DeferredItem<Item> SURSTROMMING = register(simple(item(
        "surstromming", () -> new Item(IS.of()
                                         .food(PastelFoodComponents.SURSTROMMING)), InkColors.PINK
    )));
    public static final DeferredItem<Item> EVERNECTAR = register(simple(item(
        "evernectar", () -> new EvernectarItem(
            IS.of(1, Rarity.EPIC)
              .food(PastelFoodComponents.EVERNECTAR)
              .craftRemainder(GLASS_BOTTLE), "item.pastel.evernectar.tooltip"
        ), InkColors.LIME
    )));

    // Banner Patterns
    public static final DeferredItem<Item> LOGO_BANNER_PATTERN = register(banner(item(
        "logo_banner_pattern", () -> new BannerPatternItem(
            PastelBannerPatternTags.SPECTRUM_LOGO_TAG, IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> AMETHYST_SHARD_BANNER_PATTERN = register(banner(item(
        "amethyst_shard_banner_pattern",
        () -> new BannerPatternItem(PastelBannerPatternTags.AMETHYST_SHARD_TAG, IS.of(1)), InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> AMETHYST_CLUSTER_BANNER_PATTERN = register(banner(item(
        "amethyst_cluster_banner_pattern",
        () -> new BannerPatternItem(PastelBannerPatternTags.AMETHYST_CLUSTER_TAG, IS.of(1)), InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> ASTROLOGER_BANNER_PATTERN = register(banner(item(
        "astrologer_banner_pattern", () -> new BannerPatternItem(
            PastelBannerPatternTags.ASTROLOGER_TAG, IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> VELVET_ASTROLOGER_BANNER_PATTERN = register(banner(item(
        "velvet_astrologer_banner_pattern",
        () -> new BannerPatternItem(PastelBannerPatternTags.VELVET_ASTROLOGER_TAG, IS.of(1, Rarity.UNCOMMON)),
        InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> POISONBLOOM_BANNER_PATTERN = register(banner(item(
        "poisonbloom_banner_pattern",
        () -> new BannerPatternItem(PastelBannerPatternTags.POISONBLOOM_TAG, IS.of(1, Rarity.RARE)),
        InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> DEEP_LIGHT_BANNER_PATTERN = register(banner(item(
        "deep_light_banner_pattern", () -> new BannerPatternItem(
            PastelBannerPatternTags.DEEP_LIGHT_TAG, IS.of(1, Rarity.RARE)), InkColors.LIGHT_BLUE
    )));

    // Spawning items
    public static final DeferredItem<Item> BUCKET_OF_ERASER = register(simple(item(
        "bucket_of_eraser", () -> new EmptyFluidEntityBucketItem(
            PastelEntityTypes.ERASER.get(), Fluids.EMPTY, SoundEvents.BUCKET_EMPTY, IS.of()), InkColors.PINK
    )));
    public static final DeferredItem<Item> EGG_LAYING_WOOLY_PIG_SPAWN_EGG = register(parented(
        item(
            "egg_laying_wooly_pig_spawn_egg",
            () -> new SpawnEggItem(PastelEntityTypes.EGG_LAYING_WOOLY_PIG.get(), 0x3a2c38, 0xfff2e0, IS.of()),
            InkColors.WHITE
        ), PastelModels.SPAWN_EGG
    ));
    public static final DeferredItem<Item> PRESERVATION_TURRET_SPAWN_EGG = register(parented(
        item(
            "preservation_turret_spawn_egg",
            () -> new SpawnEggItem(PastelEntityTypes.PRESERVATION_TURRET.get(), 0xf3f6f8, 0xc8c5be, IS.of()),
            InkColors.WHITE
        ), PastelModels.SPAWN_EGG
    ));
    public static final DeferredItem<Item> KINDLING_SPAWN_EGG = register(parented(
        item(
            "kindling_spawn_egg", () -> new SpawnEggItem(PastelEntityTypes.KINDLING.get(), 0xda4261, 0xffd452, IS.of()),
            InkColors.WHITE
        ), PastelModels.SPAWN_EGG
    ));
    public static final DeferredItem<Item> LIZARD_SPAWN_EGG = register(parented(
        item(
            "lizard_spawn_egg", () -> new SpawnEggItem(PastelEntityTypes.LIZARD.get(), 0x896459, 0x503a40, IS.of()),
            InkColors.WHITE
        ), PastelModels.SPAWN_EGG
    ));
    public static final DeferredItem<Item> ERASER_SPAWN_EGG = register(parented(
        item(
            "eraser_spawn_egg", () -> new SpawnEggItem(PastelEntityTypes.ERASER.get(), 0x200d29, 0xc83e93, IS.of()),
            InkColors.WHITE
        ), PastelModels.SPAWN_EGG
    ));

    // Magical Tools
    public static final DeferredItem<Item> BAG_OF_HOLDING = register(
        simple(item("bag_of_holding", () -> new BagOfHoldingItem(IS.of(1)), InkColors.PURPLE)));
    public static final DeferredItem<Item> RADIANCE_STAFF = register(
        item("radiance_staff", () -> new RadianceStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW));
    public static final DeferredItem<NaturesStaffItem> NATURES_STAFF = register(
        item("natures_staff", () -> new NaturesStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIME));
    public static final DeferredItem<Item> STAFF_OF_REMEMBRANCE = register(
        item("staff_of_remembrance", () -> new StaffOfRemembranceItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIME));
    public static final DeferredItem<Item> CONSTRUCTORS_STAFF = register(handheld(
        item("constructors_staff", () -> new ConstructorsStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_GRAY)));
    public static final DeferredItem<Item> EXCHANGING_STAFF = register(handheld(
        item("exchanging_staff", () -> new ExchangeStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_GRAY)));
    public static final DeferredItem<Item> BLOCK_FLOODER = register(
        simple(item("block_flooder", () -> new BlockFlooderItem(IS.of(Rarity.UNCOMMON)), InkColors.LIGHT_GRAY)));
    public static final DeferredItem<Item> PIPE_BOMB = register(
        item("pipe_bomb", () -> new PipeBombItem(IS.of(1)), InkColors.ORANGE));
    public static final DeferredItem<EnderSpliceItem> ENDER_SPLICE = register(
        item("ender_splice", () -> new EnderSpliceItem(IS.of(16, Rarity.UNCOMMON)), InkColors.PURPLE));
    public static final DeferredItem<Item> PERTURBED_EYE = register(
        simple(item("perturbed_eye", () -> new PerturbedEyeItem(IS.of(Rarity.UNCOMMON)), InkColors.RED)));
    public static final DeferredItem<Item> CRESCENT_CLOCK = register(item(
        "crescent_clock", () -> new ItemWithTooltip(IS.of(1), "item.pastel.crescent_clock.tooltip"),
        InkColors.MAGENTA
    ));
    public static final DeferredItem<Item> PRIMORDIAL_LIGHTER = register(
        simple(item("primordial_lighter", () -> new PrimordialLighterItem(IS.of(1)), InkColors.ORANGE)));

    public static final DeferredItem<Item> NIGHT_SALTS = register(
        simple(item("night_salts", () -> new NightSaltsItem(IS.of(16)), InkColors.PURPLE)));
    public static final DeferredItem<Item> SOOTHING_BOUQUET = register(
        simple(item("soothing_bouquet", () -> new SoothingBouquetItem(IS.of(1, Rarity.RARE)), InkColors.PURPLE)));
    public static final DeferredItem<Item> CONCEALING_OILS = register(layered(
        item("concealing_oils", () -> new ConcealingOilsItem(IS.of(1)), InkColors.BLACK), "", "_tint", "_overlay"));
    public static final DeferredItem<Item> BITTER_OILS = register(simple(item(
        "bitter_oils", () -> new DrinkItem(IS.of(16)
                                             .food(PastelFoodComponents.BITTER_OILS)), InkColors.BLUE
    )));

    public static final DeferredItem<Item> INCANDESCENT_ESSENCE = register(simple(burnable(
        item(
            "incandescent_essence", () -> new Item(
                IS.of()
                  .fireResistant()
            ), InkColors.ORANGE
        ), 2400
    )));
    public static final DeferredItem<Item> FROSTBITE_ESSENCE = register(simple(item(
        "frostbite_essence", () -> new Item(IS.of()),
        InkColors.LIGHT_BLUE
    )));
    public static final DeferredItem<Item> MOONSTONE_CORE = register(simple(item(
        "moonstone_core", () -> new Item(
            IS.of(16, Rarity.RARE)), InkColors.WHITE
    )));

    // Music discs
    public static final DeferredItem<Item> MUSIC_DISC_DISCOVERY = register(simple(item(
        "music_disc_discovery", () -> new Item(IS.of(1, Rarity.RARE)
                                                 .jukeboxPlayable(PastelJukeboxSongs.DISCOVERY)), InkColors.GREEN
    )));
    public static final DeferredItem<Item> MUSIC_DISC_CREDITS = register(simple(item(
        "music_disc_credits", () -> new Item(IS.of(1, Rarity.RARE)
                                               .jukeboxPlayable(PastelJukeboxSongs.CREDITS)), InkColors.GREEN
    )));
    public static final DeferredItem<Item> MUSIC_DISC_DIVINITY = register(simple(item(
        "music_disc_divinity", () -> new Item(IS.of(1, Rarity.RARE)
                                                .jukeboxPlayable(PastelJukeboxSongs.DIVINITY)), InkColors.GREEN
    )));

    // Item Frames
    public static final DeferredItem<Item> PHANTOM_FRAME = register(simple(item(
        "phantom_frame", () -> new PhantomFrameItem(PastelEntityTypes.PHANTOM_FRAME.get(), IS.of()),
        InkColors.YELLOW
    )));
    public static final DeferredItem<Item> GLOW_PHANTOM_FRAME = register(simple(item(
        "glow_phantom_frame", () -> new PhantomGlowFrameItem(PastelEntityTypes.GLOW_PHANTOM_FRAME.get(), IS.of()),
        InkColors.YELLOW
    )));

    // Specialty Magical Tools
    public static final DeferredItem<KnowledgeGemItem> KNOWLEDGE_GEM = register(
        item("knowledge_gem", () -> new KnowledgeGemItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE));
    public static final DeferredItem<Item> CELESTIAL_POCKETWATCH = register(simple(item(
        "celestial_pocketwatch", () -> new CelestialPocketWatchItem(IS.of(1, Rarity.UNCOMMON)), InkColors.MAGENTA)));
    public static final DeferredItem<Item> ARTISANS_ATLAS = register(
        simple(item("artisans_atlas", () -> new ArtisansAtlasItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)));
    public static final DeferredItem<Item> GILDED_BOOK = register(
        simple(item("gilded_book", () -> new GildedBookItem(IS.of(Rarity.UNCOMMON)), InkColors.PURPLE)));
    public static final DeferredItem<Item> ENCHANTMENT_CANVAS = register(
        item("enchantment_canvas", () -> new EnchantmentCanvasItem(IS.of(16, Rarity.UNCOMMON)), InkColors.PURPLE));
    public static final DeferredItem<Item> EVERPROMISE_RIBBON = register(
        simple(item("everpromise_ribbon", () -> new EverpromiseRibbonItem(IS.of()), InkColors.PINK)));

    // Lore
    public static final DeferredItem<Item> MYSTERIOUS_LOCKET = register(
        item("mysterious_locket", () -> new MysteriousLocketItem(IS.of(1, Rarity.UNCOMMON)), InkColors.GRAY));
    public static final DeferredItem<Item> MYSTERIOUS_COMPASS = register(
        item("mysterious_compass", () -> new MysteriousCompassItem(IS.of(1, Rarity.RARE)), InkColors.GRAY));

    // Trinkets
    public static final DeferredItem<Item> FANCIFUL_TUFF_RING = register(
        simple(item("fanciful_tuff_ring", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)));
    public static final DeferredItem<Item> FANCIFUL_BELT = register(
        simple(item("fanciful_belt", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)));
    public static final DeferredItem<Item> FANCIFUL_PENDANT = register(
        simple(item("fanciful_pendant", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)));
    public static final DeferredItem<Item> FANCIFUL_CIRCLET = register(
        simple(item("fanciful_circlet", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)));
    public static final DeferredItem<Item> FANCIFUL_GLOVES = register(
        simple(item("fanciful_gloves", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)));
    public static final DeferredItem<Item> FANCIFUL_BISMUTH_RING = register(
        simple(item("fanciful_bismuth_ring", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)));

    public static final DeferredItem<Item> GLOW_VISION_GOGGLES = register(simple(
        item("glow_vision_goggles", () -> new GlowVisionGogglesItem(IS.of(1, Rarity.UNCOMMON)), InkColors.WHITE)));
    public static final DeferredItem<Item> JEOPARDANT = register(
        simple(item("jeopardant", () -> new AttackRingItem(IS.of(1, Rarity.UNCOMMON)), InkColors.RED)));
    public static final DeferredItem<SevenLeagueBootsItem> SEVEN_LEAGUE_BOOTS = register(simple(
        item("seven_league_boots", () -> new SevenLeagueBootsItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)));
    public static final DeferredItem<Item> COTTON_CLOUD_BOOTS = register(simple(
        item("cotton_cloud_boots", () -> new CottonCloudBootsItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)));
    public static final DeferredItem<Item> RADIANCE_PIN = register(
        simple(item("radiance_pin", () -> new RadiancePinItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BLUE)));
    public static final DeferredItem<Item> TOTEM_PENDANT = register(
        simple(item("totem_pendant", () -> new TotemPendantItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BLUE)));
    public static final DeferredItem<TakeOffBeltItem> TAKE_OFF_BELT = register(
        simple(item("take_off_belt", () -> new TakeOffBeltItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)));
    public static final DeferredItem<Item> AZURE_DIKE_BELT = register(simple(item(
        "azure_dike_belt", () -> new AzureDikeBeltItem(
            IS.of(1, Rarity.UNCOMMON), PastelAdvancements.UNLOCK_AZURE_DIKE_BELT), InkColors.BLUE
    )));
    public static final DeferredItem<Item> AZURE_DIKE_RING = register(simple(item(
        "azure_dike_ring", () -> new AzureDikeRingItem(
            IS.of(1, Rarity.UNCOMMON), PastelAdvancements.UNLOCK_AZURE_DIKE_RING), InkColors.BLUE
    )));
    public static final DeferredItem<Item> AZURESQUE_DIKE_CORE = register(simple(item(
        "azuresque_dike_core", () -> new AzureDikeCoreItem(
            IS.of(1, Rarity.EPIC), PastelAdvancements.UNLOCK_AZURESQUE_DIKE_CORE), InkColors.WHITE
    )));
    public static final DeferredItem<InkDrainTrinketItem> SHIELDGRASP_AMULET = register(simple(item(
        "shieldgrasp_amulet",
        () -> new AzureDikeAmuletItem(IS.of(1, Rarity.UNCOMMON), PastelAdvancements.UNLOCK_SHIELDGRASP_AMULET),
        InkColors.BLUE
    )));
    public static final DeferredItem<InkDrainTrinketItem> HEARTSINGERS_REWARD = register(
        simple(item("heartsingers_reward", () -> new ExtraHealthRingItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PINK)));
    public static final DeferredItem<InkDrainTrinketItem> GLOVES_OF_DAWNS_GRASP = register(simple(
        item("gloves_of_dawns_grasp", () -> new ExtraReachGlovesItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)));
    public static final DeferredItem<InkDrainTrinketItem> RING_OF_PURSUIT = register(simple(
        item("ring_of_pursuit", () -> new ExtraMiningSpeedRingItem(IS.of(1, Rarity.UNCOMMON)), InkColors.MAGENTA)));
    public static final DeferredItem<InkDrainTrinketItem> RING_OF_DENSER_STEPS = register(simple(
        item("ring_of_denser_steps", () -> new RingOfDenserStepsItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BROWN)));
    public static final DeferredItem<InkDrainTrinketItem> RING_OF_AERIAL_GRACE = register(simple(
        item("ring_of_aerial_grace", () -> new RingOfAerialGraceItem(IS.of(1, Rarity.UNCOMMON)), InkColors.WHITE)));
    public static final DeferredItem<InkDrainTrinketItem> LAURELS_OF_SERENITY = register(simple(
        item("laurels_of_serenity", () -> new LaurelsOfSerenityItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)));

    // Ink storage
    public static final DeferredItem<InkFlaskItem> INK_FLASK = register(
        item("ink_flask", () -> new InkFlaskItem(IS.of(1), 64 * 64 * 100), InkColors.WHITE));
        // 64 stacks of pigments (1 pigment => 100 energy)
    public static final DeferredItem<InkAssortmentItem> INK_ASSORTMENT = register(
        simple(item("ink_assortment", () -> new InkAssortmentItem(IS.of(1), 64 * 100), InkColors.WHITE)));
    public static final DeferredItem<PigmentPaletteItem> PIGMENT_PALETTE = register(simple(item(
        "pigment_palette", () -> new PigmentPaletteItem(IS.of(1, Rarity.UNCOMMON), 64 * 64 * 100), InkColors.WHITE)));
    public static final DeferredItem<ArtistsPaletteItem> ARTISTS_PALETTE = register(simple(item(
        "artists_palette", () -> new ArtistsPaletteItem(IS.of(1, Rarity.UNCOMMON), 64 * 64 * 64 * 64 * 100),
        InkColors.WHITE
    )));
    public static final DeferredItem<CreativeInkAssortmentItem> CREATIVE_INK_ASSORTMENT = register(parented(
        item("creative_ink_assortment", () -> new CreativeInkAssortmentItem(IS.of(1, Rarity.EPIC)), InkColors.WHITE),
        PastelItems.INK_ASSORTMENT
    ));

    public static final DeferredItem<GleamingPinItem> GLEAMING_PIN = register(
        simple(item("gleaming_pin", () -> new GleamingPinItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)));
    public static final DeferredItem<Item> LESSER_POTION_PENDANT = register(layered(
        item(
            "lesser_potion_pendant", () -> new PotionPendantItem(
                IS.of(1, Rarity.UNCOMMON), 1, CONFIG.MaxLevelForEffectsInLesserPotionPendant - 1,
                PastelAdvancements.UNLOCK_LESSER_POTION_PENDANT
            ), InkColors.PINK
        ), "_base", "_overlay"
    ));
    public static final DeferredItem<Item> GREATER_POTION_PENDANT = register(layered(
        item(
            "greater_potion_pendant", () -> new PotionPendantItem(
                IS.of(1, Rarity.UNCOMMON), 3, CONFIG.MaxLevelForEffectsInGreaterPotionPendant - 1,
                PastelAdvancements.UNLOCK_GREATER_POTION_PENDANT
            ), InkColors.PINK
        ), "_base", "_overlay_1", "_overlay_2", "_overlay_3"
    ));
    public static final DeferredItem<Item> ASHEN_CIRCLET = register(item(
        "ashen_circlet", () -> new AshenCircletItem(IS.of(1, Rarity.UNCOMMON)
                                                      .fireResistant()), InkColors.ORANGE
    ));
    public static final DeferredItem<Item> WEEPING_CIRCLET = register(
        simple(item("weeping_circlet", () -> new WeepingCircletItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_BLUE)));
    public static final DeferredItem<Item> PUFF_CIRCLET = register(simple(item(
        "puff_circlet", () -> new PuffCircletItem(
            IS.of(1, Rarity.UNCOMMON), PastelAdvancements.UNLOCK_PUFF_CIRCLET), InkColors.WHITE
    )));
    public static final DeferredItem<Item> WHISPY_CIRCLET = register(
        simple(item("whispy_circlet", () -> new WhispyCircletItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BROWN)));
    public static final DeferredItem<Item> CIRCLET_OF_ARROGANCE = register(simple(
        item("circlet_of_arrogance", () -> new CircletOfArroganceItem(IS.of(1, Rarity.UNCOMMON)), InkColors.RED)));
    public static final DeferredItem<Item> NEAT_RING = register(
        simple(item("neat_ring", () -> new NeatRingItem(IS.of(1, Rarity.EPIC)), InkColors.GREEN)));

    public static final DeferredItem<Item> AETHER_GRACED_NECTAR_GLOVES = register(simple(item(
        "aether_graced_nectar_gloves", () -> new AetherGracedNectarGlovesItem(
            IS.of(1, Rarity.EPIC), PastelAdvancements.UNLOCK_AETHER_GRACED_NECTAR_GLOVES), InkColors.PURPLE
    )));

    // Pure Clusters
    public static final DeferredItem<Item> PURE_COAL = register(
        simple(burnable(item("pure_coal", () -> new Item(IS.of()), InkColors.BROWN), 3200)));
    public static final DeferredItem<Item> PURE_IRON = register(
        simple(item("pure_iron", () -> new Item(IS.of()), InkColors.BROWN)));
    public static final DeferredItem<Item> PURE_GOLD = register(
        simple(item("pure_gold", () -> new Item(IS.of()), InkColors.BROWN)));
    public static final DeferredItem<Item> PURE_DIAMOND = register(
        simple(item("pure_diamond", () -> new Item(IS.of()), InkColors.CYAN)));
    public static final DeferredItem<Item> PURE_EMERALD = register(
        simple(item("pure_emerald", () -> new Item(IS.of()), InkColors.CYAN)));
    public static final DeferredItem<Item> PURE_REDSTONE = register(
        simple(item("pure_redstone", () -> new Item(IS.of()), InkColors.RED)));
    public static final DeferredItem<Item> PURE_LAPIS = register(
        simple(item("pure_lapis", () -> new Item(IS.of()), InkColors.PURPLE)));
    public static final DeferredItem<Item> PURE_COPPER = register(
        simple(item("pure_copper", () -> new Item(IS.of()), InkColors.BROWN)));
    public static final DeferredItem<Item> PURE_QUARTZ = register(
        simple(item("pure_quartz", () -> new Item(IS.of()), InkColors.BROWN)));
    public static final DeferredItem<Item> PURE_GLOWSTONE = register(
        simple(item("pure_glowstone", () -> new Item(IS.of()), InkColors.YELLOW)));
    public static final DeferredItem<Item> PURE_PRISMARINE = register(
        simple(item("pure_prismarine", () -> new Item(IS.of()), InkColors.CYAN)));
    public static final DeferredItem<Item> PURE_NETHERITE_SCRAP = register(simple(item(
        "pure_netherite_scrap", () -> new Item(IS.of()
                                                 .fireResistant()), InkColors.BROWN
    )));
    public static final DeferredItem<Item> PURE_ECHO = register(
        simple(item("pure_echo", () -> new Item(IS.of()), InkColors.BROWN)));

    //Technical Items
    public static final DeferredItem<Item> CONNECTION_NODE_CRYSTAL = register(
        item("connection_node_crystal", () -> new Item(IS.of()), InkColors.LIGHT_GRAY));
    public static final DeferredItem<Item> PROVIDER_NODE_CRYSTAL = register(
        item("provider_node_crystal", () -> new Item(IS.of()), InkColors.MAGENTA));
    public static final DeferredItem<Item> SENDER_NODE_CRYSTAL = register(
        item("sender_node_crystal", () -> new Item(IS.of()), InkColors.YELLOW));
    public static final DeferredItem<Item> STORAGE_NODE_CRYSTAL = register(
        item("storage_node_crystal", () -> new Item(IS.of()), InkColors.CYAN));
    public static final DeferredItem<Item> BUFFER_NODE_CRYSTAL = register(
        item("buffer_node_crystal", () -> new Item(IS.of()), InkColors.GREEN));
    public static final DeferredItem<Item> GATHER_NODE_CRYSTAL = register(
        item("gather_node_crystal", () -> new Item(IS.of()), InkColors.BLACK));
    public static final DeferredItem<Item> EXTENDED_BUNDLE_ITEM = register(
        parented(item("extended_bundle", () -> new ExtendedBundleItem(IS.of()), InkColors.BROWN), BUNDLE));

    public static <T extends Item> DeferredItem<T> register(ItemRegistrar<T> registrar) {
        return registrar.holder();
    }

    public static <T extends Item> ItemRegistrar<T> item(String name, Supplier<T> item, InkColor color) {
        return new ItemRegistrar<T>(name).withItem(item, color);
    }

    public static <T extends Item> ItemRegistrar<T> burnable(ItemRegistrar<T> registrar, int burnTicks) {
        return registrar.withBurnTime(burnTicks);
    }

    public static <T extends Item> ItemRegistrar<T> simple(ItemRegistrar<T> registrar) {
        return registrar.withItemModel(PastelModelHelper::registerItemModel);
    }

    public static <T extends Item> ItemRegistrar<T> handheld(ItemRegistrar<T> registrar) {
        return registrar.withItemModel(
            (ctx, item) -> PastelModelHelper.registerItemModel(ctx, item, ModelTemplates.FLAT_HANDHELD_ITEM));
    }

    public static <T extends Item> ItemRegistrar<T> layered(
        ItemRegistrar<T> registrar, String suffix0, String suffix1) {
        return registrar.withItemModel((ctx, item) -> PastelModelHelper.registerLayeredItemModel(
            ctx, item, ModelTemplates.TWO_LAYERED_ITEM, suffix0, suffix1));
    }

    public static <T extends Item> ItemRegistrar<T> layered(
        ItemRegistrar<T> registrar, String suffix0, String suffix1, String suffix2) {
        return registrar.withItemModel((ctx, item) -> PastelModelHelper.registerLayeredItemModel(
            ctx, item, ModelTemplates.THREE_LAYERED_ITEM, suffix0, suffix1, suffix2));
    }

    public static <T extends Item> ItemRegistrar<T> layered(
        ItemRegistrar<T> registrar, String suffix0, String suffix1, String suffix2, String suffix3) {
        return registrar.withItemModel((ctx, item) -> PastelModelHelper.registerLayeredItemModel(
            ctx, item, PastelModels.GENERATED_FOUR_LAYERS, suffix0, suffix1, suffix2, suffix3));
    }

    public static <T extends Item> ItemRegistrar<T> parented(ItemRegistrar<T> registrar, ItemLike parent) {
        return registrar.withItemModel(
            (ctx, item) -> PastelModelHelper.registerParentedItemModel(ctx, item, parent.asItem()));
    }

    public static <T extends Item> ItemRegistrar<T> parented(
        ItemRegistrar<T> registrar, ResourceLocation parentModelId) {
        return registrar.withItemModel(
            (ctx, item) -> PastelModelHelper.registerParentedItemModel(ctx, item, parentModelId));
    }

    public static <T extends Item> ItemRegistrar<T> banner(ItemRegistrar<T> registrar) {
        return parented(registrar, CREEPER_BANNER_PATTERN);
    }

    public static class ItemRegistrar<T extends Item> {

        private final ResourceLocation id;
        private boolean hasItem = false;
        @Nullable
        private DeferredItem<T> holder = null;

        public ItemRegistrar(String name) {
            this.id = locate(name);
        }

        public ItemRegistrar<T> withItem(Supplier<T> itemFactory, InkColor color) {
            if (hasItem) throw new UnsupportedOperationException("Attempted to register two items with id " + id);
            hasItem = true;
            //ItemColors.ITEM_COLORS.registerColorMapping(holder.get(), color); TODO ?????
            holder = ITEM_REGISTRAR.register(id.getPath(), itemFactory);
            return this;
        }

        public ItemRegistrar<T> withBurnTime(int burnTicks) {
            BURN_TIMES.add(new Pair<>(holder, burnTicks));
            return this;
        }

        public ItemRegistrar<T> withItemModel(BiConsumer<ItemModelGenerators, T> callback) {
            PastelModelHelper.ITEM_MODEL_REGISTRAR.defer(ctx -> {
                Objects.requireNonNull(holder);
                callback.accept(ctx, holder.get());
            });
            return this;
        }

        @Nullable
        public DeferredItem<T> holder() {
            return holder;
        }

        public ResourceKey<Item> itemKey() {
            return ResourceKey.create(Registries.ITEM, id);
        }

    }

    public static void register(IEventBus bus) {
        ITEM_REGISTRAR.register(bus);
    }

    public static class IS {

        public static Item.Properties of() {
            return new Item.Properties();
        }

        public static Item.Properties of(int maxCount) {
            return new Item.Properties().stacksTo(maxCount);
        }

        public static Item.Properties of(Rarity rarity) {
            return new Item.Properties().rarity(rarity);
        }

        public static Item.Properties of(int maxCount, Rarity rarity) {
            return new Item.Properties().stacksTo(maxCount)
                                        .rarity(rarity);
        }

        public static Item.Properties active(int maxCount, Rarity rarity) {
            return new Item.Properties().stacksTo(maxCount)
                                        .rarity(rarity)
                                        .component(PastelDataComponentTypes.ACTIVATED, Unit.INSTANCE);
        }
    }

    public static PairedFoodComponent teaSconeBonus(FoodProperties foodComponent) {
        return new PairedFoodComponent(PastelItems.SCONE, true, foodComponent);
    }

}
