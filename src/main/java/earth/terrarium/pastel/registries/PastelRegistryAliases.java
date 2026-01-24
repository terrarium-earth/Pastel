package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;

public class PastelRegistryAliases {
    public static void registerAliases(){
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("decaying_light"),PastelBlocks.TEMPORAL_SHIMMERSTONE_LIGHT.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("deeper_down_portal"),PastelBlocks.IMBRIFER_PORTAL.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("hover_block"),PastelBlocks.HOVERBLOCK.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("item_bowl_enlightenment"),PastelBlocks.ENLIGHTENMENT_ITEM_BOWL.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("jade_vine_petal_block"),PastelBlocks.JADE_PETAL_BLOCK.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("jade_vine_petal_carpet"),PastelBlocks.JADE_PETAL_CARPET.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("light_level_detector"),PastelBlocks.BLOCK_LIGHT_DETECTOR.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("wand_light"),PastelBlocks.SHIMMERSTONE_LIGHT.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("pyrite_tiles_slab"),PastelBlocks.PYRITE_TILE_SLAB.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("pyrite_tiles_stairs"),PastelBlocks.PYRITE_TILE_STAIRS.getId());
        PastelBlocks.COMMON_REGISTRAR.addAlias(PastelCommon.locate("pyrite_tiles_wall"),PastelBlocks.PYRITE_TILE_WALL.getId());

        PastelItems.ITEM_REGISTRAR.addAlias(PastelCommon.locate("dragonbone_broth"),PastelItems.WYRMSCALE_JELLY.getId());
        PastelItems.ITEM_REGISTRAR.addAlias(PastelCommon.locate("glow_vision_goggles"),PastelItems.PRISCILLENT_SPECTACLES.getId());
        PastelItems.ITEM_REGISTRAR.addAlias(PastelCommon.locate("jade_vine_petals"),PastelItems.JADE_PETALS.getId());
        PastelItems.ITEM_REGISTRAR.addAlias(PastelCommon.locate("lizard_spawn_egg"),PastelItems.LIZARD_SPAWN_EGG.getId());
        PastelItems.ITEM_REGISTRAR.addAlias(PastelCommon.locate("ring_of_aerial_grace"),PastelItems.RING_OF_AETHERIAL_GRACE.getId());
        PastelItems.ITEM_REGISTRAR.addAlias(PastelCommon.locate("take_off_belt"),PastelItems.TAKEOFF_BELT.getId());
        PastelItems.ITEM_REGISTRAR.addAlias(PastelCommon.locate("logo_banner_pattern"),PastelItems.COLOR_THEORY_BANNER_PATTERN.getId());
    }
}
