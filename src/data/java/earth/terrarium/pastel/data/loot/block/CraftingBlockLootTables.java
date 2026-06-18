package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Set;

public class CraftingBlockLootTables extends BlockLootSubProvider {
    public CraftingBlockLootTables(
        HolderLookup.Provider registries
    ) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    private static final List<Block> dropSelfBlocks = List.of(
        PastelBlocks.PEDESTAL_BASIC_AMETHYST.get(), PastelBlocks.PEDESTAL_BASIC_TOPAZ.get(),
        PastelBlocks.PEDESTAL_BASIC_CITRINE.get(),
        PastelBlocks.PEDESTAL_ALL_BASIC.get(), PastelBlocks.PEDESTAL_ONYX.get(), PastelBlocks.PEDESTAL_MOONSTONE.get(),
        PastelBlocks.FUSION_SHRINE_CALCITE.get(), PastelBlocks.FUSION_SHRINE_BASALT.get(), PastelBlocks.ENCHANTER.get(),
        PastelBlocks.POTION_WORKSHOP.get(), PastelBlocks.SPIRIT_INSTILLER.get(), PastelBlocks.CRYSTALLARIEUM.get(),
        PastelBlocks.CINDERHEARTH.get(), PastelBlocks.COLOR_PICKER.get(), PastelBlocks.TITRATION_BARREL.get()
    );

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return dropSelfBlocks;
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
    }
}
