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
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
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
import earth.terrarium.pastel.items.item_frame.EnderCanvasItem;
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
import earth.terrarium.pastel.items.magic_items.FlowingStaffItem;
import earth.terrarium.pastel.items.magic_items.GildedBookItem;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.items.magic_items.NaturesStaffItem;
import earth.terrarium.pastel.items.magic_items.PaintbrushItem;
import earth.terrarium.pastel.items.magic_items.PerturbedEyeItem;
import earth.terrarium.pastel.items.magic_items.PipeBombItem;
import earth.terrarium.pastel.items.magic_items.RadianceStaffItem;
import earth.terrarium.pastel.items.magic_items.StaffOfRemembranceItem;
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
import earth.terrarium.pastel.items.tools.DarkStakeItem;
import earth.terrarium.pastel.items.tools.DraconicTwinswordItem;
import earth.terrarium.pastel.items.tools.DragonTalonItem;
import earth.terrarium.pastel.items.tools.DreamflayerItem;
import earth.terrarium.pastel.items.tools.FerociousBidentItem;
import earth.terrarium.pastel.items.tools.FoxoNineTailsItem;
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
import earth.terrarium.pastel.items.tools.VerdigrisLashItem;
import earth.terrarium.pastel.items.tools.WireHookItem;
import earth.terrarium.pastel.items.tools.WorkstaffItem;
import earth.terrarium.pastel.items.trinkets.AetherGracedNectarGlovesItem;
import earth.terrarium.pastel.items.trinkets.AshenCircletItem;
import earth.terrarium.pastel.items.trinkets.AttackRingItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeAmuletItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeBeltItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeCoreItem;
import earth.terrarium.pastel.items.trinkets.AzureDikeRingItem;
import earth.terrarium.pastel.items.trinkets.CircletOfArroganceItem;
import earth.terrarium.pastel.items.trinkets.ConsumptionRingItem;
import earth.terrarium.pastel.items.trinkets.CottonCloudBootsItem;
import earth.terrarium.pastel.items.trinkets.ExtraHealthRingItem;
import earth.terrarium.pastel.items.trinkets.ExtraMiningSpeedRingItem;
import earth.terrarium.pastel.items.trinkets.ExtraReachGlovesItem;
import earth.terrarium.pastel.items.trinkets.GleamingPinItem;
import earth.terrarium.pastel.items.trinkets.InkDrainTrinketItem;
import earth.terrarium.pastel.items.trinkets.LaurelsOfSerenityItem;
import earth.terrarium.pastel.items.trinkets.NeatRingItem;
import earth.terrarium.pastel.items.trinkets.PotionPendantItem;
import earth.terrarium.pastel.items.trinkets.PriscillentSpectaclesItem;
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
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraft.world.item.component.ItemAttributeModifiers;
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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static earth.terrarium.pastel.PastelCommon.CONFIG;
import static earth.terrarium.pastel.PastelCommon.locate;
import static net.minecraft.world.item.Items.BLACK_DYE;
import static net.minecraft.world.item.Items.BLUE_DYE;
import static net.minecraft.world.item.Items.BROWN_DYE;
import static net.minecraft.world.item.Items.BUCKET;
import static net.minecraft.world.item.Items.CYAN_DYE;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;
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
import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_DAMAGE_ID;
import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_SPEED_ID;

// TODO: I am not sure how our tools are implemented rn but they REALLY should be migrated to working off of tool
// components. ~ Azzyy (whom will not be the one doing this)
public class PastelItems {

    public static final DeferredRegister.Items ITEM_REGISTRAR = DeferredRegister.createItems(PastelCommon.MOD_ID);

    public static final List<Pair<ItemLike, Integer>> BURN_TIMES = new ArrayList<>();

    // Main items
    public static final DeferredItem<Item> GUIDEBOOK = register(
        item("guidebook", () -> new GuidebookItem(IS.of(1)), InkColors.WHITE)
    );

    public static final DeferredItem<Item> PAINTBRUSH = register(
        item("paintbrush", () -> new PaintbrushItem(IS.of(1)), InkColors.WHITE)
    );

    public static final DeferredItem<Item> TUNING_STAMP = register(
        item("tuning_stamp", () -> new TuningStampItem(IS.of(1)), InkColors.WHITE)
    );

    public static final DeferredItem<Item> CRAFTING_TABLET = register(
        item("crafting_tablet", () -> new CraftingTabletItem(IS.of(1)), InkColors.LIGHT_GRAY)
    );

