package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.compat.ae2.AE2Compat;
import earth.terrarium.pastel.compat.create.CreateCompat;
import earth.terrarium.pastel.loot.conditions.NearMoonstoneLootCondition;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.*;

import java.util.*;

public class ResourceBlockLootTables extends BlockLootSubProvider {
    private final Holder<Enchantment> fortune, resonance;

    public ResourceBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
        resonance = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(PastelEnchantments.RESONANCE);
    }

    private static final List<Block> dropSelfBlocks = List
        .of(
            PastelBlocks.RADIATING_ENDER.get(),
            PastelBlocks.FROSTBITE_CRYSTAL.get(),
            PastelBlocks.BLAZING_CRYSTAL.get(),
            PastelBlocks.ROCK_CRYSTAL.get(),
            PastelBlocks.VEGETAL_BLOCK.get(),
            PastelBlocks.NEOLITH_BLOCK.get(),
            PastelBlocks.PALTAERIA_FLOATBLOCK.get(),
            PastelBlocks.STRATINE_FLOATBLOCK.get(),
            PastelBlocks.POLISHED_TOPAZ_BLOCK.get(),
            PastelBlocks.POLISHED_AMETHYST_BLOCK.get(),
            PastelBlocks.POLISHED_CITRINE_BLOCK.get(),
            PastelBlocks.POLISHED_ONYX_BLOCK.get(),
            PastelBlocks.POLISHED_MOONSTONE_BLOCK.get(),
            PastelBlocks.PURE_COAL_BLOCK.get(),
            PastelBlocks.PURE_IRON_BLOCK.get(),
            PastelBlocks.PURE_GOLD_BLOCK.get(),
            PastelBlocks.PURE_DIAMOND_BLOCK.get(),
            PastelBlocks.PURE_EMERALD_BLOCK.get(),
            PastelBlocks.PURE_REDSTONE_BLOCK.get(),
            PastelBlocks.PURE_LAPIS_BLOCK.get(),
            PastelBlocks.PURE_COPPER_BLOCK.get(),
            PastelBlocks.PURE_QUARTZ_BLOCK.get(),
            PastelBlocks.PURE_GLOWSTONE_BLOCK.get(),
            PastelBlocks.PURE_PRISMARINE_BLOCK.get(),
            PastelBlocks.PURE_NETHERITE_SCRAP_BLOCK.get(),
            PastelBlocks.PURE_ECHO_BLOCK.get(),
            PastelBlocks.STARDUST_BLOCK.get(),
            PastelBlocks.AZURITE_BLOCK.get(),
            PastelBlocks.MALACHITE_BLOCK.get(),
            PastelBlocks.BLOODSTONE_BLOCK.get(),
            PastelBlocks.TOPAZ_BLOCK.get(),
            PastelBlocks.CITRINE_BLOCK.get(),
            PastelBlocks.ONYX_BLOCK.get(),
            PastelBlocks.MOONSTONE_BLOCK.get(),
            PastelBlocks.TOPAZ_POWDER_BLOCK.get(),
            PastelBlocks.CITRINE_POWDER_BLOCK.get(),
            PastelBlocks.AMETHYST_POWDER_BLOCK.get(),
            PastelBlocks.ONYX_POWDER_BLOCK.get(),
            PastelBlocks.MOONSTONE_POWDER_BLOCK.get(),
            PastelBlocks.BISMUTH_BLOCK.get(),
            PastelBlocks.SHIMMERSTONE_BLOCK.get(),
            PastelBlocks.BEDROCK_DUST_BLOCK.get(),
            CreateCompat.PURE_ZINC_BLOCK.get()
        );

    private static final Map<Block, ItemLike> gemClusterBlocks = Map
        .ofEntries(
            Map.entry(PastelBlocks.TOPAZ_CLUSTER.get(), PastelItems.TOPAZ_SHARD),
            Map.entry(PastelBlocks.CITRINE_CLUSTER.get(), PastelItems.CITRINE_SHARD),
            Map.entry(PastelBlocks.ONYX_CLUSTER.get(), PastelItems.ONYX_SHARD),
            Map.entry(PastelBlocks.MOONSTONE_CLUSTER.get(), PastelItems.MOONSTONE_SHARD)
        );

    private final Map<Block, ItemLike> crystallarieumClusterBlocks = Map
        .ofEntries(
            Map.entry(PastelBlocks.COAL_CLUSTER.get(), PastelItems.PURE_COAL),
            Map.entry(PastelBlocks.IRON_CLUSTER.get(), PastelItems.PURE_IRON),
            Map.entry(PastelBlocks.GOLD_CLUSTER.get(), PastelItems.PURE_GOLD),
            Map.entry(PastelBlocks.DIAMOND_CLUSTER.get(), PastelItems.PURE_DIAMOND),
            Map.entry(PastelBlocks.EMERALD_CLUSTER.get(), PastelItems.PURE_EMERALD),
            Map.entry(PastelBlocks.REDSTONE_CLUSTER.get(), PastelItems.PURE_REDSTONE),
            Map.entry(PastelBlocks.LAPIS_CLUSTER.get(), PastelItems.PURE_LAPIS),
            Map.entry(PastelBlocks.COPPER_CLUSTER.get(), PastelItems.PURE_COPPER),
            Map.entry(PastelBlocks.QUARTZ_CLUSTER.get(), PastelItems.PURE_QUARTZ),
            Map.entry(PastelBlocks.NETHERITE_SCRAP_CLUSTER.get(), PastelItems.PURE_NETHERITE_SCRAP),
            Map.entry(PastelBlocks.ECHO_CLUSTER.get(), PastelItems.PURE_ECHO),
            Map.entry(PastelBlocks.GLOWSTONE_CLUSTER.get(), PastelItems.PURE_GLOWSTONE),
            Map.entry(PastelBlocks.PRISMARINE_CLUSTER.get(), PastelItems.PURE_PRISMARINE),
            Map.entry(PastelBlocks.AZURITE_CLUSTER.get(), PastelItems.PURE_AZURITE),
            Map.entry(PastelBlocks.MALACHITE_CLUSTER.get(), PastelItems.PURE_MALACHITE),
            Map.entry(PastelBlocks.BLOODSTONE_CLUSTER.get(), PastelItems.PURE_BLOODSTONE),
            Map.entry(CreateCompat.ZINC_CLUSTER.get(), CreateCompat.PURE_ZINC),
            Map
                .entry(
                    AE2Compat.FLUIX_CLUSTER.get(),
                    registries
                        .lookupOrThrow(Registries.ITEM)
                        .getOrThrow(
                            ResourceKey
                                .create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("ae2", "fluix_crystal"))
                        )
                        .value()
                )
        );

    private static final List<Block> silkRequiredBlocks = List
        .of(
            PastelBlocks.LARGE_TOPAZ_BUD.get(),
            PastelBlocks.MEDIUM_TOPAZ_BUD.get(),
            PastelBlocks.SMALL_TOPAZ_BUD.get(),
            PastelBlocks.LARGE_CITRINE_BUD.get(),
            PastelBlocks.MEDIUM_CITRINE_BUD.get(),
            PastelBlocks.SMALL_CITRINE_BUD.get(),
            PastelBlocks.LARGE_ONYX_BUD.get(),
            PastelBlocks.MEDIUM_ONYX_BUD.get(),
            PastelBlocks.SMALL_ONYX_BUD.get(),
            PastelBlocks.LARGE_MOONSTONE_BUD.get(),
            PastelBlocks.MEDIUM_MOONSTONE_BUD.get(),
            PastelBlocks.SMALL_MOONSTONE_BUD.get(),
            PastelBlocks.SMALL_BISMUTH_BUD.get(),
            PastelBlocks.SMALL_COAL_BUD.get(),
            PastelBlocks.LARGE_COAL_BUD.get(),
            PastelBlocks.SMALL_IRON_BUD.get(),
            PastelBlocks.LARGE_IRON_BUD.get(),
            PastelBlocks.SMALL_GOLD_BUD.get(),
            PastelBlocks.LARGE_GOLD_BUD.get(),
            PastelBlocks.SMALL_DIAMOND_BUD.get(),
            PastelBlocks.LARGE_DIAMOND_BUD.get(),
            PastelBlocks.SMALL_EMERALD_BUD.get(),
            PastelBlocks.LARGE_EMERALD_BUD.get(),
            PastelBlocks.SMALL_REDSTONE_BUD.get(),
            PastelBlocks.LARGE_REDSTONE_BUD.get(),
            PastelBlocks.SMALL_LAPIS_BUD.get(),
            PastelBlocks.LARGE_LAPIS_BUD.get(),
            PastelBlocks.SMALL_COPPER_BUD.get(),
            PastelBlocks.LARGE_COPPER_BUD.get(),
            PastelBlocks.SMALL_QUARTZ_BUD.get(),
            PastelBlocks.LARGE_QUARTZ_BUD.get(),
            PastelBlocks.SMALL_NETHERITE_SCRAP_BUD.get(),
            PastelBlocks.LARGE_NETHERITE_SCRAP_BUD.get(),
            PastelBlocks.SMALL_ECHO_BUD.get(),
            PastelBlocks.LARGE_ECHO_BUD.get(),
            PastelBlocks.SMALL_GLOWSTONE_BUD.get(),
            PastelBlocks.LARGE_GLOWSTONE_BUD.get(),
            PastelBlocks.SMALL_PRISMARINE_BUD.get(),
            PastelBlocks.LARGE_PRISMARINE_BUD.get(),
            PastelBlocks.LARGE_AZURITE_BUD.get(),
            PastelBlocks.SMALL_AZURITE_BUD.get(),
            PastelBlocks.LARGE_MALACHITE_BUD.get(),
            PastelBlocks.SMALL_MALACHITE_BUD.get(),
            PastelBlocks.LARGE_BLOODSTONE_BUD.get(),
            PastelBlocks.SMALL_BLOODSTONE_BUD.get(),
            CreateCompat.LARGE_ZINC_BUD.get(),
            CreateCompat.SMALL_ZINC_BUD.get(),
            AE2Compat.LARGE_FLUIX_BUD.get(),
            AE2Compat.SMALL_FLUIX_BUD.get()
        );

    private static final Map<Block, Tuple<ItemLike, NumberProvider>> oreBlocks = Map
        .ofEntries(
            Map
                .entry(
                    PastelBlocks.SHIMMERSTONE_ORE.get(),
                    new Tuple<>(PastelItems.SHIMMERSTONE_GEM, UniformGenerator.between(1, 3))
                ),
            Map
                .entry(
                    PastelBlocks.DEEPSLATE_SHIMMERSTONE_ORE.get(),
                    new Tuple<>(PastelItems.SHIMMERSTONE_GEM, UniformGenerator.between(1, 3))
                ),
            Map.entry(PastelBlocks.BLACKSLAG_COAL_ORE.get(), new Tuple<>(Items.COAL, ConstantValue.exactly(1))),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_COPPER_ORE.get(),
                    new Tuple<>(Items.RAW_COPPER, UniformGenerator.between(2, 5))
                ),
            Map.entry(PastelBlocks.BLACKSLAG_IRON_ORE.get(), new Tuple<>(Items.RAW_IRON, ConstantValue.exactly(1))),
            Map.entry(PastelBlocks.BLACKSLAG_GOLD_ORE.get(), new Tuple<>(Items.RAW_GOLD, ConstantValue.exactly(1))),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_LAPIS_ORE.get(),
                    new Tuple<>(Items.LAPIS_LAZULI, UniformGenerator.between(4, 9))
                ),
            Map.entry(PastelBlocks.BLACKSLAG_DIAMOND_ORE.get(), new Tuple<>(Items.DIAMOND, ConstantValue.exactly(1))),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_REDSTONE_ORE.get(),
                    new Tuple<>(Items.REDSTONE, UniformGenerator.between(4, 5))
                ),
            Map.entry(PastelBlocks.BLACKSLAG_EMERALD_ORE.get(), new Tuple<>(Items.EMERALD, ConstantValue.exactly(1)))
        );

    private static final Map<Block, Tuple<ItemLike, ItemLike>> gemstoneOreBlocks = Map
        .ofEntries(
            Map.entry(PastelBlocks.TOPAZ_ORE.get(), new Tuple<>(PastelItems.TOPAZ_SHARD, PastelItems.TOPAZ_POWDER)),
            Map
                .entry(
                    PastelBlocks.DEEPSLATE_TOPAZ_ORE.get(),
                    new Tuple<>(PastelItems.TOPAZ_SHARD, PastelItems.TOPAZ_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_TOPAZ_ORE.get(),
                    new Tuple<>(PastelItems.TOPAZ_SHARD, PastelItems.TOPAZ_POWDER)
                ),
            Map.entry(PastelBlocks.AMETHYST_ORE.get(), new Tuple<>(Items.AMETHYST_SHARD, PastelItems.AMETHYST_POWDER)),
            Map
                .entry(
                    PastelBlocks.DEEPSLATE_AMETHYST_ORE.get(),
                    new Tuple<>(Items.AMETHYST_SHARD, PastelItems.AMETHYST_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_AMETHYST_ORE.get(),
                    new Tuple<>(Items.AMETHYST_SHARD, PastelItems.AMETHYST_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.CITRINE_ORE.get(),
                    new Tuple<>(PastelItems.CITRINE_SHARD, PastelItems.CITRINE_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.DEEPSLATE_CITRINE_ORE.get(),
                    new Tuple<>(PastelItems.CITRINE_SHARD, PastelItems.CITRINE_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_CITRINE_ORE.get(),
                    new Tuple<>(PastelItems.CITRINE_SHARD, PastelItems.CITRINE_POWDER)
                ),
            Map.entry(PastelBlocks.ONYX_ORE.get(), new Tuple<>(PastelItems.ONYX_SHARD, PastelItems.ONYX_POWDER)),
            Map
                .entry(
                    PastelBlocks.DEEPSLATE_ONYX_ORE.get(),
                    new Tuple<>(PastelItems.ONYX_SHARD, PastelItems.ONYX_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_ONYX_ORE.get(),
                    new Tuple<>(PastelItems.ONYX_SHARD, PastelItems.ONYX_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.MOONSTONE_ORE.get(),
                    new Tuple<>(PastelItems.MOONSTONE_SHARD, PastelItems.MOONSTONE_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.DEEPSLATE_MOONSTONE_ORE.get(),
                    new Tuple<>(PastelItems.MOONSTONE_SHARD, PastelItems.MOONSTONE_POWDER)
                ),
            Map
                .entry(
                    PastelBlocks.BLACKSLAG_MOONSTONE_ORE.get(),
                    new Tuple<>(PastelItems.MOONSTONE_SHARD, PastelItems.MOONSTONE_POWDER)
                )
        );

    private void gemstoneOre(Block ore, Tuple<ItemLike, ItemLike> drops) {
        add(
            ore,
            createSilkTouchDispatchTable(
                ore,
                EntryGroup
                    .list(
                        LootItem
                            .lootTableItem(drops.getA())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                            .apply(ApplyBonusCount.addOreBonusCount(fortune))
                            .apply(ApplyExplosionDecay.explosionDecay()),
                        LootItem
                            .lootTableItem(drops.getB())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                            .apply(ApplyBonusCount.addOreBonusCount(fortune))
                            .apply(ApplyExplosionDecay.explosionDecay())
                    )
            ).setRandomSequence(ResourceLocation.withDefaultNamespace("blocks/gilded_blackstone"))
        );
    }

    private void ore(Block block, Tuple<ItemLike, NumberProvider> tuple) {
        if (tuple.getB().getType() == NumberProviders.CONSTANT) {
            add(
                block,
                createSilkTouchDispatchTable(
                    block,
                    LootItem
                        .lootTableItem(tuple.getA())
                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                        .apply(ApplyExplosionDecay.explosionDecay())
                )
            );
        } else {
            add(
                block,
                createSilkTouchDispatchTable(
                    block,
                    LootItem
                        .lootTableItem(tuple.getA())
                        .apply(SetItemCountFunction.setCount(tuple.getB()))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                        .apply(ApplyExplosionDecay.explosionDecay())
                )
            );
        }
    }

    private void gemCluster(Block clusterBlock, ItemLike gem) {
        add(
            clusterBlock,
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    LootItem.lootTableItem(clusterBlock).when(hasSilkTouch()),
                                    AlternativesEntry
                                        .alternatives(
                                            LootItem
                                                .lootTableItem(gem)
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)))
                                                .apply(ApplyBonusCount.addOreBonusCount(fortune))
                                                .when(
                                                    MatchTool
                                                        .toolMatches(
                                                            ItemPredicate.Builder
                                                                .item()
                                                                .of(
                                                                    TagKey
                                                                        .create(
                                                                            Registries.ITEM,
                                                                            ResourceLocation
                                                                                .withDefaultNamespace(
                                                                                    "cluster_max_harvestables"
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                ),
                                            LootItem
                                                .lootTableItem(gem)
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))
                                                .apply(ApplyExplosionDecay.explosionDecay())
                                        )
                                )
                        )
                )
        );
    }

    private void crystallarieumCluster(Block clusterBlock, ItemLike pureResource) {
        add(
            clusterBlock,
            createSilkTouchDispatchTable(
                clusterBlock,
                LootItem
                    .lootTableItem(pureResource)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))
            )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(dropSelfBlocks);
        blocks.addAll(gemClusterBlocks.keySet());
        blocks.addAll(silkRequiredBlocks);
        blocks.addAll(crystallarieumClusterBlocks.keySet());
        blocks.addAll(oreBlocks.keySet());
        blocks.addAll(gemstoneOreBlocks.keySet());

        blocks.add(PastelBlocks.LARGE_BISMUTH_BUD.get());
        blocks.add(PastelBlocks.BISMUTH_CLUSTER.get());
        blocks.add(PastelBlocks.AZURE_CRYSTAL.get());
        blocks.add(PastelBlocks.AZURE_OUTCROP.get());
        blocks.add(PastelBlocks.VIRIDIAN_CRYSTAL.get());
        blocks.add(PastelBlocks.STRATINE_ORE.get());
        blocks.add(PastelBlocks.PALTAERIA_ORE.get());
        blocks.add(PastelBlocks.STUCK_STORM_STONE.get());
        blocks.add(PastelBlocks.CRACKED_DRAGONBONE.get());
        return blocks;
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
        gemClusterBlocks.forEach(this::gemCluster);
        silkRequiredBlocks.forEach(this::dropWhenSilkTouch);
        crystallarieumClusterBlocks.forEach(this::crystallarieumCluster);
        oreBlocks.forEach(this::ore);
        gemstoneOreBlocks.forEach(this::gemstoneOre);

        add(
            PastelBlocks.LARGE_BISMUTH_BUD.get(),
            createSilkTouchDispatchTable(
                PastelBlocks.LARGE_BISMUTH_BUD.get(),
                LootItem.lootTableItem(PastelItems.BISMUTH_FLAKE)
            )
        );
        add(
            PastelBlocks.AZURE_CRYSTAL.get(),
            createSilkTouchDispatchTable(
                PastelBlocks.AZURE_CRYSTAL.get(),
                LootItem
                    .lootTableItem(PastelItems.RAW_AZURITE)
                    .apply(ApplyExplosionDecay.explosionDecay())
                    .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.2f, 0))
                    .apply(ApplyBonusCount.addUniformBonusCount(resonance, 1))
            )
        );
        add(
            PastelBlocks.AZURE_OUTCROP.get(),
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            LootItem
                                .lootTableItem(PastelItems.RAW_AZURITE)
                                .when(LootItemRandomChanceCondition.randomChance(0.25f))
                                .apply(ApplyExplosionDecay.explosionDecay())
                        )
                )
        );
        add(
            PastelBlocks.VIRIDIAN_CRYSTAL.get(),
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    LootItem
                                        .lootTableItem(PastelBlocks.VIRIDIAN_CRYSTAL.get())
                                        .when(
                                            AnyOfCondition
                                                .anyOf(
                                                    NearMoonstoneLootCondition.nearMoonstone(10),
                                                    MatchTool
                                                        .toolMatches(
                                                            ItemPredicate.Builder.item().of(PastelItemTags.WORKSTAFFS)
                                                        )
                                                )
                                        )
                                        .when(hasSilkTouch()),
                                    LootItem
                                        .lootTableItem(PastelItems.RAW_MALACHITE)
                                        .when(
                                            AnyOfCondition
                                                .anyOf(
                                                    NearMoonstoneLootCondition.nearMoonstone(10),
                                                    MatchTool
                                                        .toolMatches(
                                                            ItemPredicate.Builder.item().of(PastelItemTags.WORKSTAFFS)
                                                        )
                                                )
                                        )
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                                        .apply(ApplyExplosionDecay.explosionDecay()),
                                    LootItem
                                        .lootTableItem(Items.RAW_COPPER)
                                        .when(
                                            InvertedLootItemCondition
                                                .invert(
                                                    AnyOfCondition
                                                        .anyOf(
                                                            NearMoonstoneLootCondition.nearMoonstone(10),
                                                            MatchTool
                                                                .toolMatches(
                                                                    ItemPredicate.Builder
                                                                        .item()
                                                                        .of(PastelItemTags.WORKSTAFFS)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        add(
            PastelBlocks.STRATINE_ORE.get(),
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    LootItem.lootTableItem(PastelBlocks.STRATINE_ORE.get()).when(hasSilkTouch()),
                                    LootItem
                                        .lootTableItem(PastelItems.STRATINE_GEM)
                                        .when(
                                            MatchTool
                                                .toolMatches(
                                                    ItemPredicate.Builder
                                                        .item()
                                                        .withSubPredicate(
                                                            ItemSubPredicates.ENCHANTMENTS,
                                                            ItemEnchantmentsPredicate
                                                                .enchantments(
                                                                    List
                                                                        .of(
                                                                            new EnchantmentPredicate(
                                                                                resonance,
                                                                                MinMaxBounds.Ints.atLeast(1)
                                                                            )
                                                                        )
                                                                )
                                                        )
                                                )
                                        ),
                                    LootItem
                                        .lootTableItem(PastelItems.STRATINE_GEM)
                                        .when(LootItemRandomChanceCondition.randomChance(0.06f)),
                                    LootItem
                                        .lootTableItem(PastelItems.STRATINE_FRAGMENTS)
                                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                                        .apply(ApplyExplosionDecay.explosionDecay())
                                )
                        )
                )
        );
        add(
            PastelBlocks.PALTAERIA_ORE.get(),
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    LootItem.lootTableItem(PastelBlocks.PALTAERIA_ORE.get()).when(hasSilkTouch()),
                                    LootItem
                                        .lootTableItem(PastelItems.PALTAERIA_GEM)
                                        .when(
                                            MatchTool
                                                .toolMatches(
                                                    ItemPredicate.Builder
                                                        .item()
                                                        .withSubPredicate(
                                                            ItemSubPredicates.ENCHANTMENTS,
                                                            ItemEnchantmentsPredicate
                                                                .enchantments(
                                                                    List
                                                                        .of(
                                                                            new EnchantmentPredicate(
                                                                                resonance,
                                                                                MinMaxBounds.Ints.atLeast(1)
                                                                            )
                                                                        )
                                                                )
                                                        )
                                                )
                                        ),
                                    LootItem
                                        .lootTableItem(PastelItems.PALTAERIA_FRAGMENTS)
                                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                                        .apply(ApplyExplosionDecay.explosionDecay())
                                )
                        )
                )
        );
        dropOther(PastelBlocks.STUCK_STORM_STONE.get(), PastelItems.STORM_STONE);
        add(
            PastelBlocks.CRACKED_DRAGONBONE.get(),
            createSilkTouchDispatchTable(
                PastelBlocks.CRACKED_DRAGONBONE.get(),
                LootItem
                    .lootTableItem(PastelItems.DRAGONBONE_CHUNK)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
            )
        );
        add(
            PastelBlocks.BISMUTH_CLUSTER.get(),
            createSilkTouchDispatchTable(
                PastelBlocks.BISMUTH_CLUSTER.get(),
                LootItem
                    .lootTableItem(PastelItems.BISMUTH_CRYSTAL)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))
            )
        );
    }
}
