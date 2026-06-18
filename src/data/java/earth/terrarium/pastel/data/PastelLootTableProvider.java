package earth.terrarium.pastel.data;

import earth.terrarium.pastel.data.loot.block.BalciteBlockLootTables;
import earth.terrarium.pastel.data.loot.block.ColoredBlockLootTables;
import earth.terrarium.pastel.data.loot.block.CraftingBlockLootTables;
import earth.terrarium.pastel.data.loot.block.DecoBlockLootTables;
import earth.terrarium.pastel.data.loot.block.FunctionalBlockLootTables;
import earth.terrarium.pastel.data.loot.block.HeadBlockLootTables;
import earth.terrarium.pastel.data.loot.block.PlantBlockLootTables;
import earth.terrarium.pastel.data.loot.block.ResourceBlockLootTables;
import earth.terrarium.pastel.data.loot.block.StoneLikeBlockLootTables;
import earth.terrarium.pastel.data.loot.block.WoodLikeBlockLootTables;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PastelLootTableProvider extends LootTableProvider {

    // for sanity: every block should either have a loot table or be explicitly defined as not having one
    private static Set<ResourceKey<LootTable>> getBlockSet() {
        return Set
            .copyOf(
                PastelBlocks.COMMON_REGISTRAR
                    .getEntries()
                    .stream()
                    .filter(p -> p.get().properties.drops != BuiltInLootTables.EMPTY)
                    .map(e -> e.value().getLootTable())
                    .toList()
            );
    }

    public PastelLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
            output,
            getBlockSet(),
            List
                .of(
                    new SubProviderEntry(BalciteBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(ColoredBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(CraftingBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(DecoBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(FunctionalBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(HeadBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(PlantBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(ResourceBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(StoneLikeBlockLootTables::new, LootContextParamSets.BLOCK),
                    new SubProviderEntry(WoodLikeBlockLootTables::new, LootContextParamSets.BLOCK)
                ),
            registries
        );
    }
}
