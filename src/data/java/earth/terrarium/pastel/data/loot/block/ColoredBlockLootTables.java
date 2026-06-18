package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ColoredBlockLootTables extends BlockLootSubProvider {
    private final Registry<Block> blockRegistry;

    private final Holder<Enchantment> fortune;

    private final Holder<Enchantment> silk;

    private final Holder<Enchantment> resonance;

    private final Map<Block, Tuple<ItemLike, ItemLike>> coloredLeaves = new HashMap<>();

    private final List<Block> dropSelfBlocks = new ArrayList<>();

    private final List<Block> pottedPlantBlocks = new ArrayList<>();

    private final List<Block> slabBlocks = new ArrayList<>();

    public ColoredBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        blockRegistry = PastelBlocks.COMMON_REGISTRAR.getRegistry().get();
        fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
        silk = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
        resonance = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(PastelEnchantments.RESONANCE);
        populateDropSelfAndSlabBlocks();
        populateColoredLeafBlocks();
        populatePottedPlantBlocks();
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(coloredLeaves.keySet());
        blocks.addAll(dropSelfBlocks);
        blocks.addAll(pottedPlantBlocks);
        blocks.addAll(slabBlocks);
        return blocks;
    }

    private void coloredLeaves(Block block, Tuple<ItemLike, ItemLike> tuple) {
        add(
            block,
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    LootItem
                                        .lootTableItem(block)
                                        .when(
                                            AnyOfCondition
                                                .anyOf(
                                                    MatchTool
                                                        .toolMatches(
                                                            ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)
                                                        ),
                                                    MatchTool
                                                        .toolMatches(
                                                            ItemPredicate.Builder
                                                                .item()
                                                                .withSubPredicate(
                                                                    ItemSubPredicates.ENCHANTMENTS,
                                                                    ItemEnchantmentsPredicate.Enchantments
                                                                        .enchantments(
                                                                            List
                                                                                .of(
                                                                                    new EnchantmentPredicate(
                                                                                        silk,
                                                                                        MinMaxBounds.Ints.atLeast(1)
                                                                                    )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        ),
                                    LootItem
                                        .lootTableItem(tuple.getA())
                                        .when(
                                            AnyOfCondition
                                                .anyOf(
                                                    BonusLevelTableCondition
                                                        .bonusLevelFlatChance(fortune, 0.0025f, 0.005f, 0.0075f, 0.01f),
                                                    BonusLevelTableCondition.bonusLevelFlatChance(resonance, 0f, 0.15f)
                                                )
                                        )
                                )
                        )
                )
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            LootItem
                                .lootTableItem(tuple.getB())
                                .when(
                                    BonusLevelTableCondition
                                        .bonusLevelFlatChance(fortune, 0.2f, 0.25f, 0.3f, 0.35f, 0.4f)
                                )
                        )
                        .when(
                            InvertedLootItemCondition
                                .invert(
                                    AnyOfCondition
                                        .anyOf(
                                            MatchTool
                                                .toolMatches(ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)),
                                            MatchTool
                                                .toolMatches(
                                                    ItemPredicate.Builder
                                                        .item()
                                                        .withSubPredicate(
                                                            ItemSubPredicates.ENCHANTMENTS,
                                                            ItemEnchantmentsPredicate.Enchantments
                                                                .enchantments(
                                                                    List
                                                                        .of(
                                                                            new EnchantmentPredicate(
                                                                                silk,
                                                                                MinMaxBounds.Ints.atLeast(1)
                                                                            )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static final List<String> colorPrefixes = List
        .of(
            "cyan_",
            "magenta_",
            "yellow_",
            "black_",
            "white_",
            "pink_",
            "purple_",
            "red_",
            "blue_",
            "light_blue_",
            "gray_",
            "light_gray_",
            "brown_",
            "green_",
            "lime_",
            "orange_"
        );

    private static final List<String> blockTypes = List
        .of(
            "block",
            "button",
            "fence",
            "fence_gate",
            "glowblock",
            "lamp",
            "log",
            "planks",
            "pressure_plate",
            "sapling",
            "spore_blossom",
            "stairs",
            "wood"
        );

    private void populateDropSelfAndSlabBlocks() {
        for (
            String color : colorPrefixes
        ) {
            dropSelfBlocks.add(blockRegistry.get(PastelCommon.locate("stripped_" + color + "log")));
            dropSelfBlocks.add(blockRegistry.get(PastelCommon.locate("stripped_" + color + "wood")));
            dropSelfBlocks.add(blockRegistry.get(PastelCommon.locate(color + "sapling")));
            dropSelfBlocks
                .add(blockRegistry.get(PastelCommon.locate("cushion_" + color.substring(0, color.length() - 1))));
            slabBlocks.add(blockRegistry.get(PastelCommon.locate(color + "slab")));
            for (
                String block : blockTypes
            ) {
                ResourceLocation id = PastelCommon.locate(color + block);
                Block result = blockRegistry.get(id);
                if (result != null) {
                    dropSelfBlocks.add(result);
                }
            }
        }
    }

    private void populatePottedPlantBlocks() {
        for (
            String color : colorPrefixes
        ) {
            String col = color.substring(0, color.length() - 1);
            pottedPlantBlocks.add(blockRegistry.get(PastelCommon.locate("potted_" + col + "_sapling")));
        }
    }

    private void populateColoredLeafBlocks() {
        var blocks = PastelBlocks.COMMON_REGISTRAR.getRegistry().get();
        var items = PastelItems.ITEM_REGISTRAR.getRegistry().get();
        for (
            String color : colorPrefixes
        ) {
            coloredLeaves
                .put(
                    blocks.get(PastelCommon.locate(color + "leaves")),
                    new Tuple<>(
                        Objects.requireNonNull(items.get(PastelCommon.locate(color + "sapling"))),
                        Objects.requireNonNull(items.get(PastelCommon.locate(color + "pigment")))
                    )
                );
        }
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
        coloredLeaves.forEach(this::coloredLeaves);
        pottedPlantBlocks.forEach(this::dropPottedContents);
        slabBlocks.forEach(slab -> add(slab, createSlabItemTable(slab)));
    }
}