    // Structure placers
    public static final DeferredItem<Item> PEDESTAL_TIER_1_STRUCTURE_PLACER = register(
        item(
            "pedestal_tier_1_structure_placer",
            () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.PEDESTAL_SIMPLE),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> PEDESTAL_TIER_2_STRUCTURE_PLACER = register(
        item(
            "pedestal_tier_2_structure_placer",
            () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.PEDESTAL_ADVANCED),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> PEDESTAL_TIER_3_STRUCTURE_PLACER = register(
        item(
            "pedestal_tier_3_structure_placer",
            () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.PEDESTAL_COMPLEX),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> FUSION_SHRINE_STRUCTURE_PLACER = register(
        item(
            "fusion_shrine_structure_placer",
            () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.FUSION_SHRINE),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> ENCHANTER_STRUCTURE_PLACER = register(
        item(
            "enchanter_structure_placer",
            () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.ENCHANTER),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> SPIRIT_INSTILLER_STRUCTURE_PLACER = register(
        item(
            "spirit_instiller_structure_placer",
            () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.SPIRIT_INSTILLER),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> CINDERHEARTH_STRUCTURE_PLACER = register(
        item(
            "cinderhearth_structure_placer",
            () -> new StructurePlacerItem(IS.of(1), PastelMultiblocks.CINDERHEARTH),
            InkColors.WHITE
        )
    );

    // Gem shards and powders
    public static final DeferredItem<Item> TOPAZ_SHARD = register(
        item("topaz_shard", () -> new Item(IS.of()), InkColors.CYAN)
    );

    public static final DeferredItem<Item> CITRINE_SHARD = register(
        item("citrine_shard", () -> new Item(IS.of()), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> ONYX_SHARD = register(
        item("onyx_shard", () -> new Item(IS.of()), InkColors.BLACK)
    );

    public static final DeferredItem<Item> MOONSTONE_SHARD = register(
        item("moonstone_shard", () -> new Item(IS.of()), InkColors.WHITE)
    );

    public static final DeferredItem<Item> SPECTRAL_SHARD = register(
        item("spectral_shard", () -> new Item(IS.of(Rarity.RARE)), InkColors.WHITE)
    );

    public static final DeferredItem<Item> TOPAZ_POWDER = register(
        item("topaz_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.CYAN), InkColors.CYAN)
    );

    public static final DeferredItem<Item> AMETHYST_POWDER = register(
        item("amethyst_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.MAGENTA), InkColors.MAGENTA)
    );

    public static final DeferredItem<Item> CITRINE_POWDER = register(
        item("citrine_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.YELLOW), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> ONYX_POWDER = register(
        item("onyx_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.BLACK), InkColors.BLACK)
    );

    public static final DeferredItem<Item> MOONSTONE_POWDER = register(
        item("moonstone_powder", () -> new GemstonePowderItem(IS.of(), PastelGemstoneColor.WHITE), InkColors.WHITE)
    );

    // Pigment
    public static final DeferredItem<Item> WHITE_PIGMENT = register(
        item("white_pigment", () -> new PigmentItem(IS.of(), InkColors.WHITE, WHITE_DYE), InkColors.WHITE)
    );

    public static final DeferredItem<Item> ORANGE_PIGMENT = register(
        item("orange_pigment", () -> new PigmentItem(IS.of(), InkColors.ORANGE, ORANGE_DYE), InkColors.ORANGE)
    );

    public static final DeferredItem<Item> MAGENTA_PIGMENT = register(
        item("magenta_pigment", () -> new PigmentItem(IS.of(), InkColors.MAGENTA, MAGENTA_DYE), InkColors.MAGENTA)
    );

    public static final DeferredItem<Item> LIGHT_BLUE_PIGMENT = register(
        item(
            "light_blue_pigment",
            () -> new PigmentItem(IS.of(), InkColors.LIGHT_BLUE, LIGHT_BLUE_DYE),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<Item> YELLOW_PIGMENT = register(
        item("yellow_pigment", () -> new PigmentItem(IS.of(), InkColors.YELLOW, YELLOW_DYE), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> LIME_PIGMENT = register(
        item("lime_pigment", () -> new PigmentItem(IS.of(), InkColors.LIME, LIME_DYE), InkColors.LIME)
    );

    public static final DeferredItem<Item> PINK_PIGMENT = register(
        item("pink_pigment", () -> new PigmentItem(IS.of(), InkColors.PINK, PINK_DYE), InkColors.PINK)
    );

    public static final DeferredItem<Item> GRAY_PIGMENT = register(
        item("gray_pigment", () -> new PigmentItem(IS.of(), InkColors.GRAY, GRAY_DYE), InkColors.GRAY)
    );

    public static final DeferredItem<Item> LIGHT_GRAY_PIGMENT = register(
        item(
            "light_gray_pigment",
            () -> new PigmentItem(IS.of(), InkColors.LIGHT_GRAY, LIGHT_GRAY_DYE),
            InkColors.LIGHT_GRAY
        )
    );

    public static final DeferredItem<Item> CYAN_PIGMENT = register(
        item("cyan_pigment", () -> new PigmentItem(IS.of(), InkColors.CYAN, CYAN_DYE), InkColors.CYAN)
    );

    public static final DeferredItem<Item> PURPLE_PIGMENT = register(
        item("purple_pigment", () -> new PigmentItem(IS.of(), InkColors.PURPLE, PURPLE_DYE), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> BLUE_PIGMENT = register(
        item("blue_pigment", () -> new PigmentItem(IS.of(), InkColors.BLUE, BLUE_DYE), InkColors.BLUE)
    );

    public static final DeferredItem<Item> BROWN_PIGMENT = register(
        item("brown_pigment", () -> new PigmentItem(IS.of(), InkColors.BROWN, BROWN_DYE), InkColors.BROWN)
    );

    public static final DeferredItem<Item> GREEN_PIGMENT = register(
        item("green_pigment", () -> new PigmentItem(IS.of(), InkColors.GREEN, GREEN_DYE), InkColors.GREEN)
    );

    public static final DeferredItem<Item> RED_PIGMENT = register(
        item("red_pigment", () -> new PigmentItem(IS.of(), InkColors.RED, RED_DYE), InkColors.RED)
    );

    public static final DeferredItem<Item> BLACK_PIGMENT = register(
        item("black_pigment", () -> new PigmentItem(IS.of(), InkColors.BLACK, BLACK_DYE), InkColors.BLACK)
    );

    // Preenchanted tools
    public static final DeferredItem<PreenchantedMultiToolItem> MULTITOOL = register(
        item(
            "multitool",
            () -> new PreenchantedMultiToolItem(
                Tiers.IRON,
                2,
                -2.4F,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(Tiers.IRON.getUses())
            ),
            InkColors.BROWN
        )
    );

    public static final DeferredItem<GlintlessPickaxe> TENDER_PICKAXE = register(
        item(
            "tender_pickaxe",
            () -> new GlintlessPickaxe(
                PastelToolMaterial.LOW_HEALTH,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                    .attributes(
                        PickaxeItem.createAttributes(PastelToolMaterial.LOW_HEALTH, 1, -2.8F)
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(Enchantments.SILK_TOUCH, 1);
                }
            },
            InkColors.BLUE
        )
    );

    public static final DeferredItem<GlintlessPickaxe> LUCKY_PICKAXE = register(
        item(
            "lucky_pickaxe",
            () -> new GlintlessPickaxe(
                PastelToolMaterial.LOW_HEALTH,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                    .attributes(
                        PickaxeItem.createAttributes(PastelToolMaterial.LOW_HEALTH, 1, -2.8F)
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(Enchantments.FORTUNE, 3);
                }
            },
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<RazorFalchionItem> RAZOR_FALCHION = register(
        item(
            "razor_falchion",
            () -> new RazorFalchionItem(
                PastelToolMaterial.LOW_HEALTH,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                    .attributes(
                        SwordItem.createAttributes(PastelToolMaterial.LOW_HEALTH, 4, -2.2F)
                    )
            ),
            InkColors.RED
        )
    );

    public static final DeferredItem<OblivionPickaxeItem> OBLIVION_PICKAXE = register(
        item(
            "oblivion_pickaxe",
            () -> new OblivionPickaxeItem(
                PastelToolMaterial.VOIDING,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.VOIDING.getUses())
                    .attributes(
                        PickaxeItem.createAttributes(PastelToolMaterial.VOIDING, 1, -2.8F)
                    )
            ),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<GlintlessPickaxe> RESONANT_PICKAXE = register(
        item(
            "resonant_pickaxe",
            () -> new GlintlessPickaxe(
                PastelToolMaterial.LOW_HEALTH_MINING_LEVEL_4,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.LOW_HEALTH.getUses())
                    .attributes(
                        PickaxeItem
                            .createAttributes(
                                PastelToolMaterial.LOW_HEALTH_MINING_LEVEL_4,
                                1,
                                -2.8F
                            )
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(PastelEnchantments.RESONANCE, 1);
                }
            },
            InkColors.WHITE
        )
    );

    public static final DeferredItem<GlintlessPickaxe> DRAGONRENDING_PICKAXE = register(
        item(
            "dragonrending_pickaxe",
            () -> new GlintlessPickaxe(
                PastelToolMaterial.DRACONIC,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.DRACONIC.getUses())
                    .attributes(
                        PickaxeItem.createAttributes(PastelToolMaterial.DRACONIC, 1, -2.8F)
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(PastelEnchantments.RAZING, 3);
                }
            },
            InkColors.WHITE
        )
    );

    public static final DeferredItem<LagoonRodItem> LAGOON_ROD = register(
        item(
            "lagoon_rod",
            () -> new LagoonRodItem(
                IS
                    .of()
                    .durability(256)
            ),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<MoltenRodItem> MOLTEN_ROD = register(
        item(
            "molten_rod",
            () -> new MoltenRodItem(
                IS
                    .of()
                    .durability(256)
            ),
            InkColors.ORANGE
        )
    );

    // Bedrock Tools
    public static final DeferredItem<PastelPickaxeItem> BEDROCK_PICKAXE = register(
        item(
            "bedrock_pickaxe",
            () -> new PastelPickaxeItem(
                PastelToolMaterial.BEDROCK,
                IS
                    .of(Rarity.UNCOMMON)
                    .attributes(
                        PickaxeItem.createAttributes(PastelToolMaterial.BEDROCK, 1, -2.8F)
                    )
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(Enchantments.SILK_TOUCH, 1);
                }
            },
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockAxeItem> BEDROCK_AXE = register(
        item(
            "bedrock_axe",
            () -> new BedrockAxeItem(
                PastelToolMaterial.BEDROCK,
                IS
                    .of(Rarity.UNCOMMON)
                    .attributes(AxeItem.createAttributes(PastelToolMaterial.BEDROCK, 5, -3.0F))
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockShovelItem> BEDROCK_SHOVEL = register(
        item(
            "bedrock_shovel",
            () -> new BedrockShovelItem(
                PastelToolMaterial.BEDROCK,
                IS
                    .of(Rarity.UNCOMMON)
                    .attributes(ShovelItem.createAttributes(PastelToolMaterial.BEDROCK, 1, -3.0F))
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockSwordItem> BEDROCK_SWORD = register(
        item(
            "bedrock_sword",
            () -> new BedrockSwordItem(
                PastelToolMaterial.BEDROCK,
                IS
                    .of(Rarity.UNCOMMON)
                    .attributes(SwordItem.createAttributes(PastelToolMaterial.BEDROCK, 4, -2.4F))
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockHoeItem> BEDROCK_HOE = register(
        item(
            "bedrock_hoe",
            () -> new BedrockHoeItem(
                PastelToolMaterial.BEDROCK,
                IS
                    .of(Rarity.UNCOMMON)
                    .attributes(HoeItem.createAttributes(PastelToolMaterial.BEDROCK, 2, -0.0F))
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockBowItem> BEDROCK_BOW = register(
        item(
            "bedrock_bow",
            () -> new BedrockBowItem(
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockCrossbowItem> BEDROCK_CROSSBOW = register(
        item(
            "bedrock_crossbow",
            () -> new BedrockCrossbowItem(
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(
                        DataComponents.UNBREAKABLE,
                        new Unbreakable(false)
                    )
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockShearsItem> BEDROCK_SHEARS = register(
        item(
            "bedrock_shears",
            () -> new BedrockShearsItem(
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .component(
                        DataComponents.TOOL,
                        ShearsItem.createToolProperties()
                    )
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockFishingRodItem> BEDROCK_FISHING_ROD = register(
        item(
            "bedrock_fishing_rod",
            () -> new BedrockFishingRodItem(
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(PastelToolMaterial.BEDROCK.getUses())
                    .component(
                        DataComponents.UNBREAKABLE,
                        new Unbreakable(false)
                    )
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<WorkstaffItem> MALACHITE_WORKSTAFF = register(
        item(
            "malachite_workstaff",
            () -> new WorkstaffItem(PastelToolMaterial.MALACHITE, 1, -3.2F, IS.of(1, Rarity.UNCOMMON)),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<GreatswordItem> MALACHITE_ULTRA_GREATSWORD = register(
        item(
            "malachite_ultra_greatsword",
            () -> new GreatswordItem(PastelToolMaterial.MALACHITE, 7, -2.8F, 1.0F, IS.of(1, Rarity.UNCOMMON)),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<MalachiteCrossbowItem> MALACHITE_CROSSBOW = register(
        item(
            "malachite_crossbow",
            () -> new MalachiteCrossbowItem(
                IS
                    .of(1, Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(PastelToolMaterial.MALACHITE.getUses())
            ),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<MalachiteBidentItem> MALACHITE_BIDENT = register(
        item(
            "malachite_bident",
            () -> new MalachiteBidentItem(
                IS
                    .active(1, Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.MALACHITE.getUses()),
                -2.4,
                9,
                0.25F,
                0F
            ),
            InkColors.GREEN
        )
    );

    // variants by socketing a moonstone core
    public static final DeferredItem<GlassCrestWorkstaffItem> GLASS_CREST_WORKSTAFF = register(
        item(
            "glass_crest_workstaff",
            () -> new GlassCrestWorkstaffItem(PastelToolMaterial.GLASS_CREST, 1, -2.8F, IS.of(1, Rarity.UNCOMMON)),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<GlassCrestGreatswordItem> GLASS_CREST_ULTRA_GREATSWORD = register(
        item(
            "glass_crest_ultra_greatsword",
            () -> new GlassCrestGreatswordItem(
                PastelToolMaterial.GLASS_CREST,
                5,
                -2.8F,
                1.0F,
                IS.of(1, Rarity.UNCOMMON)
            ),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<GlassCrestCrossbowItem> GLASS_CREST_CROSSBOW = register(
        item(
            "glass_crest_crossbow",
            () -> new GlassCrestCrossbowItem(
                IS
                    .of(1, Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(
                        PastelToolMaterial.GLASS_CREST.getUses()
                    )
            ),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<FerociousBidentItem> FEROCIOUS_GLASS_CREST_BIDENT = register(
        item(
            "ferocious_glass_crest_bident",
            () -> new FerociousBidentItem(
                IS
                    .active(1, Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.GLASS_CREST.getUses()),
                -2.2,
                13,
                0.33F,
                0.33F
            ),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<FractalBidentItem> FRACTAL_GLASS_CREST_BIDENT = register(
        item(
            "fractal_glass_crest_bident",
            () -> new FractalBidentItem(
                IS
                    .active(1, Rarity.UNCOMMON)
                    .durability(PastelToolMaterial.GLASS_CREST.getUses()),
                -2.4,
                6.5,
                0.25F,
                0.25F
            ),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> MALACHITE_GLASS_ARROW = register(
        item(
            "malachite_glass_arrow",
            () -> new GlassArrowItem(
                IS.of(Rarity.UNCOMMON),
                GlassArrowVariant.MALACHITE,
                ColoredCraftingParticleEffect.LIME
            ),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<Item> TOPAZ_GLASS_ARROW = register(
        item(
            "topaz_glass_arrow",
            () -> new GlassArrowItem(
                IS.of(Rarity.UNCOMMON),
                GlassArrowVariant.TOPAZ,
                ColoredCraftingParticleEffect.CYAN
            ),
            InkColors.CYAN
        )
    );

    public static final DeferredItem<Item> AMETHYST_GLASS_ARROW = register(
        item(
            "amethyst_glass_arrow",
            () -> new GlassArrowItem(
                IS.of(Rarity.UNCOMMON),
                GlassArrowVariant.AMETHYST,
                ColoredCraftingParticleEffect.MAGENTA
            ),
            InkColors.MAGENTA
        )
    );

    public static final DeferredItem<Item> CITRINE_GLASS_ARROW = register(
        item(
            "citrine_glass_arrow",
            () -> new GlassArrowItem(
                IS.of(Rarity.UNCOMMON),
                GlassArrowVariant.CITRINE,
                ColoredCraftingParticleEffect.YELLOW
            ),
            InkColors.YELLOW
        )
    );

    public static final DeferredItem<Item> ONYX_GLASS_ARROW = register(
        item(
            "onyx_glass_arrow",
            () -> new GlassArrowItem(
                IS.of(Rarity.UNCOMMON),
                GlassArrowVariant.ONYX,
                ColoredCraftingParticleEffect.BLACK
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<Item> MOONSTONE_GLASS_ARROW = register(
        item(
            "moonstone_glass_arrow",
            () -> new GlassArrowItem(
                IS.of(Rarity.UNCOMMON),
                GlassArrowVariant.MOONSTONE,
                ColoredCraftingParticleEffect.WHITE
            ),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> DARK_STAKE = register(
        item(
            "dark_stake",
            () -> new DarkStakeItem(
                IS
                    .of(16, Rarity.UNCOMMON)
                    .attributes(
                        ItemAttributeModifiers
                            .builder()
                            .add(
                                Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(
                                    BASE_ATTACK_DAMAGE_ID,
                                    4.0f,
                                    AttributeModifier.Operation.ADD_VALUE
                                ),
                                EquipmentSlotGroup.MAINHAND
                            )
                            .add(
                                Attributes.ATTACK_SPEED,
                                new AttributeModifier(
                                    BASE_ATTACK_SPEED_ID,
                                    -2.5f,
                                    AttributeModifier.Operation.ADD_VALUE
                                ),
                                EquipmentSlotGroup.MAINHAND
                            )
                            .build()
                    )
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<Item> OMNI_ACCELERATOR = register(
        item(
            "omni_accelerator",
            () -> new OmniAcceleratorItem(
                IS
                    .of(1, Rarity.UNCOMMON)
                    .component(
                        DataComponents.BUNDLE_CONTENTS,
                        BundleContents.EMPTY
                    )
            ),
            InkColors.YELLOW
        )
    );

    // Special tools
    // TODO: set attribute modifiers similarly to how vanilla swords do it
    public static final DeferredItem<DreamflayerItem> DREAMFLAYER = register(
        item(
            "dreamflayer",
            () -> new DreamflayerItem(PastelToolMaterial.DREAMFLAYER, 3, -1.8F, IS.of(1, Rarity.UNCOMMON)),
            InkColors.RED
        )
    );

    public static final DeferredItem<NightfallsBladeItem> NIGHTFALLS_BLADE = register(
        item(
            "nightfalls_blade",
            () -> new NightfallsBladeItem(Tiers.DIAMOND, 3, -2.4F, IS.of(1, Rarity.UNCOMMON)),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<DraconicTwinswordItem> DRACONIC_TWINSWORD = register(
        item(
            "draconic_twinsword",
            () -> new DraconicTwinswordItem(PastelToolMaterial.DRACONIC, 6, -3.0F, IS.of(1, Rarity.RARE)),
            InkColors.YELLOW
        )
    );

    public static final DeferredItem<DragonTalonItem> DRAGON_TALON = register(
        item(
            "dragon_talon",
            () -> new DragonTalonItem(
                PastelToolMaterial.DRACONIC,
                -3.0,
                -1.0,
                IS
                    .of(1, Rarity.RARE)
                    .durability(PastelToolMaterial.DRACONIC.getUses())
            ),
            InkColors.YELLOW
        )
    );

    public static final DeferredItem<LightGreatswordItem> KNOTTED_SWORD = register(
        item(
            "knotted_sword",
            () -> new LightGreatswordItem(
                PastelToolMaterial.VERDIGRIS,
                3,
                -2.4F,
                0.25F,
                0.5F,
                0xFFd4d6ff,
                new ColorGradient(
                    new Color(139, 255, 213),
                    new Color(0, 178, 183)
                )
                    .fadeAlpha(1, 0)
                    .fadeAlpha(0, 0, 1, 0.05f),
                IS
                    .of(1, Rarity.UNCOMMON)
                    .durability(
                        PastelToolMaterial.VERDIGRIS.getUses()
                    )
            ),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<NectarLanceItem> NECTAR_LANCE = register(
        item(
            "nectar_lance",
            () -> new NectarLanceItem(
                PastelToolMaterial.NECTAR,
                0,
                -2.4F,
                0.5F,
                1.5F,
                0xFFf8e8ff,
                new ColorGradient(
                    new Color(246, 192, 255),
                    new Color(155, 0, 204)
                )
                    .fadeAlpha(1, 0)
                    .fadeAlpha(0, 0, 1, 0.05f),
                IS
                    .of(1, Rarity.EPIC)
                    .durability(
                        PastelToolMaterial.NECTAR.getUses()
                    )
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<VerdigrisLashItem> VERDIGRIS_LASH = register(
        item(
            "verdigris_lash",
            () -> new VerdigrisLashItem(
                PastelToolMaterial.VERDIGRIS,
                2,
                -3,
                IS.of(1, Rarity.UNCOMMON).durability(PastelToolMaterial.VERDIGRIS.getUses())
            ),
            InkColors.GREEN // it's actually _cerise_, but we don't have the stargazer colors yet
        )
    );

    public static final DeferredItem<FoxoNineTailsItem> FOX_O_NINE_TAILS = register(
        item(
            "fox_o_nine_tails",
            () -> new FoxoNineTailsItem(
                PastelToolMaterial.VERDIGRIS,
                1,
                -3,
                IS.of(1, Rarity.EPIC).durability(PastelToolMaterial.NECTAR.getUses())
            ),
            InkColors.PINK // it's even cerisier, but we don't have the stargazer colors yet
        )
    );

    // Bedrock Armor
    public static final DeferredItem<BedrockArmorItem> BEDROCK_HELMET = register(
        item(
            "bedrock_helmet",
            () -> new BedrockArmorItem(
                PastelArmorMaterials.BEDROCK,
                ArmorItem.Type.HELMET,
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(70 * 13)
                    .component(
                        DataComponents.UNBREAKABLE,
                        new Unbreakable(false)
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(Enchantments.PROJECTILE_PROTECTION, 5);
                }
            },
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockArmorItem> BEDROCK_CHESTPLATE = register(
        item(
            "bedrock_chestplate",
            () -> new BedrockArmorItem(
                PastelArmorMaterials.BEDROCK,
                ArmorItem.Type.CHESTPLATE,
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(70 * 15)
                    .component(
                        DataComponents.UNBREAKABLE,
                        new Unbreakable(false)
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(Enchantments.PROTECTION, 5);
                }
            },
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockArmorItem> BEDROCK_LEGGINGS = register(
        item(
            "bedrock_leggings",
            () -> new BedrockArmorItem(
                PastelArmorMaterials.BEDROCK,
                ArmorItem.Type.LEGGINGS,
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(70 * 16)
                    .component(
                        DataComponents.UNBREAKABLE,
                        new Unbreakable(false)
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(Enchantments.BLAST_PROTECTION, 5);
                }
            },
            InkColors.BLACK
        )
    );

    public static final DeferredItem<BedrockArmorItem> BEDROCK_BOOTS = register(
        item(
            "bedrock_boots",
            () -> new BedrockArmorItem(
                PastelArmorMaterials.BEDROCK,
                ArmorItem.Type.BOOTS,
                IS
                    .of(Rarity.UNCOMMON)
                    .fireResistant()
                    .durability(70 * 11)
                    .component(
                        DataComponents.UNBREAKABLE,
                        new Unbreakable(false)
                    )
            ) {
                @Override
                public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
                    return Map.of(Enchantments.FIRE_PROTECTION, 5);
                }
            },
            InkColors.BLACK
        )
    );

    // Crystal Armor - Iron-level dura
    public static final DeferredItem<CrystalArmorItem> ONYX_HELMET = register(
        item(
            "onyx_helmet",
            () -> new CrystalArmorItem(
                PastelArmorMaterials.CRYSTAL,
                ArmorItem.Type.HELMET,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(15 * 13)
            ),
            InkColors.BLUE
        )
    );

    public static final DeferredItem<CrystalArmorItem> AMETHYST_CHESTPLATE = register(
        item(
            "amethyst_chestplate",
            () -> new CrystalArmorItem(
                PastelArmorMaterials.CRYSTAL,
                ArmorItem.Type.CHESTPLATE,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(15 * 15)
            ),
            InkColors.BLUE
        )
    );

    // double dura because it's topaz
    public static final DeferredItem<CrystalArmorItem> TOPAZ_LEGGINGS = register(
        item(
            "topaz_leggings",
            () -> new CrystalArmorItem(
                PastelArmorMaterials.CRYSTAL,
                ArmorItem.Type.LEGGINGS,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(15 * 16 * 2)
            ),
            InkColors.BLUE
        )
    );

    public static final DeferredItem<CrystalArmorItem> CITRINE_BOOTS = register(
        item(
            "citrine_boots",
            () -> new CrystalArmorItem(
                PastelArmorMaterials.CRYSTAL,
                ArmorItem.Type.BOOTS,
                IS
                    .of(Rarity.UNCOMMON)
                    .durability(15 * 11)
                    .attributes(
                        new ItemAttributeModifiers(
                            List
                                .of(
                                    new ItemAttributeModifiers.Entry(
                                        Attributes.MOVEMENT_SPEED,
                                        CrystalArmorItem.GEM_BOOTS_SPEED,
                                        EquipmentSlotGroup.FEET
                                    ),
                                    new ItemAttributeModifiers.Entry(
                                        Attributes.ARMOR,
                                        new AttributeModifier(
                                            PastelCommon
                                                .locate(
                                                    "citrine_boots_armor"
                                                ),
                                            3,
                                            AttributeModifier.Operation.ADD_VALUE
                                        ),
                                        EquipmentSlotGroup.FEET
                                    )
                                ),
                            true
                        )
                    )
            ),
            InkColors.BLUE
        )
    );

    // Decay drops
    public static final DeferredItem<Item> VEGETAL = register(
        burnable(
            item("vegetal", () -> new ItemWithLoomPattern(IS.of(), PastelBannerPatterns.VEGETAL), InkColors.LIME),
            800
        )
    );

    public static final DeferredItem<Item> NEOLITH = register(
        item(
            "neolith",
            () -> new ItemWithLoomPattern(IS.of(Rarity.UNCOMMON), PastelBannerPatterns.NEOLITH),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> BEDROCK_DUST = register(
        item(
            "bedrock_dust",
            () -> new ItemWithLoomPattern(IS.of(Rarity.UNCOMMON), PastelBannerPatterns.BEDROCK_DUST),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<MidnightAberrationItem> MIDNIGHT_ABERRATION = register(
        item("midnight_aberration", () -> new MidnightAberrationItem(IS.of(Rarity.UNCOMMON)), InkColors.GRAY)
    );

    public static final DeferredItem<Item> MIDNIGHT_CHIP = register(
        item("midnight_chip", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.GRAY)
    );

    public static final DeferredItem<Item> BISMUTH_FLAKE = register(
        item("bismuth_flake", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.CYAN)
    );

    public static final DeferredItem<Item> BISMUTH_CRYSTAL = register(
        item("bismuth_crystal", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.CYAN)
    );

    public static final DeferredItem<Item> RAW_MALACHITE = register(
        item("raw_malachite", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> PURE_MALACHITE = register(
        item("pure_malachite", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.GREEN)
    );

    // Fluid Buckets
    public static final DeferredItem<Item> LIQUID_CRYSTAL_BUCKET = register(
        item(
            "liquid_crystal_bucket",
            () -> new BucketItem(
                PastelFluids.LIQUID_CRYSTAL.get(),
                IS
                    .of(1)
                    .craftRemainder(BUCKET)
            ),
            InkColors.LIGHT_GRAY
        )
    );

    public static final DeferredItem<Item> HUMUS_BUCKET = register(
        item(
            "humus_bucket",
            () -> new BucketItem(
                PastelFluids.HUMUS.get(),
                IS
                    .of(1)
                    .craftRemainder(BUCKET)
            ),
            InkColors.BROWN
        )
    );

    public static final DeferredItem<Item> MIDNIGHT_SOLUTION_BUCKET = register(
        item(
            "midnight_solution_bucket",
            () -> new BucketItem(
                PastelFluids.MIDNIGHT_SOLUTION.get(),
                IS
                    .of(1)
                    .craftRemainder(BUCKET)
            ),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<Item> DRAGONROT_BUCKET = register(
        item(
            "dragonrot_bucket",
            () -> new BucketItem(
                PastelFluids.DRAGONROT.get(),
                IS
                    .of(1)
                    .craftRemainder(BUCKET)
            ),
            InkColors.LIGHT_GRAY
        )
    );

    // Decay bottles
    public static final DeferredItem<Item> BOTTLE_OF_FADING = register(
        item(
            "bottle_of_fading",
            () -> new DecayPlacerItem(
                PastelBlocks.FADING.get(),
                IS.of(16),
                List.of(Component.translatable("item.pastel.bottle_of_fading.tooltip"))
            ),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<Item> BOTTLE_OF_FAILING = register(
        item(
            "bottle_of_failing",
            () -> new DecayPlacerItem(
                PastelBlocks.FAILING.get(),
                IS.of(16),
                List.of(Component.translatable("item.pastel.bottle_of_failing.tooltip"))
            ),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<Item> BOTTLE_OF_RUIN = register(
        item(
            "bottle_of_ruin",
            () -> new DecayPlacerItem(
                PastelBlocks.RUIN.get(),
                IS.of(16),
                List
                    .of(
                        Component.translatable("item.pastel.bottle_of_ruin.tooltip")
                    )
            ),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<Item> BOTTLE_OF_FORFEITURE = register(
        item(
            "bottle_of_forfeiture",
            () -> new DecayPlacerItem(
                PastelBlocks.FORFEITURE.get(),
                IS.of(16),
                List
                    .of(
                        CreativeOnlyItem.DESCRIPTION,
                        Component.translatable("item.pastel.bottle_of_forfeiture.tooltip")
                    )
            ),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<Item> BOTTLE_OF_DECAY_AWAY = register(
        item(
            "bottle_of_decay_away",
            () -> new DecayPlacerItem(
                PastelBlocks.DECAY_AWAY.get(),
                IS.of(16),
                List.of(Component.translatable("item.pastel.bottle_of_decay_away.tooltip"))
            ),
            InkColors.PINK
        )
    );

    // Resources
    public static final DeferredItem<Item> SHIMMERSTONE_GEM = register(
        item(
            "shimmerstone_gem",
            () -> new ItemWithLoomPattern(IS.of(), PastelBannerPatterns.SHIMMERSTONE),
            InkColors.YELLOW
        )
    );

    public static final DeferredItem<Item> RAW_AZURITE = register(
        item("raw_azurite", () -> new ItemWithLoomPattern(IS.of(), PastelBannerPatterns.RAW_AZURITE), InkColors.BLUE)
    );

    public static final DeferredItem<Item> PURE_AZURITE = register(
        item("pure_azurite", () -> new Item(IS.of()), InkColors.BLUE)
    );

    public static final DeferredItem<FloatItem> PALTAERIA_FRAGMENTS = register(
        item("paltaeria_fragments", () -> new FloatItem(IS.of(), 0.00125F), InkColors.LIGHT_BLUE)
    );

    public static final DeferredItem<FloatItem> PALTAERIA_GEM = register(
        item("paltaeria_gem", () -> new FloatItem(IS.of(16), 0.01F), InkColors.LIGHT_BLUE)
    );

    public static final DeferredItem<FloatItem> STRATINE_FRAGMENTS = register(
        item(
            "stratine_fragments",
            () -> new FloatItem(
                IS
                    .of()
                    .fireResistant(),
                -0.00125F
            ),
            InkColors.RED
        )
    );

    public static final DeferredItem<FloatItem> STRATINE_GEM = register(
        item(
            "stratine_gem",
            () -> new FloatItem(
                IS
                    .of(16)
                    .fireResistant(),
                -0.01F
            ),
            InkColors.RED
        )
    );

    public static final DeferredItem<Item> PYRITE_CHUNK = register(
        item("pyrite_chunk", () -> new Item(IS.of()), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> DRAGONBONE_CHUNK = register(
        item("dragonbone_chunk", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.GRAY)
    );

    public static final DeferredItem<Item> BONE_ASH = register(
        item("bone_ash", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.GRAY)
    );

    public static final DeferredItem<Item> RESPLENDENT_FEATHER = register(
        item("resplendent_feather", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> RAW_BLOODSTONE = register(
        item("raw_bloodstone", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.RED)
    );

    public static final DeferredItem<Item> PURE_BLOODSTONE = register(
        item("pure_bloodstone", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.RED)
    );

    public static final DeferredItem<Item> DOWNSTONE_FRAGMENTS = register(
        item("downstone_fragments", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.LIGHT_GRAY)
    );

    public static final DeferredItem<Item> RESONANCE_SHARD = register(
        item("resonance_shard", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.WHITE)
    );

    public static final DeferredItem<Item> AETHER_VESTIGES = register(
        item(
            "aether_vestiges",
            () -> new AetherVestigesItem(
                IS
                    .of(1, Rarity.EPIC)
                    .fireResistant(),
                "item.pastel.aether_vestiges.tooltip"
            ),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> QUITOXIC_POWDER = register(
        item("quitoxic_powder", () -> new Item(IS.of()), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> STORM_STONE = register(
        item("storm_stone", () -> new StormStoneItem(IS.of()), InkColors.LIGHT_BLUE)
    );

    public static final DeferredItem<Item> MERMAIDS_GEM = register(
        item(
            "mermaids_gem",
            () -> new ItemNameBlockItem(
                PastelBlocks.MERMAIDS_BRUSH.get(),
                IS
                    .of()
                    .component(
                        PastelDataComponentTypes.MERMAIDS_GEM,
                        SimpleFluidContent.copyOf(new FluidStack(Fluids.WATER, 1000))
                    )
            ),
            InkColors.YELLOW
        )
    );

    public static final DeferredItem<Item> STAR_FRAGMENT = register(
        item("star_fragment", () -> new Item(IS.of(16)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> STARDUST = register(
        item("stardust", () -> new ItemWithLoomPattern(IS.of(), PastelBannerPatterns.SHIMMER), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> ASH_FLAKES = register(
        item("ash_flakes", () -> new AshItem(IS.of(), "item.pastel.ash_flakes.tooltip"), InkColors.LIGHT_GRAY)
    );

    public static final DeferredItem<Item> HIBERNATING_JADE_VINE_BULB = register(
        item(
            "hibernating_jade_vine_bulb",
            () -> new ItemWithTooltip(IS.of(16), "item.pastel.hibernating_jade_vine_bulb.tooltip"),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<Item> GERMINATED_JADE_VINE_BULB = register(
        item("germinated_jade_vine_bulb", () -> new GerminatedJadeVineBulbItem(IS.of(16)), InkColors.LIME)
    );

    public static final DeferredItem<Item> JADE_PETALS = register(
        item("jade_petals", () -> new ItemWithLoomPattern(IS.of(), PastelBannerPatterns.JADE_VINE), InkColors.LIME)
    );

    // TODO: Funky unlock?
    public static final DeferredItem<Item> JADEITE_PETALS = register(
        item("jadeite_petals", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.BROWN)
    );

    public static final DeferredItem<Item> BLOOD_ORCHID_PETAL = register(
        item("blood_orchid_petal", () -> new Item(IS.of()), InkColors.RED)
    );

    public static final DeferredItem<Item> BLOODBOIL_SYRUP = register(
        item(
            "bloodboil_syrup",
            () -> new BloodOrchidDrinkItem(
                IS
                    .of()
                    .food(PastelFoodComponents.BLOODBOIL_SYRUP)
                    .craftRemainder(GLASS_BOTTLE)
            ),
            InkColors.RED
        )
    );

    public static final DeferredItem<Item> MILKY_RESIN = register(
        item("milky_resin", () -> new Item(IS.of(Rarity.UNCOMMON)), InkColors.LIGHT_GRAY)
    );

    // Food & drinks
    public static final DeferredItem<Item> MOONSTRUCK_NECTAR = register(
        item(
            "moonstruck_nectar",
            () -> new MoonstruckNectarItem(
                IS
                    .of(Rarity.UNCOMMON)
                    .food(PastelFoodComponents.MOONSTRUCK_NECTAR)
                    .craftRemainder(GLASS_BOTTLE)
            ),
            InkColors.LIME
        )
    );

    public static final DeferredItem<Item> JADE_JELLY = register(
        item(
            "jade_jelly",
            () -> new ItemWithTooltip(
                IS
                    .of()
                    .food(PastelFoodComponents.JADE_JELLY),
                "item.pastel.jade_jelly.tooltip"
            ),
            InkColors.LIME
        )
    );

    public static final DeferredItem<Item> GLASS_PEACH = register(
        item(
            "glass_peach",
            () -> new ItemWithTooltip(
                IS
                    .of()
                    .food(PastelFoodComponents.GLASS_PEACH),
                "item.pastel.glass_peach.tooltip"
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> FISSURE_PLUM = register(
        item(
            "fissure_plum",
            () -> new AliasedTooltipItem(
                PastelBlocks.ABYSSAL_VINES.get(),
                IS
                    .of()
                    .food(PastelFoodComponents.FISSURE_PLUM),
                "item.pastel.fissure_plum.tooltip"
            ),
            InkColors.BROWN
        )
    );

    public static final DeferredItem<Item> NIGHTDEW_SPROUT = register(
        item(
            "nightdew_sprout",
            () -> new AliasedTooltipItem(
                PastelBlocks.NIGHTDEW.get(),
                IS
                    .of()
                    .food(PastelFoodComponents.NIGHTDEW_SPROUT),
                "item.pastel.nightdew_sprout.tooltip"
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> NECTARDEW_BURGEON = register(
        item(
            "nectardew_burgeon",
            () -> new NectardewBurgeonItem(
                IS
                    .of()
                    .food(PastelFoodComponents.NECTARDEW_BURGEON),
                "item.pastel.nectardew_burgeon.tooltip"
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> RESTORATION_TEA = register(
        item(
            "restoration_tea",
            () -> new RestorationTeaItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.RESTORATION_TEA)
                    .craftRemainder(GLASS_BOTTLE)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(
                            PastelFoodComponents.RESTORATION_TEA_SCONE_BONUS
                        )
                    )
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> CLOTTED_CREAM = register(
        item(
            "clotted_cream",
            () -> new ClottedCreamItem(
                IS
                    .of()
                    .food(PastelFoodComponents.CLOTTED_CREAM),
                new String[] {
                    "item.pastel.clotted_cream.tooltip", "item.pastel.clotted_cream.tooltip2"
                }
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> FRESH_CHOCOLATE = register(
        item(
            "fresh_chocolate",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.FRESH_CHOCOLATE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> HOT_CHOCOLATE = register(
        item(
            "hot_chocolate",
            () -> new DrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.HOT_CHOCOLATE)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(PastelFoodComponents.HOT_CHOCOLATE_SCONE_BONUS)
                    )
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> KARAK_CHAI = register(
        item(
            "karak_chai",
            () -> new DrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.KARAK_CHAI)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(PastelFoodComponents.KARAK_CHAI_SCONE_BONUS)
                    )
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> AZALEA_TEA = register(
        item(
            "azalea_tea",
            () -> new AzaleaTeaItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.AZALEA_TEA)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(PastelFoodComponents.AZALEA_TEA_SCONE_BONUS)
                    )
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> BODACIOUS_BERRY_BAR = register(
        item(
            "bodacious_berry_bar",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.BODACIOUS_BERRY_BAR)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> DEMON_TEA = register(
        item(
            "demon_tea",
            () -> new BloodOrchidDrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.DEMON_TEA)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(PastelFoodComponents.DEMON_TEA_SCONE_BONUS)
                    )
            ),
            InkColors.RED
        )
    );

    public static final DeferredItem<Item> SCONE = register(
        item(
            "scone",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.SCONE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> CHEONG = register(
        item(
            "cheong",
            () -> new ItemWithTooltip(
                IS
                    .of()
                    .food(PastelFoodComponents.CHEONG),
                "item.pastel.cheong.tooltip"
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MERMAIDS_JAM = register(
        item(
            "mermaids_jam",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MERMAIDS_JAM)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MERMAIDS_POPCORN = register(
        item(
            "mermaids_popcorn",
            () -> new ItemWithTooltip(
                IS
                    .of()
                    .food(PastelFoodComponents.MERMAIDS_POPCORN),
                "item.pastel.mermaids_popcorn.tooltip"
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> LE_FISHE_AU_CHOCOLAT = register(
        item(
            "le_fishe_au_chocolat",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.LE_FISHE_AU_CHOCOLAT)
            ),
            InkColors.PINK
        )
    );
//	public static final Item STUFFED_PETALS = register((item("stuffed_petals", () -> new Item(IS.of().food
//	(PastelFoodComponents.STUFFED_PETALS)), InkColors.PINK)));
//	public static final Item PASTICHE = register((item("pastiche", () -> new Item(IS.of().food
//	(PastelFoodComponents.PASTICHE)), InkColors.PINK)));
//	public static final Item VITTORIAS_ROAST = register((item("vittorias_roast", () -> new Item(IS.of().food
//	(PastelFoodComponents.VITTORIAS_ROAST)), InkColors.PINK)));

    //	public static final Item VITTORIAS_ROAST = register((item("vittorias_roast", () -> new Item(IS.of().food
    //	(PastelFoodComponents.VITTORIAS_ROAST)), InkColors.PINK)));

    public static final DeferredItem<Item> INFUSED_BEVERAGE = register(
        item(
            "infused_beverage",
            () -> new BeverageItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.BEVERAGE)
                    .craftRemainder(GLASS_BOTTLE)
                    .component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                    .component(
                        PastelDataComponentTypes.INFUSED_BEVERAGE,
                        InfusedBeverageComponent.DEFAULT
                    )
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> SUSPICIOUS_BREW = register(
        item(
            "suspicious_brew",
            () -> new SuspiciousBrewItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.BEVERAGE)
                    .craftRemainder(GLASS_BOTTLE)
            ),
            InkColors.LIME
        )
    );

    public static final DeferredItem<Item> REPRISE = register(
        item(
            "reprise",
            () -> new RepriseItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.BEVERAGE)
                    .craftRemainder(GLASS_BOTTLE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> PURE_ALCOHOL = register(
        burnable(
            item(
                "pure_alcohol",
                () -> new DrinkItem(
                    IS
                        .of(16, Rarity.UNCOMMON)
                        .food(PastelFoodComponents.PURE_ALCOHOL)
                        .craftRemainder(GLASS_BOTTLE)
                ),
                InkColors.WHITE
            ),
            16000
        )
    );

    public static final DeferredItem<Item> JADE_WINE = register(
        item(
            "jade_wine",
            () -> new JadeWineItem(
                IS
                    .of(16, Rarity.UNCOMMON)
                    .food(PastelFoodComponents.BEVERAGE)
                    .craftRemainder(GLASS_BOTTLE)
            ),
            InkColors.LIME
        )
    );

    public static final DeferredItem<Item> CHRYSOCOLLA = register(
        burnable(
            item(
                "chrysocolla",
                () -> new DrinkItem(
                    IS
                        .of(16, Rarity.UNCOMMON)
                        .food(PastelFoodComponents.PURE_ALCOHOL)
                        .craftRemainder(GLASS_BOTTLE)
                ),
                InkColors.LIME
            ),
            16000
        )
    );

    public static final DeferredItem<Item> HONEY_PASTRY = register(
        item(
            "honey_pastry",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.HONEY_PASTRY)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> LUCKY_ROLL = register(
        item(
            "lucky_roll",
            () -> new Item(
                IS
                    .of(16)
                    .food(PastelFoodComponents.LUCKY_ROLL)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> TRIPLE_MEAT_POT_PIE = register(
        item(
            "triple_meat_pot_pie",
            () -> new Item(
                IS
                    .of(8)
                    .food(PastelFoodComponents.TRIPLE_MEAT_POT_PIE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> GLISTERING_JELLY_TEA = register(
        item(
            "glistering_jelly_tea",
            () -> new DrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.GLISTERING_JELLY_TEA)
                    .craftRemainder(GLASS_BOTTLE)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(
                            PastelFoodComponents.GLISTERING_JELLY_TEA_SCONE_BONUS
                        )
                    )
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> FREIGEIST = register(
        item(
            "freigeist",
            () -> new FreigeistItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.FREIGEIST)
                    .craftRemainder(GLASS_BOTTLE)
            ),
            InkColors.RED
        )
    );

    public static final DeferredItem<Item> DIVINATION_HEART = register(
        item(
            "divination_heart",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.DIVINATION_HEART)
            ),
            InkColors.RED
        )
    );

    public static final DeferredItem<Item> STAR_CANDY = register(
        item(
            "star_candy",
            () -> new StarCandyItem(
                IS
                    .of(Rarity.UNCOMMON)
                    .food(PastelFoodComponents.STAR_CANDY)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> ENCHANTED_STAR_CANDY = register(
        item(
            "enchanted_star_candy",
            () -> new EnchantedStarCandyItem(
                IS
                    .of(Rarity.UNCOMMON)
                    .food(PastelFoodComponents.ENCHANTED_STAR_CANDY)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> ENCHANTED_GOLDEN_CARROT = register(
        item(
            "enchanted_golden_carrot",
            () -> new ItemWithGlint(
                IS
                    .of(Rarity.EPIC)
                    .food(PastelFoodComponents.ENCHANTED_GOLDEN_CARROT)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> JARAMEL = register(
        item(
            "jaramel",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.JARAMEL)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> JARAMEL_TART = register(
        item(
            "jaramel_tart",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.JARAMEL_TART)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> SALTED_JARAMEL_TART = register(
        item(
            "salted_jaramel_tart",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.SALTED_JARAMEL_TART)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> ASHEN_TART = register(
        item(
            "ashen_tart",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.ASHEN_TART)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> WEEPING_TART = register(
        item(
            "weeping_tart",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.WEEPING_TART)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> WHISPY_TART = register(
        item(
            "whispy_tart",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.WHISPY_TART)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> PUFF_TART = register(
        item(
            "puff_tart",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.PUFF_TART)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> JARAMEL_TRIFLE = register(
        item(
            "jaramel_trifle",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.JARAMEL_TRIFLE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> SALTED_JARAMEL_TRIFLE = register(
        item(
            "salted_jaramel_trifle",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.SALTED_JARAMEL_TRIFLE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MONSTER_TRIFLE = register(
        item(
            "monster_trifle",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MONSTER_TRIFLE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> DEMON_TRIFLE = register(
        item(
            "demon_trifle",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.DEMON_TRIFLE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MYCEYLON = register(
        item("myceylon", () -> new Item(IS.of()), InkColors.PINK)
    );

    public static final DeferredItem<Item> MYCEYLON_APPLE_PIE = register(
        item(
            "myceylon_apple_pie",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MYCEYLON_APPLE_PIE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MYCEYLON_PUMPKIN_PIE = register(
        item(
            "myceylon_pumpkin_pie",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MYCEYLON_PUMPKIN_PIE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MYCEYLON_COOKIE = register(
        item(
            "myceylon_cookie",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MYCEYLON_COOKIE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> ALOE_LEAF = register(
        item(
            "aloe_leaf",
            () -> new ItemNameBlockItem(
                PastelBlocks.ALOE.get(),
                IS
                    .of()
                    .food(PastelFoodComponents.ALOE_LEAF)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> SAWBLADE_HOLLY_BERRY = register(
        item(
            "sawblade_holly_berry",
            () -> new ItemNameBlockItem(
                PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                IS
                    .of()
                    .food(Foods.SWEET_BERRIES)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> PRICKLY_BAYLEAF = register(
        item(
            "prickly_bayleaf",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.PRICKLY_BAYLEAF)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> TRIPLE_MEAT_POT_STEW = register(
        item(
            "triple_meat_pot_stew",
            () -> new StackableStewItem(
                IS
                    .of(8)
                    .food(PastelFoodComponents.TRIPLE_MEAT_POT_STEW)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> WYRMSCALE_JELLY = register(
        item(
            "wyrmscale_jelly",
            () -> new StackableStewItem(
                IS
                    .of(8)
                    .food(PastelFoodComponents.WYRMSCALE_JELLY)
            ),
            InkColors.GRAY
        )
    );

    public static final DeferredItem<Item> DOOMBLOOM_SEED = register(
        item(
            "doombloom_seed",
            () -> new ItemNameBlockItem(
                PastelBlocks.DOOMBLOOM.get(),
                IS
                    .of()
                    .fireResistant()
            ),
            InkColors.BLACK
        )
    );

    public static final DeferredItem<Item> GLISTERING_MELON_SEEDS = register(
        item(
            "glistering_melon_seeds",
            () -> new ItemNameBlockItem(PastelBlocks.GLISTERING_MELON_STEM.get(), IS.of()),
            InkColors.LIME
        )
    );

    public static final DeferredItem<Item> AMARANTH_GRAINS = register(
        item("amaranth_grains", () -> new ItemNameBlockItem(PastelBlocks.AMARANTH.get(), IS.of()), InkColors.LIME)
    );

    // Cookbooks
    public static final DeferredItem<Item> MELOCHITES_COOKBOOK_VOL_1 = register(
        item(
            "melochites_cookbook_vol_1",
            () -> new CookbookItem(
                IS
                    .of()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON),
                GuidebookItem
                    .addressOf(
                        GuidebookItem.CUISINE_CATEGORY_ID,
                        locate("cuisine/cookbooks/melochites_cookbook_vol_1")
                    )
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> MELOCHITES_COOKBOOK_VOL_2 = register(
        item(
            "melochites_cookbook_vol_2",
            () -> new CookbookItem(
                IS
                    .of()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON),
                GuidebookItem
                    .addressOf(
                        GuidebookItem.CUISINE_CATEGORY_ID,
                        locate("cuisine/cookbooks/melochites_cookbook_vol_2")
                    )
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> IMBRIFER_COOKBOOK = register(
        item(
            "imbrifer_cookbook",
            () -> new CookbookItem(
                IS
                    .of()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON),
                GuidebookItem
                    .addressOf(GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/imbrifer_cookbook"))
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> IMPERIAL_COOKBOOK = register(
        item(
            "imperial_cookbook",
            () -> new CookbookItem(
                IS
                    .of()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON),
                GuidebookItem
                    .addressOf(GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/imperial_cookbook"))
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> BREWERS_HANDBOOK = register(
        item(
            "brewers_handbook",
            () -> new CookbookItem(
                IS
                    .of()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON),
                GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, locate("cuisine/cookbooks/brewers_handbook"))
            ),
            InkColors.PURPLE
        )
    );

    //public static final Item VARIA_COOKBOOK = register((item("varia_cookbook", () -> new CookbookItem(IS.of()
    // .maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, PastelCommon
    // .locate("cuisine/cookbooks/varia_cookbook"))), InkColors.PURPLE)));
    public static final DeferredItem<Item> POISONERS_HANDBOOK = register(
        item(
            "poisoners_handbook",
            () -> new CookbookItem(
                IS
                    .of()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC),
                GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, locate("dimension/lore/poisoners_handbook")),
                PastelMobEffects.ETERNAL_SLUMBER_COLOR
            ),
            InkColors.PURPLE
        )
    );

    public static final DeferredItem<Item> AQUA_REGIA = register(
        item(
            "aqua_regia",
            () -> new JadeWineItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.AQUA_REGIA)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> BAGNUN = register(
        item(
            "bagnun",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.BAGNUN)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> BANYASH = register(
        item(
            "banyash",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.BANYASH)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> BERLINER = register(
        item(
            "berliner",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.BERLINER)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> BRISTLE_MEAD = register(
        item(
            "bristle_mead",
            () -> new BeverageItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.BEVERAGE)
                    .component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> CHAUVE_SOURIS_AU_VIN = register(
        item(
            "chauve_souris_au_vin",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.CHAUVE_SOURIS_AU_VIN)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> CRAWFISH = register(
        item(
            "crawfish",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.CRAWFISH)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> CRAWFISH_COCKTAIL = register(
        item(
            "crawfish_cocktail",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.CRAWFISH_COCKTAIL)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> CREAM_PASTRY = register(
        item(
            "cream_pastry",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.CREAM_PASTRY)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> FADED_KOI = register(
        item(
            "faded_koi",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.FADED_KOI)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> FISHCAKE = register(
        item(
            "fishcake",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.FISHCAKE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> LIZARD_MEAT = register(
        item(
            "lizard_meat",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.LIZARD_MEAT)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> COOKED_LIZARD_MEAT = register(
        item(
            "cooked_lizard_meat",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.COOKED_LIZARD_MEAT)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> GOLDEN_BRISTLE_TEA = register(
        item(
            "golden_bristle_tea",
            () -> new DrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.GOLDEN_BRISTLE_TEA)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(
                            PastelFoodComponents.GOLDEN_BRISTLE_TEA_SCONE_BONUS
                        )
                    )
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> HARE_ROAST = register(
        item(
            "hare_roast",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.HARE_ROAST)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> JUNKET = register(
        item(
            "junket",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.JUNKET)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> KOI = register(
        item(
            "koi",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.KOI)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MEATLOAF = register(
        item(
            "meatloaf",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MEATLOAF)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MEATLOAF_SANDWICH = register(
        item(
            "meatloaf_sandwich",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MEATLOAF_SANDWICH)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MELLOW_SHALLOT_SOUP = register(
        item(
            "mellow_shallot_soup",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.MELLOW_SHALLOT_SOUP)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> MORCHELLA = register(
        item(
            "morchella",
            () -> new BeverageItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.BEVERAGE)
                    .component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> NECTERED_VIOGNIER = register(
        item(
            "nectered_viognier",
            () -> new JadeWineItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.NECTERED_VIOGNIER)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> PEACHES_FLAMBE = register(
        item(
            "peaches_flambe",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.PEACHES_FLAMBE)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> PEACH_CREAM = register(
        item(
            "peach_cream",
            () -> new DrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.PEACH_CREAM)
                    .component(
                        PastelDataComponentTypes.PAIRED_FOOD_COMPONENT,
                        teaSconeBonus(PastelFoodComponents.PEACH_CREAM_SCONE_BONUS)
                    )
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> PEACH_JAM = register(
        item(
            "peach_jam",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.PEACH_JAM)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> RABBIT_CREAM_PIE = register(
        item(
            "rabbit_cream_pie",
            () -> new ItemWithTooltip(
                IS
                    .of()
                    .food(PastelFoodComponents.RABBIT_CREAM_PIE),
                "item.pastel.rabbit_cream_pie.tooltip"
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> SEDATIVES = register(
        item(
            "sedatives",
            () -> new SedativesItem(
                IS
                    .of()
                    .food(PastelFoodComponents.SEDATIVES),
                "item.pastel.sedatives.tooltip"
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> SLUSHSLIDE = register(
        item(
            "slushslide",
            () -> new DrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.SLUSHSLIDE),
                "item.pastel.slushslide.tooltip"
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> SURSTROMMING = register(
        item(
            "surstromming",
            () -> new Item(
                IS
                    .of()
                    .food(PastelFoodComponents.SURSTROMMING)
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> EVERNECTAR = register(
        item(
            "evernectar",
            () -> new EvernectarItem(
                IS
                    .of(1, Rarity.EPIC)
                    .food(PastelFoodComponents.EVERNECTAR)
                    .craftRemainder(GLASS_BOTTLE),
                "item.pastel.evernectar.tooltip"
            ),
            InkColors.LIME
        )
    );

    // Banner Patterns
    public static final DeferredItem<Item> COLOR_THEORY_BANNER_PATTERN = register(
        item(
            "color_theory_banner_pattern",
            () -> new BannerPatternItem(PastelBannerPatternTags.COLOR_THEORY_TAG, IS.of(1, Rarity.UNCOMMON)),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<Item> AMETHYST_SHARD_BANNER_PATTERN = register(
        item(
            "amethyst_shard_banner_pattern",
            () -> new BannerPatternItem(PastelBannerPatternTags.AMETHYST_SHARD_TAG, IS.of(1)),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<Item> AMETHYST_CLUSTER_BANNER_PATTERN = register(
        item(
            "amethyst_cluster_banner_pattern",
            () -> new BannerPatternItem(PastelBannerPatternTags.AMETHYST_CLUSTER_TAG, IS.of(1)),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<Item> ASTROLOGER_BANNER_PATTERN = register(
        item(
            "astrologer_banner_pattern",
            () -> new BannerPatternItem(PastelBannerPatternTags.ASTROLOGER_TAG, IS.of(1, Rarity.UNCOMMON)),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<Item> VELVET_ASTROLOGER_BANNER_PATTERN = register(
        item(
            "velvet_astrologer_banner_pattern",
            () -> new BannerPatternItem(PastelBannerPatternTags.VELVET_ASTROLOGER_TAG, IS.of(1, Rarity.UNCOMMON)),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<Item> POISONBLOOM_BANNER_PATTERN = register(
        item(
            "poisonbloom_banner_pattern",
            () -> new BannerPatternItem(PastelBannerPatternTags.POISONBLOOM_TAG, IS.of(1, Rarity.RARE)),
            InkColors.LIGHT_BLUE
        )
    );

    public static final DeferredItem<Item> DEEP_LIGHT_BANNER_PATTERN = register(
        item(
            "deep_light_banner_pattern",
            () -> new BannerPatternItem(PastelBannerPatternTags.DEEP_LIGHT_TAG, IS.of(1, Rarity.RARE)),
            InkColors.LIGHT_BLUE
        )
    );

    // Spawning items
    public static final DeferredItem<Item> BUCKET_OF_ERASER = register(
        item(
            "bucket_of_eraser",
            () -> new EmptyFluidEntityBucketItem(
                PastelEntityTypes.ERASER.get(),
                Fluids.EMPTY,
                SoundEvents.BUCKET_EMPTY,
                IS.of()
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> EGG_LAYING_WOOLY_PIG_SPAWN_EGG = register(
        item(
            "egg_laying_wooly_pig_spawn_egg",
            () -> new SpawnEggItem(PastelEntityTypes.EGG_LAYING_WOOLY_PIG.get(), 0x3a2c38, 0xfff2e0, IS.of()),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> PRESERVATION_TURRET_SPAWN_EGG = register(
        item(
            "preservation_turret_spawn_egg",
            () -> new SpawnEggItem(PastelEntityTypes.PRESERVATION_TURRET.get(), 0xf3f6f8, 0xc8c5be, IS.of()),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> KINDLING_SPAWN_EGG = register(
        item(
            "kindling_spawn_egg",
            () -> new SpawnEggItem(PastelEntityTypes.KINDLING.get(), 0xda4261, 0xffd452, IS.of()),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> LIZARD_SPAWN_EGG = register(
        item(
            "lurking_lizard_spawn_egg",
            () -> new SpawnEggItem(PastelEntityTypes.LIZARD.get(), 0x896459, 0x503a40, IS.of()),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> ERASER_SPAWN_EGG = register(
        item(
            "eraser_spawn_egg",
            () -> new SpawnEggItem(PastelEntityTypes.ERASER.get(), 0x200d29, 0xc83e93, IS.of()),
            InkColors.WHITE
        )
    );

    // Magical Tools
    public static final DeferredItem<Item> BAG_OF_HOLDING = register(
        item("bag_of_holding", () -> new BagOfHoldingItem(IS.of(1)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> WIRE_HOOK = register(
        item("wire_hook", () -> new WireHookItem(IS.of(1)), InkColors.LIGHT_BLUE)
    );

    public static final DeferredItem<Item> RADIANCE_STAFF = register(
        item("radiance_staff", () -> new RadianceStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)
    );

    public static final DeferredItem<NaturesStaffItem> NATURES_STAFF = register(
        item("natures_staff", () -> new NaturesStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIME)
    );

    public static final DeferredItem<Item> STAFF_OF_REMEMBRANCE = register(
        item("staff_of_remembrance", () -> new StaffOfRemembranceItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIME)
    );

    public static final DeferredItem<Item> CONSTRUCTORS_STAFF = register(
        item("constructors_staff", () -> new ConstructorsStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.CYAN)
    );

    public static final DeferredItem<Item> FLOWING_STAFF = register(
        item("flowing_staff", () -> new FlowingStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.CYAN)
    );

    public static final DeferredItem<Item> EXCHANGING_STAFF = register(
        item("exchanging_staff", () -> new ExchangeStaffItem(IS.of(1, Rarity.UNCOMMON)), InkColors.CYAN)
    );

    public static final DeferredItem<Item> BLOCK_FLOODER = register(
        item("block_flooder", () -> new BlockFlooderItem(IS.of(Rarity.UNCOMMON)), InkColors.LIGHT_GRAY)
    );

    public static final DeferredItem<Item> PIPE_BOMB = register(
        item("pipe_bomb", () -> new PipeBombItem(IS.of(1)), InkColors.ORANGE)
    );

    public static final DeferredItem<EnderSpliceItem> ENDER_SPLICE = register(
        item("ender_splice", () -> new EnderSpliceItem(IS.of(16, Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> ENDER_CANVAS = register(
        item("ender_canvas", () -> new EnderCanvasItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> PERTURBED_EYE = register(
        item("perturbed_eye", () -> new PerturbedEyeItem(IS.of(Rarity.UNCOMMON)), InkColors.RED)
    );

    public static final DeferredItem<Item> CRESCENT_CLOCK = register(
        item(
            "crescent_clock",
            () -> new ItemWithTooltip(IS.of(1), "item.pastel.crescent_clock.tooltip"),
            InkColors.MAGENTA
        )
    );

    public static final DeferredItem<Item> PRIMORDIAL_LIGHTER = register(
        item("primordial_lighter", () -> new PrimordialLighterItem(IS.of(1)), InkColors.ORANGE)
    );

    public static final DeferredItem<Item> NIGHT_SALTS = register(
        item("night_salts", () -> new NightSaltsItem(IS.of(16)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> SOOTHING_BOUQUET = register(
        item("soothing_bouquet", () -> new SoothingBouquetItem(IS.of(1, Rarity.RARE)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> CONCEALING_OILS = register(
        item("concealing_oils", () -> new ConcealingOilsItem(IS.of(1)), InkColors.BLACK)
    );

    public static final DeferredItem<Item> BITTER_OILS = register(
        item(
            "bitter_oils",
            () -> new DrinkItem(
                IS
                    .of(16)
                    .food(PastelFoodComponents.BITTER_OILS)
            ),
            InkColors.BLUE
        )
    );

    public static final DeferredItem<Item> INCANDESCENT_ESSENCE = register(
        burnable(
            item(
                "incandescent_essence",
                () -> new Item(
                    IS
                        .of()
                        .fireResistant()
                ),
                InkColors.ORANGE
            ),
            2400
        )
    );

    public static final DeferredItem<Item> FROSTBITE_ESSENCE = register(
        item("frostbite_essence", () -> new Item(IS.of()), InkColors.LIGHT_BLUE)
    );

    public static final DeferredItem<Item> MOONSTONE_CORE = register(
        item("moonstone_core", () -> new Item(IS.of(16, Rarity.RARE)), InkColors.WHITE)
    );

    // Music discs
    public static final DeferredItem<Item> MUSIC_DISC_DISCOVERY = register(
        item(
            "music_disc_discovery",
            () -> new Item(
                IS
                    .of(1, Rarity.RARE)
                    .jukeboxPlayable(PastelJukeboxSongs.DISCOVERY)
            ),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<Item> MUSIC_DISC_CREDITS = register(
        item(
            "music_disc_credits",
            () -> new Item(
                IS
                    .of(1, Rarity.RARE)
                    .jukeboxPlayable(PastelJukeboxSongs.CREDITS)
            ),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<Item> MUSIC_DISC_DIVINITY = register(
        item(
            "music_disc_divinity",
            () -> new Item(
                IS
                    .of(1, Rarity.RARE)
                    .jukeboxPlayable(PastelJukeboxSongs.DIVINITY)
            ),
            InkColors.GREEN
        )
    );

    public static final DeferredItem<Item> MUSIC_DISC_MEMORIAL = register(
        item(
            "music_disc_memorial",
            () -> new Item(
                IS
                    .of(1, Rarity.RARE)
                    .jukeboxPlayable(PastelJukeboxSongs.MEMORIAL)
            ),
            InkColors.GREEN
        )
    );

    // Item Frames
    public static final DeferredItem<Item> PHANTOM_FRAME = register(
        item(
            "phantom_frame",
            () -> new PhantomFrameItem(PastelEntityTypes.PHANTOM_FRAME.get(), IS.of()),
            InkColors.YELLOW
        )
    );

    public static final DeferredItem<Item> GLOW_PHANTOM_FRAME = register(
        item(
            "glow_phantom_frame",
            () -> new PhantomGlowFrameItem(PastelEntityTypes.GLOW_PHANTOM_FRAME.get(), IS.of()),
            InkColors.YELLOW
        )
    );

    // Specialty Magical Tools
    public static final DeferredItem<KnowledgeGemItem> KNOWLEDGE_GEM = register(
        item("knowledge_gem", () -> new KnowledgeGemItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> CELESTIAL_POCKETWATCH = register(
        item(
            "celestial_pocketwatch",
            () -> new CelestialPocketWatchItem(IS.of(1, Rarity.UNCOMMON)),
            InkColors.MAGENTA
        )
    );

    public static final DeferredItem<Item> ARTISANS_ATLAS = register(
        item("artisans_atlas", () -> new ArtisansAtlasItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> GILDED_BOOK = register(
        item("gilded_book", () -> new GildedBookItem(IS.of(Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> ENCHANTMENT_CANVAS = register(
        item("enchantment_canvas", () -> new EnchantmentCanvasItem(IS.of(16, Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> EVERPROMISE_RIBBON = register(
        item("everpromise_ribbon", () -> new EverpromiseRibbonItem(IS.of()), InkColors.PINK)
    );

    // Lore
    public static final DeferredItem<Item> MYSTERIOUS_LOCKET = register(
        item("mysterious_locket", () -> new MysteriousLocketItem(IS.of(1, Rarity.UNCOMMON)), InkColors.GRAY)
    );

    public static final DeferredItem<Item> MYSTERIOUS_COMPASS = register(
        item("mysterious_compass", () -> new MysteriousCompassItem(IS.of(1, Rarity.RARE)), InkColors.GRAY)
    );

    // Trinkets
    public static final DeferredItem<Item> FANCIFUL_TUFF_RING = register(
        item("fanciful_tuff_ring", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> FANCIFUL_BELT = register(
        item("fanciful_belt", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> FANCIFUL_PENDANT = register(
        item("fanciful_pendant", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> FANCIFUL_CIRCLET = register(
        item("fanciful_circlet", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> FANCIFUL_GLOVES = register(
        item("fanciful_gloves", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> FANCIFUL_BISMUTH_RING = register(
        item("fanciful_bismuth_ring", () -> new Item(IS.of(16, Rarity.UNCOMMON)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> PRISCILLENT_SPECTACLES = register(
        item(
            "priscillent_spectacles",
            () -> new PriscillentSpectaclesItem(IS.of(1, Rarity.UNCOMMON)),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> JEOPARDANT = register(
        item("jeopardant", () -> new AttackRingItem(IS.of(1, Rarity.UNCOMMON)), InkColors.RED)
    );

    public static final DeferredItem<SevenLeagueBootsItem> SEVEN_LEAGUE_BOOTS = register(
        item("seven_league_boots", () -> new SevenLeagueBootsItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> COTTON_CLOUD_BOOTS = register(
        item("cotton_cloud_boots", () -> new CottonCloudBootsItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> RADIANCE_PIN = register(
        item("radiance_pin", () -> new RadiancePinItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BLUE)
    );

    public static final DeferredItem<Item> TOTEM_PENDANT = register(
        item("totem_pendant", () -> new TotemPendantItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BLUE)
    );

    public static final DeferredItem<TakeOffBeltItem> TAKEOFF_BELT = register(
        item("takeoff_belt", () -> new TakeOffBeltItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> AZURE_DIKE_BELT = register(
        item(
            "azure_dike_belt",
            () -> new AzureDikeBeltItem(IS.of(1, Rarity.UNCOMMON), PastelAdvancements.Unlocks.Trinkets.AZURE_DIKE_BELT),
            InkColors.BLUE
        )
    );

    public static final DeferredItem<Item> AZURE_DIKE_RING = register(
        item(
            "azure_dike_ring",
            () -> new AzureDikeRingItem(IS.of(1, Rarity.UNCOMMON), PastelAdvancements.Unlocks.Trinkets.AZURE_DIKE_RING),
            InkColors.BLUE
        )
    );

    public static final DeferredItem<Item> AZURESQUE_DIKE_CORE = register(
        item(
            "azuresque_dike_core",
            () -> new AzureDikeCoreItem(IS.of(1, Rarity.EPIC), PastelAdvancements.Unlocks.Trinkets.AZURESQUE_DIKE_CORE),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<InkDrainTrinketItem> SHIELDGRASP_AMULET = register(
        item(
            "shieldgrasp_amulet",
            () -> new AzureDikeAmuletItem(
                IS.of(1, Rarity.UNCOMMON),
                PastelAdvancements.Unlocks.Trinkets.SHIELDGRASP_AMULET
            ),
            InkColors.BLUE
        )
    );

    public static final DeferredItem<Item> RING_OF_CONSUMPTION = register(
        item("ring_of_consumption", () -> new ConsumptionRingItem(IS.of(1, Rarity.UNCOMMON)), InkColors.RED)
    );

    public static final DeferredItem<InkDrainTrinketItem> HEARTSINGERS_REWARD = register(
        item("heartsingers_reward", () -> new ExtraHealthRingItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PINK)
    );

    public static final DeferredItem<InkDrainTrinketItem> GLOVES_OF_DAWNS_GRASP = register(
        item("gloves_of_dawns_grasp", () -> new ExtraReachGlovesItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)
    );

    public static final DeferredItem<InkDrainTrinketItem> RING_OF_PURSUIT = register(
        item("ring_of_pursuit", () -> new ExtraMiningSpeedRingItem(IS.of(1, Rarity.UNCOMMON)), InkColors.MAGENTA)
    );

    public static final DeferredItem<InkDrainTrinketItem> RING_OF_DENSER_STEPS = register(
        item("ring_of_denser_steps", () -> new RingOfDenserStepsItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BROWN)
    );

    public static final DeferredItem<InkDrainTrinketItem> RING_OF_AETHERIAL_GRACE = register(
        item("ring_of_aetherial_grace", () -> new RingOfAerialGraceItem(IS.of(1, Rarity.UNCOMMON)), InkColors.WHITE)
    );

    public static final DeferredItem<InkDrainTrinketItem> LAURELS_OF_SERENITY = register(
        item("laurels_of_serenity", () -> new LaurelsOfSerenityItem(IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE)
    );

    // Ink storage
    public static final DeferredItem<InkFlaskItem> INK_FLASK = register(
        item("ink_flask", () -> new InkFlaskItem(IS.of(1), 64 * 64 * 100), InkColors.WHITE)
    );

    // 64 stacks of pigments (1 pigment => 100 energy)
    public static final DeferredItem<InkAssortmentItem> INK_ASSORTMENT = register(
        item("ink_assortment", () -> new InkAssortmentItem(IS.of(1), 64 * 100), InkColors.WHITE)
    );

    public static final DeferredItem<PigmentPaletteItem> PIGMENT_PALETTE = register(
        item(
            "pigment_palette",
            () -> new PigmentPaletteItem(IS.of(1, Rarity.UNCOMMON), 64 * 64 * 100),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<ArtistsPaletteItem> ARTISTS_PALETTE = register(
        item(
            "artists_palette",
            () -> new ArtistsPaletteItem(IS.of(1, Rarity.UNCOMMON), 64 * 64 * 64 * 64 * 100),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<CreativeInkAssortmentItem> CREATIVE_INK_ASSORTMENT = register(
        item("creative_ink_assortment", () -> new CreativeInkAssortmentItem(IS.of(1, Rarity.EPIC)), InkColors.WHITE)
    );

    public static final DeferredItem<GleamingPinItem> GLEAMING_PIN = register(
        item("gleaming_pin", () -> new GleamingPinItem(IS.of(1, Rarity.UNCOMMON)), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> LESSER_POTION_PENDANT = register(
        item(
            "lesser_potion_pendant",
            () -> new PotionPendantItem(
                IS.of(1, Rarity.UNCOMMON),
                1,
                CONFIG.MaxLevelForEffectsInLesserPotionPendant - 1,
                PastelAdvancements.Unlocks.Trinkets.LESSER_POTION_PENDANT
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> GREATER_POTION_PENDANT = register(
        item(
            "greater_potion_pendant",
            () -> new PotionPendantItem(
                IS.of(1, Rarity.UNCOMMON),
                3,
                CONFIG.MaxLevelForEffectsInGreaterPotionPendant - 1,
                PastelAdvancements.Unlocks.Trinkets.GREATER_POTION_PENDANT
            ),
            InkColors.PINK
        )
    );

    public static final DeferredItem<Item> ASHEN_CIRCLET = register(
        item(
            "ashen_circlet",
            () -> new AshenCircletItem(
                IS
                    .of(1, Rarity.UNCOMMON)
                    .fireResistant()
            ),
            InkColors.ORANGE
        )
    );

    public static final DeferredItem<Item> WEEPING_CIRCLET = register(
        item("weeping_circlet", () -> new WeepingCircletItem(IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_BLUE)
    );

    public static final DeferredItem<Item> PUFF_CIRCLET = register(
        item(
            "puff_circlet",
            () -> new PuffCircletItem(IS.of(1, Rarity.UNCOMMON), PastelAdvancements.Unlocks.Trinkets.PUFF_CIRCLET),
            InkColors.WHITE
        )
    );

    public static final DeferredItem<Item> WHISPY_CIRCLET = register(
        item("whispy_circlet", () -> new WhispyCircletItem(IS.of(1, Rarity.UNCOMMON)), InkColors.BROWN)
    );

    public static final DeferredItem<Item> CIRCLET_OF_ARROGANCE = register(
        item("circlet_of_arrogance", () -> new CircletOfArroganceItem(IS.of(1, Rarity.UNCOMMON)), InkColors.RED)
    );

    public static final DeferredItem<Item> NEAT_RING = register(
        item("neat_ring", () -> new NeatRingItem(IS.of(1, Rarity.EPIC)), InkColors.GREEN)
    );

    public static final DeferredItem<Item> AETHER_GRACED_NECTAR_GLOVES = register(
        item(
            "aether_graced_nectar_gloves",
            () -> new AetherGracedNectarGlovesItem(
                IS.of(1, Rarity.EPIC),
                PastelAdvancements.Lategame.COLLECT_AETHER_GRACED_NECTAR_GLOVES
            ),
            InkColors.PURPLE
        )
    );

    // Pure Clusters
    public static final DeferredItem<Item> PURE_COAL = register(
        burnable(item("pure_coal", () -> new Item(IS.of()), InkColors.BROWN), 3200)
    );

    public static final DeferredItem<Item> PURE_IRON = register(
        item("pure_iron", () -> new Item(IS.of()), InkColors.BROWN)
    );

    public static final DeferredItem<Item> PURE_GOLD = register(
        item("pure_gold", () -> new Item(IS.of()), InkColors.BROWN)
    );

    public static final DeferredItem<Item> PURE_DIAMOND = register(
        item("pure_diamond", () -> new Item(IS.of()), InkColors.CYAN)
    );

    public static final DeferredItem<Item> PURE_EMERALD = register(
        item("pure_emerald", () -> new Item(IS.of()), InkColors.CYAN)
    );

    public static final DeferredItem<Item> PURE_REDSTONE = register(
        item("pure_redstone", () -> new Item(IS.of()), InkColors.RED)
    );

    public static final DeferredItem<Item> PURE_LAPIS = register(
        item("pure_lapis", () -> new Item(IS.of()), InkColors.PURPLE)
    );

    public static final DeferredItem<Item> PURE_COPPER = register(
        item("pure_copper", () -> new Item(IS.of()), InkColors.BROWN)
    );

    public static final DeferredItem<Item> PURE_QUARTZ = register(
        item("pure_quartz", () -> new Item(IS.of()), InkColors.BROWN)
    );

    public static final DeferredItem<Item> PURE_GLOWSTONE = register(
        item("pure_glowstone", () -> new Item(IS.of()), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> PURE_PRISMARINE = register(
        item("pure_prismarine", () -> new Item(IS.of()), InkColors.CYAN)
    );

    public static final DeferredItem<Item> PURE_NETHERITE_SCRAP = register(
        item(
            "pure_netherite_scrap",
            () -> new Item(
                IS
                    .of()
                    .fireResistant()
            ),
            InkColors.BROWN
        )
    );

    public static final DeferredItem<Item> PURE_ECHO = register(
        item("pure_echo", () -> new Item(IS.of()), InkColors.BROWN)
    );

    //Technical Items
    public static final DeferredItem<Item> CONNECTION_NODE_CRYSTAL = register(
        item("connection_node_crystal", () -> new Item(IS.of()), InkColors.LIGHT_GRAY)
    );

    public static final DeferredItem<Item> PROVIDER_NODE_CRYSTAL = register(
        item("provider_node_crystal", () -> new Item(IS.of()), InkColors.MAGENTA)
    );

    public static final DeferredItem<Item> SENDER_NODE_CRYSTAL = register(
        item("sender_node_crystal", () -> new Item(IS.of()), InkColors.YELLOW)
    );

    public static final DeferredItem<Item> STORAGE_NODE_CRYSTAL = register(
        item("storage_node_crystal", () -> new Item(IS.of()), InkColors.CYAN)
    );

    public static final DeferredItem<Item> BUFFER_NODE_CRYSTAL = register(
        item("buffer_node_crystal", () -> new Item(IS.of()), InkColors.GREEN)
    );

    public static final DeferredItem<Item> GATHER_NODE_CRYSTAL = register(
        item("gather_node_crystal", () -> new Item(IS.of()), InkColors.BLACK)
    );

    public static final DeferredItem<Item> EXTENDED_BUNDLE_ITEM = register(
        item("extended_bundle", () -> new ExtendedBundleItem(IS.of()), InkColors.BROWN)
    );

    public static <T extends Item> DeferredItem<T> register(ItemRegistrar<T> registrar) {
        return registrar.holder();
    }

    public static <T extends Item> ItemRegistrar<T> item(String name, Supplier<T> item, InkColor color) {
        return new ItemRegistrar<T>(name).withItem(item, color);
    }

    public static <T extends Item> ItemRegistrar<T> burnable(ItemRegistrar<T> registrar, int burnTicks) {
        return registrar.withBurnTime(burnTicks);
    }

    public static class ItemRegistrar<T extends Item> {

        private final ResourceLocation id;

        private boolean hasItem = false;

        @Nullable private DeferredItem<T> holder = null;

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

        @Nullable public DeferredItem<T> holder() {
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
            return new Item.Properties()
                .stacksTo(maxCount)
                .rarity(rarity);
        }

        public static Item.Properties active(int maxCount, Rarity rarity) {
            return new Item.Properties()
                .stacksTo(maxCount)
                .rarity(rarity)
                .component(PastelDataComponentTypes.ACTIVATED, Unit.INSTANCE);
        }
    }

    public static PairedFoodComponent teaSconeBonus(FoodProperties foodComponent) {
        return new PairedFoodComponent(PastelItems.SCONE, true, foodComponent);
    }

}
