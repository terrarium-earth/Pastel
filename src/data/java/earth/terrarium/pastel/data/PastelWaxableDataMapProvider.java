package earth.terrarium.pastel.data;

import earth.terrarium.pastel.registries.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.registries.datamaps.builtin.*;

import java.util.concurrent.*;

public class PastelWaxableDataMapProvider extends DataMapProvider {

    protected PastelWaxableDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.WAXABLES).add(PastelBlocks.HUMMINGSTONE, new Waxable(PastelBlocks.WAXED_HUMMINGSTONE.get()), false);
    }

    @Override
    public String getName() {
        return "Waxables";
    }
}
