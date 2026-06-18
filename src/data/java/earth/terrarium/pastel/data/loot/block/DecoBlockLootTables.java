package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.blocks.decoration.PastelBedBlock;
import earth.terrarium.pastel.blocks.imbrifer.groundcover.AshPileBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DecoBlockLootTables extends BlockLootSubProvider {
    public DecoBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    private static final List<Block> dropSelfBlocks = List
        .of(
            PastelBlocks.ITEM_BOWL_CALCITE.get(),
            PastelBlocks.ITEM_BOWL_BASALT.get(),
            PastelBlocks.ITEM_ROUNDEL.get(),
            PastelBlocks.PRIMORDIAL_TORCH.get(),
            PastelBlocks.TOPAZ_PYLON.get(),
            PastelBlocks.AMETHYST_PYLON.get(),
            PastelBlocks.CITRINE_PYLON.get(),
            PastelBlocks.ONYX_PYLON.get(),
            PastelBlocks.MOONSTONE_PYLON.get(),
            PastelBlocks.LONGING_CHIMERA.get(),
            PastelBlocks.RESPLENDENT_BLOCK.get(),
            PastelBlocks.RESPLENDENT_CARPET.get(),
            PastelBlocks.RESPLENDENT_CUSHION.get(),
            PastelBlocks.JADE_PETAL_BLOCK.get(),
            PastelBlocks.JADEITE_PETAL_BLOCK.get(),
            PastelBlocks.JADE_PETAL_CARPET.get(),
            PastelBlocks.JADEITE_PETAL_CARPET.get(),
            PastelBlocks.STONE_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.BASALT_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.CALCITE_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.DEEPSLATE_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.BLACKSLAG_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.GRANITE_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.DIORITE_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.ANDESITE_SHIMMERSTONE_LIGHT.get(),
            PastelBlocks.TOPAZ_CHIME.get(),
            PastelBlocks.AMETHYST_CHIME.get(),
            PastelBlocks.CITRINE_CHIME.get(),
            PastelBlocks.ONYX_CHIME.get(),
            PastelBlocks.MOONSTONE_CHIME.get()
        );

    private static final List<Block> glassBlocks = List
        .of(
            PastelBlocks.TOPAZ_GLASS.get(),
            PastelBlocks.AMETHYST_GLASS.get(),
            PastelBlocks.CITRINE_GLASS.get(),
            PastelBlocks.ONYX_GLASS.get(),
            PastelBlocks.MOONSTONE_GLASS.get(),
            PastelBlocks.RADIANT_GLASS.get(),
            PastelBlocks.TOPAZ_GLASS_PANE.get(),
            PastelBlocks.AMETHYST_GLASS_PANE.get(),
            PastelBlocks.CITRINE_GLASS_PANE.get(),
            PastelBlocks.ONYX_GLASS_PANE.get(),
            PastelBlocks.MOONSTONE_GLASS_PANE.get(),
            PastelBlocks.RADIANT_GLASS_PANE.get(),
            PastelBlocks.SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.TOPAZ_SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.AMETHYST_SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.CITRINE_SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.ONYX_SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS.get(),
            PastelBlocks.HUMMINGSTONE_GLASS.get(),
            PastelBlocks.HUMMINGSTONE_GLASS_PANE.get(),
            PastelBlocks.HUMMINGSTONE.get(),
            PastelBlocks.WAXED_HUMMINGSTONE.get()
        );

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(dropSelfBlocks);
        blocks.addAll(glassBlocks);
        blocks.add(PastelBlocks.PRIMORDIAL_WALL_TORCH.get());
        blocks.add(PastelBlocks.ASH.get());
        blocks.add(PastelBlocks.ASH_PILE.get());
        blocks.add(PastelBlocks.RESPLENDENT_BED.get());
        return blocks;
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
        glassBlocks.forEach(this::dropWhenSilkTouch);
        dropOther(PastelBlocks.PRIMORDIAL_WALL_TORCH.get(), PastelBlocks.PRIMORDIAL_TORCH.get());
        add(
            PastelBlocks.ASH.get(),
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    LootItem.lootTableItem(PastelBlocks.ASH.get()).when(hasSilkTouch()),
                                    LootItem
                                        .lootTableItem(PastelItems.ASH_FLAKES)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                                        .apply(
                                            ApplyBonusCount
                                                .addUniformBonusCount(
                                                    registries
                                                        .lookupOrThrow(Registries.ENCHANTMENT)
                                                        .getOrThrow(Enchantments.FORTUNE)
                                                )
                                        )
                                        .apply(LimitCount.limitCount(IntRange.range(1, 4)))
                                        .apply(ApplyExplosionDecay.explosionDecay())
                                )
                        )
                )
        );
        add(
            // hi. i hate you.
            PastelBlocks.ASH_PILE.get(),
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    AlternativesEntry
                                        .alternatives(
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 1)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))),
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 2)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))),
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 3)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))),
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 4)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))),
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 5)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(5))),
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 6)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))),
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 7)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(7))),
                                            LootItem
                                                .lootTableItem(PastelItems.ASH_FLAKES)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 8)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(8)))
                                        )
                                        .when(doesNotHaveSilkTouch()),
                                    AlternativesEntry
                                        .alternatives(
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 1)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))),
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 2)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))),
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 3)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))),
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 4)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))),
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 5)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(5))),
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 6)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))),
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 7)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(7))),
                                            LootItem
                                                .lootTableItem(PastelBlocks.ASH_PILE)
                                                .when(
                                                    LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(PastelBlocks.ASH_PILE.get())
                                                        .setProperties(
                                                            StatePropertiesPredicate.Builder
                                                                .properties()
                                                                .hasProperty(AshPileBlock.LAYERS, 8)
                                                        )
                                                )
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(8)))
                                        )
                                        .when(hasSilkTouch())
                                )
                        )
                )
                .setRandomSequence(ResourceLocation.parse("minecraft:blocks/snow"))
        );

        add(
            PastelBlocks.RESPLENDENT_BED.get(),
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .add(
                            LootItem
                                .lootTableItem(PastelBlocks.RESPLENDENT_BED.get())
                                .when(
                                    LootItemBlockStatePropertyCondition
                                        .hasBlockStateProperties(PastelBlocks.RESPLENDENT_BED.get())
                                        .setProperties(
                                            StatePropertiesPredicate.Builder
                                                .properties()
                                                .hasProperty(PastelBedBlock.PART, BedPart.HEAD)
                                        )
                                )
                        )
                )
        );
    }
}
