package earth.terrarium.pastel.data;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;

import java.util.concurrent.CompletableFuture;

public class PastelWaxableDataMapProvider extends DataMapProvider {

    protected PastelWaxableDataMapProvider(
        PackOutput packOutput,
        CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.WAXABLES)
            .add(
                PastelBlocks.HUMMINGSTONE,
                new Waxable(PastelBlocks.WAXED_HUMMINGSTONE.get()),
                false
            );
    }

    @Override
    public String getName() {
        return "Waxables";
    }
}
