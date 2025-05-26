package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.registries.datamaps.builtin.*;

import java.util.concurrent.*;

public class SpectrumBurnTimeDataMapProvider extends DataMapProvider {

    protected SpectrumBurnTimeDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var builder = builder(NeoForgeDataMaps.FURNACE_FUELS);
        SpectrumItems.BURN_TIMES.forEach( (item, integer) ->
                builder.add(item.builtInRegistryHolder(), new FurnaceFuel(integer), false)
        );
    }

    @Override
    public String getName() {
        return "Furnace-fuels";
    }
}
