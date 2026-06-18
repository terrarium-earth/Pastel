package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.imbrifer.WeepingGalaFrondsTipBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WoodLikeBlockLootTables extends BlockLootSubProvider {
    private final Registry<Block> blocks;
    private final List<Block> dropSelfBlocks = new ArrayList<>();
    private final List<Block> pottedPlantBlocks = new ArrayList<>();
    private final List<Block> slabBlocks = new ArrayList<>();
    private final List<Block> doorBlocks = new ArrayList<>();

    public WoodLikeBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        blocks = PastelBlocks.COMMON_REGISTRAR.getRegistry().get();
        populateBlockLists();
    }

    private void populateBlockLists() {
        List<String> variants = List.of("slate_", "ebony_", "ivory_", "chestnut_", "weeping_gala_");
        List<String> genericDropSelfBlockTypes = List.of("planks", "stairs", "fence", "fence_gate", "trapdoor", "button", "pressure_plate");
        for (String variant : variants) {
            String shroomOrSprig = (Objects.equals(variant, "weeping_gala_")) ? "sprig" : "noxshroom";
            String noxwoodOrNothing = (Objects.equals(variant, "weeping_gala_")) ? "" : "noxwood_";
            pottedPlantBlocks.add(blocks.get(PastelCommon.locate("potted_" + variant + shroomOrSprig)));
            dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + shroomOrSprig)));
            for (String blockType : genericDropSelfBlockTypes) {
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + blockType)));
            }
            slabBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + "slab")));
            doorBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + "door")));
            dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + "pillar")));
            dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + "lamp")));
            dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + "light")));
            dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + "lantern")));
            dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + noxwoodOrNothing + "amphora")));
            if (!shroomOrSprig.equals("sprig")) {
                dropSelfBlocks.add(blocks.get(PastelCommon.locate("stripped_" + variant + "noxcap_stem")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + "noxcap_stem")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + "noxcap_block")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate("stripped_" + variant + "noxcap_hyphae")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + "noxcap_hyphae")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + "noxcap_gills")));
            } else {
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + "log")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate("stripped_" + variant + "log")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + "wood")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate("stripped_" + variant + "wood")));
                dropSelfBlocks.add(blocks.get(PastelCommon.locate(variant + "barrel")));
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(dropSelfBlocks);
        blocks.addAll(pottedPlantBlocks);
        blocks.addAll(slabBlocks);
        blocks.addAll(doorBlocks);
        blocks.add(PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get());
        blocks.add(PastelBlocks.WEEPING_GALA_LEAVES.get());
        return blocks;
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
        pottedPlantBlocks.forEach(this::dropPottedContents);
        slabBlocks.forEach(block -> add(block, createSlabItemTable(block)));
        doorBlocks.forEach(block -> add(block, createDoorTable(block)));

        add( // PERHAPS, THIS IS HELL.
                PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get(), LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WeepingGalaFrondsTipBlock.FORM, WeepingGalaFrondsTipBlock.Form.RESIN))).add(LootItem.lootTableItem(PastelItems.MILKY_RESIN)).add(LootItem.lootTableItem(PastelBlocks.WEEPING_GALA_SPRIG))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WeepingGalaFrondsTipBlock.FORM, WeepingGalaFrondsTipBlock.Form.SPRIG))).add(LootItem.lootTableItem(PastelBlocks.WEEPING_GALA_SPRIG))));
        add(PastelBlocks.WEEPING_GALA_LEAVES.get(), createLeavesDrops(PastelBlocks.WEEPING_GALA_LEAVES.get(), PastelBlocks.WEEPING_GALA_SPRIG.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f));
    }
}
