package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.blocks.conditional.amaranth.AmaranthCropBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.AbyssalVineBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.AloeBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.SawbladeHollyBushBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.TallDragonjagBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeVineBulbBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeVinePlantBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlantBlockLootTables extends BlockLootSubProvider {
    public PlantBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    private static final List<Block> dropSelfBlocks = List.of(PastelBlocks.SMALL_RED_DRAGONJAG.get(), PastelBlocks.SMALL_YELLOW_DRAGONJAG.get(), PastelBlocks.SMALL_PINK_DRAGONJAG.get(), PastelBlocks.SMALL_PURPLE_DRAGONJAG.get(), PastelBlocks.SMALL_BLACK_DRAGONJAG.get(), PastelBlocks.SWEET_PEA.get(), PastelBlocks.HUMMING_BELL.get(), PastelBlocks.APRICOTTI.get(), PastelBlocks.NEPHRITE_BLOSSOM_BULB.get(), PastelBlocks.JADEITE_LOTUS_BULB.get(), PastelBlocks.QUITOXIC_REEDS.get(), PastelBlocks.CLOVER.get(), PastelBlocks.FOUR_LEAF_CLOVER.get(), PastelBlocks.AMARANTH_BUSHEL.get(), PastelBlocks.RESONANT_LILY.get());

    private static final Map<Block, Block> tallDragonjagBlocks = Map.ofEntries(Map.entry(PastelBlocks.TALL_YELLOW_DRAGONJAG.get(), PastelBlocks.SMALL_YELLOW_DRAGONJAG.get()), Map.entry(PastelBlocks.TALL_RED_DRAGONJAG.get(), PastelBlocks.SMALL_RED_DRAGONJAG.get()), Map.entry(PastelBlocks.TALL_PINK_DRAGONJAG.get(), PastelBlocks.SMALL_PINK_DRAGONJAG.get()), Map.entry(PastelBlocks.TALL_PURPLE_DRAGONJAG.get(), PastelBlocks.SMALL_PURPLE_DRAGONJAG.get()), Map.entry(PastelBlocks.TALL_BLACK_DRAGONJAG.get(), PastelBlocks.SMALL_BLACK_DRAGONJAG.get()));

    private static final List<Block> pottedPlantBlocks = List.of(PastelBlocks.POTTED_AMARANTH_BUSHEL.get(), PastelBlocks.POTTED_RESONANT_LILY.get(), PastelBlocks.POTTED_BLOOD_ORCHID.get());

    private void dragonjag(Block tall, Block small) {
        add(tall, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(small)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(tall).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).hasProperty(TallDragonjagBlock.DEAD, false))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(tall).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).hasProperty(TallDragonjagBlock.DEAD, false))), BlockPos.ZERO.above()))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(small)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(tall).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).hasProperty(TallDragonjagBlock.DEAD, false))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(tall).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).hasProperty(TallDragonjagBlock.DEAD, false))), BlockPos.ZERO.below()))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(dropSelfBlocks);
        blocks.addAll(tallDragonjagBlocks.keySet());
        blocks.addAll(pottedPlantBlocks);
        blocks.add(PastelBlocks.GLISTERING_MELON.get());
        blocks.add(PastelBlocks.GLISTERING_MELON_STEM.get());
        blocks.add(PastelBlocks.ATTACHED_GLISTERING_MELON_STEM.get());
        blocks.add(PastelBlocks.VARIA_SPROUT.get());
        blocks.add(PastelBlocks.ALOE.get());
        blocks.add(PastelBlocks.SAWBLADE_HOLLY_BUSH.get());
        blocks.add(PastelBlocks.BRISTLE_SPROUTS.get());
        blocks.add(PastelBlocks.DOOMBLOOM.get());
        blocks.add(PastelBlocks.SNAPPING_IVY.get());
        blocks.add(PastelBlocks.ABYSSAL_VINES.get());
        blocks.add(PastelBlocks.NIGHTDEW.get());
        blocks.add(PastelBlocks.MOSS_BALL.get());
        blocks.add(PastelBlocks.GIANT_MOSS_BALL.get());
        blocks.add(PastelBlocks.JADE_VINE_BULB.get());
        blocks.add(PastelBlocks.JADE_VINES.get());
        blocks.add(PastelBlocks.NEPHRITE_BLOSSOM_STEM.get());
        blocks.add(PastelBlocks.NEPHRITE_BLOSSOM_LEAVES.get());
        blocks.add(PastelBlocks.JADEITE_LOTUS_STEM.get());
        blocks.add(PastelBlocks.JADEITE_LOTUS_FLOWER.get());
        blocks.add(PastelBlocks.MERMAIDS_BRUSH.get());
        blocks.add(PastelBlocks.AMARANTH.get());
        blocks.add(PastelBlocks.BLOOD_ORCHID.get());
        return blocks;
    }

    @Override
    protected void generate() {
        Holder<Enchantment> fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
        Holder<Enchantment> silk = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
        dropSelfBlocks.forEach(this::dropSelf);
        tallDragonjagBlocks.forEach(this::dragonjag);
        pottedPlantBlocks.forEach(this::dropPottedContents);

        add(PastelBlocks.GLISTERING_MELON.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(LootItem.lootTableItem(PastelBlocks.GLISTERING_MELON.get()).when(hasSilkTouch()), LootItem.lootTableItem(Items.GLISTERING_MELON_SLICE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0f))).apply(ApplyBonusCount.addUniformBonusCount(fortune, 1)).apply(LimitCount.limitCount(IntRange.upperBound(9))).apply(ApplyExplosionDecay.explosionDecay())))));
        add(PastelBlocks.GLISTERING_MELON_STEM.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.GLISTERING_MELON_SEEDS).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.03333333f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.6666667f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.1f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.1333333f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.1666666f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.2f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.23333333f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.26666666f)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.GLISTERING_MELON_STEM.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
        add(PastelBlocks.ATTACHED_GLISTERING_MELON_STEM.get(), LootTable.lootTable().withPool(LootPool.lootPool().apply(ApplyExplosionDecay.explosionDecay()).add(LootItem.lootTableItem(PastelItems.GLISTERING_MELON_SEEDS).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(2, 0.53333336f))))));
        add(PastelBlocks.VARIA_SPROUT.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelBlocks.VARIA_SPROUT.get()).when(AnyOfCondition.anyOf(MatchTool.toolMatches(ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)), MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(List.of(new EnchantmentPredicate(silk, MinMaxBounds.Ints.atLeast(1)))))))))));
        add(PastelBlocks.ALOE.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.ALOE_LEAF.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.ALOE.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AloeBlock.AGE, 4)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.ALOE.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AloeBlock.AGE, 3)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.ALOE.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AloeBlock.AGE, 2)))))));
        add(PastelBlocks.SAWBLADE_HOLLY_BUSH.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.SAWBLADE_HOLLY_BERRY)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 3.0f))).apply(ApplyBonusCount.addUniformBonusCount(fortune, 1)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.SAWBLADE_HOLLY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SawbladeHollyBushBlock.AGE, 7)))));
        add(PastelBlocks.BRISTLE_SPROUTS.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelBlocks.BRISTLE_SPROUTS.get()).when(AnyOfCondition.anyOf(MatchTool.toolMatches(ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)), MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(List.of(new EnchantmentPredicate(silk, MinMaxBounds.Ints.atLeast(1)))))))))));
        add(PastelBlocks.DOOMBLOOM.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.DOOMBLOOM_SEED).when(AnyOfCondition.anyOf(MatchTool.toolMatches(ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)), MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(List.of(new EnchantmentPredicate(silk, MinMaxBounds.Ints.atLeast(1)))))))))));
        dropWhenSilkTouch(PastelBlocks.SNAPPING_IVY.get());
        add(PastelBlocks.ABYSSAL_VINES.get(), LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.ABYSSAL_VINES.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbyssalVineBlock.BERRIES, true))).add(LootItem.lootTableItem(PastelItems.FISSURE_PLUM))));
        add(PastelBlocks.NIGHTDEW.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.NIGHTDEW_SPROUT)).when(LootItemRandomChanceCondition.randomChance(0.334f))));
        add(PastelBlocks.MOSS_BALL.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelBlocks.MOSS_BALL.get()).when(AnyOfCondition.anyOf(MatchTool.toolMatches(ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)), MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(List.of(new EnchantmentPredicate(silk, MinMaxBounds.Ints.atLeast(1)))))))))));
        add(PastelBlocks.GIANT_MOSS_BALL.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelBlocks.GIANT_MOSS_BALL.get()).when(AnyOfCondition.anyOf(MatchTool.toolMatches(ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)), MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(List.of(new EnchantmentPredicate(silk, MinMaxBounds.Ints.atLeast(1)))))))))));
        add(PastelBlocks.JADE_VINE_BULB.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.GERMINATED_JADE_VINE_BULB).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.JADE_VINE_BULB.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JadeVineBulbBlock.DEAD, false))))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.HIBERNATING_JADE_VINE_BULB).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.JADE_VINE_BULB.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JadeVineBulbBlock.DEAD, true))))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.JADE_JELLY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).apply(ApplyExplosionDecay.explosionDecay()).apply(ApplyBonusCount.addUniformBonusCount(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(PastelEnchantments.RESONANCE), 2))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.JADE_VINE_BULB.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JadeVineBulbBlock.DEAD, true)))));
        add(PastelBlocks.JADE_VINES.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.GERMINATED_JADE_VINE_BULB)).when(InvertedLootItemCondition.invert(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.JADE_VINES.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JadeVinePlantBlock.AGE, 0))))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.HIBERNATING_JADE_VINE_BULB).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.JADE_VINES.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JadeVinePlantBlock.AGE, 0)))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3)).add(LootItem.lootTableItem(PastelItems.JADE_JELLY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.JADE_VINES.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JadeVinePlantBlock.AGE, 0)))));
        add(PastelBlocks.NEPHRITE_BLOSSOM_STEM.get(), createSilkTouchOrShearsDispatchTable(PastelBlocks.NEPHRITE_BLOSSOM_STEM.get(), LootItem.lootTableItem(Items.STICK)));
        add(PastelBlocks.NEPHRITE_BLOSSOM_LEAVES.get(), createSilkTouchOrShearsDispatchTable(PastelBlocks.NEPHRITE_BLOSSOM_LEAVES.get(), LootItem.lootTableItem(PastelBlocks.NEPHRITE_BLOSSOM_BULB.get()).when(BonusLevelTableCondition.bonusLevelFlatChance(fortune, 0.1f, 0.15f, 0.2f, 0.25f))));
        add(PastelBlocks.JADEITE_LOTUS_STEM.get(), createSilkTouchOrShearsDispatchTable(PastelBlocks.JADEITE_LOTUS_STEM.get(), LootItem.lootTableItem(Items.STICK)));
        add(PastelBlocks.JADEITE_LOTUS_FLOWER.get(), createSilkTouchOrShearsDispatchTable(PastelBlocks.JADEITE_LOTUS_FLOWER.get(), LootItem.lootTableItem(PastelBlocks.JADEITE_LOTUS_BULB.get())));
        add(PastelBlocks.MERMAIDS_BRUSH.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(PastelItems.MERMAIDS_GEM).when(AnyOfCondition.anyOf(MatchTool.toolMatches(ItemPredicate.Builder.item().of(PastelItemTags.SHEARS)), MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(List.of(new EnchantmentPredicate(silk, MinMaxBounds.Ints.atLeast(1)))))))))));
        add(PastelBlocks.AMARANTH.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(LootItem.lootTableItem(PastelBlocks.AMARANTH_BUSHEL.get()).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.AMARANTH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AmaranthCropBlock.AGE, 7))).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.5714286f, 1)), LootItem.lootTableItem(PastelItems.AMARANTH_GRAINS)))).apply(ApplyExplosionDecay.explosionDecay()));
        add(PastelBlocks.BLOOD_ORCHID.get(), createSilkTouchOrShearsDispatchTable(PastelBlocks.BLOOD_ORCHID.get(), LootItem.lootTableItem(PastelItems.BLOOD_ORCHID_PETAL)));
    }
}
