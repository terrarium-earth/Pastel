package earth.terrarium.pastel.data;

import earth.terrarium.pastel.registries.*;
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
        SpectrumItems.BURN_TIMES.forEach( (p) ->
                builder.add(p.getFirst().asItem().builtInRegistryHolder(), new FurnaceFuel(p.getSecond()), false)
        );
    }

    @Override
    public String getName() {
        return "Furnace-fuels";
    }
}
