package earth.terrarium.pastel.data;

import earth.terrarium.pastel.registries.PastelCompostableBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class PastelCompostableDataMapProvider extends DataMapProvider {
    public PastelCompostableDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        PastelCompostableBlocks.register(builder(NeoForgeDataMaps.COMPOSTABLES));
    }

    @Override
    public String getName() {
        return "Compostables";
    }
}
