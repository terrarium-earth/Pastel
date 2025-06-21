package earth.terrarium.pastel.capabilities;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.item.SplitDamageHandler;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class PastelCapabilities {

    public static final class Ink {
        //TODO convert ink stuff to caps
    }

    public static final class Miscellaneous {

        public static final ItemCapability<AreaMiningHandler, Void> MINING = ItemCapability.createVoid(SpectrumCommon.locate("area_mining"), AreaMiningHandler.class);

        public static final ItemCapability<SplitDamageHandler, Void> SPLIT_DAMAGE = ItemCapability.createVoid(SpectrumCommon.locate("split_damage"), SplitDamageHandler.class);

    }
}
