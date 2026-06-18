package earth.terrarium.pastel.data;

import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class PastelBurnTimeDataMapProvider extends DataMapProvider {

    protected PastelBurnTimeDataMapProvider(
        PackOutput packOutput,
        CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var builder = builder(NeoForgeDataMaps.FURNACE_FUELS);
        PastelItems.BURN_TIMES
            .forEach(
                (p) -> builder
                    .add(
                        p
                            .getFirst()
                            .asItem()
                            .builtInRegistryHolder(),
                        new FurnaceFuel(p.getSecond()),
                        false
                    )
            );
    }

    @Override
    public String getName() {
        return "Furnace-fuels";
    }
}
