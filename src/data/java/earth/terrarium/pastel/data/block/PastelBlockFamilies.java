package earth.terrarium.pastel.data.block;

import com.google.common.base.Suppliers;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

// this is written like it belongs in the main classpath,
// but it wouldn't be used at all there
public class PastelBlockFamilies {
    public static final Supplier<BlockFamily> BASAL_MARBLE = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BASAL_MARBLE.get())
                    .stairs(PastelBlocks.BASAL_MARBLE_STAIRS.get())
                    .slab(PastelBlocks.BASAL_MARBLE_SLAB.get())
                    .wall(PastelBlocks.BASAL_MARBLE_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BASAL_MARBLE_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BASAL_MARBLE_BRICKS.get())
                    .stairs(PastelBlocks.BASAL_MARBLE_BRICK_STAIRS.get())
                    .slab(PastelBlocks.BASAL_MARBLE_BRICK_SLAB.get())
                    .wall(PastelBlocks.BASAL_MARBLE_BRICK_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BASAL_MARBLE_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BASAL_MARBLE_TILES.get())
                    .stairs(PastelBlocks.BASAL_MARBLE_TILE_STAIRS.get())
                    .slab(PastelBlocks.BASAL_MARBLE_TILE_SLAB.get())
                    .wall(PastelBlocks.BASAL_MARBLE_TILE_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> POLISHED_BASAL_MARBLE = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.POLISHED_BASAL_MARBLE.get())
                    .stairs(PastelBlocks.POLISHED_BASAL_MARBLE_STAIRS.get())
                    .slab(PastelBlocks.POLISHED_BASAL_MARBLE_SLAB.get())
                    .wall(PastelBlocks.POLISHED_BASAL_MARBLE_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> COBBLED_BLACKSLAG = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.COBBLED_BLACKSLAG.get())
                    .stairs(PastelBlocks.COBBLED_BLACKSLAG_STAIRS.get())
                    .slab(PastelBlocks.COBBLED_BLACKSLAG_SLAB.get())
                    .wall(PastelBlocks.COBBLED_BLACKSLAG_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BLACKSLAG = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BLACKSLAG.get())
                    .stairs(PastelBlocks.BLACKSLAG_STAIRS.get())
                    .slab(PastelBlocks.BLACKSLAG_SLAB.get())
                    .wall(PastelBlocks.BLACKSLAG_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> POLISHED_BLACKSLAG = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.POLISHED_BLACKSLAG.get())
                    .chiseled(PastelBlocks.CHISELED_POLISHED_BLACKSLAG.get())
                    .stairs(PastelBlocks.POLISHED_BLACKSLAG_STAIRS.get())
                    .slab(PastelBlocks.POLISHED_BLACKSLAG_SLAB.get())
                    .wall(PastelBlocks.POLISHED_BLACKSLAG_WALL.get())
                    .button(PastelBlocks.POLISHED_BLACKSLAG_BUTTON.get())
                    .pressurePlate(PastelBlocks.POLISHED_BLACKSLAG_PRESSURE_PLATE.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BLACKSLAG_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BLACKSLAG_BRICKS.get())
                    .stairs(PastelBlocks.BLACKSLAG_BRICK_STAIRS.get())
                    .slab(PastelBlocks.BLACKSLAG_BRICK_SLAB.get())
                    .wall(PastelBlocks.BLACKSLAG_BRICK_WALL.get())
                    .cracked(PastelBlocks.CRACKED_BLACKSLAG_BRICKS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BLACKSLAG_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BLACKSLAG_TILES.get())
                    .cracked(PastelBlocks.CRACKED_BLACKSLAG_TILES.get())
                    .slab(PastelBlocks.BLACKSLAG_TILE_SLAB.get())
                    .stairs(PastelBlocks.BLACKSLAG_TILE_STAIRS.get())
                    .wall(PastelBlocks.BLACKSLAG_TILE_WALL.get())
                    .getFamily()
    );
    
    public record BalciteMegaFamily(
            BlockFamily polished,
            BlockFamily vanilla,
            BlockFamily bricks,
            BlockFamily tiles,
            BlockFamily planed,
            Block notched,
            Block pillar,
            Block crest
    ) {}

    public static final Supplier<BlockFamily> POLISHED_BASALT = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.POLISHED_BASALT.get())
                    .slab(PastelBlocks.POLISHED_BASALT_SLAB.get())
                    .stairs(PastelBlocks.POLISHED_BASALT_STAIRS.get())
                    .wall(PastelBlocks.POLISHED_BASALT_WALL.get())
                    .button(PastelBlocks.POLISHED_BASALT_BUTTON.get())
                    .pressurePlate(PastelBlocks.POLISHED_BASALT_PRESSURE_PLATE.get())
                    .chiseled(PastelBlocks.CHISELED_POLISHED_BASALT.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> SMOOTH_BASALT = Suppliers.memoize(() ->
            new BlockFamily.Builder(Blocks.SMOOTH_BASALT)
                    .stairs(PastelBlocks.SMOOTH_BASALT_STAIRS.get())
                    .slab(PastelBlocks.SMOOTH_BASALT_SLAB.get())
                    .wall(PastelBlocks.SMOOTH_BASALT_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BASALT_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BASALT_BRICKS.get())
                    .stairs(PastelBlocks.BASALT_BRICK_STAIRS.get())
                    .slab(PastelBlocks.BASALT_BRICK_SLAB.get())
                    .wall(PastelBlocks.BASALT_BRICK_WALL.get())
                    .cracked(PastelBlocks.CRACKED_BASALT_BRICKS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BASALT_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BASALT_TILES.get())
                    .stairs(PastelBlocks.BASALT_TILE_STAIRS.get())
                    .slab(PastelBlocks.BASALT_TILE_SLAB.get())
                    .wall(PastelBlocks.BASALT_TILE_WALL.get())
                    .cracked(PastelBlocks.CRACKED_BASALT_TILES.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> PLANED_BASALT = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.PLANED_BASALT.get())
                    .stairs(PastelBlocks.PLANED_BASALT_STAIRS.get())
                    .slab(PastelBlocks.PLANED_BASALT_SLAB.get())
                    .wall(PastelBlocks.PLANED_BASALT_WALL.get())
                    .getFamily()
    );
    
    public static final Supplier<BalciteMegaFamily> BASALT_ALL = Suppliers.memoize(() ->
        new BalciteMegaFamily(
                POLISHED_BASALT.get(),
                SMOOTH_BASALT.get(),
                BASALT_BRICKS.get(),
                BASALT_TILES.get(),
                PLANED_BASALT.get(),
                PastelBlocks.NOTCHED_POLISHED_BASALT.get(),
                PastelBlocks.POLISHED_BASALT_PILLAR.get(),
                PastelBlocks.POLISHED_BASALT_CREST.get()
        )
    );

    public static final Supplier<BlockFamily> POLISHED_CALCITE = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.POLISHED_CALCITE.get())
                    .slab(PastelBlocks.POLISHED_CALCITE_SLAB.get())
                    .stairs(PastelBlocks.POLISHED_CALCITE_STAIRS.get())
                    .wall(PastelBlocks.POLISHED_CALCITE_WALL.get())
                    .button(PastelBlocks.POLISHED_CALCITE_BUTTON.get())
                    .pressurePlate(PastelBlocks.POLISHED_CALCITE_PRESSURE_PLATE.get())
                    .chiseled(PastelBlocks.CHISELED_POLISHED_CALCITE.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> CALCITE = Suppliers.memoize(() ->
            new BlockFamily.Builder(Blocks.CALCITE)
                    .stairs(PastelBlocks.CALCITE_STAIRS.get())
                    .slab(PastelBlocks.CALCITE_SLAB.get())
                    .wall(PastelBlocks.CALCITE_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> CALCITE_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.CALCITE_BRICKS.get())
                    .stairs(PastelBlocks.CALCITE_BRICK_STAIRS.get())
                    .slab(PastelBlocks.CALCITE_BRICK_SLAB.get())
                    .wall(PastelBlocks.CALCITE_BRICK_WALL.get())
                    .cracked(PastelBlocks.CRACKED_CALCITE_BRICKS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> CALCITE_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.CALCITE_TILES.get())
                    .stairs(PastelBlocks.CALCITE_TILE_STAIRS.get())
                    .slab(PastelBlocks.CALCITE_TILE_SLAB.get())
                    .wall(PastelBlocks.CALCITE_TILE_WALL.get())
                    .cracked(PastelBlocks.CRACKED_CALCITE_TILES.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> PLANED_CALCITE = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.PLANED_CALCITE.get())
                    .stairs(PastelBlocks.PLANED_CALCITE_STAIRS.get())
                    .slab(PastelBlocks.PLANED_CALCITE_SLAB.get())
                    .wall(PastelBlocks.PLANED_CALCITE_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BalciteMegaFamily> CALCITE_ALL = Suppliers.memoize(() ->
            new BalciteMegaFamily(
                    POLISHED_CALCITE.get(),
                    CALCITE.get(),
                    CALCITE_BRICKS.get(),
                    CALCITE_TILES.get(),
                    PLANED_CALCITE.get(),
                    PastelBlocks.NOTCHED_POLISHED_CALCITE.get(),
                    PastelBlocks.POLISHED_CALCITE_PILLAR.get(),
                    PastelBlocks.POLISHED_CALCITE_CREST.get()
            )
    );

    public static final Supplier<BlockFamily> POLISHED_SHALE_CLAY = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.POLISHED_SHALE_CLAY.get())
                    .slab(PastelBlocks.POLISHED_SHALE_CLAY_SLAB.get())
                    .stairs(PastelBlocks.POLISHED_SHALE_CLAY_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> WEATHERED_POLISHED_SHALE_CLAY = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY.get())
                    .slab(PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB.get())
                    .stairs(PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> EXPOSED_POLISHED_SHALE_CLAY = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.get())
                    .slab(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB.get())
                    .stairs(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> SHALE_CLAY_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.SHALE_CLAY_BRICKS.get())
                    .slab(PastelBlocks.SHALE_CLAY_BRICK_SLAB.get())
                    .stairs(PastelBlocks.SHALE_CLAY_BRICK_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> WEATHERED_SHALE_CLAY_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS.get())
                    .slab(PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_SLAB.get())
                    .stairs(PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> EXPOSED_SHALE_CLAY_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS.get())
                    .slab(PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB.get())
                    .stairs(PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> SHALE_CLAY_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.SHALE_CLAY_TILES.get())
                    .slab(PastelBlocks.SHALE_CLAY_TILE_SLAB.get())
                    .stairs(PastelBlocks.SHALE_CLAY_TILE_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> WEATHERED_SHALE_CLAY_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.WEATHERED_SHALE_CLAY_TILES.get())
                    .slab(PastelBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB.get())
                    .stairs(PastelBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> EXPOSED_SHALE_CLAY_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.EXPOSED_SHALE_CLAY_TILES.get())
                    .slab(PastelBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB.get())
                    .stairs(PastelBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> POLISHED_BONE_ASH = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.POLISHED_BONE_ASH.get())
                    .stairs(PastelBlocks.POLISHED_BONE_ASH_STAIRS.get())
                    .slab(PastelBlocks.POLISHED_BONE_ASH_SLAB.get())
                    .wall(PastelBlocks.POLISHED_BONE_ASH_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BONE_ASH_BRICKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BONE_ASH_BRICKS.get())
                    .stairs(PastelBlocks.BONE_ASH_BRICK_STAIRS.get())
                    .slab(PastelBlocks.BONE_ASH_BRICK_SLAB.get())
                    .wall(PastelBlocks.BONE_ASH_BRICK_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> BONE_ASH_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.BONE_ASH_TILES.get())
                    .stairs(PastelBlocks.BONE_ASH_TILE_STAIRS.get())
                    .slab(PastelBlocks.BONE_ASH_TILE_SLAB.get())
                    .wall(PastelBlocks.BONE_ASH_TILE_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> PYRITE = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.PYRITE.get())
                    .stairs(PastelBlocks.PYRITE_STAIRS.get())
                    .slab(PastelBlocks.PYRITE_SLAB.get())
                    .wall(PastelBlocks.PYRITE_WALL.get())
                    .getFamily()
    );

    public static final Supplier<BlockFamily> PYRITE_TILES = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.PYRITE_TILES.get())
                    .stairs(PastelBlocks.PYRITE_TILE_STAIRS.get())
                    .slab(PastelBlocks.PYRITE_TILE_SLAB.get())
                    .wall(PastelBlocks.PYRITE_TILE_WALL.get())
                    .getFamily()
    );

    public record WoodFamily(
            BlockFamily plankFamily,
            Block log,
            Block wood,
            Block strippedLog,
            Block strippedWood,
            TagKey<Item> logs
    ) {
        public WoodFamily(
                BlockFamily family,
                DeferredBlock<Block> log,
                DeferredBlock<Block> wood,
                DeferredBlock<Block> strippedLog,
                DeferredBlock<Block> strippedWood,
                TagKey<Item> logs
        ) {
            this(family, log.get(), wood.get(), strippedLog.get(), strippedWood.get(), logs);
        }


        public Block planks() {
            return plankFamily.getBaseBlock();
        }
    }

    public static final Supplier<BlockFamily> WEEPING_GALA_PLANKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.WEEPING_GALA_PLANKS.get())
                    .button(PastelBlocks.WEEPING_GALA_BUTTON.get())
                    .door(PastelBlocks.WEEPING_GALA_DOOR.get())
                    .fence(PastelBlocks.WEEPING_GALA_FENCE.get())
                    .fenceGate(PastelBlocks.WEEPING_GALA_FENCE_GATE.get())
                    .pressurePlate(PastelBlocks.WEEPING_GALA_PRESSURE_PLATE.get())
                    .slab(PastelBlocks.WEEPING_GALA_SLAB.get())
                    .stairs(PastelBlocks.WEEPING_GALA_STAIRS.get())
                    .trapdoor(PastelBlocks.WEEPING_GALA_TRAPDOOR.get())
                    .getFamily()
    );

    public static final Supplier<WoodFamily> WEEPING_GALA = Suppliers.memoize(() ->
            new WoodFamily(
                    WEEPING_GALA_PLANKS.get(),
                    PastelBlocks.WEEPING_GALA_LOG.get(),
                    PastelBlocks.WEEPING_GALA_WOOD.get(),
                    PastelBlocks.STRIPPED_WEEPING_GALA_LOG.get(),
                    PastelBlocks.STRIPPED_WEEPING_GALA_WOOD.get(),
                    PastelItemTags.WEEPING_GALA_LOGS
            )
    );

    public static final Supplier<PastelInkColorCollection<BlockFamily>> COLORED_PLANKS = Suppliers.memoize(() ->
            PastelInkColorCollection.unbox(
                PastelInkColorCollection.Instance.INSTANCE.apply7(
                    PastelBlockFamilies::makeColoredFamily,
                    PastelBlocks.COLORED_PLANKS,
                    PastelBlocks.COLORED_BUTTONS,
                    PastelBlocks.COLORED_FENCES,
                    PastelBlocks.COLORED_FENCE_GATES,
                    PastelBlocks.COLORED_PRESSURE_PLATES,
                    PastelBlocks.COLORED_SLABS,
                    PastelBlocks.COLORED_STAIRS
                )
            )
    );

    public static final Supplier<PastelInkColorCollection<WoodFamily>> COLORED_WOODS = Suppliers.memoize(() ->
            PastelInkColorCollection.unbox(
                    PastelInkColorCollection.Instance.INSTANCE.apply6(
                            WoodFamily::new,
                            COLORED_PLANKS.get(),
                            PastelBlocks.COLORED_LOGS,
                            PastelBlocks.COLORED_WOODS,
                            PastelBlocks.STRIPPED_COLORED_LOGS,
                            PastelBlocks.STRIPPED_COLORED_WOODS,
                            PastelItemTags.ColoredLogs.VALUES
                    )
            )
    );

    private static BlockFamily makeColoredFamily(
            DeferredBlock<Block> planks,
            DeferredBlock<Block> button,
            DeferredBlock<Block> fence,
            DeferredBlock<Block> fenceGate,
            DeferredBlock<Block> pressurePlate,
            DeferredBlock<Block> slab,
            DeferredBlock<Block> stairs
    ) {
        return new BlockFamily.Builder(planks.get())
                .button(button.get())
                .fence(fence.get())
                .fenceGate(fenceGate.get())
                .pressurePlate(pressurePlate.get())
                .slab(slab.get())
                .stairs(stairs.get())
                .getFamily();
    }

    public static final Supplier<BlockFamily> CHESTNUT_PLANKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.CHESTNUT_NOXWOOD_PLANKS.get())
                    .button(PastelBlocks.CHESTNUT_NOXWOOD_BUTTON.get())
                    .door(PastelBlocks.CHESTNUT_NOXWOOD_DOOR.get())
                    .fence(PastelBlocks.CHESTNUT_NOXWOOD_FENCE.get())
                    .fenceGate(PastelBlocks.CHESTNUT_NOXWOOD_FENCE_GATE.get())
                    .pressurePlate(PastelBlocks.CHESTNUT_NOXWOOD_PRESSURE_PLATE.get())
                    .slab(PastelBlocks.CHESTNUT_NOXWOOD_SLAB.get())
                    .stairs(PastelBlocks.CHESTNUT_NOXWOOD_STAIRS.get())
                    .trapdoor(PastelBlocks.CHESTNUT_NOXWOOD_TRAPDOOR.get())
                    .getFamily()
    );

    public static final Supplier<WoodFamily> CHESTNUT_NOXWOOD = Suppliers.memoize(() ->
            new WoodFamily(
                    CHESTNUT_PLANKS.get(),
                    PastelBlocks.CHESTNUT_NOXCAP_STEM,
                    PastelBlocks.CHESTNUT_NOXCAP_HYPHAE,
                    PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM,
                    PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE,
                    PastelItemTags.CHESTNUT_NOXCAP_STEMS
            )
    );

    public static final Supplier<BlockFamily> EBONY_PLANKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.EBONY_NOXWOOD_PLANKS.get())
                    .button(PastelBlocks.EBONY_NOXWOOD_BUTTON.get())
                    .door(PastelBlocks.EBONY_NOXWOOD_DOOR.get())
                    .fence(PastelBlocks.EBONY_NOXWOOD_FENCE.get())
                    .fenceGate(PastelBlocks.EBONY_NOXWOOD_FENCE_GATE.get())
                    .pressurePlate(PastelBlocks.EBONY_NOXWOOD_PRESSURE_PLATE.get())
                    .slab(PastelBlocks.EBONY_NOXWOOD_SLAB.get())
                    .stairs(PastelBlocks.EBONY_NOXWOOD_STAIRS.get())
                    .trapdoor(PastelBlocks.EBONY_NOXWOOD_TRAPDOOR.get())
                    .getFamily()
    );

    public static final Supplier<WoodFamily> EBONY_NOXWOOD = Suppliers.memoize(() ->
            new WoodFamily(
                    EBONY_PLANKS.get(),
                    PastelBlocks.EBONY_NOXCAP_STEM,
                    PastelBlocks.EBONY_NOXCAP_HYPHAE,
                    PastelBlocks.STRIPPED_EBONY_NOXCAP_STEM,
                    PastelBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE,
                    PastelItemTags.EBONY_NOXCAP_STEMS
            )
    );

    public static final Supplier<BlockFamily> IVORY_PLANKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.IVORY_NOXWOOD_PLANKS.get())
                    .button(PastelBlocks.IVORY_NOXWOOD_BUTTON.get())
                    .door(PastelBlocks.IVORY_NOXWOOD_DOOR.get())
                    .fence(PastelBlocks.IVORY_NOXWOOD_FENCE.get())
                    .fenceGate(PastelBlocks.IVORY_NOXWOOD_FENCE_GATE.get())
                    .pressurePlate(PastelBlocks.IVORY_NOXWOOD_PRESSURE_PLATE.get())
                    .slab(PastelBlocks.IVORY_NOXWOOD_SLAB.get())
                    .stairs(PastelBlocks.IVORY_NOXWOOD_STAIRS.get())
                    .trapdoor(PastelBlocks.IVORY_NOXWOOD_TRAPDOOR.get())
                    .getFamily()
    );

    public static final Supplier<WoodFamily> IVORY_NOXWOOD = Suppliers.memoize(() ->
            new WoodFamily(
                    IVORY_PLANKS.get(),
                    PastelBlocks.IVORY_NOXCAP_STEM,
                    PastelBlocks.IVORY_NOXCAP_HYPHAE,
                    PastelBlocks.STRIPPED_IVORY_NOXCAP_STEM,
                    PastelBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE,
                    PastelItemTags.IVORY_NOXCAP_STEMS
            )
    );

    public static final Supplier<BlockFamily> SLATE_PLANKS = Suppliers.memoize(() ->
            new BlockFamily.Builder(PastelBlocks.SLATE_NOXWOOD_PLANKS.get())
                    .button(PastelBlocks.SLATE_NOXWOOD_BUTTON.get())
                    .door(PastelBlocks.SLATE_NOXWOOD_DOOR.get())
                    .fence(PastelBlocks.SLATE_NOXWOOD_FENCE.get())
                    .fenceGate(PastelBlocks.SLATE_NOXWOOD_FENCE_GATE.get())
                    .pressurePlate(PastelBlocks.SLATE_NOXWOOD_PRESSURE_PLATE.get())
                    .slab(PastelBlocks.SLATE_NOXWOOD_SLAB.get())
                    .stairs(PastelBlocks.SLATE_NOXWOOD_STAIRS.get())
                    .trapdoor(PastelBlocks.SLATE_NOXWOOD_TRAPDOOR.get())
                    .getFamily()
    );

    public static final Supplier<WoodFamily> SLATE_NOXWOOD = Suppliers.memoize(() ->
            new WoodFamily(
                    SLATE_PLANKS.get(),
                    PastelBlocks.SLATE_NOXCAP_STEM,
                    PastelBlocks.SLATE_NOXCAP_HYPHAE,
                    PastelBlocks.STRIPPED_SLATE_NOXCAP_STEM,
                    PastelBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE,
                    PastelItemTags.SLATE_NOXCAP_STEMS
            )
    );
}
