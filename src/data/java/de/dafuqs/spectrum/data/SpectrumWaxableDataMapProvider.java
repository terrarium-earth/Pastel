package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.registries.datamaps.builtin.*;

import java.util.concurrent.*;

public class SpectrumWaxableDataMapProvider extends DataMapProvider {

    protected SpectrumWaxableDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.WAXABLES).add(SpectrumBlocks.HUMMINGSTONE.builtInRegistryHolder(), new Waxable(SpectrumBlocks.WAXED_HUMMINGSTONE), false);
    }

    @Override
    public String getName() {
        return "Waxables:";
    }
}
