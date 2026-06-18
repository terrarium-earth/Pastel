package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

public class HeadBlockLootTables extends BlockLootSubProvider {
    public HeadBlockLootTables(
        HolderLookup.Provider registries
    ) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PastelBlocks.MOB_HEADS
            .values()
            .stream()
            .map(DeferredHolder::get)
            .toList();
    }

    @Override
    protected void generate() {
        for (
            DeferredBlock<Block> head : PastelBlocks.MOB_HEADS.values()
        ) {
            dropSelf(head.get());
        }
    }
}
