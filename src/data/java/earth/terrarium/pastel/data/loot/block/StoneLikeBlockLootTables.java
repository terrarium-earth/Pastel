package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StoneLikeBlockLootTables extends BlockLootSubProvider {
    public StoneLikeBlockLootTables(
        HolderLookup.Provider registries
    ) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    private void dropOtherWhenNotSilked(Block stoneLikeBlock, Block cobbledBlock) {
        add(stoneLikeBlock, createSingleItemTableWithSilkTouch(stoneLikeBlock, cobbledBlock));
    }

    private static final List<Block> dropSelfBlocks = List
        .of(
            PastelBlocks.BASAL_MARBLE_BRICKS.get(),
            PastelBlocks.BASAL_MARBLE_BRICK_STAIRS.get(),
            PastelBlocks.BASAL_MARBLE_BRICK_WALL.get(),
            PastelBlocks.BLACKSLAG_STAIRS.get(),
            PastelBlocks.BLACKSLAG_WALL.get(),
            PastelBlocks.COBBLED_BLACKSLAG.get(),
            PastelBlocks.COBBLED_BLACKSLAG_STAIRS.get(),
            PastelBlocks.COBBLED_BLACKSLAG_WALL.get(),
            PastelBlocks.BLACKSLAG_TILES.get(),
            PastelBlocks.BLACKSLAG_TILE_STAIRS.get(),
            PastelBlocks.BLACKSLAG_TILE_WALL.get(),
            PastelBlocks.CRACKED_BLACKSLAG_TILES.get(),
            PastelBlocks.BLACKSLAG_BRICKS.get(),
            PastelBlocks.BLACKSLAG_BRICK_STAIRS.get(),
            PastelBlocks.BLACKSLAG_BRICK_WALL.get(),
            PastelBlocks.CRACKED_BLACKSLAG_BRICKS.get(),
            PastelBlocks.POLISHED_BLACKSLAG.get(),
            PastelBlocks.POLISHED_BLACKSLAG_STAIRS.get(),
            PastelBlocks.POLISHED_BLACKSLAG_WALL.get(),
            PastelBlocks.POLISHED_BLACKSLAG_BUTTON.get(),
            PastelBlocks.POLISHED_BLACKSLAG_PRESSURE_PLATE.get(),
            PastelBlocks.CHISELED_POLISHED_BLACKSLAG.get(),
            PastelBlocks.POLISHED_SHALE_CLAY.get(),
            PastelBlocks.POLISHED_SHALE_CLAY_STAIRS.get(),
            PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.get(),
            PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS.get(),
            PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY.get(),
            PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_STAIRS.get(),
            PastelBlocks.SHALE_CLAY_BRICKS.get(),
            PastelBlocks.SHALE_CLAY_BRICK_STAIRS.get(),
            PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS.get(),
            PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS.get(),
            PastelBlocks.SHALE_CLAY_TILES.get(),
            PastelBlocks.SHALE_CLAY_TILE_STAIRS.get(),
            PastelBlocks.EXPOSED_SHALE_CLAY_TILES.get(),
            PastelBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS.get(),
            PastelBlocks.WEATHERED_SHALE_CLAY_TILES.get(),
            PastelBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS.get(),
            PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS.get(),
            PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_STAIRS.get(),
            PastelBlocks.PYRITE_STAIRS.get(),
            PastelBlocks.PYRITE_WALL.get(),
            PastelBlocks.PYRITE_TILE_STAIRS.get(),
            PastelBlocks.PYRITE_TILE_WALL.get(),
            PastelBlocks.POLISHED_BONE_ASH.get(),
            PastelBlocks.POLISHED_BONE_ASH_STAIRS.get(),
            PastelBlocks.POLISHED_BONE_ASH_WALL.get(),
            PastelBlocks.BONE_ASH_BRICKS.get(),
            PastelBlocks.BONE_ASH_BRICK_STAIRS.get(),
            PastelBlocks.BONE_ASH_BRICK_WALL.get(),
            PastelBlocks.BONE_ASH_TILES.get(),
            PastelBlocks.BONE_ASH_TILE_STAIRS.get(),
            PastelBlocks.BONE_ASH_TILE_WALL.get(),
            PastelBlocks.BASAL_MARBLE.get(),
            PastelBlocks.BASAL_MARBLE_STAIRS.get(),
            PastelBlocks.BASAL_MARBLE_WALL.get(),
            PastelBlocks.POLISHED_BASAL_MARBLE.get(),
            PastelBlocks.POLISHED_BASAL_MARBLE_STAIRS.get(),
            PastelBlocks.POLISHED_BASAL_MARBLE_WALL.get(),
            PastelBlocks.BASAL_MARBLE_TILES.get(),
            PastelBlocks.BASAL_MARBLE_TILE_STAIRS.get(),
            PastelBlocks.BASAL_MARBLE_TILE_WALL.get(),
            PastelBlocks.POLISHED_BLACKSLAG_PILLAR.get(),
            PastelBlocks.ANCIENT_CHISELED_POLISHED_BLACKSLAG.get(),
            PastelBlocks.SHALE_CLAY.get(),
            PastelBlocks.POLISHED_BONE_ASH_PILLAR.get(),
            PastelBlocks.BONE_ASH_SHINGLES.get(),
            PastelBlocks.SLUSH.get(),
            PastelBlocks.HORNSLAKE.get(),
            PastelBlocks.FLAYED_EARTH.get(),
            PastelBlocks.BASAL_MARBLE_PILLAR.get(),
            PastelBlocks.PYRITE_RIPPER.get()
        );

    private static final List<Block> slabBlocks = List
        .of(
            PastelBlocks.BASAL_MARBLE_BRICK_SLAB.get(),
            PastelBlocks.BLACKSLAG_SLAB.get(),
            PastelBlocks.COBBLED_BLACKSLAG_SLAB.get(),
            PastelBlocks.BLACKSLAG_TILE_SLAB.get(),
            PastelBlocks.BLACKSLAG_BRICK_SLAB.get(),
            PastelBlocks.POLISHED_BLACKSLAG_SLAB.get(),
            PastelBlocks.POLISHED_SHALE_CLAY_SLAB.get(),
            PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB.get(),
            PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB.get(),
            PastelBlocks.SHALE_CLAY_BRICK_SLAB.get(),
            PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB.get(),
            PastelBlocks.SHALE_CLAY_TILE_SLAB.get(),
            PastelBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB.get(),
            PastelBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB.get(),
            PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_SLAB.get(),
            PastelBlocks.PYRITE_SLAB.get(),
            PastelBlocks.PYRITE_TILE_SLAB.get(),
            PastelBlocks.POLISHED_BONE_ASH_SLAB.get(),
            PastelBlocks.BONE_ASH_BRICK_SLAB.get(),
            PastelBlocks.BONE_ASH_TILE_SLAB.get(),
            PastelBlocks.BASAL_MARBLE_SLAB.get(),
            PastelBlocks.POLISHED_BASAL_MARBLE_SLAB.get(),
            PastelBlocks.BASAL_MARBLE_TILE_SLAB.get()
        );

    private static final List<Block> pyriteBlocks = List
        .of(
            PastelBlocks.PYRITE.get(),
            PastelBlocks.PYRITE_PROJECTOR.get(),
            PastelBlocks.PYRITE_PILE.get(),
            PastelBlocks.PYRITE_TUBING.get(),
            PastelBlocks.PYRITE_RELIEF.get(),
            PastelBlocks.PYRITE_STACK.get(),
            PastelBlocks.PYRITE_PANELING.get(),
            PastelBlocks.PYRITE_VENT.get(),
            PastelBlocks.PYRITE_PLATING.get(),
            PastelBlocks.PYRITE_TILES.get()
        );

    private static final Map<Block, Block> silkRequiredBlocks = Map
        .ofEntries(
            Map.entry(PastelBlocks.BLACKSLAG.get(), PastelBlocks.COBBLED_BLACKSLAG.get()),
            Map.entry(PastelBlocks.SAWBLADE_GRASS.get(), PastelBlocks.COBBLED_BLACKSLAG.get()),
            Map.entry(PastelBlocks.SHIMMEL.get(), PastelBlocks.COBBLED_BLACKSLAG.get()),
            Map.entry(PastelBlocks.OVERGROWN_BLACKSLAG.get(), PastelBlocks.COBBLED_BLACKSLAG.get()),
            Map.entry(PastelBlocks.OVERGROWN_SLUSH.get(), PastelBlocks.SLUSH.get()),
            Map.entry(PastelBlocks.FROSTED_DEEPSLATE.get(), Blocks.COBBLED_DEEPSLATE)
        );

    private void addPyrite(Block block) {
        add(
            // i hate you
            block,
            LootTable
                .lootTable()
                .withPool(
                    LootPool
                        .lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            AlternativesEntry
                                .alternatives(
                                    LootItem
                                        .lootTableItem(block)
                                        .when(hasSilkTouch()),
                                    LootItem
                                        .lootTableItem(
                                            PastelItems.PYRITE_CHUNK
                                        )
                                        .apply(
                                            SetItemCountFunction
                                                .setCount(
                                                    ConstantValue
                                                        .exactly(
                                                            4
                                                        )
                                                )
                                        )
                                        .apply(
                                            ApplyExplosionDecay.explosionDecay()
                                        )
                                )
                        )
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(dropSelfBlocks);
        blocks.addAll(slabBlocks);
        blocks.addAll(silkRequiredBlocks.keySet());
        blocks.addAll(pyriteBlocks);
        blocks.add(PastelBlocks.INFESTED_BLACKSLAG.get());
        blocks.add(PastelBlocks.ASHEN_BLACKSLAG.get());
        blocks.add(PastelBlocks.TILLED_SLUSH.get());
        blocks.add(PastelBlocks.TILLED_SHALE_CLAY.get());
        return blocks;
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
        slabBlocks.forEach(block -> add(block, createSlabItemTable(block)));
        silkRequiredBlocks.forEach(this::dropOtherWhenNotSilked);
        pyriteBlocks.forEach(this::addPyrite);
        otherWhenSilkTouch(PastelBlocks.INFESTED_BLACKSLAG.get(), PastelBlocks.BLACKSLAG.get()); // what the fuck?
        add(
            PastelBlocks.ASHEN_BLACKSLAG.get(),
            LootTable
                .lootTable() // it's ash.
                .withPool(
                    LootPool
                        .lootPool()
                        .when(hasSilkTouch())
                        .add(
                            LootItem
                                .lootTableItem(
                                    PastelBlocks.ASHEN_BLACKSLAG.get()
                                )
                        )
                )
                .withPool(
                    LootPool
                        .lootPool()
                        .when(doesNotHaveSilkTouch())
                        .add(
                            LootItem
                                .lootTableItem(
                                    PastelBlocks.COBBLED_BLACKSLAG.get()
                                )
                        )
                )
                .withPool(
                    LootPool
                        .lootPool()
                        .when(doesNotHaveSilkTouch())
                        .add(
                            LootItem
                                .lootTableItem(
                                    PastelItems.ASH_FLAKES
                                )
                        )
                )
        );
        dropOther(PastelBlocks.TILLED_SLUSH.get(), PastelBlocks.SLUSH.get());
        dropOther(PastelBlocks.TILLED_SHALE_CLAY.get(), PastelBlocks.SHALE_CLAY.get());
    }
}
