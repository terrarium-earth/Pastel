package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.SpectrumCompostableBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class SpectrumCompostableDataMapProvider extends DataMapProvider {
    public SpectrumCompostableDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        SpectrumCompostableBlocks.register(builder(NeoForgeDataMaps.COMPOSTABLES));
    }

    @Override
    public String getName() {
        return "Compostables";
    }
}
