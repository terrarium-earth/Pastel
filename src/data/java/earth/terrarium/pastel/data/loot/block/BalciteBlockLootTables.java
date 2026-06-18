package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BalciteBlockLootTables extends BlockLootSubProvider {
    public BalciteBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    private static List<Block> slabBlocks = List
        .of(
            PastelBlocks.SMOOTH_BASALT_SLAB.get(),
            PastelBlocks.POLISHED_BASALT_SLAB.get(),
            PastelBlocks.BASALT_BRICK_SLAB.get(),
            PastelBlocks.BASALT_TILE_SLAB.get(),
            PastelBlocks.PLANED_BASALT_SLAB.get(),
            PastelBlocks.CALCITE_SLAB.get(),
            PastelBlocks.POLISHED_CALCITE_SLAB.get(),
            PastelBlocks.CALCITE_BRICK_SLAB.get(),
            PastelBlocks.CALCITE_TILE_SLAB.get(),
            PastelBlocks.PLANED_CALCITE_SLAB.get()
        );

    private static List<Block> dropSelfBlocks = List
        .of(
            PastelBlocks.SMOOTH_BASALT_STAIRS.get(),
            PastelBlocks.SMOOTH_BASALT_WALL.get(),
            PastelBlocks.POLISHED_BASALT.get(),
            PastelBlocks.POLISHED_BASALT_STAIRS.get(),
            PastelBlocks.POLISHED_BASALT_WALL.get(),
            PastelBlocks.POLISHED_BASALT_BUTTON.get(),
            PastelBlocks.POLISHED_BASALT_PRESSURE_PLATE.get(),
            PastelBlocks.CHISELED_POLISHED_BASALT.get(),
            PastelBlocks.BASALT_BRICKS.get(),
            PastelBlocks.BASALT_BRICK_STAIRS.get(),
            PastelBlocks.BASALT_BRICK_WALL.get(),
            PastelBlocks.CRACKED_BASALT_BRICKS.get(),
            PastelBlocks.BASALT_TILES.get(),
            PastelBlocks.BASALT_TILE_STAIRS.get(),
            PastelBlocks.BASALT_TILE_WALL.get(),
            PastelBlocks.CRACKED_BASALT_TILES.get(),
            PastelBlocks.PLANED_BASALT.get(),
            PastelBlocks.PLANED_BASALT_STAIRS.get(),
            PastelBlocks.PLANED_BASALT_WALL.get(),
            PastelBlocks.CALCITE_STAIRS.get(),
            PastelBlocks.CALCITE_WALL.get(),
            PastelBlocks.POLISHED_CALCITE.get(),
            PastelBlocks.POLISHED_CALCITE_STAIRS.get(),
            PastelBlocks.POLISHED_CALCITE_WALL.get(),
            PastelBlocks.POLISHED_CALCITE_BUTTON.get(),
            PastelBlocks.POLISHED_CALCITE_PRESSURE_PLATE.get(),
            PastelBlocks.CHISELED_POLISHED_CALCITE.get(),
            PastelBlocks.CALCITE_BRICKS.get(),
            PastelBlocks.CALCITE_BRICK_STAIRS.get(),
            PastelBlocks.CALCITE_BRICK_WALL.get(),
            PastelBlocks.CRACKED_CALCITE_BRICKS.get(),
            PastelBlocks.CALCITE_TILES.get(),
            PastelBlocks.CALCITE_TILE_STAIRS.get(),
            PastelBlocks.CALCITE_TILE_WALL.get(),
            PastelBlocks.CRACKED_CALCITE_TILES.get(),
            PastelBlocks.PLANED_CALCITE.get(),
            PastelBlocks.PLANED_CALCITE_STAIRS.get(),
            PastelBlocks.PLANED_CALCITE_WALL.get(),
            PastelBlocks.POLISHED_BASALT_PILLAR.get(),
            PastelBlocks.POLISHED_BASALT_CREST.get(),
            PastelBlocks.NOTCHED_POLISHED_BASALT.get(),
            PastelBlocks.TOPAZ_CHISELED_BASALT.get(),
            PastelBlocks.AMETHYST_CHISELED_BASALT.get(),
            PastelBlocks.CITRINE_CHISELED_BASALT.get(),
            PastelBlocks.ONYX_CHISELED_BASALT.get(),
            PastelBlocks.TOPAZ_CHISELED_CALCITE.get(),
            PastelBlocks.AMETHYST_CHISELED_CALCITE.get(),
            PastelBlocks.CITRINE_CHISELED_CALCITE.get(),
            PastelBlocks.ONYX_CHISELED_CALCITE.get(),
            PastelBlocks.MOONSTONE_CHISELED_BASALT.get(),
            PastelBlocks.MOONSTONE_CHISELED_CALCITE.get(),
            PastelBlocks.POLISHED_CALCITE_PILLAR.get(),
            PastelBlocks.POLISHED_CALCITE_CREST.get(),
            PastelBlocks.NOTCHED_POLISHED_CALCITE.get(),
            PastelBlocks.TOPAZ_BASALT_LIGHT.get(),
            PastelBlocks.AMETHYST_BASALT_LIGHT.get(),
            PastelBlocks.CITRINE_BASALT_LIGHT.get(),
            PastelBlocks.ONYX_BASALT_LIGHT.get(),
            PastelBlocks.MOONSTONE_BASALT_LIGHT.get(),
            PastelBlocks.TOPAZ_CALCITE_LIGHT.get(),
            PastelBlocks.AMETHYST_CALCITE_LIGHT.get(),
            PastelBlocks.CITRINE_CALCITE_LIGHT.get(),
            PastelBlocks.ONYX_CALCITE_LIGHT.get(),
            PastelBlocks.MOONSTONE_CALCITE_LIGHT.get()
        );

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(dropSelfBlocks);
        blocks.addAll(slabBlocks);
        return blocks;
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
        slabBlocks.forEach(block -> add(block, createSlabItemTable(block)));
    }
}
